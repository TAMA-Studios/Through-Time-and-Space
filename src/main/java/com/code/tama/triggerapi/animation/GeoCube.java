/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

public class GeoCube {

	public enum Face {
		UP, DOWN, EAST, NORTH, WEST, SOUTH
	}

	public record FaceUV(float u0, float v0, float u1, float v1, int rotation) {
	}

	public final float originX, originY, originZ;
	public final float sizeX, sizeY, sizeZ;
	public final float inflate;
	public final boolean mirror;

	public final float u, v;
	public final Map<Face, FaceUV> perFaceUV;

	public final float pivotX, pivotY, pivotZ;
	public final float rotX, rotY, rotZ;

	private List<LocalQuad> bakedQuads;

	public GeoCube(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float u, float v,
			float inflate, boolean mirror, float pivotX, float pivotY, float pivotZ, float rotX, float rotY,
			float rotZ) {
		this(originX, originY, originZ, sizeX, sizeY, sizeZ, u, v, null, inflate, mirror, pivotX, pivotY, pivotZ, rotX,
				rotY, rotZ);
	}

	public GeoCube(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ,
			Map<Face, FaceUV> perFaceUV, float inflate, boolean mirror, float pivotX, float pivotY, float pivotZ,
			float rotX, float rotY, float rotZ) {
		this(originX, originY, originZ, sizeX, sizeY, sizeZ, 0f, 0f, perFaceUV, inflate, mirror, pivotX, pivotY, pivotZ,
				rotX, rotY, rotZ);
	}

