/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import com.code.tama.triggerapi.ReflectionBuddy;
import com.code.tama.tts.mixin.BlockBehaviourPropertiesAccessor;
import com.code.tama.tts.server.blocks.HardLightBlock;
import com.code.tama.tts.server.blocks.Panels.*;
import com.code.tama.tts.server.blocks.core.*;
import com.code.tama.tts.server.blocks.cosmetic.*;
import com.code.tama.tts.server.blocks.gadgets.CompressedMultiblockBlock;
import com.code.tama.tts.server.blocks.gadgets.SonicConfiguratorBlock;
import com.code.tama.tts.server.blocks.gadgets.WorkbenchBlock;
import com.code.tama.tts.server.blocks.monitor.CRTMonitorBlock;
import com.code.tama.tts.server.blocks.monitor.MonitorBlock;
import com.code.tama.tts.server.blocks.monitor.MonitorPanel;
import com.code.tama.tts.server.blocks.subsystems.DematerializationCircuitCoreBlock;
import com.code.tama.tts.server.blocks.subsystems.NetherReactorCoreBlock;
import com.code.tama.tts.server.blocks.tardis.*;
import com.code.tama.tts.server.blocks.tardis.DoorBlock;
import com.code.tama.tts.server.items.tabs.DimensionalTab;
import com.code.tama.tts.server.items.tabs.MainTab;
import com.code.tama.tts.server.items.tabs.Roundel;
import com.code.tama.tts.server.registries.TTSRegistrate;
import com.code.tama.tts.server.tileentities.HudolinConsoleTile;
import com.code.tama.tts.server.tileentities.NESSConsoleTile;
import com.code.tama.tts.server.worlds.tree.GallifreyanOakTreeGrower;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;

import static com.code.tama.tts.TTSMod.registrate;


@SuppressWarnings({"unused", "deprecation"})
public class TTSBlocks {
	// public static final DeferredRegister<Block> BLOCKS =
	// DeferredRegister.create(Registries.BLOCK, MODID);

	public static final BlockEntry<ExampleTileBlock> EXAMPLE_TILE_BLOCK = registrate().block("example_tile_block",
			ExampleTileBlock::new).simpleItem().defaultBlockstate().register();

	// This uses a custom BlockItem so we just do registrate().block() here to not make any item
	public static final BlockEntry<CompressedMultiblockBlock> COMPRESSED_MULTIBLOCK = registrate()
			.block("compressed_multiblock_block", CompressedMultiblockBlock::new).simpleItem().defaultBlockstate().register();

	@MainTab
	public static final BlockEntry<SonicConfiguratorBlock> SONIC_CONFIGURATOR_BLOCK = Builder(
			"sonic_configurator", SonicConfiguratorBlock::new).simpleItem().defaultBlockstate().register();

	@MainTab
	public static final BlockEntry<SkyBlock> SKY_BLOCK = Builder("sky_block", SkyBlock::new).simpleItem().defaultBlockstate().register();

	@MainTab
	public static final BlockEntry<SkyBlock.VoidBlock> VOID_BLOCK = Builder("void_block", SkyBlock.VoidBlock::new).simpleItem().defaultBlockstate().register();

	@Roundel
	public static final BlockEntry<Block> QUARTZ_ROUNDEL = SetupRoundel("quartz_block");

	@Roundel
	public static final BlockEntry<Block> POLISHED_BLACKSTONE_ROUNDEL = SetupRoundel("polished_blackstone");

	@Roundel
	public static final BlockEntry<Block> CHROMIUM_ROUNDEL = SetupRoundel("chromium_block");

	@MainTab
	public static final BlockEntry<Block> CHROMIUM_BLOCK = registrate().block("chromium_block", Block::new).properties((properties) -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(5.0F, 6.0F).sound(SoundType.METAL)).simpleItem().defaultBlockstate().register();

	@MainTab
	public static final BlockEntry<StructuralSteelBlock> BRUSHED_STRUCTURAL_STEEL = Builder(
			"decoration/structural_steel_brushed/structural_steel",
			(prop) -> new StructuralSteelBlock(WeatheringSteel.WeatherState.UNAFFECTED, prop)).register();

