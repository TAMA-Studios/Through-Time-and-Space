package com.code.tama.mtm.client.renderers;


import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.core.abstractClasses.HierarchicalExteriorModel;
import com.code.tama.mtm.core.interfaces.IUseExteriorModels;
import com.code.tama.mtm.server.misc.ExteriorVariant;
import com.code.tama.mtm.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TardisExteriorRenderer<T extends ExteriorTile> extends IUseExteriorModels implements BlockEntityRenderer<T> {
    public HierarchicalExteriorModel MODEL;
    public ResourceLocation TEXTURE = new ResourceLocation("mtm", "textures/tiles/exterior.png");
    int index = -1;
    int VarIndex = -1;

    public TardisExteriorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull T exteriorTile, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        float transparency = exteriorTile.getTransparency();
        if(index != exteriorTile.getModelIndex()) {
            index = exteriorTile.getModelIndex();
            this.MODEL = this.getHandler().InstanceModels.get(exteriorTile.getModelIndex());
        }

        if(VarIndex != ExteriorVariants.GetOrdinal(exteriorTile.GetVariant())) {
            VarIndex = ExteriorVariants.GetOrdinal(exteriorTile.GetVariant());
            this.TEXTURE = exteriorTile.GetVariant().GetTexture();

            boolean same = false;
            if(!ExteriorVariants.Get(VarIndex).GetModelName().equals(this.getHandler().InstanceModels.get(index).GetModelName())) {
                same = true;
                ExteriorVariant variant = exteriorTile.GetVariant();
                while(same) {
                    variant = ExteriorVariants.Cycle(variant);

//                    System.out.println(variant.GetModelName());
//                    System.out.println(this.getHandler().InstanceModels.get(index).GetModelName());
                    if(variant.GetModelName().equals(this.getHandler().InstanceModels.get(index).GetModelName())) {
                        exteriorTile.Variant = variant;
                        this.VarIndex = ExteriorVariants.GetOrdinal(variant);
                        this.TEXTURE = variant.GetTexture();
                        exteriorTile.NeedsClientUpdate();
                        same = false;
                        break;
                    }
                }
            }
        }

        poseStack.pushPose();
        poseStack.translate(0, -0.645, 0);
        this.MODEL.SetupAnimations(exteriorTile, partialTicks);
        this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)), combinedLight, OverlayTexture.NO_OVERLAY,
                1.0f, 1.0f, 1.0f, transparency);

//        this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(exteriorTile.Variant.GetEmmisiveTexture())), combinedLight, OverlayTexture.NO_OVERLAY,
//                1.0f, 1.0f, 1.0f, transparency);
        poseStack.popPose();
    }
}