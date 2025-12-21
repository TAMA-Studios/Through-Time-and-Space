/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.PhysicalStateManager;

import com.code.tama.triggerapi.helpers.ThreadUtils;

public class ServerThreads {
	public static Thread TakeoffThread(ITARDISLevel tardis) {
		return ThreadUtils.NewThread((itardisLevel) -> {
			itardisLevel.UpdateClient(DataUpdateValues.ALL);
			new PhysicalStateManager(itardisLevel, itardisLevel.GetExteriorTile()).serverTakeOff();
		}, tardis, "Takeoff Thread");
	}

	public static Thread LandingThread(ITARDISLevel tardis) {
		return ThreadUtils.NewThread((itardisLevel) -> {
			new PhysicalStateManager(itardisLevel).serverLand();
		}, tardis, "Landing Thread");
	}
}
