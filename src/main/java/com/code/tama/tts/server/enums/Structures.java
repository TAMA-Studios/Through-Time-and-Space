/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.enums;

import static com.code.tama.tts.TTSMod.MODID;

import net.minecraft.resources.ResourceLocation;

public enum Structures {
  CleanInterior(new ResourceLocation(MODID, "clean")),
  CitadelInterior(new ResourceLocation(MODID, "citadel"));

  final ResourceLocation path;

  Structures(ResourceLocation path) {
    this.path = path;
  }

  public ResourceLocation GetRL() {
    return this.path;
  }
}
