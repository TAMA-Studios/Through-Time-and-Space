package com.code.tama.mtm.Client.Models;// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.code.tama.mtm.Client.Animations.HudolinConsoleAnimation;
import com.code.tama.mtm.TileEntities.ConsoleTile;
import com.code.tama.mtm.misc.Interfaces.IConsoleModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class HudolinConsole<T extends ConsoleTile> extends HierarchicalModel<Entity> implements IConsoleModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "hudolinconsole"), "main");
    private final ModelPart root;
    private final ModelPart console_plinth_one;
    private final ModelPart console_plinth_two;
    private final ModelPart console_plinth_two2;
    private final ModelPart bone11;
    private final ModelPart bone12;
    private final ModelPart bone13;
    private final ModelPart bone14;
    private final ModelPart bone15;
    private final ModelPart bone16;
    private final ModelPart console_plinth_three;
    private final ModelPart clawlegs;
    private final ModelPart clawlegs2;
    private final ModelPart clawlegs3;
    private final ModelPart under_console_struts;
    private final ModelPart console_corners;
    private final ModelPart console_collar_one;
    private final ModelPart console_collar_two;
    private final ModelPart bone17;
    private final ModelPart bone18;
    private final ModelPart bone19;
    private final ModelPart bone20;
    private final ModelPart bone21;
    private final ModelPart bone22;
    private final ModelPart panel;
    private final ModelPart panel2;
    private final ModelPart panel3;
    private final ModelPart panel4;
    private final ModelPart panel5;
    private final ModelPart panel6;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone;
    private final ModelPart base;
    private final ModelPart bottom_rotor;
    private final ModelPart bottom_rotor_ring;
    private final ModelPart bone29;
    private final ModelPart bone30;
    private final ModelPart bone31;
    private final ModelPart bone32;
    private final ModelPart bone33;
    private final ModelPart bone34;
    private final ModelPart top_rotor;
    private final ModelPart top_rotor_ring;
    private final ModelPart bone35;
    private final ModelPart bone36;
    private final ModelPart bone37;
    private final ModelPart bone38;
    private final ModelPart bone39;
    private final ModelPart bone40;
    private final ModelPart upper_console_one;
    private final ModelPart bone23;
    private final ModelPart bone24;
    private final ModelPart bone25;
    private final ModelPart bone26;
    private final ModelPart bone27;
    private final ModelPart bone28;
    private final ModelPart upper_console_two;
    private final ModelPart ConsoleCollarTwo3;
    private final ModelPart ConsoleCollarOne6;
    private final ModelPart panel_two_glowies;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart dials;
    private final ModelPart crank_lever;
    private final ModelPart bone7;
    private final ModelPart buttons;
    private final ModelPart control_two;
    private final ModelPart panel_three_lights;
    private final ModelPart control_three;
    private final ModelPart control_four;
    private final ModelPart bone8;
    private final ModelPart bone9;
    private final ModelPart bone10;
    private final ModelPart useless_ass_panel;
    private final ModelPart lamp;
    private final ModelPart throttle;
    private final ModelPart handbrake;
    private final ModelPart x;
    private final ModelPart y;
    private final ModelPart z;
    private final ModelPart increment;
    private final ModelPart randomiser;
    private final ModelPart communicator;
    private final ModelPart monitor;
    private final ModelPart refuller;
    private final ModelPart door_control;
    private final ModelPart dimension_changer;
    private final ModelPart exterior_facing;
    private final ModelPart fast_return_switch;
    private final ModelPart glowies;
    private final ModelPart bone42;
    private final ModelPart bone41;
    private final ModelPart bone43;
    private final ModelPart bone44;
    private final ModelPart bone45;
    private final ModelPart bone46;
    private final ModelPart bone47;
    private final ModelPart bone48;
    private final ModelPart bone49;

    public HudolinConsole(ModelPart root) {
        this.root = root.getChild("root");
        this.console_plinth_one = this.root.getChild("console_plinth_one");
        this.console_plinth_two = this.root.getChild("console_plinth_two");
        this.console_plinth_two2 = this.console_plinth_two.getChild("console_plinth_two2");
        this.bone11 = this.console_plinth_two2.getChild("bone11");
        this.bone12 = this.console_plinth_two2.getChild("bone12");
        this.bone13 = this.console_plinth_two2.getChild("bone13");
        this.bone14 = this.console_plinth_two2.getChild("bone14");
        this.bone15 = this.console_plinth_two2.getChild("bone15");
        this.bone16 = this.console_plinth_two2.getChild("bone16");
        this.console_plinth_three = this.root.getChild("console_plinth_three");
        this.clawlegs = this.root.getChild("clawlegs");
        this.clawlegs2 = this.root.getChild("clawlegs2");
        this.clawlegs3 = this.root.getChild("clawlegs3");
        this.under_console_struts = this.root.getChild("under_console_struts");
        this.console_corners = this.root.getChild("console_corners");
        this.console_collar_one = this.root.getChild("console_collar_one");
        this.console_collar_two = this.root.getChild("console_collar_two");
        this.bone17 = this.console_collar_two.getChild("bone17");
        this.bone18 = this.console_collar_two.getChild("bone18");
        this.bone19 = this.console_collar_two.getChild("bone19");
        this.bone20 = this.console_collar_two.getChild("bone20");
        this.bone21 = this.console_collar_two.getChild("bone21");
        this.bone22 = this.console_collar_two.getChild("bone22");
        this.panel = this.root.getChild("panel");
        this.panel2 = this.root.getChild("panel2");
        this.panel3 = this.root.getChild("panel3");
        this.panel4 = this.root.getChild("panel4");
        this.panel5 = this.root.getChild("panel5");
        this.panel6 = this.root.getChild("panel6");
        this.bone2 = this.root.getChild("bone2");
        this.bone3 = this.root.getChild("bone3");
        this.bone4 = this.root.getChild("bone4");
        this.bone = this.root.getChild("bone");
        this.base = this.root.getChild("base");
        this.bottom_rotor = this.root.getChild("bottom_rotor");
        this.bottom_rotor_ring = this.bottom_rotor.getChild("bottom_rotor_ring");
        this.bone29 = this.bottom_rotor_ring.getChild("bone29");
        this.bone30 = this.bottom_rotor_ring.getChild("bone30");
        this.bone31 = this.bottom_rotor_ring.getChild("bone31");
        this.bone32 = this.bottom_rotor_ring.getChild("bone32");
        this.bone33 = this.bottom_rotor_ring.getChild("bone33");
        this.bone34 = this.bottom_rotor_ring.getChild("bone34");
        this.top_rotor = this.root.getChild("top_rotor");
        this.top_rotor_ring = this.top_rotor.getChild("top_rotor_ring");
        this.bone35 = this.top_rotor_ring.getChild("bone35");
        this.bone36 = this.top_rotor_ring.getChild("bone36");
        this.bone37 = this.top_rotor_ring.getChild("bone37");
        this.bone38 = this.top_rotor_ring.getChild("bone38");
        this.bone39 = this.top_rotor_ring.getChild("bone39");
        this.bone40 = this.top_rotor_ring.getChild("bone40");
        this.upper_console_one = this.root.getChild("upper_console_one");
        this.bone23 = this.upper_console_one.getChild("bone23");
        this.bone24 = this.upper_console_one.getChild("bone24");
        this.bone25 = this.upper_console_one.getChild("bone25");
        this.bone26 = this.upper_console_one.getChild("bone26");
        this.bone27 = this.upper_console_one.getChild("bone27");
        this.bone28 = this.upper_console_one.getChild("bone28");
        this.upper_console_two = this.root.getChild("upper_console_two");
        this.ConsoleCollarTwo3 = this.root.getChild("ConsoleCollarTwo3");
        this.ConsoleCollarOne6 = this.root.getChild("ConsoleCollarOne6");
        this.panel_two_glowies = this.root.getChild("panel_two_glowies");
        this.bone5 = this.root.getChild("bone5");
        this.bone6 = this.root.getChild("bone6");
        this.dials = this.bone6.getChild("dials");
        this.crank_lever = this.root.getChild("crank_lever");
        this.bone7 = this.root.getChild("bone7");
        this.buttons = this.root.getChild("buttons");
        this.control_two = this.root.getChild("control_two");
        this.panel_three_lights = this.root.getChild("panel_three_lights");
        this.control_three = this.root.getChild("control_three");
        this.control_four = this.root.getChild("control_four");
        this.bone8 = this.root.getChild("bone8");
        this.bone9 = this.root.getChild("bone9");
        this.bone10 = this.root.getChild("bone10");
        this.useless_ass_panel = this.root.getChild("useless_ass_panel");
        this.lamp = this.root.getChild("lamp");
        this.throttle = this.root.getChild("throttle");
        this.handbrake = this.root.getChild("handbrake");
        this.x = this.root.getChild("x");
        this.y = this.root.getChild("y");
        this.z = this.root.getChild("z");
        this.increment = this.root.getChild("increment");
        this.randomiser = this.root.getChild("randomiser");
        this.communicator = this.root.getChild("communicator");
        this.monitor = this.root.getChild("monitor");
        this.refuller = this.root.getChild("refuller");
        this.door_control = this.root.getChild("door_control");
        this.dimension_changer = this.root.getChild("dimension_changer");
        this.exterior_facing = this.root.getChild("exterior_facing");
        this.fast_return_switch = this.root.getChild("fast_return_switch");
        this.glowies = this.root.getChild("glowies");
        this.bone42 = this.root.getChild("bone42");
        this.bone41 = this.root.getChild("bone41");
        this.bone43 = this.root.getChild("bone43");
        this.bone44 = this.root.getChild("bone44");
        this.bone45 = this.root.getChild("bone45");
        this.bone46 = this.root.getChild("bone46");
        this.bone47 = this.root.getChild("bone47");
        this.bone48 = this.root.getChild("bone48");
        this.bone49 = this.root.getChild("bone49");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 139).addBox(-2.25F, -8.0067F, 14.5478F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.95F, -21.45F, -0.8F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition cube_r2 = root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(86, 41).addBox(-3.35F, 1.3F, -2.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.3022F, -19.5876F, -6.1706F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition panel_three_small_lights_r1 = root.addOrReplaceChild("panel_three_small_lights_r1", CubeListBuilder.create().texOffs(128, 70).addBox(-5.625F, -7.6067F, 11.8478F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -20.7F, -0.8F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition cube_r3 = root.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(68, 40).addBox(-3.3F, -8.1567F, 13.3478F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -20.7F, -0.8F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition console_plinth_one = root.addOrReplaceChild("console_plinth_one", CubeListBuilder.create(), PartPose.offset(0.0F, -39.8F, 0.0F));

        PartDefinition corner_one_r1 = console_plinth_one.addOrReplaceChild("corner_one_r1", CubeListBuilder.create().texOffs(138, 82).addBox(-0.093F, -1.817F, 9.9186F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0799F, 40.624F, 0.1534F, 0.0F, -0.576F, 0.0F));

        PartDefinition corner_one_r2 = console_plinth_one.addOrReplaceChild("corner_one_r2", CubeListBuilder.create().texOffs(138, 84).addBox(-0.343F, -1.817F, -11.2564F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0799F, 40.624F, 0.1534F, 0.0F, -0.4887F, 0.0F));

        PartDefinition corner_one_r3 = console_plinth_one.addOrReplaceChild("corner_one_r3", CubeListBuilder.create().texOffs(138, 78).addBox(-0.093F, -1.817F, 9.9186F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0799F, 40.624F, 0.1534F, 0.0F, 1.5272F, 0.0F));

        PartDefinition corner_one_r4 = console_plinth_one.addOrReplaceChild("corner_one_r4", CubeListBuilder.create().texOffs(138, 80).addBox(-0.043F, -1.817F, -11.2564F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0799F, 40.624F, 0.1534F, -3.1416F, 1.5272F, 3.1416F));

        PartDefinition corner_one_r5 = console_plinth_one.addOrReplaceChild("corner_one_r5", CubeListBuilder.create().texOffs(138, 76).addBox(-0.093F, -1.817F, 9.9186F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0799F, 40.624F, 0.1534F, 0.0F, 0.48F, 0.0F));

        PartDefinition corner_one_r6 = console_plinth_one.addOrReplaceChild("corner_one_r6", CubeListBuilder.create().texOffs(138, 74).addBox(-0.043F, -1.817F, -11.2564F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0799F, 40.624F, 0.1534F, 0.0F, 0.5672F, 0.0F));

        PartDefinition cube_r4 = console_plinth_one.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 64).addBox(-4.9363F, -3.279F, -10.0349F, 10.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.064F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r5 = console_plinth_one.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(60, 43).addBox(-4.9363F, -3.279F, -10.0349F, 10.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.064F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r6 = console_plinth_one.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 43).addBox(-4.9363F, -3.279F, -10.0349F, 10.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.064F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition console_plinth_two = root.addOrReplaceChild("console_plinth_two", CubeListBuilder.create(), PartPose.offset(0.0F, -24.7F, 0.0F));

        PartDefinition corner_one_r7 = console_plinth_two.addOrReplaceChild("corner_one_r7", CubeListBuilder.create().texOffs(86, 135).addBox(-0.45F, -1.0F, -9.0121F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0626F, 22.575F, 0.0698F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r8 = console_plinth_two.addOrReplaceChild("corner_one_r8", CubeListBuilder.create().texOffs(72, 135).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-8.3255F, 23.075F, 0.0198F, 0.0F, 1.5708F, 0.0F));

        PartDefinition corner_one_r9 = console_plinth_two.addOrReplaceChild("corner_one_r9", CubeListBuilder.create().texOffs(68, 135).addBox(-0.55F, -1.0F, -8.7621F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0874F, 22.575F, 0.0698F, 0.0F, -1.5708F, 0.0F));

        PartDefinition corner_one_r10 = console_plinth_two.addOrReplaceChild("corner_one_r10", CubeListBuilder.create().texOffs(134, 66).addBox(-0.45F, -1.0F, -8.9871F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0624F, 22.575F, 0.1198F, 0.0F, 0.5236F, 0.0F));

        PartDefinition corner_one_r11 = console_plinth_two.addOrReplaceChild("corner_one_r11", CubeListBuilder.create().texOffs(28, 136).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(4.17F, 23.075F, 7.2307F, 0.0F, -2.618F, 0.0F));

        PartDefinition corner_one_r12 = console_plinth_two.addOrReplaceChild("corner_one_r12", CubeListBuilder.create().texOffs(94, 136).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-4.1862F, 23.075F, 7.3157F, 0.0F, 2.618F, 0.0F));

        PartDefinition cube_r7 = console_plinth_two.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(68, 17).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 23.7F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r8 = console_plinth_two.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(68, 17).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 23.7F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r9 = console_plinth_two.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(68, 17).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 23.7F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition console_plinth_two2 = console_plinth_two.addOrReplaceChild("console_plinth_two2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition corner_one_r13 = console_plinth_two2.addOrReplaceChild("corner_one_r13", CubeListBuilder.create().texOffs(12, 136).addBox(-1.19F, -1.21F, -7.7259F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0563F, 20.3175F, 0.0628F, 0.0F, -0.6109F, 0.0F));

        PartDefinition corner_one_r14 = console_plinth_two2.addOrReplaceChild("corner_one_r14", CubeListBuilder.create().texOffs(136, 18).addBox(-0.56F, -1.66F, -0.44F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-7.493F, 20.7675F, 0.0178F, 0.0F, 1.5708F, 0.0F));

        PartDefinition corner_one_r15 = console_plinth_two2.addOrReplaceChild("corner_one_r15", CubeListBuilder.create().texOffs(128, 135).addBox(0.42F, -1.21F, -7.4759F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0787F, 20.3175F, 0.0628F, 0.0F, -1.4399F, 0.0F));

        PartDefinition corner_one_r16 = console_plinth_two2.addOrReplaceChild("corner_one_r16", CubeListBuilder.create().texOffs(124, 135).addBox(-1.265F, -1.21F, -7.8034F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0562F, 20.3175F, 0.1078F, 0.0F, 0.4363F, 0.0F));

        PartDefinition corner_one_r17 = console_plinth_two2.addOrReplaceChild("corner_one_r17", CubeListBuilder.create().texOffs(8, 136).addBox(-0.46F, -1.66F, -0.29F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.753F, 20.7675F, 6.5076F, 0.0F, -2.618F, 0.0F));

        PartDefinition corner_one_r18 = console_plinth_two2.addOrReplaceChild("corner_one_r18", CubeListBuilder.create().texOffs(90, 135).addBox(-0.46F, -1.66F, -0.34F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.7676F, 20.7675F, 6.5842F, 0.0F, 2.6616F, 0.0F));

        PartDefinition cube_r10 = console_plinth_two2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(74, 0).addBox(-3.5396F, -2.2962F, -7.1964F, 7.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.33F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r11 = console_plinth_two2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(74, 0).addBox(-3.4396F, -2.2962F, -6.9214F, 7.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.33F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r12 = console_plinth_two2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(74, 0).addBox(-3.4396F, -2.2962F, -7.0964F, 7.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.33F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bone11 = console_plinth_two2.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(80, 139).addBox(-2.7F, -2.0F, 6.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.6F, -2.0F, 6.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.5F, -2.0F, 6.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 21.675F, 0.0F));

        PartDefinition bone12 = console_plinth_two2.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(80, 139).addBox(-2.7F, -2.0F, 6.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.6F, -2.0F, 6.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.5F, -2.0F, 6.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 21.675F, -0.1F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone13 = console_plinth_two2.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(80, 139).addBox(-2.425F, -2.0F, 6.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.325F, -2.0F, 6.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.775F, -2.0F, 6.15F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 21.675F, -0.1F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone14 = console_plinth_two2.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(80, 139).addBox(1.675F, -2.0F, 6.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-2.425F, -2.0F, 6.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 21.675F, 0.3F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone15 = console_plinth_two2.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(80, 139).addBox(1.675F, -2.0F, 6.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-2.425F, -2.0F, 6.05F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1F, 21.675F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone16 = console_plinth_two2.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(80, 139).addBox(1.675F, -2.0F, 6.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-2.425F, -2.0F, 6.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1F, 21.675F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition console_plinth_three = root.addOrReplaceChild("console_plinth_three", CubeListBuilder.create(), PartPose.offset(0.0F, -39.6F, 0.0F));

        PartDefinition corner_one_r19 = console_plinth_three.addOrReplaceChild("corner_one_r19", CubeListBuilder.create().texOffs(56, 135).addBox(-0.45F, -1.0F, -9.0121F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(136, 24).addBox(-0.4482F, -1.0F, 7.837F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0626F, 25.575F, 0.0698F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r20 = console_plinth_three.addOrReplaceChild("corner_one_r20", CubeListBuilder.create().texOffs(52, 135).addBox(-0.55F, -1.0F, 7.9129F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(134, 127).addBox(-0.55F, -1.0F, -8.7621F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0874F, 25.575F, 0.0698F, 0.0F, -1.5708F, 0.0F));

        PartDefinition corner_one_r21 = console_plinth_three.addOrReplaceChild("corner_one_r21", CubeListBuilder.create().texOffs(68, 120).addBox(-0.45F, -1.0F, -8.9871F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(24, 136).addBox(-0.4982F, -1.0F, 7.712F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0624F, 25.575F, 0.1198F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r13 = console_plinth_three.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 85).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 26.7F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r14 = console_plinth_three.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 85).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 26.7F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r15 = console_plinth_three.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 85).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 26.7F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition clawlegs = root.addOrReplaceChild("clawlegs", CubeListBuilder.create(), PartPose.offset(-6.2419F, 4.3721F, 3.8953F));

        PartDefinition cube_r16 = clawlegs.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(60, 132).addBox(5.882F, -6.86F, -0.886F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(128, 78).addBox(2.902F, -6.92F, -0.876F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 3.1416F, -0.5236F, 3.1416F));

        PartDefinition cube_r17 = clawlegs.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(38, 124).addBox(-0.5F, -1.0F, -0.95F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5853F, -7.1123F, 0.2412F, -2.8449F, -0.4363F, 2.5133F));

        PartDefinition cube_r18 = clawlegs.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(124, 18).addBox(-1.175F, -1.2F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2901F, -8.5239F, -0.3154F, -2.618F, 0.0F, 1.5708F));

        PartDefinition clawlegs2 = root.addOrReplaceChild("clawlegs2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0919F, 4.3721F, -7.1797F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r19 = clawlegs2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(60, 132).addBox(5.882F, -6.36F, -0.886F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(128, 82).addBox(2.902F, -6.42F, -0.876F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, -0.5236F, 3.1416F));

        PartDefinition cube_r20 = clawlegs2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(124, 123).addBox(-0.2336F, -0.5769F, -0.9569F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5853F, -7.6123F, 0.2412F, -2.8449F, -0.4363F, 2.5133F));

        PartDefinition cube_r21 = clawlegs2.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(124, 23).addBox(-0.55F, -1.2026F, -0.9984F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2901F, -9.0239F, -0.3154F, -2.618F, 0.0F, 1.5708F));

        PartDefinition clawlegs3 = root.addOrReplaceChild("clawlegs3", CubeListBuilder.create(), PartPose.offsetAndRotation(6.4081F, 4.3721F, 3.8203F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r22 = clawlegs3.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(60, 132).addBox(5.882F, -6.36F, -0.886F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(128, 74).addBox(2.902F, -6.42F, -0.876F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, -0.5236F, 3.1416F));

        PartDefinition cube_r23 = clawlegs3.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(124, 32).addBox(-0.2336F, -0.5769F, -0.9569F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5853F, -7.6123F, 0.2412F, -2.8449F, -0.4363F, 2.5133F));

        PartDefinition cube_r24 = clawlegs3.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(100, 123).addBox(-0.65F, -1.2026F, -0.9984F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2901F, -9.0239F, -0.3154F, -2.618F, 0.0F, 1.5708F));

        PartDefinition under_console_struts = root.addOrReplaceChild("under_console_struts", CubeListBuilder.create(), PartPose.offset(-0.075F, -0.3F, 0.0F));

        PartDefinition cube_r25 = under_console_struts.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(88, 116).addBox(-1.137F, -21.08F, -2.62F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.6981F, 2.0944F, 0.0F));

        PartDefinition cube_r26 = under_console_struts.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(72, 116).addBox(-1.037F, -20.98F, -2.62F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.6981F, -2.0944F, 0.0F));

        PartDefinition cube_r27 = under_console_struts.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(116, 18).addBox(-1.037F, -21.03F, -2.62F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition console_corners = root.addOrReplaceChild("console_corners", CubeListBuilder.create(), PartPose.offset(0.0F, 5.018F, -10.66F));

        PartDefinition corner_r1 = console_corners.addOrReplaceChild("corner_r1", CubeListBuilder.create().texOffs(112, 123).addBox(-24.5574F, -1.0F, -2.4577F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, -19.218F, 10.66F, 0.0F, -1.0079F, 0.0F));

        PartDefinition corner_r2 = console_corners.addOrReplaceChild("corner_r2", CubeListBuilder.create().texOffs(0, 124).addBox(-24.5574F, -1.0F, -2.4577F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, -19.218F, 10.66F, -3.1416F, 1.0036F, 3.1416F));

        PartDefinition corner_r3 = console_corners.addOrReplaceChild("corner_r3", CubeListBuilder.create().texOffs(12, 124).addBox(-24.5574F, -1.0F, -2.4577F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, -19.218F, 10.66F, -3.1416F, -1.0865F, 3.1416F));

        PartDefinition corner_r4 = console_corners.addOrReplaceChild("corner_r4", CubeListBuilder.create().texOffs(112, 123).addBox(-24.5574F, -1.0F, -2.4577F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, -19.218F, 10.66F, 0.0F, 1.0908F, 0.0F));

        PartDefinition corner_r5 = console_corners.addOrReplaceChild("corner_r5", CubeListBuilder.create().texOffs(112, 123).addBox(-1.475F, -1.0F, -1.55F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.0F, -19.218F, 10.56F, -3.1416F, 0.0428F, 3.1416F));

        PartDefinition corner_r6 = console_corners.addOrReplaceChild("corner_r6", CubeListBuilder.create().texOffs(112, 123).addBox(-1.475F, -1.0F, -1.55F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.2F, -19.218F, 10.76F, 0.0F, 0.0436F, 0.0F));

        PartDefinition console_collar_one = root.addOrReplaceChild("console_collar_one", CubeListBuilder.create(), PartPose.offset(0.0F, -27.5F, 0.0F));

        PartDefinition corner_one_r22 = console_collar_one.addOrReplaceChild("corner_one_r22", CubeListBuilder.create().texOffs(98, 137).addBox(-0.45F, -1.0F, -9.0121F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(138, 3).addBox(-0.4482F, -1.0F, 7.837F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0626F, 4.575F, 0.0698F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r23 = console_collar_one.addOrReplaceChild("corner_one_r23", CubeListBuilder.create().texOffs(106, 137).addBox(-0.55F, -1.0F, 7.9129F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(102, 137).addBox(-0.55F, -1.0F, -8.7621F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0874F, 4.575F, 0.0698F, 0.0F, -1.5708F, 0.0F));

        PartDefinition corner_one_r24 = console_collar_one.addOrReplaceChild("corner_one_r24", CubeListBuilder.create().texOffs(36, 137).addBox(-0.45F, -1.0F, -8.9871F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(138, 0).addBox(-0.4982F, -1.0F, 7.712F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0624F, 4.575F, 0.1198F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r28 = console_collar_one.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(48, 87).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.7F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r29 = console_collar_one.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(48, 87).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.7F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r30 = console_collar_one.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(48, 87).addBox(-4.044F, -2.218F, -7.996F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.7F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition console_collar_two = root.addOrReplaceChild("console_collar_two", CubeListBuilder.create(), PartPose.offset(0.095F, -2.9F, -0.206F));

        PartDefinition corner_one_r25 = console_collar_two.addOrReplaceChild("corner_one_r25", CubeListBuilder.create().texOffs(136, 109).addBox(-0.5232F, -2.65F, 5.687F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(136, 32).addBox(-0.5232F, -2.65F, -6.788F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -20.925F, 0.2758F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r26 = console_collar_two.addOrReplaceChild("corner_one_r26", CubeListBuilder.create().texOffs(136, 40).addBox(-0.4482F, -2.65F, 5.687F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(136, 44).addBox(-0.4482F, -2.65F, -6.763F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -20.925F, 0.2758F, 0.0F, 0.5236F, 0.0F));

        PartDefinition corner_one_r27 = console_collar_two.addOrReplaceChild("corner_one_r27", CubeListBuilder.create().texOffs(60, 136).addBox(-0.5482F, -2.65F, 5.687F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 136).addBox(-0.5732F, -2.65F, -6.738F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -20.925F, 0.2758F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r31 = console_collar_two.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(36, 105).addBox(-2.96F, -4.418F, -6.282F, 6.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -19.14F, 0.338F, -3.1416F, -2.0944F, 3.1416F));

        PartDefinition cube_r32 = console_collar_two.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(36, 105).addBox(-3.26F, -4.418F, -6.182F, 6.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -19.14F, 0.338F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r33 = console_collar_two.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(36, 105).addBox(-3.26F, -4.418F, -5.882F, 6.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -19.14F, 0.338F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bone17 = console_collar_two.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(80, 139).addBox(-2.65F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.095F, -20.825F, 0.206F));

        PartDefinition bone18 = console_collar_two.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(80, 139).addBox(-2.6F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.5F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.6F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.095F, -20.825F, 0.206F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone19 = console_collar_two.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(80, 139).addBox(-2.65F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.095F, -20.825F, 0.206F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone20 = console_collar_two.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(80, 139).addBox(-2.65F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.195F, -20.825F, 0.256F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone21 = console_collar_two.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(80, 139).addBox(-2.65F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.195F, -20.825F, 0.256F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone22 = console_collar_two.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(80, 139).addBox(-2.65F, -2.0F, 5.225F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.55F, -2.0F, 5.225F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.55F, -2.0F, 5.225F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.195F, -20.825F, 0.256F, 0.0F, -2.0944F, 0.0F));

        PartDefinition panel = root.addOrReplaceChild("panel", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0029F, 5.2697F, 6.9223F, 22.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0371F, -20.4717F, 0.0187F));

        PartDefinition cube_r34 = panel.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 17).addBox(-11.5F, -8.1965F, 2.9073F, 21.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0371F, 0.5717F, -0.8187F, -0.7418F, 0.0F, 0.0F));

        PartDefinition panel2 = root.addOrReplaceChild("panel2", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0029F, 5.2697F, 6.9223F, 22.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0371F, -20.4717F, 0.0187F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r35 = panel2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 30).addBox(-11.3F, -6.1321F, 4.799F, 21.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0371F, -2.2283F, -0.8187F, -0.7418F, 0.0F, 0.0F));

        PartDefinition panel3 = root.addOrReplaceChild("panel3", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0029F, 5.2697F, 6.9223F, 22.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0371F, -20.4717F, 0.0187F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r36 = panel3.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(0, 30).addBox(-11.5F, -6.1321F, 4.799F, 21.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0371F, -2.2283F, -0.8187F, -0.7418F, 0.0F, 0.0F));

        PartDefinition panel4 = root.addOrReplaceChild("panel4", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0029F, 5.2697F, 6.9223F, 22.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0371F, -20.4717F, 0.0187F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r37 = panel4.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(0, 30).addBox(-11.5F, -6.1321F, 4.799F, 21.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0371F, -2.2283F, -0.8187F, -0.7418F, 0.0F, 0.0F));

        PartDefinition panel5 = root.addOrReplaceChild("panel5", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0029F, 5.2697F, 6.9223F, 22.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0371F, -20.4717F, 0.0187F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r38 = panel5.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(0, 30).addBox(-11.5F, -6.1321F, 4.799F, 21.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0371F, -2.2283F, -0.8187F, -0.7418F, 0.0F, 0.0F));

        PartDefinition panel6 = root.addOrReplaceChild("panel6", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0029F, 5.2697F, 6.9223F, 22.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0371F, -20.4717F, 0.0187F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r39 = panel6.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(0, 30).addBox(-11.5F, -6.1321F, 4.799F, 21.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0371F, -2.2283F, -0.8187F, -0.7418F, 0.0F, 0.0F));

        PartDefinition bone2 = root.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(9.9198F, -20.8799F, 5.6383F, 0.0F, 2.0944F, 0.0F));

        PartDefinition corner_r7 = bone2.addOrReplaceChild("corner_r7", CubeListBuilder.create().texOffs(80, 116).addBox(0.7713F, -5.2958F, -1.0849F, 3.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5917F, 0.0608F, 6.0529F, 0.9505F, 0.5656F, 1.2216F));

        PartDefinition corner_r8 = bone2.addOrReplaceChild("corner_r8", CubeListBuilder.create().texOffs(80, 116).addBox(-11.0181F, 5.2816F, -0.0165F, 3.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.2802F, 2.7799F, -6.2383F, 0.0F, 0.0F, 0.9163F));

        PartDefinition bone3 = root.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-6.3884F, -20.7191F, 11.8912F));

        PartDefinition corner_r9 = bone3.addOrReplaceChild("corner_r9", CubeListBuilder.create().texOffs(80, 116).addBox(-1.45F, -7.0F, -0.9F, 3.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8F, 0.0F, 0.9505F, 0.5656F, 1.2216F));

        PartDefinition corner_r10 = bone3.addOrReplaceChild("corner_r10", CubeListBuilder.create().texOffs(80, 116).addBox(-13.2395F, 3.577F, -0.0915F, 3.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.6884F, 5.5191F, -12.2912F, 0.0F, 0.0F, 0.9163F));

        PartDefinition bone4 = root.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.9884F, -20.7191F, -11.4088F, 0.0F, -2.0944F, 0.0F));

        PartDefinition corner_r11 = bone4.addOrReplaceChild("corner_r11", CubeListBuilder.create().texOffs(80, 116).addBox(0.7713F, -5.2958F, -0.8599F, 3.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9505F, 0.5656F, 1.2216F));

        PartDefinition corner_r12 = bone4.addOrReplaceChild("corner_r12", CubeListBuilder.create().texOffs(80, 116).addBox(-11.0181F, 5.2816F, -0.0915F, 3.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.6884F, 2.7191F, -12.2912F, 0.0F, 0.0F, 0.9163F));

        PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(118, 118).addBox(-13.7738F, 5.9336F, 4.6663F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(120, 59).addBox(-13.2808F, 5.2736F, 5.1563F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(108, 84).addBox(-13.2808F, 5.0736F, 6.1563F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.6188F, -21.6496F, 12.4767F));

        PartDefinition cube_r40 = bone.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(118, 113).addBox(-2.4855F, -0.41F, -1.9865F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4083F, 6.6436F, 5.6878F, -0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r41 = bone.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(124, 131).addBox(-3.64F, -0.6F, -2.62F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(86, 131).addBox(2.36F, -0.6F, -2.62F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(70, 131).addBox(-0.64F, -0.825F, -2.62F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 120).addBox(-2.74F, -0.6F, -0.02F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(120, 41).addBox(-2.74F, -0.6F, 2.7F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(132, 105).addBox(3.72F, -0.525F, 2.678F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(68, 36).addBox(-7.14F, -0.6F, 5.42F, 15.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.9538F, 1.3496F, -2.3367F, -0.7418F, 0.0F, 0.0F));

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(104, 113).addBox(-3.195F, -9.018F, 5.409F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.172F, -3.18F, -0.065F));

        PartDefinition cube_r42 = base.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(116, 0).addBox(-3.0F, -4.1F, -0.5F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0887F, -4.918F, 3.1862F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r43 = base.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(116, 0).addBox(-3.008F, -11.818F, 5.669F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.65F, 2.8F, 0.078F, 0.0F, 1.0516F, 0.0F));

        PartDefinition cube_r44 = base.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(104, 113).addBox(-3.035F, -11.818F, 5.5F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, 2.8F, 0.338F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r45 = base.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(116, 0).addBox(-3.435F, -11.818F, 5.318F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.455F, 2.8F, 0.247F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r46 = base.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(104, 113).addBox(-3.737F, -11.818F, 4.603F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.053F, 2.8F, 0.416F, -3.1416F, -1.0516F, 3.1416F));

        PartDefinition bottom_rotor = root.addOrReplaceChild("bottom_rotor", CubeListBuilder.create().texOffs(96, 116).addBox(2.66F, -13.16F, -1.31F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 116).addBox(-1.54F, -13.16F, 2.87F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.075F, -22.0F, -0.325F));

        PartDefinition cube_r47 = bottom_rotor.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(96, 116).addBox(-0.5F, -8.0F, -1.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.68F, -5.16F, 3.37F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r48 = bottom_rotor.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(96, 116).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.02F, -5.16F, -0.81F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r49 = bottom_rotor.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(96, 116).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.02F, -5.16F, 1.29F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r50 = bottom_rotor.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(96, 116).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.16F, -5.16F, 1.29F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r51 = bottom_rotor.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(96, 116).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.04F, -5.16F, -2.89F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r52 = bottom_rotor.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(96, 116).addBox(-1.0F, -8.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.68F, -5.16F, -2.89F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bottom_rotor_ring = bottom_rotor.addOrReplaceChild("bottom_rotor_ring", CubeListBuilder.create(), PartPose.offset(0.17F, 18.1F, 0.119F));

        PartDefinition cube_r53 = bottom_rotor_ring.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(72, 105).addBox(-2.96F, -2.418F, -5.282F, 6.0F, 1.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.403F, -23.14F, 0.338F, -3.1416F, -2.0944F, 3.1416F));

        PartDefinition cube_r54 = bottom_rotor_ring.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(72, 105).addBox(-3.26F, -2.418F, -5.182F, 6.0F, 1.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.403F, -23.14F, 0.338F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r55 = bottom_rotor_ring.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(72, 105).addBox(-3.26F, -2.418F, -4.882F, 6.0F, 1.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.403F, -23.14F, 0.338F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bone29 = bottom_rotor_ring.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offset(-0.095F, -20.825F, 0.206F));

        PartDefinition bone30 = bottom_rotor_ring.addOrReplaceChild("bone30", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.095F, -20.825F, 0.206F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone31 = bottom_rotor_ring.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.095F, -20.825F, 0.206F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone32 = bottom_rotor_ring.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.195F, -20.825F, 0.256F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone33 = bottom_rotor_ring.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.195F, -20.825F, 0.256F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone34 = bottom_rotor_ring.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.195F, -20.825F, 0.256F, 0.0F, -2.0944F, 0.0F));

        PartDefinition top_rotor = root.addOrReplaceChild("top_rotor", CubeListBuilder.create().texOffs(96, 116).addBox(-0.8401F, -17.16F, -3.4292F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 116).addBox(-3.9254F, -17.16F, -0.2382F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 116).addBox(2.2513F, -17.16F, -0.2626F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 116).addBox(-0.834F, -17.16F, 2.8534F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.325F, -55.5F, -0.325F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r56 = top_rotor.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(96, 116).addBox(-0.375F, -10.0F, -0.625F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3112F, -7.16F, 2.48F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r57 = top_rotor.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(96, 116).addBox(-0.125F, -10.0F, -0.825F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.347F, -7.16F, 2.9981F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r58 = top_rotor.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(96, 116).addBox(-0.525F, -10.0F, -0.85F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.137F, -7.16F, -1.9058F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r59 = top_rotor.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(96, 116).addBox(-1.075F, -10.0F, -0.4F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5211F, -7.16F, -2.4239F, 0.0F, 1.5708F, 0.0F));

        PartDefinition top_rotor_ring = top_rotor.addOrReplaceChild("top_rotor_ring", CubeListBuilder.create(), PartPose.offset(-0.155F, 25.35F, 0.531F));

        PartDefinition cube_r60 = top_rotor_ring.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(72, 105).addBox(-2.96F, -2.418F, -4.718F, 6.0F, 1.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.403F, -34.14F, -0.338F, 3.1416F, 2.0944F, 3.1416F));

        PartDefinition cube_r61 = top_rotor_ring.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(72, 105).addBox(-3.26F, -2.418F, -4.818F, 6.0F, 1.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.403F, -34.14F, -0.338F, 3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r62 = top_rotor_ring.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(72, 105).addBox(-3.26F, -2.418F, -5.118F, 6.0F, 1.0F, 10.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.403F, -34.14F, -0.338F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition bone35 = top_rotor_ring.addOrReplaceChild("bone35", CubeListBuilder.create(), PartPose.offset(-0.095F, -20.825F, -0.206F));

        PartDefinition bone36 = top_rotor_ring.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.095F, -20.825F, -0.206F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone37 = top_rotor_ring.addOrReplaceChild("bone37", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.095F, -20.825F, -0.206F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone38 = top_rotor_ring.addOrReplaceChild("bone38", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.195F, -20.825F, -0.256F, 0.0F, -3.1416F, 0.0F));

        PartDefinition bone39 = top_rotor_ring.addOrReplaceChild("bone39", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.195F, -20.825F, -0.256F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone40 = top_rotor_ring.addOrReplaceChild("bone40", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.195F, -20.825F, -0.256F, 0.0F, 2.0944F, 0.0F));

        PartDefinition upper_console_one = root.addOrReplaceChild("upper_console_one", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.205F, -100.2F, -0.206F, 0.0F, 0.0F, -3.1416F));

        PartDefinition corner_one_r28 = upper_console_one.addOrReplaceChild("corner_one_r28", CubeListBuilder.create().texOffs(0, 137).addBox(-0.5232F, -4.65F, 5.687F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(136, 135).addBox(-0.5232F, -4.65F, -6.788F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -47.925F, 0.2758F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r29 = upper_console_one.addOrReplaceChild("corner_one_r29", CubeListBuilder.create().texOffs(136, 131).addBox(-0.4482F, -4.65F, 5.687F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(136, 121).addBox(-0.4482F, -4.65F, -6.763F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -47.925F, 0.2758F, 0.0F, 0.5236F, 0.0F));

        PartDefinition corner_one_r30 = upper_console_one.addOrReplaceChild("corner_one_r30", CubeListBuilder.create().texOffs(136, 117).addBox(-0.5482F, -4.65F, 5.687F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(136, 113).addBox(-0.5732F, -4.65F, -6.738F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -47.925F, 0.2758F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r63 = upper_console_one.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(36, 105).addBox(-2.96F, -6.418F, -6.282F, 6.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -46.14F, 0.338F, -3.1416F, -2.0944F, 3.1416F));

        PartDefinition cube_r64 = upper_console_one.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(36, 105).addBox(-3.26F, -6.418F, -6.182F, 6.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -46.14F, 0.338F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r65 = upper_console_one.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(36, 105).addBox(-3.26F, -6.418F, -5.882F, 6.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -46.14F, 0.338F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bone23 = upper_console_one.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(80, 139).addBox(-2.65F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.55F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.095F, -49.825F, 0.206F));

        PartDefinition bone24 = upper_console_one.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(80, 139).addBox(-2.625F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.525F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.575F, -2.0F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.095F, -49.825F, 0.206F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone25 = upper_console_one.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(80, 139).addBox(-2.625F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.525F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.575F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.095F, -49.825F, 0.206F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone26 = upper_console_one.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(80, 139).addBox(-2.625F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.525F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.575F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.095F, -49.825F, 0.206F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone27 = upper_console_one.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(80, 139).addBox(-2.625F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.525F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.575F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.095F, -49.825F, 0.206F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone28 = upper_console_one.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(80, 139).addBox(-2.6F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(-0.5F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(80, 139).addBox(1.6F, -2.0F, 5.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.095F, -49.825F, 0.206F, 0.0F, -2.0944F, 0.0F));

        PartDefinition upper_console_two = root.addOrReplaceChild("upper_console_two", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -78.5F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition corner_one_r31 = upper_console_two.addOrReplaceChild("corner_one_r31", CubeListBuilder.create().texOffs(44, 137).addBox(-0.45F, -3.0F, -9.0121F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(16, 138).addBox(-0.4482F, -3.0F, 7.837F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0626F, -25.425F, 0.0698F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r32 = upper_console_two.addOrReplaceChild("corner_one_r32", CubeListBuilder.create().texOffs(114, 137).addBox(-0.55F, -3.0F, 7.9129F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(110, 137).addBox(-0.55F, -3.0F, -8.7621F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0874F, -25.425F, 0.0698F, 0.0F, -1.5708F, 0.0F));

        PartDefinition corner_one_r33 = upper_console_two.addOrReplaceChild("corner_one_r33", CubeListBuilder.create().texOffs(32, 137).addBox(-0.45F, -3.0F, -8.9871F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(118, 137).addBox(-0.4982F, -3.0F, 7.712F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0624F, -25.425F, 0.1198F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r66 = upper_console_two.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(96, 87).addBox(-4.044F, -4.218F, -7.996F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.3F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r67 = upper_console_two.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(96, 87).addBox(-4.044F, -4.218F, -7.996F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.3F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r68 = upper_console_two.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(96, 87).addBox(-4.044F, -4.218F, -7.996F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.3F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition ConsoleCollarTwo3 = root.addOrReplaceChild("ConsoleCollarTwo3", CubeListBuilder.create(), PartPose.offset(0.095F, -2.9F, -0.206F));

        PartDefinition corner_one_r34 = ConsoleCollarTwo3.addOrReplaceChild("corner_one_r34", CubeListBuilder.create().texOffs(120, 128).addBox(-0.5232F, -7.65F, 5.687F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(112, 128).addBox(-0.5232F, -7.65F, -6.788F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -48.925F, 0.2758F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r35 = ConsoleCollarTwo3.addOrReplaceChild("corner_one_r35", CubeListBuilder.create().texOffs(108, 128).addBox(-0.4482F, -7.65F, 5.687F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 128).addBox(-0.4482F, -7.65F, -6.763F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -48.925F, 0.2758F, 0.0F, 0.5236F, 0.0F));

        PartDefinition corner_one_r36 = ConsoleCollarTwo3.addOrReplaceChild("corner_one_r36", CubeListBuilder.create().texOffs(100, 128).addBox(-0.5482F, -7.65F, 5.687F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(116, 128).addBox(-0.5732F, -7.65F, -6.738F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1576F, -48.925F, 0.2758F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r69 = ConsoleCollarTwo3.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(0, 104).addBox(-2.96F, -9.418F, -6.282F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -47.14F, 0.338F, -3.1416F, -2.0944F, 3.1416F));

        PartDefinition cube_r70 = ConsoleCollarTwo3.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(0, 104).addBox(-3.26F, -9.418F, -6.182F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -47.14F, 0.338F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r71 = ConsoleCollarTwo3.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(0, 104).addBox(-3.26F, -9.418F, -5.882F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.403F, -47.14F, 0.338F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition ConsoleCollarOne6 = root.addOrReplaceChild("ConsoleCollarOne6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -74.5F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition corner_one_r37 = ConsoleCollarOne6.addOrReplaceChild("corner_one_r37", CubeListBuilder.create().texOffs(82, 131).addBox(-0.45F, -3.0F, -9.0121F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(4, 133).addBox(-0.4482F, -3.0F, 7.837F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0626F, -16.425F, 0.0698F, 0.0F, -0.5236F, 0.0F));

        PartDefinition corner_one_r38 = ConsoleCollarOne6.addOrReplaceChild("corner_one_r38", CubeListBuilder.create().texOffs(78, 131).addBox(-0.55F, -3.0F, 7.9129F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(20, 129).addBox(-0.55F, -3.0F, -8.7621F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0874F, -16.425F, 0.0698F, 0.0F, -1.5708F, 0.0F));

        PartDefinition corner_one_r39 = ConsoleCollarOne6.addOrReplaceChild("corner_one_r39", CubeListBuilder.create().texOffs(0, 129).addBox(-0.45F, -3.0F, -8.9871F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(132, 131).addBox(-0.4982F, -3.0F, 7.712F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0624F, -16.425F, 0.1198F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r72 = ConsoleCollarOne6.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(60, 64).addBox(-4.044F, -4.218F, -7.996F, 8.0F, 7.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.3F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r73 = ConsoleCollarOne6.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(60, 64).addBox(-4.044F, -4.218F, -7.996F, 8.0F, 7.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.3F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r74 = ConsoleCollarOne6.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(60, 64).addBox(-4.044F, -4.218F, -7.996F, 8.0F, 7.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.3F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition panel_two_glowies = root.addOrReplaceChild("panel_two_glowies", CubeListBuilder.create(), PartPose.offset(1.0F, -22.7F, -0.8F));

        PartDefinition cube_r75 = panel_two_glowies.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(118, 84).addBox(-1.8F, -8.1567F, 6.2478F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(130, 8).addBox(-1.8F, -7.9817F, 11.9478F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 77).addBox(3.2F, -8.1567F, 9.6478F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(108, 70).addBox(-6.8F, -8.1567F, 9.6478F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition bone5 = root.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(1.95F, -23.45F, -0.8F));

        PartDefinition cube_r76 = bone5.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(8, 133).addBox(5.725F, -7.7067F, 13.5478F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone6 = root.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(4.5826F, -19.6656F, -13.8459F));

        PartDefinition lights_right_r1 = bone6.addOrReplaceChild("lights_right_r1", CubeListBuilder.create().texOffs(134, 58).addBox(5.8F, -2.4F, 0.1F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(116, 33).addBox(-2.4F, -2.4F, 0.1F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.2376F, 0.1919F, 1.8456F, -0.829F, 0.0F, 0.0F));

        PartDefinition dials = bone6.addOrReplaceChild("dials", CubeListBuilder.create(), PartPose.offset(-4.7026F, -3.6492F, 3.3765F));

        PartDefinition cube_r77 = dials.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(140, 16).addBox(2.2F, -2.1F, 0.4F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(140, 14).addBox(-0.8F, -2.1F, 0.4F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(140, 18).addBox(-3.8F, -2.1F, 0.4F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition crank_lever = root.addOrReplaceChild("crank_lever", CubeListBuilder.create(), PartPose.offset(4.5826F, -19.6656F, -13.8459F));

        PartDefinition base_r1 = crank_lever.addOrReplaceChild("base_r1", CubeListBuilder.create().texOffs(134, 63).addBox(-0.94F, -0.24F, -1.42F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(130, 36).addBox(-1.44F, -0.74F, -0.42F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.829F, -0.0107F, 0.0091F));

        PartDefinition bone7 = root.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.3826F, -19.6656F, -13.8459F));

        PartDefinition base_r2 = bone7.addOrReplaceChild("base_r2", CubeListBuilder.create().texOffs(54, 128).addBox(-1.84F, -4.54F, -0.02F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition switches_r1 = bone7.addOrReplaceChild("switches_r1", CubeListBuilder.create().texOffs(100, 116).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3176F, 1.6204F, 0.355F, -0.9959F, 0.4981F, -0.6353F));

        PartDefinition buttons = root.addOrReplaceChild("buttons", CubeListBuilder.create(), PartPose.offset(1.115F, -18.0429F, -17.24F));

        PartDefinition base_r3 = buttons.addOrReplaceChild("base_r3", CubeListBuilder.create().texOffs(94, 133).addBox(-11.6F, -0.96F, 0.18F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4676F, 0.3773F, 3.3941F, -0.829F, -0.0107F, 0.0091F));

        PartDefinition cube_r78 = buttons.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(24, 140).addBox(-5.5F, -2.925F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(140, 24).addBox(-6.8F, -2.925F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 140).addBox(-5.5F, -4.225F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(140, 20).addBox(-6.8F, -4.225F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition control_two = root.addOrReplaceChild("control_two", CubeListBuilder.create(), PartPose.offset(1.0F, -22.7F, -0.8F));

        PartDefinition cube_r79 = control_two.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(50, 124).addBox(-1.3F, -8.1567F, 7.8478F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition panel_three_lights = root.addOrReplaceChild("panel_three_lights", CubeListBuilder.create(), PartPose.offset(1.95F, -23.45F, -0.8F));

        PartDefinition cube_r80 = panel_three_lights.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(130, 4).addBox(-2.375F, -7.9067F, 6.5728F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 2.3998F, -1.0472F, 3.1416F));

        PartDefinition cube_r81 = panel_three_lights.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(130, 4).addBox(0.625F, -7.9067F, 6.5728F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(130, 4).addBox(3.625F, -7.9067F, 6.5728F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition control_three = root.addOrReplaceChild("control_three", CubeListBuilder.create(), PartPose.offset(1.0F, -22.7F, -0.8F));

        PartDefinition cube_r82 = control_three.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(104, 105).addBox(-3.8F, -7.6067F, 7.3478F, 10.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition control_four = root.addOrReplaceChild("control_four", CubeListBuilder.create(), PartPose.offset(1.95F, -23.45F, -0.8F));

        PartDefinition cube_r83 = control_four.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(138, 129).addBox(0.75F, -8.0067F, 14.5478F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition cube_r84 = control_four.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(104, 109).addBox(-4.275F, -7.6067F, 11.8478F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.95F, 2.75F, 0.0F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone8 = root.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(12.3022F, -21.5876F, -6.1706F));

        PartDefinition cube_r85 = bone8.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(138, 28).addBox(6.0F, 0.8F, 1.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 134).addBox(5.5F, 1.3F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition bone9 = root.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(12.3022F, -21.5876F, -6.1706F));

        PartDefinition cube_r86 = bone9.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(52, 139).addBox(-5.8F, 0.8F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 134).addBox(-6.25F, 1.3F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition bone10 = root.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(12.3022F, -21.5876F, -6.1706F));

        PartDefinition cube_r87 = bone10.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(134, 49).addBox(-2.5F, 1.225F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 38).addBox(2.75F, 0.8F, 1.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(138, 36).addBox(1.25F, 0.8F, 1.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 64).addBox(-3.375F, 1.3F, -1.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition useless_ass_panel = root.addOrReplaceChild("useless_ass_panel", CubeListBuilder.create(), PartPose.offset(1.0F, -20.7F, -0.8F));

        PartDefinition cube_r88 = useless_ass_panel.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(76, 139).addBox(-9.75F, 0.525F, 4.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 139).addBox(1.725F, 0.525F, 4.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 128).addBox(1.25F, 0.6F, 3.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 128).addBox(-10.25F, 0.6F, 3.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.8904F, -0.7301F, 3.0544F, -0.7418F, 1.0472F, 0.0F));

        PartDefinition cube_r89 = useless_ass_panel.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(124, 127).addBox(-7.5F, 0.0F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(126, 109).addBox(-1.5F, 0.0F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.8904F, -0.7301F, 3.0544F, -0.3927F, 1.0472F, 0.0F));

        PartDefinition cube_r90 = useless_ass_panel.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(134, 61).addBox(-2.45F, -6.7567F, 4.6978F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(132, 107).addBox(-2.2F, -6.8567F, 6.6978F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(102, 36).addBox(-5.7F, -6.8567F, 9.4478F, 9.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 1.0472F, 0.0F));

        PartDefinition cube_r91 = useless_ass_panel.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(8, 140).addBox(1.5F, 0.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(138, 139).addBox(-0.5F, 0.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(140, 8).addBox(3.5F, 0.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(140, 10).addBox(5.5F, 0.3F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 139).addBox(1.5F, 0.3F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 139).addBox(-0.5F, 0.3F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(130, 139).addBox(3.5F, 0.3F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(134, 139).addBox(5.5F, 0.3F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.2366F, 0.9589F, 9.6052F, -0.7418F, 1.0472F, 0.0F));

        PartDefinition lamp = root.addOrReplaceChild("lamp", CubeListBuilder.create(), PartPose.offset(-8.9458F, -22.5354F, 8.9259F));

        PartDefinition cube_r92 = lamp.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(42, 132).addBox(-1.75F, -0.575F, -0.725F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(32, 132).addBox(-1.75F, -0.575F, -0.725F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r93 = lamp.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(20, 137).addBox(-0.5F, -1.4752F, -0.498F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.6485F, -0.3839F, -0.692F, -1.5708F, -1.0472F, 0.0F));

        PartDefinition cube_r94 = lamp.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(136, 125).addBox(-0.5017F, -0.175F, -0.575F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.6485F, -0.3839F, -0.692F, -1.1345F, -1.0472F, 0.0F));

        PartDefinition cube_r95 = lamp.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(138, 6).addBox(-0.5005F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.5524F, -0.3178F, -0.0591F, 2.0071F, 1.0472F, 3.1416F));

        PartDefinition cube_r96 = lamp.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(40, 137).addBox(-0.525F, -1.0F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.9422F, 0.4F, -0.8327F, -0.3054F, -1.0472F, 0.0F));

        PartDefinition cube_r97 = lamp.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(84, 139).addBox(3.2F, -7.8567F, 6.9478F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(9.9458F, 1.8354F, -9.7259F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition throttle = root.addOrReplaceChild("throttle", CubeListBuilder.create().texOffs(16, 133).addBox(-0.5F, -3.9092F, -0.4945F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-4.52F, -17.8958F, 12.0975F));

        PartDefinition handbrake = root.addOrReplaceChild("handbrake", CubeListBuilder.create(), PartPose.offset(4.7098F, -18.2762F, -15.4415F));

        PartDefinition lever_section_2_r1 = handbrake.addOrReplaceChild("lever_section_2_r1", CubeListBuilder.create().texOffs(138, 72).addBox(-0.5F, -1.9F, 0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -4.0F, -0.829F, -0.0107F, 0.0091F));

        PartDefinition lever_section_1_r1 = handbrake.addOrReplaceChild("lever_section_1_r1", CubeListBuilder.create().texOffs(48, 134).addBox(-0.5F, -3.5F, 0.3F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0133F, 1.6435F, -2.453F, -0.829F, -0.0107F, 0.0091F));

        PartDefinition x = root.addOrReplaceChild("x", CubeListBuilder.create(), PartPose.offset(12.3022F, -19.5876F, -6.1706F));

        PartDefinition x_r1 = x.addOrReplaceChild("x_r1", CubeListBuilder.create().texOffs(138, 30).addBox(2.75F, 0.8F, -0.025F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition y = root.addOrReplaceChild("y", CubeListBuilder.create(), PartPose.offset(12.3022F, -19.5876F, -6.1706F));

        PartDefinition y_r1 = y.addOrReplaceChild("y_r1", CubeListBuilder.create().texOffs(138, 68).addBox(1.25F, 0.8F, -0.025F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition z = root.addOrReplaceChild("z", CubeListBuilder.create(), PartPose.offset(12.3022F, -19.5876F, -6.1706F));

        PartDefinition z_r1 = z.addOrReplaceChild("z_r1", CubeListBuilder.create().texOffs(138, 66).addBox(-0.25F, 0.8F, -0.025F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition increment = root.addOrReplaceChild("increment", CubeListBuilder.create(), PartPose.offset(12.3022F, -19.5876F, -6.1706F));

        PartDefinition increment_r1 = increment.addOrReplaceChild("increment_r1", CubeListBuilder.create().texOffs(138, 70).addBox(-2.0F, 0.8F, 1.475F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition randomiser = root.addOrReplaceChild("randomiser", CubeListBuilder.create(), PartPose.offset(1.95F, -21.45F, -0.8F));

        PartDefinition randomiser_r1 = randomiser.addOrReplaceChild("randomiser_r1", CubeListBuilder.create().texOffs(138, 127).addBox(6.25F, -8.0067F, 14.0478F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition communicator = root.addOrReplaceChild("communicator", CubeListBuilder.create(), PartPose.offset(-0.075F, -19.0F, -1.2F));

        PartDefinition communicator_r1 = communicator.addOrReplaceChild("communicator_r1", CubeListBuilder.create().texOffs(54, 120).addBox(-2.0145F, -0.59F, -1.5135F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2855F, 3.994F, 19.3645F, -0.0436F, 0.0F, 0.0F));

        PartDefinition monitor = root.addOrReplaceChild("monitor", CubeListBuilder.create(), PartPose.offset(12.3022F, -19.5876F, -6.1706F));

        PartDefinition monitor_r1 = monitor.addOrReplaceChild("monitor_r1", CubeListBuilder.create().texOffs(120, 45).addBox(-1.775F, 1.3F, -6.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 2.0944F, 0.0F));

        PartDefinition refuller = root.addOrReplaceChild("refuller", CubeListBuilder.create(), PartPose.offset(-12.7164F, -16.8731F, 7.3501F));

        PartDefinition refuller_r1 = refuller.addOrReplaceChild("refuller_r1", CubeListBuilder.create().texOffs(104, 41).addBox(-3.0F, -0.6F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, -1.0472F, 0.0F));

        PartDefinition door_control = root.addOrReplaceChild("door_control", CubeListBuilder.create(), PartPose.offset(-9.9F, -20.3F, 5.775F));

        PartDefinition door_control_r1 = door_control.addOrReplaceChild("door_control_r1", CubeListBuilder.create().texOffs(140, 12).addBox(-0.4569F, -0.1067F, -0.6304F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2F, -0.1F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition dimension_changer = root.addOrReplaceChild("dimension_changer", CubeListBuilder.create(), PartPose.offset(9.2366F, -19.7411F, 8.8052F));

        PartDefinition exterior_facing = root.addOrReplaceChild("exterior_facing", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition exterior_facing_r1 = exterior_facing.addOrReplaceChild("exterior_facing_r1", CubeListBuilder.create().texOffs(128, 167).addBox(4.4F, -8.6067F, 9.1228F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -20.7F, -0.8F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition fast_return_switch = root.addOrReplaceChild("fast_return_switch", CubeListBuilder.create(), PartPose.offset(1.95F, -21.45F, -0.8F));

        PartDefinition fast_return_switch_r1 = fast_return_switch.addOrReplaceChild("fast_return_switch_r1", CubeListBuilder.create().texOffs(88, 139).addBox(-1.7F, -7.1317F, 4.9728F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.95F, 0.75F, 0.0F, -0.7418F, 1.0472F, 0.0F));

        PartDefinition glowies = root.addOrReplaceChild("glowies", CubeListBuilder.create(), PartPose.offset(-11.2607F, -17.8381F, 6.5058F));

        PartDefinition cube_r98 = glowies.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(122, 139).addBox(-0.3F, -7.9067F, 11.9478F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.2107F, -2.9119F, -7.2808F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition bone42 = root.addOrReplaceChild("bone42", CubeListBuilder.create(), PartPose.offset(-10.4607F, -17.7881F, 7.7798F));

        PartDefinition cube_r99 = bone42.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(126, 139).addBox(1.2F, -7.9567F, 11.9478F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4607F, -2.9119F, -8.5798F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition bone41 = root.addOrReplaceChild("bone41", CubeListBuilder.create(), PartPose.offset(-11.9607F, -17.7881F, 5.1817F));

        PartDefinition cube_r100 = bone41.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(126, 139).addBox(-1.8F, -7.9567F, 11.9478F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.9607F, -2.9119F, -5.9817F, -0.7418F, -1.0472F, 0.0F));

        PartDefinition bone43 = root.addOrReplaceChild("bone43", CubeListBuilder.create(), PartPose.offset(-11.8691F, -19.993F, -2.5036F));

        PartDefinition cube_r101 = bone43.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(24, 124).addBox(3.7F, -7.6067F, 7.3478F, 2.5F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.8941F, -0.707F, 1.7286F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone44 = root.addOrReplaceChild("bone44", CubeListBuilder.create(), PartPose.offset(-10.6191F, -19.993F, -4.6687F));

        PartDefinition cube_r102 = bone44.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(124, 28).addBox(1.2F, -7.6067F, 7.3478F, 2.5F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.6441F, -0.707F, 3.8937F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone45 = root.addOrReplaceChild("bone45", CubeListBuilder.create(), PartPose.offset(-9.3691F, -19.993F, -6.8338F));

        PartDefinition cube_r103 = bone45.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(24, 124).addBox(-1.3F, -7.6067F, 7.3478F, 2.5F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.3941F, -0.707F, 6.0588F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone46 = root.addOrReplaceChild("bone46", CubeListBuilder.create(), PartPose.offset(-8.1191F, -19.993F, -8.9988F));

        PartDefinition cube_r104 = bone46.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(124, 28).addBox(-3.8F, -7.6067F, 7.3478F, 2.5F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.1441F, -0.707F, 8.2238F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone47 = root.addOrReplaceChild("bone47", CubeListBuilder.create(), PartPose.offset(-9.2387F, -21.4261F, -1.9193F));

        PartDefinition cube_r105 = bone47.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(12, 129).addBox(3.625F, -7.9067F, 6.5728F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.1887F, -0.0239F, 1.1193F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone48 = root.addOrReplaceChild("bone48", CubeListBuilder.create(), PartPose.offset(-7.7387F, -21.4261F, -4.5174F));

        PartDefinition cube_r106 = bone48.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(4, 129).addBox(0.625F, -7.9067F, 6.5728F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.6887F, -0.0239F, 3.7174F, -0.7418F, -2.0944F, 0.0F));

        PartDefinition bone49 = root.addOrReplaceChild("bone49", CubeListBuilder.create(), PartPose.offset(-6.2387F, -21.4261F, -7.1154F));

        PartDefinition cube_r107 = bone49.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(130, 0).addBox(-2.375F, -7.9067F, 6.5728F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.1887F, -0.0239F, 6.3154F, -0.7418F, -2.0944F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }


    @Override
    public void SetupAnimations(ConsoleTile tile, float ageInTicks) {
        if(!tile.ControlAnimationMap.isEmpty()) {
            if(tile.ControlAnimationMap.get(new Vec3(0.25f, 1.11f, 0.71f)) != null)
                this.throttle.xRot = (float) Math.toRadians(tile.ControlAnimationMap.get(new Vec3(0.25f, 1.11f, 0.71f)) * 45);
        }
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(tile.GetRotorAnimation(), HudolinConsoleAnimation.rotor_loop, ageInTicks);
    }

    @Override
    public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {

    }
}