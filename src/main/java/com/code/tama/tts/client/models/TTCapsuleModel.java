/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.TTSMod;
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

public class TTCapsuleModel<T extends ExteriorTile> extends HierarchicalExteriorModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(TTSMod.MODID, "ttcapsulemodel"), "main");
    private final ModelPart base;
    private final ModelPart RightDoor;
    private final ModelPart LeftDoor;

    public TTCapsuleModel(ModelPart root) {
        super(root, Constants.ExteriorModelNames.TTCapsule, LAYER_LOCATION);
        this.base = root.getChild("base");
        this.RightDoor = root.getChild("RightDoor");
        this.LeftDoor = root.getChild("LeftDoor");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(1, 0).addBox(-8.0F, -35.0F, -8.0F, 1.0F, 32.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(137, 0).mirror().addBox(7.0F, -35.0F, -8.0F, 1.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 48).addBox(-9.0F, -3.0F, -9.0F, 18.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(64, 0).addBox(-9.0F, -39.0F, -9.0F, 18.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(103, 22).addBox(-8.0F, -32.0F, -9.0F, 1.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition RightDoor = partdefinition.addOrReplaceChild("RightDoor", CubeListBuilder.create().texOffs(1, 102).addBox(0.0F, -32.0F, 0.0F, 7.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 20.0F, -7.0F));

        PartDefinition LeftDoor = partdefinition.addOrReplaceChild("LeftDoor", CubeListBuilder.create().texOffs(15, 69).addBox(-7.0F, -32.0F, 0.0F, 7.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 20.0F, -7.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    /**
     * Modid + name of the exterior <br />
     * If you're adding a custom exterior with an addon just use your modid + exterior name
     **/
    @Override
    public ResourceLocation GetModelName() {
        return Constants.ExteriorModelNames.TTCapsule;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.translate(-0.5, -2.15, 0.5);
//        poseStack.scale(0.43f, 0.43f, 0.43f);
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        RightDoor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftDoor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    @Override
    public @NotNull ModelPart root() {
        return this.base;
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
    public Map<Integer, ArrayList<Float>> RotationList() {
        Map<Integer, ArrayList<Float>> map = new HashMap<>();
        ArrayList<Float> LeftList = new ArrayList<>();
        ArrayList<Float> RightList = new ArrayList<>();

        LeftList.add(0f);
        LeftList.add(0f);
        LeftList.add((float) Math.toRadians(75));

        RightList.add(0f);
        RightList.add(-(float) Math.toRadians(75));
        RightList.add(-(float) Math.toRadians(75));

        map.put(0, LeftList);
        map.put(1, RightList);
        return map;
    }
}
