/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

/**
 * Parses the Bedrock/geckolib .animation.json format: { "animations": {
 * "animation.tardis.door_open": { "loop": false, "animation_length": 1.5,
 * "bones": { "door": { "rotation": { "0.0": [0, 0, 0], "1.5": [0, 90, 0] } } }
 * } } } <br />
 * Values can be a plain [x,y,z] array, or an object {"vector":[x,y,z]}, both
 * are supported. Easing fields are ignored for now (linear only).
 */
public class GeoAnimationLoader {

	private static final Map<ResourceLocation, Map<String, GeoAnimation>> CACHE = new HashMap<>();

	/** Cached, safe to call every frame, only parses once per ResourceLocation. */
	public static Map<String, GeoAnimation> load(ResourceManager resourceManager, ResourceLocation location) {
		return CACHE.computeIfAbsent(location, loc -> loadUncached(resourceManager, loc));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	private static Map<String, GeoAnimation> loadUncached(ResourceManager resourceManager, ResourceLocation location) {
		try (InputStreamReader reader = new InputStreamReader(
				resourceManager.getResource(location)
						.orElseThrow(() -> new IOException("Missing animation file: " + location)).open(),
				StandardCharsets.UTF_8)) {
			JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
			return parse(root);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load animations " + location, e);
		}
	}

	public static Map<String, GeoAnimation> parse(JsonObject root) {
		Map<String, GeoAnimation> result = new HashMap<>();
		JsonObject animations = root.getAsJsonObject("animations");
		for (Map.Entry<String, JsonElement> entry : animations.entrySet()) {
			String animName = entry.getKey();
			JsonObject animObj = entry.getValue().getAsJsonObject();

			boolean loop = animObj.has("loop") && animObj.get("loop").getAsBoolean();
			float length = animObj.has("animation_length") ? animObj.get("animation_length").getAsFloat() : 0f;

			Map<String, GeoAnimation.BoneTrack> boneTracks = new HashMap<>();
			if (animObj.has("bones")) {
				JsonObject bonesObj = animObj.getAsJsonObject("bones");
				for (Map.Entry<String, JsonElement> boneEntry : bonesObj.entrySet()) {
					String boneName = boneEntry.getKey();
					JsonObject boneAnim = boneEntry.getValue().getAsJsonObject();
					GeoAnimation.BoneTrack track = new GeoAnimation.BoneTrack();
					if (boneAnim.has("rotation"))
						track.rotation = parseTrack(boneAnim.get("rotation"));
					if (boneAnim.has("position"))
						track.position = parseTrack(boneAnim.get("position"));
					if (boneAnim.has("scale"))
						track.scale = parseTrack(boneAnim.get("scale"));
					boneTracks.put(boneName, track);

					if (length == 0f) {
						length = Math.max(length, maxTime(track));
					}
				}
			}

			result.put(animName, new GeoAnimation(animName, length, loop, boneTracks));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static float maxTime(GeoAnimation.BoneTrack t) {
		float max = 0f;
		for (List<Keyframe> l : new List[]{t.rotation, t.position, t.scale}) {
			if (l != null)
				for (Keyframe k : l)
					max = Math.max(max, k.time());
		}
		return max;
	}

	/**
	 * A track can either be a single [x,y,z] (static, no animation) or an object of
	 * "time": value entries.
	 */
	private static List<Keyframe> parseTrack(JsonElement el) {
		List<Keyframe> keyframes = new ArrayList<>();
		if (el.isJsonArray()) {
			float[] v = vec3(el.getAsJsonArray());
			keyframes.add(new Keyframe(0f, v[0], v[1], v[2]));
			return keyframes;
		}
		JsonObject obj = el.getAsJsonObject();
		List<Map.Entry<String, JsonElement>> entries = new ArrayList<>(obj.entrySet());
		entries.sort(Comparator.comparingDouble(e -> Double.parseDouble(e.getKey())));
		for (Map.Entry<String, JsonElement> e : entries) {
			float time = Float.parseFloat(e.getKey());
			JsonElement valueEl = e.getValue();
			float[] v;
			if (valueEl.isJsonObject() && valueEl.getAsJsonObject().has("vector")) {
				v = vec3(valueEl.getAsJsonObject().getAsJsonArray("vector"));
			} else if (valueEl.isJsonArray()) {
				v = vec3(valueEl.getAsJsonArray());
			} else {
				v = new float[]{0, 0, 0};
			}
			keyframes.add(new Keyframe(time, v[0], v[1], v[2]));
		}
		return keyframes;
	}

	private static float[] vec3(JsonArray a) {
		return new float[]{a.get(0).getAsFloat(), a.get(1).getAsFloat(), a.get(2).getAsFloat()};
	}
}