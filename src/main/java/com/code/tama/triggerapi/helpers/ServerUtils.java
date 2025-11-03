/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import java.io.File;
import java.nio.file.Path;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ServerUtils {
	/**
	 * Returns a Path pointing at the world folder (the server world folder) when
	 * available. Fallbacks: - If running on a server (ServerLevel or current
	 * MinecraftServer), returns server.getWorldPath(LevelResource.ROOT) - If
	 * running on client singleplayer, uses the integrated server - If none of the
	 * above available (e.g., client connected to remote server), falls back to game
	 * directory Note: can't discover a remote server's filesystem from the client.
	 */
	public static Path getWorldDirectoryPath(@Nullable Level level) {
		// If we have a ServerLevel, use the server directly.
		if (level instanceof ServerLevel serverLevel) {
			MinecraftServer server = serverLevel.getServer();
			return server.getWorldPath(LevelResource.ROOT);
		}

		// If we are running on server context but without a level reference:
		// (Forge: ServerLifecycleHooks.getCurrentServer() is another option)
		MinecraftServer currentServer = getCurrentServer();
		if (currentServer != null) {
			return currentServer.getWorldPath(LevelResource.ROOT);
		}

		// Client side: try integrated server (singleplayer)
		if (Minecraft.getInstance() != null) {
			IntegratedServer integrated = Minecraft.getInstance().getSingleplayerServer();
			if (integrated != null) {
				return integrated.getWorldPath(LevelResource.ROOT);
			}

			// Last-resort fallback: client game directory (not the actual world folder on
			// remote servers)
			File gameDir = Minecraft.getInstance().gameDirectory;
			if (gameDir != null) {
				return gameDir.toPath();
			}
		}

		// If everything else fails, return current working directory
		return Path.of(".");
	}

	public static Path getServerDirectoryPath() {
		return getCurrentServer().getWorldPath(LevelResource.ROOT);
	}

	public static MinecraftServer getCurrentServer() {
		// If running on the server thread directly, MinecraftServer.getServer() isn't a
		// thing in recent mappings.
		// Use Minecraft.getInstance().getSingleplayerServer() for integrated server
		// (checked above),
		// or use Forge ServerLifecycleHooks on server side.
		try {
			return ServerLifecycleHooks.getCurrentServer();
		} catch (Throwable t) {
			return Minecraft.getInstance().getSingleplayerServer();
		}
	}

	/**
	 * Convenience that returns a File instead of Path.
	 */
	public static File getWorldDirectoryFile(@Nullable Level level) {
		return getWorldDirectoryPath(level).toFile();
	}
}
