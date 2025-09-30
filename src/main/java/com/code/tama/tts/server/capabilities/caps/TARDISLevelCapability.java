/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.caps;

import static com.code.tama.tts.TTSMod.LOGGER;
import static com.code.tama.tts.server.blocks.ExteriorBlock.FACING;

import com.code.tama.tts.Exteriors;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DoorData;
import com.code.tama.tts.server.enums.tardis.FlightTerminationProtocolEnum;
import com.code.tama.tts.server.events.TardisEvent;
import com.code.tama.tts.server.misc.*;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapLightPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapVariantPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncTARDISCapPacketS2C;
import com.code.tama.tts.server.registries.LandingTypeRegistry;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.tardis.data.ControlParameters;
import com.code.tama.tts.server.tardis.data.ProtocolData;
import com.code.tama.tts.server.tardis.data.SubsystemsData;
import com.code.tama.tts.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.tts.server.tardis.flightsoundschemes.FlightSoundHandler;
import com.code.tama.tts.server.tardis.flightsoundschemes.SmithSoundScheme;
import com.code.tama.tts.server.threads.CrashThread;
import com.code.tama.tts.server.threads.LandThread;
import com.code.tama.tts.server.threads.TakeOffThread;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

public class TARDISLevelCapability implements ITARDISLevel {
    float LightLevel;
    Map<UUID, PlayerPosition> ViewingPlayerPositions = new HashMap<>();
    UUID OwnerUUID;
    Exterior ExteriorVariant, ExteriorModelID = Exteriors.EXTERIORS.get(0);
    boolean IsInFlight, IsPowered, ShouldPlayRotorAnimation, IsDiscoMode;
    DoorData InteriorDoorData;
    int TicksInFlight, TicksTillDestination, Increment = 1;
    Direction Facing = Direction.NORTH, DestinationFacing = Direction.NORTH;
    Level level;
    SpaceTimeCoordinate Destination = new SpaceTimeCoordinate(),
            Location = new SpaceTimeCoordinate(),
            doorBlock = new SpaceTimeCoordinate();
    ExteriorTile exteriorTile;
    ResourceKey<Level> ExteriorDimensionKey, DestinationDimensionKey;
    FlightTerminationProtocolEnum flightTerminationProtocol;
    AbstractSoundScheme FlightSoundScheme;
    SubsystemsData SubsystemsData = new SubsystemsData();
    ControlParameters ControlData = new ControlParameters();
    ProtocolData ProtocolData = new ProtocolData();
    private long ticks = 0;

    public TARDISLevelCapability(Level level) {
        this.level = level;
    }

    @Override
    public CompoundTag serializeNBT() {
        LOGGER.debug("Saving: Location={} Destination={}", this.Location, this.Destination);

        CompoundTag Tag = new CompoundTag();
        if (this.OwnerUUID != null) Tag.putUUID("ownerID", this.OwnerUUID);
        NBTUtils.putPlayerPosMap("viewer_player_map_", this.ViewingPlayerPositions, Tag);

        Tag.put("subsystems", this.GetSubsystemsData().serializeNBT());
        Tag.put("controlData", this.GetControlData().serializeNBT());
        Tag.put("protocolData", this.GetProtocolData().serializeNBT());
        Tag.putString("exterior_model_id", this.ExteriorModelID.GetModelName().toString());
        Tag.putInt("flight_sound_scheme", FlightSoundHandler.GetID(this.FlightSoundScheme));
        Tag.putInt("increment", this.Increment);
        if (this.InteriorDoorData == null) this.InteriorDoorData = new DoorData(90, this.GetDoorBlock());
        Tag.put("doorData", this.InteriorDoorData.serializeNBT());
        Tag.putBoolean("play_rotor_animation", this.ShouldPlayRotorAnimation);
        Tag.putBoolean("isInFlight", this.IsInFlight);
        Tag.putString("facing", this.Facing.getName());
        Tag.putString("destination_facing", this.DestinationFacing.getName());
        Tag.put("destination", this.GetDestination().serializeNBT());
        Tag.put("location", this.GetExteriorLocation().serializeNBT());
        Tag.put("door", this.doorBlock.serializeNBT());
        if (this.ExteriorVariant != null) Tag.put("exterior_variant", this.ExteriorVariant.serializeNBT());
        Tag.putBoolean("is_powered_on", this.IsPowered);
        Tag.putFloat("light_level", this.LightLevel);
        if (this.ExteriorDimensionKey != null) {
            Tag.putString(
                    "exterior_dimension_key_path",
                    this.ExteriorDimensionKey.location().getPath());
            Tag.putString(
                    "exterior_dimension_key_namespace",
                    this.ExteriorDimensionKey.location().getNamespace());
        }
        if (this.DestinationDimensionKey != null) {
            Tag.putString(
                    "destination_dimension_key_path",
                    this.DestinationDimensionKey.location().getPath());
            Tag.putString(
                    "destination_dimension_key_namespace",
                    this.DestinationDimensionKey.location().getNamespace());
        }
        if (this.flightTerminationProtocol != null) {
            Tag.putString("flight_termination_protocol", this.flightTerminationProtocol.GetName());
        }

        this.UpdateClient();

        return Tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("ownerID")) this.OwnerUUID = nbt.getUUID("ownerID");
        if (nbt.contains("subsystems")) this.SubsystemsData = new SubsystemsData(nbt.getCompound("subsystems"));
        if (nbt.contains("controlData")) this.ControlData = new ControlParameters(nbt.getCompound("controlData"));
        if (nbt.contains("protocolData")) this.ProtocolData = new ProtocolData(nbt.getCompound("protocolData"));
        if (nbt.contains("exterior_model_id"))
            this.ExteriorModelID = Exteriors.GetByName(ResourceLocation.parse(nbt.getString("exterior_model_id")));

