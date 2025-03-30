package com.code.tama.mtm.server.worlds.biomes;

import net.minecraft.world.level.biome.Climate;

public class ParameterPoints {
    public static Climate.ParameterPoint GallifreyPlains() {
        Climate.Parameter temperature = Climate.Parameter.span(0.2F, 0.6F);
        Climate.Parameter humidity = Climate.Parameter.span(0.3F, 0.8F);
        Climate.Parameter continentalness = Climate.Parameter.point(0.5F);
        Climate.Parameter erosion = Climate.Parameter.span(0.1F, 0.4F);
        Climate.Parameter depth = Climate.Parameter.point(0.3F);
        Climate.Parameter weirdness = Climate.Parameter.point(0.0F);

        return Climate.parameters(
                temperature, humidity, continentalness, erosion, depth, weirdness, 0.1F
        );
    }

    public static Climate.ParameterPoint GallifreyDesert() {
        Climate.Parameter temperature = Climate.Parameter.span(0.5F, 0.9F);
        Climate.Parameter humidity = Climate.Parameter.span(0.3F, 0.6F);
        Climate.Parameter continentalness = Climate.Parameter.point(0.5F);
        Climate.Parameter erosion = Climate.Parameter.span(0.1F, 0.4F);
        Climate.Parameter depth = Climate.Parameter.point(0.3F);
        Climate.Parameter weirdness = Climate.Parameter.point(0.0F);

        return Climate.parameters(
                temperature, humidity, continentalness, erosion, depth, weirdness, 0.1F
        );
    }
}
