/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.S2C.FlightLoopSoundPacket;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;
import com.code.tama.tts.server.threads.FlightSoundThread;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PhysicalStateManager {

	/**
	 * Fixed reference point inside the TARDIS interior, used purely as the
	 * lookup key for the flight sound system (see {@link FlightSoundThread}).
	 */
	private static final BlockPos AMBIENT_SOUND_POS = BlockPos.ZERO;

	/** How long to sleep between polls while waiting on a stage's sound to finish. */
	private static final long POLL_INTERVAL_MS = 50L;

	/**
	 * Safety net: if a stage's sound never reports finished (e.g. a broken/absent
	 * sound scheme), give up waiting after this many polls instead of hanging the
	 * Takeoff/Landing thread forever. ~2 minutes at 50ms/poll.
	 */
	private static final int MAX_POLLS = 20 * 60 * 2;

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

	/* ==================== CLIENT ANIMATION (purely visual - unchanged) ==================== */

	public void clientLand(long startTick) {
		landFadeAnimation(startTick);
	}

	public void clientTakeOff(long startTick) {
		takeOffFadeAnimation(startTick);
	}

	private void landFadeAnimation(long startTick) {
		float base = 1.0f;
		float initialAmp = 1.0f;
		float decay = 0.05f;
		float freq = 0.3f;

		while (this.exteriorTile.state.equals(ExteriorState.LANDING)) {
			assert this.exteriorTile.getLevel() != null;
			long tick = this.exteriorTile.getLevel().getGameTime() - startTick;
			float amp = (float) (initialAmp * Math.exp(-decay * tick));
			float alpha = base - (amp * (float) Math.abs(Math.sin(freq * tick)));
			exteriorTile.setTransparency(alpha);
		}
	}

	private void takeOffFadeAnimation(long startTick) {
		float base = 0.0f;
		float initialAmp = 1.0f;
		float decay = 0.05f;
		float freq = 0.3f;

		while (this.exteriorTile.state.equals(ExteriorState.TAKINGOFF)) {
			assert exteriorTile.getLevel() != null;
			long tick = exteriorTile.getLevel().getGameTime() - startTick;
			float amp = (float) (initialAmp * Math.exp(-decay * tick));
			float alpha = base + (amp * (float) Math.abs(Math.sin(freq * tick)));
			exteriorTile.setTransparency(alpha);
		}
	}

	/* ==================== SERVER STATE MACHINE ==================== */

	/**
	 * Drives the full Taking Off stage: plays the takeoff sound, waits for it to
	 * actually finish (instead of spinning forever - see below), then destroys
	 * the exterior and calls {@link ITARDISLevel#Fly()} to enter In Flight,
	 * immediately followed by starting the looping flight-loop sound.
	 */
	public void serverTakeOff() {
		assert itardisLevel != null;
		assert exteriorTile != null;

		ServerLevel interior = (ServerLevel) itardisLevel.GetLevel();

		// Make sure nothing stale is occupying the ambient sound slot
		FlightSoundThread.stop(interior, AMBIENT_SOUND_POS);

		itardisLevel.UpdateExteriorState(ExteriorState.TAKINGOFF);

		AbstractFlightSound takeoffSound = itardisLevel.GetFlightData().getFlightSoundScheme().GetTakeoff();
		takeoffSound.SetFinished(false);
		new FlightSoundThread(interior, AMBIENT_SOUND_POS, takeoffSound).start();

		if (!waitUntilFinished(takeoffSound)) {
			System.err.println(
					"[TTS] Takeoff sound for TARDIS at " + AMBIENT_SOUND_POS + " never reported finished - forcing takeoff to continue anyway.");
		}

		// Takeoff sound is done - remove the exterior and actually go flying
		itardisLevel.UpdateExteriorState(ExteriorState.SHOULDNTEXIST);
		itardisLevel.Fly();

		// Flight Loop plays from here through In Flight and Vortex Limbo, until serverLand() stops it
		Networking.sendPacketToDimension(new FlightLoopSoundPacket(true), interior);
	}

	/**
	 * Drives the full Landing stage: stops the flight loop, places the exterior
	 * at the destination, plays the landing sound, waits for it to actually
	 * finish, then marks the TARDIS fully Landed.
	 */
	public void serverLand() {
		assert itardisLevel != null;

		ServerLevel interior = (ServerLevel) itardisLevel.GetLevel();

		// Flight loop stops the instant landing begins.
		Networking.sendPacketToDimension(new FlightLoopSoundPacket(false), interior);
		FlightSoundThread.stop(interior, AMBIENT_SOUND_POS);

		// Physically place the exterior at the destination
		itardisLevel.Land();
		itardisLevel.GetFlightData().setPlayRotorAnimation(true);

		this.exteriorTile = itardisLevel.GetExteriorTile();

		itardisLevel.UpdateExteriorState(ExteriorState.LANDING);
		itardisLevel.UpdateClient(DataUpdateValues.ALL);

		AbstractFlightSound landingSound = itardisLevel.GetFlightData().getFlightSoundScheme().GetLanding();
		landingSound.SetFinished(false);
		new FlightSoundThread(interior, AMBIENT_SOUND_POS, landingSound).start();

		itardisLevel.GetFlightData().setPlayRotorAnimation(false);

		if (!waitUntilFinished(landingSound)) {
			System.err.println(
					"[TTS] Landing sound for TARDIS at " + AMBIENT_SOUND_POS + " never reported finished - forcing landing to finish anyway.");
		}

		itardisLevel.UpdateExteriorState(ExteriorState.LANDED);
	}

	/**
	 * Polls (with a real sleep - not a busy-spin) until the given sound reports
	 * finished. This is what the old version was missing: previously nothing ever
	 * called {@code SetFinished(true)} on the takeoff/landing sound, so the
	 * equivalent wait loop spun at 100% CPU forever and the TARDIS could never
	 * leave Taking Off / finish Landing.
	 *
	 * @return false if it gave up due to the safety timeout instead of the sound
	 *         actually finishing.
	 */
	private boolean waitUntilFinished(AbstractFlightSound sound) {
		int polls = 0;
		while (!sound.IsFinished()) {
			if (++polls > MAX_POLLS)
				return false;
			try {
				Thread.sleep(POLL_INTERVAL_MS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return false;
			}
		}
		return true;
	}
}