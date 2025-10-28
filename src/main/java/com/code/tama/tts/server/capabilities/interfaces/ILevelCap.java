/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.interfaces;

import com.code.tama.tts.server.misc.containers.TIRBlockContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;
import java.util.UUID;

public interface ILevelCap extends INBTSerializable<CompoundTag> {
	Map<UUID, TIRBlockContainer> GetTIRBlocks();
	void SetTIRBlocks(Map<UUID, TIRBlockContainer> containerMap);
	void OnLoad(ServerPlayer player);
}
