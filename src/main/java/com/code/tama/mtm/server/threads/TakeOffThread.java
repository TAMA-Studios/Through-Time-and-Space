package com.code.tama.mtm.server.threads;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.server.misc.PhysicalStateManager;

public class TakeOffThread extends Thread {
    ITARDISLevel itardisLevel;
    public TakeOffThread(ITARDISLevel itardisLevel) {
        this.itardisLevel = itardisLevel;
    }

    @Override
    public void run() {
        this.itardisLevel.SetPlayRotorAnimation(true);
        this.itardisLevel.UpdateClient();
        new PhysicalStateManager(this.itardisLevel, this.itardisLevel.GetExteriorTile()).TakeOff();
        super.run();
        return;
    }
}
