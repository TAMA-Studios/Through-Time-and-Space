package com.code.tama.mtm.server;

import com.code.tama.mtm.server.tileentities.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.server.MTMBlocks.PORTAL_BLOCK;
import static com.code.tama.mtm.MTMMod.MODID;

public class MTMTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES;
    public static final RegistryObject<BlockEntityType<ExteriorTile>> EXTERIOR_TILE;
    public static final RegistryObject<BlockEntityType<DoorTile>> DOOR_TILE;
//    public static final RegistryObject<BlockEntityType<PortalTileEntity>> PORTAL_TILE_ENTITY;
    public static final RegistryObject<BlockEntityType<ChameleonCircuitPanelTileEntity>> CHAMELEON_CIRCUIT_PANEL;
    public static final RegistryObject<BlockEntityType<HartnellDoorTile>> HARTNELL_DOOR;
    public static final RegistryObject<BlockEntityType<HartnellDoorTilePlaceholder>> HARTNELL_DOOR_PLACEHOLDER;
    public static final RegistryObject<BlockEntityType<ConsoleTile>> HUDOLIN_CONSOLE_TILE;
    public static final RegistryObject<BlockEntityType<PortalTileEntity>> PORTAL_TILE_ENTITY;


    static {
        TILE_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

        PORTAL_TILE_ENTITY =
        TILE_ENTITIES.register("portal_tile_entity", () ->
                BlockEntityType.Builder.of(PortalTileEntity::new, PORTAL_BLOCK.get()).build(null));

        HUDOLIN_CONSOLE_TILE = TILE_ENTITIES.register("hudolin_console_tile", () -> create(
                ConsoleTile::new,
                MTMBlocks.HUDOLIN_CONSOLE_BLOCK.get()));

        EXTERIOR_TILE = TILE_ENTITIES.register("exterior_tile", () -> create(
                ExteriorTile::new,
                MTMBlocks.EXTERIOR_BLOCK.get()));

        DOOR_TILE = TILE_ENTITIES.register("door_tile", () -> create(
                DoorTile::new,
                MTMBlocks.DOOR_BLOCK.get()));

        CHAMELEON_CIRCUIT_PANEL = TILE_ENTITIES.register("chameleon_circuit_panel", () -> create(
                ChameleonCircuitPanelTileEntity::new,
                MTMBlocks.CHAMELEON_CIRCUIT_BLOCK.get()));

//        PORTAL_TILE_ENTITY = TILE_ENTITIES.register(
//                "portal_tile_entity",
//                () -> BlockEntityType.Builder.of(PortalTileEntity::new, PORTAL_BLOCK.get()).build(null)
//        );

        HARTNELL_DOOR =
                TILE_ENTITIES.register("hartnell_door",
                        () -> BlockEntityType.Builder.of(HartnellDoorTile::new, MTMBlocks.HARTNELL_DOOR.get()).build(null));

        HARTNELL_DOOR_PLACEHOLDER =
                TILE_ENTITIES.register("hartnell_door_placeholder",
                        () -> BlockEntityType.Builder.of(HartnellDoorTilePlaceholder::new, MTMBlocks.HARTNELL_DOOR_PLACEHOLDER.get()).build(null));
    }

    public static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks){
        return BlockEntityType.Builder.of(factory, blocks).build(null);
    }
    
}
