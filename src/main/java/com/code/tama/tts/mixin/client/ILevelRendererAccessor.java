package com.code.tama.tts.mixin.client;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface ILevelRendererAccessor { // Mixins are being crap and not letting me use @Shadow without crashing non-devmode players so I need an accessor
    @Accessor
    public RenderBuffers getRenderBuffers();

    @Invoker("renderSnowAndRain")
    void renderSnowAndRain(LightTexture tex, float smth, double idfk, double rtfm, double ohwaititsforgenvm);
}
