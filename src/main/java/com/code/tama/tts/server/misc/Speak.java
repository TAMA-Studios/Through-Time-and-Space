package com.code.tama.tts.server.misc;

import com.code.tama.tts.client.TTSSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class Speak {
    // TODO: Record audio for this
    public static void say(String message, Level level, BlockPos pos) {
        System.out.println(message);
        switch (message) {
            case "taking_off": {
                level.playSound(null, null, TTSSounds.THUD.get(), SoundSource.BLOCKS);
                break;
            }
            case "landing": {
                level.playSound(null, null, TTSSounds.THUD.get(), SoundSource.BLOCKS);
                break;
            }
            case "in_flight": {
                level.playSound(null, null, TTSSounds.THUD.get(), SoundSource.BLOCKS);
                break;
            }
            case "limbo": {
                level.playSound(null, null, TTSSounds.THUD.get(), SoundSource.BLOCKS);
                break;
            }
            case "landed": {
                level.playSound(null, null, TTSSounds.THUD.get(), SoundSource.BLOCKS);
                break;
            }
        }
    }
}
