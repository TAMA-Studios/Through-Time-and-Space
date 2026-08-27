/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class GeoRenderer {

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

		poseStack.translate((bone.pivotX + bone.animPosX) / 16.0, (bone.pivotY + bone.animPosY) / 16.0,
				(bone.pivotZ + bone.animPosZ) / 16.0);

		float totalRotX = bone.baseRotX + bone.animRotX;
		float totalRotY = bone.baseRotY + bone.animRotY;
		float totalRotZ = bone.baseRotZ + bone.animRotZ;

		if (totalRotZ != 0)
			poseStack.mulPose(new Quaternionf().rotationZ((float) Math.toRadians(-totalRotZ)));
		if (totalRotY != 0)
			poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-totalRotY)));
		if (totalRotX != 0)
			poseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(-totalRotX)));

		if (bone.animScaleX != 1 || bone.animScaleY != 1 || bone.animScaleZ != 1) {
			poseStack.scale(bone.animScaleX, bone.animScaleY, bone.animScaleZ);
		}

		Matrix4f pose = poseStack.last().pose();
		Matrix3f normalMat = poseStack.last().normal();

		for (GeoCube cube : bone.cubes) {
			for (GeoCube.LocalQuad quad : cube.getBakedQuads(texW, texH)) {
				Vector3f n = new Vector3f(quad.nx, quad.ny, quad.nz);
				n.mul(normalMat);
				n.normalize();

				for (GeoCube.LocalVertex v : quad.vertices) {
					Vector4f pos = new Vector4f(v.x / 16.0f, v.y / 16.0f, v.z / 16.0f, 1.0f);
					pos.mul(pose);
					buffer.vertex(pos.x(), pos.y(), pos.z()).color(r, g, b, a).uv(v.u, v.v).overlayCoords(overlay)
							.uv2(packedLight).normal(n.x(), n.y(), n.z()).endVertex();
				}
			}
		}

		for (GeoBone child : bone.children) {
			renderBone(child, texW, texH, poseStack, buffer, packedLight, overlay, r, g, b, a);
		}

		poseStack.popPose();
	}
}