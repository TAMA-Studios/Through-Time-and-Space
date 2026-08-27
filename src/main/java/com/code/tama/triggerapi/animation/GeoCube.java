/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A single cuboid inside a bone, in "pixel" units (16 units = 1 block), same
 * convention Blockbench uses. Origin is the min corner in the bone's local
 * space (already offset so the bone pivot is at 0,0,0 — see GeoModelLoader
 * where this offset is applied).
 *
 * Supports both Blockbench UV modes: - Box UV: a single "uv":[u,v] pair, and
 * all 6 faces are auto-laid-out around it (the classic Minecraft entity-model
 * cross layout). - Per-Face UV: "uv" is an object keyed by face name
 * ("north","south", "east","west","up","down"), each with its own "uv":[u,v]
 * top-left corner and "uv_size":[w,h] extent to the opposite corner. Sizes can
 * be negative — that's not bad data, it's how Bedrock's format encodes an
 * axis-flip for that face (every "down" face in Bedrock/Blockbench exports
 * needs its V axis inverted relative to the other faces, hence a consistently
 * negative height there).
 */
public class GeoCube {

	public final float originX, originY, originZ;
	public final float sizeX, sizeY, sizeZ;
	public final float u, v; // used only when explicitFaceUV is null (box-UV mode)
	public final float inflate;
	public final boolean mirror;

	// Optional per-cube rotation around its own pivot (Blockbench allows this
	// independent of bone rotation)
	public final float pivotX, pivotY, pivotZ;
	public final float rotX, rotY, rotZ;

	/**
	 * null = box-UV mode. Non-null = per-face UV mode; keys are
	 * "north","south","east","west","up","down", values are {u0, v0, u1, v1}
	 * already resolved from uv + uv_size (signs preserved, NOT normalized).
	 */
	private final Map<String, float[]> explicitFaceUV;

	private List<LocalQuad> bakedQuads;

	/** Box-UV constructor (single u,v pair, auto layout). */
	public GeoCube(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float u, float v,
			float inflate, boolean mirror, float pivotX, float pivotY, float pivotZ, float rotX, float rotY,
			float rotZ) {
		this(originX, originY, originZ, sizeX, sizeY, sizeZ, u, v, inflate, mirror, pivotX, pivotY, pivotZ, rotX, rotY,
				rotZ, null);
	}

	/**
	 * Per-face UV constructor, pass the resolved face-rect map, u/v are ignored
	 * when this is non-null.
	 */
	public GeoCube(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float u, float v,
			float inflate, boolean mirror, float pivotX, float pivotY, float pivotZ, float rotX, float rotY, float rotZ,
			Map<String, float[]> explicitFaceUV) {
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.u = u;
		this.v = v;
		this.inflate = inflate;
		this.mirror = mirror;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
		this.pivotZ = pivotZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.explicitFaceUV = explicitFaceUV;
	}

