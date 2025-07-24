package com.code.tama.tts.server.misc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@RequiredArgsConstructor @Getter
public class ARSStructure {
    final ResourceLocation path;
    final Component Name;
}
