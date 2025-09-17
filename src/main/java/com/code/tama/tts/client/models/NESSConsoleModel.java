/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models; // Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.server.tileentities.NESSConsoleTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class NESSConsoleModel<T extends NESSConsoleTile> extends HierarchicalModel<Entity>
        implements IAnimateableModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into
    // this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(TTSMod.MODID, "nessconsolemodel"), "main");
    private final ModelPart base;
    private final ModelPart controls3;
    private final ModelPart panel18;
    private final ModelPart panel17;
    private final ModelPart bone39;
    private final ModelPart bone38;
    private final ModelPart panel16;
    private final ModelPart panel15;
    private final ModelPart bone37;
    private final ModelPart helmic3;
    private final ModelPart panel14;
    private final ModelPart bone36;
    private final ModelPart panel13;
    private final ModelPart bone35;
    private final ModelPart pillar18;
    private final ModelPart pillar17;
    private final ModelPart pillar16;
    private final ModelPart bone34;
    private final ModelPart bone33;
    private final ModelPart pillar15;
    private final ModelPart bone32;
    private final ModelPart pillar14;
    private final ModelPart pillar13;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart consolepiece;
    private final ModelPart consolepieceside2;
    private final ModelPart consolepieceside1;
    private final ModelPart consolepiece2;
    private final ModelPart consolepieceside3;
    private final ModelPart consolepieceside4;
    private final ModelPart consolepiece3;
    private final ModelPart consolepieceside5;
    private final ModelPart consolepieceside6;
    private final ModelPart consolepiece4;
    private final ModelPart consolepieceside7;
    private final ModelPart consolepieceside8;
    private final ModelPart consolepiece5;
    private final ModelPart consolepieceside9;
    private final ModelPart consolepieceside10;
    private final ModelPart consolepiece6;
    private final ModelPart consolepieceside11;
    private final ModelPart consolepieceside12;
    private final ModelPart rotorbase;
    private final ModelPart controls;
    private final ModelPart panel1;
    private final ModelPart lock;
    private final ModelPart powerbox;
    private final ModelPart powerswitch3;
    private final ModelPart powerswitch1;
    private final ModelPart powerswitch2;
    private final ModelPart ceiling;
    private final ModelPart doorbutton;
    private final ModelPart chambuttons;
    private final ModelPart redcham;
    private final ModelPart chambuttons2;
    private final ModelPart chambuttons1;
    private final ModelPart greencham;
    private final ModelPart chambuttons4;
    private final ModelPart chambuttons3;
    private final ModelPart panel2;
    private final ModelPart bone7;
    private final ModelPart bone8;
    private final ModelPart diagnosticbutton;
    private final ModelPart telepathicbutton;
    private final ModelPart panel3;
    private final ModelPart gravitybutton;
    private final ModelPart shieldbutton;
    private final ModelPart stabiliserinput;
    private final ModelPart panel4;
    private final ModelPart bone9;
    private final ModelPart dimension;
    private final ModelPart helmic;
    private final ModelPart helmicball;
    private final ModelPart rotatedial;
    private final ModelPart saver2;
    private final ModelPart saver1;
    private final ModelPart analogdial;
    private final ModelPart analoggydial;
    private final ModelPart panel5;
    private final ModelPart bone10;
    private final ModelPart zigzaglever;
    private final ModelPart axisinput;
    private final ModelPart keyboard;
    private final ModelPart kbuttons3;
    private final ModelPart kbuttons2;
    private final ModelPart kbuttons1;
    private final ModelPart panel6;
    private final ModelPart timewheel;
    private final ModelPart timedial;
    private final ModelPart clockdial1;
    private final ModelPart clockdial2;
    private final ModelPart timebutton2;
    private final ModelPart timebutton1;
    private final ModelPart pressurewheel;
    private final ModelPart pressuredial;
    private final ModelPart pillar1;
    private final ModelPart phaser;
    private final ModelPart shellphaser;
    private final ModelPart laser;
    private final ModelPart pillar2;
    private final ModelPart height;
    private final ModelPart wheel;
    private final ModelPart pillar3;
    private final ModelPart monitor;
    private final ModelPart bone18;
    private final ModelPart plotter;
    private final ModelPart bone13;
    private final ModelPart bone11;
    private final ModelPart bone16;
    private final ModelPart primarymovy;
    private final ModelPart bone12;
    private final ModelPart pump;
    private final ModelPart pumpy;
    private final ModelPart pillar4;
    private final ModelPart throttle;
    private final ModelPart bone17;
    private final ModelPart bell;
    private final ModelPart bone14;
    private final ModelPart pillar5;
    private final ModelPart handbrake;
    private final ModelPart handbrakepart;
    private final ModelPart pillar6;
    private final ModelPart powerswitch;
    private final ModelPart bone31;
    private final ModelPart bone30;
    private final ModelPart bone29;
    private final ModelPart bone28;
    private final ModelPart bone27;
    private final ModelPart bone26;
    private final ModelPart consolepiece12;
    private final ModelPart consolepieceside24;
    private final ModelPart consolepieceside23;
    private final ModelPart consolepiece11;
    private final ModelPart consolepieceside22;
    private final ModelPart consolepieceside21;
    private final ModelPart consolepiece10;
    private final ModelPart consolepieceside20;
    private final ModelPart consolepieceside19;
    private final ModelPart consolepiece9;
    private final ModelPart consolepieceside18;
    private final ModelPart consolepieceside17;
    private final ModelPart consolepiece8;
    private final ModelPart consolepieceside16;
    private final ModelPart consolepieceside15;
    private final ModelPart consolepiece7;
    private final ModelPart consolepieceside14;
    private final ModelPart consolepieceside13;
    private final ModelPart controls2;
    private final ModelPart panel12;
    private final ModelPart panel11;
    private final ModelPart bone25;
    private final ModelPart bone24;
    private final ModelPart panel10;
    private final ModelPart panel9;
    private final ModelPart bone23;
    private final ModelPart helmic2;
    private final ModelPart panel8;
    private final ModelPart bone22;
    private final ModelPart panel7;
    private final ModelPart bone21;
    private final ModelPart pillar12;
    private final ModelPart pillar11;
    private final ModelPart pillar10;
    private final ModelPart bone20;
    private final ModelPart bone19;
    private final ModelPart pillar9;
    private final ModelPart bone15;
    private final ModelPart pillar8;
    private final ModelPart pillar7;
    private final ModelPart bone46;
    private final ModelPart group8;
    private final ModelPart group7;
    private final ModelPart group6;
    private final ModelPart group5;
    private final ModelPart bone45;
    private final ModelPart bone44;
    private final ModelPart bone43;
    private final ModelPart bone42;
    private final ModelPart bone41;
    private final ModelPart bone40;
    private final ModelPart consolepiece18;
    private final ModelPart consolepieceside36;
    private final ModelPart consolepieceside35;
    private final ModelPart consolepiece17;
    private final ModelPart consolepieceside34;
    private final ModelPart consolepieceside33;
    private final ModelPart consolepiece16;
    private final ModelPart consolepieceside32;
    private final ModelPart consolepieceside31;
    private final ModelPart consolepiece15;
    private final ModelPart consolepieceside30;
    private final ModelPart consolepieceside29;
    private final ModelPart consolepiece14;
    private final ModelPart consolepieceside28;
    private final ModelPart consolepieceside27;
    private final ModelPart consolepiece13;
    private final ModelPart consolepieceside26;
    private final ModelPart consolepieceside25;

    public NESSConsoleModel(ModelPart root) {
        this.base = root.getChild("base");
        this.controls3 = this.base.getChild("controls3");
        this.panel18 = this.controls3.getChild("panel18");
        this.panel17 = this.controls3.getChild("panel17");
        this.bone39 = this.panel17.getChild("bone39");
        this.bone38 = this.panel17.getChild("bone38");
        this.panel16 = this.controls3.getChild("panel16");
        this.panel15 = this.controls3.getChild("panel15");
        this.bone37 = this.panel15.getChild("bone37");
        this.helmic3 = this.panel15.getChild("helmic3");
        this.panel14 = this.controls3.getChild("panel14");
        this.bone36 = this.panel14.getChild("bone36");
        this.panel13 = this.controls3.getChild("panel13");
        this.bone35 = this.panel13.getChild("bone35");
        this.pillar18 = this.controls3.getChild("pillar18");
        this.pillar17 = this.controls3.getChild("pillar17");
        this.pillar16 = this.controls3.getChild("pillar16");
        this.bone34 = this.pillar16.getChild("bone34");
        this.bone33 = this.pillar16.getChild("bone33");
        this.pillar15 = this.controls3.getChild("pillar15");
        this.bone32 = this.pillar15.getChild("bone32");
        this.pillar14 = this.controls3.getChild("pillar14");
        this.pillar13 = this.controls3.getChild("pillar13");
        this.bone = this.base.getChild("bone");
        this.bone2 = this.base.getChild("bone2");
        this.bone3 = this.base.getChild("bone3");
        this.bone4 = this.base.getChild("bone4");
        this.bone5 = this.base.getChild("bone5");
        this.bone6 = this.base.getChild("bone6");
        this.consolepiece = this.base.getChild("consolepiece");
        this.consolepieceside2 = this.consolepiece.getChild("consolepieceside2");
        this.consolepieceside1 = this.consolepiece.getChild("consolepieceside1");
        this.consolepiece2 = this.base.getChild("consolepiece2");
        this.consolepieceside3 = this.consolepiece2.getChild("consolepieceside3");
        this.consolepieceside4 = this.consolepiece2.getChild("consolepieceside4");
        this.consolepiece3 = this.base.getChild("consolepiece3");
        this.consolepieceside5 = this.consolepiece3.getChild("consolepieceside5");
        this.consolepieceside6 = this.consolepiece3.getChild("consolepieceside6");
        this.consolepiece4 = this.base.getChild("consolepiece4");
        this.consolepieceside7 = this.consolepiece4.getChild("consolepieceside7");
        this.consolepieceside8 = this.consolepiece4.getChild("consolepieceside8");
        this.consolepiece5 = this.base.getChild("consolepiece5");
        this.consolepieceside9 = this.consolepiece5.getChild("consolepieceside9");
        this.consolepieceside10 = this.consolepiece5.getChild("consolepieceside10");
        this.consolepiece6 = this.base.getChild("consolepiece6");
        this.consolepieceside11 = this.consolepiece6.getChild("consolepieceside11");
        this.consolepieceside12 = this.consolepiece6.getChild("consolepieceside12");
        this.rotorbase = this.base.getChild("rotorbase");
        this.controls = this.base.getChild("controls");
        this.panel1 = this.controls.getChild("panel1");
        this.lock = this.panel1.getChild("lock");
        this.powerbox = this.panel1.getChild("powerbox");
        this.powerswitch3 = this.panel1.getChild("powerswitch3");
        this.powerswitch1 = this.panel1.getChild("powerswitch1");
        this.powerswitch2 = this.panel1.getChild("powerswitch2");
        this.ceiling = this.panel1.getChild("ceiling");
        this.doorbutton = this.panel1.getChild("doorbutton");
        this.chambuttons = this.panel1.getChild("chambuttons");
        this.redcham = this.chambuttons.getChild("redcham");
        this.chambuttons2 = this.redcham.getChild("chambuttons2");
        this.chambuttons1 = this.redcham.getChild("chambuttons1");
        this.greencham = this.chambuttons.getChild("greencham");
        this.chambuttons4 = this.greencham.getChild("chambuttons4");
        this.chambuttons3 = this.greencham.getChild("chambuttons3");
        this.panel2 = this.controls.getChild("panel2");
        this.bone7 = this.panel2.getChild("bone7");
        this.bone8 = this.panel2.getChild("bone8");
        this.diagnosticbutton = this.panel2.getChild("diagnosticbutton");
        this.telepathicbutton = this.panel2.getChild("telepathicbutton");
        this.panel3 = this.controls.getChild("panel3");
        this.gravitybutton = this.panel3.getChild("gravitybutton");
        this.shieldbutton = this.panel3.getChild("shieldbutton");
        this.stabiliserinput = this.panel3.getChild("stabiliserinput");
        this.panel4 = this.controls.getChild("panel4");
        this.bone9 = this.panel4.getChild("bone9");
        this.dimension = this.panel4.getChild("dimension");
        this.helmic = this.panel4.getChild("helmic");
        this.helmicball = this.helmic.getChild("helmicball");
        this.rotatedial = this.panel4.getChild("rotatedial");
        this.saver2 = this.panel4.getChild("saver2");
        this.saver1 = this.panel4.getChild("saver1");
        this.analogdial = this.panel4.getChild("analogdial");
        this.analoggydial = this.analogdial.getChild("analoggydial");
        this.panel5 = this.controls.getChild("panel5");
        this.bone10 = this.panel5.getChild("bone10");
        this.zigzaglever = this.panel5.getChild("zigzaglever");
        this.axisinput = this.panel5.getChild("axisinput");
        this.keyboard = this.panel5.getChild("keyboard");
        this.kbuttons3 = this.keyboard.getChild("kbuttons3");
        this.kbuttons2 = this.keyboard.getChild("kbuttons2");
        this.kbuttons1 = this.keyboard.getChild("kbuttons1");
        this.panel6 = this.controls.getChild("panel6");
        this.timewheel = this.panel6.getChild("timewheel");
        this.timedial = this.timewheel.getChild("timedial");
        this.clockdial1 = this.panel6.getChild("clockdial1");
        this.clockdial2 = this.panel6.getChild("clockdial2");
        this.timebutton2 = this.panel6.getChild("timebutton2");
        this.timebutton1 = this.panel6.getChild("timebutton1");
        this.pressurewheel = this.panel6.getChild("pressurewheel");
        this.pressuredial = this.pressurewheel.getChild("pressuredial");
        this.pillar1 = this.controls.getChild("pillar1");
        this.phaser = this.pillar1.getChild("phaser");
        this.shellphaser = this.phaser.getChild("shellphaser");
        this.laser = this.pillar1.getChild("laser");
        this.pillar2 = this.controls.getChild("pillar2");
        this.height = this.pillar2.getChild("height");
        this.wheel = this.pillar2.getChild("wheel");
        this.pillar3 = this.controls.getChild("pillar3");
        this.monitor = this.pillar3.getChild("monitor");
        this.bone18 = this.pillar3.getChild("bone18");
        this.plotter = this.bone18.getChild("plotter");
        this.bone13 = this.plotter.getChild("bone13");
        this.bone11 = this.pillar3.getChild("bone11");
        this.bone16 = this.bone11.getChild("bone16");
        this.primarymovy = this.bone16.getChild("primarymovy");
        this.bone12 = this.primarymovy.getChild("bone12");
        this.pump = this.pillar3.getChild("pump");
        this.pumpy = this.pump.getChild("pumpy");
        this.pillar4 = this.controls.getChild("pillar4");
        this.throttle = this.pillar4.getChild("throttle");
        this.bone17 = this.pillar4.getChild("bone17");
        this.bell = this.bone17.getChild("bell");
        this.bone14 = this.bell.getChild("bone14");
        this.pillar5 = this.controls.getChild("pillar5");
        this.handbrake = this.pillar5.getChild("handbrake");
        this.handbrakepart = this.handbrake.getChild("handbrakepart");
        this.pillar6 = this.controls.getChild("pillar6");
        this.powerswitch = this.pillar6.getChild("powerswitch");
        this.bone31 = this.base.getChild("bone31");
        this.bone30 = this.base.getChild("bone30");
        this.bone29 = this.base.getChild("bone29");
        this.bone28 = this.base.getChild("bone28");
        this.bone27 = this.base.getChild("bone27");
        this.bone26 = this.base.getChild("bone26");
        this.consolepiece12 = this.base.getChild("consolepiece12");
        this.consolepieceside24 = this.consolepiece12.getChild("consolepieceside24");
        this.consolepieceside23 = this.consolepiece12.getChild("consolepieceside23");
        this.consolepiece11 = this.base.getChild("consolepiece11");
        this.consolepieceside22 = this.consolepiece11.getChild("consolepieceside22");
        this.consolepieceside21 = this.consolepiece11.getChild("consolepieceside21");
        this.consolepiece10 = this.base.getChild("consolepiece10");
        this.consolepieceside20 = this.consolepiece10.getChild("consolepieceside20");
        this.consolepieceside19 = this.consolepiece10.getChild("consolepieceside19");
        this.consolepiece9 = this.base.getChild("consolepiece9");
        this.consolepieceside18 = this.consolepiece9.getChild("consolepieceside18");
        this.consolepieceside17 = this.consolepiece9.getChild("consolepieceside17");
        this.consolepiece8 = this.base.getChild("consolepiece8");
        this.consolepieceside16 = this.consolepiece8.getChild("consolepieceside16");
        this.consolepieceside15 = this.consolepiece8.getChild("consolepieceside15");
        this.consolepiece7 = this.base.getChild("consolepiece7");
        this.consolepieceside14 = this.consolepiece7.getChild("consolepieceside14");
        this.consolepieceside13 = this.consolepiece7.getChild("consolepieceside13");
        this.controls2 = this.base.getChild("controls2");
        this.panel12 = this.controls2.getChild("panel12");
        this.panel11 = this.controls2.getChild("panel11");
        this.bone25 = this.panel11.getChild("bone25");
        this.bone24 = this.panel11.getChild("bone24");
        this.panel10 = this.controls2.getChild("panel10");
        this.panel9 = this.controls2.getChild("panel9");
        this.bone23 = this.panel9.getChild("bone23");
        this.helmic2 = this.panel9.getChild("helmic2");
        this.panel8 = this.controls2.getChild("panel8");
        this.bone22 = this.panel8.getChild("bone22");
        this.panel7 = this.controls2.getChild("panel7");
        this.bone21 = this.panel7.getChild("bone21");
        this.pillar12 = this.controls2.getChild("pillar12");
        this.pillar11 = this.controls2.getChild("pillar11");
        this.pillar10 = this.controls2.getChild("pillar10");
        this.bone20 = this.pillar10.getChild("bone20");
        this.bone19 = this.pillar10.getChild("bone19");
        this.pillar9 = this.controls2.getChild("pillar9");
        this.bone15 = this.pillar9.getChild("bone15");
        this.pillar8 = this.controls2.getChild("pillar8");
        this.pillar7 = this.controls2.getChild("pillar7");
        this.bone46 = this.base.getChild("bone46");
        this.group8 = this.base.getChild("group8");
        this.group7 = this.base.getChild("group7");
        this.group6 = this.base.getChild("group6");
        this.group5 = this.base.getChild("group5");
        this.bone45 = this.base.getChild("bone45");
        this.bone44 = this.base.getChild("bone44");
        this.bone43 = this.base.getChild("bone43");
        this.bone42 = this.base.getChild("bone42");
        this.bone41 = this.base.getChild("bone41");
        this.bone40 = this.base.getChild("bone40");
        this.consolepiece18 = this.base.getChild("consolepiece18");
        this.consolepieceside36 = this.consolepiece18.getChild("consolepieceside36");
        this.consolepieceside35 = this.consolepiece18.getChild("consolepieceside35");
        this.consolepiece17 = this.base.getChild("consolepiece17");
        this.consolepieceside34 = this.consolepiece17.getChild("consolepieceside34");
        this.consolepieceside33 = this.consolepiece17.getChild("consolepieceside33");
        this.consolepiece16 = this.base.getChild("consolepiece16");
        this.consolepieceside32 = this.consolepiece16.getChild("consolepieceside32");
        this.consolepieceside31 = this.consolepiece16.getChild("consolepieceside31");
        this.consolepiece15 = this.base.getChild("consolepiece15");
        this.consolepieceside30 = this.consolepiece15.getChild("consolepieceside30");
        this.consolepieceside29 = this.consolepiece15.getChild("consolepieceside29");
        this.consolepiece14 = this.base.getChild("consolepiece14");
        this.consolepieceside28 = this.consolepiece14.getChild("consolepieceside28");
        this.consolepieceside27 = this.consolepiece14.getChild("consolepieceside27");
        this.consolepiece13 = this.base.getChild("consolepiece13");
        this.consolepieceside26 = this.consolepiece13.getChild("consolepieceside26");
        this.consolepieceside25 = this.consolepiece13.getChild("consolepieceside25");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base =
                partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition controls3 =
                base.addOrReplaceChild("controls3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panel18 =
                controls3.addOrReplaceChild("panel18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panel17 = controls3.addOrReplaceChild(
                "panel17",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone39 = panel17.addOrReplaceChild(
                "bone39",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.0F, -20.0F, -12.0F, -1.0409F, 0.7119F, 0.3655F));

        PartDefinition bone38 = panel17.addOrReplaceChild(
                "bone38",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.5F, -20.0F, -12.0F, -1.0409F, 0.7119F, 0.3655F));

        PartDefinition panel16 = controls3.addOrReplaceChild(
                "panel16",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition panel15 =
                controls3.addOrReplaceChild("panel15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone37 = panel15.addOrReplaceChild(
                "bone37",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.0F, -18.0F, 14.0F, -0.9355F, 0.4981F, -0.5749F));

        PartDefinition helmic3 = panel15.addOrReplaceChild(
                "helmic3",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(3.0F, -20.1328F, 14.75F, -0.3927F, 0.0F, 0.0F));

        PartDefinition panel14 = controls3.addOrReplaceChild(
                "panel14", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone36 = panel14.addOrReplaceChild(
                "bone36",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-2.7679F, -23.0F, -10.4019F, 0.0F, 0.0F, 0.7854F));

        PartDefinition panel13 = controls3.addOrReplaceChild(
                "panel13", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone35 = panel13.addOrReplaceChild(
                "bone35",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(1.5F, -19.6465F, -12.6967F, 0.3927F, 0.0F, 0.0F));

        PartDefinition pillar18 = controls3.addOrReplaceChild(
                "pillar18",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition pillar17 =
                controls3.addOrReplaceChild("pillar17", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r1 = pillar17.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create()
                        .texOffs(67, 119)
                        .addBox(1.5F, -0.5F, -0.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 118)
                        .addBox(-1.5F, -0.5F, -0.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.0F, -21.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition pillar16 = controls3.addOrReplaceChild(
                "pillar16",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition bone34 = pillar16.addOrReplaceChild(
                "bone34",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -20.7399F, -17.3488F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone33 = pillar16.addOrReplaceChild(
                "bone33",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -22.0F, -13.0F, 0.2849F, -0.274F, 0.7459F));

        PartDefinition pillar15 = controls3.addOrReplaceChild(
                "pillar15",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-21.0F, 0.0F, 37.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition bone32 = pillar15.addOrReplaceChild(
                "bone32",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -20.7071F, 26.9498F, 0.3927F, 0.0F, 0.0F));

        PartDefinition pillar14 =
                controls3.addOrReplaceChild("pillar14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pillar13 = controls3.addOrReplaceChild(
                "pillar13",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone = base.addOrReplaceChild(
                "bone",
                CubeListBuilder.create()
                        .texOffs(110, 167)
                        .addBox(-12.1112F, -28.7404F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(130, 181)
                        .addBox(-20.9733F, -16.5843F, -1.025F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(110, 158)
                        .addBox(-13.5128F, -9.1221F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 69)
                        .addBox(-28.0F, -3.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r2 = bone.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create()
                        .texOffs(162, 68)
                        .addBox(2.3827F, -0.9239F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-23.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r3 = bone.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create()
                        .texOffs(166, 150)
                        .addBox(-5.2929F, -3.2071F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(-11.0128F, -5.1221F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r4 = bone.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create()
                        .texOffs(184, 7)
                        .addBox(-0.2929F, -0.7071F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -12.2914F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r5 = bone.addOrReplaceChild(
                "cube_r5",
                CubeListBuilder.create()
                        .texOffs(146, 92)
                        .addBox(-2.3827F, -3.9239F, -1.025F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.6215F, -9.6215F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r6 = bone.addOrReplaceChild(
                "cube_r6",
                CubeListBuilder.create()
                        .texOffs(178, 63)
                        .addBox(-1.7071F, -6.7071F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -14.8772F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r7 = bone.addOrReplaceChild(
                "cube_r7",
                CubeListBuilder.create()
                        .texOffs(170, 37)
                        .addBox(-1.6173F, -1.9239F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0009F)),
                PartPose.offsetAndRotation(-14.5002F, -19.6684F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone2 = base.addOrReplaceChild(
                "bone2",
                CubeListBuilder.create()
                        .texOffs(28, 170)
                        .addBox(-12.1112F, -28.7404F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(146, 183)
                        .addBox(-20.9733F, -16.5843F, -1.025F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(160, 155)
                        .addBox(-13.5128F, -9.1221F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 71)
                        .addBox(-28.0F, -3.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r8 = bone2.addOrReplaceChild(
                "cube_r8",
                CubeListBuilder.create()
                        .texOffs(166, 27)
                        .addBox(2.3827F, -0.9239F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-23.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r9 = bone2.addOrReplaceChild(
                "cube_r9",
                CubeListBuilder.create()
                        .texOffs(168, 87)
                        .addBox(-5.2929F, -3.2071F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(-11.0128F, -5.1221F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r10 = bone2.addOrReplaceChild(
                "cube_r10",
                CubeListBuilder.create()
                        .texOffs(184, 7)
                        .addBox(-0.2929F, -0.7071F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -12.2914F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r11 = bone2.addOrReplaceChild(
                "cube_r11",
                CubeListBuilder.create()
                        .texOffs(148, 124)
                        .addBox(-2.3827F, -3.9239F, -0.975F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.6215F, -9.6215F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r12 = bone2.addOrReplaceChild(
                "cube_r12",
                CubeListBuilder.create()
                        .texOffs(178, 71)
                        .addBox(-1.7071F, -6.7071F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -14.8772F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r13 = bone2.addOrReplaceChild(
                "cube_r13",
                CubeListBuilder.create()
                        .texOffs(170, 54)
                        .addBox(-1.6173F, -1.9239F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0009F)),
                PartPose.offsetAndRotation(-14.5002F, -19.6684F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone3 = base.addOrReplaceChild(
                "bone3",
                CubeListBuilder.create()
                        .texOffs(170, 58)
                        .addBox(-12.1112F, -28.7404F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 183)
                        .addBox(-20.9733F, -16.5843F, -0.975F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(14, 161)
                        .addBox(-13.5128F, -9.1221F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(42, 73)
                        .addBox(-28.0F, -3.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r14 = bone3.addOrReplaceChild(
                "cube_r14",
                CubeListBuilder.create()
                        .texOffs(166, 31)
                        .addBox(2.3827F, -0.9239F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-23.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r15 = bone3.addOrReplaceChild(
                "cube_r15",
                CubeListBuilder.create()
                        .texOffs(168, 96)
                        .addBox(-5.2929F, -3.2071F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(-11.0128F, -5.1221F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r16 = bone3.addOrReplaceChild(
                "cube_r16",
                CubeListBuilder.create()
                        .texOffs(184, 7)
                        .addBox(-0.2929F, -0.7071F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -12.2914F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r17 = bone3.addOrReplaceChild(
                "cube_r17",
                CubeListBuilder.create()
                        .texOffs(148, 124)
                        .addBox(-2.3827F, -3.9239F, -0.975F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.6215F, -9.6215F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r18 = bone3.addOrReplaceChild(
                "cube_r18",
                CubeListBuilder.create()
                        .texOffs(178, 79)
                        .addBox(-1.7071F, -6.7071F, -0.95F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -14.8772F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r19 = bone3.addOrReplaceChild(
                "cube_r19",
                CubeListBuilder.create()
                        .texOffs(170, 164)
                        .addBox(-1.6173F, -1.9239F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0009F)),
                PartPose.offsetAndRotation(-14.5002F, -19.6684F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone4 = base.addOrReplaceChild(
                "bone4",
                CubeListBuilder.create()
                        .texOffs(62, 170)
                        .addBox(-12.1112F, -28.7404F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(146, 183)
                        .addBox(-20.9733F, -16.5843F, -1.025F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 161)
                        .addBox(-13.5128F, -9.1221F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 75)
                        .addBox(-28.0F, -3.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r20 = bone4.addOrReplaceChild(
                "cube_r20",
                CubeListBuilder.create()
                        .texOffs(166, 42)
                        .addBox(2.3827F, -0.9239F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-23.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r21 = bone4.addOrReplaceChild(
                "cube_r21",
                CubeListBuilder.create()
                        .texOffs(168, 120)
                        .addBox(-5.2929F, -3.2071F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(-11.0128F, -5.1221F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r22 = bone4.addOrReplaceChild(
                "cube_r22",
                CubeListBuilder.create()
                        .texOffs(184, 7)
                        .addBox(-0.2929F, -0.7071F, -1.025F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -12.2914F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r23 = bone4.addOrReplaceChild(
                "cube_r23",
                CubeListBuilder.create()
                        .texOffs(148, 124)
                        .addBox(-2.3827F, -3.9239F, -0.95F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.6215F, -9.6215F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r24 = bone4.addOrReplaceChild(
                "cube_r24",
                CubeListBuilder.create()
                        .texOffs(178, 104)
                        .addBox(-1.7071F, -6.7071F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -14.8772F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r25 = bone4.addOrReplaceChild(
                "cube_r25",
                CubeListBuilder.create()
                        .texOffs(170, 168)
                        .addBox(-1.6173F, -1.9239F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0009F)),
                PartPose.offsetAndRotation(-14.5002F, -19.6684F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone5 = base.addOrReplaceChild(
                "bone5",
                CubeListBuilder.create()
                        .texOffs(118, 171)
                        .addBox(-12.1112F, -28.7404F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(146, 183)
                        .addBox(-20.9733F, -16.5843F, -1.025F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(62, 161)
                        .addBox(-13.5128F, -9.1221F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(42, 77)
                        .addBox(-28.0F, -3.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r26 = bone5.addOrReplaceChild(
                "cube_r26",
                CubeListBuilder.create()
                        .texOffs(166, 46)
                        .addBox(2.3827F, -0.9239F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-23.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r27 = bone5.addOrReplaceChild(
                "cube_r27",
                CubeListBuilder.create()
                        .texOffs(168, 125)
                        .addBox(-5.2929F, -3.2071F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(-11.0128F, -5.1221F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r28 = bone5.addOrReplaceChild(
                "cube_r28",
                CubeListBuilder.create()
                        .texOffs(184, 7)
                        .addBox(-0.2929F, -0.7071F, -0.95F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -12.2914F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r29 = bone5.addOrReplaceChild(
                "cube_r29",
                CubeListBuilder.create()
                        .texOffs(148, 124)
                        .addBox(-2.3827F, -3.9239F, -1.025F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.6215F, -9.6215F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r30 = bone5.addOrReplaceChild(
                "cube_r30",
                CubeListBuilder.create()
                        .texOffs(138, 178)
                        .addBox(-1.7071F, -6.7071F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -14.8772F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r31 = bone5.addOrReplaceChild(
                "cube_r31",
                CubeListBuilder.create()
                        .texOffs(126, 171)
                        .addBox(-1.6173F, -1.9239F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0009F)),
                PartPose.offsetAndRotation(-14.5002F, -19.6684F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone6 = base.addOrReplaceChild(
                "bone6",
                CubeListBuilder.create()
                        .texOffs(0, 172)
                        .addBox(-12.1112F, -28.7404F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(146, 183)
                        .addBox(-20.9733F, -16.5843F, -0.95F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(122, 162)
                        .addBox(-13.5128F, -9.1221F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 79)
                        .addBox(-28.0F, -3.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r32 = bone6.addOrReplaceChild(
                "cube_r32",
                CubeListBuilder.create()
                        .texOffs(166, 92)
                        .addBox(2.0F, 0.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-23.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r33 = bone6.addOrReplaceChild(
                "cube_r33",
                CubeListBuilder.create()
                        .texOffs(168, 130)
                        .addBox(-6.0F, -2.5F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.009F)),
                PartPose.offsetAndRotation(-11.0128F, -6.1221F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r34 = bone6.addOrReplaceChild(
                "cube_r34",
                CubeListBuilder.create()
                        .texOffs(184, 7)
                        .addBox(-1.0F, 0.0F, -1.025F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -13.2914F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r35 = bone6.addOrReplaceChild(
                "cube_r35",
                CubeListBuilder.create()
                        .texOffs(148, 124)
                        .addBox(-2.0F, -3.0F, -0.975F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.6215F, -10.6215F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r36 = bone6.addOrReplaceChild(
                "cube_r36",
                CubeListBuilder.create()
                        .texOffs(178, 141)
                        .addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.2662F, -15.8772F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r37 = bone6.addOrReplaceChild(
                "cube_r37",
                CubeListBuilder.create()
                        .texOffs(172, 0)
                        .addBox(-2.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0009F)),
                PartPose.offsetAndRotation(-14.5002F, -20.6684F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition consolepiece = base.addOrReplaceChild(
                "consolepiece",
                CubeListBuilder.create()
                        .texOffs(128, 32)
                        .addBox(-4.9587F, -17.0F, -18.9006F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 205)
                        .addBox(-4.8625F, -18.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(153, 5)
                        .addBox(-4.8625F, -14.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(84, 85)
                        .addBox(-6.5F, -31.45F, -11.25F, 13.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 37)
                        .addBox(-5.5F, -28.5F, -10.25F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 116)
                        .addBox(-6.0F, -10.0F, -11.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(150, 64)
                        .addBox(-3.75F, -11.0F, -14.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(116, 120)
                        .addBox(-5.0F, -5.0F, -11.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 113)
                        .addBox(-7.0F, -3.0F, -13.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 54)
                        .addBox(-13.0F, -2.5F, -24.0F, 26.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 103)
                        .addBox(-7.0F, -2.5F, -14.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 14)
                        .addBox(-11.0F, -1.575F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 23)
                        .addBox(-11.0F, -2.35F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(72, 101)
                        .addBox(-8.0F, -2.0F, -27.0F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(80, 126)
                        .addBox(-5.0F, -9.0F, -10.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r38 = consolepiece.addOrReplaceChild(
                "cube_r38",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -30.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r39 = consolepiece.addOrReplaceChild(
                "cube_r39",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -30.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r40 = consolepiece.addOrReplaceChild(
                "cube_r40",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -30.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r41 = consolepiece.addOrReplaceChild(
                "cube_r41",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -30.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r42 = consolepiece.addOrReplaceChild(
                "cube_r42",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -30.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r43 = consolepiece.addOrReplaceChild(
                "cube_r43",
                CubeListBuilder.create()
                        .texOffs(42, 81)
                        .addBox(-8.5F, -0.5F, -1.0F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -17.839F, -15.4808F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r44 = consolepiece.addOrReplaceChild(
                "cube_r44",
                CubeListBuilder.create()
                        .texOffs(98, 69)
                        .addBox(-7.5F, -0.5F, -3.0F, 15.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -21.0F, -10.75F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r45 = consolepiece.addOrReplaceChild(
                "cube_r45",
                CubeListBuilder.create()
                        .texOffs(148, 130)
                        .addBox(-4.5F, -2.0F, -1.0F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.25F, -14.8F, 0.7854F, 0.0F, 0.0F));

        PartDefinition consolepieceside2 = consolepiece.addOrReplaceChild(
                "consolepieceside2",
                CubeListBuilder.create()
                        .texOffs(152, 27)
                        .addBox(16.8891F, -17.0F, -5.0844F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 203)
                        .addBox(15.7242F, -18.0F, -4.3008F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 155)
                        .addBox(15.7242F, -14.0F, -4.3008F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(96, 135)
                        .addBox(24.3827F, -2.0F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(88, 120)
                        .addBox(14.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(72, 89)
                        .addBox(10.7494F, -11.0F, -4.1854F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r46 = consolepieceside2.addOrReplaceChild(
                "cube_r46",
                CubeListBuilder.create()
                        .texOffs(134, 162)
                        .addBox(0.0F, -2.0F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.5562F, -12.25F, -0.1636F, 0.0F, 0.0F, 0.7854F));

        PartDefinition consolepieceside1 = consolepiece.addOrReplaceChild(
                "consolepieceside1",
                CubeListBuilder.create()
                        .texOffs(152, 42)
                        .addBox(-18.8478F, -17.0F, -5.156F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 202)
                        .addBox(-17.5867F, -18.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 172)
                        .addBox(-17.5867F, -14.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(66, 145)
                        .addBox(-27.3827F, -2.0F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(126, 81)
                        .addBox(-26.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(164, 178)
                        .addBox(-13.9994F, -11.0F, -3.7524F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r47 = consolepieceside1.addOrReplaceChild(
                "cube_r47",
                CubeListBuilder.create()
                        .texOffs(0, 163)
                        .addBox(-1.0F, -2.0F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.7562F, -12.25F, -0.1636F, 0.0F, 0.0F, -0.7854F));

        PartDefinition consolepiece2 = base.addOrReplaceChild(
                "consolepiece2",
                CubeListBuilder.create()
                        .texOffs(128, 44)
                        .addBox(-4.9587F, -17.0F, -18.9006F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 205)
                        .addBox(-4.8625F, -18.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(153, 5)
                        .addBox(-4.8625F, -14.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 87)
                        .addBox(-6.5F, -31.475F, -11.25F, 13.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(104, 128)
                        .addBox(-5.5F, -28.5F, -10.25F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(100, 116)
                        .addBox(-6.0F, -10.0F, -11.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(146, 151)
                        .addBox(-3.75F, -11.0F, -14.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 123)
                        .addBox(-5.0F, -5.0F, -11.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 113)
                        .addBox(-7.0F, -3.0F, -13.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 57)
                        .addBox(-13.0F, -2.5F, -24.0F, 26.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(114, 111)
                        .addBox(-7.0F, -2.5F, -14.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32)
                        .addBox(-11.0F, -1.6F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 41)
                        .addBox(-11.0F, -2.35F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 105)
                        .addBox(-8.0F, -2.0F, -27.0F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 8)
                        .addBox(-5.0F, -9.0F, -10.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r48 = consolepiece2.addOrReplaceChild(
                "cube_r48",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r49 = consolepiece2.addOrReplaceChild(
                "cube_r49",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r50 = consolepiece2.addOrReplaceChild(
                "cube_r50",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r51 = consolepiece2.addOrReplaceChild(
                "cube_r51",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r52 = consolepiece2.addOrReplaceChild(
                "cube_r52",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r53 = consolepiece2.addOrReplaceChild(
                "cube_r53",
                CubeListBuilder.create()
                        .texOffs(0, 83)
                        .addBox(-8.5F, -1.2071F, -0.2929F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -16.839F, -15.4808F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r54 = consolepiece2.addOrReplaceChild(
                "cube_r54",
                CubeListBuilder.create()
                        .texOffs(0, 105)
                        .addBox(-7.5F, -1.4239F, -2.6173F, 15.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.0F, -10.75F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r55 = consolepiece2.addOrReplaceChild(
                "cube_r55",
                CubeListBuilder.create()
                        .texOffs(150, 37)
                        .addBox(-4.5F, -2.5985F, -0.1515F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.25F, -14.9F, 0.7854F, 0.0F, 0.0F));

        PartDefinition consolepieceside3 = consolepiece2.addOrReplaceChild(
                "consolepieceside3",
                CubeListBuilder.create()
                        .texOffs(152, 76)
                        .addBox(16.8891F, -17.0F, -5.0844F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 203)
                        .addBox(15.7242F, -18.0F, -4.3008F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 155)
                        .addBox(15.7242F, -14.0F, -4.3008F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(116, 135)
                        .addBox(24.3827F, -2.0F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(0, 121)
                        .addBox(14.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(176, 178)
                        .addBox(10.7494F, -11.0F, -4.1854F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r56 = consolepieceside3.addOrReplaceChild(
                "cube_r56",
                CubeListBuilder.create()
                        .texOffs(38, 163)
                        .addBox(-0.1414F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.7F, -12.25F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition consolepieceside4 = consolepiece2.addOrReplaceChild(
                "consolepieceside4",
                CubeListBuilder.create()
                        .texOffs(152, 143)
                        .addBox(-18.8478F, -17.0F, -5.156F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 202)
                        .addBox(-17.5867F, -18.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 172)
                        .addBox(-17.5867F, -14.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(136, 135)
                        .addBox(-27.3827F, -2.05F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(126, 83)
                        .addBox(-26.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 179)
                        .addBox(-13.9994F, -11.0F, -3.7524F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r57 = consolepieceside4.addOrReplaceChild(
                "cube_r57",
                CubeListBuilder.create()
                        .texOffs(50, 163)
                        .addBox(-0.8586F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.9F, -12.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition consolepiece3 = base.addOrReplaceChild(
                "consolepiece3",
                CubeListBuilder.create()
                        .texOffs(128, 49)
                        .addBox(-4.9587F, -17.0F, -18.9006F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 205)
                        .addBox(-4.8625F, -18.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(153, 5)
                        .addBox(-4.8625F, -14.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 89)
                        .addBox(-6.5F, -31.45F, -11.25F, 13.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 128)
                        .addBox(-5.5F, -28.5F, -10.25F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 117)
                        .addBox(-6.0F, -10.0F, -11.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 0)
                        .addBox(-3.4768F, -11.0F, -13.9268F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 124)
                        .addBox(-5.0F, -5.0F, -11.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(102, 113)
                        .addBox(-7.0F, -3.0F, -13.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 60)
                        .addBox(-13.0F, -2.5F, -24.0F, 26.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 62)
                        .addBox(-7.0F, -2.5F, -14.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 50)
                        .addBox(-11.0F, -1.575F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 59)
                        .addBox(-11.0F, -2.35F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(108, 93)
                        .addBox(-8.0F, -2.0F, -27.0F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 14)
                        .addBox(-5.0F, -9.0F, -10.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r58 = consolepiece3.addOrReplaceChild(
                "cube_r58",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r59 = consolepiece3.addOrReplaceChild(
                "cube_r59",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r60 = consolepiece3.addOrReplaceChild(
                "cube_r60",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r61 = consolepiece3.addOrReplaceChild(
                "cube_r61",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r62 = consolepiece3.addOrReplaceChild(
                "cube_r62",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r63 = consolepiece3.addOrReplaceChild(
                "cube_r63",
                CubeListBuilder.create()
                        .texOffs(84, 73)
                        .addBox(-8.5F, -1.2071F, -0.2929F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -16.839F, -15.4808F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r64 = consolepiece3.addOrReplaceChild(
                "cube_r64",
                CubeListBuilder.create()
                        .texOffs(76, 105)
                        .addBox(-7.5F, -1.4239F, -2.6173F, 15.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.0F, -10.75F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r65 = consolepiece3.addOrReplaceChild(
                "cube_r65",
                CubeListBuilder.create()
                        .texOffs(150, 54)
                        .addBox(-4.5F, -2.5985F, -0.1515F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.25F, -14.8F, 0.7854F, 0.0F, 0.0F));

        PartDefinition consolepieceside5 = consolepiece3.addOrReplaceChild(
                "consolepieceside5",
                CubeListBuilder.create()
                        .texOffs(20, 153)
                        .addBox(16.8891F, -17.0F, -5.0844F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 203)
                        .addBox(15.7242F, -18.0F, -4.3008F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 155)
                        .addBox(15.7242F, -14.0F, -4.3008F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(72, 137)
                        .addBox(24.3827F, -2.0F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(88, 123)
                        .addBox(14.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 179)
                        .addBox(10.8226F, -11.0F, -3.9122F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r66 = consolepieceside5.addOrReplaceChild(
                "cube_r66",
                CubeListBuilder.create()
                        .texOffs(146, 163)
                        .addBox(-0.1414F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.8F, -12.25F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition consolepieceside6 = consolepiece3.addOrReplaceChild(
                "consolepieceside6",
                CubeListBuilder.create()
                        .texOffs(34, 153)
                        .addBox(-18.8478F, -17.0F, -5.156F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 202)
                        .addBox(-17.5867F, -18.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 172)
                        .addBox(-17.5867F, -14.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(46, 138)
                        .addBox(-27.3827F, -2.05F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(136, 68)
                        .addBox(-26.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(82, 179)
                        .addBox(-13.7994F, -11.0F, -3.9524F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r67 = consolepieceside6.addOrReplaceChild(
                "cube_r67",
                CubeListBuilder.create()
                        .texOffs(74, 164)
                        .addBox(-0.8586F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.7F, -12.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition consolepiece4 = base.addOrReplaceChild(
                "consolepiece4",
                CubeListBuilder.create()
                        .texOffs(0, 129)
                        .addBox(-4.9587F, -17.0F, -18.9006F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 205)
                        .addBox(-4.8625F, -18.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(154, 6)
                        .addBox(-4.8625F, -14.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(72, 93)
                        .addBox(-6.5F, -31.475F, -11.25F, 13.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(50, 131)
                        .addBox(-5.5F, -28.5F, -10.25F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(30, 119)
                        .addBox(-6.0F, -10.0F, -11.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 8)
                        .addBox(-3.75F, -11.0F, -14.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 0)
                        .addBox(-5.0F, -5.0F, -11.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(114, 105)
                        .addBox(-7.0F, -3.0F, -13.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 63)
                        .addBox(-13.0F, -2.5F, -24.0F, 26.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 64)
                        .addBox(-7.0F, -2.5F, -14.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(62, 0)
                        .addBox(-11.0F, -1.6F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 9)
                        .addBox(-11.0F, -2.35F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 109)
                        .addBox(-8.0F, -2.0F, -27.0F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 20)
                        .addBox(-5.0F, -9.0F, -10.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r68 = consolepiece4.addOrReplaceChild(
                "cube_r68",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r69 = consolepiece4.addOrReplaceChild(
                "cube_r69",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r70 = consolepiece4.addOrReplaceChild(
                "cube_r70",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r71 = consolepiece4.addOrReplaceChild(
                "cube_r71",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r72 = consolepiece4.addOrReplaceChild(
                "cube_r72",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r73 = consolepiece4.addOrReplaceChild(
                "cube_r73",
                CubeListBuilder.create()
                        .texOffs(84, 77)
                        .addBox(-8.5F, -1.2071F, -0.2929F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -16.839F, -15.4808F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r74 = consolepiece4.addOrReplaceChild(
                "cube_r74",
                CubeListBuilder.create()
                        .texOffs(108, 97)
                        .addBox(-7.5F, -1.4239F, -2.6173F, 15.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.0F, -10.75F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r75 = consolepiece4.addOrReplaceChild(
                "cube_r75",
                CubeListBuilder.create()
                        .texOffs(150, 59)
                        .addBox(-4.5F, -2.5985F, -0.1515F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.25F, -14.9F, 0.7854F, 0.0F, 0.0F));

        PartDefinition consolepieceside7 = consolepiece4.addOrReplaceChild(
                "consolepieceside7",
                CubeListBuilder.create()
                        .texOffs(68, 153)
                        .addBox(16.8891F, -17.0F, -5.0844F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 203)
                        .addBox(15.7242F, -18.0F, -4.3008F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 155)
                        .addBox(15.7242F, -14.0F, -4.3008F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(22, 139)
                        .addBox(24.3827F, -2.0F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(116, 125)
                        .addBox(14.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(94, 179)
                        .addBox(10.7494F, -11.0F, -4.1854F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r76 = consolepieceside7.addOrReplaceChild(
                "cube_r76",
                CubeListBuilder.create()
                        .texOffs(86, 164)
                        .addBox(-0.1414F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.7F, -12.25F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition consolepieceside8 = consolepiece4.addOrReplaceChild(
                "consolepieceside8",
                CubeListBuilder.create()
                        .texOffs(0, 155)
                        .addBox(-18.8478F, -17.0F, -5.156F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 202)
                        .addBox(-17.5867F, -18.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 172)
                        .addBox(-17.5867F, -14.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 141)
                        .addBox(-27.3827F, -2.05F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(136, 70)
                        .addBox(-26.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(146, 179)
                        .addBox(-13.9994F, -11.0F, -3.7524F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r77 = consolepieceside8.addOrReplaceChild(
                "cube_r77",
                CubeListBuilder.create()
                        .texOffs(98, 164)
                        .addBox(-0.8586F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.9F, -12.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition consolepiece5 = base.addOrReplaceChild(
                "consolepiece5",
                CubeListBuilder.create()
                        .texOffs(72, 132)
                        .addBox(-4.9587F, -17.0F, -18.9006F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 205)
                        .addBox(-4.8625F, -18.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(153, 5)
                        .addBox(-4.8625F, -14.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 95)
                        .addBox(-6.5F, -31.45F, -11.25F, 13.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 134)
                        .addBox(-5.5F, -28.5F, -10.25F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 54)
                        .addBox(-6.0F, -10.0F, -11.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 12)
                        .addBox(-3.75F, -11.0F, -14.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(54, 126)
                        .addBox(-5.0F, -5.0F, -11.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(114, 108)
                        .addBox(-7.0F, -3.0F, -13.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 66)
                        .addBox(-13.0F, -2.5F, -24.0F, 26.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 66)
                        .addBox(-7.0F, -2.5F, -14.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 18)
                        .addBox(-11.0F, -1.525F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 27)
                        .addBox(-11.0F, -2.35F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(76, 109)
                        .addBox(-8.0F, -2.0F, -27.0F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(80, 126)
                        .addBox(-5.0F, -9.0F, -10.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r78 = consolepiece5.addOrReplaceChild(
                "cube_r78",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r79 = consolepiece5.addOrReplaceChild(
                "cube_r79",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r80 = consolepiece5.addOrReplaceChild(
                "cube_r80",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r81 = consolepiece5.addOrReplaceChild(
                "cube_r81",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r82 = consolepiece5.addOrReplaceChild(
                "cube_r82",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r83 = consolepiece5.addOrReplaceChild(
                "cube_r83",
                CubeListBuilder.create()
                        .texOffs(84, 81)
                        .addBox(-8.5F, -1.2071F, -0.2929F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -16.839F, -15.4808F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r84 = consolepiece5.addOrReplaceChild(
                "cube_r84",
                CubeListBuilder.create()
                        .texOffs(38, 109)
                        .addBox(-7.5F, -1.4239F, -2.6173F, 15.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.0F, -10.75F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r85 = consolepiece5.addOrReplaceChild(
                "cube_r85",
                CubeListBuilder.create()
                        .texOffs(86, 151)
                        .addBox(-4.5F, -2.5985F, -0.1515F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.25F, -14.9F, 0.7854F, 0.0F, 0.0F));

        PartDefinition consolepieceside9 = consolepiece5.addOrReplaceChild(
                "consolepieceside9",
                CubeListBuilder.create()
                        .texOffs(48, 155)
                        .addBox(16.8891F, -17.0F, -5.0844F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 203)
                        .addBox(15.7242F, -18.0F, -4.3008F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 155)
                        .addBox(15.8236F, -14.0F, -4.3008F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(92, 143)
                        .addBox(24.3827F, -2.0F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(126, 5)
                        .addBox(14.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(180, 149)
                        .addBox(10.7494F, -11.0F, -4.1854F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r86 = consolepieceside9.addOrReplaceChild(
                "cube_r86",
                CubeListBuilder.create()
                        .texOffs(158, 164)
                        .addBox(-0.1414F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.7F, -12.25F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition consolepieceside10 = consolepiece5.addOrReplaceChild(
                "consolepieceside10",
                CubeListBuilder.create()
                        .texOffs(146, 155)
                        .addBox(-18.8478F, -17.0F, -5.156F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 202)
                        .addBox(-17.5867F, -18.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 172)
                        .addBox(-17.5867F, -14.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(112, 143)
                        .addBox(-27.3827F, -2.05F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(142, 120)
                        .addBox(-26.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 181)
                        .addBox(-13.9994F, -11.0F, -3.7524F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r87 = consolepieceside10.addOrReplaceChild(
                "cube_r87",
                CubeListBuilder.create()
                        .texOffs(166, 76)
                        .addBox(-0.8586F, -1.8914F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-15.0F, -12.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition consolepiece6 = base.addOrReplaceChild(
                "consolepiece6",
                CubeListBuilder.create()
                        .texOffs(22, 134)
                        .addBox(-4.9587F, -17.0F, -18.9006F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 205)
                        .addBox(-4.8625F, -18.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(153, 5)
                        .addBox(-4.8625F, -14.0F, -17.5F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 97)
                        .addBox(-6.5F, -31.475F, -11.25F, 13.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(134, 113)
                        .addBox(-5.5F, -28.5F, -10.25F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 58)
                        .addBox(-6.0F, -10.0F, -11.0F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 16)
                        .addBox(-3.75F, -11.0F, -14.0F, 7.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 76)
                        .addBox(-5.0F, -5.0F, -11.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 116)
                        .addBox(-7.0F, -3.0F, -13.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 68)
                        .addBox(-13.0F, -2.5F, -24.0F, 26.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 85)
                        .addBox(-7.0F, -2.5F, -14.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 36)
                        .addBox(-11.0F, -1.6F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 45)
                        .addBox(-11.0F, -2.35F, -22.0F, 23.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 113)
                        .addBox(-8.0F, -2.0F, -27.0F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 26)
                        .addBox(-5.0F, -9.0F, -10.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r88 = consolepiece6.addOrReplaceChild(
                "cube_r88",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r89 = consolepiece6.addOrReplaceChild(
                "cube_r89",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r90 = consolepiece6.addOrReplaceChild(
                "cube_r90",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r91 = consolepiece6.addOrReplaceChild(
                "cube_r91",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r92 = consolepiece6.addOrReplaceChild(
                "cube_r92",
                CubeListBuilder.create()
                        .texOffs(190, 182)
                        .addBox(-1.2071F, -1.2071F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -29.0F, -11.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r93 = consolepiece6.addOrReplaceChild(
                "cube_r93",
                CubeListBuilder.create()
                        .texOffs(42, 85)
                        .addBox(-8.5F, -1.2071F, -0.2929F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -16.839F, -15.4808F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r94 = consolepiece6.addOrReplaceChild(
                "cube_r94",
                CubeListBuilder.create()
                        .texOffs(110, 101)
                        .addBox(-7.5F, -1.4239F, -2.6173F, 15.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.0F, -10.75F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r95 = consolepiece6.addOrReplaceChild(
                "cube_r95",
                CubeListBuilder.create()
                        .texOffs(106, 151)
                        .addBox(-4.5F, -2.7071F, -0.2929F, 9.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.25F, -14.7F, 0.7854F, 0.0F, 0.0F));

        PartDefinition consolepieceside11 = consolepiece6.addOrReplaceChild(
                "consolepieceside11",
                CubeListBuilder.create()
                        .texOffs(82, 156)
                        .addBox(16.8891F, -17.0F, -5.0844F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 203)
                        .addBox(15.7242F, -18.0F, -4.3008F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 155)
                        .addBox(15.7242F, -14.0F, -4.3008F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(132, 143)
                        .addBox(24.3827F, -2.0F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(126, 73)
                        .addBox(14.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 20)
                        .addBox(10.7494F, -11.0F, -4.1854F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r96 = consolepieceside11.addOrReplaceChild(
                "cube_r96",
                CubeListBuilder.create()
                        .texOffs(166, 104)
                        .addBox(0.0F, -2.0F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.5562F, -12.25F, -0.1636F, 0.0F, 0.0F, 0.7854F));

        PartDefinition consolepieceside12 = consolepiece6.addOrReplaceChild(
                "consolepieceside12",
                CubeListBuilder.create()
                        .texOffs(96, 156)
                        .addBox(-18.8478F, -17.0F, -5.156F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 202)
                        .addBox(-17.5867F, -18.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 172)
                        .addBox(-17.5867F, -14.0F, -4.5389F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(66, 145)
                        .addBox(-27.3827F, -2.05F, -6.5718F, 3.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
                        .texOffs(142, 122)
                        .addBox(-26.0F, -2.5F, -1.4821F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(181, 23)
                        .addBox(-13.9994F, -11.0F, -3.7524F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r97 = consolepieceside12.addOrReplaceChild(
                "cube_r97",
                CubeListBuilder.create()
                        .texOffs(166, 141)
                        .addBox(-1.0F, -2.0F, -4.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.7562F, -12.25F, -0.1636F, 0.0F, 0.0F, -0.7854F));

        PartDefinition rotorbase = base.addOrReplaceChild(
                "rotorbase",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-8.0F, -30.5F, -7.0F, 17.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition controls =
                base.addOrReplaceChild("controls", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panel1 =
                controls.addOrReplaceChild("panel1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r98 = panel1.addOrReplaceChild(
                "cube_r98",
                CubeListBuilder.create()
                        .texOffs(184, 53)
                        .addBox(-8.0F, -1.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(96, 132)
                        .addBox(-5.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 50)
                        .addBox(-1.0F, -1.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0F, -18.75F, -14.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r99 = panel1.addOrReplaceChild(
                "cube_r99",
                CubeListBuilder.create()
                        .texOffs(168, 101)
                        .addBox(-2.0F, -1.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -20.5F, -12.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r100 = panel1.addOrReplaceChild(
                "cube_r100",
                CubeListBuilder.create()
                        .texOffs(36, 87)
                        .addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -24.5F, -10.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition lock = panel1.addOrReplaceChild(
                "lock",
                CubeListBuilder.create()
                        .texOffs(192, 41)
                        .addBox(-2.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 43)
                        .addBox(1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 192)
                        .addBox(2.025F, -2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(62, 9)
                        .addBox(2.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 124)
                        .addBox(-2.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 192)
                        .addBox(-3.025F, -2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -24.5F, -10.5F));

        PartDefinition powerbox = panel1.addOrReplaceChild(
                "powerbox", CubeListBuilder.create(), PartPose.offset(-2.0F, -20.0607F, -14.5607F));

        PartDefinition cube_r101 = powerbox.addOrReplaceChild(
                "cube_r101",
                CubeListBuilder.create()
                        .texOffs(74, 161)
                        .addBox(-1.0F, -1.0303F, -1.8839F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition powerswitch3 = panel1.addOrReplaceChild(
                "powerswitch3",
                CubeListBuilder.create()
                        .texOffs(54, 71)
                        .addBox(0.0F, -1.1035F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -19.2071F, -14.9142F));

        PartDefinition powerswitch1 = panel1.addOrReplaceChild(
                "powerswitch1",
                CubeListBuilder.create()
                        .texOffs(146, 98)
                        .addBox(0.0F, -2.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.5F, -19.0F, -15.25F));

        PartDefinition powerswitch2 = panel1.addOrReplaceChild(
                "powerswitch2",
                CubeListBuilder.create()
                        .texOffs(174, 115)
                        .addBox(0.0F, -2.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.5F, -19.0F, -15.25F));

        PartDefinition ceiling = panel1.addOrReplaceChild(
                "ceiling", CubeListBuilder.create(), PartPose.offset(-5.0F, -19.6124F, -14.5518F));

        PartDefinition cube_r102 = ceiling.addOrReplaceChild(
                "cube_r102",
                CubeListBuilder.create()
                        .texOffs(158, 179)
                        .addBox(-7.5F, -1.5F, -0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.0F, 1.1124F, 0.3018F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r103 = ceiling.addOrReplaceChild(
                "cube_r103",
                CubeListBuilder.create()
                        .texOffs(54, 123)
                        .addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.125F, -0.125F, 0.3927F, 0.0F, 0.0F));

        PartDefinition doorbutton = panel1.addOrReplaceChild(
                "doorbutton", CubeListBuilder.create(), PartPose.offset(2.0F, -17.75F, -14.5F));

        PartDefinition cube_r104 = doorbutton.addOrReplaceChild(
                "cube_r104",
                CubeListBuilder.create()
                        .texOffs(64, 192)
                        .addBox(2.75F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(60, 192)
                        .addBox(1.5F, -1.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition chambuttons = panel1.addOrReplaceChild(
                "chambuttons", CubeListBuilder.create(), PartPose.offset(-1.0F, -19.5F, -12.0F));

        PartDefinition redcham =
                chambuttons.addOrReplaceChild("redcham", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -1.0F));

        PartDefinition chambuttons2 =
                redcham.addOrReplaceChild("chambuttons2", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 1.0F));

        PartDefinition cube_r105 = chambuttons2.addOrReplaceChild(
                "cube_r105",
                CubeListBuilder.create()
                        .texOffs(150, 42)
                        .addBox(2.5F, -2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(114, 126)
                        .addBox(-0.5F, -2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 121)
                        .addBox(-1.5F, -2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition chambuttons1 =
                redcham.addOrReplaceChild("chambuttons1", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 1.0F));

        PartDefinition cube_r106 = chambuttons1.addOrReplaceChild(
                "cube_r106",
                CubeListBuilder.create()
                        .texOffs(166, 96)
                        .addBox(0.5F, -2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(164, 111)
                        .addBox(1.5F, -2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 151)
                        .addBox(3.5F, -2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition greencham = chambuttons.addOrReplaceChild(
                "greencham", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.25F));

        PartDefinition chambuttons4 = greencham.addOrReplaceChild(
                "chambuttons4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.25F));

        PartDefinition cube_r107 = chambuttons4.addOrReplaceChild(
                "cube_r107",
                CubeListBuilder.create()
                        .texOffs(176, 187)
                        .addBox(-1.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 133)
                        .addBox(0.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 170)
                        .addBox(2.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition chambuttons3 = greencham.addOrReplaceChild(
                "chambuttons3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.25F));

        PartDefinition cube_r108 = chambuttons3.addOrReplaceChild(
                "cube_r108",
                CubeListBuilder.create()
                        .texOffs(156, 191)
                        .addBox(-2.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(174, 190)
                        .addBox(1.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(184, 189)
                        .addBox(3.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 189)
                        .addBox(4.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panel2 = controls.addOrReplaceChild(
                "panel2",
                CubeListBuilder.create()
                        .texOffs(172, 161)
                        .addBox(-4.0F, -25.2426F, -10.7218F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r109 = panel2.addOrReplaceChild(
                "cube_r109",
                CubeListBuilder.create()
                        .texOffs(12, 170)
                        .addBox(-3.0F, -1.4239F, -0.6173F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -19.2374F, -12.8735F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r110 = panel2.addOrReplaceChild(
                "cube_r110",
                CubeListBuilder.create()
                        .texOffs(182, 97)
                        .addBox(2.0F, 1.2929F, 2.2071F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 46)
                        .addBox(-6.0F, 1.2929F, 2.2071F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -18.0F, -18.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone7 = panel2.addOrReplaceChild(
                "bone7",
                CubeListBuilder.create()
                        .texOffs(186, 0)
                        .addBox(-1.1353F, -2.1353F, -1.4619F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, -20.0F, -12.0F, -1.0409F, 0.7119F, 0.3655F));

        PartDefinition bone8 = panel2.addOrReplaceChild(
                "bone8",
                CubeListBuilder.create()
                        .texOffs(186, 0)
                        .addBox(-1.1353F, -2.1353F, -1.4619F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -20.0F, -12.0F, -1.0409F, 0.7119F, 0.3655F));

        PartDefinition diagnosticbutton = panel2.addOrReplaceChild(
                "diagnosticbutton",
                CubeListBuilder.create()
                        .texOffs(84, 192)
                        .addBox(4.5F, -1.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 87)
                        .addBox(2.5F, -1.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(88, 192)
                        .addBox(0.5F, -1.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-4.0F, -23.2426F, -10.2218F));

        PartDefinition telepathicbutton = panel2.addOrReplaceChild(
                "telepathicbutton", CubeListBuilder.create(), PartPose.offset(0.0F, -20.2374F, -12.8735F));

        PartDefinition cube_r111 = telepathicbutton.addOrReplaceChild(
                "cube_r111",
                CubeListBuilder.create()
                        .texOffs(168, 193)
                        .addBox(2.0F, -2.4239F, -0.6173F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(166, 193)
                        .addBox(1.0F, -2.4239F, -0.6173F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(164, 193)
                        .addBox(0.0F, -2.4239F, -0.6173F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(142, 193)
                        .addBox(-1.0F, -2.4239F, -0.6173F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(100, 193)
                        .addBox(-2.0F, -2.4239F, -0.6173F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panel3 = controls.addOrReplaceChild(
                "panel3",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.5036F, -26.8134F, -11.5624F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(0, 0)
                        .addBox(2.4964F, -26.8134F, -11.5624F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r112 = panel3.addOrReplaceChild(
                "cube_r112",
                CubeListBuilder.create()
                        .texOffs(82, 153)
                        .addBox(4.5F, -1.9239F, -0.1173F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(92, 192)
                        .addBox(4.5F, -2.9239F, -0.1173F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(92, 192)
                        .addBox(-0.5F, -2.9239F, -0.1173F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(50, 128)
                        .addBox(-0.5F, -1.9239F, -0.1173F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -18.167F, -14.9624F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r113 = panel3.addOrReplaceChild(
                "cube_r113",
                CubeListBuilder.create()
                        .texOffs(186, 108)
                        .addBox(9.5F, -1.4571F, 0.2071F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 180)
                        .addBox(-0.5F, -1.4571F, 0.2071F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.5F, -18.167F, -14.9624F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r114 = panel3.addOrReplaceChild(
                "cube_r114",
                CubeListBuilder.create()
                        .texOffs(0, 188)
                        .addBox(-1.7071F, -1.7071F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 187)
                        .addBox(-1.7071F, -1.7071F, -1.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -24.667F, -10.2124F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r115 = panel3.addOrReplaceChild(
                "cube_r115",
                CubeListBuilder.create()
                        .texOffs(164, 187)
                        .addBox(-1.7071F, -1.7071F, -1.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(158, 187)
                        .addBox(-1.7071F, -1.7071F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, -24.667F, -10.2124F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r116 = panel3.addOrReplaceChild(
                "cube_r116",
                CubeListBuilder.create()
                        .texOffs(156, 115)
                        .addBox(-3.0F, -0.9571F, -3.2929F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.4651F, -12.3108F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r117 = panel3.addOrReplaceChild(
                "cube_r117",
                CubeListBuilder.create()
                        .texOffs(48, 186)
                        .addBox(-0.5F, -1.7071F, -1.7071F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(62, 146)
                        .addBox(8.5F, -1.7071F, -1.7071F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(42, 139)
                        .addBox(7.5F, -2.7071F, -1.7071F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(92, 137)
                        .addBox(0.5F, -2.7071F, -1.7071F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, -20.4651F, -11.3108F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r118 = panel3.addOrReplaceChild(
                "cube_r118",
                CubeListBuilder.create()
                        .texOffs(130, 156)
                        .addBox(-3.0F, -4.9239F, -0.3827F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -19.0F, -14.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition gravitybutton = panel3.addOrReplaceChild(
                "gravitybutton", CubeListBuilder.create(), PartPose.offset(-5.5F, -19.167F, -14.9624F));

        PartDefinition cube_r119 = gravitybutton.addOrReplaceChild(
                "cube_r119",
                CubeListBuilder.create()
                        .texOffs(192, 89)
                        .addBox(0.0F, -1.9571F, 0.7071F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition shieldbutton = panel3.addOrReplaceChild(
                "shieldbutton", CubeListBuilder.create(), PartPose.offset(-5.5F, -19.167F, -14.9624F));

        PartDefinition cube_r120 = shieldbutton.addOrReplaceChild(
                "cube_r120",
                CubeListBuilder.create()
                        .texOffs(192, 91)
                        .addBox(10.0F, -1.9571F, 0.7071F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition stabiliserinput = panel3.addOrReplaceChild(
                "stabiliserinput", CubeListBuilder.create(), PartPose.offset(0.0981F, -19.167F, -16.4624F));

        PartDefinition cube_r121 = stabiliserinput.addOrReplaceChild(
                "cube_r121",
                CubeListBuilder.create()
                        .texOffs(138, 175)
                        .addBox(1.5F, -1.9239F, -0.1173F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(138, 175)
                        .addBox(2.5F, -1.9239F, -0.1173F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(138, 175)
                        .addBox(3.5F, -1.9239F, -0.1173F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5981F, 1.0F, 1.5F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panel4 = controls.addOrReplaceChild(
                "panel4",
                CubeListBuilder.create()
                        .texOffs(30, 103)
                        .addBox(-3.25F, -20.8713F, 13.2071F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(66, 143)
                        .addBox(-4.75F, -20.8713F, 13.2071F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(170, 193)
                        .addBox(-4.75F, -20.8713F, 15.2071F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 193)
                        .addBox(-3.25F, -20.8713F, 15.2071F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(186, 63)
                        .addBox(1.0F, -26.0F, 9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(112, 187)
                        .addBox(2.0F, -28.5F, 9.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 189)
                        .addBox(-0.25F, -28.5F, 9.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(186, 67)
                        .addBox(1.0F, -25.975F, 9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(158, 182)
                        .addBox(-4.0F, -28.0F, 9.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 95)
                        .addBox(-3.875F, -28.125F, 11.05F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(138, 186)
                        .addBox(-4.0F, -24.0F, 8.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r122 = panel4.addOrReplaceChild(
                "cube_r122",
                CubeListBuilder.create()
                        .texOffs(186, 75)
                        .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(186, 71)
                        .addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.75F, 11.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r123 = panel4.addOrReplaceChild(
                "cube_r123",
                CubeListBuilder.create()
                        .texOffs(144, 189)
                        .addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.8107F, -26.1465F, 10.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r124 = panel4.addOrReplaceChild(
                "cube_r124",
                CubeListBuilder.create()
                        .texOffs(30, 117)
                        .addBox(-2.5F, 0.0F, -0.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -19.3713F, 15.4571F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r125 = panel4.addOrReplaceChild(
                "cube_r125",
                CubeListBuilder.create()
                        .texOffs(188, 4)
                        .addBox(-0.475F, -1.25F, -3.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 118)
                        .addBox(1.025F, -1.25F, -5.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, -19.0F, 14.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r126 = panel4.addOrReplaceChild(
                "cube_r126",
                CubeListBuilder.create()
                        .texOffs(168, 20)
                        .addBox(-1.025F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(156, 135)
                        .addBox(5.0F, -1.0F, -3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, -19.0F, 14.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone9 = panel4.addOrReplaceChild(
                "bone9",
                CubeListBuilder.create()
                        .texOffs(40, 186)
                        .addBox(2.3725F, -0.5226F, -5.9034F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, -18.0F, 14.0F, -0.9355F, 0.4981F, -0.5749F));

        PartDefinition dimension = panel4.addOrReplaceChild(
                "dimension", CubeListBuilder.create(), PartPose.offset(-2.5F, -19.1213F, 15.4571F));

        PartDefinition cube_r127 = dimension.addOrReplaceChild(
                "cube_r127",
                CubeListBuilder.create()
                        .texOffs(190, 193)
                        .addBox(-0.5F, 0.0F, 0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(188, 193)
                        .addBox(0.475F, 0.0F, 0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(186, 193)
                        .addBox(-1.5F, 0.0F, 0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(184, 193)
                        .addBox(-2.475F, 0.0F, 0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition helmic = panel4.addOrReplaceChild(
                "helmic",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(3.0F, -20.1328F, 14.75F, -0.3927F, 0.0F, 0.0F));

        PartDefinition helmicball = helmic.addOrReplaceChild(
                "helmicball",
                CubeListBuilder.create()
                        .texOffs(186, 49)
                        .addBox(-1.0F, 1.0761F, -1.0077F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(104, 126)
                        .addBox(-2.0F, 1.5761F, -0.5077F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 128)
                        .addBox(-0.5F, 1.5761F, -2.0077F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(174, 72)
                        .addBox(-0.5F, 0.0761F, -0.5077F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.6172F, -0.375F));

        PartDefinition cube_r128 = helmicball.addOrReplaceChild(
                "cube_r128",
                CubeListBuilder.create()
                        .texOffs(64, 189)
                        .addBox(0.1533F, -2.6533F, -0.8827F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.0F, 0.375F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r129 = helmicball.addOrReplaceChild(
                "cube_r129",
                CubeListBuilder.create()
                        .texOffs(36, 188)
                        .addBox(-1.1533F, -2.6533F, -0.8827F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.0F, 0.375F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r130 = helmicball.addOrReplaceChild(
                "cube_r130",
                CubeListBuilder.create()
                        .texOffs(70, 170)
                        .addBox(-0.5F, -2.9239F, -0.1173F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 188)
                        .addBox(-0.5F, -1.4239F, 0.3827F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.0F, 0.375F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r131 = helmicball.addOrReplaceChild(
                "cube_r131",
                CubeListBuilder.create()
                        .texOffs(182, 123)
                        .addBox(-0.2294F, -1.4239F, -2.2706F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(156, 141)
                        .addBox(-1.7294F, -1.4239F, -0.7706F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.0F, 0.375F, 0.0F, 0.7854F, 0.0F));

        PartDefinition rotatedial = panel4.addOrReplaceChild(
                "rotatedial",
                CubeListBuilder.create()
                        .texOffs(-1, -1)
                        .addBox(-0.5F, -1.0F, -0.725F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(2.0F, -25.0F, 11.0F));

        PartDefinition saver2 = panel4.addOrReplaceChild(
                "saver2",
                CubeListBuilder.create()
                        .texOffs(148, 189)
                        .addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, -23.5F, 10.5F));

        PartDefinition saver1 = panel4.addOrReplaceChild(
                "saver1",
                CubeListBuilder.create()
                        .texOffs(176, 189)
                        .addBox(1.975F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(180, 189)
                        .addBox(0.025F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-4.0F, -23.5F, 10.5F));

        PartDefinition analogdial = panel4.addOrReplaceChild(
                "analogdial",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -21.091F, 11.5732F, -0.7854F, 0.0F, 0.0F));

        PartDefinition analoggydial = analogdial.addOrReplaceChild(
                "analoggydial",
                CubeListBuilder.create()
                        .texOffs(-1, 0)
                        .addBox(-0.5F, -0.5F, -1.4F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.025F, -0.6321F, -0.8107F));

        PartDefinition panel5 = controls.addOrReplaceChild(
                "panel5",
                CubeListBuilder.create()
                        .texOffs(40, 190)
                        .addBox(-0.8995F, -26.5F, -10.875F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(130, 116)
                        .addBox(3.75F, -27.0F, -10.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(46, 134)
                        .addBox(0.75F, -27.0F, -10.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 161)
                        .addBox(-3.8995F, -24.5F, -10.875F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(60, 120)
                        .addBox(-4.5F, -17.2322F, -22.5104F, 9.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(74, 188)
                        .addBox(-4.0F, -17.7322F, -22.2604F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(106, 192)
                        .addBox(-4.0F, -17.7322F, -20.0104F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(110, 192)
                        .addBox(-2.75F, -17.7322F, -20.0104F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(114, 192)
                        .addBox(-2.75F, -17.7322F, -21.2604F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 192)
                        .addBox(-1.5F, -17.7322F, -21.2604F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 118)
                        .addBox(-1.5F, -17.7322F, -20.0104F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 120)
                        .addBox(-0.25F, -17.7322F, -21.2604F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(122, 192)
                        .addBox(-0.25F, -17.7322F, -20.0104F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(80, 188)
                        .addBox(1.0F, -17.7322F, -21.2604F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(186, 141)
                        .addBox(2.25F, -17.7322F, -21.2604F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r132 = panel5.addOrReplaceChild(
                "cube_r132",
                CubeListBuilder.create()
                        .texOffs(106, 173)
                        .addBox(3.5F, -2.9239F, -2.6173F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 167)
                        .addBox(-5.5F, -2.9239F, -2.6173F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(146, 87)
                        .addBox(-5.0F, -2.9239F, -2.6173F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -19.0F, -11.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r133 = panel5.addOrReplaceChild(
                "cube_r133",
                CubeListBuilder.create()
                        .texOffs(152, 20)
                        .addBox(1.5F, -0.6739F, -4.3827F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 172)
                        .addBox(4.5F, -1.6739F, -2.3827F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(168, 182)
                        .addBox(-0.5F, -1.6739F, -2.3827F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -14.7322F, -18.5104F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r134 = panel5.addOrReplaceChild(
                "cube_r134",
                CubeListBuilder.create()
                        .texOffs(42, 71)
                        .addBox(-2.5F, -1.2071F, -0.9571F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.25F, -16.2322F, -22.0104F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r135 = panel5.addOrReplaceChild(
                "cube_r135",
                CubeListBuilder.create()
                        .texOffs(174, 4)
                        .addBox(-7.25F, -0.2071F, 2.5821F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(186, 144)
                        .addBox(-1.0F, -0.2071F, 2.5821F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.25F, -16.2322F, -20.1354F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r136 = panel5.addOrReplaceChild(
                "cube_r136",
                CubeListBuilder.create()
                        .texOffs(156, 119)
                        .addBox(-4.5F, -0.9239F, 0.3777F, 9.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -19.0036F, -15.9533F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r137 = panel5.addOrReplaceChild(
                "cube_r137",
                CubeListBuilder.create()
                        .texOffs(120, 87)
                        .addBox(-4.5F, -4.2071F, -6.7929F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -19.0F, -11.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r138 = panel5.addOrReplaceChild(
                "cube_r138",
                CubeListBuilder.create()
                        .texOffs(144, 124)
                        .addBox(-2.1664F, -2.2478F, -1.375F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.5F, -23.5F, -9.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r139 = panel5.addOrReplaceChild(
                "cube_r139",
                CubeListBuilder.create()
                        .texOffs(68, 188)
                        .addBox(-1.7071F, -1.7071F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.75F, -24.5F, -10.25F, 0.0F, 0.0F, 0.7854F));

        PartDefinition bone10 = panel5.addOrReplaceChild(
                "bone10",
                CubeListBuilder.create()
                        .texOffs(12, 188)
                        .addBox(-2.1664F, -1.2478F, -0.625F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(18, 188)
                        .addBox(-0.0877F, -3.3692F, -0.625F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 188)
                        .addBox(-2.2877F, -5.4691F, -0.625F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.7679F, -23.0F, -10.4019F, 0.0F, 0.0F, 0.7854F));

        PartDefinition zigzaglever = panel5.addOrReplaceChild(
                "zigzaglever",
                CubeListBuilder.create()
                        .texOffs(30, 188)
                        .addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.3486F, -24.012F, -12.0269F));

        PartDefinition axisinput = panel5.addOrReplaceChild(
                "axisinput",
                CubeListBuilder.create()
                        .texOffs(102, 192)
                        .addBox(-2.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.25F, -25.0F, -10.25F));

        PartDefinition keyboard =
                panel5.addOrReplaceChild("keyboard", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, -11.0F));

        PartDefinition kbuttons3 =
                keyboard.addOrReplaceChild("kbuttons3", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r140 = kbuttons3.addOrReplaceChild(
                "cube_r140",
                CubeListBuilder.create()
                        .texOffs(126, 206)
                        .addBox(-3.6F, -3.1739F, -2.1173F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 192)
                        .addBox(-2.35F, -3.1739F, -0.8673F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 192)
                        .addBox(1.4F, -3.1739F, -0.8673F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition kbuttons2 =
                keyboard.addOrReplaceChild("kbuttons2", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r141 = kbuttons2.addOrReplaceChild(
                "cube_r141",
                CubeListBuilder.create()
                        .texOffs(192, 128)
                        .addBox(0.15F, -3.1739F, -0.8673F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(172, 35)
                        .addBox(-2.1F, -3.1739F, -2.1173F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 126)
                        .addBox(2.65F, -3.1739F, -0.8673F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition kbuttons1 =
                keyboard.addOrReplaceChild("kbuttons1", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r142 = kbuttons1.addOrReplaceChild(
                "cube_r142",
                CubeListBuilder.create()
                        .texOffs(148, 206)
                        .addBox(2.65F, -3.1739F, -2.1173F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(144, 192)
                        .addBox(-1.1F, -3.1739F, -0.8673F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 130)
                        .addBox(-3.6F, -3.1739F, -0.8673F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panel6 = controls.addOrReplaceChild(
                "panel6",
                CubeListBuilder.create()
                        .texOffs(0, 183)
                        .addBox(-4.0F, -27.0F, -11.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 183)
                        .addBox(-4.0F, -27.0F, -10.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 183)
                        .addBox(1.0F, -27.0F, -10.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(30, 183)
                        .addBox(1.0F, -27.0F, -11.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r143 = panel6.addOrReplaceChild(
                "cube_r143",
                CubeListBuilder.create()
                        .texOffs(178, 58)
                        .addBox(-1.0F, -0.25F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -19.4984F, -15.4683F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r144 = panel6.addOrReplaceChild(
                "cube_r144",
                CubeListBuilder.create()
                        .texOffs(184, 133)
                        .addBox(-1.0F, -2.7071F, -0.2929F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(184, 100)
                        .addBox(-8.0F, -2.7071F, -0.2929F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -17.0F, -14.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r145 = panel6.addOrReplaceChild(
                "cube_r145",
                CubeListBuilder.create()
                        .texOffs(48, 178)
                        .addBox(-0.5F, -1.9239F, -1.1173F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.5F, -19.6465F, -12.6967F, 0.3927F, 0.0F, 0.0F));

        PartDefinition timewheel = panel6.addOrReplaceChild(
                "timewheel",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.475F, -21.3008F, -13.3407F, 0.3927F, 0.0F, 0.0F));

        PartDefinition timedial = timewheel.addOrReplaceChild(
                "timedial",
                CubeListBuilder.create()
                        .texOffs(178, 182)
                        .addBox(-1.5F, 0.0131F, -1.5587F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.025F, -1.1847F, 0.4601F));

        PartDefinition cube_r146 = timedial.addOrReplaceChild(
                "cube_r146",
                CubeListBuilder.create()
                        .texOffs(192, 151)
                        .addBox(-0.9059F, -1.8858F, -0.0941F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.8989F, 0.3673F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r147 = timedial.addOrReplaceChild(
                "cube_r147",
                CubeListBuilder.create()
                        .texOffs(192, 149)
                        .addBox(0.9059F, -3.3858F, -1.0941F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.5F, 3.4239F, -0.6327F, 0.0F, -0.7854F, 0.0F));

        PartDefinition clockdial1 = panel6.addOrReplaceChild(
                "clockdial1",
                CubeListBuilder.create()
                        .texOffs(194, 0)
                        .addBox(-0.5F, -1.5F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.5F, -25.5F, -11.15F));

        PartDefinition clockdial2 = panel6.addOrReplaceChild(
                "clockdial2",
                CubeListBuilder.create()
                        .texOffs(194, 2)
                        .addBox(-0.5F, -2.5F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.5F, -24.5F, -11.15F));

        PartDefinition timebutton2 = panel6.addOrReplaceChild(
                "timebutton2", CubeListBuilder.create(), PartPose.offset(3.0F, -18.0F, -14.0F));

        PartDefinition cube_r148 = timebutton2.addOrReplaceChild(
                "cube_r148",
                CubeListBuilder.create()
                        .texOffs(14, 159)
                        .addBox(-0.5F, -2.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition timebutton1 = panel6.addOrReplaceChild(
                "timebutton1", CubeListBuilder.create(), PartPose.offset(3.0F, -18.0F, -14.0F));

        PartDefinition cube_r149 = timebutton1.addOrReplaceChild(
                "cube_r149",
                CubeListBuilder.create()
                        .texOffs(62, 159)
                        .addBox(-7.5F, -2.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition pressurewheel = panel6.addOrReplaceChild(
                "pressurewheel",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.025F, -19.2508F, -15.8407F, 0.7854F, 0.0F, 0.0F));

        PartDefinition pressuredial = pressurewheel.addOrReplaceChild(
                "pressuredial",
                CubeListBuilder.create()
                        .texOffs(40, 183)
                        .addBox(-1.5F, 0.0131F, -1.5587F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.025F, -1.1847F, 0.4601F));

        PartDefinition cube_r150 = pressuredial.addOrReplaceChild(
                "cube_r150",
                CubeListBuilder.create()
                        .texOffs(192, 159)
                        .addBox(-0.9059F, -1.8858F, -0.0941F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.8989F, 0.3673F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r151 = pressuredial.addOrReplaceChild(
                "cube_r151",
                CubeListBuilder.create()
                        .texOffs(192, 157)
                        .addBox(0.9059F, -3.3858F, -1.0941F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.5F, 3.4239F, -0.6327F, 0.0F, -0.7854F, 0.0F));

        PartDefinition pillar1 = controls.addOrReplaceChild(
                "pillar1",
                CubeListBuilder.create()
                        .texOffs(170, 85)
                        .addBox(-1.5F, -19.6893F, -19.3965F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(174, 192)
                        .addBox(-1.0F, -25.0F, -12.25F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r152 = pillar1.addOrReplaceChild(
                "cube_r152",
                CubeListBuilder.create()
                        .texOffs(188, 113)
                        .addBox(-1.525F, -1.9239F, -0.3827F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -15.7574F, -21.1213F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r153 = pillar1.addOrReplaceChild(
                "cube_r153",
                CubeListBuilder.create()
                        .texOffs(44, 190)
                        .addBox(0.0F, -0.2071F, -1.2929F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 183)
                        .addBox(-1.025F, -0.2071F, -0.2929F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.0F, -19.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition phaser = pillar1.addOrReplaceChild(
                "phaser",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -15.7574F, -21.1213F, -0.3927F, 0.0F, 0.0F));

        PartDefinition shellphaser = phaser.addOrReplaceChild(
                "shellphaser",
                CubeListBuilder.create()
                        .texOffs(192, 164)
                        .addBox(-0.5F, 0.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(80, 183)
                        .addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 166)
                        .addBox(-0.5F, -1.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 168)
                        .addBox(-1.5F, -0.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 170)
                        .addBox(0.5F, -1.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 172)
                        .addBox(0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.9239F, -0.3827F));

        PartDefinition laser = pillar1.addOrReplaceChild(
                "laser", CubeListBuilder.create(), PartPose.offset(1.5F, -19.1893F, -18.8965F));

        PartDefinition cube_r154 = laser.addOrReplaceChild(
                "cube_r154",
                CubeListBuilder.create()
                        .texOffs(186, 79)
                        .addBox(-4.0F, -6.1642F, -1.3107F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(60, 172)
                        .addBox(-0.1F, -6.1642F, -1.3107F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 163)
                        .addBox(-3.05F, -6.1642F, -1.3107F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition pillar2 = controls.addOrReplaceChild(
                "pillar2",
                CubeListBuilder.create()
                        .texOffs(14, 155)
                        .addBox(12.0F, -26.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r155 = pillar2.addOrReplaceChild(
                "cube_r155",
                CubeListBuilder.create()
                        .texOffs(52, 189)
                        .addBox(3.0F, -1.0F, -0.975F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(16.4651F, -20.3107F, -0.1F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r156 = pillar2.addOrReplaceChild(
                "cube_r156",
                CubeListBuilder.create()
                        .texOffs(185, 188)
                        .addBox(1.0F, 0.0F, -0.975F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(187, 177)
                        .addBox(-2.0F, 0.0F, -0.975F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.0F, -22.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition height = pillar2.addOrReplaceChild(
                "height",
                CubeListBuilder.create()
                        .texOffs(184, 137)
                        .addBox(-0.5F, 0.0F, -2.0F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(194, 4)
                        .addBox(-0.5F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 174)
                        .addBox(-0.5F, -2.0F, 0.975F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(194, 6)
                        .addBox(-0.5F, -2.0F, -2.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(178, 192)
                        .addBox(-0.5F, -2.0F, -1.975F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(12.5F, -25.0F, 0.0F));

        PartDefinition wheel = pillar2.addOrReplaceChild(
                "wheel",
                CubeListBuilder.create()
                        .texOffs(203, 0)
                        .addBox(-0.4926F, -0.6642F, -0.475F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(126, 151)
                        .addBox(-2.5F, 0.0F, -2.475F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(192, 188)
                        .addBox(-2.4926F, -0.9393F, 0.525F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(20.7077F, -18.8965F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition pillar3 = controls.addOrReplaceChild(
                "pillar3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition cube_r157 = pillar3.addOrReplaceChild(
                "cube_r157",
                CubeListBuilder.create()
                        .texOffs(20, 173)
                        .addBox(-0.95F, -3.9239F, 0.5577F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.0F, -22.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r158 = pillar3.addOrReplaceChild(
                "cube_r158",
                CubeListBuilder.create()
                        .texOffs(184, 153)
                        .addBox(-1.025F, -0.2071F, -1.2929F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -18.206F, -19.5464F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r159 = pillar3.addOrReplaceChild(
                "cube_r159",
                CubeListBuilder.create()
                        .texOffs(186, 161)
                        .addBox(-1.025F, -0.4239F, -5.1173F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 28)
                        .addBox(-1.525F, -0.9239F, -2.1173F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -22.0F, -13.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition monitor = pillar3.addOrReplaceChild(
                "monitor", CubeListBuilder.create(), PartPose.offset(0.0F, -18.706F, -20.0464F));

        PartDefinition cube_r160 = monitor.addOrReplaceChild(
                "cube_r160",
                CubeListBuilder.create()
                        .texOffs(58, 189)
                        .addBox(-0.525F, -0.7071F, -0.7929F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 0.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r161 = monitor.addOrReplaceChild(
                "cube_r161",
                CubeListBuilder.create()
                        .texOffs(192, 190)
                        .addBox(-0.525F, -0.9239F, 0.8673F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5607F, -0.5607F, -0.3927F, 0.0F, 0.0F));

        PartDefinition bone18 = pillar3.addOrReplaceChild(
                "bone18",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -20.7399F, -17.3488F, 0.3927F, 0.0F, 0.0F));

        PartDefinition plotter = bone18.addOrReplaceChild(
                "plotter",
                CubeListBuilder.create()
                        .texOffs(192, 192)
                        .addBox(-0.525F, 0.2208F, -0.4969F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 193)
                        .addBox(-0.525F, -0.2792F, -0.4969F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(186, 147)
                        .addBox(-1.025F, 0.7208F, -0.9969F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(90, 187)
                        .addBox(-1.025F, 0.2208F, -0.9969F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.9239F, 0.3827F));

        PartDefinition bone13 =
                plotter.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.9239F, -0.3827F));

        PartDefinition cube_r162 = bone13.addOrReplaceChild(
                "cube_r162",
                CubeListBuilder.create()
                        .texOffs(186, 111)
                        .addBox(-1.228F, -0.4429F, -0.797F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone11 = pillar3.addOrReplaceChild(
                "bone11", CubeListBuilder.create(), PartPose.offset(0.0F, -22.3324F, -14.4142F));

        PartDefinition bone16 = bone11.addOrReplaceChild(
                "bone16", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition primarymovy = bone16.addOrReplaceChild(
                "primarymovy",
                CubeListBuilder.create()
                        .texOffs(186, 185)
                        .addBox(-1.025F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(190, 57)
                        .addBox(-0.525F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.9239F, 0.3827F));

        PartDefinition bone12 = primarymovy.addOrReplaceChild(
                "bone12",
                CubeListBuilder.create()
                        .texOffs(190, 60)
                        .addBox(-1.1733F, -2.9083F, -1.1173F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(176, 118)
                        .addBox(-2.9233F, -1.1583F, -1.1173F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.9239F, 0.6173F, 0.0F, 0.0F, 0.7777F));

        PartDefinition pump = pillar3.addOrReplaceChild(
                "pump",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -11.0F, -22.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition pumpy = pump.addOrReplaceChild(
                "pumpy",
                CubeListBuilder.create()
                        .texOffs(186, 81)
                        .addBox(-0.95F, -5.0F, 0.175F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(100, 183)
                        .addBox(-0.45F, -3.0F, 0.675F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.9239F, 0.3827F));

        PartDefinition pillar4 = controls.addOrReplaceChild(
                "pillar4",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-21.0F, 0.0F, 37.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition cube_r163 = pillar4.addOrReplaceChild(
                "cube_r163",
                CubeListBuilder.create()
                        .texOffs(123, 186)
                        .addBox(-1.1F, -0.6739F, 0.9827F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -20.7071F, 26.9498F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r164 = pillar4.addOrReplaceChild(
                "cube_r164",
                CubeListBuilder.create()
                        .texOffs(124, 185)
                        .addBox(-0.75F, -0.2071F, 0.9571F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 173)
                        .addBox(-0.75F, -1.2071F, 1.9571F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -17.0F, 21.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition throttle = pillar4.addOrReplaceChild(
                "throttle",
                CubeListBuilder.create()
                        .texOffs(104, 187)
                        .addBox(-1.5F, -2.0F, -2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.175F, -19.8588F, 23.0303F, -1.0472F, 0.0F, 0.0F));

        PartDefinition cube_r165 = throttle.addOrReplaceChild(
                "cube_r165",
                CubeListBuilder.create()
                        .texOffs(24, 129)
                        .addBox(1.35F, -3.4659F, 1.9912F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.175F, 2.3588F, -1.1643F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone17 = pillar4.addOrReplaceChild(
                "bone17",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -20.7071F, 28.9497F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bell = bone17.addOrReplaceChild(
                "bell",
                CubeListBuilder.create()
                        .texOffs(62, 155)
                        .addBox(-0.925F, 0.0F, -1.05F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.25F, -2.9239F, 0.6327F));

        PartDefinition bone14 =
                bell.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offset(-0.25F, 2.9239F, -2.6327F));

        PartDefinition cube_r166 = bone14.addOrReplaceChild(
                "cube_r166",
                CubeListBuilder.create()
                        .texOffs(4, 193)
                        .addBox(-0.7706F, 1.0761F, -0.2294F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.3F, -4.75F, 2.2F, 0.0F, 0.7854F, 0.0F));

        PartDefinition pillar5 = controls.addOrReplaceChild(
                "pillar5",
                CubeListBuilder.create()
                        .texOffs(186, 104)
                        .addBox(-17.0F, -23.0F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r167 = pillar5.addOrReplaceChild(
                "cube_r167",
                CubeListBuilder.create()
                        .texOffs(126, 175)
                        .addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(60, 119)
                        .addBox(-4.5F, -1.5F, 0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(60, 68)
                        .addBox(-5.5F, -1.5F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 68)
                        .addBox(-6.5F, -1.5F, 0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(184, 17)
                        .addBox(-6.5F, -1.4F, -0.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-15.5F, -21.5F, -0.5F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r168 = pillar5.addOrReplaceChild(
                "cube_r168",
                CubeListBuilder.create()
                        .texOffs(184, 84)
                        .addBox(-2.5F, -0.5F, -0.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(-15.5F, -21.5F, -0.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition handbrake = pillar5.addOrReplaceChild(
                "handbrake", CubeListBuilder.create(), PartPose.offset(-15.5F, -21.5F, -2.5F));

        PartDefinition cube_r169 = handbrake.addOrReplaceChild(
                "cube_r169",
                CubeListBuilder.create()
                        .texOffs(8, 193)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.148F, -2.7716F, 3.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r170 = handbrake.addOrReplaceChild(
                "cube_r170",
                CubeListBuilder.create()
                        .texOffs(40, 147)
                        .addBox(-1.5F, -6.5F, -0.5F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(154, 183)
                        .addBox(-0.5F, -6.5F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition handbrakepart = handbrake.addOrReplaceChild(
                "handbrakepart", CubeListBuilder.create(), PartPose.offset(1.5F, -2.5F, 2.0F));

        PartDefinition cube_r171 = handbrakepart.addOrReplaceChild(
                "cube_r171",
                CubeListBuilder.create()
                        .texOffs(20, 141)
                        .addBox(-1.5F, -3.5F, 0.5F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.5F, 2.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition pillar6 = controls.addOrReplaceChild(
                "pillar6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r172 = pillar6.addOrReplaceChild(
                "cube_r172",
                CubeListBuilder.create()
                        .texOffs(132, 187)
                        .addBox(-2.5F, -2.2071F, -2.7929F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(164, 173)
                        .addBox(0.5F, -2.2071F, -2.7929F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -20.75F, -15.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition powerswitch = pillar6.addOrReplaceChild(
                "powerswitch",
                CubeListBuilder.create()
                        .texOffs(12, 193)
                        .addBox(-2.35F, -3.4142F, -3.3248F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 193)
                        .addBox(0.325F, -3.4142F, -3.3248F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, -20.25F, -17.625F));

        PartDefinition cube_r173 = powerswitch.addOrReplaceChild(
                "cube_r173",
                CubeListBuilder.create()
                        .texOffs(188, 116)
                        .addBox(-1.5F, -1.7071F, -2.2929F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(182, 192)
                        .addBox(-1.4F, -3.7071F, -2.2929F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(110, 189)
                        .addBox(1.4F, -5.7071F, -2.2929F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 68)
                        .addBox(0.4F, -3.7071F, -2.2929F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(98, 187)
                        .addBox(0.4F, -3.7071F, -2.2929F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 119)
                        .addBox(-2.4F, -3.7071F, -2.2929F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(68, 185)
                        .addBox(-2.4F, -5.7071F, -2.2929F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 2.125F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone31 = base.addOrReplaceChild(
                "bone31", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone30 = base.addOrReplaceChild(
                "bone30", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone29 = base.addOrReplaceChild(
                "bone29", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone28 = base.addOrReplaceChild(
                "bone28", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone27 = base.addOrReplaceChild(
                "bone27", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone26 =
                base.addOrReplaceChild("bone26", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition consolepiece12 =
                base.addOrReplaceChild("consolepiece12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition consolepieceside24 = consolepiece12.addOrReplaceChild(
                "consolepieceside24",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside23 = consolepiece12.addOrReplaceChild(
                "consolepieceside23",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece11 = base.addOrReplaceChild(
                "consolepiece11",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepieceside22 = consolepiece11.addOrReplaceChild(
                "consolepieceside22",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside21 = consolepiece11.addOrReplaceChild(
                "consolepieceside21",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece10 = base.addOrReplaceChild(
                "consolepiece10",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition consolepieceside20 = consolepiece10.addOrReplaceChild(
                "consolepieceside20",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside19 = consolepiece10.addOrReplaceChild(
                "consolepieceside19",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece9 = base.addOrReplaceChild(
                "consolepiece9",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition consolepieceside18 = consolepiece9.addOrReplaceChild(
                "consolepieceside18",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside17 = consolepiece9.addOrReplaceChild(
                "consolepieceside17",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece8 = base.addOrReplaceChild(
                "consolepiece8",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition consolepieceside16 = consolepiece8.addOrReplaceChild(
                "consolepieceside16",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside15 = consolepiece8.addOrReplaceChild(
                "consolepieceside15",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece7 = base.addOrReplaceChild(
                "consolepiece7",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside14 = consolepiece7.addOrReplaceChild(
                "consolepieceside14",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside13 = consolepiece7.addOrReplaceChild(
                "consolepieceside13",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition controls2 =
                base.addOrReplaceChild("controls2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panel12 =
                controls2.addOrReplaceChild("panel12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r174 = panel12.addOrReplaceChild(
                "cube_r174",
                CubeListBuilder.create()
                        .texOffs(24, 193)
                        .addBox(0.0F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 193)
                        .addBox(-0.05F, 0.25F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -20.5784F, -12.3787F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panel11 = controls2.addOrReplaceChild(
                "panel11",
                CubeListBuilder.create()
                        .texOffs(28, 193)
                        .addBox(2.45F, -27.6926F, -10.7218F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 193)
                        .addBox(2.5F, -27.7426F, -11.2218F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 193)
                        .addBox(2.45F, -25.6926F, -10.7218F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 193)
                        .addBox(2.5F, -25.7426F, -11.2218F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone25 = panel11.addOrReplaceChild(
                "bone25",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.0F, -20.0F, -12.0F, -1.0409F, 0.7119F, 0.3655F));

        PartDefinition bone24 = panel11.addOrReplaceChild(
                "bone24",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.5F, -20.0F, -12.0F, -1.0409F, 0.7119F, 0.3655F));

        PartDefinition panel10 = controls2.addOrReplaceChild(
                "panel10",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition panel9 =
                controls2.addOrReplaceChild("panel9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone23 = panel9.addOrReplaceChild(
                "bone23",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.0F, -18.0F, 14.0F, -0.9355F, 0.4981F, -0.5749F));

        PartDefinition helmic2 = panel9.addOrReplaceChild(
                "helmic2",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(3.0F, -20.1328F, 14.75F, -0.3927F, 0.0F, 0.0F));

        PartDefinition panel8 = controls2.addOrReplaceChild(
                "panel8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone22 = panel8.addOrReplaceChild(
                "bone22",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-2.7679F, -23.0F, -10.4019F, 0.0F, 0.0F, 0.7854F));

        PartDefinition panel7 = controls2.addOrReplaceChild(
                "panel7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r175 = panel7.addOrReplaceChild(
                "cube_r175",
                CubeListBuilder.create()
                        .texOffs(72, 193)
                        .addBox(-0.5F, -1.4239F, 1.1327F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                        .texOffs(170, 190)
                        .addBox(-0.5F, -2.4239F, 1.1327F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(68, 193)
                        .addBox(1.5F, -1.4239F, 1.1327F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                        .texOffs(166, 190)
                        .addBox(1.5F, -2.4239F, 1.1327F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(162, 190)
                        .addBox(1.5F, -2.4239F, -0.8673F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(158, 190)
                        .addBox(-0.5F, -2.4239F, -0.8673F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, -19.8284F, -12.5858F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r176 = panel7.addOrReplaceChild(
                "cube_r176",
                CubeListBuilder.create()
                        .texOffs(48, 193)
                        .addBox(1.425F, -1.4239F, -0.9423F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                        .texOffs(44, 193)
                        .addBox(-0.575F, -1.4239F, -0.9423F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(-3.925F, -19.8284F, -12.4858F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone21 = panel7.addOrReplaceChild(
                "bone21",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(1.5F, -19.6465F, -12.6967F, 0.3927F, 0.0F, 0.0F));

        PartDefinition pillar12 = controls2.addOrReplaceChild(
                "pillar12",
                CubeListBuilder.create()
                        .texOffs(86, 189)
                        .addBox(-0.5F, -27.0F, -13.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(76, 193)
                        .addBox(-0.55F, -26.975F, -12.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition pillar11 =
                controls2.addOrReplaceChild("pillar11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pillar10 = controls2.addOrReplaceChild(
                "pillar10",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition bone20 = pillar10.addOrReplaceChild(
                "bone20",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -20.7399F, -17.3488F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone19 = pillar10.addOrReplaceChild(
                "bone19",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -22.0F, -13.0F, 0.2849F, -0.274F, 0.7459F));

        PartDefinition pillar9 = controls2.addOrReplaceChild(
                "pillar9",
                CubeListBuilder.create()
                        .texOffs(92, 189)
                        .addBox(0.0F, -28.0F, 29.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(80, 193)
                        .addBox(0.0F, -28.0F, 29.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                        .texOffs(104, 189)
                        .addBox(0.0F, -26.5F, 29.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(96, 193)
                        .addBox(0.0F, -26.5F, 29.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                        .texOffs(130, 193)
                        .addBox(0.0F, -25.0F, 29.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                        .texOffs(116, 189)
                        .addBox(0.0F, -25.0F, 29.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(134, 193)
                        .addBox(-0.05F, -15.95F, 20.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(122, 189)
                        .addBox(0.0F, -16.0F, 20.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-21.0F, 0.0F, 37.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition bone15 = pillar9.addOrReplaceChild(
                "bone15",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -20.7071F, 26.9498F, 0.3927F, 0.0F, 0.0F));

        PartDefinition pillar8 = controls2.addOrReplaceChild(
                "pillar8",
                CubeListBuilder.create()
                        .texOffs(188, 180)
                        .addBox(-13.5F, -25.25F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(138, 193)
                        .addBox(-12.75F, -25.2F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(152, 193)
                        .addBox(-21.5F, -15.2F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(138, 189)
                        .addBox(-22.0F, -15.25F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pillar7 = controls2.addOrReplaceChild(
                "pillar7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r177 = pillar7.addOrReplaceChild(
                "cube_r177",
                CubeListBuilder.create()
                        .texOffs(160, 193)
                        .addBox(-0.5F, -0.9239F, 0.8827F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(156, 193)
                        .addBox(-0.55F, -0.4239F, 0.8327F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -21.4571F, -15.5F, 0.3927F, 0.0F, 0.0F));

        PartDefinition bone46 =
                base.addOrReplaceChild("bone46", CubeListBuilder.create(), PartPose.offset(-8.0F, -8.0F, 8.0F));

        PartDefinition group8 =
                base.addOrReplaceChild("group8", CubeListBuilder.create(), PartPose.offset(-8.0F, -8.0F, 8.0F));

        PartDefinition group7 =
                base.addOrReplaceChild("group7", CubeListBuilder.create(), PartPose.offset(-8.0F, -8.0F, 8.0F));

        PartDefinition group6 =
                base.addOrReplaceChild("group6", CubeListBuilder.create(), PartPose.offset(-8.0F, -8.0F, 8.0F));

        PartDefinition group5 =
                base.addOrReplaceChild("group5", CubeListBuilder.create(), PartPose.offset(-8.0F, -8.0F, 8.0F));

        PartDefinition bone45 = base.addOrReplaceChild(
                "bone45", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone44 = base.addOrReplaceChild(
                "bone44", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone43 = base.addOrReplaceChild(
                "bone43", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone42 = base.addOrReplaceChild(
                "bone42", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone41 = base.addOrReplaceChild(
                "bone41", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone40 =
                base.addOrReplaceChild("bone40", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition consolepiece18 =
                base.addOrReplaceChild("consolepiece18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition consolepieceside36 = consolepiece18.addOrReplaceChild(
                "consolepieceside36",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside35 = consolepiece18.addOrReplaceChild(
                "consolepieceside35",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece17 = base.addOrReplaceChild(
                "consolepiece17",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepieceside34 = consolepiece17.addOrReplaceChild(
                "consolepieceside34",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside33 = consolepiece17.addOrReplaceChild(
                "consolepieceside33",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece16 = base.addOrReplaceChild(
                "consolepiece16",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition consolepieceside32 = consolepiece16.addOrReplaceChild(
                "consolepieceside32",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside31 = consolepiece16.addOrReplaceChild(
                "consolepieceside31",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece15 = base.addOrReplaceChild(
                "consolepiece15",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition consolepieceside30 = consolepiece15.addOrReplaceChild(
                "consolepieceside30",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside29 = consolepiece15.addOrReplaceChild(
                "consolepieceside29",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece14 = base.addOrReplaceChild(
                "consolepiece14",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition consolepieceside28 = consolepiece14.addOrReplaceChild(
                "consolepieceside28",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside27 = consolepiece14.addOrReplaceChild(
                "consolepieceside27",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition consolepiece13 = base.addOrReplaceChild(
                "consolepiece13",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside26 = consolepiece13.addOrReplaceChild(
                "consolepieceside26",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition consolepieceside25 = consolepiece13.addOrReplaceChild(
                "consolepieceside25",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        return LayerDefinition.create(meshdefinition, 208, 208);
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
    public @NotNull ModelPart root() {
        return this.base;
    }

    @Override
    public void renderToBuffer(
            @NotNull PoseStack poseStack,
            @NotNull VertexConsumer vertexConsumer,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void SetupAnimations(T tile, float ageInTicks) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        //		this.animate(tile.ControlAnimationMap.get(0).rotorAnimationState, NESSConsoleModelAnimation.handbrakeon,
        // ageInTicks);
    }
}
