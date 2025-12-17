/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.exterior.ExteriorStatePacket;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class PhysicalStateManager {

	private ExteriorTile exteriorTile;
	private final ITARDISLevel itardisLevel;

	public PhysicalStateManager(@NotNull ITARDISLevel itardisLevel, @NotNull ExteriorTile exteriorTile) {
		this.itardisLevel = itardisLevel;
		this.exteriorTile = exteriorTile;
	}

	public PhysicalStateManager(@NotNull ITARDISLevel itardisLevel) {
		this.itardisLevel = itardisLevel;
		this.exteriorTile = null;
	}

	/**
	 * ONLY CALL THIS CONSTRUCTOR CLIENT SIDE!!
	 **/
	@OnlyIn(Dist.CLIENT)
	public PhysicalStateManager(@NotNull ExteriorTile exteriorTile) {
		this.itardisLevel = null;
		this.exteriorTile = exteriorTile;
	}

	/* ==================== SERVER METHODS ==================== */

	private void landAnimation(long startTick, boolean server) {
		float base = 1.0f;
		float initialAmp = 1.0f;
		float decay = 0.05f;
		float freq = 0.3f;

		while (!this.exteriorTile.state.equals(ExteriorState.LANDED)) {
			if (!server) {
				assert this.exteriorTile.getLevel() != null;
				long tick = this.exteriorTile.getLevel().getGameTime() - startTick;
				float amp = (float) (initialAmp * Math.exp(-decay * tick));
				float alpha = base - (amp * (float) Math.abs(Math.sin(freq * tick)));
				exteriorTile.setTransparency(alpha);
			}
			if (server) {
				// Wait for the takeoff to be finished
				assert itardisLevel != null;
				if (itardisLevel.GetFlightData().getFlightSoundScheme().GetLanding().IsFinished()) {
					exteriorTile.state = ExteriorState.LANDED;
					break;
				}
			}
		}
	}

	private void takeOffAnimation(long startTick, boolean server) {
		float base = 0.0f;
		float initialAmp = 1.0f;
		float decay = 0.05f;
		float freq = 0.3f;
		if(this.exteriorTile == null) itardisLevel.Fly();

		while (!this.exteriorTile.state.equals(ExteriorState.TAKEOFF)) {// itardisLevel.GetFlightData().IsTakingOff())
																		// {
			if (!server) { // If it's client side, actually do the animation
				assert exteriorTile.getLevel() != null;
				long tick = exteriorTile.getLevel().getGameTime() - startTick;
				float amp = (float) (initialAmp * Math.exp(-decay * tick));
				float alpha = base + (amp * (float) Math.abs(Math.sin(freq * tick)));
				exteriorTile.setTransparency(alpha);
			}
			if (server) { // If it is server side, just wait for the animation to be done
				assert itardisLevel != null;
				if (itardisLevel.GetFlightData().getFlightSoundScheme().GetTakeoff().IsFinished()) {
					itardisLevel.Fly();
					exteriorTile.state = ExteriorState.TAKEOFF;
					break;
				}
			}
		}
	}

	/* ==================== CLIENT ENTRY POINTS ==================== */

	public void clientLand(long startTick) {
		landAnimation(startTick, false);
	}

	public void clientTakeOff(long startTick) {
		takeOffAnimation(startTick, false);
	}

	/* ==================== ANIMATION CORE ==================== */

	public void serverLand() {
		assert itardisLevel != null;
		itardisLevel.Land();
		long tick = this.itardisLevel.GetLevel().getGameTime();
		// Play takeoff sound to everyone in dimension
		this.itardisLevel.GetLevel().players().forEach(player -> this.itardisLevel.GetFlightData()
				.getFlightSoundScheme().GetLanding().PlayIfFinished(player.level(), player.blockPosition()));

		Networking.sendPacketToDimension(
				new ExteriorStatePacket(this.itardisLevel.GetNavigationalData().getDestination().GetBlockPos(),
						ExteriorState.LANDED, tick),
				this.itardisLevel.GetLevel());

		this.exteriorTile = itardisLevel.GetExteriorTile();
		// run the animation server-side
		landAnimation(tick, true);
	}

	public void serverTakeOff() {
		assert itardisLevel != null;
		long tick = itardisLevel.GetLevel().getGameTime();
		// Play takeoff sound to everyone in dimension
		this.itardisLevel.GetLevel().players().forEach(player -> this.itardisLevel.GetFlightData()
				.getFlightSoundScheme().GetTakeoff().PlayIfFinished(player.level(), player.blockPosition()));
		assert exteriorTile.getLevel() != null;
		// send packet to everyone in the dimension
		// run the animation server-side
		takeOffAnimation(tick, true);
	}
}