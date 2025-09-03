/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.client.renderers.tiles.FragmentLinksTile;
import com.code.tama.tts.server.tileentities.*;
import com.code.tama.tts.server.tileentities.monitors.CRTMonitorTile;
import com.code.tama.tts.server.tileentities.monitors.MonitorPanelTile;
import com.code.tama.tts.server.tileentities.monitors.MonitorTile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.registries.TTSBlocks.*;

public class TTSTileEntities {

    public static final RegistryObject<BlockEntityType<ChameleonCircuitPanelTileEntity>> CHAMELEON_CIRCUIT_PANEL;
    public static final RegistryObject<BlockEntityType<DoorTile>> DOOR_TILE;
    // \/ Change this to be the Tile Entity Class
    public static final RegistryObject<BlockEntityType<ExampleTileEntity>> EXAMPLE_TILE;
    // Define this RegistryObject in the static block down below
    public static final RegistryObject<BlockEntityType<ExteriorTile>> EXTERIOR_TILE;
    public static final RegistryObject<BlockEntityType<HartnellRotorTile>> HARTNELL_ROTOR;
    public static final RegistryObject<BlockEntityType<HartnellDoorTile>> HARTNELL_DOOR;
    public static final RegistryObject<BlockEntityType<HartnellDoorTilePlaceholder>> HARTNELL_DOOR_PLACEHOLDER;
    public static final RegistryObject<BlockEntityType<ConsoleTile>> HUDOLIN_CONSOLE_TILE;
    public static final RegistryObject<BlockEntityType<MonitorPanelTile>> MONITOR_PANEL_TILE;
    public static final RegistryObject<BlockEntityType<MonitorTile>> MONITOR_TILE;
    public static final RegistryObject<BlockEntityType<CRTMonitorTile>> CRT_MONITOR_TILE;
    public static final RegistryObject<BlockEntityType<FragmentLinksTile>> FRAGMENT_LINKS_TILE;
    public static final RegistryObject<BlockEntityType<PortalTileEntity>> PORTAL_TILE_ENTITY;
    public static final RegistryObject<BlockEntityType<WorkbenchTile>> WORKBENCH_TILE;
    public static final RegistryObject<BlockEntityType<ChromiumBlockEntity>> CHROMIUM_BLOCK_ENTITY;
    public static final RegistryObject<BlockEntityType<SkyTile>> SKY_TILE;
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES;

    static {
        TILE_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

        // They all get defined right here
        EXAMPLE_TILE = TILE_ENTITIES.register(
                "example_tile",
                () -> create(
                        ExampleTileEntity::new, // Tile entity class
                        TTSBlocks.EXAMPLE_TILE_BLOCK.get())); // The RegistryObject of the block

        SKY_TILE = TILE_ENTITIES.register("sky_block", () -> create(SkyTile::new, SKY_BLOCK.get(), VOID_BLOCK.get()));

        FRAGMENT_LINKS_TILE = TILE_ENTITIES.register("fragment_links_tile", () -> create(FragmentLinksTile::new, FRAGMENT_LINKS.get()));

        PORTAL_TILE_ENTITY =
                TILE_ENTITIES.register("portal_tile_entity", () -> create(PortalTileEntity::new, PORTAL_BLOCK.get()));

        CHROMIUM_BLOCK_ENTITY = TILE_ENTITIES.register(
                "chromium_block_entity", () -> create(ChromiumBlockEntity::new, TTSBlocks.CHROMIUM_BLOCK.get()));

        HARTNELL_ROTOR = TILE_ENTITIES.register(
                "hartnell_rotor", () -> create(HartnellRotorTile::new, TTSBlocks.HARTNELL_ROTOR.get()));

        HUDOLIN_CONSOLE_TILE = TILE_ENTITIES.register(
                "hudolin_console_tile", () -> create(ConsoleTile::new, TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get()));

        MONITOR_TILE =
                TILE_ENTITIES.register("monitor_tile", () -> create(MonitorTile::new, TTSBlocks.MONITOR_BLOCK.get()));

        CRT_MONITOR_TILE = TILE_ENTITIES.register(
                "crt_monitor_tile", () -> create(CRTMonitorTile::new, TTSBlocks.CRT_MONITOR_BLOCK.get()));

        MONITOR_PANEL_TILE = TILE_ENTITIES.register(
                "monitor_panel_tile", () -> create(MonitorPanelTile::new, TTSBlocks.MONITOR_PANEL.get()));

        EXTERIOR_TILE = TILE_ENTITIES.register(
                "exterior_tile", () -> create(ExteriorTile::new, TTSBlocks.EXTERIOR_BLOCK.get()));

        DOOR_TILE = TILE_ENTITIES.register("door_tile", () -> create(DoorTile::new, TTSBlocks.DOOR_BLOCK.get()));

        CHAMELEON_CIRCUIT_PANEL = TILE_ENTITIES.register(
                "chameleon_circuit_panel",
                () -> create(ChameleonCircuitPanelTileEntity::new, TTSBlocks.CHAMELEON_CIRCUIT_BLOCK.get()));

        HARTNELL_DOOR = TILE_ENTITIES.register(
                "hartnell_door", () -> create(HartnellDoorTile::new, TTSBlocks.HARTNELL_DOOR.get()));

        HARTNELL_DOOR_PLACEHOLDER = TILE_ENTITIES.register(
                "hartnell_door_placeholder",
                () -> create(HartnellDoorTilePlaceholder::new, TTSBlocks.HARTNELL_DOOR_PLACEHOLDER.get()));

        WORKBENCH_TILE = TILE_ENTITIES.register(
                "celestial_workbench", () -> create(WorkbenchTile::new, TTSBlocks.WORKBENCH.get()));
    }

    public static <T extends BlockEntity> BlockEntityType<T> create(
            BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory, blocks).build(null);
    }
}
