/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.packets;

import com.code.tama.triggerapi.dimensions.packets.s2c.SyncDimensionsS2C;
import com.code.tama.triggerapi.dimensions.packets.s2c.UpdateDimensionsS2C;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class DimensionPacketsRegistration {
	public static void registerPackets() {
		UniversalCommon.Networking.registerMsg(SyncDimensionsS2C.class);

		UniversalCommon.Networking.registerMsg(UpdateDimensionsS2C.class);
	}
}
