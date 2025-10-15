/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.misc.containers.ARSStructureContainer;
import com.code.tama.tts.server.registries.tardis.ARSRegistry;
import lombok.Getter;

import java.util.List;

public class DataARSList {
    @Getter
    private static List<ARSStructureContainer> StructureList;

    public static void setList(List<ARSStructureContainer> list) {
        StructureList = list;
        ARSRegistry.STRUCTURES.clear();
        ARSRegistry.STRUCTURES.addAll(list);
    }
}
