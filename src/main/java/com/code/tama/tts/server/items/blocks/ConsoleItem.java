/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items.blocks;

import com.code.tama.tts.client.renderers.items.ConsoleItemRenderer;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;

public class ConsoleItem<T extends AbstractConsoleTile> extends BlockItem {
    RegistryObject<BlockEntityType<T>> type;

    public ConsoleItem(RegistryObject<BlockEntityType<T>> type, Block block, Properties properties) {
        super(block, properties);
        this.type = type;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new ConsoleItemRenderer(
                        Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                        Minecraft.getInstance().getEntityModels(),
                        type.get(),
                        getBlock().defaultBlockState());
            }
        });
    }
}
