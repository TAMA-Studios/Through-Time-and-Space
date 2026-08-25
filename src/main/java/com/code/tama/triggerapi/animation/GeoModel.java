/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.List;
import java.util.Map;

public record GeoModel(int textureWidth, int textureHeight, List<GeoBone> rootBones, Map<String, GeoBone> bonesByName) {
	public GeoBone getBone(String name) {
		return bonesByName.get(name);
	}
	public void resetPose() {
		for (GeoBone b : bonesByName.values())
			b.resetAnim();
	}
}
