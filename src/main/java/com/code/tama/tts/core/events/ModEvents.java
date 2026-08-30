/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.events;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.entities.TardisFlightEntity;
import com.code.tama.tts.core.registries.forge.TTSEntities;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TTSMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(TTSEntities.TARDIS_FLIGHT.get(), TardisFlightEntity.createAttributes().build());
	}
}
