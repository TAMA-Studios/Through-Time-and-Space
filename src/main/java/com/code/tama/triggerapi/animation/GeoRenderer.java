/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.lwjgl.system.MemoryUtil;

/**
 * Shared rendering logic for both items and blocks. This is the piece that
 * replaces "swap the whole baked model" — instead we push one PoseStack
 * transform per bone and render that bone's cached local quads, so a single
 * baked model can show any pose, not just the ones you pre-baked.
 */
public class GeoRenderer {

	/**
	 * @param poseStack
	 *            current pose stack (already positioned for the block/item)
	 * @param buffer
	 *            target vertex consumer (respects your render type / texture)
	 * @param packedLight
	 *            combined light
	 * @param overlay
	 *            overlay coords (usually OverlayTexture.NO_OVERLAY)
	 * @param r,g,b,a
	 *            tint, usually 1,1,1,1
	 */
	public static void render(GeoModel model, PoseStack poseStack, VertexConsumer buffer, int packedLight, int overlay,
			float r, float g, float b, float a) {
		for (GeoBone root : model.rootBones()) {
			renderBone(root, model.textureWidth(), model.textureHeight(), poseStack, buffer, packedLight, overlay, r, g,
					b, a);
		}
	}

	private static void renderBone(GeoBone bone, int texW, int texH, PoseStack poseStack, VertexConsumer buffer,
			int packedLight, int overlay, float r, float g, float b, float a) {
		poseStack.pushPose();

		// Move to pivot (converted from pixel units to block-space units)
		poseStack.translate((bone.pivotX + bone.animPosX) / 16.0, (bone.pivotY + bone.animPosY) / 16.0,
				(bone.pivotZ + bone.animPosZ) / 16.0);

		float totalRotX = bone.baseRotX + bone.animRotX;
		float totalRotY = bone.baseRotY + bone.animRotY;
		float totalRotZ = bone.baseRotZ + bone.animRotZ;

		// Order (X-then-Y-then-Z applied to the point, i.e. Rz,Ry,Rx call order onto
		// the
		// stack) is verified against Bedrock's documented rotation order and against a
		// real
		// bone-rotation model (matrix.geo.json). Angle SIGN is inferred rather than
		// independently verified for bones specifically — GeoCube's rotation needed
		// negating
		// (confirmed by hand-tracing real hinge geometry against its expected
		// connection
		// point), and it would be unusual for Bedrock to use opposite rotation
		// directions for
		// bones vs cubes, so the same negation is applied here. If a bone-rotated (not
		// just
		// cube-rotated) model still spins the wrong way after this, that's the thing to
		// double check first.
		if (totalRotZ != 0)
			poseStack.mulPose(new org.joml.Quaternionf().rotationZ((float) Math.toRadians(-totalRotZ)));
		if (totalRotY != 0)
			poseStack.mulPose(new org.joml.Quaternionf().rotationY((float) Math.toRadians(-totalRotY)));
		if (totalRotX != 0)
			poseStack.mulPose(new org.joml.Quaternionf().rotationX((float) Math.toRadians(-totalRotX)));

		if (bone.animScaleX != 1 || bone.animScaleY != 1 || bone.animScaleZ != 1) {
			poseStack.scale(bone.animScaleX, bone.animScaleY, bone.animScaleZ);
		}

		Matrix4f pose = poseStack.last().pose();
		Matrix3f normalMat = poseStack.last().normal();

		long vec = MemoryUtil.nmemAlloc(Vec4f.SIZE);
		long vec3 = MemoryUtil.nmemAlloc(Vec3f.SIZE);

		for (GeoCube cube : bone.cubes) {
			for (GeoCube.LocalQuad quad : cube.getBakedQuads(texW, texH)) {
				Vec3f.write(vec3, quad.nx, quad.ny, quad.nz); // No use in clearing it from the last loop, just
																// overwrite.
				Vec3f.mulGeneric(vec3, normalMat);

				for (GeoCube.LocalVertex v : quad.vertices) {
					// pivot offset already baked into cube coordinates relative to bone pivot,
					// remaining conversion is pixel units -> block units.

					Vec4f.write(vec, v.x / 16.0f, v.y / 16.0f, v.z / 16.0f, 1.0f);
					Vec4f.mulGeneric(vec, pose);

					buffer.vertex(Vec4f.read(vec, Vec4f.x), Vec4f.read(vec, Vec4f.y), Vec4f.read(vec, Vec4f.z))
							.color(r, g, b, a).uv(v.u, v.v).overlayCoords(overlay).uv2(packedLight)
							.normal(Vec3f.read(vec3, Vec3f.x), Vec3f.read(vec3, Vec3f.y), Vec3f.read(vec3, Vec3f.z))
							.endVertex();
				}
			}
		}

		MemoryUtil.nmemFree(vec);
		MemoryUtil.nmemFree(vec3);

		for (GeoBone child : bone.children) {
			renderBone(child, texW, texH, poseStack, buffer, packedLight, overlay, r, g, b, a);
		}

		poseStack.popPose();
	}

