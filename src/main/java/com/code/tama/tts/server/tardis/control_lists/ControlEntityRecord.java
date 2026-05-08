/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

import net.minecraft.world.phys.AABB;

public record ControlEntityRecord(float cx, float cy, float cz, // pivot = center of cube
		float hw, float hh, float hd, // half-extents (width, height, depth)
		float yawDeg, // rotation around Y axis in degrees
		int ID) {
	/** Reconstruct the local-space AABB (un-rotated, centered on origin). */
	public AABB toLocalAABB() {
		return new AABB(-hw, -hh, -hd, hw, hh, hd);
	}
}