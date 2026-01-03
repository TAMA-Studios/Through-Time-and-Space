/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.server.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.caps.PlayerCapability;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.enums.Structures;
import com.code.tama.tts.server.events.TardisEvent;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.exterior.TriggerSyncExteriorPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.exterior.SyncTransparencyPacketS2C;
import com.code.tama.tts.server.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.threads.GetExteriorVariantThread;
import com.code.tama.tts.server.worlds.TStemCreation;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.ServerLifecycleHooks;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.dimensions.DimensionAPI;
import com.code.tama.triggerapi.helpers.MathUtils;
import com.code.tama.triggerapi.helpers.world.WorldHelper;
import com.code.tama.triggerapi.universal.UniversalServerOnly;

public class ExteriorTile extends AbstractPortalTile {
	public ExteriorState state = ExteriorState.LANDED;

	private ResourceKey<Level> INTERIOR_DIMENSION;

	@Getter
	private float transparency = 1.0f; // Default fully visible
	int DoorState;
	@Getter
	@Setter
	ResourceLocation ModelIndex = ExteriorsRegistry.EXTERIORS.get(0).getModel();
	public ExteriorModelContainer Model = ExteriorsRegistry.EXTERIORS.get(0);

	public String PlacerName;
	public UUID PlacerUUID;

	public boolean ShouldMakeDimOnNextTick = false, isArtificial;

	public boolean ThreadWorking = false;

	public ExteriorAnimationData exteriorAnimationData = new ExteriorAnimationData();

	public ExteriorTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		if (this.PlacerUUID != null)
			tag.putUUID("placerUUID", this.PlacerUUID);
		if (this.PlacerName != null)
			tag.putString("placerName", this.PlacerName);
		tag.putInt("FlightState", this.state.ordinal());
		tag.putBoolean("artificial", this.isArtificial);

