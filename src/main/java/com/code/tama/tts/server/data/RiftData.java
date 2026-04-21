/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data;

import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.core.BlockPos;

import com.code.tama.triggerapi.codec.Codecs;

@Getter @Setter
@RequiredArgsConstructor
public class RiftData {
	public static Codec<RiftData> CODEC = RecordCodecBuilder
			.create(instance -> instance
					.group(BlockPos.CODEC.fieldOf("pos").forGetter(RiftData::getPos),
							Codec.FLOAT.fieldOf("yRot").forGetter(RiftData::getYRot),
							Codecs.UUID_CODEC.fieldOf("uuid").forGetter(RiftData::getRiftUUID),
							Codec.INT.fieldOf("usedTime").forGetter(RiftData::getUsedTime))
					.apply(instance, RiftData::new));

	final BlockPos pos;
	final float yRot;
	final UUID riftUUID;
	int usedTime = 0;

	public void setUsedTime(int usedTime) {
		this.usedTime = Math.min(256, usedTime);
	}

	public RiftData(BlockPos pos, float yRot, UUID uuid, int usedTime) {
		this.pos = pos;
		this.riftUUID = uuid;
		this.yRot = yRot;
		this.usedTime = usedTime;
	}
}
