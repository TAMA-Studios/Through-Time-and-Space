package com.code.tama.triggerapi.networking.gui;

import com.code.tama.triggerapi.gui.GuiDefinition;
import com.code.tama.triggerapi.gui.GuiLoader;
import com.code.tama.triggerapi.gui.LuaScriptEngine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ButtonClickPacket {
    private final ResourceLocation guiId;
    private final String elementId;
    private final int button;
    
    public ButtonClickPacket(ResourceLocation guiId, String elementId, int button) {
        this.guiId = guiId;
        this.elementId = elementId;
        this.button = button;
    }
    
    public static void encode(ButtonClickPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.guiId);
        buffer.writeUtf(packet.elementId);
        buffer.writeInt(packet.button);
    }
    
    public static ButtonClickPacket decode(FriendlyByteBuf buffer) {
        return new ButtonClickPacket(
            buffer.readResourceLocation(),
            buffer.readUtf(),
            buffer.readInt()
        );
    }
    
    public static void handle(ButtonClickPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            
            GuiDefinition definition = GuiLoader.getGuiDefinition(packet.guiId);
            if (definition == null || definition.getElements() == null) return;
            
            // Find the clicked element
            for (GuiDefinition.GuiElement element : definition.getElements()) {
                if (element.getId() != null && element.getId().equals(packet.elementId)) {
                    if (element.getScript() != null) {
                        executeScript(element.getScript(), player, packet.guiId, packet.elementId, packet.button);
                    }
                    break;
                }
            }
        });
        context.setPacketHandled(true);
    }
    
    private static void executeScript(String scriptRef, ServerPlayer player, 
                                     ResourceLocation guiId, String elementId, int button) {
        String script;
        
        // Check if it's a script file reference
        boolean isFileReference = scriptRef.contains(":") || 
                                 (scriptRef.contains("/") && !scriptRef.trim().startsWith("player.") && 
                                  !scriptRef.trim().startsWith("if") && !scriptRef.trim().startsWith("local"));
        
        if (isFileReference) {
            // Script file reference
            script = GuiLoader.getLuaScript(scriptRef);
            if (script == null) {
                player.sendSystemMessage(Component.literal("§cScript not found: " + scriptRef));
                return;
            }
        } else {
            // Inline script
            script = scriptRef;
        }
        
        // Execute script
        LuaScriptEngine.ScriptContext context = new LuaScriptEngine.ScriptContext();
        context.set("guiId", guiId.toString());
        context.set("elementId", elementId);
        context.set("button", button);
        
        LuaScriptEngine.ScriptResult result = LuaScriptEngine.executeScript(script, player, context);
        
        if (!result.isSuccess()) {
            player.sendSystemMessage(Component.literal("§cScript Error: " + result.getMessage()));
        }
    }
}