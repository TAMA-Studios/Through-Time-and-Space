/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.core.abstractClasses.TickingTile;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.registries.TTSTileEntities;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.block.state.BlockState;

public class HartnellRotorTile extends TickingTile {
    @Getter
    private AnimationState RotorAnimationState = new AnimationState();

    public HartnellRotorTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.EXAMPLE_TILE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) return;
        this.level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            this.RotorAnimationState.animateWhen(cap.IsInFlight(), (int) this.level.getGameTime());
        });
    }
}
