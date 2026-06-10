package com.code.tama.tts.client.models;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class VortexCannonAnimation {
		public static final AnimationDefinition FIRE = AnimationDefinition.Builder.withLength(1.3333F)
				.addAnimation("wheels",
						new AnimationChannel(AnimationChannel.Targets.ROTATION,
								new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(0.5F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(1.25F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM)))
				.addAnimation("base",
						new AnimationChannel(AnimationChannel.Targets.ROTATION,
								new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(1.25F, KeyframeAnimations.degreeVec(1.25F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM)))
				.addAnimation("base",
						new AnimationChannel(AnimationChannel.Targets.POSITION,
								new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 1.9924F, -2.1743F),
										AnimationChannel.Interpolations.CATMULLROM),
								new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.5F, -11.04F),
										AnimationChannel.Interpolations.CATMULLROM)))
				.build();
	}
