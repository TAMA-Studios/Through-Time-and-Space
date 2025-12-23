/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class FlightSoundThread extends Thread {
	static Map<BlockPos, Level> lockedIn = new HashMap<>();
	private long OTicks = 0;
	public boolean running = false;
	BlockPos blockPos;
	Level level;
	AbstractFlightSound sound;

	public FlightSoundThread(Level level, BlockPos blockPos, AbstractFlightSound sound) {
		this.setName("flight_sound_thread-" + sound.GetSound().getLocation());
		this.level = level;
		this.blockPos = blockPos;
		this.sound = sound;
	}

	@Override
	public void run() {
		this.running = true;
		int ticks = 0;
		while (!this.sound.IsFinished()) {
			if (ticks == 0)
				level.playSound(null, blockPos, this.sound.GetSound(), SoundSource.BLOCKS);
			if (this.level.getGameTime() != OTicks) {
				if (ticks >= this.sound.GetLength()) {
					this.sound.SetFinished(true);
					this.running = false;
					this.stop(); // IDGAF if it's deprecated, if it works, I'm using it
					break;
				}
				ticks++;
				OTicks = (int) this.level.getGameTime();
			}
		}
		super.run();
	}
}
