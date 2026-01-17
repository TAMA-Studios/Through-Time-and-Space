/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.helpers;

import static com.code.tama.tts.TTSMod.LOGGER;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.level.ServerPlayer;

public class SkinHelper {
	private static final Map<UUID, Property> skinCache = new HashMap<>();

	public static void copySkinFromPlayer(ServerPlayer player, String targetPlayerName) {
		net.minecraft.server.MinecraftServer server = player.getServer();
		if (server == null)
			return;

		// 1. Check if target is online
		ServerPlayer targetPlayer = server.getPlayerList().getPlayerByName(targetPlayerName);
		if (targetPlayer != null) {
			Property textures = targetPlayer.getGameProfile().getProperties().get("textures").stream().findFirst()
					.orElse(null);
			if (textures != null) {
				applyTextureProperty(player, textures);
				return;
			}
		}

		// 2. Check Cache next
		// Note: We need the UUID to check the cache properly
		server.getProfileCache().getAsync(targetPlayerName, profileOpt -> {
			if (profileOpt.isPresent()) {
				GameProfile profile = profileOpt.get();
				if (skinCache.containsKey(profile.getId())) {
					applyTextureProperty(player, skinCache.get(profile.getId()));
				} else {
					// 3. Fetch from Mojang (Async)
					fetchAndApplySkin(server, player, profile);
				}
			} else {
				LOGGER.warn("Skin lookup failed: Player {} not found.", targetPlayerName);
			}
		});
	}

	private static void fetchAndApplySkin(net.minecraft.server.MinecraftServer server, ServerPlayer player,
			GameProfile profile) {
		CompletableFuture.runAsync(() -> {
			// This fetches the signed texture data from Mojang
			GameProfile fullProfile = server.getSessionService().fillProfileProperties(profile, true);
			Property textures = fullProfile.getProperties().get("textures").stream().findFirst().orElse(null);

			if (textures != null) {
				skinCache.put(fullProfile.getId(), textures);
				server.execute(() -> applyTextureProperty(player, textures));
			}
		});
	}

	public static void resetPlayerSkin(ServerPlayer player) {
		net.minecraft.server.MinecraftServer server = player.getServer();
		if (server == null)
			return;

		// To reset, we fetch the player's OWN original textures from Mojang
		fetchAndApplySkin(server, player, new GameProfile(player.getUUID(), player.getScoreboardName()));
	}

	private static void applyTextureProperty(ServerPlayer player, Property textures) {
		GameProfile profile = player.getGameProfile();
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", textures);
		refreshPlayerSkin(player);
	}

	public static String getSkinTexture(com.mojang.authlib.GameProfile profile) {
		com.mojang.authlib.properties.Property textures = profile.getProperties().get("textures").stream().findFirst()
				.orElse(null);
		if (textures != null) {
			try {
				String json = new String(java.util.Base64.getDecoder().decode(textures.getValue()));
				com.google.gson.JsonObject obj = new com.google.gson.JsonParser().parse(json).getAsJsonObject();
				return obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
			} catch (Exception e) {
				LOGGER.error("Failed to parse skin texture", e);
			}
		}
		return "default";
	}

	public static String getSkinModel(com.mojang.authlib.GameProfile profile) {
		com.mojang.authlib.properties.Property textures = profile.getProperties().get("textures").stream().findFirst()
				.orElse(null);
		if (textures != null) {
			try {
				String json = new String(java.util.Base64.getDecoder().decode(textures.getValue()));
				com.google.gson.JsonObject obj = new com.google.gson.JsonParser().parse(json).getAsJsonObject();
				com.google.gson.JsonObject skin = obj.getAsJsonObject("textures").getAsJsonObject("SKIN");
				if (skin.has("metadata")) {
					return skin.getAsJsonObject("metadata").get("model").getAsString();
				}
			} catch (Exception e) {
				LOGGER.error("Failed to parse skin model", e);
			}
		}
		return "default";
	}

	public static void applySkinChange(ServerPlayer player, String textureUrl, String model) {
		com.mojang.authlib.GameProfile profile = player.getGameProfile();

		// Remove existing textures property
		profile.getProperties().removeAll("textures");

		// Create new texture data
		com.google.gson.JsonObject texturesObj = new com.google.gson.JsonObject();
		com.google.gson.JsonObject skinObj = new com.google.gson.JsonObject();
		skinObj.addProperty("url", textureUrl);

		if ("slim".equalsIgnoreCase(model)) {
			com.google.gson.JsonObject metadata = new com.google.gson.JsonObject();
			metadata.addProperty("model", "slim");
			skinObj.add("metadata", metadata);
		}

		com.google.gson.JsonObject texturesRoot = new com.google.gson.JsonObject();
		texturesRoot.add("SKIN", skinObj);
		texturesObj.add("textures", texturesRoot);
		texturesObj.addProperty("timestamp", System.currentTimeMillis());
		texturesObj.addProperty("profileId", profile.getId().toString().replace("-", ""));
		texturesObj.addProperty("profileName", profile.getName());

		// Encode to base64
		String encoded = java.util.Base64.getEncoder().encodeToString(
				new com.google.gson.Gson().toJson(texturesObj).getBytes(java.nio.charset.StandardCharsets.UTF_8));

		// Add new property
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", encoded));

		// Refresh player for all clients
		refreshPlayerSkin(player);
	}

	private static void applyProfileToPlayer(ServerPlayer player, com.mojang.authlib.GameProfile targetProfile) {
		com.mojang.authlib.GameProfile playerProfile = player.getGameProfile();
		playerProfile.getProperties().removeAll("textures");

		targetProfile.getProperties().get("textures")
				.forEach(property -> playerProfile.getProperties().put("textures", property));

		refreshPlayerSkin(player);
	}

	public static void refreshPlayerSkin(ServerPlayer player) {
		// Method 1: Remove and re-add player to force skin refresh
		net.minecraft.server.MinecraftServer server = player.getServer();
		if (server != null) {
			// Send player info update packet to all clients
			net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket packet = new net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket(
					net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED, player);

			for (ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {
				serverPlayer.connection.send(packet);
			}

			// Respawn player to force full refresh
			player.connection.send(new net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket(
					java.util.List.of(player.getUUID())));
			player.connection.send(new net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket(
					net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, player));
		}
	}
}
