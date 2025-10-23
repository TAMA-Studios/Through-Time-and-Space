/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.interfaces;

import java.util.Map;
import java.util.UUID;

import com.code.tama.tts.server.misc.containers.TIRBlockContainer;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ILevelCap extends INBTSerializable<CompoundTag> {
	Map<UUID, TIRBlockContainer> GetTIRBlocks();
}
