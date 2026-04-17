/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.config.TTSConfig;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.mixin.client.IMinecraftAccessor;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexBuffer;
import lombok.Getter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.packets.S2C.PortalSyncPacketS2C;
import com.code.tama.triggerapi.boti.teleporting.SeamlessTeleport;
import com.code.tama.triggerapi.helpers.rendering.FBOHelper;
import com.code.tama.triggerapi.tileEntities.TickingTile;

/** Other tiles implement this to get data for portals */
public abstract class AbstractPortalTile extends TickingTile {

    @OnlyIn(Dist.CLIENT)
    volatile boolean tts$fakeLevelTornDown = false;

	@Getter
	LevelRenderer fakeRenderer = null;
	@Getter
	ClientLevel fakeLevel = null;

	@OnlyIn(Dist.CLIENT)
	private FBOHelper FBOContainer;

	long fuckYouTimer = 0;

	private final List<Integer> recievedPackets = new ArrayList<>();

	@OnlyIn(Dist.CLIENT)
	public VertexBuffer MODEL_VBO;

	public Vec3 SkyColor = Vec3.ZERO;

	@OnlyIn(Dist.CLIENT)
	public Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();

	@OnlyIn(Dist.CLIENT)
	public Map<BakedModel, Integer> chunkModels = new HashMap<>();

	@OnlyIn(Dist.CLIENT)
	public List<BotiBlockContainer> containers = new ArrayList<>();

	public ResourceKey<DimensionType> dimensionTypeId;

	public long lastRequestTime = 0;
	public long lastUpdateTime = 0;

	@Getter
	public ResourceKey<Level> targetLevel;

	@Getter
	public BlockPos targetPos = new BlockPos(0, 128, 0);

	public float targetY = 0;

	public DimensionType type;

	private static final double PREPARE_RADIUS_SQ = 16.0 * 16.0;
	private static final int PREPARE_CHECK_INTERVAL = 10;
	private int prepareCheckTimer = 0;

	public AbstractPortalTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	public FBOHelper getFBOContainer() {
		return this.FBOContainer == null ? this.FBOContainer = new FBOHelper() : this.FBOContainer;
	}

	public void setTargetLevel(ResourceKey<Level> levelKey, BlockPos targetPos, float yRot, boolean markDirty) {
		if (this.level == null)
			return;
		this.targetLevel = levelKey;
		this.targetPos = targetPos;
		this.type = ServerLifecycleHooks.getCurrentServer().getLevel(levelKey).dimensionType();
		this.dimensionTypeId = ServerLifecycleHooks.getCurrentServer().getLevel(levelKey).dimensionTypeId();
		this.targetY = yRot;

		chunkModels.clear();
		blockEntities.clear();

		if (markDirty && !level.isClientSide()) {
			setChanged();
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
			Networking.INSTANCE.send(PacketDistributor.DIMENSION.with(() -> this.level.dimension()),
					new PortalSyncPacketS2C(worldPosition, targetLevel, type, targetPos, dimensionTypeId, targetY));
		}
	}

	@Override
	public void tick() {
		fuckYouTimer++;

		if (fuckYouTimer >= 1200) {
			fuckYouTimer = 0;
			this.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
					.ifPresent(cap -> this.setTargetLevel(cap.GetCurrentLevel(),
							cap.GetNavigationalData().GetExteriorLocation().GetBlockPos(), targetY, true));
			return;
		}

		updateFakeLevel();

		if (this.targetLevel != null) {
			tickProximityPrepare();
			return;
		}

		assert this.getLevel() != null;
		if (this.getLevel().isClientSide) {
			if (!TTSConfig.ClientConfig.BOTI_ENABLED.get())
				return;
		}

		if (!this.getLevel().isClientSide && !TTSConfig.ServerConfig.BOTI_ENABLED.get())
			return;

		this.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
				.ifPresent(cap -> this.setTargetLevel(cap.GetCurrentLevel(),
						cap.GetNavigationalData().GetExteriorLocation().GetBlockPos(), targetY, true));
	}

