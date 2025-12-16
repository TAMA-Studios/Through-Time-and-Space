/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions;

import com.code.tama.triggerapi.ReflectionBuddy;
import com.code.tama.triggerapi.dimensions.packets.s2c.SyncDimensionsS2C;
import com.code.tama.triggerapi.dimensions.packets.s2c.UpdateDimensionsS2C;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.networking.Networking;
import com.google.common.collect.Lists;
import com.ibm.icu.impl.locale.XCldrStub.ImmutableSet;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryAccess.ImmutableRegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static com.code.tama.tts.TTSMod.MODID;

/**
 * TriggerAPI Dynamic Dimension Registration implementation */
@SuppressWarnings("deprecation")
public final class DimensionManager implements DimensionAPI {

	private static final Set<ResourceKey<Level>> VANILLA_LEVELS = Set.of(Level.OVERWORLD, Level.NETHER, Level.END);
	public static final DimensionManager INSTANCE = new DimensionManager();
	private static final Logger LOGGER = TTSMod.LOGGER;

	private Set<ResourceKey<Level>> levelsPendingUnregistration = new HashSet<>();

	private DimensionManager() {
	}

	/** ======================== Core Dimension Creation ======================== */

	@SuppressWarnings("deprecation")
	private static ServerLevel createAndRegisterLevel(MinecraftServer server, Map<ResourceKey<Level>, ServerLevel> map,
			ResourceKey<Level> levelKey, Supplier<LevelStem> dimensionFactory) {

		ServerLevel overworld = server.getLevel(Level.OVERWORLD);
		LevelStem dimension = dimensionFactory.get();
		ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registries.LEVEL_STEM, levelKey.location());

		ChunkProgressListener progressListener = ReflectionBuddy.MinecraftServerAccess.progressListenerFactory
				.apply(server).create(11);
		Executor executor = ReflectionBuddy.MinecraftServerAccess.executor.apply(server);
		var storageAccess = ReflectionBuddy.MinecraftServerAccess.storageSource.apply(server);
		DerivedLevelData derivedData = new DerivedLevelData(server.getWorldData(),
				server.getWorldData().overworldData());

		// Register dimension
		var dimensionRegistry = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
		if (dimensionRegistry instanceof MappedRegistry<LevelStem> writableRegistry) {
			writableRegistry.unfreeze();
			writableRegistry.register(dimensionKey, dimension, Lifecycle.stable());
		} else {
			throw new IllegalStateException("Dimension registry not writable: " + dimensionKey.location());
		}

		assert overworld != null;
		ServerLevel newLevel = new ServerLevel(server, executor, storageAccess, derivedData, levelKey, dimension,
				progressListener, server.getWorldData().isDebugWorld(), overworld.getSeed(), List.of(), false, null);

		// Add world border listener
		overworld.getWorldBorder()
				.addListener(new BorderChangeListener.DelegateBorderChangeListener(newLevel.getWorldBorder()));

		// Register level
		map.put(levelKey, newLevel);
		server.markWorldsDirty();

		// Fire event and prepare world
		MinecraftForge.EVENT_BUS.post(new LevelEvent.Load(newLevel));
		prepareWorld(progressListener, newLevel);

		// Sync with clients
		Networking.sendPacketToAll(new SyncDimensionsS2C(levelKey, true));

