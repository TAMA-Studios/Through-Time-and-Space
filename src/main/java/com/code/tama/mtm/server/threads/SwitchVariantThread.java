package com.code.tama.mtm.server.threads;

import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.server.misc.ExteriorVariant;

public class SwitchVariantThread extends Thread {
    ITARDISLevel Cap;
    public SwitchVariantThread(ITARDISLevel cap) {
        this.Cap = cap;
    }

    @Override
    public void run() {
        super.run();
//        while (true) {
//            if(!Cap.GetExteriorVariant().GetModelName().equals(ExteriorModelsHandler.ModelList.get(Cap.GetExteriorModel().ID))) {
//                Cap.SetExteriorVariant(ExteriorVariants.Cycle(Cap.GetExteriorVariant()));
//            }
//            else {
//                Cap.UpdateClient();
//                break;
//            }
//        }
        if(Cap.GetExteriorModel().getExteriorVariants().contains(Cap.GetExteriorVariant().GetTexture())) {
            Cap.SetExteriorVariant(ExteriorVariants.Cycle(Cap.GetExteriorVariant()));
        }

        else for(ExteriorVariant variant : ExteriorVariants.Variants) {
            if(variant.GetModelName().equals(Cap.GetExteriorModel().getModelName())) {
                Cap.SetExteriorVariant(variant);
                break;
            }
        }

        Cap.UpdateClient();
        return;
    }
}