/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.models;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.core.tileentities.VortexCannonTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class VortexCannon<T extends VortexCannonTile> extends HierarchicalModel<Entity>
		implements
			IAnimateableModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "custommodel"), "main");
	private final ModelPart base;
	private final ModelPart wheels;
	private final ModelPart root;

	public VortexCannon(ModelPart root) {
		this.root = root;
		this.base = root.getChild("base");
		this.wheels = this.base.getChild("wheels");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(76, 60)
						.addBox(-1.0F, -1.578F, -16.5456F, 2.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 14.578F, 1.5456F));

		PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -16.0F, -11.0F, 10.0F, 10.0F, 46.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 9.422F, -1.5456F, 0.3054F, 0.0F, 0.0F));

		PartDefinition wheels = base.addOrReplaceChild("wheels",
				CubeListBuilder.create().texOffs(0, 56).mirror()
						.addBox(-10.0F, -8.3333F, -8.5F, 2.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(0, 56).addBox(8.0F, -8.3333F, -8.5F, 2.0F, 17.0F, 17.0F, new CubeDeformation(0.0F))
						.texOffs(73, 56).addBox(-10.0F, -1.3333F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.7553F, 5.9544F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
	                      float headPitch) {

	}

	@Override
	public void SetupAnimations(T tile, float ageInTicks) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(tile.state, VortexCannonAnimation.FIRE, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
	                           float red, float green, float blue, float alpha) {
		this.base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}

