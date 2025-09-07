/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.Exteriors;
import com.code.tama.tts.server.data.json.dataHolders.DataExterior;
import com.code.tama.tts.server.misc.Exterior;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;

public class DataExteriorList {
    @Getter
    private static List<DataExterior> exteriorList;

    public static void setExteriorList(List<DataExterior> list) {
        exteriorList = list;
        for (DataExterior exterior : exteriorList) {
            Exterior toAdd = new Exterior(exterior.name(), exterior.ModelName());
            AtomicReference<Boolean> ExistsOrNot = new AtomicReference<>();
            ExistsOrNot.set(false);
            for (Exterior existing : Exteriors.EXTERIORS) {
                if (existing.GetModelName().equals(toAdd.GetModelName())) ExistsOrNot.set(true);
            }

            if (!ExistsOrNot.get()) Exteriors.EXTERIORS.add(toAdd);
        }
    }
}
