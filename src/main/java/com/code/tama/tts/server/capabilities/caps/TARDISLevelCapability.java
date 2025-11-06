/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.caps;

import com.code.tama.triggerapi.helpers.ThreadUtils;
import com.code.tama.tts.server.ServerThreads;
import com.code.tama.tts.server.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.*;
import com.code.tama.tts.server.events.TardisEvent;
import com.code.tama.tts.server.misc.BlockHelper;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapLightPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncTARDISCapPacketS2C;
import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.code.tama.tts.server.registries.tardis.LandingTypeRegistry;
import com.code.tama.tts.server.threads.CrashThread;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.royawesome.jlibnoise.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.code.tama.tts.server.blocks.tardis.ExteriorBlock.FACING;

public class TARDISLevelCapability implements ITARDISLevel {
	TARDISData data = new TARDISData(this);
	TARDISNavigationalData navigationalData = new TARDISNavigationalData(this);

	@OnlyIn(Dist.CLIENT)
	TARDISClientData clientData = new TARDISClientData(this);

	TARDISFlightData flightData = new TARDISFlightData(this);
	Level level;
	ExteriorTile exteriorTile;
	private long ticks = 0;

	public TARDISLevelCapability(Level level) {
		this.level = level;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.put("data", TARDISData.CODEC.encodeStart(NbtOps.INSTANCE, data).get().orThrow());
		tag.put("flight_data", TARDISFlightData.CODEC.encodeStart(NbtOps.INSTANCE, flightData).get().orThrow());
		tag.put("navigational_data",
				TARDISNavigationalData.CODEC.encodeStart(NbtOps.INSTANCE, navigationalData).get().orThrow());

		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.data = TARDISData.CODEC.parse(NbtOps.INSTANCE, nbt.get("data")).get().orThrow();
		this.navigationalData = TARDISNavigationalData.CODEC.parse(NbtOps.INSTANCE, nbt.get("navigational_data")).get()
				.orThrow();
		this.flightData = TARDISFlightData.CODEC.parse(NbtOps.INSTANCE, nbt.get("flight_data")).get().orThrow();

		this.data.setTARDIS(this);
		this.navigationalData.setTARDIS(this);
		this.flightData.setTARDIS(this);
	}

	public TARDISData GetData() {
		return data;
	}

	@Override
	public void setData(TARDISData data) {
		this.data = data;
		this.data.setTARDIS(this);
	}

	@Override
	public void setNavigationalData(TARDISNavigationalData data) {
		this.navigationalData = data;
		this.navigationalData.setTARDIS(this);
	}

	@Override
	public void setFlightData(TARDISFlightData data) {
		this.flightData = data;
		this.flightData.setTARDIS(this);
	}

	public TARDISNavigationalData GetNavigationalData() {
		return navigationalData;
	}

	@Override
	public TARDISClientData GetClientData() {
		return this.clientData;
	}

	public TARDISFlightData GetFlightData() {
		return flightData;
	}

	@Override
	public Level GetLevel() {
		return this.level;
	}

	@Override
	public boolean CanFly() {
		return this.data.getSubSystemsData().getDematerializationCircuit().isActivated(this.level)
				&& this.data.isPowered();
	}

	@Override
	public ResourceKey<Level> GetCurrentLevel() {
		if (this.navigationalData.getLocation().getLevelKey() != null)
			return this.navigationalData.getLocation().getLevelKey();
		if (this.level.isClientSide && this.navigationalData.getExteriorDimensionKey() == null)
			this.UpdateClient(DataUpdateValues.DATA);
		if (this.navigationalData.getExteriorDimensionKey() == null) {
			if (this.GetExteriorTile() != null) {
				this.navigationalData.setExteriorDimensionKey(
						Objects.requireNonNull(Objects.requireNonNull(this.GetExteriorTile()).getLevel()).dimension());
			} else if (!this.level.isClientSide)
				this.GetNavigationalData()
						.SetCurrentLevel(Objects.requireNonNull(this.level.getServer()).overworld().dimension());
		}

		return this.navigationalData.getExteriorDimensionKey() == null
				? Level.OVERWORLD
				: this.navigationalData.getDestinationDimensionKey();
	}

	/**
	 * If you call this while the TARDIS is in flight you're gay and it'll return
	 * null. <b>DO NOT CALL THIS WHILE THE TARDIS IS IN FLIGHT</b><br />
	 *
	 * @return The ExteriorTile belonging to this TARDIS
	 */
	@Override
	@Nullable public ExteriorTile GetExteriorTile() {
		if (!this.flightData.isInFlight()) {
			if (this.exteriorTile == null) {
				if (!this.level.isClientSide && this.level.getServer() != null) {
					if (this.level.getServer().getLevel(this.navigationalData.getExteriorDimensionKey()) != null) {
						ServerLevel tardisLevel = this.level.getServer()
								.getLevel(this.navigationalData.getExteriorDimensionKey());

						assert tardisLevel != null;

						ChunkAccess chunk = this.level
								.getChunk(this.GetNavigationalData().GetExteriorLocation().GetBlockPos());

						BlockEntity fromChunk = chunk
								.getBlockEntity(this.navigationalData.getDestination().GetBlockPos());

						if (fromChunk instanceof ExteriorTile tile)
							return exteriorTile = tile;

						this.ForceLoadExteriorChunk(true);

						this.exteriorTile = (ExteriorTile) chunk
								.getBlockEntity(this.GetNavigationalData().GetExteriorLocation().GetBlockPos());

						this.ForceLoadExteriorChunk(false);

						return this.exteriorTile;
					}
				}
			} else
				return this.exteriorTile;
		}
		return null;
	}

