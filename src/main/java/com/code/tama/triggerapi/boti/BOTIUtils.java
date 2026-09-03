/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.config.TTSConfig;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.mixin.BlockAccessor;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.client.BotiPortalModel;
import com.code.tama.triggerapi.boti.client.FluidQuadCollector;
import com.code.tama.triggerapi.boti.client.OccupancyGrid;
import com.code.tama.triggerapi.boti.packets.C2S.PortalChunkRequestPacketC2S;
import com.code.tama.triggerapi.helpers.rendering.StencilUtils;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class BOTIUtils {
	public static Map<AbstractPortalTile, Boolean> isUpdating = new HashMap<>();
	public static final ModelPart BOTIModel = BuildBOTIModel();

	/**
	 * Per-tile occupancy data covering the FULL gathered volume (including culled
	 * interior blocks), used for AO occlusion sampling. Populate this from whatever
	 * packet handler receives the occupancy payload from ChunkGatheringThread --
	 * see the TODO near computeQuadAO's call site.
	 */
	public static Map<AbstractPortalTile, OccupancyGrid> occupancyGrids = new HashMap<>();

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
		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
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
			if (!(isUpdating.containsKey(portal) && isUpdating.get(portal))) {
				isUpdating.put(portal, true);
				BOTIUtils.updateChunkModel(portal); // Get this going so it properly syncs
				if (!portal.containers.isEmpty()) {
					isUpdating.put(portal, false);
					portal.MODEL_VBO = BOTIUtils.buildModelVBO(portal.containers, portal); // Build VBO so it's not null
				}
			}
		} else {
			pose.pushPose();

			var mc = Minecraft.getInstance();
			var terDispatcher = mc.getBlockEntityRenderDispatcher();

			minecraft.level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
				pose.translate(-0.5f, 0.5f, 0.5f);
				// pose.scale(0.2f, 0.2f, 0.2f);
				pose.mulPose(Axis.YP.rotationDegrees(cap.GetNavigationalData().getFacing().toYRot()));
			});

			RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);

			portal.MODEL_VBO.bind();
			portal.MODEL_VBO.drawWithShader(pose.last().pose(), RenderSystem.getProjectionMatrix(),
					Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();

			// After VBO draw, before TE rendering
			RenderSystem.setProjectionMatrix(RenderSystem.getProjectionMatrix(), VertexSorting.ORTHOGRAPHIC_Z);

			portal.blockEntities.forEach((pos, be) -> {
				pose.pushPose();

				pose.translate(pos.getX(), pos.getY(), pos.getZ());
				BlockEntityRenderer<BlockEntity> renderer = terDispatcher.getRenderer(be);
				if (renderer != null)
					renderer.render(be, mc.getPartialTick(), pose, mc.renderBuffers().bufferSource(), 0xf000f0, 0);
				pose.popPose();
			});

			var bufferSource = mc.renderBuffers().bufferSource();
			// Flush opaque types first, then translucent
			bufferSource.endBatch(RenderType.solid());
			bufferSource.endBatch(RenderType.cutout());
			bufferSource.endBatch(RenderType.cutoutMipped());
			bufferSource.endBatch(RenderType.translucent());
			bufferSource.endBatch(); // catch-all for any TE-specific render types
			pose.popPose();
		}
	}

	/**
	 * Computes per-vertex ambient occlusion for a quad by sampling the light of the
	 * 3 blocks surrounding each corner (face-adjacent, edge-adjacent, corner).
	 * Returns 4 floats (0-1), one per vertex.
	 *
	 * @param occupancy
	 *            full-volume occlusion lookup (may be null if not yet wired up /
	 *            not received for this tile -- falls back to sparse
	 *            container-map-only sampling, which will look flatter).
	 */
	private static float[] computeQuadAO(BakedQuad quad, BlockPos pos, int centerLight,
			Map<BlockPos, BotiBlockContainer> map, OccupancyGrid occupancy) {

		Direction face = quad.getDirection();
		float[] result = new float[4];

		// The 4 corners of this face, relative to block pos
		// We need to sample the 3 neighbours at each corner for AO
		int[][] corners = getFaceCornerOffsets(face);

		for (int v = 0; v < 4; v++) {
			int[] c = corners[v];
			// The three neighbours that affect this corner's AO:
			// side1 (along u), side2 (along v), and the diagonal corner
			BlockPos n1 = pos.offset(face.getStepX(), face.getStepY(), face.getStepZ()).offset(c[0], c[1], 0);
			BlockPos n2 = pos.offset(face.getStepX(), face.getStepY(), face.getStepZ()).offset(0, c[2], c[3]);
			BlockPos nc = pos.offset(face.getStepX(), face.getStepY(), face.getStepZ()).offset(c[0], c[1] + c[2], c[3]);

			float l1 = getLightAt(n1, centerLight, map, occupancy);
			float l2 = getLightAt(n2, centerLight, map, occupancy);
			float lc = getLightAt(nc, centerLight, map, occupancy);

			// Average the corner + its two edge neighbours
			result[v] = (getLightAt(pos, centerLight, map, occupancy) + l1 + l2 + lc) / 4f / 15f;
		}
		return result;
	}

	/**
	 * Resolves an effective light value (0-15) for AO purposes at the given
	 * (relative) position. <br />
	 * <br />
	 * Priority: 1. We have a rendered container here -> use its actual light (or
	 * full bright if it's air). 2. No container, but the occupancy grid says this
	 * cell was solid in the original gathered volume -> it's an occluded interior
	 * block that got culled from rendering; treat it as dark so it still casts
	 * contact shadow onto the exposed face next to it. 3. No container, occupancy
	 * says not solid (or unknown / out of bounds, e.g. outside the gathered volume
	 * entirely) -> treat as open air / fully lit rather than inheriting the center
	 * block's own value, which is what was flattening every face before.
	 */
	private static float getLightAt(BlockPos p, int fallback, Map<BlockPos, BotiBlockContainer> map,
			OccupancyGrid occupancy) {
		BotiBlockContainer c = map.get(p);
		if (c != null) {
			return c.getState().isAir() ? 15f : c.getLight();
		}
		if (occupancy != null && occupancy.isInBounds(p) && occupancy.isSolid(p)) {
			return 4f; // occluded interior block we didn't render, still darkens the corner
		}
		return 15f; // genuinely open / unknown -> don't inherit the center block's own light
	}

	// Corner offsets for each face, (u1, v1, u2, v2) per vertex
	private static int[][] getFaceCornerOffsets(Direction face) {
		return switch (face) {
			case UP -> new int[][]{{-1, 0, 0, -1}, {1, 0, 0, -1}, {1, 0, 0, 1}, {-1, 0, 0, 1}};
			case DOWN -> new int[][]{{-1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, -1}, {-1, 0, 0, -1}};
			case NORTH -> new int[][]{{1, 0, 0, -1}, {-1, 0, 0, -1}, {-1, 0, 1, 0}, {1, 0, 1, 0}};
			case SOUTH -> new int[][]{{-1, 0, 0, -1}, {1, 0, 0, -1}, {1, 0, 1, 0}, {-1, 0, 1, 0}};
			case WEST -> new int[][]{{0, -1, 0, -1}, {0, 1, 0, -1}, {0, 1, 0, 1}, {0, -1, 0, 1}};
			case EAST -> new int[][]{{0, -1, 0, 1}, {0, 1, 0, 1}, {0, 1, 0, -1}, {0, -1, 0, -1}};
		};
	}

	public static VertexBuffer buildModelVBO(List<BotiBlockContainer> containers, AbstractPortalTile tile) {
		Minecraft mc = Minecraft.getInstance();

		int ChunksToRender = 8;
		float yaw = tile.targetY;
		Direction direction = Direction.fromYRot(yaw);

		BufferBuilder buffer = new BufferBuilder((int) (ChunksToRender * Math.pow(16, 3)));
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);

		// Dump all quads into the buffer
		PoseStack stack = new PoseStack();

		Map<BlockPos, BotiBlockContainer> chunkMap = getMapFromContainerList(containers);
		OccupancyGrid occupancy = occupancyGrids.get(tile); // null-safe; see TODO on the field above

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
					// Use ZERO so renderLiquid emits 0-1 relative vertices
					// and collector subtracts ZERO (no-op), keeping them 0-1 relative
					FluidQuadCollector fluidCollector = new FluidQuadCollector(BlockPos.ZERO);

					assert Minecraft.getInstance().level != null;
					Minecraft.getInstance().getBlockRenderer().renderLiquid(BlockPos.ZERO, // world pos for neighbor
							// sampling, wrong dim but
							// geometry shape is all we
							// need
							Minecraft.getInstance().level, fluidCollector, container.getState(), fluidState);

					// Vertices are 0-1 relative; the pose stack (translated to `pos` above)
					// places them -- do NOT also add pos.getX()/Y()/Z() manually here, or
					// every block ends up offset by its own position twice ("exploded" VBO).
					for (FluidQuadCollector.FluidVertex v : fluidCollector.getVertices()) {
						buffer.vertex(stack.last().pose(), v.x, v.y, v.z).color(v.r, v.g, v.b, v.a).uv(v.u, v.v)
								.uv2(v.light).endVertex();
					}
				}
			}

			// In buildModelVBO, replace the quad loop:
			for (BakedQuad quad : getModelFromBlock(container.getState(), pos, rand, chunkMap, direction)) {
				float qr, qg, qb;
				if (quad.isTinted()) {
					qr = r;
					qg = g;
					qb = b;
				} else {
					qr = 1f;
					qg = 1f;
					qb = 1f;
				}

				float shade = switch (quad.getDirection()) {
					case DOWN -> 0.5f;
					case UP -> 1.0f;
					case NORTH, SOUTH -> 0.8f;
					case EAST, WEST -> 0.6f;
				};
				qr *= shade;
				qg *= shade;
				qb *= shade;

				// Per-vertex smooth lighting instead of flat per-block.
				// occupancy (full pre-culling solid volume) gives this real contrast to
				// work with -- without it every corner sample falls back to the same
				// center-block value and the face reads as flat / fullbright.
				float[] ao = computeQuadAO(quad, pos, container.getLight(), chunkMap, occupancy);

				// Real world light (sky/block), independent of AO -- this drives the
				// lightmap (uv2) channel only. AO itself is carried purely in vertex
				// color below so it interpolates smoothly instead of quantizing to
				// 16 discrete lightmap steps (which would band once there's real
				// contrast to show).
				int worldLight = Math.max(container.getLight(), 4);
				int lightmap = (worldLight << 20) | (worldLight << 4);

				int[] vertices = quad.getVertices();
				// BakedQuad vertex format: x,y,z,color,u,v,lightmap,normal, 8 ints per vertex
				for (int v = 0; v < 4; v++) {
					int base = v * 8;
					float vx = Float.intBitsToFloat(vertices[base]);
					float vy = Float.intBitsToFloat(vertices[base + 1]);
					float vz = Float.intBitsToFloat(vertices[base + 2]);
					float vu = Float.intBitsToFloat(vertices[base + 4]);
					float vv = Float.intBitsToFloat(vertices[base + 5]);

					float vertLight = ao[v];
					float fr = qr * vertLight;
					float fg = qg * vertLight;
					float fb = qb * vertLight;

					// Local (block-relative) coords only -- the pose stack already carries
					// this block's translation, so adding pos.getX()/Y()/Z() again here
					// would double the offset (that was the "exploded" VBO bug).
					buffer.vertex(stack.last().pose(), vx, vy, vz).color(fr, fg, fb, 1.0f).uv(vu, vv)
							.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmap).endVertex();
				}
			}

			stack.popPose();
		});

		BufferBuilder.RenderedBuffer rendered = buffer.end();
		TTSMod.LOGGER.debug("[BOTI VBO] Built VBO with {} containers, buffer vertex count: {}", containers.size(),
				rendered.drawState().vertexCount());

		VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
		vbo.bind();
		vbo.upload(rendered);
		VertexBuffer.unbind();

		return vbo;
	}

	public static Map<BlockPos, BotiBlockContainer> getMapFromContainerList(List<BotiBlockContainer> list) {
		Map<BlockPos, BotiBlockContainer> map = new HashMap<>(list.size());
		for (BotiBlockContainer container : list) {
			map.put(container.getPos(), container);
		}
		return map;
	}

	public static List<BakedQuad> getModelFromBlock(BlockState state, BlockPos pos, RandomSource rand,
			Map<BlockPos, BotiBlockContainer> map, Direction viewingFrom) {
		BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
		BakedModel model = blockRenderer.getBlockModel(state);
		List<BakedQuad> quads = new java.util.ArrayList<>();
		quads.addAll(model.getQuads(state, null, rand));
		TTSMod.LOGGER.debug("[BOTI] block {} null-face quads: {}", state, quads.size());

		for (Direction dir : Direction.values()) {
			if (viewingFrom != null && dir.equals(viewingFrom.getOpposite()))
				continue;

			BlockPos neighbourPos = pos.relative(dir);
			BotiBlockContainer neighborContainer = map.get(neighbourPos);
			if (neighborContainer != null) {
				if (BOTIUtils.shouldRenderFace(state, neighborContainer.getState(), Minecraft.getInstance().level, pos,
						dir, neighbourPos)) {
					quads.addAll(model.getQuads(state, dir, rand));
				}
			} else {
				quads.addAll(model.getQuads(state, dir, rand));
			}
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