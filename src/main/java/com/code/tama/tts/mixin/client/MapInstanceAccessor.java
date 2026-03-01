/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.gui.MapRenderer$MapInstance")
public interface MapInstanceAccessor {

    @Accessor("data")
    MapItemSavedData tts$getData();
}