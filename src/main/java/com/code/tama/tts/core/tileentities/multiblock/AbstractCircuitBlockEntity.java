/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities.multiblock;

import java.util.List;

import com.code.tama.tts.server.tardis.subsystems.AbstractSubsystem;
import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import com.code.tama.tts.server.tardis.subsystems.ImASubsystem;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AbstractCircuitBlockEntity extends BlockEntity implements ImASubsystem, ImAMultiblock {
	public AbstractCircuitBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
			AbstractSubsystem abstractSubsystem) {
		super(type, pos, state);
		subsystem = abstractSubsystem;
	}

	@Getter
	private final AbstractSubsystem subsystem;
	public boolean isFormed = false;

	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		this.load(tag);
		super.handleUpdateTag(tag);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.isFormed = nbt.getBoolean("formed");
		this.subsystem.deserializeNBT(nbt.getCompound("system"));
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putBoolean("formed", this.isFormed);
		tag.put("system", this.subsystem.serializeNBT());
	}

	@Override
	public void OnActivate(Level level, BlockPos pos) {
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);

		this.isFormed = true;
		this.getSubsystem().OnActivate(this.level, this.getBlockPos());
		onCreate(pos, level);
	}

	@Override
	public void OnDeActivate(Level level, BlockPos pos) {
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);

		this.getSubsystem().OnDeActivate(this.level, this.getBlockPos());
		this.isFormed = this.getSubsystem().isActivated();
	}

	public boolean isActivated() {
		return this.getSubsystem().isActivated();
	}

	@Override
	public String name() {
		return "demat_circuit";
	}

	@Override
	public void onSlaveRemoved() {
		removeThis(this.level, this.getBlockPos());
		assert this.level != null;
		this.level.removeBlockEntity(this.getBlockPos());
	}

	@Override
	public void setRemoved() {
		removeThis(this.level, this.getBlockPos());
		super.setRemoved();
	}

	@Override
	public List<BlockPos> getPositions() {
		return DematerializationCircuit.GetOrCreateMap().keySet().stream().toList();
	}
}
