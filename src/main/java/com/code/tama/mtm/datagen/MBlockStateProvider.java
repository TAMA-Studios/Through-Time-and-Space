package com.code.tama.mtm.datagen;

import com.code.tama.mtm.MTMMod;
import com.code.tama.mtm.server.MTMBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.MTMMod.MODID;

public class MBlockStateProvider extends BlockStateProvider {
    public MBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(MTMBlocks.ZEITON_BLOCK);
        blockWithItem(MTMBlocks.RAW_ZEITON_BLOCK);

        blockWithItem(MTMBlocks.AMETHYST_ROTOR);
        blockWithItem(MTMBlocks.COPPER_ROTOR);
        blockWithItem(MTMBlocks.BLUE_ROTOR);

        blockWithItem(MTMBlocks.ZEITON_ORE);
        blockWithItem(MTMBlocks.DEEPSLATE_ZEITON_ORE);
        blockWithItem(MTMBlocks.END_STONE_ZEITON_ORE);
        blockWithItem(MTMBlocks.NETHER_ZEITON_ORE);

        stairsBlock(((StairBlock) MTMBlocks.GALLIFREYAN_OAK_STAIRS.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()));
        slabBlock(((SlabBlock) MTMBlocks.GALLIFREYAN_OAK_SLAB.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()));

        buttonBlock(((ButtonBlock) MTMBlocks.GALLIFREYAN_OAK_BUTTON.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()));
        pressurePlateBlock(((PressurePlateBlock) MTMBlocks.GALLIFREYAN_OAK_PRESSURE_PLATE.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()));

        fenceBlock(((FenceBlock) MTMBlocks.GALLIFREYAN_OAK_FENCE.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()));
        fenceGateBlock(((FenceGateBlock) MTMBlocks.GALLIFREYAN_OAK_FENCE_GATE.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()));
        wallBlock(((WallBlock) MTMBlocks.GALLIFREYAN_OAK_WALL.get()), blockTexture(MTMBlocks.ZEITON_BLOCK.get()));

