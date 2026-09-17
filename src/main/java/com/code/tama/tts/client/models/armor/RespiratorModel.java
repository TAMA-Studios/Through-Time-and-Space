/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.models.armor;// Made with Blockbench 5.1.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class RespiratorModel<T extends LivingEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(UniversalCommon.modRL("respirator"),
			"main");
	private final ModelPart steve;
	public final ModelPart Head;
	private final ModelPart bone2;
	private final ModelPart bone3;

	public RespiratorModel(ModelPart root) {
		this.steve = root.getChild("steve");
		this.Head = root.getChild("Head");
		this.bone2 = this.Head.getChild("bone2");
		this.bone3 = this.Head.getChild("bone3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition steve = partdefinition.addOrReplaceChild("steve",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-4.0F, -23.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 16)
						.addBox(-4.0F, -15.5F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(24, 16)
						.addBox(-8.0F, -15.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 32)
						.addBox(4.0F, -15.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(32, 0)
						.addBox(-4.0F, -3.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(16, 32)
						.addBox(0.0F, -3.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 15.5F, 0.0F));

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 48).addBox(
				-0.5F, -27.5F, -4.9F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 48.0F, 0.0F));

		PartDefinition cube_r1 = Head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(32, 40).addBox(-2.0F, -3.0F, -1.0F, 3.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.153F, -23.75F, -3.6456F, 0.0F, 0.3054F, 0.0F));

		PartDefinition cube_r2 = Head.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -3.0F, -1.0F, 4.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.15F, -23.75F, -3.4F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r3 = Head.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(32, 32).addBox(-3.0F, -3.0F, -1.0F, 4.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.15F, -23.75F, -3.4F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r4 = Head.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(48, 12).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(-1.5515F, -26.7903F, -4.2494F, -0.1547F, 0.2711F, -0.0837F));

		PartDefinition cube_r5 = Head.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(48, 10).addBox(-2.5F, -0.5F, -2.5F, 4.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(-1.0178F, -26.2094F, -2.4512F, -0.4461F, 0.2163F, -0.2062F));

		PartDefinition cube_r6 = Head.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(12, 48).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(1.5515F, -26.7903F, -4.2494F, -0.1547F, -0.2711F, 0.0837F));

		PartDefinition cube_r7 = Head.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(48, 8).addBox(-1.5F, -0.5F, -2.5F, 4.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(1.0178F, -26.2094F, -2.4512F, -0.4461F, -0.2163F, 0.2062F));

		PartDefinition cube_r8 = Head.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(40, 24).addBox(-1.0F, -3.0F, -1.0F, 3.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.153F, -23.75F, -3.6456F, 0.0F, -0.3054F, 0.0F));

		PartDefinition bone2 = Head.addOrReplaceChild("bone2", CubeListBuilder.create(),
				PartPose.offset(-1.553F, -23.75F, -3.5456F));

		PartDefinition cube_r9 = bone2
				.addOrReplaceChild("cube_r9",
						CubeListBuilder.create().texOffs(48, 46).addBox(-0.25F, -2.0F, -1.75F, 1.0F, 1.0F, 1.0F,
								new CubeDeformation(-0.2F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

		PartDefinition cube_r10 = bone2.addOrReplaceChild("cube_r10",
				CubeListBuilder.create().texOffs(38, 48).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(-0.2994F, -1.5F, -1.4157F, 0.0F, 1.3526F, 0.0F));

		PartDefinition cube_r11 = bone2.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(32, 48).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(-1.7619F, -1.5F, -1.6534F, 0.0F, 1.7017F, 0.0F));

		PartDefinition cube_r12 = bone2.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(12, 50).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(-2.894F, -1.5F, -1.4048F, 0.0F, 2.8362F, 0.0F));

		PartDefinition cube_r13 = bone2.addOrReplaceChild("cube_r13",
				CubeListBuilder.create().texOffs(48, 4).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(-3.3701F, -1.5F, 1.2213F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r14 = bone2.addOrReplaceChild("cube_r14",
				CubeListBuilder.create().texOffs(44, 48).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(-3.3128F, -1.5F, 0.0814F, 0.0F, 3.0107F, 0.0F));

		PartDefinition bone3 = Head.addOrReplaceChild("bone3", CubeListBuilder.create(),
				PartPose.offset(1.553F, -23.75F, -3.5456F));

		PartDefinition cube_r15 = bone3.addOrReplaceChild("cube_r15",
				CubeListBuilder.create().texOffs(48, 46).mirror()
						.addBox(-0.75F, -2.0F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

		PartDefinition cube_r16 = bone3.addOrReplaceChild("cube_r16",
				CubeListBuilder.create().texOffs(38, 48).mirror()
						.addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false),
				PartPose.offsetAndRotation(0.2994F, -1.5F, -1.4157F, 0.0F, -1.3526F, 0.0F));

		PartDefinition cube_r17 = bone3.addOrReplaceChild("cube_r17",
				CubeListBuilder.create().texOffs(32, 48).mirror()
						.addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false),
				PartPose.offsetAndRotation(1.7619F, -1.5F, -1.6534F, 0.0F, -1.7017F, 0.0F));

		PartDefinition cube_r18 = bone3.addOrReplaceChild("cube_r18",
				CubeListBuilder.create().texOffs(12, 50).mirror()
						.addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false),
				PartPose.offsetAndRotation(2.894F, -1.5F, -1.4048F, 0.0F, -2.8362F, 0.0F));

		PartDefinition cube_r19 = bone3.addOrReplaceChild("cube_r19",
				CubeListBuilder.create().texOffs(48, 4).mirror()
						.addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false),
				PartPose.offsetAndRotation(3.3701F, -1.5F, 1.2213F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r20 = bone3.addOrReplaceChild("cube_r20",
				CubeListBuilder.create().texOffs(44, 48).mirror()
						.addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false),
				PartPose.offsetAndRotation(3.3128F, -1.5F, 0.0814F, 0.0F, -3.0107F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		// steve.render(poseStack, vertexConsumer, packedLight, packedOverlay, red,
		// green, blue, alpha);
		poseStack.pushPose();
		poseStack.translate(0, -1, 0);
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}
}