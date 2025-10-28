/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items.core;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class PowerableItem extends Item {
	public abstract int getEnergyCapacity();

	public PowerableItem(Properties properties, int power) {
		super(properties.durability(power).setNoRepair());
	}

	/**
	 * Override this to make sure people don't put mending on it like a certain
	 * <i>REGENERATION MOD</i>s fob watch
	 **/
	@Override
	public boolean isEnchantable(@NotNull ItemStack stack) {
		return false;
	}

	public int GetPower(ItemStack stack) {
		return stack.getMaxDamage() - stack.getDamageValue();
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
			@NotNull TooltipFlag flagIn) {
		tooltip.add(Component.literal("Power - " + GetPower(stack)));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	/** SUPER THIS AT THE TOP! **/
	@Override
	public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack,
			int i) {
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			livingEntity.releaseUsingItem();
			return;
		}
	}

	/** SUPER THIS AT THE TOP! **/
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
			@NotNull InteractionHand interactionHand) {
		if (player.getMainHandItem().getDamageValue() >= player.getMainHandItem().getMaxDamage() - 1) {
			player.releaseUsingItem();
			return InteractionResultHolder.fail(player.getMainHandItem());
		}

		return super.use(level, player, interactionHand);
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
		if (useOnContext.getHand() == InteractionHand.OFF_HAND) {
			assert useOnContext.getPlayer() != null;
			return InteractionResultHolder.success(useOnContext.getPlayer().getOffhandItem()).getResult();
		}

		assert useOnContext.getPlayer() != null;
		useOnContext.getItemInHand().setDamageValue(useOnContext.getItemInHand().getDamageValue() + 10);
		// useOnContext.getItemInHand().hurtAndBreak(
		// 1, useOnContext.getPlayer(), livingEntityx ->
		// livingEntityx.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		// useOnContext.getPlayer().getCooldowns().addCooldown(this, 20);

		// this.InteractionType.onUse(useOnContext);
		return InteractionResult.SUCCESS;
	}

	/**
	 * Adds (or removes) power to the item (Remove power by using negative numbers
	 *
	 * @param stack
	 *            The ItemStack
	 * @param Power
	 *            The amount of power to add
	 */
	public void AddPower(@NotNull ItemStack stack, int Power) {
		stack.setDamageValue(stack.getDamageValue() - Power);
	}

	public boolean HasPower(@NotNull ItemStack stack) {
		return (stack.getDamageValue() >= stack.getMaxDamage() - 1);
	}

	/**
	 * Checks if the ItemStack the Player is holding has power, if it doesn't, it
	 * forces the Player to stop using it. Intended for use in scenarios where you
	 * should only proceed using the item IF it has power
	 *
	 * @param player
	 *            The Player that is holding the powerable ItemStack
	 * @return If the ItemStack has power or not
	 */
	public boolean CheckPowerBeforeUse(Player player) {
		if (player.getMainHandItem().getDamageValue() >= player.getMainHandItem().getMaxDamage() - 1) {
			player.releaseUsingItem();
			return false;
		}
		return true;
	}
}
