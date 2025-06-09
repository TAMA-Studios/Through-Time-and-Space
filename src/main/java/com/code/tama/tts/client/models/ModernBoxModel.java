/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models;


import com.code.tama.tts.client.models.core.HierarchicalExteriorModel;
import com.code.tama.tts.core.Constants;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import lombok.Getter;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.code.tama.tts.TTSMod.MODID;

public class ModernBoxModel<T extends ExteriorTile> extends HierarchicalExteriorModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "modernboxmodel"), "main");
	private final ModelPart Root;
	private final ModelPart Shell;
	private final ModelPart bone18;
	private final ModelPart bone26;
	private final ModelPart bone27;
	private final ModelPart bone28;
	private final ModelPart bone29;
	private final ModelPart bone30;
	private final ModelPart bone32;
	private final ModelPart bone33;
	private final ModelPart bone31;
	private final ModelPart bone19;
	private final ModelPart bone20;
	private final ModelPart bone24;
	private final ModelPart bone25;
	private final ModelPart bone23;
	private final ModelPart bone21;
	private final ModelPart bone22;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart bone7;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone12;
	private final ModelPart bone13;
	private final ModelPart bone15;
	private final ModelPart bone9;
	private final ModelPart bone11;
	private final ModelPart bone16;
	private final ModelPart bone17;
	private final ModelPart bone34;
	private final ModelPart bone35;
	private final ModelPart bone36;
	private final ModelPart bone37;
	private final ModelPart bone8;
	private final ModelPart bone10;
	private final ModelPart bone14;
	@Getter
	private final ModelPart Boti;
	private final ModelPart Emmisive;
	private final ModelPart text_POLICE4;
	private final ModelPart character_p7;
	private final ModelPart text_BOX4;
	private final ModelPart character_b7;
	private final ModelPart text_PUBLIC4;
	private final ModelPart character_p8;
	private final ModelPart text_CALL4;
	private final ModelPart character_c10;
	private final ModelPart character_a4;
	private final ModelPart character_l13;
	private final ModelPart character_l14;
	private final ModelPart character_u4;
	private final ModelPart character_b8;
	private final ModelPart character_l15;
	private final ModelPart character_i7;
	private final ModelPart character_c11;
	private final ModelPart character_o7;
	private final ModelPart character_x4;
	private final ModelPart character_o8;
	private final ModelPart character_l16;
	private final ModelPart character_i8;
	private final ModelPart character_c12;
	private final ModelPart character_e4;
	private final ModelPart text_POLICE3;
	private final ModelPart character_p5;
	private final ModelPart text_BOX3;
	private final ModelPart character_b5;
	private final ModelPart text_PUBLIC3;
	private final ModelPart character_p6;
	private final ModelPart text_CALL3;
	private final ModelPart character_c7;
	private final ModelPart character_a3;
	private final ModelPart character_l9;
	private final ModelPart character_l10;
	private final ModelPart character_u3;
	private final ModelPart character_b6;
	private final ModelPart character_l11;
	private final ModelPart character_i5;
	private final ModelPart character_c8;
	private final ModelPart character_o5;
	private final ModelPart character_x3;
	private final ModelPart character_o6;
	private final ModelPart character_l12;
	private final ModelPart character_i6;
	private final ModelPart character_c9;
	private final ModelPart character_e3;
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
	private final ModelPart text_POLICE2;
	private final ModelPart character_p3;
	private final ModelPart text_BOX2;
	private final ModelPart character_b3;
	private final ModelPart text_PUBLIC2;
	private final ModelPart character_p4;
	private final ModelPart text_CALL2;
	private final ModelPart character_c4;
	private final ModelPart character_a2;
	private final ModelPart character_l5;
	private final ModelPart character_l6;
	private final ModelPart character_u2;
	private final ModelPart character_b4;
	private final ModelPart character_l7;
	private final ModelPart character_i3;
	private final ModelPart character_c5;
	private final ModelPart character_o3;
	private final ModelPart character_x2;
	private final ModelPart character_o4;
	private final ModelPart character_l8;
	private final ModelPart character_i4;
	private final ModelPart character_c6;
	private final ModelPart character_e2;
	private final ModelPart Doors;
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

	public ModernBoxModel (ModelPart root) {
		super(root, Constants.ExteriorModelNames.VOXEL_CHIBNALL, LAYER_LOCATION);
		this.Root = root.getChild("Root");
		this.Shell = this.Root.getChild("Shell");
		this.bone18 = this.Shell.getChild("bone18");
		this.bone26 = this.bone18.getChild("bone26");
		this.bone27 = this.bone26.getChild("bone27");
		this.bone28 = this.bone26.getChild("bone28");
		this.bone29 = this.bone18.getChild("bone29");
		this.bone30 = this.bone29.getChild("bone30");
		this.bone32 = this.bone29.getChild("bone32");
		this.bone33 = this.bone29.getChild("bone33");
		this.bone31 = this.bone29.getChild("bone31");
		this.bone19 = this.bone18.getChild("bone19");
		this.bone20 = this.bone18.getChild("bone20");
		this.bone24 = this.bone18.getChild("bone24");
		this.bone25 = this.bone18.getChild("bone25");
		this.bone23 = this.bone18.getChild("bone23");
		this.bone21 = this.bone18.getChild("bone21");
		this.bone22 = this.bone18.getChild("bone22");
		this.bone4 = this.Shell.getChild("bone4");
		this.bone5 = this.Shell.getChild("bone5");
		this.bone6 = this.Shell.getChild("bone6");
		this.bone7 = this.Shell.getChild("bone7");
		this.bone = this.Shell.getChild("bone");
		this.bone2 = this.Shell.getChild("bone2");
		this.bone3 = this.Shell.getChild("bone3");
		this.bone12 = this.Shell.getChild("bone12");
		this.bone13 = this.Shell.getChild("bone13");
		this.bone15 = this.bone13.getChild("bone15");
		this.bone9 = this.bone15.getChild("bone9");
		this.bone11 = this.bone15.getChild("bone11");
		this.bone16 = this.bone13.getChild("bone16");
		this.bone17 = this.bone16.getChild("bone17");
		this.bone34 = this.bone16.getChild("bone34");
		this.bone35 = this.bone13.getChild("bone35");
		this.bone36 = this.bone35.getChild("bone36");
		this.bone37 = this.bone35.getChild("bone37");
		this.bone8 = this.bone13.getChild("bone8");
		this.bone10 = this.bone8.getChild("bone10");
		this.bone14 = this.bone8.getChild("bone14");
		this.Boti = this.Root.getChild("Boti");
		this.Emmisive = this.Root.getChild("Emmisive");
		this.text_POLICE4 = this.Emmisive.getChild("text_POLICE4");
		this.character_p7 = this.text_POLICE4.getChild("character_p7");
		this.text_BOX4 = this.character_p7.getChild("text_BOX4");
		this.character_b7 = this.text_BOX4.getChild("character_b7");
		this.text_PUBLIC4 = this.character_b7.getChild("text_PUBLIC4");
		this.character_p8 = this.text_PUBLIC4.getChild("character_p8");
		this.text_CALL4 = this.character_p8.getChild("text_CALL4");
		this.character_c10 = this.text_CALL4.getChild("character_c10");
		this.character_a4 = this.text_CALL4.getChild("character_a4");
		this.character_l13 = this.text_CALL4.getChild("character_l13");
		this.character_l14 = this.text_CALL4.getChild("character_l14");
		this.character_u4 = this.text_PUBLIC4.getChild("character_u4");
		this.character_b8 = this.text_PUBLIC4.getChild("character_b8");
		this.character_l15 = this.text_PUBLIC4.getChild("character_l15");
		this.character_i7 = this.text_PUBLIC4.getChild("character_i7");
		this.character_c11 = this.text_PUBLIC4.getChild("character_c11");
		this.character_o7 = this.text_BOX4.getChild("character_o7");
		this.character_x4 = this.text_BOX4.getChild("character_x4");
		this.character_o8 = this.text_POLICE4.getChild("character_o8");
		this.character_l16 = this.text_POLICE4.getChild("character_l16");
		this.character_i8 = this.text_POLICE4.getChild("character_i8");
		this.character_c12 = this.text_POLICE4.getChild("character_c12");
		this.character_e4 = this.text_POLICE4.getChild("character_e4");
		this.text_POLICE3 = this.Emmisive.getChild("text_POLICE3");
		this.character_p5 = this.text_POLICE3.getChild("character_p5");
		this.text_BOX3 = this.character_p5.getChild("text_BOX3");
		this.character_b5 = this.text_BOX3.getChild("character_b5");
		this.text_PUBLIC3 = this.character_b5.getChild("text_PUBLIC3");
		this.character_p6 = this.text_PUBLIC3.getChild("character_p6");
		this.text_CALL3 = this.character_p6.getChild("text_CALL3");
		this.character_c7 = this.text_CALL3.getChild("character_c7");
		this.character_a3 = this.text_CALL3.getChild("character_a3");
		this.character_l9 = this.text_CALL3.getChild("character_l9");
		this.character_l10 = this.text_CALL3.getChild("character_l10");
		this.character_u3 = this.text_PUBLIC3.getChild("character_u3");
		this.character_b6 = this.text_PUBLIC3.getChild("character_b6");
		this.character_l11 = this.text_PUBLIC3.getChild("character_l11");
		this.character_i5 = this.text_PUBLIC3.getChild("character_i5");
		this.character_c8 = this.text_PUBLIC3.getChild("character_c8");
		this.character_o5 = this.text_BOX3.getChild("character_o5");
		this.character_x3 = this.text_BOX3.getChild("character_x3");
		this.character_o6 = this.text_POLICE3.getChild("character_o6");
		this.character_l12 = this.text_POLICE3.getChild("character_l12");
		this.character_i6 = this.text_POLICE3.getChild("character_i6");
		this.character_c9 = this.text_POLICE3.getChild("character_c9");
		this.character_e3 = this.text_POLICE3.getChild("character_e3");
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
		this.text_POLICE2 = this.Emmisive.getChild("text_POLICE2");
		this.character_p3 = this.text_POLICE2.getChild("character_p3");
		this.text_BOX2 = this.character_p3.getChild("text_BOX2");
		this.character_b3 = this.text_BOX2.getChild("character_b3");
		this.text_PUBLIC2 = this.character_b3.getChild("text_PUBLIC2");
		this.character_p4 = this.text_PUBLIC2.getChild("character_p4");
		this.text_CALL2 = this.character_p4.getChild("text_CALL2");
		this.character_c4 = this.text_CALL2.getChild("character_c4");
		this.character_a2 = this.text_CALL2.getChild("character_a2");
		this.character_l5 = this.text_CALL2.getChild("character_l5");
		this.character_l6 = this.text_CALL2.getChild("character_l6");
		this.character_u2 = this.text_PUBLIC2.getChild("character_u2");
		this.character_b4 = this.text_PUBLIC2.getChild("character_b4");
		this.character_l7 = this.text_PUBLIC2.getChild("character_l7");
		this.character_i3 = this.text_PUBLIC2.getChild("character_i3");
		this.character_c5 = this.text_PUBLIC2.getChild("character_c5");
		this.character_o3 = this.text_BOX2.getChild("character_o3");
		this.character_x2 = this.text_BOX2.getChild("character_x2");
		this.character_o4 = this.text_POLICE2.getChild("character_o4");
		this.character_l8 = this.text_POLICE2.getChild("character_l8");
		this.character_i4 = this.text_POLICE2.getChild("character_i4");
		this.character_c6 = this.text_POLICE2.getChild("character_c6");
		this.character_e2 = this.text_POLICE2.getChild("character_e2");
		this.Doors = this.Root.getChild("Doors");
		this.LeftDoor = this.Doors.getChild("LeftDoor");
		this.LeftNonEmmisives = this.LeftDoor.getChild("LeftNonEmmisives");
		this.phone = this.LeftNonEmmisives.getChild("phone");
		this.phoneitem = this.phone.getChild("phoneitem");
		this.phonedoor = this.LeftNonEmmisives.getChild("phonedoor");
		this.phoneitem2 = this.phonedoor.getChild("phoneitem2");
		this.LeftEmmisives = this.LeftDoor.getChild("LeftEmmisives");
		this.RightDoor = this.Doors.getChild("RightDoor");
		this.RightNonEmmisive = this.RightDoor.getChild("RightNonEmmisive");
		this.RightEmmisives = this.RightDoor.getChild("RightEmmisives");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Shell = Root.addOrReplaceChild("Shell", CubeListBuilder.create().texOffs(0, 58).addBox(-26.0F, -5.0F, -26.0F, 52.0F, 1.0F, 52.0F, new CubeDeformation(0.0F))
				.texOffs(28, 66).addBox(-23.0F, -92.0F, -23.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(264, 284).addBox(17.0F, -80.0F, -24.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(264, 284).addBox(-18.0F, -80.0F, -24.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(156, 108).addBox(-18.0F, -81.0F, -24.0F, 36.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(238, 11).addBox(-21.0F, -87.0F, -27.0F, 42.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 111).addBox(-22.0F, -92.7F, -22.0F, 44.0F, 6.0F, 44.0F, new CubeDeformation(0.25F))
				.texOffs(150, 129).addBox(-18.0F, -96.7F, -18.0F, 36.0F, 4.0F, 18.0F, new CubeDeformation(0.35F))
				.texOffs(104, 277).addBox(-23.0F, -80.0F, -2.0F, 1.0F, 75.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(210, 281).addBox(-24.0F, -80.0F, -1.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(262, 133).addBox(-23.0F, -8.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(176, 233).addBox(-22.5F, -23.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(114, 259).addBox(-23.0F, -26.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(230, 203).addBox(-22.5F, -41.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(32, 254).addBox(-23.0F, -44.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(230, 158).addBox(-22.5F, -59.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(240, 251).addBox(-23.0F, -62.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(0, 251).addBox(-23.0F, -80.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(178, 231).addBox(-22.5F, -77.0F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(178, 231).addBox(-22.5F, -77.0F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(192, 278).addBox(-23.0F, -80.0F, -17.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(186, 278).addBox(-23.0F, -80.0F, 15.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(260, 284).addBox(-24.0F, -80.0F, 17.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(186, 151).addBox(-24.0F, -81.0F, -18.0F, 1.0F, 1.0F, 36.0F, new CubeDeformation(0.0F))
				.texOffs(256, 284).addBox(-24.0F, -80.0F, -18.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(279, 203).addBox(2.0F, -77.0F, 21.5F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(279, 203).addBox(-15.0F, -77.0F, 21.5F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(224, 151).addBox(-15.0F, -80.0F, 22.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(144, 207).addBox(-15.0F, -62.0F, 22.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(262, 166).addBox(-15.0F, -59.0F, 21.5F, 30.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(144, 207).addBox(-15.0F, -44.0F, 22.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 177).addBox(-15.0F, -41.0F, 21.5F, 30.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(144, 203).addBox(-15.0F, -26.0F, 22.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 161).addBox(-15.0F, -23.0F, 21.5F, 30.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(144, 199).addBox(-15.0F, -8.0F, 22.0F, 30.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 284).addBox(-1.0F, -80.0F, 23.0F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(176, 278).addBox(-2.0F, -80.0F, 22.0F, 4.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(192, 278).addBox(-17.0F, -80.0F, 22.0F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(186, 278).addBox(15.0F, -80.0F, 22.0F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(178, 231).addBox(21.5F, -77.0F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(178, 231).addBox(21.5F, -77.0F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(208, 248).addBox(22.0F, -80.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(82, 244).addBox(22.0F, -62.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(206, 7).addBox(21.5F, -59.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(32, 254).addBox(22.0F, -44.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(112, 199).addBox(21.5F, -41.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(114, 259).addBox(22.0F, -26.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(198, 188).addBox(21.5F, -23.0F, -15.0F, 1.0F, 15.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(262, 133).addBox(22.0F, -8.0F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(198, 278).addBox(23.0F, -80.0F, -1.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(94, 277).addBox(22.0F, -80.0F, -2.0F, 1.0F, 75.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(192, 278).addBox(22.0F, -80.0F, 15.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(186, 278).addBox(22.0F, -80.0F, -17.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(252, 284).addBox(-18.0F, -80.0F, 23.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(156, 106).addBox(-18.0F, -81.0F, 23.0F, 36.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(248, 284).addBox(17.0F, -80.0F, 23.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(244, 284).addBox(23.0F, -80.0F, 17.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(162, 0).addBox(23.0F, -81.0F, -18.0F, 1.0F, 1.0F, 36.0F, new CubeDeformation(0.0F))
				.texOffs(240, 284).addBox(23.0F, -80.0F, -18.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 161).addBox(18.1F, -88.0F, 18.1F, 7.0F, 83.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 161).addBox(18.0F, -88.0F, -25.0F, 7.0F, 83.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(28, 58).addBox(-23.0F, -92.0F, 19.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 41).addBox(19.0F, -92.0F, 19.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 33).addBox(19.0F, -92.0F, -23.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(156, 58).addBox(-27.0F, -87.0F, -21.0F, 5.0F, 6.0F, 42.0F, new CubeDeformation(0.0F))
				.texOffs(134, 151).addBox(22.0F, -87.0F, -21.0F, 5.0F, 6.0F, 42.0F, new CubeDeformation(0.0F))
				.texOffs(54, 0).mirror().addBox(0.0F, 0.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 0).mirror().addBox(-27.0F, 0.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-27.005F, -4.0091F, -26.995F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(0.0F, -3.999F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 0).mirror().addBox(0.0F, 0.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 0).addBox(-27.0F, 0.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F))
				.texOffs(54, 0).mirror().addBox(0.0F, -4.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(54, 0).addBox(-27.0F, -4.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-27.0F, -2.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(0.0F, -2.0F, -27.0F, 27.0F, 0.0F, 54.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(108, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = Shell.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(108, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r2 = Shell.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(108, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(150, 129).mirror().addBox(-18.0F, -96.7F, -18.0F, 36.0F, 4.0F, 18.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r3 = Shell.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(108, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 54).addBox(-27.0F, -4.0F, -27.0F, 54.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = Shell.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(40, 74).addBox(-2.5F, -103.0F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(29, 82).addBox(-2.0F, -100.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.05F))
				.texOffs(29, 82).addBox(-2.0F, -102.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.05F))
				.texOffs(28, 41).addBox(-2.0F, -105.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(30, 162).addBox(-3.0F, -104.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(30, 162).addBox(-3.0F, -98.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(32, 163).addBox(-2.5F, -97.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r5 = Shell.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 161).addBox(18.0F, -88.0F, -25.0F, 7.0F, 83.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r6 = Shell.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(28, 161).addBox(18.0F, -88.0F, 18.0F, 7.0F, 83.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.0F, 0.1F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r7 = Shell.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(238, 11).addBox(-21.0F, -3.0F, -2.5F, 42.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 24.5F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r8 = Shell.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(28, 74).addBox(-2.5F, -103.0F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone18 = Shell.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offsetAndRotation(-22.6318F, -49.8493F, -0.0234F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r9 = bone18.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(175, 276).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(192, 276).addBox(10.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4932F, 41.8493F, -0.3516F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r10 = bone18.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(174, 262).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(228, 232).addBox(-0.5F, -25.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.6432F, 34.3743F, -0.0016F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r11 = bone18.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(229, 246).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(245, 246).addBox(10.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4932F, 23.8493F, -0.3516F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r12 = bone18.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(229, 232).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(245, 232).addBox(10.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4932F, 9.1958F, 0.0019F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r13 = bone18.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(241, 232).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(187, 262).addBox(-0.5F, 10.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3682F, 16.3743F, -0.0016F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r14 = bone18.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(245, 232).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(191, 262).addBox(-0.5F, 10.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3568F, 16.3743F, -0.0016F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r15 = bone18.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(257, 232).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(204, 262).addBox(-0.5F, 10.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.6318F, 16.3743F, -0.0016F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r16 = bone18.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(192, 262).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(175, 262).addBox(-23.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5068F, 27.1958F, 0.0019F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bone26 = bone18.addOrReplaceChild("bone26", CubeListBuilder.create(), PartPose.offset(-2.3682F, -19.6257F, -0.0016F));

		PartDefinition bone27 = bone26.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r17 = bone27.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(189, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r18 = bone27.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(177, 257).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 7.475F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r19 = bone27.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(177, 243).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, -7.1786F, 0.0036F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r20 = bone27.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(177, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.275F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r21 = bone27.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(177, 250).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.0F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r22 = bone27.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(181, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.775F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r23 = bone27.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(185, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7929F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone28 = bone26.addOrReplaceChild("bone28", CubeListBuilder.create(), PartPose.offset(17.0F, 0.0F, 0.0F));

		PartDefinition cube_r24 = bone28.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(203, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r25 = bone28.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(191, 257).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 7.475F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r26 = bone28.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(191, 243).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, -7.1786F, 0.0036F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r27 = bone28.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(191, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.275F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r28 = bone28.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(191, 250).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.0F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r29 = bone28.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(195, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.775F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r30 = bone28.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(199, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.775F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone29 = bone18.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offsetAndRotation(-22.6182F, -19.6257F, 24.9984F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r31 = bone30.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(274, 203).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r32 = bone30.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(262, 217).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 7.475F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r33 = bone30.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(262, 203).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, -7.1786F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r34 = bone30.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(262, 203).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.275F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r35 = bone30.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(263, 211).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.0F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r36 = bone30.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(263, 211).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.3464F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r37 = bone30.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(267, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7929F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r38 = bone30.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(267, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r39 = bone30.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(271, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r40 = bone30.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(271, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7929F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone32 = bone29.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offsetAndRotation(-20.25F, 0.0F, 8.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r41 = bone32.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(203, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r42 = bone32.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(191, 257).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 7.475F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r43 = bone32.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(191, 243).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, -7.1786F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r44 = bone32.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(191, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.275F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r45 = bone32.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(192, 251).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.0F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r46 = bone32.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(192, 251).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.3464F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r47 = bone32.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(196, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7929F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r48 = bone32.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(196, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r49 = bone32.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(200, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r50 = bone32.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(200, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7929F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone33 = bone29.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offsetAndRotation(-20.25F, 0.0F, 25.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r51 = bone33.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(189, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r52 = bone33.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(177, 257).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 7.475F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r53 = bone33.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(177, 243).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, -7.1786F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r54 = bone33.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(177, 243).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.275F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r55 = bone33.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(178, 251).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.0F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r56 = bone33.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(178, 251).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.3464F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r57 = bone33.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(182, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7929F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r58 = bone33.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(182, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r59 = bone33.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(186, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r60 = bone33.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(186, 244).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7929F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone31 = bone29.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offset(17.0F, 0.0F, 0.0F));

		PartDefinition cube_r61 = bone31.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(291, 203).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r62 = bone31.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(279, 217).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 7.475F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r63 = bone31.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(279, 203).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, -7.1786F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r64 = bone31.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(279, 203).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.275F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r65 = bone31.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(280, 211).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.0F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r66 = bone31.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(280, 211).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.125F, 0.3464F, 0.0035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r67 = bone31.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(284, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.775F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r68 = bone31.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(284, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4821F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r69 = bone31.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(288, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.4821F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r70 = bone31.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(288, 204).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.775F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone19 = bone18.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offset(0.0006F, -1.6399F, 0.0002F));

		PartDefinition cube_r71 = bone19.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(279, 166).addBox(1.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(246, 187).addBox(-10.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0062F, -0.0143F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r72 = bone19.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(279, 166).addBox(2.0125F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(246, 187).addBox(-14.9875F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0062F, -0.0143F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r73 = bone19.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(291, 166).addBox(9.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(258, 187).addBox(-2.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0062F, -0.0143F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r74 = bone19.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(279, 180).addBox(2.0125F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(246, 201).addBox(-14.9875F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0062F, -0.0143F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone20 = bone18.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offsetAndRotation(-22.6994F, -1.6399F, 22.6252F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r75 = bone20.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(279, 166).addBox(1.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(262, 166).addBox(-10.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r76 = bone20.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(279, 166).addBox(2.0125F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(262, 166).addBox(-14.9875F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r77 = bone20.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(291, 166).addBox(9.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(274, 166).addBox(-2.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r78 = bone20.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(279, 180).addBox(2.0125F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(262, 180).addBox(-14.9875F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone24 = bone18.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0494F, 16.3601F, 45.3252F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r79 = bone24.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(128, 228).addBox(1.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.035F, -0.0393F, 0.0487F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r80 = bone24.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(128, 228).addBox(2.0125F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -0.0072F, 0.0451F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r81 = bone24.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(140, 228).addBox(9.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0171F, -0.0393F, 0.0487F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r82 = bone24.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(128, 242).addBox(2.0125F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -0.0143F, 0.0451F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r83 = bone24.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(111, 242).addBox(-14.9875F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -0.0143F, 0.0451F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r84 = bone24.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(123, 228).addBox(-2.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0171F, -0.0393F, 0.0487F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r85 = bone24.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(111, 228).addBox(-14.9875F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -0.0072F, 0.0451F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r86 = bone24.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(111, 228).addBox(-10.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.035F, -0.0393F, 0.0487F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone25 = bone18.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0494F, 16.3601F, 45.3252F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r87 = bone25.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(128, 228).addBox(1.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.035F, 17.9607F, 0.0487F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r88 = bone25.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(128, 228).addBox(2.0125F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, 17.9928F, 0.0451F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r89 = bone25.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(140, 228).addBox(9.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0171F, 17.9607F, 0.0487F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r90 = bone25.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(128, 242).addBox(2.0125F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, 17.9857F, 0.0451F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r91 = bone25.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(111, 242).addBox(-14.9875F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, 17.9857F, 0.0451F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r92 = bone25.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(123, 228).addBox(-2.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0171F, 17.9607F, 0.0487F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r93 = bone25.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(111, 228).addBox(-14.9875F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, 17.9928F, 0.0451F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r94 = bone25.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(111, 228).addBox(-10.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.035F, 17.9607F, 0.0487F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone23 = bone18.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0494F, 16.3601F, 45.3252F, 3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r95 = bone23.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(128, 228).addBox(1.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.035F, -18.0393F, 0.0487F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r96 = bone23.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(128, 228).addBox(2.0125F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -18.0072F, 0.0451F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r97 = bone23.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(140, 228).addBox(9.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0171F, -18.0393F, 0.0487F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r98 = bone23.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(128, 242).addBox(2.0125F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -18.0143F, 0.0451F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r99 = bone23.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(111, 242).addBox(-14.9875F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -18.0143F, 0.0451F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r100 = bone23.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(123, 228).addBox(-2.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0171F, -18.0393F, 0.0487F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r101 = bone23.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(111, 228).addBox(-14.9875F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0385F, -18.0072F, 0.0451F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r102 = bone23.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(111, 228).addBox(-10.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.035F, -18.0393F, 0.0487F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone21 = bone18.addOrReplaceChild("bone21", CubeListBuilder.create(), PartPose.offsetAndRotation(-22.6994F, 16.3601F, 22.6252F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r103 = bone21.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(129, 177).addBox(1.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 177).addBox(-10.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r104 = bone21.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(129, 177).addBox(2.0125F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 177).addBox(-14.9875F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r105 = bone21.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(141, 177).addBox(9.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(124, 177).addBox(-2.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r106 = bone21.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(129, 191).addBox(2.0125F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 191).addBox(-14.9875F, 4.5545F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone22 = bone18.addOrReplaceChild("bone22", CubeListBuilder.create(), PartPose.offsetAndRotation(-22.6994F, 34.3601F, 22.6252F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r107 = bone22.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(129, 161).addBox(1.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 161).addBox(-10.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r108 = bone22.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(129, 161).addBox(2.0125F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 161).addBox(-14.9875F, -5.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r109 = bone22.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(141, 161).addBox(9.8515F, -7.4715F, 10.349F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(124, 161).addBox(-2.1693F, -7.4715F, -1.6718F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r110 = bone22.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(129, 175).addBox(2.0125F, 4.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 175).addBox(-14.9875F, 4.5546F, 5.0571F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0063F, -0.0143F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone4 = Shell.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(287, 285).addBox(-22.9F, -87.0F, -17.9F, 5.0F, 82.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(307, 280).addBox(-17.9F, -87.0F, -22.9F, 0.0F, 82.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 0.0F, 0.1F));

		PartDefinition bone5 = Shell.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(286, 285).addBox(-23.9F, -86.0F, -17.9F, 6.0F, 81.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(307, 280).addBox(-17.9F, -87.0F, -22.9F, 0.0F, 82.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.0F, 0.1F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone6 = Shell.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(286, 285).addBox(-23.9F, -86.0F, -17.9F, 6.0F, 81.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(306, 279).addBox(-17.9F, -86.0F, -23.9F, 0.0F, 81.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.0F, 0.1F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition bone7 = Shell.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(287, 285).addBox(-22.8F, -87.0F, -17.8F, 5.0F, 82.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(306, 279).addBox(-17.8F, -86.0F, -23.8F, 0.0F, 81.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.0F, 0.1F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone = Shell.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(130, 291).addBox(-22.0F, -36.4444F, 15.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 2.5556F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 2.5556F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(130, 289).addBox(-21.9F, -35.4444F, -2.0F, 1.0F, 74.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(131, 291).addBox(-22.0F, -36.4444F, -17.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, 17.5556F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 20.5556F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 20.5556F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, 35.5556F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(364, 292).addBox(-22.8F, -36.4444F, 17.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(364, 292).addBox(-23.0F, -36.4444F, -18.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -0.4444F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, -15.4444F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, -15.4444F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -18.4444F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -35.3944F, -15.0F, 1.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(290, 102).addBox(-22.0F, -36.2944F, -15.0F, 1.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(290, 102).addBox(-22.0F, -36.2944F, 0.0F, 1.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -43.5556F, 0.0F));

		PartDefinition bone2 = Shell.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(130, 291).addBox(-22.0F, -36.4444F, 15.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 2.5556F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 2.5556F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(130, 289).addBox(-21.9F, -35.4444F, -2.0F, 1.0F, 74.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(131, 291).addBox(-22.0F, -36.4444F, -17.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, 17.5556F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 20.5556F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 20.5556F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, 35.5556F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(364, 292).addBox(-22.8F, -36.4444F, 17.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(364, 292).addBox(-23.0F, -36.4444F, -18.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -0.4444F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, -15.4444F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, -15.4444F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -18.4444F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -35.3944F, -15.0F, 1.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(290, 102).addBox(-22.0F, -36.2944F, -15.0F, 1.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(290, 102).addBox(-22.0F, -36.2944F, 0.0F, 1.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -43.5556F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone3 = Shell.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(130, 291).addBox(-22.0F, -36.4444F, 15.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 2.5556F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 2.5556F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(130, 289).addBox(-21.9F, -35.4444F, -2.0F, 1.0F, 74.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(131, 291).addBox(-22.0F, -36.4444F, -17.0F, 1.0F, 75.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, 17.5556F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 20.5556F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, 20.5556F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, 35.5556F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(364, 292).addBox(-22.8F, -36.4444F, 17.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(364, 292).addBox(-23.0F, -36.4444F, -18.0F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -0.4444F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, -15.4444F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 115).addBox(-22.3F, -15.4444F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -18.4444F, -15.0F, 1.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(275, 86).addBox(-22.0F, -35.3944F, -15.0F, 1.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(290, 102).addBox(-22.0F, -36.2944F, -15.0F, 1.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(290, 102).addBox(-22.0F, -36.2944F, 0.0F, 1.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -43.5556F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone12 = Shell.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offset(-26.0F, -4.3F, -49.0F));

		PartDefinition bone13 = Shell.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offset(-26.0F, -4.3F, -49.0F));

		PartDefinition bone15 = bone13.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offsetAndRotation(26.0086F, 0.1418F, 22.8741F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r111 = bone15.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(52, 108).addBox(0.0F, 0.0142F, 51.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.4142F, 51.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.4142F, 50.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.0142F, 50.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.4142F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.0142F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(0.0F, 0.0142F, 2.0F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(0.0F, 0.4142F, 2.0F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1366F, -0.8511F, -25.9914F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r112 = bone15.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(52, 108).addBox(0.0F, 0.4142F, 50.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.4142F, 51.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(0.0F, 0.4142F, 2.0F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.4142F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1266F, -0.8411F, -26.1414F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bone9 = bone15.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.1359F, -0.8518F, -25.9914F));

		PartDefinition cube_r113 = bone9.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(99, 108).addBox(0.0F, 0.4142F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0093F, 0.0108F, -0.15F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r114 = bone9.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(99, 108).addBox(-0.25F, 0.6642F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 0.8142F, -1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-0.0101F, 0.01F, -0.15F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r115 = bone9.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(99, 108).addBox(-0.65F, 0.8142F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F)), PartPose.offsetAndRotation(0.0819F, 0.1868F, -0.09F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r116 = bone9.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(99, 108).addBox(0.0F, 0.4142F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.0142F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0007F, 0.0007F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r117 = bone9.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(98, 108).addBox(-1.0F, 0.8616F, 0.0083F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.322F, 0.4558F, -0.6114F, -0.6196F, 0.0F, 0.7854F));

		PartDefinition cube_r118 = bone9.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(99, 108).addBox(0.0F, 0.0116F, 0.0083F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0007F, 0.0007F, 0.0F, -0.6196F, 0.0F, 0.7854F));

		PartDefinition cube_r119 = bone9.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(99, 108).addBox(-0.25F, 0.6642F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 0.8142F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.44F, 0.6542F, -1.16F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(-0.4F, 0.8142F, -1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.4F, 0.6142F, -1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.43F, 0.4442F, -1.02F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(-0.48F, 0.8942F, -1.37F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bone11 = bone15.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.1266F, -0.8411F, 25.0086F));

		PartDefinition cube_r120 = bone11.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(99, 108).addBox(0.001F, 0.8142F, 0.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(-0.25F, 1.0642F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 1.2142F, 1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.46F, 1.2742F, 1.34F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F))
				.texOffs(99, 108).addBox(0.001F, 0.8F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.001F, 0.4F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(-0.25F, 1.05F, 0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 1.2F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.44F, 1.04F, 1.16F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(-0.4F, 1.2F, 1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.4F, 1.0F, 1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.43F, 0.83F, 1.02F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(-0.48F, 1.28F, 1.37F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(0.2821F, -0.2835F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r121 = bone11.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(98, 108).addBox(-0.45F, 2.1884F, 0.0247F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F))
				.texOffs(99, 108).addBox(0.001F, 0.9063F, -0.4182F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2821F, -0.2835F, 0.0F, 0.6196F, 0.0F, 0.7854F));

		PartDefinition bone16 = bone13.addOrReplaceChild("bone16", CubeListBuilder.create(), PartPose.offset(26.0086F, 0.1418F, 22.8741F));

		PartDefinition cube_r122 = bone16.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(52, 108).addBox(18.4809F, -18.4799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0657F, 23.7328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0657F, 24.7328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(18.4809F, -18.0657F, -24.2672F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.0657F, -25.2672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0799F, 23.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.4799F, 23.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.0799F, -25.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.4799F, -25.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(18.4809F, -18.4799F, -24.1172F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(18.4809F, -18.0799F, -24.1172F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-52.1432F, -0.8418F, 26.2431F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bone17 = bone16.addOrReplaceChild("bone17", CubeListBuilder.create(), PartPose.offset(0.1359F, -0.8518F, -25.9914F));

		PartDefinition cube_r123 = bone17.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(99, 108).addBox(18.4809F, -18.0657F, -26.2672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8157F, -27.0172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6657F, -27.3172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0199F, -17.6057F, -27.4572F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F))
				.texOffs(99, 108).addBox(18.4809F, -18.0799F, -26.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.4799F, -26.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8299F, -26.8672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, -27.3672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0399F, -17.8399F, -27.2772F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, -27.1672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0799F, -17.8799F, -27.1672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0499F, -18.0499F, -27.1372F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(17.9999F, -17.5999F, -27.4872F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(-52.2791F, 0.01F, 52.2345F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r124 = bone17.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(98, 108).addBox(18.0299F, 1.4036F, -32.4367F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F))
				.texOffs(99, 108).addBox(18.4809F, 0.1216F, -31.9938F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-52.2791F, 0.01F, 52.2345F, -0.6196F, 0.0F, 0.7854F));

		PartDefinition bone34 = bone16.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offset(0.1266F, -0.8411F, 25.0086F));

		PartDefinition cube_r125 = bone34.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(99, 108).addBox(18.4809F, -18.0657F, 25.0328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8157F, 25.7828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6657F, 26.0828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0199F, -17.6057F, 26.2228F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F))
				.texOffs(99, 108).addBox(18.4809F, -18.0799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.4799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8299F, 25.6328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, 26.1328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0399F, -17.8399F, 26.0428F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, 25.9328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0799F, -17.8799F, 25.9328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0499F, -18.0499F, 25.9028F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(17.9999F, -17.5999F, 26.2528F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(-52.2697F, -0.0007F, 1.2345F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r126 = bone34.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(98, 108).addBox(18.0299F, 1.2675F, 31.2458F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F))
				.texOffs(99, 108).addBox(18.4809F, -0.0146F, 30.8029F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-52.2697F, -0.0007F, 1.2345F, 0.6196F, 0.0F, 0.7854F));

		PartDefinition bone35 = bone13.addOrReplaceChild("bone35", CubeListBuilder.create(), PartPose.offsetAndRotation(26.0086F, 0.1418F, 22.8741F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r127 = bone35.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(52, 108).addBox(18.4809F, -18.4799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0657F, 23.7328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0657F, 24.7328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(18.4809F, -18.0657F, -24.2672F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.0657F, -25.2672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.0799F, 23.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(18.4809F, -18.4799F, 23.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.0799F, -25.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.4799F, -25.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(18.4809F, -18.4799F, -24.1172F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(18.4809F, -18.0799F, -24.1172F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.2604F, -0.8418F, 0.1086F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bone36 = bone35.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offset(0.1359F, -0.8518F, -25.9914F));

		PartDefinition cube_r128 = bone36.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(99, 108).addBox(18.4809F, -18.0657F, -26.2672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8157F, -27.0172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6657F, -27.3172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0199F, -17.6057F, -27.4572F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F))
				.texOffs(99, 108).addBox(18.4809F, -18.0799F, -26.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.4799F, -26.1172F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8299F, -26.8672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, -27.3672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0399F, -17.8399F, -27.2772F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, -27.1672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0799F, -17.8799F, -27.1672F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0499F, -18.0499F, -27.1372F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(17.9999F, -17.5999F, -27.4872F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(-78.3963F, 0.01F, 26.1F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r129 = bone36.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(98, 108).addBox(18.0299F, 1.4036F, -32.4367F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F))
				.texOffs(99, 108).addBox(18.4809F, 0.1216F, -31.9938F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.3963F, 0.01F, 26.1F, -0.6196F, 0.0F, 0.7854F));

		PartDefinition bone37 = bone35.addOrReplaceChild("bone37", CubeListBuilder.create(), PartPose.offset(0.1266F, -0.8411F, 25.0086F));

		PartDefinition cube_r130 = bone37.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(99, 108).addBox(18.4809F, -18.0657F, 25.0328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8157F, 25.7828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6657F, 26.0828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0199F, -17.6057F, 26.2228F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F))
				.texOffs(99, 108).addBox(18.4809F, -18.0799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.4809F, -18.4799F, 24.8828F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(18.2299F, -17.8299F, 25.6328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, 26.1328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0399F, -17.8399F, 26.0428F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(18.0799F, -17.6799F, 25.9328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0799F, -17.8799F, 25.9328F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(18.0499F, -18.0499F, 25.9028F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(17.9999F, -17.5999F, 26.2528F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(-78.387F, -0.0007F, -24.9F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r131 = bone37.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(98, 108).addBox(18.0299F, 1.2675F, 31.2458F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F))
				.texOffs(99, 108).addBox(18.4809F, -0.0146F, 30.8029F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.387F, -0.0007F, -24.9F, 0.6196F, 0.0F, 0.7854F));

		PartDefinition bone8 = bone13.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(26.0086F, 0.1418F, 22.8741F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r132 = bone8.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(52, 108).addBox(0.0F, 0.0142F, 51.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.4142F, 51.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.4142F, 50.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.0142F, 50.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.4142F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.0142F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(0.0F, 0.0142F, 2.0F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(0.0F, 0.4142F, 2.0F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.9806F, -0.8511F, -52.1259F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r133 = bone8.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(52, 108).addBox(0.0F, 0.4142F, 50.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 108).addBox(0.0F, 0.4142F, 51.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(52, 61).addBox(0.0F, 0.4142F, 2.0F, 1.0F, 1.0F, 48.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.4142F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.9906F, -0.8411F, -52.2759F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bone10 = bone8.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.1359F, -0.8518F, -25.9914F));

		PartDefinition cube_r134 = bone10.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(99, 108).addBox(0.0F, 0.4142F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.1266F, 0.0108F, -26.2845F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r135 = bone10.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(99, 108).addBox(-0.25F, 0.6642F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 0.8142F, -1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-26.1273F, 0.01F, -26.2845F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r136 = bone10.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(99, 108).addBox(-0.65F, 0.8142F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F)), PartPose.offsetAndRotation(-26.0354F, 0.1868F, -26.2245F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r137 = bone10.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(99, 108).addBox(0.0F, 0.4142F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.0F, 0.0142F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.1165F, 0.0007F, -26.1345F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r138 = bone10.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(98, 108).addBox(-1.0F, 0.8616F, 0.0083F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(-25.7952F, 0.4558F, -26.746F, -0.6196F, 0.0F, 0.7854F));

		PartDefinition cube_r139 = bone10.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(99, 108).addBox(0.0F, 0.0116F, 0.0083F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.1165F, 0.0007F, -26.1345F, -0.6196F, 0.0F, 0.7854F));

		PartDefinition cube_r140 = bone10.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(99, 108).addBox(-0.25F, 0.6642F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 0.8142F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.44F, 0.6542F, -1.16F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(-0.4F, 0.8142F, -1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.4F, 0.6142F, -1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.43F, 0.4442F, -1.02F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(-0.48F, 0.8942F, -1.37F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(-26.1172F, 0.0F, -26.1345F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bone14 = bone8.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offset(0.1266F, -0.8411F, 25.0086F));

		PartDefinition cube_r141 = bone14.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(99, 108).addBox(0.001F, 0.8142F, 0.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(-0.25F, 1.0642F, 0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 1.2142F, 1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.46F, 1.2742F, 1.34F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.46F))
				.texOffs(99, 108).addBox(0.001F, 0.8F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(0.001F, 0.4F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(99, 108).addBox(-0.25F, 1.05F, 0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(99, 108).addBox(-0.4F, 1.2F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.44F, 1.04F, 1.16F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.44F))
				.texOffs(99, 108).addBox(-0.4F, 1.2F, 1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.4F, 1.0F, 1.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(99, 108).addBox(-0.43F, 0.83F, 1.02F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.43F))
				.texOffs(99, 108).addBox(-0.48F, 1.28F, 1.37F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.48F)), PartPose.offsetAndRotation(-25.8351F, -0.2835F, -26.1345F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r142 = bone14.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(98, 108).addBox(-0.45F, 2.1884F, 0.0247F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.45F))
				.texOffs(99, 108).addBox(0.001F, 0.9063F, -0.4182F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.8351F, -0.2835F, -26.1345F, 0.6196F, 0.0F, 0.7854F));

		PartDefinition Boti = Root.addOrReplaceChild("Boti", CubeListBuilder.create(), PartPose.offset(0.5F, -44.25F, 0.0F));

		PartDefinition boti_r1 = Boti.addOrReplaceChild("boti_r1", CubeListBuilder.create().texOffs(294, 196).addBox(-18.0F, -39.5F, 0.0F, 35.0F, 79.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition Emmisive = Root.addOrReplaceChild("Emmisive", CubeListBuilder.create().texOffs(1, 251).addBox(-22.6F, -77.0F, 2.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(1, 251).addBox(-22.6F, -77.0F, -15.0F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r143 = Emmisive.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(0, 263).addBox(2.0F, -7.5F, -0.5F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 263).addBox(-15.0F, -7.5F, -0.5F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -69.5F, 22.1F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r144 = Emmisive.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(1, 251).addBox(-0.5F, -7.5F, -6.5F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.1F, -69.5F, -8.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r145 = Emmisive.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(1, 251).addBox(-0.5F, -7.5F, -6.5F, 1.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.1F, -69.5F, 8.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r146 = Emmisive.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(28, 17).addBox(-2.0F, -103.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition text_POLICE4 = Emmisive.addOrReplaceChild("text_POLICE4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition character_p7 = text_POLICE4.addOrReplaceChild("character_p7", CubeListBuilder.create().texOffs(346, 113).addBox(-12.5F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.3F, -51.2F, -18.0F, 0.8F, 2.0F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -51.2F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -50.0F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition text_BOX4 = character_p7.addOrReplaceChild("text_BOX4", CubeListBuilder.create(), PartPose.offset(12.8F, -48.0F, -18.0F));

		PartDefinition character_b7 = text_BOX4.addOrReplaceChild("character_b7", CubeListBuilder.create().texOffs(346, 113).addBox(-6.18F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, -0.8F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 1.6F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 0.4F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 0.6F, -1.2F, 0.4F, 0.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 1.0F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, -0.8F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition text_PUBLIC4 = character_b7.addOrReplaceChild("text_PUBLIC4", CubeListBuilder.create(), PartPose.offset(-13.78F, 0.9F, -1.2F));

		PartDefinition character_p8 = text_PUBLIC4.addOrReplaceChild("character_p8", CubeListBuilder.create().texOffs(346, 113).addBox(-9.2F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.6F, -64.8F, -24.8F, 0.4F, 1.0F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition text_CALL4 = character_p8.addOrReplaceChild("text_CALL4", CubeListBuilder.create(), PartPose.offset(-7.35F, -61.7F, -24.8F));

		PartDefinition character_c10 = text_CALL4.addOrReplaceChild("character_c10", CubeListBuilder.create().texOffs(346, 113).addBox(-5.76F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -61.6F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_a4 = text_CALL4.addOrReplaceChild("character_a4", CubeListBuilder.create().texOffs(346, 113).addBox(-4.7F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.6F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.1F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.0F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l13 = text_CALL4.addOrReplaceChild("character_l13", CubeListBuilder.create().texOffs(346, 113).addBox(-3.64F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.24F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l14 = text_CALL4.addOrReplaceChild("character_l14", CubeListBuilder.create().texOffs(346, 113).addBox(-2.58F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.18F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_u4 = text_PUBLIC4.addOrReplaceChild("character_u4", CubeListBuilder.create().texOffs(346, 113).addBox(-8.14F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.34F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.74F, -63.6F, -24.8F, 0.4F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_b8 = text_PUBLIC4.addOrReplaceChild("character_b8", CubeListBuilder.create().texOffs(346, 113).addBox(-6.88F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -63.6F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.1F, -24.8F, 0.2F, 0.2F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -63.9F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.8F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_l15 = text_PUBLIC4.addOrReplaceChild("character_l15", CubeListBuilder.create().texOffs(346, 113).addBox(-5.82F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.42F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_i7 = text_PUBLIC4.addOrReplaceChild("character_i7", CubeListBuilder.create().texOffs(346, 113).addBox(-4.76F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_c11 = text_PUBLIC4.addOrReplaceChild("character_c11", CubeListBuilder.create().texOffs(346, 113).addBox(-4.3F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -64.8F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_o7 = text_BOX4.addOrReplaceChild("character_o7", CubeListBuilder.create().texOffs(346, 113).addBox(-4.06F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, -0.8F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.46F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, 1.6F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_x4 = text_BOX4.addOrReplaceChild("character_x4", CubeListBuilder.create().texOffs(346, 113).addBox(-1.54F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.54F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.04F, 0.1F, -1.2F, 1.0F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_o8 = text_POLICE4.addOrReplaceChild("character_o8", CubeListBuilder.create().texOffs(346, 113).addBox(-10.38F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -51.2F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.78F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -48.8F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_l16 = text_POLICE4.addOrReplaceChild("character_l16", CubeListBuilder.create().texOffs(346, 113).addBox(-7.86F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.06F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_i8 = text_POLICE4.addOrReplaceChild("character_i8", CubeListBuilder.create().texOffs(346, 113).addBox(-5.74F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_c12 = text_POLICE4.addOrReplaceChild("character_c12", CubeListBuilder.create().texOffs(346, 113).addBox(-4.82F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_e4 = text_POLICE4.addOrReplaceChild("character_e4", CubeListBuilder.create().texOffs(346, 113).addBox(-2.7F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -50.0F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition text_POLICE3 = Emmisive.addOrReplaceChild("text_POLICE3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition character_p5 = text_POLICE3.addOrReplaceChild("character_p5", CubeListBuilder.create().texOffs(346, 113).addBox(-12.5F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.3F, -51.2F, -18.0F, 0.8F, 2.0F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -51.2F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -50.0F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition text_BOX3 = character_p5.addOrReplaceChild("text_BOX3", CubeListBuilder.create(), PartPose.offset(12.8F, -48.0F, -18.0F));

		PartDefinition character_b5 = text_BOX3.addOrReplaceChild("character_b5", CubeListBuilder.create().texOffs(346, 113).addBox(-6.18F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, -0.8F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 1.6F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 0.4F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 0.6F, -1.2F, 0.4F, 0.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 1.0F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, -0.8F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition text_PUBLIC3 = character_b5.addOrReplaceChild("text_PUBLIC3", CubeListBuilder.create(), PartPose.offset(-13.78F, 0.9F, -1.2F));

		PartDefinition character_p6 = text_PUBLIC3.addOrReplaceChild("character_p6", CubeListBuilder.create().texOffs(346, 113).addBox(-9.2F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.6F, -64.8F, -24.8F, 0.4F, 1.0F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition text_CALL3 = character_p6.addOrReplaceChild("text_CALL3", CubeListBuilder.create(), PartPose.offset(-7.35F, -61.7F, -24.8F));

		PartDefinition character_c7 = text_CALL3.addOrReplaceChild("character_c7", CubeListBuilder.create().texOffs(346, 113).addBox(-5.76F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -61.6F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_a3 = text_CALL3.addOrReplaceChild("character_a3", CubeListBuilder.create().texOffs(346, 113).addBox(-4.7F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.6F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.1F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.0F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l9 = text_CALL3.addOrReplaceChild("character_l9", CubeListBuilder.create().texOffs(346, 113).addBox(-3.64F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.24F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l10 = text_CALL3.addOrReplaceChild("character_l10", CubeListBuilder.create().texOffs(346, 113).addBox(-2.58F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.18F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_u3 = text_PUBLIC3.addOrReplaceChild("character_u3", CubeListBuilder.create().texOffs(346, 113).addBox(-8.14F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.34F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.74F, -63.6F, -24.8F, 0.4F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_b6 = text_PUBLIC3.addOrReplaceChild("character_b6", CubeListBuilder.create().texOffs(346, 113).addBox(-6.88F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -63.6F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.1F, -24.8F, 0.2F, 0.2F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -63.9F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.8F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_l11 = text_PUBLIC3.addOrReplaceChild("character_l11", CubeListBuilder.create().texOffs(346, 113).addBox(-5.82F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.42F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_i5 = text_PUBLIC3.addOrReplaceChild("character_i5", CubeListBuilder.create().texOffs(346, 113).addBox(-4.76F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_c8 = text_PUBLIC3.addOrReplaceChild("character_c8", CubeListBuilder.create().texOffs(346, 113).addBox(-4.3F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -64.8F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_o5 = text_BOX3.addOrReplaceChild("character_o5", CubeListBuilder.create().texOffs(346, 113).addBox(-4.06F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, -0.8F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.46F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, 1.6F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_x3 = text_BOX3.addOrReplaceChild("character_x3", CubeListBuilder.create().texOffs(346, 113).addBox(-1.54F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.54F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.04F, 0.1F, -1.2F, 1.0F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_o6 = text_POLICE3.addOrReplaceChild("character_o6", CubeListBuilder.create().texOffs(346, 113).addBox(-10.38F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -51.2F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.78F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -48.8F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_l12 = text_POLICE3.addOrReplaceChild("character_l12", CubeListBuilder.create().texOffs(346, 113).addBox(-7.86F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.06F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_i6 = text_POLICE3.addOrReplaceChild("character_i6", CubeListBuilder.create().texOffs(346, 113).addBox(-5.74F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_c9 = text_POLICE3.addOrReplaceChild("character_c9", CubeListBuilder.create().texOffs(346, 113).addBox(-4.82F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_e3 = text_POLICE3.addOrReplaceChild("character_e3", CubeListBuilder.create().texOffs(346, 113).addBox(-2.7F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -50.0F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition text_POLICE = Emmisive.addOrReplaceChild("text_POLICE", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition character_p = text_POLICE.addOrReplaceChild("character_p", CubeListBuilder.create().texOffs(346, 113).addBox(-12.5F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.3F, -51.2F, -18.0F, 0.8F, 2.0F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -51.2F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -50.0F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition text_BOX = character_p.addOrReplaceChild("text_BOX", CubeListBuilder.create(), PartPose.offset(12.8F, -48.0F, -18.0F));

		PartDefinition character_b = text_BOX.addOrReplaceChild("character_b", CubeListBuilder.create().texOffs(346, 113).addBox(-6.18F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, -0.8F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 1.6F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 0.4F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 0.6F, -1.2F, 0.4F, 0.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 1.0F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, -0.8F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition text_PUBLIC = character_b.addOrReplaceChild("text_PUBLIC", CubeListBuilder.create(), PartPose.offset(-13.78F, 0.9F, -1.2F));

		PartDefinition character_p2 = text_PUBLIC.addOrReplaceChild("character_p2", CubeListBuilder.create().texOffs(346, 113).addBox(-9.2F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.6F, -64.8F, -24.8F, 0.4F, 1.0F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition text_CALL = character_p2.addOrReplaceChild("text_CALL", CubeListBuilder.create(), PartPose.offset(-7.35F, -61.7F, -24.8F));

		PartDefinition character_c3 = text_CALL.addOrReplaceChild("character_c3", CubeListBuilder.create().texOffs(346, 113).addBox(-5.76F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -61.6F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_a = text_CALL.addOrReplaceChild("character_a", CubeListBuilder.create().texOffs(346, 113).addBox(-4.7F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.6F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.1F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.0F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l3 = text_CALL.addOrReplaceChild("character_l3", CubeListBuilder.create().texOffs(346, 113).addBox(-3.64F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.24F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l4 = text_CALL.addOrReplaceChild("character_l4", CubeListBuilder.create().texOffs(346, 113).addBox(-2.58F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.18F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_u = text_PUBLIC.addOrReplaceChild("character_u", CubeListBuilder.create().texOffs(346, 113).addBox(-8.14F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.34F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.74F, -63.6F, -24.8F, 0.4F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_b2 = text_PUBLIC.addOrReplaceChild("character_b2", CubeListBuilder.create().texOffs(346, 113).addBox(-6.88F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -63.6F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.1F, -24.8F, 0.2F, 0.2F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -63.9F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.8F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_l2 = text_PUBLIC.addOrReplaceChild("character_l2", CubeListBuilder.create().texOffs(346, 113).addBox(-5.82F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.42F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_i2 = text_PUBLIC.addOrReplaceChild("character_i2", CubeListBuilder.create().texOffs(346, 113).addBox(-4.76F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_c2 = text_PUBLIC.addOrReplaceChild("character_c2", CubeListBuilder.create().texOffs(346, 113).addBox(-4.3F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -64.8F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_o2 = text_BOX.addOrReplaceChild("character_o2", CubeListBuilder.create().texOffs(346, 113).addBox(-4.06F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, -0.8F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.46F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, 1.6F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_x = text_BOX.addOrReplaceChild("character_x", CubeListBuilder.create().texOffs(346, 113).addBox(-1.54F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.54F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.04F, 0.1F, -1.2F, 1.0F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_o = text_POLICE.addOrReplaceChild("character_o", CubeListBuilder.create().texOffs(346, 113).addBox(-10.38F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -51.2F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.78F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -48.8F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_l = text_POLICE.addOrReplaceChild("character_l", CubeListBuilder.create().texOffs(346, 113).addBox(-7.86F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.06F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_i = text_POLICE.addOrReplaceChild("character_i", CubeListBuilder.create().texOffs(346, 113).addBox(-5.74F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_c = text_POLICE.addOrReplaceChild("character_c", CubeListBuilder.create().texOffs(346, 113).addBox(-4.82F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_e = text_POLICE.addOrReplaceChild("character_e", CubeListBuilder.create().texOffs(346, 113).addBox(-2.7F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -50.0F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition text_POLICE2 = Emmisive.addOrReplaceChild("text_POLICE2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition character_p3 = text_POLICE2.addOrReplaceChild("character_p3", CubeListBuilder.create().texOffs(346, 113).addBox(-12.5F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.3F, -51.2F, -18.0F, 0.8F, 2.0F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -51.2F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-11.7F, -50.0F, -18.0F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition text_BOX2 = character_p3.addOrReplaceChild("text_BOX2", CubeListBuilder.create(), PartPose.offset(12.8F, -48.0F, -18.0F));

		PartDefinition character_b3 = text_BOX2.addOrReplaceChild("character_b3", CubeListBuilder.create().texOffs(346, 113).addBox(-6.18F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, -0.8F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 1.6F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.38F, 0.4F, -1.2F, 0.4F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 0.6F, -1.2F, 0.4F, 0.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, 1.0F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.98F, -0.8F, -1.2F, 0.8F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition text_PUBLIC2 = character_b3.addOrReplaceChild("text_PUBLIC2", CubeListBuilder.create(), PartPose.offset(-13.78F, 0.9F, -1.2F));

		PartDefinition character_p4 = text_PUBLIC2.addOrReplaceChild("character_p4", CubeListBuilder.create().texOffs(346, 113).addBox(-9.2F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.6F, -64.8F, -24.8F, 0.4F, 1.0F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.8F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition text_CALL2 = character_p4.addOrReplaceChild("text_CALL2", CubeListBuilder.create(), PartPose.offset(-7.35F, -61.7F, -24.8F));

		PartDefinition character_c4 = text_CALL2.addOrReplaceChild("character_c4", CubeListBuilder.create().texOffs(346, 113).addBox(-5.76F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -61.6F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.36F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_a2 = text_CALL2.addOrReplaceChild("character_a2", CubeListBuilder.create().texOffs(346, 113).addBox(-4.7F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.6F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.1F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.3F, -61.0F, -26.4F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l5 = text_CALL2.addOrReplaceChild("character_l5", CubeListBuilder.create().texOffs(346, 113).addBox(-3.64F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.24F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_l6 = text_CALL2.addOrReplaceChild("character_l6", CubeListBuilder.create().texOffs(346, 113).addBox(-2.58F, -61.6F, -26.4F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.18F, -60.4F, -26.4F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(4.96F, 60.0F, 26.4F));

		PartDefinition character_u2 = text_PUBLIC2.addOrReplaceChild("character_u2", CubeListBuilder.create().texOffs(346, 113).addBox(-8.14F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.34F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.74F, -63.6F, -24.8F, 0.4F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_b4 = text_PUBLIC2.addOrReplaceChild("character_b4", CubeListBuilder.create().texOffs(346, 113).addBox(-6.88F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.8F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -63.6F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.48F, -64.2F, -24.8F, 0.2F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.1F, -24.8F, 0.2F, 0.2F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -63.9F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-6.28F, -64.8F, -24.8F, 0.4F, 0.7F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_l7 = text_PUBLIC2.addOrReplaceChild("character_l7", CubeListBuilder.create().texOffs(346, 113).addBox(-5.82F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-5.42F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_i3 = text_PUBLIC2.addOrReplaceChild("character_i3", CubeListBuilder.create().texOffs(346, 113).addBox(-4.76F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_c5 = text_PUBLIC2.addOrReplaceChild("character_c5", CubeListBuilder.create().texOffs(346, 113).addBox(-4.3F, -64.8F, -24.8F, 0.4F, 1.6F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -64.8F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.9F, -63.6F, -24.8F, 0.6F, 0.4F, 5.55F, new CubeDeformation(0.0F)), PartPose.offset(8.4F, 63.2F, 24.8F));

		PartDefinition character_o3 = text_BOX2.addOrReplaceChild("character_o3", CubeListBuilder.create().texOffs(346, 113).addBox(-4.06F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, -0.8F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-2.46F, -0.8F, -1.2F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-3.26F, 1.6F, -1.2F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_x2 = text_BOX2.addOrReplaceChild("character_x2", CubeListBuilder.create().texOffs(346, 113).addBox(-1.54F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.54F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, -0.8F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-0.34F, 1.3F, -1.2F, 0.8F, 1.1F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.04F, 0.1F, -1.2F, 1.0F, 1.4F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(2.58F, -2.4F, 1.2F));

		PartDefinition character_o4 = text_POLICE2.addOrReplaceChild("character_o4", CubeListBuilder.create().texOffs(346, 113).addBox(-10.38F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -51.2F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-8.78F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-9.58F, -48.8F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_l8 = text_POLICE2.addOrReplaceChild("character_l8", CubeListBuilder.create().texOffs(346, 113).addBox(-7.86F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-7.06F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_i4 = text_POLICE2.addOrReplaceChild("character_i4", CubeListBuilder.create().texOffs(346, 113).addBox(-5.74F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_c6 = text_POLICE2.addOrReplaceChild("character_c6", CubeListBuilder.create().texOffs(346, 113).addBox(-4.82F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-4.02F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition character_e2 = text_POLICE2.addOrReplaceChild("character_e2", CubeListBuilder.create().texOffs(346, 113).addBox(-2.7F, -51.2F, -18.0F, 0.8F, 3.2F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -51.2F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -50.0F, -18.0F, 0.8F, 0.8F, 5.6F, new CubeDeformation(0.0F))
				.texOffs(346, 113).addBox(-1.9F, -48.8F, -18.0F, 1.2F, 0.8F, 5.6F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, -34.25F, -9.25F));

		PartDefinition Doors = Root.addOrReplaceChild("Doors", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LeftDoor = Doors.addOrReplaceChild("LeftDoor", CubeListBuilder.create(), PartPose.offset(-16.9167F, -42.2308F, -23.0167F));

		PartDefinition LeftNonEmmisives = LeftDoor.addOrReplaceChild("LeftNonEmmisives", CubeListBuilder.create().texOffs(6, 284).addBox(-0.0833F, -37.8333F, 0.0167F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 139).addBox(1.9167F, 34.1667F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 284).addBox(14.9167F, -37.8333F, 0.0167F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 127).addBox(1.9167F, 19.1667F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 111).addBox(1.9167F, 1.1667F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 135).addBox(1.9167F, 16.1667F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 131).addBox(1.9167F, -1.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 74).addBox(1.9167F, -34.8333F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 127).addBox(1.9167F, -19.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 127).addBox(1.9167F, -37.8333F, 0.0167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(320, 289).addBox(14.9167F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(320, 292).addBox(15.9167F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(320, 289).addBox(-0.0833F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(320, 292).addBox(0.9167F, -37.8333F, 0.9167F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(340, 35).addBox(1.9167F, -37.8333F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(340, 35).addBox(1.9167F, 34.1667F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(340, 35).addBox(1.9167F, -19.8333F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(340, 35).addBox(1.9167F, 16.1667F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(340, 35).addBox(1.9167F, -1.8333F, 0.9167F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(234, 281).addBox(15.9167F, -37.8333F, -0.9833F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -2.8333F, 1.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(173, 60).addBox(1.9167F, -15.8333F, 1.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -2.8333F, 2.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(173, 60).addBox(1.9167F, -15.8333F, 2.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(173, 60).addBox(1.9167F, -15.8333F, 3.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -2.8333F, 3.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(161, 61).addBox(2.9167F, -15.8333F, 7.6167F, 11.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(171, 60).addBox(13.9167F, -15.8333F, 2.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(171, 60).addBox(13.9167F, -15.8333F, 3.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(171, 60).addBox(13.9167F, -15.8333F, 1.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -16.8333F, 3.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -16.8333F, 4.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(173, 60).addBox(1.9167F, -15.8333F, 4.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -2.8333F, 4.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(171, 60).addBox(13.9167F, -15.8333F, 4.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(171, 60).addBox(13.9167F, -15.8333F, 5.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -16.8333F, 5.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(173, 60).addBox(1.9167F, -15.8333F, 5.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -2.8333F, 5.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(171, 60).addBox(13.9167F, -15.8333F, 6.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -16.8333F, 6.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(173, 60).addBox(1.9167F, -15.8333F, 6.6167F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -2.8333F, 6.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -16.8333F, 2.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(157, 59).addBox(1.9167F, -16.8333F, 1.6167F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0641F, 0.0F));

		PartDefinition cube_r147 = LeftNonEmmisives.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(340, 113).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, -26.9619F, 0.3702F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r148 = LeftNonEmmisives.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(0, 111).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 111).addBox(-0.5F, -25.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2703F, 26.6667F, 0.3703F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r149 = LeftNonEmmisives.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(0, 111).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, 19.5203F, 0.3703F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r150 = LeftNonEmmisives.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(12, 111).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 111).addBox(-0.5F, -25.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5631F, 26.6667F, 0.3703F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r151 = LeftNonEmmisives.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(0, 125).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, 34.1667F, 0.0167F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r152 = LeftNonEmmisives.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(0, 125).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, 16.1667F, 0.0167F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r153 = LeftNonEmmisives.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(0, 111).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, 1.5203F, 0.3703F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r154 = LeftNonEmmisives.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(1, 105).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, -1.8333F, 0.0167F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r155 = LeftNonEmmisives.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(13, 91).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(12, 74).addBox(-0.5F, -25.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5417F, -9.3083F, 0.3667F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r156 = LeftNonEmmisives.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(1, 91).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, -16.4869F, 0.3702F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r157 = LeftNonEmmisives.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(1, 91).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 74).addBox(-0.5F, -25.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2667F, -9.3083F, 0.3667F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r158 = LeftNonEmmisives.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(0, 74).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, -34.4868F, 0.3702F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r159 = LeftNonEmmisives.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(348, 106).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.7488F, -27.3083F, 0.3667F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r160 = LeftNonEmmisives.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(344, 106).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7488F, -27.3083F, 0.3667F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r161 = LeftNonEmmisives.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(0, 88).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4167F, -19.8333F, 0.0167F, -0.7854F, 0.0F, 0.0F));

		PartDefinition phone = LeftNonEmmisives.addOrReplaceChild("phone", CubeListBuilder.create().texOffs(160, 98).addBox(-12.0F, -7.25F, -20.4F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(167, 96).addBox(-10.75F, -8.25F, -20.425F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(167, 96).addBox(-8.75F, -8.25F, -20.425F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(159, 97).addBox(-12.0F, -6.25F, -21.4F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(158, 96).addBox(-12.0F, -5.75F, -22.4F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(156, 94).addBox(-12.0F, -5.0F, -24.4F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(17.9167F, 1.1667F, 27.0167F));

		PartDefinition cube_r162 = phone.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(158, 80).mirror().addBox(-4.75F, -2.925F, -5.0F, 9.0F, 0.0F, 9.0F, new CubeDeformation(-2.0F)).mirror(false), PartPose.offsetAndRotation(-9.5F, -6.9629F, -22.5569F, -0.4363F, 0.0F, -3.1416F));

		PartDefinition cube_r163 = phone.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(156, 94).addBox(-3.5F, -0.5F, -2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -5.3921F, -22.3761F, 0.4363F, 0.0F, 0.0F));

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
				.texOffs(28, 0).addBox(11.9167F, -6.0192F, -0.0833F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.8141F, 0.5F));

		PartDefinition cube_r164 = phonedoor.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(182, 97).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9167F, -11.5192F, 2.4167F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r165 = phonedoor.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(192, 93).addBox(-0.5F, 0.075F, -1.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(192, 93).addBox(-0.5F, -0.25F, -1.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(192, 93).addBox(-0.5F, -0.5F, -1.775F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(192, 93).addBox(-0.5F, -0.55F, -0.425F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.9047F, -5.9034F, 2.1667F, 0.0F, -1.5708F, 1.789F));

		PartDefinition cube_r166 = phonedoor.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(192, 93).addBox(-0.5F, -2.05F, -1.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
				.texOffs(192, 93).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.7667F, -6.7942F, 2.1667F, 0.0F, -1.5708F, 2.3998F));

		PartDefinition cube_r167 = phonedoor.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(191, 92).addBox(-0.525F, 0.05F, 0.225F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(191, 92).addBox(-0.525F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.7917F, -6.7692F, 2.1667F, 0.0F, -1.5708F, -1.3526F));

		PartDefinition cube_r168 = phonedoor.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(182, 97).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.9167F, -11.5192F, 2.4167F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r169 = phonedoor.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(190, 96).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4167F, -5.5192F, 3.9167F, 0.0F, 0.0F, 0.7854F));

		PartDefinition phoneitem2 = phonedoor.addOrReplaceChild("phoneitem2", CubeListBuilder.create(), PartPose.offset(8.9167F, -11.5192F, 2.4167F));

		PartDefinition cube_r170 = phoneitem2.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(182, 97).addBox(-1.25F, 0.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F))
				.texOffs(182, 97).addBox(-1.25F, 1.0F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(190, 92).addBox(-1.25F, 1.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(190, 92).addBox(-1.25F, 2.0F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(190, 92).addBox(-1.25F, 2.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(190, 92).addBox(-1.25F, 2.875F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(190, 92).addBox(-1.25F, 3.25F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F))
				.texOffs(190, 92).addBox(-1.25F, 3.5F, -3.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition LeftEmmisives = LeftDoor.addOrReplaceChild("LeftEmmisives", CubeListBuilder.create().texOffs(0, 263).addBox(1.9167F, -34.7692F, 0.5167F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.1F));

		PartDefinition RightDoor = Doors.addOrReplaceChild("RightDoor", CubeListBuilder.create(), PartPose.offset(16.9F, -42.1667F, -23.05F));

		PartDefinition RightNonEmmisive = RightDoor.addOrReplaceChild("RightNonEmmisive", CubeListBuilder.create().texOffs(0, 284).addBox(-16.9F, -37.8333F, 0.05F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(222, 281).addBox(-1.9F, -37.8333F, 0.05F, 2.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 119).addBox(-14.9F, 34.1667F, 0.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 58).addBox(-14.9F, 19.1667F, 0.55F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 115).addBox(-14.9F, 16.1667F, 0.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-14.9F, 1.1667F, 0.55F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(132, 111).addBox(-14.9F, -1.8333F, 0.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-14.9F, -16.8333F, 0.55F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 106).addBox(-14.9F, -19.8333F, 0.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 106).addBox(-14.9F, -37.8333F, 0.05F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(290, 289).addBox(-15.9F, -37.8333F, 0.95F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(290, 292).addBox(-16.9F, -37.8333F, 0.95F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(290, 292).addBox(-1.9F, -37.8333F, 0.95F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(290, 289).addBox(-0.9F, -37.8333F, 0.95F, 1.0F, 75.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(340, 35).mirror().addBox(-14.9F, -37.8333F, 0.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(340, 35).mirror().addBox(-14.9F, 34.1667F, 0.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(340, 35).mirror().addBox(-14.9F, -19.8333F, 0.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(340, 35).mirror().addBox(-14.9F, 16.1667F, 0.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(340, 43).addBox(-14.9F, -1.8333F, 0.95F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 74).addBox(-14.9F, -34.8333F, 0.55F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 6).addBox(-15.9F, -11.3333F, -0.95F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 6).addBox(-15.9F, -6.3333F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 6).addBox(-15.9F, -12.3333F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r171 = RightNonEmmisive.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(0, 111).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 111).addBox(-0.5F, -25.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.5464F, 26.6667F, 0.4036F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r172 = RightNonEmmisive.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(0, 125).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, 34.1667F, 0.05F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r173 = RightNonEmmisive.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(12, 111).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 111).addBox(-0.5F, -25.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2536F, 26.6667F, 0.4036F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r174 = RightNonEmmisive.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(0, 111).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, 19.5203F, 0.4036F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r175 = RightNonEmmisive.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(0, 125).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, 16.1667F, 0.05F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r176 = RightNonEmmisive.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(0, 111).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, 1.5203F, 0.4036F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r177 = RightNonEmmisive.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(0, 74).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, -34.4868F, 0.4035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r178 = RightNonEmmisive.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(12, 74).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 16).addBox(-0.5F, 10.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.275F, -27.3083F, 0.4F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r179 = RightNonEmmisive.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(340, 113).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, -27.3083F, 0.05F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r180 = RightNonEmmisive.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(0, 74).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-0.5F, 10.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.55F, -27.3083F, 0.4F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r181 = RightNonEmmisive.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(344, 106).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.775F, -27.3083F, 0.4F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r182 = RightNonEmmisive.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(348, 106).addBox(-0.5F, -7.5F, 0.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.775F, -27.3083F, 0.4F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r183 = RightNonEmmisive.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(0, 88).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, -19.8333F, 0.05F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r184 = RightNonEmmisive.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(0, 16).addBox(-6.5F, -0.5F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, -16.4869F, 0.4035F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r185 = RightNonEmmisive.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(0, 30).addBox(-6.5F, -1.0F, 0.0F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4F, -1.8333F, 0.05F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r186 = RightNonEmmisive.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.3F, -0.8333F, 2.0F, 3.1416F, 0.0F, -1.5708F));

		PartDefinition cube_r187 = RightNonEmmisive.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.4F, -0.3333F, 1.75F, 3.1416F, 0.0F, -1.5708F));

		PartDefinition cube_r188 = RightNonEmmisive.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.3F, 0.1667F, 2.0F, 3.1416F, 0.0F, -1.5708F));

		PartDefinition cube_r189 = RightNonEmmisive.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.3F, 0.1667F, 2.0F, 3.1416F, 0.0F, -1.5708F));

		PartDefinition cube_r190 = RightNonEmmisive.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.3F, -0.8333F, 2.0F, 3.1416F, 0.0F, -1.5708F));

		PartDefinition cube_r191 = RightNonEmmisive.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.55F, -0.3333F, 2.25F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r192 = RightNonEmmisive.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 6).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.3F, -0.8333F, 2.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r193 = RightNonEmmisive.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(28, 6).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 6).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.3F, 0.1667F, 2.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition RightEmmisives = RightDoor.addOrReplaceChild("RightEmmisives", CubeListBuilder.create().texOffs(0, 263).addBox(-14.9F, -34.8333F, 0.55F, 13.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.1F));

		return LayerDefinition.create(meshdefinition, 368, 368);
	}

	public ModelPart root() {
		return this.Root;
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(180));
		poseStack.mulPose(Axis.YP.rotationDegrees(180));
		poseStack.translate(-0.5, -1.3, 0.5);
		poseStack.scale(0.43f, 0.43f, 0.43f);
		this.Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
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
		return Constants.ExteriorModelNames.VOXEL_CHIBNALL;
	}
}