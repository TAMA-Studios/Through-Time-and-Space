/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.PhysicalStateManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.code.tama.triggerapi.helpers.ThreadUtils;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerThreads {
	public static Thread TakeoffThread(ITARDISLevel tardis) {
		return ThreadUtils.NewThread((itardisLevel) -> {
			itardisLevel.GetFlightData().setPlayRotorAnimation(true);
			itardisLevel.UpdateClient(DataUpdateValues.FLIGHT);
			itardisLevel.UpdateClient(DataUpdateValues.NAVIGATIONAL);
			new PhysicalStateManager(itardisLevel, itardisLevel.GetExteriorTile()).serverTakeOff();
		}, tardis, "Takeoff Thread");
	}

	public static Thread LandingThread(ITARDISLevel tardis) {
		return ThreadUtils.NewThread((itardisLevel) -> {
			itardisLevel.GetFlightData().setPlayRotorAnimation(false);
			itardisLevel.UpdateClient(DataUpdateValues.FLIGHT);
			itardisLevel.UpdateClient(DataUpdateValues.NAVIGATIONAL);
			new PhysicalStateManager(itardisLevel, itardisLevel.GetExteriorTile()).serverLand();
		}, tardis, "Landing Thread");
	}
}
