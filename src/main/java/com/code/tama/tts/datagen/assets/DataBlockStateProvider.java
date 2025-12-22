/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen.assets;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.blocks.Panels.ChameleonCircuitPanel;
import com.code.tama.tts.server.blocks.Panels.PowerLever;
import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static com.code.tama.tts.TTSMod.MODID;

public class DataBlockStateProvider extends BlockStateProvider {
	private final List<Block> states = new ArrayList<>();

	public DataBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, MODID, exFileHelper);
	}

	/// ////////////////////////////////// HERE!
	@Override
	protected void registerStatesAndModels() {
		states.add(TTSBlocks.POWER_LEVER.get());
		states.add(TTSBlocks.BRUSHED_STRUCTURAL_STEEL.get());

		RegisterStateForExistingModel(TTSBlocks.BRUSHED_STRUCTURAL_STEEL,
				"decoration/structural_steel_brushed/structural_steel");

		RegisterStateForExistingModel(TTSBlocks.BRUSHED_STRUCTURAL_STEEL_WEATHERED,
				"decoration/structural_steel_brushed/structural_steel_weathered");

		RegisterStateForExistingModel(TTSBlocks.BRUSHED_STRUCTURAL_STEEL_RUSTED,
				"decoration/structural_steel_brushed/structural_steel_rusted");

		getVariantBuilder(TTSBlocks.POWER_LEVER.get()).forAllStates(state -> {
			AttachFace face = state.getValue(PowerLever.FACE);
			Direction facing = state.getValue(PowerLever.FACING);
			boolean powered = state.getValue(BlockStateProperties.POWERED);

			int xRot = 0;
			int yRot = 0;

			switch (face) {
				case FLOOR -> {
					yRot = facing.get2DDataValue() * 90;
					break;
				}
				case CEILING -> {
					xRot = 180;
					yRot = facing.get2DDataValue() * 90;
					break;
				}
				case WALL -> {
					xRot = 90;
					yRot = switch (facing) {
						case NORTH -> 0;
						case EAST -> 90;
						case SOUTH -> 180;
						case WEST -> 270;
						default -> 0;
					};
					break;
				}
			}

			// Model selection based on POWERED
			String modelName = powered ? "block/control/power_lever_on" : "block/control/power_lever";

			return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc(modelName))).rotationX(xRot)
					.rotationY(yRot).build();
		});

		for (RegistryEntry<Block> block : TTSBlocks.AllValues().stream().toList()) {
			if (states.contains(block.get()))
				continue;
			states.add(block.get());
			try {
				if (block.get().defaultBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
					Horizontal(block);
				}

				// if (AnnotationUtils.hasAnnotation(Roundel.class, block))
				else
					BlockWithItemAndState(block);

				// blockWithItem(block);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private <T extends Block> void RegisterStateForExistingModel(BlockEntry<T> block, String model) {
		getVariantBuilder(block.get()).forAllStates(state -> {
			String modelName = model.substring(8).equals("block/") ? model : "block/" + model;
			return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc(modelName))).build();
		});
	}

	private <T extends Block> void BlockWithItemAndState(RegistryEntry<T> registryObject) {
		ModelBuilder<BlockModelBuilder> modelBuilder = models().cubeAll(name(registryObject.get()),
				blockTexture(registryObject.get()));

		simpleBlockWithItem(registryObject.get(), modelBuilder);
	}

	private void Horizontal(RegistryEntry<Block> registryObject) {
		Block block = registryObject.get();

		// Safety check: only handle blocks that have the HORIZONTAL_FACING property
		if (!block.defaultBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
			TTSMod.LOGGER.warn("Block {} has no HORIZONTAL_FACING property! Skipping Horizontal()", block);
			return;
		}

		ConfiguredModel model[] = new ConfiguredModel[4];

		int i = 0;

		for (Direction direction : Direction.values()) {
			if (!(direction.equals(Direction.UP) || direction.equals(Direction.DOWN))) {

				int yRot = switch (direction) {
					case NORTH -> 180;
					case SOUTH -> 0;
					case WEST -> 90;
					case EAST -> 270;
					default -> 0;
				};

				model[i] = new ConfiguredModel(new ModelFile.UncheckedModelFile(MODID + ":block/" + name(block)), 0,
						yRot, false);

				i++;
			}
		}

		getVariantBuilder(block).partialState().setModels(model);

		simpleBlockItem(registryObject.get(), model[0].model);
		// blockItem(registryObject);
	}

	private void SpecialBlockItem(RegistryObject<? extends Block> blockRegistryObject) {
		simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(
				MODID + ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
	}

	private void blockItem(RegistryEntry<Block> blockRegistryObject) {
		simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(
				MODID + ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
	}

	private void blockWithItem(RegistryEntry<Block> blockRegistryObject) {
		try {

			models().cubeAll(name(blockRegistryObject.get()), blockRegistryObject.getId());
			simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
			// TTSMod.LOGGER.info(this.blockTexture(blockRegistryObject.get()));
			// TTSMod.LOGGER.info(cubeAll(blockRegistryObject.get()).getLocation());
		} catch (Exception e) {
			TTSMod.LOGGER.info(e.getMessage());
		}
	}

	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	private void leavesBlock(RegistryEntry<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(),
				models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),
						new ResourceLocation("minecraft:block/leaves"), "all", blockTexture(blockRegistryObject.get()))
						.renderType("cutout"));
	}

	private String name(Block block) {
		return "block/" + key(block).getPath();
	}

	private void saplingBlock(RegistryEntry<Block> blockRegistryObject) {
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

	public static <T extends Block> void existingModel(DataGenContext<Block, T> ctx,
			RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(ctx.get()).forAllStates(state -> {
			int y = 0;

			if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
				y = (int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
			} else if (state.hasProperty(BlockStateProperties.FACING)) {
				y = (int) state.getValue(BlockStateProperties.FACING).toYRot();
			}

			return new ConfiguredModel[]{new ConfiguredModel(
					provider.models().getExistingFile(new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName())), 0,
					y, false)};
		});
	}

	public static <T extends Block> void existingModel(String path, DataGenContext<Block, T> ctx,
			RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(ctx.get()).forAllStates(state -> {
			int y = 0;

			if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
				y = (int) state.getValue(BlockStateProperties.FACING).toYRot();
			} else if (state.hasProperty(BlockStateProperties.FACING)) {
				y = (int) state.getValue(BlockStateProperties.FACING).toYRot();
			}

			return new ConfiguredModel[]{new ConfiguredModel(
					provider.models().getExistingFile(new ResourceLocation(TTSMod.MODID, "block/" + path)), 0, y,
					false)};
		});
	}

	public static <T extends Block> void air(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(ctx.get()).forAllStates(state -> new ConfiguredModel[]{
				new ConfiguredModel(provider.models().getExistingFile(new ResourceLocation("block/air")))});
	}

	public static <T extends Block> void slab(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(ctx.get()).forAllStates(state -> {
			SlabType TYPE = state.getValue(BlockStateProperties.SLAB_TYPE);

			return switch (TYPE) {
				case TOP ->
					new ConfiguredModel[]{new ConfiguredModel(provider.models().slab("block/" + ctx.getName() + "_top",
							new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_side"),
							new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_bottom"),
							new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_top")))};

				case BOTTOM -> new ConfiguredModel[]{
						new ConfiguredModel(provider.models().slab("block/" + ctx.getName() + "_bottom",
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_side"),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_bottom"),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_top")))};

				default -> new ConfiguredModel[]{
						new ConfiguredModel(provider.models().slab("block/" + ctx.getName().replace("slab", ""),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_side"),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_bottom"),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName() + "_top")))};
			};
		});
	}

	public static <T extends Block> void simpleSlab(DataGenContext<Block, T> ctx,
			RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(ctx.get()).forAllStates(state -> {
			SlabType TYPE = state.getValue(BlockStateProperties.SLAB_TYPE);

			return switch (TYPE) {
				case TOP ->
					new ConfiguredModel[]{new ConfiguredModel(provider.models().slab("block/" + ctx.getName() + "_top",
							new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName()),
							new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName()),
							new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName())))};

				case BOTTOM -> new ConfiguredModel[]{
						new ConfiguredModel(provider.models().slab("block/" + ctx.getName() + "_bottom",
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName()),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName()),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName())))};

				default -> new ConfiguredModel[]{
						new ConfiguredModel(provider.models().slab("block/" + ctx.getName().replace("slab", ""),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName()),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName()),
								new ResourceLocation(TTSMod.MODID, "block/" + ctx.getName())))};
			};
		});
	}

	public static <T extends Block> void simpleTrapdoor(DataGenContext<Block, T> ctx,
			RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(ctx.get()).forAllStates(state -> {

			Boolean OPEN = state.getValue(BlockStateProperties.OPEN);

			Half HALF = state.getValue(BlockStateProperties.HALF);

			Direction FACING = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

			return trapdoor(OPEN, HALF, FACING, ctx.getName());
		});
	}

	public static <T extends Block> void controlPanel(DataGenContext<Block, T> ctx,
			RegistrateBlockstateProvider provider) {
		provider.getVariantBuilder(ctx.get()).forAllStates(state -> {

			int PRESSED = state.getValue(ChameleonCircuitPanel.PRESSED_BUTTON);

			Direction FACING = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

			return controlPanel(FACING, PRESSED, ctx.getName());
		});
	}

	public static ConfiguredModel[] trapdoor(Boolean OPEN, Half HALF, Direction FACING, String Name) {

		String modelPath;
		if (OPEN) {
			modelPath = MODID + ":block/" + Name + "_open";
		} else if (HALF == Half.TOP) {
			modelPath = MODID + ":block/" + Name + "_top";
		} else {
			modelPath = MODID + ":block/" + Name + "_bottom";
		}

		int x = 0;
		int y = 0;

		switch (FACING) {
			case NORTH -> {
				// north / top / open
				if (HALF == Half.TOP && OPEN) {
					x = 180;
					y = 180;
				}
			}
			case EAST -> {
				y = 90;
				if (HALF == Half.TOP && OPEN) {
					x = 180;
					y = 270;
				}
			}
			case SOUTH -> {
				y = 180;
				if (HALF == Half.TOP && OPEN) {
					x = 180;
					y = 0;
				}
			}
			case WEST -> {
				y = 270;
				if (HALF == Half.TOP && OPEN) {
					x = 180;
					y = 90;
				}
			}
		}

		return new ConfiguredModel[]{new ConfiguredModel(new ModelFile.UncheckedModelFile(modelPath), x, y, false)};
	}

	public static ConfiguredModel[] controlPanel(Direction FACING, int PRESSED, String blockName) {
		String modelPath;
		switch (PRESSED) {
			case 0 -> modelPath = "tts:block/control/" + blockName + "_0";
			case 1 -> modelPath = "tts:block/control/" + blockName + "_1";
			case 2 -> modelPath = "tts:block/control/" + blockName + "_2";
			case 3 -> modelPath = "tts:block/control/" + blockName + "_3";
			case 4 -> modelPath = "tts:block/control/" + blockName + "_4";
			case 5 -> modelPath = "tts:block/control/" + blockName + "_5";
			case 6 -> modelPath = "tts:block/control/" + blockName + "_6";
			case 7 -> modelPath = "tts:block/control/" + blockName + "_7";
			case 8 -> modelPath = "tts:block/control/" + blockName + "_8";
			case 9 -> modelPath = "tts:block/control/" + blockName + "_9";
			case 10 -> modelPath = "tts:block/control/" + blockName + "_10";
			case 11 -> modelPath = "tts:block/control/" + blockName + "_11";
			case 12 -> modelPath = "tts:block/control/" + blockName + "_12";
			case 13 -> modelPath = "tts:block/control/" + blockName + "_13";
			case 14 -> modelPath = "tts:block/control/" + blockName + "_14";
			case 15 -> modelPath = "tts:block/control/" + blockName + "_15";
			case 16 -> modelPath = "tts:block/control/" + blockName + "_16";
			case 17 -> modelPath = "tts:block/control/" + blockName + "_17";
			case 18 -> modelPath = "tts:block/control/" + blockName + "_18";
			case 19 -> modelPath = "tts:block/control/" + blockName + "_19";
			case 20 -> modelPath = "tts:block/control/" + blockName + "_20";
			case 21 -> modelPath = "tts:block/control/" + blockName + "_21";
			case 22 -> modelPath = "tts:block/control/" + blockName + "_22";
			case 23 -> modelPath = "tts:block/control/" + blockName + "_23";
			case 24 -> modelPath = "tts:block/control/" + blockName + "_24";
			case 25 -> modelPath = "tts:block/control/" + blockName + "_25";
			case 26 -> modelPath = "tts:block/control/" + blockName + "_26";
			case 27 -> modelPath = "tts:block/control/" + blockName + "_27";
			case 28 -> modelPath = "tts:block/control/" + blockName + "_28";
			case 29 -> modelPath = "tts:block/control/" + blockName + "_29";
			case 30 -> modelPath = "tts:block/control/" + blockName + "_30";
			case 31 -> modelPath = "tts:block/control/" + blockName + "_31";
			case 32 -> modelPath = "tts:block/control/" + blockName + "_32";
			case 33 -> modelPath = "tts:block/control/" + blockName + "_33";
			case 34 -> modelPath = "tts:block/control/" + blockName + "_34";
			case 35 -> modelPath = "tts:block/control/" + blockName + "_35";
			case 36 -> modelPath = "tts:block/control/" + blockName + "_36";
			case 37 -> modelPath = "tts:block/control/" + blockName + "_37";
			case 38 -> modelPath = "tts:block/control/" + blockName + "_38";
			case 39 -> modelPath = "tts:block/control/" + blockName + "_39";
			case 40 -> modelPath = "tts:block/control/" + blockName + "_40";
			case 41 -> modelPath = "tts:block/control/" + blockName + "_41";
			case 42 -> modelPath = "tts:block/control/" + blockName + "_42";
			case 43 -> modelPath = "tts:block/control/" + blockName + "_43";
			case 44 -> modelPath = "tts:block/control/" + blockName + "_44";
			case 45 -> modelPath = "tts:block/control/" + blockName + "_45";
			case 46 -> modelPath = "tts:block/control/" + blockName + "_46";
			case 47 -> modelPath = "tts:block/control/" + blockName + "_47";
			case 48 -> modelPath = "tts:block/control/" + blockName + "_48";
			case 49 -> modelPath = "tts:block/control/" + blockName + "_49";
			case 50 -> modelPath = "tts:block/control/" + blockName + "_50";

			default -> throw new IllegalArgumentException("Oops: " + PRESSED);
		}

		int y = switch (FACING) {
			case SOUTH -> 180;
			case WEST -> 270;
			case EAST -> 90;
			default -> 0;
		};

		System.gc();

		return new ConfiguredModel[]{new ConfiguredModel(new ModelFile.UncheckedModelFile(modelPath), 0, y, false)};
	}

}
