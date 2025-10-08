package com.code.tama.tts.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMinecraftAccessor {
    @Mutable
    @Accessor("levelRenderer")
    void setLevelRenderer(LevelRenderer renderer);
}
