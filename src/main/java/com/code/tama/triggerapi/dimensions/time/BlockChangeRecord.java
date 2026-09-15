/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.state.BlockState;

/**
 * One recorded edit in the base dimension, stored in a form that can be
 * resolved against a differently-shaped target heightmap.
 *
 * <h2>Anchoring</h2> The aged generator shifts surface terrain, so an edit's
 * absolute Y is not necessarily the right Y downstream. Each record therefore
 * picks an anchoring mode <em>once, at write time</em>:
 * <ul>
 * <li>{@link Anchor#GROUND_RELATIVE} -- the edit was near the surface. Store
 * its offset from the source heightmap; resolve downstream as
 * {@code targetGround + offset} so it rides the eroded terrain up or down.</li>
 * <li>{@link Anchor#ABSOLUTE} -- the edit was deep underground (where the
 * erosion bias doesn't reach) or far above any ground (bridges, skybases).
 * Store plain Y and place it unchanged. Floating builds stay frozen exactly as
 * built rather than crumpling column-by-column onto uneven terrain.</li>
 * </ul>
 * Deciding at write time means the read path is a single branch with no
 * thresholds to re-evaluate.
 */
public record BlockChangeRecord(int x, int z, Anchor anchor,
		/** Ground-relative offset, or absolute Y, depending on {@link #anchor()}. */
		short y, /**
					 * The state as it exists in the BASE dimension. Decay is applied at read time.
					 */
		BlockState sourceState) {

	public enum Anchor {
		GROUND_RELATIVE, ABSOLUTE;

		static Anchor of(boolean groundRelative) {
			return groundRelative ? GROUND_RELATIVE : ABSOLUTE;
		}
	}

	/**
	 * Blocks further below the surface than this don't need ground-relative
	 * anchoring -- the erosion bias only perturbs {@code erosion()}, which shapes
	 * surface topology, so deep terrain doesn't move. Also the cutoff that keeps
	 * mineshafts out of the surface-height math entirely.
	 */
	public static final int UNDERGROUND_CUTOFF = 12;

	/**
	 * Blocks higher than this above the surface have no meaningful "ground" to
	 * ride. Anchor them absolutely rather than letting each column chase whatever
	 * hill happens to sit below it.
	 */
	public static final int FLOATING_CUTOFF = 12;

	/**
	 * Builds a record, choosing the anchoring mode from the block's relationship to
	 * the source dimension's surface.
	 *
	 * @param sourceGroundY
	 *            the base dimension's heightmap value for this column
	 */
	public static BlockChangeRecord of(BlockPos pos, BlockState state, int sourceGroundY) {
		int delta = pos.getY() - sourceGroundY;
		boolean nearSurface = delta <= FLOATING_CUTOFF && delta >= -UNDERGROUND_CUTOFF;
		return new BlockChangeRecord(pos.getX(), pos.getZ(), Anchor.of(nearSurface),
				(short) (nearSurface ? delta : pos.getY()), state);
	}

	/**
	 * Resolves the Y this record should be written at in a target dimension.
	 *
	 * @param targetGroundY
	 *            the target dimension's heightmap value for this column; only
	 *            consulted for {@link Anchor#GROUND_RELATIVE} records, so callers
	 *            may pass anything (e.g. 0) when the anchor is absolute.
	 */
	public int resolveY(int targetGroundY) {
		return anchor == Anchor.GROUND_RELATIVE ? targetGroundY + y : y;
	}

	public boolean needsGroundLookup() {
		return anchor == Anchor.GROUND_RELATIVE;
	}

	/**
	 * Packs x/z into a chunk-local key so a chunk's records can be deduped by
	 * position.
	 */
	public long positionKey() {
		return ((long) x & 0xFFFFFFFFL) << 32 | ((long) z & 0xFFFFFFFFL) | ((long) y << 8);
	}

	/* ======================== Persistence ======================== */

	public CompoundTag save() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("x", x);
		tag.putInt("z", z);
		tag.putShort("y", y);
		tag.putBoolean("rel", anchor == Anchor.GROUND_RELATIVE);
		tag.put("state", NbtUtils.writeBlockState(sourceState));
		return tag;
	}

	public static BlockChangeRecord load(CompoundTag tag,
			net.minecraft.core.HolderGetter<net.minecraft.world.level.block.Block> blocks) {
		return new BlockChangeRecord(tag.getInt("x"), tag.getInt("z"), Anchor.of(tag.getBoolean("rel")),
				tag.getShort("y"), NbtUtils.readBlockState(blocks, tag.getCompound("state")));
	}
}