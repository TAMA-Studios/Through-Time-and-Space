/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.registries.TTSItems.DIMENSIONAL_ITEMS;
import static com.code.tama.tts.server.registries.TTSItems.ITEMS;

import com.code.tama.tts.server.blocks.*;
import com.code.tama.tts.server.blocks.Panels.*;
import com.code.tama.tts.server.blocks.core.ConsoleBlock;
import com.code.tama.tts.server.blocks.core.ModFlammableRotatedPillarBlock;
import com.code.tama.tts.server.blocks.core.RotorBlock;
import com.code.tama.tts.server.blocks.core.WeatheringSteel;
import com.code.tama.tts.server.blocks.monitor.CRTMonitorBlock;
import com.code.tama.tts.server.blocks.monitor.MonitorBlock;
import com.code.tama.tts.server.blocks.monitor.MonitorPanel;
import com.code.tama.tts.server.blocks.subsystems.DematerializationCircuitCoreBlock;
import com.code.tama.tts.server.blocks.subsystems.NetherReactorCoreBlock;
import com.code.tama.tts.server.items.tabs.DimensionalTab;
import com.code.tama.tts.server.items.tabs.MainTab;
import com.code.tama.tts.server.items.tabs.Roundel;
import com.code.tama.tts.server.tileentities.HudolinConsoleTile;
import com.code.tama.tts.server.tileentities.NESSConsoleTile;
import com.code.tama.tts.server.worlds.tree.GallifreyanOakTreeGrower;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TTSBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);

    //                         Set this \/ to your block class
    //                        \/ this too
    public static RegistryObject<ExampleTileBlock> EXAMPLE_TILE_BLOCK =
            RegisterWithItemSpecial("example_tile_block", () -> new ExampleTileBlock(BlockBehaviour.Properties.of()));

    // This uses a custom BlockItem so we just do BLOCKS.register()
    public static RegistryObject<CompressedMultiblockBlock> COMPRESSED_MULTIBLOCK = BLOCKS.register(
            "compressed_multiblock_block", () -> new CompressedMultiblockBlock(BlockBehaviour.Properties.of()));

    @MainTab
    public static RegistryObject<SonicConfiguratorBlock> SONIC_CONFIGURATOR_BLOCK = RegisterWithItemSpecial(
            "sonic_configurator", () -> new SonicConfiguratorBlock(BlockBehaviour.Properties.of()));

    @MainTab
    public static RegistryObject<Block> OBJ_TARDIS = RegisterWithItemSpecial(
            "obj_tardis", () -> new Block(BlockBehaviour.Properties.of().noOcclusion()));

    @MainTab
    public static final RegistryObject<Block> SKY_BLOCK = RegisterWithItemSpecial("sky_block", SkyBlock::new);

    @MainTab
    public static final RegistryObject<Block> VOID_BLOCK =
            RegisterWithItemSpecial("void_block", SkyBlock.VoidBlock::new);

    @Roundel
    public static final RegistryObject<Block> QUARTZ_ROUNDEL = SetupRoundel("quartz_block");

    @Roundel
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_ROUNDEL = SetupRoundel("polished_blackstone");

    @Roundel
    public static final RegistryObject<Block> CHROMIUM_ROUNDEL = SetupRoundel("chromium_block");

    @MainTab
    public static final RegistryObject<Block> CHROMIUM_BLOCK = RegisterWithItem(
            "chromium_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));

    @MainTab
    public static final RegistryObject<Block> STRUCTURAL_STEEL = RegisterWithItem(
            "structural_steel",
            () -> new StructuralSteelBlock(
                    WeatheringSteel.WeatherState.UNAFFECTED,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_GRAY)
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)));

    @MainTab
    public static final RegistryObject<Block> STRUCTURAL_STEEL_WEATHERED = RegisterWithItem(
            "structural_steel_weathered",
            () -> new StructuralSteelBlock(
                    WeatheringSteel.WeatherState.WEATHERED,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_GRAY)
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)));

    @MainTab
    public static final RegistryObject<Block> STRUCTURAL_STEEL_RUSTED = RegisterWithItem(
            "structural_steel_rusted",
            () -> new StructuralSteelBlock(
                    WeatheringSteel.WeatherState.RUSTED,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_GRAY)
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)));

    @MainTab
    public static final RegistryObject<Block> BRUSHED_STEEL = RegisterWithItem(
            "brushed_steel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));

    @MainTab
    public static final RegistryObject<Block> CARBON_STEEL_LADDER = RegisterWithItem(
            "carbon_steel_ladder",
            () -> new LadderBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)
                    .forceSolidOff()
                    .noOcclusion()));

    @MainTab
    public static final RegistryObject<Block> CARBON_STEEL = RegisterWithItem(
            "carbon_steel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));

    @MainTab
    public static final RegistryObject<Block> CARBON_STEEL_GRATE_SLAB = RegisterWithItem(
            "carbon_steel_grate_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    @MainTab
    public static final RegistryObject<Block> CARBON_STEEL_GRATE = RegisterWithItem(
            "carbon_steel_grate",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    @MainTab
    public static final RegistryObject<Block> CARBON_STEEL_SLAB = RegisterWithItem(
            "carbon_steel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    @MainTab
    public static final RegistryObject<Block> CARBON_STEEL_TRAPDOOR = RegisterWithItem(
            "carbon_steel_trapdoor",
            () -> new TrapDoorBlock(
                    BlockBehaviour.Properties.of()
                            .noOcclusion()
                            .mapColor(MapColor.COLOR_LIGHT_GRAY)
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL),
                    BlockSetType.POLISHED_BLACKSTONE));

    @MainTab
    public static RegistryObject<HartnellRotor> HARTNELL_ROTOR = RegisterWithItemSpecial(
            "hartnell_rotor", () -> new HartnellRotor(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    @MainTab
    public static RegistryObject<Block> HARD_LIGHT = RegisterWithItem(
            "hard_light",
            () -> new HardLightBlock(
                    BlockBehaviour.Properties.copy(Blocks.GLASS).lightLevel(BlockState -> 8)));

    @MainTab
    public static RegistryObject<ExteriorBlock> EXTERIOR_BLOCK = BLOCKS.register(
            "exterior_block",
            () -> new ExteriorBlock(BlockBehaviour.Properties.of().noOcclusion(), TTSTileEntities.EXTERIOR_TILE));

    @MainTab
    public static RegistryObject<ConsoleBlock<HudolinConsoleTile>> HUDOLIN_CONSOLE_BLOCK = BLOCKS.register(
            "hudolin_console_block",
            () -> new ConsoleBlock<>(
                    BlockBehaviour.Properties.of().noOcclusion(), TTSTileEntities.HUDOLIN_CONSOLE_TILE));

    public static RegistryObject<ConsoleBlock<NESSConsoleTile>> NESS_CONSOLE_BLOCK = BLOCKS.register(
            "ness_console_block",
            () -> new ConsoleBlock<>(BlockBehaviour.Properties.of().noOcclusion(), TTSTileEntities.NESS_CONSOLE_TILE));

    public static RegistryObject<ChameleonCircuitPanel> CHAMELEON_CIRCUIT_BLOCK = RegisterWithItemSpecial(
            "chameleon_circuit_panel",
            () -> new ChameleonCircuitPanel(
                    BlockBehaviour.Properties.of().noOcclusion(), TTSTileEntities.CHAMELEON_CIRCUIT_PANEL));

    public static RegistryObject<com.code.tama.tts.server.blocks.DoorBlock> DOOR_BLOCK = RegisterWithItemSpecial(
            "door_block",
            () -> new com.code.tama.tts.server.blocks.DoorBlock(
                    BlockBehaviour.Properties.of().noOcclusion(), TTSTileEntities.DOOR_TILE));

    public static final RegistryObject<Block> MONITOR_BLOCK = RegisterWithItem(
            "monitor_block",
            () -> new MonitorBlock(
                    BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> CRT_MONITOR_BLOCK = RegisterWithItem(
            "crt_monitor_block", () -> new CRTMonitorBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA)));

    public static final RegistryObject<Block> MONITOR_PANEL = RegisterWithItem(
            "monitor_panel",
            () -> new MonitorPanel(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> COORDINATE_PANEL = RegisterWithItem(
            "coordinate_panel",
            () -> new CoordinatePanelBlock(
                    BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> BLUE_ROTOR = RegisterWithItem(
            "rotor/blue",
            () -> new RotorBlock(BlockBehaviour.Properties.of()
                    .strength(1.25f)
                    .sound(SoundType.GLASS)
                    .noOcclusion()));

    public static final RegistryObject<Block> AMETHYST_ROTOR = RegisterWithItem(
            "rotor/amethyst",
            () -> new RotorBlock(BlockBehaviour.Properties.of()
                    .strength(1.25f)
                    .sound(SoundType.GLASS)
                    .noOcclusion()));

    public static final RegistryObject<Block> COPPER_ROTOR = RegisterWithItem(
            "rotor/copper",
            () -> new RotorBlock(BlockBehaviour.Properties.of()
                    .strength(1.25f)
                    .sound(SoundType.GLASS)
                    .noOcclusion()));

    public static final RegistryObject<Block> LIGHT_PANEL = RegisterWithItem(
            "light_panel",
            () -> new LightPanel(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ARS_PANEL = RegisterWithItem(
            "ars_panel",
            () -> new ARSPanel(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> THROTTLE = RegisterWithItem(
            "throttle",
            () -> new ThrottleBlock(
                    BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> TOYOTA_THROTTLE = RegisterWithItem(
            "toyota_throttle",
            () -> new ToyotaThrottleBlock(
                    BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> POWER_LEVER = RegisterWithItem(
            "power_lever",
            () -> new PowerLever(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> DESTINATION_INFO_PANEL = RegisterWithItem(
            "destination_info_panel",
            () -> new DestinationInfoBlock(
                    BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<HartnellDoor> HARTNELL_DOOR =
            RegisterWithItemSpecial("hartnell_door", () -> new HartnellDoor(TTSTileEntities.HARTNELL_DOOR));

    public static final RegistryObject<WorkbenchBlock> TEMPORAL_FABRICATOR =
            RegisterWithItemSpecial("temporal_fabricator", WorkbenchBlock::new);

    public static final RegistryObject<Block> FRAGMENT_LINKS = RegisterWithItem(
            "fragment_links", () -> new FragmentLinksBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_WIRE)));

    public static final RegistryObject<Block> DEMATERIALIZATION_CIRCUIT_CORE =
            RegisterWithItem("dematerialization_circuit_core", DematerializationCircuitCoreBlock::new);

    public static final RegistryObject<Block> NETHER_REACTOR_CORE = RegisterWithItem(
            "nether_reactor_core",
            () -> new NetherReactorCoreBlock(
                    BlockBehaviour.Properties.of().strength(1.5f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> TARDIS_ENGINE_INTERFACE = RegisterWithItem(
            "tardis_engine_interface",
            () -> new EngineInterfaceBlock(
                    BlockBehaviour.Properties.of().strength(1.5f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> TARDIS_ENGINES = RegisterWithItem(
            "tardis_engines",
            () -> new EnginesBlock(BlockBehaviour.Properties.of().strength(1.5f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> PORTAL_BLOCK = BLOCKS.register(
            "portal_block",
            () -> new PortalBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(5.0F)
                    .noOcclusion()
                    .lightLevel(state -> 10)));

    /**
     * Gallifrey Blocks
     **/
    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_LOG = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_log",
            () -> new ModFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_SAND = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_sand",
            () -> new SandBlock(10634503, BlockBehaviour.Properties.of()),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_WOOD = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_wood",
            () -> new ModFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> STRIPPED_GALLIFREYAN_OAK_LOG = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_log_stripped",
            () -> new ModFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> STRIPPED_GALLIFREYAN_OAK_WOOD = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_wood_stripped",
            () -> new ModFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_PLANKS = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            },
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_LEAVES = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            },
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_SAPLING = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_sapling",
            () -> new SaplingBlock(new GallifreyanOakTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_STAIRS = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_stairs",
            () -> new StairBlock(
                    () -> TTSBlocks.ZEITON_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_SLAB = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_slab",
            () -> new SlabBlock(
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_BUTTON = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_button",
            () -> new ButtonBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.WOOD),
                    BlockSetType.IRON,
                    10,
                    true),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_PRESSURE_PLATE = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_pressure_plate",
            () -> new PressurePlateBlock(
                    PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD),
                    BlockSetType.IRON),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_FENCE = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_fence",
            () -> new FenceBlock(
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_FENCE_GATE = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_fence_gate",
            () -> new FenceGateBlock(
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD),
                    SoundEvents.CHAIN_PLACE,
                    SoundEvents.ANVIL_BREAK));

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_WALL = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_wall",
            () -> new WallBlock(
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD)),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_DOOR = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_door",
            () -> new net.minecraft.world.level.block.DoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                            .sound(SoundType.WOOD)
                            .noOcclusion(),
                    BlockSetType.IRON),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> GALLIFREYAN_OAK_TRAPDOOR = RegisterWithItem(
            "dimensional/gallifreyan/gallifreyan_oak_trapdoor",
            () -> new TrapDoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                            .sound(SoundType.WOOD)
                            .noOcclusion(),
                    BlockSetType.IRON),
            DIMENSIONAL_ITEMS,
            BLOCKS);

    @DimensionalTab
    public static final RegistryObject<Block> VAROS_ROCKS = RegisterWithItem(
            "dimensional/varos/rocks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)),
            DIMENSIONAL_ITEMS,
            BLOCKS);
    /**
     * Ores
     **/
    public static final RegistryObject<Block> ZEITON_BLOCK = RegisterWithItem(
            "zeiton/zeiton_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> RAW_ZEITON_BLOCK = RegisterWithItem(
            "zeiton/raw_zeiton_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ZEITON_ORE = RegisterWithItem(
            "zeiton/zeiton_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 6)));

    public static final RegistryObject<Block> DEEPSLATE_ZEITON_ORE = RegisterWithItem(
            "zeiton/deepslate_zeiton_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                            .strength(3f)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));

    public static final RegistryObject<Block> NETHER_ZEITON_ORE = RegisterWithItem(
            "zeiton/nether_zeiton_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.NETHERRACK)
                            .strength(1f)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));

    public static final RegistryObject<Block> END_STONE_ZEITON_ORE = RegisterWithItem(
            "zeiton/end_stone_zeiton_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.END_STONE)
                            .strength(5f)
                            .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));

    public static final RegistryObject<HartnellDoorMultiBlock> HARTNELL_DOOR_PLACEHOLDER = RegisterWithItemSpecial(
            "hartnell_door_placeholder", () -> new HartnellDoorMultiBlock(TTSTileEntities.HARTNELL_DOOR_PLACEHOLDER));

    /**
     * For registering "Special" blocks, like an {@link ExteriorBlock}
     **/
    public static <T extends Block> RegistryObject<T> RegisterWithItemSpecial(String name, final Supplier<T> block) {

        final RegistryObject<T> reg = BLOCKS.register(name, block);

        ITEMS.register(name, () -> new BlockItem(reg.get(), new Item.Properties()));

        return reg;
    }

    /**
     * Automatically handles registering the block and item for you, all you do is pass in the name
     **/
    public static RegistryObject<Block> SetupBlock(String name) {
        return RegisterWithItem(
                name, () -> new Block(BlockBehaviour.Properties.of().strength(1.25f)));
    }

    /**
     * Registers the block and a {@link BlockItem}
     **/
    public static RegistryObject<Block> SetupRoundel(String name) {
        return RegisterWithItem(
                "roundel/" + name,
                () -> new Block(BlockBehaviour.Properties.of().strength(1.25f).lightLevel(BlockState -> 15)),
                new Item.Properties());
    }

    /**
     * Registers the block and a {@link BlockItem}
     **/
    public static RegistryObject<Block> RegisterWithItem(String name, Supplier<Block> supplier) {
        return RegisterWithItem(name, supplier, new Item.Properties());
    }

    /**
     * Registers the block and a {@link BlockItem} to a specific registry (for tab compat)
     **/
    public static RegistryObject<Block> RegisterWithItem(
            String name, Supplier<Block> supplier, DeferredRegister<Item> reg, DeferredRegister<Block> reg1) {
        RegistryObject<Block> block = reg1.register(name, supplier);
        reg.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    /**
     * Registers the block and a {@link BlockItem} with custom {@link Item.Properties}
     **/
    public static RegistryObject<Block> RegisterWithItem(
            String name, Supplier<Block> supplier, Item.Properties itemProperties) {
        RegistryObject<Block> block = Register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), itemProperties));
        return block;
    }

    /**
     * Registers a block
     **/
    public static RegistryObject<Block> Register(String name, Supplier<Block> supplier) {
        return BLOCKS.register(name, supplier);
    }
}
