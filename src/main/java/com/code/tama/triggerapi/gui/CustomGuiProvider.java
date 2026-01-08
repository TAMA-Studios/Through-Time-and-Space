package com.code.tama.triggerapi.gui;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomGuiProvider implements MenuProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomGuiProvider.class);
    
    private final GuiDefinition definition;
    private final ResourceLocation guiId;
    
    public CustomGuiProvider(GuiDefinition definition, ResourceLocation guiId) {
        this.definition = definition;
        this.guiId = guiId;
    }
    
    public static void openGui(ServerPlayer player, ResourceLocation guiId) {
        GuiDefinition def = GuiLoader.getGuiDefinition(guiId);
        if (def == null) {
            LOGGER.error("GUI definition not found: {}", guiId);
            return;
        }
        
        player.openMenu(new CustomGuiProvider(def, guiId));
    }
    
    @Override
    public Component getDisplayName() {
        return Component.literal(definition.getTitle() != null ? definition.getTitle() : "GUI");
    }
    
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInv, Player player) {
        int size = definition.getSize();
        MenuType<?> menuType = getMenuType(size);
        
        SimpleContainer container = new SimpleContainer(size);
        
        // Fill background if specified
        if (definition.getBackground() != null && definition.getBackground().shouldFillEmpty()) {
            ItemStack bgItem = createItemStack(definition.getBackground().getItem());
            for (int i = 0; i < size; i++) {
                container.setItem(i, bgItem.copy());
            }
        }
        
        // Place buttons
        if (definition.getButtons() != null) {
            for (GuiDefinition.ButtonDefinition button : definition.getButtons()) {
                if (button.getSlot() >= 0 && button.getSlot() < size) {
                    ItemStack stack = createButton(button);
                    container.setItem(button.getSlot(), stack);
                }
            }
        }
        
        return new CustomChestMenu(menuType, containerId, playerInv, container, 
                                   definition, guiId, size / 9);
    }
    
    private ItemStack createButton(GuiDefinition.ButtonDefinition button) {
        ItemStack stack = createItemStack(button.getItem());
        
        if (button.getName() != null) {
            stack.setHoverName(Component.literal(button.getName()));
        }
        
        if (button.getLore() != null && !button.getLore().isEmpty()) {
            net.minecraft.nbt.CompoundTag display = stack.getOrCreateTagElement("display");
            net.minecraft.nbt.ListTag loreList = new net.minecraft.nbt.ListTag();
            
            for (String line : button.getLore()) {
                loreList.add(net.minecraft.nbt.StringTag.valueOf(
                    Component.Serializer.toJson(Component.literal(line))));
            }
            
            display.put("Lore", loreList);
        }
        
        if (button.isGlow()) {
            stack.enchant(Enchantments.UNBREAKING, 1);
            stack.getOrCreateTag().putInt("HideFlags", 1);
        }
        
        return stack;
    }
    
    private ItemStack createItemStack(String itemId) {
        if (itemId == null) {
            return new ItemStack(Items.STONE);
        }
        
        try {
            ResourceLocation id = new ResourceLocation(itemId);
            Item item = BuiltInRegistries.ITEM.get(id);
            return new ItemStack(item);
        } catch (Exception e) {
            LOGGER.error("Invalid item ID: {}", itemId);
            return new ItemStack(Items.BARRIER);
        }
    }
    
    private MenuType<?> getMenuType(int size) {
        int rows = size / 9;
        return switch (rows) {
            case 1 -> MenuType.GENERIC_9x1;
            case 2 -> MenuType.GENERIC_9x2;
            case 3 -> MenuType.GENERIC_9x3;
            case 4 -> MenuType.GENERIC_9x4;
            case 5 -> MenuType.GENERIC_9x5;
            case 6 -> MenuType.GENERIC_9x6;
            default -> MenuType.GENERIC_9x3;
        };
    }
    
    private static class CustomChestMenu extends ChestMenu {
        private final GuiDefinition definition;
        private final ResourceLocation guiId;
        
        public CustomChestMenu(MenuType<?> type, int containerId, Inventory playerInv,
                              SimpleContainer container, GuiDefinition definition,
                              ResourceLocation guiId, int rows) {
            super(type, containerId, playerInv, container, rows);
            this.definition = definition;
            this.guiId = guiId;
        }
        
        @Override
        public void clicked(int slotId, int button, ClickType clickType, Player player) {
            // Prevent taking items from GUI
            if (slotId >= 0 && slotId < this.getContainer().getContainerSize()) {
                // Find button definition for this slot
                GuiDefinition.ButtonDefinition buttonDef = null;
                if (definition.getButtons() != null) {
                    for (GuiDefinition.ButtonDefinition def : definition.getButtons()) {
                        if (def.getSlot() == slotId) {
                            buttonDef = def;
                            break;
                        }
                    }
                }
                
                if (buttonDef != null && buttonDef.getScript() != null) {
                    // Execute script
                    LuaScriptEngine.ScriptContext context = new LuaScriptEngine.ScriptContext();
                    context.set("guiId", guiId.toString());
                    context.set("slot", slotId);
                    context.set("button", button);
                    context.set("clickType", clickType.name());
                    
                    LuaScriptEngine.ScriptResult result = 
                        LuaScriptEngine.executeScript(buttonDef.getScript(), player, context);
                    
                    if (!result.isSuccess()) {
                        player.sendSystemMessage(Component.literal("§cScript Error: " + result.getMessage()));
                    }
                    
                    if (buttonDef.shouldCloseOnClick()) {
                        player.closeContainer();
                    }
                }
                
                return; // Prevent default behavior
            }
            
            super.clicked(slotId, button, clickType, player);
        }
    }
}