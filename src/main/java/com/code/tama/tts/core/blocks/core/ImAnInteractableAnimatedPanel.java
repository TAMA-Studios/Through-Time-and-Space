/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.blocks.core;

import com.code.tama.tts.client.EmmisiveRenderType;
import com.code.tama.tts.client.TTSSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import com.code.tama.triggerapi.animation.AnimatedBlockConfig;
import com.code.tama.triggerapi.animation.AnimatedBlockRegistry;
import com.code.tama.triggerapi.animation.GeoHelper;
import com.code.tama.triggerapi.animation.IGeoAnimatedBlock;

public interface ImAnInteractableAnimatedPanel extends IGeoAnimatedBlock {
	default void rClickAnim(@NotNull Level world, @NotNull BlockPos pos) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.add(pos, this).player.stop();
			AnimatedBlockRegistry.add(pos, this).player.play(GeoHelper.getAnimations("panel").get("animation.rclick"),
					world.getGameTime());
		}
		world.playSound(null, pos, TTSSounds.KEYBOARD_PRESS_01.get(), SoundSource.BLOCKS);
	}

	default void lClickAnim(@NotNull Level world, @NotNull BlockPos pos) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.add(pos, this).player.stop();
			AnimatedBlockRegistry.add(pos, this).player.play(GeoHelper.getAnimations("panel").get("animation.lclick"),
					world.getGameTime());
		}
		world.playSound(null, pos, TTSSounds.KEYBOARD_PRESS_01.get(), SoundSource.BLOCKS);
	}

	default void mClickAnim(@NotNull Level world, @NotNull BlockPos pos) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.add(pos, this).player.stop();
			AnimatedBlockRegistry.add(pos, this).player.play(GeoHelper.getAnimations("panel").get("animation.mclick"),
					world.getGameTime());
		}
		world.playSound(null, pos, TTSSounds.KEYBOARD_PRESS_01.get(), SoundSource.BLOCKS);
	}

	@Override
	default void transformRender(BlockState state, PoseStack poseStack, MultiBufferSource.BufferSource buffer,
			float partialTick) {
		poseStack.mulPose(state.getValue(HorizontalDirectionalBlock.FACING).getOpposite().getRotation());
		poseStack.mulPose(Axis.XN.rotationDegrees(90f));
	}

	@Override
	default RenderType renderType() {
		return EmmisiveRenderType.getEmissiveEntity(getGeoTexture());
	}

	default void onPlace(BlockPos pos) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.add(pos, this);
		}
	}
}
