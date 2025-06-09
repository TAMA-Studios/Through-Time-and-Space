/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;


import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONParsed;
import com.code.tama.tts.client.models.core.HierarchicalExteriorModel;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TardisExteriorRenderer<T extends ExteriorTile> implements BlockEntityRenderer<T> {
    public HierarchicalExteriorModel MODEL;
    public ResourceLocation TEXTURE = new ResourceLocation("tts", "textures/tiles/exterior.png");
    ResourceLocation index = new ResourceLocation("");
    int VarIndex = -1;

    public TardisExteriorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull T exteriorTile, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

        float transparency = exteriorTile.getTransparency();
//        if(index != exteriorTile.getModelIndex()) {
//            index = exteriorTile.getModelIndex();
//            this.MODEL = ExteriorModelsBakery.InstanceModels.get(exteriorTile.GetVariant().GetModelName());
//        }

        poseStack.pushPose();
        poseStack.translate(0.5, 1, 0.5);

        AbstractJSONRenderer ext = new AbstractJSONRenderer(exteriorTile.GetVariant().GetModelName());

        JavaJSONParsed parsed = JavaJSON.getParsedJavaJSON(ext);

        parsed.getPart("LeftDoor").yRot = exteriorTile.DoorsOpen() > 1 ? (float) Math.toRadians(70) : 0;//(float) Math.toRadians(0);
        parsed.getPart("RightDoor").yRot = exteriorTile.DoorsOpen() > 0 ? (float) Math.toRadians(-70) : 0;

        parsed.getModelInfo().getModel().renderToBuffer(poseStack, bufferSource.getBuffer(ext.getRenderType()), combinedLight, OverlayTexture.NO_OVERLAY,
                1.0f, 1.0f, 1.0f, transparency);

//        ext.render(exteriorTile, partialTicks, poseStack, bufferSource, combinedLight, combinedOverlay);
        poseStack.popPose();
//        if(VarIndex != ExteriorVariants.GetOrdinal(exteriorTile.GetVariant())) {
//            VarIndex = ExteriorVariants.GetOrdinal(exteriorTile.GetVariant());
//            this.TEXTURE = exteriorTile.GetVariant().GetTexture();
//
//            if(!ExteriorVariants.Get(VarIndex).GetModelName().equals(exteriorTile.GetVariant().GetModelName())) {
//                ExteriorVariant variant = exteriorTile.GetVariant();
//                while(true) {
//                    variant = ExteriorVariants.Cycle(variant);
//
////                    System.out.println(variant.GetModelName());
////                    System.out.println(this.getHandler().InstanceModels.get(index).GetModelName());
//                    if(variant.GetModelName().equals(ExteriorModelsBakery.InstanceModels.get(index).GetModelName())) {
//                        exteriorTile.Variant = variant;
//                        this.VarIndex = ExteriorVariants.GetOrdinal(variant);
//                        this.TEXTURE = variant.GetTexture();
//                        exteriorTile.NeedsClientUpdate();
//                        break;
//                    }
//                }
//            }
//        }

//        poseStack.pushPose();
//        poseStack.translate(0, -0.645, 0);
//        this.MODEL.SetupAnimations(exteriorTile, partialTicks);
//        this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)), combinedLight, OverlayTexture.NO_OVERLAY,
//                1.0f, 1.0f, 1.0f, transparency);

//        this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(exteriorTile.Variant.GetEmmisiveTexture())), combinedLight, OverlayTexture.NO_OVERLAY,
//                1.0f, 1.0f, 1.0f, transparency);
//        poseStack.popPose();
    }
}