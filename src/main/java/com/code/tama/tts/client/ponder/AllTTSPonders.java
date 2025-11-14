/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.ponder;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;

import net.minecraft.resources.ResourceLocation;

public class AllTTSPonders {
	public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

		HELPER.forComponents(TTSBlocks.HUDOLIN_CONSOLE_BLOCK).addStoryBoard("gadgets/console", TTSPonderings::console);
	}
}
