/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.tardis.controls.*;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ControlsRegistry {
	public static final ResourceKey<Registry<AbstractControl>> CONTROL_REGISTRY_KEY = ResourceKey
			.createRegistryKey(new ResourceLocation(MODID, "control"));

	public static final DeferredRegister<AbstractControl> CONTROLS = DeferredRegister.create(CONTROL_REGISTRY_KEY,
			MODID);

	public static final RegistryObject<EmptyControl> EMPTY = CONTROLS.register("empty", EmptyControl::new);
	public static final RegistryObject<EnvironmentScannerControl> ENVIRONMENT_SCANNER = CONTROLS
			.register("environment_scanner", EnvironmentScannerControl::new);
	public static final RegistryObject<FacingControl> FACING_CONTROL = CONTROLS.register("facing_control",
			FacingControl::new);

	public static final RegistryObject<IncrementControl> INCREMENT_CONTROL = CONTROLS.register("increment_control",
			IncrementControl::new);
	public static final RegistryObject<PowerControl> POWER_CONTROL = CONTROLS.register("power_control",
			PowerControl::new);

	public static final RegistryObject<ThrottleControl> THROTTLE = CONTROLS.register("throttle", ThrottleControl::new);

	public static final RegistryObject<VariantControl> VARIANT_CONTROL = CONTROLS.register("variant_control",
			VariantControl::new);

	public static final RegistryObject<X_Control> X_CONTROL = CONTROLS.register("x_control", X_Control::new);

	public static final RegistryObject<Y_Control> Y_CONTROL = CONTROLS.register("y_control", Y_Control::new);

	public static final RegistryObject<Z_Control> Z_CONTROL = CONTROLS.register("z_control", Z_Control::new);

	public static final RegistryObject<DimensionControl> DIMENSION_CYCLE = CONTROLS.register("dimension_cycle",
			DimensionControl::new);

	@SuppressWarnings("unchecked")
	public static AbstractControl Cycle(AbstractControl control) {
		for (int i = 0; i < CONTROLS.getEntries().size(); i++) {
			if (control.equals(((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[i]).get())) {
				if (i == CONTROLS.getEntries().size())
					return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[0]).get();
				return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[++i]).get();
			}
		}

		return EMPTY.get();
	}

	@SuppressWarnings("unchecked")
	public static AbstractControl CycleBackwards(AbstractControl control) {
		for (int i = CONTROLS.getEntries().size(); i >= 0; i--) {
			if (control.equals(CONTROLS.getEntries().toArray()[i])) {
				if (i == 0)
					return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[CONTROLS.getEntries()
							.size()]).get();
				return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[i]).get();
			}
		}

		return EMPTY.get();
	}

	@SuppressWarnings("unchecked")
	public static RegistryObject<AbstractControl> getFromOrdinal(int control) {
		return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[control]);
	}

	@SuppressWarnings("unchecked")
	public static int getOrdinal(AbstractControl control) {
		for (int i = 0; i < CONTROLS.getEntries().size(); i++) {
			if (control.equals(((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[i]).get()))
				return i;
		}

		return 0;
	}

	public static void register(IEventBus modEventBus) {
		CONTROLS.makeRegistry(() -> new RegistryBuilder<AbstractControl>().hasTags().disableSaving().disableSync());
		CONTROLS.register(modEventBus);
	}
}