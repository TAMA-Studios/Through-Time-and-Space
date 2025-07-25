/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class PlaceStructureThread extends Thread {
    final BlockPos pos;
    final ServerLevel serverLevel;
    final ResourceLocation structure;

    public PlaceStructureThread(ServerLevel level, BlockPos pos, ResourceLocation structure) {
        this.setName("Structure Place Thread");
        this.serverLevel = level;
        this.pos = pos;
        this.structure = structure;
    }

    @Override
    public void run() {
        if (this.serverLevel != null) {
            // The resource location of the .nbt structure

            // Load the structure template
            StructureTemplate template = this.serverLevel.getStructureManager().getOrCreate(this.structure);
            int X = -template.getSize().getX();
            int Y = -template.getSize().getY();
            int Z = -template.getSize().getZ();

            BlockPos offset = new BlockPos(X / 2, Y / 2, Z / 2);
            BlockPos structureStartPos = this.pos.offset(offset);

            // Placement settings (adjust as needed)
            StructurePlaceSettings settings = new StructurePlaceSettings()
                    .setIgnoreEntities(false) // Include entities
                    // stored in the
                    // structure
                    .setRotation(Rotation.NONE) // No rotation
                    .setMirror(Mirror.NONE); // No mirroring

            // Place the structure
            template.placeInWorld(
                    this.serverLevel, structureStartPos, structureStartPos, settings, this.serverLevel.getRandom(), 3);

            System.out.println("Placed structure at: " + structureStartPos);
        }
        super.run();
    }
}
