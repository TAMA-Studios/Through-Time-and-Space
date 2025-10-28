/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.caps;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.interfaces.ILevelCap;
import com.code.tama.tts.server.misc.containers.TIRBlockContainer;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.UpdateTIRPacketS2C;
import com.mojang.serialization.DataResult;
import lombok.RequiredArgsConstructor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

@RequiredArgsConstructor
public class LevelCapability implements ILevelCap {
	Map<UUID, TIRBlockContainer> TIRBlocks = new HashMap<>();
	public final Level level;

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();

		nbt.putInt("TIRSize", TIRBlocks.size());
		AtomicInteger i = new AtomicInteger();
		TIRBlocks.forEach((uuid, tirBlockContainer) -> {
			CompoundTag tag = new CompoundTag();
			tag.putUUID("uuid", uuid);
			tag.put("container",
					TIRBlockContainer.CODEC.encodeStart(NbtOps.INSTANCE, tirBlockContainer).get().orThrow());
			nbt.put(String.valueOf(i.incrementAndGet()), tag);
		});
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		Map<UUID, TIRBlockContainer> TIRBlocks = new HashMap<>();

		int size = nbt.getInt("TIRSize");
		for (int i = 0; i < size; i++) {
			CompoundTag tag = nbt.getCompound(String.valueOf(i));
			if (tag.contains("uuid")) {
				UUID uuid = tag.getUUID("uuid");

				DataResult<TIRBlockContainer> result = TIRBlockContainer.CODEC.parse(NbtOps.INSTANCE,
						tag.get("container"));
				result.resultOrPartial(err -> TTSMod.LOGGER.error("[TIR] Failed to decode TIRBlockContainer: {}", err))
						.ifPresent(container -> {
							TIRBlocks.put(uuid, container);
						});
			}
		}

		this.TIRBlocks = TIRBlocks;
	}

	@Override
	public Map<UUID, TIRBlockContainer> GetTIRBlocks() {
		return this.TIRBlocks;
	}

	@Override
	public void SetTIRBlocks(Map<UUID, TIRBlockContainer> containerMap) {
		this.TIRBlocks = containerMap;
	}

	@Override
	public void OnLoad(ServerPlayer player) {
		Networking.sendToPlayer(player, new UpdateTIRPacketS2C(this.GetTIRBlocks()));
	}
}
