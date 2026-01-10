/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds.helper;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetClientTARDISCapSupplier;

import java.awt.*;
import java.util.ArrayList;

import com.code.tama.tts.client.renderers.DevOverlayRenderer;
import com.code.tama.tts.client.renderers.SonicOverlayRenderer;
import com.code.tama.tts.client.renderers.worlds.GallifreySkyRenderer;
import com.code.tama.tts.client.renderers.worlds.SkyBlock;
import com.code.tama.tts.client.renderers.worlds.TardisSkyRenderer;
import com.code.tama.tts.mixin.client.ILevelRendererAccessor;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.Tags;
import com.code.tama.tts.server.items.TwineItem;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4i;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.code.tama.triggerapi.helpers.world.RayTraceUtils;

public class CustomLevelRenderer {
	private static final Vec3 PLANET_POSITION = new Vec3(0, 100, 0); // Position of the cube planet in world coordinates
	static boolean InittedSkyboxThread;
	static long Ticks;
	public static ArrayList<AbstractLevelRenderer> Renderers = new ArrayList<>();

	public static void AddRenderer(AbstractLevelRenderer renderer) {
		CustomLevelRenderer.Renderers.add(renderer);
	}

	/** Adds all the renderers to the renderer array */
	public static void Register() {
		AddRenderer(new GallifreySkyRenderer());
		AddRenderer(new TardisSkyRenderer());
	}

	public static void applyFogEffect(float ambientLight) {
		float fogDensity = Math.max(0.1f, 1.0f - ambientLight); // Inverse relationship with ambient light
		// Random RGB values for disco mode
		assert Minecraft.getInstance().level != null;
		RenderSystem.setShaderFogColor(fogDensity, fogDensity, fogDensity);
	}

	public static void applyLighting(float ambientLight, boolean Disco) {
		ambientLight = Math.max(0.0f, Math.min(ambientLight, 1.5f));

		// Apply a non-linear scaling to preserve light sources
		float adjustedLight = (float) Math.pow(ambientLight, 1.05f);

		float rgb[] = getCyclingRGB(
				Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getPartialTick(), 0.01f);

		// RenderSystem.setShaderFogColor(r, g, b);
		if (Disco)
			RenderSystem.setShaderColor(rgb[0] + 0.5f, rgb[1] + 0.5f, rgb[2] + 0.5f, 1.0f);
		else
			RenderSystem.setShaderColor(adjustedLight, adjustedLight, adjustedLight, 1.0f);
	}

