package com.code.tama.mtm.server;

import com.code.tama.mtm.server.entities.controls.ModularControl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.MTMMod.MODID;

public class MTMEntities {

    // Create the Deferred Register for entities
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<ModularControl>> MODULAR_CONTROL =
            ENTITY_TYPES.register("modular_control",
                    () -> EntityType.Builder.<ModularControl>of(ModularControl::new, MobCategory.MISC)
                            .sized(0.1f, 0.1f)
                            .build("modular_control"));
}
