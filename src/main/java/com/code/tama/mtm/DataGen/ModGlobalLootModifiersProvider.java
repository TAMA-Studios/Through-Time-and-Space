package com.code.tama.mtm.DataGen;

import com.code.tama.mtm.Items.MItems;
import com.code.tama.mtm.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import static com.code.tama.mtm.mtm.MODID;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, MODID);
    }

    @Override
    protected void start() {
//        add("pine_cone_from_grass", new AddItemModifier(new LootItemCondition[] {
//                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
//                LootItemRandomChanceCondition.randomChance(0.35f).build()}, ModItems.PINE_CONE.get()));
//
//        add("pine_cone_from_creeper", new AddItemModifier(new LootItemCondition[] {
//                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build() }, ModItems.PINE_CONE.get()));

        add("zeiton_from_jungle_temples", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build() }, MItems.ZEITON.get()));
    }
}