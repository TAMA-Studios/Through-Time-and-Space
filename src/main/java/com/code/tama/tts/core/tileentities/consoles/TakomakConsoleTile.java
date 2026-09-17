/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities.consoles;

import com.code.tama.tts.core.entities.controls.ModularControl;
import com.code.tama.tts.server.tardis.control_lists.AbstractControlList;
import com.code.tama.tts.server.tardis.control_lists.ControlLists;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import com.code.tama.triggerapi.helpers.world.BlockUtils;

public class TakomakConsoleTile extends AbstractConsoleTile {
	public TakomakConsoleTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public AbstractControlList GetControlList() {
		return ControlLists.GetTokamak();
	}

	@Override
	void summonButtons(Level level) {
		BlockPos blockPos = this.getBlockPos();
		Vec3 centerPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);

		this.GetControlList().getPositionSizeMap().forEach((record) -> {
			assert this.getLevel() != null;

			float offs;
			if (this.getLevel().getBlockState(this.getBlockPos().below()).getBlock() instanceof SnowLayerBlock)
				offs = 1;
			else
				offs = BlockUtils.getReverseHeightModifier(this.getLevel().getBlockState(this.getBlockPos().below()));

			offs -= 0.05f;
			offs += 0.5f;

			// Spawn position uses the record's center (cx/cy/cz)
			Vec3 summonPos = centerPos.add(record.cx(), record.cy() - record.hh() - offs, record.cz());

			ModularControl entity = new ModularControl(level, this, record);
			entity.setPos(summonPos);

			// Stamp the yaw from the record onto the entity so getLocalHitboxSlices() works
			// correctly
			entity.setYRot(record.yawDeg());
			entity.yRotO = record.yawDeg();

			entity.setPos(summonPos);
			entity.setYRot(record.yawDeg());
			entity.yRotO = record.yawDeg();
			entity.refreshDimensions(); // forces makeBoundingBox to re-run with correct yaw

			level.addFreshEntity(entity);
			this.ControlSize++;
			this.ControlAnimationMap.put(record.ID(), 0.0f);

			if (this.GetControlList().GetDefaultControlAssignment().containsKey(entity.Identifier())) {
				entity.SetControl(this.GetControlList().GetDefaultControlAssignment().get(entity.Identifier()));
			}

			this.ControlMap.put(summonPos, entity.getUUID());
		});

		level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 2);
	}
}
