/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BotiChunkContainer {
	boolean IsFluid;
	boolean IsTile;
	CompoundTag entityTag;
	FluidState fluidState;
	final Level level;
	final int light;
	final BlockPos pos;
	final BlockState state;

	public BotiChunkContainer(Level level, BlockState state, BlockPos pos, int light, boolean IsTile,
			CompoundTag tileTag) {
		this.state = state;
		this.IsTile = IsTile;
		this.entityTag = tileTag;
		this.pos = pos;
		this.light = light;
		this.level = level;
	}

	public BotiChunkContainer(Level level, BlockState state, FluidState fluidState, BlockPos pos, int light) {
		this.state = state;
		this.fluidState = fluidState;
		this.pos = pos;
		this.light = light;
		this.IsFluid = true;
		this.level = level;
	}

	@Contract("_ -> new")
	@SuppressWarnings("deprecation")
	public static @NotNull BotiChunkContainer decode(@NotNull FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();

		// Read BlockState
		BlockState state = Block.BLOCK_STATE_REGISTRY.byId(buf.readVarInt());

		int light = buf.readVarInt();
		boolean IsFluid = buf.readBoolean();
		boolean IsTile = buf.readBoolean();
		if (IsFluid) {
			int id = buf.readVarInt();
			FluidState fluid = Fluid.FLUID_STATE_REGISTRY.byId(id);
			return new BotiChunkContainer(Minecraft.getInstance().level, state, fluid, pos, light);
		}
		if (IsTile) {
			return new BotiChunkContainer(Minecraft.getInstance().level, state, pos, light, true, buf.readNbt());
		}
		return new BotiChunkContainer(Minecraft.getInstance().level, light, pos, state);
	}

	public static List<BotiChunkContainer> decodeList(FriendlyByteBuf buf) {
		int size = buf.readVarInt();
		List<BotiChunkContainer> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(BotiChunkContainer.decode(buf));
		}
		return list;
	}

	public static void encodeList(List<BotiChunkContainer> list, FriendlyByteBuf buf) {
		buf.writeVarInt(list.size());
		for (BotiChunkContainer container : list) {
			container.encode(buf);
		}
	}

	public void encode(@NotNull FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);

		// Write BlockState as raw ID (includes properties!)
		int stateId = Block.BLOCK_STATE_REGISTRY.getId(state);
		buf.writeVarInt(stateId);
		buf.writeVarInt(light);
		buf.writeBoolean(IsFluid);
		buf.writeBoolean(IsTile);

		if (IsFluid) {
			int fluidStateId = Fluid.FLUID_STATE_REGISTRY.getId(fluidState);
			buf.writeVarInt(fluidStateId);
		}

		if (IsTile) {
			buf.writeNbt(level.getBlockEntity(pos).saveWithFullMetadata());
		}
	}
}
