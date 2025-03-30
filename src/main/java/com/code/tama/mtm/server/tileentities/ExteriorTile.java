package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.MTMTileEntities;
import com.code.tama.mtm.server.blocks.ExteriorBlock;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.enums.Structures;
import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.networking.packets.exterior.SyncExteriorVariantPacket;
import com.code.tama.mtm.server.networking.packets.exterior.SyncTransparencyPacket;
import com.code.tama.mtm.server.threads.PlaceStructureThread;
import com.code.tama.mtm.server.misc.ExteriorVariant;
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

import static com.code.tama.mtm.MTMMod.MODID;

public class ExteriorTile extends BlockEntity {
    private ResourceKey<Level> INTERIOR_DIMENSION;
    private float transparency = 1.0f; // Default fully visible
    private int transparencyInt; // Default fully visible
    public ExteriorVariant Variant;
    int DoorState;

    public ExteriorTile(BlockPos p_155229_, BlockState p_155230_) {
        super(MTMTileEntities.EXTERIOR_TILE.get(), p_155229_, p_155230_);
    }

    public int DoorsOpen() {
        return this.DoorState;
    }

    public int CycleDoors() {
        this.SetDoorsOpen(switch(this.DoorsOpen()) {
            case 0 -> 1;
            case 1 -> 2;
            default -> 0;
        });
        return this.DoorsOpen();
    }

    public void SetDoorsOpen(int doorState) {
        this.DoorState = doorState;
    }

    public ExteriorVariant GetVariant() {
        return this.Variant;
    }

