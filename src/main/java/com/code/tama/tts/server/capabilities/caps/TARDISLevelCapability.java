/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.caps;

import static com.code.tama.tts.server.blocks.tardis.ExteriorBlock.FACING;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import com.code.tama.tts.config.TTSConfig;
import com.code.tama.tts.server.CommonThreads;
import com.code.tama.tts.server.ServerThreads;
import com.code.tama.tts.server.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.DecoyFlightEvent;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.FlightEvent;
import com.code.tama.tts.server.data.json.lists.DataFlightEventList;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.data.tardis.EnergyHandler;
import com.code.tama.tts.server.data.tardis.data.*;
import com.code.tama.tts.server.events.TardisEvent;
import com.code.tama.tts.server.misc.BlockHelper;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapLightPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncTARDISCapPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncTARDISFlightEventPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.exterior.ExteriorStatePacket;
import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.code.tama.tts.server.registries.tardis.FlightTerminationProtocolRegistry;
import com.code.tama.tts.server.registries.tardis.LandingTypeRegistry;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.royawesome.jlibnoise.MathHelper;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.server.ServerLifecycleHooks;

public class TARDISLevelCapability implements ITARDISLevel {
	private final EnergyHandler energyHandler = new EnergyHandler();
	private Thread TickThread;
	private TARDISData data = new TARDISData(this);
	private TARDISNavigationalData navigationalData = new TARDISNavigationalData(this);

	private TARDISEnvironmentalData environmentalData = new TARDISEnvironmentalData(this);
	@OnlyIn(Dist.CLIENT)
	private final TARDISClientData clientData = new TARDISClientData(this);

	private TARDISFlightData flightData = new TARDISFlightData(this);
	Level level;
	ExteriorTile exteriorTile;
	private long ticks = 0, lastFlightEvent;

	public TARDISLevelCapability(Level level) {
		this.level = level;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		this.energyHandler.saveNBT(tag);
		tag.put("data", TARDISData.CODEC.encodeStart(NbtOps.INSTANCE, data).get().orThrow());
		tag.put("flight_data", TARDISFlightData.CODEC.encodeStart(NbtOps.INSTANCE, flightData).get().orThrow());
		tag.put("navigational_data",
				TARDISNavigationalData.CODEC.encodeStart(NbtOps.INSTANCE, navigationalData).get().orThrow());
		tag.put("environmental_data",
				TARDISEnvironmentalData.CODEC.encodeStart(NbtOps.INSTANCE, environmentalData).get().orThrow());

		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.energyHandler.loadNBT(nbt);
		this.data = TARDISData.CODEC.parse(NbtOps.INSTANCE, nbt.get("data")).get().orThrow();
		this.navigationalData = TARDISNavigationalData.CODEC.parse(NbtOps.INSTANCE, nbt.get("navigational_data")).get()
				.orThrow();
		this.flightData = TARDISFlightData.CODEC.parse(NbtOps.INSTANCE, nbt.get("flight_data")).get().orThrow();
		this.environmentalData = TARDISEnvironmentalData.CODEC.parse(NbtOps.INSTANCE, nbt.get("environmental_data"))
				.get().orThrow();

		this.data.setTARDIS(this);
		this.navigationalData.setTARDIS(this);
		this.flightData.setTARDIS(this);
		this.environmentalData.setTARDIS(this);
	}

	public TARDISData GetData() {
		return data;
	}

