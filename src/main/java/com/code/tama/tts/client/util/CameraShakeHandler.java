/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.util;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.Random;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class CameraShakeHandler {
    private static int duration = 0;
    private static float intensity = 0;
    private static final Random random = new Random();

    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (duration > 0 && intensity > 0) {
            float shakeYaw = (random.nextFloat() - 0.5f) * intensity;
            float shakePitch = (random.nextFloat() - 0.5f) * intensity;

            event.setYaw(event.getYaw() + shakeYaw);
            event.setPitch(event.getPitch() + shakePitch);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && duration > 0) {
            duration--;
            if (duration <= 0) {
                intensity = 0;
            }
        }
    }

    public static void startShake(float xIntensity, int t) {
        intensity = xIntensity;
        duration = t;
    }

    public static void endShake() {
        intensity = 0;
        duration = 0;
    }
}
