/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.triggerapi.MathUtils;
import com.code.tama.triggerapi.WorldHelper;
import com.code.tama.tts.Exteriors;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.enums.Structures;
import com.code.tama.tts.server.misc.Exterior;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.exterior.TriggerSyncExteriorVariantPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.exterior.SyncTransparencyPacketS2C;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.threads.GetExteriorVariantThread;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static com.code.tama.tts.TTSMod.MODID;

public class ExteriorTile extends BlockEntity {
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof ExteriorTile exteriorTile) {

            // if (blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide) {
            // if (blockEntity.getLevel().getServer().getLevel(exteriorTile.GetInterior())
            // != null) {
            // blockEntity.getLevel().getServer().getLevel(exteriorTile.GetInterior()).getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap
            // -> {
            // float newAlpha = (float) Math.abs(Math.sin(level.getGameTime() * 0.05)); //
            // Oscillating transparency
            // int newAlpha2 = (int) Math.abs(Math.sin(level.getGameTime())); // Oscillating
            // transparency
            // exteriorTile.setTransparency(newAlpha);
            // exteriorTile.setTransparencyInt(newAlpha2);
            // });
            // }
            // }
            // }
            // tts.exteriorTileTickThread.Init(level, pos, state, exteriorTile);
            // tts.exteriorTileTickThread.run();
            if (exteriorTile.GetInterior() == null) return;

            // if (level.getBlockState(pos).getBlock() instanceof ExteriorBlock
            // exteriorBlock)
            // if (exteriorBlock.GetInteriorKey() == null)
            // if (exteriorTile.GetInterior() != null)
            // exteriorTile.SetInteriorAndSyncWithBlock(exteriorTile.GetInterior());

            if (level != null
                    && !level.isClientSide
                    && level.getServer().getLevel(exteriorTile.GetInterior()) != null) {
                level.getServer()
                        .getLevel(exteriorTile.GetInterior())
                        .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                        .ifPresent(cap -> {
                            if ((!exteriorTile
                                            .getBlockPos()
                                            .equals(cap.GetExteriorLocation().GetBlockPos())
                                    || cap.IsInFlight())) exteriorTile.UtterlyDestroy();
                            exteriorTile.Variant = cap.GetExteriorVariant();
                        });
            } else {
            }
        }
    }

    public boolean ThreadWorking = false;
    public Exterior Variant;
    int DoorState;

    @Getter
    @Setter
    ResourceLocation ModelIndex = Exteriors.EXTERIORS.get(0).GetModelName();

    private ResourceKey<Level> INTERIOR_DIMENSION;

    @Getter
    private float transparency = 1.0f; // Default fully visible

    @Getter
    private int transparencyInt; // Default fully visible

    public ExteriorTile(BlockPos p_155229_, BlockState p_155230_) {
        super(TTSTileEntities.EXTERIOR_TILE.get(), p_155229_, p_155230_);
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
        return this.DoorState;
    }

    @Nullable
    public ExteriorBlock GetBlock() {
        assert this.level != null;
        Block block = this.level
                .getServer()
                .getLevel(this.level.dimension())
                .getBlockState(this.getBlockPos())
                .getBlock();
        if (block instanceof ExteriorBlock exteriorBlock) return exteriorBlock;
        else return null;
    }

    public ResourceKey<Level> GetInterior() {
        return this.INTERIOR_DIMENSION;
    }

    public Exterior GetVariant() {
        this.UpdateVariant();
        return this.Variant == null ? Exteriors.Get(0) : this.Variant;
    }

    /**
     * NEVER RUN THIS! THIS SHOULD ONLY BE USED ONCE, AND THAT IS WHEN IT'S FIRST
     * INITIALIZED WHEN THE DIMENSION IS CREATED
     **/
    public void Init(ResourceKey<Level> LevelKey) {
        this.INTERIOR_DIMENSION = LevelKey;
        this.PlaceInterior(Structures.CleanInterior);
    }

    public void NeedsClientUpdate() {
        if (this.level == null) return;
        if (this.level.isClientSide) return;
        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 2);
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
        this.DoorState = doorState;
    }

    public void SetInteriorAndSyncWithBlock(ResourceKey<Level> INTERIOR_DIMENSION) {
        this.INTERIOR_DIMENSION = INTERIOR_DIMENSION;
        assert this.level != null;
        if (this.level.getBlockState(this.getBlockPos()).getBlock() instanceof ExteriorBlock exteriorBlock) {
            exteriorBlock.SetInteriorKey(this.INTERIOR_DIMENSION);
            this.setChanged();
        }
    }

    public void TeleportToInterior(Entity EntityToTeleport) {
        if (this.getLevel() == null || this.getLevel().isClientSide) return;
        if (this.INTERIOR_DIMENSION == null) return;
        ServerLevel Interior = this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION);
        assert Interior != null;
        Interior.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            float X, Y, Z;
            BlockPos pos = cap.GetDoorBlock().GetBlockPos().north();
            X = pos.getX() + 0.5f;
            Y = pos.getY() == 0 ? 128 : pos.getY();
            Z = pos.getZ() + 0.5f;
            float yRot = cap.GetDoorData().getYRot() + EntityToTeleport.getYRot();
            System.out.println(yRot);
            EntityToTeleport.teleportTo(Interior, X, Y, Z, Set.of(), yRot, 0);
        });
    }

    public void UpdateVariant() {
        new GetExteriorVariantThread(this).start();
    }

    /**
     * Utterly Destroys the tile entity and the linked {@link ExteriorBlock} like, 4
     * different ways, scheduling a tick that destroys it, removing it through
     * servers level methods, etc
     **/
    public void UtterlyDestroy() {
        assert this.level != null;
        // this.level.getServer().execute(new TickTask(5, () -> {
        if (this.GetBlock() != null) {
            this.GetBlock().MarkForRemoval();
            this.level
                    .getServer()
                    .getLevel(this.level.dimension())
                    .scheduleTick(this.getBlockPos(), this.GetBlock(), 1);
        }
        this.level.getServer().getLevel(this.level.dimension()).removeBlock(this.getBlockPos(), false);
        this.level.getServer().getLevel(this.level.dimension()).removeBlockEntity(this.getBlockPos());
        // }));
        // this.setRemoved();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt("TransparencyInt", this.transparencyInt);
        tag.putString("modelPath", this.getModelIndex().getPath());
        tag.putString("modelNamespace", this.getModelIndex().getNamespace());
        tag.putFloat("Transparency", this.transparency);
        tag.put("variant", this.GetVariant().serializeNBT());
        tag.putInt("doors", this.DoorState);
        return tag;
        // this.serializeNBT
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        // this.deserializeNBT
        if (tag.contains("Transparency")) {
            this.transparency = tag.getFloat("Transparency");
        }
        if (tag.contains("modelPath") && tag.contains("modelNamespace")) {
            this.ModelIndex = new ResourceLocation(tag.getString("modelNamespace"), tag.getString("modelPath"));
        } else {
            this.ModelIndex = Exteriors.EXTERIORS.get(0).GetModelName();
        }
        if (tag.contains("variant")) {
            this.Variant = new Exterior(tag.getCompound("variant"));
        }
        if (tag.contains("TransparencyInt")) {
            this.transparencyInt = tag.getInt("TransparencyInt");
        }
        if (tag.contains("doors")) {
            this.DoorState = tag.getInt("doors");
        }
        super.handleUpdateTag(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("modelPath") && tag.contains("modelNamespace")) {
            this.ModelIndex = new ResourceLocation(tag.getString("modelNamespace"), tag.getString("modelPath"));
        } else {
            this.ModelIndex = Exteriors.EXTERIORS.get(0).GetModelName();
        }
        if (tag.contains("interior_path")) {
            this.INTERIOR_DIMENSION = ResourceKey.create(
                    Registries.DIMENSION, new ResourceLocation(MODID, tag.getString("interior_path")));
            // if(this.getLevel() != null)
            // if(this.GetBlock() != null) {
            // this.GetBlock().SetInteriorKey(this.INTERIOR_DIMENSION);
            // }
        }
        if (tag.contains("variant")) {
            this.Variant = new Exterior(tag.getCompound("variant"));
        }
        this.setVariant(Exteriors.GetOrdinal(new Exterior(tag.getCompound("variant"))));
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

    public void setVariant(int variant) {
        if (variant >= Exteriors.EXTERIORS.size()) variant = 0;
        this.Variant = Exteriors.EXTERIORS.get(variant);
        this.setChanged();

        if (this.level instanceof ServerLevel serverLevel) {
            ServerLevel level1 = serverLevel.getServer().getLevel(this.INTERIOR_DIMENSION);
            if (level1 != null) {
                level1.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                        .ifPresent(cap -> {
                            this.ModelIndex = cap.GetExteriorModel().GetModelName();
                            this.Variant = cap.GetExteriorVariant();
                            cap.UpdateClient();

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
        tag.putString("modelPath", this.ModelIndex.getPath());
        tag.putString("modelNamespace", this.ModelIndex.getNamespace());
        if (this.INTERIOR_DIMENSION != null)
            tag.putString("interior_path", this.INTERIOR_DIMENSION.location().getPath());
        if (this.GetVariant() == null) {
            this.setVariant(Exteriors.GetOrdinal(Exteriors.EXTERIORS.get(0)));
            tag.put("variant", Exteriors.EXTERIORS.get(0).serializeNBT());
        } else tag.put("variant", this.GetVariant().serializeNBT());

        super.saveAdditional(tag);
    }
}
