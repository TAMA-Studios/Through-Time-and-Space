/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions;

import com.code.tama.triggerapi.dimensions.packets.s2c.SyncDimensionsS2C;
import com.code.tama.triggerapi.universal.UniversalClientOnly;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class SyncDimensionsClientHandler {
	/**
	 * Doesn't work when this code is called from the packet so it gets its own class
	 */
	public static void handleDimSyncPacket(SyncDimensionsS2C mes) {

		if (UniversalClientOnly.client().player == null || UniversalClientOnly.getPacketListener().levels() == null)
			return;

		Set<ResourceKey<Level>> levels = UniversalClientOnly.client().player.connection.levels();
		// If this player knows about this dimension
		if (levels.contains(mes.level())) {
			// If remove
			if (!mes.add()) {
				levels.remove(mes.level());
			}
		}
		// If player does not know about this dim, and we're trying to add it
		else if (mes.add()) {
			levels.add(mes.level());
		}
	}
}
