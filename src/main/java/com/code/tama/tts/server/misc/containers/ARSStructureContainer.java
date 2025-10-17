/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.containers;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record ARSStructureContainer(ResourceLocation path, Component Name, int HeightOffs) {
}
