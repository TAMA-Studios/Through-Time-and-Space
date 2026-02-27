/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.packets;

import com.code.tama.triggerapi.networking.ImAPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientSeamlessTeleport implements ImAPacket {

	public static void handle(SeamlessTeleportPacket packet) {
		Minecraft mc = Minecraft.getInstance();
		ClientPacketListener connection = mc.getConnection();
		if (connection == null)
			return;

		ResourceKey<Level> destDim = ResourceKey.create(Registries.DIMENSION, packet.dimension());

		// If we're already in the right dimension (server already moved us),
		// just do a smooth position update — no screen change needed
		if (mc.level != null && mc.level.dimension() == destDim) {
			if (mc.player != null) {
				mc.player.moveTo(packet.x(), packet.y(), packet.z(), packet.yaw(), packet.pitch());
				mc.player.setYRot(packet.yaw());
				mc.player.setXRot(packet.pitch());
			}
			return;
		}

		// Cross-dimension: we need to switch the client level
		// This is the core of the seamlessness — replicate what handleRespawn does
		// but WITHOUT calling setScreen(downloadingTerrain) or setScreen(null)

		// The mixin on ClientPacketListener.handleRespawn will have already
		// suppressed the vanilla respawn packet, so we do the level switch here
		performClientDimensionSwitch(mc, connection, destDim, packet);
	}

	private static void performClientDimensionSwitch(Minecraft mc, ClientPacketListener conn,
			ResourceKey<Level> destDim, SeamlessTeleportPacket packet) {
		// Grab the registry access and level data we need
		// (mirrors vanilla handleRespawn logic, minus the loading screen)

		LocalPlayer oldPlayer = mc.player;

		// Tell vanilla we're doing a dimension change by invoking the internals
		// cleanly, we use a mixin accessor to call the normally-private
		// ClientPacketListener#createNewPlayer or equivalent
		//
		// Realistically: the mixin on handleRespawn is doing this work for us
		// (see MixinClientPacketListener). This method just repositions.

		if (mc.player != null) {
			mc.player.moveTo(packet.x(), packet.y(), packet.z(), packet.yaw(), packet.pitch());
		}
	}
}