    public void CycleVariant() {
        this.Variant = ExteriorVariants.Cycle(this.Variant);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        if(this.INTERIOR_DIMENSION != null)
            tag.putString("interior_path", this.INTERIOR_DIMENSION.location().getPath());
        if(this.Variant == null){
            this.setVariant(ExteriorVariants.GetOrdinal(ExteriorVariants.Variants.get(0)));
            tag.put("variant", ExteriorVariants.Variants.get(0).serializeNBT());
        }
        else
            tag.put("variant", this.Variant.serializeNBT());

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        if(tag.contains("interior_path")) {
            this.INTERIOR_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(MODID, tag.getString("interior_path")));
//            if(this.getLevel() != null)
//            if(this.GetBlock() != null) {
//                this.GetBlock().SetInteriorKey(this.INTERIOR_DIMENSION);
//            }
        }
        this.setVariant(ExteriorVariants.GetOrdinal(new ExteriorVariant(tag.getCompound("variant"))));
        super.load(tag);
    }

    /** NEVER RUN THIS! THIS SHOULD ONLY BE USED ONCE, AND THAT IS WHEN IT'S FIRST INITIALIZED WHEN THE DIMENSION IS CREATED **/
    public void Init(ResourceKey<Level> LevelKey) {
        this.INTERIOR_DIMENSION = LevelKey;
        this.PlaceInterior(Structures.CleanInterior);
    }

    public void PlaceInterior(Structures structure) {
        if (this.getLevel().isClientSide) return;
        new PlaceStructureThread(this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION), new BlockPos(0, 128, 0), structure).start();
        this.setChanged();
    }

    public ResourceKey<Level> GetInterior() {
        return this.INTERIOR_DIMENSION;
    }

    public void SetInteriorAndSyncWithBlock(ResourceKey<Level> INTERIOR_DIMENSION) {
        this.INTERIOR_DIMENSION = INTERIOR_DIMENSION;
        assert this.level != null;
        if(this.level.getBlockState(this.getBlockPos()).getBlock() instanceof ExteriorBlock exteriorBlock) {
            exteriorBlock.SetInteriorKey(this.INTERIOR_DIMENSION);
            this.setChanged();
        }
    }

    @Nullable public ExteriorBlock GetBlock() {
        assert this.level != null;
        Block block = this.level.getServer().getLevel(this.level.dimension()).getBlockState(this.getBlockPos()).getBlock();
        if(block instanceof ExteriorBlock exteriorBlock) return exteriorBlock;
        else return null;
    }

    /** Utterly Destroys the tile entity and the linked {@link ExteriorBlock} like, 4 different ways, scheduling a tick that destroys it, removing it through servers level methods, etc**/
    public void UtterlyDestroy() {
        assert this.level != null;
//        this.level.getServer().execute(new TickTask(5, () -> {
        if(this.GetBlock() != null) {
            this.GetBlock().MarkForRemoval();
            this.level.getServer().getLevel(this.level.dimension()).scheduleTick(this.getBlockPos(), this.GetBlock(), 1);
        }
        this.level.getServer().getLevel(this.level.dimension()).removeBlock(this.getBlockPos(), false);
        this.level.getServer().getLevel(this.level.dimension()).removeBlockEntity(this.getBlockPos());
//        }));
//        this.setRemoved();
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof ExteriorTile exteriorTile) {

//            if (blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide) {
//                if (blockEntity.getLevel().getServer().getLevel(exteriorTile.GetInterior()) != null) {
//                    blockEntity.getLevel().getServer().getLevel(exteriorTile.GetInterior()).getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
//                        float newAlpha = (float) Math.abs(Math.sin(level.getGameTime() * 0.05)); // Oscillating transparency
//                        int newAlpha2 = (int) Math.abs(Math.sin(level.getGameTime())); // Oscillating transparency
//                        exteriorTile.setTransparency(newAlpha);
//                        exteriorTile.setTransparencyInt(newAlpha2);
//                    });
//                }
//                }
//            }
//            mtm.exteriorTileTickThread.Init(level, pos, state, exteriorTile);
//            mtm.exteriorTileTickThread.run();
            if (exteriorTile.GetInterior() == null) return;

//            if (level.getBlockState(pos).getBlock() instanceof ExteriorBlock exteriorBlock)
//                if (exteriorBlock.GetInteriorKey() == null)
//                    if (exteriorTile.GetInterior() != null)
//                        exteriorTile.SetInteriorAndSyncWithBlock(exteriorTile.GetInterior());


            if (!level.isClientSide) {
                level.getServer().getLevel(exteriorTile.GetInterior()).getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {


                    if ((!exteriorTile.getBlockPos().equals(cap.GetExteriorLocation().GetBlockPos()) || cap.IsInFlight())) exteriorTile.UtterlyDestroy();
                });
            }
            else {
            }
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt("TransparencyInt", this.transparencyInt);
        tag.putFloat("Transparency", this.transparency);
        tag.putInt("variant", ExteriorVariants.GetOrdinal(this.Variant));
        tag.putInt("doors", this.DoorState);
        return tag;
        //this.serializeNBT
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        //this.deserializeNBT
        super.handleUpdateTag(tag);
        if (tag.contains("Transparency")) {
            this.transparency = tag.getFloat("Transparency");
        }
        if (tag.contains("variant")) {
            this.Variant = ExteriorVariants.Get(tag.getInt("variant"));
        }
        if (tag.contains("TransparencyInt")) {
            this.transparencyInt = tag.getInt("TransparencyInt");
        }
        if(tag.contains("doors")) {
            this.DoorState = tag.getInt("doors");
        }
    }

    public void TeleportToInterior(Entity EntityToTeleport) {
        if(this.getLevel().isClientSide) return;
        if(this.INTERIOR_DIMENSION == null) return;
        ServerLevel Interior = this.getLevel().getServer().getLevel(this.INTERIOR_DIMENSION);
        assert Interior != null;
        Interior.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            int X, Y, Z;
            BlockPos pos = cap.GetDoorBlock().GetBlockPos().north();
            X = pos.getX();
            Y = pos.getY() == 0 ? 128 : pos.getY();
            Z = pos.getZ();
            EntityToTeleport.teleportTo(Interior, X, Y, Z, Set.of(), 0, 0);
        });
    }

    public float getTransparency() {
        return this.transparency;
    }
    public int getTransparencyInt() {
        return this.transparencyInt;
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
                    Networking.sendToPlayer(player, new SyncTransparencyPacket(transparency, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()));
                }
            }
        }
    }

    public void setVariant(int variant) {
        if(variant >= ExteriorVariants.Variants.size()) variant = 0;
        this.Variant = ExteriorVariants.Variants.get(variant);
        this.setChanged();

        if (this.level instanceof ServerLevel serverLevel) {
            // Notify clients of the update using a block update
            serverLevel.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

            // Send the custom packet to all nearby players (example: radius of 64 blocks)
            for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
                if (player.blockPosition().closerThan(worldPosition, 64)) {
                    Networking.sendToPlayer(player, new SyncExteriorVariantPacket(ExteriorVariants.GetOrdinal(this.Variant), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()));
                }
            }
        }
    }

    public void setClientTransparency(float alpha) {
        this.transparency = alpha;
    }

    public void setTransparencyInt(int alpha) {
        this.transparencyInt = alpha; //Math.max(0.0f, Math.min(1.0f, alpha)); // Clamp between 0 (invisible) and 1 (fully visible)
        setChanged();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
}
