/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server;

import com.code.tama.triggerapi.helpers.ThreadUtils;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

public class CommonThreads {
	public static Thread TARDISTickThread(ITARDISLevel cap) {
		return ThreadUtils.NewThread((tardis) -> {
			if (tardis.GetData().getSubSystemsData().DynamorphicController.isActivated(tardis.GetLevel())
					&& !tardis.GetData().getSubSystemsData().DynamorphicGeneratorStacks.isEmpty() && tardis.GetData().isRefueling()
					&& !tardis.GetFlightData().isInFlight()) {
				if (tardis.GetLevel().getGameTime() % 20 == 1)
					tardis.GetData().setFuel(tardis.GetData().getFuel() + 1);
			}

			if (tardis.GetFlightData().isInFlight()) {
				tardis.FlightTick();
			}
		}, cap, "tardis-flight-thread-" + cap.GetLevel().dimension());
	}
}
