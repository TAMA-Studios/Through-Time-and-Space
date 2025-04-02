package com.code.tama.mtm.server;

import com.code.tama.mtm.server.blocks.*;
import com.code.tama.mtm.server.tardis.controls.Panels.*;
import com.code.tama.mtm.server.worlds.tree.GallifreyanOakTreeGrower;
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

import java.util.function.Supplier;

import static com.code.tama.mtm.MTMMod.MODID;
import static com.code.tama.mtm.server.MTMItems.DIMENSIONAL_ITEMS;
import static com.code.tama.mtm.server.MTMItems.ITEMS;

public class MTMBlocks {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);

    public static RegistryObject<ExteriorBlock> EXTERIOR_BLOCK = BLOCKS.register("exterior_block", () -> new ExteriorBlock(BlockBehaviour.Properties.of().noOcclusion(), MTMTileEntities.EXTERIOR_TILE));

    public static RegistryObject<ConsoleBlock> HUDOLIN_CONSOLE_BLOCK = BLOCKS.register("hudolin_console_block", () -> new ConsoleBlock(BlockBehaviour.Properties.of().noOcclusion(), MTMTileEntities.HUDOLIN_CONSOLE_TILE));

    public static RegistryObject<ChameleonCircuitPanel> CHAMELEON_CIRCUIT_BLOCK = RegisterWithItemSpecial("chameleon_circuit_panel", () -> new ChameleonCircuitPanel(BlockBehaviour.Properties.of().noOcclusion(), MTMTileEntities.CHAMELEON_CIRCUIT_PANEL));

    public static RegistryObject<com.code.tama.mtm.server.blocks.DoorBlock> DOOR_BLOCK = RegisterWithItemSpecial("door_block", () -> new com.code.tama.mtm.server.blocks.DoorBlock(BlockBehaviour.Properties.of().noOcclusion(), MTMTileEntities.DOOR_TILE));

    public static final RegistryObject<Block> MONITOR_BLOCK = RegisterWithItem("monitor_block",
            () -> new MonitorBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> COORDINATE_PANEL = RegisterWithItem("coordinate_panel",
            () -> new CoordinatePanelBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> BLUE_ROTOR = RegisterWithItem("rotor/blue",
            () -> new RotorBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> AMETHYST_ROTOR = RegisterWithItem("rotor/amethyst",
            () -> new RotorBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> COPPER_ROTOR = RegisterWithItem("rotor/copper",
            () -> new RotorBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> LIGHT_PANEL = RegisterWithItem("light_panel",
            () -> new LightPanel(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> THROTTLE = RegisterWithItem("throttle",
            () -> new ThrottleBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> TOYOTA_THROTTLE = RegisterWithItem("toyota_throttle",
            () -> new ToyotaThrottleBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> POWER_LEVER = RegisterWithItem("power_lever",
            () -> new PowerLever(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> DESTINATION_INFO_PANEL = RegisterWithItem("destination_info_panel",
            () -> new DestinationInfoBlock(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.STONE)));

    public static final RegistryObject<HartnellDoor> HARTNELL_DOOR = RegisterWithItemSpecial("hartnell_door", () -> new HartnellDoor(
            MTMTileEntities.HARTNELL_DOOR));

    public static final RegistryObject<Block> PORTAL_BLOCK = BLOCKS.register("portal_block",
            () -> new PortalBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(5.0F)
                    .noOcclusion()
                    .lightLevel(state -> 10))
    );

    /**
     * Gallifrey Blocks
     **/

    public static final RegistryObject<Block> GALLIFREYAN_OAK_LOG = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_SAND = RegisterWithItem("dimensional/gallifreyan/gallifreyan_sand",
            () -> new SandBlock(10634503, BlockBehaviour.Properties.of()), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_WOOD = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> STRIPPED_GALLIFREYAN_OAK_LOG = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_log_stripped",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> STRIPPED_GALLIFREYAN_OAK_WOOD = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_wood_stripped",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_PLANKS = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_planks",
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
            }, DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_LEAVES = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_leaves",
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
            }, DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_SAPLING = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_sapling",
            () -> new SaplingBlock(new GallifreyanOakTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), DIMENSIONAL_ITEMS, BLOCKS);


    public static final RegistryObject<Block> GALLIFREYAN_OAK_STAIRS = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_stairs",
            () -> new StairBlock(() -> MTMBlocks.ZEITON_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_SLAB = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_BUTTON = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.WOOD),
                    BlockSetType.IRON, 10, true), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_PRESSURE_PLATE = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD),
                    BlockSetType.IRON), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_FENCE = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_FENCE_GATE = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD), SoundEvents.CHAIN_PLACE, SoundEvents.ANVIL_BREAK));

    public static final RegistryObject<Block> GALLIFREYAN_OAK_WALL = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD)), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_DOOR = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_door",
            () -> new net.minecraft.world.level.block.DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).noOcclusion(), BlockSetType.IRON), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> GALLIFREYAN_OAK_TRAPDOOR = RegisterWithItem("dimensional/gallifreyan/gallifreyan_oak_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.WOOD).noOcclusion(), BlockSetType.IRON), DIMENSIONAL_ITEMS, BLOCKS);

    public static final RegistryObject<Block> VAROS_ROCKS = RegisterWithItem("dimensional/varos/rocks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)), DIMENSIONAL_ITEMS, BLOCKS);
    /**
     * Ores
     **/

    public static final RegistryObject<Block> ZEITON_BLOCK = RegisterWithItem("zeiton/zeiton_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> RAW_ZEITON_BLOCK = RegisterWithItem("zeiton/raw_zeiton_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ZEITON_ORE = RegisterWithItem("zeiton/zeiton_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));

    public static final RegistryObject<Block> DEEPSLATE_ZEITON_ORE = RegisterWithItem("zeiton/deepslate_zeiton_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> NETHER_ZEITON_ORE = RegisterWithItem("zeiton/nether_zeiton_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)
                    .strength(1f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistryObject<Block> END_STONE_ZEITON_ORE = RegisterWithItem("zeiton/end_stone_zeiton_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));


    public static final RegistryObject<HartnellDoorMultiBlock> HARTNELL_DOOR_PLACEHOLDER = RegisterWithItemSpecial("hartnell_door_placeholder", () -> new HartnellDoorMultiBlock(MTMTileEntities.HARTNELL_DOOR_PLACEHOLDER));

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
        return RegisterWithItem(name, () -> new Block(BlockBehaviour.Properties.of().strength(1.25f)));
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

    public static RegistryObject<Block> RegisterWithItem(String name, Supplier<Block> supplier, DeferredRegister<Item> reg, DeferredRegister<Block> reg1) {
        RegistryObject<Block> block = reg1.register(name, supplier);
        reg.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    /**
     * Registers the block and a {@link BlockItem} with custom {@link Item.Properties}
     **/

    public static RegistryObject<Block> RegisterWithItem(String name, Supplier<Block> supplier, Item.Properties itemProperties) {
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