/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.networking.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.gui.CustomGuiScreen;
import com.code.tama.triggerapi.networking.ImAPacket;

/**
 * Server -> Client packet to sync shared context with CustomGuiScreen Sent when
 * GUI opens or after scripts modify context
 */
public class SyncContextPacket implements ImAPacket {
	private final ResourceLocation guiId;
	private final Map<String, Object> contextData;

	public SyncContextPacket(ResourceLocation guiId, Map<String, Object> contextData) {
		this.guiId = guiId;
		this.contextData = contextData;
	}

	public static void encode(SyncContextPacket packet, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(packet.guiId);
		buffer.writeInt(packet.contextData.size());

		for (Map.Entry<String, Object> entry : packet.contextData.entrySet()) {
			buffer.writeUtf(entry.getKey());
			serializeValue(buffer, entry.getValue());
		}
	}

	public static SyncContextPacket decode(FriendlyByteBuf buffer) {
		ResourceLocation guiId = buffer.readResourceLocation();
		int size = buffer.readInt();
		Map<String, Object> contextData = new HashMap<>();

		for (int i = 0; i < size; i++) {
			String key = buffer.readUtf();
			Object value = deserializeValue(buffer);
			contextData.put(key, value);
		}

		return new SyncContextPacket(guiId, contextData);
	}

	private static void serializeValue(FriendlyByteBuf buffer, Object value) {
		if (value == null) {
			buffer.writeByte(0);
		} else if (value instanceof String) {
			buffer.writeByte(1);
			buffer.writeUtf((String) value);
		} else if (value instanceof Integer) {
			buffer.writeByte(2);
			buffer.writeInt((Integer) value);
		} else if (value instanceof Float) {
			buffer.writeByte(3);
			buffer.writeFloat((Float) value);
		} else if (value instanceof Double) {
			buffer.writeByte(4);
			buffer.writeDouble((Double) value);
		} else if (value instanceof Boolean) {
			buffer.writeByte(5);
			buffer.writeBoolean((Boolean) value);
		} else if (value instanceof Long) {
			buffer.writeByte(6);
			buffer.writeLong((Long) value);
		} else {
			// Fallback: convert to string
			buffer.writeByte(1);
			buffer.writeUtf(value.toString());
		}
	}

	private static Object deserializeValue(FriendlyByteBuf buffer) {
		byte type = buffer.readByte();
		return switch (type) {
			case 0 -> null;
			case 1 -> buffer.readUtf();
			case 2 -> buffer.readInt();
			case 3 -> buffer.readFloat();
			case 4 -> buffer.readDouble();
			case 5 -> buffer.readBoolean();
			case 6 -> buffer.readLong();
			default -> null;
		};
	}

	public static void handle(SyncContextPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			if (Minecraft.getInstance().screen instanceof CustomGuiScreen guiScreen) {
				// Only update if it's the matching GUI
				if (guiScreen.guiId.equals(packet.guiId)) {
					guiScreen.updateSharedContext(packet.contextData);
				}
			}
		});
		context.setPacketHandled(true);
	}
}