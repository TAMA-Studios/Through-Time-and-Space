/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.Panels;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import com.code.tama.triggerapi.animation.*;
import com.code.tama.triggerapi.universal.UniversalCommon;
import com.code.tama.tts.client.TTSSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;

public class ToyotaThrottleBlock extends ThrottleBlock implements IGeoAnimatedBlock {
	public ToyotaThrottleBlock(Properties p_54120_) {
		super(p_54120_);
	}

	@Override
	public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos p_220829_, RandomSource p_220830_) {
		if (!AnimatedBlockRegistry.contains(p_220829_))
			AnimatedBlockRegistry.add(p_220829_, this);
		super.animateTick(p_220827_, p_220828_, p_220829_, p_220830_);
	}

	@Override
	public void transformRender(BlockState state, PoseStack poseStack, MultiBufferSource.BufferSource buffer, float partialTick) {
		poseStack.mulPose(state.getValue(ARSPanel.FACING).getOpposite().getRotation());
		poseStack.mulPose(Axis.XN.rotationDegrees(90f));
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
	                                      @NotNull Player player, InteractionHand hand, @NotNull BlockHitResult blockRayTraceResult) {
		if (hand.equals(InteractionHand.OFF_HAND))
			return InteractionResult.PASS;
		if (level.isClientSide)
			return InteractionResult.PASS;
		boolean Power = !state.getValue(POWERED);

		AtomicReference<InteractionResult> interactionResultAtomicReference = new AtomicReference<>();
		interactionResultAtomicReference.set(InteractionResult.FAIL);

		state.setValue(POWERED, Power);

		AtomicReference<SoundState> soundState = new AtomicReference<>(SoundState.FAIL);
		GetTARDISCapSupplier(level).ifPresent(cap -> {
			if (!cap.GetFlightData().isInFlight()) {
				cap.Dematerialize();
				soundState.set(SoundState.ON);
			} else if (cap.GetFlightData().isInFlight()) {
				cap.Rematerialize();
				soundState.set(SoundState.OFF);
			} else {
				soundState.set(SoundState.FAIL);
			}

			level.setBlockAndUpdate(pos, state.setValue(POWERED, Power));
			if (Power) animateOn(pos, level);
			else animateOff(pos, level);
			interactionResultAtomicReference.set(InteractionResult.SUCCESS);
		});

		// level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F,
		// 0.5f);
		switch (soundState.get()) {
			case ON -> level.playSound(null, pos, TTSSounds.THROTTLE_ON.get(), SoundSource.BLOCKS);
			case OFF -> level.playSound(null, pos, TTSSounds.THROTTLE_OFF.get(), SoundSource.BLOCKS);
			default -> level.playSound(null, pos, SoundEvents.NOTE_BLOCK_BIT.get(), SoundSource.BLOCKS);
		}
		level.gameEvent(player, state.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
		return interactionResultAtomicReference.get();
	}

	public void animateOn(BlockPos pos, Level level) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.add(pos, this).player.stop();
			AnimatedBlockRegistry.add(pos, this).player.play(GeoHelper.getAnimations("toyota_throttle").get("animation.on"), level.getGameTime());
		}
	}

	public void animateOff(BlockPos pos, Level level) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.add(pos, this).player.stop();
			AnimatedBlockRegistry.add(pos, this).player.play(GeoHelper.getAnimations("toyota_throttle").get("animation.off"), level.getGameTime());
		}
	}

	@Override
	public GeoModel getGeoModel() {
		return GeoHelper.getModel("blockgeo/toyota_throttle");
	}

	@Override
	public ResourceLocation getGeoTexture() {
		return UniversalCommon.modRL("textures/block/controls/toyota_throttle.png");
	}

	@Override
	public VoxelShape createShapeOFF() {
		return Stream
				.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(4, 1, 3, 12, 2, 11), Block.box(4, 2, 4, 12, 3, 10),
						Block.box(4, 3, 5, 12, 4, 9), Block.box(4, 4, 8, 12, 5, 10), Block.box(4, 5, 9, 12, 6, 11),
						Block.box(4, 6, 10, 12, 7, 12), Block.box(4, 7, 11.2, 12, 8, 12.2))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	}

	@Override
	public VoxelShape createShapeON() {
		return Stream
				.of(Block.box(0, 0, 0, 16, 1, 16), Block.box(4, 1, 3, 12, 2, 11), Block.box(4, 2, 4, 12, 3, 10),
						Block.box(4, 3, 5, 12, 4, 9), Block.box(4, 3, 2, 12, 4, 5),
						Block.box(4, 4, 0.5999999999999999, 12, 5, 3.5999999999999996),
						Block.box(4, 5, 0.5999999999999996, 12, 6, 1.5999999999999996))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	}
}
