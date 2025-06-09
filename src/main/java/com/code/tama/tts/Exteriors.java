/* (C) TAMA Studios 2025 */
package com.code.tama.tts;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.code.tama.tts.server.misc.Exterior;

import net.minecraft.resources.ResourceLocation;

public class Exteriors {
    public static ArrayList<Exterior> EXTERIORS = new ArrayList<>();


    public static int Cycle(int Index) {
        Index++;
        return Index >= EXTERIORS.size() ? 0 : Index;
    }

    public static int CycleDown(int Index) {
        Index--;
        return Math.max(Index, 0);
    }

    public static Exterior Cycle(Exterior Variant) {
        return EXTERIORS.get(Cycle(GetOrdinal(Variant)));
    }

    public static Exterior CycleDown(Exterior Variant) {
        return EXTERIORS.get(CycleDown(GetOrdinal(Variant)));
    }

    public static Exterior Get(int Variant) {
        if(Variant >= EXTERIORS.size()) Variant = 0;
        return EXTERIORS.get(Variant);
    }

    public static Exterior GetByName(ResourceLocation Name) {
        return EXTERIORS.stream().filter(ext -> ext.GetModelName().equals(Name)).toList().get(0);
    }

    public static int GetOrdinal(Exterior Variant) {
        AtomicReference<Integer> VariantNumber = new AtomicReference<>();
        VariantNumber.set(0);

        OUTSIDE:
        for (Exterior variant : Exteriors.EXTERIORS) {
            if (variant.equals(Variant)) break OUTSIDE;
            VariantNumber.set(VariantNumber.get() + 1);
        }

        return VariantNumber.get();
    }
}