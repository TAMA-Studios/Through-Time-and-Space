package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.MTMTileEntities;
import com.code.tama.mtm.server.blocks.HartnellDoorMultiBlock;
import com.code.tama.mtm.server.misc.SpaceTimeCoordinate;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HartnellDoorTilePlaceholder extends BlockEntity {
    private SpaceTimeCoordinate Controller;
    private boolean IsOpen = false;

    public HartnellDoorTilePlaceholder(BlockPos pos, BlockState state) {
        super(MTMTileEntities.HARTNELL_DOOR_PLACEHOLDER.get(), pos, state);
    }

    public void setFormed(SpaceTimeCoordinate Controller) {
        this.Controller = Controller;
    }

    public SpaceTimeCoordinate GetController() {
        return this.Controller;
    }

    public void SetController(BlockPos pos) {
        this.Controller = new SpaceTimeCoordinate(pos);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        this.Controller = SpaceTimeCoordinate.of(compoundTag.getCompound("controller"));
        this.IsOpen = compoundTag.getBoolean("is_open");
        ((HartnellDoorMultiBlock) this.getBlockState().getBlock()).IsOpen = this.IsOpen;
        ((HartnellDoorMultiBlock) this.getBlockState().getBlock()).SetController(this.GetController().GetBlockPos());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.SetController(((HartnellDoorMultiBlock) this.level.getServer().getLevel(this.level.dimension()).getBlockState(this.worldPosition).getBlock()).GetController());
        nbt.put("controller", this.Controller.serializeNBT());
        nbt.putBoolean("is_open", this.IsOpen);
    }
}

