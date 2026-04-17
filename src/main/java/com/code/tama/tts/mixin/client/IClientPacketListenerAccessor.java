package com.code.tama.tts.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPacketListener.class)
public interface IClientPacketListenerAccessor {
    @Accessor("level")
    ClientLevel getLevel();

    @Accessor("level")
    void setLevel(ClientLevel level);

    @Invoker("updateLevelChunk")
    void updateLevelChunk(int cx, int cz, ClientboundLevelChunkPacketData data);

    @Invoker("applyLightData")
    void applyLightData(int cx, int cz, ClientboundLightUpdatePacketData data);

    @Invoker("enableChunkLight")
    void enableChunkLight(LevelChunk chunk, int cx, int cz);
}