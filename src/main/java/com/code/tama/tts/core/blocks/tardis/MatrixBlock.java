package com.code.tama.tts.core.blocks.tardis;

import com.code.tama.triggerapi.animation.*;
import com.code.tama.triggerapi.universal.UniversalCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MatrixBlock extends AnimatedGeoBlockBase {
    public MatrixBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos p_220829_, RandomSource p_220830_) {
        if (!AnimatedBlockRegistry.contains(p_220829_))
            AnimatedBlockRegistry.add(p_220829_, this).player.play(GeoHelper.getAnimations("matrix").get("animation.idle"), p_220828_.getGameTime());
        super.animateTick(p_220827_, p_220828_, p_220829_, p_220830_);
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                        @NotNull BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
            AnimatedBlockRegistry.add(pos, this).player.play(GeoHelper.getAnimations("matrix").get("animation.idle"), level.getGameTime());
        }
    }

    @Override
    protected BlockEntityType<AnimatedGeoBlockEntity> getBlockEntityType() {
        return null; // TODO: Make block entity for matrix for BE compat!
    }

    @Override
    public GeoModel getGeoModel() {
        return GeoHelper.getModel("blockgeo/matrix");
    }

    @Override
    public ResourceLocation getGeoTexture() {
        return UniversalCommon.modRL("textures/block/core/matrix.png");
    }

    @Override
    public void transformRender(BlockState state, PoseStack poseStack, MultiBufferSource.BufferSource buffer, float partialTick) {}
}