	@Override
	public TARDISEnvironmentalData GetEnvironmentalData() {
		return this.environmentalData;
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
	public void setCurrentFlightEvent(@Nullable FlightEvent event) {
		if (event == null)
			event = new DecoyFlightEvent();
		this.GetFlightData().setFlightEvent(event.copy());
	}

	@Override
	public FlightEvent getCurrentFlightEvent() {
		return this.GetFlightData().getFlightEvent();
	}

	@Override
	public void UpdateExteriorState(ExteriorState state) {
		if (this.GetExteriorTile() == null || this.level.isClientSide)
			return;
		ForceLoadExteriorChunk(true);
		if (state.equals(ExteriorState.SHOULDNTEXIST))
			GetExteriorTile().UtterlyDestroy();

		else {
			long tick = this.GetLevel().getGameTime();

			this.exteriorTile.state = state;

			Networking.sendPacketToDimension(
					new ExteriorStatePacket(this.GetNavigationalData().getDestination().GetBlockPos(),
							ExteriorState.LANDING, tick),
					this.getExteriorLevel());
		}
		ForceLoadExteriorChunk(false);
	}

	@Override
	public EnergyHandler getEnergy() {
		return this.energyHandler;
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
	public boolean CanTakeoff() {
		return this.data.getSubSystemsData().getDematerializationCircuit().isActivated(this.level)
				&& this.data.isPowered() && this.data.getControlData().isCoordinateLock()
				&& !this.data.getControlData().isVortexAnchor() && this.data.getFuel() > 0
				&& !this.data.getControlData().isEngineBrake();
	}

	@Override
	public boolean CanFly() {
		return this.data.getSubSystemsData().getDematerializationCircuit().isActivated(this.level)
				&& this.data.isPowered() && this.data.getFuel() > 0 && !this.data.getControlData().isEngineBrake();
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
				: this.navigationalData.getExteriorDimensionKey();
	}

	/**
	 * If you call this while the TARDIS is in flight you're gay, and it'll return
	 * null. <b>DO NOT CALL THIS WHILE THE TARDIS IS IN FLIGHT</b><br />
	 *
	 * @return The ExteriorTile belonging to this TARDIS
	 */
	@Override
	@Nullable public ExteriorTile GetExteriorTile() {
		if (this.exteriorTile == null) {
			if (!this.level.isClientSide && this.level.getServer() != null) {
				if (this.level.getServer().getLevel(this.navigationalData.getExteriorDimensionKey()) != null) {
					ServerLevel tardisLevel = this.level.getServer()
							.getLevel(this.navigationalData.getExteriorDimensionKey());

					assert tardisLevel != null;

					ChunkAccess chunk = this.level
							.getChunk(this.GetNavigationalData().GetExteriorLocation().GetBlockPos());

					BlockEntity fromChunk = chunk.getBlockEntity(this.navigationalData.getDestination().GetBlockPos());

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

		return null;
	}

	@Override
	public void SetExteriorTile(ExteriorTile exteriorTile) {
		this.exteriorTile = exteriorTile;
		// this.exteriorTile.SetInteriorAndSyncWithBlock(this.level.dimension());
		assert exteriorTile.getLevel() != null;
		this.navigationalData
				.setLocation(new SpaceTimeCoordinate(exteriorTile.getBlockPos(), exteriorTile.getLevel().dimension()));
		this.exteriorTile.updateModel();
		assert exteriorTile.getLevel() != null;
		this.navigationalData.setExteriorDimensionKey(exteriorTile.getLevel().dimension());
	}

	@Override
	public void Fly() {
		// Null exterior checks!
		this.NullExteriorChecksAndFixes();

		if (!this.CanTakeoff() || this.GetLevel().isClientSide())
			return;

		this.GetNavigationalData().setPreviousLocation(this.GetNavigationalData().getLocation());

		if (this.GetExteriorTile() != null) {
			ExteriorTile ext = this.GetExteriorTile();

			assert ext != null;
			Level exteriorLevel = ext.getLevel();
			assert exteriorLevel != null;
			this.ForceLoadExteriorChunk(true);

			ext.UtterlyDestroy();

			this.ForceLoadExteriorChunk(false);
		}

		this.GetFlightData().setInFlight(true);
		MinecraftForge.EVENT_BUS.post(new TardisEvent.TakeOff(this, TardisEvent.State.END));

		this.UpdateClient(DataUpdateValues.ALL);
	}

	public void FlightTick() {
		this.GetFlightData().setTicksInFlight(this.GetFlightData().getTicksInFlight() + 1);

		if (!this.CanFly())
			this.Crash();

		if (this.GetData().getControlData().isVortexAnchor())
			return;

		SpaceTimeCoordinate current = this.GetNavigationalData().getLocation();
		SpaceTimeCoordinate delta = flightData.distanceToLoc();

		double speed = TTSConfig.ServerConfig.BLOCKS_PER_TICK.get() + this.data.getControlData().GetArtronPacketOutput()
				+ (this.data.getControlData().isAPCState() ? 10 : 0); // speed in blocks per tick, calculated using
																		// default config value, + Artron packet output
																		// + APC on ? 10 : 0

		double dx = Math.signum(delta.GetX()) * speed;
		double dy = Math.signum(delta.GetY()) * speed;
		double dz = Math.signum(delta.GetZ()) * speed;

		current.AddX(dx).AddY(dy).AddZ(dz);

		this.GetNavigationalData().setLocation(current);

		this.data
				.setFuel(Math.max(this.data.getFuel() - ((long) speed + (this.data.getControlData().Stabilizers ? 5 : 0)
						+ ((this.GetFlightData().getTicksInFlight() / 1000))), 0)); // The longer you're in flight for,
																					// the faster fuel drains, for every
																					// 50 seconds you're in flight,
																					// it'll drain 1 fuel unit faster

		if (!level.isClientSide)
			HandleFlightEvents();
	}

	public void HandleFlightEvents() {
		if (this.data.getControlData().Stabilizers) {
			if (!(this.getCurrentFlightEvent() instanceof DecoyFlightEvent))
				this.setCurrentFlightEvent(null);
			return;
		}

		// Sometimes just add a little helmic drift
		if (ticks % (80 + ThreadLocalRandom.current().nextInt(120)) == 1) {
			this.data.getControlData().setHelmicRegulator(
					this.data.getControlData().getHelmicRegulator() + ThreadLocalRandom.current().nextInt(2) - 1 // Set
																													// it
																													// to
																													// a
																													// value
																													// of
																													// -1
																													// to
																													// 1
			);
			this.UpdateClient(DataUpdateValues.DATA);
		}
		// and temporal drift
		if (ticks % (40 + ThreadLocalRandom.current().nextInt(60)) == 1) {
			this.flightData.setDrift(this.flightData.getDrift() + ThreadLocalRandom.current().nextInt(10) - 5 // set it
																												// to a
																												// value
																												// from
																												// -5 to
																												// 5
			);
			this.UpdateClient(DataUpdateValues.FLIGHT);
		}

		if (!(this.getCurrentFlightEvent() instanceof DecoyFlightEvent)) { // If there's an actual flight event
			if (this.getCurrentFlightEvent().RequiredControls.isEmpty()) { // And it's completed
				this.setCurrentFlightEvent(null); // Clear the flight event

				// Post the forge event
				TardisEvent.FlightEventSucceed event = new TardisEvent.FlightEventSucceed(this);
				MinecraftForge.EVENT_BUS.post(event);

				this.lastFlightEvent = ticks;
				this.UpdateClient(DataUpdateValues.FLIGHT_EVENTS);
			}
			if ((this.ticks - (this.lastFlightEvent
					+ this.getCurrentFlightEvent().Time) > TTSConfig.ServerConfig.FLIGHT_EVENT_DURATION.get())) {

				// Post the forge event
				TardisEvent.FlightEventFail event = new TardisEvent.FlightEventFail(this);
				MinecraftForge.EVENT_BUS.post(event);
				// If someone cancelled it, just stop.
				if (event.isCanceled())
					return;

				// Perform the failure action of this specific flight event
				this.getCurrentFlightEvent().action.Action.accept(this);

				// Clear the flight event
				this.setCurrentFlightEvent(null);
				this.lastFlightEvent = ticks;
				this.UpdateClient(DataUpdateValues.FLIGHT_EVENTS);
			}
		}

		// Timing stuff to make sure you don't get a new flight event every tick
		if (!(this.ticks % TTSConfig.ServerConfig.TICKS_BETWEEN_FLIGHT_EVENT.get() == 1
				&& this.ticks - this.lastFlightEvent > TTSConfig.ServerConfig.TICKS_BETWEEN_FLIGHT_EVENT.get())
				|| this.GetFlightData().getTicksInFlight() < 80)
			return;

		int eventIndex = Math.max(ThreadLocalRandom.current().nextInt(0, DataFlightEventList.getList().size()), 0);

		this.setCurrentFlightEvent(DataFlightEventList.getList().get(eventIndex));

		TardisEvent.FlightEventStart event = new TardisEvent.FlightEventStart(this);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			this.setCurrentFlightEvent(null);
			return;
		}

		this.level.players().forEach(
				p -> p.sendSystemMessage(Component.literal("Flight event: " + this.getCurrentFlightEvent().name)));

		this.lastFlightEvent = this.ticks;
		this.UpdateClient(DataUpdateValues.FLIGHT_EVENTS);
	}

	@Override
	public void Dematerialize() {
		if (this.GetData().getControlData().isSimpleMode()) {
			this.GetData().getControlData().setCoordinateLock(true);
			this.GetData().getControlData().setAPCState(true);
			this.GetData().getDoorData().setDoorsOpen(0);
			this.GetData().getControlData().setVortexAnchor(false);
			this.GetData().getControlData().setArtronPacketOutput(1);
			this.GetData().getControlData()
					.setFlightTerminationProtocol(FlightTerminationProtocolRegistry.POLITE_TERMINUS);
			this.GetData().getControlData().setBrakes(false);
		}
		if (!this.CanTakeoff())
			return;

		if (this.GetFlightData().isInFlight() || this.GetFlightData().IsTakingOff())
			return;

		TardisEvent.TakeOff event = new TardisEvent.TakeOff(this, TardisEvent.State.START);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.isCanceled())
			return;

		GetFlightData().setPlayRotorAnimation(true);

		if (this.exteriorTile == null) {
			Fly();
			return;
		}

		this.data.setSparking(false);

		if (level.isClientSide())
			return;

		if (this.GetFlightData().isInFlight()) {// this.GetExteriorTile() == null) {
			// // Makes it so the TARDIS is supposed to be in-flight
			// this.GetFlightData().setInFlight(true);
			// this.GetFlightData().getFlightSoundScheme().GetTakeoff().SetFinished(true);
			// // Lands the TARDIS, creating an exterior
			// this.UpdateClient(DataUpdateValues.FLIGHT);
			// this.UpdateClient(DataUpdateValues.NAVIGATIONAL);
			// this.Rematerialize();
		} else {
			// Start a new Takeoff thread
			ServerThreads.TakeoffThread(this).start();
		}
	}

	@Override
	public void Rematerialize() {
		// if (!this.flightData.isInFlight())
		// return;

		TardisEvent.Land event = new TardisEvent.Land(this, TardisEvent.State.START);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.isCanceled())
			return;

		if (!level.isClientSide())
			ServerThreads.LandingThread(this).start();
	}

	@Override
	public void Land() {
		this.flightData.setInFlight(false);
		this.flightData.setTicksInFlight(0);
		if (!this.GetLevel().isClientSide) {

			ServerLevel CurrentLevel = Objects.requireNonNull(this.GetLevel().getServer())
					.getLevel(this.GetNavigationalData().getDestination().getLevelKey());
			assert CurrentLevel != null;

			this.ForceLoadExteriorChunk(true);

			BlockPos pos = BlockHelper.snapToGround(this.GetLevel(),
					this.GetNavigationalData().getDestination().GetBlockPos());

			pos = LandingTypeRegistry.UP.GetLandingPos(pos, CurrentLevel);

			// Perform landing protocol calculations and stuffs
			this.GetData().getControlData().getFlightTerminationProtocol().OnLand(this, pos, CurrentLevel);
			pos = this.GetData().getControlData().getFlightTerminationProtocol().GetLandPos();

			if (CurrentLevel.isOutsideBuildHeight(pos))
				pos = pos.atY(64);

			SpaceTimeCoordinate coords = new SpaceTimeCoordinate(pos, CurrentLevel.dimension());

			this.GetNavigationalData().SetExteriorLocation(coords);
			this.GetNavigationalData().setDestination(coords);
			this.GetNavigationalData().setFacing(this.GetNavigationalData().getDestinationFacing());

			BlockState exteriorBlockState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();

			CurrentLevel.setBlock(this.GetNavigationalData().getDestination().GetBlockPos(),
					exteriorBlockState.setValue(FACING, this.GetNavigationalData().getFacing()), 3);

			this.GetLevel().setBlockAndUpdate(coords.GetBlockPos(), exteriorBlockState);
			if (CurrentLevel.getBlockEntity(pos) != null) {
				this.SetExteriorTile(((ExteriorTile) CurrentLevel.getBlockEntity(pos)));
			} else {
				ExteriorTile tile = ((ExteriorTile) ((ExteriorBlock) exteriorBlockState.getBlock())
						.newBlockEntity(coords.GetBlockPos(), exteriorBlockState));
				assert tile != null;
				CurrentLevel.setBlockEntity(tile);
				tile.setLevel(CurrentLevel);
				this.SetExteriorTile(tile);
			}
			this.ForceLoadExteriorChunk(false);
			this.GetFlightData().setPlayRotorAnimation(false);
			this.UpdateClient(DataUpdateValues.ALL);
		}

		MinecraftForge.EVENT_BUS.post(new TardisEvent.Land(this, TardisEvent.State.END));
		this.GetNavigationalData().SetExteriorLocation(this.GetNavigationalData().getDestination());
		this.NullExteriorChecksAndFixes();
	}

	@Override
	public void Crash() {
		if (this.level.isClientSide || !this.flightData.isInFlight())
			return;

		TardisEvent.Crash event = new TardisEvent.Crash(this, TardisEvent.State.START);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.isCanceled())
			return;

		// this.flightData.setInFlight(false);
		this.data.setSparking(true);
		this.environmentalData
				.SetLightLevel((float) MathHelper.clamp((double) this.level.random.nextInt(10) / 10, 0.3, 0.7));

		this.Rematerialize();
		// new CrashThread(this).start();
		// this.NullExteriorChecksAndFixes();
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
		// ThreadUtils.RunThread((cap, toSync) -> {
		TARDISLevelCapability cap = this;
		if (cap.level == null)
			return;
		if (this.level.isClientSide)
			Networking.sendPacketToDimension(cap.level.dimension(),
					new TriggerSyncCapPacketC2S(cap.level.dimension(), toUpdate));
		else {
			if (toUpdate == DataUpdateValues.FLIGHT_EVENTS) {
				Networking.sendPacketToDimension(cap.level.dimension(),
						new SyncTARDISFlightEventPacketS2C(cap.GetFlightData().getFlightEvent()));
			} else {

				Networking.sendPacketToDimension(cap.level.dimension(),
						new SyncTARDISCapPacketS2C(cap.data, cap.navigationalData, cap.flightData, toUpdate));

				if (this.GetExteriorTile() != null) {
					Objects.requireNonNull(cap.GetExteriorTile()).Model = cap.data.getExteriorModel();
					Objects.requireNonNull(cap.GetExteriorTile()).setModelIndex(cap.data.getExteriorModel().getModel());
					Objects.requireNonNull(cap.GetExteriorTile()).setChanged();
					cap.GetExteriorTile().getLevel().sendBlockUpdated(cap.GetExteriorTile().getBlockPos(),
							cap.GetExteriorTile().getBlockState(), cap.GetExteriorTile().getBlockState(), 3);
				}
			}
		}
		// }, this, toUpdate, "tardis_update_thread");
	}

	@Override
	public void Tick() {
		this.ticks++;

		if (TickThread == null) {
			TickThread = CommonThreads.TARDISTickThread(this);
			TickThread.start();
		} else
			TickThread.run();
		// if (GetData().getSubSystemsData().DynamorphicController.isActivated(level)
		// && !GetData().getSubSystemsData().DynamorphicGeneratorStacks.isEmpty() &&
		// this.data.isRefueling()
		// && !this.flightData.isInFlight()) {
		// if (this.level.getGameTime() % 20 == 1)
		// this.data.setFuel(this.data.getFuel() + 1);
		// }
		//
		// if (this.flightData.isInFlight()) {
		// this.FlightTick();
		// }
	}

	@Override
	public float GetLightLevel() {
		if (this.environmentalData.getLightLevel() == 0.0f) {
			Networking.sendToServer(new TriggerSyncCapLightPacketC2S(this.level.dimension()));
			return 0.1f;
		}
		return this.environmentalData.getLightLevel();
	}

	@Override
	public long getTicks() {
		return this.ticks;
	}

	@Override
	public void ForceLoadExteriorChunk(boolean ForceLoad) {

		MinecraftServer server = this.level.getServer();
		if (server != null) {
			server.execute(() -> {
				ChunkPos pos = new ChunkPos(this.GetNavigationalData().GetExteriorLocation().GetBlockPos());
				if (getExteriorLevel().hasChunk(pos.x, pos.z))
					getExteriorLevel().setChunkForced(pos.x, pos.z, ForceLoad);
			});
		}
	}

	public static ITARDISLevel GetTARDISCap(Level level) {
		if (level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).isPresent())
			return level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).orElse(null);
		else
			return null;
	}

	public static LazyOptional<ITARDISLevel> GetTARDISCapSupplier(Level level) {
		return level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY);
	}

	public static LazyOptional<ITARDISLevel> GetTARDISCapSupplier(ResourceKey<Level> level) {
		return ServerLifecycleHooks.getCurrentServer().getLevel(level)
				.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY);
	}

	public static LazyOptional<ITARDISLevel> GetClientTARDISCapSupplier() {
		return Minecraft.getInstance().level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY);
	}

	public ServerLevel getExteriorLevel() {
		return this.GetNavigationalData().getDestination().getLevel().getServer()
				.getLevel(this.navigationalData.getLocation().getLevelKey());
	}
}
