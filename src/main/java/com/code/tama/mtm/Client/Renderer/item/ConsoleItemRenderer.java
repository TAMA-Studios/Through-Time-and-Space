package com.code.tama.mtm.Client.Renderer.item;

import com.code.tama.mtm.Blocks.MBlocks;
import com.code.tama.mtm.TileEntities.ConsoleTile;
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
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/** Renderer for the ExteriorTile BlockItem **/
public class ConsoleItemRenderer extends BlockEntityWithoutLevelRenderer {
    public ConsoleItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext context, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState dummyState = MBlocks.HUDOLIN_CONSOLE_BLOCK.get().defaultBlockState();
        ConsoleTile dummyBlockEntity = new ConsoleTile(BlockPos.ZERO, dummyState);

        BlockEntityRenderer<ConsoleTile> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(dummyBlockEntity);
        poseStack.pushPose();
        if(context.equals(ItemDisplayContext.GUI)) {
            poseStack.scale(0.35f, 0.35f, 0.35f);
            poseStack.translate(2f, 0.3f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(20f));
            poseStack.mulPose(Axis.YP.rotationDegrees(225f));

        }
        else {
            poseStack.scale(0.3f, 0.3f, 0.3f);
            poseStack.translate(1f, 1f, 1f);
        }

        if (renderer != null) {
            renderer.render(dummyBlockEntity, 0, poseStack, buffer, packedLight, packedOverlay);
        }
        poseStack.popPose();
    }
}
