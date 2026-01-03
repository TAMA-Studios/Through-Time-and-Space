/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.models;// Made with Blockbench 5.0.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.server.tileentities.EmptyArtificialShellTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ShellBaseModel<T extends EmptyArtificialShellTile> extends HierarchicalModel<Entity>
		implements
			IAnimateableModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(TTSMod.MODID, "shellbase"), "main");
	private final ModelPart root;
	private final ModelPart base;
	private final ModelPart beam1;
	private final ModelPart beam2;
	private final ModelPart beam3;
	private final ModelPart beam4;
	private final ModelPart baseTop;
	private final ModelPart plate1;
	private final ModelPart plate2;
	private final ModelPart plate3;
	private final ModelPart plate4;

	public ShellBaseModel(ModelPart root) {
		this.root = root;
		this.base = root.getChild("base");
		this.beam1 = root.getChild("beam1");
		this.beam2 = root.getChild("beam2");
		this.beam3 = root.getChild("beam3");
		this.beam4 = root.getChild("beam4");
		this.baseTop = root.getChild("baseTop");
		this.plate1 = root.getChild("plate1");
		this.plate2 = root.getChild("plate2");
		this.plate3 = root.getChild("plate3");
		this.plate4 = root.getChild("plate4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-8.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition beam1 = partdefinition.addOrReplaceChild("beam1", CubeListBuilder.create().texOffs(0, 16)
				.addBox(-8.0F, -29.0F, -8.0F, 3.0F, 26.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition beam2 = partdefinition.addOrReplaceChild("beam2", CubeListBuilder.create().texOffs(0, 16).addBox(
				5.0F, -29.0F, -8.0F, 3.0F, 26.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition beam3 = partdefinition.addOrReplaceChild("beam3", CubeListBuilder.create().texOffs(0, 16)
				.addBox(-21.0F, -29.0F, 5.0F, 3.0F, 26.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(13.0F, 24.0F, 0.0F));

		PartDefinition beam4 = partdefinition.addOrReplaceChild("beam4", CubeListBuilder.create().texOffs(0, 16)
				.addBox(-8.0F, -29.0F, 5.0F, 3.0F, 26.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(13.0F, 24.0F, 0.0F));

		PartDefinition baseTop = partdefinition.addOrReplaceChild("baseTop", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-21.0F, -32.0F, -21.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(13.0F, 24.0F, 13.0F));

		PartDefinition plate1 = partdefinition.addOrReplaceChild("plate1", CubeListBuilder.create().texOffs(32, 32)
				.addBox(-8.0F, -32.0F, -8.1F, 16.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition plate2 = partdefinition
				.addOrReplaceChild("plate2",
						CubeListBuilder.create().texOffs(32, 32).addBox(-8.0F, -32.0F, -8.1F, 16.0F, 32.0F, 0.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition plate3 = partdefinition
				.addOrReplaceChild("plate3",
						CubeListBuilder.create().texOffs(32, 32).addBox(-8.0F, -32.0F, -8.1F, 16.0F, 32.0F, 0.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition plate4 = partdefinition.addOrReplaceChild("plate4",
				CubeListBuilder.create().texOffs(32, 32).addBox(-8.0F, -32.0F, -8.1F, 16.0F, 32.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		beam1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		beam2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		beam3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		beam4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		baseTop.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		plate1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		plate2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		plate3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		plate4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	public void setAllInvis() {
		this.plate1.visible = false;
		this.plate2.visible = false;
		this.plate3.visible = false;
		this.plate4.visible = false;
		this.beam1.visible = false;
		this.beam2.visible = false;
		this.beam3.visible = false;
		this.beam4.visible = false;
		this.base.visible = false;
		this.baseTop.visible = false;
	}

	@Override
	public void SetupAnimations(T tile, float ageInTicks) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		setAllInvis();
		this.plate1.visible = tile.PlasmicShellPlates > 0;
		this.plate2.visible = tile.PlasmicShellPlates > 1;
		this.plate3.visible = tile.PlasmicShellPlates > 2;
		this.plate4.visible = tile.PlasmicShellPlates > 3;
		this.beam1.visible = tile.StructuralBeams > 0;
		this.beam2.visible = tile.StructuralBeams > 1;
		this.beam3.visible = tile.StructuralBeams > 2;
		this.beam4.visible = tile.StructuralBeams > 3;
		this.base.visible = true;
		this.baseTop.visible = tile.StructuralBeams > 3;
	}
}