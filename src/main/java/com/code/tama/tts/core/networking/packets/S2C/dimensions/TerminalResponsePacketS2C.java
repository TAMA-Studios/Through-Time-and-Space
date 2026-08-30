/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.S2C.dimensions;

import java.util.function.Supplier;

import com.code.tama.tts.client.gui.terminal.TARDISConsoleScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/**
 * Sent from server -> client to print a line (or block of lines, separated by
 * {@code \n}) into whichever {@link TARDISConsoleScreen} is currently open, as
 * a response to a command the player typed.
 */
public class TerminalResponsePacketS2C {

	private final String message;

	public TerminalResponsePacketS2C(String message) {
		this.message = message;
	}

	public TerminalResponsePacketS2C(FriendlyByteBuf buf) {
		this.message = buf.readUtf(32767);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeUtf(message, 32767);
	}

	public static void handle(TerminalResponsePacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (Minecraft.getInstance().screen instanceof TARDISConsoleScreen console) {
				console.appendOutput(msg.message);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
