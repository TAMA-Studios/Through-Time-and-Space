/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.models; // Made with Blockbench 4.12.5

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

public class CoralConsoleTopper<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("tts", "coralconsoletopper"), "main");
	private final ModelPart topper;
	private final ModelPart bone88;
	private final ModelPart walls;
	private final ModelPart bone96;
	private final ModelPart bone89;
	private final ModelPart bone90;
	private final ModelPart bone91;
	private final ModelPart bone92;
	private final ModelPart bone93;
	private final ModelPart bone94;
	private final ModelPart bone95;
	private final ModelPart corners;
	private final ModelPart corners2;
	private final ModelPart corners3;
	private final ModelPart corners4;
	private final ModelPart corners5;
	private final ModelPart corners6;

	public CoralConsoleTopper(ModelPart root) {
		this.topper = root.getChild("topper");
		this.bone88 = this.topper.getChild("bone88");
		this.walls = this.bone88.getChild("walls");
		this.bone96 = this.walls.getChild("bone96");
		this.bone89 = this.bone96.getChild("bone89");
		this.bone90 = this.bone96.getChild("bone90");
		this.bone91 = this.bone96.getChild("bone91");
		this.bone92 = this.bone96.getChild("bone92");
		this.bone93 = this.bone96.getChild("bone93");
		this.bone94 = this.bone96.getChild("bone94");
		this.bone95 = this.walls.getChild("bone95");
		this.corners = this.bone88.getChild("corners");
		this.corners2 = this.bone88.getChild("corners2");
		this.corners3 = this.bone88.getChild("corners3");
		this.corners4 = this.bone88.getChild("corners4");
		this.corners5 = this.bone88.getChild("corners5");
		this.corners6 = this.bone88.getChild("corners6");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition topper = partdefinition.addOrReplaceChild("topper", CubeListBuilder.create(),
				PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition bone88 = topper.addOrReplaceChild("bone88", CubeListBuilder.create(),
				PartPose.offset(0.0F, -19.7F, 0.0F));

		PartDefinition walls = bone88.addOrReplaceChild("walls", CubeListBuilder.create(),
				PartPose.offset(-11.6878F, -43.3F, 1.0801F));

		PartDefinition cube_r1 = walls.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(230, 70).addBox(-10.0F, -128.0F, -3.0F, 10.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(16.7321F, 0.0F, 9.6603F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r2 = walls.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(230, 70).mirror()
						.addBox(0.0F, -128.0F, -3.0F, 10.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(18.4641F, 0.0F, 8.6603F, -0.2618F, 1.0472F, 0.0F));

		PartDefinition cube_r3 = walls.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(230, 70).addBox(0.0F, -128.0F, 0.0F, 10.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(18.4641F, 0.0F, -10.6603F, 0.2618F, -1.0472F, 0.0F));

		PartDefinition cube_r4 = walls.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(230, 70).mirror()
						.addBox(-10.0F, -128.0F, 0.0F, 10.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(16.7321F, 0.0F, -11.6603F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r5 = walls.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(230, 70).addBox(0.0F, -128.0F, 0.0F, 10.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.2618F, 1.0472F, 0.0F));

		PartDefinition cube_r6 = walls.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(230, 70).mirror()
						.addBox(0.0F, -128.0F, -3.0F, 10.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, -1.0472F, 0.0F));

		PartDefinition bone96 = walls.addOrReplaceChild("bone96", CubeListBuilder.create(),
				PartPose.offset(15.0916F, -92.2297F, -5.9201F));

		PartDefinition bone89 = bone96.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(146, 56)
				.addBox(-28.2886F, -192.0F, 40.0836F, 51.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-0.7038F, 156.2297F, 6.74F));

		PartDefinition bone90 = bone96.addOrReplaceChild("bone90",
				CubeListBuilder.create().texOffs(145, 56).addBox(-25.6055F, -4.5244F, -4.9988F, 52.0F, 6.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(36.5414F, -31.2459F, 28.6479F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone91 = bone96.addOrReplaceChild("bone91",
				CubeListBuilder.create().texOffs(146, 56).addBox(-21.2728F, -4.5244F, -2.0418F, 52.0F, 6.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(36.5414F, -31.2459F, -13.1534F, -3.1416F, 1.0472F, 3.1416F));

		PartDefinition bone92 = bone96.addOrReplaceChild("bone92",
				CubeListBuilder.create().texOffs(146, 56).addBox(-22.0F, -4.5244F, 1.9641F, 51.0F, 6.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0076F, -31.2459F, -35.279F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition bone93 = bone96.addOrReplaceChild("bone93",
				CubeListBuilder.create().texOffs(146, 56).addBox(-26.0F, -4.5244F, 3.6961F, 52.0F, 6.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-36.1933F, -31.2459F, -14.3784F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition bone94 = bone96.addOrReplaceChild("bone94",
				CubeListBuilder.create().texOffs(146, 56).addBox(-29.0F, -4.5244F, 1.9641F, 52.0F, 6.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-36.1933F, -31.2459F, 27.4229F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone95 = walls.addOrReplaceChild("bone95", CubeListBuilder.create(),
				PartPose.offset(12.7321F, -123.4756F, 41.7284F));

		PartDefinition corners = bone88.addOrReplaceChild("corners", CubeListBuilder.create(),
				PartPose.offset(0.0F, -43.3F, 0.0F));

		PartDefinition cube_r7 = corners.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(173, 66).mirror()
						.addBox(-1.125F, -128.0F, -3.0F, 22.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(6.7763F, 0.0F, 9.7404F, -0.4874F, 0.9925F, -0.2643F));

		PartDefinition cube_r8 = corners.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(173, 66).addBox(-21.875F, -128.0F, -3.0F, 23.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0442F, 0.0F, 10.7404F, -0.2591F, 0.0383F, 0.1434F));

		PartDefinition corners2 = bone88.addOrReplaceChild("corners2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -43.3F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r9 = corners2.addOrReplaceChild("cube_r9",
				CubeListBuilder.create().texOffs(173, 66).mirror()
						.addBox(-1.125F, -128.0F, -3.0F, 22.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(6.7763F, 0.0F, 9.7404F, -0.4874F, 0.9925F, -0.2643F));

		PartDefinition cube_r10 = corners2.addOrReplaceChild("cube_r10",
				CubeListBuilder.create().texOffs(173, 66).addBox(-21.875F, -128.0F, -3.0F, 23.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0442F, 0.0F, 10.7404F, -0.2591F, 0.0383F, 0.1434F));

		PartDefinition corners3 = bone88.addOrReplaceChild("corners3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -43.3F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

		PartDefinition cube_r11 = corners3.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(173, 66).mirror()
						.addBox(-1.125F, -128.0F, -3.0F, 22.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(6.7763F, 0.0F, 9.7404F, -0.4874F, 0.9925F, -0.2643F));

		PartDefinition cube_r12 = corners3.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(173, 66).addBox(-21.875F, -128.0F, -3.0F, 23.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0442F, 0.0F, 10.7404F, -0.2591F, 0.0383F, 0.1434F));

		PartDefinition corners4 = bone88.addOrReplaceChild("corners4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -43.3F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r13 = corners4.addOrReplaceChild("cube_r13",
				CubeListBuilder.create().texOffs(173, 66).mirror()
						.addBox(-1.125F, -128.0F, -3.0F, 22.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(6.7763F, 0.0F, 9.7404F, -0.4874F, 0.9925F, -0.2643F));

		PartDefinition cube_r14 = corners4.addOrReplaceChild("cube_r14",
				CubeListBuilder.create().texOffs(173, 66).addBox(-21.875F, -128.0F, -3.0F, 23.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0442F, 0.0F, 10.7404F, -0.2591F, 0.0383F, 0.1434F));

		PartDefinition corners5 = bone88.addOrReplaceChild("corners5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -43.3F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition cube_r15 = corners5.addOrReplaceChild("cube_r15",
				CubeListBuilder.create().texOffs(173, 66).mirror()
						.addBox(-1.125F, -128.0F, -3.0F, 22.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(6.7763F, 0.0F, 9.7404F, -0.4874F, 0.9925F, -0.2643F));

		PartDefinition cube_r16 = corners5.addOrReplaceChild("cube_r16",
				CubeListBuilder.create().texOffs(173, 66).addBox(-21.875F, -128.0F, -3.0F, 23.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0442F, 0.0F, 10.7404F, -0.2591F, 0.0383F, 0.1434F));

		PartDefinition corners6 = bone88.addOrReplaceChild("corners6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -43.3F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r17 = corners6.addOrReplaceChild("cube_r17",
				CubeListBuilder.create().texOffs(173, 66).mirror()
						.addBox(-1.125F, -128.0F, -3.0F, 22.0F, 128.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(6.7763F, 0.0F, 9.7404F, -0.4874F, 0.9925F, -0.2643F));

		PartDefinition cube_r18 = corners6.addOrReplaceChild("cube_r18",
				CubeListBuilder.create().texOffs(173, 66).addBox(-21.875F, -128.0F, -3.0F, 23.0F, 128.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0442F, 0.0F, 10.7404F, -0.2591F, 0.0383F, 0.1434F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		topper.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}