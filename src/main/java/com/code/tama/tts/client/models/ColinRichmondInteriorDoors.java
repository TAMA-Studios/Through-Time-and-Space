/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models;// Made with Blockbench 4.12.2
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

import static com.code.tama.tts.TTSMod.MODID;

public class ColinRichmondInteriorDoors<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "colinrichmondinteriordoors"), "main");
    private final ModelPart Shell;
    private final ModelPart Emmisive;
    private final ModelPart text_POLICE;
    private final ModelPart character_p;
    private final ModelPart text_BOX;
    private final ModelPart character_b;
    private final ModelPart text_PUBLIC;
    private final ModelPart character_p2;
    private final ModelPart text_CALL;
    private final ModelPart character_c3;
    private final ModelPart character_a;
    private final ModelPart character_l3;
    private final ModelPart character_l4;
    private final ModelPart character_u;
    private final ModelPart character_b2;
    private final ModelPart character_l2;
    private final ModelPart character_i2;
    private final ModelPart character_c2;
    private final ModelPart character_o2;
    private final ModelPart character_x;
    private final ModelPart character_o;
    private final ModelPart character_l;
    private final ModelPart character_i;
    private final ModelPart character_c;
    private final ModelPart character_e;
    private final ModelPart LeftDoor;
    private final ModelPart LeftNonEmmisives;
    private final ModelPart phone;
    private final ModelPart phoneitem;
    private final ModelPart phonedoor;
    private final ModelPart phoneitem2;
    private final ModelPart LeftEmmisives;
    private final ModelPart RightDoor;
    private final ModelPart RightNonEmmisive;
    private final ModelPart RightEmmisives;

    public ColinRichmondInteriorDoors(ModelPart root) {
        this.Shell = root.getChild("Shell");
        this.Emmisive = root.getChild("Emmisive");
        this.text_POLICE = this.Emmisive.getChild("text_POLICE");
        this.character_p = this.text_POLICE.getChild("character_p");
        this.text_BOX = this.character_p.getChild("text_BOX");
        this.character_b = this.text_BOX.getChild("character_b");
        this.text_PUBLIC = this.character_b.getChild("text_PUBLIC");
        this.character_p2 = this.text_PUBLIC.getChild("character_p2");
        this.text_CALL = this.character_p2.getChild("text_CALL");
        this.character_c3 = this.text_CALL.getChild("character_c3");
        this.character_a = this.text_CALL.getChild("character_a");
        this.character_l3 = this.text_CALL.getChild("character_l3");
        this.character_l4 = this.text_CALL.getChild("character_l4");
        this.character_u = this.text_PUBLIC.getChild("character_u");
        this.character_b2 = this.text_PUBLIC.getChild("character_b2");
        this.character_l2 = this.text_PUBLIC.getChild("character_l2");
        this.character_i2 = this.text_PUBLIC.getChild("character_i2");
        this.character_c2 = this.text_PUBLIC.getChild("character_c2");
        this.character_o2 = this.text_BOX.getChild("character_o2");
        this.character_x = this.text_BOX.getChild("character_x");
        this.character_o = this.text_POLICE.getChild("character_o");
        this.character_l = this.text_POLICE.getChild("character_l");
        this.character_i = this.text_POLICE.getChild("character_i");
        this.character_c = this.text_POLICE.getChild("character_c");
        this.character_e = this.text_POLICE.getChild("character_e");
        this.LeftDoor = root.getChild("LeftDoor");
        this.LeftNonEmmisives = this.LeftDoor.getChild("LeftNonEmmisives");
        this.phone = this.LeftNonEmmisives.getChild("phone");
        this.phoneitem = this.phone.getChild("phoneitem");
        this.phonedoor = this.LeftNonEmmisives.getChild("phonedoor");
        this.phoneitem2 = this.phonedoor.getChild("phoneitem2");
        this.LeftEmmisives = this.LeftDoor.getChild("LeftEmmisives");
        this.RightDoor = root.getChild("RightDoor");
        this.RightNonEmmisive = this.RightDoor.getChild("RightNonEmmisive");
        this.RightEmmisives = this.RightDoor.getChild("RightEmmisives");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Shell = partdefinition.addOrReplaceChild("Shell", CubeListBuilder.create().texOffs(63, 97).addBox(-18.0F, -82.0F, -25.0F, 36.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(344, 280).addBox(-25.0F, -82.0F, -25.0F, 7.0F, 83.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(332, 197).addBox(-18.0F, -82.0F, -25.1F, 18.0F, 83.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(332, 197).addBox(0.0F, -82.0F, -25.1F, 18.0F, 83.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(268, 284).addBox(17.0F, -74.0F, -24.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(264, 284).addBox(-18.0F, -74.0F, -24.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(264, 284).addBox(-18.0F, -74.0F, -23.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(268, 284).addBox(17.0F, -74.0F, -23.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(156, 108).addBox(-18.0F, -75.0F, -24.0F, 36.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(156, 108).addBox(-18.0F, -75.0F, -23.0F, 36.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(65, 288).addBox(17.0F, -74.0F, -21.5F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(277, 63).addBox(-18.0F, -75.0F, -21.5F, 36.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(128, 292).addBox(-18.0F, -74.0F, -21.5F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(263, 63).addBox(-21.0F, -81.0F, -27.0F, 42.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(278, 63).addBox(-18.0F, -81.9F, -19.9F, 36.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(320, 280).addBox(18.0F, -82.0F, -25.0F, 7.0F, 83.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition Emmisive = partdefinition.addOrReplaceChild("Emmisive", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition text_POLICE = Emmisive.addOrReplaceChild("text_POLICE", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition character_p = text_POLICE.addOrReplaceChild("character_p", CubeListBuilder.create().texOffs(342, 109).addBox(-12.5F, -46.2F, -18.0F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-11.3F, -46.2F, -18.0F, 0.8F, 2.0F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-11.7F, -46.2F, -18.0F, 0.4F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-11.7F, -45.0F, -18.0F, 0.4F, 0.8F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

        PartDefinition text_BOX = character_p.addOrReplaceChild("text_BOX", CubeListBuilder.create(), PartPose.offset(12.8F, -48.0F, -18.0F));

        PartDefinition character_b = text_BOX.addOrReplaceChild("character_b", CubeListBuilder.create().texOffs(342, 109).addBox(-6.18F, 4.2F, -1.2F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-5.38F, 4.2F, -1.2F, 0.4F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-5.38F, 6.6F, -1.2F, 0.4F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-5.38F, 5.4F, -1.2F, 0.4F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.98F, 5.6F, -1.2F, 0.4F, 0.4F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.98F, 6.0F, -1.2F, 0.8F, 1.4F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.98F, 4.2F, -1.2F, 0.8F, 1.4F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

        PartDefinition text_PUBLIC = character_b.addOrReplaceChild("text_PUBLIC", CubeListBuilder.create(), PartPose.offset(-13.78F, 0.9F, -1.2F));

        PartDefinition character_p2 = text_PUBLIC.addOrReplaceChild("character_p2", CubeListBuilder.create().texOffs(342, 109).addBox(-9.2F, -59.8F, -24.8F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-8.6F, -59.8F, -24.8F, 0.4F, 1.0F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-8.8F, -59.8F, -24.8F, 0.2F, 0.4F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-8.8F, -59.2F, -24.8F, 0.2F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

        PartDefinition text_CALL = character_p2.addOrReplaceChild("text_CALL", CubeListBuilder.create(), PartPose.offset(-7.35F, -61.7F, -24.8F));

        PartDefinition character_c3 = text_CALL.addOrReplaceChild("character_c3", CubeListBuilder.create().texOffs(342, 109).addBox(-5.76F, -56.6F, -26.4F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-5.36F, -56.6F, -26.4F, 0.6F, 0.4F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-5.36F, -55.4F, -26.4F, 0.6F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

        PartDefinition character_a = text_CALL.addOrReplaceChild("character_a", CubeListBuilder.create().texOffs(342, 109).addBox(-4.7F, -56.6F, -26.4F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.3F, -56.6F, -26.4F, 0.2F, 0.4F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.1F, -56.6F, -26.4F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.3F, -56.0F, -26.4F, 0.2F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

        PartDefinition character_l3 = text_CALL.addOrReplaceChild("character_l3", CubeListBuilder.create().texOffs(342, 109).addBox(-3.64F, -56.6F, -26.4F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-3.24F, -55.4F, -26.4F, 0.6F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

        PartDefinition character_l4 = text_CALL.addOrReplaceChild("character_l4", CubeListBuilder.create().texOffs(342, 109).addBox(-2.58F, -56.6F, -26.4F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-2.18F, -55.4F, -26.4F, 0.6F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

        PartDefinition character_u = text_PUBLIC.addOrReplaceChild("character_u", CubeListBuilder.create().texOffs(342, 109).addBox(-8.14F, -59.8F, -24.8F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-7.34F, -59.8F, -24.8F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-7.74F, -58.6F, -24.8F, 0.4F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

        PartDefinition character_b2 = text_PUBLIC.addOrReplaceChild("character_b2", CubeListBuilder.create().texOffs(342, 109).addBox(-6.88F, -59.8F, -24.8F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-6.48F, -59.8F, -24.8F, 0.2F, 0.4F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-6.48F, -58.6F, -24.8F, 0.2F, 0.4F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-6.48F, -59.2F, -24.8F, 0.2F, 0.4F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-6.28F, -59.1F, -24.8F, 0.2F, 0.2F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-6.28F, -58.9F, -24.8F, 0.4F, 0.7F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-6.28F, -59.8F, -24.8F, 0.4F, 0.7F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

        PartDefinition character_l2 = text_PUBLIC.addOrReplaceChild("character_l2", CubeListBuilder.create().texOffs(342, 109).addBox(-5.82F, -59.8F, -24.8F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-5.42F, -58.6F, -24.8F, 0.6F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

        PartDefinition character_i2 = text_PUBLIC.addOrReplaceChild("character_i2", CubeListBuilder.create().texOffs(342, 109).addBox(-4.76F, -59.8F, -24.8F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

        PartDefinition character_c2 = text_PUBLIC.addOrReplaceChild("character_c2", CubeListBuilder.create().texOffs(342, 109).addBox(-4.3F, -59.8F, -24.8F, 0.4F, 1.6F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-3.9F, -59.8F, -24.8F, 0.6F, 0.4F, 9.55F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-3.9F, -58.6F, -24.8F, 0.6F, 0.4F, 9.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

        PartDefinition character_o2 = text_BOX.addOrReplaceChild("character_o2", CubeListBuilder.create().texOffs(342, 109).addBox(-4.06F, 4.2F, -1.2F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-3.26F, 4.2F, -1.2F, 0.8F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-2.46F, 4.2F, -1.2F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-3.26F, 6.6F, -1.2F, 0.8F, 0.8F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

        PartDefinition character_x = text_BOX.addOrReplaceChild("character_x", CubeListBuilder.create().texOffs(342, 109).addBox(-1.54F, 6.3F, -1.2F, 0.8F, 1.1F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-1.54F, 4.2F, -1.2F, 0.8F, 1.1F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-0.34F, 4.2F, -1.2F, 0.8F, 1.1F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-0.34F, 6.3F, -1.2F, 0.8F, 1.1F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-1.04F, 5.1F, -1.2F, 1.0F, 1.4F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

        PartDefinition character_o = text_POLICE.addOrReplaceChild("character_o", CubeListBuilder.create().texOffs(342, 109).addBox(-10.38F, -46.2F, -18.0F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-9.58F, -46.2F, -18.0F, 0.8F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-8.78F, -46.2F, -18.0F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-9.58F, -43.8F, -18.0F, 0.8F, 0.8F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

        PartDefinition character_l = text_POLICE.addOrReplaceChild("character_l", CubeListBuilder.create().texOffs(342, 109).addBox(-7.86F, -46.2F, -18.0F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-7.06F, -43.8F, -18.0F, 1.2F, 0.8F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

        PartDefinition character_i = text_POLICE.addOrReplaceChild("character_i", CubeListBuilder.create().texOffs(342, 109).addBox(-5.74F, -46.2F, -18.0F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

        PartDefinition character_c = text_POLICE.addOrReplaceChild("character_c", CubeListBuilder.create().texOffs(342, 109).addBox(-4.82F, -46.2F, -18.0F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.02F, -46.2F, -18.0F, 1.2F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-4.02F, -43.8F, -18.0F, 1.2F, 0.8F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

        PartDefinition character_e = text_POLICE.addOrReplaceChild("character_e", CubeListBuilder.create().texOffs(342, 109).addBox(-2.7F, -46.2F, -18.0F, 0.8F, 3.2F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-1.9F, -46.2F, -18.0F, 1.2F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-1.9F, -45.0F, -18.0F, 0.8F, 0.8F, 9.6F, new CubeDeformation(0.0F))
                .texOffs(342, 109).addBox(-1.9F, -43.8F, -18.0F, 1.2F, 0.8F, 9.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

        PartDefinition LeftDoor = partdefinition.addOrReplaceChild("LeftDoor", CubeListBuilder.create(), PartPose.offset(-16.9F, -13.2308F, -23.0167F));

        PartDefinition LeftNonEmmisives = LeftDoor.addOrReplaceChild("LeftNonEmmisives", CubeListBuilder.create().texOffs(6, 284).addBox(-0.1F, -37.8333F, 0.0167F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 139).addBox(1.9F, 34.1667F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 284).addBox(14.9F, -37.8333F, 0.0167F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 127).addBox(1.9F, 19.1667F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 111).addBox(1.9F, 1.1667F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 135).addBox(1.9F, 16.1667F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 131).addBox(1.9F, -1.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(1.9F, -34.8333F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 127).addBox(1.9F, -19.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 127).addBox(1.9F, -37.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(320, 289).addBox(14.9F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(320, 292).addBox(15.9F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(320, 289).addBox(-0.1F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(320, 292).addBox(0.9F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(340, 35).addBox(1.9F, -37.8333F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(340, 35).addBox(1.9F, 34.1667F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(340, 35).addBox(1.9F, -19.8333F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(340, 35).addBox(1.9F, 16.1667F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(340, 35).addBox(1.9F, -1.8333F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(234, 281).addBox(15.9F, -37.8333F, -0.9833F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -2.8333F, 1.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(173, 60).addBox(1.9F, -15.8333F, 1.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -2.8333F, 2.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(173, 60).addBox(1.9F, -15.8333F, 2.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(173, 60).addBox(1.9F, -15.8333F, 3.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -2.8333F, 3.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(161, 61).addBox(2.9F, -15.8333F, 7.6167F, 11.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(171, 60).addBox(13.9F, -15.8333F, 2.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(171, 60).addBox(13.9F, -15.8333F, 3.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(171, 60).addBox(13.9F, -15.8333F, 1.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -16.8333F, 3.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -16.8333F, 4.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(173, 60).addBox(1.9F, -15.8333F, 4.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -2.8333F, 4.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(171, 60).addBox(13.9F, -15.8333F, 4.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(171, 60).addBox(13.9F, -15.8333F, 5.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -16.8333F, 5.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(173, 60).addBox(1.9F, -15.8333F, 5.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -2.8333F, 5.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(171, 60).addBox(13.9F, -15.8333F, 6.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -16.8333F, 6.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(173, 60).addBox(1.9F, -15.8333F, 6.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -2.8333F, 6.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(171, 60).addBox(13.9F, -15.8333F, 7.0667F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -16.8333F, 7.0667F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(161, 61).addBox(2.9F, -15.8333F, 8.0667F, 11.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(173, 60).addBox(1.9F, -15.8333F, 7.0667F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -2.8333F, 7.0667F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -16.8333F, 2.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 59).addBox(1.9F, -16.8333F, 1.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0641F, 0.0F));

        PartDefinition phone = LeftNonEmmisives.addOrReplaceChild("phone", CubeListBuilder.create().texOffs(160, 98).addBox(-12.0F, -7.25F, -20.4F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(167, 96).addBox(-10.75F, -8.25F, -20.425F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(167, 96).addBox(-8.75F, -8.25F, -20.425F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(159, 97).addBox(-12.0F, -6.25F, -21.4F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(158, 96).addBox(-12.0F, -5.75F, -22.4F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(156, 94).addBox(-12.0F, -5.0F, -24.4F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(17.9F, 1.1667F, 27.0167F));

        PartDefinition cube_r1 = phone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(158, 80).mirror().addBox(-4.75F, -2.925F, -5.0F, 9.0F, 0.0F, 9.0F, new CubeDeformation(-2.0F)).mirror(false), PartPose.offsetAndRotation(-9.5F, -6.9629F, -22.5568F, -0.4363F, 0.0F, -3.1416F));

        PartDefinition cube_r2 = phone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(156, 94).addBox(-3.5F, -0.5F, -2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -5.3921F, -22.3761F, 0.4363F, 0.0F, 0.0F));

        PartDefinition phoneitem = phone.addOrReplaceChild("phoneitem", CubeListBuilder.create().texOffs(166, 96).addBox(-12.25F, -7.25F, -20.575F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(160, 98).addBox(-11.5F, -7.5F, -20.575F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(166, 96).addBox(-7.75F, -7.25F, -20.575F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.25F, 0.0F));

        PartDefinition phonedoor = LeftNonEmmisives.addOrReplaceChild("phonedoor", CubeListBuilder.create().texOffs(0, 90).addBox(-0.0833F, -15.0192F, 0.0167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(166, 40).addBox(1.9167F, -13.0192F, 0.4167F, 9.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(186, 42).addBox(1.9167F, -11.0192F, 1.4167F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(186, 42).addBox(1.9167F, -11.0192F, 2.4167F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(190, 92).addBox(3.9167F, -10.0192F, 3.4167F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(190, 92).addBox(6.9167F, -10.0192F, 3.4167F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(11.9167F, -9.0192F, -0.9833F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(11.9167F, -10.0192F, -0.0833F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(11.9167F, -6.0192F, -0.0833F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9833F, -1.8141F, 0.5F));

        PartDefinition cube_r3 = phonedoor.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(182, 97).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9167F, -11.5192F, 2.4167F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r4 = phonedoor.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(192, 93).addBox(-0.5F, 0.075F, -1.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(192, 93).addBox(-0.5F, -0.25F, -1.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(192, 93).addBox(-0.5F, -0.5F, -1.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(192, 93).addBox(-0.5F, -0.55F, -0.425F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.9048F, -5.9034F, 2.1667F, 0.0F, -1.5708F, 1.789F));

        PartDefinition cube_r5 = phonedoor.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(192, 93).addBox(-0.5F, -2.05F, -1.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(192, 93).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.7667F, -6.7942F, 2.1667F, 0.0F, -1.5708F, 2.3998F));

        PartDefinition cube_r6 = phonedoor.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(191, 92).addBox(-0.525F, 0.05F, 0.225F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(191, 92).addBox(-0.525F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.7917F, -6.7692F, 2.1667F, 0.0F, -1.5708F, -1.3526F));

        PartDefinition cube_r7 = phonedoor.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(182, 97).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.9167F, -11.5192F, 2.4167F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r8 = phonedoor.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(190, 96).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4167F, -5.5192F, 3.9167F, 0.0F, 0.0F, 0.7854F));

        PartDefinition phoneitem2 = phonedoor.addOrReplaceChild("phoneitem2", CubeListBuilder.create(), PartPose.offset(8.9167F, -11.5192F, 2.4167F));

        PartDefinition cube_r9 = phoneitem2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(182, 97).addBox(-1.25F, 0.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F))
                .texOffs(182, 97).addBox(-1.25F, 1.0F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(190, 92).addBox(-1.25F, 1.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(190, 92).addBox(-1.25F, 2.0F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(190, 92).addBox(-1.25F, 2.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(190, 92).addBox(-1.25F, 2.875F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(190, 92).addBox(-1.25F, 3.25F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(190, 92).addBox(-1.25F, 3.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition LeftEmmisives = LeftDoor.addOrReplaceChild("LeftEmmisives", CubeListBuilder.create().texOffs(0, 263).addBox(1.9F, -34.7692F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.1F));

        PartDefinition RightDoor = partdefinition.addOrReplaceChild("RightDoor", CubeListBuilder.create(), PartPose.offset(16.9F, -18.1667F, -21.05F));

        PartDefinition RightNonEmmisive = RightDoor.addOrReplaceChild("RightNonEmmisive", CubeListBuilder.create().texOffs(0, 284).addBox(-16.9F, -32.8333F, -1.95F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(222, 281).addBox(-1.9F, -32.8333F, -1.95F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 119).addBox(-14.9F, 39.1667F, -1.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 58).addBox(-14.9F, 24.1667F, -1.45F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 115).addBox(-14.9F, 21.1667F, -1.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-14.9F, 6.1667F, -1.45F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(132, 111).addBox(-14.9F, 3.1667F, -1.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-14.9F, -11.8333F, -1.45F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 106).addBox(-14.9F, -14.8333F, -1.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 106).addBox(-14.9F, -32.8333F, -1.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(290, 289).addBox(-15.9F, -32.8333F, -1.05F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(290, 292).addBox(-16.9F, -32.8333F, -1.05F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(290, 292).addBox(-1.9F, -32.8333F, -1.05F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(290, 289).addBox(-0.9F, -32.8333F, -1.05F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(340, 35).mirror().addBox(-14.9F, -32.8333F, -1.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(340, 35).mirror().addBox(-14.9F, 39.1667F, -1.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(340, 35).mirror().addBox(-14.9F, -14.8333F, -1.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(340, 35).mirror().addBox(-14.9F, 21.1667F, -1.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(340, 43).addBox(-14.9F, 3.1667F, -1.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(-14.9F, -29.8333F, -1.45F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 6).addBox(-15.9F, -6.3333F, -2.95F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 6).addBox(-15.9F, -1.3333F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 6).addBox(-15.9F, -7.3333F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r10 = RightNonEmmisive.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.3F, -0.8333F, 0.0F, 3.1416F, 0.0F, -1.5708F));

        PartDefinition cube_r11 = RightNonEmmisive.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.4F, -0.3333F, -0.25F, 3.1416F, 0.0F, -1.5708F));

        PartDefinition cube_r12 = RightNonEmmisive.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.3F, 0.1667F, 0.0F, 3.1416F, 0.0F, -1.5708F));

        PartDefinition cube_r13 = RightNonEmmisive.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.3F, 0.1667F, 0.0F, 3.1416F, 0.0F, -1.5708F));

        PartDefinition cube_r14 = RightNonEmmisive.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.3F, -0.8333F, 0.0F, 3.1416F, 0.0F, -1.5708F));

        PartDefinition cube_r15 = RightNonEmmisive.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.55F, -0.3333F, 0.25F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r16 = RightNonEmmisive.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 6).addBox(-0.5F, 5.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.3F, -0.8333F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r17 = RightNonEmmisive.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 6).addBox(-0.5F, 3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.3F, 0.1667F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition RightEmmisives = RightDoor.addOrReplaceChild("RightEmmisives", CubeListBuilder.create().texOffs(0, 263).addBox(-14.9F, -34.8333F, -1.45F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -0.1F));

        return LayerDefinition.create(meshdefinition, 368, 368);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Shell.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Emmisive.render(poseStack, vertexConsumer, packedLight * 16, packedOverlay, red, green, blue, alpha);
        LeftDoor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        RightDoor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}