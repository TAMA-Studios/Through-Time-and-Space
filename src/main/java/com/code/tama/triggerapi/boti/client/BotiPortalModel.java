/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BotiPortalModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart BOTI;

	public BotiPortalModel(ModelPart root) {
		this.BOTI = root.getChild("BOTI");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("BOTI", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -32.0F, 0.0F, 16.0F, 32.0F,
				0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(mesh, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		BOTI.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	public ModelPart root() {
		return BOTI;
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		// No animation
	}
}
