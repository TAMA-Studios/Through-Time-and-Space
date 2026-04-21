/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.sonic;

import com.code.tama.triggerapi.helpers.world.RayTraceUtils;
import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSItems;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.caps.LevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ILevelCap;
import com.code.tama.tts.server.data.RiftData;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.misc.progressable.IWeldable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SonicRiftMode extends SonicMode {
	public Item getIcon() {
		return TTSItems.CORAL_SONIC.asItem();
	}

	public String getName() {
		return "rift_mode";
	}

	private BlockPos cachedPos, targetedRiftPos;
	boolean isTargetingRift = false;

	public void onUse(UseOnContext context) {
		if (context.getHand().equals(InteractionHand.OFF_HAND))
			return;

		Player player = context.getPlayer();
		Level level = context.getLevel();

		assert player != null;
		HitResult hitResult = RayTraceUtils.rayTraceFromEntity(player, 25, false);
		if (!(hitResult instanceof BlockHitResult))
			return;

		// TODO: TEST THIS!

		ILevelCap cap = level.getCapability(Capabilities.LEVEL_CAPABILITY).orElseGet(() -> new LevelCapability(level));

		BlockPos usedPos = ((BlockHitResult) hitResult).getBlockPos(); // context.getClickedPos();

		if(cachedPos != usedPos) {
			cap.GetRiftDataAABBs().forEach((aabb, rift) -> {
				if (aabb.contains(usedPos.getCenter())) {
					isTargetingRift = true;
					targetedRiftPos = rift.getPos();
				}
				else {
					isTargetingRift = false;
					targetedRiftPos = null;
				}
			});
		}

		if (isTargetingRift) {
			RiftData rift = cap.GetRiftData().get(targetedRiftPos);

			rift.setUsedTime(rift.getUsedTime() + 1);
			System.out.println(rift.getUsedTime());
		}
	}
}
