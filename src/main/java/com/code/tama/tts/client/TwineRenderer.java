/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client;

import com.code.tama.tts.mixin.client.ILevelRendererAccessor;
import com.code.tama.tts.server.items.TwineItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

public class TwineRenderer {
    public static void renderTwine(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES)
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null)
            return;

        // Check if player is holding twine
        for (ItemStack stack : player.getHandSlots()) {
            if (stack.getItem() instanceof TwineItem) {
                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains("BoundEntityId")) {
                    int entityId = tag.getInt("BoundEntityId");
                    Entity boundEntity = player.level().getEntity(entityId);

                    if (boundEntity != null) {
                        renderTwineString(event.getPoseStack(),
                                ((ILevelRendererAccessor) event.getLevelRenderer()).getRenderBuffers().bufferSource(),
                                player, boundEntity, event.getPartialTick());
                    }
                }
            }
        }
    }

    private static void renderTwineString(PoseStack poseStack, MultiBufferSource buffer, Player player, Entity target,
                                          float partialTick) {
        poseStack.pushPose();

        Vec3 playerPos = player.getEyePosition(partialTick).add(0, -0.5, 0);
        Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2, 0);
        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        poseStack.translate(playerPos.x - camera.x, playerPos.y - camera.y, playerPos.z - camera.z);

        VertexConsumer vc = buffer.getBuffer(RenderType.leash());
        Matrix4f matrix = poseStack.last().pose();

        float dx = (float) (targetPos.x - playerPos.x);
        float dy = (float) (targetPos.y - playerPos.y);
        float dz = (float) (targetPos.z - playerPos.z);

        int segments = Mth.clamp((int) (Math.sqrt(dx * dx + dz * dz) * 6), 24, 64);
        float thickness = 0.025f;
        int light = LightTexture.FULL_BRIGHT;

        for (int i = 0; i <= segments; i++) {
            float t = i / (float) segments;
            float nextT = Math.min(1.0f, (i + 1f) / segments);

            float x = dx * t;
            float y = dy * t - Mth.sin(t * Mth.PI) * 0.2f;
            float z = dz * t;

            float nx = dx * nextT - x;
            float ny = dy * nextT - y - Mth.sin(nextT * Mth.PI) * 0.2f;
            float nz = dz * nextT - z;

            float len = Mth.sqrt(nx * nx + nz * nz);
            if (len != 0.0f) {
                nx /= len;
                nz /= len;
            }

            float px = -nz * thickness;
            float pz = nx * thickness;

            int r = (i & 1) == 0 ? 139 : 120;
            int g = (i & 1) == 0 ? 90 : 75;
            int b = (i & 1) == 0 ? 43 : 35;

            vc.vertex(matrix, x + px, y, z + pz).color(r, g, b, 255).uv2(light).endVertex();

            vc.vertex(matrix, x - px, y, z - pz).color(r, g, b, 255).uv2(light).endVertex();
        }

        poseStack.popPose();
    }
}