	@MainTab
	public static final BlockEntry<StructuralSteelBlock> BRUSHED_STRUCTURAL_STEEL_WEATHERED = Builder(
			"decoration/structural_steel_brushed/structural_steel_weathered",
			(prop) -> new StructuralSteelBlock(WeatheringSteel.WeatherState.WEATHERED, prop)).register();

	@MainTab
	public static final BlockEntry<StructuralSteelBlock> BRUSHED_STRUCTURAL_STEEL_RUSTED = Builder(
			"decoration/structural_steel_brushed/structural_steel_rusted",
			(prop) -> new StructuralSteelBlock(WeatheringSteel.WeatherState.RUSTED, prop)).register();

	@MainTab
	public static final BlockEntry<Block> BRUSHED_STEEL = Builder("brushed_steel",
			Block::new).properties(prop -> prop.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(5.0F, 6.0F)
					.sound(SoundType.METAL)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<LadderBlock> CARBON_STEEL_LADDER = Builder("carbon_steel_ladder",
			LadderBlock::new).properties(p -> p.forceSolidOff().strength(0.8F).sound(SoundType.LADDER)
					.noOcclusion().pushReaction(PushReaction.DESTROY)).simpleItem().register();

	@MainTab
	public static final BlockEntry<Block> CARBON_STEEL = Builder("carbon_steel",
			Block::new).properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(5.0F, 6.0F)
					.sound(SoundType.METAL)).simpleItem().defaultBlockstate().register();