        this.ViewingPlayerPositions = NBTUtils.getPlayerPosMap("viewer_player_map_", nbt);

        this.IsInFlight = nbt.getBoolean("isInFlight");
        this.ShouldPlayRotorAnimation = nbt.getBoolean("play_rotor_animation");
        this.FlightSoundScheme = FlightSoundHandler.GetByID(nbt.getInt("flight_sound_scheme"));
        if (this.FlightSoundScheme == null) this.FlightSoundScheme = new SmithSoundScheme();

        if (nbt.contains("doorData")) {
            DoorData doorData = new DoorData();
            doorData.deserializeNBT(nbt.getCompound("doorData"));
            this.InteriorDoorData = doorData;
        }

        if (nbt.contains("exterior_variant")) {
            this.ExteriorVariant = new Exterior(nbt.getCompound("exterior_variant"));
        } else this.ExteriorVariant = Exteriors.Get(0);

        this.LightLevel = nbt.getFloat("light_level");

        this.Increment = nbt.getInt("increment");
        this.DestinationFacing = Direction.byName(nbt.getString("destination_facing"));
        this.Facing = Direction.byName(nbt.getString("facing"));

        this.SetExteriorLocation(SpaceTimeCoordinate.of(nbt.getCompound("location")));
        this.SetDestination(SpaceTimeCoordinate.of(nbt.getCompound("destination")));
        this.doorBlock = SpaceTimeCoordinate.of(nbt.getCompound("door"));

        if (nbt.contains("exterior_dimension_key_path"))
            this.ExteriorDimensionKey = ResourceKey.create(
                    Registries.DIMENSION,
                    new ResourceLocation(
                            nbt.getString("exterior_dimension_key_namespace"),
                            nbt.getString("exterior_dimension_key_path")));
        else {
            this.NullExteriorChecksAndFixes();
            if (this.GetExteriorTile() != null)
                this.ExteriorDimensionKey = this.GetExteriorTile().getLevel().dimension();
        }

        if (nbt.contains("destination_dimension_key_path"))
            this.DestinationDimensionKey = ResourceKey.create(
                    Registries.DIMENSION,
                    new ResourceLocation(
                            nbt.getString("destination_dimension_key_namespace"),
                            nbt.getString("destination_dimension_key_path")));

        this.IsPowered = nbt.getBoolean("is_powered_on");

        if (this.ExteriorDimensionKey == null)
            this.ExteriorDimensionKey =
                    ServerLifecycleHooks.getCurrentServer().overworld().dimension();

        if (!this.IsInFlight) {
            if (this.GetExteriorTile() != null)
                this.GetExteriorTile().SetInteriorAndSyncWithBlock(this.level.dimension());
            else if (!this.level.isClientSide) this.Land();
        }