	private void tickProximityPrepare() {
		if (this.level == null || this.level.isClientSide())
			return;
		if (this.targetLevel == null)
			return;

		prepareCheckTimer++;
		if (prepareCheckTimer < PREPARE_CHECK_INTERVAL)
			return;
		prepareCheckTimer = 0;

		ServerLevel serverLevel = (ServerLevel) this.level;
		ServerLevel destLevel = serverLevel.getServer().getLevel(this.targetLevel);
		if (destLevel == null)
			return;

		AABB searchBox = new AABB(worldPosition).inflate(Math.sqrt(PREPARE_RADIUS_SQ));
		List<ServerPlayer> nearbyPlayers = serverLevel.getEntitiesOfClass(ServerPlayer.class, searchBox);
		Vec3 portalCenter = Vec3.atCenterOf(worldPosition);

		for (ServerPlayer player : nearbyPlayers) {
			if (player.distanceToSqr(portalCenter) <= PREPARE_RADIUS_SQ) {
				SeamlessTeleport.prepare(player, destLevel, this.targetPos, this.targetY);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateChunkDataFromServer(List<BotiBlockContainer> chunkData, int packetIndex, int totalPackets) {
		if (!TTSConfig.ClientConfig.BOTI_ENABLED.get())
			return;
		if (packetIndex > totalPackets || this.recievedPackets.contains(packetIndex)) {
			TTSMod.LOGGER.warn("Portal tile received packet not meant for it, or it's updating too quickly... ruh roh");
			return;
		} else {
			recievedPackets.add(packetIndex);
		}

		chunkData.forEach(container -> {
			if (container.isIsTile()) {
				BlockEntity entity = BlockEntity.loadStatic(container.getPos(), container.getState(),
						container.getEntityTag());
				blockEntities.put(container.getPos(), entity);
				containers.remove(container);
			}
		});
		containers.addAll(chunkData);

		if (recievedPackets.size() >= totalPackets) {
			this.recievedPackets.clear();
			this.MODEL_VBO = BOTIUtils.buildModelVBO(this.containers, this);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void updateFakeLevel() {
		if (this.level == null || !this.level.isClientSide) return;

		// fakeLevel already built for this dimension — just resample sky color cheaply.
		// Do NOT rebuild every tick; rebuilding creates a new LevelRenderer each time
		// which leaks GL resources and nukes any chunk data already loaded into it.
		if (fakeLevel != null && fakeLevel.dimension().equals(this.targetLevel)) {
			this.SkyColor = fakeLevel.getSkyColor(
					this.targetPos.getCenter(),
					((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick
			);
			return;
		}

		// targetLevel not known yet — nothing to build
		if (this.type == null || this.targetLevel == null) return;

		// Build fakeLevel + fakeRenderer once, on the render thread.
		// recordRenderCall enqueues onto the actual GL/render thread, which is
		// required for LevelRenderer construction and getSkyColor.
		RenderSystem.recordRenderCall(() -> {
			Minecraft mc = Minecraft.getInstance();
			if (mc.level == null || mc.player == null) return;

			// Guard again inside the lambda — targetLevel could have been cleared
			// between the outer check and this lambda executing.
			if (this.targetLevel == null || this.dimensionTypeId == null) return;

			// Tear down any previous fakeRenderer for a *different* dimension.
			// (Same-dimension case is caught by the early-return above.)
			if (fakeRenderer != null) {
				fakeRenderer.allChanged();
				fakeRenderer.close();
			}

			Holder<DimensionType> dimTypeHolder = mc.level.registryAccess()
					.registryOrThrow(Registries.DIMENSION_TYPE)
					.getHolderOrThrow(this.dimensionTypeId);

			fakeRenderer = new LevelRenderer(
					mc,
					mc.getEntityRenderDispatcher(),
					mc.getBlockEntityRenderDispatcher(),
					mc.renderBuffers()
			);

			fakeLevel = new ClientLevel(
					mc.player.connection,
					mc.level.getLevelData(),
					this.targetLevel,
					dimTypeHolder,
					mc.options.getEffectiveRenderDistance(),
					mc.options.getEffectiveRenderDistance(),
					mc.level.getProfilerSupplier(),
					fakeRenderer,
					false,
					0L
			);

			fakeRenderer.setLevel(fakeLevel);
			fakeRenderer.getChunkRenderDispatcher().uploadAllPendingUploads();
			// Sync time so sky angle matches target dim (irrelevant for nether/end
			// but correct for overworld↔overworld portals).
			fakeLevel.setGameTime(mc.level.getGameTime());
			fakeLevel.setDayTime(mc.level.getDayTime());

			// Prime the light engine for the chunk window — without this everything
			// in fakeLevel renders black regardless of packed light values.
			ChunkPos center = new ChunkPos(this.targetPos);
			int r = 2;
			for (int cx = -r; cx <= r; cx++) {
				for (int cz = -r; cz <= r; cz++) {
					fakeLevel.getLightEngine().setLightEnabled(
							new ChunkPos(center.x + cx, center.z + cz), true);
				}
			}

			this.SkyColor = fakeLevel.getSkyColor(
					this.targetPos.getCenter(),
					((IMinecraftAccessor) mc).getTimer().partialTick
			);

			// Register with the client registry so the chunk mixin knows where to
			// route incoming vanilla chunk packets for this portal.
			registerFakeLevel();
		});
	}

	@OnlyIn(Dist.CLIENT)
	public void registerFakeLevel() {
		ChunkPos center = new ChunkPos(this.targetPos);
		FakePortalLevelRegistry.register(new FakePortalLevelRegistry.FakePortalEntry(
				this.getBlockPos(),
				this.targetLevel,
				this.fakeLevel,
				this.fakeRenderer,
				center.x,
				center.z,
				2 // must match FAKE_LEVEL_CHUNK_RADIUS in PortalChunkRequestPacketC2S
		));
	}

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.level != null && this.level.isClientSide) {
            tts$fakeLevelTornDown = true;          // synchronous; render thread sees this immediately
            FakePortalLevelRegistry.unregister(this.getBlockPos());
            RenderSystem.recordRenderCall(() -> {
                if (fakeRenderer != null) {
                    fakeRenderer.close();
                    fakeRenderer = null;
                }
                fakeLevel = null;
            });
        }
    }
}