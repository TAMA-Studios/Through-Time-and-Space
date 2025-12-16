/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import com.code.tama.triggerapi.boti.client.BotiChunkContainer;
import com.code.tama.triggerapi.boti.client.BotiPortalModel;
import com.code.tama.triggerapi.boti.client.FluidQuadCollector;
import com.code.tama.triggerapi.boti.packets.C2S.PortalChunkRequestPacketC2S;
import com.code.tama.triggerapi.helpers.rendering.StencilUtils;
import com.code.tama.tts.TTSConfig;
import com.code.tama.tts.mixin.BlockAccessor;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.networking.Networking;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class BOTIUtils {
	public static final ModelPart BOTIModel = BuildBOTIModel();

	private static ModelPart BuildBOTIModel() {
		return BotiPortalModel.createBodyLayer().bakeRoot();
	}

	public static void GatherChunkData(AbstractPortalTile portalTile, Level level, int chunks) {
		if (!TTSConfig.ServerConfig.BOTI_ENABLED.get())
			return;
		BlockPos targetPos = portalTile.getTargetPos();
		new ChunkGatheringThread(chunks, (ServerLevel) level, portalTile, targetPos).start();
	}

	public static void RenderMinimal(PoseStack pose, AbstractPortalTile portal) {
		if (!TTSConfig.ClientConfig.BOTI_ENABLED.get())
			return;
		Minecraft mc = Minecraft.getInstance();
		assert mc.level != null;
		mc.level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
			pose.pushPose();
			portal.getFBOContainer().Render(pose, (stack, botiSource) -> StencilUtils.drawFrame(stack, 1, 2),
					(stack, buff) -> {
					}, (stack, botiSource) -> BOTIUtils.RenderScene(stack, portal));
			pose.popPose();
		});
	}

	public static void RenderScene(PoseStack pose, AbstractPortalTile portal) {
		if (!TTSConfig.ClientConfig.BOTI_ENABLED.get())
			return;
		RenderSystem.enableDepthTest();
		Minecraft minecraft = Minecraft.getInstance();

		assert minecraft.level != null;
		long currentTime = minecraft.level.getGameTime();

		if (currentTime - portal.lastUpdateTime >= 1200) { // update model every 1200 ticks, or a minute TODO: make
															// configurable! also make
			// only on
			// chunk update!
			BOTIUtils.updateChunkModel(portal);
			portal.lastUpdateTime = currentTime;
		}

		if (portal.MODEL_VBO == null) { // It'll be null the first time it's accessed, forcing a build
			portal.MODEL_VBO = BOTIUtils.buildModelVBO(portal.containers, portal); // Build VBO so it's not null
			BOTIUtils.updateChunkModel(portal); // Get this going so it properly syncs
		} else {
			pose.pushPose();

			minecraft.level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
				pose.translate(-0.5, 2, 0);
				pose.scale(0.2f, 0.2f, 0.2f);
				pose.mulPose(Axis.YP.rotationDegrees(cap.GetNavigationalData().getFacing().toYRot()));
			});

			RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);

			portal.MODEL_VBO.bind();
			portal.MODEL_VBO.drawWithShader(pose.last().pose(), RenderSystem.getProjectionMatrix(),
					Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();

			pose.popPose();
		}
	}

	public static VertexBuffer buildModelVBO(List<BotiChunkContainer> containers, AbstractPortalTile tile) {
		Minecraft mc = Minecraft.getInstance();

		int ChunksToRender = 8;

		BufferBuilder buffer = new BufferBuilder((int) (ChunksToRender * Math.pow(16, 3)));
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);

		// Dump all quads into the buffer
		PoseStack stack = new PoseStack();

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
					Minecraft.getInstance().getBlockRenderer().renderLiquid(pos, Minecraft.getInstance().level,
							fluidCollector, container.getState(), fluidState);

					// Now feed collector.getVertices() into VBO
					for (FluidQuadCollector.FluidVertex v : fluidCollector.getVertices()) {
						buffer.vertex(v.x, v.y, v.z).color(v.r, v.g, v.b, v.a).uv(v.u, v.v).uv2(container.getLight())
								.endVertex();
					}
				}
			}

			for (BakedQuad quad : getModelFromBlock(container.getState(), pos, rand, chunkMap)) {
				// Convert packed light into brightness factor (0.0–1.0)
				float brightness = (float) (container.getLight() / 0xf000f0);

				// Apply brightness to base RGB values
				float rLit = r; // *= brightness;
				float gLit = g; // *= brightness;
				float bLit = b; // *= brightness;

				buffer.putBulkData(stack.last(), quad, rLit, gLit, bLit, 1.0F, container.getLight(),
						OverlayTexture.NO_OVERLAY, true);
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

	public static Map<BlockPos, BotiChunkContainer> getMapFromContainerList(List<BotiChunkContainer> list) {
		Map<BlockPos, BotiChunkContainer> map = new HashMap<>(list.size());
		for (BotiChunkContainer container : list) {
			map.put(container.getPos(), container);
		}
		return map;
	}

	public static List<BakedQuad> getModelFromBlock(BlockState state, BlockPos pos, RandomSource rand,
			Map<BlockPos, BotiChunkContainer> map) {
		BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
		Direction[] directions = Direction.values();
		BakedModel model = blockRenderer.getBlockModel(state);
		List<BakedQuad> quads = new java.util.ArrayList<>();
		// render only non-occluded faces
		for (Direction dir : directions) {
			BlockPos neighbourPos = pos.relative(dir);
			BotiChunkContainer neighborContainer = map.get(neighbourPos);
			if (neighborContainer != null) {
				if (BOTIUtils.shouldRenderFace(state, neighborContainer.getState(), Minecraft.getInstance().level, pos,
						dir, neighbourPos))
					quads.addAll(model.getQuads(state, dir, rand));
			} else
				quads.addAll(model.getQuads(state, dir, rand));
		}
		return quads;
	}

	public static boolean isSideVisibleFrom(BlockPos from, BlockPos to, Direction side) {
		// Get center points for both blocks
		Vec3 fromCenter = new Vec3(from.getX() + 0.5, from.getY() + 0.5, from.getZ() + 0.5);
		Vec3 toCenter = new Vec3(to.getX() + 0.5, to.getY() + 0.5, to.getZ() + 0.5);

		// Vector from target to source
		Vec3 toFrom = fromCenter.subtract(toCenter).normalize();

		// Direction vector of the face
		Vec3 faceNormal = new Vec3(side.getStepX(), side.getStepY(), side.getStepZ());

		// Dot product < 0 means the face is pointing toward the source
		double dot = toFrom.dot(faceNormal);
		return dot < 0;
	}

	public static boolean shouldRenderFace(BlockState state, BlockState neighbor, BlockGetter level, BlockPos pos,
			Direction dir, BlockPos secondPos) {
		if (state.skipRendering(neighbor, dir)) {
			return false;
		} else if (state.supportsExternalFaceHiding()
				&& neighbor.hidesNeighborFace(level, secondPos, state, dir.getOpposite())) {
			return false;
		} else if (neighbor.canOcclude()) {
			Block.BlockStatePairKey block$blockstatepairkey = new Block.BlockStatePairKey(state, neighbor, dir);
			Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> object2bytelinkedopenhashmap = BlockAccessor
					.getOcclusionCache().get();
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

	public static void updateChunkModel(AbstractPortalTile tileEntity) {
		if (!TTSConfig.ClientConfig.BOTI_ENABLED.get())
			return;
		assert Minecraft.getInstance().level != null;
		if (!Minecraft.getInstance().level.isClientSide())
			return;
		tileEntity.containers.clear();
		tileEntity.blockEntities.clear();

		long currentTime = Minecraft.getInstance().level.getGameTime();

		if (tileEntity.targetLevel != null)
			Networking.INSTANCE
					.sendToServer(new PortalChunkRequestPacketC2S(tileEntity.getBlockPos(), tileEntity.getTargetLevel(),
							tileEntity.getTargetPos(), TTSConfig.ClientConfig.BOTI_RENDER_DISTANCE.get()));

		tileEntity.lastRequestTime = currentTime;
	}
}
