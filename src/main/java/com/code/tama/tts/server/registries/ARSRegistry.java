/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.server.enums.Structures;
import com.code.tama.tts.server.misc.ARSStructure;
import java.util.ArrayList;
import net.minecraft.network.chat.Component;

public class ARSRegistry {
    public static ArrayList<ARSStructure> STRUCTURES = new ArrayList<>();

    public static ARSStructure CLEAN_INTERIOR =
            AddStructure(new ARSStructure(Structures.CleanInterior.GetRL(), Component.translatable("tts.ars.clean")));

    public static ARSStructure GetStructure(int ID) {
        return STRUCTURES.get(ID);
    }

    public static ARSStructure AddStructure(ARSStructure structure) {
        STRUCTURES.add(structure);
        return structure;
    }

    public static ARSStructure CycleStruct(ARSStructure structure) {
        for (int i = 0; i < STRUCTURES.size() - 1; i++) {
            if (GetStructure(i).equals(structure)) return GetStructure(i + 1);
        }
        return GetStructure(0);
    }

    public static ARSStructure GetByName(String name) {
        for (ARSStructure structure : STRUCTURES) {
            if (structure.Name().equals(Component.translatable(name))) return structure;
        }
        return STRUCTURES.get(0);
    }
}