	public static class Vec4f {
		public static final byte x = 0, y = Float.BYTES, z = Float.BYTES * 2, t = Float.BYTES * 3,
				SIZE = Float.BYTES * 4;

		public static float read(long addr, byte index) {
			return MemoryUtil.memGetFloat(addr + index);
		}

		public static void write(long addr, byte index, float value) {
			MemoryUtil.memPutFloat(addr + index, value);
		}

		public static void write(long addr, float x, float y, float z, float t) {
			write(addr, Vec4f.x, x);
			write(addr, Vec4f.y, y);
			write(addr, Vec4f.z, z);
			write(addr, Vec4f.t, t);
		}

		static void mulGeneric(long addr, Matrix4fc mat) {
			float x = read(addr, Vec4f.x);
			float y = read(addr, Vec4f.y);
			float z = read(addr, Vec4f.z);
			float t = read(addr, Vec4f.t);
			write(addr, Vec4f.x, org.joml.Math.fma(mat.m00(), x,
					org.joml.Math.fma(mat.m10(), y, org.joml.Math.fma(mat.m20(), z, mat.m30() * t))));
			write(addr, Vec4f.y, org.joml.Math.fma(mat.m01(), x,
					org.joml.Math.fma(mat.m11(), y, org.joml.Math.fma(mat.m21(), z, mat.m31() * t))));
			write(addr, Vec4f.z, org.joml.Math.fma(mat.m02(), x,
					org.joml.Math.fma(mat.m12(), y, org.joml.Math.fma(mat.m22(), z, mat.m32() * t))));
			write(addr, Vec4f.t, org.joml.Math.fma(mat.m03(), x,
					org.joml.Math.fma(mat.m13(), y, org.joml.Math.fma(mat.m23(), z, mat.m33() * t))));
		}
	}

	public static class Vec3f {
		public static final byte x = 0, y = Float.BYTES, z = Float.BYTES * 2, SIZE = Float.BYTES * 3;

		public static float read(long addr, byte index) {
			return MemoryUtil.memGetFloat(addr + index);
		}

		public static void write(long addr, byte index, float value) {
			MemoryUtil.memPutFloat(addr + index, value);
		}

		public static void write(long addr, float x, float y, float z) {
			write(addr, Vec3f.x, x);
			write(addr, Vec3f.y, y);
			write(addr, Vec3f.z, z);
		}

		static void mulGeneric(long addr, Matrix3f mat) {
			float lx = read(addr, Vec3f.x);
			float ly = read(addr, Vec3f.y);
			float lz = read(addr, Vec3f.z);
			write(addr, Vec3f.x, org.joml.Math.fma(mat.m00(), lx, org.joml.Math.fma(mat.m10(), ly, mat.m20() * lz)));
			write(addr, Vec3f.y, org.joml.Math.fma(mat.m01(), lx, org.joml.Math.fma(mat.m11(), ly, mat.m21() * lz)));
			write(addr, Vec3f.z, org.joml.Math.fma(mat.m02(), lx, org.joml.Math.fma(mat.m12(), ly, mat.m22() * lz)));
		}
	}
}