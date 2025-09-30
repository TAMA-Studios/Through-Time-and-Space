/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.portal.PortalChunkRequestPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalChunkDataPacketS2C;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class PortalTileEntityRenderer implements BlockEntityRenderer<PortalTileEntity> {
    private final BlockEntityRendererProvider.Context context;
    private float lastRenderTick = -1;
    private final Minecraft mc = Minecraft.getInstance();
    private VertexConsumer buffer;

    public PortalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public int getViewDistance() {
        return 24;
    }

    @Override
    public void render(
            @NotNull PortalTileEntity tileEntity,
            float partialTick,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        assert mc.level != null;
        if (!mc.level.isClientSide() || tileEntity.getTargetLevel() == null || tileEntity.getTargetPos() == null) {
            return;
        }

        long currentTime = mc.level.getGameTime();
        if (mc.getPartialTick() == lastRenderTick) {
            return; // Throttle to once per tick
        }
        lastRenderTick = mc.getPartialTick();

        poseStack.pushPose();

        if (currentTime - tileEntity.lastUpdateTime >= 10) {
            updateChunkModel(tileEntity);
            tileEntity.lastUpdateTime = currentTime;
        }

        if(this.buffer == null) {

        }


        poseStack.translate(-6, -10, -8);


        tileEntity.containers.forEach((container) -> {
            BlockPos pos = container.getPos();
            BlockState state = container.getState();
            int Light = container.getLight();

            poseStack.pushPose();
            poseStack.translate(
                    pos.getX(), pos.getY(), pos.getZ());

            BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
            // Get the BlockColors instance
            BlockColors blockColors = Minecraft.getInstance().getBlockColors();

            // Get the block color (for grass and shit) for the block state at the given position
            int color = blockColors.getColor(
                    state, Minecraft.getInstance().level, pos, 0);

            // Extract RGB components (normalize to 0-1 range)
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;
            Minecraft.getInstance()
                    .getBlockRenderer()
                    .getModelRenderer()
                    .renderModel(
                            poseStack.last(),
                            buffer.getBuffer(RenderType.translucent()),
                            state,
                            model,
                            r,
                            g,
                            b,
                            Light,
                            packedOverlay);

            poseStack.popPose();
        });



        if(false) // Disabled to age
        tileEntity.stateMap.forEach((pos, state) -> {
            poseStack.pushPose();
            poseStack.translate(
                    pos.getX(), pos.getY(), pos.getZ());

            BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
            // Get the BlockColors instance
            BlockColors blockColors = Minecraft.getInstance().getBlockColors();

            // Get the block color (for grass and shit) for the block state at the given position
            int color = blockColors.getColor(
                    state, Minecraft.getInstance().level, pos, 0);

            // Extract RGB components (normalize to 0-1 range)
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;
            Minecraft.getInstance()
                    .getBlockRenderer()
                    .getModelRenderer()
                    .renderModel(
                            poseStack.last(),
                            buffer.getBuffer(RenderType.translucent()),
                            state,
                            model,
                            r,
                            g,
                            b,
                            packedLight,
                            packedOverlay);

            poseStack.popPose();
        });



        if(false) // Rest is disabled till I can get a VBO in
        if (tileEntity.stateMap != null) { // This one goes ahead and makes a shit ton of BakedModels and renders em all, no good currently (cause no VBO) but potential to be good
            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            ModelBlockRenderer modelRenderer = blockRenderer.getModelRenderer();
            BlockColors blockColors = Minecraft.getInstance().getBlockColors();

            RandomSource rand = tileEntity.getLevel().random;
            Direction[] directions = Direction.values();

            tileEntity.stateMap.forEach((pos, state) -> {
                poseStack.pushPose();
                poseStack.translate(pos.getX(), pos.getY(), pos.getZ());

                BakedModel model = blockRenderer.getBlockModel(state);

                int color = blockColors.getColor(state, Minecraft.getInstance().level, pos, 0);
                float r = ((color >> 16) & 0xFF) / 255.0f;
                float g = ((color >> 8) & 0xFF) / 255.0f;
                float b = (color & 0xFF) / 255.0f;

                VertexConsumer vc = buffer.getBuffer(RenderType.translucent());

                List<BakedQuad> quads = new java.util.ArrayList<>(List.of());
                // render only non-occluded faces
                for (Direction dir : directions) {
                    BlockPos neighbourPos = pos.relative(dir);
                    BlockState neighbourState = tileEntity.stateMap.get(neighbourPos);
                    boolean occluded = neighbourState != null &&
                            neighbourState.isSolidRender(Minecraft.getInstance().level, neighbourPos);

                    if (!occluded) {
                        // pull just this side’s quads and render them
                        quads.addAll(model.getQuads(state, dir, rand));
                    }
                }

                BakedModel model1 = new BakedModel() {
                    @Override
                    public @NotNull ItemOverrides getOverrides() {
                        return ItemOverrides.EMPTY;
                    }

                    @SuppressWarnings("deprecation")
                    @Override
                    public @NotNull TextureAtlasSprite getParticleIcon() {
                        return mc.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                                .apply(new ResourceLocation("minecraft", "stone"));
                    }

                    @Override
                    public @NotNull List<BakedQuad> getQuads(
                            @Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand) {
                        return quads;
                    }

                    @Override
                    public boolean isCustomRenderer() {
                        return false;
                    }

                    @Override
                    public boolean isGui3d() {
                        return true;
                    }

                    @Override
                    public boolean useAmbientOcclusion() {
                        return true;
                    }

                    @Override
                    public boolean usesBlockLight() {
                        return true;
                    }
                };

                modelRenderer.renderModel(
                        poseStack.last(),
                        vc,
                        null,
                        model1,
                        r, g, b,
                        packedLight,
                        packedOverlay);

                poseStack.popPose();
            });

        }

        if(false) // This one renders the 5 billion BakedModels made in the Packet, no good on performance
        if (tileEntity.chunkModels != null) {
            poseStack.pushPose();
            poseStack.translate(-8, -8, -8);
            VertexConsumer chunkConsumer = buffer.getBuffer(RenderType.cutout());
            tileEntity.chunkModels.forEach((bakedModel, color) -> {
                float r = ((color >> 16) & 0xFF) / 255.0f;
                float g = ((color >> 8) & 0xFF) / 255.0f;
                float b = (color & 0xFF) / 255.0f;

                context.getBlockRenderDispatcher()
                        .getModelRenderer()
                        .renderModel(
                                poseStack.last(),
                                chunkConsumer,
                                null,
                                bakedModel,
                                r,
                                g,
                                b,
                                packedLight,
                                packedOverlay);

            });
            poseStack.popPose();
        }

        // Render block entities
        for (Map.Entry<BlockPos, BlockEntity> entry : tileEntity.blockEntities.entrySet()) {
            BlockPos offsetPos = entry.getKey();
            BlockEntity be = entry.getValue();
            BlockEntityRenderer<BlockEntity> renderer =
                    mc.getBlockEntityRenderDispatcher().getRenderer(be);
            if (renderer != null) {
                poseStack.pushPose();
                poseStack.translate(offsetPos.getX() - 8, offsetPos.getY() - 8, offsetPos.getZ() - 8);
                renderer.render(be, partialTick, poseStack, buffer, packedLight, packedOverlay);
                poseStack.popPose();
            } else {
                System.out.println("No renderer found for block entity " + be + " at " + offsetPos);
            }
        }

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull PortalTileEntity tileEntity) {
        return true;
    }


    private void updateChunkModel(PortalTileEntity tileEntity) {
        assert mc.level != null;
        if (!mc.level.isClientSide()) return;

        long currentTime = mc.level.getGameTime();
        if (tileEntity.lastRequestTime == 0 || currentTime - tileEntity.lastRequestTime >= 10) {
            Level targetLevel = mc.level.dimension() == tileEntity.getTargetLevel() ? mc.level : null;
            if (targetLevel != null) {
                ChunkPos chunkPos = new ChunkPos(tileEntity.getTargetPos());
                LevelChunk chunk = targetLevel.getChunk(chunkPos.x, chunkPos.z);
                tileEntity.updateChunkModelFromServer(
                        new PortalChunkDataPacketS2C(tileEntity.getBlockPos(), chunk, tileEntity.getTargetPos())
                                .chunkData);
            } else {
                Networking.INSTANCE.sendToServer(new PortalChunkRequestPacketC2S(
                        tileEntity.getBlockPos(), tileEntity.getTargetLevel(), tileEntity.getTargetPos()));
            }
            tileEntity.lastRequestTime = currentTime;
        }
    }
}
