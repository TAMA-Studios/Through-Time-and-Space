package com.code.tama.mtm.server.worlds.tree;

import com.code.tama.mtm.server.worlds.tree.custom.GallifreyanFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.MTMMod.MODID;

public class ModFoliagePlacers {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, MODID);

    public static final RegistryObject<FoliagePlacerType<GallifreyanFoliagePlacer>> GALLIFREYAN_OAK_PLACER =
            FOLIAGE_PLACERS.register("gallifreyan_oak_foliage_placer", () -> new FoliagePlacerType<>(GallifreyanFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
    }
}