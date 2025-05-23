package com.code.tama.mtm;

import com.code.tama.mtm.core.Constants;
import com.code.tama.mtm.server.misc.ExteriorVariant;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static com.code.tama.mtm.MTMMod.MODID;

public class ExteriorVariants {
    public static ArrayList<ExteriorVariant> Variants = new ArrayList<>();

    public static final ExteriorVariant EXT_11A = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/mof_11a"), "11th Police Box (A)", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_11B = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/mof_11b"), "11th Police Box (B)", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_11C = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/mof_11c"), "11th Police Box (C)", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_12A = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/mof_12a"), "12th Police Box (A)", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_12B = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/mof_12b"), "12th Police Box (B)", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_9 = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/rtd_9"), "9th Police Box", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_9_BW = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/rtd_9_bw"), "9th Police Box (Bad Wolf)", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_10 = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/box/rtd_10"), "10th Police Box", Constants.ExteriorModelNames.ModernBox);
    public static final ExteriorVariant EXT_WHITTAKER = new ExteriorVariant(new ResourceLocation(MODID, "textures/tiles/exterior/whittaker/box/whittaker"), "AWJ", Constants.ExteriorModelNames.Whittaker);
    
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
        Variants.add(EXT_12A);
        Variants.add(EXT_12B);
        Variants.add(EXT_11A);    
        Variants.add(EXT_11B);
        Variants.add(EXT_11C);
        Variants.add(EXT_10);
        Variants.add(EXT_9);
        Variants.add(EXT_9_BW);
        Variants.add(EXT_WHITTAKER);
    }
}