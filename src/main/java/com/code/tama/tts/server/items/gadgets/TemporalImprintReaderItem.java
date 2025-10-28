/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items.gadgets;

import java.util.List;
import java.util.UUID;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.items.core.PowerableItem;
import com.code.tama.tts.server.misc.containers.TIRBlockContainer;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.UpdateTIRPacketS2C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TemporalImprintReaderItem extends PowerableItem {
	private boolean firstTick = false;
	BlockPos pos;
	public TemporalImprintReaderItem() {
		super(new Properties().stacksTo(1), 256);
	}

	@Override
	public @NotNull ItemStack getDefaultInstance() {
		return super.getDefaultInstance();
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		float hue = Math.max(0.0F,
				((float) this.getEnergyCapacity() - (float) this.GetPower(stack)) / (float) this.getEnergyCapacity());
		return Mth.hsvToRgb(hue, 1.0F, 1.0F);
	}

	@Override
	public int getEnergyCapacity() {
		return 256;
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int i,
			boolean b) {
		if (firstTick) {
			firstTick = false;
			if (entity instanceof ServerPlayer player) {
				level.getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(cap -> {
					Networking.sendToPlayer(player, new UpdateTIRPacketS2C(cap.GetTIRBlocks()));
				});
			}
		}
		super.inventoryTick(stack, level, entity, i, b);
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
		if (!IsLinked(useOnContext.getItemInHand()))
			useOnContext.getLevel().getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(cap -> {
				UUID uuid = UUID.randomUUID();
				cap.GetTIRBlocks().put(uuid, new TIRBlockContainer(useOnContext.getClickedPos(), uuid));
				useOnContext.getItemInHand().getOrCreateTag().putUUID("uuid", uuid);
			});
		return super.useOn(useOnContext);
	}

	public static boolean IsLinked(ItemStack stack) {
		return stack.getOrCreateTag().contains("uuid");
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
			@NotNull TooltipFlag flagIn) {

		tooltip.add(Component.translatable("gadgets.tir.blockPos", GetLinkedPos(stack, worldIn).toString()));

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public BlockPos GetLinkedPos(ItemStack stack, Level level) {
		if (pos == null) {
			if (level != null)
				if (stack.getOrCreateTag().contains("uuid")) {
					level.getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(
							cap -> this.pos = cap.GetTIRBlocks().get(stack.getOrCreateTag().getUUID("uuid")).getPos());
				}
		}

		return this.pos != null ? this.pos : BlockPos.ZERO;
	}
}
