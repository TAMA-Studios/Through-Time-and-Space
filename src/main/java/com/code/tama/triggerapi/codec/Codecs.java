/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.codec;

import java.util.List;
import java.util.UUID;

import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.world.phys.Vec3;

public class Codecs {
	public static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);

	public static final Codec<Vec3> VEC3 = Codec.DOUBLE.listOf().comapFlatMap(
			(instance) -> Util.fixedSize(instance, 3).map((map) -> new Vec3(map.get(0), map.get(1), map.get(2))),
			(vec) -> List.of(vec.x(), vec.y(), vec.z()));
}
