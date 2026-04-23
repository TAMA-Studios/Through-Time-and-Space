/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data;

import java.util.UUID;
import java.util.function.Consumer;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.createmod.catnip.annotations.ClientOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.server.ServerLifecycleHooks;

import com.code.tama.triggerapi.UniversalEntityRenderer;
import com.code.tama.triggerapi.codec.Codecs;
import com.code.tama.triggerapi.helpers.rendering.FBOHelper;
import com.code.tama.triggerapi.miscs.TriConsumer;

@Getter
@Setter
@RequiredArgsConstructor
public class RiftData {
	public static Codec<RiftData> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(BlockPos.CODEC.fieldOf("pos").forGetter(RiftData::getPos),
					Codec.FLOAT.fieldOf("yRot").forGetter(RiftData::getYRot),
					Codecs.UUID_CODEC.fieldOf("uuid").forGetter(RiftData::getRiftUUID),
					Codec.INT.fieldOf("usedTime").forGetter(RiftData::getUsedTime),
					Codec.STRING.fieldOf("whatsInside").forGetter(RiftData::getWhatsInside))
			.apply(instance, RiftData::new));

	final BlockPos pos;
	final float yRot;
	final UUID riftUUID;
	final String WhatsInside;
	final Level level;
	int usedTime = 0;

	@ClientOnly
	public FBOHelper fboHelper;

	public WheelOfFortune WhatsInside() {
		return WheelOfFortune.valueOf(this.WhatsInside);
	}
	public void setUsedTime(int usedTime) {
		System.out.println(usedTime);
		this.usedTime = Math.min(32, usedTime);
	}

	public void GetOuttaEre() {
		this.level.getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(cap -> cap.removeRift(this));
	}

	public RiftData(BlockPos pos, float yRot, UUID uuid, int usedTime, String whatsInside) {
		this.pos = pos;
		this.riftUUID = uuid;
		this.yRot = yRot;
		this.usedTime = usedTime;
		this.WhatsInside = whatsInside;
		if (Dist.CLIENT.isClient())
			this.level = Minecraft.getInstance().level;
		else
			this.level = ServerLifecycleHooks.getCurrentServer().overworld();
	}

	public enum WheelOfFortune {
		CREEPER("creeper", (riftData, pose, bugger) -> {
			pose.pushPose();

			UniversalEntityRenderer.RenderEntityStatic(
					UniversalEntityRenderer.getCachedEntity(EntityType.CREEPER, riftData.level), pose, bugger); // Renders
																												// a
																												// cached
																												// creeper
																												// entity
			pose.popPose();
		}, (riftData) -> {
			riftData.level.addFreshEntity(new Creeper(EntityType.CREEPER, riftData.level));
		}), YOUDIE("youdie", (riftData, pose, bugger) -> {
		}, (riftData) -> {
		}), GETRANDOMORE("getrandomore", (riftData, pose, bugger) -> {
			Minecraft.getInstance().getItemRenderer().renderStatic(Items.DIAMOND.getDefaultInstance(),
					ItemDisplayContext.NONE, 0xf000f0, OverlayTexture.NO_OVERLAY, pose, bugger, riftData.level, 1);
		}, (riftData) -> {
			ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, riftData.level);
			itemEntity.setItem(Items.DIAMOND.getDefaultInstance());
			riftData.level.addFreshEntity(itemEntity);
		});
		WheelOfFortune(String name, TriConsumer<RiftData, PoseStack, MultiBufferSource> render,
				Consumer<RiftData> onOpen) {
			this.name = name;
			this.render = render;
			this.onOpen = onOpen;
		}

		public final String name;
		public final TriConsumer<RiftData, PoseStack, MultiBufferSource> render;
		public final Consumer<RiftData> onOpen;
	}
}
