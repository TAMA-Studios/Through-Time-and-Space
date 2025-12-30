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

	public static final RegistryObject<XControl> X_CONTROL = CONTROLS.register("x_control", XControl::new);

	public static final RegistryObject<YControl> Y_CONTROL = CONTROLS.register("y_control", YControl::new);

	public static final RegistryObject<ZControl> Z_CONTROL = CONTROLS.register("z_control", ZControl::new);

	public static final RegistryObject<SimplestControl> VORTEX_ANCHOR_CONTROL = CONTROLS.register("vortex_anchor",
			() -> new SimplestControl(tardis -> tardis.GetData().getControlData().setVortexAnchor(true),
					tardis -> tardis.GetData().getControlData().setVortexAnchor(false)));

	public static final RegistryObject<SimplestControl> ENGINE_BRAKE = CONTROLS.register("engine_brake",
			() -> new SimplestControl(tardis -> tardis.GetData().getControlData().setEngineBrake(true),
					tardis -> tardis.GetData().getControlData().setEngineBrake(false)));

	public static final RegistryObject<SimplestControl> SIMPLE_MODE = CONTROLS.register("simple_mode",
			() -> new SimplestControl(tardis -> tardis.GetData().getControlData().setSimpleMode(true),
					tardis -> tardis.GetData().getControlData().setSimpleMode(false)));

	public static final RegistryObject<SimplestControl> BRAKES = CONTROLS.register("brakes",
			() -> new SimplestControl(tardis -> tardis.GetData().getControlData().setBrakes(true),
					tardis -> tardis.GetData().getControlData().setBrakes(false)));

	public static final RegistryObject<SimplestControl> REFUEL = CONTROLS.register("refuel",
			() -> new SimplestControl(tardis -> {
				if (!tardis.GetData().getSubSystemsData().DynamorphicGeneratorStacks.isEmpty()
						&& tardis.GetData().getSubSystemsData().DynamorphicController.isActivated(tardis.GetLevel()))
					tardis.GetData().setRefueling(true);
			}, tardis -> tardis.GetData().setRefueling(false)));

	public static final RegistryObject<SimplestControl> STABILIZERS = CONTROLS.register("stabilizers",
			() -> new SimplestControl(tardis -> tardis.GetData().getControlData().setStabilizers(true),
					tardis -> tardis.GetData().getControlData().setStabilizers(true)));

	public static final RegistryObject<FastReturnControl> FAST_RETURN = CONTROLS.register("fast_return",
			FastReturnControl::new);

	public static final RegistryObject<DimensionControl> DIMENSION_CYCLE = CONTROLS.register("dimension_cycle",
			DimensionControl::new);

	public static final RegistryObject<HelmicRegulatorControl> HELMIC_REGULATOR = CONTROLS.register("helmic_regulator",
			HelmicRegulatorControl::new);

	public static final RegistryObject<CoordinateLockControl> COORDINATE_LOCK = CONTROLS.register("coordinate_lock",
			CoordinateLockControl::new);

	public static final RegistryObject<CoordinateLockControl> APC_STATE = CONTROLS.register("apc_state",
			CoordinateLockControl::new);

	public static final RegistryObject<ArtronPacketOutput> ARTRON_PACKET_OUTPUT = CONTROLS
			.register("artron_packet_output", ArtronPacketOutput::new);

	public static final RegistryObject<TerminationProtocolControl> TERMINATION_PROTOCOL = CONTROLS
			.register("termination_protocol", TerminationProtocolControl::new);

	@SuppressWarnings("unchecked")
	public static AbstractControl Cycle(AbstractControl control) {
		// for (int i = 0; i < CONTROLS.getEntries().size(); i++) {
		// if (control.equals(((RegistryObject<AbstractControl>)
		// CONTROLS.getEntries().toArray()[i]).get())) {
		// if (i >= CONTROLS.getEntries().size() - 1)
		// return ((RegistryObject<AbstractControl>)
		// CONTROLS.getEntries().toArray()[0]).get();
		// else
		// return ((RegistryObject<AbstractControl>)
		// CONTROLS.getEntries().toArray()[++i]).get();
		// }
		// }

		int ordinal = (getOrdinal(control) + 1);
		if (ordinal >= CONTROLS.getEntries().size())
			return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[0]).get();
		else {
			return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[ordinal]).get();
		}
	}

	@SuppressWarnings("unchecked")
	public static AbstractControl CycleBackwards(AbstractControl control) {
		int ordinal = (getOrdinal(control) - 1);
		if (ordinal <= 0)
			return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[CONTROLS.getEntries().size() - 1])
					.get();
		else {
			return ((RegistryObject<AbstractControl>) CONTROLS.getEntries().toArray()[ordinal]).get();
		}
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