/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;

@Mixin(ClientPacketListener.class)
public interface ClientPacketListenerAccessor {

	@Accessor("minecraft")
	Minecraft getMinecraft();

	@Accessor("level")
	ClientLevel getLevel();

	@Accessor("level")
	void setLevel(ClientLevel level);
}