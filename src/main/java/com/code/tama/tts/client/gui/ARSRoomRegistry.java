/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.gui;

import java.util.List;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.ChooseARSRoomC2S;
import com.code.tama.tts.core.registries.tardis.ARSRegistry;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;

public class ARSRoomRegistry {

	/** Info about a placeable room type, shown in the picker screen. */
	public record ARSRoomInfo(String id, String displayName, int color) {
	}

	public static List<ARSRoomInfo> getAvailableRooms() {
		return ARSRegistry.STRUCTURES_INFO;
	}

	public static void placeRoom(ARSPos pos, ARSRoomInfo info) {
		TTSMod.LOGGER.info("[ARS] place room '{}' at {} (block origin {})", info.id, pos, pos.getOrigin());
		Networking.sendToServer(new ChooseARSRoomC2S(pos, info.id, info.color));

		TARDISLevelCapability.GetClientTARDISCapSupplier().ifPresent(p -> {
			p.addARSGrid(new ARSGrid(pos, info.id(), info.color()));
		});
	}
}