/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientRegistryLayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {

	@Shadow
	private LayeredRegistryAccess<ClientRegistryLayer> registryAccess;

	// -------------------------------------------------------------------------

	@Inject(method = "handleRespawn", at = @At("HEAD"), cancellable = true)
	private void seamless_onHandleRespawn(ClientboundRespawnPacket packet, CallbackInfo ci) {
		if (!ClientSeamlessTeleportState.isSeamlessPending())
			return;
		ci.cancel();
		ClientSeamlessTeleportState.clearPending();
		tts$performSeamlessRespawn(packet);
	}

	@Unique
	private void tts$performSeamlessRespawn(ClientboundRespawnPacket packet) {
		ClientPacketListenerAccessor self = (ClientPacketListenerAccessor) (Object) this;
		Minecraft mc = self.getMinecraft();

		ResourceKey<Level> newDimKey = packet.getDimension();

		Holder<DimensionType> dimTypeHolder = registryAccess.compositeAccess()
				.registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow(packet.getDimensionType());

		LocalPlayer oldPlayer = mc.player;
		int oldPlayerId = oldPlayer.getId();

		ClientLevel.ClientLevelData existingData = (ClientLevel.ClientLevelData) mc.level.getLevelData();

		ClientLevel.ClientLevelData newLevelData = new ClientLevel.ClientLevelData(
				existingData.getDifficulty(),
				existingData.isHardcore(),
				packet.isFlat());

		ClientLevel newLevel = new ClientLevel(
				(ClientPacketListener) (Object) this,
				newLevelData,
				newDimKey,
				dimTypeHolder,
				mc.options.simulationDistance().get(),
				mc.options.renderDistance().get(),
				mc::getProfiler,
				mc.levelRenderer,
				packet.isDebug(),
				packet.getSeed());

		// Swap level without triggering mc.setLevel() (which would open the loading screen)
		self.setLevel(newLevel);
		mc.level = newLevel;
		mc.levelRenderer.setLevel(newLevel);

		// KEEP_ALL_DATA = (byte) 3 in 1.20.1.
		// ClientboundRespawnPacket.KEEP_ALL_DATA may not be a public constant in all
		// mappings, so we use the raw byte value here to be safe.
		//   bit 0 (0x01) = keep attributes
		//   bit 1 (0x02) = keep entity data
		//   0x03         = keep all data
		final byte KEEP_ALL_DATA = (byte) 0x03;
		boolean keepData = packet.shouldKeep(KEEP_ALL_DATA);

		LocalPlayer newPlayer = mc.gameMode.createPlayer(newLevel, oldPlayer.getStats(), oldPlayer.getRecipeBook());
		newPlayer.setId(oldPlayerId);

		if (keepData) {
			newPlayer.restoreFrom(oldPlayer);
		}

		mc.gameMode.setLocalMode(packet.getPlayerGameType(), packet.getPreviousPlayerGameType());

		newPlayer.input = new KeyboardInput(mc.options);
		mc.gameMode.adjustPlayer(newPlayer);
		mc.player = newPlayer;

		if (mc.cameraEntity == oldPlayer) {
			mc.cameraEntity = newPlayer;
		}

		newLevel.addPlayer(oldPlayerId, (AbstractClientPlayer) newPlayer);

		newPlayer.onUpdateAbilities();

		// What we are deliberately NOT doing:
		// mc.setScreen(new ReceivingLevelScreen(...)) <- loading screen, SKIPPED
		// mc.setScreen(null) <- clear after load, SKIPPED
	}
}