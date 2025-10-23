/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.containers;

import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;

import net.minecraft.core.BlockPos;

import com.code.tama.triggerapi.codec.Codecs;

@Getter
@Setter
public class TIRBlockContainer {
	public static Codec<TIRBlockContainer> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(BlockPos.CODEC.fieldOf("pos").forGetter(TIRBlockContainer::getPos),
					Codecs.UUID_CODEC.fieldOf("tirUUID").forGetter(TIRBlockContainer::getTirUUID))
			.apply(instance, TIRBlockContainer::new));

	BlockPos pos;
	UUID tirUUID;

	TIRBlockContainer(BlockPos pos, UUID uuid) {
		this.pos = pos;
		this.tirUUID = uuid;
	}
}
