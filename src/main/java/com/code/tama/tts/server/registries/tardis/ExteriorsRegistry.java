/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ExteriorsRegistry {
    public static ArrayList<ExteriorModelContainer> EXTERIORS = new ArrayList<>();

    public static ExteriorModelContainer Cycle(ExteriorModelContainer Variant) {
        return EXTERIORS.get(Cycle(GetOrdinal(Variant)));
    }

    public static int Cycle(int Index) {
        Index++;
        return Index >= EXTERIORS.size() ? 0 : Index;
    }

    public static ExteriorModelContainer CycleDown(ExteriorModelContainer Variant) {
        return EXTERIORS.get(CycleDown(GetOrdinal(Variant)));
    }

    public static int CycleDown(int Index) {
        Index--;
        return Math.max(Index, 0);
    }

    public static ExteriorModelContainer Get(int Variant) {
        if (Variant >= EXTERIORS.size()) Variant = 0;
        return EXTERIORS.get(Variant);
    }

    public static ExteriorModelContainer GetByName(ResourceLocation Name) {
        return EXTERIORS.stream()
                .filter(ext -> ext.getModel().equals(Name))
                .toList()
                .get(0);
    }

    public static int GetOrdinal(ExteriorModelContainer Variant) {
        AtomicReference<Integer> VariantNumber = new AtomicReference<>();
        VariantNumber.set(0);

        OUTSIDE:
        for (ExteriorModelContainer variant : ExteriorsRegistry.EXTERIORS) {
            if (variant.equals(Variant)) break OUTSIDE;
            VariantNumber.set(VariantNumber.get() + 1);
        }

        return VariantNumber.get();
    }
}
