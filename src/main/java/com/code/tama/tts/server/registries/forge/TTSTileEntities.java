/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import com.code.tama.tts.client.renderers.monitors.CRTMonitorRenderer;
import com.code.tama.tts.client.renderers.monitors.MonitorPanelRenderer;
import com.code.tama.tts.client.renderers.monitors.MonitorRenderer;
import com.code.tama.tts.client.renderers.tiles.ChameleonCircuitRenderer;
import com.code.tama.tts.client.renderers.tiles.FaultLocatorRenderer;
import com.code.tama.tts.client.renderers.tiles.console.HudolinConsoleRenderer;
import com.code.tama.tts.client.renderers.tiles.console.NESSConsoleRenderer;
import com.code.tama.tts.client.renderers.tiles.decoration.HartnellRotorRenderer;
import com.code.tama.tts.client.renderers.tiles.decoration.PortalTileEntityRenderer;
import com.code.tama.tts.client.renderers.tiles.decoration.SkyTileRenderer;
import com.code.tama.tts.client.renderers.tiles.gadgets.CompressedMultiblockRenderer;
import com.code.tama.tts.client.renderers.tiles.tardis.FragmentLinksTile;
import com.code.tama.tts.client.renderers.tiles.tardis.InteriorDoorRenderer;
import com.code.tama.tts.client.renderers.tiles.tardis.TardisExteriorRenderer;
import com.code.tama.tts.server.registries.TTSRegistrate;
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

@SuppressWarnings("unchecked")
public class TTSTileEntities {

	public static final BlockEntityEntry<ChameleonCircuitPanelTileEntity> CHAMELEON_CIRCUIT_PANEL = registrate()
			.blockEntity("chameleon_circuit_panel", ChameleonCircuitPanelTileEntity::new).renderer(() -> ChameleonCircuitRenderer::new)
			.validBlocks(TTSBlocks.CHAMELEON_CIRCUIT_BLOCK).register();

	public static final BlockEntityEntry<EmptyArtificialShellTile> EMPTY_SHELL = registrate()
			.blockEntity("empty_shell", EmptyArtificialShellTile::new).validBlocks(TTSBlocks.EMPTY_SHELL).register();

	public static final BlockEntityEntry<DoorTile> DOOR_TILE = registrate().blockEntity("door_tile", DoorTile::new)
			.validBlocks(TTSBlocks.DOOR_BLOCK).renderer(() -> InteriorDoorRenderer::new).register();

	public static final BlockEntityEntry<ExampleTileEntity> EXAMPLE_TILE = registrate()
			.blockEntity("example_tile", ExampleTileEntity::new).validBlocks(TTSBlocks.EXAMPLE_TILE_BLOCK).register();

	public static final BlockEntityEntry<FaultLocatorTile> FAULT_LOCATOR = registrate()
			.blockEntity("fault_locator", FaultLocatorTile::new).validBlocks(TTSBlocks.FAULT_LOCATOR).renderer(() -> FaultLocatorRenderer::new).register();

	public static final BlockEntityEntry<SonicConfiguratorTileEntity> SONIC_CONFIGURATOR = registrate()
			.blockEntity("sonic_configurator", SonicConfiguratorTileEntity::new)
			.validBlocks(TTSBlocks.SONIC_CONFIGURATOR_BLOCK).register();

	public static final BlockEntityEntry<ExteriorTile> EXTERIOR_TILE = registrate()
			.blockEntity("exterior_tile", ExteriorTile::new).validBlocks(TTSBlocks.EXTERIOR_BLOCK)
			.renderer(() -> TardisExteriorRenderer::new).register();

	public static final BlockEntityEntry<HartnellRotorTile> HARTNELL_ROTOR = registrate()
			.blockEntity("hartnell_rotor", HartnellRotorTile::new).validBlocks(TTSBlocks.HARTNELL_ROTOR)
			.renderer(() -> HartnellRotorRenderer::new).register();

	public static final BlockEntityEntry<HartnellDoorTile> HARTNELL_DOOR = registrate()
			.blockEntity("hartnell_door", HartnellDoorTile::new).validBlocks(TTSBlocks.HARTNELL_DOOR).register();

	public static final BlockEntityEntry<HartnellDoorTilePlaceholder> HARTNELL_DOOR_PLACEHOLDER = registrate()
			.blockEntity("hartnell_door_placeholder", HartnellDoorTilePlaceholder::new)
			.validBlocks(TTSBlocks.HARTNELL_DOOR_PLACEHOLDER).register();

