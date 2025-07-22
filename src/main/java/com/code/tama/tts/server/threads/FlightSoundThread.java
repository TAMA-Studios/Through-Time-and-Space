/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class FlightSoundThread extends Thread {
    BlockPos blockPos;
    Level level;
    AbstractFlightSound sound;
    static Map<BlockPos, Level> lockedIn = new HashMap<>();
    private boolean run = true;
    private int id = 0;
    private boolean jumpstarted = false;
    private long OTicks = 0;

    public FlightSoundThread(Level level, BlockPos blockPos, AbstractFlightSound sound) {
        this.setName("Flight Sound Thread");
        this.level = level;
        this.blockPos = blockPos;
        this.sound = sound;
    }

    @Override
    public void run() {
        if(!this.run) {
//            this.stop();
            return;
        }
        if (!this.level.isClientSide) return;
        int ticks = 0;
        while (!this.sound.IsFinished()) {
            if(ticks == 0)
                Minecraft.getInstance().player.playSound(this.sound.GetSound());
            if (this.level.getGameTime() != OTicks) {
                if (ticks == this.sound.GetLength()) this.sound.SetFinished(true);
                ticks++;
                OTicks = (int) this.level.getGameTime();
            }
        }
//        jumpstarted = false;
        super.run();
    }
}