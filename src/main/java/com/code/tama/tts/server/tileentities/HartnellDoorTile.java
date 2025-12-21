/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.blocks.cosmetic.HartnellDoor;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HartnellDoorTile extends BlockEntity {
	private boolean formed = false;
	public boolean IsOpen = false;

	public HartnellDoorTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public boolean IsOpen() {
		return ((HartnellDoor) this.level.getBlockState(this.getBlockPos()).getBlock()).IsOpen();
	}

	public void SetIsOpen(boolean IsOpen) {
		this.IsOpen = IsOpen;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.formed = tag.getBoolean("Formed");
		this.IsOpen = tag.getBoolean("IsOpen");
		((HartnellDoor) this.getBlockState().getBlock()).SetIsOpen(this.IsOpen);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = this.serializeNBT();

		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		super.handleUpdateTag(tag);
		deserializeNBT(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		deserializeNBT(tag);
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("Formed", this.formed);
		tag.putBoolean("IsOpen", this.IsOpen);
		return tag;
	}

	public void setFormed(boolean formed) {
		this.formed = formed;
		setChanged();
	}
}
