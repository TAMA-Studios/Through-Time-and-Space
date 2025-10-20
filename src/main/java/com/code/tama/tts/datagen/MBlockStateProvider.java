/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.registries.forge.TTSBlocks;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MBlockStateProvider extends BlockStateProvider {
	public MBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, MODID, exFileHelper);
	}

	///////////////////////////////////// HERE!
	@Override
	protected void registerStatesAndModels() {
		for (RegistryObject<Block> block : TTSBlocks.BLOCKS.getEntries()) {
			try {
				if (block.getId().toString().contains("roundel"))
					Roundel(block);
				blockWithItem(block);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void Roundel(RegistryObject<Block> registryObject) {
		ModelBuilder<BlockModelBuilder> modelBuilder = models().cubeAll(name(registryObject.get()),
				blockTexture(registryObject.get()));
		simpleBlock(registryObject.get(), modelBuilder);
	}

	private void SpecialBlockItem(RegistryObject<? extends Block> blockRegistryObject) {
		simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(
				MODID + ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
	}

	private void blockItem(RegistryObject<Block> blockRegistryObject) {
		simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(
				MODID + ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
	}

	private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
		try {
			simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
			TTSMod.LOGGER.info(this.blockTexture(blockRegistryObject.get()));
			TTSMod.LOGGER.info(cubeAll(blockRegistryObject.get()).getLocation());
		} catch (Exception e) {
			TTSMod.LOGGER.info(e.getMessage());
		}
	}

	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(),
				models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),
						new ResourceLocation("minecraft:block/leaves"), "all", blockTexture(blockRegistryObject.get()))
						.renderType("cutout"));
	}

	private String name(Block block) {
		return key(block).getPath();
	}

	private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
		try {
			simpleBlock(blockRegistryObject.get(),
					models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),
							blockTexture(blockRegistryObject.get())).renderType("cutout"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void axisBlock(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end) {
		try {
			axisBlock(block, models().cubeColumn(name(block), side, end),
					models().cubeColumnHorizontal(name(block) + "_horizontal", side, end));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void buttonBlock(ButtonBlock block, ResourceLocation texture) {
		try {
			ModelFile button = models().button(name(block), texture);
			ModelFile buttonPressed = models().buttonPressed(name(block) + "_pressed", texture);
			buttonBlock(block, button, buttonPressed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doorBlockWithRenderType(DoorBlock block, ResourceLocation bottom, ResourceLocation top,
			String renderType) {
		try {
			String baseName = key(block).toString();
			ModelFile bottomLeft = models().doorBottomLeft(baseName + "_bottom_left", bottom, top)
					.renderType(renderType);
			ModelFile bottomLeftOpen = models().doorBottomLeftOpen(baseName + "_bottom_left_open", bottom, top)
					.renderType(renderType);
			ModelFile bottomRight = models().doorBottomRight(baseName + "_bottom_right", bottom, top)
					.renderType(renderType);
			ModelFile bottomRightOpen = models().doorBottomRightOpen(baseName + "_bottom_right_open", bottom, top)
					.renderType(renderType);
			ModelFile topLeft = models().doorTopLeft(baseName + "_top_left", bottom, top).renderType(renderType);
			ModelFile topLeftOpen = models().doorTopLeftOpen(baseName + "_top_left_open", bottom, top)
					.renderType(renderType);
			ModelFile topRight = models().doorTopRight(baseName + "_top_right", bottom, top).renderType(renderType);
			ModelFile topRightOpen = models().doorTopRightOpen(baseName + "_top_right_open", bottom, top)
					.renderType(renderType);
			doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight,
					topRightOpen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fenceBlock(FenceBlock block, ResourceLocation texture) {
		try {
			String baseName = key(block).toString();
			fourWayBlock(block, models().fencePost(baseName + "_post", texture),
					models().fenceSide(baseName + "_side", texture));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fenceGateBlock(FenceGateBlock block, ResourceLocation texture) {
		try {
			ModelFile gate = models().fenceGate(key(block).toString(), texture);
			ModelFile gateOpen = models().fenceGateOpen(key(block).toString() + "_open", texture);
			ModelFile gateWall = models().fenceGateWall(key(block).toString() + "_wall", texture);
			ModelFile gateWallOpen = models().fenceGateWallOpen(key(block).toString() + "_wall_open", texture);
			fenceGateBlock(block, gate, gateOpen, gateWall, gateWallOpen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
		simpleBlock(signBlock, sign);
		simpleBlock(wallSignBlock, sign);
	}

	public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
		ModelFile sign = models().sign(name(signBlock), texture);
		hangingSignBlock(signBlock, wallSignBlock, sign);
	}

	@Override
	public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
		try {
			ModelFile pressurePlate = models().pressurePlate(name(block), texture);
			ModelFile pressurePlateDown = models().pressurePlateDown(name(block) + "_down", texture);
			pressurePlateBlock(block, pressurePlate, pressurePlateDown);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void slabBlock(SlabBlock block, ResourceLocation doubleslab, ResourceLocation texture) {
		try {
			slabBlock(block, doubleslab, texture, texture, texture);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stairsBlock(StairBlock block, ResourceLocation texture) {
		try {
			stairsBlock(block, texture, texture, texture);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void trapdoorBlockWithRenderType(TrapDoorBlock block, ResourceLocation texture, boolean orientable,
			String renderType) {
		try {
			String baseName = key(block).toString();
			ModelFile bottom = orientable
					? models().trapdoorOrientableBottom(baseName + "_bottom", texture).renderType(renderType)
					: models().trapdoorBottom(baseName + "_bottom", texture).renderType(renderType);
			ModelFile top = orientable
					? models().trapdoorOrientableTop(baseName + "_top", texture).renderType(renderType)
					: models().trapdoorTop(baseName + "_top", texture).renderType(renderType);
			ModelFile open = orientable
					? models().trapdoorOrientableOpen(baseName + "_open", texture).renderType(renderType)
					: models().trapdoorOpen(baseName + "_open", texture).renderType(renderType);
			trapdoorBlock(block, bottom, top, open, orientable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void makeStrawberryCrop(CropBlock block, String modelName, String
	// textureName) {
	// Function<BlockState, ConfiguredModel[]> function = state ->
	// strawberryStates(state, block, modelName, textureName);
	//
	// getVariantBuilder(block).forAllStates(function);
	// }

	// private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block,
	// String modelName, String textureName) {
	// ConfiguredModel[] models = new ConfiguredModel[1];
	// models[0] = new ConfiguredModel(models().crop(modelName +
	// state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
	// new ResourceLocation(MODID, "block/" + textureName +
	// state.getValue(((StrawberryCropBlock)
	// block).getAgeProperty()))).renderType("cutout"));
	//
	// return models;
	// }

	// public void makeCornCrop(CropBlock block, String modelName, String
	// textureName) {
	// Function<BlockState, ConfiguredModel[]> function = state -> cornStates(state,
	// block, modelName, textureName);
	//
	// getVariantBuilder(block).forAllStates(function);
	// }
	//
	// private ConfiguredModel[] cornStates(BlockState state, CropBlock block,
	// String modelName, String textureName) {
	// ConfiguredModel[] models = new ConfiguredModel[1];
	// models[0] = new ConfiguredModel(models().crop(modelName +
	// state.getValue(((CornCropBlock) block).getAgeProperty()),
	// new ResourceLocation(MODID, "block/" + textureName +
	// state.getValue(((CornCropBlock)
	// block).getAgeProperty()))).renderType("cutout"));
	//
	// return models;
	// }

	@Override
	public void wallBlock(WallBlock block, ResourceLocation texture) {
		try {
			wallBlock(block, models().wallPost(key(block).toString() + "_post", texture),
					models().wallSide(key(block).toString() + "_side", texture),
					models().wallSideTall(key(block).toString() + "_side_tall", texture));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
