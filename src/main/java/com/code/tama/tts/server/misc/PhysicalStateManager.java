/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.exterior.ExteriorStatePacket;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import org.jetbrains.annotations.NotNull;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PhysicalStateManager {

	private final ExteriorTile exteriorTile;
	private final ITARDISLevel itardisLevel;

	public PhysicalStateManager(@NotNull ITARDISLevel itardisLevel, @NotNull ExteriorTile exteriorTile) {
		this.itardisLevel = itardisLevel;
		this.exteriorTile = exteriorTile;
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

		while (!this.exteriorTile.state.equals(ExteriorStatePacket.State.LAND)) {
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
				// TODO: Signal to the Exterior that it's fully landed (Idk what I was thinking
				// when I wrote this comment but I'll leave it here if I someday remember)
				// ThreadUtils.Pause(!itardisLevel.GetFlightData().getFlightSoundScheme().GetLanding().IsFinished());
				if (itardisLevel.GetFlightData().getFlightSoundScheme().GetLanding().IsFinished()) {

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

		while (!this.exteriorTile.state.equals(ExteriorStatePacket.State.TAKEOFF)) {// itardisLevel.GetFlightData().IsTakingOff())
																					// {
			if (!server) {
				assert exteriorTile.getLevel() != null;
				long tick = exteriorTile.getLevel().getGameTime() - startTick;
				float amp = (float) (initialAmp * Math.exp(-decay * tick));
				float alpha = base + (amp * (float) Math.abs(Math.sin(freq * tick)));
				exteriorTile.setTransparency(alpha);
			}
			if (server) {
				// Wait for the takeoff to be finished
				assert itardisLevel != null;
				// ThreadUtils.Pause(!itardisLevel.GetFlightData().getFlightSoundScheme().GetTakeoff().IsFinished());
				if (itardisLevel.GetFlightData().getFlightSoundScheme().GetTakeoff().IsFinished()) {
					itardisLevel.Fly();
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
		this.itardisLevel.GetLevel().players().forEach(player -> this.itardisLevel.GetFlightData()
				.getFlightSoundScheme().GetLanding().PlayIfFinished(player.level(), player.blockPosition()));

		exteriorTile.state = ExteriorStatePacket.State.LAND;

		Networking.sendPacketToDimension(
				new ExteriorStatePacket(this.itardisLevel.GetNavigationalData().getDestination().GetBlockPos(),
						ExteriorStatePacket.State.LAND, tick),
				this.itardisLevel.GetLevel());
		landAnimation(tick, true);
	}

	public void serverTakeOff() {
		assert itardisLevel != null;
		long tick = itardisLevel.GetLevel().getGameTime();
		exteriorTile.state = ExteriorStatePacket.State.TAKEOFF;
		// send packet to everyone in the dimension
		this.itardisLevel.GetLevel().players().forEach(player -> this.itardisLevel.GetFlightData()
				.getFlightSoundScheme().GetTakeoff().PlayIfFinished(player.level(), player.blockPosition()));
		assert exteriorTile.getLevel() != null;
		Networking.sendPacketToDimension(
				new ExteriorStatePacket(exteriorTile.getBlockPos(), ExteriorStatePacket.State.TAKEOFF, tick),
				exteriorTile.getLevel());
		// run the animation server-side
		takeOffAnimation(tick, true);
	}
}
