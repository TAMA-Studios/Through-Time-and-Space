/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities;

import java.util.ArrayList;

import com.code.tama.tts.core.items.core.NozzleItem;
import lombok.Getter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import com.code.tama.triggerapi.tileEntities.TickingTile;
import com.code.tama.triggerapi.universal.UniversalCommon;

@Getter
public class WorkbenchTile extends TickingTile {
	public AnimationState OPEN = new AnimationState();
	public AnimationState IDLE = new AnimationState();
	public AnimationState FABRICATING = new AnimationState();
	public AnimationState CLOSE = new AnimationState();

	public boolean LastState = false;
	public boolean Open = false;
	public boolean Fabricating = false;
	public int AnimationTicks = 0;
	private long startedFabrication = 0L;

	public ArrayList<Item> StoredItems = new ArrayList<>();
	public NozzleItem nozzle;

	public WorkbenchTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void tick() {
	}

	@Override
	public void clientTick() {
		this.AnimationTicks++;

		if (this.Fabricating && this.startedFabrication == 0)
			this.startedFabrication = this.AnimationTicks;
		else if (!this.Fabricating)
			this.startedFabrication = 0;

		if (this.Open && !LastState) {
			this.CLOSE.ifStarted(AnimationState::stop);
			this.OPEN.startIfStopped(this.AnimationTicks);
		}

		if (!this.Open && this.LastState) {
			this.OPEN.ifStarted(AnimationState::stop);
			this.CLOSE.startIfStopped(this.AnimationTicks);
		}
		// this.IDLE.animateWhen(this.Open && !this.Fabricating, this.AnimationTicks);
		this.FABRICATING.animateWhen(this.Fabricating, this.AnimationTicks);

		this.LastState = this.Open;
	}

	@Override
	public void load(CompoundTag nbt) {
		if (nbt.contains("one"))
			this.StoredItems.add(BuiltInRegistries.ITEM.get(UniversalCommon.parse(nbt.getString("one"))));

		if (nbt.contains("two"))
			this.StoredItems.add(BuiltInRegistries.ITEM.get(UniversalCommon.parse(nbt.getString("two"))));

		if (nbt.contains("three"))
			this.StoredItems.add(BuiltInRegistries.ITEM.get(UniversalCommon.parse(nbt.getString("three"))));

		if (nbt.contains("four"))
			this.StoredItems.add(BuiltInRegistries.ITEM.get(UniversalCommon.parse(nbt.getString("four"))));

		if (nbt.contains("five"))
			this.StoredItems.add(BuiltInRegistries.ITEM.get(UniversalCommon.parse(nbt.getString("five"))));

		if (nbt.contains("six"))
			this.StoredItems.add(BuiltInRegistries.ITEM.get(UniversalCommon.parse(nbt.getString("six"))));
		super.load(nbt);
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		int size = this.getStoredItems().size();
		if (size > 0)
			nbt.putString("one", BuiltInRegistries.ITEM.getKey(this.StoredItems.get(0)).toString());

		if (size > 1)
			nbt.putString("two", BuiltInRegistries.ITEM.getKey(this.StoredItems.get(1)).toString());

		if (size > 2)
			nbt.putString("three", BuiltInRegistries.ITEM.getKey(this.StoredItems.get(2)).toString());

		if (size > 3)
			nbt.putString("four", BuiltInRegistries.ITEM.getKey(this.StoredItems.get(3)).toString());

		if (size > 4)
			nbt.putString("five", BuiltInRegistries.ITEM.getKey(this.StoredItems.get(4)).toString());

		if (size > 5)
			nbt.putString("six", BuiltInRegistries.ITEM.getKey(this.StoredItems.get(5)).toString());

		super.saveAdditional(nbt);
	}
}