package com.code.tama.mtm.World.tree;

import com.code.tama.mtm.World.tree.custom.GallifreyanOakTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.mtm.MODID;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, MODID);

    public static final RegistryObject<TrunkPlacerType<GallifreyanOakTrunkPlacer>> GALLIFREYAN_OAK_TRUNK_PLACER =
            TRUNK_PLACER.register("pine_trunk_placer", () -> new TrunkPlacerType<>(GallifreyanOakTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACER.register(eventBus);
    }
}