/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.items.gadgets;

import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import com.code.tama.triggerapi.gui.CustomGuiProvider;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class VortexManipulatorItem extends Item {
	public static final ResourceLocation VM_SCREEN = UniversalCommon.modRL("vm");

	public VortexManipulatorItem(Properties p_41383_) {
		super(p_41383_);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
			@NotNull InteractionHand hand) {
		if (player instanceof ServerPlayer sp)
			CustomGuiProvider.openGui(sp, VM_SCREEN);
		return super.use(level, player, hand);
	}
}