	@Override
	public void SetExteriorTile(ExteriorTile exteriorTile) {
		this.exteriorTile = exteriorTile;
		assert exteriorTile.getLevel() != null;
		this.navigationalData
				.setLocation(new SpaceTimeCoordinate(exteriorTile.getBlockPos(), exteriorTile.getLevel().dimension()));
		assert exteriorTile.getLevel() != null;
		this.navigationalData.setExteriorDimensionKey(exteriorTile.getLevel().dimension());
	}

	@Override
	public void Fly() {
		// Null exterior checks!
		this.NullExteriorChecksAndFixes();

		//////////////////////// CALCULATIONS START ////////////////////////
		// Set distance between the location and destination
		double Distance = this.GetNavigationalData().getDestination().GetBlockPos().getCenter()
				.distanceTo(this.GetNavigationalData().GetExteriorLocation().GetBlockPos().getCenter());
		// Set reach destination ticks
		this.GetFlightData().setTicksTillDestination((int) Distance * 20);
		//////////////////////// CALCULATIONS END ////////////////////////
		// Make sure this is powered before continuing
		if (!this.CanFly())
			return;
		if (this.GetLevel().isClientSide())
			return;
		if (this.GetExteriorTile() == null)
			return;
		ExteriorTile ext = this.GetExteriorTile();

		// Set the TARDIS in flight
		this.GetFlightData().setInFlight(true);

		assert ext != null;
		Level exteriorLevel = ext.getLevel();
		assert exteriorLevel != null;
		this.ForceLoadExteriorChunk(true);

		ext.UtterlyDestroy();

		this.ForceLoadExteriorChunk(false);
		MinecraftForge.EVENT_BUS.post(new TardisEvent.TakeOff(this, TardisEvent.State.END));

		this.UpdateClient(DataUpdateValues.ALL);
	}

	@Override
	public void Dematerialize() {
		if (!this.CanFly())
			return;

		if (this.GetFlightData().isInFlight() && !this.GetFlightData().IsTakingOff())
			return;

		this.data.setSparking(false);

		MinecraftForge.EVENT_BUS.post(new TardisEvent.TakeOff(this, TardisEvent.State.START));

		UpdateClient(DataUpdateValues.FLIGHT);

		if (this.GetExteriorTile() == null) {
			// // Makes it so the TARDIS is supposed to be in-flight
			// this.GetFlightData().setInFlight(true);
			// this.GetFlightData().getFlightSoundScheme().GetTakeoff().SetFinished(true);
			// // Lands the TARDIS, creating an exterior
			// this.UpdateClient(DataUpdateValues.FLIGHT);
			// this.UpdateClient(DataUpdateValues.NAVIGATIONAL);
			// this.Rematerialize();
		} else {
			// Start a new Takeoff thread
			if (!level.isClientSide()) {
				this.GetFlightData().setPlayRotorAnimation(true);
				ServerThreads.TakeoffThread(this).start();
			}
		}
	}

	@Override
	public void Rematerialize() {
		if (!this.flightData.isInFlight())
			return;

		UpdateClient(DataUpdateValues.FLIGHT);

		MinecraftForge.EVENT_BUS.post(new TardisEvent.Land(this, TardisEvent.State.START));
		// TODO: This
		if (!level.isClientSide())
			ServerThreads.LandingThread(this).start();
	}

