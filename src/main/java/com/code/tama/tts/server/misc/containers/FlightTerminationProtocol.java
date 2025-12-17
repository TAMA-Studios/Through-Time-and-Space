/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.containers;

import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Random;

@RequiredArgsConstructor
@Getter
public class FlightTerminationProtocol {
	// The probability of the TARDIS landing off course
	public final float Accuracy;
	// How much the floaterior shakes during remat
	public final float LandShakeAmount;
	// Whether the Pilot is able to select the termination protocol
	public final boolean Selectable;
	// Modifier used in calculations for how fast the TARDIS flies
	public final float Speed;
	// How much the exterior shakes during demat
	public final float TakeoffShakeAmount;
	public final String name;


	private BlockPos landPos;


	public BlockPos GetLandPos() {
		return this.landPos;
	}

	/**
	 * Called when the TARDIS flight sequence is terminated (Right before the
	 * exterior tile is placed)
	 *
	 * @param blockPos
	 *            the destination position
	 * @param itardisLevel
	 *            the capability of the TARDIS interior level
	 * @param level
	 *            The level that the Exterior will be placed in
	 */
	public void OnLand(ITARDISLevel itardisLevel, BlockPos blockPos, Level level) {
		CameraShakeHandler.startShake(this.LandShakeAmount, 40);
		if (this.ShouldBeInaccurate())
			this.SetLandPos(level.getBlockRandomPos(blockPos.getX(), blockPos.getY(), blockPos.getZ(),
					(int) ((this.Accuracy - 1) * 10)));
	}

	public void SetLandPos(BlockPos pos) {
		this.landPos = pos;
	}

	public boolean ShouldBeInaccurate() {
		return new Random(System.currentTimeMillis()).nextFloat(1) < this.Accuracy;
	}

	public String TranslatableString() {
		return "tts.flight_termination_protocol." + this.name;
	}
}
