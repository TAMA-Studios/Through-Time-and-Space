package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.blocks.AbstractMonitorBlock;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
@Getter @Setter
public abstract class AbstractMonitorTile extends BlockEntity {
    public int categoryID = 1;
    public boolean powered = false;
    
    public AbstractMonitorTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public void onLoad() {
        this.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(ITARDISLevel::UpdateClient);
        super.onLoad();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putBoolean("powered", isPowered());
        tag.putInt("categoryID", getCategoryID());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        setPowered(tag.getBoolean("powered"));
        setCategoryID(tag.getInt("categoryID"));
        super.load(tag);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        setPowered(tag.getBoolean("powered"));
        setCategoryID(tag.getInt("categoryID"));
        super.handleUpdateTag(tag);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("powered", powered);
        tag.putInt("categoryID", categoryID);
        return tag;
    }
    
    public AbstractMonitorBlock GetBlock() {
        return ((AbstractMonitorBlock) this.getBlockState().getBlock());
    }
}