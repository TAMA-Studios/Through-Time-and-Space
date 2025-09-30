/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import lombok.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@Builder
public class PlayerPosition {
    public final float YRot;
    public final float Xrot;
    public final Vec3 pos;

    @Builder.Default
    public ResourceKey<Level> levelKey = Level.OVERWORLD;

    public BlockPos GetPos() {
        return new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
    }

    @Override
    public String toString() {
        return String.format(
                "%s/%s/%s/%s/%s/%s",
                YRot, Xrot, pos.x, pos.y, pos.z, levelKey.location().toString());
    }

    public static PlayerPosition fromString(String position) {
        String string[] = position.split("/");
        return new PlayerPositionBuilder()
                .YRot(Float.parseFloat(string[0]))
                        .Xrot(Float.parseFloat(string[1]))
                        .pos(new Vec3(
                                Double.parseDouble(string[2]),
                                Double.parseDouble(string[3]),
                                Double.parseDouble(string[4])))
                        .levelKey(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(string[5])))
                        .build();
    }
}
