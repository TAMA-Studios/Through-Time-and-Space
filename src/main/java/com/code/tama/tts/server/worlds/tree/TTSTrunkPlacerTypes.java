/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.tree;

import com.code.tama.tts.server.worlds.tree.custom.GallifreyanOakTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.tts.TTSMod.MODID;

public class TTSTrunkPlacerTypes {
	public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER = DeferredRegister
			.create(Registries.TRUNK_PLACER_TYPE, MODID);

	public static final RegistryObject<TrunkPlacerType<GallifreyanOakTrunkPlacer>> GALLIFREYAN_OAK_TRUNK_PLACER = TTSTrunkPlacerTypes.TRUNK_PLACER
			.register("pine_trunk_placer", () -> new TrunkPlacerType<>(GallifreyanOakTrunkPlacer.CODEC));

	public static void register(IEventBus eventBus) {
		TRUNK_PLACER.register(eventBus);
	}
}
