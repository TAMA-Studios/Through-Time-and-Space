/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.items;

import com.code.tama.tts.server.entities.controls.AbstractControlEntity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TwineItem extends Item {

	public TwineItem(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("BoundEntityId")) {
			int entityId = tag.getInt("BoundEntityId");
			Entity entity = level.getEntity(entityId);

			if (entity instanceof AbstractControlEntity controlEntity) {
				// Interact with the control entity
				controlEntity.onTwineInteract(player);
				return InteractionResultHolder.success(stack);
			} else {
				// Entity no longer exists, clear the binding
				tag.remove("BoundEntityId");
				tag.remove("BoundEntityUUID");
				player.displayClientMessage(net.minecraft.network.chat.Component.literal("Twine connection lost!"),
						true);
			}
		}

		return InteractionResultHolder.pass(stack);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (isSelected && entity instanceof Player player) {
			CompoundTag tag = stack.getTag();
			if (tag != null && tag.contains("BoundEntityId")) {
				int entityId = tag.getInt("BoundEntityId");
				Entity boundEntity = level.getEntity(entityId);

				// Check if entity still exists and is in range
				if (boundEntity == null || !boundEntity.isAlive()) {
					tag.remove("BoundEntityId");
					tag.remove("BoundEntityUUID");
				} else {
					// Check distance (similar to lead's max distance)
					double distance = player.distanceTo(boundEntity);
					if (distance > 25.0) {
						if (!level.isClientSide) {
							tag.remove("BoundEntityId");
							tag.remove("BoundEntityUUID");
							player.displayClientMessage(net.minecraft.network.chat.Component.literal("Twine broke!"),
									true);
						}
					}
				}
			}
		}
	}
}