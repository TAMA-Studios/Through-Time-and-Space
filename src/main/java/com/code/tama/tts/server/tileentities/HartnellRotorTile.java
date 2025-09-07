/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.registries.TTSTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.block.state.BlockState;

public class HartnellRotorTile extends TickingTile {
    private final AnimationState RotorAnimationState = new AnimationState();

    public AnimationState getRotorAnimationState() {
        return this.RotorAnimationState;
    }

    public HartnellRotorTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.HARTNELL_ROTOR.get(), pos, state);
    }

    @Override
    public void tick() {
        assert level != null;
        level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            if (level.isClientSide)
                this.getRotorAnimationState().animateWhen(cap.ShouldPlayRotorAnimation(), (int) level.getGameTime());
            if (cap.ShouldPlayRotorAnimation()) {
                cap.GetFlightScheme().GetFlightLoop().PlayIfUnfinished(level, this.worldPosition);
            } else cap.GetFlightScheme().GetFlightLoop().SetFinished(true);
        });
    }
}