		if (this.INTERIOR_DIMENSION != null) {
			assert this.level != null;
			Capabilities.getCap(Capabilities.TARDIS_LEVEL_CAPABILITY,
					this.level.getServer().getLevel(this.INTERIOR_DIMENSION)).ifPresent(cap -> {
						if (cap.GetExteriorTile() == this) {
							this.ModelIndex = cap.GetData().getExteriorModel().getModel();
							this.Model = cap.GetData().getExteriorModel();
						}
					});
		}
		tag.putString("modelPath", this.getModelIndex().getPath());
		tag.putString("modelNamespace", this.getModelIndex().getNamespace());
		tag.putFloat("Transparency", this.transparency);
		ExteriorModelContainer.CODEC.encode(this.GetVariant(), NbtOps.INSTANCE, tag);
		assert this.level != null;
		if (this.level.getServer().getLevel(this.INTERIOR_DIMENSION) != null)
			if (this.level.getServer().getLevel(this.INTERIOR_DIMENSION)
					.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).isPresent())
				this.level.getServer().getLevel(this.INTERIOR_DIMENSION)
						.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
						.ifPresent(cap -> tag.putInt("doors", cap.GetData().getDoorData().getDoorsOpen()));
			else
				tag.putInt("doors", this.DoorState);
		if (this.INTERIOR_DIMENSION != null)
			tag.putString("interior", this.INTERIOR_DIMENSION.location().getPath());

		super.saveAdditional(tag);
	}

	@Override
	public BlockPos getTargetPos() {
		return super.getTargetPos();
	}

	public int CycleDoors() {
		this.SetDoorsOpen(switch (this.DoorsOpen()) {
			case 0 -> 1;
			case 1 -> 2;
			default -> 0;
		});
		return this.DoorsOpen();
	}

	public int DoorsOpen() {
		if (this.level != null && this.GetInterior() != null && !this.level.isClientSide) {
			assert this.getLevel() != null;
			return this.level.getServer().getLevel(this.GetInterior())
					.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
					.orElse(new TARDISLevelCapability(this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION)))
					.GetData().getInteriorDoorData().getDoorsOpen();
		} else
			return this.DoorState;
	}

	@Nullable public ExteriorBlock GetBlock() {
		assert this.level != null;
		if (this.level.getServer() != null) {
			assert this.level != null;
			Block block = this.level.getServer().getLevel(this.level.dimension()).getBlockState(this.getBlockPos())
					.getBlock();
			if (block instanceof ExteriorBlock exteriorBlock)
				return exteriorBlock;
		}
		return null;
	}

	public ResourceKey<Level> GetInterior() {
		return this.INTERIOR_DIMENSION;
	}

	public ExteriorModelContainer GetVariant() {
		this.UpdateVariant();
		return this.Model == null ? ExteriorsRegistry.Get(0) : this.Model;
	}

	public void UpdateAll() {
		if (this.level == null)
			return;
		if (this.level.isClientSide)
			return;

		this.updateModel();
		this.updateTargetPos();
		this.setChanged();
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
		this.UpdateVariant();
	}

	public void PlaceInterior(Structures structure) {
		assert this.getLevel() != null;
		if (this.getLevel().isClientSide)
			return;
		WorldHelper.PlaceStructure(this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION),
				new BlockPos(MathUtils.RoundTo48(0), MathUtils.RoundTo48(128), MathUtils.RoundTo48(0)),
				structure.GetRL());
	}

	public void SetDoorsOpen(int doorState) {
		assert this.level != null;
		if (!this.level.isClientSide)
			if (this.INTERIOR_DIMENSION != null)
				this.level.getServer().getLevel(this.INTERIOR_DIMENSION)
						.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
						.ifPresent(cap -> cap.GetData().getInteriorDoorData().setDoorsOpen(doorState));
		this.DoorState = doorState;
	}

	public void SetInterior(ResourceKey<Level> INTERIOR_DIMENSION) {
		this.INTERIOR_DIMENSION = INTERIOR_DIMENSION;
		this.UpdateAll();
	}

	public void updateTargetPos() {
		assert this.getLevel() != null;
		ServerLevel Interior = this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION);

		assert Interior != null;
		TARDISLevelCapability.GetTARDISCapSupplier(Interior)
				.ifPresent(cap -> this.setTargetLevel(cap.GetLevel().dimension(),
						cap.GetData().getDoorData().getLocation().GetBlockPos(), cap.GetData().getDoorData().getYRot(),
						true));
	}

	public void TeleportToInterior(Entity EntityToTeleport) {
		if (this.getLevel() == null || this.getLevel().isClientSide || this.INTERIOR_DIMENSION == null)
			return;

		// Don't teleport if the entity in question is viewing the exterior via
		// Environment Scanner
		if (EntityToTeleport.getCapability(Capabilities.PLAYER_CAPABILITY).isPresent()
				&& !Objects.equals(EntityToTeleport.getCapability(Capabilities.PLAYER_CAPABILITY)
						.orElse(new PlayerCapability(EntityToTeleport)).GetViewingTARDIS(), ""))
			return;

		ServerLevel Interior = this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION);

		assert Interior != null;
		Interior.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {

			TardisEvent.EntityEnterTARDIS event = new TardisEvent.EntityEnterTARDIS(cap, TardisEvent.State.START,
					EntityToTeleport);
			MinecraftForge.EVENT_BUS.post(event);

			if (event.isCanceled())
				return;

			float X, Y, Z;

			BlockPos pos = cap.GetData().getDoorData().getLocation().GetBlockPos()
					.relative(Direction.fromYRot(cap.GetData().getDoorData().getYRot()), 2);

			X = pos.getX() + 0.5f;
			Y = pos.getY() == 0 ? 128 : pos.getY();
			Z = pos.getZ() + 0.5f;

			float yRot = cap.GetData().getDoorData().getYRot() + EntityToTeleport.getYRot();

			if (EntityToTeleport instanceof ServerPlayer player) {
				player.getAbilities().flying = false;
				player.onUpdateAbilities();
			}

			EntityToTeleport.teleportTo(Interior, X, Y, Z, Set.of(), yRot, 0);

			MinecraftForge.EVENT_BUS
					.post(new TardisEvent.EntityEnterTARDIS(cap, TardisEvent.State.END, EntityToTeleport));
		});
	}

	public void UpdateVariant() {
		new GetExteriorVariantThread(this).start();
	}

	/**
	 * Utterly Destroys the tile entity and the linked {@link ExteriorBlock}
	 */
	public void UtterlyDestroy() {
		assert this.level != null;
		level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
		level.removeBlockEntity(this.getBlockPos());
		this.setRemoved();
	}

	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
		// this.serializeNBT
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		this.load(tag);
		super.handleUpdateTag(tag);
	}

	@Override
	public void load(CompoundTag tag) {

		if (tag.hasUUID("placerUUID")) {
			this.PlacerUUID = tag.getUUID("placerUUID");
		}

		if (tag.contains("placerName", 8)) { // 8 = String type in NBT
			this.PlacerName = tag.getString("placerName");
		}

		if (tag.contains("FlightState"))
			this.state = ExteriorState.values()[tag.getInt("FlightState")];

		if (tag.contains("artificial"))
			this.isArtificial = tag.getBoolean("artificial");

		if (tag.contains("modelPath") && tag.contains("modelNamespace")) {
			this.ModelIndex = new ResourceLocation(tag.getString("modelNamespace"), tag.getString("modelPath"));
		} else {
			this.ModelIndex = ExteriorsRegistry.EXTERIORS.get(0).getModel();
		}
		if (tag.contains("model")) {
			this.Model = ExteriorModelContainer.CODEC.parse(NbtOps.INSTANCE, tag.get("model")).get().orThrow();
		}

		if (tag.contains("interior")) {
			this.INTERIOR_DIMENSION = ResourceKey.create(Registries.DIMENSION,
					new ResourceLocation(MODID + "-tardis", tag.getString("interior")));
			this.targetLevel = this.INTERIOR_DIMENSION;
		}

		super.load(tag);
	}

	@Override
	public void onChunkUnloaded() {
		assert this.level != null;
		if (!this.level.isClientSide) {
			if (this.state.equals(ExteriorState.SHOULDNTEXIST) || this.state.equals(ExteriorState.TAKINGOFF)) {
				this.UtterlyDestroy();
				return;
			}
			if (UniversalServerOnly.getServer() == null
					|| UniversalServerOnly.getServer().getLevel(this.INTERIOR_DIMENSION) == null)
				return;
			TARDISLevelCapability
					.GetTARDISCapSupplier(UniversalServerOnly.getServer().getLevel(this.INTERIOR_DIMENSION))
					.ifPresent(tardis -> {
						if (tardis.GetFlightData().IsTakingOff() || tardis.GetFlightData().isInFlight())
							this.UtterlyDestroy();
					});

			super.onChunkUnloaded();
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (this.level != null && this.level.isClientSide)
			Networking.sendToServer(new TriggerSyncExteriorPacketC2S(this.level.dimension(), this.getBlockPos().getX(),
					this.getBlockPos().getY(), this.getBlockPos().getZ()));
	}

	public void setClientTransparency(float alpha) {
		this.transparency = alpha;
	}

	public void setModel(int model) {
		if (model >= ExteriorsRegistry.EXTERIORS.size())
			model = 0;
		this.Model = ExteriorsRegistry.EXTERIORS.get(model);
		this.setChanged();

		if (this.level instanceof ServerLevel serverLevel) {
			ServerLevel level1 = serverLevel.getServer().getLevel(this.INTERIOR_DIMENSION);
			if (level1 != null) {
				level1.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
					this.ModelIndex = cap.GetData().getExteriorModel().getModel();
					this.Model = cap.GetData().getExteriorModel();
					cap.UpdateClient(DataUpdateValues.RENDERING);
					// Networking.sendPacketToDimension(this.level.dimension(), new
					// SyncExteriorVariantPacketS2C(this.ModelIndex,
					// ExteriorVariants.GetOrdinal(this.Variant), worldPosition.getX(),
					// worldPosition.getY(), worldPosition.getZ()));
				});
			}
		}
	}

	public void updateModel() {

		if (this.level instanceof ServerLevel serverLevel) {
			ServerLevel level1 = serverLevel.getServer().getLevel(this.INTERIOR_DIMENSION);
			if (level1 != null) {
				level1.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
					this.ModelIndex = cap.GetData().getExteriorModel().getModel();
					this.Model = cap.GetData().getExteriorModel();
					cap.UpdateClient(DataUpdateValues.RENDERING);
					// Networking.sendPacketToDimension(this.level.dimension(), new
					// SyncExteriorVariantPacketS2C(this.ModelIndex,
					// ExteriorVariants.GetOrdinal(this.Variant), worldPosition.getX(),
					// worldPosition.getY(), worldPosition.getZ()));
					this.setChanged();
				});
			}
		}
	}

	public void setTransparency(float alpha) {
		this.transparency = alpha;
		setChanged();

		if (level instanceof ServerLevel serverLevel) {
			// Notify clients of the update using a block update
			serverLevel.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

			// Send the custom packet to all nearby players (example: radius of 64 blocks)
			for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
				if (player.blockPosition().closerThan(worldPosition, 64)) {
					Networking.sendToPlayer(player, new SyncTransparencyPacketS2C(transparency, worldPosition.getX(),
							worldPosition.getY(), worldPosition.getZ()));
				}
			}
		}
	}

	@Override
	public void tick() {
		if (this.state.equals(ExteriorState.SHOULDNTEXIST))
			this.UtterlyDestroy();

		if (this.ShouldMakeDimOnNextTick)
			makeInterior(this.isArtificial);

		if (this.GetInterior() == null)
			return;

		if (level != null && !level.isClientSide && level.getServer().getLevel(this.GetInterior()) != null) {
			level.getServer().getLevel(this.GetInterior()).getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
					.ifPresent(cap -> {
						if ((!this.getBlockPos().equals(cap.GetNavigationalData().GetExteriorLocation().GetBlockPos())
								|| cap.GetFlightData().isInFlight()))
							this.UtterlyDestroy();

						if (this.targetLevel == null) {
							this.setTargetLevel(cap.GetLevel().dimension(),
									cap.GetData().getDoorData().getLocation().GetBlockPos(),
									cap.GetData().getDoorData().getYRot(), true);
							BOTIUtils.updateChunkModel(this);
						}
					});
		}
	}

	private void makeInterior(boolean isArtificial) {
		assert level != null;
		if (level.isClientSide || level.getServer() == null)
			return;
		level.getServer().execute(() -> {
			ResourceKey<Level> resourceKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(
					MODID + "-tardis", "owner-" + this.PlacerName.toLowerCase() + "-uuid-" + UUID.randomUUID()));

			ServerLevel tardisLevel;

			if (isArtificial)
				tardisLevel = DimensionAPI.get().getOrCreateLevel(level.getServer(), resourceKey,
						() -> TStemCreation.createArtificialTARDISLevelStem(level.getServer()));
			else
				tardisLevel = DimensionAPI.get().getOrCreateLevel(level.getServer(), resourceKey,
						() -> TStemCreation.createNaturalTARDISLevelStem(level.getServer()));

			GetTARDISCapSupplier(tardisLevel).ifPresent((cap) -> {
				cap.SetExteriorTile(this);
				assert this.getLevel() != null;
				cap.GetNavigationalData().setExteriorDimensionKey(this.getLevel().dimension());
				cap.GetNavigationalData().SetExteriorLocation(new SpaceTimeCoordinate(this.getBlockPos()));
				BlockPos loc = cap.GetNavigationalData().GetExteriorLocation().GetBlockPos();
				cap.GetNavigationalData().setDestination(new SpaceTimeCoordinate(
						level.getBlockRandomPos(loc.getX(), loc.getY(), loc.getZ(), 500000).atY(64)));
				cap.GetNavigationalData().SetCurrentLevel(this.level.dimension());
				cap.GetData().setOwnerUUID(this.PlacerUUID);
			});

			this.INTERIOR_DIMENSION = tardisLevel.dimension();
			this.PlaceInterior(Structures.CleanInterior);

			ServerPlayer player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(this.PlacerUUID);

			if (player != null) {
				player.getCapability(Capabilities.PLAYER_CAPABILITY)
						.ifPresent(cap -> cap.AddOwnedTARDIS(this.INTERIOR_DIMENSION.location().getPath()));
			}
		});
		this.ShouldMakeDimOnNextTick = false;
	}

}