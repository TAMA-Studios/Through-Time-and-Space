/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.config.FlightType;
import com.code.tama.tts.config.TTSConfig;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.entities.controls.ModularControl;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncButtonAnimationSetPacketS2C;
import com.code.tama.tts.server.registries.forge.TTSParticles;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import com.code.tama.triggerapi.helpers.ThreadUtils;

public abstract class AbstractControl {
	private float AnimationState = 0.0f;
	private boolean NeedsUpdate;

	public long animationStartTime = Long.MAX_VALUE;

	public static void Spark(Level level, Vec3 pos) {
		if (!level.isClientSide)
			return;

		for (int i = 0; i < 8; i++) {
			RandomSource random = RandomSource.create(i + Minecraft.getInstance().level.getGameTime());
			double spread = random.nextInt(10) < 5 ? 0.05D : -0.05D;
			double modifier = random.nextInt(10) < 5 ? (random.nextDouble() - 0.2D) : (random.nextDouble() + 0.2D);
			double xSpeed = modifier * spread;
			double ySpeed = (level.random.nextDouble()) * 0.2D;
			double zSpeed = modifier * spread;

			// if (level.random.nextFloat() < 0.3F)
			// level.addParticle(ParticleTypes.ELECTRIC_SPARK, pos.x, pos.y, pos.z, xSpeed,
			// 0, zSpeed);
			// else
			// level.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, xSpeed, ySpeed,
			// zSpeed);

			level.addParticle(TTSParticles.ELECTRIC_SPARK.get(), pos.x, pos.y, pos.z, xSpeed, ySpeed, zSpeed);
		}
	}

	public float GetAnimationState() {
		return this.AnimationState;
	}

	public abstract SoundEvent GetFailSound();

	public abstract SoundEvent GetSuccessSound();

	public boolean NeedsUpdate() {
		return this.NeedsUpdate;
	}

	public abstract InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player);

	public abstract InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player);

	public void RenderFlightEvent(PoseStack stack, MultiBufferSource source, ModularControl control) {
		assert control.consoleTile.getLevel() != null;
		if (control.level() == null)
			return;
		TARDISLevelCapability.GetTARDISCapSupplier(control.level()).ifPresent((cap) -> {
			if (cap.getCurrentFlightEvent().RequiredControls.contains(this.id())) {
				assert Minecraft.getInstance().level != null;
				if (Minecraft.getInstance().level.random.nextInt(100000) <= 5) {
					Spark(Minecraft.getInstance().level, control.position());
				}

				if (!TTSConfig.ClientConfig.FLIGHT_TYPE.get().equals(FlightType.ADVANCED)) {
					PoseStack.Pose pose = stack.last();

					Matrix4f matrix = pose.pose();

					Tesselator tesselator = Tesselator.getInstance();
					BufferBuilder bufferBuilder = tesselator.getBuilder();

					RenderSystem.enableDepthTest();
					RenderSystem.disableCull();

					RenderSystem.setShader(GameRenderer::getPositionColorShader);
					RenderSystem.setShaderColor(1, 0, 0, 0.5f);
					bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

					float r = 1f, g = 0f, b = 0f, a = 0.5f;

					double minX = control.size.minX;
					double minY = control.size.minY;
					double minZ = control.size.minZ;
					double maxX = control.size.maxX;
					double maxY = control.size.maxY;
					double maxZ = control.size.maxZ;

					// FRONT (Z = maxZ)
					bufferBuilder.vertex(matrix, (float) minX, (float) minY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) minY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) maxY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) maxY, (float) maxZ).color(r, g, b, a)
							.endVertex();

					// BACK (Z = minZ)
					bufferBuilder.vertex(matrix, (float) maxX, (float) minY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) minY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) maxY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) maxY, (float) minZ).color(r, g, b, a)
							.endVertex();

					// LEFT (X = minX)
					bufferBuilder.vertex(matrix, (float) minX, (float) minY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) minY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) maxY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) maxY, (float) minZ).color(r, g, b, a)
							.endVertex();

					// RIGHT (X = maxX)
					bufferBuilder.vertex(matrix, (float) maxX, (float) minY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) minY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) maxY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) maxY, (float) maxZ).color(r, g, b, a)
							.endVertex();

					// TOP (Y = maxY)
					bufferBuilder.vertex(matrix, (float) minX, (float) maxY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) maxY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) maxY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) maxY, (float) minZ).color(r, g, b, a)
							.endVertex();

					// BOTTOM (Y = minY)
					bufferBuilder.vertex(matrix, (float) minX, (float) minY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) minY, (float) minZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) maxX, (float) minY, (float) maxZ).color(r, g, b, a)
							.endVertex();
					bufferBuilder.vertex(matrix, (float) minX, (float) minY, (float) maxZ).color(r, g, b, a)
							.endVertex();

					tesselator.end();

					RenderSystem.setShaderColor(1, 1, 1, 1);

					RenderSystem.disableDepthTest();
					RenderSystem.enableCull();
				} else {
				}

			}
		});
	}

	public void SetAnimationState(float state) {
		this.AnimationState = state;
		this.NeedsUpdate = true;
	}

	public void SetNeedsUpdate(boolean Update) {
		this.NeedsUpdate = Update;
	}

	public void UpdateClient(AbstractConsoleTile consoleTile) {
		ThreadUtils.RunThread((tile) -> {
			Networking.sendPacketToDimension(consoleTile.getLevel().dimension(),
					new SyncButtonAnimationSetPacketS2C(consoleTile.ControlAnimationMap, consoleTile.getBlockPos()));
		}, consoleTile, "console_update_client_thread");
		this.NeedsUpdate = false;
	}

	public abstract ResourceLocation id();

	public String getTranslationKey() {
		return id().getNamespace() + ".controls." + id().getPath();
	}

	@Deprecated
	public String name() {
		return id().getPath();
	}

	public void render(PoseStack stack, MultiBufferSource source, int combinedLight, ModularControl control) {
	}
}
