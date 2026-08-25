/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.List;
import java.util.Map;

/**
 * @param boneTracks
 *            bone name -> tracks
 */
public record GeoAnimation(String name, float lengthSeconds, boolean loop, Map<String, BoneTrack> boneTracks) {

	public static class BoneTrack {
		public List<Keyframe> rotation;
		public List<Keyframe> position;
		public List<Keyframe> scale;
	}

	/**
	 * Applies this animation at the given time (seconds since animation start) onto
	 * the model's bones. Call model.resetPose() first if you're not blending with
	 * another animation.
	 */
	public void apply(GeoModel model, float timeSeconds) {
		float t = timeSeconds;
		if (loop && lengthSeconds > 0) {
			t = t % lengthSeconds;
		} else if (t > lengthSeconds) {
			t = lengthSeconds;
		}

		for (Map.Entry<String, BoneTrack> entry : boneTracks.entrySet()) {
			GeoBone bone = model.getBone(entry.getKey());
			if (bone == null)
				continue;
			BoneTrack track = entry.getValue();

			float[] rot = Keyframe.sample(track.rotation, t, new float[]{0, 0, 0});
			float[] pos = Keyframe.sample(track.position, t, new float[]{0, 0, 0});
			float[] scale = Keyframe.sample(track.scale, t, new float[]{1, 1, 1});

			bone.animRotX = rot[0];
			bone.animRotY = rot[1];
			bone.animRotZ = rot[2];
			bone.animPosX = pos[0];
			bone.animPosY = pos[1];
			bone.animPosZ = pos[2];
			bone.animScaleX = scale[0];
			bone.animScaleY = scale[1];
			bone.animScaleZ = scale[2];
		}
	}
}
