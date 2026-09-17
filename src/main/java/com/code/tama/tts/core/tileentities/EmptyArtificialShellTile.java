/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities;

import com.code.tama.tts.server.misc.progressable.IWeldable;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import com.code.tama.triggerapi.helpers.MathUtils;
import com.code.tama.triggerapi.tileEntities.EnergyHoldingTile;

@Getter
@Setter
public class EmptyArtificialShellTile extends EnergyHoldingTile implements IWeldable {
	public int PlasmicShellPlates, StructuralBeams, Weld;
	public boolean ShouldMakeExt = false, Dams = false;

	@Override
	public int capacity() {
		return 1000;
	}

	@Override
	public int maxReceive() {
		return 10;
	}

	@Override
	public int maxTransmit() {
		return 0;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	public EmptyArtificialShellTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	/**
	 * @return The max weld, PlasmicShellPlates * 40 - 40 weld per plate
	 */
	@Override
	public int getMaxWeld() {
		return this.PlasmicShellPlates * 40;
	}

	@Override
	public int getWeld() {
		return this.Weld;
	}

	@Override
	public void setWeld(int weld) {
		this.Weld = Math.toIntExact(MathUtils.clamp(weld, 0, this.getMaxWeld()));
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.putInt("plates", PlasmicShellPlates);
		tag.putInt("beams", StructuralBeams);
		tag.putInt("weld", Weld);
		tag.putBoolean("dams", Dams);
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		this.PlasmicShellPlates = tag.getInt("plates");
		this.StructuralBeams = tag.getInt("beams");
		this.Weld = tag.getInt("weld");
		this.Dams = tag.getBoolean("dams");
		super.load(tag);
	}

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

	public void update() {
		if (this.level == null || this.level.isClientSide)
			return;

		this.setChanged();
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
	}
}