        this.flightTerminationProtocol =
                FlightTerminationProtocolEnum.GetFromName(nbt.getString("flight_termination_protocol"));
    }

    @Override
    public Exterior GetExteriorModel() {
        return this.ExteriorModelID;
    }

    @Override
    public void SetExteriorModel(Exterior Exterior) {
        this.ExteriorModelID = Exterior;
    }

    @Override
    public SpaceTimeCoordinate GetDestination() {
        if (this.Destination.getLevel() == null) this.Destination.setLevel(this.DestinationDimensionKey);
        return this.Destination.copy();
    }

    @Override
    public void SetDestination(SpaceTimeCoordinate Destination) {
        this.Destination = Destination.copy();
    }

    @Override
    public Level GetLevel() {
        return this.level;
    }

    @Override
    public void SetPlayRotorAnimation(boolean PlayAnimation) {
        this.ShouldPlayRotorAnimation = PlayAnimation;
    }

    @Override
    public boolean ShouldPlayRotorAnimation() {
        return this.ShouldPlayRotorAnimation;
    }

    @Override
    public void SetDestinationFacing(Direction Facing) {
        this.DestinationFacing = Facing;
    }

    @Override
    public void SetFacing(Direction Facing) {
        this.Facing = Facing;
    }

    @Override
    public Direction GetDestinationFacing() {
        return this.DestinationFacing;
    }

    @Override
    public Direction GetFacing() {
        return this.Facing;
    }

    @Override
    public UUID GetOwnerID() {
        return this.OwnerUUID;
    }

    @Override
    public void SetOwner(UUID owner) {
        this.OwnerUUID = owner;
    }

    @Override
    public Map<UUID, PlayerPosition> GetViewingMap() {
        return this.ViewingPlayerPositions;
    }

    @Override
    public boolean IsViewingTARDIS(UUID player) {
        return this.ViewingPlayerPositions.containsKey(player);
    }

    @Override
    public void SetViewing(UUID player, PlayerPosition position) {
        this.ViewingPlayerPositions.put(player, position);
    }

    @Override
    public boolean CanFly() {
        return this.GetSubsystemsData().getDematerializationCircuit().isActivated(this.level) && this.IsPowered;
    }

    @Override
    public Direction NextDestinationFacing() {
        return this.DestinationFacing.getClockWise();
    }

    @Override
    public ResourceKey<Level> GetCurrentLevel() {
        if (this.Location.getLevelKey() != null) return this.Location.getLevelKey();
        if (this.level.isClientSide && this.ExteriorDimensionKey == null) this.UpdateClient();
        if (this.ExteriorDimensionKey == null) {
            if (this.GetExteriorTile() != null) {
                this.ExteriorDimensionKey = this.GetExteriorTile().getLevel().dimension();
            } else if (!this.level.isClientSide)
                this.SetCurrentLevel(this.level.getServer().overworld().dimension());
        }

        return this.ExteriorDimensionKey == null ? Level.OVERWORLD : this.ExteriorDimensionKey;
    }

    @Override
    public void SetCurrentLevel(ResourceKey<Level> exteriorLevel) {
        this.Location.setLevel(exteriorLevel);
        this.ExteriorDimensionKey = exteriorLevel;
    }

    @Override
    @Nullable
    public ExteriorTile GetExteriorTile() {
        if (!this.IsInFlight) {
            if (this.exteriorTile == null) {
                if (!this.level.isClientSide && this.level.getServer() == null) {
                    if (this.level.getServer().getLevel(this.ExteriorDimensionKey) != null) {
                        ServerLevel tardisLevel = this.level.getServer().getLevel(this.ExteriorDimensionKey);

                        assert tardisLevel != null;
                        if (tardisLevel.getBlockEntity(
                                        this.GetExteriorLocation().GetBlockPos())
                                != null)
                            this.exteriorTile = (ExteriorTile) tardisLevel.getBlockEntity(
                                    this.GetExteriorLocation().GetBlockPos());
                        else {
                            tardisLevel.setChunkForced(
                                    (int) (this.GetExteriorLocation().GetX() / 16),
                                    (int) (this.GetExteriorLocation().GetZ() / 16),
                                    true);

                            this.exteriorTile = (ExteriorTile) tardisLevel.getBlockEntity(
                                    this.GetExteriorLocation().GetBlockPos());

                            tardisLevel.setChunkForced(
                                    (int) (this.GetExteriorLocation().GetX() / 16),
                                    (int) (this.GetExteriorLocation().GetZ() / 16),
                                    false);

                            return this.exteriorTile;
                        }
                    }
                }
            } else return this.exteriorTile;
        }
        return null;
    }

    @Override
    public void SetExteriorTile(ExteriorTile exteriorTile) {
        this.exteriorTile = exteriorTile;
        this.Location = new SpaceTimeCoordinate(
                exteriorTile.getBlockPos(), exteriorTile.getLevel().dimension());
        assert exteriorTile.getLevel() != null;
        this.ExteriorDimensionKey = exteriorTile.getLevel().dimension();
    }

    @Override
    public void SetExteriorLocation(SpaceTimeCoordinate loc) {
        this.Location = loc.copy();
    }

    @Override
    public SpaceTimeCoordinate GetExteriorLocation() {
        if (!this.level.isClientSide && this.GetExteriorTile() != null && this.Location == null) {
            return new SpaceTimeCoordinate(
                    this.GetExteriorTile().getBlockPos(),
                    this.GetExteriorTile().getLevel().dimension());
        }
        return this.Location.copy();
    }

    @Override
    public SpaceTimeCoordinate GetDoorBlock() {
        return this.doorBlock;
    }

    @Override
    public void SetDoorBlock(SpaceTimeCoordinate doorBlock) {
        this.doorBlock = doorBlock;
    }

    @Override
    public DoorData GetDoorData() {
        if (this.InteriorDoorData == null) {
            // if(this.level != null && !this.level.isClientSide) {
            // List<>this.level.blockEn.getEntitiesOfClass(DoorTile.class, new AABB(new
            // BlockPos(0, 128, 0)).inflate(100));
            // }
            this.InteriorDoorData = new DoorData(0, new SpaceTimeCoordinate(new BlockPos(0, 128, 0)));
        }
        return this.InteriorDoorData;
    }

    @Override
    public void SetDoorData(DoorData doorBlock) {
        this.InteriorDoorData = doorBlock;
    }

    @Override
    public AbstractSoundScheme GetFlightScheme() {
        if (this.FlightSoundScheme == null) this.FlightSoundScheme = new SmithSoundScheme();
        return this.FlightSoundScheme;
    }

    @Override
    public void SetSoundScheme(AbstractSoundScheme SoundScheme) {
        this.FlightSoundScheme = SoundScheme;
    }

    @Override
    public boolean IsInFlight() {
        return this.IsInFlight;
    }

    @Override
    public boolean IsTakingOff() {
        return this.ShouldPlayRotorAnimation && !this.IsInFlight;
    }

    @Override
    public void SetInFlight(boolean IsInFlight) {
        if (!this.level.isClientSide) {
            this.IsInFlight = IsInFlight;
        }
    }

    @Override
    public void Fly() {
        // Null exterior checks!
        this.NullExteriorChecksAndFixes();

        //////////////////////// CALCULATIONS START ////////////////////////
        // Set distance between the location and destination
        double Distance = this.GetDestination()
                .GetBlockPos()
                .getCenter()
                .distanceTo(this.GetExteriorLocation().GetBlockPos().getCenter());
        // Set reach destination ticks
        this.SetTicksTillReachedDestination((int) Distance * 20);
        //////////////////////// CALCULATIONS END ////////////////////////
        // Make sure this is powered before continuing
        if (!this.CanFly()) return;
        if (this.GetLevel().isClientSide()) return;
        if (this.GetExteriorTile() == null) return;
        ExteriorTile ext = this.GetExteriorTile();

        // Set the TARDIS in flight
        this.SetInFlight(true);

        assert ext != null;
        Level exteriorLevel = ext.getLevel();
        assert exteriorLevel != null;
        exteriorLevel
                .getServer()
                .getLevel(exteriorLevel.dimension())
                .setChunkForced(
                        (int) (this.GetExteriorLocation().GetX() / 16),
                        (int) (this.GetExteriorLocation().GetZ() / 16),
                        true);

        ext.UtterlyDestroy();

        exteriorLevel
                .getServer()
                .getLevel(exteriorLevel.dimension())
                .setChunkForced(
                        (int) (this.GetExteriorLocation().GetX() / 16),
                        (int) (this.GetExteriorLocation().GetZ() / 16),
                        false);
        this.UpdateClient();
        MinecraftForge.EVENT_BUS.post(new TardisEvent.TakeOff(this, TardisEvent.State.END));
    }

    @Override
    public void Dematerialize() {
        if (!this.CanFly()) return;

        if (this.IsInFlight() && !this.IsTakingOff()) return;

        MinecraftForge.EVENT_BUS.post(new TardisEvent.TakeOff(this, TardisEvent.State.START));

        if (this.GetExteriorTile() == null) {
            // Makes it so the TARDIS is supposed to be in-flight
            this.SetInFlight(true);
            this.GetFlightScheme().GetTakeoff().SetFinished(true);
            // Lands the TARDIS, creating an exterior
            this.UpdateClient();
            this.Rematerialize();
        } else {
            // Start a new Takeoff thread
            this.SetPlayRotorAnimation(true);
            new TakeOffThread(this).start();
        }
    }

    @Override
    public void Rematerialize() {
        if (this.level.isClientSide) return;
        if (!this.IsInFlight) return;

        MinecraftForge.EVENT_BUS.post(new TardisEvent.Land(this, TardisEvent.State.START));
        // TODO: This
        new LandThread(this).start();
    }

    @Override
    public void Land() {
        this.IsInFlight = false;
        if (!this.GetLevel().isClientSide) {

            ServerLevel CurrentLevel = this.GetLevel().getServer().getLevel(this.GetCurrentLevel());
            assert CurrentLevel != null;
            CurrentLevel.setChunkForced(
                    (int) (this.GetDestination().GetX() / 16),
                    (int) (this.GetDestination().GetZ() / 16),
                    true);

            BlockPos pos = BlockHelper.snapToGround(
                    this.GetLevel(), this.GetDestination().GetBlockPos());

            //            this.GetFlightTerminationPolicy().GetProtocol().OnLand(this, pos,
            // CurrentLevel);
            //            pos = this.GetFlightTerminationPolicy().GetProtocol().GetLandPos();

            pos = LandingTypeRegistry.UP.GetLandingPos(pos, CurrentLevel);

            SpaceTimeCoordinate coords = new SpaceTimeCoordinate(pos, CurrentLevel.dimension());

            this.SetExteriorLocation(coords.copy());
            this.SetDestination(coords.copy());
            this.SetFacing(this.GetDestinationFacing());

            BlockState exteriorBlockState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();

            CurrentLevel.setBlock(
                    this.GetDestination().GetBlockPos(), exteriorBlockState.setValue(FACING, this.GetFacing()), 3);

            ((ExteriorBlock) exteriorBlockState.getBlock())
                    .SetInteriorKey(this.GetLevel().dimension());

            this.GetLevel().setBlockAndUpdate(coords.GetBlockPos(), exteriorBlockState);
            if (CurrentLevel.getBlockEntity(pos) != null) {
                BlockPos finalPos = pos; // pos used in lambda must be final or effectively final
                CurrentLevel.getServer().execute(new TickTask(1, () -> {
                    this.SetExteriorTile(((ExteriorTile) CurrentLevel.getBlockEntity(finalPos)));
                }));
            }
            CurrentLevel.setChunkForced(
                    (int) (this.GetDestination().GetX() / 16),
                    (int) (this.GetDestination().GetZ() / 16),
                    false);
            this.SetPlayRotorAnimation(false);
            this.UpdateClient();
        }

        MinecraftForge.EVENT_BUS.post(new TardisEvent.Land(this, TardisEvent.State.END));
        this.SetExteriorLocation(this.GetDestination());
        this.NullExteriorChecksAndFixes();
    }

    @Override
    public void Crash() {
        if (this.level.isClientSide) return;
        if (!this.IsInFlight) return;
        MinecraftForge.EVENT_BUS.post(new TardisEvent.Crash(this, TardisEvent.State.START));
        this.IsInFlight = false;
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

    @Override
    public int GetTicksTillReachedDestination() {
        return this.TicksTillDestination;
    }

    @Override
    public int GetTicksInFlight() {
        return this.TicksInFlight;
    }

    @Override
    public void SetTicksTillReachedDestination(int ticks) {
        this.TicksTillDestination = ticks;
    }

    @Override
    public void SetTicksInFlight(int ticks) {
        this.TicksInFlight = ticks;
    }

    @Override
    public void SetPowered(boolean IsPoweredOn) {
        this.IsPowered = IsPoweredOn;
        if (this.IsInFlight && !IsPoweredOn) {
            this.Crash();
        }
    }

    @Override
    public boolean IsPoweredOn() {
        return this.IsPowered;
    }

    @Override
    public boolean IsDiscoMode() {
        return this.IsDiscoMode;
    }

    @Override
    public int GetIncrement() {
        return this.Increment;
    }

    @Override
    public int GetNextIncrement() {
        return switch (this.Increment) {
            case 1 -> 10;
            case 10 -> 100;
            case 100 -> 1000;
            case 1000 -> 10000;
            case 10000 -> 100000;
            default -> 1;
        };
    }

    @Override
    public int GetPreviousIncrement() {
        return switch (this.Increment) {
            case 100000 -> 10000;
            case 10000 -> 1000;
            case 1000 -> 100;
            case 100 -> 10;
            case 10 -> 1;
            default -> 100000;
        };
    }

    /**
     * TODO: ADD MORE HERE!
     **/
    public void NullExteriorChecksAndFixes() {
        if (!this.level.isClientSide)
            if (this.level.getServer().getLevel(this.GetCurrentLevel()) == null)
                this.SetCurrentLevel(this.level.getServer().overworld().dimension());
    }

    public void UpdateClient() {
        if (this.level.isClientSide)
            Networking.sendPacketToDimension(
                    this.level.dimension(), new TriggerSyncCapPacketC2S(this.level.dimension()));
        else {
            Networking.sendPacketToDimension(
                    this.level.dimension(),
                    new SyncTARDISCapPacketS2C(
                            this.LightLevel,
                            this.IsPowered,
                            this.IsInFlight,
                            this.ShouldPlayRotorAnimation,
                            this.GetDestination().GetBlockPos(),
                            this.GetExteriorLocation().GetBlockPos(),
                            this.GetCurrentLevel(),
                            this.GetExteriorModel().GetModelName()));

            if (this.GetExteriorTile() != null) {
                this.GetExteriorTile().Variant = this.GetExteriorVariant();
                this.GetExteriorTile().setModelIndex(this.GetExteriorModel().GetModelName());
                this.GetExteriorTile().setChanged();
                this.GetExteriorTile().NeedsClientUpdate();
            }
        }
    }

    @Override
    public void Tick() {
        this.ticks++;
        if (this.IsInFlight && this.TicksInFlight > 0) {
            this.TicksInFlight -= this.GetControlData().GetArtronPacketOutput();
        }
    }

    @Override
    public void SetIncrement(int IncrementValue) {
        this.Increment = IncrementValue;
    }

    @Override
    public Exterior GetExteriorVariant() {
        if (this.ExteriorVariant == null) {
            Networking.sendToServer(new TriggerSyncCapVariantPacketC2S(this.level.dimension()));
            return Exteriors.EXTERIORS.get(0);
        }
        return this.ExteriorVariant;
    }

    @Override
    public void SetExteriorVariant(Exterior exteriorVariant) {
        this.ExteriorVariant = exteriorVariant;
        if (this.GetExteriorTile() != null) {
            this.GetExteriorTile().Variant = exteriorVariant;
            this.GetExteriorTile().setModelIndex(exteriorVariant.GetModelName());
            this.GetExteriorTile().NeedsClientUpdate();
            this.GetExteriorTile().setChanged();
        }
    }

    @Override
    public void CycleVariant() {
        this.ExteriorVariant = Exteriors.Cycle(this.ExteriorVariant);
    }

    @Override
    public float GetLightLevel() {
        if (this.LightLevel == 0.0f) {
            Networking.sendToServer(new TriggerSyncCapLightPacketC2S(this.level.dimension()));
            return 0.1f;
        }
        return this.LightLevel;
    }

    @Override
    public void SetLightLevel(float LightLevel) {
        LightLevel = Math.max(Math.min(LightLevel, 1.5F), 0.1F);
        this.LightLevel = LightLevel;
    }

    @Override
    public FlightTerminationProtocolEnum GetFlightTerminationPolicy() {
        return this.flightTerminationProtocol;
    }

    @Override
    public void SetFlightTerminationPolicy(FlightTerminationProtocolEnum policy) {
        this.flightTerminationProtocol = policy;
    }

    @Override
    public SubsystemsData GetSubsystemsData() {
        return this.SubsystemsData;
    }

    @Override
    public ProtocolData GetProtocolData() {
        return this.ProtocolData;
    }

    @Override
    public ControlParameters GetControlData() {
        return this.ControlData;
    }

    @Override
    public long getTicks() {
        return this.ticks;
    }

    @Nullable
    Player GetOwner() {
        if (this.level.isClientSide) return null;
        //        return this.level.getServer().overworld().getPlayerByUUID(this.GetOwnerID());
        return this.level.getServer().getPlayerList().getPlayer(this.OwnerUUID);
    }

    public void ForceLoadExteriorChunk(boolean ForceLoad) {
        this.GetDestination()
                .getLevel()
                .getServer()
                .getLevel(this.DestinationDimensionKey)
                .setChunkForced(
                        (int) (this.GetExteriorLocation().GetX() / 16),
                        (int) (this.GetExteriorLocation().GetZ() / 16),
                        false);
    }
}
