/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import com.code.tama.tts.client.renderers.tiles.tardis.FragmentLinksTile;
import com.code.tama.tts.server.tileentities.*;
import com.code.tama.tts.server.tileentities.monitors.CRTMonitorTile;
import com.code.tama.tts.server.tileentities.monitors.MonitorPanelTile;
import com.code.tama.tts.server.tileentities.monitors.MonitorTile;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import static com.code.tama.tts.TTSMod.registrate;

public class TTSTileEntities {

	public static final BlockEntityEntry<ChameleonCircuitPanelTileEntity> CHAMELEON_CIRCUIT_PANEL = registrate().blockEntity("chameleon_circuit_panel", ChameleonCircuitPanelTileEntity::new)
			.validBlocks(TTSBlocks.CHAMELEON_CIRCUIT_BLOCK).register();

	public static final BlockEntityEntry<DoorTile> DOOR_TILE = registrate()
			.blockEntity("door_tile", DoorTile::new).validBlocks(TTSBlocks.DOOR_BLOCK).register();

	public static final BlockEntityEntry<ExampleTileEntity> EXAMPLE_TILE = registrate()
			.blockEntity("example_tile", ExampleTileEntity::new).validBlocks(TTSBlocks.EXAMPLE_TILE_BLOCK).register();

	public static final BlockEntityEntry<SonicConfiguratorTileEntity> SONIC_CONFIGURATOR = registrate().blockEntity("sonic_configurator", SonicConfiguratorTileEntity::new)
			.validBlocks(TTSBlocks.SONIC_CONFIGURATOR_BLOCK).register();

	public static final BlockEntityEntry<ExteriorTile> EXTERIOR_TILE = registrate()
			.blockEntity("exterior_tile", ExteriorTile::new).validBlocks(TTSBlocks.EXTERIOR_BLOCK).register();

	public static final BlockEntityEntry<HartnellRotorTile> HARTNELL_ROTOR = registrate()
			.blockEntity("hartnell_rotor", HartnellRotorTile::new).validBlocks(TTSBlocks.HARTNELL_ROTOR).register();

	public static final BlockEntityEntry<HartnellDoorTile> HARTNELL_DOOR = registrate()
			.blockEntity("hartnell_door", HartnellDoorTile::new).validBlocks(TTSBlocks.HARTNELL_DOOR).register();

	public static final BlockEntityEntry<HartnellDoorTilePlaceholder> HARTNELL_DOOR_PLACEHOLDER = registrate().blockEntity("hartnell_door_placeholder", HartnellDoorTilePlaceholder::new)
			.validBlocks(TTSBlocks.HARTNELL_DOOR_PLACEHOLDER).register();

	public static final BlockEntityEntry<HudolinConsoleTile> HUDOLIN_CONSOLE_TILE = registrate()
			.blockEntity("hudolin_console_tile", HudolinConsoleTile::new).validBlocks(TTSBlocks.HUDOLIN_CONSOLE_BLOCK)
			.register();

	public static final BlockEntityEntry<NESSConsoleTile> NESS_CONSOLE_TILE = registrate()
			.blockEntity("ness_console_tile", NESSConsoleTile::new).validBlocks(TTSBlocks.NESS_CONSOLE_BLOCK)
			.register();

	public static final BlockEntityEntry<MonitorTile> MONITOR_TILE = registrate()
			.blockEntity("monitor_tile", MonitorTile::new).validBlocks(TTSBlocks.MONITOR_BLOCK).register();

	public static final BlockEntityEntry<CRTMonitorTile> CRT_MONITOR_TILE = registrate()
			.blockEntity("crt_monitor_tile", CRTMonitorTile::new).validBlocks(TTSBlocks.CRT_MONITOR_BLOCK).register();

	public static final BlockEntityEntry<MonitorPanelTile> MONITOR_PANEL_TILE = registrate()
			.blockEntity("monitor_panel_tile", MonitorPanelTile::new).validBlocks(TTSBlocks.MONITOR_PANEL).register();

	public static final BlockEntityEntry<FragmentLinksTile> FRAGMENT_LINKS_TILE = registrate()
			.blockEntity("fragment_links_tile", FragmentLinksTile::new).validBlocks(TTSBlocks.FRAGMENT_LINKS)
			.register();

	public static final BlockEntityEntry<PortalTileEntity> PORTAL_TILE_ENTITY = registrate()
			.blockEntity("portal_tile_entity", PortalTileEntity::new).validBlocks(TTSBlocks.PORTAL_BLOCK).register();

	public static final BlockEntityEntry<ChromiumBlockEntity> CHROMIUM_BLOCK_ENTITY = registrate()
			.blockEntity("chromium_block_entity", ChromiumBlockEntity::new).validBlocks(TTSBlocks.CHROMIUM_BLOCK)
			.register();

	public static final BlockEntityEntry<SkyTile> SKY_TILE = registerTile("sky_block", SkyTile::new,
			TTSBlocks.SKY_BLOCK, TTSBlocks.VOID_BLOCK);

	public static final BlockEntityEntry<CompressedMultiblockTile> COMPRESSED_MULTIBLOCK_TILE = registerTile(
			"compressed_multiblock_tile", CompressedMultiblockTile::new, TTSBlocks.COMPRESSED_MULTIBLOCK);

	public static final BlockEntityEntry<WorkbenchTile> WORKBENCH_TILE = registrate()
			.blockEntity("celestial_workbench", WorkbenchTile::new).validBlocks(TTSBlocks.TEMPORAL_FABRICATOR)
			.register();

	// Generic helper if you still want shorthand syntax
	@SafeVarargs
	public static <P extends Block, T extends BlockEntity> BlockEntityEntry<T> registerTile(String name,
			BlockEntityBuilder.BlockEntityFactory<T> factory, NonNullSupplier<P>... blocks) {
		return registrate().blockEntity(name, factory).validBlocks(blocks).register();
	}

	public static void register() {
		// Trigger static class load
	}
}
