package com.code.tama.mtm.server.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class BlockHelper {

    public static BlockPos snapToGround(Level world, BlockPos pos) {
        ChunkAccess chunkAccess = world.getChunk(ChunkPos.getX(pos.getX()), ChunkPos.getZ(pos.getY()));
        BlockPos.MutableBlockPos mutablePos = pos.mutable();//new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
        // Move downward until a solid block is found
        while (mutablePos.getY() > world.getMinBuildHeight()) {
            BlockState state = chunkAccess.getBlockState(mutablePos);
            if (!state.isAir()) {
                return mutablePos.immutable(); // Return the first solid block found
            }
            mutablePos.move(0, -1, 0);
        }

        return pos; // Fallback to original position if no ground found
    }

    public boolean containsBlock(Level world, BlockPos start, BlockPos end, Block blockToCheck) {
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int y = start.getY(); y <= end.getY(); y++) {
                for (int z = start.getZ(); z <= end.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    if (state.is(blockToCheck)) {
                        return true; // Found the block
                    }
                }
            }
        }
        return false; // Block not found
    }

    public boolean containsFluid(Level world, BlockPos start, BlockPos end, Fluid fluidToCheck) {
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int y = start.getY(); y <= end.getY(); y++) {
                for (int z = start.getZ(); z <= end.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    FluidState fluidState = world.getBlockState(pos).getFluidState();
                    if (fluidState.is(fluidToCheck)) {
                        return true; // Found the fluid
                    }
                }
            }
        }
        return false; // Fluid not found
    }

}
