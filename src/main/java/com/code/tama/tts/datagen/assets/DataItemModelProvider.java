/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen.assets;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.LinkedHashMap;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DataItemModelProvider extends ItemModelProvider {
	private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

	static {
		trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
		trimMaterials.put(TrimMaterials.IRON, 0.2F);
		trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
		trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
		trimMaterials.put(TrimMaterials.COPPER, 0.5F);
		trimMaterials.put(TrimMaterials.GOLD, 0.6F);
		trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
		trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
		trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
		trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
	}

	public DataItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		// simpleItem(MItems.BAR_BRAWL_MUSIC_DISC);
		// simpleBlockItem(MBlocks.SAPPHIRE_DOOR);
		// fenceItem(MBlocks.SAPPHIRE_FENCE, MBlocks.SAPPHIRE_BLOCK);
		// buttonItem(MBlocks.SAPPHIRE_BUTTON, MBlocks.SAPPHIRE_BLOCK);
		// wallItem(MBlocks.SAPPHIRE_WALL, MBlocks.SAPPHIRE_BLOCK);
		// evenSimplerBlockItem(MBlocks.SAPPHIRE_STAIRS);
		// trapdoorItem(MBlocks.SAPPHIRE_TRAPDOOR);
		// handheldItem(MItems.SAPPHIRE_SWORD);
		// trimmedArmorItem(MItems.SAPPHIRE_HELMET);
		// simpleBlockItemBlockTexture(MBlocks.CATMINT);
		// withExistingParent(MItems.RHINO_SPANW_EGG.getId().getPath(),
		// mcLoc("item/template_spawn_egg"));
		// saplingItem(MBlocks.PINE_SAPLING);
		withExistingParent(TTSBlocks.EXTERIOR_BLOCK.getId().getPath(), mcLoc("block/air"));
		withExistingParent(TTSBlocks.HUDOLIN_CONSOLE_BLOCK.getId().getPath(), mcLoc("block/air"));
		withExistingParent(TTSBlocks.NESS_CONSOLE_BLOCK.getId().getPath(), mcLoc("block/air"));
		simpleBlockItemBlockTexture(TTSBlocks.INTERIOR_ROCK);
		saplingItem(TTSBlocks.GALLIFREYAN_SAPLING);
		// simplestBlockItem(TTSBlocks.BLUE_ROTOR);
		// simplestBlockItem(TTSBlocks.COPPER_ROTOR);
		// simplestBlockItem(TTSBlocks.AMETHYST_ROTOR);
		// simplestBlockItem(TTSBlocks.CRT_MONITOR_BLOCK);
		// // withExistingParent(TTSBlocks.GALLIFREYAN_OAK_LOG.getId().getPath(), new
		// ResourceLocation(MODID, "dimensional/gallifreyan/gallifreyan_oak_log"));
		// evenSimplerBlockItem(TTSBlocks.GALLIFREYAN_OAK_LOG);
		// evenSimplerBlockItem(TTSBlocks.GALLIFREYAN_OAK_LEAVES);
		// evenSimplerBlockItem(TTSBlocks.GALLIFREYAN_OAK_PLANKS);
		// evenSimplerBlockItem(TTSBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD);
		// evenSimplerBlockItem(TTSBlocks.STRIPPED_GALLIFREYAN_OAK_LOG);
		// evenSimplerBlockItem(TTSBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD);
		// evenSimplerBlockItem(TTSBlocks.GALLIFREYAN_SAND);
		for (RegistryEntry<Block> block : TTSBlocks.AllValues().stream().toList()) {
			ResourceLocation outputLoc = extendWithFolder(
					new ResourceLocation(modid, ("item/" + block.getId().getPath())));
			if (!this.generatedModels.containsValue(outputLoc))
				simplestBlockItem(block);
		}
	}

	private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
		final String MOD_ID = MODID;

		if (itemRegistryObject.get() instanceof ArmorItem armorItem) {
			trimMaterials.forEach((trimMaterial, value) -> {
				float trimValue = value;

				String armorType = switch (armorItem.getEquipmentSlot()) {
					case HEAD -> "helmet";
					case CHEST -> "chestplate";
					case LEGS -> "leggings";
					case FEET -> "boots";
					default -> "";
				};

				String armorItemPath = "item/" + armorItem;
				String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
				String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
				ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
				ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
				ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

				// This is used for making the ExistingFileHelper acknowledge that this texture
				// exist, so this will
				// avoid an IllegalArgumentException
				existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

				// Trimmed armorItem files
				getBuilder(currentTrimName).parent(new ModelFile.UncheckedModelFile("item/generated"))
						.texture("layer0", armorItemResLoc).texture("layer1", trimResLoc);

				// Non-trimmed armorItem file (normal variant)
				this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated")).override()
						.model(new ModelFile.UncheckedModelFile(trimNameResLoc))
						.predicate(mcLoc("trim_type"), trimValue).end().texture("layer0",
								new ResourceLocation(MOD_ID, "item/" + itemRegistryObject.getId().getPath()));
			});
		}
	}

	private ItemModelBuilder saplingItem(RegistryEntry<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(MODID, "block/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(MODID, "item/" + item.getId().getPath()));
	}

	public void evenSimplerBlockItem(RegistryEntry<Block> block) {
		this.withExistingParent(MODID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
	}

	public void trapdoorItem(RegistryEntry<Block> block) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
	}

	public void fenceItem(RegistryEntry<Block> block, RegistryEntry<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
				.texture("texture", new ResourceLocation(MODID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void buttonItem(RegistryEntry<Block> block, RegistryEntry<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture", new ResourceLocation(MODID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void wallItem(RegistryEntry<Block> block, RegistryEntry<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
				.texture("wall", new ResourceLocation(MODID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld")).texture("layer0",
				new ResourceLocation(MODID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockItem(RegistryEntry<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(MODID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simplestBlockItem(RegistryEntry<Block> item) {
		assert item.getId() != null;
		ResourceLocation resourceLocation = item.getId();
		try {
			return withExistingParent("item/" + resourceLocation.getPath(),
					new ResourceLocation(MODID, "block/" + item.getId().getPath()));
		} catch (Exception ignored) {
			System.out.println("errored at " + item.getId());
		}
		return null;
	}

	private ItemModelBuilder simpleBlockItemBlockTexture(RegistryEntry<Block> item) {
		try {
			return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
					new ResourceLocation(MODID, "block/" + item.getId().getPath()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResourceLocation extendWithFolder(ResourceLocation rl) {
		if (rl.getPath().contains("/")) {
			return rl;
		}
		return new ResourceLocation(rl.getNamespace(), folder + "/" + rl.getPath());
	}
}