	/**
	 * A single vertex of a baked quad, in bone-local pixel space (still needs /16
	 * to reach block space).
	 */
	public static class LocalVertex {
		public final float x, y, z, u, v;
		public LocalVertex(float x, float y, float z, float u, float v) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.u = u;
			this.v = v;
		}
	}

	/**
	 * 4 vertices + face normal, already fully baked (rotation/inflate/mirror
	 * applied).
	 */
	public static class LocalQuad {
		public final LocalVertex[] vertices; // length 4
		public final float nx, ny, nz;
		public LocalQuad(LocalVertex[] vertices, float nx, float ny, float nz) {
			this.vertices = vertices;
			this.nx = nx;
			this.ny = ny;
			this.nz = nz;
		}
	}

	public List<LocalQuad> getBakedQuads(int texWidth, int texHeight) {
		if (bakedQuads == null) {
			bakedQuads = bake(texWidth, texHeight);
		}
		return bakedQuads;
	}

	private List<LocalQuad> bake(int texWidth, int texHeight) {
		float x0 = originX - inflate, y0 = originY - inflate, z0 = originZ - inflate;
		float x1 = originX + sizeX + inflate, y1 = originY + sizeY + inflate, z1 = originZ + sizeZ + inflate;

		// 8 corners
		float[][] c = new float[][]{{x0, y0, z0}, {x1, y0, z0}, {x1, y1, z0}, {x0, y1, z0}, // z0 face corners 0-3
				{x0, y0, z1}, {x1, y0, z1}, {x1, y1, z1}, {x0, y1, z1} // z1 face corners 4-7
		};

		float dx = sizeX, dy = sizeY, dz = sizeZ;

		List<LocalQuad> quads = new ArrayList<>(6);

		if (explicitFaceUV != null) {
			// Per-Face UV mode: each face's rect comes straight from the JSON, signs and
			// all.
			quads.add(face(c[7], c[6], c[2], c[3], 0, 1, 0, rect("up")));
			quads.add(face(c[0], c[1], c[5], c[4], 0, -1, 0, rect("down")));
			quads.add(face(c[1], c[5], c[6], c[2], 1, 0, 0, rect("east")));
			quads.add(face(c[0], c[1], c[2], c[3], 0, 0, -1, rect("north")));
			quads.add(face(c[4], c[0], c[3], c[7], -1, 0, 0, rect("west")));
			quads.add(face(c[5], c[4], c[7], c[6], 0, 0, 1, rect("south")));
		} else {
			// Box-UV mode: slot layout verified against a real Blockbench box-UV export.
			// Top row is [UP][DOWN], bottom row is [EAST][NORTH][WEST][SOUTH]. Side-face V
			// direction: object-top (y1) -> smaller V (top of texture), object-bottom (y0)
			// -> larger V (bottom of texture) — texture images are stored top-down, so this
			// is what makes a texture painted "right side up" actually appear right side
			// up.
			quads.add(face(c[7], c[6], c[2], c[3], 0, 1, 0, u + dz, v, u + dz + dx, v + dz));
			quads.add(face(c[0], c[1], c[5], c[4], 0, -1, 0, u + dz + dx, v, u + dz + dx + dx, v + dz));
			quads.add(face(c[1], c[5], c[6], c[2], 1, 0, 0, u, v + dz + dy, u + dz, v + dz));
			quads.add(face(c[0], c[1], c[2], c[3], 0, 0, -1, u + dz, v + dz + dy, u + dz + dx, v + dz));
			quads.add(face(c[4], c[0], c[3], c[7], -1, 0, 0, u + dz + dx, v + dz + dy, u + dz + dx + dz, v + dz));
			quads.add(face(c[5], c[4], c[7], c[6], 0, 0, 1, u + dz + dx + dz, v + dz + dy, u + dz + dx + dz + dx,
					v + dz));
		}

		List<LocalQuad> result = new ArrayList<>(6);
		for (LocalQuad q : quads) {
			result.add(applyPivotRotationAndUv(q, texWidth, texHeight));
		}
		return result;
	}

	/**
	 * Resolves one named face's {u0,v0,u1,v1} from the explicit-UV map, or a
	 * degenerate rect if absent.
	 */
	private float[] rect(String faceName) {
		float[] r = explicitFaceUV.get(faceName);
		return r != null ? r : new float[]{0, 0, 0, 0};
	}

	private LocalQuad face(float[] p0, float[] p1, float[] p2, float[] p3, float nx, float ny, float nz, float[] rect) {
		return face(p0, p1, p2, p3, nx, ny, nz, rect[0], rect[1], rect[2], rect[3]);
	}

	private LocalQuad face(float[] p0, float[] p1, float[] p2, float[] p3, float nx, float ny, float nz, float u0,
			float v0, float u1, float v1) {
		boolean m = mirror;
		LocalVertex[] verts = new LocalVertex[]{new LocalVertex(p0[0], p0[1], p0[2], m ? u1 : u0, v0),
				new LocalVertex(p1[0], p1[1], p1[2], m ? u0 : u1, v0),
				new LocalVertex(p2[0], p2[1], p2[2], m ? u0 : u1, v1),
				new LocalVertex(p3[0], p3[1], p3[2], m ? u1 : u0, v1)};
		return new LocalQuad(verts, mirror ? -nx : nx, ny, mirror ? -nz : nz);
	}

	/**
	 * Applies the cube's own rotation around its pivot, and converts pixel UV ->
	 * 0..1 UV.
	 */
	private LocalQuad applyPivotRotationAndUv(LocalQuad q, int texWidth, int texHeight) {
		boolean hasRot = rotX != 0 || rotY != 0 || rotZ != 0;
		LocalVertex[] outVerts = new LocalVertex[4];
		float[] outNormal = {q.nx, q.ny, q.nz};
		for (int i = 0; i < 4; i++) {
			LocalVertex src = q.vertices[i];
			float x = src.x, y = src.y, z = src.z;
			if (hasRot) {
				float[] p = rotate(x - pivotX, y - pivotY, z - pivotZ, rotX, rotY, rotZ);
				x = p[0] + pivotX;
				y = p[1] + pivotY;
				z = p[2] + pivotZ;
			}
			outVerts[i] = new LocalVertex(x, y, z, src.u / texWidth, src.v / texHeight);
		}
		if (hasRot) {
			outNormal = rotate(q.nx, q.ny, q.nz, rotX, rotY, rotZ);
		}
		return new LocalQuad(outVerts, outNormal[0], outNormal[1], outNormal[2]);
	}

	/**
	 * Degrees, applied X-then-Y-then-Z to the point (matches Bedrock's documented
	 * bone/cube rotation order).
	 */
	private static float[] rotate(float x, float y, float z, float rxDeg, float ryDeg, float rzDeg) {
		double rx = Math.toRadians(rxDeg), ry = Math.toRadians(ryDeg), rz = Math.toRadians(rzDeg);
		// X
		double y1 = y * Math.cos(rx) - z * Math.sin(rx);
		double z1 = y * Math.sin(rx) + z * Math.cos(rx);
		double x1 = x;
		// Y
		double x2 = x1 * Math.cos(ry) + z1 * Math.sin(ry);
		double z2 = -x1 * Math.sin(ry) + z1 * Math.cos(ry);
		double y2 = y1;
		// Z
		double x3 = x2 * Math.cos(rz) - y2 * Math.sin(rz);
		double y3 = x2 * Math.sin(rz) + y2 * Math.cos(rz);
		double z3 = z2;
		return new float[]{(float) x3, (float) y3, (float) z3};
	}
}