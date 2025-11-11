/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds.effects;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.drawPlanet;

import java.util.Objects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

public class GallifreyEffects extends DimensionSpecialEffects {

	private static VertexBuffer StarsVBO = null;
	private static VertexBuffer SunsVBO = null;

	private final ResourceKey<DimensionType> targetType;

	public GallifreyEffects(ResourceKey<DimensionType> targetType) {
		super(Float.NaN, false, SkyType.NONE, false, false);
		this.targetType = targetType;
	}

	public static void renderSun(@NotNull PoseStack poseStack, Matrix4f matrix4f, @NotNull Vec3 position,
			Quaternionf rotation, Vec3 PivotPoint, float size) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/environment/sun.png"));

		poseStack.pushPose();

		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();
		poseStack.translate(position.x, position.y, position.z);
		poseStack.rotateAround(rotation, (float) PivotPoint.x, (float) PivotPoint.y, (float) PivotPoint.z);

		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		if (SunsVBO == null || SunsVBO.isInvalid()) {
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			SunsVBO = new VertexBuffer(VertexBuffer.Usage.STATIC);
			SunsVBO.bind();
			SunsVBO.upload(drawPlanet(buffer, size));
			VertexBuffer.unbind();
		}

		if (!SunsVBO.isInvalid()) {
			SunsVBO.bind();
			SunsVBO.drawWithShader(poseStack.last().pose(), matrix4f, Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();
		}

		SunsVBO.close();

		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();
		poseStack.popPose();
	}

	@Override
	public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 p_108878_, float p_108879_) {
		return new Vec3(0, 0, 0);
	}

	@Override
	public float getCloudHeight() {
		return 198;
	}

	@Override
	public boolean hasGround() {
		return true;
	}

	@Override
	public boolean isFoggyAt(int p_108874_, int p_108875_) {
		return false;
	}

	@Override
	public boolean renderClouds(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack,
			double camX, double camY, double camZ, @NotNull Matrix4f projectionMatrix) {
		return true;
	}

	@Override
	public boolean renderSky(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack,
			@NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {

		assert Minecraft.getInstance().level != null;
		renderSun(poseStack, projectionMatrix, new Vec3(50, 400, 0),
				Axis.ZP.rotation(Minecraft.getInstance().level.getSunAngle(Minecraft.getInstance().getPartialTick())),
				new Vec3(0, 0, 0), 20);

		renderSun(poseStack, projectionMatrix, new Vec3(0, 450, 175),
				Axis.ZP.rotation(Minecraft.getInstance().level.getSunAngle(Minecraft.getInstance().getPartialTick())),
				new Vec3(0, 0, 0), 20);

		return false;
	}
}
