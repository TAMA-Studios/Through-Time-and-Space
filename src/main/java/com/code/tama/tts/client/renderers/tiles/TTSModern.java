/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import static com.code.tama.tts.TTSMod.MODID;
import static com.mojang.math.Axis.XP;

import com.code.tama.triggerapi.JavaInJSON.IUseJavaJSON;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TTSModern<T extends BlockEntity> implements IUseJavaJSON, BlockEntityRenderer<T> {
    public TTSModern() {
        this.registerJavaJSON(new ResourceLocation(MODID, "models/exterior/voxel_moffat.json"));
    }

    public TTSModern(BlockEntityRendererProvider.Context context) {
        this.registerJavaJSON(new ResourceLocation(MODID, "models/java/tts.json"));
    }

    @Override
    public void render(
            T exampleTileEntity, float v, PoseStack poseStack, MultiBufferSource bufferSource, int i, int i1) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 1.5f, 0.5f);
        poseStack.mulPose(XP.rotationDegrees(180));
        getModel().renderToBuffer(poseStack, bufferSource.getBuffer(getRenderType()), i, i1, 1, 1, 1, 1); // JavaJSON
        // Extra
        poseStack.popPose();
    }
}
