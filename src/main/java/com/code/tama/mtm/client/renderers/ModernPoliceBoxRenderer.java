package com.code.tama.mtm.client.renderers;


import com.code.tama.mtm.client.models.ModernBoxModel;
import com.code.tama.mtm.server.misc.interfaces.IExteriorModel;
import com.code.tama.mtm.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import static com.code.tama.mtm.MTMMod.MODID;

public class ModernPoliceBoxRenderer<T extends ExteriorTile, C extends HierarchicalModel<Entity> & IExteriorModel<T>> implements BlockEntityRenderer<T> {
    public C COLIN_RICHMOND_MODEL;
    public static ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_11b.png");

    public ModernPoliceBoxRenderer(BlockEntityRendererProvider.Context context) {
        this.AddExterior((C) new ModernBoxModel<ExteriorTile>(context.bakeLayer(ModernBoxModel.LAYER_LOCATION)));
    }

    public void AddExterior(C exterior) {
        this.COLIN_RICHMOND_MODEL = exterior;//context.bakeLayer(ModernBoxModel.LAYER_LOCATION);
    }

    @Override
    public void render(@NotNull T exteriorTile, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if(exteriorTile.Variant != null)
            if(exteriorTile.Variant.GetTexture() != null && !exteriorTile.Variant.GetTexture().toString().replace(":", "").isBlank())
                TEXTURE = exteriorTile.Variant.GetTexture();
        else TEXTURE = new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_11b.png");
        float transparency = exteriorTile.getTransparency();

        poseStack.pushPose();
//        poseStack.mulPose(Axis.XP.rotationDegrees(180));
//        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.translate(0, -0.645, 0);
//        poseStack.scale(0.43f, 0.43f, 0.43f);
        this.COLIN_RICHMOND_MODEL.SetupAnimations(exteriorTile, partialTicks);
        this.COLIN_RICHMOND_MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)), combinedLight, OverlayTexture.NO_OVERLAY,
                1.0f, 1.0f, 1.0f, transparency);
        poseStack.popPose();


    }
}
