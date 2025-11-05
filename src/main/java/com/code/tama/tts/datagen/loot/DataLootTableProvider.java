/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class DataLootTableProvider {
	public static LootTableProvider create(PackOutput output) {
		return new LootTableProvider(output, Set.of(),
				List.of(new LootTableProvider.SubProviderEntry(DataBlockLootTables::new, LootContextParamSets.BLOCK)));
	}
}
