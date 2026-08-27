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
 * Parses the same .geo.json format Blockbench exports for "Generic Model" /
 * used by geckolib, so you can keep using Blockbench for modelling. Only the
 * fields we need are read; unknown fields are ignored.
 *
 * Expected shape: { "minecraft:geometry": [ { "description": { "texture_width":
 * 64, "texture_height": 64 }, "bones": [ { "name": "body", "parent": "root",
 * "pivot": [x,y,z], "rotation": [x,y,z], "cubes": [ { "origin":[x,y,z],
 * "size":[x,y,z], "uv":[u,v], "inflate":0, "mirror":false, "rotation":[x,y,z],
 * "pivot":[x,y,z] } ] } ] } ] }
 */
public class GeoModelLoader {

	private static final Map<ResourceLocation, GeoModel> CACHE = new HashMap<>();

	/**
	 * Cached, safe to call this every frame if you need to, it only parses once per
	 * ResourceLocation.
	 */
	public static GeoModel load(ResourceManager resourceManager, ResourceLocation location) {
		return CACHE.computeIfAbsent(location, loc -> loadUncached(resourceManager, loc));
	}

	/**
	 * Call from a ResourceManagerReloadListener if you want /reload to pick up
	 * edited models.
	 */
	public static void clearCache() {
		CACHE.clear();
	}

	private static GeoModel loadUncached(ResourceManager resourceManager, ResourceLocation location) {
		try (InputStreamReader reader = new InputStreamReader(
				resourceManager.getResource(location)
						.orElseThrow(() -> new IOException("Missing geo model: " + location)).open(),
				StandardCharsets.UTF_8)) {
			JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
			return parse(root);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load geo model " + location, e);
		}
	}

	public static GeoModel parse(JsonObject root) {
		JsonArray geoArray = root.getAsJsonArray("minecraft:geometry");
		JsonObject geo = geoArray.get(0).getAsJsonObject();

		JsonObject description = geo.getAsJsonObject("description");
		int texW = description != null && description.has("texture_width")
				? description.get("texture_width").getAsInt()
				: 64;
		int texH = description != null && description.has("texture_height")
				? description.get("texture_height").getAsInt()
				: 64;

		JsonArray bonesJson = geo.getAsJsonArray("bones");

		// First pass: create all bones with no parent link yet (need pivot for cube
		// offset math)
		Map<String, JsonObject> rawByName = new LinkedHashMap<>();
		for (JsonElement el : bonesJson) {
			JsonObject bo = el.getAsJsonObject();
			rawByName.put(bo.get("name").getAsString(), bo);
		}

		Map<String, GeoBone> bones = new HashMap<>();
		List<GeoBone> roots = new ArrayList<>();
		Map<String, float[]> absolutePivots = new HashMap<>(); // name -> absolute [x,y,z] pivot, for parent-delta math

		// Build in dependency order (parents before children)
		Set<String> built = new HashSet<>();
		for (String name : rawByName.keySet()) {
			buildBone(name, rawByName, bones, built, roots, absolutePivots, texW, texH);
		}

		return new GeoModel(texW, texH, roots, bones);
	}

