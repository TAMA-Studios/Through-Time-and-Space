/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.animations.consoles; // Save this class in your mod and generate

// all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 4.12.6 Exported for Minecraft version 1.19 or later with Mojang mappings
 *
 * @author Author
 */
public class NESSConsoleModelAnimation {
    public static final AnimationDefinition throttleon = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "throttle",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(132.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "throttle",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();

    public static final AnimationDefinition throttleoff = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "throttle",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(132.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "throttle",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic0 = AnimationDefinition.Builder.withLength(0.05F)
            .looping()
            .addAnimation(
                    "helmic",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.05F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic1 = AnimationDefinition.Builder.withLength(4.0F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    4.0F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic2 = AnimationDefinition.Builder.withLength(3.6F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    3.6F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic3 = AnimationDefinition.Builder.withLength(3.2F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    3.2F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic4 = AnimationDefinition.Builder.withLength(2.8F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    2.8F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic5 = AnimationDefinition.Builder.withLength(2.4F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    2.4F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic6 = AnimationDefinition.Builder.withLength(2.0F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    2.0F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic7 = AnimationDefinition.Builder.withLength(1.6F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.6F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic8 = AnimationDefinition.Builder.withLength(1.2F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.2F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic9 = AnimationDefinition.Builder.withLength(0.8F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.8F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmic10 = AnimationDefinition.Builder.withLength(0.4F)
            .looping()
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.4F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition pump = AnimationDefinition.Builder.withLength(0.5F)
            .addAnimation(
                    "pumpy",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.posVec(0.0F, 7.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.5F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition primary = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "primarymovy",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, -90.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();

    public static final AnimationDefinition ancillary = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "primarymovy",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(0.0F, 90.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();

    public static final AnimationDefinition preset = AnimationDefinition.Builder.withLength(1.5F)
            .looping()
            .addAnimation(
                    "analoggydial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.5F,
                                    KeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition analog = AnimationDefinition.Builder.withLength(1.5F)
            .looping()
            .addAnimation(
                    "analoggydial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.5F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition poweroff = AnimationDefinition.Builder.withLength(0.85F)
            .addAnimation(
                    "powerswitch",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(75.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.5F,
                                    KeyframeAnimations.degreeVec(-100.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.65F,
                                    KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.85F,
                                    KeyframeAnimations.degreeVec(-95.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition poweron = AnimationDefinition.Builder.withLength(0.6F)
            .addAnimation(
                    "powerswitch",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(-95.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.45F,
                                    KeyframeAnimations.degreeVec(65.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.6F,
                                    KeyframeAnimations.degreeVec(75.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition height1 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "height",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition height2 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "height",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition height3 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "height",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition height4 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "height",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 135.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition height5 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "height",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 180.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel1 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel2 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel3 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 45.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel4 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 67.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel5 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 90.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel6 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 112.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel7 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 135.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel8 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 157.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel9 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 180.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel10 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 202.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel11 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 225.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel12 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 247.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel13 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 270.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel14 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 292.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel15 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 315.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition wheel16 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "wheel",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 337.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition doortoggle = AnimationDefinition.Builder.withLength(0.3F)
            .addAnimation(
                    "doorbutton",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.25F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();

    public static final AnimationDefinition laseron = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "laser",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(117.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "laser",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition laseroff = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "laser",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(117.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "laser",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition phaseidle = AnimationDefinition.Builder.withLength(4.75F)
            .looping()
            .addAnimation(
                    "shellphaser",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.55F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 102.5F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -11.14F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.3F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 235.91F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.8F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.95F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    2.4F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 156.59F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    3.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -113.41F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    3.4F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 97.05F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    3.9F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -13.18F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    4.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 60.91F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    4.75F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition phaseon = AnimationDefinition.Builder.withLength(0.2F)
            .addAnimation(
                    "shellphaser",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition phaseoff = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "shellphaser",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition lockon = AnimationDefinition.Builder.withLength(0.3F)
            .addAnimation(
                    "lock",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(180.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition lockoff = AnimationDefinition.Builder.withLength(0.3F)
            .addAnimation(
                    "lock",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.degreeVec(180.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition ceilingtoggle = AnimationDefinition.Builder.withLength(0.3F)
            .addAnimation(
                    "ceiling",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition bell = AnimationDefinition.Builder.withLength(0.3F)
            .addAnimation(
                    "bell",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "bell",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "bell",
                    new AnimationChannel(
                            AnimationChannel.Targets.SCALE,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.scaleVec(1.0F, 0.65F, 1.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.scaleVec(1.0F, 1.05F, 1.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition monitoron = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "monitor",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition monitoroff = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "monitor",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition monitortoggle = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "monitor",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.25F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate1 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate2 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -45.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate3 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate4 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -135.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate5 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -180.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate6 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -225.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate7 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -270.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition rotate8 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "rotatedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -315.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition axis1 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "zigzaglever",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "zigzaglever",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(-3.075F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition axis2 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "zigzaglever",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "zigzaglever",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(-0.15F, 3.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition axis3 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "zigzaglever",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "zigzaglever",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(-0.125F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition handbrakeon = AnimationDefinition.Builder.withLength(0.5F)
            .addAnimation(
                    "handbrake",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -92.5F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "handbrake",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "handbrakepart",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition handbrakeoff = AnimationDefinition.Builder.withLength(0.5F)
            .addAnimation(
                    "handbrake",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -92.5F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "handbrake",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "handbrakepart",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -32.5F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time1 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time2 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -67.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time3 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -45.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 45.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time4 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 67.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time5 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 90.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time6 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 112.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time7 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 135.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time8 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 67.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 157.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time9 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 180.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time10 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 112.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 202.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time11 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 135.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 225.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time12 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 157.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 247.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time13 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 180.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 270.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time14 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 202.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 292.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time15 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 225.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 315.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition time16 = AnimationDefinition.Builder.withLength(0.05F)
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 247.5F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timedial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 337.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition timeactive = AnimationDefinition.Builder.withLength(0.5F)
            .looping()
            .addAnimation(
                    "clockdial1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.5F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 360.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition plotterturn = AnimationDefinition.Builder.withLength(1.0F)
            .addAnimation(
                    "plotter",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.0F,
                                    KeyframeAnimations.degreeVec(0.0F, -360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition plotteruse = AnimationDefinition.Builder.withLength(0.35F)
            .addAnimation(
                    "plotter",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, -0.6F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "plotter",
                    new AnimationChannel(
                            AnimationChannel.Targets.SCALE,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.scaleVec(1.0F, 0.725F, 1.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition gravityuse = AnimationDefinition.Builder.withLength(0.2F)
            .addAnimation(
                    "gravitybutton",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.25F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition shielduse = AnimationDefinition.Builder.withLength(0.2F)
            .addAnimation(
                    "shieldbutton",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.25F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition stabiliseruse = AnimationDefinition.Builder.withLength(0.35F)
            .addAnimation(
                    "stabiliserinput",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "stabiliserinput",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.posVec(0.0F, -1.5F, 0.5F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition healthuse = AnimationDefinition.Builder.withLength(0.35F)
            .addAnimation(
                    "diagnosticbutton",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition telepathicuse = AnimationDefinition.Builder.withLength(1.5F)
            .addAnimation(
                    "telepathicbutton",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.9F,
                                    KeyframeAnimations.posVec(0.0F, -0.5F, 0.25F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.5F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerboxopen = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "powerbox",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(-112.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "powerbox",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerboxclose = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "powerbox",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(-112.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "powerbox",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerswitch1off = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "powerswitch1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerswitch1on = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "powerswitch1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerswitch2off = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "powerswitch2",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerswitch2on = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "powerswitch2",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(52.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerswitch3on = AnimationDefinition.Builder.withLength(0.1F)
            .addAnimation(
                    "powerswitch3",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition powerswitch3off = AnimationDefinition.Builder.withLength(0.1F)
            .addAnimation(
                    "powerswitch3",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(67.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition chameleonuse = AnimationDefinition.Builder.withLength(1.4F)
            .addAnimation(
                    "chambuttons2",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.75F,
                                    KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.9F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "chambuttons2",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.75F,
                                    KeyframeAnimations.posVec(0.0F, -0.5F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.9F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "chambuttons1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.45F,
                                    KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.55F,
                                    KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.85F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.35F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "chambuttons1",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.45F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.55F,
                                    KeyframeAnimations.posVec(0.0F, -0.5F, -0.5F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.85F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "chambuttons4",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.7F,
                                    KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.8F,
                                    KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.1F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "chambuttons4",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.7F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.8F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, -0.5F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.1F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "chambuttons3",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.55F,
                                    KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.05F,
                                    KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.4F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "chambuttons3",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.55F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.05F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.25F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    1.4F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition timebutton1use = AnimationDefinition.Builder.withLength(0.2F)
            .addAnimation(
                    "timebutton1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timebutton1",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.25F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition timebutton2use = AnimationDefinition.Builder.withLength(0.2F)
            .addAnimation(
                    "timebutton2",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "timebutton2",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.25F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition zigzaginput = AnimationDefinition.Builder.withLength(0.2F)
            .addAnimation(
                    "axisinput",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.1F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.25F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition coordinatebankuse = AnimationDefinition.Builder.withLength(0.7F)
            .addAnimation(
                    "kbuttons3",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.3F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.6F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.7F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "kbuttons2",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.5F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.65F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "kbuttons1",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.45F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.6F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition saveruse = AnimationDefinition.Builder.withLength(1.9F)
            .addAnimation(
                    "saver2",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.7F,
                                    KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.3F,
                                    KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.9F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "saver2",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.7F,
                                    KeyframeAnimations.posVec(0.0F, 0.25F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.3F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.9F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "saver1",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.7F,
                                    KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.3F,
                                    KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.9F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(
                    "saver1",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.7F,
                                    KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.3F,
                                    KeyframeAnimations.posVec(0.0F, 0.25F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    1.9F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();

    public static final AnimationDefinition dimensionuse = AnimationDefinition.Builder.withLength(0.45F)
            .addAnimation(
                    "dimension",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.posVec(0.0F, -0.5F, -0.5F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.35F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition dimensionconfirm = AnimationDefinition.Builder.withLength(0.2F)
            .addAnimation(
                    "dimension",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(
                                    0.2F,
                                    KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition dimensioncancel = AnimationDefinition.Builder.withLength(0.25F)
            .addAnimation(
                    "dimension",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(-50.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.25F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();

    public static final AnimationDefinition helmicactive = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition helmicdeactive = AnimationDefinition.Builder.withLength(0.15F)
            .addAnimation(
                    "helmicball",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, -0.75F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    0.15F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition pressureuse = AnimationDefinition.Builder.withLength(3.0F)
            .looping()
            .addAnimation(
                    "pressuredial",
                    new AnimationChannel(
                            AnimationChannel.Targets.ROTATION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(
                                    3.0F,
                                    KeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(
                    "pressuredial",
                    new AnimationChannel(
                            AnimationChannel.Targets.POSITION,
                            new Keyframe(
                                    0.0F,
                                    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
}
