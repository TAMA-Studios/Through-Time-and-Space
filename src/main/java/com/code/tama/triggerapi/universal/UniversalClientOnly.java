package com.code.tama.triggerapi.universal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UniversalClientOnly {
    /**
     * @return The Minecraft Client
     */
    public static Minecraft client() {
        return Minecraft.getInstance();
    }

    /**
     * @return Get Client packet listener
     */
    public static ClientPacketListener getPacketListener() {
        return client().getConnection();
    }
}
