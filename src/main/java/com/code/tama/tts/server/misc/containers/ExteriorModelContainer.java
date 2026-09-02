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
			.group(ResourceLocation.CODEC.fieldOf("model").forGetter(ExteriorModelContainer::getModel),
					ResourceLocation.CODEC.fieldOf("texture").forGetter(ExteriorModelContainer::getTexture),
					ResourceLocation.CODEC.fieldOf("lightmap").forGetter(ExteriorModelContainer::getLightMap),
					Codec.FLOAT.fieldOf("maxDoorDeg").forGetter(ExteriorModelContainer::getMaxDoorDeg),
					Codec.STRING.fieldOf("name").forGetter(ExteriorModelContainer::getName))
			.apply(exteriorInstance, ExteriorModelContainer::new));

	private ResourceLocation LightMap;
	private ResourceLocation Model;
	private String Name;
	private float maxDoorDeg;
	private ResourceLocation Texture;

	public ExteriorModelContainer(ResourceLocation model, ResourceLocation texture, ResourceLocation lightMap,
			float maxDoorDeg, String name) {
		this.Model = model;
		this.Name = name;
		this.Texture = texture;
		this.LightMap = lightMap;
		this.maxDoorDeg = maxDoorDeg;
	}

	public String getTranslationKey() {
		return Model.getNamespace() + ".exterior." + Name;
	}
}
