/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti.client;

import java.util.BitSet;

import net.minecraft.core.BlockPos;

/**
 * Compact 1-bit-per-block occupancy lookup covering the FULL volume that
 * {@code ChunkGatheringThread} gathers on the server -- including interior
 * blocks that never get emitted as {@code BotiBlockContainer}s because they
 * failed the exposed-face / behind-portal culling.
 * <br /><br />
 * Ambient occlusion needs to know "is there solid geometry here at all",
 * not just "do we have a rendered container here". Without this, AO
 * sampling falls back to treating every culled interior neighbour as
 * open air (or as a copy of the center block's own light), which kills
 * all contrast and makes faces look flat / fullbright.
 * <br /><br />
 * Coordinates in and out of this class are in the same "relative to
 * targetPos" space that {@code BotiBlockContainer#getPos()} uses, matching
 * the keys already used in {@code BOTIUtils.getMapFromContainerList}.
 */
public class OccupancyGrid {

	private final BitSet solid;
	private final int sizeX, sizeY, sizeZ;
	// Offset from a relative BlockPos (relative to targetPos) to the flat
	// array's local index space. Equal to (targetPos - worldMin) on the
	// gathering side -- see ChunkGatheringThread#run originX/Y/Z.
	private final int originX, originY, originZ;

	public OccupancyGrid(BitSet solid, int sizeX, int sizeY, int sizeZ, int originX, int originY, int originZ) {
		this.solid = solid;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
	}

	private int localX(BlockPos relPos) {
		return relPos.getX() + originX;
	}

	private int localY(BlockPos relPos) {
		return relPos.getY() + originY;
	}

	private int localZ(BlockPos relPos) {
		return relPos.getZ() + originZ;
	}

	public boolean isInBounds(BlockPos relPos) {
		int lx = localX(relPos);
		int ly = localY(relPos);
		int lz = localZ(relPos);
		return lx >= 0 && lx < sizeX && ly >= 0 && ly < sizeY && lz >= 0 && lz < sizeZ;
	}

	/**
	 * @param relPos position relative to the portal's targetPos (same space as
	 *               BotiBlockContainer#getPos())
	 * @return true if this cell was solid (non-air) in the originally gathered
	 *         volume, regardless of whether it survived exposed-face culling.
	 */
	public boolean isSolid(BlockPos relPos) {
		if (!isInBounds(relPos))
			return false;
		int lx = localX(relPos);
		int ly = localY(relPos);
		int lz = localZ(relPos);
		int index = lx * sizeY * sizeZ + ly * sizeZ + lz;
		return solid.get(index);
	}

	/** Rebuild from the flat boolean[] the gathering thread already builds server-side, before serializing. */
	public static BitSet pack(boolean[] flatSolid) {
		BitSet bits = new BitSet(flatSolid.length);
		for (int i = 0; i < flatSolid.length; i++) {
			if (flatSolid[i])
				bits.set(i);
		}
		return bits;
	}

	// -- Wire format helpers ----------------------------------------------
	// Used by PortalChunkDataPacketS2C to attach this grid to the packet for
	// the first batch of a gather, and to reconstruct it client-side.

	public byte[] getSolidBytes() {
		return solid.toByteArray();
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getSizeZ() {
		return sizeZ;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}

	public int getOriginZ() {
		return originZ;
	}

	public static OccupancyGrid fromBytes(byte[] bytes, int sizeX, int sizeY, int sizeZ, int originX, int originY,
			int originZ) {
		return new OccupancyGrid(BitSet.valueOf(bytes), sizeX, sizeY, sizeZ, originX, originY, originZ);
	}
}