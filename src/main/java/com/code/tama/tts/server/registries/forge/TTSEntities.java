/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.entities.controls.ModularControl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TTSEntities {

    // Create the Deferred Register for entities
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<ModularControl>> MODULAR_CONTROL = ENTITY_TYPES.register(
            "modular_control", () -> EntityType.Builder.<ModularControl>of(ModularControl::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f)
                    .build("modular_control"));
}
