/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

import static com.code.tama.tts.TTSMod.MODID;

public class DataGlobalLootModifiersProvider extends GlobalLootModifierProvider {
	public DataGlobalLootModifiersProvider(PackOutput output) {
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

//		add("zeiton_from_jungle_temples",
//				new AddItemModifier(
//						new LootItemCondition[]{
//								new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build()},
//						TTSItems.ZEITON.asItem()));
	}
}