	private static GeoBone buildBone(String name, Map<String, JsonObject> raw, Map<String, GeoBone> built,
			Set<String> builtNames, List<GeoBone> roots, Map<String, float[]> absolutePivots, int texW, int texH) {
		if (builtNames.contains(name))
			return built.get(name);
		JsonObject bo = raw.get(name);
		String parentName = bo.has("parent") ? bo.get("parent").getAsString() : null;
		GeoBone parent = null;
		if (parentName != null && raw.containsKey(parentName)) {
			parent = buildBone(parentName, raw, built, builtNames, roots, absolutePivots, texW, texH);
		}

		// Bedrock/Blockbench "pivot" is ABSOLUTE model-space, not parent-relative, but
		// poseStack.translate() during rendering is relative to whatever the parent
		// already
		// pushed. Feeding the raw absolute value in compounds outward every level of
		// the
		// hierarchy (this is the "model is exploded/mangled" bug). Convert to a delta
		// from
		// the parent's absolute pivot before it goes into GeoBone.
		float[] pivotAbs = vec3(bo, "pivot", 0, 0, 0);
		float[] parentPivotAbs = parentName != null
				? absolutePivots.getOrDefault(parentName, new float[]{0, 0, 0})
				: new float[]{0, 0, 0};
		float[] pivotRel = new float[]{pivotAbs[0] - parentPivotAbs[0], pivotAbs[1] - parentPivotAbs[1],
				pivotAbs[2] - parentPivotAbs[2]};
		absolutePivots.put(name, pivotAbs);

		float[] rot = vec3(bo, "rotation", 0, 0, 0);

		GeoBone bone = new GeoBone(name, parent, pivotRel[0], pivotRel[1], pivotRel[2], rot[0], rot[1], rot[2]);

		if (bo.has("cubes")) {
			for (JsonElement el : bo.getAsJsonArray("cubes")) {
				JsonObject co = el.getAsJsonObject();
				float[] origin = vec3(co, "origin", 0, 0, 0);
				float[] size = vec3(co, "size", 0, 0, 0);
				float inflate = co.has("inflate") ? co.get("inflate").getAsFloat() : 0f;
				boolean mirror = co.has("mirror") && co.get("mirror").getAsBoolean();
				float[] cubeRot = vec3(co, "rotation", 0, 0, 0);
				float[] cubePivot = co.has("pivot")
						? vec3(co, "pivot", 0, 0, 0)
						: new float[]{origin[0] + size[0] / 2f, origin[1] + size[1] / 2f, origin[2] + size[2] / 2f};

				// Cube geometry needs to be relative to THIS bone's own absolute pivot
				// (independent of the parent-delta fix above, that fix only affects
				// where poseStack.translate moves the bone itself, not cube-local shape).
				float relOriginX = origin[0] - pivotAbs[0], relOriginY = origin[1] - pivotAbs[1],
						relOriginZ = origin[2] - pivotAbs[2];
				float relPivotX = cubePivot[0] - pivotAbs[0], relPivotY = cubePivot[1] - pivotAbs[1],
						relPivotZ = cubePivot[2] - pivotAbs[2];

				GeoCube cube;
				JsonElement uvEl = co.get("uv");
				if (uvEl != null && uvEl.isJsonObject()) {
					// Per-Face UV mode: "uv": { "north": {"uv":[u,v],"uv_size":[w,h]}, ... }
					Map<String, float[]> faceRects = new HashMap<>();
					JsonObject uvObj = uvEl.getAsJsonObject();
					for (String face : new String[]{"north", "south", "east", "west", "up", "down"}) {
						if (!uvObj.has(face))
							continue;
						JsonObject faceObj = uvObj.getAsJsonObject(face);
						float[] faceUv = vec2(faceObj, "uv", 0, 0);
						float[] faceSize = vec2(faceObj, "uv_size", 0, 0);
						// Deliberately NOT normalizing negative sizes — a negative height/width
						// here is Bedrock's own encoding for that face's axis being flipped
						// (every "down" face needs this), so u1/v1 can legitimately be less
						// than u0/v0 and that's preserved as-is.
						faceRects.put(face,
								new float[]{faceUv[0], faceUv[1], faceUv[0] + faceSize[0], faceUv[1] + faceSize[1]});
					}
					cube = new GeoCube(relOriginX, relOriginY, relOriginZ, size[0], size[1], size[2], 0, 0, inflate,
							mirror, relPivotX, relPivotY, relPivotZ, cubeRot[0], cubeRot[1], cubeRot[2], faceRects);
				} else {
					// Box-UV mode: "uv": [u, v]
					float[] uv = uv(co);
					cube = new GeoCube(relOriginX, relOriginY, relOriginZ, size[0], size[1], size[2], uv[0], uv[1],
							inflate, mirror, relPivotX, relPivotY, relPivotZ, cubeRot[0], cubeRot[1], cubeRot[2]);
				}
				bone.cubes.add(cube);
			}
		}

		built.put(name, bone);
		builtNames.add(name);
		if (parent == null)
			roots.add(bone);
		return bone;
	}

	private static float[] vec3(JsonObject o, String key, float dx, float dy, float dz) {
		if (!o.has(key))
			return new float[]{dx, dy, dz};
		JsonArray a = o.getAsJsonArray(key);
		return new float[]{a.get(0).getAsFloat(), a.get(1).getAsFloat(), a.get(2).getAsFloat()};
	}

	private static float[] uv(JsonObject co) {
		if (!co.has("uv") || co.get("uv").isJsonNull()) {
			return new float[]{0, 0};
		}

		JsonElement uvElement = co.get("uv");

		// Format: "uv": [0.0, 0.0]
		if (uvElement.isJsonArray()) {
			JsonArray a = uvElement.getAsJsonArray();
			if (a.size() >= 2) {
				return new float[]{a.get(0).getAsFloat(), a.get(1).getAsFloat()};
			}
		}
		// Format: "uv": { "u": 0.0, "v": 0.0 }
		else if (uvElement.isJsonObject()) {
			JsonObject obj = uvElement.getAsJsonObject();
			float u = obj.has("u") ? obj.get("u").getAsFloat() : 0f;
			float v = obj.has("v") ? obj.get("v").getAsFloat() : 0f;
			return new float[]{u, v};
		}

		return new float[]{0, 0};
	}

	private static float[] vec2(JsonObject o, String key, float dx, float dy) {
		if (!o.has(key))
			return new float[]{dx, dy};
		JsonArray a = o.getAsJsonArray(key);
		return new float[]{a.get(0).getAsFloat(), a.get(1).getAsFloat()};
	}
}