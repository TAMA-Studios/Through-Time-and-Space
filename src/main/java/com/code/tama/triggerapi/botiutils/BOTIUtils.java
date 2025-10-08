/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.botiutils;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.triggerapi.rendering.BOTIInit;
import com.code.tama.triggerapi.rendering.FBOHelper;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.client.FluidQuadCollector;
import com.code.tama.tts.mixin.BlockAccessor;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.portal.PortalChunkRequestPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalChunkDataPacketS2C;
import com.code.tama.tts.server.tileentities.AbstractPortalTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import java.util.*;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class BOTIUtils {
    public static BOTIInit BOTI = new BOTIInit();
    public static List<BakedQuad> getModelFromBlock(
            BlockState state, BlockPos pos, RandomSource rand, Map<BlockPos, BotiChunkContainer> map) {
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        Direction[] directions = Direction.values();
        BakedModel model = blockRenderer.getBlockModel(state);
        List<BakedQuad> quads = new java.util.ArrayList<>(List.of());
        // render only non-occluded faces
        for (Direction dir : directions) {
            BlockPos neighbourPos = pos.relative(dir);
            BotiChunkContainer neighborContainer = map.get(neighbourPos);
            if (neighborContainer != null) {
                if (BOTIUtils.shouldRenderFace(
                        state, neighborContainer.getState(), Minecraft.getInstance().level, pos, dir, neighbourPos))
                    quads.addAll(model.getQuads(state, dir, rand));
            } else quads.addAll(model.getQuads(state, dir, rand));
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

    public static VertexBuffer buildModelVBO(List<BotiChunkContainer> containers, AbstractPortalTile tile) {
        Minecraft mc = Minecraft.getInstance();

        BufferBuilder buffer = new BufferBuilder((int) Math.pow(16, 3));
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);

        // Dump all quads into the buffer
        PoseStack stack = new PoseStack();

        // TODO: POSITION VBO RELATIVE TO BLOCK
//        stack.translate(tile.getBlockPos().getX(), tile.getBlockPos().getY(), tile.getBlockPos().getZ());

        Map<BlockPos, BotiChunkContainer> chunkMap = getMapFromContainerList(containers);

        chunkMap.forEach((pos, container) -> {
            BlockColors colors = mc.getBlockColors();
            int color = colors.getColor(container.getState(), Minecraft.getInstance().level, container.getPos(), 0);

            // Extract RGB components (normalize to 0-1 range)
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;

            RandomSource rand = RandomSource.create(pos.asLong());
            stack.pushPose();
            stack.translate(pos.getX(), pos.getY(), pos.getZ());

            if (container.isIsFluid()) {
                FluidState fluidState = container.getFluidState();
                if (!fluidState.isEmpty()) {
                    FluidQuadCollector fluidCollector = new FluidQuadCollector();

                    assert Minecraft.getInstance().level != null;
                    Minecraft.getInstance()
                            .getBlockRenderer()
                            .renderLiquid(
                                    pos,
                                    Minecraft.getInstance().level,
                                    fluidCollector,
                                    container.getState(),
                                    fluidState);

                    // Now feed collector.getVertices() into VBO
                    for (FluidQuadCollector.FluidVertex v : fluidCollector.getVertices()) {
                        buffer.vertex(v.x, v.y, v.z)
                                .color(v.r, v.g, v.b, v.a)
                                .uv(v.u, v.v)
                                .uv2(container.getLight())
                                .endVertex();
                    }
                }
            }

            for (BakedQuad quad : getModelFromBlock(container.getState(), pos, rand, chunkMap)) {
                // Convert packed light into brightness factor (0.0–1.0)
                float brightness = (float) container.getLight() / 0xf000f0;

                // Apply brightness to base RGB values
                float rLit = r;// - 1 + brightness;
                float gLit = g;// - 1 + brightness;
                float bLit = b;// - 1 + brightness;

                buffer.putBulkData(
                        stack.last(),
                        quad,
                        rLit,
                        gLit,
                        bLit,
                        1.0F,
                        container.getLight(),
                        OverlayTexture.NO_OVERLAY,
                        true);
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

    public static void updateChunkModel(AbstractPortalTile tileEntity) {
        assert Minecraft.getInstance().level != null;
        if (!Minecraft.getInstance().level.isClientSide()) return;
        tileEntity.containers.clear();
        tileEntity.blockEntities.clear();

        long currentTime = Minecraft.getInstance().level.getGameTime();

        if(tileEntity.targetLevel != null)
            Networking.INSTANCE.sendToServer(new PortalChunkRequestPacketC2S(
                tileEntity.getBlockPos(), tileEntity.getTargetLevel(), tileEntity.getTargetPos()));

        tileEntity.lastRequestTime = currentTime;
    }

    public static boolean shouldRenderFace(
            BlockState state, BlockState neighbor, BlockGetter level, BlockPos pos, Direction dir, BlockPos secondPos) {
        if(true) return true;

        if (state.skipRendering(neighbor, dir)) {
            return false;
        } else if (state.supportsExternalFaceHiding()
                && neighbor.hidesNeighborFace(level, secondPos, state, dir.getOpposite())) {
            return false;
        } else if (neighbor.canOcclude()) {
            Block.BlockStatePairKey block$blockstatepairkey = new Block.BlockStatePairKey(state, neighbor, dir);
            Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> object2bytelinkedopenhashmap =
                    BlockAccessor.getOcclusionCache().get();
            byte b0 = object2bytelinkedopenhashmap.getAndMoveToFirst(block$blockstatepairkey);
            if (b0 != 127) {
                return b0 != 0;
            } else {
                VoxelShape voxelshape = state.getFaceOcclusionShape(level, pos, dir);
                if (voxelshape.isEmpty()) {
                    return true;
                } else {
                    VoxelShape voxelshape1 = neighbor.getFaceOcclusionShape(level, secondPos, dir.getOpposite());
                    boolean flag = Shapes.joinIsNotEmpty(voxelshape, voxelshape1, BooleanOp.ONLY_FIRST);
                    if (object2bytelinkedopenhashmap.size() == 2048) {
                        object2bytelinkedopenhashmap.removeLastByte();
                    }

                    object2bytelinkedopenhashmap.putAndMoveToFirst(block$blockstatepairkey, (byte) (flag ? 1 : 0));
                    return flag;
                }
            }
        } else {
            return true;
        }
    }

    public static void Render(PoseStack pose, MultiBufferSource buffer, AbstractPortalTile portal) {
        Minecraft mc = Minecraft.getInstance();

        assert mc.level != null;
        mc.level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> FBOHelper.Render(portal, pose, 0xf000f0));
//        mc.updateMaxMipLevel();
//
//        StencilUtils.setupStencil((matrix) -> StencilUtils.drawFrame(pose, 2, 4), pose);
//
//        mc.getMainRenderTarget().unbindWrite();
//
//        BOTI.setupFramebuffer();
//        BOTI.fbo.bindWrite(false);
//
//        assert mc.level != null;
//        Vec3 skyColor = mc.level.getSkyColor(mc.player.position(), mc.getPartialTick());
//        BOTI.fbo.setClearColor((float)skyColor.x, (float)skyColor.y, (float)skyColor.z, 0);
//
//// Clear both depth and stencil properly
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
//
//        pose.pushPose();
//
//        pose.scale(0.2f, 0.2f, 0.2f);
//
//        pose.pushPose();
//
//        pose.scale(0.2f, 0.2f, 0.2f);
//
//        RenderStuff(pose, portal);
//
//        pose.popPose();
//
//        mc.getMainRenderTarget().bindWrite(false);
//
//        BOTI.fbo.blitToScreen(mc.getWindow().getScreenWidth(), mc.getWindow().getScreenHeight(), true);
//        StencilUtils.endStencil();
//
//        BOTI.endFBO();
//
//        mc.getMainRenderTarget().bindWrite(false);

//        pose.popPose();
    }

    public static void RenderStuff(PoseStack pose, AbstractPortalTile portal) {
        Minecraft minecraft = Minecraft.getInstance();

        assert minecraft.level != null;
        long currentTime = minecraft.level.getGameTime();

        if (currentTime - portal.lastUpdateTime >= 40) { // 1200 for a min
            BOTIUtils.updateChunkModel(portal);
            portal.lastUpdateTime = currentTime;
        }

        if (portal.MODEL_VBO == null) { // It'll be null the first time it's accessed, forcing a build
            portal.MODEL_VBO = BOTIUtils.buildModelVBO(portal.containers, portal);
        } else {

            pose.pushPose();

            assert GameRenderer.getPositionColorTexLightmapShader() != null;
            RenderSystem.setupShaderLights(GameRenderer.getPositionColorTexLightmapShader());

            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();

            portal.MODEL_VBO.bind();
            portal.MODEL_VBO.drawWithShader(
                    pose.last().pose(),
                    RenderSystem.getProjectionMatrix(),
                    Objects.requireNonNull(RenderSystem.getShader()));
            VertexBuffer.unbind();

            pose.popPose();
        }
    }


    @SuppressWarnings("unchecked")
    public static void PortalChunkDataPacketS2C(ServerPlayer player, AbstractPortalTile portalTile, Level level) {
        BlockPos portalPos = portalTile.getBlockPos();
        BlockPos targetPos = portalTile.getTargetPos();
        Direction exteriorAxis = Direction.NORTH;
        if (Capabilities.getCap(Capabilities.TARDIS_LEVEL_CAPABILITY, portalTile.getLevel())
                .isPresent())
            exteriorAxis = Capabilities.getCap(Capabilities.TARDIS_LEVEL_CAPABILITY, portalTile.getLevel())
                    .orElseGet(null)
                    .GetNavigationalData()
                    .getDestinationFacing();

        int maxBlocks = 70000;
        try {
            ArrayList<BotiChunkContainer> containers = new ArrayList<>();
            List<List<BotiChunkContainer>> containerLists = new ArrayList<>();
            boolean isSquare = true;
            int chunksToRender = 4;
            int uMax = (exteriorAxis.equals(Direction.WEST) ? 1 : chunksToRender / 2);
            int uMin = (exteriorAxis.equals(Direction.EAST) ? 0 : -chunksToRender / 2);
            int vMax = (exteriorAxis.equals(Direction.NORTH) ? 1 : chunksToRender / 2);
            int vMin = (exteriorAxis.equals(Direction.SOUTH) ? 0 : -chunksToRender / 2);

            if(isSquare) {
                vMin = -chunksToRender / 2;
                vMax = chunksToRender / 2;
                uMax = chunksToRender / 2;
                uMin = -chunksToRender / 2;
            }
            for (int u = uMin + 1; u < uMax; u++) { // turn either the u or the v to = 0 based on the direction you're viewing from
                for (int v = vMin + 1; v < vMax; v++) {
                    ChunkPos chunkPos = new ChunkPos(
                            new BlockPos(targetPos.getX() + (u * 16), targetPos.getY(), targetPos.getZ() + (v * 16)));
                    level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, true); // Force load chunk
                    LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
                    LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));

                    BlockPos relTargetPos = new BlockPos(targetPos.getX() % 16, targetPos.getY() % 16, targetPos.getZ() % 16);

                    for (int y = 0; y < 16; y++) {
                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) {
                                BlockState state = section.getBlockState(x, y, z);
                                FluidState fluidState = section.getFluidState(x, y, z);

                                if (!state.isAir()) {
                                    BlockPos pos = new BlockPos(
                                            x + (u * 16) - relTargetPos.getX(),
                                            y - relTargetPos.getY(),
                                            z + (v * 16) - relTargetPos.getZ()
                                    );
                                    //
                                    // if(level.getBlockEntity(BlockUtils.fromChunkAndLocal(chunkPos, pos)
                                    //                                            .atY(targetPos.getY())) != null) {
                                    //                                        BlockEntity entity =
                                    // level.getBlockEntity(BlockUtils.fromChunkAndLocal(chunkPos, pos)
                                    //                                                .atY(targetPos.getY()));
                                    //                                        containers.add(new
                                    // BotiChunkContainer(level,
                                    //                                                state,
                                    //                                                pos,
                                    //                                                BlockUtils.getPackedLight(
                                    //                                                        level,
                                    //
                                    // BlockUtils.fromChunkAndLocal(chunkPos, pos)
                                    //
                                    // .atY(targetPos.getY())), true, entity.saveWithFullMetadata()));
                                    //                                    }

                                    if (fluidState.isEmpty())
                                        containers.add(new BotiChunkContainer(
                                                level,
                                                state,
                                                pos,
                                                BlockUtils.getPackedLight(
                                                        level,
                                                        BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
                                                                .atY(targetPos.getY()))));
                                    else
                                        containers.add(new BotiChunkContainer(
                                                level,
                                                state,
                                                fluidState,
                                                pos,
                                                BlockUtils.getPackedLight(
                                                        level,
                                                        BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
                                                                .atY(targetPos.getY()))));
                                }
                                if (containers.size() >= maxBlocks) {
                                    containerLists.add((List<BotiChunkContainer>) containers.clone());
                                    containers.clear();
                                }
                            }
                        }
                    }
                }
            }
            if (!containers.isEmpty()) {
                containerLists.add((List<BotiChunkContainer>) containers.clone());
                containers.clear();
            }

            for (int i = 0; i < containerLists.size(); i++) {
                Networking.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new PortalChunkDataPacketS2C(portalPos, containerLists.get(i), i, containerLists.size()));
            }
            // 126142 (Too big)
            // 71267 (prob could go higher before hitting the limit but this works at 6-ish chunks)

        } catch (Exception e) {
            TTSMod.LOGGER.error("Exception in packet construction: {}", e.getMessage());
        }
    }
}