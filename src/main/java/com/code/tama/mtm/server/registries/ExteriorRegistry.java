package com.code.tama.mtm.server.registries;

import com.code.tama.mtm.server.tardis.exteriors.AWJExterior;
import com.code.tama.mtm.server.tardis.exteriors.AbstractExterior;
import com.code.tama.mtm.server.tardis.exteriors.ModernExterior;
import com.code.tama.mtm.server.tardis.exteriors.TTCapsuleExterior;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.MTMMod.MODID;

public class ExteriorRegistry {
    public static final ResourceKey<Registry<AbstractExterior>> EXTERIORS_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MODID, "exteriors"));

    public static final DeferredRegister<AbstractExterior> EXTERIORS =
            DeferredRegister.create(EXTERIORS_KEY, MODID);

    public static final RegistryObject<ModernExterior> MODERN = EXTERIORS.register("voxel_moffat", ModernExterior::new);
    public static final RegistryObject<AWJExterior> AWJ = EXTERIORS.register("voxel_rtd", AWJExterior::new);
    public static final RegistryObject<TTCapsuleExterior> TT_CAPSULE = EXTERIORS.register("voxel_chibnal", TTCapsuleExterior::new);


    public static void register(IEventBus modEventBus) {
        EXTERIORS.makeRegistry(() -> new RegistryBuilder<AbstractExterior>()
                .hasTags()
                .disableSaving()
                .disableSync()
        );
        EXTERIORS.register(modEventBus);
    }
}