package com.code.tama.tts.server.worlds;

import com.code.tama.tts.server.dimensions.TARDISArtificialDimensionChunkGenerator;
import com.code.tama.tts.server.dimensions.TARDISNaturalDimensionChunkGenerator;
import com.code.tama.tts.server.worlds.dimension.TDimensions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.dimension.LevelStem;

public class TStemCreation {
    public static LevelStem createArtificialTARDISLevelStem(MinecraftServer server) {
        return new LevelStem(server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
                .getHolderOrThrow(TDimensions.TARDIS_DIM_TYPE), new TARDISArtificialDimensionChunkGenerator());
    }

    public static LevelStem createNaturalTARDISLevelStem(MinecraftServer server) {
        return new LevelStem(server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
                .getHolderOrThrow(TDimensions.TARDIS_DIM_TYPE), new TARDISNaturalDimensionChunkGenerator());
    }
}
