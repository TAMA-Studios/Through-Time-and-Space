/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.containers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.resources.ResourceLocation;

@NoArgsConstructor
@Getter
public class ExteriorModelContainer {
    public static Codec<ExteriorModelContainer> CODEC = RecordCodecBuilder.create(exteriorInstance -> exteriorInstance
            .group(
                    ResourceLocation.CODEC.fieldOf("model").forGetter(ExteriorModelContainer::getModel),
                    Codec.STRING.fieldOf("name").forGetter(ExteriorModelContainer::getName))
            .apply(exteriorInstance, ExteriorModelContainer::new));

    private ResourceLocation Model;
    private String Name;

    public ExteriorModelContainer(ResourceLocation model, String name) {
        Model = model;
        Name = name;
    }
}
