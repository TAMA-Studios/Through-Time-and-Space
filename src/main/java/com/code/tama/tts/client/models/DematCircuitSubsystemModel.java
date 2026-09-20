/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.models;// Made with Blockbench 5.1.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class DematCircuitSubsystemModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "dematcircuitsubsystem"), "main");
	private final ModelPart bone;
	private final ModelPart bone10;
	private final ModelPart bone9;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart bone7;
	private final ModelPart bone8;

	public DematCircuitSubsystemModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone10 = this.bone.getChild("bone10");
		this.bone9 = this.bone10.getChild("bone9");
		this.bone2 = this.bone10.getChild("bone2");
		this.bone3 = this.bone10.getChild("bone3");
		this.bone4 = this.bone10.getChild("bone4");
		this.bone5 = this.bone10.getChild("bone5");
		this.bone6 = this.bone10.getChild("bone6");
		this.bone7 = this.bone10.getChild("bone7");
		this.bone8 = this.bone.getChild("bone8");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-28, 100)
				.addBox(-16.0F, -15.248F, -14.0F, 32.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(-28, 100)
				.addBox(-16.0F, 0.726F, -14.0F, 32.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bone
				.addOrReplaceChild("cube_r1",
						CubeListBuilder.create().texOffs(84, 28).addBox(-3.0F, -3.0F, -1.05F, 6.0F, 6.0F, 16.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone10 = bone.addOrReplaceChild("bone10", CubeListBuilder.create(),
				PartPose.offset(5.0F, -7.25F, -3.0F));

		PartDefinition bone9 = bone10.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(15, 15)
				.addBox(-13.0727F, -8.0F, -10.9297F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 28)
				.addBox(-8.0727F, -3.0F, -10.9297F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone2 = bone10.addOrReplaceChild("bone2",
				CubeListBuilder.create().texOffs(15, 15)
						.addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 28)
						.addBox(-3.0F, -3.0F, -8.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone3 = bone10.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(15, 15)
				.addBox(-2.9282F, -8.0F, -10.9282F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 28)
				.addBox(2.0718F, -3.0F, -10.9282F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone4 = bone10.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(15, 15)
				.addBox(-2.9273F, -8.0F, -16.7851F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 28)
				.addBox(2.0727F, -3.0F, -16.7851F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone5 = bone10.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(15, 15)
				.addBox(-7.9991F, -8.0F, -19.7143F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 28)
				.addBox(-2.9991F, -3.0F, -19.7143F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone6 = bone10.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(16, 16)
				.addBox(-7.9991F, -8.0F, -19.7143F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(84, 28)
				.addBox(-2.9991F, -3.0F, -19.7143F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone7 = bone10.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(15, 15)
				.addBox(-13.0718F, -8.0F, -16.7866F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 28)
				.addBox(-8.0718F, -3.0F, -16.7866F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone8 = bone.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 44)
				.addBox(-1.0F, -8.0F, 9.0F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)).texOffs(34, 68)
				.addBox(-7.0F, -14.0F, 30.0F, 14.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(1, 84)
				.addBox(-4.0F, -11.0F, 31.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 38)
				.addBox(5.5F, -8.51F, -25.6863F, 10.0F, 3.0F, 3.0F, new CubeDeformation(-0.01F)).texOffs(0, 38)
				.addBox(-15.5F, -8.51F, -25.6863F, 10.0F, 3.0F, 3.0F, new CubeDeformation(-0.01F)).texOffs(52, 28)
				.addBox(-6.018F, -9.5022F, -26.6863F, 1.0F, 5.0F, 5.0F, new CubeDeformation(-0.01F)).texOffs(52, 28)
				.mirror().addBox(5.0248F, -9.5022F, -26.6863F, 1.0F, 5.0F, 5.0F, new CubeDeformation(-0.01F))
				.mirror(false), PartPose.offset(0.0F, -0.1F, -1.75F));

		PartDefinition cube_r2 = bone8.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(1, 84)
						.addBox(-5.75F, -5.0F, 30.45F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(34, 68)
						.addBox(-8.75F, -8.0F, 29.45F, 14.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 0)
						.addBox(-2.75F, -2.0F, 8.45F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, -0.25F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r3 = bone8.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(0, 38).addBox(-16.2571F, -9.0F, -28.009F, 31.0F, 3.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.395F, 0.5F, 1.096F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r4 = bone8.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(0, 38).addBox(-16.2571F, -9.0F, -28.009F, 31.0F, 3.0F, 3.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(0.6243F, 0.49F, -0.0698F, -3.1416F, 1.0472F, 3.1416F));

		PartDefinition cube_r5 = bone8.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(0, 38).addBox(-15.7571F, -9.0F, -28.009F, 31.0F, 3.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2571F, 0.5F, -0.009F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r6 = bone8.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(0, 38).addBox(-16.2571F, -9.0F, -28.009F, 31.0F, 3.0F, 3.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-1.3813F, 0.49F, 1.2414F, 3.1416F, -1.0472F, -3.1416F));

		PartDefinition cube_r7 = bone8.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(0, 38).addBox(-16.2571F, -9.0F, -28.009F, 31.0F, 3.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.638F, 0.5F, 2.4073F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r8 = bone8.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(25, 28).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-3.9008F, -3.098F, -24.1863F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r9 = bone8.addOrReplaceChild("cube_r9",
				CubeListBuilder.create().texOffs(25, 28).mirror()
						.addBox(1.125F, -0.3F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false),
				PartPose.offsetAndRotation(2.2034F, -14.1486F, -24.1863F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r10 = bone8.addOrReplaceChild("cube_r10",
				CubeListBuilder.create().texOffs(52, 28).mirror()
						.addBox(1.125F, -0.3F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false),
				PartPose.offsetAndRotation(2.2034F, -3.1058F, -24.1863F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r11 = bone8.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(25, 28).mirror()
						.addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false),
				PartPose.offsetAndRotation(3.9076F, -3.098F, -24.1863F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r12 = bone8.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(25, 28).mirror()
						.addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false),
				PartPose.offsetAndRotation(3.9076F, -10.9064F, -24.1863F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r13 = bone8.addOrReplaceChild("cube_r13",
				CubeListBuilder.create().texOffs(25, 28).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-3.9008F, -10.9064F, -24.1863F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r14 = bone8.addOrReplaceChild("cube_r14",
				CubeListBuilder.create().texOffs(1, 84)
						.addBox(-2.25F, -5.0F, 30.45F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(34, 68)
						.addBox(-5.25F, -8.0F, 29.45F, 14.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(46, 44)
						.addBox(0.75F, -2.0F, 8.45F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, -0.25F, 0.0F, -2.0944F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}