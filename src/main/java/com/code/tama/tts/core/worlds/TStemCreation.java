/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.worlds;

import com.code.tama.tts.core.dimensions.TARDISNaturalDimensionChunkGenerator;
import com.code.tama.tts.core.dimensions.VoidDimensionChunkGenerator;
import com.code.tama.tts.core.worlds.dimension.TDimensions;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.dimension.LevelStem;

public class TStemCreation {
	public static LevelStem createArtificialTARDISLevelStem(MinecraftServer server) {
		return new LevelStem(server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
				.getHolderOrThrow(TDimensions.TARDIS_DIM_TYPE), new VoidDimensionChunkGenerator());
	}

	public static LevelStem createSPAAACELevelStem(MinecraftServer server) {
		return new LevelStem(server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
				.getHolderOrThrow(TDimensions.SPACE_DIM), new VoidDimensionChunkGenerator());
	}

	public static LevelStem createNaturalTARDISLevelStem(MinecraftServer server) {
		return new LevelStem(server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
				.getHolderOrThrow(TDimensions.TARDIS_DIM_TYPE), new TARDISNaturalDimensionChunkGenerator());
	}
}
