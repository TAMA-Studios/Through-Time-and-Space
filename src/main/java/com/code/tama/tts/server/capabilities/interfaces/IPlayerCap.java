/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerCap extends INBTSerializable<CompoundTag> {
    String GetViewingTARDIS();

    void SetViewingTARDIS(String tardis);
}
