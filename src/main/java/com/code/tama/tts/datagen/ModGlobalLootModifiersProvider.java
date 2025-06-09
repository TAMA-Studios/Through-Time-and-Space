/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.loots.AddItemModifier;
import com.code.tama.tts.server.registries.TTSItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, MODID);
    }

    @Override
    protected void start() {
        // add("pine_cone_from_grass", new AddItemModifier(new LootItemCondition[] {
        // LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
        // LootItemRandomChanceCondition.randomChance(0.35f).build()},
        // ModItems.PINE_CONE.get()));
        //
        // add("pine_cone_from_creeper", new AddItemModifier(new LootItemCondition[] {
        // new LootTableIdCondition.Builder(new
        // ResourceLocation("entities/creeper")).build() }, ModItems.PINE_CONE.get()));

        add(
                "zeiton_from_jungle_temples",
                new AddItemModifier(
                        new LootItemCondition[] {
                            new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build()
                        },
                        TTSItems.ZEITON.get()));
    }
}
