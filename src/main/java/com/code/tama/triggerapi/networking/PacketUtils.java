/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.networking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class PacketUtils {
	public static void encode(@NotNull FriendlyByteBuf buf, Object... objects) {
		for (Object object : objects) {
			if (object instanceof Integer obj)
				buf.writeInt(obj);

			if (object instanceof Boolean obj)
				buf.writeBoolean(obj);

			if (object instanceof String obj)
				buf.writeUtf(obj);

			if (object instanceof BlockPos obj)
				buf.writeBlockPos(obj);

			if (object instanceof UUID obj)
				buf.writeUUID(obj);

			if (object instanceof CompoundTag obj)
				buf.writeNbt(obj);

			if (object instanceof ResourceLocation obj)
				buf.writeResourceLocation(obj);

			if (object instanceof ResourceKey<?> obj)
				buf.writeResourceKey(obj);
		}
	}

	public static DecodedDataHolder decode(FriendlyByteBuf buf, DATATYPE... datatypes) {
		List<Object> decoded = new ArrayList<>();

		for (DATATYPE datatype : datatypes) {
			if (datatype == DATATYPE.INTEGER)
				decoded.add(buf.readInt());

			if (datatype == DATATYPE.BOOLEAN)
				decoded.add(buf.readBoolean());

			if (datatype == DATATYPE.STRING)
				decoded.add(buf.readUtf());

			if (datatype == DATATYPE.BLOCKPOS)
				decoded.add(buf.readBlockPos());

			if (datatype == DATATYPE.UUID)
				decoded.add(buf.readUUID());

			if (datatype == DATATYPE.NBT)
				decoded.add(buf.readNbt());

			if (datatype == DATATYPE.RESOURCE_LOCATION)
				decoded.add(buf.readResourceLocation());

			// if(datatype == DATATYPE.RESOURCE_KEY)
			// buf.readResourceKey();
		}

		return new DecodedDataHolder(decoded);
	}

	public static Object arrFromHolder(DecodedDataHolder holder)[] {
		return Arrays.stream(holder.objects()).toArray();
	}

	public enum DATATYPE {
		INTEGER("int"), BOOLEAN("bool"), STRING("string"), UUID("uuid"), BLOCKPOS("blockpos"), NBT(
				"nbt"), RESOURCE_LOCATION("resourceLocation"), RESOURCE_KEY("resourceKey"),;
		DATATYPE(String name) {
			this.name = name;
		}
		final String name;
	}

	public record DecodedDataHolder(Object... objects) {
	}
}
