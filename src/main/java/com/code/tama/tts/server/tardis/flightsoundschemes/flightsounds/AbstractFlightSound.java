/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.tts.server.threads.FlightSoundThread;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public abstract class AbstractFlightSound {
    boolean Finished;
    private boolean Started;

    public abstract int GetLength();

    public abstract SoundEvent GetSound();

    public boolean IsFinished() {
        return this.Finished;
    }

    public boolean IsStarted() {
        return this.Started;
    }

    public void SetFinished(boolean IsFinished) {
        this.Finished = IsFinished;
        this.Started = !IsFinished;
    }

    public void Play(Level level, BlockPos blockPos) {
        this.Started = true;
        new FlightSoundThread(level, blockPos, this).start();
    }

    public void PlayIfUnfinished(Level level, BlockPos blockPos) {
        if(this.Started) return;
        this.Started = true;
        new FlightSoundThread(level, blockPos, this).start();
    }
}