package com.code.tama.mtm.Data;

import com.code.tama.mtm.Enums.tardis.ExteriorModel;
import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.misc.ExteriorVariant;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DataExteriorList {
    private static List<DataExterior> exteriorList;

    public static void setExteriorList(List<DataExterior> list) {
        exteriorList = list;
        for (DataExterior exterior : exteriorList) {
            ExteriorVariant toAdd = new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, exterior.getTexture(), exterior.getName());
            AtomicReference<Boolean> ExistsOrNot = new AtomicReference<>();
            ExistsOrNot.set(false);
            for(ExteriorVariant exteriorVariant : ExteriorVariants.Variants) {
                if(exteriorVariant.GetTexture().equals(toAdd.GetTexture()))
                    ExistsOrNot.set(true);
            }

            if(!ExistsOrNot.get())
                ExteriorVariants.Variants.add(toAdd);
        }
    }

    public static List<DataExterior> getExteriorList() {
        return exteriorList;
    }
}
