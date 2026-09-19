/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.core.blocks.tardis.DecoyExteriorBlock;
import com.code.tama.tts.core.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DecoyExteriorTile extends AbstractPortalTile {
	@Getter
	Direction facing = Direction.NORTH;

	@Getter
	int DoorState;

	@Getter
	public ExteriorModelContainer Model;

	public ExteriorAnimationData exteriorAnimationData = new ExteriorAnimationData();

	public DecoyExteriorTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		if (this.getBlockState().getBlock() instanceof DecoyExteriorBlock)
			tag.putString("facing", this.getBlockState().getValue(DecoyExteriorBlock.FACING).getName());
		else
			tag.putString("facing", this.facing.getName());
		tag.putInt("doorsOpen", this.DoorsOpen());

		ExteriorModelContainer.CODEC.encode(this.GetVariant(), NbtOps.INSTANCE, tag);

		tag.putInt("doors", this.DoorState);

		super.saveAdditional(tag);
	}

	@Override
	public BlockPos getTargetPos() {
		return super.getTargetPos();
	}

	public int CycleDoors() {
		this.SetDoorsOpen(switch (this.DoorsOpen()) {
			case 0 -> 1;
			case 1 -> 2;
			default -> 0;
		});
		return this.DoorsOpen();
	}

	public int DoorsOpen() {
		return this.DoorState;
	}

	public ExteriorModelContainer GetVariant() {
		return this.Model == null ? ExteriorsRegistry.Get(0) : this.Model;
	}

	public void UpdateAll() {
		if (this.level == null || this.level.isClientSide)
			return;

		this.setChanged();
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
	}

	public void SetDoorsOpen(int doorState) {
		this.DoorState = doorState;
	}

	/**
	 * Utterly Destroys the tile entity and the linked {@link DecoyExteriorBlock}
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
		if (this.getBlockState().getBlock() instanceof DecoyExteriorBlock) {
			this.facing = this.getBlockState().getValue(DecoyExteriorBlock.FACING);

		} else if (tag.contains("facing")) {
			this.facing = Direction.byName(tag.getString("facing"));
		}

		if (tag.contains("model")) {
			this.Model = ExteriorModelContainer.CODEC.parse(NbtOps.INSTANCE, tag.get("model")).get().orThrow();
		}
		if (tag.contains("model")) {
			this.Model = ExteriorModelContainer.CODEC.parse(NbtOps.INSTANCE, tag.get("model")).get().orThrow();
		}

		if (tag.contains("doorsOpen")) {
			this.SetDoorsOpen(tag.getInt("doorsOpen"));
		}

		super.load(tag);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (this.level != null && !this.level.isClientSide) {
			this.UpdateAll();
		}
	}

	public void setModel(int model) {
		if (model >= ExteriorsRegistry.EXTERIORS.size())
			model = 0;
		this.Model = ExteriorsRegistry.EXTERIORS.get(model);
		if (!this.level.isClientSide)
			this.UpdateAll();
	}

}