	@MainTab
	public static final BlockEntry<SlabBlock> CARBON_STEEL_GRATE_SLAB = Builder("carbon_steel_grate_slab",
			SlabBlock::new).properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(5.0F, 6.0F)
					.sound(SoundType.METAL).noOcclusion()).simpleItem().register();

	@MainTab
	public static final BlockEntry<SlabBlock> CARBON_STEEL_GRATE = Builder("carbon_steel_grate",
			SlabBlock::new).properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(5.0F, 6.0F)
					.sound(SoundType.METAL).noOcclusion()).simpleItem().register();

	@MainTab
	public static final BlockEntry<SlabBlock> CARBON_STEEL_SLAB = Builder("carbon_steel_slab",
			SlabBlock::new).properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(5.0F, 6.0F)
					.sound(SoundType.METAL).noOcclusion()).simpleItem().register();

	@MainTab
	public static final BlockEntry<TrapDoorBlock> CARBON_STEEL_TRAPDOOR = Builder("carbon_steel_trapdoor",
			(prop) -> new TrapDoorBlock(prop, BlockSetType.IRON)).properties(p -> p.noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)
					.strength(5.0F, 6.0F).sound(SoundType.METAL)).simpleItem().defaultBlockstate().register();

	@MainTab
	public static final BlockEntry<HartnellRotor> HARTNELL_ROTOR = Builder("hartnell_rotor",
			HartnellRotor::new).properties(p -> BlockBehaviour.Properties.copy(Blocks.GLASS)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<HardLightBlock> HARD_LIGHT = Builder("hard_light",
			HardLightBlock::new).properties(p -> BlockBehaviour.Properties.copy(Blocks.GLASS).lightLevel(BlockState -> 8)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<ExteriorBlock> EXTERIOR_BLOCK = Builder("exterior_block",
			prop -> new ExteriorBlock(prop, TTSTileEntities.EXTERIOR_TILE)).properties(BlockBehaviour.Properties::noOcclusion).simpleItem().defaultBlockstate().register();

	@MainTab
	public static final BlockEntry<ConsoleBlock<HudolinConsoleTile>> HUDOLIN_CONSOLE_BLOCK = Builder(
			"hudolin_console_block",
			p -> new ConsoleBlock<HudolinConsoleTile>(p, TTSTileEntities.HUDOLIN_CONSOLE_TILE)).properties(BlockBehaviour.Properties::noOcclusion).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<ConsoleBlock<NESSConsoleTile>> NESS_CONSOLE_BLOCK = Builder(
			"ness_console_block",
			p -> new ConsoleBlock<NESSConsoleTile>(p, TTSTileEntities.NESS_CONSOLE_TILE)).properties(BlockBehaviour.Properties::noOcclusion).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<ChameleonCircuitPanel> CHAMELEON_CIRCUIT_BLOCK = Builder(
			"chameleon_circuit_panel", p -> new ChameleonCircuitPanel(p, TTSTileEntities.CHAMELEON_CIRCUIT_PANEL)).properties(BlockBehaviour.Properties::noOcclusion).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<DoorBlock> DOOR_BLOCK = Builder("door_block",
			DoorBlock::new).properties(p -> p.noOcclusion().noCollission()).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<MonitorBlock> MONITOR_BLOCK = Builder("monitor_block",
			MonitorBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).simpleItem().register();

	@MainTab
	public static final BlockEntry<CRTMonitorBlock> CRT_MONITOR_BLOCK = Builder("crt_monitor_block",
			CRTMonitorBlock::new).properties(p -> BlockBehaviour.Properties.copy(Blocks.TERRACOTTA)).simpleItem().register();

	@MainTab
	public static final BlockEntry<MonitorPanel> MONITOR_PANEL = Builder("monitor_panel",
			MonitorPanel::new).properties(p -> p.strength(1f).sound(SoundType.GLASS)).simpleItem().register();

	@MainTab
	public static final BlockEntry<CoordinatePanelBlock> COORDINATE_PANEL = Builder("coordinate_panel",
			CoordinatePanelBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<RotorBlock> BLUE_ROTOR = Builder("rotor/blue",
			RotorBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.GLASS).noOcclusion()).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<RotorBlock> AMETHYST_ROTOR = Builder("rotor/amethyst",
			RotorBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.GLASS).noOcclusion()).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<RotorBlock> COPPER_ROTOR = Builder("rotor/copper",
			RotorBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.GLASS).noOcclusion()).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<LightPanel> LIGHT_PANEL = Builder("light_panel",
			LightPanel::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).simpleItem().register();

	@MainTab
	public static final BlockEntry<ARSPanel> ARS_PANEL = Builder("ars_panel",
			ARSPanel::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).simpleItem().register();

	@MainTab
	public static final BlockEntry<ThrottleBlock> THROTTLE = Builder("throttle",
			ThrottleBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).simpleItem().register();

	@MainTab
	public static final BlockEntry<ToyotaThrottleBlock> TOYOTA_THROTTLE = Builder("toyota_throttle",
			ToyotaThrottleBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).simpleItem().register();

	@MainTab
	public static final BlockEntry<PowerLever> POWER_LEVER = Builder("power_lever",
			PowerLever::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).simpleItem().register();

	@MainTab
	public static final BlockEntry<DestinationInfoBlock> DESTINATION_INFO_PANEL = Builder("destination_info_panel",
			DestinationInfoBlock::new).properties(p -> p.strength(1.25f).sound(SoundType.STONE)).simpleItem().register();

	@MainTab
	public static final BlockEntry<HartnellDoor> HARTNELL_DOOR = TTSBlocks.Builder("hartnell_door",
			p -> new HartnellDoor(p, TTSTileEntities.HARTNELL_DOOR)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<WorkbenchBlock> TEMPORAL_FABRICATOR = TTSBlocks
			.Builder("temporal_fabricator", WorkbenchBlock::new).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<FragmentLinksBlock> FRAGMENT_LINKS = Builder("fragment_links",
			FragmentLinksBlock::new).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<DematerializationCircuitCoreBlock> DEMATERIALIZATION_CIRCUIT_CORE = Builder(
			"dematerialization_circuit_core", DematerializationCircuitCoreBlock::new).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<NetherReactorCoreBlock> NETHER_REACTOR_CORE = Builder("nether_reactor_core",
			NetherReactorCoreBlock::new).properties(p -> p.strength(1.5f).sound(SoundType.STONE)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<EngineInterfaceBlock> TARDIS_ENGINE_INTERFACE = Builder("tardis_engine_interface",
			EngineInterfaceBlock::new).properties(p -> p.strength(1.5f).sound(SoundType.STONE)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<EnginesBlock> TARDIS_ENGINES = Builder("tardis_engines",
			EnginesBlock::new).properties(p -> p.strength(1.5f).sound(SoundType.STONE)).defaultBlockstate().simpleItem().register();

	@MainTab
	public static final BlockEntry<PortalBlock> PORTAL_BLOCK = Builder("portal_block",
			PortalBlock::new).properties(p -> p.mapColor(MapColor.COLOR_PURPLE).strength(5.0F).noOcclusion()
					.lightLevel(state -> 10)).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> INTERIOR_ROCK = Builder("organic/interior_rock", Block::new).properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.25f).sound(SoundType.METAL)).defaultBlockstate().simpleItem().register();

	/* Moon Block */
	@DimensionalTab
	public static final BlockEntry<SandBlock> MOON_ROCK = Builder("dimensional/moon/moon_rock",
			properties -> new SandBlock(0, properties)).properties(p -> p.strength(1.0f)).defaultBlockstate().simpleItem().register();

	/* Gallifrey Blocks */

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_LOG = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_log",
			new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f))).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<SandBlock> GALLIFREYAN_SAND = Builder(
			"dimensional/gallifreyan/gallifreyan_sand", p -> new SandBlock(10634503, p)).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_WOOD = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_wood",
			new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f))).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> STRIPPED_GALLIFREYAN_OAK_LOG = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_log_stripped",
			new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f))).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> STRIPPED_GALLIFREYAN_OAK_WOOD = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_wood_stripped",
			new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f))).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_PLANKS = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_planks",
			new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
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
			}).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_LEAVES = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_leaves",
			new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
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
			}).defaultBlockstate().simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_SAPLING = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_sapling",
			new SaplingBlock(new GallifreyanOakTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_STAIRS = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_stairs",
			new StairBlock(() -> GALLIFREYAN_OAK_PLANKS.get().defaultBlockState(),
					BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD))).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_SLAB = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_slab",
			new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD))).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_BUTTON = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_button",
			new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.WOOD),
					BlockSetType.IRON, 10, true)).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_PRESSURE_PLATE = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_pressure_plate",
			new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
					BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD), BlockSetType.OAK)).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_FENCE = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_fence",
			new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD))).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_FENCE_GATE = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_fence_gate",
			new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD),
					SoundEvents.CHAIN_PLACE, SoundEvents.ANVIL_BREAK)).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_WALL = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_wall",
			new WallBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD))).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_DOOR = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_door",
			new net.minecraft.world.level.block.DoorBlock(
					BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(SoundType.WOOD).noOcclusion(),
					BlockSetType.IRON)).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> GALLIFREYAN_OAK_TRAPDOOR = Builder(
			"dimensional/gallifreyan/gallifreyan_oak_trapdoor",
			new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).noOcclusion(),
					BlockSetType.IRON)).simpleItem().register();

	@DimensionalTab
	public static final BlockEntry<Block> VAROS_ROCKS = Builder("dimensional/varos/rocks",
			new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE))).defaultBlockstate().simpleItem().register();

	/** Ores */
	public static final BlockEntry<Block> ZEITON_BLOCK = Builder("zeiton/zeiton_block",
			new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE))).defaultBlockstate().simpleItem().register();

	public static final BlockEntry<Block> RAW_ZEITON_BLOCK = Builder("zeiton/raw_zeiton_block",
			new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.STONE))).defaultBlockstate().simpleItem().register();

	public static final BlockEntry<Block> ZEITON_ORE = Builder("zeiton/zeiton_ore",
			new DropExperienceBlock(
					BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops(),
					UniformInt.of(3, 6))).defaultBlockstate().simpleItem().register();

	public static final BlockEntry<Block> DEEPSLATE_ZEITON_ORE = Builder("zeiton/deepslate_zeiton_ore",
			new DropExperienceBlock(
					BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(3f).requiresCorrectToolForDrops(),
					UniformInt.of(3, 7))).defaultBlockstate().simpleItem().register();

	public static final BlockEntry<Block> NETHER_ZEITON_ORE = Builder("zeiton/nether_zeiton_ore",
			new DropExperienceBlock(
					BlockBehaviour.Properties.copy(Blocks.NETHERRACK).strength(1f).requiresCorrectToolForDrops(),
					UniformInt.of(3, 7))).defaultBlockstate().simpleItem().register();

	public static final BlockEntry<Block> END_STONE_ZEITON_ORE = Builder("zeiton/end_stone_zeiton_ore",
			new DropExperienceBlock(
					BlockBehaviour.Properties.copy(Blocks.END_STONE).strength(5f).requiresCorrectToolForDrops(),
					UniformInt.of(3, 7))).defaultBlockstate().simpleItem().register();

	public static final BlockEntry<Block> HARTNELL_DOOR_PLACEHOLDER = Builder(
			"hartnell_door_placeholder", new HartnellDoorMultiBlock(TTSTileEntities.HARTNELL_DOOR_PLACEHOLDER)).defaultBlockstate().simpleItem().register();

	/**
	 * Automatically handles registering the block and item for you, all you do is
	 * pass in the name
	 */
	public static BlockEntry<Block> SetupBlock(String name) {
		return Builder(name, Block::new).properties(p -> p.strength(1.25f)).register();
	}

	/** Registers the block and a {@link BlockItem} */
	public static BlockEntry<Block> SetupRoundel(String name) {
		return Builder("roundel/" + name,
				Block::new).properties(p -> p.strength(1.25f).lightLevel(BlockState -> 15)).register();
	}

	/**
	 * Registers the block and a {@link BlockItem}
	 */
	public static <T extends Block> BlockEntry<T> RegisterWithItem(String name, T supplier) {
		return registrate().block(name, (prop) -> supplier).simpleItem().defaultBlockstate().defaultLang()
				.register();
	}

	/** Registers a block */
	public static BlockBuilder<Block, TTSRegistrate> Builder(String name, Block block) {
		return registrate().block(name, (properties) -> block);
	}

	public static <T extends Block> BlockBuilder<T, TTSRegistrate> Builder(String name, NonNullFunction<BlockBehaviour.Properties, T> block) {
		return registrate().block(name, block);
	}

	public static List<RegistryEntry<Block>> AllValues() {
		return registrate().getAll(Registries.BLOCK).stream().toList();
	}

	public static RegistryEntry<Block> regFromBlock(Block block) {
		for (RegistryEntry<Block> b : AllValues()) {
			if (b.get().equals(block))
				return b;
		}
		return null;
	}


	/**
	 * @param toCopy Block to copy Properties from
	 * @param copyTo Properties to copy to
	 * @return A Properties copied from the blockToCopy
	 */
	BlockBehaviour.Properties copyProperties(Block toCopy, BlockBehaviour.Properties copyTo) {
		BlockBehaviour.Properties propertiesToCopy = ReflectionBuddy.BlockBehaviorAccess.properties.apply(toCopy);

		BlockBehaviourPropertiesAccessor accessor = (BlockBehaviourPropertiesAccessor) toCopy;
		BlockBehaviourPropertiesAccessor copyAccessor = (BlockBehaviourPropertiesAccessor) copyTo;

		// God I love accessors
		copyAccessor.setDestroyTime(accessor.getDestroyTime());
		copyAccessor.setExplosionResistance(accessor.getExplosionResistance());
		copyAccessor.setHasCollision(accessor.getHasCollision());
		copyAccessor.setIsRandomlyTicking(accessor.getIsRandomlyTicking());
		copyAccessor.setLightEmission(accessor.getLightEmission());
		copyAccessor.setMapColor(accessor.getMapColor());
		copyAccessor.setSoundType(accessor.getSoundType());
		copyAccessor.setFriction(accessor.getFriction());
		copyAccessor.setSpeedFactor(accessor.getSpeedFactor());
		copyAccessor.setDynamicShape(accessor.getDynamicShape());
		copyAccessor.setCanOcclude(accessor.getCanOcclude());
		copyAccessor.setIsAir(accessor.getIsAir());
		copyAccessor.setIgnitedByLava(accessor.getIgnitedByLava());
		copyAccessor.setLiquid(accessor.getLiquid());
		copyAccessor.setForceSolidOff(accessor.getForceSolidOff());
		copyAccessor.setForceSolidOn(accessor.getForceSolidOn());
		copyAccessor.setPushReaction(accessor.getPushReaction());
		copyAccessor.setRequiresCorrectToolForDrops(accessor.getRequiresCorrectToolForDrops());
		copyAccessor.setOffsetFunction(accessor.getOffsetFunction());
		copyAccessor.setSpawnParticlesOnBreak(accessor.getSpawnParticlesOnBreak());
		copyAccessor.setRequiredFeatures(accessor.getRequiredFeatures());
		copyAccessor.setEmissiveRendering(accessor.getEmissiveRendering());
		copyAccessor.setInstrument(accessor.getInstrument());
		copyAccessor.setReplaceable(accessor.getReplaceable());

		return copyTo;
	}

	public static void register() {
	}
}
