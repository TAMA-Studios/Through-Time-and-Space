/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items.gadgets;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.items.core.PowerableItem;
import com.code.tama.tts.server.misc.containers.TIRBlockContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class TemporalImprintReaderItem extends PowerableItem {
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
        float hue = Math.max(0.0F, ((float) this.getEnergyCapacity() - (float) this.GetPower(stack)) / (float) this.getEnergyCapacity());
		return Mth.hsvToRgb(hue, 1.0F, 1.0F);
	}

	@Override
	public int getEnergyCapacity() {
		return 256;
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
		assert worldIn != null;
		tooltip.add(Component.translatable("gadgets.tir.blockPos", GetLinkedPos(stack, worldIn).toString()));

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public BlockPos GetLinkedPos(ItemStack stack, Level level) {
		if (pos == null)
			return this.pos = level.getCapability(Capabilities.LEVEL_CAPABILITY).orElseGet(null).GetTIRBlocks()
					.get(stack.getOrCreateTag().getUUID("uuid")).getPos();
		else
			return this.pos;
	}
}
