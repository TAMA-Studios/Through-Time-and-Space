package com.code.tama.mtm.Client.Renderer;


import com.code.tama.mtm.Entities.Controls.ModularControl;
import com.code.tama.mtm.Items.MItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class ControlRenderer extends EntityRenderer<ModularControl> {
    public ControlRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ModularControl modularControl) {
        return null;
    }

    @Override
    public boolean shouldRender(@NotNull ModularControl LivingEntity, @NotNull Frustum Camera, double CamX, double CamY, double CamZ) {
        return super.shouldRender(LivingEntity, Camera, CamX, CamY, CamZ);
    }

    @Override
    public void render(@NotNull ModularControl Entity, float EntityYaw, float PartialTick, @NotNull PoseStack PoseStack, @NotNull MultiBufferSource Buffer, int PackedLight) {
        super.render(Entity, EntityYaw, PartialTick, PoseStack, Buffer, PackedLight);
        if(Minecraft.getInstance().hitResult instanceof EntityHitResult result && result.getEntity() == Entity){
            assert Minecraft.getInstance().player != null;
            if (Minecraft.getInstance().player.isHolding(MItems.SONIC_SCREWDRIVER.get()))
                this.renderNameTag(Entity, Entity.TranslationKey(), PoseStack, Buffer, PackedLight);
        }
    }
}