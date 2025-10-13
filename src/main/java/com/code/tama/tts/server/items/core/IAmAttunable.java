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

public abstract class IAmAttunable extends Item {
    public IAmAttunable(Properties p_41383_) {
        super(p_41383_);
    }

    public void Attune(ITARDISLevel level, ItemStack stack) {
        this.getShareTag(stack)
                .putString("tardis", level.GetLevel().dimension().location().toString());
    }

    public boolean IsAttunedTo(ITARDISLevel level, ItemStack stack) {
        return this.getShareTag(stack)
                .getString("tardis")
                .equals(level.GetLevel().dimension().location().toString());
    }

    public ResourceKey<Level> GetAttuned(ItemStack stack) {
        ResourceLocation levelLoc = ResourceLocation.parse(
                TTSMod.MODID + ":" + this.getShareTag(stack).getString("tardis"));
        return ResourceKey.create(Registries.DIMENSION, levelLoc);
    }
}
