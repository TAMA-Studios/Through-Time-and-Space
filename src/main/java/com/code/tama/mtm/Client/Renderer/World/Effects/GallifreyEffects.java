package com.code.tama.mtm.Client.Renderer.World.Effects;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.IForgeDimensionSpecialEffects;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class GallifreyEffects extends DimensionSpecialEffects implements IForgeDimensionSpecialEffects {

    public GallifreyEffects() {
        super(Float.NaN, false, SkyType.NONE, false, false);
    }

    @Override
    public float getCloudHeight() {
        return -10;
    }

    @Override
    public boolean hasGround() {
        return false;
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 p_108878_, float p_108879_) {
        return new Vec3(0, 0, 0);
    }

    @Override
    public boolean isFoggyAt(int p_108874_, int p_108875_) {
        return false;
    }

    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix) {
        return true;
    }

    @Override
    public boolean renderSky(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack, @NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {
        return true;
    }
}
