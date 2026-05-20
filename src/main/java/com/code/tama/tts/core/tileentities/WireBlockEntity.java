/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.tileentities;

import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WireBlockEntity extends BlockEntity {

	// Stored as a relative offset from this block's position.
	// This means .nbt structures paste correctly regardless of where they land.
	// At runtime we resolve to an absolute BlockPos via getLinkedPos().
	@Nullable private BlockPos linkedOffset = null;

	public <T extends BlockEntity> WireBlockEntity(BlockEntityType<T> tBlockEntityType, BlockPos pos,
			BlockState state) {
		super(tBlockEntityType, pos, state);
	}

	public WireBlockEntity(BlockPos pos, BlockState state) {
		super(TTSTileEntities.WIRES.get(), pos, state);
	}

	/**
	 * Returns the absolute world position of the linked wire, or null if unlinked.
	 * Resolves the stored relative offset against this block's current
	 * worldPosition.
	 */
	@Nullable public BlockPos getLinkedPos() {
		if (linkedOffset == null)
			return null;
		return worldPosition.offset(linkedOffset);
	}

	/**
	 * Links to an absolute world position by computing and storing the offset. Pass
	 * null to unlink.
	 */
	public void setLinkedPos(@Nullable BlockPos absolutePos) {
		if (absolutePos == null) {
			linkedOffset = null;
		} else {
			linkedOffset = absolutePos.subtract(worldPosition);
		}
		setChanged();
		if (level != null && !level.isClientSide) {
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
		}
	}

	// -------------------------------------------------------------------------
	// NBT save / load — persists the relative offset
	// -------------------------------------------------------------------------

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (linkedOffset != null) {
			tag.putInt("link_dx", linkedOffset.getX());
			tag.putInt("link_dy", linkedOffset.getY());
			tag.putInt("link_dz", linkedOffset.getZ());
		}
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("link_dx")) {
			linkedOffset = new BlockPos(tag.getInt("link_dx"), tag.getInt("link_dy"), tag.getInt("link_dz"));
		} else {
			linkedOffset = null;
		}
	}

	// -------------------------------------------------------------------------
	// Client sync — send full NBT on block update
	// -------------------------------------------------------------------------

	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}

	@Nullable @Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
}