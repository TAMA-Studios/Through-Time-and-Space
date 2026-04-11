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
@OnlyIn(Dist.CLIENT)
public abstract class AbstractPortalTile extends TickingTile {
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

	/**
	 * Radius (in blocks) at which the tile starts pre-loading geometry for
	 * approaching players. Must be large enough that the gather finishes before the
	 * player reaches the portal. With a fast server a value of 16 is comfortable;
	 * increase if players can sprint toward the portal.
	 */
	private static final double PREPARE_RADIUS_SQ = 16.0 * 16.0;

	/**
	 * How often (in ticks) the proximity check runs. Running it every tick is
	 * wasteful; every 10 ticks (~0.5 s) is fine — players can't cover 16 blocks in
	 * half a second at walking speed.
	 */
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

		updateSkyColor();

		if (this.targetLevel != null) {
			// Target is known — run the proximity pre-load check on the server side.
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

	/**
	 * Server-side only. Checks whether any player is within
	 * {@link #PREPARE_RADIUS_SQ} blocks of this portal and, if so, triggers
	 * {@link SeamlessTeleport#prepare(ServerPlayer, ServerLevel, BlockPos, float)}
	 * for them.
	 *
	 * Runs at {@link #PREPARE_CHECK_INTERVAL}-tick intervals to keep overhead low.
	 */
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

		// Expand a search box around the portal block.
		AABB searchBox = new AABB(worldPosition).inflate(Math.sqrt(PREPARE_RADIUS_SQ));

		List<ServerPlayer> nearbyPlayers = serverLevel.getEntitiesOfClass(ServerPlayer.class, searchBox);

		Vec3 portalCenter = Vec3.atCenterOf(worldPosition);

		for (ServerPlayer player : nearbyPlayers) {
			if (player.distanceToSqr(portalCenter) <= PREPARE_RADIUS_SQ) {
				// Fire prepare(). it guards itself against double-invocation.
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

	// Drop-in replacement for the updateSkyColor() method in AbstractPortalTile.
	// RenderSystem.recordRenderCall() queues the lambda onto the actual render
	// thread (not just the main game thread), which is what getSkyColor() requires.

	@OnlyIn(Dist.CLIENT)
	public void updateSkyColor() {
		if (this.level == null || !this.level.isClientSide)
			return;

		// Only rebuild fakeLevel when dimension changes
		if (fakeLevel != null && fakeLevel.dimension().equals(this.targetLevel)) {
			// Just resample sky color each tick — cheap
			this.SkyColor = fakeLevel.getSkyColor(this.targetPos.getCenter(),
					((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
			return;
		}

		RenderSystem.recordRenderCall(() -> {
			if (this.type == null)
				return;

			Minecraft mc = Minecraft.getInstance();
			if (mc.level == null || mc.player == null)
				return;

			// Use the TARGET dimension's DimensionType, not the current one
			DimensionType targetDimType = mc.level.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
					.getHolderOrThrow(this.dimensionTypeId).value(); // <-- unwrap the Holder

			Holder<DimensionType> dimTypeHolder = mc.level.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
					.getHolderOrThrow(this.dimensionTypeId);

			fakeRenderer = new LevelRenderer(mc, mc.getEntityRenderDispatcher(), mc.getBlockEntityRenderDispatcher(),
					mc.renderBuffers());

			fakeLevel = new ClientLevel(mc.player.connection, mc.level.getLevelData(), // NOTE: see below
					this.targetLevel, dimTypeHolder, mc.options.getEffectiveRenderDistance(),
					mc.options.getEffectiveRenderDistance(), mc.level.getProfilerSupplier(), fakeRenderer, false, 0L);

			fakeRenderer.setLevel(fakeLevel);

			// Sync game time so sky angle matches target dim's time
			// For nether/end this doesn't matter, but for overworld it does
			fakeLevel.setGameTime(mc.level.getGameTime());
			fakeLevel.setDayTime(mc.level.getDayTime());

			this.SkyColor = fakeLevel.getSkyColor(this.targetPos.getCenter(),
					((IMinecraftAccessor) mc).getTimer().partialTick);

			registerFakeLevel();
		});
	}

	public void registerFakeLevel() {
		ChunkPos center = new ChunkPos(this.targetPos);
		FakePortalLevelRegistry.register(new FakePortalLevelRegistry.FakePortalEntry(this.getBlockPos(),
				this.targetLevel, this.fakeLevel, this.fakeRenderer, center.x, center.z, 2 // 5x5 chunk area, same
																							// radius you use when
																							// requesting chunks from
																							// server
		));
	}

	// In setRemoved()
	@Override
	public void setRemoved() {
		super.setRemoved();
		FakePortalLevelRegistry.unregister(this.getBlockPos());
	}
}