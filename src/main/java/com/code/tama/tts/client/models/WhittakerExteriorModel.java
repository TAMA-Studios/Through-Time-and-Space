/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.core.HierarchicalExteriorModel;
import com.code.tama.tts.core.Constants;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class WhittakerExteriorModel<T extends ExteriorTile> extends HierarchicalExteriorModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "whittaker"), "main");
	private final ModelPart Root;
	private final ModelPart LeftDoor;
	private final ModelPart RightDoor;

	public WhittakerExteriorModel(ModelPart root) {
		super(root, Constants.ExteriorModelNames.VOXEL_MOFFAT, LAYER_LOCATION);
		this.Root = root.getChild("Root");
		this.LeftDoor = this.Root.getChild("LeftDoor");
		this.RightDoor = this.Root.getChild("RightDoor");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create().texOffs(0, 0).addBox(-26.0F, -5.0F, -26.0F, 52.0F, 5.0F, 52.0F, new CubeDeformation(0.0F))
		.texOffs(0, 57).addBox(-25.0F, -6.0F, -25.0F, 50.0F, 1.0F, 50.0F, new CubeDeformation(0.0F))
		.texOffs(72, 157).addBox(-24.0F, -88.0F, -24.0F, 6.0F, 82.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 157).addBox(-24.0F, -88.0F, 18.0F, 6.0F, 82.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 157).addBox(18.0F, -88.0F, 18.0F, 6.0F, 82.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(48, 157).addBox(18.0F, -88.0F, -24.0F, 6.0F, 82.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(28, 64).addBox(19.0F, -91.0F, -23.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 57).addBox(-23.0F, -91.0F, -23.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 40).addBox(-23.0F, -91.0F, 19.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 33).addBox(19.0F, -91.0F, 19.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(270, 286).addBox(17.0F, -80.0F, -23.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(266, 286).addBox(-18.0F, -80.0F, -23.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(90, 157).addBox(-18.0F, -81.0F, -23.0F, 36.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(258, 286).addBox(-23.0F, -80.0F, 17.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 159).addBox(-23.0F, -81.0F, -18.0F, 1.0F, 1.0F, 36.0F, new CubeDeformation(0.0F))
		.texOffs(262, 286).addBox(-23.0F, -80.0F, -18.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(250, 286).addBox(17.0F, -80.0F, 22.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(150, 105).addBox(-18.0F, -81.0F, 22.0F, 36.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(254, 286).addBox(-18.0F, -80.0F, 22.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(90, 278).addBox(22.0F, -80.0F, -18.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(156, 0).addBox(22.0F, -81.0F, -18.0F, 1.0F, 1.0F, 36.0F, new CubeDeformation(0.0F))
		.texOffs(246, 286).addBox(22.0F, -80.0F, 17.0F, 1.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(232, 0).addBox(-21.0F, -87.0F, -26.0F, 42.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(150, 57).addBox(-26.0F, -87.0F, -21.0F, 4.0F, 6.0F, 42.0F, new CubeDeformation(0.0F))
		.texOffs(204, 210).addBox(-21.0F, -87.0F, 22.0F, 42.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(134, 147).addBox(22.0F, -87.0F, -21.0F, 4.0F, 6.0F, 42.0F, new CubeDeformation(0.0F))
		.texOffs(0, 108).addBox(-22.0F, -92.0F, -22.0F, 44.0F, 5.0F, 44.0F, new CubeDeformation(0.0F))
		.texOffs(132, 108).addBox(-18.0F, -95.0F, -18.0F, 36.0F, 3.0F, 36.0F, new CubeDeformation(0.0F))
		.texOffs(42, 278).addBox(-22.0F, -80.0F, 15.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 278).addBox(-22.0F, -80.0F, 0.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 278).addBox(-23.0F, -80.0F, -1.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(236, 253).addBox(-22.0F, -9.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(228, 147).addBox(-21.5F, -24.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(62, 245).addBox(-22.0F, -27.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(204, 225).addBox(-21.5F, -42.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(0, 245).addBox(-22.0F, -45.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(172, 210).addBox(-21.5F, -60.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(128, 240).addBox(-22.0F, -63.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(160, 255).addBox(-22.0F, -80.0F, -15.0F, 1.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(96, 210).addBox(-21.5F, -78.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(24, 278).addBox(-22.0F, -80.0F, -2.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 278).addBox(-22.0F, -80.0F, -17.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 278).addBox(15.0F, -80.0F, 21.0F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 278).addBox(0.0F, -80.0F, 21.0F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 278).addBox(-1.0F, -80.0F, 22.0F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 196).addBox(-15.0F, -9.0F, 21.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(184, 147).addBox(-15.0F, -24.0F, 20.5F, 30.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 200).addBox(-15.0F, -27.0F, 21.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(232, 55).addBox(-15.0F, -42.0F, 20.5F, 30.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 204).addBox(-15.0F, -45.0F, 21.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(258, 192).addBox(-15.0F, -60.0F, 20.5F, 30.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(204, 220).addBox(-15.0F, -63.0F, 21.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(232, 10).addBox(-15.0F, -80.0F, 21.0F, 30.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(260, 147).addBox(-15.0F, -78.0F, 20.5F, 30.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(66, 278).addBox(-2.0F, -80.0F, 21.0F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 278).addBox(-17.0F, -80.0F, 21.0F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(222, 270).addBox(21.0F, -80.0F, -17.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(228, 270).addBox(21.0F, -80.0F, -2.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 278).addBox(22.0F, -80.0F, -1.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 22).addBox(21.0F, -9.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(140, 195).addBox(20.5F, -24.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(232, 75).addBox(21.0F, -27.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(196, 165).addBox(20.5F, -42.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(236, 220).addBox(21.0F, -45.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(200, 7).addBox(20.5F, -60.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(240, 108).addBox(21.0F, -63.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(94, 255).addBox(21.0F, -80.0F, -15.0F, 1.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(200, 52).addBox(20.5F, -78.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(6, 278).addBox(21.0F, -80.0F, 0.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 278).addBox(21.0F, -80.0F, 15.0F, 1.0F, 74.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 71).addBox(-2.5F, -102.0F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = Root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 77).addBox(-2.5F, -102.0F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 10).addBox(-3.0F, -103.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(27, 26).addBox(-3.0F, -97.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(28, 17).addBox(-2.0F, -102.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 47).addBox(-2.0F, -104.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(-2.5F, -96.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition LeftDoor = Root.addOrReplaceChild("LeftDoor", CubeListBuilder.create().texOffs(240, 286).addBox(-0.0833F, -37.7692F, 0.0167F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(234, 286).addBox(14.9167F, -37.7692F, 0.0167F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(1.9167F, -12.2692F, -0.4833F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 278).addBox(15.9167F, -37.7692F, -0.9833F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 132).addBox(1.9167F, 33.2308F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 124).addBox(1.9167F, 18.2308F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 128).addBox(1.9167F, 15.2308F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 108).addBox(1.9167F, 0.2308F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 124).addBox(1.9167F, -2.7692F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 89).addBox(1.9167F, -17.7692F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 120).addBox(1.9167F, -20.7692F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 139).addBox(1.9167F, -37.7692F, 0.0167F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 73).addBox(1.9167F, -35.7692F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.9167F, -42.2308F, -22.0167F));

		PartDefinition RightDoor = Root.addOrReplaceChild("RightDoor", CubeListBuilder.create().texOffs(155, 286).addBox(-16.9167F, -37.8333F, 0.0167F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(84, 278).addBox(-1.9167F, -37.8333F, 0.0167F, 2.0F, 74.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 116).addBox(-14.9167F, 33.1667F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 57).addBox(-14.9167F, 18.1667F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 112).addBox(-14.9167F, 15.1667F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-14.9167F, 0.1667F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 108).addBox(-14.9167F, -2.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-14.9167F, -17.8333F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 48).addBox(-14.9167F, -20.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 136).addBox(-14.9167F, -37.8333F, 0.0167F, 13.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-14.9167F, -35.8333F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 6).addBox(-15.9167F, -12.3333F, -0.9833F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(16.9167F, -42.1667F, -22.0167F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(180));
		poseStack.mulPose(Axis.YP.rotationDegrees(180));
		poseStack.translate(-0.5, -1.3, 0.5);
		poseStack.scale(0.43f, 0.43f, 0.43f);
		Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	@Override
	public @NotNull ModelPart root() {
		return this.Root;
	}

	@Override
	public ModelPart LeftDoor() {
		return this.LeftDoor;
	}

	@Override
	public ModelPart RightDoor() {
		return this.RightDoor;
	}

	@Override
	public ResourceLocation GetModelName() {
		return Constants.ExteriorModelNames.VOXEL_MOFFAT;
	}
}