/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.triggerapi.MathUtils;
import com.code.tama.triggerapi.WorldHelper;
import com.code.tama.triggerapi.dimensions.DimensionAPI;
import com.code.tama.triggerapi.dimensions.DimensionManager;
import com.code.tama.tts.Exteriors;
import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.enums.Structures;
import com.code.tama.tts.server.events.TardisEvent;
import com.code.tama.tts.server.misc.Exterior;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.exterior.TriggerSyncExteriorVariantPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.exterior.SyncTransparencyPacketS2C;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.tardis.data.DataUpdateValues;
import com.code.tama.tts.server.threads.GetExteriorVariantThread;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExteriorTile extends AbstractPortalTile {
    public boolean ShouldMakeDimOnNextTick = false, IsEmptyShell = true;
    public LivingEntity Placer;
    public ExteriorAnimationData exteriorAnimationData = new ExteriorAnimationData();

    public boolean ThreadWorking = false;
    public Exterior Model;
    int DoorState;

    @Getter
    @Setter
    ResourceLocation ModelIndex = Exteriors.EXTERIORS.get(0).getModel();

    private ResourceKey<Level> INTERIOR_DIMENSION;

    @Getter
    private float transparency = 1.0f; // Default fully visible

    @Getter
    private int transparencyInt; // Default fully visible

    public ExteriorTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.EXTERIOR_TILE.get(), pos, state);
    }

    public int CycleDoors() {
        this.SetDoorsOpen(
                switch (this.DoorsOpen()) {
                    case 0 -> 1;
                    case 1 -> 2;
                    default -> 0;
                });
        return this.DoorsOpen();
    }

    public int DoorsOpen() {
        if (this.level != null && this.GetInterior() != null && !this.level.isClientSide)
            return this.level
                    .getServer()
                    .getLevel(this.GetInterior())
                    .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                    .orElse(new TARDISLevelCapability(
                            this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION)))
                    .GetData()
                    .getInteriorDoorData()
                    .getDoorsOpen();
        else return this.DoorState;
    }

    @Nullable public ExteriorBlock GetBlock() {
        if (this.level.getServer() != null) {
            assert this.level != null;
            Block block = this.level
                    .getServer()
                    .getLevel(this.level.dimension())
                    .getBlockState(this.getBlockPos())
                    .getBlock();
            if (block instanceof ExteriorBlock exteriorBlock) return exteriorBlock;
        }
        return null;
    }

    public ResourceKey<Level> GetInterior() {
        return this.INTERIOR_DIMENSION;
    }

    public Exterior GetVariant() {
        this.UpdateVariant();
        return this.Model == null ? Exteriors.Get(0) : this.Model;
    }

    public void NeedsClientUpdate() {
        if (this.level == null) return;
        if (this.level.isClientSide) return;
        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
    }

    public void PlaceInterior(Structures structure) {
        if (this.getLevel().isClientSide) return;
        WorldHelper.PlaceStructure(
                this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION),
                new BlockPos(MathUtils.RoundTo48(0), MathUtils.RoundTo48(128), MathUtils.RoundTo48(0)),
                structure.GetRL());
        this.setChanged();
    }

    public void SetDoorsOpen(int doorState) {
        assert this.level != null;
        if (!this.level.isClientSide)
            this.level
                    .getServer()
                    .getLevel(this.INTERIOR_DIMENSION)
                    .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                    .ifPresent(cap -> cap.GetData().getInteriorDoorData().setDoorsOpen(doorState));
        this.DoorState = doorState;
    }

    public void SetInteriorAndSyncWithBlock(ResourceKey<Level> INTERIOR_DIMENSION) {
        this.INTERIOR_DIMENSION = INTERIOR_DIMENSION;
        this.IsEmptyShell = false;
        assert this.level != null;
        if (this.level.getBlockState(this.getBlockPos()).getBlock() instanceof ExteriorBlock exteriorBlock) {
            exteriorBlock.SetInteriorKey(this.INTERIOR_DIMENSION);
            this.setChanged();
        }
    }

    public void TeleportToInterior(Entity EntityToTeleport) {
        if (this.getLevel() == null || this.getLevel().isClientSide) return;
        if (this.INTERIOR_DIMENSION == null) return;

        // Don't teleport if the entity in question is viewing the exterior via Environment Scanner
        if (EntityToTeleport.getCapability(Capabilities.PLAYER_CAPABILITY).isPresent()
                && !Objects.equals(
                        EntityToTeleport.getCapability(Capabilities.PLAYER_CAPABILITY)
                                .orElse(null)
                                .GetViewingTARDIS(),
                        "")) return;

        ServerLevel Interior = this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION);
        assert Interior != null;
        Interior.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            MinecraftForge.EVENT_BUS.post(
                    new TardisEvent.EntityEnterTARDIS(cap, TardisEvent.State.START, EntityToTeleport));
            float X, Y, Z;
            BlockPos pos = cap.GetData()
                    .getDoorData()
                    .getLocation()
                    .GetBlockPos()
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
            MinecraftForge.EVENT_BUS.post(
                    new TardisEvent.EntityEnterTARDIS(cap, TardisEvent.State.END, EntityToTeleport));
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
        if (tag.contains("modelPath") && tag.contains("modelNamespace")) {
            this.ModelIndex = new ResourceLocation(tag.getString("modelNamespace"), tag.getString("modelPath"));
        } else {
            this.ModelIndex = Exteriors.EXTERIORS.get(0).getModel();
        }
        if (tag.contains("model")) {
            this.Model = Exterior.CODEC
                    .parse(NbtOps.INSTANCE, tag.get("model"))
                    .get()
                    .orThrow();
        }

        if (tag.contains("IsEmptyShell")) {
            this.IsEmptyShell = tag.getBoolean("IsEmptyShell");
        }

        if (tag.contains("interior")) {
            this.INTERIOR_DIMENSION = ResourceKey.create(
                    Registries.DIMENSION, new ResourceLocation(MODID + "-tardis", tag.getString("interior")));
            this.targetLevel = this.INTERIOR_DIMENSION;
            this.IsEmptyShell = false;
        }

        super.load(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.level != null && this.level.isClientSide)
            Networking.sendToServer(new TriggerSyncExteriorVariantPacketC2S(
                    this.level.dimension(),
                    this.getBlockPos().getX(),
                    this.getBlockPos().getY(),
                    this.getBlockPos().getZ()));
    }

    public void setClientTransparency(float alpha) {
        this.transparency = alpha;
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
                    Networking.sendToPlayer(
                            player,
                            new SyncTransparencyPacketS2C(
                                    transparency, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()));
                }
            }
        }
    }

    public void setTransparencyInt(int alpha) {
        this.transparencyInt = alpha; // Math.max(0.0f, Math.min(1.0f, alpha)); // Clamp between 0 (invisible) and 1
        // (fully visible)
        setChanged();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setModel(int model) {
        if (model >= Exteriors.EXTERIORS.size()) model = 0;
        this.Model = Exteriors.EXTERIORS.get(model);
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

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("TransparencyInt", this.transparencyInt);
        tag.putBoolean("IsEmptyShell", this.IsEmptyShell);

        if (this.INTERIOR_DIMENSION != null)
            Capabilities.getCap(
                            Capabilities.TARDIS_LEVEL_CAPABILITY,
                            this.level.getServer().getLevel(this.INTERIOR_DIMENSION))
                    .ifPresent(cap -> {
                        if (cap.GetExteriorTile() == this) {
                            this.ModelIndex = cap.GetData().getExteriorModel().getModel();
                            this.Model = cap.GetData().getExteriorModel();
                        }
                    });
        tag.putString("modelPath", this.getModelIndex().getPath());
        tag.putString("modelNamespace", this.getModelIndex().getNamespace());
        tag.putFloat("Transparency", this.transparency);
        Exterior.CODEC.encode(this.GetVariant(), NbtOps.INSTANCE, tag);
        if (this.level.getServer().getLevel(this.INTERIOR_DIMENSION) != null)
            if (this.level
                    .getServer()
                    .getLevel(this.INTERIOR_DIMENSION)
                    .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                    .isPresent())
                this.level
                        .getServer()
                        .getLevel(this.INTERIOR_DIMENSION)
                        .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                        .ifPresent(cap ->
                                tag.putInt("doors", cap.GetData().getDoorData().getDoorsOpen()));
            else tag.putInt("doors", this.DoorState);
        if (this.INTERIOR_DIMENSION != null)
            tag.putString("interior", this.INTERIOR_DIMENSION.location().getPath());

        super.saveAdditional(tag);
    }

    @Override
    public void onChunkUnloaded() {
        if (!this.level.isClientSide) {
            Capabilities.getCap(
                            Capabilities.TARDIS_LEVEL_CAPABILITY,
                            ServerLifecycleHooks.getCurrentServer().getLevel(this.INTERIOR_DIMENSION))
                    .ifPresent(tadis -> {
                        if (tadis.GetFlightData().IsTakingOff()
                                || tadis.GetFlightData().isInFlight()) {
                            this.UtterlyDestroy();
                        }
                    });
            super.onChunkUnloaded();
        }
    }

    @Override
    public void tick() {
        if (this.INTERIOR_DIMENSION == null) {
            if (!this.IsEmptyShell) this.UtterlyDestroy();
        } else this.IsEmptyShell = false;

        if (this.ShouldMakeDimOnNextTick) {
            if (level.isClientSide || level.getServer() == null) return;
            level.getServer().execute(() -> {
                ResourceKey<Level> resourceKey = ResourceKey.create(
                        Registries.DIMENSION,
                        new ResourceLocation(
                                MODID + "-tardis",
                                "created-"
                                        + LocalDate.now()
                                        + "-by-"
                                        + this.Placer.getName().getString().toLowerCase()
                                        + "-uuid-"
                                        + UUID.randomUUID()));
                ServerLevel tardisLevel = DimensionAPI.get()
                        .getOrCreateLevel(
                                level.getServer(),
                                resourceKey,
                                () -> DimensionManager.CreateTARDISLevelStem(level.getServer()));

                ((ExteriorBlock) this.getBlockState().getBlock()).SetInteriorKey(tardisLevel.dimension());

                tardisLevel.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent((cap) -> {
                    cap.SetExteriorTile(this);
                    assert this.getLevel() != null;
                    cap.GetNavigationalData()
                            .setExteriorDimensionKey(this.getLevel().dimension());
                    cap.GetNavigationalData().SetExteriorLocation(new SpaceTimeCoordinate(this.getBlockPos()));
                    assert this.Placer != null;
                    cap.GetData().setOwnerUUID(this.Placer.getUUID());
                });

                this.INTERIOR_DIMENSION = tardisLevel.dimension();
                this.PlaceInterior(Structures.CleanInterior);
            });
            this.ShouldMakeDimOnNextTick = false;
            this.IsEmptyShell = false;
        }

        if (this.GetInterior() == null) return;

        if (level != null && !level.isClientSide && level.getServer().getLevel(this.GetInterior()) != null) {
            level.getServer()
                    .getLevel(this.GetInterior())
                    .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                    .ifPresent(cap -> {
                        if ((!this.getBlockPos()
                                        .equals(cap.GetNavigationalData()
                                                .GetExteriorLocation()
                                                .GetBlockPos())
                                || cap.GetFlightData().isInFlight())) this.UtterlyDestroy();

                        if (this.containers.isEmpty() || this.targetLevel == null)
                            this.setTargetLevel(
                                    cap.GetLevel().dimension(),
                                    cap.GetData().getDoorData().getLocation().GetBlockPos(),
                                    cap.GetData().getDoorData().getYRot(),
                                    true);
                    });
        }
    }
}
