/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen;

import com.code.tama.tts.datagen.assets.DataBlockStateProvider;
import com.code.tama.tts.datagen.assets.DataItemModelProvider;
import com.code.tama.tts.datagen.loot.DataGlobalLootModifiersProvider;
import com.code.tama.tts.datagen.loot.DataLootTableProvider;
import com.code.tama.tts.datagen.tags.DataBlockTagGenerator;
import com.code.tama.tts.datagen.tags.DataItemTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

import static com.code.tama.tts.TTSMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(event.includeServer(), new DataRecipeProvider(packOutput));
		generator.addProvider(event.includeServer(), DataLootTableProvider.create(packOutput));

		generator.addProvider(event.includeClient(), new DataBlockStateProvider(packOutput, existingFileHelper));
		generator.addProvider(event.includeClient(), new DataItemModelProvider(packOutput, existingFileHelper));

		DataBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
				new DataBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
		generator.addProvider(event.includeServer(), new DataItemTagGenerator(packOutput, lookupProvider,
				blockTagGenerator.contentsGetter(), existingFileHelper));

		generator.addProvider(event.includeServer(), new DataGlobalLootModifiersProvider(packOutput));

		generator.addProvider(event.includeServer(), new DataWorldGenProvider(packOutput, lookupProvider));
	}
}
