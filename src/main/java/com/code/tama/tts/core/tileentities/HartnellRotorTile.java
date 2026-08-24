/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import lombok.Getter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import com.code.tama.triggerapi.tileEntities.TickingTile;

@Getter
public class HartnellRotorTile extends TickingTile {
	private final AnimationState RotorAnimationState = new AnimationState();
	public int AnimationTicks = 0;

	public HartnellRotorTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void tick() {
		assert level != null;
		level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
			if (cap.GetFlightData().isPlayRotorAnimation()) {
				cap.GetFlightData().getFlightSoundScheme().GetFlightLoop().PlayLooped(level, this.worldPosition);
			} else
				cap.GetFlightData().getFlightSoundScheme().GetFlightLoop().Stop();
		});
	}

	@Override
	public void clientTick() {
		this.AnimationTicks++;
		TARDISLevelCapability.GetClientTARDISCapSupplier().ifPresent(cap -> {
			this.getRotorAnimationState().animateWhen(
					cap.GetFlightData().isPlayRotorAnimation() || this.getBlockState().getValue(POWERED),
					this.AnimationTicks);
		});
	}
}
