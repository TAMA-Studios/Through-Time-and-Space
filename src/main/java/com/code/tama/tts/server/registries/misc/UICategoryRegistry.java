/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.misc;

import com.code.tama.tts.client.UI.category.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus;

import static com.code.tama.tts.TTSMod.MODID;

public class UICategoryRegistry {
	public static final ResourceKey<Registry<UICategory>> UI_CATEGORY_REGISTRY_KEY = ResourceKey
			.createRegistryKey(new ResourceLocation(MODID, "ui_category"));

	public static final DeferredRegister<UICategory> UI_CATEGORIES = DeferredRegister.create(UI_CATEGORY_REGISTRY_KEY,
			MODID);

	public static final RegistryObject<UICategory> ALL = UI_CATEGORIES.register("all", PlaceholderCategory::new);

	public static final RegistryObject<UICategory> CURRENT_LOC = UI_CATEGORIES.register("current_location",
			LocationUICategory::new);

	public static final RegistryObject<UICategory> EXTERIOR_DIAGNOSTIC_READOUT = UI_CATEGORIES.register("exterior_diagnostic_readout",
			LocationUICategory::new);

	public static final RegistryObject<UICategory> FLIGHT_STATUS = UI_CATEGORIES.register("flight_status",
			FlightStatusUICategory::new);

	public static final RegistryObject<UICategory> BOTI = UI_CATEGORIES.register("boti", BOTIUICategory::new);

	public static final RegistryObject<UICategory> DESTINATION_LOC = UI_CATEGORIES.register("destination",
			DestinationUICategory::new);

	public static final RegistryObject<UICategory> INTERIOR_PROPS = UI_CATEGORIES.register("int_props",
			InteriorPropsUICategory::new);

	private static int ID = 0;

	/**
	 * THIS IS ONLY TO BE USED DURING REGISTRATION OF AN ID DO <i>NOT</i> USE IT!
	 * USE {@link UICategoryRegistry#getMaxID()}}
	 */
	@ApiStatus.Internal
	public static int getID() {
		return ID++;
	}

	public static int getMaxID() {
		return ID - 1;
	}

	public static void register(IEventBus modEventBus) {
		UI_CATEGORIES.makeRegistry(() -> new RegistryBuilder<UICategory>().hasTags().disableSaving().disableSync());
		UI_CATEGORIES.register(modEventBus);
	}
}
