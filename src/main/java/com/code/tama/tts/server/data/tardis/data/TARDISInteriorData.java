/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis.data;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;

import com.code.tama.triggerapi.helpers.MathUtils;

@Getter
@Setter
public class TARDISInteriorData {
	public static final Codec<TARDISInteriorData> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Codec.FLOAT.fieldOf("light_level").forGetter(TARDISInteriorData::getLightLevel),
					Codec.FLOAT.fieldOf("gravity_level").forGetter(TARDISInteriorData::getGravityLevel),
					Codec.FLOAT.fieldOf("oxygen_level").forGetter(TARDISInteriorData::getOxygenLevel),
					Codec.INT.fieldOf("humId").forGetter(TARDISInteriorData::getHum))
			.apply(instance, TARDISInteriorData::new));

	float LightLevel, gravityLevel = 0.08f, oxygenLevel;
	ITARDISLevel TARDIS;
	int hum;

	public TARDISInteriorData(float lightLevel, float gravityLevel, float oxygenLevel, int hum) {
		LightLevel = lightLevel;
		this.gravityLevel = gravityLevel;
		this.oxygenLevel = oxygenLevel;
		this.hum = hum;
	}

	public TARDISInteriorData(TARDISLevelCapability TARDIS) {
		this.TARDIS = TARDIS;
	}

	public void SetLightLevel(float LightLevel) {
		LightLevel = Math.max(Math.min(LightLevel, 1.3F), 0.1F);
		this.LightLevel = LightLevel;
	}

	public float getLightLevel() {
		if (this.TARDIS.GetData().isSparking()) {
			return this.LightLevel - (MathUtils.clamp(this.TARDIS.GetLevel().random.nextFloat(), 0.0f, 0.4f) - 0.2f);
		} else
			return this.LightLevel;
	}
}
