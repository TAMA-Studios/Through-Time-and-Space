/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.models.consoles;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.core.tileentities.consoles.CoralConsoleTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})

public class CoralConsoleModel<T extends CoralConsoleTile> extends HierarchicalModel<Entity>
		implements
			IAnimateableModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("aseoha", "coralconsolemodel"), "main");
	private final ModelPart root;
	private final ModelPart bb_main8;
	private final ModelPart bb_main7;
	private final ModelPart bb_main;
	private final ModelPart bb_main2;
	private final ModelPart bb_main3;
	private final ModelPart bb_main4;
	private final ModelPart bb_main5;
	private final ModelPart bb_main6;
	private final ModelPart arm13;
	private final ModelPart middle_ring7;
	private final ModelPart middle_ring2;
	private final ModelPart arm14;
	private final ModelPart middle_ring3;
	private final ModelPart middle_ring4;
	private final ModelPart arm15;
	private final ModelPart middle_ring5;
	private final ModelPart middle_ring6;
	private final ModelPart crackled_rim;
	private final ModelPart crackled_rim2;
	private final ModelPart crackled_rim3;
	private final ModelPart crackled_rim4;
	private final ModelPart crackled_rim5;
	private final ModelPart crackled_rim6;
	private final ModelPart arm6;
	private final ModelPart arm2;
	private final ModelPart arm3;
	private final ModelPart arm4;
	private final ModelPart arm5;
	private final ModelPart arm7;
	private final ModelPart arm8;
	private final ModelPart arm9;
	private final ModelPart arm10;
	private final ModelPart arm11;
	private final ModelPart arm12;
	private final ModelPart arm17;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart throttle_casing;
	private final ModelPart pipes_one;
	private final ModelPart pipes_one2;
	private final ModelPart pipes_one3;
	private final ModelPart pipes_one4;
	private final ModelPart pipes_one5;
	private final ModelPart pipes_one6;
	private final ModelPart pipes_one7;
	private final ModelPart pipes_one8;
	private final ModelPart pipes_one9;
	private final ModelPart pipes_one10;
	private final ModelPart pipes_one11;
	private final ModelPart pipes_one12;
	private final ModelPart pipes_one13;
	private final ModelPart pipes_one14;
	private final ModelPart pipes_one15;
	private final ModelPart pipes_one16;
	private final ModelPart pipes_one17;
	private final ModelPart pipes_one18;
	private final ModelPart under_console_cover;
	private final ModelPart under_console_cover2;
	private final ModelPart under_console_cover3;
	private final ModelPart under_console_cover4;
	private final ModelPart under_console_cover5;
	private final ModelPart under_console_cover6;
	private final ModelPart rheostat;
	private final ModelPart silver_doohickey;
	private final ModelPart silver_doohickey2;
	private final ModelPart dial;
	private final ModelPart bone97;
	private final ModelPart ppweight;
	private final ModelPart ppweight2;
	private final ModelPart bone98;
	private final ModelPart moving_one;
	private final ModelPart moving_two;
	private final ModelPart clutter;
	private final ModelPart clutter2;
	private final ModelPart blue_glow;
	private final ModelPart orange_glow;
	private final ModelPart nuther_blue_glow;
	private final ModelPart purple_glow;
	private final ModelPart fuckin_lever_thing;
	private final ModelPart fuckin_lever_thing2;
	private final ModelPart bone99;
	private final ModelPart bone100;
	private final ModelPart bone125;
	private final ModelPart bone126;
	private final ModelPart bone127;
	private final ModelPart bone128;
	private final ModelPart bone129;
	private final ModelPart bone130;
	private final ModelPart bone131;
	private final ModelPart bone132;
	private final ModelPart silver_doohickey3;
	private final ModelPart dial2;
	private final ModelPart silver_doohickey4;
	private final ModelPart silver_doohickey5;
	private final ModelPart dial4;
	private final ModelPart bone133;
	private final ModelPart bone134;
	private final ModelPart bone135;
	private final ModelPart bone136;
	private final ModelPart bone137;
	private final ModelPart bone138;
	private final ModelPart bone139;
	private final ModelPart bone31;
	private final ModelPart bone32;
	private final ModelPart bone33;
	private final ModelPart bone34;
	private final ModelPart bone35;
	private final ModelPart bone36;
	private final ModelPart bone37;
	private final ModelPart bone38;
	private final ModelPart bone39;
	private final ModelPart bone40;
	private final ModelPart bone41;
	private final ModelPart bone42;
	private final ModelPart bone55;
	private final ModelPart bone56;
	private final ModelPart bone57;
	private final ModelPart bone58;
	private final ModelPart bone59;
	private final ModelPart bone60;
	private final ModelPart bone49;
	private final ModelPart bone50;
	private final ModelPart bone51;
	private final ModelPart bone52;
	private final ModelPart bone53;
	private final ModelPart bone54;
	private final ModelPart bone7;
	private final ModelPart bone8;
	private final ModelPart bone9;
	private final ModelPart bone10;
	private final ModelPart bone11;
	private final ModelPart bone12;
	private final ModelPart bone13;
	private final ModelPart bone14;
	private final ModelPart bone15;
	private final ModelPart bone16;
	private final ModelPart bone17;
	private final ModelPart bone18;
	private final ModelPart bone19;
	private final ModelPart bone20;
	private final ModelPart bone21;
	private final ModelPart bone22;
	private final ModelPart bone23;
	private final ModelPart bone24;
	private final ModelPart bone25;
	private final ModelPart bone26;
	private final ModelPart bone27;
	private final ModelPart bone28;
	private final ModelPart bone29;
	private final ModelPart bone30;
	private final ModelPart bone43;
	private final ModelPart bone44;
	private final ModelPart bone45;
	private final ModelPart bone46;
	private final ModelPart bone47;
	private final ModelPart bone48;
	private final ModelPart rotor_top;
	private final ModelPart rotor_ring3;
	private final ModelPart bone71;
	private final ModelPart bone72;
	private final ModelPart bone79;
	private final ModelPart bone80;
	private final ModelPart bone81;
	private final ModelPart rotor_ring4;
	private final ModelPart bone82;
	private final ModelPart bone83;
	private final ModelPart bone84;
	private final ModelPart bone85;
	private final ModelPart bone86;
	private final ModelPart rotor_bottom;
	private final ModelPart console_collar_one16;
	private final ModelPart console_collar_one17;
	private final ModelPart bone73;
	private final ModelPart bone74;
	private final ModelPart bone75;
	private final ModelPart bone76;
	private final ModelPart bone77;
	private final ModelPart bone78;
	private final ModelPart rotor_ring;
	private final ModelPart bone62;
	private final ModelPart bone63;
	private final ModelPart bone64;
	private final ModelPart bone65;
	private final ModelPart bone66;
	private final ModelPart rotor_ring2;
	private final ModelPart bone61;
	private final ModelPart bone67;
	private final ModelPart bone68;
	private final ModelPart bone69;
	private final ModelPart bone70;
	private final ModelPart monitor;
	private final ModelPart throttle;
	private final ModelPart handbrake;
	private final ModelPart door_control;
	private final ModelPart communicator;
	private final ModelPart randomiser;
	private final ModelPart x;
	private final ModelPart y;
	private final ModelPart z;
	private final ModelPart increment;
	private final ModelPart refuller;
	private final ModelPart fast_return_switch;
	private final ModelPart exterior_facing;
	private final ModelPart dimension_changer;
	private final ModelPart bone87;
	private final ModelPart ttsControls;

	public CoralConsoleModel(ModelPart root) {
		this.root = root.getChild("root");
		this.bb_main8 = this.root.getChild("bb_main8");
		this.bb_main7 = this.root.getChild("bb_main7");
		this.bb_main = this.root.getChild("bb_main");
		this.bb_main2 = this.bb_main.getChild("bb_main2");
		this.bb_main3 = this.bb_main2.getChild("bb_main3");
		this.bb_main4 = this.bb_main3.getChild("bb_main4");
		this.bb_main5 = this.bb_main4.getChild("bb_main5");
		this.bb_main6 = this.bb_main5.getChild("bb_main6");
		this.arm13 = this.root.getChild("arm13");
		this.middle_ring7 = this.arm13.getChild("middle_ring7");
		this.middle_ring2 = this.middle_ring7.getChild("middle_ring2");
		this.arm14 = this.arm13.getChild("arm14");
		this.middle_ring3 = this.arm14.getChild("middle_ring3");
		this.middle_ring4 = this.middle_ring3.getChild("middle_ring4");
		this.arm15 = this.arm14.getChild("arm15");
		this.middle_ring5 = this.arm15.getChild("middle_ring5");
		this.middle_ring6 = this.middle_ring5.getChild("middle_ring6");
		this.crackled_rim = this.root.getChild("crackled_rim");
		this.crackled_rim2 = this.crackled_rim.getChild("crackled_rim2");
		this.crackled_rim3 = this.crackled_rim2.getChild("crackled_rim3");
		this.crackled_rim4 = this.crackled_rim3.getChild("crackled_rim4");
		this.crackled_rim5 = this.crackled_rim4.getChild("crackled_rim5");
		this.crackled_rim6 = this.crackled_rim5.getChild("crackled_rim6");
		this.arm6 = this.root.getChild("arm6");
		this.arm2 = this.arm6.getChild("arm2");
		this.arm3 = this.arm2.getChild("arm3");
		this.arm4 = this.arm3.getChild("arm4");
		this.arm5 = this.arm4.getChild("arm5");
		this.arm7 = this.arm5.getChild("arm7");
		this.arm8 = this.root.getChild("arm8");
		this.arm9 = this.arm8.getChild("arm9");
		this.arm10 = this.arm9.getChild("arm10");
		this.arm11 = this.arm10.getChild("arm11");
		this.arm12 = this.arm11.getChild("arm12");
		this.arm17 = this.arm12.getChild("arm17");
		this.bone = this.root.getChild("bone");
		this.bone2 = this.bone.getChild("bone2");
		this.bone3 = this.bone2.getChild("bone3");
		this.bone4 = this.bone3.getChild("bone4");
		this.bone5 = this.bone4.getChild("bone5");
		this.bone6 = this.bone5.getChild("bone6");
		this.throttle_casing = this.root.getChild("throttle_casing");
		this.pipes_one = this.root.getChild("pipes_one");
		this.pipes_one2 = this.pipes_one.getChild("pipes_one2");
		this.pipes_one3 = this.pipes_one2.getChild("pipes_one3");
		this.pipes_one4 = this.pipes_one.getChild("pipes_one4");
		this.pipes_one5 = this.pipes_one4.getChild("pipes_one5");
		this.pipes_one6 = this.pipes_one5.getChild("pipes_one6");
		this.pipes_one7 = this.pipes_one4.getChild("pipes_one7");
		this.pipes_one8 = this.pipes_one7.getChild("pipes_one8");
		this.pipes_one9 = this.pipes_one8.getChild("pipes_one9");
		this.pipes_one10 = this.pipes_one7.getChild("pipes_one10");
		this.pipes_one11 = this.pipes_one10.getChild("pipes_one11");
		this.pipes_one12 = this.pipes_one11.getChild("pipes_one12");
		this.pipes_one13 = this.pipes_one10.getChild("pipes_one13");
		this.pipes_one14 = this.pipes_one13.getChild("pipes_one14");
		this.pipes_one15 = this.pipes_one14.getChild("pipes_one15");
		this.pipes_one16 = this.pipes_one13.getChild("pipes_one16");
		this.pipes_one17 = this.pipes_one16.getChild("pipes_one17");
		this.pipes_one18 = this.pipes_one17.getChild("pipes_one18");
		this.under_console_cover = this.root.getChild("under_console_cover");
		this.under_console_cover2 = this.under_console_cover.getChild("under_console_cover2");
		this.under_console_cover3 = this.under_console_cover2.getChild("under_console_cover3");
		this.under_console_cover4 = this.under_console_cover3.getChild("under_console_cover4");
		this.under_console_cover5 = this.under_console_cover4.getChild("under_console_cover5");
		this.under_console_cover6 = this.under_console_cover5.getChild("under_console_cover6");
		this.rheostat = this.root.getChild("rheostat");
		this.silver_doohickey = this.root.getChild("silver_doohickey");
		this.silver_doohickey2 = this.silver_doohickey.getChild("silver_doohickey2");
		this.dial = this.silver_doohickey2.getChild("dial");
		this.bone97 = this.root.getChild("bone97");
		this.ppweight = this.root.getChild("ppweight");
		this.ppweight2 = this.root.getChild("ppweight2");
		this.bone98 = this.root.getChild("bone98");
		this.moving_one = this.root.getChild("moving_one");
		this.moving_two = this.root.getChild("moving_two");
		this.clutter = this.root.getChild("clutter");
		this.clutter2 = this.root.getChild("clutter2");
		this.blue_glow = this.root.getChild("blue_glow");
		this.orange_glow = this.root.getChild("orange_glow");
		this.nuther_blue_glow = this.root.getChild("nuther_blue_glow");
		this.purple_glow = this.root.getChild("purple_glow");
		this.fuckin_lever_thing = this.root.getChild("fuckin_lever_thing");
		this.fuckin_lever_thing2 = this.root.getChild("fuckin_lever_thing2");
		this.bone99 = this.root.getChild("bone99");
		this.bone100 = this.root.getChild("bone100");
		this.bone125 = this.root.getChild("bone125");
		this.bone126 = this.root.getChild("bone126");
		this.bone127 = this.root.getChild("bone127");
		this.bone128 = this.root.getChild("bone128");
		this.bone129 = this.root.getChild("bone129");
		this.bone130 = this.root.getChild("bone130");
		this.bone131 = this.root.getChild("bone131");
		this.bone132 = this.root.getChild("bone132");
		this.silver_doohickey3 = this.root.getChild("silver_doohickey3");
		this.dial2 = this.silver_doohickey3.getChild("dial2");
		this.silver_doohickey4 = this.root.getChild("silver_doohickey4");
		this.silver_doohickey5 = this.root.getChild("silver_doohickey5");
		this.dial4 = this.silver_doohickey5.getChild("dial4");
		this.bone133 = this.root.getChild("bone133");
		this.bone134 = this.root.getChild("bone134");
		this.bone135 = this.bone134.getChild("bone135");
		this.bone136 = this.bone135.getChild("bone136");
		this.bone137 = this.bone136.getChild("bone137");
		this.bone138 = this.bone137.getChild("bone138");
		this.bone139 = this.bone138.getChild("bone139");
		this.bone31 = this.root.getChild("bone31");
		this.bone32 = this.bone31.getChild("bone32");
		this.bone33 = this.bone32.getChild("bone33");
		this.bone34 = this.bone33.getChild("bone34");
		this.bone35 = this.bone34.getChild("bone35");
		this.bone36 = this.bone35.getChild("bone36");
		this.bone37 = this.root.getChild("bone37");
		this.bone38 = this.bone37.getChild("bone38");
		this.bone39 = this.bone38.getChild("bone39");
		this.bone40 = this.bone39.getChild("bone40");
		this.bone41 = this.bone40.getChild("bone41");
		this.bone42 = this.bone41.getChild("bone42");
		this.bone55 = this.root.getChild("bone55");
		this.bone56 = this.bone55.getChild("bone56");
		this.bone57 = this.bone56.getChild("bone57");
		this.bone58 = this.bone57.getChild("bone58");
		this.bone59 = this.bone58.getChild("bone59");
		this.bone60 = this.bone59.getChild("bone60");
		this.bone49 = this.bone55.getChild("bone49");
		this.bone50 = this.bone49.getChild("bone50");
		this.bone51 = this.bone50.getChild("bone51");
		this.bone52 = this.bone51.getChild("bone52");
		this.bone53 = this.bone52.getChild("bone53");
		this.bone54 = this.bone53.getChild("bone54");
		this.bone7 = this.root.getChild("bone7");
		this.bone8 = this.bone7.getChild("bone8");
		this.bone9 = this.bone8.getChild("bone9");
		this.bone10 = this.bone9.getChild("bone10");
		this.bone11 = this.bone10.getChild("bone11");
		this.bone12 = this.bone11.getChild("bone12");
		this.bone13 = this.bone7.getChild("bone13");
		this.bone14 = this.bone13.getChild("bone14");
		this.bone15 = this.bone14.getChild("bone15");
		this.bone16 = this.bone15.getChild("bone16");
		this.bone17 = this.bone16.getChild("bone17");
		this.bone18 = this.bone17.getChild("bone18");
		this.bone19 = this.root.getChild("bone19");
		this.bone20 = this.bone19.getChild("bone20");
		this.bone21 = this.bone20.getChild("bone21");
		this.bone22 = this.bone21.getChild("bone22");
		this.bone23 = this.bone22.getChild("bone23");
		this.bone24 = this.bone23.getChild("bone24");
		this.bone25 = this.bone19.getChild("bone25");
		this.bone26 = this.bone25.getChild("bone26");
		this.bone27 = this.bone26.getChild("bone27");
		this.bone28 = this.bone27.getChild("bone28");
		this.bone29 = this.bone28.getChild("bone29");
		this.bone30 = this.bone29.getChild("bone30");
		this.bone43 = this.bone30.getChild("bone43");
		this.bone44 = this.bone43.getChild("bone44");
		this.bone45 = this.bone44.getChild("bone45");
		this.bone46 = this.bone45.getChild("bone46");
		this.bone47 = this.bone46.getChild("bone47");
		this.bone48 = this.bone47.getChild("bone48");
		this.rotor_top = this.root.getChild("rotor_top");
		this.rotor_ring3 = this.rotor_top.getChild("rotor_ring3");
		this.bone71 = this.rotor_ring3.getChild("bone71");
		this.bone72 = this.bone71.getChild("bone72");
		this.bone79 = this.bone72.getChild("bone79");
		this.bone80 = this.bone79.getChild("bone80");
		this.bone81 = this.bone80.getChild("bone81");
		this.rotor_ring4 = this.rotor_ring3.getChild("rotor_ring4");
		this.bone82 = this.rotor_ring4.getChild("bone82");
		this.bone83 = this.bone82.getChild("bone83");
		this.bone84 = this.bone83.getChild("bone84");
		this.bone85 = this.bone84.getChild("bone85");
		this.bone86 = this.bone85.getChild("bone86");
		this.rotor_bottom = this.root.getChild("rotor_bottom");
		this.console_collar_one16 = this.rotor_bottom.getChild("console_collar_one16");
		this.console_collar_one17 = this.console_collar_one16.getChild("console_collar_one17");
		this.bone73 = this.rotor_bottom.getChild("bone73");
		this.bone74 = this.bone73.getChild("bone74");
		this.bone75 = this.bone74.getChild("bone75");
		this.bone76 = this.bone75.getChild("bone76");
		this.bone77 = this.bone76.getChild("bone77");
		this.bone78 = this.bone77.getChild("bone78");
		this.rotor_ring = this.rotor_bottom.getChild("rotor_ring");
		this.bone62 = this.rotor_ring.getChild("bone62");
		this.bone63 = this.bone62.getChild("bone63");
		this.bone64 = this.bone63.getChild("bone64");
		this.bone65 = this.bone64.getChild("bone65");
		this.bone66 = this.bone65.getChild("bone66");
		this.rotor_ring2 = this.rotor_ring.getChild("rotor_ring2");
		this.bone61 = this.rotor_ring2.getChild("bone61");
		this.bone67 = this.bone61.getChild("bone67");
		this.bone68 = this.bone67.getChild("bone68");
		this.bone69 = this.bone68.getChild("bone69");
		this.bone70 = this.bone69.getChild("bone70");
		this.monitor = this.root.getChild("monitor");
		this.throttle = this.root.getChild("throttle");
		this.handbrake = this.root.getChild("handbrake");
		this.door_control = this.root.getChild("door_control");
		this.communicator = this.root.getChild("communicator");
		this.randomiser = this.root.getChild("randomiser");
		this.x = this.root.getChild("x");
		this.y = this.root.getChild("y");
		this.z = this.root.getChild("z");
		this.increment = this.root.getChild("increment");
		this.refuller = this.root.getChild("refuller");
		this.fast_return_switch = this.root.getChild("fast_return_switch");
		this.exterior_facing = this.root.getChild("exterior_facing");
		this.dimension_changer = this.root.getChild("dimension_changer");
		this.bone87 = this.root.getChild("bone87");
		this.ttsControls = root.getChild("ttsControls");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bb_main3_r1 = root.addOrReplaceChild("bb_main3_r1",
				CubeListBuilder.create().texOffs(29, 203).addBox(-3.0F, -3.0F, 0.1F, 8.0F, 6.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.8304F, -19.4047F, -12.5463F, -3.098F, -0.5236F, -3.1416F));

		PartDefinition bb_main3_r2 = root.addOrReplaceChild("bb_main3_r2",
				CubeListBuilder.create().texOffs(108, 168)
						.addBox(-4.575F, -20.425F, 8.05F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(108, 167)
						.addBox(-2.075F, -20.425F, 8.05F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(105, 152)
						.addBox(-4.775F, -20.425F, 7.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(89, 111)
						.addBox(-1.525F, -20.175F, 0.55F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 34)
						.addBox(-9.0F, -20.175F, -0.975F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.618F, 0.5236F, -3.1416F));

		PartDefinition bb_main8_r1 = root.addOrReplaceChild("bb_main8_r1",
				CubeListBuilder.create().texOffs(83, 144)
						.addBox(12.0F, -1.625F, -4.4F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(68, 143)
						.addBox(11.5F, -2.125F, -6.9F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -11.45F, 18.75F, -0.5236F, 0.5236F, 0.0F));

		PartDefinition bb_main6_r1 = root.addOrReplaceChild("bb_main6_r1",
				CubeListBuilder.create().texOffs(24, 141)
						.addBox(-13.4F, -0.634F, -2.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(6, 141)
						.addBox(-8.5F, -0.634F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, -0.5236F, 0.0F));

		PartDefinition cube_r1 = root.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(0, 34).addBox(-0.25F, -4.5F, -0.5F, 1.0F, 8.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(18.0284F, -7.3756F, -7.5399F, 0.4821F, 0.2129F, 1.187F));

		PartDefinition cube_r2 = root
				.addOrReplaceChild("cube_r2",
						CubeListBuilder.create().texOffs(5, 88).addBox(-18.5F, -6.5F, -3.0F, 1.0F, 9.0F, 6.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition middle_ring7_r1 = root.addOrReplaceChild("middle_ring7_r1",
				CubeListBuilder.create().texOffs(0, 47).addBox(-1.0F, -0.5F, -1.0F, 1.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-17.3032F, -10.5F, -13.97F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bb_main2_r1 = root.addOrReplaceChild("bb_main2_r1",
				CubeListBuilder.create().texOffs(0, 21).addBox(-9.0F, -20.2F, -0.75F, 18.0F, 0.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.618F, -0.5236F, 3.1416F));

		PartDefinition cube_r3 = root.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(49, 8).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.5359F, -13.5F, -15.8564F, -0.6319F, -0.9025F, 0.7505F));

		PartDefinition bb_main8_r2 = root.addOrReplaceChild("bb_main8_r2",
				CubeListBuilder.create().texOffs(92, 95).addBox(0.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(16.85F, -13.4282F, 5.1303F, 3.1416F, 0.0F, 2.7576F));

		PartDefinition bb_main8_r3 = root.addOrReplaceChild("bb_main8_r3",
				CubeListBuilder.create().texOffs(54, 167)
						.addBox(-5.45F, 1.25F, -10.45F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 151)
						.addBox(-0.95F, 1.1F, -11.2F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(14.4968F, -15.9252F, -8.3697F, 3.1416F, 0.0F, -2.6093F));

		PartDefinition bb_main8_r4 = root.addOrReplaceChild("bb_main8_r4",
				CubeListBuilder.create().texOffs(49, 14).addBox(-5.25F, 1.6F, -14.5F, 3.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(14.4968F, -15.9252F, -8.3697F, 3.1416F, 0.0F, -2.6529F));

		PartDefinition bb_main4_r1 = root.addOrReplaceChild("bb_main4_r1",
				CubeListBuilder.create().texOffs(0, 47).addBox(-9.5F, -20.2F, 0.05F, 18.0F, 0.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 1.5708F, 0.5236F));

		PartDefinition bb_main8_r5 = root.addOrReplaceChild("bb_main8_r5",
				CubeListBuilder.create().texOffs(49, 43).addBox(6.75F, -1.275F, 1.0F, 3.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -11.45F, 18.75F, 0.3054F, 0.5236F, 0.0F));

		PartDefinition bb_main8 = root.addOrReplaceChild("bb_main8", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bb_main8_r6 = bb_main8.addOrReplaceChild("bb_main8_r6",
				CubeListBuilder.create().texOffs(90, 73)
						.addBox(5.75F, -1.625F, -1.95F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 60)
						.addBox(1.5F, -1.425F, -9.4F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -11.45F, 18.75F, -0.5236F, 0.5236F, 0.0F));

		PartDefinition bb_main8_r7 = bb_main8.addOrReplaceChild("bb_main8_r7",
				CubeListBuilder.create().texOffs(0, 60).addBox(-0.25F, -0.9F, -1.5F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(14.4968F, -15.9252F, -8.3697F, 2.9286F, -0.482F, -2.7051F));

		PartDefinition bb_main8_r8 = bb_main8.addOrReplaceChild("bb_main8_r8",
				CubeListBuilder.create().texOffs(82, 79).addBox(0.25F, -1.9F, -1.0F, 1.0F, 2.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(14.4968F, -15.9252F, -8.3697F, 2.9286F, -0.482F, -2.7051F));

		PartDefinition bb_main7 = root.addOrReplaceChild("bb_main7", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bb_main7_r1 = bb_main7.addOrReplaceChild("bb_main7_r1",
				CubeListBuilder.create().texOffs(111, 46).addBox(-2.75F, -0.1F, -2.2F, 3.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-16.3386F, -14.4991F, -5.0F, 0.0219F, 0.3152F, -0.4606F));

		PartDefinition bb_main = root.addOrReplaceChild("bb_main", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bb_main_r1 = bb_main.addOrReplaceChild("bb_main_r1",
				CubeListBuilder.create().texOffs(49, 22)
						.addBox(-8.5F, 0.916F, -10.5F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(49, 35)
						.addBox(-8.5F, 0.866F, -10.5F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bb_main2 = bb_main.addOrReplaceChild("bb_main2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bb_main2_r2 = bb_main2.addOrReplaceChild("bb_main2_r2",
				CubeListBuilder.create().texOffs(49, 22).addBox(-8.5F, 0.866F, -11.5F, 18.0F, 0.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bb_main3 = bb_main2.addOrReplaceChild("bb_main3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bb_main3_r3 = bb_main3.addOrReplaceChild("bb_main3_r3",
				CubeListBuilder.create().texOffs(49, 48).addBox(-8.5F, 0.866F, -10.5F, 18.0F, 0.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bb_main4 = bb_main3.addOrReplaceChild("bb_main4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bb_main4_r2 = bb_main4.addOrReplaceChild("bb_main4_r2",
				CubeListBuilder.create().texOffs(49, 22)
						.addBox(-8.5F, 0.991F, -10.5F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(49, 35)
						.addBox(-8.5F, 0.866F, -10.5F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bb_main5 = bb_main4.addOrReplaceChild("bb_main5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bb_main5_r1 = bb_main5.addOrReplaceChild("bb_main5_r1",
				CubeListBuilder.create().texOffs(49, 22).addBox(-8.5F, 0.866F, -10.5F, 18.0F, 0.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bb_main6 = bb_main5.addOrReplaceChild("bb_main6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bb_main6_r2 = bb_main6.addOrReplaceChild("bb_main6_r2",
				CubeListBuilder.create().texOffs(0, 8)
						.addBox(-8.5F, 0.766F, -10.5F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(49, 22)
						.addBox(-8.5F, 0.866F, -10.5F, 18.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, 0.0F, 0.0F));

		PartDefinition arm13 = root.addOrReplaceChild("arm13", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition middle_ring7 = arm13.addOrReplaceChild("middle_ring7", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 3.1416F));

		PartDefinition middle_ring7_r2 = middle_ring7.addOrReplaceChild("middle_ring7_r2",
				CubeListBuilder.create().texOffs(0, 0).addBox(20.5F, 9.0F, -2.0F, 3.0F, 3.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0194F, -0.0122F, -0.0336F, 0.0F, 1.0472F, 0.0F));

		PartDefinition middle_ring2 = middle_ring7.addOrReplaceChild("middle_ring2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 3.1416F));

		PartDefinition middle_ring7_r3 = middle_ring2.addOrReplaceChild("middle_ring7_r3",
				CubeListBuilder.create().texOffs(0, 0).addBox(26.5F, 9.0F, -2.0F, 3.0F, 3.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.9806F, -20.9878F, -5.1626F, 0.0F, -2.0944F, 0.0F));

		PartDefinition arm14 = arm13.addOrReplaceChild("arm14", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition middle_ring3 = arm14.addOrReplaceChild("middle_ring3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 3.1416F));

		PartDefinition middle_ring7_r4 = middle_ring3.addOrReplaceChild("middle_ring7_r4",
				CubeListBuilder.create().texOffs(0, 0).addBox(20.5F, 9.0F, -2.0F, 3.0F, 3.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0194F, -0.0122F, -0.0336F, 0.0F, 1.0472F, 0.0F));

		PartDefinition middle_ring4 = middle_ring3.addOrReplaceChild("middle_ring4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 3.1416F));

		PartDefinition middle_ring7_r5 = middle_ring4.addOrReplaceChild("middle_ring7_r5",
				CubeListBuilder.create().texOffs(0, 0).addBox(20.5F, 9.0F, -2.0F, 3.0F, 3.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0194F, -20.9878F, -0.0336F, 0.0F, 1.0472F, 0.0F));

		PartDefinition arm15 = arm14.addOrReplaceChild("arm15", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition middle_ring5 = arm15.addOrReplaceChild("middle_ring5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 3.1416F));

		PartDefinition middle_ring7_r6 = middle_ring5.addOrReplaceChild("middle_ring7_r6",
				CubeListBuilder.create().texOffs(0, 0).addBox(20.5F, 9.0F, -2.0F, 3.0F, 3.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0194F, -0.0122F, -0.0336F, 0.0F, 1.0472F, 0.0F));

		PartDefinition middle_ring6 = middle_ring5.addOrReplaceChild("middle_ring6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 3.1416F));

		PartDefinition middle_ring7_r7 = middle_ring6.addOrReplaceChild("middle_ring7_r7",
				CubeListBuilder.create().texOffs(0, 0).addBox(20.5F, 9.0F, -2.0F, 3.0F, 3.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0194F, -20.9878F, -0.0336F, 0.0F, 1.0472F, 0.0F));

		PartDefinition crackled_rim = root.addOrReplaceChild("crackled_rim", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition crackled_rim_r1 = crackled_rim.addOrReplaceChild("crackled_rim_r1",
				CubeListBuilder.create().texOffs(49, 61).addBox(-9.6F, -1.25F, -0.4165F, 20.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4F, -11.2832F, -19.1674F, -2.0944F, 0.0F, 0.0F));

		PartDefinition crackled_rim2 = crackled_rim.addOrReplaceChild("crackled_rim2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition crackled_rim2_r1 = crackled_rim2.addOrReplaceChild("crackled_rim2_r1",
				CubeListBuilder.create().texOffs(49, 61).addBox(-9.6F, -1.25F, -0.4165F, 20.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4F, -11.2832F, -19.1674F, -2.0944F, 0.0F, 0.0F));

		PartDefinition crackled_rim3 = crackled_rim2.addOrReplaceChild("crackled_rim3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition crackled_rim3_r1 = crackled_rim3.addOrReplaceChild("crackled_rim3_r1",
				CubeListBuilder.create().texOffs(49, 61).addBox(-9.6F, -1.25F, -0.4165F, 20.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4F, -11.2832F, -19.1674F, -2.0944F, 0.0F, 0.0F));

		PartDefinition crackled_rim4 = crackled_rim3.addOrReplaceChild("crackled_rim4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition crackled_rim4_r1 = crackled_rim4.addOrReplaceChild("crackled_rim4_r1",
				CubeListBuilder.create().texOffs(49, 61).addBox(-9.6F, -1.25F, -0.4165F, 20.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4F, -11.2832F, -19.1674F, -2.0944F, 0.0F, 0.0F));

		PartDefinition crackled_rim5 = crackled_rim4.addOrReplaceChild("crackled_rim5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition crackled_rim5_r1 = crackled_rim5.addOrReplaceChild("crackled_rim5_r1",
				CubeListBuilder.create().texOffs(49, 61).addBox(-9.6F, -1.25F, -0.4165F, 20.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4F, -11.2832F, -19.1674F, -2.0944F, 0.0F, 0.0F));

		PartDefinition crackled_rim6 = crackled_rim5.addOrReplaceChild("crackled_rim6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition crackled_rim6_r1 = crackled_rim6.addOrReplaceChild("crackled_rim6_r1",
				CubeListBuilder.create().texOffs(49, 61).addBox(-9.6F, -1.25F, -0.4165F, 20.0F, 2.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4F, -11.2832F, -19.1674F, -2.0944F, 0.0F, 0.0F));

		PartDefinition arm6 = root.addOrReplaceChild("arm6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition arm6_r1 = arm6.addOrReplaceChild("arm6_r1",
				CubeListBuilder.create().texOffs(98, 8).addBox(-0.6862F, 1.1086F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm6_r2 = arm6.addOrReplaceChild("arm6_r2",
				CubeListBuilder.create().texOffs(98, 35).addBox(-5.7062F, -0.0698F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm6_r3 = arm6.addOrReplaceChild("arm6_r3",
				CubeListBuilder.create().texOffs(92, 85).addBox(-11.8219F, -19.5164F, -2.0F, 7.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.6713F, 1.2264F, 0.0F, 0.0F, 0.0F, -0.0785F));

		PartDefinition arm2 = arm6.addOrReplaceChild("arm2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm6_r4 = arm2.addOrReplaceChild("arm6_r4",
				CubeListBuilder.create().texOffs(98, 8).addBox(-0.6862F, 1.1086F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm6_r5 = arm2.addOrReplaceChild("arm6_r5",
				CubeListBuilder.create().texOffs(98, 35).addBox(-5.7062F, -0.0698F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm6_r6 = arm2.addOrReplaceChild("arm6_r6",
				CubeListBuilder.create().texOffs(92, 85).addBox(-11.8219F, -19.5164F, -2.0F, 7.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.6713F, 1.2264F, 0.0F, 0.0F, 0.0F, -0.0785F));

		PartDefinition arm3 = arm2.addOrReplaceChild("arm3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm6_r7 = arm3.addOrReplaceChild("arm6_r7",
				CubeListBuilder.create().texOffs(98, 8).addBox(-0.6862F, 1.1086F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm6_r8 = arm3.addOrReplaceChild("arm6_r8",
				CubeListBuilder.create().texOffs(98, 35).addBox(-5.7062F, -0.0698F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm6_r9 = arm3.addOrReplaceChild("arm6_r9",
				CubeListBuilder.create().texOffs(92, 85).addBox(-11.8219F, -19.5164F, -2.0F, 7.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.6713F, 1.2264F, 0.0F, 0.0F, 0.0F, -0.0785F));

		PartDefinition arm4 = arm3.addOrReplaceChild("arm4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm6_r10 = arm4.addOrReplaceChild("arm6_r10",
				CubeListBuilder.create().texOffs(98, 8).addBox(-0.6862F, 1.1086F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm6_r11 = arm4.addOrReplaceChild("arm6_r11",
				CubeListBuilder.create().texOffs(98, 35).addBox(-5.7062F, -0.0698F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm6_r12 = arm4.addOrReplaceChild("arm6_r12",
				CubeListBuilder.create().texOffs(92, 85).addBox(-11.8219F, -19.5164F, -2.0F, 7.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.6713F, 1.2264F, 0.0F, 0.0F, 0.0F, -0.0785F));

		PartDefinition arm5 = arm4.addOrReplaceChild("arm5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm6_r13 = arm5.addOrReplaceChild("arm6_r13",
				CubeListBuilder.create().texOffs(98, 8).addBox(-0.6862F, 1.1086F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm6_r14 = arm5.addOrReplaceChild("arm6_r14",
				CubeListBuilder.create().texOffs(98, 35).addBox(-5.7062F, -0.0698F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm6_r15 = arm5.addOrReplaceChild("arm6_r15",
				CubeListBuilder.create().texOffs(92, 85).addBox(-11.8219F, -19.5164F, -2.0F, 7.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.6713F, 1.2264F, 0.0F, 0.0F, 0.0F, -0.0785F));

		PartDefinition arm7 = arm5.addOrReplaceChild("arm7", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm6_r16 = arm7.addOrReplaceChild("arm6_r16",
				CubeListBuilder.create().texOffs(98, 8).addBox(-0.6862F, 1.1086F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm6_r17 = arm7.addOrReplaceChild("arm6_r17",
				CubeListBuilder.create().texOffs(98, 35).addBox(-5.7062F, -0.0698F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm6_r18 = arm7.addOrReplaceChild("arm6_r18",
				CubeListBuilder.create().texOffs(92, 85).addBox(-11.8219F, -19.5164F, -2.0F, 7.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.6713F, 1.2264F, 0.0F, 0.0F, 0.0F, -0.0785F));

		PartDefinition arm8 = root.addOrReplaceChild("arm8", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -23.0F, 0.0F, 0.0F, -1.5708F, 3.1416F));

		PartDefinition arm8_r1 = arm8.addOrReplaceChild("arm8_r1",
				CubeListBuilder.create().texOffs(98, 8).addBox(0.461F, -0.5297F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm8_r2 = arm8.addOrReplaceChild("arm8_r2",
				CubeListBuilder.create().texOffs(98, 35).addBox(-4.957F, -1.9242F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm8_r3 = arm8.addOrReplaceChild("arm8_r3",
				CubeListBuilder.create().texOffs(21, 84).addBox(-1.1997F, 0.4725F, -2.0F, 9.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-14.0055F, -18.3713F, 0.0F, 0.0F, 0.0F, -2.0944F));

		PartDefinition arm9 = arm8.addOrReplaceChild("arm9", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm9_r1 = arm9.addOrReplaceChild("arm9_r1",
				CubeListBuilder.create().texOffs(98, 8).addBox(0.461F, -0.5297F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm9_r2 = arm9.addOrReplaceChild("arm9_r2",
				CubeListBuilder.create().texOffs(98, 35).addBox(-4.957F, -1.9242F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm9_r3 = arm9.addOrReplaceChild("arm9_r3",
				CubeListBuilder.create().texOffs(21, 84).addBox(-0.1432F, -0.2646F, -2.0F, 9.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-12.8304F, -17.8266F, 0.0F, 0.0F, 0.0F, -2.0944F));

		PartDefinition arm10 = arm9.addOrReplaceChild("arm10", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm10_r1 = arm10.addOrReplaceChild("arm10_r1",
				CubeListBuilder.create().texOffs(98, 8).addBox(0.461F, -0.5297F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm10_r2 = arm10.addOrReplaceChild("arm10_r2",
				CubeListBuilder.create().texOffs(98, 35).addBox(0.0535F, -0.9783F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-18.307F, -16.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm10_r3 = arm10.addOrReplaceChild("arm10_r3",
				CubeListBuilder.create().texOffs(21, 84).addBox(-12.8774F, -20.0133F, -2.0F, 9.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0946F, -38.729F, 0.0F, 0.0F, 0.0F, -2.0944F));

		PartDefinition arm11 = arm10.addOrReplaceChild("arm11", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm11_r1 = arm11.addOrReplaceChild("arm11_r1",
				CubeListBuilder.create().texOffs(98, 8).addBox(0.461F, -0.5297F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm11_r2 = arm11.addOrReplaceChild("arm11_r2",
				CubeListBuilder.create().texOffs(98, 35).addBox(-4.957F, -1.9242F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm11_r3 = arm11.addOrReplaceChild("arm11_r3",
				CubeListBuilder.create().texOffs(21, 84).addBox(-12.8774F, -20.0133F, -2.0F, 9.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0946F, -38.729F, 0.0F, 0.0F, 0.0F, -2.0944F));

		PartDefinition arm12 = arm11.addOrReplaceChild("arm12", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm12_r1 = arm12.addOrReplaceChild("arm12_r1",
				CubeListBuilder.create().texOffs(98, 8).addBox(0.461F, -0.5297F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm12_r2 = arm12.addOrReplaceChild("arm12_r2",
				CubeListBuilder.create().texOffs(98, 35).addBox(-4.957F, -1.9242F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm12_r3 = arm12.addOrReplaceChild("arm12_r3",
				CubeListBuilder.create().texOffs(21, 84).addBox(-12.8774F, -20.0133F, -2.0F, 9.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0946F, -38.729F, 0.0F, 0.0F, 0.0F, -2.0944F));

		PartDefinition arm17 = arm12.addOrReplaceChild("arm17", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition arm17_r1 = arm17.addOrReplaceChild("arm17_r1",
				CubeListBuilder.create().texOffs(98, 8).addBox(0.461F, -0.5297F, -2.0F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-23.6126F, -13.2895F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition arm17_r2 = arm17.addOrReplaceChild("arm17_r2",
				CubeListBuilder.create().texOffs(98, 35).addBox(-4.957F, -1.9242F, -2.0F, 5.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.384F));

		PartDefinition arm17_r3 = arm17.addOrReplaceChild("arm17_r3",
				CubeListBuilder.create().texOffs(21, 84).addBox(-12.8774F, -20.0133F, -2.0F, 9.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0946F, -38.729F, 0.0F, 0.0F, 0.0F, -2.0944F));

		PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone_r1 = bone
				.addOrReplaceChild("bone_r1",
						CubeListBuilder.create().texOffs(98, 15).addBox(-3.6F, -18.3288F, -8.5122F, 7.0F, 2.0F, 3.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone2_r1 = bone2.addOrReplaceChild(
				"bone2_r1", CubeListBuilder.create().texOffs(98, 15).addBox(-3.6F, -18.3288F, -8.5122F, 7.0F, 2.0F,
						3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone3_r1 = bone3.addOrReplaceChild(
				"bone3_r1", CubeListBuilder.create().texOffs(98, 15).addBox(-3.6F, -18.3288F, -8.5122F, 7.0F, 2.0F,
						3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone4_r1 = bone4.addOrReplaceChild(
				"bone4_r1", CubeListBuilder.create().texOffs(98, 15).addBox(-3.6F, -18.3288F, -8.5122F, 7.0F, 2.0F,
						3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone5_r1 = bone5.addOrReplaceChild(
				"bone5_r1", CubeListBuilder.create().texOffs(98, 15).addBox(-3.6F, -18.3288F, -8.5122F, 7.0F, 2.0F,
						3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone6_r1 = bone6.addOrReplaceChild(
				"bone6_r1", CubeListBuilder.create().texOffs(98, 15).addBox(-3.6F, -18.3288F, -8.5122F, 7.0F, 2.0F,
						3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition throttle_casing = root.addOrReplaceChild("throttle_casing", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.4F, 0.25F, 0.0F, -0.0436F, -0.5236F, 0.0F));

		PartDefinition throttle_casing_r1 = throttle_casing.addOrReplaceChild("throttle_casing_r1",
				CubeListBuilder.create().texOffs(107, 92).addBox(-0.5131F, 0.3242F, -0.543F, 5.0F, 2.0F, 3.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(-8.1386F, -17.4991F, -16.0F, 0.5498F, -0.9599F, -0.6545F));

		PartDefinition throttle_casing_r2 = throttle_casing.addOrReplaceChild("throttle_casing_r2",
				CubeListBuilder.create().texOffs(5, 108).addBox(-0.4957F, 0.205F, -0.5509F, 5.0F, 2.0F, 3.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(-8.1386F, -17.4991F, -16.0F, 0.5236F, -0.9599F, -0.6168F));

		PartDefinition pipes_one = root.addOrReplaceChild("pipes_one", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition pipes_one_r1 = pipes_one.addOrReplaceChild("pipes_one_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-7.25F, -10.0627F, 0.4244F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.0F, -1.0F, -10.0F, 0.7418F, 0.5236F, 0.0F));

		PartDefinition pipes_one2 = pipes_one.addOrReplaceChild("pipes_one2", CubeListBuilder.create(),
				PartPose.offset(-8.0F, -1.0F, -10.0F));

		PartDefinition pipes_one2_r1 = pipes_one2.addOrReplaceChild("pipes_one2_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 0.8827F, -2.0239F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 1.1781F, 0.5236F, 0.0F));

		PartDefinition pipes_one3 = pipes_one2.addOrReplaceChild("pipes_one3", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pipes_one3_r1 = pipes_one3.addOrReplaceChild("pipes_one3_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 2.8928F, -0.866F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 0.8727F, 0.5236F, 0.0F));

		PartDefinition pipes_one4 = pipes_one.addOrReplaceChild("pipes_one4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition pipes_one4_r1 = pipes_one4.addOrReplaceChild("pipes_one4_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-7.25F, -10.0627F, 0.4244F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.0F, -1.0F, -10.0F, 0.7418F, 0.5236F, 0.0F));

		PartDefinition pipes_one5 = pipes_one4.addOrReplaceChild("pipes_one5", CubeListBuilder.create(),
				PartPose.offset(-8.0F, -1.0F, -10.0F));

		PartDefinition pipes_one5_r1 = pipes_one5.addOrReplaceChild("pipes_one5_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 0.8827F, -2.0239F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 1.1781F, 0.5236F, 0.0F));

		PartDefinition pipes_one6 = pipes_one5.addOrReplaceChild("pipes_one6", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pipes_one6_r1 = pipes_one6.addOrReplaceChild("pipes_one6_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 2.8928F, -0.866F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 0.8727F, 0.5236F, 0.0F));

		PartDefinition pipes_one7 = pipes_one4.addOrReplaceChild("pipes_one7", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition pipes_one7_r1 = pipes_one7.addOrReplaceChild("pipes_one7_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-7.25F, -10.0627F, 0.4244F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.0F, -1.0F, -10.0F, 0.7418F, 0.5236F, 0.0F));

		PartDefinition pipes_one8 = pipes_one7.addOrReplaceChild("pipes_one8", CubeListBuilder.create(),
				PartPose.offset(-8.0F, -1.0F, -10.0F));

		PartDefinition pipes_one8_r1 = pipes_one8.addOrReplaceChild("pipes_one8_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 0.8827F, -2.0239F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 1.1781F, 0.5236F, 0.0F));

		PartDefinition pipes_one9 = pipes_one8.addOrReplaceChild("pipes_one9", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pipes_one9_r1 = pipes_one9.addOrReplaceChild("pipes_one9_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 2.8928F, -0.866F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 0.8727F, 0.5236F, 0.0F));

		PartDefinition pipes_one10 = pipes_one7.addOrReplaceChild("pipes_one10", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition pipes_one10_r1 = pipes_one10.addOrReplaceChild("pipes_one10_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-7.25F, -10.0627F, 0.4244F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.0F, -1.0F, -10.0F, 0.7418F, 0.5236F, 0.0F));

		PartDefinition pipes_one11 = pipes_one10.addOrReplaceChild("pipes_one11", CubeListBuilder.create(),
				PartPose.offset(-8.0F, -1.0F, -10.0F));

		PartDefinition pipes_one11_r1 = pipes_one11.addOrReplaceChild("pipes_one11_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 0.8827F, -2.0239F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 1.1781F, 0.5236F, 0.0F));

		PartDefinition pipes_one12 = pipes_one11.addOrReplaceChild("pipes_one12", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pipes_one12_r1 = pipes_one12.addOrReplaceChild("pipes_one12_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 2.8928F, -0.866F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 0.8727F, 0.5236F, 0.0F));

		PartDefinition pipes_one13 = pipes_one10.addOrReplaceChild("pipes_one13", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition pipes_one13_r1 = pipes_one13.addOrReplaceChild("pipes_one13_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-7.25F, -10.0627F, 0.4244F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.0F, -1.0F, -10.0F, 0.7418F, 0.5236F, 0.0F));

		PartDefinition pipes_one14 = pipes_one13.addOrReplaceChild("pipes_one14", CubeListBuilder.create(),
				PartPose.offset(-8.0F, -1.0F, -10.0F));

		PartDefinition pipes_one14_r1 = pipes_one14.addOrReplaceChild("pipes_one14_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 0.8827F, -2.0239F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 1.1781F, 0.5236F, 0.0F));

		PartDefinition pipes_one15 = pipes_one14.addOrReplaceChild("pipes_one15", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pipes_one15_r1 = pipes_one15.addOrReplaceChild("pipes_one15_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 2.8928F, -0.866F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 0.8727F, 0.5236F, 0.0F));

		PartDefinition pipes_one16 = pipes_one13.addOrReplaceChild("pipes_one16", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition pipes_one16_r1 = pipes_one16.addOrReplaceChild("pipes_one16_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-7.25F, -10.0627F, 0.4244F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.0F, -1.0F, -10.0F, 0.7418F, 0.5236F, 0.0F));

		PartDefinition pipes_one17 = pipes_one16.addOrReplaceChild("pipes_one17", CubeListBuilder.create(),
				PartPose.offset(-8.0F, -1.0F, -10.0F));

		PartDefinition pipes_one17_r1 = pipes_one17.addOrReplaceChild("pipes_one17_r1",
				CubeListBuilder.create().texOffs(0, 73).addBox(-9.0F, 0.8827F, -2.0239F, 18.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 1.1781F, 0.5236F, 0.0F));

		PartDefinition pipes_one18 = pipes_one17.addOrReplaceChild("pipes_one18", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pipes_one18_r1 = pipes_one18.addOrReplaceChild("pipes_one18_r1",
				CubeListBuilder.create().texOffs(61, 67).addBox(-9.0F, 2.8928F, -0.866F, 18.0F, 3.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0207F, -8.6441F, -5.2679F, 0.8727F, 0.5236F, 0.0F));

		PartDefinition under_console_cover = root.addOrReplaceChild("under_console_cover", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition under_console_cover_r1 = under_console_cover.addOrReplaceChild(
				"under_console_cover_r1", CubeListBuilder.create().texOffs(0, 78).addBox(-5.0F, -6.0F, -15.0F, 10.0F,
						7.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition under_console_cover2 = under_console_cover.addOrReplaceChild("under_console_cover2",
				CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition under_console_cover2_r1 = under_console_cover2.addOrReplaceChild(
				"under_console_cover2_r1", CubeListBuilder.create().texOffs(0, 78).addBox(-4.625F, -6.0F, -14.625F,
						10.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition under_console_cover3 = under_console_cover2.addOrReplaceChild("under_console_cover3",
				CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition under_console_cover3_r1 = under_console_cover3.addOrReplaceChild(
				"under_console_cover3_r1", CubeListBuilder.create().texOffs(0, 78).addBox(-4.675F, -6.0F, -14.275F,
						10.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition under_console_cover4 = under_console_cover3.addOrReplaceChild("under_console_cover4",
				CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition under_console_cover4_r1 = under_console_cover4.addOrReplaceChild(
				"under_console_cover4_r1", CubeListBuilder.create().texOffs(0, 78).addBox(-5.0F, -6.0F, -13.95F, 10.0F,
						7.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition under_console_cover5 = under_console_cover4.addOrReplaceChild("under_console_cover5",
				CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition under_console_cover5_r1 = under_console_cover5.addOrReplaceChild(
				"under_console_cover5_r1", CubeListBuilder.create().texOffs(0, 78).addBox(-5.375F, -6.0F, -14.25F,
						10.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition under_console_cover6 = under_console_cover5.addOrReplaceChild("under_console_cover6",
				CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition under_console_cover6_r1 = under_console_cover6.addOrReplaceChild(
				"under_console_cover6_r1", CubeListBuilder.create().texOffs(0, 78).addBox(-5.25F, -6.0F, -13.9F, 10.0F,
						7.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition rheostat = root.addOrReplaceChild("rheostat", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rheostat_r1 = rheostat.addOrReplaceChild("rheostat_r1", CubeListBuilder.create().texOffs(29, 100)
				.addBox(-7.7463F, -1.337F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)).texOffs(107, 109)
				.addBox(-7.7463F, -0.337F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-13.307F, -17.5021F, 0.0F, 0.0F, 0.0F, -0.4276F));

		PartDefinition silver_doohickey = root.addOrReplaceChild("silver_doohickey", CubeListBuilder.create(),
				PartPose.offset(-13.307F, -17.5021F, 0.0F));

		PartDefinition silver_doohickey_r1 = silver_doohickey.addOrReplaceChild(
				"silver_doohickey_r1", CubeListBuilder.create().texOffs(111, 83).addBox(-1.1316F, 1.303F, -1.5F, 3.0F,
						1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition silver_doohickey2 = silver_doohickey.addOrReplaceChild("silver_doohickey2",
				CubeListBuilder.create().texOffs(111, 83).addBox(11.7184F, -0.247F, 9.75F, 3.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition dial = silver_doohickey2.addOrReplaceChild("dial",
				CubeListBuilder.create().texOffs(0, 53).addBox(-0.3386F, -23.9299F, 9.5736F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.307F, 17.5021F, 0.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition bone97 = root.addOrReplaceChild("bone97", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone97_r1 = bone97.addOrReplaceChild("bone97_r1",
				CubeListBuilder.create().texOffs(49, 9).addBox(-8.5F, 0.766F, -10.5F, 18.0F, 0.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -12.45F, 18.75F, -0.5236F, 0.0F, 0.0F));

		PartDefinition ppweight = root.addOrReplaceChild("ppweight", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ppweight_r1 = ppweight.addOrReplaceChild("ppweight_r1",
				CubeListBuilder.create().texOffs(71, 101).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-12.25F, -12.5F, 13.75F, -0.5236F, -0.1745F, -0.2967F));

		PartDefinition ppweight2 = root.addOrReplaceChild("ppweight2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition ppweight_r2 = ppweight2.addOrReplaceChild("ppweight_r2",
				CubeListBuilder.create().texOffs(100, 66).addBox(-0.3882F, 1.2213F, 0.6121F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.6118F, -16.9022F, 11.9615F, -0.6109F, -0.6109F, 0.0F));

		PartDefinition bone98 = root.addOrReplaceChild("bone98", CubeListBuilder.create(),
				PartPose.offset(-7.5697F, -16.0238F, 12.8985F));

		PartDefinition bone98_r1 = bone98.addOrReplaceChild("bone98_r1",
				CubeListBuilder.create().texOffs(49, 52).addBox(-0.5F, 0.5F, -1.75F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.6125F, -0.5417F, -0.0562F));

		PartDefinition moving_one = root.addOrReplaceChild("moving_one", CubeListBuilder.create(),
				PartPose.offset(-9.5986F, -14.6924F, 12.4127F));

		PartDefinition moving_one_r1 = moving_one.addOrReplaceChild("moving_one_r1",
				CubeListBuilder.create().texOffs(98, 22).addBox(-0.5F, -0.375F, -0.325F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.9F, 0.0F, -1.2654F, -0.5236F, 0.0F));

		PartDefinition moving_two = root.addOrReplaceChild("moving_two", CubeListBuilder.create(),
				PartPose.offset(-9.5986F, -14.6924F, 12.4127F));

		PartDefinition moving_two_r1 = moving_two.addOrReplaceChild("moving_two_r1",
				CubeListBuilder.create().texOffs(98, 22).addBox(-2.2F, -0.375F, -0.325F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.9F, 0.0F, -1.2654F, -0.5236F, 0.0F));

		PartDefinition clutter = root.addOrReplaceChild("clutter", CubeListBuilder.create(),
				PartPose.offset(-9.5986F, -14.6924F, 12.4127F));

		PartDefinition moving_two2_r1 = clutter.addOrReplaceChild("moving_two2_r1",
				CubeListBuilder.create().texOffs(98, 22).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.738F, 1.6045F, -1.6577F, -1.627F, 0.5529F, -0.9392F));

		PartDefinition clutter2 = root.addOrReplaceChild("clutter2", CubeListBuilder.create(),
				PartPose.offset(-9.5986F, -14.6924F, 12.4127F));

		PartDefinition moving_two3_r1 = clutter2.addOrReplaceChild("moving_two3_r1",
				CubeListBuilder.create().texOffs(98, 22).addBox(-2.75F, -0.75F, -0.75F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.738F, 1.6045F, -1.6577F, -1.4524F, 0.5529F, -0.9392F));

		PartDefinition blue_glow = root.addOrReplaceChild("blue_glow", CubeListBuilder.create(),
				PartPose.offset(-0.5F, -12.45F, 18.75F));

		PartDefinition bb_main8_r9 = blue_glow.addOrReplaceChild("bb_main8_r9",
				CubeListBuilder.create().texOffs(0, 69).addBox(9.75F, -1.675F, -1.2F, 3.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.5236F, 0.5236F, 0.0F));

		PartDefinition orange_glow = root.addOrReplaceChild("orange_glow", CubeListBuilder.create(),
				PartPose.offset(-0.5F, -12.45F, 18.75F));

		PartDefinition bb_main8_r10 = orange_glow.addOrReplaceChild("bb_main8_r10",
				CubeListBuilder.create().texOffs(49, 67).addBox(6.25F, -1.675F, -1.2F, 3.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.5236F, 0.5236F, 0.0F));

		PartDefinition nuther_blue_glow = root.addOrReplaceChild("nuther_blue_glow", CubeListBuilder.create(),
				PartPose.offset(14.4968F, -16.9252F, -8.3697F));

		PartDefinition bb_main8_r11 = nuther_blue_glow.addOrReplaceChild("bb_main8_r11",
				CubeListBuilder.create().texOffs(39, 166).addBox(-0.7718F, 1.6252F, -10.8803F, 2.0F, 0.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.2F, 0.5F, 0.0F, 3.1416F, 0.0F, -2.6093F));

		PartDefinition bb_main8_r12 = nuther_blue_glow.addOrReplaceChild("bb_main8_r12",
				CubeListBuilder.create().texOffs(0, 21).addBox(0.5282F, 1.5252F, -10.8803F, 1.0F, 0.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1F, 0.5F, 0.0F, 3.1416F, 0.0F, -2.6093F));

		PartDefinition purple_glow = root.addOrReplaceChild("purple_glow", CubeListBuilder.create(),
				PartPose.offset(14.4968F, -16.9252F, -8.3697F));

		PartDefinition bb_main8_r13 = purple_glow.addOrReplaceChild("bb_main8_r13",
				CubeListBuilder.create().texOffs(49, 18).addBox(-1.2718F, 1.6252F, -7.8803F, 3.0F, 0.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.2F, 0.5F, 0.0F, 3.1416F, 0.0F, -2.6093F));

		PartDefinition fuckin_lever_thing = root.addOrReplaceChild("fuckin_lever_thing", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm2_r1 = fuckin_lever_thing.addOrReplaceChild("arm2_r1",
				CubeListBuilder.create().texOffs(0, 26).addBox(-0.2397F, -0.5044F, -1.4812F, 3.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(18.2068F, -14.8572F, -11.0674F, -2.8811F, 0.4595F, 0.5412F));

		PartDefinition arm2_r2 = fuckin_lever_thing.addOrReplaceChild("arm2_r2",
				CubeListBuilder.create().texOffs(25, 78).addBox(-0.4897F, -0.5044F, -0.9812F, 4.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(18.2068F, -14.8572F, -11.0674F, 2.8218F, -0.422F, -2.4617F));

		PartDefinition arm2_r3 = fuckin_lever_thing.addOrReplaceChild("arm2_r3",
				CubeListBuilder.create().texOffs(49, 22)
						.addBox(-9.5F, -26.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(49, 27)
						.addBox(-10.0F, -26.5F, -1.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(55, 108)
						.addBox(-10.7252F, -24.5F, -1.4F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.8218F, -0.422F, -2.4617F));

		PartDefinition arm2_r4 = fuckin_lever_thing.addOrReplaceChild("arm2_r4",
				CubeListBuilder.create().texOffs(50, 73).addBox(-1.25F, -0.25F, 0.0F, 3.0F, 3.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(17.6607F, -14.6034F, -12.0151F, 2.6516F, -0.1925F, -1.9152F));

		PartDefinition fuckin_lever_thing2 = root.addOrReplaceChild("fuckin_lever_thing2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition arm2_r5 = fuckin_lever_thing2.addOrReplaceChild("arm2_r5",
				CubeListBuilder.create().texOffs(0, 26).addBox(-0.2397F, -0.5044F, -1.4812F, 3.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(18.2068F, -14.8572F, -11.0674F, -2.8811F, 0.4595F, 0.5412F));

		PartDefinition arm2_r6 = fuckin_lever_thing2.addOrReplaceChild("arm2_r6",
				CubeListBuilder.create().texOffs(25, 78).addBox(-0.4897F, -0.5044F, -0.9812F, 4.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(18.2068F, -14.8572F, -11.0674F, 2.8218F, -0.422F, -2.4617F));

		PartDefinition arm2_r7 = fuckin_lever_thing2.addOrReplaceChild("arm2_r7",
				CubeListBuilder.create().texOffs(49, 22)
						.addBox(-9.5F, -26.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(49, 27)
						.addBox(-10.0F, -26.5F, -1.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(55, 108)
						.addBox(-10.7252F, -24.5F, -1.4F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.8218F, -0.422F, -2.4617F));

		PartDefinition arm2_r8 = fuckin_lever_thing2.addOrReplaceChild("arm2_r8",
				CubeListBuilder.create().texOffs(50, 73).addBox(-1.25F, -0.25F, 0.0F, 3.0F, 3.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(17.6607F, -14.6034F, -12.0151F, 2.6516F, -0.1925F, -1.9152F));

		PartDefinition bone99 = root.addOrReplaceChild("bone99", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0036F, 0.0F));

		PartDefinition bb_main8_r14 = bone99.addOrReplaceChild("bb_main8_r14",
				CubeListBuilder.create().texOffs(49, 14).addBox(-2.1122F, 0.5446F, -0.7966F, 3.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(16.6978F, -13.5439F, -6.8697F, 3.1042F, 0.4076F, -2.6993F));

		PartDefinition bb_main8_r15 = bone99.addOrReplaceChild("bb_main8_r15",
				CubeListBuilder.create().texOffs(92, 95).addBox(-0.238F, 0.1146F, -0.2966F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(16.6978F, -13.5439F, -6.8697F, 2.8004F, 0.2306F, 2.6791F));

		PartDefinition bone100 = root.addOrReplaceChild("bone100", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bb_main3_r4 = bone100.addOrReplaceChild("bb_main3_r4",
				CubeListBuilder.create().texOffs(41, 97).addBox(-6.775F, -20.309F, 8.55F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.618F, 0.5236F, -3.1416F));

		PartDefinition bone125 = root.addOrReplaceChild("bone125", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r4 = bone125.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(82, 73).addBox(0.0F, -1.5F, 9.0F, 1.0F, 4.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.5359F, -13.5F, -15.8564F, -0.9599F, -0.4887F, 1.2566F));

		PartDefinition bone126 = root.addOrReplaceChild("bone126", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = bone126.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(77, 73).addBox(-0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.7165F, -17.4291F, 6.3239F, 0.5236F, 0.0797F, 1.4747F));

		PartDefinition bone127 = root.addOrReplaceChild("bone127", CubeListBuilder.create(),
				PartPose.offset(-11.7165F, -17.4291F, 6.3239F));

		PartDefinition cube_r6 = bone127.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(0, 64).addBox(-0.5F, -3.75F, -1.5F, 1.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0797F, 1.4747F));

		PartDefinition bone128 = root.addOrReplaceChild("bone128", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r7 = bone128.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(0, 64).addBox(-0.75F, 2.175F, -0.625F, 1.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.7165F, -17.4291F, 6.3239F, 0.5236F, 0.1745F, 1.2217F));

		PartDefinition bone129 = root.addOrReplaceChild("bone129", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r8 = bone129.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, -2.25F, 0.0F, 4.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-13.6104F, -17.1386F, 8.5175F, 0.5236F, 0.1745F, 1.2217F));

		PartDefinition bone130 = root.addOrReplaceChild("bone130", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r9 = bone130.addOrReplaceChild("cube_r9",
				CubeListBuilder.create().texOffs(7, 64).addBox(-1.75F, 6.175F, -1.625F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.7165F, -17.4291F, 6.3239F, 0.4899F, 0.2594F, 1.0655F));

		PartDefinition bone131 = root.addOrReplaceChild("bone131", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r10 = bone131.addOrReplaceChild("cube_r10",
				CubeListBuilder.create().texOffs(5, 34).addBox(-1.75F, 6.675F, -0.375F, 1.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.7165F, -17.4291F, 6.3239F, 0.4899F, 0.2594F, 1.0655F));

		PartDefinition bone132 = root.addOrReplaceChild("bone132", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r11 = bone132.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(5, 34).addBox(-0.4F, -1.325F, -0.375F, 1.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.7165F, -17.4291F, 6.3239F, 0.5493F, -0.0052F, 1.5224F));

		PartDefinition silver_doohickey3 = root.addOrReplaceChild("silver_doohickey3", CubeListBuilder.create(),
				PartPose.offset(-13.307F, -17.5021F, 0.0F));

		PartDefinition silver_doohickey2_r1 = silver_doohickey3.addOrReplaceChild("silver_doohickey2_r1",
				CubeListBuilder.create().texOffs(111, 83).addBox(-1.5886F, -23.9991F, 7.25F, 3.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.307F, 18.5021F, 0.0F, 2.5744F, 0.0F, 3.1416F));

		PartDefinition dial2 = silver_doohickey3.addOrReplaceChild("dial2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(13.307F, 17.5021F, 0.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition silver_doohickey3_r1 = dial2.addOrReplaceChild("silver_doohickey3_r1",
				CubeListBuilder.create().texOffs(41, 94).addBox(-1.0886F, -24.9299F, 9.5736F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.9199F, 0.0F, 3.1416F));

		PartDefinition silver_doohickey4 = root.addOrReplaceChild("silver_doohickey4", CubeListBuilder.create(),
				PartPose.offset(-13.307F, -17.5021F, 0.0F));

		PartDefinition silver_doohickey2_r2 = silver_doohickey4.addOrReplaceChild("silver_doohickey2_r2",
				CubeListBuilder.create().texOffs(5, 39)
						.addBox(1.4114F, -19.2491F, 11.25F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 30)
						.addBox(-0.3386F, -19.2491F, 10.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.307F, 18.5021F, 0.0F, -3.1416F, 1.0472F, -3.098F));

		PartDefinition silver_doohickey5 = root.addOrReplaceChild("silver_doohickey5", CubeListBuilder.create(),
				PartPose.offset(-13.307F, -17.5021F, 0.0F));

		PartDefinition dial4 = silver_doohickey5.addOrReplaceChild("dial4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(13.307F, 17.5021F, 0.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition silver_doohickey3_r2 = dial4.addOrReplaceChild("silver_doohickey3_r2",
				CubeListBuilder.create().texOffs(49, 39).addBox(-1.0886F, -23.9299F, 7.5736F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3398F, 0.7887F, 0.7821F));

		PartDefinition silver_doohickey3_r3 = dial4.addOrReplaceChild("silver_doohickey3_r3",
				CubeListBuilder.create().texOffs(49, 39).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(15.2136F, -18.1729F, -2.9343F, 0.6016F, 0.7887F, 0.7821F));

		PartDefinition bone133 = root.addOrReplaceChild("bone133", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -2.0F, 3.0F, -3.1416F, 1.0472F, -2.9234F));

		PartDefinition cube_r12 = bone133.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(77, 73).addBox(0.0F, -3.5F, -0.25F, 1.0F, 4.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.7165F, -17.4291F, 6.3239F, 0.5222F, 0.0058F, 1.3526F));

		PartDefinition bone134 = root.addOrReplaceChild("bone134", CubeListBuilder.create().texOffs(87, 117)
				.addBox(-4.9558F, -2.0F, -9.5801F, 10.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -20.5F, 0.0F));

		PartDefinition cube_r13 = bone134.addOrReplaceChild("cube_r13",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.3218F, 0.0F, -8.2141F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone135 = bone134.addOrReplaceChild("bone135", CubeListBuilder.create(),
				PartPose.offset(4.5442F, 0.0F, -8.7141F));

		PartDefinition cube_r14 = bone135
				.addOrReplaceChild("cube_r14",
						CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r15 = bone135.addOrReplaceChild("cube_r15",
				CubeListBuilder.create().texOffs(87, 117).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.7942F, 0.0F, 8.9641F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone136 = bone135.addOrReplaceChild("bone136", CubeListBuilder.create(),
				PartPose.offset(-14.366F, 0.0F, 9.2942F));

		PartDefinition cube_r16 = bone136
				.addOrReplaceChild("cube_r16",
						CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r17 = bone136.addOrReplaceChild("cube_r17",
				CubeListBuilder.create().texOffs(87, 117).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(10.1603F, 0.0F, -0.3301F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone137 = bone136.addOrReplaceChild("bone137", CubeListBuilder.create(),
				PartPose.offset(-5.4282F, 0.0F, 8.3301F));

		PartDefinition cube_r18 = bone137
				.addOrReplaceChild("cube_r18",
						CubeListBuilder.create().texOffs(87, 117).addBox(-5.0F, -2.0F, -13.0F, 10.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r19 = bone137.addOrReplaceChild("cube_r19",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.9282F, 0.0F, -0.5359F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone138 = bone137.addOrReplaceChild("bone138", CubeListBuilder.create().texOffs(87, 117)
				.addBox(-9.5F, -2.0F, -4.134F, 10.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(19.7942F, 0.0F, -0.0359F));

		PartDefinition cube_r20 = bone138
				.addOrReplaceChild("cube_r20",
						CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone139 = bone138.addOrReplaceChild("bone139", CubeListBuilder.create(),
				PartPose.offset(10.7942F, 0.0F, 0.0359F));

		PartDefinition cube_r21 = bone139
				.addOrReplaceChild("cube_r21",
						CubeListBuilder.create().texOffs(87, 117).addBox(-5.0F, -2.0F, -13.0F, 10.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r22 = bone139.addOrReplaceChild("cube_r22",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.4282F, 0.0F, -8.3301F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone31 = root.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(96, 61)
				.addBox(-4.5897F, -4.0F, -7.4821F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.6F, -20.45F, -0.35F));

		PartDefinition cube_r23 = bone31.addOrReplaceChild("cube_r23",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.4558F, -2.0F, -6.9821F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone32 = bone31.addOrReplaceChild("bone32", CubeListBuilder.create(),
				PartPose.offset(4.5442F, 0.0F, -8.7141F));

		PartDefinition cube_r24 = bone32.addOrReplaceChild("cube_r24",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.134F, -2.0F, 1.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r25 = bone32.addOrReplaceChild("cube_r25",
				CubeListBuilder.create().texOffs(96, 61).addBox(-4.0F, -2.0F, -9.4821F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.4796F, -2.0F, 9.9372F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone33 = bone32.addOrReplaceChild("bone33", CubeListBuilder.create(),
				PartPose.offset(-14.366F, 0.0F, 9.2942F));

		PartDefinition cube_r26 = bone33.addOrReplaceChild("cube_r26",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.366F, -2.0F, 0.366F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r27 = bone33.addOrReplaceChild("cube_r27",
				CubeListBuilder.create().texOffs(96, 61).addBox(-4.0F, -2.0F, -9.0F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(10.1603F, -2.0F, 0.4019F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone34 = bone33.addOrReplaceChild("bone34", CubeListBuilder.create(),
				PartPose.offset(-5.4282F, 0.0F, 8.3301F));

		PartDefinition cube_r28 = bone34.addOrReplaceChild("cube_r28",
				CubeListBuilder.create().texOffs(96, 61).addBox(-4.0F, -2.0F, -12.0F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r29 = bone34.addOrReplaceChild("cube_r29",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.7942F, -2.0F, -1.0359F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone35 = bone34.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(96, 61)
				.addBox(-9.134F, -4.0F, -3.5F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(19.7942F, 0.0F, -0.0359F));

		PartDefinition cube_r30 = bone35.addOrReplaceChild("cube_r30",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.134F, -2.0F, -0.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone36 = bone35.addOrReplaceChild("bone36", CubeListBuilder.create(),
				PartPose.offset(10.7942F, 0.0F, 0.0359F));

		PartDefinition cube_r31 = bone36.addOrReplaceChild("cube_r31",
				CubeListBuilder.create().texOffs(96, 61).addBox(-3.2045F, -2.0F, -11.4103F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.1764F, -2.0F, 0.3941F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r32 = bone36.addOrReplaceChild("cube_r32",
				CubeListBuilder.create().texOffs(41, 91).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.0622F, -2.0F, -7.9641F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone37 = root.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(96, 61)
				.addBox(-4.5897F, -18.0F, -7.4821F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.6F, -44.05F, -0.35F));

		PartDefinition cube_r33 = bone37.addOrReplaceChild("cube_r33",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.4558F, -16.0F, -6.9821F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone38 = bone37.addOrReplaceChild("bone38", CubeListBuilder.create(),
				PartPose.offset(4.5442F, 0.0F, -8.7141F));

		PartDefinition cube_r34 = bone38.addOrReplaceChild("cube_r34",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.134F, -16.0F, 1.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r35 = bone38.addOrReplaceChild("cube_r35",
				CubeListBuilder.create().texOffs(96, 61).addBox(-4.0F, -2.0F, -9.4821F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.4796F, -16.0F, 9.9372F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone39 = bone38.addOrReplaceChild("bone39", CubeListBuilder.create(),
				PartPose.offset(-14.366F, 0.0F, 9.2942F));

		PartDefinition cube_r36 = bone39.addOrReplaceChild("cube_r36",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.366F, -16.0F, 0.366F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r37 = bone39.addOrReplaceChild("cube_r37",
				CubeListBuilder.create().texOffs(96, 61).addBox(-4.0F, -2.0F, -9.0F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(10.1603F, -16.0F, 0.4019F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create(),
				PartPose.offset(-5.4282F, 0.0F, 8.3301F));

		PartDefinition cube_r38 = bone40.addOrReplaceChild("cube_r38",
				CubeListBuilder.create().texOffs(96, 61).addBox(-4.0F, -2.0F, -12.0F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r39 = bone40.addOrReplaceChild("cube_r39",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.7942F, -16.0F, -1.0359F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone41 = bone40.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(96, 61)
				.addBox(-9.134F, -18.0F, -3.5F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(19.7942F, 0.0F, -0.0359F));

		PartDefinition cube_r40 = bone41.addOrReplaceChild("cube_r40",
				CubeListBuilder.create().texOffs(92, 92).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.134F, -16.0F, -0.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone42 = bone41.addOrReplaceChild("bone42", CubeListBuilder.create(),
				PartPose.offset(10.7942F, 0.0F, 0.0359F));

		PartDefinition cube_r41 = bone42.addOrReplaceChild("cube_r41",
				CubeListBuilder.create().texOffs(96, 61).addBox(-3.2045F, -2.0F, -11.4103F, 8.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.1764F, -16.0F, 0.3941F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r42 = bone42.addOrReplaceChild("cube_r42",
				CubeListBuilder.create().texOffs(41, 91).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.0622F, -16.0F, -7.9641F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone55 = root.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(90, 79)
				.addBox(-4.9558F, -43.4F, -9.5801F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -20.7F, 0.0F));

		PartDefinition cube_r43 = bone55.addOrReplaceChild("cube_r43",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.3218F, -41.4F, -8.2141F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone56 = bone55.addOrReplaceChild("bone56", CubeListBuilder.create(),
				PartPose.offset(4.5442F, 0.0F, -8.7141F));

		PartDefinition cube_r44 = bone56.addOrReplaceChild("cube_r44",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -41.4F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r45 = bone56.addOrReplaceChild("cube_r45",
				CubeListBuilder.create().texOffs(90, 79).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.7942F, -41.4F, 8.9641F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone57 = bone56.addOrReplaceChild("bone57", CubeListBuilder.create(),
				PartPose.offset(-14.366F, 0.0F, 9.2942F));

		PartDefinition cube_r46 = bone57.addOrReplaceChild("cube_r46",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -41.4F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r47 = bone57.addOrReplaceChild("cube_r47",
				CubeListBuilder.create().texOffs(90, 79).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(10.1603F, -41.4F, -0.3301F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone58 = bone57.addOrReplaceChild("bone58", CubeListBuilder.create(),
				PartPose.offset(-5.4282F, 0.0F, 8.3301F));

		PartDefinition cube_r48 = bone58.addOrReplaceChild("cube_r48",
				CubeListBuilder.create().texOffs(90, 79).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -41.4F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r49 = bone58.addOrReplaceChild("cube_r49",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.9282F, -41.4F, -0.5359F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone59 = bone58.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(90, 79)
				.addBox(-9.5F, -43.4F, -1.134F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(19.7942F, 0.0F, -0.0359F));

		PartDefinition cube_r50 = bone59.addOrReplaceChild("cube_r50",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -41.4F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone60 = bone59.addOrReplaceChild("bone60", CubeListBuilder.create(),
				PartPose.offset(10.7942F, 0.0F, 0.0359F));

		PartDefinition cube_r51 = bone60.addOrReplaceChild("cube_r51",
				CubeListBuilder.create().texOffs(90, 79).addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -41.4F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r52 = bone60.addOrReplaceChild("cube_r52",
				CubeListBuilder.create().texOffs(5, 8).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.4282F, -41.4F, -8.3301F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone49 = bone55.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(109, 251).mirror()
				.addBox(-4.9558F, -45.3F, -10.5801F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r53 = bone49.addOrReplaceChild("cube_r53",
				CubeListBuilder.create().texOffs(25, 181).addBox(0.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.1878F, -43.3F, -8.7141F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone50 = bone49.addOrReplaceChild("bone50", CubeListBuilder.create(),
				PartPose.offset(4.5442F, 0.0F, -8.7141F));

		PartDefinition cube_r54 = bone50.addOrReplaceChild("cube_r54",
				CubeListBuilder.create().texOffs(25, 181).addBox(0.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -43.3F, -1.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r55 = bone50.addOrReplaceChild("cube_r55",
				CubeListBuilder.create().texOffs(109, 251).addBox(-5.0F, -2.0F, -11.0F, 10.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.7942F, -43.3F, 8.9641F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone51 = bone50.addOrReplaceChild("bone51", CubeListBuilder.create(),
				PartPose.offset(-14.366F, 0.0F, 9.2942F));

		PartDefinition cube_r56 = bone51.addOrReplaceChild("cube_r56",
				CubeListBuilder.create().texOffs(25, 181).addBox(0.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.866F, -43.3F, 0.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r57 = bone51.addOrReplaceChild("cube_r57",
				CubeListBuilder.create().texOffs(109, 251).addBox(-5.0F, -2.0F, -11.0F, 10.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(10.1603F, -43.3F, -0.3301F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone52 = bone51.addOrReplaceChild("bone52", CubeListBuilder.create(),
				PartPose.offset(-5.4282F, 0.0F, 8.3301F));

		PartDefinition cube_r58 = bone52.addOrReplaceChild("cube_r58",
				CubeListBuilder.create().texOffs(109, 251).mirror()
						.addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, -43.3F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r59 = bone52.addOrReplaceChild("cube_r59",
				CubeListBuilder.create().texOffs(25, 181).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.9282F, -43.3F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone53 = bone52.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(109, 251)
				.addBox(-9.5F, -45.3F, -1.134F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(19.7942F, 0.0F, -0.0359F));

		PartDefinition cube_r60 = bone53.addOrReplaceChild("cube_r60",
				CubeListBuilder.create().texOffs(25, 181).addBox(0.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -43.3F, 1.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create(),
				PartPose.offset(10.7942F, 0.0F, 0.0359F));

		PartDefinition cube_r61 = bone54.addOrReplaceChild("cube_r61",
				CubeListBuilder.create().texOffs(109, 251).mirror()
						.addBox(-5.0F, -2.0F, -10.0F, 10.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, -43.3F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r62 = bone54.addOrReplaceChild("cube_r62",
				CubeListBuilder.create().texOffs(25, 181).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.5622F, -43.3F, -8.8301F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone7 = root.addOrReplaceChild("bone7",
				CubeListBuilder.create().texOffs(17, 121).addBox(-2.634F, -2.0F, 4.5622F, 6.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.741F, -19.5F, 0.0128F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r63 = bone7
				.addOrReplaceChild("cube_r63",
						CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone8 = bone7
				.addOrReplaceChild("bone8",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -2.0F, 4.5622F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r64 = bone8.addOrReplaceChild("cube_r64",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone9 = bone8
				.addOrReplaceChild("bone9",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.7321F, -2.0F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r65 = bone9.addOrReplaceChild("cube_r65",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0981F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone10 = bone9
				.addOrReplaceChild("bone10",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -2.0F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r66 = bone10.addOrReplaceChild("cube_r66",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone11 = bone10
				.addOrReplaceChild("bone11",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.634F, -2.0F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r67 = bone11.addOrReplaceChild("cube_r67",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone12 = bone11
				.addOrReplaceChild("bone12",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.2679F, -2.0F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r68 = bone12.addOrReplaceChild("cube_r68",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.366F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone13 = bone7.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(103, 127)
				.addBox(-2.634F, -2.0F, 4.5622F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r69 = bone13
				.addOrReplaceChild("cube_r69",
						CubeListBuilder.create().texOffs(103, 127).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone14 = bone13
				.addOrReplaceChild("bone14",
						CubeListBuilder.create().texOffs(103, 127).addBox(-3.366F, -2.0F, 4.5622F, 6.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r70 = bone14.addOrReplaceChild("cube_r70",
				CubeListBuilder.create().texOffs(103, 127).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 3.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone15 = bone14
				.addOrReplaceChild("bone15",
						CubeListBuilder.create().texOffs(103, 127).addBox(-3.7321F, -2.0F, 5.1962F, 6.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r71 = bone15.addOrReplaceChild("cube_r71",
				CubeListBuilder.create().texOffs(103, 127).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 3.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0981F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone16 = bone15
				.addOrReplaceChild("bone16",
						CubeListBuilder.create().texOffs(103, 127).addBox(-3.366F, -2.0F, 5.8301F, 6.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r72 = bone16.addOrReplaceChild("cube_r72",
				CubeListBuilder.create().texOffs(103, 127).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 3.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone17 = bone16
				.addOrReplaceChild("bone17",
						CubeListBuilder.create().texOffs(103, 127).addBox(-2.634F, -2.0F, 5.8301F, 6.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r73 = bone17.addOrReplaceChild("cube_r73",
				CubeListBuilder.create().texOffs(103, 127).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 3.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone18 = bone17
				.addOrReplaceChild("bone18",
						CubeListBuilder.create().texOffs(103, 127).addBox(-2.2679F, -2.0F, 5.1962F, 6.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r74 = bone18.addOrReplaceChild("cube_r74",
				CubeListBuilder.create().texOffs(103, 127).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 3.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.366F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone19 = root.addOrReplaceChild("bone19",
				CubeListBuilder.create().texOffs(17, 121).addBox(-2.634F, -2.0F, 4.5622F, 6.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.741F, -22.3F, 0.0128F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r75 = bone19
				.addOrReplaceChild("cube_r75",
						CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone20 = bone19
				.addOrReplaceChild("bone20",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -2.0F, 4.5622F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r76 = bone20.addOrReplaceChild("cube_r76",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone21 = bone20
				.addOrReplaceChild("bone21",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.7321F, -2.0F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r77 = bone21.addOrReplaceChild("cube_r77",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0981F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone22 = bone21
				.addOrReplaceChild("bone22",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -2.0F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r78 = bone22.addOrReplaceChild("cube_r78",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone23 = bone22
				.addOrReplaceChild("bone23",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.634F, -2.0F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r79 = bone23.addOrReplaceChild("cube_r79",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone24 = bone23
				.addOrReplaceChild("bone24",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.2679F, -2.0F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r80 = bone24.addOrReplaceChild("cube_r80",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.366F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone25 = bone19.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(17, 121)
				.addBox(-2.634F, -41.0F, 4.5622F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r81 = bone25
				.addOrReplaceChild("cube_r81",
						CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -41.0F, 5.0F, 2.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone26 = bone25
				.addOrReplaceChild("bone26",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -41.0F, 4.5622F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r82 = bone26.addOrReplaceChild("cube_r82",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -41.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone27 = bone26
				.addOrReplaceChild("bone27",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.7321F, -41.0F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r83 = bone27.addOrReplaceChild("cube_r83",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -41.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0981F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone28 = bone27
				.addOrReplaceChild("bone28",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -41.0F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r84 = bone28.addOrReplaceChild("cube_r84",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -41.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone29 = bone28
				.addOrReplaceChild("bone29",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.634F, -41.0F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r85 = bone29.addOrReplaceChild("cube_r85",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -41.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone30 = bone29
				.addOrReplaceChild("bone30",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.2679F, -41.0F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r86 = bone30.addOrReplaceChild("cube_r86",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -41.0F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.366F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone43 = bone30.addOrReplaceChild("bone43",
				CubeListBuilder.create().texOffs(17, 121).addBox(-2.634F, -39.9F, 4.5622F, 6.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r87 = bone43
				.addOrReplaceChild("cube_r87",
						CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -39.9F, 5.0F, 2.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone44 = bone43
				.addOrReplaceChild("bone44",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -39.9F, 4.5622F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r88 = bone44.addOrReplaceChild("cube_r88",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -39.9F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone45 = bone44
				.addOrReplaceChild("bone45",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.7321F, -39.9F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r89 = bone45.addOrReplaceChild("cube_r89",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -39.9F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0981F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone46 = bone45
				.addOrReplaceChild("bone46",
						CubeListBuilder.create().texOffs(17, 121).addBox(-3.366F, -39.9F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r90 = bone46.addOrReplaceChild("cube_r90",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -39.9F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7321F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone47 = bone46
				.addOrReplaceChild("bone47",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.634F, -39.9F, 5.8301F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r91 = bone47.addOrReplaceChild("cube_r91",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -39.9F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 1.2679F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone48 = bone47
				.addOrReplaceChild("bone48",
						CubeListBuilder.create().texOffs(17, 121).addBox(-2.2679F, -39.9F, 5.1962F, 6.0F, 2.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r92 = bone48.addOrReplaceChild("cube_r92",
				CubeListBuilder.create().texOffs(17, 121).addBox(-1.0F, -39.9F, 5.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.366F, 0.0F, 0.634F, 0.0F, -0.5236F, 0.0F));

		PartDefinition rotor_top = root.addOrReplaceChild("rotor_top",
				CubeListBuilder.create().texOffs(53, 84)
						.addBox(-0.5F, -70.6F, 2.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.2F)).texOffs(59, 73)
						.addBox(-1.0F, -70.6F, -4.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 3.7F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition rotor_ring_top3_r1 = rotor_top.addOrReplaceChild("rotor_ring_top3_r1",
				CubeListBuilder.create().texOffs(59, 73).addBox(-1.0F, -66.3F, -4.0F, 2.0F, 25.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -4.3F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition rotor_ring_top3_r2 = rotor_top.addOrReplaceChild("rotor_ring_top3_r2",
				CubeListBuilder.create().texOffs(48, 84).addBox(-0.5F, -66.8F, 2.5F, 1.0F, 25.0F, 1.0F,
						new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, -3.8F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition rotor_ring_top3_r3 = rotor_top.addOrReplaceChild("rotor_ring_top3_r3",
				CubeListBuilder.create().texOffs(77, 85)
						.addBox(-0.5F, -25.3F, 2.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.2F)).texOffs(59, 73)
						.addBox(-1.0F, -25.3F, -4.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -45.3F, 0.0F, 3.1416F, 1.0472F, 3.1416F));

		PartDefinition rotor_ring3 = rotor_top.addOrReplaceChild("rotor_ring3", CubeListBuilder.create().texOffs(98, 22)
				.addBox(-1.866F, -25.6F, -5.6962F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-0.134F, -31.7F, 0.1962F));

		PartDefinition cube_r93 = rotor_ring3
				.addOrReplaceChild("cube_r93",
						CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone71 = rotor_ring3
				.addOrReplaceChild("bone71",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -25.6F, -5.6962F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r94 = bone71.addOrReplaceChild("cube_r94",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone72 = bone71
				.addOrReplaceChild("bone72",
						CubeListBuilder.create().texOffs(92, 92).addBox(-2.2679F, -25.6F, -5.4641F, 4.0F, 1.0F, 6.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r95 = bone72.addOrReplaceChild("cube_r95",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4019F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone79 = bone72
				.addOrReplaceChild("bone79",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -25.6F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r96 = bone79.addOrReplaceChild("cube_r96",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone80 = bone79
				.addOrReplaceChild("bone80",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.866F, -25.6F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r97 = bone80.addOrReplaceChild("cube_r97",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone81 = bone80
				.addOrReplaceChild("bone81",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.7321F, -25.6F, -5.4641F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r98 = bone81.addOrReplaceChild("cube_r98",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.134F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition rotor_ring4 = rotor_ring3.addOrReplaceChild("rotor_ring4",
				CubeListBuilder.create().texOffs(98, 22).addBox(-1.866F, -25.6F, -5.6962F, 4.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r99 = rotor_ring4
				.addOrReplaceChild("cube_r99",
						CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone82 = rotor_ring4
				.addOrReplaceChild("bone82",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -25.6F, -5.6962F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r100 = bone82.addOrReplaceChild("cube_r100",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone83 = bone82
				.addOrReplaceChild("bone83",
						CubeListBuilder.create().texOffs(92, 92).addBox(-2.2679F, -25.6F, -5.4641F, 4.0F, 1.0F, 6.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r101 = bone83.addOrReplaceChild("cube_r101",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4019F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone84 = bone83
				.addOrReplaceChild("bone84",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -25.6F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r102 = bone84.addOrReplaceChild("cube_r102",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone85 = bone84
				.addOrReplaceChild("bone85",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.866F, -25.6F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r103 = bone85.addOrReplaceChild("cube_r103",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone86 = bone85
				.addOrReplaceChild("bone86",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.7321F, -25.6F, -5.4641F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r104 = bone86.addOrReplaceChild("cube_r104",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -25.6F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.134F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition rotor_bottom = root.addOrReplaceChild("rotor_bottom",
				CubeListBuilder.create().texOffs(68, 73)
						.addBox(-1.0F, -15.1571F, 2.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(82, 85)
						.addBox(-0.5F, -15.1571F, -3.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, -29.8929F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition rotor_ring_bottom_r1 = rotor_bottom.addOrReplaceChild("rotor_ring_bottom_r1",
				CubeListBuilder.create().texOffs(68, 73).addBox(-1.0F, -45.3F, 2.0F, 2.0F, 25.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 30.1429F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition rotor_ring_bottom_r2 = rotor_bottom.addOrReplaceChild("rotor_ring_bottom_r2",
				CubeListBuilder.create().texOffs(87, 85)
						.addBox(-0.5F, -0.3F, -3.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(68, 73)
						.addBox(-1.0F, -0.3F, 2.0F, 2.0F, 25.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -14.8571F, 0.0F, 3.1416F, 1.0472F, 3.1416F));

		PartDefinition rotor_ring_bottom_r3 = rotor_bottom.addOrReplaceChild("rotor_ring_bottom_r3",
				CubeListBuilder.create().texOffs(0, 88).addBox(-0.5F, -41.3F, -3.5F, 1.0F, 25.0F, 1.0F,
						new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 26.1429F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition console_collar_one16 = rotor_bottom.addOrReplaceChild("console_collar_one16",
				CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 42.1429F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition console_collar_one17 = console_collar_one16.addOrReplaceChild("console_collar_one17",
				CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone73 = rotor_bottom.addOrReplaceChild("bone73", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 42.1429F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone74 = bone73.addOrReplaceChild("bone74", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone75 = bone74.addOrReplaceChild("bone75", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone76 = bone75.addOrReplaceChild("bone76", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone77 = bone76.addOrReplaceChild("bone77", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone78 = bone77.addOrReplaceChild("bone78", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition rotor_ring = rotor_bottom.addOrReplaceChild("rotor_ring", CubeListBuilder.create()
				.texOffs(98, 22).addBox(-1.866F, -3.2F, -5.6962F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-0.134F, 3.5929F, 0.1962F));

		PartDefinition cube_r105 = rotor_ring
				.addOrReplaceChild("cube_r105",
						CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone62 = rotor_ring
				.addOrReplaceChild("bone62",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -3.2F, -5.6962F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r106 = bone62.addOrReplaceChild("cube_r106",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone63 = bone62
				.addOrReplaceChild("bone63",
						CubeListBuilder.create().texOffs(92, 92).addBox(-2.2679F, -3.2F, -5.4641F, 4.0F, 1.0F, 6.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r107 = bone63.addOrReplaceChild("cube_r107",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4019F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone64 = bone63
				.addOrReplaceChild("bone64",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -3.2F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r108 = bone64.addOrReplaceChild("cube_r108",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone65 = bone64
				.addOrReplaceChild("bone65",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.866F, -3.2F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r109 = bone65.addOrReplaceChild("cube_r109",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone66 = bone65
				.addOrReplaceChild("bone66",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.7321F, -3.2F, -5.4641F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r110 = bone66.addOrReplaceChild("cube_r110",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.134F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition rotor_ring2 = rotor_ring.addOrReplaceChild("rotor_ring2",
				CubeListBuilder.create().texOffs(98, 22).addBox(-1.866F, -3.2F, -5.6962F, 4.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r111 = rotor_ring2
				.addOrReplaceChild("cube_r111",
						CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone61 = rotor_ring2
				.addOrReplaceChild("bone61",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -3.2F, -5.6962F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r112 = bone61.addOrReplaceChild("cube_r112",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone67 = bone61
				.addOrReplaceChild("bone67",
						CubeListBuilder.create().texOffs(92, 92).addBox(-2.2679F, -3.2F, -5.4641F, 4.0F, 1.0F, 6.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r113 = bone67.addOrReplaceChild("cube_r113",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4019F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone68 = bone67
				.addOrReplaceChild("bone68",
						CubeListBuilder.create().texOffs(98, 22).addBox(-2.134F, -3.2F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r114 = bone68.addOrReplaceChild("cube_r114",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2679F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone69 = bone68
				.addOrReplaceChild("bone69",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.866F, -3.2F, -5.2321F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r115 = bone69.addOrReplaceChild("cube_r115",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.4641F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone70 = bone69
				.addOrReplaceChild("bone70",
						CubeListBuilder.create().texOffs(98, 22).addBox(-1.7321F, -3.2F, -5.4641F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r116 = bone70.addOrReplaceChild("cube_r116",
				CubeListBuilder.create().texOffs(49, 35).addBox(-1.0F, -3.2F, -6.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.134F, 0.0F, 0.2321F, 0.0F, -0.5236F, 0.0F));

		PartDefinition monitor = root.addOrReplaceChild("monitor", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-6.4608F, -19.484F, -11.5705F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bb_main3_r5 = monitor.addOrReplaceChild("bb_main3_r5",
				CubeListBuilder.create().texOffs(98, 56).addBox(-4.5F, 1.5393F, 2.5872F, 9.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.158F, 0.1264F, -1.8898F, 1.5272F, -0.5236F, 0.0F));

		PartDefinition bb_main3_r6 = monitor.addOrReplaceChild("bb_main3_r6",
				CubeListBuilder.create().texOffs(98, 42).addBox(-4.5F, -1.5607F, -4.2378F, 9.0F, 2.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.158F, 0.1264F, -1.8898F, -1.5272F, 0.5236F, -3.1416F));

		PartDefinition bb_main3_r7 = monitor.addOrReplaceChild("bb_main3_r7",
				CubeListBuilder.create().texOffs(20, 91).addBox(-4.5F, -3.5F, -0.5F, 9.0F, 7.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.6304F, 0.0793F, -0.9758F, -3.098F, 0.5236F, -3.1416F));

		PartDefinition bb_main3_r8 = monitor.addOrReplaceChild("bb_main3_r8",
				CubeListBuilder.create().texOffs(77, 73).addBox(-0.275F, -21.676F, 5.9936F, 1.0F, 1.0F, 10.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.3725F, 19.484F, 12.8715F, 3.098F, 0.5236F, -3.1416F));

		PartDefinition throttle = root.addOrReplaceChild("throttle",
				CubeListBuilder.create().texOffs(5, 88)
						.addBox(-0.5F, -2.6566F, 0.1088F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(5, 104)
						.addBox(-0.5F, -2.6566F, 0.1088F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(1.275F, -18.45F, -14.75F, -0.6545F, 0.0F, 0.0F));

		PartDefinition handbrake = root.addOrReplaceChild("handbrake", CubeListBuilder.create(),
				PartPose.offset(-17.4744F, -9.731F, -14.5842F));

		PartDefinition middle_ring7_r8 = handbrake.addOrReplaceChild("middle_ring7_r8",
				CubeListBuilder.create().texOffs(2, 41).addBox(-0.4447F, -0.6195F, -3.3922F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, -0.7854F, 0.0F));

		PartDefinition door_control = root.addOrReplaceChild("door_control", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.05F, -16.3071F, 20.8887F, 0.0F, 1.5708F, 0.0F));

		PartDefinition door_control_r1 = door_control.addOrReplaceChild("door_control_r1",
				CubeListBuilder.create().texOffs(50, 77)
						.addBox(-9.9463F, -2.187F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 88)
						.addBox(-7.9463F, -2.187F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(7.5817F, -1.195F, -0.05F, 0.0F, 0.0F, -0.4276F));

		PartDefinition communicator = root.addOrReplaceChild("communicator", CubeListBuilder.create(),
				PartPose.offset(14.4968F, -16.9252F, -8.3697F));

		PartDefinition bb_main8_r16 = communicator.addOrReplaceChild("bb_main8_r16",
				CubeListBuilder.create().texOffs(37, 111)
						.addBox(-4.25F, 1.35F, -5.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 17)
						.addBox(-4.5F, 1.1F, -4.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -3.1252F, 0.0405F, -2.7573F));

		PartDefinition randomiser = root.addOrReplaceChild("randomiser", CubeListBuilder.create(),
				PartPose.offset(-13.307F, -17.5021F, 0.0F));

		PartDefinition randomiser_r1 = randomiser.addOrReplaceChild("randomiser_r1",
				CubeListBuilder.create().texOffs(32, 73).addBox(-6.8316F, 1.503F, -1.5F, 4.0F, 1.0F, 9.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.0436F, -0.5149F));

		PartDefinition x = root.addOrReplaceChild("x", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bb_main3_r9 = x.addOrReplaceChild("bb_main3_r9",
				CubeListBuilder.create().texOffs(49, 48).addBox(3.225F, -20.425F, 4.3F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.618F, 0.5236F, -3.1416F));

		PartDefinition y = root.addOrReplaceChild("y", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bb_main3_r10 = y.addOrReplaceChild("bb_main3_r10",
				CubeListBuilder.create().texOffs(49, 48).addBox(-0.775F, -20.425F, 4.3F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.618F, 0.5236F, -3.1416F));

		PartDefinition z = root.addOrReplaceChild("z", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bb_main3_r11 = z.addOrReplaceChild("bb_main3_r11",
				CubeListBuilder.create().texOffs(49, 48).addBox(-4.775F, -20.425F, 4.3F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.618F, 0.5236F, -3.1416F));

		PartDefinition increment = root.addOrReplaceChild("increment", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition ppweight_r3 = increment.addOrReplaceChild("ppweight_r3",
				CubeListBuilder.create().texOffs(93, 61).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(12.0778F, -15.8131F, 0.0F, 0.0F, 1.5708F, 0.5672F));

		PartDefinition refuller = root.addOrReplaceChild("refuller", CubeListBuilder.create(),
				PartPose.offset(18.0284F, -7.3756F, -7.5399F));

		PartDefinition cube_r117 = refuller.addOrReplaceChild("cube_r117",
				CubeListBuilder.create().texOffs(7, 21)
						.addBox(-0.25F, -6.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 47)
						.addBox(-0.25F, -4.5F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4821F, 0.2129F, 1.187F));

		PartDefinition fast_return_switch = root.addOrReplaceChild("fast_return_switch", CubeListBuilder.create(),
				PartPose.offset(-7.5697F, -15.0238F, 12.8985F));

		PartDefinition bone98_r2 = fast_return_switch.addOrReplaceChild("bone98_r2",
				CubeListBuilder.create().texOffs(99, 0).addBox(0.0F, 0.25F, -1.25F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6125F, -0.5417F, -0.0562F));

		PartDefinition exterior_facing = root.addOrReplaceChild("exterior_facing", CubeListBuilder.create(),
				PartPose.offset(9.011F, -18.3241F, 6.8925F));

		PartDefinition silver_doohickey2_r3 = exterior_facing.addOrReplaceChild("silver_doohickey2_r3",
				CubeListBuilder.create().texOffs(56, 8)
						.addBox(-2.3386F, -19.6991F, 10.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(77, 79)
						.addBox(-1.5886F, -19.9491F, 10.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-9.011F, 19.3241F, -6.8925F, 0.0F, 1.0472F, 0.0F));

		PartDefinition dimension_changer = root.addOrReplaceChild("dimension_changer", CubeListBuilder.create(),
				PartPose.offset(-0.5F, -11.45F, 18.75F));

		PartDefinition bb_main8_r17 = dimension_changer.addOrReplaceChild("bb_main8_r17",
				CubeListBuilder.create().texOffs(49, 56).addBox(9.75F, -1.275F, 1.0F, 3.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.5236F, 0.0F));

		PartDefinition bone87 = root.addOrReplaceChild("bone87", CubeListBuilder.create(),
				PartPose.offset(-5.8304F, -19.4047F, -12.5463F));

		PartDefinition bb_main3_r12 = bone87.addOrReplaceChild("bb_main3_r12",
				CubeListBuilder.create().texOffs(30, 192).addBox(-2.5F, -3.0F, 0.125F, 7.0F, 6.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.098F, -0.5236F, -3.1416F));

		PartDefinition ttsControls = partdefinition.addOrReplaceChild("ttsControls",
				CubeListBuilder.create().texOffs(242, 248)
						.addBox(-1.4F, -20.35F, -17.0F, 3.0F, 4.2F, 4.0F, new CubeDeformation(0.0F)).texOffs(246, 253)
						.addBox(-13.85F, -13.75F, 10.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(246, 253)
						.addBox(-15.1F, -12.5F, 11.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(246, 253)
						.addBox(-12.9F, -13.3F, 13.1F, 1.275F, 1.725F, 1.45F, new CubeDeformation(0.0F))
						.texOffs(242, 248).addBox(-1.1F, -17.8F, 14.875F, 2.2F, 4.0F, 6.575F, new CubeDeformation(0.0F))
						.texOffs(246, 252).addBox(-0.35F, -14.1F, 21.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
						.texOffs(246, 252).addBox(2.15F, -12.6F, 17.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
						.texOffs(249, 254).addBox(5.9F, -13.25F, 15.95F, 0.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(249, 254).addBox(6.8F, -13.25F, 15.45F, 0.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(249, 254).addBox(7.7F, -13.25F, 14.85F, 0.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(250, 255).addBox(7.0F, -13.5F, 13.95F, 0.6F, 1.0F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(250, 255).addBox(8.3F, -13.5F, 13.25F, 0.6F, 1.0F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(250, 255).addBox(9.2F, -13.5F, 12.75F, 0.6F, 1.0F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(250, 255).addBox(10.05F, -13.5F, 12.25F, 0.6F, 1.0F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(250, 255).addBox(6.15F, -13.5F, 14.45F, 0.6F, 1.0F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(250, 255).addBox(5.25F, -13.5F, 14.95F, 0.6F, 1.0F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(248, 253).addBox(8.7F, -13.25F, 13.15F, 2.6F, 1.0F, 2.1F, new CubeDeformation(0.0F))
						.texOffs(244, 251).addBox(-1.6F, -17.75F, 9.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(244, 251).addBox(-13.65F, -16.3F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(250, 254).addBox(-16.55F, -13.5F, 5.3F, 0.9F, 0.5F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(250, 254).addBox(-18.35F, -12.7F, 5.3F, 1.4F, 0.7F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(250, 254).addBox(-18.1F, -12.8F, -0.8F, 1.4F, 0.8F, 1.1F, new CubeDeformation(0.0F))
						.texOffs(251, 254).addBox(-17.35F, -12.75F, 3.8F, 0.4F, 0.3F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(251, 254).addBox(-18.25F, -12.25F, 3.75F, 0.4F, 0.3F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(251, 254).addBox(-17.3F, -12.75F, 2.3F, 0.4F, 0.3F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(251, 254).addBox(-18.2F, -12.25F, 2.25F, 0.4F, 0.3F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(251, 254).addBox(-17.25F, -12.75F, 0.8F, 0.4F, 0.3F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(251, 254).addBox(-18.15F, -12.25F, 0.75F, 0.4F, 0.3F, 1.0F, new CubeDeformation(0.0F))
						.texOffs(250, 254).addBox(-16.5F, -13.5F, 4.3F, 0.9F, 0.5F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(250, 254).addBox(-16.5F, -13.5F, 3.3F, 0.9F, 0.5F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(249, 254).addBox(-16.45F, -13.5F, 2.3F, 0.9F, 0.5F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(249, 254).addBox(-16.4F, -13.5F, 1.3F, 0.9F, 0.5F, 0.6F, new CubeDeformation(0.0F))
						.texOffs(249, 254).addBox(-16.35F, -13.55F, 0.3F, 0.9F, 0.55F, 0.6F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r118 = ttsControls.addOrReplaceChild("cube_r118",
				CubeListBuilder.create().texOffs(244, 251).addBox(-2.3F, -3.0F, -1.9F, 3.3F, 2.0F, 3.6F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-14.0F, -14.25F, -7.85F, 0.0F, -0.48F, 0.0F));

		PartDefinition cube_r119 = ttsControls.addOrReplaceChild("cube_r119",
				CubeListBuilder.create().texOffs(245, 252).addBox(-0.85F, -2.0F, -1.025F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.5F, -16.0F, -6.35F, 0.0F, -0.5061F, 0.0F));

		PartDefinition cube_r120 = ttsControls.addOrReplaceChild("cube_r120",
				CubeListBuilder.create().texOffs(244, 253).addBox(-1.4F, -1.0F, -1.075F, 3.0F, 2.0F, 2.2F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.15F, -15.3F, 11.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r121 = ttsControls.addOrReplaceChild("cube_r121",
				CubeListBuilder.create().texOffs(245, 253).addBox(-2.1F, -1.0F, -0.95F, 4.05F, 2.0F, 1.9F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.15F, -12.5F, 15.75F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r122 = ttsControls.addOrReplaceChild("cube_r122",
				CubeListBuilder.create().texOffs(244, 252).addBox(-0.95F, -1.175F, -1.075F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.65F, -14.55F, 12.25F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r123 = ttsControls.addOrReplaceChild("cube_r123",
				CubeListBuilder.create().texOffs(247, 254).addBox(-0.5F, -0.5F, -0.375F, 0.975F, 1.0F, 0.95F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-9.55F, -13.75F, 12.35F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r124 = ttsControls.addOrReplaceChild("cube_r124",
				CubeListBuilder.create().texOffs(247, 254).addBox(-0.525F, -0.5F, -0.475F, 0.975F, 1.0F, 0.975F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.05F, -13.75F, 11.6F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r125 = ttsControls.addOrReplaceChild("cube_r125",
				CubeListBuilder.create().texOffs(246, 255)
						.addBox(-2.5F, -2.0F, -0.7F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(246, 255)
						.addBox(-2.5F, -2.0F, 0.7F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-18.85F, -15.25F, 10.65F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r126 = ttsControls.addOrReplaceChild("cube_r126",
				CubeListBuilder.create().texOffs(246, 252).addBox(-1.65F, -1.5F, -1.575F, 3.4F, 3.0F, 3.275F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-17.35F, -13.25F, -5.35F, 0.0F, 0.2793F, 0.0F));

		PartDefinition cube_r127 = ttsControls.addOrReplaceChild("cube_r127",
				CubeListBuilder.create().texOffs(245, 252).addBox(-0.975F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-18.1F, -14.5F, -9.85F, 0.0F, -0.48F, 0.0F));

		PartDefinition cube_r128 = ttsControls.addOrReplaceChild("cube_r128",
				CubeListBuilder.create().texOffs(246, 251).addBox(-0.9F, -1.0F, -1.025F, 2.0F, 2.4F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, -13.25F, -16.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r129 = ttsControls.addOrReplaceChild("cube_r129",
				CubeListBuilder.create().texOffs(240, 250).addBox(-0.725F, -1.0F, -3.1F, 4.45F, 2.0F, 5.7F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-17.025F, -10.0F, -14.6F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r130 = ttsControls.addOrReplaceChild("cube_r130",
				CubeListBuilder.create().texOffs(241, 251).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 3.5F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-9.75F, -12.5F, -14.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r131 = ttsControls.addOrReplaceChild("cube_r131",
				CubeListBuilder.create().texOffs(233, 247).addBox(-4.8F, -3.5F, -3.125F, 9.0F, 7.0F, 3.325F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.0F, -19.5F, -11.5F, 0.0F, 0.5236F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		// ttsControls.render(poseStack, vertexConsumer, packedLight, packedOverlay,
		// red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

	@Override
	public void SetupAnimations(T tile, float ageInTicks) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

	}
}