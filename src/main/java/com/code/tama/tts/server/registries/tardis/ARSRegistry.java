/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import com.code.tama.tts.server.enums.Structures;
import com.code.tama.tts.server.misc.containers.ARSStructureContainer;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class ARSRegistry {
    public static ArrayList<ARSStructureContainer> STRUCTURES = new ArrayList<>();

    public static ARSStructureContainer CLEAN_INTERIOR = AddStructure(
            new ARSStructureContainer(Structures.CleanInterior.GetRL(), Component.translatable("tts.ars.clean"), 0));

    public static ARSStructureContainer GetStructure(int ID) {
        return STRUCTURES.get(ID);
    }

    public static ARSStructureContainer AddStructure(ARSStructureContainer structure) {
        STRUCTURES.add(structure);
        return structure;
    }

    public static ARSStructureContainer CycleStruct(ARSStructureContainer structure) {
        for (int i = 0; i < STRUCTURES.size() - 1; i++) {
            if (GetStructure(i).equals(structure)) return GetStructure(i + 1);
        }
        return GetStructure(0);
    }

    public static ARSStructureContainer GetByName(String name) {
        for (ARSStructureContainer structure : STRUCTURES) {
            if (structure.Name().equals(Component.translatable(name))) return structure;
        }
        return STRUCTURES.get(0);
    }
}