        doorBlockWithRenderType(((DoorBlock) MTMBlocks.GALLIFREYAN_OAK_DOOR.get()), modLoc("block/dimensional/gallifreyan/gallifreyan_oak_door_bottom"), modLoc("block/dimensional/gallifreyan/gallifreyan_oak_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) MTMBlocks.GALLIFREYAN_OAK_TRAPDOOR.get()), modLoc("block/dimensional/gallifreyan/gallifreyan_oak_trapdoor"), true, "cutout");

//        makeStrawberryCrop((CropBlock) MBlocks.STRAWBERRY_CROP.get(), "strawberry_stage", "strawberry_stage");
//        makeCornCrop(((CropBlock) MBlocks.CORN_CROP.get()), "corn_stage_", "corn_stage_");

//        simpleBlockWithItem(MBlocks.CATMINT.get(), models().cross(blockTexture(MBlocks.CATMINT.get()).getPath(),
//                blockTexture(MBlocks.CATMINT.get())).renderType("cutout"));
//        simpleBlockWithItem(MBlocks.POTTED_CATMINT.get(), models().singleTexture("potted_catmint", new ResourceLocation("flower_pot_cross"), "plant",
//                blockTexture(MBlocks.CATMINT.get())).renderType("cutout"));

//        simpleBlockWithItem(MBlocks.GEM_POLISHING_STATION.get(),
//                new ModelFile.UncheckedModelFile(modLoc("block/gem_polishing_station")));

        logBlock(((RotatedPillarBlock) MTMBlocks.GALLIFREYAN_OAK_LOG.get()));
        axisBlock(((RotatedPillarBlock) MTMBlocks.GALLIFREYAN_OAK_WOOD.get()), blockTexture(MTMBlocks.GALLIFREYAN_OAK_LOG.get()), blockTexture(MTMBlocks.GALLIFREYAN_OAK_LOG.get()));

        axisBlock(((RotatedPillarBlock) MTMBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get()), blockTexture(MTMBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get()),
                new ResourceLocation(MODID, "block/stripped_gallifreyan_oak_log_top"));

        axisBlock(((RotatedPillarBlock) MTMBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD.get()), blockTexture(MTMBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get()),
                blockTexture(MTMBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get()));

        blockWithItem(MTMBlocks.GALLIFREYAN_OAK_LOG);
        blockWithItem(MTMBlocks.GALLIFREYAN_OAK_WOOD);
        blockWithItem(MTMBlocks.STRIPPED_GALLIFREYAN_OAK_LOG);
        blockWithItem(MTMBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD);
        blockWithItem(MTMBlocks.GALLIFREYAN_SAND);
        blockWithItem(MTMBlocks.GALLIFREYAN_OAK_PLANKS);
        SpecialBlockItem(MTMBlocks.HUDOLIN_CONSOLE_BLOCK);
        leavesBlock(MTMBlocks.GALLIFREYAN_OAK_LEAVES);

//        signBlock(((StandingSignBlock) MBlocks.PINE_SIGN.get()), ((WallSignBlock) MBlocks.PINE_WALL_SIGN.get()),
//                blockTexture(MBlocks.PINE_PLANKS.get()));

//        hangingSignBlock(MBlocks.PINE_HANGING_SIGN.get(), MBlocks.PINE_WALL_HANGING_SIGN.get(), blockTexture(MBlocks.PINE_PLANKS.get()));
        saplingBlock(MTMBlocks.GALLIFREYAN_SAPLING);

//        blockWithItem(MBlocks.MOD_PORTAL);
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        try {
            simpleBlock(blockRegistryObject.get(),
                    models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void axisBlock(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end) {
        try {
            axisBlock(block,
                    models().cubeColumn(name(block), side, end),
                    models().cubeColumnHorizontal(name(block) + "_horizontal", side, end));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trapdoorBlockWithRenderType(TrapDoorBlock block, ResourceLocation texture, boolean orientable, String renderType) {
        try {
            String baseName = key(block).toString();
            ModelFile bottom = orientable ? models().trapdoorOrientableBottom(baseName + "_bottom", texture).renderType(renderType) : models().trapdoorBottom(baseName + "_bottom", texture).renderType(renderType);
            ModelFile top = orientable ? models().trapdoorOrientableTop(baseName + "_top", texture).renderType(renderType) : models().trapdoorTop(baseName + "_top", texture).renderType(renderType);
            ModelFile open = orientable ? models().trapdoorOrientableOpen(baseName + "_open", texture).renderType(renderType) : models().trapdoorOpen(baseName + "_open", texture).renderType(renderType);
            trapdoorBlock(block, bottom, top, open, orientable);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doorBlockWithRenderType(DoorBlock block, ResourceLocation bottom, ResourceLocation top, String renderType) {
        try {
            String baseName = key(block).toString();
            ModelFile bottomLeft = models().doorBottomLeft(baseName + "_bottom_left", bottom, top).renderType(renderType);
            ModelFile bottomLeftOpen = models().doorBottomLeftOpen(baseName + "_bottom_left_open", bottom, top).renderType(renderType);
            ModelFile bottomRight = models().doorBottomRight(baseName + "_bottom_right", bottom, top).renderType(renderType);
            ModelFile bottomRightOpen = models().doorBottomRightOpen(baseName + "_bottom_right_open", bottom, top).renderType(renderType);
            ModelFile topLeft = models().doorTopLeft(baseName + "_top_left", bottom, top).renderType(renderType);
            ModelFile topLeftOpen = models().doorTopLeftOpen(baseName + "_top_left_open", bottom, top).renderType(renderType);
            ModelFile topRight = models().doorTopRight(baseName + "_top_right", bottom, top).renderType(renderType);
            ModelFile topRightOpen = models().doorTopRightOpen(baseName + "_top_right_open", bottom, top).renderType(renderType);
            doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void wallBlock(WallBlock block, ResourceLocation texture) {
        try {
            wallBlock(block, models().wallPost(key(block).toString() + "_post", texture),
                    models().wallSide(key(block).toString() + "_side", texture),
                    models().wallSideTall(key(block).toString() + "_side_tall", texture));
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buttonBlock(ButtonBlock block, ResourceLocation texture) {
        try {
            ModelFile button = models().button(name(block), texture);
            ModelFile buttonPressed = models().buttonPressed(name(block) + "_pressed", texture);
            buttonBlock(block, button, buttonPressed);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fenceBlock(FenceBlock block, ResourceLocation texture) {
        try {
            String baseName = key(block).toString();
            fourWayBlock(block,
                    models().fencePost(baseName + "_post", texture),
                    models().fenceSide(baseName + "_side", texture));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
        try {
            ModelFile pressurePlate = models().pressurePlate(name(block), texture);
            ModelFile pressurePlateDown = models().pressurePlateDown(name(block) + "_down", texture);
            pressurePlateBlock(block, pressurePlate, pressurePlateDown);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stairsBlock(StairBlock block, ResourceLocation texture) {
        try {
            stairsBlock(block, texture, texture, texture);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void slabBlock(SlabBlock block, ResourceLocation doubleslab, ResourceLocation texture) {
        try {
            slabBlock(block, doubleslab, texture, texture, texture);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void SpecialBlockItem(RegistryObject<? extends Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

//    public void makeStrawberryCrop(CropBlock block, String modelName, String textureName) {
//        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);
//
//        getVariantBuilder(block).forAllStates(function);
//    }

//    private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
//        ConfiguredModel[] models = new ConfiguredModel[1];
//        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
//                new ResourceLocation(MODID, "block/" + textureName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()))).renderType("cutout"));
//
//        return models;
//    }

//    public void makeCornCrop(CropBlock block, String modelName, String textureName) {
//        Function<BlockState, ConfiguredModel[]> function = state -> cornStates(state, block, modelName, textureName);
//
//        getVariantBuilder(block).forAllStates(function);
//    }
//
//    private ConfiguredModel[] cornStates(BlockState state, CropBlock block, String modelName, String textureName) {
//        ConfiguredModel[] models = new ConfiguredModel[1];
//        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((CornCropBlock) block).getAgeProperty()),
//                new ResourceLocation(MODID, "block/" + textureName + state.getValue(((CornCropBlock) block).getAgeProperty()))).renderType("cutout"));
//
//        return models;
//    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        try {
            simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
        }
        catch (Exception e) {
            MTMMod.LOGGER.info(e.getMessage());
        }
    }
}