		return newLevel;
	}

	public static void prepareWorld(ChunkProgressListener chunkProgress, ServerLevel level) {
		LOGGER.info("Preparing dynamic dimension: {}", level.dimension().location());
		chunkProgress.updateSpawnPos(new ChunkPos(level.getSharedSpawnPos()));
		level.getChunkSource().addRegionTicket(TicketType.START, new ChunkPos(level.getSharedSpawnPos()), 11,
				Unit.INSTANCE);
	}

	public static LevelStem createLevelCopy(MinecraftServer server) {
		ServerLevel oldLevel = server.overworld();
		DynamicOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, server.registryAccess());
		ChunkGenerator newGen = ChunkGenerator.CODEC.encodeStart(ops, oldLevel.getChunkSource().getGenerator())
				.flatMap(nbt -> ChunkGenerator.CODEC.parse(ops, nbt)).getOrThrow(false, s -> {
					throw new CommandRuntimeException(Component.literal("Error copying dimension: " + s));
				});
		return new LevelStem(oldLevel.dimensionTypeRegistration(), newGen);
	}

	/** ======================== Dimension Removal ======================== */

	@SuppressWarnings("deprecation")
	private void unregisterScheduledDimensions(MinecraftServer server) {
		if (levelsPendingUnregistration.isEmpty())
			return;

		Set<ResourceKey<Level>> keysToRemove = levelsPendingUnregistration;
		levelsPendingUnregistration = new HashSet<>();

		Set<ResourceKey<Level>> removedKeys = new HashSet<>();
		ServerLevel overworld = server.getLevel(Level.OVERWORLD);

		var oldRegistry = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
		if (!(oldRegistry instanceof MappedRegistry<LevelStem> oldMappedRegistry))
			return;

		var layeredRegistryAccess = ReflectionBuddy.MinecraftServerAccess.registries.apply(server);
		var composite = ReflectionBuddy.LayeredRegistryAccessAccess.composite.apply(layeredRegistryAccess);
		if (!(composite instanceof ImmutableRegistryAccess))
			return;

		for (ResourceKey<Level> levelKey : keysToRemove) {
			@Nullable ServerLevel level = server.getLevel(levelKey);
			if (level == null)
				continue;

			UnregisterDimensionEvent event = new UnregisterDimensionEvent(level);
			if (MinecraftForge.EVENT_BUS.post(event))
				continue;

			@Nullable ServerLevel removedLevel = server.forgeGetWorldMap().remove(levelKey);
			if (removedLevel == null)
				continue;

			// Eject players
			for (ServerPlayer player : Lists.newArrayList(removedLevel.players())) {
				ResourceKey<Level> respawn = player.getRespawnDimension();
				if (keysToRemove.contains(respawn))
					respawn = Level.OVERWORLD;
				ServerLevel dest = server.getLevel(respawn != null ? respawn : Level.OVERWORLD);
				BlockPos pos = player.getRespawnPosition();
				if (pos == null) {
                    assert dest != null;
                    pos = dest.getSharedSpawnPos();
                }
                assert dest != null;
                player.teleportTo(dest, pos.getX(), pos.getY(), pos.getZ(), player.getRespawnAngle(), 0f);
			}

			removedLevel.save(null, false, removedLevel.noSave());
			MinecraftForge.EVENT_BUS.post(new LevelEvent.Unload(removedLevel));

			// Remove border listener
            assert overworld != null;
            overworld.getWorldBorder().removeListener(Objects.requireNonNull(ReflectionBuddy.WorldBorderAccess.listeners
                    .apply(overworld.getWorldBorder()).stream()
                    .filter(l -> l instanceof BorderChangeListener.DelegateBorderChangeListener delegate
                            && ReflectionBuddy.DelegateBorderChangeListenerAccess.worldBorder
                            .apply(delegate) == removedLevel.getWorldBorder())
                    .findFirst().orElse(null)));

			removedKeys.add(levelKey);
		}

		if (!removedKeys.isEmpty()) {
			// Rebuild registry with remaining dimensions
			MappedRegistry<LevelStem> newRegistry = new MappedRegistry<>(Registries.LEVEL_STEM,
					oldMappedRegistry.registryLifecycle());
			oldRegistry.entrySet().forEach(entry -> {
				ResourceKey<LevelStem> key = entry.getKey();
				ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, key.location());
				if (!removedKeys.contains(levelKey))
					newRegistry.register(key, entry.getValue(), oldRegistry.lifecycle(entry.getValue()));
			});

			// Update layered registries
			List<RegistryAccess.Frozen> newRegistryAccessList = new ArrayList<>();
			for (RegistryLayer layer : RegistryLayer.values()) {
				if (layer == RegistryLayer.DIMENSIONS) {
					newRegistryAccessList
							.add(new RegistryAccess.ImmutableRegistryAccess(List.of(newRegistry)).freeze());
				} else {
					newRegistryAccessList.add(layeredRegistryAccess.getLayer(layer));
				}
			}

			Map<ResourceKey<? extends Registry<?>>, Registry<?>> newMap = new HashMap<>();
			newRegistryAccessList.forEach(r -> r.registries().toList().forEach(e -> newMap.put(e.key(), e.value())));

			ReflectionBuddy.LayeredRegistryAccessAccess.values.set(layeredRegistryAccess,
					List.copyOf(newRegistryAccessList));
			ReflectionBuddy.ImmutableRegistryAccessAccess.registries.set((ImmutableRegistryAccess) composite, newMap);

			server.markWorldsDirty();
			Networking.sendPacketToAll(new UpdateDimensionsS2C(removedKeys, false));
		}
	}

	/** ======================== Public API ======================== */

	public ServerLevel getOrCreateLevel(MinecraftServer server, ResourceKey<Level> levelKey,
			Supplier<LevelStem> factory) {
		Map<ResourceKey<Level>, ServerLevel> map = server.forgeGetWorldMap();
		return map.getOrDefault(levelKey, createAndRegisterLevel(server, map, levelKey, factory));
	}

	public void markDimensionForUnregistration(MinecraftServer server, ResourceKey<Level> level) {
		if (!VANILLA_LEVELS.contains(level))
			levelsPendingUnregistration.add(level);
	}

	public Set<ResourceKey<Level>> getLevelsPendingUnregistration() {
		return ImmutableSet.copyOf(levelsPendingUnregistration);
	}

	ServerLevel createDimension(MinecraftServer server, ResourceLocation location) {
		return getOrCreateLevel(server, ResourceKey.create(Registries.DIMENSION, location),
				() -> createLevelCopy(server));
	}

	/** ======================== Forge Event Handler ======================== */
	@EventBusSubscriber(modid = MODID)
	private static class ForgeEventHandler {

		@SubscribeEvent
		public static void onServerAboutToStart(ServerAboutToStartEvent event) {
			MinecraftServer server = event.getServer();
			if (server.overworld() == null)
				return;

			Registry<LevelStem> reg = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
			reg.entrySet().stream().filter(e -> e.getKey().location().getNamespace().equals(MODID))
					.forEach(e -> DimensionManager.INSTANCE.getOrCreateLevel(server,
							ResourceKey.create(Registries.DIMENSION, e.getKey().location()), e::getValue));
		}

		@SubscribeEvent
		public static void onServerStopped(ServerStoppedEvent event) {
			DimensionManager.INSTANCE.levelsPendingUnregistration.clear();
		}

		@SubscribeEvent
		public static void onServerTick(ServerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
				if (server != null)
					DimensionManager.INSTANCE.unregisterScheduledDimensions(server);
			}
		}
	}
}
