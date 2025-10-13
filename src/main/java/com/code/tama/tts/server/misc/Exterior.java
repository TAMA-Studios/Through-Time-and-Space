/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.resources.ResourceLocation;

@NoArgsConstructor
@Getter
public class Exterior {
    public static Codec<Exterior> CODEC = RecordCodecBuilder.create(exteriorInstance -> exteriorInstance
            .group(
                    ResourceLocation.CODEC.fieldOf("model").forGetter(Exterior::getModel),
                    Codec.STRING.fieldOf("name").forGetter(Exterior::getName))
            .apply(exteriorInstance, Exterior::new));

    private ResourceLocation Model;
    private String Name;

    public Exterior(ResourceLocation model, String name) {
        Model = model;
        Name = name;
    }
}
