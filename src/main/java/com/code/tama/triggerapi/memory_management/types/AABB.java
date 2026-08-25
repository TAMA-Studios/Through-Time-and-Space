/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management.types;
import com.code.tama.memory_management.Struct;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;

@Struct
public class AABB {
	private static final double EPSILON = 1.0E-7D;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;

	public static long create(double p_82295_, double p_82296_, double p_82297_, double p_82298_, double p_82299_,
			double p_82300_) {
		// long toRet = NativeAABB.create();
		// NativeAABB.setMinX(toRet, Math.min(p_82295_, p_82298_));
		// NativeAABB.setMinY(toRet, Math.min(p_82296_, p_82299_));
		// NativeAABB.setMinZ(toRet, Math.min(p_82297_, p_82300_));
		// NativeAABB.setMaxX(toRet, Math.max(p_82295_, p_82298_));
		// NativeAABB.setMaxY(toRet, Math.max(p_82296_, p_82299_));
		// NativeAABB.setMaxZ(toRet, Math.max(p_82297_, p_82300_));

		return 1;// toRet;
	}

	public static long of(BoundingBox p_82322_) {
		return create(p_82322_.minX(), p_82322_.minY(), p_82322_.minZ(), (p_82322_.maxX() + 1), (p_82322_.maxY() + 1),
				(p_82322_.maxZ() + 1));
	}

	public static long unitCubeFromLowerCorner(Vec3 p_82334_) {
		return create(p_82334_.x, p_82334_.y, p_82334_.z, p_82334_.x + 1.0D, p_82334_.y + 1.0D, p_82334_.z + 1.0D);
	}
}
