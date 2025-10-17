/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.code.tama.triggerapi.dimensions.packets.s2c.SyncDimensionsS2C;

@OnlyIn(Dist.CLIENT)
public class SyncDimensionsClientHandler {
	/**
	 * Doesn't work when this code is called from the packet so it gets its own
	 * method
	 */
	public static void handleDimSyncPacket(SyncDimensionsS2C mes) {

		if (Minecraft.getInstance().player == null || Minecraft.getInstance().player.connection.levels() == null)
			return;

		Set<ResourceKey<Level>> levels = Minecraft.getInstance().player.connection.levels();
		// If this player knows about this dimension
		if (levels.contains(mes.level)) {
			// If remove
			if (!mes.add) {
				levels.remove(mes.level);
			}
		}
		// If player does not know about this dim, and we're trying to add it
		else if (mes.add) {
			levels.add(mes.level);
		}
	}
}
