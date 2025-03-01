package com.code.tama.mtm;

import com.code.tama.mtm.Enums.tardis.ExteriorModel;
import com.code.tama.mtm.misc.ExteriorVariant;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static com.code.tama.mtm.mtm.MODID;

public class ExteriorVariants {
    public static ArrayList<ExteriorVariant> Variants = new ArrayList<>();

    public static int Cycle(int Index) {
        Index++;
        return Index >= Variants.size() ? 0 : Index;
    }

    public static ExteriorVariant Cycle(ExteriorVariant Variant) {
        return Variants.get(Cycle(GetOrdinal(Variant)));
    }

    public static ExteriorVariant Get(int Variant) {
        if(Variant >= Variants.size()) Variant = 0;
        return Variants.get(Variant);
    }

    public static int GetOrdinal(ExteriorVariant Variant) {
        AtomicReference<Integer> VariantNumber = new AtomicReference<>();
        VariantNumber.set(0);

        OUTSIDE:
        for(ExteriorVariant variant : ExteriorVariants.Variants) {
            if(variant.equals(Variant)) break OUTSIDE;
            VariantNumber.set(VariantNumber.get() + 1);
        }

        return VariantNumber.get();
    }

    public static void InitVariants() {
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_11a")));
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_11b")));
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_11c")));
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_12a")));
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_12b")));
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/rtd_9")));
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/rtd_9_bw")));
        Variants.add(new ExteriorVariant(ExteriorModel.COLIN_RICHMOND, new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/rtd_10")));
    }
}