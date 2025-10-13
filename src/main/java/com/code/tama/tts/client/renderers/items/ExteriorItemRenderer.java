/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.items;

import com.code.tama.tts.client.renderers.tiles.TardisExteriorRenderer;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ExteriorItemRenderer extends BlockEntityWithoutLevelRenderer {
    public ExteriorItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @Override
    public void renderByItem(
            @NotNull ItemStack stack,
            @NotNull ItemDisplayContext context,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        BlockState dummyState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();
        ExteriorTile dummyBlockEntity = new ExteriorTile(BlockPos.ZERO, dummyState);
        dummyBlockEntity.ShouldMakeDimOnNextTick = false;

        TardisExteriorRenderer<ExteriorTile> renderer = (TardisExteriorRenderer<ExteriorTile>)
                Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(dummyBlockEntity);
        poseStack.pushPose();

        poseStack.scale(0.35f, 0.35f, 0.35f);
        poseStack.translate(1.5, -0.25f, 0);

        if (context.equals(ItemDisplayContext.GUI)) {
            poseStack.mulPose(Axis.XP.rotationDegrees(20f));
            if (Minecraft.getInstance().level == null) poseStack.mulPose(Axis.YP.rotationDegrees(220f));
            else
                poseStack.mulPose(Axis.YP.rotationDegrees(
                        (float) Minecraft.getInstance().level.getGameTime() % 360));

            poseStack.translate(-0.5, 0, -0.5);

        } else {
            poseStack.scale(0.3f, 0.3f, 0.3f);
        }

        if (renderer != null) {
            renderer.render(
                    dummyBlockEntity,
                    Minecraft.getInstance().getPartialTick(),
                    poseStack,
                    buffer,
                    packedLight,
                    packedOverlay);
        }
        poseStack.popPose();
    }
}
