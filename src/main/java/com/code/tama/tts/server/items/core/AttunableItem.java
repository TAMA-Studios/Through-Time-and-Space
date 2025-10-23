/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items.core;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AttunableItem extends Item {
	public AttunableItem(Properties p_41383_) {
		super(p_41383_);
	}

	public void Attune(ITARDISLevel level, ItemStack stack) {
		stack.getOrCreateTag().putString("tardis", level.GetLevel().dimension().location().toString());
	}

	public ResourceKey<Level> GetAttuned(ItemStack stack) {
		ResourceLocation levelLoc = new ResourceLocation(TTSMod.MODID + ":" + stack.getOrCreateTag().getString("tardis"));
		return ResourceKey.create(Registries.DIMENSION, levelLoc);
	}

	public boolean IsAttunedTo(ITARDISLevel level, ItemStack stack) {
		return stack.getOrCreateTag().getString("tardis").equals(level.GetLevel().dimension().location().toString());
	}
}
