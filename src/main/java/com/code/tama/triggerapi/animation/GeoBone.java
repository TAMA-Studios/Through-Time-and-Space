/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.ArrayList;
import java.util.List;

/**
 * A named bone in the hierarchy. Pivot/rotation are in pixel units / degrees
 * (Blockbench convention). animRotX/Y/Z, animPosX/Y/Z, animScaleX/Y/Z hold the
 * CURRENT animated offsets applied on top of the base pose each frame, reset
 * them to defaults before applying an animation, then let GeoAnimation.apply()
 * fill them in.
 */
public class GeoBone {

	public final String name;
	public final GeoBone parent; // null for root bones
	public final List<GeoBone> children = new ArrayList<>();
	public final List<GeoCube> cubes = new ArrayList<>();

	public final float pivotX, pivotY, pivotZ;
	public final float baseRotX, baseRotY, baseRotZ;

	// Per-frame animated deltas (degrees for rotation, pixel units for position,
	// multiplier for scale)
	public float animRotX = 0, animRotY = 0, animRotZ = 0;
	public float animPosX = 0, animPosY = 0, animPosZ = 0;
	public float animScaleX = 1, animScaleY = 1, animScaleZ = 1;

	public GeoBone(String name, GeoBone parent, float pivotX, float pivotY, float pivotZ, float baseRotX,
			float baseRotY, float baseRotZ) {
		this.name = name;
		this.parent = parent;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
		this.pivotZ = pivotZ;
		this.baseRotX = baseRotX;
		this.baseRotY = baseRotY;
		this.baseRotZ = baseRotZ;
		if (parent != null)
			parent.children.add(this);
	}

	public void resetAnim() {
		animRotX = animRotY = animRotZ = 0;
		animPosX = animPosY = animPosZ = 0;
		animScaleX = animScaleY = animScaleZ = 1;
	}
}
