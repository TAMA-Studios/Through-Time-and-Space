/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items.gadgets;

import java.util.List;

import com.code.tama.tts.manual.ManualScreen;
import com.code.tama.tts.server.items.core.IAttunableItem;
import com.code.tama.tts.server.registries.misc.SonicModeRegistry;
import com.code.tama.tts.server.sonic.SonicBlockMode;
import com.code.tama.tts.server.sonic.SonicBuilderMode;
import com.code.tama.tts.server.sonic.SonicMode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;

import com.code.tama.triggerapi.GrammarNazi;

public class SonicItem implements IAttunableItem {
	private final int Variants;

	@Getter
	@Setter
	public @NotNull SonicMode InteractionType = new SonicBlockMode();

	public SonicItem(Properties properties, int variants) {
		super(properties.durability(1000).setNoRepair());
		this.Variants = variants;
	}

	@Override
	public @NotNull ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		stack.getOrCreateTag().putInt("chapter", 9);
		return stack;
	}

	/**
	 * Override this to make sure people don't put mending on it like a certain
	 * <i>REGENERATION MOD</i>s fob watch
	 **/
	@Override
	public boolean isEnchantable(@NotNull ItemStack stack) {
		return false;
	}

	@SuppressWarnings("deprecation")
	private @NotNull HitResult calculateHitResult(LivingEntity livingEntity) {
		final double MAX_BRUSH_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0;
		return ProjectileUtil.getHitResultOnViewVector(livingEntity,
				entity -> !entity.isSpectator() && entity.isPickable(), MAX_BRUSH_DISTANCE);
	}

	public void CycleVariant(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		int Variant = GetVariant(stack);
		if (Variant < this.Variants)
			Variant++;
		else
			Variant = 1;
		tag.putInt("CustomModelData", Variant);
		stack.setTag(tag);
	}

	public int GetPower(ItemStack stack) {
		return stack.getMaxDamage() - stack.getDamageValue();
	}

	public int GetVariant(ItemStack stack) {
		if (stack.getOrCreateTag().contains("CustomModelData"))
			return IsExtended(stack)
					? stack.getOrCreateTag().getInt("CustomModelData") / 100
					: stack.getOrCreateTag().getInt("CustomModelData");
		return 1;
	}

	public boolean IsExtended(ItemStack stack) {
		if (stack.getOrCreateTag().contains("CustomModelData"))
			return stack.getOrCreateTag().getInt("CustomModelData") >= 100;
		return false;
	}

	public void SetVariant(ItemStack stack, int Variant) {
		Variant = Math.max(Math.min(Variant, this.Variants), 1);
		CompoundTag tag = stack.getOrCreateTag();
		if (IsExtended(stack))
			Variant *= 100;
		tag.putInt("CustomModelData", Variant);
		stack.setTag(tag);
	}

	public void ToggleExtend(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		// Invert CustomModelData sign
		int Variant = 100;
		if (tag.contains("CustomModelData")) {
			Variant = tag.getInt("CustomModelData");
			if (Variant >= 100)
				Variant /= 100;
			else
				Variant *= 100;
		}
		tag.putInt("CustomModelData", Variant);
		stack.setTag(tag);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
			@NotNull TooltipFlag flagIn) {
		tooltip.add(Component.literal("Sonic Screwdriver! Doesn't work on wood and allat"));
		tooltip.add(Component.literal("Power - " + GetPower(stack)));
		tooltip.add(Component.translatable("tooltip.tts.ctrl"));
		if (Screen.hasControlDown()) {
			Minecraft.getInstance().setScreen(new ManualScreen(stack));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
			@NotNull Player p_41444_) {
		if (this.InteractionType instanceof SonicBuilderMode sonicBuilderMode) {
			sonicBuilderMode.handleInteraction(p_41444_, state, level, pos, false,
					p_41444_.getItemInHand(InteractionHand.MAIN_HAND), this.getDescriptionId());
			return false;
		}

		return false;
	}

	@Override
	public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack,
			int i) {
		// if (this.InteractionType != SonicInteractionType.SCANNER) return;
		// if Item durability (powah) is 0, stop using
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			livingEntity.releaseUsingItem();
			return;
		}

		if (i >= 0 && livingEntity instanceof Player player) {
			HitResult hitResult = this.calculateHitResult(livingEntity);
			if (hitResult instanceof BlockHitResult blockHitResult && hitResult.getType() == HitResult.Type.BLOCK) {
				int j = this.getUseDuration(itemStack) - i + 1;
				boolean bl = j % 10 == 5;
				if (bl) {
					BlockPos blockPos = blockHitResult.getBlockPos();
					BlockState blockState = level.getBlockState(blockPos);
					SoundEvent soundEvent;
					if (blockState.getBlock() instanceof BrushableBlock brushableBlock) {
						soundEvent = brushableBlock.getBrushSound();
					} else {
						soundEvent = SoundEvents.BRUSH_GENERIC;
					}

					level.playSound(player, blockPos, soundEvent, SoundSource.BLOCKS);
					if (!level.isClientSide()
							&& level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity) {
						boolean bl2 = brushableBlockEntity.brush(level.getGameTime(), player,
								blockHitResult.getDirection());
						if (bl2) {
							EquipmentSlot equipmentSlot = itemStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
									? EquipmentSlot.OFFHAND
									: EquipmentSlot.MAINHAND;
							itemStack.hurtAndBreak(1, livingEntity,
									livingEntityx -> livingEntityx.broadcastBreakEvent(equipmentSlot));
						}
					}
				}

				return;
			}

			livingEntity.releaseUsingItem();
		} else {
			livingEntity.releaseUsingItem();
		}
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
			@NotNull InteractionHand interactionHand) {
		if (interactionHand == InteractionHand.OFF_HAND) {
			if (player.isCrouching())
				CycleVariant(player.getOffhandItem());
			else
				ToggleExtend(player.getOffhandItem());
			return InteractionResultHolder.success(player.getOffhandItem());
		}
		if (player.isCrouching()) {
			RegistryObject<SonicMode> nextMode = SonicModeRegistry
					.getFromOrdinal((SonicModeRegistry.ordinal(this.InteractionType) + 1));
			if (!player.level().isClientSide) {
				this.InteractionType = nextMode.get();
				player.sendSystemMessage(Component.literal(GrammarNazi.CleanString(nextMode.get().getName())));
			}

		} else {
			if (player.getMainHandItem().getDamageValue() >= player.getMainHandItem().getMaxDamage() - 1) {
				player.releaseUsingItem();
				return InteractionResultHolder.fail(player.getMainHandItem());
			}

			this.useOn(new UseOnContext(player, interactionHand,
					new BlockHitResult(player.getEyePosition(), player.getDirection(),
							BlockPos.containing(player.getEyePosition().add(player.getViewVector(1.0F).scale(5.0D))),
							false)));
		}
		return super.use(level, player, interactionHand);
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
		if (useOnContext.getHand() == InteractionHand.OFF_HAND) {
			assert useOnContext.getPlayer() != null;
			if (useOnContext.getPlayer().isCrouching())
				CycleVariant(useOnContext.getItemInHand());
			else
				ToggleExtend(useOnContext.getItemInHand());
			return InteractionResultHolder.success(useOnContext.getPlayer().getOffhandItem()).getResult();
		}

		assert useOnContext.getPlayer() != null;
		useOnContext.getItemInHand().setDamageValue(useOnContext.getItemInHand().getDamageValue() + 10);
		// useOnContext.getItemInHand().hurtAndBreak(
		// 1, useOnContext.getPlayer(), livingEntityx ->
		// livingEntityx.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		// useOnContext.getPlayer().getCooldowns().addCooldown(this, 20);

		this.InteractionType.onUse(useOnContext);
		return InteractionResult.SUCCESS;
	}
}
