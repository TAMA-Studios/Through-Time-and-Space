/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models; // Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.interfaces.IAnimateableModel;
import com.code.tama.tts.server.tileentities.HartnellRotorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class HartnellRotorModel<T extends HartnellRotorTile> extends HierarchicalModel<Entity>
        implements IAnimateableModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into
    // this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(TTSMod.MODID, "hartnellrotor"), "main");
    private final ModelPart bone;
    private final ModelPart glass;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart bone10;
    private final ModelPart bone11;
    private final ModelPart bone12;
    private final ModelPart bone7;
    private final ModelPart bone8;
    private final ModelPart bone9;
    private final ModelPart bone2;

    public HartnellRotorModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.glass = this.bone.getChild("glass");
        this.bone3 = this.bone.getChild("bone3");
        this.bone4 = this.bone3.getChild("bone4");
        this.bone5 = this.bone4.getChild("bone5");
        this.bone6 = this.bone5.getChild("bone6");
        this.bone10 = this.bone3.getChild("bone10");
        this.bone11 = this.bone10.getChild("bone11");
        this.bone12 = this.bone11.getChild("bone12");
        this.bone7 = this.bone3.getChild("bone7");
        this.bone8 = this.bone7.getChild("bone8");
        this.bone9 = this.bone8.getChild("bone9");
        this.bone2 = this.bone.getChild("bone2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild(
                "bone",
                CubeListBuilder.create()
                        .texOffs(32, 45)
                        .addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 45)
                        .addBox(-4.0F, -2.5F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 52)
                        .addBox(-0.5F, -19.0F, -0.5F, 1.0F, 19.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(34, 52)
                        .addBox(-2.5F, -4.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 54)
                        .addBox(-3.5F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 54)
                        .addBox(1.5F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(103, 1)
                        .addBox(-6.5F, -13.0F, 0.0F, 13.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(108, -1)
                        .addBox(0.0F, -18.0F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32)
                        .addBox(-6.5F, -5.5F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32)
                        .addBox(-6.5F, -13.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32)
                        .addBox(-6.5F, -5.5F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32)
                        .addBox(-6.5F, -13.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 64)
                        .addBox(-4.0F, 1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition glass = bone.addOrReplaceChild(
                "glass",
                CubeListBuilder.create()
                        .texOffs(72, 1)
                        .addBox(-5.0F, -23.5F, 7.65F, 10.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition cube_r1 = glass.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create()
                        .texOffs(72, 1)
                        .addBox(-5.0F, -26.0F, 7.65F, 10.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r2 = glass.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create()
                        .texOffs(72, 1)
                        .addBox(-5.0F, -26.0F, 7.65F, 10.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r3 = glass.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create()
                        .texOffs(72, 1)
                        .addBox(-5.0F, -26.0F, 7.65F, 10.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r4 = glass.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create()
                        .texOffs(72, 1)
                        .addBox(-5.0F, -26.0F, 7.65F, 10.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r5 = glass.addOrReplaceChild(
                "cube_r5",
                CubeListBuilder.create()
                        .texOffs(72, 1)
                        .addBox(-5.0F, -26.0F, 7.65F, 10.0F, 26.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone3 = bone.addOrReplaceChild(
                "bone3",
                CubeListBuilder.create()
                        .texOffs(34, 32)
                        .addBox(-9.0F, -4.0F, -9.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(34, 32)
                        .addBox(-9.0F, -20.6F, -9.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 7.0F, 1.0F));

        PartDefinition cube_r6 = bone3.addOrReplaceChild(
                "cube_r6",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.5F, -16.801F, 6.75F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition cube_r7 = bone3.addOrReplaceChild(
                "cube_r7",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.5F, -16.801F, 6.75F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, 1.2217F, 0.0F));

        PartDefinition cube_r8 = bone3.addOrReplaceChild(
                "cube_r8",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.5F, -16.801F, 6.75F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, 2.6267F, 0.0F));

        PartDefinition cube_r9 = bone3.addOrReplaceChild(
                "cube_r9",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.5F, -16.801F, 6.75F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, -2.6093F, 0.0F));

        PartDefinition cube_r10 = bone3.addOrReplaceChild(
                "cube_r10",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.5F, -16.801F, 6.75F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, -1.213F, 0.0F));

        PartDefinition cube_r11 = bone3.addOrReplaceChild(
                "cube_r11",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.5F, -16.801F, 6.75F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, -0.6458F, 0.0F));

        PartDefinition bone4 =
                bone3.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(-1.0F, -4.0F, -1.0F));

        PartDefinition bone5 = bone4.addOrReplaceChild(
                "bone5",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.2F, -16.8F, -0.8F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(-0.8F, -0.001F, 7.55F));

        PartDefinition bone6 = bone5.addOrReplaceChild(
                "bone6",
                CubeListBuilder.create()
                        .texOffs(60, 0)
                        .addBox(-0.2F, -14.3F, -0.8F, 1.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(56, 0)
                        .addBox(-0.275F, -6.6F, -0.8F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.7F, 0.0F, 0.0F));

        PartDefinition cube_r12 = bone6.addOrReplaceChild(
                "cube_r12",
                CubeListBuilder.create()
                        .texOffs(65, 0)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                        .texOffs(65, 0)
                        .addBox(-0.5F, -8.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)),
                PartPose.offsetAndRotation(0.3F, -6.0F, -0.3F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r13 = bone6.addOrReplaceChild(
                "cube_r13",
                CubeListBuilder.create()
                        .texOffs(68, 0)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                        .texOffs(68, 0)
                        .addBox(-0.5F, -8.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)),
                PartPose.offsetAndRotation(-0.4F, -6.0F, -0.3F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone10 = bone3.addOrReplaceChild(
                "bone10",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, 2.0508F, 0.0F));

        PartDefinition bone11 = bone10.addOrReplaceChild(
                "bone11",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.2F, -16.8F, -0.8F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(-0.8F, -0.001F, 7.55F));

        PartDefinition bone12 = bone11.addOrReplaceChild(
                "bone12",
                CubeListBuilder.create()
                        .texOffs(60, 0)
                        .addBox(-0.2F, -14.3F, -0.8F, 1.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(56, 0)
                        .addBox(-0.275F, -6.6F, -0.8F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.7F, 0.0F, 0.0F));

        PartDefinition cube_r14 = bone12.addOrReplaceChild(
                "cube_r14",
                CubeListBuilder.create()
                        .texOffs(65, 0)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                        .texOffs(65, 0)
                        .addBox(-0.5F, -8.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)),
                PartPose.offsetAndRotation(0.3F, -6.0F, -0.3F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r15 = bone12.addOrReplaceChild(
                "cube_r15",
                CubeListBuilder.create()
                        .texOffs(68, 0)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                        .texOffs(68, 0)
                        .addBox(-0.5F, -8.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)),
                PartPose.offsetAndRotation(-0.4F, -6.0F, -0.3F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone7 = bone3.addOrReplaceChild(
                "bone7",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.0F, -4.0F, -1.0F, 0.0F, -1.9635F, 0.0F));

        PartDefinition bone8 = bone7.addOrReplaceChild(
                "bone8",
                CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(-0.2F, -16.8F, -0.8F, 1.0F, 17.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(-0.8F, -0.001F, 7.55F));

        PartDefinition bone9 = bone8.addOrReplaceChild(
                "bone9",
                CubeListBuilder.create()
                        .texOffs(60, 0)
                        .addBox(-0.2F, -14.3F, -0.8F, 1.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(56, 0)
                        .addBox(-0.275F, -6.6F, -0.8F, 1.0F, 7.0F, 1.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.7F, 0.0F, 0.0F));

        PartDefinition cube_r16 = bone9.addOrReplaceChild(
                "cube_r16",
                CubeListBuilder.create()
                        .texOffs(65, 0)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                        .texOffs(65, 0)
                        .addBox(-0.5F, -8.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)),
                PartPose.offsetAndRotation(0.3F, -6.0F, -0.3F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r17 = bone9.addOrReplaceChild(
                "cube_r17",
                CubeListBuilder.create()
                        .texOffs(68, 0)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                        .texOffs(68, 0)
                        .addBox(-0.5F, -8.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)),
                PartPose.offsetAndRotation(-0.4F, -6.0F, -0.3F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone2 =
                bone.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(2.0F, -7.0F, 0.0F));

        PartDefinition cube_r18 = bone2.addOrReplaceChild(
                "cube_r18",
                CubeListBuilder.create()
                        .texOffs(1, 0)
                        .addBox(-0.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 0)
                        .addBox(3.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.5F, -3.5926F, 0.1014F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r19 = bone2.addOrReplaceChild(
                "cube_r19",
                CubeListBuilder.create()
                        .texOffs(1, 0)
                        .addBox(-0.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 0)
                        .addBox(3.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.5F, -0.7642F, 0.1014F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r20 = bone2.addOrReplaceChild(
                "cube_r20",
                CubeListBuilder.create()
                        .texOffs(1, 0)
                        .addBox(-0.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 0)
                        .addBox(3.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.5F, -2.1791F, 0.1007F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r21 = bone2.addOrReplaceChild(
                "cube_r21",
                CubeListBuilder.create()
                        .texOffs(1, 0)
                        .addBox(-0.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 0)
                        .addBox(3.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.5F, -5.0061F, 0.1007F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r22 = bone2.addOrReplaceChild(
                "cube_r22",
                CubeListBuilder.create()
                        .texOffs(1, 0)
                        .addBox(-0.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 0)
                        .addBox(3.5F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.5F, 0.65F, 0.1F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(
            Entity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {}

    @Override
    public void renderToBuffer(
            PoseStack poseStack,
            VertexConsumer vertexConsumer,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.bone;
    }

    @Override
    public void SetupAnimations(T tile, float ageInTicks) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(tile.getRotorAnimationState(), HartnellRotorAnimation.LOOP, ageInTicks);
    }

    public static class HartnellRotorAnimation {
        public static final AnimationDefinition LOOP = AnimationDefinition.Builder.withLength(4.0F)
                .looping()
                .addAnimation(
                        "bone",
                        new AnimationChannel(
                                AnimationChannel.Targets.ROTATION,
                                new Keyframe(
                                        0.0F,
                                        KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(
                                        4.0F,
                                        KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation(
                        "bone",
                        new AnimationChannel(
                                AnimationChannel.Targets.POSITION,
                                new Keyframe(
                                        0.0F,
                                        KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(
                                        2.0F,
                                        KeyframeAnimations.posVec(0.0F, -11.0F, 0.0F),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(
                                        4.0F,
                                        KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .build();
    }
}
