package com.code.tama.mtm.Threads;

import com.code.tama.mtm.Capabilities.Interfaces.ITARDISLevel;
import com.code.tama.mtm.misc.PhysicalStateManager;

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
    }
}
