/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds.effects;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.drawPlanet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.data.json.loaders.PlanetLoader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
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

import com.code.tama.triggerapi.helpers.PlanetHelper;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class SpaceSkyEffects extends DimensionSpecialEffects {

	// -------------------------------------------------------------------------
	// Shader
	// -------------------------------------------------------------------------

	private static ShaderInstance skyShader = null;

	public static void registerShaders(net.minecraft.server.packs.resources.ResourceProvider provider, ShaderSink sink)
			throws java.io.IOException {
		sink.register(
				new ShaderInstance(provider, UniversalCommon.modRL("tardis_sky"), DefaultVertexFormat.POSITION_TEX),
				shader -> skyShader = shader);
	}

	@FunctionalInterface
	public interface ShaderSink {
		void register(ShaderInstance shader, java.util.function.Consumer<ShaderInstance> onLoad)
				throws java.io.IOException;
	}

	// -------------------------------------------------------------------------
	// VBOs
	// Sun is a single static mesh — one sun, one size, safe to cache.
	// Planets each have their own size so we key a VBO map by planet id.
	// -------------------------------------------------------------------------

	private static VertexBuffer sunVBO = null;

	/** Keyed by whatever unique id your planet data object exposes. */
	private static final Map<String, VertexBuffer> planetVBOs = new HashMap<>();

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	private final ResourceKey<DimensionType> targetType;

	public SpaceSkyEffects(ResourceKey<DimensionType> targetType) {
		super(Float.NaN, false, SkyType.NONE, false, false);
		this.targetType = targetType;
	}

	// -------------------------------------------------------------------------
	// Space sky (star-field shader)
	//
	// The quad is in NDC space so its z value has nothing to do with world
	// distance — it always lands on the near plane in clip space. Writing
	// gl_FragDepth = 1.0 in the fragment shader is the correct fix: every sky
	// fragment is stamped at the far plane so any real geometry in front wins
	// the depth test automatically. We keep depthMask(true) so that write
	// actually happens, and keep depthTest enabled so subsequent draws are
	// unaffected.
	// -------------------------------------------------------------------------

	private static void renderSpaceSky(Camera camera, float partialTick) {
		if (skyShader == null) {
			TTSMod.LOGGER.warn("[TardisSkyEffects] tardis_sky shader not loaded");
			return;
		}

		Minecraft mc = Minecraft.getInstance();
		assert mc.level != null;

		float time = (mc.level.getGameTime() % 1_000_000L) / 20.0f + partialTick / 20.0f;
		float resX = (float) mc.getWindow().getWidth();
		float resY = (float) mc.getWindow().getHeight();
		float fov = (float) Math.toRadians(mc.gameRenderer.getFov(camera, partialTick, true));
		float aspect = resX / resY;

		Matrix4f invProj = new Matrix4f().perspective(fov, aspect, 0.05f, 1024.0f).invert();

		float yaw = (float) Math.toRadians(camera.getYRot());
		float pitch = (float) Math.toRadians(camera.getXRot());
		Matrix4f invView = new Matrix4f().rotateY((float) Math.PI - yaw).rotateX(-pitch);

		// Depth test ON so the far-plane write interacts correctly with later passes.
		// Depth mask ON so gl_FragDepth = 1.0 in the shader actually gets written.
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
		RenderSystem.disableCull();

		skyShader.apply();
		RenderSystem.setShader(() -> skyShader);

		var invProjUniform = skyShader.getUniform("InvProjMat");
		if (invProjUniform != null)
			invProjUniform.set(invProj);

		var invViewUniform = skyShader.getUniform("InvViewMat");
		if (invViewUniform != null)
			invViewUniform.set(invView);

		var uTime = skyShader.getUniform("uTime");
		if (uTime != null)
			uTime.set(time);

		var uRes = skyShader.getUniform("uResolution");
		if (uRes != null)
			uRes.set(resX, resY);

		BufferBuilder buffer = Tesselator.getInstance().getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(-1f, -1f, -1f).uv(0f, 0f).endVertex();
		buffer.vertex(1f, -1f, -1f).uv(1f, 0f).endVertex();
		buffer.vertex(1f, 1f, -1f).uv(1f, 1f).endVertex();
		buffer.vertex(-1f, 1f, -1f).uv(0f, 1f).endVertex();
		BufferUploader.drawWithShader(buffer.end());

		skyShader.clear();

		RenderSystem.enableCull();
		RenderSystem.enableBlend();
	}

	// -------------------------------------------------------------------------
	// Sun
	// Rendered without depth test so it always appears over the star-field
	// regardless of its world-space position. It does not write depth either —
	// it is a skybox object that world geometry should always occlude.
	// -------------------------------------------------------------------------

	public static void renderSun(@NotNull PoseStack poseStack, Matrix4f projectionMatrix, @NotNull Vec3 position,
			Quaternionf rotation, Vec3 pivotPoint, float size) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/environment/sun.png"));

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.disableBlend();

		poseStack.pushPose();
		poseStack.translate(position.x, position.y, position.z);
		poseStack.rotateAround(rotation, (float) pivotPoint.x, (float) pivotPoint.y, (float) pivotPoint.z);

		if (sunVBO == null || sunVBO.isInvalid()) {
			BufferBuilder buffer = Tesselator.getInstance().getBuilder();
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			sunVBO = new VertexBuffer(VertexBuffer.Usage.STATIC);
			sunVBO.bind();
			sunVBO.upload(drawPlanet(buffer, size));
			VertexBuffer.unbind();
		}

		if (!sunVBO.isInvalid()) {
			sunVBO.bind();
			sunVBO.drawWithShader(poseStack.last().pose(), projectionMatrix,
					Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();
		}

		poseStack.popPose();

		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.enableBlend();
	}

	// -------------------------------------------------------------------------
	// Planet
	// Each planet gets its own VBO keyed by id so different sizes are baked
	// correctly. Same no-depth-test rule as the sun.
	// -------------------------------------------------------------------------

	public static void renderPlanet(@NotNull PoseStack poseStack, Matrix4f projectionMatrix, @NotNull Vec3 position,
			float size, @NotNull String planetId, @NotNull ResourceLocation texture) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.setShaderTexture(0, texture);

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.disableBlend();

		// Build a new projection matrix with a far plane large enough
		// to never frustum-clip planets at any orbital distance
		Minecraft mc = Minecraft.getInstance();
		Camera camera = mc.gameRenderer.getMainCamera();
		float fov = (float) Math.toRadians(mc.gameRenderer.getFov(camera, mc.getFrameTime(), true));
		float aspect = (float) mc.getWindow().getWidth() / mc.getWindow().getHeight();
		Matrix4f farProj = new Matrix4f().perspective(fov, aspect, 0.05f, 10_000_000.0f);

		poseStack.pushPose();
		poseStack.translate(position.x, position.y, position.z);

		VertexBuffer vbo = planetVBOs.get(planetId);
		if (vbo == null || vbo.isInvalid()) {
			BufferBuilder buffer = Tesselator.getInstance().getBuilder();
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
			vbo.bind();
			vbo.upload(drawPlanet(buffer, size));
			VertexBuffer.unbind();
			planetVBOs.put(planetId, vbo);
		}

		if (!vbo.isInvalid()) {
			vbo.bind();
			vbo.drawWithShader(poseStack.last().pose(), farProj, // use our far-plane projection, not Minecraft's
					Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();
		}

		poseStack.popPose();

		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.enableBlend();
	}

	// -------------------------------------------------------------------------
	// DimensionSpecialEffects overrides
	// -------------------------------------------------------------------------

	@Override
	public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 skyColor, float brightness) {
		return skyColor;
	}

	@Override
	public boolean isFoggyAt(int x, int y) {
		return false;
	}

	@Override
	public boolean renderSky(@NotNull ClientLevel level, int ticks, float partialTick, PoseStack poseStack,
			@NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {

		Minecraft mc = Minecraft.getInstance();
		assert mc.level != null;
		assert mc.player != null;

		Vec3 playerPos = mc.player.position();

		// 1. Star-field — writes gl_FragDepth = 1.0 via the shader so all
		// subsequent geometry trivially wins the depth test.
		renderSpaceSky(camera, partialTick);

		// 2. Planets — no depth test, always over the star-field.
		poseStack.pushPose();

		PlanetLoader.list().forEach(p -> {
			Vec3 planetPos = PlanetHelper.getPosition(p, level.getGameTime());
			Vec3 dir = planetPos.subtract(playerPos);
			TTSMod.LOGGER.info("id={} gameTime={} planetPos={} dir={}", p.getId(), level.getGameTime(), planetPos, dir);
			renderPlanet(poseStack, projectionMatrix, dir, p.getSize(), p.getId(),
					UniversalCommon.parse(p.getTexture()));
		});
		poseStack.popPose();

		setupFog.run();
		return false;
	}
}