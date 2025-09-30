/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.items;

import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/** Renderer for the ConsoleTile BlockItem **/
public class ConsoleItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final AbstractConsoleTile ConsoleToRender;

    public ConsoleItemRenderer(
            BlockEntityRenderDispatcher dispatcher,
            EntityModelSet modelSet,
            BlockEntityType<?> type,
            BlockState state) {
        super(dispatcher, modelSet);
        this.ConsoleToRender = (AbstractConsoleTile) type.create(BlockPos.ZERO, state);
    }

    @Override
    public void renderByItem(
            @NotNull ItemStack stack,
            @NotNull ItemDisplayContext context,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {

        BlockEntityRenderer<AbstractConsoleTile> renderer =
                Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(this.ConsoleToRender);
        poseStack.pushPose();
        if (context.equals(ItemDisplayContext.GUI)) {
            poseStack.scale(0.35f, 0.35f, 0.35f);
            poseStack.translate(2f, 0.3f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(20f));
            poseStack.mulPose(Axis.YP.rotationDegrees(225f));

        } else {
            poseStack.scale(0.3f, 0.3f, 0.3f);
            poseStack.translate(1f, 1f, 1f);
        }

        if (renderer != null) {
            renderer.render(this.ConsoleToRender, 0, poseStack, buffer, packedLight, packedOverlay);
        }
        poseStack.popPose();
    }
}
