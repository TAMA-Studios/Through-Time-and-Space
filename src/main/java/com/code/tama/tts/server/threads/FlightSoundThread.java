/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class FlightSoundThread extends Thread {
	static Map<BlockPos, Level> lockedIn = new HashMap<>();
	private long OTicks = 0;
	private int id = 0;
	private boolean jumpstarted = false;
	private boolean run = true;
	BlockPos blockPos;
	Level level;
	AbstractFlightSound sound;

	public FlightSoundThread(Level level, BlockPos blockPos, AbstractFlightSound sound) {
		this.setName("Flight Sound Thread");
		this.level = level;
		this.blockPos = blockPos;
		this.sound = sound;
	}

	@Override
	public void run() {
		if (!this.run) {
			// this.stop();
			return;
		}
		int ticks = 0;
		while (!this.sound.IsFinished()) {
			if (ticks == 0)
				level.playSound(null, blockPos, this.sound.GetSound(), SoundSource.BLOCKS);
			if (this.level.getGameTime() != OTicks) {
				if (ticks >= this.sound.GetLength()) {
					this.sound.SetFinished(true);
					break;
				}
				ticks++;
				OTicks = (int) this.level.getGameTime();
			}
		}
		// jumpstarted = false;
		super.run();
	}
}
