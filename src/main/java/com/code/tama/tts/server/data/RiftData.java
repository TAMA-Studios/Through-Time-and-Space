/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data;

import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;

import net.minecraft.core.BlockPos;

import com.code.tama.triggerapi.codec.Codecs;

@Getter
public class RiftData {
	public static Codec<RiftData> CODEC = RecordCodecBuilder
			.create(instance -> instance
					.group(BlockPos.CODEC.fieldOf("pos").forGetter(RiftData::getPos),
							Codec.FLOAT.fieldOf("yRot").forGetter(RiftData::getYRot),
							Codecs.UUID_CODEC.fieldOf("uuid").forGetter(RiftData::getRiftUUID))
					.apply(instance, RiftData::new));

	BlockPos pos;
	UUID riftUUID;
	float yRot;

	public RiftData(BlockPos pos, float yRot, UUID uuid) {
		this.pos = pos;
		this.riftUUID = uuid;
		this.yRot = yRot;
	}
}
