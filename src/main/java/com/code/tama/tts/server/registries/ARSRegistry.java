package com.code.tama.tts.server.registries;

import com.code.tama.tts.server.enums.Structures;
import com.code.tama.tts.server.misc.ARSStructure;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class ARSRegistry {
    private static final ArrayList<ARSStructure> ARSStructures = new ArrayList<>();

    public static ARSStructure CLEAN_INTERIOR = AddStructure(new ARSStructure(Structures.CleanInterior.GetRL() , Component.translatable("tts.ars.clean")));
    public static ARSStructure CITADEL_INTERIOR = AddStructure(new ARSStructure(Structures.CitadelInterior.GetRL(), Component.translatable("tts.ars.citadel")));
    public static ARSStructure CLEAN_WORKSHOP = AddStructure(new ARSStructure(Structures.CitadelInterior.GetRL(), Component.translatable("tts.ars.clean_workshop")));

    public static ARSStructure GetStructure(int ID) {
        return ARSStructures.get(ID);
    }

    public static ARSStructure AddStructure(ARSStructure structure) {
        ARSStructures.add(structure);
        return structure;
    }

    public static ARSStructure CycleStruct(ARSStructure structure) {
        boolean next = false;
        for (ARSStructure structure1 : ARSStructures) {
            if(next) {
                return structure1;
            }
            if(structure1.equals(structure)) {
                next = true;
            }
        }
        if(next) return GetStructure(0);
        else return structure;
    }

    /** Make sure the static fields get loaded **/
    public static void RegisterDefaults() {

    }
}
