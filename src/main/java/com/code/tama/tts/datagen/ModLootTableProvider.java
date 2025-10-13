/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen;

import com.code.tama.tts.datagen.loot.ModBlockLootTables;
import java.util.List;
import java.util.Set;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ModLootTableProvider {
  public static LootTableProvider create(PackOutput output) {
    return new LootTableProvider(
        output,
        Set.of(),
        List.of(
            new LootTableProvider.SubProviderEntry(
                ModBlockLootTables::new, LootContextParamSets.BLOCK)));
  }
}
