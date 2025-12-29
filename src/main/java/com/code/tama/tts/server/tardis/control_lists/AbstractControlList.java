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
	};

	public AbstractControlList() {
	}

	public int AddControl(Vector3f min, Vector3f max) {
		PositionSizeMap.add(new ControlEntityRecord(min.x, min.y, min.z, max.x, max.y, max.z, ID++));
		return ID;
	}

	public int AddControl(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		PositionSizeMap.add(new ControlEntityRecord(minX, minY, minZ, maxX, maxY, maxZ, ID++));
		return ID;
	}
}
