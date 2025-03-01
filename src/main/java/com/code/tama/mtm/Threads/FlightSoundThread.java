package com.code.tama.mtm.Threads;

import com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds.AbstractFlightSound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class FlightSoundThread extends Thread {
    Level level;
    BlockPos blockPos;
    AbstractFlightSound sound;

    public FlightSoundThread(Level level, BlockPos blockPos, AbstractFlightSound sound) {
        this.level = level;
        this.blockPos = blockPos;
        this.sound = sound;
    }

    @Override
    public void run() {
        if(this.level.isClientSide) {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.playSound(this.sound.GetSound());
        }
        int ticks = 0;
        int OldTicks = (int) this.level.getGameTime();
        while(!this.sound.IsFinished()) {
            if(this.level.getGameTime() != OldTicks) {
                if (ticks == this.sound.GetLength())
                    this.sound.SetFinished(true);
                ticks++;
                OldTicks = (int) this.level.getGameTime();
            }
        }
        super.run();
    }
}