	public static BufferBuilder.RenderedBuffer drawPlanet(BufferBuilder buffer, float size) {
		Matrix4f matrix = new Matrix4f(); // poseStack.last().pose();
		float BaseSize = 20.0F;
		buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).uv(1, 0).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).uv(1, 1).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize).uv(0, 1).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).uv(0, 0).endVertex();

		// Top
		buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size).uv(0, 0).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize).uv(0, 1).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).uv(1, 1).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size).uv(1, 0).endVertex();

		// East
		buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).uv(0, 0).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size).uv(0, 1).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).uv(1, 1).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).uv(1, 0).endVertex();

		// West
		buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).uv(0, 0).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize).uv(0, 1).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size).uv(1, 1).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size).uv(1, 0).endVertex();

		// SOUTH
		buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size).uv(0, 0).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size).uv(0, 1).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size).uv(1, 1).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).uv(1, 0).endVertex();

		// Down
		buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).uv(1, 0).endVertex();
		buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).uv(1, 1).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).uv(0, 1).endVertex();
		buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size).uv(0, 0).endVertex();

		return buffer.end();
	}

	// Returns an array: [r, g, b], each in 0..1
	public static float[] getCyclingRGB(float time, float speed) {
		float hue = (time * speed) % 1.0f; // cycles from 0 to 1
		int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
		float r = ((rgb >> 16) & 0xFF) / 255.0f;
		float g = ((rgb >> 8) & 0xFF) / 255.0f;
		float b = (rgb & 0xFF) / 255.0f;
		return new float[]{r, g, b};
	}

	@SubscribeEvent
	public static void onRenderGUI(RenderGuiEvent event) {
		// ModList.get().getModFileById("aseoha").versionString()
		SonicOverlayRenderer.Render(event.getGuiGraphics().pose(), event.getGuiGraphics().bufferSource());
		DevOverlayRenderer.Render(event.getGuiGraphics().pose(), event.getGuiGraphics().bufferSource());
	}

	private static final ResourceLocation CUSTOM_CROSSHAIR = new ResourceLocation(MODID,
			"textures/gui/sonic_crosshair.png");

	@SubscribeEvent
	public static void onRenderCrosshair(RenderGuiOverlayEvent.Pre event) {
		if (event.getOverlay().id().equals(VanillaGuiOverlay.CROSSHAIR.id())) {
			Player player = Minecraft.getInstance().player;

			if (player == null)
				return;
			ItemStack stack = player.getMainHandItem();

			if (!(stack.getItem() instanceof SonicItem))
				return;

			BlockHitResult result = RayTraceUtils.getLookingAtBlock(25);
			assert Minecraft.getInstance().level != null;
			if (result != null) {
				Minecraft.getInstance().level.getBlockState(result.getBlockPos());
				BlockState state = Minecraft.getInstance().level.getBlockState(result.getBlockPos());
				if (state.getTags().toList().contains(Tags.Blocks.SONICABLE)) {
					event.setCanceled(true);

					renderCustomCrosshair(event.getGuiGraphics(), event.getPartialTick());
					return;
				}
			}

			EntityHitResult entityHitResult = RayTraceUtils.getPlayerPOVHitResult(Minecraft.getInstance().player);
			if (entityHitResult != null) {
				Entity entity = entityHitResult.getEntity();
				if (entity.getType().getTags().toList().contains(Tags.Entities.SONICABLE)) {
					event.setCanceled(true);

					renderCustomCrosshair(event.getGuiGraphics(), event.getPartialTick());
				}
			}
		}
	}

	private static void renderCustomCrosshair(GuiGraphics guiGraphics, float partialTick) {
		int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

		int x = width / 2 - 8;
		int y = height / 2 - 8;

		guiGraphics.blit(CUSTOM_CROSSHAIR, x, y, 0, 0, 16, 16, 16, 16);
	}

	// This method will handle the rendering event
	@SubscribeEvent
	public static void onRenderLevel(RenderLevelStageEvent event) {
		renderTwine(event);

		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS)
			SkyBlock.renderSky(
					new SkyBlock.RenderData(event.getPoseStack(), event.getPartialTick(), event.getProjectionMatrix()));

		Ticks = Minecraft.getInstance().level.getGameTime();

		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
			// Get each renderer
			CustomLevelRenderer.Renderers.forEach(renderer -> {
				// Run the Render code in each renderer
				renderer.Render(event.getCamera(), event.getProjectionMatrix(), event.getPoseStack(),
						event.getFrustum(), event.getPartialTick());
			});
		}

		// Calculate the light level from the cap if it exists
		float ambientLight = GetClientTARDISCapSupplier().map(ITARDISLevel::GetLightLevel).orElse(1.0f);

		boolean Disco = GetClientTARDISCapSupplier().map(level -> level.GetData().isIsDiscoMode()).orElse(false); // Default
																													// value

		// Apply the calculated lighting
		CustomLevelRenderer.applyLighting(ambientLight, Disco);
		CustomLevelRenderer.applyFogEffect(ambientLight);
	}

	public static void renderTwine(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES)
			return;

		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if (player == null)
			return;

		// Check if player is holding twine
		for (ItemStack stack : player.getHandSlots()) {
			if (stack.getItem() instanceof TwineItem) {
				CompoundTag tag = stack.getTag();
				if (tag != null && tag.contains("BoundEntityId")) {
					int entityId = tag.getInt("BoundEntityId");
					Entity boundEntity = player.level().getEntity(entityId);

					if (boundEntity != null) {
						renderTwineString(event.getPoseStack(),
								((ILevelRendererAccessor) event.getLevelRenderer()).getRenderBuffers().bufferSource(),
								player, boundEntity, event.getPartialTick());
					}
				}
			}
		}
	}

	private static void renderTwineString(PoseStack poseStack, MultiBufferSource buffer, Player player, Entity target,
			float partialTick) {
		poseStack.pushPose();

		Vec3 playerPos = player.getEyePosition(partialTick);
		Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2, 0);
		Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

		poseStack.translate(playerPos.x - camera.x, playerPos.y - camera.y, playerPos.z - camera.z);

		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.leash());
		Matrix4f matrix = poseStack.last().pose();

		// Calculate the direction and render the string
		float dx = (float) (targetPos.x - playerPos.x);
		float dy = (float) (targetPos.y - playerPos.y);
		float dz = (float) (targetPos.z - playerPos.z);

		// Render string segments (similar to lead rendering)
		int segments = 24;
		for (int i = 0; i < segments; i++) {
			float t = i / (float) segments;
			float nextT = (i + 1) / (float) segments;

			float x1 = dx * t;
			float y1 = dy * t - (float) (Math.sin(t * Math.PI) * 0.2);
			float z1 = dz * t;

			float x2 = dx * nextT;
			float y2 = dy * nextT - (float) (Math.sin(nextT * Math.PI) * 0.2);
			float z2 = dz * nextT;

			vertexConsumer.vertex(matrix, x1, y1, z1).color(139, 90, 43, 255).endVertex();
			vertexConsumer.vertex(matrix, x2, y2, z2).color(139, 90, 43, 255).endVertex();
		}

		poseStack.popPose();
	}

	public static void renderImageSky(PoseStack poseStack, ResourceLocation resourceLocation, Vector4i Colors) {
		if (true)
			return; // Disabled but just doing `return` throws unreachable

		poseStack.pushPose();

		// Disable depth testing and culling for skybox
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		RenderSystem.disableCull(); // Disable back face culling to ensure inner faces are visible

		// --- Render Panorama (Inside Cube) ---
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, resourceLocation);

		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		if (bufferBuilder.building())
			return;
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		float panoramaSize = 200.0f;

		poseStack.translate(-panoramaSize + panoramaSize, 0, 0);

		// Reverse vertex order (clockwise) to face inward
		// Front face (North)
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, -panoramaSize).uv(0.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, -panoramaSize).uv(1.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, -panoramaSize).uv(1.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, -panoramaSize).uv(0.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

		// Back face (South)
		poseStack.mulPose(Axis.ZP.rotationDegrees(180)); // Flip the face upside down
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, panoramaSize).uv(1.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, panoramaSize).uv(1.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, panoramaSize).uv(0.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, panoramaSize).uv(0.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		poseStack.mulPose(Axis.ZN.rotationDegrees(180)); // Restore the matrix so all the other faces aren't affected

		// Left face
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, panoramaSize).uv(0.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, panoramaSize).uv(0.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, -panoramaSize).uv(1.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, -panoramaSize).uv(1.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

		// Right face
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, -panoramaSize).uv(0.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, -panoramaSize).uv(0.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, panoramaSize).uv(1.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, panoramaSize).uv(1.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

		// Top face
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, -panoramaSize).uv(0.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, panoramaSize).uv(0.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, panoramaSize).uv(1.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, -panoramaSize).uv(1.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

		// Bottom face
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, panoramaSize).uv(0.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, -panoramaSize).uv(0.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, -panoramaSize).uv(1.0f, 1.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
		bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, panoramaSize).uv(1.0f, 0.0f)
				.color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

		BufferUploader.drawWithShader(bufferBuilder.end());

		// Restore render state

		RenderSystem.enableCull();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		poseStack.popPose();
	}

	public static void renderPlanet(@NotNull PoseStack poseStack, @NotNull Vec3 position, Quaternionf rotation,
			Vec3 PivotPoint, float size, String name) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/environment/" + name + ".png"));

		poseStack.pushPose();
		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();
		poseStack.translate(position.x, position.y, position.z);
		poseStack.rotateAround(rotation, (float) PivotPoint.x, (float) PivotPoint.y, (float) PivotPoint.z);
		poseStack.scale(30.0F, 30.0F, 30.0F);

		BufferBuilder buffer = Tesselator.getInstance().getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		BufferUploader.drawWithShader(drawPlanet(buffer, size));

		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();
		poseStack.popPose();
	}
}
