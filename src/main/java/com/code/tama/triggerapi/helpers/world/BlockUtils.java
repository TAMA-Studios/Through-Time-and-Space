/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.world;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

import com.code.tama.triggerapi.NativeLoader;
import com.code.tama.triggerapi.universal.UniversalCommon;
import com.code.tama.triggerapi.universal.UniversalServerOnly;

@SuppressWarnings("unused")
public class BlockUtils {

	static {
		NativeLoader.load("tts_native");
	}

	// -- Native (Rust) — pure math, no MC objects ------------------------------

	private static native int getPackedLight(int blockLight, int skyLight);
	private static native String getRelativeBlockPos(int bx, int by, int bz, int ox, int oy, int oz);
	private static native String fromChunkAndLocal(int chunkX, int chunkZ, int localX, int localY, int localZ);
	private static native float getReverseHeightModifier(int blockType, int snowLayers);
	private static native float getDifferenceInHeight(int fromType, int fromLayers, int toType, int toLayers);
	private static native long packBlockPos(int x, int y, int z);
	private static native double blockDistance(int x1, int y1, int z1, int x2, int y2, int z2);
	private static native boolean isWithinRadius(int x1, int y1, int z1, int x2, int y2, int z2, double radius);
	private static native boolean sameChunk(int x1, int z1, int x2, int z2);

	// -- Public API — same signatures as before --------------------------------

	/** Breaks a block at the given position. */
	public static void breakBlock(Level world, BlockPos pos) {
		UniversalServerOnly.Level.breakBlock(world, pos);
	}

	/** Converts a chunk + local position into a world BlockPos. */
	public static BlockPos fromChunkAndLocal(ChunkPos chunkPos, BlockPos localPos) {
		String[] parts = fromChunkAndLocal(chunkPos.x, chunkPos.z, UniversalCommon.Pos.x(localPos),
				UniversalCommon.Pos.y(localPos), UniversalCommon.Pos.z(localPos)).split(",");
		return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
	}

	/** Height difference between two block states. */
	public static float getDifferenceInHeight(BlockState from, BlockState to) {
		return getDifferenceInHeight(blockTypeId(from), snowLayers(from), blockTypeId(to), snowLayers(to));
	}

	/**
	 * Height fraction for a block state (air=1, full=1, slab=0.5, carpet=1/16,
	 * snow=layers/8).
	 */
	public static float getHeightModifier(BlockState state) {
		// Still useful to expose directly for callers who already have a BlockState
		if (state.getBlock().equals(Blocks.AIR))
			return 1f;
		if (state.getBlock() instanceof SlabBlock && state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM)
			return 0.5f;
		if (state.getBlock() instanceof SnowLayerBlock)
			return state.getValue(SnowLayerBlock.LAYERS) * 0.125f;
		if (state.getBlock() instanceof CarpetBlock)
			return 0.0625f;
		return 1.0f;
	}

	/** Clamped block light level at pos. */
	public static int getLight(Level level, BlockPos pos) {
		return Mth.clamp(level.getBrightness(LightLayer.BLOCK, pos), 0, 15);
	}

	/** Packed block+sky light value for rendering. */
	public static int getPackedLight(Level level, BlockPos pos) {
		int block = level.getBrightness(LightLayer.BLOCK, pos);
		int sky = level.getBrightness(LightLayer.SKY, pos);
		return getPackedLight(block, sky); // native does the bit shift
	}

	/** Adds an offset position to a base position and returns the result. */
	public static BlockPos getRelativeBlockPos(BlockPos basePos, BlockPos offsetPos) {
		String[] parts = getRelativeBlockPos(basePos.getX(), basePos.getY(), basePos.getZ(), offsetPos.getX(),
				offsetPos.getY(), offsetPos.getZ()).split(",");
		return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
	}

	public static float getReverseHeightModifier(BlockState state) {
		return getReverseHeightModifier(blockTypeId(state), snowLayers(state));
	}

	/** Returns true if the block at pos matches the expected block type. */
	public static boolean isBlock(Level world, BlockPos pos, BlockState expected) {
		return world.getBlockState(pos).is(expected.getBlock());
	}

	/**
	 * Places a block only if the position is currently empty. Returns true on
	 * success.
	 */
	public static boolean placeBlock(Level world, BlockPos pos, BlockState state) {
		if (world.isEmptyBlock(pos)) {
			world.setBlock(pos, state, 3);
			return true;
		}
		return false;
	}

	// -- Bonus convenience methods (native-backed) -----------------------------

	/** Euclidean distance between two BlockPos. */
	public static double distance(BlockPos a, BlockPos b) {
		return blockDistance(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
	}

	/** True if b is within radius of a. Avoids sqrt. */
	public static boolean isWithinRadius(BlockPos a, BlockPos b, double radius) {
		return isWithinRadius(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ(), radius);
	}

	/** True if both positions are in the same 16×16 chunk column. */
	public static boolean sameChunk(BlockPos a, BlockPos b) {
		return sameChunk(a.getX(), a.getZ(), b.getX(), b.getZ());
	}

	/**
	 * Packs a BlockPos into a long using Minecraft's own encoding. Faster
	 * HashMap/HashSet key than boxing a BlockPos object.
	 */
	public static long pack(BlockPos pos) {
		return packBlockPos(pos.getX(), pos.getY(), pos.getZ());
	}

	// -- Internal helpers ------------------------------------------------------

	/**
	 * Maps a BlockState to the numeric block-type id used by native height
	 * functions.
	 */
	private static int blockTypeId(BlockState state) {
		if (state.getBlock().equals(Blocks.AIR))
			return 4;
		if (state.getBlock() instanceof SlabBlock && state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM)
			return 1;
		if (state.getBlock() instanceof SnowLayerBlock)
			return 2;
		if (state.getBlock() instanceof CarpetBlock)
			return 3;
		return 0;
	}

	private static int snowLayers(BlockState state) {
		if (state.getBlock() instanceof SnowLayerBlock)
			return state.getValue(SnowLayerBlock.LAYERS);
		return 1;
	}
}