	private GeoCube(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float u,
			float v, Map<Face, FaceUV> perFaceUV, float inflate, boolean mirror, float pivotX, float pivotY,
			float pivotZ, float rotX, float rotY, float rotZ) {
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.u = u;
		this.v = v;
		this.perFaceUV = perFaceUV;
		this.inflate = inflate;
		this.mirror = mirror;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
		this.pivotZ = pivotZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	public record LocalVertex(float x, float y, float z, float u, float v) {
	}

	public record LocalQuad(LocalVertex[] vertices, float nx, float ny, float nz) {
	}

	public List<LocalQuad> getBakedQuads(int texWidth, int texHeight) {
		if (bakedQuads == null) {
			bakedQuads = bake(texWidth, texHeight);
		}
		return bakedQuads;
	}

	private List<LocalQuad> bake(int texWidth, int texHeight) {
		float[][] c = getC();

		float dx = sizeX, dy = sizeY, dz = sizeZ;
		List<LocalQuad> quads = new ArrayList<>(6);

		// Restored Clockwise (CW) face winding matching standard Blockbench model
		// geometry
		// UP (+Y)
		addFace(quads, Face.UP, c[7], c[6], c[2], c[3], 0, 1, 0, u + dz, v, u + dz + dx, v + dz);
		// DOWN (-Y)
		addFace(quads, Face.DOWN, c[0], c[1], c[5], c[4], 0, -1, 0, u + dz + dx, v, u + dz + dx + dx, v + dz);
		// EAST (+X)
		addFace(quads, Face.EAST, c[1], c[2], c[6], c[5], 1, 0, 0, u, v + dz + dy, u + dz, v + dz);
		// NORTH (-Z)
		addFace(quads, Face.NORTH, c[0], c[3], c[2], c[1], 0, 0, -1, u + dz, v + dz + dy, u + dz + dx, v + dz);
		// WEST (-X)
		addFace(quads, Face.WEST, c[4], c[7], c[3], c[0], -1, 0, 0, u + dz + dx, v + dz + dy, u + dz + dx + dz, v + dz);
		// SOUTH (+Z)
		addFace(quads, Face.SOUTH, c[5], c[6], c[7], c[4], 0, 0, 1, u + dz + dx + dz, v + dz + dy,
				u + dz + dx + dz + dx, v + dz);

		List<LocalQuad> result = new ArrayList<>(quads.size());
		for (LocalQuad q : quads) {
			result.add(applyPivotRotationAndUv(q, texWidth, texHeight));
		}
		return result;
	}

	private float @NotNull [][] getC() {
		float x0 = originX - inflate, y0 = originY - inflate, z0 = originZ - inflate;
		float x1 = originX + sizeX + inflate, y1 = originY + sizeY + inflate, z1 = originZ + sizeZ + inflate;

		// 8 box corners:
		// c[0..3] = z0 (North) face corners
		// c[4..7] = z1 (South) face corners
		return new float[][]{{x0, y0, z0}, {x1, y0, z0}, {x1, y1, z0}, {x0, y1, z0}, // 0, 1, 2, 3
				{x0, y0, z1}, {x1, y0, z1}, {x1, y1, z1}, {x0, y1, z1} // 4, 5, 6, 7
		};
	}

	private void addFace(List<LocalQuad> out, Face face, float[] p0, float[] p1, float[] p2, float[] p3, float nx,
			float ny, float nz, float boxU0, float boxV0, float boxU1, float boxV1) {
		float u0, v0, u1, v1;
		int rotation = 0;
		if (perFaceUV != null) {
			FaceUV fuv = perFaceUV.get(face);
			if (fuv == null)
				return; // Omitted face is omitted per spec
			u0 = fuv.u0;
			v0 = fuv.v0;
			u1 = fuv.u1;
			v1 = fuv.v1;
			rotation = fuv.rotation;
		} else {
			u0 = boxU0;
			v0 = boxV0;
			u1 = boxU1;
			v1 = boxV1;
		}
		out.add(face(p0, p1, p2, p3, nx, ny, nz, u0, v0, u1, v1, rotation));
	}

	private LocalQuad face(float[] p0, float[] p1, float[] p2, float[] p3, float nx, float ny, float nz, float u0,
			float v0, float u1, float v1, int uvRotationDeg) {
		float[][] cornerUv = new float[][]{{u0, v0}, {u1, v0}, {u1, v1}, {u0, v1}};

		int steps = ((uvRotationDeg / 90) % 4 + 4) % 4;
		if (steps != 0) {
			float[][] rotated = new float[4][];
			for (int i = 0; i < 4; i++) {
				rotated[i] = cornerUv[(i - steps + 4) % 4];
			}
			cornerUv = rotated;
		}

		boolean m = mirror;
		LocalVertex[] verts = new LocalVertex[]{
				new LocalVertex(p0[0], p0[1], p0[2], m ? cornerUv[1][0] : cornerUv[0][0], cornerUv[0][1]),
				new LocalVertex(p1[0], p1[1], p1[2], m ? cornerUv[0][0] : cornerUv[1][0], cornerUv[1][1]),
				new LocalVertex(p2[0], p2[1], p2[2], m ? cornerUv[3][0] : cornerUv[2][0], cornerUv[2][1]),
				new LocalVertex(p3[0], p3[1], p3[2], m ? cornerUv[2][0] : cornerUv[3][0], cornerUv[3][1])};
		return new LocalQuad(verts, mirror ? -nx : nx, ny, mirror ? -nz : nz);
	}

	private LocalQuad applyPivotRotationAndUv(LocalQuad q, int texWidth, int texHeight) {
		boolean hasRot = rotX != 0 || rotY != 0 || rotZ != 0;
		LocalVertex[] outVerts = new LocalVertex[4];
		float[] outNormal = {q.nx, q.ny, q.nz};
		for (int i = 0; i < 4; i++) {
			LocalVertex src = q.vertices[i];
			float x = src.x, y = src.y, z = src.z;
			if (hasRot) {
				float[] p = rotate(x - pivotX, y - pivotY, z - pivotZ, -rotX, -rotY, -rotZ);
				x = p[0] + pivotX;
				y = p[1] + pivotY;
				z = p[2] + pivotZ;
			}
			outVerts[i] = new LocalVertex(x, y, z, src.u / texWidth, src.v / texHeight);
		}
		if (hasRot) {
			outNormal = rotate(q.nx, q.ny, q.nz, -rotX, -rotY, -rotZ);
		}
		return new LocalQuad(outVerts, outNormal[0], outNormal[1], outNormal[2]);
	}

	/**
	 * Rotates point (x,y,z) around origin in X -> Y -> Z order (Bedrock geometry
	 * spec).
	 */
	private static float[] rotate(float x, float y, float z, float rxDeg, float ryDeg, float rzDeg) {
		double rx = Math.toRadians(rxDeg);
		double ry = Math.toRadians(ryDeg);
		double rz = Math.toRadians(rzDeg);

		// 1. X Rotation
		double y1 = y * Math.cos(rx) - z * Math.sin(rx);
		double z1 = y * Math.sin(rx) + z * Math.cos(rx);

		// 2. Y Rotation (Corrected sign: x' = x*cos - z*sin, z' = x*sin + z*cos)
		double x2 = (double) x * Math.cos(ry) - z1 * Math.sin(ry);
		double z2 = (double) x * Math.sin(ry) + z1 * Math.cos(ry);

		// 3. Z Rotation
		double x3 = x2 * Math.cos(rz) - y1 * Math.sin(rz);
		double y3 = x2 * Math.sin(rz) + y1 * Math.cos(rz);

		// // 1. X Rotation
		// double y1 = y * Math.cos(rx) - z * Math.sin(rx);
		// double z1 = y * Math.sin(rx) + z * Math.cos(rx);
		// double x1 = x;
		//
		// // 2. Y Rotation (Corrected sign: x' = x*cos - z*sin, z' = x*sin + z*cos)
		// double x2 = x1 * Math.cos(ry) - z1 * Math.sin(ry);
		// double z2 = x1 * Math.sin(ry) + z1 * Math.cos(ry);
		// double y2 = y1;
		//
		// // 3. Z Rotation
		// double x3 = x2 * Math.cos(rz) - y2 * Math.sin(rz);
		// double y3 = x2 * Math.sin(rz) + y2 * Math.cos(rz);
		// double z3 = z2;

		return new float[]{(float) x3, (float) y3, (float) z2};
	}
}