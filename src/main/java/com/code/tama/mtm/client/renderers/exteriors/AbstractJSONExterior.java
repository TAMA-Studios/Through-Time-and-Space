package com.code.tama.mtm.client.renderers.exteriors;

import com.code.tama.mtm.server.tileentities.ExteriorTile;
import com.code.tama.triggerapi.JavaInJSON.IUseJavaJSON;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;

import static com.mojang.math.Axis.XP;

public class AbstractJSONExterior implements IUseJavaJSON, BlockEntityRenderer<ExteriorTile> {
    public AbstractJSONExterior(ResourceLocation model) {
        this.registerJavaJSON(model);
    }

    @Override
    public void render(ExteriorTile exteriorTile, float v, PoseStack poseStack, MultiBufferSource bufferSource, int i, int i1) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 1.5f, 0.5f);
        poseStack.mulPose(XP.rotationDegrees(180));

        if (getModel() != null)
            getModel().renderToBuffer(poseStack, bufferSource.getBuffer(getRenderType()), i, i1, 1, 1, 1, 1); // JavaJSON Extra
        poseStack.popPose();
    }
}
