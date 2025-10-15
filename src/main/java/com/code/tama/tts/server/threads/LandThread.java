/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.containers.PhysicalStateManager;

public class LandThread extends Thread {
    ITARDISLevel itardisLevel;

    public LandThread(ITARDISLevel itardisLevel) {
        this.setName("Landing Thread");
        this.itardisLevel = itardisLevel;
    }

    @Override
    public void run() {
        this.itardisLevel.GetFlightData().setPlayRotorAnimation(false);
        this.itardisLevel.UpdateClient(DataUpdateValues.FLIGHT);
        this.itardisLevel.UpdateClient(DataUpdateValues.NAVIGATIONAL);
        new PhysicalStateManager(this.itardisLevel, this.itardisLevel.GetExteriorTile()).serverLand();
        super.run();
    }
}
