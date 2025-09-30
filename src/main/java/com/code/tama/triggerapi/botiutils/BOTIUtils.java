package com.code.tama.triggerapi.botiutils;

import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.portal.PortalChunkRequestPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalChunkDataPacketS2C;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOTIUtils {
    public static List<BakedQuad> getModelFromBlock(BlockState state, BlockPos pos, PortalTileEntity tileEntity, RandomSource rand, Map<BlockPos, BotiChunkContainer> map) {
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        Direction[] directions = Direction.values();
        BakedModel model = blockRenderer.getBlockModel(state);
        List<BakedQuad> quads = new java.util.ArrayList<>(List.of());
        // render only non-occluded faces
        for (Direction dir : directions) {
            BlockPos neighbourPos = pos.relative(dir);
            BotiChunkContainer neighbourState = map.get(neighbourPos);
            boolean occluded = neighbourState != null &&
                    neighbourState.getState().isSolidRender(Minecraft.getInstance().level, neighbourPos);

            if(neighbourState != null && (!neighbourState.getState().canOcclude() && !state.canOcclude())) occluded = true;
            if (!occluded) {
                // pull just this side’s quads and render them
                quads.addAll(model.getQuads(state, dir, rand));
            }
        }
        return quads;
    }

    public static Map<BlockPos, BotiChunkContainer> getMapFromContainerList(List<BotiChunkContainer> list) {
        Map<BlockPos, BotiChunkContainer> map = new HashMap<>(list.size());
        for (BotiChunkContainer container : list) {
            map.put(container.getPos(), container);
        }
        return map;
    }

    public static VertexBuffer buildModelVBO(PortalTileEntity entity) {
        Minecraft mc = Minecraft.getInstance();

        BufferBuilder buffer = new BufferBuilder((int) Math.pow(16, 3));
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);

        // Dump all quads into the buffer
        PoseStack stack = new PoseStack();
        Map<BlockPos, BotiChunkContainer> chunkMap = getMapFromContainerList(entity.containers);
        chunkMap.forEach((pos, container) -> {
            BlockColors colors = mc.getBlockColors();
            int color = colors.getColor(container.getState(), Minecraft.getInstance().level, container.getPos(), 0);

            // Extract RGB components (normalize to 0-1 range)
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;

            stack.pushPose();
            stack.translate(pos.getX(), pos.getY(), pos.getZ());
            RandomSource rand = RandomSource.create(pos.asLong());

            for (BakedQuad quad : getModelFromBlock(container.getState(), pos, entity, rand, chunkMap)) {
                buffer.putBulkData(stack.last(), quad, r, g, b, 1.0F, container.getLight(), OverlayTexture.NO_OVERLAY, true);
            }

            stack.popPose();
        });

        BufferBuilder.RenderedBuffer rendered = buffer.end();

        VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
        vbo.bind();
        vbo.upload(rendered);
        VertexBuffer.unbind();

        return vbo;
    }

    public static void updateChunkModel(PortalTileEntity tileEntity) {
        assert Minecraft.getInstance().level != null;
        if (!Minecraft.getInstance().level.isClientSide()) return;

        long currentTime = Minecraft.getInstance().level.getGameTime();
        if (tileEntity.lastRequestTime == 0 || currentTime - tileEntity.lastRequestTime >= 10) {
            Level targetLevel = Minecraft.getInstance().level.dimension() == tileEntity.getTargetLevel() ? Minecraft.getInstance().level : null;
            if (targetLevel != null) {
                tileEntity.updateChunkDataFromServer(
                        new PortalChunkDataPacketS2C(tileEntity.getBlockPos(), targetLevel, tileEntity.getTargetPos())
                                .containersL);
            } else {
                Networking.INSTANCE.sendToServer(new PortalChunkRequestPacketC2S(
                        tileEntity.getBlockPos(), tileEntity.getTargetLevel(), tileEntity.getTargetPos()));
            }
            tileEntity.lastRequestTime = currentTime;
        }
    }
}
