/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.registries.TTSTileEntities;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SonicConfiguratorTileEntity extends BlockEntity {
    @Getter
    @Setter
    ItemStack stack = ItemStack.EMPTY;

    public SonicConfiguratorTileEntity(BlockPos pos, BlockState state) {
        super(TTSTileEntities.SONIC_CONFIGURATOR.get(), pos, state);
    }
}
