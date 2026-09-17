/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.models.consoles;// Made with Blockbench 5.1.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.core.tileentities.consoles.TakomakConsoleTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

import com.code.tama.triggerapi.universal.UniversalCommon;

@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class TakomakModel<T extends TakomakConsoleTile> extends HierarchicalModel<Entity>
		implements
			IAnimateableModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(UniversalCommon.modRL("takomak"),
			"main");
	private final ModelPart root;
	private final ModelPart bottom;
	private final ModelPart hex;
	private final ModelPart BTopPanels2;
	private final ModelPart BTopPanels;
	private final ModelPart BTopBottomSeperators;
	private final ModelPart BBottomPanels2;
	private final ModelPart BBottomPanels;
	private final ModelPart BBottomSeperators;
	private final ModelPart emmissives;
	private final ModelPart RotorEmmisiveOne6;
	private final ModelPart bone69;
	private final ModelPart bone70;
	private final ModelPart bone71;
	private final ModelPart bone72;
	private final ModelPart bone73;
	private final ModelPart bone74;
	private final ModelPart rotorLightBeamWindow;
	private final ModelPart base;
	private final ModelPart panels;
	private final ModelPart bottomPanels;
	private final ModelPart bottompanel1;
	private final ModelPart bottompanel2;
	private final ModelPart bottompanel3;
	private final ModelPart bottompanel4;
	private final ModelPart bottompanel5;
	private final ModelPart bottomPanelMergers;
	private final ModelPart bottomMerger1;
	private final ModelPart middlePanels;
	private final ModelPart PanelBackFillers;
	private final ModelPart bone37;
	private final ModelPart bone39;
	private final ModelPart bone46;
	private final ModelPart bone38;
	private final ModelPart bone25;
	private final ModelPart bone42;
	private final ModelPart bone32;
	private final ModelPart bone54;
	private final ModelPart bone65;
	private final ModelPart bone40;
	private final ModelPart topPanels;
	private final ModelPart topPanelMergers;
	private final ModelPart bone;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart bone2;
	private final ModelPart panel1;
	private final ModelPart joystick;
	private final ModelPart bone77;
	private final ModelPart bone76;
	private final ModelPart bone75;
	private final ModelPart bone78;
	private final ModelPart panel1Controls;
	private final ModelPart doorControl;
	private final ModelPart doorLight1;
	private final ModelPart doorLight2;
	private final ModelPart doorLight3;
	private final ModelPart doorLight4;
	private final ModelPart doorLight5;
	private final ModelPart doorLight6;
	private final ModelPart bone14;
	private final ModelPart bone17;
	private final ModelPart doorControlKnob;
	private final ModelPart doorControlLever;
	private final ModelPart panel1Misc;
	private final ModelPart panel1Tape;
	private final ModelPart panel2;
	private final ModelPart panel2panel;
	private final ModelPart bone26;
	private final ModelPart bone27;
	private final ModelPart bone51;
	private final ModelPart panel2panelMisc;
	private final ModelPart panel2panelLight11;
	private final ModelPart bone12;
	private final ModelPart panel3;
	private final ModelPart bone16;
	private final ModelPart bone34;
	private final ModelPart bone35;
	private final ModelPart bone31;
	private final ModelPart bone28;
	private final ModelPart bone63;
	private final ModelPart bone29;
	private final ModelPart bone30;
	private final ModelPart bone67;
	private final ModelPart bone36;
	private final ModelPart panel4;
	private final ModelPart panel4Lights;
	private final ModelPart panel4Light1;
	private final ModelPart panel4Light2;
	private final ModelPart panel4Controls;
	private final ModelPart panel4wire;
	private final ModelPart panel4PumpThings;
	private final ModelPart panel4PumpThing1;
	private final ModelPart panel4PumpThing2;
	private final ModelPart coffeepot;
	private final ModelPart bone33;
	private final ModelPart panel5;
	private final ModelPart bone50;
	private final ModelPart doodads;
	private final ModelPart bone7;
	private final ModelPart bone8;
	private final ModelPart bone20;
	private final ModelPart bone19;
	private final ModelPart bone9;
	private final ModelPart bone11;
	private final ModelPart bone18;
	private final ModelPart bone13;
	private final ModelPart bone66;
	private final ModelPart bone68;
	private final ModelPart panel6;
	private final ModelPart bone47;
	private final ModelPart bone79;
	private final ModelPart bone83;
	private final ModelPart bone81;
	private final ModelPart bone80;
	private final ModelPart bone10;
	private final ModelPart bone48;
	private final ModelPart bone49;
	private final ModelPart rotor;
	private final ModelPart rotorTop;
	private final ModelPart bone53;
	private final ModelPart bone55;
	private final ModelPart bone52;
	private final ModelPart bone56;
	private final ModelPart bone57;
	private final ModelPart bone61;
	private final ModelPart bone58;
	private final ModelPart bone59;
	private final ModelPart bone60;
	private final ModelPart rotorBase;
	private final ModelPart rotorglass;
	private final ModelPart rotorBaseBit;
	private final ModelPart rotorBaseBit2;
	private final ModelPart rotorBaseBit3;
	private final ModelPart rotorBaseBit4;
	private final ModelPart timeColumn;
	private final ModelPart timeColumnBit;
	private final ModelPart timeColumnBit2;
	private final ModelPart timeColumnFins1;
	private final ModelPart bone15;
	private final ModelPart bone21;
	private final ModelPart cube_112;
	private final ModelPart cube_113;
	private final ModelPart cube_114;
	private final ModelPart cube_115;
	private final ModelPart cube_116;
	private final ModelPart cube_117;
	private final ModelPart cube_118;
	private final ModelPart cube_119;
	private final ModelPart cube_120;
	private final ModelPart cube_121;
	private final ModelPart cube_122;
	private final ModelPart cube_123;
	private final ModelPart cube_124;
	private final ModelPart cube_125;
	private final ModelPart cube_126;
	private final ModelPart cube_127;
	private final ModelPart cube_128;
	private final ModelPart cube_129;
	private final ModelPart cube_130;
	private final ModelPart cube_131;
	private final ModelPart cube_132;
	private final ModelPart cube_133;
	private final ModelPart bone22;
	private final ModelPart timeColumnFins2;
	private final ModelPart bone23;
	private final ModelPart bone24;
	private final ModelPart cube_2;
	private final ModelPart cube_3;
	private final ModelPart cube_4;
	private final ModelPart cube_5;
	private final ModelPart cube_6;
	private final ModelPart cube_7;
	private final ModelPart cube_8;
	private final ModelPart cube_9;
	private final ModelPart cube_10;
	private final ModelPart cube_11;
	private final ModelPart cube_12;
	private final ModelPart cube_13;
	private final ModelPart cube_14;
	private final ModelPart cube_15;
	private final ModelPart cube_16;
	private final ModelPart cube_17;
	private final ModelPart cube_18;
	private final ModelPart cube_19;
	private final ModelPart cube_20;
	private final ModelPart cube_21;
	private final ModelPart cube_22;
	private final ModelPart cube_23;
	private final ModelPart bone41;
	private final ModelPart timeColumnFins3;
	private final ModelPart bone43;
	private final ModelPart bone44;
	private final ModelPart cube_24;
	private final ModelPart cube_25;
	private final ModelPart cube_26;
	private final ModelPart cube_27;
	private final ModelPart cube_28;
	private final ModelPart cube_29;
	private final ModelPart cube_30;
	private final ModelPart cube_31;
	private final ModelPart cube_32;
	private final ModelPart cube_33;
	private final ModelPart cube_34;
	private final ModelPart cube_35;
	private final ModelPart cube_36;
	private final ModelPart cube_37;
	private final ModelPart cube_38;
	private final ModelPart cube_39;
	private final ModelPart cube_40;
	private final ModelPart cube_41;
	private final ModelPart cube_42;
	private final ModelPart cube_43;
	private final ModelPart cube_44;
	private final ModelPart cube_45;
	private final ModelPart bone45;
	private final ModelPart timeColumnFins;
	private final ModelPart rotorFlap;
	private final ModelPart rotorFlap2;
	private final ModelPart rotorFlap3;
	private final ModelPart rotorFlap4;
	private final ModelPart rotorFlap5;
	private final ModelPart rotorFlap6;
	private final ModelPart rotorFlap7;
	private final ModelPart rotorFlap8;
	private final ModelPart rotorFlap9;
	private final ModelPart rotorFlap10;
	private final ModelPart rotorFlap11;
	private final ModelPart rotorFlap12;
	private final ModelPart rotorFlap13;
	private final ModelPart rotorFlap14;
	private final ModelPart rotorFlap15;
	private final ModelPart rotorFlap16;
	private final ModelPart rotorFlap17;
	private final ModelPart rotorFlap18;
	private final ModelPart rotorFlap19;
	private final ModelPart rotorFlap20;
	private final ModelPart rotorFlap21;
	private final ModelPart rotorFlap22;
	private final ModelPart rotorFlap23;
	private final ModelPart rotorFlap24;
	private final ModelPart rotorFlap25;
	private final ModelPart timeColumnFins4;
	private final ModelPart rotorFlap51;
	private final ModelPart rotorFlap52;
	private final ModelPart rotorFlap53;
	private final ModelPart rotorFlap54;
	private final ModelPart rotorFlap55;
	private final ModelPart rotorFlap56;
	private final ModelPart rotorFlap57;
	private final ModelPart rotorFlap58;
	private final ModelPart rotorFlap59;
	private final ModelPart rotorFlap60;
	private final ModelPart rotorFlap61;
	private final ModelPart rotorFlap62;
	private final ModelPart rotorFlap63;
	private final ModelPart rotorFlap64;
	private final ModelPart rotorFlap65;
	private final ModelPart rotorFlap66;
	private final ModelPart rotorFlap67;
	private final ModelPart rotorFlap68;
	private final ModelPart rotorFlap69;
	private final ModelPart rotorFlap70;
	private final ModelPart rotorFlap71;
	private final ModelPart rotorFlap72;
	private final ModelPart rotorFlap73;
	private final ModelPart rotorFlap74;
	private final ModelPart rotorFlap75;
	private final ModelPart timeColumnFins5;
	private final ModelPart rotorFlap26;
	private final ModelPart rotorFlap27;
	private final ModelPart rotorFlap28;
	private final ModelPart rotorFlap29;
	private final ModelPart rotorFlap30;
	private final ModelPart rotorFlap31;
	private final ModelPart rotorFlap32;
	private final ModelPart rotorFlap33;
	private final ModelPart rotorFlap34;
	private final ModelPart rotorFlap35;
	private final ModelPart rotorFlap36;
	private final ModelPart rotorFlap37;
	private final ModelPart rotorFlap38;
	private final ModelPart rotorFlap39;
	private final ModelPart rotorFlap40;
	private final ModelPart rotorFlap41;
	private final ModelPart rotorFlap42;
	private final ModelPart rotorFlap43;
	private final ModelPart rotorFlap44;
	private final ModelPart rotorFlap45;
	private final ModelPart rotorFlap46;
	private final ModelPart rotorFlap47;
	private final ModelPart rotorFlap48;
	private final ModelPart rotorFlap49;
	private final ModelPart rotorFlap50;
	private final ModelPart timeColumnStruts;
	private final ModelPart paddles;
	private final ModelPart bone64;
	private final ModelPart bone84;
	private final ModelPart controls;
	private final ModelPart telepathics;
	private final ModelPart positioning;

	public TakomakModel(ModelPart root) {
		this.root = root.getChild("root");
		this.bottom = this.root.getChild("bottom");
		this.hex = this.bottom.getChild("hex");
		this.BTopPanels2 = this.bottom.getChild("BTopPanels2");
		this.BTopPanels = this.BTopPanels2.getChild("BTopPanels");
		this.BTopBottomSeperators = this.BTopPanels2.getChild("BTopBottomSeperators");
		this.BBottomPanels2 = this.bottom.getChild("BBottomPanels2");
		this.BBottomPanels = this.BBottomPanels2.getChild("BBottomPanels");
		this.BBottomSeperators = this.BBottomPanels2.getChild("BBottomSeperators");
		this.emmissives = this.root.getChild("emmissives");
		this.RotorEmmisiveOne6 = this.emmissives.getChild("RotorEmmisiveOne6");
		this.bone69 = this.RotorEmmisiveOne6.getChild("bone69");
		this.bone70 = this.RotorEmmisiveOne6.getChild("bone70");
		this.bone71 = this.RotorEmmisiveOne6.getChild("bone71");
		this.bone72 = this.RotorEmmisiveOne6.getChild("bone72");
		this.bone73 = this.RotorEmmisiveOne6.getChild("bone73");
		this.bone74 = this.RotorEmmisiveOne6.getChild("bone74");
		this.rotorLightBeamWindow = this.emmissives.getChild("rotorLightBeamWindow");
		this.base = this.root.getChild("base");
		this.panels = this.base.getChild("panels");
		this.bottomPanels = this.panels.getChild("bottomPanels");
		this.bottompanel1 = this.bottomPanels.getChild("bottompanel1");
		this.bottompanel2 = this.bottomPanels.getChild("bottompanel2");
		this.bottompanel3 = this.bottomPanels.getChild("bottompanel3");
		this.bottompanel4 = this.bottomPanels.getChild("bottompanel4");
		this.bottompanel5 = this.bottomPanels.getChild("bottompanel5");
		this.bottomPanelMergers = this.bottomPanels.getChild("bottomPanelMergers");
		this.bottomMerger1 = this.bottomPanelMergers.getChild("bottomMerger1");
		this.middlePanels = this.panels.getChild("middlePanels");
		this.PanelBackFillers = this.middlePanels.getChild("PanelBackFillers");
		this.bone37 = this.PanelBackFillers.getChild("bone37");
		this.bone39 = this.PanelBackFillers.getChild("bone39");
		this.bone46 = this.PanelBackFillers.getChild("bone46");
		this.bone38 = this.PanelBackFillers.getChild("bone38");
		this.bone25 = this.middlePanels.getChild("bone25");
		this.bone42 = this.middlePanels.getChild("bone42");
		this.bone32 = this.bone42.getChild("bone32");
		this.bone54 = this.bone32.getChild("bone54");
		this.bone65 = this.bone54.getChild("bone65");
		this.bone40 = this.bone42.getChild("bone40");
		this.topPanels = this.panels.getChild("topPanels");
		this.topPanelMergers = this.topPanels.getChild("topPanelMergers");
		this.bone = this.topPanelMergers.getChild("bone");
		this.bone3 = this.topPanelMergers.getChild("bone3");
		this.bone4 = this.topPanelMergers.getChild("bone4");
		this.bone5 = this.topPanelMergers.getChild("bone5");
		this.bone6 = this.topPanelMergers.getChild("bone6");
		this.bone2 = this.topPanelMergers.getChild("bone2");
		this.panel1 = this.topPanels.getChild("panel1");
		this.joystick = this.panel1.getChild("joystick");
		this.bone77 = this.panel1.getChild("bone77");
		this.bone76 = this.panel1.getChild("bone76");
		this.bone75 = this.panel1.getChild("bone75");
		this.bone78 = this.bone75.getChild("bone78");
		this.panel1Controls = this.panel1.getChild("panel1Controls");
		this.doorControl = this.panel1Controls.getChild("doorControl");
		this.doorLight1 = this.doorControl.getChild("doorLight1");
		this.doorLight2 = this.doorControl.getChild("doorLight2");
		this.doorLight3 = this.doorControl.getChild("doorLight3");
		this.doorLight4 = this.doorControl.getChild("doorLight4");
		this.doorLight5 = this.doorControl.getChild("doorLight5");
		this.doorLight6 = this.doorControl.getChild("doorLight6");
		this.bone14 = this.doorControl.getChild("bone14");
		this.bone17 = this.bone14.getChild("bone17");
		this.doorControlKnob = this.bone17.getChild("doorControlKnob");
		this.doorControlLever = this.bone17.getChild("doorControlLever");
		this.panel1Misc = this.panel1.getChild("panel1Misc");
		this.panel1Tape = this.panel1Misc.getChild("panel1Tape");
		this.panel2 = this.topPanels.getChild("panel2");
		this.panel2panel = this.panel2.getChild("panel2panel");
		this.bone26 = this.panel2panel.getChild("bone26");
		this.bone27 = this.panel2panel.getChild("bone27");
		this.bone51 = this.panel2panel.getChild("bone51");
		this.panel2panelMisc = this.bone51.getChild("panel2panelMisc");
		this.panel2panelLight11 = this.panel2panelMisc.getChild("panel2panelLight11");
		this.bone12 = this.panel2panelMisc.getChild("bone12");
		this.panel3 = this.topPanels.getChild("panel3");
		this.bone16 = this.panel3.getChild("bone16");
		this.bone34 = this.bone16.getChild("bone34");
		this.bone35 = this.bone34.getChild("bone35");
		this.bone31 = this.bone35.getChild("bone31");
		this.bone28 = this.bone31.getChild("bone28");
		this.bone63 = this.bone28.getChild("bone63");
		this.bone29 = this.bone28.getChild("bone29");
		this.bone30 = this.bone28.getChild("bone30");
		this.bone67 = this.bone34.getChild("bone67");
		this.bone36 = this.bone16.getChild("bone36");
		this.panel4 = this.topPanels.getChild("panel4");
		this.panel4Lights = this.panel4.getChild("panel4Lights");
		this.panel4Light1 = this.panel4Lights.getChild("panel4Light1");
		this.panel4Light2 = this.panel4Lights.getChild("panel4Light2");
		this.panel4Controls = this.panel4.getChild("panel4Controls");
		this.panel4wire = this.panel4Controls.getChild("panel4wire");
		this.panel4PumpThings = this.panel4Controls.getChild("panel4PumpThings");
		this.panel4PumpThing1 = this.panel4PumpThings.getChild("panel4PumpThing1");
		this.panel4PumpThing2 = this.panel4PumpThings.getChild("panel4PumpThing2");
		this.coffeepot = this.panel4Controls.getChild("coffeepot");
		this.bone33 = this.panel4Controls.getChild("bone33");
		this.panel5 = this.topPanels.getChild("panel5");
		this.bone50 = this.panel5.getChild("bone50");
		this.doodads = this.bone50.getChild("doodads");
		this.bone7 = this.doodads.getChild("bone7");
		this.bone8 = this.doodads.getChild("bone8");
		this.bone20 = this.doodads.getChild("bone20");
		this.bone19 = this.doodads.getChild("bone19");
		this.bone9 = this.doodads.getChild("bone9");
		this.bone11 = this.doodads.getChild("bone11");
		this.bone18 = this.doodads.getChild("bone18");
		this.bone13 = this.doodads.getChild("bone13");
		this.bone66 = this.doodads.getChild("bone66");
		this.bone68 = this.doodads.getChild("bone68");
		this.panel6 = this.topPanels.getChild("panel6");
		this.bone47 = this.panel6.getChild("bone47");
		this.bone79 = this.bone47.getChild("bone79");
		this.bone83 = this.bone47.getChild("bone83");
		this.bone81 = this.bone47.getChild("bone81");
		this.bone80 = this.bone47.getChild("bone80");
		this.bone10 = this.panel6.getChild("bone10");
		this.bone48 = this.panel6.getChild("bone48");
		this.bone49 = this.panel6.getChild("bone49");
		this.rotor = this.base.getChild("rotor");
		this.rotorTop = this.rotor.getChild("rotorTop");
		this.bone53 = this.rotorTop.getChild("bone53");
		this.bone55 = this.rotorTop.getChild("bone55");
		this.bone52 = this.bone55.getChild("bone52");
		this.bone56 = this.rotorTop.getChild("bone56");
		this.bone57 = this.rotorTop.getChild("bone57");
		this.bone61 = this.bone57.getChild("bone61");
		this.bone58 = this.rotorTop.getChild("bone58");
		this.bone59 = this.rotorTop.getChild("bone59");
		this.bone60 = this.bone59.getChild("bone60");
		this.rotorBase = this.rotor.getChild("rotorBase");
		this.rotorglass = this.rotorBase.getChild("rotorglass");
		this.rotorBaseBit = this.rotorBase.getChild("rotorBaseBit");
		this.rotorBaseBit2 = this.rotorBase.getChild("rotorBaseBit2");
		this.rotorBaseBit3 = this.rotorBase.getChild("rotorBaseBit3");
		this.rotorBaseBit4 = this.rotorBase.getChild("rotorBaseBit4");
		this.timeColumn = this.rotor.getChild("timeColumn");
		this.timeColumnBit = this.timeColumn.getChild("timeColumnBit");
		this.timeColumnBit2 = this.timeColumn.getChild("timeColumnBit2");
		this.timeColumnFins1 = this.timeColumn.getChild("timeColumnFins1");
		this.bone15 = this.timeColumnFins1.getChild("bone15");
		this.bone21 = this.timeColumnFins1.getChild("bone21");
		this.cube_112 = this.timeColumnFins1.getChild("cube_112");
		this.cube_113 = this.timeColumnFins1.getChild("cube_113");
		this.cube_114 = this.timeColumnFins1.getChild("cube_114");
		this.cube_115 = this.timeColumnFins1.getChild("cube_115");
		this.cube_116 = this.timeColumnFins1.getChild("cube_116");
		this.cube_117 = this.timeColumnFins1.getChild("cube_117");
		this.cube_118 = this.timeColumnFins1.getChild("cube_118");
		this.cube_119 = this.timeColumnFins1.getChild("cube_119");
		this.cube_120 = this.timeColumnFins1.getChild("cube_120");
		this.cube_121 = this.timeColumnFins1.getChild("cube_121");
		this.cube_122 = this.timeColumnFins1.getChild("cube_122");
		this.cube_123 = this.timeColumnFins1.getChild("cube_123");
		this.cube_124 = this.timeColumnFins1.getChild("cube_124");
		this.cube_125 = this.timeColumnFins1.getChild("cube_125");
		this.cube_126 = this.timeColumnFins1.getChild("cube_126");
		this.cube_127 = this.timeColumnFins1.getChild("cube_127");
		this.cube_128 = this.timeColumnFins1.getChild("cube_128");
		this.cube_129 = this.timeColumnFins1.getChild("cube_129");
		this.cube_130 = this.timeColumnFins1.getChild("cube_130");
		this.cube_131 = this.timeColumnFins1.getChild("cube_131");
		this.cube_132 = this.timeColumnFins1.getChild("cube_132");
		this.cube_133 = this.timeColumnFins1.getChild("cube_133");
		this.bone22 = this.timeColumnFins1.getChild("bone22");
		this.timeColumnFins2 = this.timeColumn.getChild("timeColumnFins2");
		this.bone23 = this.timeColumnFins2.getChild("bone23");
		this.bone24 = this.timeColumnFins2.getChild("bone24");
		this.cube_2 = this.timeColumnFins2.getChild("cube_2");
		this.cube_3 = this.timeColumnFins2.getChild("cube_3");
		this.cube_4 = this.timeColumnFins2.getChild("cube_4");
		this.cube_5 = this.timeColumnFins2.getChild("cube_5");
		this.cube_6 = this.timeColumnFins2.getChild("cube_6");
		this.cube_7 = this.timeColumnFins2.getChild("cube_7");
		this.cube_8 = this.timeColumnFins2.getChild("cube_8");
		this.cube_9 = this.timeColumnFins2.getChild("cube_9");
		this.cube_10 = this.timeColumnFins2.getChild("cube_10");
		this.cube_11 = this.timeColumnFins2.getChild("cube_11");
		this.cube_12 = this.timeColumnFins2.getChild("cube_12");
		this.cube_13 = this.timeColumnFins2.getChild("cube_13");
		this.cube_14 = this.timeColumnFins2.getChild("cube_14");
		this.cube_15 = this.timeColumnFins2.getChild("cube_15");
		this.cube_16 = this.timeColumnFins2.getChild("cube_16");
		this.cube_17 = this.timeColumnFins2.getChild("cube_17");
		this.cube_18 = this.timeColumnFins2.getChild("cube_18");
		this.cube_19 = this.timeColumnFins2.getChild("cube_19");
		this.cube_20 = this.timeColumnFins2.getChild("cube_20");
		this.cube_21 = this.timeColumnFins2.getChild("cube_21");
		this.cube_22 = this.timeColumnFins2.getChild("cube_22");
		this.cube_23 = this.timeColumnFins2.getChild("cube_23");
		this.bone41 = this.timeColumnFins2.getChild("bone41");
		this.timeColumnFins3 = this.timeColumn.getChild("timeColumnFins3");
		this.bone43 = this.timeColumnFins3.getChild("bone43");
		this.bone44 = this.timeColumnFins3.getChild("bone44");
		this.cube_24 = this.timeColumnFins3.getChild("cube_24");
		this.cube_25 = this.timeColumnFins3.getChild("cube_25");
		this.cube_26 = this.timeColumnFins3.getChild("cube_26");
		this.cube_27 = this.timeColumnFins3.getChild("cube_27");
		this.cube_28 = this.timeColumnFins3.getChild("cube_28");
		this.cube_29 = this.timeColumnFins3.getChild("cube_29");
		this.cube_30 = this.timeColumnFins3.getChild("cube_30");
		this.cube_31 = this.timeColumnFins3.getChild("cube_31");
		this.cube_32 = this.timeColumnFins3.getChild("cube_32");
		this.cube_33 = this.timeColumnFins3.getChild("cube_33");
		this.cube_34 = this.timeColumnFins3.getChild("cube_34");
		this.cube_35 = this.timeColumnFins3.getChild("cube_35");
		this.cube_36 = this.timeColumnFins3.getChild("cube_36");
		this.cube_37 = this.timeColumnFins3.getChild("cube_37");
		this.cube_38 = this.timeColumnFins3.getChild("cube_38");
		this.cube_39 = this.timeColumnFins3.getChild("cube_39");
		this.cube_40 = this.timeColumnFins3.getChild("cube_40");
		this.cube_41 = this.timeColumnFins3.getChild("cube_41");
		this.cube_42 = this.timeColumnFins3.getChild("cube_42");
		this.cube_43 = this.timeColumnFins3.getChild("cube_43");
		this.cube_44 = this.timeColumnFins3.getChild("cube_44");
		this.cube_45 = this.timeColumnFins3.getChild("cube_45");
		this.bone45 = this.timeColumnFins3.getChild("bone45");
		this.timeColumnFins = this.timeColumn.getChild("timeColumnFins");
		this.rotorFlap = this.timeColumnFins.getChild("rotorFlap");
		this.rotorFlap2 = this.timeColumnFins.getChild("rotorFlap2");
		this.rotorFlap3 = this.timeColumnFins.getChild("rotorFlap3");
		this.rotorFlap4 = this.timeColumnFins.getChild("rotorFlap4");
		this.rotorFlap5 = this.timeColumnFins.getChild("rotorFlap5");
		this.rotorFlap6 = this.timeColumnFins.getChild("rotorFlap6");
		this.rotorFlap7 = this.timeColumnFins.getChild("rotorFlap7");
		this.rotorFlap8 = this.timeColumnFins.getChild("rotorFlap8");
		this.rotorFlap9 = this.timeColumnFins.getChild("rotorFlap9");
		this.rotorFlap10 = this.timeColumnFins.getChild("rotorFlap10");
		this.rotorFlap11 = this.timeColumnFins.getChild("rotorFlap11");
		this.rotorFlap12 = this.timeColumnFins.getChild("rotorFlap12");
		this.rotorFlap13 = this.timeColumnFins.getChild("rotorFlap13");
		this.rotorFlap14 = this.timeColumnFins.getChild("rotorFlap14");
		this.rotorFlap15 = this.timeColumnFins.getChild("rotorFlap15");
		this.rotorFlap16 = this.timeColumnFins.getChild("rotorFlap16");
		this.rotorFlap17 = this.timeColumnFins.getChild("rotorFlap17");
		this.rotorFlap18 = this.timeColumnFins.getChild("rotorFlap18");
		this.rotorFlap19 = this.timeColumnFins.getChild("rotorFlap19");
		this.rotorFlap20 = this.timeColumnFins.getChild("rotorFlap20");
		this.rotorFlap21 = this.timeColumnFins.getChild("rotorFlap21");
		this.rotorFlap22 = this.timeColumnFins.getChild("rotorFlap22");
		this.rotorFlap23 = this.timeColumnFins.getChild("rotorFlap23");
		this.rotorFlap24 = this.timeColumnFins.getChild("rotorFlap24");
		this.rotorFlap25 = this.timeColumnFins.getChild("rotorFlap25");
		this.timeColumnFins4 = this.timeColumn.getChild("timeColumnFins4");
		this.rotorFlap51 = this.timeColumnFins4.getChild("rotorFlap51");
		this.rotorFlap52 = this.timeColumnFins4.getChild("rotorFlap52");
		this.rotorFlap53 = this.timeColumnFins4.getChild("rotorFlap53");
		this.rotorFlap54 = this.timeColumnFins4.getChild("rotorFlap54");
		this.rotorFlap55 = this.timeColumnFins4.getChild("rotorFlap55");
		this.rotorFlap56 = this.timeColumnFins4.getChild("rotorFlap56");
		this.rotorFlap57 = this.timeColumnFins4.getChild("rotorFlap57");
		this.rotorFlap58 = this.timeColumnFins4.getChild("rotorFlap58");
		this.rotorFlap59 = this.timeColumnFins4.getChild("rotorFlap59");
		this.rotorFlap60 = this.timeColumnFins4.getChild("rotorFlap60");
		this.rotorFlap61 = this.timeColumnFins4.getChild("rotorFlap61");
		this.rotorFlap62 = this.timeColumnFins4.getChild("rotorFlap62");
		this.rotorFlap63 = this.timeColumnFins4.getChild("rotorFlap63");
		this.rotorFlap64 = this.timeColumnFins4.getChild("rotorFlap64");
		this.rotorFlap65 = this.timeColumnFins4.getChild("rotorFlap65");
		this.rotorFlap66 = this.timeColumnFins4.getChild("rotorFlap66");
		this.rotorFlap67 = this.timeColumnFins4.getChild("rotorFlap67");
		this.rotorFlap68 = this.timeColumnFins4.getChild("rotorFlap68");
		this.rotorFlap69 = this.timeColumnFins4.getChild("rotorFlap69");
		this.rotorFlap70 = this.timeColumnFins4.getChild("rotorFlap70");
		this.rotorFlap71 = this.timeColumnFins4.getChild("rotorFlap71");
		this.rotorFlap72 = this.timeColumnFins4.getChild("rotorFlap72");
		this.rotorFlap73 = this.timeColumnFins4.getChild("rotorFlap73");
		this.rotorFlap74 = this.timeColumnFins4.getChild("rotorFlap74");
		this.rotorFlap75 = this.timeColumnFins4.getChild("rotorFlap75");
		this.timeColumnFins5 = this.timeColumn.getChild("timeColumnFins5");
		this.rotorFlap26 = this.timeColumnFins5.getChild("rotorFlap26");
		this.rotorFlap27 = this.timeColumnFins5.getChild("rotorFlap27");
		this.rotorFlap28 = this.timeColumnFins5.getChild("rotorFlap28");
		this.rotorFlap29 = this.timeColumnFins5.getChild("rotorFlap29");
		this.rotorFlap30 = this.timeColumnFins5.getChild("rotorFlap30");
		this.rotorFlap31 = this.timeColumnFins5.getChild("rotorFlap31");
		this.rotorFlap32 = this.timeColumnFins5.getChild("rotorFlap32");
		this.rotorFlap33 = this.timeColumnFins5.getChild("rotorFlap33");
		this.rotorFlap34 = this.timeColumnFins5.getChild("rotorFlap34");
		this.rotorFlap35 = this.timeColumnFins5.getChild("rotorFlap35");
		this.rotorFlap36 = this.timeColumnFins5.getChild("rotorFlap36");
		this.rotorFlap37 = this.timeColumnFins5.getChild("rotorFlap37");
		this.rotorFlap38 = this.timeColumnFins5.getChild("rotorFlap38");
		this.rotorFlap39 = this.timeColumnFins5.getChild("rotorFlap39");
		this.rotorFlap40 = this.timeColumnFins5.getChild("rotorFlap40");
		this.rotorFlap41 = this.timeColumnFins5.getChild("rotorFlap41");
		this.rotorFlap42 = this.timeColumnFins5.getChild("rotorFlap42");
		this.rotorFlap43 = this.timeColumnFins5.getChild("rotorFlap43");
		this.rotorFlap44 = this.timeColumnFins5.getChild("rotorFlap44");
		this.rotorFlap45 = this.timeColumnFins5.getChild("rotorFlap45");
		this.rotorFlap46 = this.timeColumnFins5.getChild("rotorFlap46");
		this.rotorFlap47 = this.timeColumnFins5.getChild("rotorFlap47");
		this.rotorFlap48 = this.timeColumnFins5.getChild("rotorFlap48");
		this.rotorFlap49 = this.timeColumnFins5.getChild("rotorFlap49");
		this.rotorFlap50 = this.timeColumnFins5.getChild("rotorFlap50");
		this.timeColumnStruts = this.timeColumn.getChild("timeColumnStruts");
		this.paddles = this.rotor.getChild("paddles");
		this.bone64 = this.paddles.getChild("bone64");
		this.bone84 = this.paddles.getChild("bone84");
		this.controls = this.root.getChild("controls");
		this.telepathics = this.controls.getChild("telepathics");
		this.positioning = root.getChild("positioning");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition bottom = root.addOrReplaceChild("bottom", CubeListBuilder.create(),
				PartPose.offset(-0.0223F, -4.8537F, 0.4694F));

		PartDefinition hex = bottom.addOrReplaceChild("hex", CubeListBuilder.create().texOffs(18, 493).addBox(-2.5552F,
				-1.25F, 0.8746F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.9588F, 0.0973F, 3.0331F));

		PartDefinition hex_r1 = hex.addOrReplaceChild("hex_r1",
				CubeListBuilder.create().texOffs(18, 493).addBox(-0.0871F, -1.0F, 1.0228F, 3.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition hex_r2 = hex.addOrReplaceChild("hex_r2",
				CubeListBuilder.create().texOffs(14, 493).addBox(-0.3064F, -1.0F, 0.9021F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition hex_r3 = hex.addOrReplaceChild("hex_r3",
				CubeListBuilder.create().texOffs(14, 493).addBox(-2.6433F, -1.0F, 1.7097F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition hex_r4 = hex.addOrReplaceChild("hex_r4",
				CubeListBuilder.create().texOffs(18, 493).addBox(0.8314F, -1.0F, 1.5531F, 3.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.383F, -0.25F, -3.0226F, 0.0F, -0.7854F, 0.0F));

		PartDefinition hex_r5 = hex.addOrReplaceChild("hex_r5",
				CubeListBuilder.create().texOffs(14, 493).addBox(0.7451F, -1.0F, 1.0406F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.383F, -0.25F, -3.0226F, 0.0F, -1.1781F, 0.0F));

		PartDefinition hex_r6 = hex.addOrReplaceChild("hex_r6",
				CubeListBuilder.create().texOffs(18, 493).addBox(-1.5307F, -1.0F, 0.6001F, 3.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.383F, -0.25F, -3.0226F, 0.0F, -1.5708F, 0.0F));

		PartDefinition hex_r7 = hex.addOrReplaceChild("hex_r7",
				CubeListBuilder.create().texOffs(14, 493).addBox(-1.8018F, -1.0F, 1.064F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.383F, -0.25F, -3.0226F, -3.1416F, -1.1781F, -3.1416F));

		PartDefinition hex_r8 = hex.addOrReplaceChild("hex_r8",
				CubeListBuilder.create().texOffs(18, 493).addBox(1.3618F, -1.0F, 0.6345F, 3.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.3604F, -0.25F, -7.4056F, -3.1416F, -0.7854F, -3.1416F));

		PartDefinition hex_r9 = hex.addOrReplaceChild("hex_r9",
				CubeListBuilder.create().texOffs(14, 493).addBox(0.8836F, -1.0F, -0.011F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.3604F, -0.25F, -7.4056F, -3.1416F, -0.3927F, -3.1416F));

		PartDefinition hex_r10 = hex.addOrReplaceChild("hex_r10",
				CubeListBuilder.create().texOffs(18, 493).addBox(-1.8052F, -1.0F, -0.4244F, 3.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.3604F, -0.25F, -7.4056F, -3.1416F, 0.0F, -3.1416F));

		PartDefinition hex_r11 = hex.addOrReplaceChild("hex_r11",
				CubeListBuilder.create().texOffs(14, 493).addBox(-2.4475F, -1.0F, 0.2226F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.3604F, -0.25F, -7.4056F, -3.1416F, 0.3927F, -3.1416F));

		PartDefinition hex_r12 = hex.addOrReplaceChild("hex_r12",
				CubeListBuilder.create().texOffs(14, 493).addBox(-1.8038F, -1.0F, -0.827F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.0226F, -0.25F, -6.1536F, -3.1416F, 1.1781F, -3.1416F));

		PartDefinition hex_r13 = hex.addOrReplaceChild("hex_r13",
				CubeListBuilder.create().texOffs(18, 493).addBox(-0.8088F, -1.0F, -1.1478F, 3.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.0226F, -0.25F, -6.1536F, -3.1416F, 0.7854F, -3.1416F));

		PartDefinition hex_r14 = hex.addOrReplaceChild("hex_r14",
				CubeListBuilder.create().texOffs(18, 493).addBox(-1.1495F, -1.0F, -0.875F, 3.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.7477F, -0.25F, -2.7028F, 0.0F, 1.5708F, 0.0F));

		PartDefinition hex_r15 = hex.addOrReplaceChild("hex_r15",
				CubeListBuilder.create().texOffs(14, 493).addBox(-1.0674F, -3.8141F, -1.4574F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.321F, 2.5641F, -1.4405F, 0.0F, 1.1781F, 0.0F));

		PartDefinition BTopPanels2 = bottom.addOrReplaceChild("BTopPanels2", CubeListBuilder.create(),
				PartPose.offset(0.0F, -3.818F, 0.0F));

		PartDefinition BTopPanels = BTopPanels2.addOrReplaceChild("BTopPanels", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition hex_r16 = BTopPanels.addOrReplaceChild("hex_r16",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.3095F, 0.0F, 4.3095F, 0.3054F, 0.7854F, 0.0F));

		PartDefinition hex_r17 = BTopPanels.addOrReplaceChild("hex_r17",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 6.0946F, 0.3054F, 0.0F, 0.0F));

		PartDefinition hex_r18 = BTopPanels.addOrReplaceChild("hex_r18",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.3095F, 0.0F, 4.3095F, 0.3054F, -0.7854F, 0.0F));

		PartDefinition hex_r19 = BTopPanels.addOrReplaceChild("hex_r19",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.0946F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.3054F));

		PartDefinition hex_r20 = BTopPanels.addOrReplaceChild("hex_r20",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -6.0946F, -2.8362F, 0.0F, -3.1416F));

		PartDefinition hex_r21 = BTopPanels.addOrReplaceChild("hex_r21",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.3095F, 0.0F, -4.3095F, -2.8362F, 0.7854F, 3.1416F));

		PartDefinition hex_r22 = BTopPanels.addOrReplaceChild("hex_r22",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.3095F, 0.0F, -4.3095F, -2.8362F, -0.7854F, 3.1416F));

		PartDefinition hex_r23 = BTopPanels.addOrReplaceChild("hex_r23",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0954F, 0.0301F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.0946F, 0.0F, 0.0F, 0.0F, 1.5708F, -0.3054F));

		PartDefinition BTopBottomSeperators = BTopPanels2.addOrReplaceChild("BTopBottomSeperators",
				CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.0233F, -0.2804F, -0.0129F, 0.0F, 0.0F, -3.1416F));

		PartDefinition hex_r24 = BTopBottomSeperators.addOrReplaceChild("hex_r24",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(5.6598F, 0.0F, 2.3674F, 0.3054F, 1.1781F, 0.0F));

		PartDefinition hex_r25 = BTopBottomSeperators.addOrReplaceChild("hex_r25",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(2.328F, 0.0F, 5.6761F, 0.3054F, 0.3927F, 0.0F));

		PartDefinition hex_r26 = BTopBottomSeperators.addOrReplaceChild("hex_r26",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-2.3674F, 0.0F, 5.6598F, 0.3054F, -0.3927F, 0.0F));

		PartDefinition hex_r27 = BTopBottomSeperators.addOrReplaceChild("hex_r27",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-5.6761F, 0.0F, 2.328F, 0.3054F, -1.1781F, 0.0F));

		PartDefinition hex_r28 = BTopBottomSeperators.addOrReplaceChild("hex_r28",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-5.6598F, 0.0F, -2.3674F, -2.8362F, -1.1781F, -3.1416F));

		PartDefinition hex_r29 = BTopBottomSeperators.addOrReplaceChild("hex_r29",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(5.6761F, 0.0F, -2.328F, -2.8362F, 1.1781F, -3.1416F));

		PartDefinition hex_r30 = BTopBottomSeperators.addOrReplaceChild("hex_r30",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(2.3674F, 0.0F, -5.6598F, -2.8362F, 0.3927F, -3.1416F));

		PartDefinition hex_r31 = BTopBottomSeperators.addOrReplaceChild("hex_r31",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0954F, -0.4699F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-2.328F, 0.0F, -5.6761F, -2.8362F, -0.3927F, -3.1416F));

		PartDefinition BBottomPanels2 = bottom.addOrReplaceChild("BBottomPanels2", CubeListBuilder.create(),
				PartPose.offset(4.3095F, 4.432F, 4.3095F));

		PartDefinition BBottomPanels = BBottomPanels2.addOrReplaceChild("BBottomPanels", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hex_r32 = BBottomPanels.addOrReplaceChild("hex_r32",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.7854F, 0.0F));

		PartDefinition hex_r33 = BBottomPanels.addOrReplaceChild("hex_r33",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.3095F, 0.0F, 1.7851F, 0.3054F, 0.0F, 0.0F));

		PartDefinition hex_r34 = BBottomPanels.addOrReplaceChild("hex_r34",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.619F, 0.0F, 0.0F, 0.3054F, -0.7854F, 0.0F));

		PartDefinition hex_r35 = BBottomPanels.addOrReplaceChild("hex_r35",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.4041F, 0.0F, -4.3095F, 0.0F, -1.5708F, 0.3054F));

		PartDefinition hex_r36 = BBottomPanels.addOrReplaceChild("hex_r36",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.3095F, 0.0F, -10.4041F, -2.8362F, 0.0F, -3.1416F));

		PartDefinition hex_r37 = BBottomPanels.addOrReplaceChild("hex_r37",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -8.6191F, -2.8362F, 0.7854F, 3.1416F));

		PartDefinition hex_r38 = BBottomPanels.addOrReplaceChild("hex_r38",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.619F, 0.0F, -8.6191F, -2.8362F, -0.7854F, 3.1416F));

		PartDefinition hex_r39 = BBottomPanels.addOrReplaceChild("hex_r39",
				CubeListBuilder.create().texOffs(16, 478).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.7851F, 0.0F, -4.3095F, 0.0F, 1.5708F, -0.3054F));

		PartDefinition BBottomSeperators = BBottomPanels2.addOrReplaceChild("BBottomSeperators",
				CubeListBuilder.create(), PartPose.offset(-3.9578F, -6.057F, -4.972F));

		PartDefinition hex_r40 = BBottomSeperators.addOrReplaceChild("hex_r40",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(5.2848F, 6.2516F, 3.017F, 0.3054F, 1.1781F, 0.0F));

		PartDefinition hex_r41 = BBottomSeperators.addOrReplaceChild("hex_r41",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(1.953F, 6.2516F, 6.3256F, 0.3054F, 0.3927F, 0.0F));

		PartDefinition hex_r42 = BBottomSeperators.addOrReplaceChild("hex_r42",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-2.7424F, 6.2516F, 6.3093F, 0.3054F, -0.3927F, 0.0F));

		PartDefinition hex_r43 = BBottomSeperators.addOrReplaceChild("hex_r43",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-6.0511F, 6.2516F, 2.9775F, 0.3054F, -1.1781F, 0.0F));

		PartDefinition hex_r44 = BBottomSeperators.addOrReplaceChild("hex_r44",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-6.0348F, 6.2516F, -1.7179F, -2.8362F, -1.1781F, -3.1416F));

		PartDefinition hex_r45 = BBottomSeperators.addOrReplaceChild("hex_r45",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(5.3011F, 6.2516F, -1.6785F, -2.8362F, 1.1781F, -3.1416F));

		PartDefinition hex_r46 = BBottomSeperators.addOrReplaceChild("hex_r46",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(1.9924F, 6.2516F, -5.0103F, -2.8362F, 0.3927F, -3.1416F));

		PartDefinition hex_r47 = BBottomSeperators.addOrReplaceChild("hex_r47",
				CubeListBuilder.create().texOffs(60, 505).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F,
						new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-2.703F, 6.2516F, -5.0266F, -2.8362F, -0.3927F, -3.1416F));

		PartDefinition emmissives = root.addOrReplaceChild("emmissives", CubeListBuilder.create().texOffs(288, 179)
				.addBox(-0.6F, -47.76F, -0.1126F, 1.0F, 28.0F, 1.0F, new CubeDeformation(-0.25F)),
				PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition RotorEmmisiveOne6 = emmissives.addOrReplaceChild("RotorEmmisiveOne6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone69 = RotorEmmisiveOne6.addOrReplaceChild("bone69", CubeListBuilder.create(),
				PartPose.offset(-3.8612F, -21.4874F, 2.3948F));

		PartDefinition cube_r1 = bone69.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(107, 505)
						.addBox(0.2969F, 1.5454F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(105, 507)
						.addBox(-0.2031F, 1.6954F, -1.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.6987F, -2.816F, 1.4426F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition cube_r2 = bone69.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(15, 186).addBox(-1.6599F, -1.0726F, -0.8643F, 3.0F, 4.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.2795F, 1.101F, -0.8954F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition bone70 = RotorEmmisiveOne6.addOrReplaceChild("bone70", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-3.8612F, -21.4874F, 2.3948F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r3 = bone70.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(107, 505)
						.addBox(0.2969F, 1.5454F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(105, 507)
						.addBox(-0.2031F, 1.6954F, -1.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.4487F, -2.816F, 6.7908F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition cube_r4 = bone70.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(15, 186).addBox(-1.6599F, -1.0726F, -1.3643F, 3.0F, 4.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.5295F, 1.101F, 4.4528F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition bone71 = RotorEmmisiveOne6.addOrReplaceChild("bone71", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.3954F, -21.4874F, 4.8524F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r5 = bone71.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(107, 505)
						.addBox(0.2969F, 1.5454F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(105, 507)
						.addBox(-0.2031F, 1.6954F, -1.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.6987F, -2.816F, 6.3578F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition cube_r6 = bone71.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(15, 186).addBox(-1.6599F, -1.0726F, -0.8643F, 3.0F, 4.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.2795F, 1.101F, 4.0198F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition bone72 = RotorEmmisiveOne6.addOrReplaceChild("bone72", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.3954F, -21.4874F, 0.6024F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r7 = bone72.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(107, 505)
						.addBox(0.2969F, 1.5454F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(105, 507)
						.addBox(-0.2031F, 1.6954F, -1.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.7054F, -2.816F, 4.9984F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition cube_r8 = bone72.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(15, 186).addBox(-1.6599F, -1.0726F, -1.3643F, 3.0F, 4.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.7271F, 1.101F, 2.6603F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition bone73 = RotorEmmisiveOne6.addOrReplaceChild("bone73", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.3954F, -21.4874F, 0.6024F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r9 = bone73.addOrReplaceChild("cube_r9",
				CubeListBuilder.create().texOffs(107, 505)
						.addBox(0.2969F, 1.5454F, -1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(105, 507)
						.addBox(-0.2031F, 1.6954F, -2.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.0314F, -2.816F, 5.0988F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition cube_r10 = bone73.addOrReplaceChild("cube_r10",
				CubeListBuilder.create().texOffs(15, 186).addBox(-1.6599F, -1.0726F, -1.8643F, 3.0F, 4.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0531F, 1.101F, 2.7608F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition bone74 = RotorEmmisiveOne6.addOrReplaceChild("bone74", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.3954F, -21.4874F, 0.6024F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r11 = bone74.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(107, 505)
						.addBox(0.2969F, 1.5454F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(105, 507)
						.addBox(-0.2031F, 1.6954F, -2.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.7814F, -2.816F, 4.8667F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition cube_r12 = bone74.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(15, 186).addBox(-1.6599F, -1.0726F, -2.3643F, 3.0F, 4.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.8031F, 1.101F, 2.5287F, -0.2393F, 0.4703F, -0.4939F));

		PartDefinition rotorLightBeamWindow = emmissives
				.addOrReplaceChild(
						"rotorLightBeamWindow", CubeListBuilder.create().texOffs(284, 179).addBox(-0.5F, -14.0F, -0.5F,
								1.0F, 28.0F, 1.0F, new CubeDeformation(-0.15F)),
						PartPose.offset(-0.1F, -33.76F, 0.3874F));

		PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 1.25F, 0.25F, 0.0F, -1.0472F, 0.0F));

		PartDefinition panels = base.addOrReplaceChild("panels", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bottomPanels = panels.addOrReplaceChild("bottomPanels", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bottompanel1 = bottomPanels.addOrReplaceChild("bottompanel1", CubeListBuilder.create(),
				PartPose.offset(0.0F, -0.4561F, 0.0F));

		PartDefinition bottompanel2 = bottomPanels.addOrReplaceChild("bottompanel2", CubeListBuilder.create(),
				PartPose.offset(0.0F, -0.0361F, 0.0F));

		PartDefinition bottompanel3 = bottomPanels.addOrReplaceChild("bottompanel3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -0.0361F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bottompanel4 = bottomPanels.addOrReplaceChild("bottompanel4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -0.0361F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r13 = bottompanel4.addOrReplaceChild("cube_r13",
				CubeListBuilder.create().texOffs(0, 453).addBox(-8.1883F, -0.4747F, -4.1776F, 22.0F, 1.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.7948F, -11.2097F, -15.2375F, -0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r14 = bottompanel4.addOrReplaceChild("cube_r14",
				CubeListBuilder.create().texOffs(0, 453).addBox(-6.6883F, -0.4747F, -4.1776F, 22.0F, 1.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-15.0836F, -11.2097F, -4.0298F, -0.3491F, 1.0472F, 0.0F));

		PartDefinition cube_r15 = bottompanel4.addOrReplaceChild("cube_r15",
				CubeListBuilder.create().texOffs(0, 453).addBox(-6.6883F, -0.4747F, -4.1776F, 22.0F, 1.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.7719F, -11.2097F, 10.9175F, 2.7925F, 1.0472F, 3.1416F));

		PartDefinition cube_r16 = bottompanel4.addOrReplaceChild("cube_r16",
				CubeListBuilder.create().texOffs(66, 506).addBox(-11.343F, -0.6514F, -2.194F, 18.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.669F, -11.4664F, -0.2902F, 2.3126F, 1.0472F, 3.1416F));

		PartDefinition cube_r17 = bottompanel4.addOrReplaceChild("cube_r17",
				CubeListBuilder.create().texOffs(7, 506).addBox(-15.3429F, -0.6514F, -2.194F, 22.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.326F, -11.4664F, -7.8125F, -0.829F, 1.0472F, 0.0F));

		PartDefinition cube_r18 = bottompanel4.addOrReplaceChild("cube_r18",
				CubeListBuilder.create().texOffs(7, 506).addBox(-17.6826F, 16.2554F, 13.2717F, 22.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.6996F, -34.2909F, -5.7959F, -0.829F, 0.0F, 0.0F));

		PartDefinition cube_r19 = bottompanel4.addOrReplaceChild("cube_r19",
				CubeListBuilder.create().texOffs(14, 500).addBox(-11.0F, -4.5F, -0.5F, 18.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.9799F, -10.1152F, 5.4619F, 2.3126F, -1.0472F, 3.1416F));

		PartDefinition cube_r20 = bottompanel4.addOrReplaceChild("cube_r20",
				CubeListBuilder.create().texOffs(7, 506).addBox(-11.0F, -4.5F, -0.5F, 22.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(9.9799F, -10.1152F, -6.0423F, -0.829F, -1.0472F, 0.0F));

		PartDefinition cube_r21 = bottompanel4.addOrReplaceChild("cube_r21",
				CubeListBuilder.create().texOffs(0, 453).addBox(-6.6883F, -0.4747F, -4.1776F, 22.0F, 1.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(15.1175F, -11.2097F, 3.4493F, 2.7925F, -1.0472F, 3.1416F));

		PartDefinition cube_r22 = bottompanel4.addOrReplaceChild("cube_r22",
				CubeListBuilder.create().texOffs(0, 453).addBox(-10.61F, 0.1333F, -6.6067F, 22.0F, 1.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(10.6098F, -10.9503F, -6.8563F, -0.3491F, -1.0472F, 0.0F));

		PartDefinition bottompanel5 = bottomPanels.addOrReplaceChild("bottompanel5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -0.0361F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bottomPanelMergers = bottomPanels.addOrReplaceChild("bottomPanelMergers",
				CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bottomMerger1 = bottomPanelMergers.addOrReplaceChild("bottomMerger1", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition middlePanels = panels.addOrReplaceChild("middlePanels", CubeListBuilder.create().texOffs(0, 16)
				.addBox(-11.055F, -0.1737F, -19.1183F, 22.0F, 1.0F, 15.0F, new CubeDeformation(-0.01F)).texOffs(88, 78)
				.addBox(-11.266F, 0.3648F, -19.3301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)).texOffs(109, 62)
				.addBox(10.1565F, 0.3648F, -19.3298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)).texOffs(88, 78)
				.addBox(-11.266F, -2.3342F, -19.3301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)).texOffs(109, 62)
				.addBox(10.1565F, -2.3842F, -19.3298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)).texOffs(110, 87)
				.addBox(-11.055F, -1.9957F, -19.1183F, 22.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(109, 86)
				.addBox(-11.055F, -1.9957F, -14.1183F, 22.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(440, 496)
				.addBox(-10.552F, -1.0757F, -18.2293F, 21.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.2979F, -13.0074F, 0.2256F));

		PartDefinition cube_r23 = middlePanels.addOrReplaceChild("cube_r23",
				CubeListBuilder.create().texOffs(440, 496).addBox(-9.7624F, -13.06F, -17.3877F, 21.0F, 1.0F, 15.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.1127F, 11.9843F, 0.1966F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r24 = middlePanels.addOrReplaceChild("cube_r24",
				CubeListBuilder.create().texOffs(440, 496).addBox(-10.708F, -13.06F, -16.9491F, 21.0F, 1.0F, 15.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.2277F, 11.9843F, 0.3957F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r25 = middlePanels.addOrReplaceChild("cube_r25",
				CubeListBuilder.create().texOffs(440, 496).addBox(-11.5607F, -13.06F, -17.5488F, 21.0F, 1.0F, 15.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.1126F, 11.9843F, 0.5949F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r26 = middlePanels.addOrReplaceChild("cube_r26",
				CubeListBuilder.create().texOffs(439, 495).addBox(-13.9676F, -13.06F, -18.587F, 22.0F, 1.0F, 15.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.6327F, 11.9843F, 1.894F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r27 = middlePanels.addOrReplaceChild("cube_r27",
				CubeListBuilder.create().texOffs(440, 496).addBox(-10.522F, -13.06F, -19.0256F, 21.0F, 1.0F, 15.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7676F, 11.9843F, 0.3957F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r28 = middlePanels.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(109, 70)
				.addBox(-10.5738F, -13.475F, -14.3193F, 22.0F, 0.0F, 6.0F, new CubeDeformation(0.01F)).texOffs(110, 71)
				.addBox(-10.5738F, -13.475F, -19.3193F, 22.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
				.addBox(-10.5738F, -11.653F, -19.3193F, 22.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.0371F, 11.4793F, 0.4367F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r29 = middlePanels.addOrReplaceChild("cube_r29",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.3802F, 1.0146F, -2.1834F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(9.8222F, -3.3988F, -17.12F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r30 = middlePanels.addOrReplaceChild("cube_r30",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.3343F, 0.6147F, -2.2098F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-9.8858F, -2.9488F, -17.1468F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r31 = middlePanels.addOrReplaceChild("cube_r31",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.3802F, 0.1646F, -2.1833F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-19.763F, -2.5488F, -0.0925F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r32 = middlePanels.addOrReplaceChild("cube_r32",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.3343F, 0.1646F, -2.2098F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-19.763F, -2.5488F, -0.0925F, -3.1416F, 1.0472F, -3.1416F));

		PartDefinition cube_r33 = middlePanels.addOrReplaceChild("cube_r33",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.3343F, -0.2354F, -2.2098F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-9.9322F, -2.0988F, 16.9885F, -3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r34 = middlePanels.addOrReplaceChild("cube_r34",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.3802F, -0.2354F, -2.1834F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-9.9322F, -2.0988F, 16.9885F, 3.1416F, 1.0472F, -3.1416F));

		PartDefinition cube_r35 = middlePanels.addOrReplaceChild("cube_r35",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.3802F, -0.6853F, -2.1833F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(9.7758F, -1.6488F, 17.0152F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r36 = middlePanels.addOrReplaceChild("cube_r36",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.3343F, -0.6853F, -2.2098F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(9.7758F, -1.6488F, 17.0152F, -3.1416F, -1.0472F, -3.1416F));

		PartDefinition cube_r37 = middlePanels.addOrReplaceChild("cube_r37",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.7498F, -2.3653F, -2.9295F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(9.3608F, 2.7302F, -16.4268F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r38 = middlePanels.addOrReplaceChild("cube_r38",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.7957F, -2.1153F, -2.9031F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-9.5161F, 2.4802F, -16.4006F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r39 = middlePanels.addOrReplaceChild("cube_r39",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.7498F, -1.8653F, -2.9295F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-18.9319F, 2.2302F, -0.0396F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r40 = middlePanels.addOrReplaceChild("cube_r40",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.7957F, -1.8653F, -2.9031F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-18.9319F, 2.2302F, -0.0396F, -3.1416F, 1.0472F, -3.1416F));

		PartDefinition cube_r41 = middlePanels.addOrReplaceChild("cube_r41",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.7498F, -1.6153F, -2.9295F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-9.4708F, 1.9802F, 16.2952F, 3.1416F, 1.0472F, 3.1416F));

		PartDefinition cube_r42 = middlePanels.addOrReplaceChild("cube_r42",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.7957F, -1.6153F, -2.9031F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(-9.4708F, 1.9802F, 16.2952F, -3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r43 = middlePanels.addOrReplaceChild("cube_r43",
				CubeListBuilder.create().texOffs(109, 62).addBox(0.7957F, -1.3653F, -2.903F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(9.4067F, 1.7302F, 16.2687F, -3.1416F, -1.0472F, -3.1416F));

		PartDefinition cube_r44 = middlePanels.addOrReplaceChild("cube_r44",
				CubeListBuilder.create().texOffs(88, 78).addBox(-1.7498F, -1.3653F, -2.9295F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(9.4067F, 1.7302F, 16.2687F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r45 = middlePanels.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(88, 78)
				.addBox(-10.5738F, -14.975F, -19.3193F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)).texOffs(88, 78)
				.addBox(-10.5738F, -17.6582F, -19.3193F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(0.216F, 15.324F, -0.645F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r46 = middlePanels.addOrReplaceChild("cube_r46",
				CubeListBuilder.create().texOffs(109, 62)
						.addBox(-0.5F, -27.45F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)).texOffs(109, 62)
						.addBox(-0.5F, -30.1332F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2115F)),
				PartPose.offsetAndRotation(21.5509F, 27.799F, -0.1714F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r47 = middlePanels.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(109, 102)
				.addBox(-10.6462F, -13.475F, -14.3193F, 22.0F, 0.0F, 6.0F, new CubeDeformation(0.01F)).texOffs(110, 103)
				.addBox(-10.6462F, -13.475F, -19.3192F, 22.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
				.addBox(-10.6462F, -11.653F, -19.3192F, 22.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4629F, 11.4793F, -0.2388F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r48 = middlePanels.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(109, 118)
				.addBox(-10.5738F, -13.475F, -14.3193F, 22.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(110, 119)
				.addBox(-10.5738F, -13.475F, -19.3193F, 22.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.0729F, 11.4793F, -0.5682F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r49 = middlePanels.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(109, 134)
				.addBox(-10.5376F, -13.475F, -14.3819F, 22.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(110, 135)
				.addBox(-10.5376F, -13.475F, -19.3819F, 22.0F, 1.0F, 5.0F, new CubeDeformation(-0.01F))
				.texOffs(100, 125).addBox(-10.5376F, -13.475F, -19.3819F, 22.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-10.5376F, -11.653F, -19.3819F, 22.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.4073F, 11.4793F, -0.3951F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r50 = middlePanels.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(109, 134)
				.addBox(-10.6462F, -13.475F, -14.3193F, 22.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(110, 135)
				.addBox(-10.6462F, -13.475F, -19.3193F, 22.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 64)
				.addBox(-10.6462F, -11.653F, -19.3193F, 22.0F, 1.0F, 15.0F, new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(0.3529F, 11.4793F, 0.1073F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r51 = middlePanels.addOrReplaceChild("cube_r51",
				CubeListBuilder.create().texOffs(0, 32).addBox(-12.0738F, -12.725F, -19.3193F, 22.0F, 1.0F, 15.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-0.8229F, 12.5513F, 0.7308F, 0.0F, -2.0944F, 0.0F));

		PartDefinition PanelBackFillers = middlePanels.addOrReplaceChild("PanelBackFillers", CubeListBuilder.create(),
				PartPose.offset(0.4073F, 11.2793F, -0.3951F));

		PartDefinition bone37 = PanelBackFillers.addOrReplaceChild("bone37", CubeListBuilder.create(),
				PartPose.offset(2.0099F, 0.2F, -5.7357F));

		PartDefinition cube_r52 = bone37.addOrReplaceChild("cube_r52",
				CubeListBuilder.create().texOffs(0, 297).addBox(-1.5377F, -13.575F, -14.6319F, 16.0F, 0.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0873F, 0.0F, 0.2769F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone39 = PanelBackFillers.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(71, 304)
				.addBox(-14.6585F, -13.675F, -12.8668F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(39, 322)
				.addBox(-14.6585F, -13.925F, -12.8668F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0099F, 0.2F, -5.7357F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r53 = bone39.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(0, 291)
				.addBox(-1.5377F, -13.575F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(39, 310)
				.addBox(-1.5377F, -13.925F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(71, 310)
				.addBox(-1.5377F, -13.675F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0989F, 0.0F, -4.9947F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r54 = bone39.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(39, 292)
				.addBox(-1.5377F, -13.725F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(71, 298)
				.addBox(-1.5377F, -13.475F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-9.2181F, -0.2F, 6.9739F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r55 = bone39.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(71, 316)
				.addBox(-1.5377F, -13.475F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(39, 316)
				.addBox(-1.5377F, -13.725F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.7557F, -0.2F, 6.1984F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r56 = bone39.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(71, 322)
				.addBox(-1.5377F, -13.475F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(39, 304)
				.addBox(-1.5377F, -13.725F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1962F, -0.2F, 0.2141F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone46 = PanelBackFillers.addOrReplaceChild("bone46", CubeListBuilder.create(),
				PartPose.offsetAndRotation(2.0099F, 0.2F, -5.7357F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r57 = bone46.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(39, 298)
				.addBox(-1.5377F, -13.725F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 273)
				.addBox(-1.5377F, -13.375F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(71, 292)
				.addBox(-1.5377F, -13.475F, -14.6319F, 16.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.6267F, -0.2F, -11.2559F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r58 = bone46.addOrReplaceChild("cube_r58",
				CubeListBuilder.create().texOffs(-1, 279).addBox(-2.5377F, -13.575F, -14.6319F, 17.0F, 0.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-8.089F, 0.0F, -10.4804F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone38 = PanelBackFillers.addOrReplaceChild("bone38", CubeListBuilder.create(),
				PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r59 = bone38.addOrReplaceChild(
				"cube_r59", CubeListBuilder.create().texOffs(0, 285).addBox(-7.5377F, -12.575F, -14.3819F, 19.0F, 0.0F,
						6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.2F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone25 = middlePanels.addOrReplaceChild("bone25", CubeListBuilder.create(),
				PartPose.offset(-6.6385F, -2.1957F, 10.9118F));

		PartDefinition cube_r60 = bone25.addOrReplaceChild("cube_r60",
				CubeListBuilder.create().texOffs(14, 309).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, -0.3F, 3.1416F, -0.7854F, -3.1416F));

		PartDefinition cube_r61 = bone25.addOrReplaceChild("cube_r61",
				CubeListBuilder.create().texOffs(14, 309).addBox(3.5F, -1.4F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.6F, 0.4F, -1.0071F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r62 = bone25.addOrReplaceChild("cube_r62",
				CubeListBuilder.create().texOffs(14, 309).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.1F, 0.0F, -0.3F, 3.1416F, -0.7854F, -3.1416F));

		PartDefinition cube_r63 = bone25.addOrReplaceChild("cube_r63",
				CubeListBuilder.create().texOffs(14, 309).addBox(3.5F, -1.4F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.3071F, 0.4F, -0.3F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r64 = bone25.addOrReplaceChild("cube_r64",
				CubeListBuilder.create().texOffs(14, 309).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.1F, 0.0F, 0.7F, 3.1416F, -0.7854F, -3.1416F));

		PartDefinition cube_r65 = bone25.addOrReplaceChild("cube_r65",
				CubeListBuilder.create().texOffs(14, 309).addBox(3.5F, -1.4F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.6F, 0.4F, 0.4071F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r66 = bone25.addOrReplaceChild("cube_r66",
				CubeListBuilder.create().texOffs(14, 309).addBox(3.5F, -1.4F, -1.0F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.8929F, 0.4F, -0.3F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r67 = bone25.addOrReplaceChild("cube_r67",
				CubeListBuilder.create().texOffs(14, 309).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, 0.7F, 3.1416F, -0.7854F, -3.1416F));

		PartDefinition cube_r68 = bone25.addOrReplaceChild("cube_r68",
				CubeListBuilder.create().texOffs(4, 307).addBox(-1.0F, -0.4569F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.6F, -0.35F, 0.2157F, 3.1416F, 0.7854F, -3.1416F));

		PartDefinition cube_r69 = bone25.addOrReplaceChild("cube_r69",
				CubeListBuilder.create().texOffs(4, 307).addBox(3.0F, -0.2569F, -0.95F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(4.6F, -0.55F, 0.2657F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r70 = bone25.addOrReplaceChild("cube_r70",
				CubeListBuilder.create().texOffs(3, 315).addBox(3.3F, -4.2F, -1.85F, 3.0F, 3.1969F, 3.0F,
						new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(5.4F, 0.6F, -0.1157F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition bone42 = middlePanels.addOrReplaceChild("bone42", CubeListBuilder.create(),
				PartPose.offset(-9.3663F, -3.5661F, 0.1099F));

		PartDefinition bone32 = bone42.addOrReplaceChild("bone32", CubeListBuilder.create(),
				PartPose.offset(9.183F, -0.6989F, 0.0966F));

		PartDefinition bone54 = bone32.addOrReplaceChild("bone54", CubeListBuilder.create(),
				PartPose.offset(0.0217F, 0.2176F, -0.039F));

		PartDefinition cube_r71 = bone54.addOrReplaceChild("cube_r71",
				CubeListBuilder.create().texOffs(320, 14).addBox(-0.5317F, 2.1803F, -11.0731F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(-0.09F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7741F, 0.5239F, 3.1164F));

		PartDefinition cube_r72 = bone54.addOrReplaceChild("cube_r72",
				CubeListBuilder.create().texOffs(320, 14).addBox(-0.4192F, 2.1726F, -11.0529F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(-0.09F)),
				PartPose.offsetAndRotation(0.0229F, 0.0F, 0.0384F, 1.197F, 1.549F, 1.5519F));

		PartDefinition cube_r73 = bone54.addOrReplaceChild("cube_r73",
				CubeListBuilder.create().texOffs(327, 1).addBox(0.0408F, -0.8554F, -10.301F, 0.0F, 1.0F, 6.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.0217F, -1.0007F, 0.039F, -3.1164F, -0.5239F, -3.1164F));

		PartDefinition cube_r74 = bone54.addOrReplaceChild("cube_r74",
				CubeListBuilder.create().texOffs(320, 14).addBox(-0.4161F, 2.1659F, -11.1009F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(-0.09F)),
				PartPose.offsetAndRotation(-0.043F, -0.0003F, -0.0002F, 2.7725F, -0.5242F, -3.1125F));

		PartDefinition cube_r75 = bone54.addOrReplaceChild("cube_r75",
				CubeListBuilder.create().texOffs(320, 14).addBox(-0.4942F, 2.1871F, -11.0937F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(-0.09F)),
				PartPose.offsetAndRotation(-0.0664F, 0.0F, 0.0397F, -1.9446F, -1.549F, 1.5897F));

		PartDefinition cube_r76 = bone54.addOrReplaceChild("cube_r76",
				CubeListBuilder.create().texOffs(320, 14).addBox(-0.4817F, 2.1793F, -11.0735F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(-0.09F)),
				PartPose.offsetAndRotation(-0.0435F, 0.0F, 0.0781F, -0.3675F, -0.5239F, 0.0252F));

		PartDefinition cube_r77 = bone54.addOrReplaceChild("cube_r77",
				CubeListBuilder.create().texOffs(320, 14).addBox(-0.3942F, 2.1721F, -10.9531F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(-0.09F)),
				PartPose.offsetAndRotation(0.0012F, 0.0F, 0.0774F, -0.3423F, 0.523F, 0.0252F));

		PartDefinition cube_r78 = bone54.addOrReplaceChild("cube_r78",
				CubeListBuilder.create().texOffs(327, 1).addBox(0.1381F, -0.8337F, -10.2581F, 0.0F, 1.0F, 6.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.0217F, -1.0007F, 0.039F, 0.0504F, 0.523F, 0.0252F));

		PartDefinition cube_r79 = bone54.addOrReplaceChild("cube_r79",
				CubeListBuilder.create().texOffs(327, 1).addBox(0.0506F, -0.8348F, -10.2797F, 0.0F, 1.0F, 6.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.0217F, -1.0007F, 0.039F, 0.0252F, -0.5239F, 0.0252F));

		PartDefinition cube_r80 = bone54.addOrReplaceChild("cube_r80",
				CubeListBuilder.create().texOffs(327, 1).addBox(0.0381F, -0.8353F, -10.3013F, 0.0F, 1.0F, 6.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.0217F, -1.0007F, 0.039F, -1.5519F, -1.549F, 1.5897F));

		PartDefinition cube_r81 = bone54.addOrReplaceChild("cube_r81",
				CubeListBuilder.create().texOffs(327, 1).addBox(0.0007F, -0.8343F, -10.3168F, 0.0F, 1.0F, 6.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.0217F, -1.0007F, 0.039F, -3.1164F, 0.5239F, 3.1164F));

		PartDefinition cube_r82 = bone54.addOrReplaceChild("cube_r82",
				CubeListBuilder.create().texOffs(327, 1).addBox(0.1132F, -0.8331F, -10.2581F, 0.0F, 1.0F, 6.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.0217F, -1.0007F, 0.039F, 1.5897F, 1.549F, 1.5519F));

		PartDefinition bone65 = bone54.addOrReplaceChild("bone65",
				CubeListBuilder.create().texOffs(160, 0).addBox(4.1466F, -0.1F, -2.9054F, 1.0F, 1.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1399F, -1.6402F, -0.1589F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r83 = bone65.addOrReplaceChild("cube_r83",
				CubeListBuilder.create().texOffs(160, 0).addBox(3.8288F, -1.4226F, -3.2884F, 1.0F, 1.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1156F, 1.3226F, 0.5569F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r84 = bone65.addOrReplaceChild("cube_r84",
				CubeListBuilder.create().texOffs(160, 0).addBox(3.8288F, -1.4226F, -3.2884F, 1.0F, 1.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4829F, 1.3226F, 0.2685F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r85 = bone65.addOrReplaceChild("cube_r85",
				CubeListBuilder.create().texOffs(160, 0).addBox(3.8288F, -1.4226F, -3.2884F, 1.0F, 1.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4168F, 1.3226F, -0.1938F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r86 = bone65.addOrReplaceChild("cube_r86",
				CubeListBuilder.create().texOffs(160, 0).addBox(3.8288F, -1.4226F, -3.2884F, 1.0F, 1.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0166F, 1.3226F, -0.3677F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r87 = bone65.addOrReplaceChild("cube_r87",
				CubeListBuilder.create().texOffs(160, 0).addBox(3.8288F, -1.4226F, -3.2884F, 1.0F, 1.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.3839F, 1.3226F, -0.0793F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone40 = bone42.addOrReplaceChild("bone40", CubeListBuilder.create(),
				PartPose.offset(2.6639F, 0.1205F, 3.8854F));

		PartDefinition cube_r88 = bone40.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(263, 6)
				.addBox(-6.125F, -0.9306F, -3.2911F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(268, 9)
				.addBox(-2.825F, -0.9768F, -2.8103F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.4204F, 0.1854F, -0.3871F, 2.8362F, 1.0472F, 3.1416F));

		PartDefinition cube_r89 = bone40.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(263, 6)
				.addBox(-6.125F, -0.9306F, -3.2911F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(268, 9)
				.addBox(-2.775F, -0.9768F, -2.8103F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.4375F, 0.153F, 3.1796F, 2.8362F, 0.0F, 3.1416F));

		PartDefinition cube_r90 = bone40.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(263, 6)
				.addBox(-6.125F, -0.9306F, -3.2911F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(268, 9)
				.addBox(-2.775F, -0.9768F, -2.8103F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(12.5147F, 0.1527F, -0.2098F, 2.8362F, -1.0472F, 3.1416F));

		PartDefinition cube_r91 = bone40.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(263, 6)
				.addBox(-5.975F, -0.9306F, -3.2911F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(268, 9)
				.addBox(-2.825F, -0.9768F, -2.8103F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.694F, 0.153F, -10.7322F, -0.3054F, 0.0F, 0.0F));

		PartDefinition cube_r92 = bone40.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(263, 6)
				.addBox(-6.125F, -0.9306F, -3.2911F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(268, 9)
				.addBox(-2.875F, -0.9768F, -2.8103F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(12.6163F, 0.153F, -7.1648F, -0.3054F, -1.0472F, 0.0F));

		PartDefinition topPanels = panels.addOrReplaceChild("topPanels", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition topPanelMergers = topPanels.addOrReplaceChild("topPanelMergers", CubeListBuilder.create(),
				PartPose.offset(0.0F, -14.07F, 0.0F));

		PartDefinition bone = topPanelMergers.addOrReplaceChild("bone", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = topPanelMergers.addOrReplaceChild("bone3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.065F, 0.0F, 0.1172F, 0.0F, -1.0647F, 0.0F));

		PartDefinition bone4 = topPanelMergers.addOrReplaceChild("bone4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.071F, 0.0F, -0.1136F, 0.0F, -2.1293F, 0.0F));

		PartDefinition bone5 = topPanelMergers.addOrReplaceChild("bone5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.5358F, 0.0F, 0.014F, 0.0F, 3.1154F, 0.0F));

		PartDefinition bone6 = topPanelMergers.addOrReplaceChild("bone6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.2969F, 0.0F, 0.5248F, 0.0F, 2.0857F, 0.0F));

		PartDefinition bone2 = topPanelMergers.addOrReplaceChild("bone2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.165F, 0.0F, 0.2916F, 0.0F, 1.0559F, 0.0F));

		PartDefinition panel1 = topPanels.addOrReplaceChild("panel1", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r93 = panel1.addOrReplaceChild("cube_r93",
				CubeListBuilder.create().texOffs(76, 153).addBox(-8.955F, -1.8572F, -1.7186F, 18.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.0984F, -13.5338F, -7.0947F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r94 = panel1.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(102, 239)
				.addBox(0.5F, -1.0474F, -0.8331F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-0.5F, -1.0474F, -0.8331F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-1.5F, -1.0474F, -0.8331F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-2.5F, -1.0474F, -0.8331F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)),
				PartPose.offsetAndRotation(-16.577F, -15.8995F, -4.3523F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r95 = panel1.addOrReplaceChild("cube_r95",
				CubeListBuilder.create().texOffs(38, 228).addBox(-10.945F, -2.9292F, -6.4086F, 22.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.7144F, -14.1366F, -6.142F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition joystick = panel1.addOrReplaceChild("joystick", CubeListBuilder.create(),
				PartPose.offset(-14.4126F, -14.7859F, -10.1212F));

		PartDefinition cube_r96 = joystick.addOrReplaceChild("cube_r96",
				CubeListBuilder.create().texOffs(180, 202).addBox(0.0142F, -2.2279F, -0.167F, 1.0F, 2.0F, 1.0F,
						new CubeDeformation(-0.45F)),
				PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.1719F, -0.4971F, -0.3492F));

		PartDefinition cube_r97 = joystick.addOrReplaceChild("cube_r97",
				CubeListBuilder.create().texOffs(204, 155).addBox(0.0812F, -1.9179F, -0.234F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(-0.1826F, -0.5732F, -0.0281F, 0.1719F, -0.4971F, -0.3492F));

		PartDefinition bone77 = panel1.addOrReplaceChild("bone77", CubeListBuilder.create(),
				PartPose.offset(-9.3392F, -13.9337F, -8.893F));

		PartDefinition cube_r98 = bone77.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(158, 211)
				.addBox(-11.0512F, -3.1259F, -6.1716F, 16.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(158, 206)
				.addBox(-11.0512F, -3.1998F, -6.1641F, 16.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(163, 222)
				.addBox(-9.8012F, -3.2472F, -3.7972F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(163, 221)
				.addBox(-9.8012F, -3.2722F, -3.9972F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(158, 216)
				.addBox(-11.0512F, -3.2998F, -6.1641F, 16.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(158, 227)
				.addBox(-11.0512F, -3.1998F, -6.1641F, 16.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone76 = panel1.addOrReplaceChild("bone76", CubeListBuilder.create(),
				PartPose.offset(-15.8354F, -15.5175F, -7.3882F));

		PartDefinition cube_r99 = bone76.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(175, 224)
				.addBox(-5.9F, -0.5238F, -2.2575F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(175, 224)
				.addBox(-1.7F, -0.5238F, -2.2575F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0F, 0.05F, 0.0F, 2.8362F, -1.0472F, -3.1416F));

		PartDefinition cube_r100 = bone76.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(175, 224)
				.addBox(0.5488F, -3.4998F, -5.2641F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(175, 224)
				.addBox(-5.7512F, -3.4998F, -5.3641F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(6.4961F, 1.6338F, -1.5048F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r101 = bone76.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(167, 224)
				.addBox(-5.9F, -0.5238F, -2.2575F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(167, 224)
				.addBox(-1.7F, -0.5238F, -2.2575F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.8362F, -1.0472F, -3.1416F));

		PartDefinition cube_r102 = bone76.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(167, 224)
				.addBox(0.5488F, -3.4998F, -5.2641F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(167, 224)
				.addBox(-5.7512F, -3.4998F, -5.3641F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(6.4961F, 1.5838F, -1.5048F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone75 = panel1.addOrReplaceChild("bone75", CubeListBuilder.create(),
				PartPose.offset(-11.5384F, -13.8432F, -6.1674F));

		PartDefinition cube_r103 = bone75.addOrReplaceChild("cube_r103",
				CubeListBuilder.create().texOffs(155, 157).addBox(3.51F, -2.5161F, -4.1716F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(1.8704F, -1.0142F, -1.69F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r104 = bone75.addOrReplaceChild("cube_r104",
				CubeListBuilder.create().texOffs(149, 157).addBox(2.51F, -4.1161F, -5.1716F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(-1.5F)),
				PartPose.offsetAndRotation(2.5334F, -1.1645F, -1.8846F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone78 = bone75.addOrReplaceChild("bone78",
				CubeListBuilder.create().texOffs(139, 159)
						.addBox(-0.5F, -0.65F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(139, 159)
						.addBox(-0.5F, -0.95F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(139, 159)
						.addBox(-0.5F, -0.95F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(139, 159)
						.addBox(-0.5F, -0.95F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(139, 159)
						.addBox(-0.5F, -0.95F, -0.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)),
				PartPose.offsetAndRotation(2.5007F, -3.3677F, -5.8006F, 0.2393F, -0.4703F, -0.4939F));

		PartDefinition panel1Controls = panel1.addOrReplaceChild("panel1Controls", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition doorControl = panel1Controls.addOrReplaceChild("doorControl", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.335F, 0.0F));

		PartDefinition doorLight1 = doorControl.addOrReplaceChild("doorLight1", CubeListBuilder.create(),
				PartPose.offset(-10.1541F, -15.611F, -5.8625F));

		PartDefinition doorLight2 = doorControl.addOrReplaceChild("doorLight2", CubeListBuilder.create(),
				PartPose.offset(-10.1541F, -15.611F, -5.8625F));

		PartDefinition doorLight3 = doorControl.addOrReplaceChild("doorLight3", CubeListBuilder.create(),
				PartPose.offset(-10.1541F, -15.611F, -5.8625F));

		PartDefinition doorLight4 = doorControl.addOrReplaceChild("doorLight4", CubeListBuilder.create(),
				PartPose.offset(-10.1541F, -15.611F, -5.8625F));

		PartDefinition doorLight5 = doorControl.addOrReplaceChild("doorLight5", CubeListBuilder.create(),
				PartPose.offset(-10.1541F, -15.611F, -5.8625F));

		PartDefinition doorLight6 = doorControl.addOrReplaceChild("doorLight6", CubeListBuilder.create(),
				PartPose.offset(-10.1541F, -15.611F, -5.8625F));

		PartDefinition bone14 = doorControl.addOrReplaceChild("bone14", CubeListBuilder.create(),
				PartPose.offset(-11.7357F, -16.1141F, -2.6753F));

		PartDefinition bone17 = bone14.addOrReplaceChild("bone17", CubeListBuilder.create(),
				PartPose.offset(2.924F, -1.3859F, -3.6824F));

		PartDefinition cube_r105 = bone17.addOrReplaceChild("cube_r105",
				CubeListBuilder.create().texOffs(154, 204).addBox(-1.0166F, -1.5583F, -0.17F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.15F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4024F, -0.3446F, -0.9F));

		PartDefinition cube_r106 = bone17.addOrReplaceChild("cube_r106",
				CubeListBuilder.create().texOffs(147, 228).addBox(-0.0938F, -2.3275F, -2.9315F, 3.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.7657F, 2.1282F, 0.4534F, 0.4024F, -0.3446F, -0.9F));

		PartDefinition cube_r107 = bone17.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(0, 381)
				.addBox(-2.7725F, -0.3628F, -4.6969F, 3.0F, 0.0F, 5.0F, new CubeDeformation(-0.2F)).texOffs(0, 389)
				.addBox(-2.5225F, -0.2378F, -4.4469F, 3.0F, 3.0F, 5.0F, new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.6863F, 0.4335F, -2.2471F, 2.7392F, 0.3446F, 2.2416F));

		PartDefinition cube_r108 = bone17.addOrReplaceChild("cube_r108",
				CubeListBuilder.create().texOffs(76, 183).addBox(-1.5F, 0.5F, -1.1F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.809F, -0.8805F, 2.8169F, 0.4024F, -0.3446F, -0.9F));

		PartDefinition doorControlKnob = bone17.addOrReplaceChild("doorControlKnob", CubeListBuilder.create(),
				PartPose.offset(-0.876F, 0.4188F, -0.3914F));

		PartDefinition cube_r109 = doorControlKnob.addOrReplaceChild("cube_r109",
				CubeListBuilder.create().texOffs(100, 193).addBox(-0.2314F, -1.1878F, -0.25F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.4024F, -0.3446F, -0.9F));

		PartDefinition doorControlLever = bone17.addOrReplaceChild("doorControlLever", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-1.9908F, 0.4725F, 3.3464F, -0.0503F, -0.5214F, 0.1007F));

		PartDefinition cube_r110 = doorControlLever.addOrReplaceChild("cube_r110", CubeListBuilder.create()
				.texOffs(65, 180).addBox(-0.1468F, -0.5808F, -0.5243F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.375F))
				.texOffs(69, 180).addBox(-0.1468F, -0.5808F, -0.8493F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0607F, 0.3975F, 0.018F, -0.0004F, -0.0218F, -1.533F));

		PartDefinition cube_r111 = doorControlLever.addOrReplaceChild("cube_r111",
				CubeListBuilder.create().texOffs(71, 180).addBox(-0.0319F, -0.5236F, -1.9993F, 1.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.25F)),
				PartPose.offsetAndRotation(-0.0009F, 0.5079F, -0.0845F, -0.0004F, -0.0218F, -1.533F));

		PartDefinition cube_r112 = doorControlLever.addOrReplaceChild("cube_r112",
				CubeListBuilder.create().texOffs(202, 8)
						.addBox(-2.1625F, -0.5F, -0.4F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(164, 202)
						.addBox(0.1625F, -0.5F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.225F)).texOffs(168, 202)
						.addBox(-0.9125F, -0.5F, -0.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(0.0559F, -1.6128F, 0.3626F, -0.0004F, -0.1091F, -1.533F));

		PartDefinition panel1Misc = panel1.addOrReplaceChild("panel1Misc", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-11.6047F, -14.07F, -6.7F, 0.0F, -0.5236F, 0.0F));

		PartDefinition panel1Tape = panel1Misc.addOrReplaceChild("panel1Tape", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.0131F, -2.0052F, -0.8956F, 0.0F, 0.0F, -0.1309F));

		PartDefinition cube_r113 = panel1Tape.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(150, 62)
				.addBox(-1.6047F, -2.4441F, -6.299F, 2.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(202, 127)
				.addBox(-1.1227F, -3.4082F, -5.839F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)).texOffs(198, 127)
				.addBox(-1.6227F, -3.1582F, -6.339F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(52, 181)
				.addBox(-1.1407F, -4.7281F, 3.729F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(54, 188)
				.addBox(-1.1407F, -4.4781F, 3.729F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.15F)).texOffs(204, 125)
				.addBox(-1.6227F, -2.9082F, 3.261F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(198, 127)
				.addBox(-1.6227F, -3.1582F, 3.261F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(1.2631F, 1.6052F, 0.3956F, 0.0F, 0.0F, -0.3054F));

		PartDefinition cube_r114 = panel1Tape.addOrReplaceChild("cube_r114",
				CubeListBuilder.create().texOffs(206, 130).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-0.2654F, -1.1719F, -4.9434F, 3.1416F, 0.0F, -0.3054F));

		PartDefinition panel2 = topPanels.addOrReplaceChild("panel2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r115 = panel2.addOrReplaceChild("cube_r115",
				CubeListBuilder.create().texOffs(38, 228).addBox(-10.945F, -3.1676F, -6.3335F, 22.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.7314F, -13.8866F, -5.8518F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition panel2panel = panel2.addOrReplaceChild("panel2panel", CubeListBuilder.create(),
				PartPose.offset(-11.6974F, -13.6936F, -6.7265F));

		PartDefinition cube_r116 = panel2panel.addOrReplaceChild("cube_r116",
				CubeListBuilder.create().texOffs(134, 155).addBox(-1.0452F, -0.9769F, -4.3566F, 2.0F, 1.0F, 9.0F,
						new CubeDeformation(-0.199F)),
				PartPose.offsetAndRotation(-3.0247F, -2.1582F, 3.4602F, 0.6584F, 1.3225F, 0.3768F));

		PartDefinition cube_r117 = panel2panel.addOrReplaceChild("cube_r117",
				CubeListBuilder.create().texOffs(134, 155).mirror()
						.addBox(1.8062F, -3.3026F, -4.6224F, 2.0F, 1.0F, 9.0F, new CubeDeformation(-0.199F))
						.mirror(false),
				PartPose.offsetAndRotation(0.0179F, 0.2334F, -1.5117F, 0.2072F, 0.7512F, -0.124F));

		PartDefinition cube_r118 = panel2panel.addOrReplaceChild("cube_r118",
				CubeListBuilder.create().texOffs(122, 163).addBox(-3.239F, -3.3026F, 3.4096F, 4.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.21F)),
				PartPose.offsetAndRotation(1.5386F, -0.025F, -2.5064F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r119 = panel2panel.addOrReplaceChild("cube_r119",
				CubeListBuilder.create().texOffs(178, 162).addBox(-4.239F, -3.3026F, 3.4096F, 5.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.21F)),
				PartPose.offsetAndRotation(0.0987F, -0.025F, -0.0124F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r120 = panel2panel.addOrReplaceChild("cube_r120",
				CubeListBuilder.create().texOffs(146, 50).addBox(-7.239F, -3.3026F, -4.5904F, 14.0F, 1.0F, 9.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone26 = panel2panel.addOrReplaceChild("bone26", CubeListBuilder.create(),
				PartPose.offset(-3.0117F, -2.1105F, 3.4677F));

		PartDefinition cube_r121 = bone26.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(139, 159)
				.addBox(-0.3975F, -0.4717F, -0.8361F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, -0.8361F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, -0.6361F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, -0.5861F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.38F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.0717F, -0.5861F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, -0.3861F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.4717F, 0.664F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, 0.464F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, 0.664F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, 0.214F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.2717F, 0.414F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.38F)).texOffs(139, 159)
				.addBox(-0.3975F, -0.0717F, 0.414F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(137, 161)
				.addBox(-0.3975F, 0.6783F, -0.5861F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(138, 162)
				.addBox(-0.3975F, 0.4283F, 0.414F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(138, 162)
				.addBox(-0.3975F, 0.4283F, -0.5861F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(-3.6532F, -0.5473F, -0.6264F, 0.6584F, 1.3225F, 0.3768F));

		PartDefinition bone27 = panel2panel.addOrReplaceChild("bone27", CubeListBuilder.create(),
				PartPose.offset(-3.0117F, -2.1105F, 3.7177F));

		PartDefinition cube_r122 = bone27.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(139, 159)
				.addBox(-0.7803F, -0.5252F, -1.1849F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, -1.1849F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, -0.9849F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, -0.9349F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.38F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.1252F, -0.9349F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, -0.7349F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.5252F, -0.0849F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, -0.2849F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, -0.0849F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, 0.2651F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.3252F, 0.0651F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.38F)).texOffs(139, 159)
				.addBox(-0.7803F, -0.1252F, 0.0651F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(137, 161)
				.addBox(-0.7803F, 0.6248F, -0.9349F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(138, 162)
				.addBox(-0.7803F, 0.3748F, 0.0651F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(138, 162)
				.addBox(-0.7803F, 0.3748F, -0.9349F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(1.9426F, -0.5991F, -9.8844F, 2.9344F, -0.7512F, 3.0175F));

		PartDefinition bone51 = panel2panel.addOrReplaceChild("bone51", CubeListBuilder.create(),
				PartPose.offset(0.25F, -0.25F, 0.25F));

		PartDefinition cube_r123 = bone51.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(116, 173)
				.addBox(-0.648F, -3.4361F, 1.3716F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(198, 139)
				.addBox(1.702F, -3.5371F, 1.1046F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(198, 139)
				.addBox(-4.078F, -3.5371F, 1.1046F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(118, 152)
				.addBox(-2.158F, -3.4361F, -0.2354F, 4.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(120, 159)
				.addBox(-2.158F, -3.3361F, -0.2354F, 4.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(174, 172)
				.addBox(-4.048F, -3.3361F, -4.4564F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r124 = bone51.addOrReplaceChild("cube_r124",
				CubeListBuilder.create().texOffs(200, 150).addBox(-1.5F, -1.3686F, -0.6622F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(1.4416F, -2.6264F, -2.8651F, 0.7418F, 1.0472F, 0.0F));

		PartDefinition cube_r125 = bone51.addOrReplaceChild("cube_r125",
				CubeListBuilder.create().texOffs(200, 150).addBox(-0.5F, -1.3686F, -0.6622F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(-1.9484F, -2.6264F, 3.0066F, 0.7418F, 1.0472F, 0.0F));

		PartDefinition cube_r126 = bone51.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(23, 176)
				.addBox(-1.8932F, 14.8653F, -13.6419F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(23, 177)
				.addBox(-2.6432F, 14.8653F, -13.6419F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 170)
				.addBox(-2.4932F, 14.7653F, -13.4919F, 8.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(3.0782F, -20.3835F, 3.5725F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition panel2panelMisc = bone51.addOrReplaceChild("panel2panelMisc", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-1.1605F, -2.01F, -0.67F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r127 = panel2panelMisc.addOrReplaceChild("cube_r127", CubeListBuilder.create()
				.texOffs(44, 183).addBox(-2.0837F, -1.6823F, -3.7426F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(34, 183).addBox(-2.0837F, -1.6823F, -3.2066F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(24, 183).addBox(-2.0837F, -1.6823F, -2.6706F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(0, 183).addBox(-2.0837F, -1.6823F, -2.1346F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(54, 183).addBox(-2.0837F, -1.6823F, -1.5986F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(182, 181).addBox(-2.0837F, -1.6823F, -0.2586F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(120, 182).addBox(-2.0837F, -1.6823F, 0.2774F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(110, 182).addBox(-2.0837F, -1.6823F, 0.8134F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(172, 181).addBox(-2.0837F, -1.6823F, 1.3494F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
				.texOffs(162, 181).addBox(-2.0837F, -1.6823F, 1.8854F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3142F));

		PartDefinition panel2panelLight11 = panel2panelMisc.addOrReplaceChild("panel2panelLight11",
				CubeListBuilder.create(), PartPose.offset(3.015F, -0.938F, 4.422F));

		PartDefinition bone12 = panel2panelMisc.addOrReplaceChild("bone12", CubeListBuilder.create(),
				PartPose.offset(-3.0674F, 1.4635F, 0.5345F));

		PartDefinition cube_r128 = bone12.addOrReplaceChild("cube_r128",
				CubeListBuilder.create().texOffs(21, 235).addBox(-1.7682F, 2.1133F, -3.8325F, 3.0F, 2.0F, 9.0F,
						new CubeDeformation(-1.0F)),
				PartPose.offsetAndRotation(0.5408F, 1.6643F, 0.0F, 3.1416F, 0.0F, -0.3142F));

		PartDefinition panel3 = topPanels.addOrReplaceChild("panel3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r129 = panel3.addOrReplaceChild("cube_r129",
				CubeListBuilder.create().texOffs(221, 147).mirror()
						.addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false),
				PartPose.offsetAndRotation(-5.1224F, -15.2988F, -8.3633F, -0.9128F, -0.2852F, 0.6047F));

		PartDefinition cube_r130 = panel3.addOrReplaceChild("cube_r130",
				CubeListBuilder.create().texOffs(221, 147).mirror()
						.addBox(-4.5F, -1.5F, -0.5F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-8.3647F, -15.1998F, -11.7235F, -0.2277F, -0.9243F, -0.4694F));

		PartDefinition cube_r131 = panel3.addOrReplaceChild("cube_r131",
				CubeListBuilder.create().texOffs(211, 143).mirror()
						.addBox(-2.1363F, -0.9769F, -0.4365F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.01F))
						.mirror(false),
				PartPose.offsetAndRotation(-7.899F, -16.2982F, -9.4506F, 2.8893F, 0.9243F, 2.6721F));

		PartDefinition cube_r132 = panel3.addOrReplaceChild("cube_r132",
				CubeListBuilder.create().texOffs(221, 151).mirror()
						.addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false),
				PartPose.offsetAndRotation(-10.0638F, -15.2988F, 0.1955F, 2.8553F, 0.5741F, -2.4344F));

		PartDefinition cube_r133 = panel3.addOrReplaceChild("cube_r133",
				CubeListBuilder.create().texOffs(221, 147).addBox(-4.5F, -1.5F, -0.5F, 9.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-14.595F, -15.1998F, -0.9323F, 2.5104F, 0.0662F, 2.8649F));

		PartDefinition cube_r134 = panel3.addOrReplaceChild("cube_r134",
				CubeListBuilder.create().texOffs(211, 143).addBox(-4.3637F, -1.9769F, -0.4365F, 9.0F, 3.0F, 1.0F,
						new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(-14.533F, -14.6632F, -1.6804F, 0.1513F, -0.0662F, -0.2766F));

		PartDefinition cube_r135 = panel3.addOrReplaceChild("cube_r135",
				CubeListBuilder.create().texOffs(210, 177).addBox(-8.0F, -0.276F, -1.4169F, 8.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-11.1216F, -16.5403F, -1.3836F, 2.1555F, -1.0472F, 3.1416F));

		PartDefinition cube_r136 = panel3.addOrReplaceChild("cube_r136",
				CubeListBuilder.create().texOffs(210, 177).addBox(-0.0412F, -0.2728F, -0.583F, 4.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.6563F, -17.3837F, -1.115F, 1.0991F, 0.9152F, 0.1354F));

		PartDefinition cube_r137 = panel3.addOrReplaceChild("cube_r137",
				CubeListBuilder.create().texOffs(210, 177).addBox(-4.9589F, -0.2728F, -0.583F, 5.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.5536F, -17.3837F, -8.2211F, 0.7972F, 1.1667F, -0.2108F));

		PartDefinition cube_r138 = panel3.addOrReplaceChild("cube_r138",
				CubeListBuilder.create().texOffs(40, 181).addBox(-6.945F, -3.1676F, -6.3334F, 5.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.351F, -16.4665F, -6.886F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r139 = panel3.addOrReplaceChild("cube_r139",
				CubeListBuilder.create().texOffs(210, 177).addBox(-6.945F, -3.1676F, -6.3334F, 4.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.9538F, -16.4665F, -4.1099F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r140 = panel3.addOrReplaceChild("cube_r140",
				CubeListBuilder.create().texOffs(46, 226).addBox(-6.945F, -3.1676F, -6.3334F, 14.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.4885F, -13.8866F, -5.692F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r141 = panel3.addOrReplaceChild("cube_r141",
				CubeListBuilder.create().texOffs(38, 222).addBox(-10.945F, -3.1676F, -6.3334F, 22.0F, 0.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.4882F, -13.8857F, -5.6919F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone16 = panel3.addOrReplaceChild("bone16", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-11.6735F, -15.9602F, -6.8452F, -0.0524F, 0.0005F, 0.096F));

		PartDefinition cube_r142 = bone16.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(138, 231)
				.addBox(-3.9564F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(138, 231)
				.addBox(-4.3064F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(138, 231)
				.addBox(-4.6564F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(138, 231)
				.addBox(-5.0064F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(138, 231)
				.addBox(-5.3564F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(138, 231)
				.addBox(-5.7064F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(138, 231)
				.addBox(-6.0564F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(138, 231)
				.addBox(-6.4064F, -1.33F, -4.4647F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(131, 218)
				.addBox(-7.7564F, -2.23F, -4.5147F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-1.5F)).texOffs(105, 209)
				.addBox(-7.2564F, -0.705F, -4.5647F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(112, 255)
				.addBox(-7.2564F, -0.63F, -4.5647F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1264F, 0.4606F, 0.0788F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone34 = bone16.addOrReplaceChild("bone34", CubeListBuilder.create(),
				PartPose.offset(-1.2582F, -0.079F, -0.6015F));

		PartDefinition cube_r143 = bone34.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(105, 250)
				.addBox(-0.5761F, -1.4904F, -0.4055F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(105, 250)
				.addBox(-0.3761F, -1.4904F, -0.4055F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(-0.2325F, 0.3046F, 3.9938F, 0.2161F, 0.7939F, -0.1112F));

		PartDefinition cube_r144 = bone34.addOrReplaceChild("cube_r144",
				CubeListBuilder.create().texOffs(105, 250).addBox(-4.7795F, -1.308F, -0.1259F, 1.0F, 2.0F, 1.0F,
						new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(1.2306F, 0.2138F, 0.8223F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r145 = bone34.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(105, 250)
				.addBox(-4.5295F, -1.308F, -0.1259F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(104, 257)
				.addBox(-3.5795F, -1.008F, -1.8259F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(96, 249)
				.addBox(-5.3795F, -1.208F, -2.1259F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(96, 249)
				.addBox(-4.6795F, -1.208F, -2.1259F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(104, 249)
				.addBox(-4.4295F, -1.208F, -0.6259F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(104, 249)
				.addBox(-5.1295F, -1.208F, -0.6259F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(2.2205F, -1.308F, -1.1259F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(2.9205F, -1.308F, -1.1259F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(1.5205F, -1.308F, -1.1259F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(0.9958F, -1.3829F, 0.3514F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-3.0042F, -1.3829F, 0.3514F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)),
				PartPose.offsetAndRotation(1.2056F, 0.2138F, 0.8656F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone35 = bone34.addOrReplaceChild("bone35", CubeListBuilder.create(),
				PartPose.offset(-0.9128F, 0.5432F, 0.4815F));

		PartDefinition cube_r146 = bone35.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(102, 239)
				.addBox(0.0337F, -1.3314F, -4.0215F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-1.1663F, -1.3314F, -4.0215F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-2.3663F, -1.3314F, -4.0215F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)),
				PartPose.offsetAndRotation(2.2184F, -0.3294F, 0.2841F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r147 = bone35.addOrReplaceChild("cube_r147",
				CubeListBuilder.create().texOffs(113, 247).addBox(-0.5182F, -0.7731F, 0.4031F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.38F)),
				PartPose.offsetAndRotation(0.3776F, -1.1545F, 1.7048F, 1.5177F, 1.073F, -0.4095F));

		PartDefinition cube_r148 = bone35.addOrReplaceChild("cube_r148",
				CubeListBuilder.create().texOffs(110, 247).addBox(-0.0656F, -0.5231F, 0.0216F, 0.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0531F, -0.8799F, 1.4335F, 1.8023F, 1.0588F, -0.085F));

		PartDefinition cube_r149 = bone35.addOrReplaceChild("cube_r149",
				CubeListBuilder.create().texOffs(110, 247).addBox(0.0466F, -0.5231F, 0.0453F, 0.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0531F, -0.8799F, 1.4335F, 1.1218F, 1.013F, -0.8656F));

		PartDefinition cube_r150 = bone35.addOrReplaceChild("cube_r150",
				CubeListBuilder.create().texOffs(87, 228).addBox(-2.7219F, -0.2965F, -0.8499F, 5.0F, 2.0F, 2.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(0.6516F, 0.5426F, -0.6852F, 2.6616F, 1.0472F, 0.0F));

		PartDefinition cube_r151 = bone35.addOrReplaceChild("cube_r151",
				CubeListBuilder.create().texOffs(95, 233).addBox(-2.7219F, -0.7166F, -0.9969F, 5.0F, 2.0F, 2.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(0.9434F, -0.4104F, -0.5167F, 1.8762F, 1.0472F, 0.0F));

		PartDefinition cube_r152 = bone35.addOrReplaceChild("cube_r152",
				CubeListBuilder.create().texOffs(95, 233).addBox(-2.7219F, -0.9097F, -1.3978F, 5.0F, 2.0F, 2.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(1.7333F, -0.846F, -0.0607F, 1.0908F, 1.0472F, 0.0F));

		PartDefinition bone31 = bone35.addOrReplaceChild("bone31", CubeListBuilder.create(),
				PartPose.offset(13.9445F, 15.2459F, 6.6152F));

		PartDefinition bone28 = bone31.addOrReplaceChild("bone28", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-12.9317F, -16.0391F, -7.1967F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone63 = bone28.addOrReplaceChild("bone63", CubeListBuilder.create(),
				PartPose.offset(-0.3105F, 1.01F, -0.1724F));

		PartDefinition cube_r153 = bone63.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(98, 239)
				.addBox(-0.9534F, 0.219F, 2.6843F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(96, 237)
				.addBox(-0.9534F, 0.219F, 0.7093F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0681F, 1.5565F, -1.921F));

		PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create(),
				PartPose.offset(-0.2828F, 0.3772F, -0.2882F));

		PartDefinition cube_r154 = bone29.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(101, 245)
				.addBox(-2.2034F, 0.219F, 2.7843F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(97, 242)
				.addBox(-2.2034F, 0.219F, 0.7343F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(89, 244)
				.addBox(-2.7034F, -0.281F, 1.1843F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)),
				PartPose.offsetAndRotation(-0.0277F, 0.6328F, 0.0909F, -0.0681F, 1.5565F, -1.921F));

		PartDefinition bone30 = bone28.addOrReplaceChild("bone30", CubeListBuilder.create(),
				PartPose.offset(-0.2828F, 0.6272F, -0.2133F));

		PartDefinition cube_r155 = bone30.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(101, 245)
				.addBox(0.2966F, 0.219F, 2.7843F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(89, 244)
				.addBox(-0.2034F, -0.281F, 1.1843F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)).texOffs(97, 242)
				.addBox(0.2966F, 0.219F, 0.7343F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(-0.0277F, 0.3828F, 0.0909F, -0.0681F, 1.5565F, -1.921F));

		PartDefinition bone67 = bone34.addOrReplaceChild("bone67", CubeListBuilder.create(),
				PartPose.offset(1.2056F, 0.2138F, 0.8656F));

		PartDefinition cube_r156 = bone67.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(96, 253)
				.addBox(-3.5795F, -1.108F, -1.5759F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(96, 257)
				.addBox(-3.5795F, -1.608F, -1.5759F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.85F)).texOffs(104, 253)
				.addBox(-3.5795F, -1.158F, -1.5759F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone36 = bone16.addOrReplaceChild("bone36", CubeListBuilder.create(),
				PartPose.offset(-0.0526F, 0.1348F, 0.2641F));

		PartDefinition cube_r157 = bone36.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(116, 241)
				.addBox(2.2189F, -1.1549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(112, 241)
				.addBox(2.2189F, -0.7549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(116, 241)
				.addBox(0.7689F, -1.1549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(112, 241)
				.addBox(0.7689F, -0.7549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(116, 241)
				.addBox(-0.5811F, -1.1549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(112, 241)
				.addBox(-0.5811F, -0.7549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(116, 241)
				.addBox(-1.9311F, -1.1549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(112, 241)
				.addBox(-1.9311F, -0.7549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(116, 241)
				.addBox(-3.2811F, -1.1549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(112, 241)
				.addBox(-3.2811F, -0.7549F, 1.7126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition panel4 = topPanels.addOrReplaceChild("panel4", CubeListBuilder.create(),
				PartPose.offset(0.2607F, -1.5281F, 0.6622F));

		PartDefinition cube_r158 = panel4.addOrReplaceChild("cube_r158",
				CubeListBuilder.create().texOffs(38, 228).addBox(-10.945F, -3.3016F, -6.3334F, 22.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.4545F, -12.2307F, -6.4645F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r159 = panel4.addOrReplaceChild("cube_r159",
				CubeListBuilder.create().texOffs(32, 155).addBox(-8.625F, -3.0956F, -2.2434F, 17.0F, 2.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.4881F, -12.0237F, -6.4839F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition panel4Lights = panel4.addOrReplaceChild("panel4Lights", CubeListBuilder.create(),
				PartPose.offset(-17.7721F, -12.6431F, -4.5001F));

		PartDefinition panel4Light1 = panel4Lights.addOrReplaceChild("panel4Light1", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r160 = panel4Light1.addOrReplaceChild("cube_r160",
				CubeListBuilder.create().texOffs(75, 177).addBox(-2.075F, -2.4323F, -0.8798F, 1.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 1.0472F, 0.0F));

		PartDefinition panel4Light2 = panel4Lights.addOrReplaceChild("panel4Light2", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r161 = panel4Light2.addOrReplaceChild("cube_r161",
				CubeListBuilder.create().texOffs(117, 177).addBox(11.665F, -2.4323F, -0.8798F, 1.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 1.0472F, 0.0F));

		PartDefinition panel4Controls = panel4.addOrReplaceChild("panel4Controls", CubeListBuilder.create(),
				PartPose.offset(-10.4881F, -12.0237F, -6.4839F));

		PartDefinition cube_r162 = panel4Controls.addOrReplaceChild("cube_r162", CubeListBuilder.create()
				.texOffs(68, 161).addBox(-1.815F, -2.835F, -5.845F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.6F))
				.texOffs(52, 161).addBox(-1.815F, -2.935F, -5.845F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.6F))
				.texOffs(52, 161).addBox(-1.815F, -2.935F, 2.005F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.6F))
				.texOffs(68, 161).addBox(-1.815F, -2.835F, 2.005F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition panel4wire = panel4Controls.addOrReplaceChild("panel4wire", CubeListBuilder.create(),
				PartPose.offset(2.1651F, -7.75F, 1.25F));

		PartDefinition cube_r163 = panel4wire.addOrReplaceChild("cube_r163",
				CubeListBuilder.create().texOffs(152, 145).mirror()
						.addBox(1.958F, -1.835F, -4.84F, 4.0F, 1.0F, 9.0F, new CubeDeformation(-0.1F)).mirror(false),
				PartPose.offsetAndRotation(-1.9053F, 7.75F, -1.1F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r164 = panel4wire.addOrReplaceChild("cube_r164",
				CubeListBuilder.create().texOffs(144, 145).mirror()
						.addBox(-2.042F, -2.585F, -4.34F, 4.0F, 1.0F, 9.0F, new CubeDeformation(-0.1F)).mirror(false),
				PartPose.offsetAndRotation(-1.732F, 7.75F, -1.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition panel4PumpThings = panel4Controls.addOrReplaceChild("panel4PumpThings", CubeListBuilder.create(),
				PartPose.offset(-0.433F, 0.0F, -0.25F));

		PartDefinition panel4PumpThing1 = panel4PumpThings.addOrReplaceChild("panel4PumpThing1",
				CubeListBuilder.create(),
				PartPose.offsetAndRotation(-4.4178F, -2.9366F, 2.3853F, -0.0653F, 0.0037F, 0.1133F));

		PartDefinition cube_r165 = panel4PumpThing1.addOrReplaceChild("cube_r165",
				CubeListBuilder.create().texOffs(126, 202).addBox(-0.8901F, -2.3739F, -0.67F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.346F, 1.0499F, 0.3977F, -0.1243F, -0.51F, 0.2506F));

		PartDefinition cube_r166 = panel4PumpThing1.addOrReplaceChild("cube_r166",
				CubeListBuilder.create().texOffs(202, 52).addBox(-1.4756F, -9.713F, -1.8509F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(4.5102F, 0.229F, -8.0292F, -1.6951F, -0.51F, 0.2506F));

		PartDefinition cube_r167 = panel4PumpThing1.addOrReplaceChild("cube_r167", CubeListBuilder.create()
				.texOffs(200, 171).addBox(-1.2961F, -1.9979F, 8.694F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(209, 171).addBox(-1.5461F, -1.9979F, 8.694F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
				.texOffs(208, 171).addBox(-1.0461F, -1.9979F, 8.694F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(5.036F, 1.0499F, -7.7256F, -0.1243F, -0.51F, 0.2506F));

		PartDefinition panel4PumpThing2 = panel4PumpThings.addOrReplaceChild("panel4PumpThing2",
				CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0813F, -3.0599F, -4.3598F, -0.0653F, 0.0037F, 0.1133F));

		PartDefinition cube_r168 = panel4PumpThing2.addOrReplaceChild("cube_r168", CubeListBuilder.create()
				.texOffs(209, 171).addBox(-1.5461F, -1.9979F, 0.444F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
				.texOffs(200, 171).addBox(-1.2961F, -1.9979F, 0.444F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(208, 171).addBox(-1.0461F, -1.9979F, 0.444F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(0.5547F, 1.2405F, -0.9702F, -0.1243F, -0.51F, 0.2506F));

		PartDefinition cube_r169 = panel4PumpThing2.addOrReplaceChild("cube_r169",
				CubeListBuilder.create().texOffs(202, 52).addBox(-1.4756F, -1.463F, -1.8509F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0289F, 0.4196F, -1.2738F, -1.6951F, -0.51F, 0.2506F));

		PartDefinition cube_r170 = panel4PumpThing2.addOrReplaceChild("cube_r170",
				CubeListBuilder.create().texOffs(126, 202).addBox(-0.8901F, -2.3739F, -8.92F, 1.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.1353F, 1.2405F, 7.1531F, -0.1243F, -0.51F, 0.2506F));

		PartDefinition coffeepot = panel4Controls.addOrReplaceChild("coffeepot", CubeListBuilder.create(),
				PartPose.offset(1.374F, -7.75F, 0.6201F));

		PartDefinition cube_r171 = coffeepot.addOrReplaceChild("cube_r171",
				CubeListBuilder.create().texOffs(40, 170).addBox(-2.039F, -3.6579F, -3.801F, 5.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.7617F, 6.9079F, 1.2719F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r172 = coffeepot.addOrReplaceChild("cube_r172",
				CubeListBuilder.create().texOffs(86, 254).addBox(-0.7197F, -3.0F, -0.8232F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.501F)),
				PartPose.offsetAndRotation(1.626F, 5.5F, 0.4407F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r173 = coffeepot.addOrReplaceChild("cube_r173",
				CubeListBuilder.create().texOffs(76, 254).addBox(-0.7197F, -3.0F, -2.1768F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(1.626F, 5.5F, 0.4407F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r174 = coffeepot.addOrReplaceChild("cube_r174",
				CubeListBuilder.create().texOffs(86, 254).addBox(-1.0F, -3.0F, -1.5F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.501F)),
				PartPose.offsetAndRotation(0.8297F, 5.5F, -1.4479F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r175 = coffeepot.addOrReplaceChild("cube_r175",
				CubeListBuilder.create().texOffs(76, 254).addBox(-1.0F, -3.0F, -1.5F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(-0.6228F, 5.5F, -1.6391F, -3.1416F, 1.309F, 3.1416F));

		PartDefinition cube_r176 = coffeepot.addOrReplaceChild("cube_r176",
				CubeListBuilder.create().texOffs(86, 254).addBox(-1.0F, -3.0F, -1.5F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.501F)),
				PartPose.offsetAndRotation(-1.7851F, 5.5F, -0.7473F, -3.1416F, 0.5236F, 3.1416F));

		PartDefinition cube_r177 = coffeepot.addOrReplaceChild("cube_r177",
				CubeListBuilder.create().texOffs(76, 254).addBox(-1.0F, -3.0F, -1.5F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(0.3681F, 5.5F, 2.0588F, 0.0F, -1.309F, 0.0F));

		PartDefinition cube_r178 = coffeepot.addOrReplaceChild("cube_r178",
				CubeListBuilder.create().texOffs(76, 254).addBox(-1.0F, -3.0F, -1.5F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(-1.9764F, 5.5F, 0.7053F, -3.1416F, -0.2618F, 3.1416F));

		PartDefinition cube_r179 = coffeepot.addOrReplaceChild("cube_r179",
				CubeListBuilder.create().texOffs(86, 254).addBox(-1.0F, -3.0F, -1.5F, 2.0F, 5.0F, 3.0F,
						new CubeDeformation(-0.501F)),
				PartPose.offsetAndRotation(-1.0845F, 5.5F, 1.8676F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition bone33 = panel4Controls.addOrReplaceChild("bone33", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r180 = bone33.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(115, 165)
				.addBox(-2.985F, -3.9696F, -5.6634F, 6.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(100, 164)
				.addBox(-2.985F, -4.0696F, -5.6634F, 6.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(100, 160)
				.addBox(-2.985F, -3.9196F, -5.6634F, 6.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(85, 165)
				.addBox(-2.985F, -3.8196F, -5.6634F, 6.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition panel5 = topPanels.addOrReplaceChild("panel5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r181 = panel5.addOrReplaceChild("cube_r181",
				CubeListBuilder.create().texOffs(221, 151).mirror()
						.addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false),
				PartPose.offsetAndRotation(-4.8456F, -15.2988F, -8.7839F, -0.9128F, -0.2852F, 0.6047F));

		PartDefinition cube_r182 = panel5.addOrReplaceChild("cube_r182",
				CubeListBuilder.create().texOffs(221, 147).mirror()
						.addBox(-4.5F, -1.5F, -0.5F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-8.0879F, -15.1998F, -12.1441F, -0.2277F, -0.9243F, -0.4694F));

		PartDefinition cube_r183 = panel5.addOrReplaceChild("cube_r183",
				CubeListBuilder.create().texOffs(211, 143).mirror()
						.addBox(-2.1363F, -0.9769F, -0.4365F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
						.mirror(false),
				PartPose.offsetAndRotation(-7.6222F, -16.2982F, -9.8713F, 2.8893F, 0.9243F, 2.6721F));

		PartDefinition cube_r184 = panel5.addOrReplaceChild("cube_r184",
				CubeListBuilder.create().texOffs(215, 151).addBox(-4.7728F, -1.8148F, -0.2234F, 5.0F, 3.0F, 1.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-8.5103F, -13.7077F, 0.861F, 0.2863F, -0.5741F, 0.7072F));

		PartDefinition cube_r185 = panel5.addOrReplaceChild("cube_r185",
				CubeListBuilder.create().texOffs(211, 147).addBox(-4.3637F, -1.8936F, -0.2234F, 9.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-14.3182F, -14.6998F, -1.3529F, 0.6312F, -0.0662F, -0.2766F));

		PartDefinition cube_r186 = panel5.addOrReplaceChild("cube_r186",
				CubeListBuilder.create().texOffs(211, 143).addBox(-4.3637F, -1.9769F, -0.4365F, 9.0F, 3.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-14.2562F, -14.6632F, -2.1011F, 0.1513F, -0.0662F, -0.2766F));

		PartDefinition cube_r187 = panel5.addOrReplaceChild("cube_r187",
				CubeListBuilder.create().texOffs(210, 177).addBox(-8.0F, -0.276F, -1.4169F, 8.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.8448F, -16.5403F, -1.8043F, 2.1555F, -1.0472F, 3.1416F));

		PartDefinition cube_r188 = panel5.addOrReplaceChild("cube_r188",
				CubeListBuilder.create().texOffs(210, 177).addBox(-0.0411F, -0.2728F, -0.583F, 4.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.3795F, -17.3837F, -1.5356F, 1.0991F, 0.9152F, 0.1354F));

		PartDefinition cube_r189 = panel5.addOrReplaceChild("cube_r189",
				CubeListBuilder.create().texOffs(210, 177).addBox(-4.9589F, -0.2728F, -0.583F, 5.0F, 5.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.2768F, -17.3837F, -8.6418F, 0.7972F, 1.1667F, -0.2108F));

		PartDefinition cube_r190 = panel5.addOrReplaceChild("cube_r190",
				CubeListBuilder.create().texOffs(40, 181).addBox(-6.945F, -3.1676F, -6.3335F, 5.0F, 3.0F, 1.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-0.0743F, -16.4665F, -7.3066F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r191 = panel5.addOrReplaceChild("cube_r191",
				CubeListBuilder.create().texOffs(210, 177).addBox(-6.945F, -3.1676F, -6.3334F, 4.0F, 3.0F, 1.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-1.677F, -16.4665F, -4.5306F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r192 = panel5.addOrReplaceChild("cube_r192",
				CubeListBuilder.create().texOffs(46, 226).addBox(-6.945F, -3.1676F, -6.3334F, 14.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.2117F, -13.8866F, -6.1127F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r193 = panel5.addOrReplaceChild("cube_r193",
				CubeListBuilder.create().texOffs(38, 222).addBox(-10.945F, -3.1676F, -6.3334F, 22.0F, 0.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-10.2115F, -13.8857F, -6.1125F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone50 = panel5.addOrReplaceChild("bone50", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-11.6735F, -15.9602F, -6.8452F, -0.0524F, 0.0005F, 0.096F));

		PartDefinition cube_r194 = bone50.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(219, 178)
				.addBox(-8.0814F, -1.93F, -4.4897F, 15.0F, 2.0F, 9.0F, new CubeDeformation(-1.0F)).texOffs(206, 189)
				.addBox(-8.0814F, -1.83F, -4.6147F, 15.0F, 2.0F, 9.0F, new CubeDeformation(-0.75F)).texOffs(203, 200)
				.addBox(-7.3564F, -0.88F, -4.5647F, 14.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(93, 224)
				.addBox(-7.3564F, -0.63F, -4.5647F, 14.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.4021F, 0.4562F, -0.3425F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition doodads = bone50.addOrReplaceChild("doodads", CubeListBuilder.create(),
				PartPose.offset(0.4021F, 0.4562F, -0.3425F));

		PartDefinition cube_r195 = doodads.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(102, 239)
				.addBox(3.7337F, -1.4814F, -4.6965F, 2.0F, 1.6247F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(4.2337F, -1.4814F, -3.8465F, 2.0F, 1.6247F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-5.6663F, -1.4814F, -3.8465F, 2.0F, 1.6247F, 2.0F, new CubeDeformation(-0.7F)).texOffs(102, 239)
				.addBox(-5.2663F, -1.4814F, -4.6965F, 2.0F, 1.6247F, 2.0F, new CubeDeformation(-0.7F)),
				PartPose.offsetAndRotation(-0.3547F, -0.3213F, 0.5067F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition bone7 = doodads.addOrReplaceChild("bone7", CubeListBuilder.create(),
				PartPose.offset(-4.0406F, -0.2887F, 3.3984F));

		PartDefinition cube_r196 = bone7.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(104, 249)
				.addBox(-1.0935F, -1.0346F, -0.9022F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F)).texOffs(106, 250)
				.addBox(-0.3435F, -0.8846F, -0.4022F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(106, 250)
				.addBox(-0.5435F, -0.8846F, -0.4022F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(106, 250)
				.addBox(-0.7435F, -0.8846F, -0.4022F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(0.0976F, -0.0077F, -0.0565F, 0.1719F, -0.4971F, -0.3492F));

		PartDefinition bone8 = doodads.addOrReplaceChild("bone8", CubeListBuilder.create(),
				PartPose.offset(-4.4423F, -0.0205F, 2.3928F));

		PartDefinition cube_r197 = bone8.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(104, 249)
				.addBox(-1.0501F, -1.0478F, -1.1405F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F)).texOffs(106, 250)
				.addBox(-0.7001F, -0.6978F, -0.6405F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(106, 250)
				.addBox(-0.5001F, -0.6978F, -0.6405F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(106, 250)
				.addBox(-0.3001F, -0.6978F, -0.6405F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(0.0502F, -0.0109F, 0.1334F, 0.157F, 0.2788F, -0.223F));

		PartDefinition bone20 = doodads.addOrReplaceChild("bone20", CubeListBuilder.create(),
				PartPose.offset(-5.2482F, 0.3329F, 1.5473F));

		PartDefinition cube_r198 = bone20.addOrReplaceChild("cube_r198",
				CubeListBuilder.create().texOffs(114, 253)
						.addBox(-0.3F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(114, 253)
						.addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(112, 254)
						.addBox(-0.7F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(4.6082F, -0.0711F, -8.0294F, 0.9562F, -1.3857F, -1.2146F));

		PartDefinition bone19 = doodads.addOrReplaceChild("bone19", CubeListBuilder.create(),
				PartPose.offset(-5.2482F, 0.3329F, 1.5473F));

		PartDefinition cube_r199 = bone19.addOrReplaceChild("cube_r199",
				CubeListBuilder.create().texOffs(114, 253)
						.addBox(-0.3F, -0.35F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(114, 253)
						.addBox(-0.5F, -0.35F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(112, 254)
						.addBox(-0.7F, -0.35F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(-1.0819F, -0.3095F, 1.6758F, 0.1759F, 0.537F, -0.1759F));

		PartDefinition bone9 = doodads.addOrReplaceChild("bone9", CubeListBuilder.create(),
				PartPose.offset(-5.2482F, 0.3329F, 1.5473F));

		PartDefinition cube_r200 = bone9.addOrReplaceChild("cube_r200",
				CubeListBuilder.create().texOffs(104, 249).addBox(-1.0185F, -1.0346F, -1.5023F, 2.0F, 1.737F, 2.0F,
						new CubeDeformation(-0.8F)),
				PartPose.offsetAndRotation(0.4078F, -0.1419F, 0.2574F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition cube_r201 = bone9.addOrReplaceChild("cube_r201",
				CubeListBuilder.create().texOffs(106, 250).addBox(-0.5F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(-0.1914F, -0.3576F, 0.106F, -2.8362F, 1.0472F, 0.0F));

		PartDefinition cube_r202 = bone9.addOrReplaceChild("cube_r202",
				CubeListBuilder.create().texOffs(106, 250).addBox(-0.5F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(-0.0914F, -0.3576F, -0.0672F, -2.8362F, 1.0472F, 0.0F));

		PartDefinition cube_r203 = bone9.addOrReplaceChild("cube_r203",
				CubeListBuilder.create().texOffs(106, 250).addBox(-0.5F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(0.0086F, -0.3576F, -0.2404F, -2.8362F, 1.0472F, 0.0F));

		PartDefinition bone11 = doodads.addOrReplaceChild("bone11", CubeListBuilder.create(),
				PartPose.offset(-4.8404F, 0.191F, 1.8046F));

		PartDefinition cube_r204 = bone11.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(106, 250)
				.addBox(-0.2251F, -0.6978F, -0.4905F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(106, 250)
				.addBox(-0.4251F, -0.6978F, -0.4905F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(106, 250)
				.addBox(-0.6251F, -0.6978F, -0.4905F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).texOffs(104, 249)
				.addBox(-0.9751F, -1.0478F, -0.9905F, 2.0F, 1.8034F, 2.0F, new CubeDeformation(-0.8F)),
				PartPose.offsetAndRotation(3.2216F, 0.1665F, -6.5481F, 2.8362F, -1.0472F, 3.1416F));

		PartDefinition bone18 = doodads.addOrReplaceChild("bone18", CubeListBuilder.create(),
				PartPose.offset(-0.3775F, -0.0523F, -4.4456F));

		PartDefinition cube_r205 = bone18.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(106, 250)
				.addBox(0.5514F, -0.9027F, -1.0102F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(106, 250)
				.addBox(0.3514F, -0.9027F, -1.0102F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(106, 250)
				.addBox(0.1514F, -0.9027F, -1.0102F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(104, 249)
				.addBox(-0.1499F, -1.0479F, -1.5095F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F)),
				PartPose.offsetAndRotation(-0.0081F, 0.0247F, 0.0096F, 0.1719F, -0.4971F, -0.3492F));

		PartDefinition bone13 = doodads.addOrReplaceChild("bone13", CubeListBuilder.create(),
				PartPose.offset(-0.3775F, -0.0523F, -4.4456F));

		PartDefinition cube_r206 = bone13.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(106, 250)
				.addBox(-0.3348F, -0.9027F, -0.4756F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(106, 250)
				.addBox(-0.5348F, -0.9027F, -0.4756F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(106, 250)
				.addBox(-0.7348F, -0.9027F, -0.4756F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.6F)).texOffs(104, 249)
				.addBox(-1.0499F, -1.0479F, -1.0095F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F)),
				PartPose.offsetAndRotation(-0.0081F, 0.0247F, 0.0096F, 0.5001F, -1.2518F, -0.7451F));

		PartDefinition bone66 = doodads.addOrReplaceChild("bone66", CubeListBuilder.create(),
				PartPose.offset(-4.75F, -0.5F, 1.25F));

		PartDefinition cube_r207 = bone66.addOrReplaceChild("cube_r207",
				CubeListBuilder.create().texOffs(152, 510)
						.addBox(1.875F, 0.15F, -8.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 506)
						.addBox(1.875F, 0.15F, 1.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(156, 510)
						.addBox(1.45F, 0.15F, -7.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(156, 510)
						.addBox(1.45F, 0.15F, 0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 508)
						.addBox(-1.15F, 0.15F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 510)
						.addBox(0.9F, 0.15F, 0.075F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(156, 510)
						.addBox(0.25F, 0.15F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 510)
						.addBox(-0.4F, 0.15F, -0.575F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 506)
						.addBox(0.9F, 0.15F, -7.325F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 508)
						.addBox(0.25F, 0.15F, -6.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 510)
						.addBox(-0.4F, 0.15F, -6.675F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(152, 508)
						.addBox(-1.15F, 0.15F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1719F, -0.4971F, -0.3492F));

		PartDefinition bone68 = doodads.addOrReplaceChild("bone68", CubeListBuilder.create(),
				PartPose.offset(-0.4547F, -0.3213F, 0.6067F));

		PartDefinition cube_r208 = bone68.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(96, 257)
				.addBox(2.1705F, -1.958F, 1.1741F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.85F)).texOffs(104, 253)
				.addBox(2.1705F, -1.508F, 1.1741F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(96, 253)
				.addBox(2.1705F, -1.458F, 1.1741F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(96, 253)
				.addBox(-3.3295F, -1.458F, 1.1741F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(96, 257)
				.addBox(-3.3295F, -1.958F, 1.1741F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.85F)).texOffs(104, 253)
				.addBox(-3.3295F, -1.508F, 1.1741F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 1.0472F, 0.0F));

		PartDefinition panel6 = topPanels.addOrReplaceChild("panel6", CubeListBuilder.create().texOffs(95, 44)
				.addBox(13.4647F, 2.9752F, -22.2553F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(95, 44)
				.addBox(13.4653F, 0.6992F, -22.2553F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(12.065F, -15.8293F, -7.2936F, 0.0F, -2.618F, 0.0F));

		PartDefinition cube_r209 = panel6.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(0, 153)
				.addBox(-7.4265F, 0.7579F, 0.6668F, 18.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(46, 212)
				.addBox(-4.1237F, -0.2683F, -0.3064F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.8535F, 0.0878F, 0.0825F, 0.0F, 1.5708F, -0.3054F));

		PartDefinition cube_r210 = panel6.addOrReplaceChild("cube_r210",
				CubeListBuilder.create().texOffs(34, 215).addBox(-6.3162F, -0.2683F, -5.7808F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-1.8535F, 0.0878F, -1.6675F, 3.1416F, 1.1781F, 2.8362F));

		PartDefinition cube_r211 = panel6.addOrReplaceChild("cube_r211",
				CubeListBuilder.create().texOffs(39, 215).addBox(-10.3737F, -5.5183F, -3.3064F, 8.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2748F, 5.0948F, 0.0825F, 0.0F, 1.5708F, -0.3054F));

		PartDefinition cube_r212 = panel6.addOrReplaceChild("cube_r212",
				CubeListBuilder.create().texOffs(66, 217).addBox(-7.1237F, 0.0219F, -3.3172F, 16.0F, 0.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.8535F, 0.0878F, 0.0825F, 0.0F, 1.5708F, -0.2182F));

		PartDefinition cube_r213 = panel6.addOrReplaceChild("cube_r213",
				CubeListBuilder.create().texOffs(39, 207).addBox(1.25F, -1.0269F, -3.0997F, 12.0F, 2.0F, 2.0F,
						new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(2.5292F, -0.289F, -6.3438F, 0.3054F, 0.5236F, 0.0F));

		PartDefinition cube_r214 = panel6.addOrReplaceChild("cube_r214",
				CubeListBuilder.create().texOffs(25, 221).addBox(6.9353F, -0.2683F, -6.4515F, 1.0F, 1.0F, 5.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-1.8535F, 0.0878F, 1.8325F, 0.0F, 1.1781F, -0.3054F));

		PartDefinition cube_r215 = panel6.addOrReplaceChild("cube_r215",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.5265F, 1.2262F, -2.0035F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.1161F, -0.577F, 9.287F, -3.1416F, 1.0472F, -3.1416F));

		PartDefinition cube_r216 = panel6.addOrReplaceChild("cube_r216",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.3798F, 0.0968F, 8.3151F, -0.2618F, -1.0472F, 0.0F));

		PartDefinition cube_r217 = panel6.addOrReplaceChild("cube_r217",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5265F, 0.6658F, -2.2526F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.1161F, -0.577F, 9.287F, -2.8798F, 1.0472F, 3.1416F));

		PartDefinition cube_r218 = panel6.addOrReplaceChild("cube_r218",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.5265F, 0.8262F, -2.0035F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.9382F, -0.127F, 19.1642F, -3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r219 = panel6.addOrReplaceChild("cube_r219",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.9647F, 0.1468F, 17.1746F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r220 = panel6.addOrReplaceChild("cube_r220",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5265F, 0.2795F, -2.1491F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.9382F, -0.127F, 19.1642F, -2.8798F, 0.0F, 3.1416F));

		PartDefinition cube_r221 = panel6.addOrReplaceChild("cube_r221",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.5265F, 0.3762F, -2.0035F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(31.0192F, 0.273F, 9.3334F, -3.1416F, -1.0472F, -3.1416F));

		PartDefinition cube_r222 = panel6.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(93, 37)
				.mirror().addBox(-0.5265F, -0.1552F, 0.4674F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(93, 42).addBox(-0.5265F, -0.1552F, -2.0326F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(31.0192F, 0.273F, 9.3334F, -2.8798F, -1.0472F, 3.1416F));

		PartDefinition cube_r223 = panel6.addOrReplaceChild("cube_r223",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.5265F, -0.0239F, -2.0035F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(31.046F, 0.723F, -10.3746F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r224 = panel6.addOrReplaceChild("cube_r224",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(29.3097F, 0.1469F, -9.4027F, 2.8798F, 1.0472F, -3.1416F));

		PartDefinition cube_r225 = panel6.addOrReplaceChild("cube_r225",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5265F, -0.5416F, -1.9291F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(31.046F, 0.723F, -10.3746F, 0.2618F, -1.0472F, 0.0F));

		PartDefinition cube_r226 = panel6.addOrReplaceChild("cube_r226",
				CubeListBuilder.create().texOffs(93, 42).mirror()
						.addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(13.9653F, 0.1468F, -18.2622F, 2.8798F, 0.0F, 3.1416F));

		PartDefinition cube_r227 = panel6.addOrReplaceChild("cube_r227",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5265F, -0.9762F, -1.8126F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.9917F, 1.173F, -20.2518F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r228 = panel6.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(90, 39)
				.addBox(-0.4735F, -1.7068F, -0.4157F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(93, 42)
				.addBox(-0.4735F, -1.7068F, -3.1657F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.3699F, 5.552F, 8.9174F, 2.8536F, 1.0472F, -3.1416F));

		PartDefinition cube_r229 = panel6.addOrReplaceChild("cube_r229",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.4735F, -2.5768F, -2.8346F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.3699F, 5.552F, 8.9174F, 3.1416F, 1.0472F, -3.1416F));

		PartDefinition cube_r230 = panel6.addOrReplaceChild("cube_r230",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.4735F, -2.3268F, -2.8346F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.9911F, 5.302F, 18.3332F, 3.1416F, 0.0F, -3.1416F));

		PartDefinition cube_r231 = panel6.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(93, 42)
				.addBox(-0.4735F, -1.7444F, -3.0947F, 1.0F, 1.2773F, 3.0F, new CubeDeformation(0.0F)).texOffs(90, 39)
				.addBox(-0.4735F, -3.4671F, -0.3447F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.9911F, 5.302F, 18.3332F, 2.8536F, 0.0F, -3.1416F));

		PartDefinition cube_r232 = panel6.addOrReplaceChild("cube_r232",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.4735F, -2.0768F, -2.8346F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(30.326F, 5.052F, 8.872F, 3.1416F, -1.0472F, -3.1416F));

		PartDefinition cube_r233 = panel6.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(93, 42)
				.addBox(-0.4735F, -1.5047F, -3.0237F, 1.0F, 1.2773F, 3.0F, new CubeDeformation(0.0F)).texOffs(90, 39)
				.addBox(-0.4735F, -3.2274F, -0.2737F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(30.326F, 5.052F, 8.872F, 2.8536F, -1.0472F, -3.1416F));

		PartDefinition cube_r234 = panel6.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(90, 39)
				.addBox(-0.4735F, -0.9877F, -0.2027F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(93, 42)
				.addBox(-0.4735F, -0.9877F, -2.9527F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(30.2998F, 4.802F, -10.0049F, -0.288F, -1.0472F, 0.0F));

		PartDefinition cube_r235 = panel6.addOrReplaceChild("cube_r235",
				CubeListBuilder.create().texOffs(95, 44).addBox(-0.4735F, -1.8268F, -2.8346F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(30.2998F, 4.802F, -10.0049F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r236 = panel6.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(90, 39)
				.addBox(-0.4735F, -0.748F, -0.1317F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(93, 42)
				.addBox(-0.4735F, -0.748F, -2.8817F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(13.9382F, 4.552F, -19.4207F, -0.288F, 0.0F, 0.0F));

		PartDefinition cube_r237 = panel6.addOrReplaceChild("cube_r237",
				CubeListBuilder.create().texOffs(95, 44)
						.addBox(-0.5F, -27.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(95, 44)
						.addBox(-0.5F, -29.2602F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.5386F, 29.9594F, -10.6498F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r238 = panel6.addOrReplaceChild("cube_r238",
				CubeListBuilder.create().texOffs(90, 39)
						.addBox(-0.5F, -0.5F, -0.875F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(93, 42)
						.addBox(-0.5F, -0.5F, -3.625F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.7045F, 4.5095F, -9.5909F, -0.288F, 1.0472F, 0.0F));

		PartDefinition cube_r239 = panel6.addOrReplaceChild("cube_r239",
				CubeListBuilder.create().texOffs(93, 42).mirror()
						.addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(-1.3795F, 0.1468F, -9.4032F, 2.8798F, -1.0472F, -3.1416F));

		PartDefinition cube_r240 = panel6.addOrReplaceChild("cube_r240",
				CubeListBuilder.create().texOffs(93, 42).addBox(-0.5F, -0.4483F, -2.7371F, 1.0F, 1.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.4475F, 0.4238F, -10.0199F, 0.2618F, 1.0472F, 0.0F));

		PartDefinition cube_r241 = panel6.addOrReplaceChild("cube_r241",
				CubeListBuilder.create().texOffs(89, 208).addBox(4.8763F, -0.2683F, -3.3064F, 7.0F, 1.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.8535F, 0.0878F, 0.3325F, 0.0F, 1.5708F, -0.3054F));

		PartDefinition bone47 = panel6.addOrReplaceChild("bone47", CubeListBuilder.create(),
				PartPose.offset(-1.8535F, 0.0878F, 0.0825F));

		PartDefinition cube_r242 = bone47.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(39, 207)
				.addBox(-6.0737F, -0.5183F, 3.4436F, 12.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).texOffs(38, 207)
				.mirror().addBox(-5.7737F, -0.5183F, 2.4436F, 13.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
				.mirror(false).texOffs(38, 207)
				.addBox(-5.7737F, -0.5183F, 3.4436F, 13.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, -0.3054F));

		PartDefinition bone79 = bone47.addOrReplaceChild("bone79", CubeListBuilder.create(),
				PartPose.offset(4.4681F, -1.8824F, 4.2637F));

		PartDefinition cube_r243 = bone79.addOrReplaceChild("cube_r243",
				CubeListBuilder.create().texOffs(6, 233)
						.addBox(-3.021F, -2.1699F, -2.85F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(3, 242)
						.addBox(-3.171F, -2.0948F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(10, 206)
						.addBox(-3.171F, -1.9698F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)),
				PartPose.offsetAndRotation(0.094F, 0.5829F, 0.06F, -3.1416F, 0.0F, 2.7925F));

		PartDefinition bone83 = bone47.addOrReplaceChild("bone83", CubeListBuilder.create(),
				PartPose.offset(4.2181F, -1.8824F, -2.2363F));

		PartDefinition cube_r244 = bone83.addOrReplaceChild("cube_r244",
				CubeListBuilder.create().texOffs(6, 233)
						.addBox(-3.021F, -2.1699F, -2.85F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(3, 242)
						.addBox(-3.171F, -2.0948F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(10, 206)
						.addBox(-3.171F, -1.9698F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)),
				PartPose.offsetAndRotation(0.094F, 0.5829F, 0.06F, -3.1416F, 0.0F, 2.7925F));

		PartDefinition bone81 = bone47.addOrReplaceChild("bone81", CubeListBuilder.create(),
				PartPose.offsetAndRotation(3.7847F, -1.589F, 1.5137F, 3.1416F, 0.0F, 2.1817F));

		PartDefinition cube_r245 = bone81.addOrReplaceChild("cube_r245",
				CubeListBuilder.create().texOffs(6, 215)
						.addBox(-3.021F, -2.2198F, -2.85F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(3, 242)
						.addBox(-3.171F, -2.0948F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(10, 206)
						.addBox(-3.171F, -1.9698F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)),
				PartPose.offsetAndRotation(0.094F, 0.5828F, 0.06F, -3.1416F, 0.0F, 2.7925F));

		PartDefinition bone80 = bone47.addOrReplaceChild("bone80", CubeListBuilder.create(),
				PartPose.offsetAndRotation(3.7847F, -1.589F, -5.2363F, 3.1416F, 0.0F, 2.1817F));

		PartDefinition cube_r246 = bone80.addOrReplaceChild("cube_r246",
				CubeListBuilder.create().texOffs(6, 224)
						.addBox(-3.021F, -2.2198F, -2.85F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(3, 242)
						.addBox(-3.171F, -2.0948F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)).texOffs(10, 206)
						.addBox(-3.171F, -1.9698F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-1.2F)),
				PartPose.offsetAndRotation(0.094F, 0.5828F, 0.06F, -3.1416F, 0.0F, 2.7925F));

		PartDefinition bone10 = panel6.addOrReplaceChild("bone10", CubeListBuilder.create(),
				PartPose.offset(-1.8535F, 0.0878F, 0.0825F));

		PartDefinition cube_r247 = bone10.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(63, 240)
				.addBox(-5.3737F, -0.1781F, -3.0672F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(63, 243)
				.addBox(-5.1237F, -0.1781F, -3.0672F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(63, 234)
				.addBox(-5.3737F, -0.2281F, -3.0672F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(63, 237)
				.addBox(-5.1237F, -0.2281F, -3.0672F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(39, 234)
				.addBox(-5.1237F, -0.1281F, -3.3172F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, -0.2182F));

		PartDefinition cube_r248 = bone10.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(102, 239)
				.addBox(-0.95F, -1.0474F, -0.8331F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.75F)).texOffs(102, 239)
				.addBox(-0.2F, -1.0474F, -0.8331F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.75F)).texOffs(102, 239)
				.addBox(0.55F, -1.0474F, -0.8331F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.75F)),
				PartPose.offsetAndRotation(-1.9455F, 0.3396F, -3.828F, 0.3054F, 1.5708F, 0.0F));

		PartDefinition bone48 = panel6.addOrReplaceChild("bone48", CubeListBuilder.create(),
				PartPose.offset(-3.2372F, 1.1699F, 6.7062F));

		PartDefinition cube_r249 = bone48.addOrReplaceChild("cube_r249",
				CubeListBuilder.create().texOffs(82, 208).addBox(-1.8559F, -1.4435F, -1.5F, 3.0F, 2.0F, 3.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 2.6616F));

		PartDefinition cube_r250 = bone48.addOrReplaceChild("cube_r250",
				CubeListBuilder.create().texOffs(138, 507).addBox(-1.8686F, -1.5378F, -8.875F, 3.0F, 2.0F, 3.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(0.0802F, -0.2948F, -7.375F, 3.1416F, 0.0F, 2.3126F));

		PartDefinition bone49 = panel6.addOrReplaceChild("bone49", CubeListBuilder.create(),
				PartPose.offset(-3.2372F, 1.1699F, 6.7062F));

		PartDefinition cube_r251 = bone49.addOrReplaceChild("cube_r251",
				CubeListBuilder.create().texOffs(82, 208).addBox(-1.8559F, -1.4435F, -1.5F, 3.0F, 2.0F, 3.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -14.75F, 3.1416F, 0.0F, 2.6616F));

		PartDefinition cube_r252 = bone49.addOrReplaceChild("cube_r252",
				CubeListBuilder.create().texOffs(138, 507).addBox(-1.8686F, -1.5378F, -8.875F, 3.0F, 2.0F, 3.0F,
						new CubeDeformation(-0.4F)),
				PartPose.offsetAndRotation(0.0802F, -0.2948F, -22.125F, 3.1416F, 0.0F, 2.3126F));

		PartDefinition rotor = base.addOrReplaceChild("rotor", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0375F, -2.01F, -0.065F, 0.0F, -0.5236F, 0.0F));

		PartDefinition rotorTop = rotor.addOrReplaceChild("rotorTop", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.1732F, 2.01F, -0.0134F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone53 = rotorTop.addOrReplaceChild("bone53", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-4.4881F, -54.6909F, -0.16F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r253 = bone53.addOrReplaceChild("cube_r253",
				CubeListBuilder.create().texOffs(492, 110).addBox(-1.232F, 0.7322F, 1.2211F, 9.12F, 0.76F, 0.76F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.2348F, -0.0957F, -1.2266F, 0.5156F, 0.0955F, 1.404F));

		PartDefinition cube_r254 = bone53.addOrReplaceChild("cube_r254",
				CubeListBuilder.create().texOffs(463, 117).addBox(-1.102F, -1.0602F, -3.022F, 9.0F, 1.0F, 6.84F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.2066F, 0.0128F, -2.1452F, 0.0F, 0.0F, 1.3963F));

		PartDefinition cube_r255 = bone53.addOrReplaceChild("cube_r255",
				CubeListBuilder.create().texOffs(492, 110).addBox(-2.6854F, 0.1335F, 0.0334F, 9.12F, 0.76F, 0.76F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.9544F, 1.4347F, -4.377F, -0.5156F, -0.0955F, 1.404F));

		PartDefinition cube_r256 = bone53.addOrReplaceChild("cube_r256",
				CubeListBuilder.create().texOffs(499, 87).addBox(-0.9149F, -0.6492F, -1.6621F, 0.76F, 0.76F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.3588F, -0.1387F, -2.6571F, 0.0F, 0.0F, 1.3963F));

		PartDefinition bone55 = rotorTop.addOrReplaceChild("bone55", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-2.4882F, -54.6909F, 3.3041F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r257 = bone55.addOrReplaceChild("cube_r257",
				CubeListBuilder.create().texOffs(463, 117).addBox(-1.102F, -0.9602F, -4.2721F, 9.0F, 1.0F, 6.84F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.844F, 0.0232F, -0.0549F, 0.0F, 0.0F, 1.3963F));

		PartDefinition cube_r258 = bone55.addOrReplaceChild("cube_r258",
				CubeListBuilder.create().texOffs(492, 110).addBox(-2.6854F, -0.0665F, -0.2934F, 9.12F, 0.76F, 0.76F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.0842F, 1.4937F, 1.1787F, 0.5156F, 0.0955F, 1.404F));

		PartDefinition cube_r259 = bone55.addOrReplaceChild("cube_r259",
				CubeListBuilder.create().texOffs(499, 87).addBox(-0.9148F, -0.6492F, -3.0621F, 0.76F, 0.76F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.9962F, -0.1283F, -0.4168F, 0.0F, 0.0F, 1.3963F));

		PartDefinition bone52 = bone55.addOrReplaceChild("bone52", CubeListBuilder.create(),
				PartPose.offset(-0.844F, 0.0232F, 0.0951F));

		PartDefinition cube_r260 = bone52.addOrReplaceChild(
				"cube_r260", CubeListBuilder.create().texOffs(505, 100).addBox(3.4862F, 0.8007F, -1.8621F, 1.52F, 1.52F,
						1.52F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r261 = bone52.addOrReplaceChild(
				"cube_r261", CubeListBuilder.create().texOffs(504, 112).addBox(1.8823F, -1.9831F, -1.8621F, 2.28F,
						1.52F, 1.52F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.6581F));

		PartDefinition bone56 = rotorTop.addOrReplaceChild("bone56", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-2.4882F, -54.6909F, 3.3041F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r262 = bone56.addOrReplaceChild("cube_r262",
				CubeListBuilder.create().texOffs(463, 117).addBox(-1.117F, -0.9749F, -3.0721F, 9.0F, 1.0F, 6.84F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.9027F, 0.0128F, 2.5886F, 0.0F, 0.0F, 1.3963F));

		PartDefinition cube_r263 = bone56.addOrReplaceChild("cube_r263",
				CubeListBuilder.create().texOffs(499, 87).addBox(-0.9299F, -0.5639F, -1.6621F, 0.76F, 0.76F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.956F, -0.1179F, 2.0514F, 0.0F, 0.0F, 1.3963F));

		PartDefinition bone57 = rotorTop.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(476, 99)
				.addBox(-5.5058F, 7.6252F, -1.2227F, 9.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(458, 99)
				.addBox(-5.5058F, -0.6248F, -1.2227F, 9.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.4882F, -54.6909F, 3.3041F, -3.1416F, 0.5236F, 3.1416F));

		PartDefinition cube_r264 = bone57.addOrReplaceChild("cube_r264",
				CubeListBuilder.create().texOffs(463, 117).addBox(-1.132F, -1.0396F, -4.5221F, 9.0F, 1.0F, 6.84F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.7904F, 0.0128F, 5.1093F, 0.0F, 0.0F, 1.3963F));

		PartDefinition cube_r265 = bone57.addOrReplaceChild("cube_r265",
				CubeListBuilder.create().texOffs(492, 110).addBox(-2.7044F, 0.0316F, -0.2934F, 9.12F, 0.76F, 0.76F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.8495F, 1.5041F, 1.5943F, -0.5156F, -0.0955F, 1.404F));

		PartDefinition cube_r266 = bone57.addOrReplaceChild("cube_r266",
				CubeListBuilder.create().texOffs(499, 87).addBox(-0.9449F, -0.4786F, -3.0621F, 0.76F, 0.76F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.7426F, -0.1074F, 4.4793F, 0.0F, 0.0F, 1.3963F));

		PartDefinition bone61 = bone57.addOrReplaceChild("bone61", CubeListBuilder.create(),
				PartPose.offset(-5.5404F, 0.0128F, 5.0093F));

		PartDefinition cube_r267 = bone61.addOrReplaceChild(
				"cube_r267", CubeListBuilder.create().texOffs(505, 100).addBox(3.3868F, 0.9426F, -1.8621F, 1.52F, 1.52F,
						1.52F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r268 = bone61.addOrReplaceChild(
				"cube_r268", CubeListBuilder.create().texOffs(504, 112).addBox(1.8974F, -1.8106F, -1.8621F, 2.28F,
						1.52F, 1.52F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.6581F));

		PartDefinition bone58 = rotorTop.addOrReplaceChild("bone58", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-2.4882F, -54.6909F, 3.3041F, -3.1416F, -0.5236F, 3.1416F));

		PartDefinition cube_r269 = bone58.addOrReplaceChild("cube_r269",
				CubeListBuilder.create().texOffs(463, 117).addBox(-20.5499F, -8.1123F, -4.7951F, 9.0F, 1.0F, 6.84F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-12.2117F, 20.3638F, 2.4759F, 0.0F, 0.0F, 1.3963F));

		PartDefinition cube_r270 = bone58.addOrReplaceChild("cube_r270",
				CubeListBuilder.create().texOffs(492, 110).addBox(-2.714F, 0.2807F, 0.12F, 9.12F, 0.76F, 0.76F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.2562F, 1.4764F, -1.4809F, -0.5156F, -0.0955F, 1.404F));

		PartDefinition cube_r271 = bone58.addOrReplaceChild("cube_r271",
				CubeListBuilder.create().texOffs(499, 87).addBox(-20.3628F, -7.5513F, -3.5351F, 0.76F, 0.76F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-12.1627F, 20.254F, 2.1119F, 0.0F, 0.0F, 1.3963F));

		PartDefinition bone59 = rotorTop.addOrReplaceChild("bone59", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-2.4882F, -54.6909F, 3.3041F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r272 = bone59.addOrReplaceChild("cube_r272",
				CubeListBuilder.create().texOffs(463, 117).addBox(-1.117F, -1.0249F, -3.0721F, 9.0F, 1.0F, 6.84F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.5585F, 0.0128F, -3.1244F, 0.0F, 0.0F, 1.3963F));

		PartDefinition cube_r273 = bone59.addOrReplaceChild("cube_r273",
				CubeListBuilder.create().texOffs(492, 110).addBox(-2.7044F, 0.2316F, 0.0334F, 9.12F, 0.76F, 0.76F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.2963F, 1.4868F, -5.2991F, -0.5156F, -0.0955F, 1.404F));

		PartDefinition cube_r274 = bone59.addOrReplaceChild("cube_r274",
				CubeListBuilder.create().texOffs(499, 87).addBox(-0.9299F, -0.5639F, -1.7121F, 0.76F, 0.76F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.6094F, -0.1491F, -3.5793F, 0.0F, 0.0F, 1.3963F));

		PartDefinition bone60 = bone59.addOrReplaceChild("bone60", CubeListBuilder.create(),
				PartPose.offset(-7.4085F, 0.0128F, -3.1244F));

		PartDefinition cube_r275 = bone60.addOrReplaceChild(
				"cube_r275", CubeListBuilder.create().texOffs(505, 100).addBox(3.4365F, 0.8717F, -0.5121F, 1.52F, 1.52F,
						1.52F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r276 = bone60.addOrReplaceChild(
				"cube_r276", CubeListBuilder.create().texOffs(504, 112).addBox(1.8898F, -1.8968F, -0.5121F, 2.28F,
						1.52F, 1.52F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.6581F));

		PartDefinition rotorBase = rotor.addOrReplaceChild("rotorBase", CubeListBuilder.create(),
				PartPose.offset(0.402F, -13.735F, 0.0F));

		PartDefinition rotorglass = rotorBase.addOrReplaceChild("rotorglass", CubeListBuilder.create().texOffs(148, 0)
				.addBox(1.5471F, -33.43F, -3.8538F, 4.0F, 28.0F, 8.0F, new CubeDeformation(-2.0F)),
				PartPose.offset(-0.1112F, 0.0F, 0.0722F));

		PartDefinition cube_r277 = rotorglass.addOrReplaceChild("cube_r277",
				CubeListBuilder.create().texOffs(148, 0).addBox(-0.4335F, -35.45F, -2.135F, 4.0F, 28.0F, 8.0F,
						new CubeDeformation(-2.0F)),
				PartPose.offsetAndRotation(0.7494F, 2.02F, 2.722F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r278 = rotorglass.addOrReplaceChild("cube_r278",
				CubeListBuilder.create().texOffs(148, 0).addBox(-0.4335F, -35.45F, -1.885F, 4.0F, 28.0F, 8.0F,
						new CubeDeformation(-2.0F)),
				PartPose.offsetAndRotation(2.8635F, 2.02F, 0.7321F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r279 = rotorglass.addOrReplaceChild("cube_r279",
				CubeListBuilder.create().texOffs(148, 0).addBox(-0.8665F, -35.45F, -1.885F, 4.0F, 28.0F, 8.0F,
						new CubeDeformation(-2.0F)),
				PartPose.offsetAndRotation(-0.5833F, 2.02F, -2.9297F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r280 = rotorglass.addOrReplaceChild("cube_r280",
				CubeListBuilder.create().texOffs(148, 0).addBox(-0.8665F, -35.45F, -2.135F, 4.0F, 28.0F, 8.0F,
						new CubeDeformation(-2.0F)),
				PartPose.offsetAndRotation(-2.6974F, 2.02F, -0.9397F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r281 = rotorglass.addOrReplaceChild("cube_r281",
				CubeListBuilder.create().texOffs(148, 0).addBox(-0.65F, -35.45F, -2.26F, 4.0F, 28.0F, 8.0F,
						new CubeDeformation(-2.0F)),
				PartPose.offsetAndRotation(-2.0311F, 2.02F, 1.8862F, 0.0F, 3.1416F, 0.0F));

		PartDefinition rotorBaseBit = rotorBase.addOrReplaceChild("rotorBaseBit", CubeListBuilder.create()
				.texOffs(0, 258).addBox(1.4157F, -7.43F, -1.8538F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0202F, 0.0F, 0.0722F));

		PartDefinition cube_r282 = rotorBaseBit.addOrReplaceChild(
				"cube_r282", CubeListBuilder.create().texOffs(0, 258).addBox(-2.4335F, -7.43F, -1.885F, 6.0F, 4.0F,
						4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r283 = rotorBaseBit.addOrReplaceChild("cube_r283",
				CubeListBuilder.create().texOffs(4, 258).addBox(1.5665F, -7.43F, -2.135F, 2.0F, 4.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1141F, 0.0F, -0.01F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition cube_r284 = rotorBaseBit.addOrReplaceChild("cube_r284",
				CubeListBuilder.create().texOffs(0, 258).addBox(1.35F, -7.43F, -2.26F, 2.0F, 4.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1625F, 0.0F, -0.1138F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r285 = rotorBaseBit.addOrReplaceChild("cube_r285",
				CubeListBuilder.create().texOffs(0, 258).addBox(1.1335F, -7.43F, -1.885F, 2.0F, 4.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0173F, 0.0F, -0.1976F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r286 = rotorBaseBit.addOrReplaceChild("cube_r286",
				CubeListBuilder.create().texOffs(1, 258).addBox(2.1335F, -7.43F, -2.115F, 1.0F, 4.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1141F, 0.0F, -0.1976F, 0.0F, 2.0944F, 0.0F));

		PartDefinition rotorBaseBit2 = rotorBase
				.addOrReplaceChild(
						"rotorBaseBit2", CubeListBuilder.create().texOffs(104, 169).addBox(3.666F, -3.75F, -2.0283F,
								1.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)),
						PartPose.offset(-0.3716F, -0.25F, -0.2433F));

		PartDefinition cube_r287 = rotorBaseBit2.addOrReplaceChild(
				"cube_r287", CubeListBuilder.create().texOffs(104, 169).addBox(3.9065F, -3.75F, -2.555F, 1.0F, 3.0F,
						5.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r288 = rotorBaseBit2.addOrReplaceChild("cube_r288",
				CubeListBuilder.create().texOffs(104, 169).addBox(3.9065F, -3.75F, -2.805F, 1.0F, 3.0F, 5.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(0.3599F, 0.0F, -0.18F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r289 = rotorBaseBit2.addOrReplaceChild("cube_r289",
				CubeListBuilder.create().texOffs(104, 169).addBox(3.69F, -3.75F, -2.93F, 1.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.6957F, 0.0F, 0.0417F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r290 = rotorBaseBit2.addOrReplaceChild("cube_r290",
				CubeListBuilder.create().texOffs(104, 169).addBox(3.4735F, -3.75F, -2.805F, 1.0F, 3.0F, 5.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(0.6716F, 0.0F, 0.4433F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r291 = rotorBaseBit2.addOrReplaceChild("cube_r291",
				CubeListBuilder.create().texOffs(104, 169).addBox(3.4735F, -3.75F, -2.555F, 1.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.3118F, 0.0F, 0.6233F, 0.0F, 1.0472F, 0.0F));

		PartDefinition rotorBaseBit3 = rotorBase.addOrReplaceChild("rotorBaseBit3", CubeListBuilder.create()
				.texOffs(136, 245).addBox(3.8881F, -1.45F, -3.4823F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.1222F, 0.25F, 0.1767F));

		PartDefinition cube_r292 = rotorBaseBit3.addOrReplaceChild(
				"cube_r292", CubeListBuilder.create().texOffs(243, 149).addBox(4.1645F, -1.75F, -3.3581F, 2.0F, 3.0F,
						7.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.3F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r293 = rotorBaseBit3.addOrReplaceChild("cube_r293",
				CubeListBuilder.create().texOffs(136, 245).addBox(4.5145F, -1.75F, -3.6081F, 2.0F, 3.0F, 7.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-0.4939F, 0.3F, -0.32F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r294 = rotorBaseBit3.addOrReplaceChild("cube_r294",
				CubeListBuilder.create().texOffs(136, 245).addBox(3.2155F, -1.75F, -2.0019F, 2.0F, 3.0F, 7.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-1.0481F, 0.3F, -1.4646F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r295 = rotorBaseBit3.addOrReplaceChild("cube_r295",
				CubeListBuilder.create().texOffs(243, 129).addBox(2.8655F, -1.75F, -2.7519F, 2.0F, 3.0F, 7.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.4203F, 0.3F, -0.6446F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r296 = rotorBaseBit3.addOrReplaceChild("cube_r296",
				CubeListBuilder.create().texOffs(243, 139).addBox(3.34F, -1.75F, -3.43F, 2.0F, 3.0F, 7.0F,
						new CubeDeformation(-0.01F)),
				PartPose.offsetAndRotation(-0.8962F, 0.3F, 0.0877F, 0.0F, 3.1416F, 0.0F));

		PartDefinition rotorBaseBit4 = rotorBase.addOrReplaceChild("rotorBaseBit4", CubeListBuilder.create()
				.texOffs(358, 289).addBox(3.6381F, -0.45F, -4.4823F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.1222F, 0.25F, 0.1767F));

		PartDefinition cube_r297 = rotorBaseBit4.addOrReplaceChild(
				"cube_r297", CubeListBuilder.create().texOffs(358, 289).addBox(3.9145F, -0.75F, -4.3581F, 4.0F, 2.0F,
						9.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.3F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition cube_r298 = rotorBaseBit4.addOrReplaceChild("cube_r298",
				CubeListBuilder.create().texOffs(358, 289).addBox(4.2645F, -0.75F, -4.6081F, 4.0F, 2.0F, 9.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.4939F, 0.3F, -0.32F, 0.0F, -1.0472F, 0.0F));

		PartDefinition cube_r299 = rotorBaseBit4.addOrReplaceChild("cube_r299",
				CubeListBuilder.create().texOffs(358, 289).addBox(2.9655F, -0.75F, -3.0019F, 4.0F, 2.0F, 9.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0481F, 0.3F, -1.4646F, 0.0F, 1.0472F, 0.0F));

		PartDefinition cube_r300 = rotorBaseBit4.addOrReplaceChild("cube_r300",
				CubeListBuilder.create().texOffs(358, 289).addBox(2.6155F, -0.75F, -3.7519F, 4.0F, 2.0F, 9.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.4203F, 0.3F, -0.6446F, 0.0F, 2.0944F, 0.0F));

		PartDefinition cube_r301 = rotorBaseBit4.addOrReplaceChild("cube_r301",
				CubeListBuilder.create().texOffs(358, 289).addBox(3.09F, -0.75F, -4.43F, 4.0F, 2.0F, 9.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.8962F, 0.3F, 0.0877F, 0.0F, 3.1416F, 0.0F));

		PartDefinition timeColumn = rotor.addOrReplaceChild("timeColumn", CubeListBuilder.create().texOffs(293, 178)
				.addBox(-0.1F, -10.87F, -0.71F, 0.0F, 18.24F, 1.52F, new CubeDeformation(0.0F)),
				PartPose.offset(0.5F, -31.43F, 0.15F));

		PartDefinition cube_r302 = timeColumn.addOrReplaceChild("cube_r302",
				CubeListBuilder.create().texOffs(297, 178).addBox(0.0F, -43.06F, -0.76F, 0.0F, 18.24F, 1.52F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1F, 32.19F, 0.05F, 0.0F, 1.5708F, 0.0F));

		PartDefinition timeColumnBit = timeColumn.addOrReplaceChild("timeColumnBit", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.1F, 10.53F, 0.05F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r303 = timeColumnBit.addOrReplaceChild("cube_r303",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1767F, 0.0F, 0.2383F, 0.0F, 1.0472F, 3.1416F));

		PartDefinition cube_r304 = timeColumnBit.addOrReplaceChild("cube_r304",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.3143F, 0.0F, -0.008F, -3.1416F, 1.0472F, 0.0F));

		PartDefinition cube_r305 = timeColumnBit.addOrReplaceChild("cube_r305",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1697F, 0.0F, -0.2504F, -3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r306 = timeColumnBit.addOrReplaceChild("cube_r306",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1124F, 0.0F, -0.2464F, -3.1416F, -1.0472F, 0.0F));

		PartDefinition cube_r307 = timeColumnBit.addOrReplaceChild("cube_r307",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0F, -1.0472F, -3.1416F));

		PartDefinition cube_r308 = timeColumnBit.addOrReplaceChild("cube_r308",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1055F, 0.0F, 0.2413F, 0.0F, 0.0F, -3.1416F));

		PartDefinition timeColumnBit2 = timeColumn.addOrReplaceChild("timeColumnBit2", CubeListBuilder.create(),
				PartPose.offset(-0.1F, -13.97F, 0.05F));

		PartDefinition cube_r309 = timeColumnBit2.addOrReplaceChild("cube_r309",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1767F, 0.0F, 0.2383F, 0.0F, 1.0472F, 3.1416F));

		PartDefinition cube_r310 = timeColumnBit2.addOrReplaceChild("cube_r310",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.3143F, 0.0F, -0.008F, -3.1416F, 1.0472F, 0.0F));

		PartDefinition cube_r311 = timeColumnBit2.addOrReplaceChild("cube_r311",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.1697F, 0.0F, -0.2504F, -3.1416F, 0.0F, 0.0F));

		PartDefinition cube_r312 = timeColumnBit2.addOrReplaceChild("cube_r312",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1124F, 0.0F, -0.2464F, -3.1416F, -1.0472F, 0.0F));

		PartDefinition cube_r313 = timeColumnBit2.addOrReplaceChild("cube_r313",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0F, -1.0472F, -3.1416F));

		PartDefinition cube_r314 = timeColumnBit2.addOrReplaceChild("cube_r314",
				CubeListBuilder.create().texOffs(305, 190).addBox(-0.8624F, -3.1576F, -1.9784F, 2.0F, 5.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.1055F, 0.0F, 0.2413F, 0.0F, 0.0F, -3.1416F));

		PartDefinition timeColumnFins1 = timeColumn.addOrReplaceChild("timeColumnFins1", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.1F, 3.31F, 0.05F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone15 = timeColumnFins1.addOrReplaceChild("bone15", CubeListBuilder.create(),
				PartPose.offset(-1.54F, -13.94F, 0.0F));

		PartDefinition cube_28_r1 = bone15.addOrReplaceChild("cube_28_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone21 = timeColumnFins1.addOrReplaceChild("bone21", CubeListBuilder.create(),
				PartPose.offset(-1.74F, -13.19F, 0.0F));

		PartDefinition cube_27_r1 = bone21.addOrReplaceChild("cube_27_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 17.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_112 = timeColumnFins1.addOrReplaceChild("cube_112", CubeListBuilder.create(),
				PartPose.offset(-1.84F, -12.44F, 0.0F));

		PartDefinition cube_26_r1 = cube_112.addOrReplaceChild("cube_26_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -16.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 16.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_113 = timeColumnFins1.addOrReplaceChild("cube_113", CubeListBuilder.create(),
				PartPose.offset(-1.94F, -11.69F, 0.0F));

		PartDefinition cube_25_r1 = cube_113.addOrReplaceChild("cube_25_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -15.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_114 = timeColumnFins1.addOrReplaceChild("cube_114", CubeListBuilder.create(),
				PartPose.offset(-2.09F, -10.94F, 0.0F));

		PartDefinition cube_24_r1 = cube_114.addOrReplaceChild("cube_24_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_115 = timeColumnFins1.addOrReplaceChild("cube_115", CubeListBuilder.create(),
				PartPose.offset(-2.14F, -10.19F, 0.0F));

		PartDefinition cube_23_r1 = cube_115.addOrReplaceChild("cube_23_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 14.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_116 = timeColumnFins1.addOrReplaceChild("cube_116", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -9.44F, 0.0F));

		PartDefinition cube_22_r1 = cube_116.addOrReplaceChild("cube_22_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -13.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 13.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_117 = timeColumnFins1.addOrReplaceChild("cube_117", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -8.69F, 0.0F));

		PartDefinition cube_21_r1 = cube_117.addOrReplaceChild("cube_21_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -12.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_118 = timeColumnFins1.addOrReplaceChild("cube_118", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -7.94F, 0.0F));

		PartDefinition cube_20_r1 = cube_118.addOrReplaceChild("cube_20_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_119 = timeColumnFins1.addOrReplaceChild("cube_119", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -7.19F, 0.0F));

		PartDefinition cube_19_r1 = cube_119.addOrReplaceChild("cube_19_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 11.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_120 = timeColumnFins1.addOrReplaceChild("cube_120", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -6.44F, 0.0F));

		PartDefinition cube_18_r1 = cube_120.addOrReplaceChild("cube_18_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -10.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 10.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_121 = timeColumnFins1.addOrReplaceChild("cube_121", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -5.69F, 0.0F));

		PartDefinition cube_17_r1 = cube_121.addOrReplaceChild("cube_17_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -9.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_122 = timeColumnFins1.addOrReplaceChild("cube_122", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.94F, 0.0F));

		PartDefinition cube_16_r1 = cube_122.addOrReplaceChild("cube_16_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_123 = timeColumnFins1.addOrReplaceChild("cube_123", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.19F, 0.0F));

		PartDefinition cube_15_r1 = cube_123.addOrReplaceChild("cube_15_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 8.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_124 = timeColumnFins1.addOrReplaceChild("cube_124", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -3.44F, 0.0F));

		PartDefinition cube_14_r1 = cube_124.addOrReplaceChild("cube_14_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -7.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 7.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_125 = timeColumnFins1.addOrReplaceChild("cube_125", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -2.69F, 0.0F));

		PartDefinition cube_13_r1 = cube_125.addOrReplaceChild("cube_13_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -6.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_126 = timeColumnFins1.addOrReplaceChild("cube_126", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -1.94F, 0.0F));

		PartDefinition cube_12_r1 = cube_126.addOrReplaceChild("cube_12_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_127 = timeColumnFins1.addOrReplaceChild("cube_127", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -1.19F, 0.0F));

		PartDefinition cube_11_r1 = cube_127.addOrReplaceChild("cube_11_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 5.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_128 = timeColumnFins1.addOrReplaceChild("cube_128", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -0.44F, 0.0F));

		PartDefinition cube_10_r1 = cube_128.addOrReplaceChild("cube_10_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -4.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 4.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_129 = timeColumnFins1.addOrReplaceChild("cube_129", CubeListBuilder.create(),
				PartPose.offset(-2.14F, 0.31F, 0.0F));

		PartDefinition cube_9_r1 = cube_129.addOrReplaceChild("cube_9_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -3.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_130 = timeColumnFins1.addOrReplaceChild("cube_130", CubeListBuilder.create(),
				PartPose.offset(-2.04F, 1.06F, 0.0F));

		PartDefinition cube_8_r1 = cube_130.addOrReplaceChild("cube_8_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_131 = timeColumnFins1.addOrReplaceChild("cube_131", CubeListBuilder.create(),
				PartPose.offset(-1.94F, 1.81F, 0.0F));

		PartDefinition cube_7_r1 = cube_131.addOrReplaceChild("cube_7_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 2.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_132 = timeColumnFins1.addOrReplaceChild("cube_132", CubeListBuilder.create(),
				PartPose.offset(-1.84F, 2.56F, 0.0F));

		PartDefinition cube_6_r1 = cube_132.addOrReplaceChild("cube_6_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -1.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 1.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_133 = timeColumnFins1.addOrReplaceChild("cube_133", CubeListBuilder.create(),
				PartPose.offset(-1.74F, 3.31F, 0.0F));

		PartDefinition cube_5_r1 = cube_133.addOrReplaceChild("cube_5_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -0.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone22 = timeColumnFins1.addOrReplaceChild("bone22", CubeListBuilder.create(),
				PartPose.offset(-1.49F, 4.06F, 0.0F));

		PartDefinition cube_r315 = bone22.addOrReplaceChild("cube_r315",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, 0.0024F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition timeColumnFins2 = timeColumn.addOrReplaceChild("timeColumnFins2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.1F, 3.31F, 0.05F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone23 = timeColumnFins2.addOrReplaceChild("bone23", CubeListBuilder.create(),
				PartPose.offset(-1.54F, -13.94F, 0.0F));

		PartDefinition cube_29_r1 = bone23.addOrReplaceChild("cube_29_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone24 = timeColumnFins2.addOrReplaceChild("bone24", CubeListBuilder.create(),
				PartPose.offset(-1.74F, -13.19F, 0.0F));

		PartDefinition cube_28_r2 = bone24.addOrReplaceChild("cube_28_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 17.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_2 = timeColumnFins2.addOrReplaceChild("cube_2", CubeListBuilder.create(),
				PartPose.offset(-1.84F, -12.44F, 0.0F));

		PartDefinition cube_27_r2 = cube_2.addOrReplaceChild("cube_27_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -16.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 16.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_3 = timeColumnFins2.addOrReplaceChild("cube_3", CubeListBuilder.create(),
				PartPose.offset(-1.94F, -11.69F, 0.0F));

		PartDefinition cube_26_r2 = cube_3.addOrReplaceChild("cube_26_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -15.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_4 = timeColumnFins2.addOrReplaceChild("cube_4", CubeListBuilder.create(),
				PartPose.offset(-2.09F, -10.94F, 0.0F));

		PartDefinition cube_25_r2 = cube_4.addOrReplaceChild("cube_25_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_5 = timeColumnFins2.addOrReplaceChild("cube_5", CubeListBuilder.create(),
				PartPose.offset(-2.14F, -10.19F, 0.0F));

		PartDefinition cube_24_r2 = cube_5.addOrReplaceChild("cube_24_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 14.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_6 = timeColumnFins2.addOrReplaceChild("cube_6", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -9.44F, 0.0F));

		PartDefinition cube_23_r2 = cube_6.addOrReplaceChild("cube_23_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -13.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 13.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_7 = timeColumnFins2.addOrReplaceChild("cube_7", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -8.69F, 0.0F));

		PartDefinition cube_22_r2 = cube_7.addOrReplaceChild("cube_22_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -12.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_8 = timeColumnFins2.addOrReplaceChild("cube_8", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -7.94F, 0.0F));

		PartDefinition cube_21_r2 = cube_8.addOrReplaceChild("cube_21_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_9 = timeColumnFins2.addOrReplaceChild("cube_9", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -7.19F, 0.0F));

		PartDefinition cube_20_r2 = cube_9.addOrReplaceChild("cube_20_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 11.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_10 = timeColumnFins2.addOrReplaceChild("cube_10", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -6.44F, 0.0F));

		PartDefinition cube_19_r2 = cube_10.addOrReplaceChild("cube_19_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -10.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 10.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_11 = timeColumnFins2.addOrReplaceChild("cube_11", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -5.69F, 0.0F));

		PartDefinition cube_18_r2 = cube_11.addOrReplaceChild("cube_18_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -9.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_12 = timeColumnFins2.addOrReplaceChild("cube_12", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.94F, 0.0F));

		PartDefinition cube_17_r2 = cube_12.addOrReplaceChild("cube_17_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_13 = timeColumnFins2.addOrReplaceChild("cube_13", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.19F, 0.0F));

		PartDefinition cube_16_r2 = cube_13.addOrReplaceChild("cube_16_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 8.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_14 = timeColumnFins2.addOrReplaceChild("cube_14", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -3.44F, 0.0F));

		PartDefinition cube_15_r2 = cube_14.addOrReplaceChild("cube_15_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -7.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 7.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_15 = timeColumnFins2.addOrReplaceChild("cube_15", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -2.69F, 0.0F));

		PartDefinition cube_14_r2 = cube_15.addOrReplaceChild("cube_14_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -6.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_16 = timeColumnFins2.addOrReplaceChild("cube_16", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -1.94F, 0.0F));

		PartDefinition cube_13_r2 = cube_16.addOrReplaceChild("cube_13_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_17 = timeColumnFins2.addOrReplaceChild("cube_17", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -1.19F, 0.0F));

		PartDefinition cube_12_r2 = cube_17.addOrReplaceChild("cube_12_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 5.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_18 = timeColumnFins2.addOrReplaceChild("cube_18", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -0.44F, 0.0F));

		PartDefinition cube_11_r2 = cube_18.addOrReplaceChild("cube_11_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -4.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 4.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_19 = timeColumnFins2.addOrReplaceChild("cube_19", CubeListBuilder.create(),
				PartPose.offset(-2.14F, 0.31F, 0.0F));

		PartDefinition cube_10_r2 = cube_19.addOrReplaceChild("cube_10_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -3.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_20 = timeColumnFins2.addOrReplaceChild("cube_20", CubeListBuilder.create(),
				PartPose.offset(-2.04F, 1.06F, 0.0F));

		PartDefinition cube_9_r2 = cube_20.addOrReplaceChild("cube_9_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_21 = timeColumnFins2.addOrReplaceChild("cube_21", CubeListBuilder.create(),
				PartPose.offset(-1.94F, 1.81F, 0.0F));

		PartDefinition cube_8_r2 = cube_21.addOrReplaceChild("cube_8_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 2.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_22 = timeColumnFins2.addOrReplaceChild("cube_22", CubeListBuilder.create(),
				PartPose.offset(-1.84F, 2.56F, 0.0F));

		PartDefinition cube_7_r2 = cube_22.addOrReplaceChild("cube_7_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -1.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 1.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_23 = timeColumnFins2.addOrReplaceChild("cube_23", CubeListBuilder.create(),
				PartPose.offset(-1.74F, 3.31F, 0.0F));

		PartDefinition cube_6_r2 = cube_23.addOrReplaceChild("cube_6_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -0.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone41 = timeColumnFins2.addOrReplaceChild("bone41", CubeListBuilder.create(),
				PartPose.offset(-1.49F, 4.06F, 0.0F));

		PartDefinition cube_r316 = bone41.addOrReplaceChild("cube_r316",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, 0.0024F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition timeColumnFins3 = timeColumn.addOrReplaceChild("timeColumnFins3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.1F, 3.31F, 0.05F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone43 = timeColumnFins3.addOrReplaceChild("bone43", CubeListBuilder.create(),
				PartPose.offset(-1.54F, -13.94F, 0.0F));

		PartDefinition cube_30_r1 = bone43.addOrReplaceChild("cube_30_r1",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone44 = timeColumnFins3.addOrReplaceChild("bone44", CubeListBuilder.create(),
				PartPose.offset(-1.74F, -13.19F, 0.0F));

		PartDefinition cube_29_r2 = bone44.addOrReplaceChild("cube_29_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 17.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_24 = timeColumnFins3.addOrReplaceChild("cube_24", CubeListBuilder.create(),
				PartPose.offset(-1.84F, -12.44F, 0.0F));

		PartDefinition cube_28_r3 = cube_24.addOrReplaceChild("cube_28_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -16.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 16.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_25 = timeColumnFins3.addOrReplaceChild("cube_25", CubeListBuilder.create(),
				PartPose.offset(-1.94F, -11.69F, 0.0F));

		PartDefinition cube_27_r3 = cube_25.addOrReplaceChild("cube_27_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -15.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_26 = timeColumnFins3.addOrReplaceChild("cube_26", CubeListBuilder.create(),
				PartPose.offset(-2.09F, -10.94F, 0.0F));

		PartDefinition cube_26_r3 = cube_26.addOrReplaceChild("cube_26_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_27 = timeColumnFins3.addOrReplaceChild("cube_27", CubeListBuilder.create(),
				PartPose.offset(-2.14F, -10.19F, 0.0F));

		PartDefinition cube_25_r3 = cube_27.addOrReplaceChild("cube_25_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 14.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_28 = timeColumnFins3.addOrReplaceChild("cube_28", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -9.44F, 0.0F));

		PartDefinition cube_24_r3 = cube_28.addOrReplaceChild("cube_24_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -13.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 13.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_29 = timeColumnFins3.addOrReplaceChild("cube_29", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -8.69F, 0.0F));

		PartDefinition cube_23_r3 = cube_29.addOrReplaceChild("cube_23_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -12.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_30 = timeColumnFins3.addOrReplaceChild("cube_30", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -7.94F, 0.0F));

		PartDefinition cube_22_r3 = cube_30.addOrReplaceChild("cube_22_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_31 = timeColumnFins3.addOrReplaceChild("cube_31", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -7.19F, 0.0F));

		PartDefinition cube_21_r3 = cube_31.addOrReplaceChild("cube_21_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 11.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_32 = timeColumnFins3.addOrReplaceChild("cube_32", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -6.44F, 0.0F));

		PartDefinition cube_20_r3 = cube_32.addOrReplaceChild("cube_20_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -10.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 10.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_33 = timeColumnFins3.addOrReplaceChild("cube_33", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -5.69F, 0.0F));

		PartDefinition cube_19_r3 = cube_33.addOrReplaceChild("cube_19_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -9.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_34 = timeColumnFins3.addOrReplaceChild("cube_34", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.94F, 0.0F));

		PartDefinition cube_18_r3 = cube_34.addOrReplaceChild("cube_18_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_35 = timeColumnFins3.addOrReplaceChild("cube_35", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.19F, 0.0F));

		PartDefinition cube_17_r3 = cube_35.addOrReplaceChild("cube_17_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 8.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_36 = timeColumnFins3.addOrReplaceChild("cube_36", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -3.44F, 0.0F));

		PartDefinition cube_16_r3 = cube_36.addOrReplaceChild("cube_16_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -7.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 7.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_37 = timeColumnFins3.addOrReplaceChild("cube_37", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -2.69F, 0.0F));

		PartDefinition cube_15_r3 = cube_37.addOrReplaceChild("cube_15_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -6.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_38 = timeColumnFins3.addOrReplaceChild("cube_38", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -1.94F, 0.0F));

		PartDefinition cube_14_r3 = cube_38.addOrReplaceChild("cube_14_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_39 = timeColumnFins3.addOrReplaceChild("cube_39", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -1.19F, 0.0F));

		PartDefinition cube_13_r3 = cube_39.addOrReplaceChild("cube_13_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 5.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_40 = timeColumnFins3.addOrReplaceChild("cube_40", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -0.44F, 0.0F));

		PartDefinition cube_12_r3 = cube_40.addOrReplaceChild("cube_12_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -4.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 4.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_41 = timeColumnFins3.addOrReplaceChild("cube_41", CubeListBuilder.create(),
				PartPose.offset(-2.14F, 0.31F, 0.0F));

		PartDefinition cube_11_r3 = cube_41.addOrReplaceChild("cube_11_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -3.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_42 = timeColumnFins3.addOrReplaceChild("cube_42", CubeListBuilder.create(),
				PartPose.offset(-2.04F, 1.06F, 0.0F));

		PartDefinition cube_10_r3 = cube_42.addOrReplaceChild("cube_10_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_43 = timeColumnFins3.addOrReplaceChild("cube_43", CubeListBuilder.create(),
				PartPose.offset(-1.94F, 1.81F, 0.0F));

		PartDefinition cube_9_r3 = cube_43.addOrReplaceChild("cube_9_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 2.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_44 = timeColumnFins3.addOrReplaceChild("cube_44", CubeListBuilder.create(),
				PartPose.offset(-1.84F, 2.56F, 0.0F));

		PartDefinition cube_8_r3 = cube_44.addOrReplaceChild("cube_8_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -1.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 1.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_45 = timeColumnFins3.addOrReplaceChild("cube_45", CubeListBuilder.create(),
				PartPose.offset(-1.74F, 3.31F, 0.0F));

		PartDefinition cube_7_r3 = cube_45.addOrReplaceChild("cube_7_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -0.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone45 = timeColumnFins3.addOrReplaceChild("bone45", CubeListBuilder.create(),
				PartPose.offset(-1.49F, 4.06F, 0.0F));

		PartDefinition cube_r317 = bone45.addOrReplaceChild("cube_r317",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, 0.0024F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition timeColumnFins = timeColumn.addOrReplaceChild("timeColumnFins", CubeListBuilder.create(),
				PartPose.offset(-0.1F, 3.31F, 0.05F));

		PartDefinition rotorFlap = timeColumnFins.addOrReplaceChild("rotorFlap", CubeListBuilder.create(),
				PartPose.offset(-1.54F, -13.94F, 0.0F));

		PartDefinition cube_29_r3 = rotorFlap.addOrReplaceChild("cube_29_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap2 = timeColumnFins.addOrReplaceChild("rotorFlap2", CubeListBuilder.create(),
				PartPose.offset(-1.74F, -13.19F, 0.0F));

		PartDefinition cube_28_r4 = rotorFlap2.addOrReplaceChild("cube_28_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 17.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap3 = timeColumnFins.addOrReplaceChild("rotorFlap3", CubeListBuilder.create(),
				PartPose.offset(-1.84F, -12.44F, 0.0F));

		PartDefinition cube_27_r4 = rotorFlap3.addOrReplaceChild("cube_27_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -16.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 16.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap4 = timeColumnFins.addOrReplaceChild("rotorFlap4", CubeListBuilder.create(),
				PartPose.offset(-1.94F, -11.69F, 0.0F));

		PartDefinition cube_26_r4 = rotorFlap4.addOrReplaceChild("cube_26_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -15.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap5 = timeColumnFins.addOrReplaceChild("rotorFlap5", CubeListBuilder.create(),
				PartPose.offset(-2.09F, -10.94F, 0.0F));

		PartDefinition cube_25_r4 = rotorFlap5.addOrReplaceChild("cube_25_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap6 = timeColumnFins.addOrReplaceChild("rotorFlap6", CubeListBuilder.create(),
				PartPose.offset(-2.14F, -10.19F, 0.0F));

		PartDefinition cube_24_r4 = rotorFlap6.addOrReplaceChild("cube_24_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 14.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap7 = timeColumnFins.addOrReplaceChild("rotorFlap7", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -9.44F, 0.0F));

		PartDefinition cube_23_r4 = rotorFlap7.addOrReplaceChild("cube_23_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -13.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 13.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap8 = timeColumnFins.addOrReplaceChild("rotorFlap8", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -8.69F, 0.0F));

		PartDefinition cube_22_r4 = rotorFlap8.addOrReplaceChild("cube_22_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -12.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap9 = timeColumnFins.addOrReplaceChild("rotorFlap9", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -7.94F, 0.0F));

		PartDefinition cube_21_r4 = rotorFlap9.addOrReplaceChild("cube_21_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap10 = timeColumnFins.addOrReplaceChild("rotorFlap10", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -7.19F, 0.0F));

		PartDefinition cube_20_r4 = rotorFlap10.addOrReplaceChild("cube_20_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 11.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap11 = timeColumnFins.addOrReplaceChild("rotorFlap11", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -6.44F, 0.0F));

		PartDefinition cube_19_r4 = rotorFlap11.addOrReplaceChild("cube_19_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -10.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 10.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap12 = timeColumnFins.addOrReplaceChild("rotorFlap12", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -5.69F, 0.0F));

		PartDefinition cube_18_r4 = rotorFlap12.addOrReplaceChild("cube_18_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -9.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap13 = timeColumnFins.addOrReplaceChild("rotorFlap13", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.94F, 0.0F));

		PartDefinition cube_17_r4 = rotorFlap13.addOrReplaceChild("cube_17_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap14 = timeColumnFins.addOrReplaceChild("rotorFlap14", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.19F, 0.0F));

		PartDefinition cube_16_r4 = rotorFlap14.addOrReplaceChild("cube_16_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 8.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap15 = timeColumnFins.addOrReplaceChild("rotorFlap15", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -3.44F, 0.0F));

		PartDefinition cube_15_r4 = rotorFlap15.addOrReplaceChild("cube_15_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -7.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 7.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap16 = timeColumnFins.addOrReplaceChild("rotorFlap16", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -2.69F, 0.0F));

		PartDefinition cube_14_r4 = rotorFlap16.addOrReplaceChild("cube_14_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -6.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap17 = timeColumnFins.addOrReplaceChild("rotorFlap17", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -1.94F, 0.0F));

		PartDefinition cube_13_r4 = rotorFlap17.addOrReplaceChild("cube_13_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap18 = timeColumnFins.addOrReplaceChild("rotorFlap18", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -1.19F, 0.0F));

		PartDefinition cube_12_r4 = rotorFlap18.addOrReplaceChild("cube_12_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 5.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap19 = timeColumnFins.addOrReplaceChild("rotorFlap19", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -0.44F, 0.0F));

		PartDefinition cube_11_r4 = rotorFlap19.addOrReplaceChild("cube_11_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -4.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 4.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap20 = timeColumnFins.addOrReplaceChild("rotorFlap20", CubeListBuilder.create(),
				PartPose.offset(-2.14F, 0.31F, 0.0F));

		PartDefinition cube_10_r4 = rotorFlap20.addOrReplaceChild("cube_10_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -3.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap21 = timeColumnFins.addOrReplaceChild("rotorFlap21", CubeListBuilder.create(),
				PartPose.offset(-2.04F, 1.06F, 0.0F));

		PartDefinition cube_9_r4 = rotorFlap21.addOrReplaceChild("cube_9_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap22 = timeColumnFins.addOrReplaceChild("rotorFlap22", CubeListBuilder.create(),
				PartPose.offset(-1.94F, 1.81F, 0.0F));

		PartDefinition cube_8_r4 = rotorFlap22.addOrReplaceChild("cube_8_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 2.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap23 = timeColumnFins.addOrReplaceChild("rotorFlap23", CubeListBuilder.create(),
				PartPose.offset(-1.84F, 2.56F, 0.0F));

		PartDefinition cube_7_r4 = rotorFlap23.addOrReplaceChild("cube_7_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -1.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 1.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap24 = timeColumnFins.addOrReplaceChild("rotorFlap24", CubeListBuilder.create(),
				PartPose.offset(-1.74F, 3.31F, 0.0F));

		PartDefinition cube_6_r3 = rotorFlap24.addOrReplaceChild("cube_6_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -0.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap25 = timeColumnFins.addOrReplaceChild("rotorFlap25", CubeListBuilder.create(),
				PartPose.offset(-1.49F, 4.06F, 0.0F));

		PartDefinition cube_r318 = rotorFlap25.addOrReplaceChild("cube_r318",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, 0.0024F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition timeColumnFins4 = timeColumn.addOrReplaceChild("timeColumnFins4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.1F, 3.31F, 0.05F, 0.0F, -2.0944F, 0.0F));

		PartDefinition rotorFlap51 = timeColumnFins4.addOrReplaceChild("rotorFlap51", CubeListBuilder.create(),
				PartPose.offset(-1.54F, -13.94F, 0.0F));

		PartDefinition cube_30_r2 = rotorFlap51.addOrReplaceChild("cube_30_r2",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap52 = timeColumnFins4.addOrReplaceChild("rotorFlap52", CubeListBuilder.create(),
				PartPose.offset(-1.74F, -13.19F, 0.0F));

		PartDefinition cube_29_r4 = rotorFlap52.addOrReplaceChild("cube_29_r4",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 17.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap53 = timeColumnFins4.addOrReplaceChild("rotorFlap53", CubeListBuilder.create(),
				PartPose.offset(-1.84F, -12.44F, 0.0F));

		PartDefinition cube_28_r5 = rotorFlap53.addOrReplaceChild("cube_28_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -16.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 16.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap54 = timeColumnFins4.addOrReplaceChild("rotorFlap54", CubeListBuilder.create(),
				PartPose.offset(-1.94F, -11.69F, 0.0F));

		PartDefinition cube_27_r5 = rotorFlap54.addOrReplaceChild("cube_27_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -15.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap55 = timeColumnFins4.addOrReplaceChild("rotorFlap55", CubeListBuilder.create(),
				PartPose.offset(-2.09F, -10.94F, 0.0F));

		PartDefinition cube_26_r5 = rotorFlap55.addOrReplaceChild("cube_26_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap56 = timeColumnFins4.addOrReplaceChild("rotorFlap56", CubeListBuilder.create(),
				PartPose.offset(-2.14F, -10.19F, 0.0F));

		PartDefinition cube_25_r5 = rotorFlap56.addOrReplaceChild("cube_25_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 14.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap57 = timeColumnFins4.addOrReplaceChild("rotorFlap57", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -9.44F, 0.0F));

		PartDefinition cube_24_r5 = rotorFlap57.addOrReplaceChild("cube_24_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -13.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 13.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap58 = timeColumnFins4.addOrReplaceChild("rotorFlap58", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -8.69F, 0.0F));

		PartDefinition cube_23_r5 = rotorFlap58.addOrReplaceChild("cube_23_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -12.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap59 = timeColumnFins4.addOrReplaceChild("rotorFlap59", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -7.94F, 0.0F));

		PartDefinition cube_22_r5 = rotorFlap59.addOrReplaceChild("cube_22_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap60 = timeColumnFins4.addOrReplaceChild("rotorFlap60", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -7.19F, 0.0F));

		PartDefinition cube_21_r5 = rotorFlap60.addOrReplaceChild("cube_21_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 11.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap61 = timeColumnFins4.addOrReplaceChild("rotorFlap61", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -6.44F, 0.0F));

		PartDefinition cube_20_r5 = rotorFlap61.addOrReplaceChild("cube_20_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -10.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 10.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap62 = timeColumnFins4.addOrReplaceChild("rotorFlap62", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -5.69F, 0.0F));

		PartDefinition cube_19_r5 = rotorFlap62.addOrReplaceChild("cube_19_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -9.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap63 = timeColumnFins4.addOrReplaceChild("rotorFlap63", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.94F, 0.0F));

		PartDefinition cube_18_r5 = rotorFlap63.addOrReplaceChild("cube_18_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap64 = timeColumnFins4.addOrReplaceChild("rotorFlap64", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.19F, 0.0F));

		PartDefinition cube_17_r5 = rotorFlap64.addOrReplaceChild("cube_17_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 8.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap65 = timeColumnFins4.addOrReplaceChild("rotorFlap65", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -3.44F, 0.0F));

		PartDefinition cube_16_r5 = rotorFlap65.addOrReplaceChild("cube_16_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -7.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 7.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap66 = timeColumnFins4.addOrReplaceChild("rotorFlap66", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -2.69F, 0.0F));

		PartDefinition cube_15_r5 = rotorFlap66.addOrReplaceChild("cube_15_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -6.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap67 = timeColumnFins4.addOrReplaceChild("rotorFlap67", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -1.94F, 0.0F));

		PartDefinition cube_14_r5 = rotorFlap67.addOrReplaceChild("cube_14_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap68 = timeColumnFins4.addOrReplaceChild("rotorFlap68", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -1.19F, 0.0F));

		PartDefinition cube_13_r5 = rotorFlap68.addOrReplaceChild("cube_13_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 5.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap69 = timeColumnFins4.addOrReplaceChild("rotorFlap69", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -0.44F, 0.0F));

		PartDefinition cube_12_r5 = rotorFlap69.addOrReplaceChild("cube_12_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -4.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 4.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap70 = timeColumnFins4.addOrReplaceChild("rotorFlap70", CubeListBuilder.create(),
				PartPose.offset(-2.14F, 0.31F, 0.0F));

		PartDefinition cube_11_r5 = rotorFlap70.addOrReplaceChild("cube_11_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -3.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap71 = timeColumnFins4.addOrReplaceChild("rotorFlap71", CubeListBuilder.create(),
				PartPose.offset(-2.04F, 1.06F, 0.0F));

		PartDefinition cube_10_r5 = rotorFlap71.addOrReplaceChild("cube_10_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap72 = timeColumnFins4.addOrReplaceChild("rotorFlap72", CubeListBuilder.create(),
				PartPose.offset(-1.94F, 1.81F, 0.0F));

		PartDefinition cube_9_r5 = rotorFlap72.addOrReplaceChild("cube_9_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 2.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap73 = timeColumnFins4.addOrReplaceChild("rotorFlap73", CubeListBuilder.create(),
				PartPose.offset(-1.84F, 2.56F, 0.0F));

		PartDefinition cube_8_r5 = rotorFlap73.addOrReplaceChild("cube_8_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -1.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 1.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap74 = timeColumnFins4.addOrReplaceChild("rotorFlap74", CubeListBuilder.create(),
				PartPose.offset(-1.74F, 3.31F, 0.0F));

		PartDefinition cube_7_r5 = rotorFlap74.addOrReplaceChild("cube_7_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -0.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap75 = timeColumnFins4.addOrReplaceChild("rotorFlap75", CubeListBuilder.create(),
				PartPose.offset(-1.49F, 4.06F, 0.0F));

		PartDefinition cube_r319 = rotorFlap75.addOrReplaceChild("cube_r319",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, 0.0024F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition timeColumnFins5 = timeColumn.addOrReplaceChild("timeColumnFins5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-0.1F, 3.31F, 0.05F, 0.0F, 2.0944F, 0.0F));

		PartDefinition rotorFlap26 = timeColumnFins5.addOrReplaceChild("rotorFlap26", CubeListBuilder.create(),
				PartPose.offset(-1.54F, -13.94F, 0.0F));

		PartDefinition cube_30_r3 = rotorFlap26.addOrReplaceChild("cube_30_r3",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap27 = timeColumnFins5.addOrReplaceChild("rotorFlap27", CubeListBuilder.create(),
				PartPose.offset(-1.74F, -13.19F, 0.0F));

		PartDefinition cube_29_r5 = rotorFlap27.addOrReplaceChild("cube_29_r5",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -17.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 17.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap28 = timeColumnFins5.addOrReplaceChild("rotorFlap28", CubeListBuilder.create(),
				PartPose.offset(-1.84F, -12.44F, 0.0F));

		PartDefinition cube_28_r6 = rotorFlap28.addOrReplaceChild("cube_28_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -16.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 16.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap29 = timeColumnFins5.addOrReplaceChild("rotorFlap29", CubeListBuilder.create(),
				PartPose.offset(-1.94F, -11.69F, 0.0F));

		PartDefinition cube_27_r6 = rotorFlap29.addOrReplaceChild("cube_27_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -15.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap30 = timeColumnFins5.addOrReplaceChild("rotorFlap30", CubeListBuilder.create(),
				PartPose.offset(-2.09F, -10.94F, 0.0F));

		PartDefinition cube_26_r6 = rotorFlap30.addOrReplaceChild("cube_26_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 15.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap31 = timeColumnFins5.addOrReplaceChild("rotorFlap31", CubeListBuilder.create(),
				PartPose.offset(-2.14F, -10.19F, 0.0F));

		PartDefinition cube_25_r6 = rotorFlap31.addOrReplaceChild("cube_25_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -14.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 14.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap32 = timeColumnFins5.addOrReplaceChild("rotorFlap32", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -9.44F, 0.0F));

		PartDefinition cube_24_r6 = rotorFlap32.addOrReplaceChild("cube_24_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -13.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 13.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap33 = timeColumnFins5.addOrReplaceChild("rotorFlap33", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -8.69F, 0.0F));

		PartDefinition cube_23_r6 = rotorFlap33.addOrReplaceChild("cube_23_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -12.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap34 = timeColumnFins5.addOrReplaceChild("rotorFlap34", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -7.94F, 0.0F));

		PartDefinition cube_22_r6 = rotorFlap34.addOrReplaceChild("cube_22_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap35 = timeColumnFins5.addOrReplaceChild("rotorFlap35", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -7.19F, 0.0F));

		PartDefinition cube_21_r6 = rotorFlap35.addOrReplaceChild("cube_21_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -11.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 11.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap36 = timeColumnFins5.addOrReplaceChild("rotorFlap36", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -6.44F, 0.0F));

		PartDefinition cube_20_r6 = rotorFlap36.addOrReplaceChild("cube_20_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -10.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 10.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap37 = timeColumnFins5.addOrReplaceChild("rotorFlap37", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -5.69F, 0.0F));

		PartDefinition cube_19_r6 = rotorFlap37.addOrReplaceChild("cube_19_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -9.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap38 = timeColumnFins5.addOrReplaceChild("rotorFlap38", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.94F, 0.0F));

		PartDefinition cube_18_r6 = rotorFlap38.addOrReplaceChild("cube_18_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap39 = timeColumnFins5.addOrReplaceChild("rotorFlap39", CubeListBuilder.create(),
				PartPose.offset(-2.74F, -4.19F, 0.0F));

		PartDefinition cube_17_r6 = rotorFlap39.addOrReplaceChild("cube_17_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -8.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 8.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap40 = timeColumnFins5.addOrReplaceChild("rotorFlap40", CubeListBuilder.create(),
				PartPose.offset(-2.64F, -3.44F, 0.0F));

		PartDefinition cube_16_r6 = rotorFlap40.addOrReplaceChild("cube_16_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -7.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 7.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap41 = timeColumnFins5.addOrReplaceChild("rotorFlap41", CubeListBuilder.create(),
				PartPose.offset(-2.54F, -2.69F, 0.0F));

		PartDefinition cube_15_r6 = rotorFlap41.addOrReplaceChild("cube_15_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -6.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap42 = timeColumnFins5.addOrReplaceChild("rotorFlap42", CubeListBuilder.create(),
				PartPose.offset(-2.44F, -1.94F, 0.0F));

		PartDefinition cube_14_r6 = rotorFlap42.addOrReplaceChild("cube_14_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap43 = timeColumnFins5.addOrReplaceChild("rotorFlap43", CubeListBuilder.create(),
				PartPose.offset(-2.34F, -1.19F, 0.0F));

		PartDefinition cube_13_r6 = rotorFlap43.addOrReplaceChild("cube_13_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -5.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 5.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap44 = timeColumnFins5.addOrReplaceChild("rotorFlap44", CubeListBuilder.create(),
				PartPose.offset(-2.24F, -0.44F, 0.0F));

		PartDefinition cube_12_r6 = rotorFlap44.addOrReplaceChild("cube_12_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -4.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 4.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap45 = timeColumnFins5.addOrReplaceChild("rotorFlap45", CubeListBuilder.create(),
				PartPose.offset(-2.14F, 0.31F, 0.0F));

		PartDefinition cube_11_r6 = rotorFlap45.addOrReplaceChild("cube_11_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -3.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap46 = timeColumnFins5.addOrReplaceChild("rotorFlap46", CubeListBuilder.create(),
				PartPose.offset(-2.04F, 1.06F, 0.0F));

		PartDefinition cube_10_r6 = rotorFlap46.addOrReplaceChild("cube_10_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.9976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap47 = timeColumnFins5.addOrReplaceChild("rotorFlap47", CubeListBuilder.create(),
				PartPose.offset(-1.94F, 1.81F, 0.0F));

		PartDefinition cube_9_r6 = rotorFlap47.addOrReplaceChild("cube_9_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -2.2476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 2.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap48 = timeColumnFins5.addOrReplaceChild("rotorFlap48", CubeListBuilder.create(),
				PartPose.offset(-1.84F, 2.56F, 0.0F));

		PartDefinition cube_8_r6 = rotorFlap48.addOrReplaceChild("cube_8_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -1.4976F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 1.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap49 = timeColumnFins5.addOrReplaceChild("rotorFlap49", CubeListBuilder.create(),
				PartPose.offset(-1.74F, 3.31F, 0.0F));

		PartDefinition cube_7_r6 = rotorFlap49.addOrReplaceChild("cube_7_r6",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, -0.7476F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.75F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition rotorFlap50 = timeColumnFins5.addOrReplaceChild("rotorFlap50", CubeListBuilder.create(),
				PartPose.offset(-1.49F, 4.06F, 0.0F));

		PartDefinition cube_r320 = rotorFlap50.addOrReplaceChild("cube_r320",
				CubeListBuilder.create().texOffs(462, 293).addBox(-1.5176F, 0.0024F, -0.8876F, 3.0352F, -0.0048F,
						2.2752F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.1F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition timeColumnStruts = timeColumn.addOrReplaceChild("timeColumnStruts", CubeListBuilder.create(),
				PartPose.offset(-0.1F, 3.31F, 0.05F));

		PartDefinition cube_r321 = timeColumnStruts.addOrReplaceChild("cube_r321",
				CubeListBuilder.create().texOffs(489, 202).addBox(0.0024F, -43.4376F, -4.1776F, -0.0048F, 18.9952F,
						8.3552F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0F, 28.88F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

		PartDefinition cube_r322 = timeColumnStruts.addOrReplaceChild("cube_r322",
				CubeListBuilder.create().texOffs(489, 202).addBox(0.0024F, -9.9976F, -4.1776F, -0.0048F, 18.9952F,
						8.3552F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0F, -4.56F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition cube_r323 = timeColumnStruts.addOrReplaceChild("cube_r323",
				CubeListBuilder.create().texOffs(489, 202).addBox(0.0024F, -9.9976F, -4.1776F, -0.0048F, 18.9952F,
						8.3552F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0F, -4.56F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

		PartDefinition cube_r324 = timeColumnStruts.addOrReplaceChild("cube_r324",
				CubeListBuilder.create().texOffs(489, 177).addBox(0.0024F, -9.9976F, -4.1776F, -0.0048F, 18.9952F,
						8.3552F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0F, -4.56F, 0.0F, 0.0F, -2.618F, 0.0F));

		PartDefinition cube_r325 = timeColumnStruts.addOrReplaceChild("cube_r325",
				CubeListBuilder.create().texOffs(489, 177).addBox(0.0024F, -9.9976F, -4.1776F, -0.0048F, 18.9952F,
						8.3552F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0F, -4.56F, 0.0F, 0.0F, 2.618F, 0.0F));

		PartDefinition cube_r326 = timeColumnStruts.addOrReplaceChild("cube_r326",
				CubeListBuilder.create().texOffs(489, 177).addBox(0.0024F, -43.4376F, -4.1776F, -0.0048F, 18.9952F,
						8.3552F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0F, 28.88F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition paddles = rotor.addOrReplaceChild("paddles", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.15F, -20.2991F, 0.2F, 0.0F, 0.0F, -3.1416F));

		PartDefinition bone64 = paddles.addOrReplaceChild("bone64", CubeListBuilder.create(),
				PartPose.offset(2.52F, 1.8596F, 1.4428F));

		PartDefinition cube_r327 = bone64.addOrReplaceChild("cube_r327",
				CubeListBuilder.create().texOffs(496, 19).addBox(-1.0F, -0.9981F, -0.0436F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.0543F, 1.0472F, 0.0F));

		PartDefinition cube_r328 = bone64.addOrReplaceChild("cube_r328",
				CubeListBuilder.create().texOffs(496, 19).addBox(-1.0F, -0.9981F, -0.0436F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.6528F, 0.0F, 1.6092F, 3.0543F, 0.0F, 0.0F));

		PartDefinition cube_r329 = bone64.addOrReplaceChild("cube_r329",
				CubeListBuilder.create().texOffs(496, 19).addBox(-1.0F, -0.9957F, -0.0653F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.3728F, 0.0F, 0.1165F, 3.0107F, -1.0472F, 0.0F));

		PartDefinition cube_r330 = bone64.addOrReplaceChild("cube_r330",
				CubeListBuilder.create().texOffs(496, 19).addBox(-1.0F, -0.9981F, -0.0436F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.44F, 0.0F, -2.9855F, -0.0873F, -1.0472F, -3.1416F));

		PartDefinition cube_r331 = bone64.addOrReplaceChild("cube_r331",
				CubeListBuilder.create().texOffs(496, 19).addBox(-1.0F, -0.9981F, -0.0436F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.7872F, 0.0F, -4.5947F, -0.0873F, 0.0F, -3.1416F));

		PartDefinition cube_r332 = bone64.addOrReplaceChild("cube_r332",
				CubeListBuilder.create().texOffs(496, 19).addBox(-1.0F, -0.9981F, -0.0436F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.0672F, 0.0F, -3.102F, -0.0873F, 1.0472F, 3.1416F));

		PartDefinition bone84 = paddles.addOrReplaceChild("bone84", CubeListBuilder.create(),
				PartPose.offset(-0.1031F, 23.7563F, -0.0482F));

		PartDefinition cube_r333 = bone84.addOrReplaceChild("cube_r333",
				CubeListBuilder.create().texOffs(496, 21).addBox(-1.0F, 19.171F, -1.8085F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0704F, 20.2523F, 3.1002F, -3.0543F, 0.0F, 0.0F));

		PartDefinition cube_r334 = bone84.addOrReplaceChild("cube_r334",
				CubeListBuilder.create().texOffs(496, 21).addBox(-1.0F, 19.0725F, -2.7084F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(-2.6496F, 20.2523F, 1.6074F, -3.0107F, -1.0472F, 0.0F));

		PartDefinition cube_r335 = bone84.addOrReplaceChild("cube_r335",
				CubeListBuilder.create().texOffs(496, 21).addBox(-1.0F, 19.171F, -1.8085F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(-2.7169F, 20.2523F, -1.4946F, 0.0873F, -1.0472F, 3.1416F));

		PartDefinition cube_r336 = bone84.addOrReplaceChild("cube_r336",
				CubeListBuilder.create().texOffs(496, 21).addBox(-1.0F, 19.171F, -1.8085F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(-0.0641F, 20.2523F, -3.1038F, 0.0873F, 0.0F, 3.1416F));

		PartDefinition cube_r337 = bone84.addOrReplaceChild("cube_r337",
				CubeListBuilder.create().texOffs(496, 21).addBox(-1.0F, 19.171F, -1.8085F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(2.6559F, 20.2523F, -1.611F, 0.0873F, 1.0472F, -3.1416F));

		PartDefinition cube_r338 = bone84.addOrReplaceChild("cube_r338",
				CubeListBuilder.create().texOffs(496, 21).addBox(-1.0F, 19.171F, -1.8085F, 2.0F, 2.0F, 0.0F,
						new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(2.7231F, 20.2523F, 1.4909F, -3.0543F, 1.0472F, 0.0F));

		PartDefinition controls = root.addOrReplaceChild("controls", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition telepathics = controls.addOrReplaceChild("telepathics", CubeListBuilder.create(),
				PartPose.offsetAndRotation(7.0889F, -15.0573F, 13.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r339 = telepathics.addOrReplaceChild("cube_r339",
				CubeListBuilder.create().texOffs(223, 0)
						.addBox(-1.6687F, 0.0717F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.7F)).texOffs(208, 1)
						.addBox(-1.6687F, 0.8717F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.7F)).texOffs(0, 33)
						.addBox(-1.2187F, -1.7283F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.6F)),
				PartPose.offsetAndRotation(0.1297F, 0.5026F, -0.5F, 0.0F, 0.0F, 2.5744F));

		PartDefinition positioning = partdefinition.addOrReplaceChild("positioning", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void SetupAnimations(TakomakConsoleTile tile, float ageInTicks) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		// if (!tile.ControlAnimationMap.isEmpty()) {
		// if (tile.ControlAnimationMap.get(71) != null)
		// this.throttle.xRot = (float) Math.toRadians(tile.ControlAnimationMap.get(71)
		// * -45);
		// if (tile.ControlAnimationMap.get(1) != null) {
		// this.handbrake.yRot = (float)
		// Math.toRadians(tile.ControlAnimationMap.get(1));
		// }
		// }
		this.animate(tile.GetRotorAnimation(), TakomakModelAnimation.ROTOR_FLIGHT, ageInTicks);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight,
			int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();

		poseStack.translate(0f, -0.52f, 0f);
		poseStack.scale(1.5f, 1.5f, 1.5f);

		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(@NotNull Entity entity, float v, float v1, float v2, float v3, float v4) {
	}
}

// Save this class in your mod and generate all required imports
