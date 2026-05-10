/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds.effects;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.drawPlanet;

import java.util.Objects;

import com.code.tama.tts.TTSMod;
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
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

public class TardisSkyEffects extends DimensionSpecialEffects {

	private static ShaderInstance skyShader = null;

	public static void registerShaders(net.minecraft.server.packs.resources.ResourceProvider provider, ShaderSink sink)
			throws java.io.IOException {
		sink.register(new ShaderInstance(provider, new ResourceLocation(TTSMod.MODID, "tardis_sky"),
				DefaultVertexFormat.POSITION_TEX), shader -> skyShader = shader);
	}

	@FunctionalInterface
	public interface ShaderSink {
		void register(ShaderInstance shader, java.util.function.Consumer<ShaderInstance> onLoad)
				throws java.io.IOException;
	}

	private static VertexBuffer SunVBO = null;

	private final ResourceKey<DimensionType> targetType;

	public TardisSkyEffects(ResourceKey<DimensionType> targetType) {
		super(Float.NaN, false, SkyType.NONE, false, false);
		this.targetType = targetType;
	}

	private static void renderSpaceSky(PoseStack poseStack, Matrix4f projectionMatrix) {
		if (skyShader == null) {
			TTSMod.LOGGER.warn("[TardisSkyEffects] tardis_sky shader not loaded");
			return;
		}

		Minecraft mc = Minecraft.getInstance();
		assert mc.level != null;

		float time = (mc.level.getGameTime() % 1_000_000L) / 20.0f + mc.getPartialTick() / 20.0f;

		float resX = (float) mc.getWindow().getWidth();
		float resY = (float) mc.getWindow().getHeight();

		// State
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.disableBlend();
		RenderSystem.disableCull();

		// use identity matrices so our NDC quad passes through unchanged
		skyShader.MODEL_VIEW_MATRIX.set(new Matrix4f());
		skyShader.PROJECTION_MATRIX.set(new Matrix4f());

		var uTime = skyShader.getUniform("uTime");
		if (uTime != null)
			uTime.set(time);

		var uRes = skyShader.getUniform("uResolution");
		if (uRes != null)
			uRes.set(resX, resY);

		skyShader.apply();
		RenderSystem.setShader(() -> skyShader);

		// Fullscreen NDC quad, UV 0-1 maps to screen, Z=-1 is far plane
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(-1f, -1f, -1f).uv(0f, 0f).endVertex();
		buffer.vertex(1f, -1f, -1f).uv(1f, 0f).endVertex();
		buffer.vertex(1f, 1f, -1f).uv(1f, 1f).endVertex();
		buffer.vertex(-1f, 1f, -1f).uv(0f, 1f).endVertex();
		BufferUploader.drawWithShader(buffer.end());

		skyShader.clear();

		// Restore
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.enableCull();
		RenderSystem.enableBlend();
	}

	public static void renderSun(@NotNull PoseStack poseStack, Matrix4f projectionMatrix, @NotNull Vec3 position,
			Quaternionf rotation, Vec3 pivotPoint, float size) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/environment/sun.png"));

		poseStack.pushPose();

		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);

		poseStack.translate(position.x, position.y, position.z);
		poseStack.rotateAround(rotation, (float) pivotPoint.x, (float) pivotPoint.y, (float) pivotPoint.z);

		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		if (SunVBO == null || SunVBO.isInvalid()) {
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			SunVBO = new VertexBuffer(VertexBuffer.Usage.STATIC);
			SunVBO.bind();
			SunVBO.upload(drawPlanet(buffer, size));
			VertexBuffer.unbind();
		}

		if (!SunVBO.isInvalid()) {
			SunVBO.bind();
			SunVBO.drawWithShader(poseStack.last().pose(), projectionMatrix,
					Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();
		}

		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();
		poseStack.popPose();
	}

	@Override
	public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 skyColor, float brightness) {
		return skyColor; // pure SPAAAAAAAAAAAAAAAAAAAAAACE
	}

	@Override
	public boolean isFoggyAt(int x, int y) {
		return false;
	}

	@Override
	public boolean renderSky(@NotNull ClientLevel level, int ticks, float partialTick, PoseStack poseStack,
			@NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {

		Minecraft mc = Minecraft.getInstance();
		assert mc.level != null;
		assert mc.player != null;

		Vec3 position = mc.player.position();

		poseStack.pushPose();
		renderSpaceSky(poseStack, projectionMatrix);
		poseStack.popPose();

		poseStack.pushPose();
		renderSun(poseStack, projectionMatrix, new Vec3(20.0 - position.x, 200 - position.y, 20.0 - position.z),
				Axis.YP.rotation(mc.level.getSunAngle(partialTick)), new Vec3(0, 0, 0), 10);
		poseStack.popPose();

		setupFog.run();
		return false;
	}
}