	@Override
	public void Land() {
		this.flightData.setInFlight(false);
		if (!this.GetLevel().isClientSide) {

			ServerLevel CurrentLevel = Objects.requireNonNull(this.GetLevel().getServer())
					.getLevel(this.GetNavigationalData().getDestination().getLevelKey());
			assert CurrentLevel != null;

			this.ForceLoadExteriorChunk(true);

			BlockPos pos = BlockHelper.snapToGround(this.GetLevel(),
					this.GetNavigationalData().getDestination().GetBlockPos());

			// this.GetFlightTerminationPolicy().GetProtocol().OnLand(this, pos,
			// CurrentLevel);
			// pos = this.GetFlightTerminationPolicy().GetProtocol().GetLandPos();

			pos = LandingTypeRegistry.UP.GetLandingPos(pos, CurrentLevel);

			SpaceTimeCoordinate coords = new SpaceTimeCoordinate(pos, CurrentLevel.dimension());

			this.GetNavigationalData().SetExteriorLocation(coords);
			this.GetNavigationalData().setDestination(coords);
			this.GetNavigationalData().setFacing(this.GetNavigationalData().getDestinationFacing());

			BlockState exteriorBlockState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();

			CurrentLevel.setBlock(this.GetNavigationalData().getDestination().GetBlockPos(),
					exteriorBlockState.setValue(FACING, this.GetNavigationalData().getFacing()), 3);

			((ExteriorBlock) exteriorBlockState.getBlock()).SetInteriorKey(this.GetLevel().dimension());

			this.GetLevel().setBlockAndUpdate(coords.GetBlockPos(), exteriorBlockState);
			if (CurrentLevel.getBlockEntity(pos) != null) {
				BlockPos finalPos = pos; // pos used in lambda must be final or effectively final
				CurrentLevel.getServer().execute(new TickTask(1,
						() -> this.SetExteriorTile(((ExteriorTile) CurrentLevel.getBlockEntity(finalPos)))));
			}
			this.ForceLoadExteriorChunk(false);
			this.GetFlightData().setPlayRotorAnimation(false);
			this.UpdateClient(DataUpdateValues.FLIGHT);
			this.UpdateClient(DataUpdateValues.NAVIGATIONAL);
		}

		MinecraftForge.EVENT_BUS.post(new TardisEvent.Land(this, TardisEvent.State.END));
		this.GetNavigationalData().SetExteriorLocation(this.GetNavigationalData().getDestination());
		this.NullExteriorChecksAndFixes();
	}

	@Override
	public void Crash() {
		if (this.level.isClientSide)
			return;
		if (!this.flightData.isInFlight())
			return;
		MinecraftForge.EVENT_BUS.post(new TardisEvent.Crash(this, TardisEvent.State.START));
		this.flightData.setInFlight(false);
		this.data.setSparking(true);
		this.data.SetLightLevel((float) MathHelper.clamp((double) this.level.random.nextInt(10) / 10, 0.3, 0.7));
		new CrashThread(this).start();
		this.NullExteriorChecksAndFixes();
	}

	public static Rotation DirectionToRotation(Direction direction) {
		return switch (direction) {
			case DOWN, WEST -> Rotation.COUNTERCLOCKWISE_90;
			case UP, EAST -> Rotation.CLOCKWISE_90;
			case SOUTH -> Rotation.CLOCKWISE_180;
			default -> Rotation.NONE;
		};
	}

	/** TODO: ADD MORE HERE! */
	public void NullExteriorChecksAndFixes() {
		if (!this.level.isClientSide)
			if (Objects.requireNonNull(this.level.getServer()).getLevel(this.GetCurrentLevel()) == null)
				this.GetNavigationalData().SetCurrentLevel(this.level.getServer().overworld().dimension());
	}

	public void UpdateClient(int toUpdate) {
		ThreadUtils.NewThread((cap, toSync) -> {
			if (cap.level == null)
				return;
			if (this.level.isClientSide)
				Networking.sendPacketToDimension(cap.level.dimension(),
						new TriggerSyncCapPacketC2S(cap.level.dimension(), toUpdate));
			else {
				Networking.sendPacketToDimension(cap.level.dimension(),
						new SyncTARDISCapPacketS2C(cap.data, cap.navigationalData, cap.flightData, toUpdate));

				if (this.GetExteriorTile() != null) {
					Objects.requireNonNull(cap.GetExteriorTile()).Model = cap.data.getExteriorModel();
					Objects.requireNonNull(cap.GetExteriorTile()).setModelIndex(cap.data.getExteriorModel().getModel());
					Objects.requireNonNull(cap.GetExteriorTile()).setChanged();
					Objects.requireNonNull(cap.GetExteriorTile()).NeedsClientUpdate();
				}
			}
		}, this, toUpdate, "tardis_update_thread");
	}

	@Override
	public void Tick() {
		this.ticks++;
		if (this.flightData.isInFlight() && this.flightData.getTicksInFlight() > 0) {
			this.flightData.setTicksInFlight(
					this.flightData.getTicksInFlight() - this.data.getControlData().GetArtronPacketOutput());
		}
	}

	@Override
	public float GetLightLevel() {
		if (this.data.getLightLevel() == 0.0f) {
			Networking.sendToServer(new TriggerSyncCapLightPacketC2S(this.level.dimension()));
			return 0.1f;
		}
		return this.data.getLightLevel();
	}

	@Override
	public long getTicks() {
		return this.ticks;
	}

	@Override
	public void ForceLoadExteriorChunk(boolean ForceLoad) {
		Objects.requireNonNull(this.GetNavigationalData().getDestination().getLevel().getServer()
				.getLevel(this.navigationalData.getLocation().getLevelKey()))
				.setChunkForced((int) (this.GetNavigationalData().GetExteriorLocation().GetX() / 16),
						(int) (this.GetNavigationalData().GetExteriorLocation().GetZ() / 16), ForceLoad);
	}
}
