/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.server.tardis.controls.AbstractControl;
import lombok.Getter;
import org.joml.Vector3f;

public abstract class AbstractControlList {
	private int ID = 0;

	@Getter
	public ArrayList<ControlEntityRecord> PositionSizeMap = new ArrayList<>();

	public Map<Integer, AbstractControl> GetDefaultControlAssignment() {
		return new HashMap<>();
	}

	public AbstractControlList() {
	}

	// No-rotation overloads for backwards compat

	/** Min/max corners, no rotation. */
	@Deprecated
	public int AddControl(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		return MinMaxAddControl(minX, minY, minZ, maxX, maxY, maxZ);
	}

	/** Min/max corners via vectors, no rotation. */
	@Deprecated
	public int AddControl(Vector3f min, Vector3f max) {
		return MinMaxAddControl(min.x, min.y, min.z, max.x, max.y, max.z);
	}

	// Rotation overloads (Blockbench exportS center + half-extents + yaw)

	/**
	 * Center position + half-extents + yaw. This is what the Blockbench plugin
	 * should export.
	 *
	 * @param cx
	 *            center X (relative to console origin)
	 * @param cy
	 *            center Y
	 * @param cz
	 *            center Z
	 * @param hw
	 *            half-width (X)
	 * @param hh
	 *            half-height (Y)
	 * @param hd
	 *            half-depth (Z)
	 * @param yawDeg
	 *            rotation around Y axis in degrees
	 */
	public int AddControl(float cx, float cy, float cz, float hw, float hh, float hd, float yawDeg) {
		PositionSizeMap.add(new ControlEntityRecord(cx, cy, cz, hw, hh, hd, yawDeg, ID++));
		return ID;
	}

	/** Center via vector + half-extents via vector + yaw. */
	public int AddControl(Vector3f center, Vector3f halfExtents, float yawDeg) {
		return AddControl(center.x, center.y, center.z, halfExtents.x, halfExtents.y, halfExtents.z, yawDeg);
	}

	// Internal: min/max corners -> center+half-extents (used by no-rotation
	// overloads)
	private int MinMaxAddControl(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		float cx = (minX + maxX) / 2f;
		float cy = (minY + maxY) / 2f;
		float cz = (minZ + maxZ) / 2f;
		float hw = (maxX - minX) / 2f;
		float hh = (maxY - minY) / 2f;
		float hd = (maxZ - minZ) / 2f;
		PositionSizeMap.add(new ControlEntityRecord(cx, cy, cz, hw, hh, hd, (float) 0.0, ID++));
		return ID;
	}
}