	public static final BlockEntityEntry<HudolinConsoleTile> HUDOLIN_CONSOLE_TILE = registrate()
			.blockEntity("hudolin_console_tile", HudolinConsoleTile::new).validBlocks(TTSBlocks.HUDOLIN_CONSOLE_BLOCK)
			.renderer(() -> HudolinConsoleRenderer::new).register();

	public static final BlockEntityEntry<NESSConsoleTile> NESS_CONSOLE_TILE = registrate()
			.blockEntity("ness_console_tile", NESSConsoleTile::new).validBlocks(TTSBlocks.NESS_CONSOLE_BLOCK)
			.renderer(() -> NESSConsoleRenderer::new).register();

	public static final BlockEntityEntry<MonitorTile> MONITOR_TILE = registrate()
			.blockEntity("monitor_tile", MonitorTile::new).validBlocks(TTSBlocks.MONITOR_BLOCK)
			.renderer(() -> MonitorRenderer::new).register();

	public static final BlockEntityEntry<CRTMonitorTile> CRT_MONITOR_TILE = registrate()
			.blockEntity("crt_monitor_tile", CRTMonitorTile::new).validBlocks(TTSBlocks.CRT_MONITOR_BLOCK)
			.renderer(() -> CRTMonitorRenderer::new).register();

	public static final BlockEntityEntry<MonitorPanelTile> MONITOR_PANEL_TILE = registrate()
			.blockEntity("monitor_panel_tile", MonitorPanelTile::new).validBlocks(TTSBlocks.MONITOR_PANEL)
			.renderer(() -> MonitorPanelRenderer::new).register();

	public static final BlockEntityEntry<FragmentLinksTile> FRAGMENT_LINKS_TILE = registrate()
			.blockEntity("fragment_links_tile", FragmentLinksTile::new).validBlocks(TTSBlocks.FRAGMENT_LINKS)
			.register();

	public static final BlockEntityEntry<PortalTileEntity> PORTAL_TILE_ENTITY = registrate()
			.blockEntity("portal_tile_entity", PortalTileEntity::new).validBlocks(TTSBlocks.PORTAL_BLOCK)
			.renderer(() -> PortalTileEntityRenderer::new).register();

	public static final BlockEntityEntry<ChromiumBlockEntity> CHROMIUM_BLOCK_ENTITY = registrate()
			.blockEntity("chromium_block_entity", ChromiumBlockEntity::new).validBlocks(TTSBlocks.CHROMIUM_BLOCK)
			.register();

	public static final BlockEntityEntry<BlockEntity> SKY_TILE = builder("sky_block_overworld",
			(t, p, e) -> new SkyTile(SkyTile.SkyType.Overworld, t, p, e), TTSBlocks.SKY_BLOCK)
			.renderer(() -> (context) -> new SkyTileRenderer(context)).register();

	public static final BlockEntityEntry<BlockEntity> SKY_TILE_VOID = builder("sky_block_void",
			(t, p, e) -> new SkyTile(SkyTile.SkyType.Void, t, p, e), TTSBlocks.VOID_BLOCK)
			.renderer(() -> (context) -> new SkyTileRenderer(context)).register();

	public static final BlockEntityEntry<BlockEntity> COMPRESSED_MULTIBLOCK_TILE = builder("compressed_multiblock_tile",
			CompressedMultiblockTile::new, TTSBlocks.COMPRESSED_MULTIBLOCK)
			.renderer(() -> (ctx) -> new CompressedMultiblockRenderer(ctx)).register();

	public static final BlockEntityEntry<WorkbenchTile> WORKBENCH_TILE = registrate()
			.blockEntity("celestial_workbench", WorkbenchTile::new).validBlocks(TTSBlocks.TEMPORAL_FABRICATOR)
			.register();

	@SafeVarargs
	public static <P extends Block, T extends BlockEntity> BlockEntityEntry<T> registerTile(String name,
			BlockEntityBuilder.BlockEntityFactory<T> factory, NonNullSupplier<P>... blocks) {
		return registrate().blockEntity(name, factory).validBlocks(blocks).register();
	}

	@SafeVarargs
	public static <P extends Block, T extends BlockEntity> BlockEntityBuilder<T, TTSRegistrate> builder(String name,
			BlockEntityBuilder.BlockEntityFactory<T> factory, NonNullSupplier<P>... blocks) {
		return registrate().blockEntity(name, factory).validBlocks(blocks);
	}

	public static void register() {
		// Trigger static class load
	}
}
