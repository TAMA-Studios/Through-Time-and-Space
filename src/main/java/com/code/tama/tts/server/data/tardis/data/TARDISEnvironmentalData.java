package com.code.tama.tts.server.data.tardis.data;

import com.code.tama.triggerapi.helpers.MathUtils;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TARDISEnvironmentalData {
    public static final Codec<TARDISEnvironmentalData> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.FLOAT.fieldOf("lightLevel").forGetter(TARDISEnvironmentalData::getLightLevel),
                    Codec.FLOAT.fieldOf("gravityLevel").forGetter(TARDISEnvironmentalData::getGravityLevel),
                    Codec.FLOAT.fieldOf("oxygenLevel").forGetter(TARDISEnvironmentalData::getOxygenLevel)
            )
            .apply(instance, TARDISEnvironmentalData::new));


    float LightLevel, gravityLevel = 0.08f, oxygenLevel;
    ITARDISLevel TARDIS;

    public TARDISEnvironmentalData(float lightLevel, float gravityLevel, float oxygenLevel) {
        LightLevel = lightLevel;
        this.gravityLevel = gravityLevel;
        this.oxygenLevel = oxygenLevel;
    }

    public TARDISEnvironmentalData(TARDISLevelCapability TARDIS) {
        this.TARDIS = TARDIS;
    }

    public void SetLightLevel(float LightLevel) {
        LightLevel = Math.max(Math.min(LightLevel, 1.5F), 0.1F);
        this.LightLevel = LightLevel;
    }

    public float getLightLevel() {
        if (this.TARDIS.GetData().isSparking()) {
            return this.LightLevel - (MathUtils.clamp(this.TARDIS.GetLevel().random.nextFloat(), 0.0f, 0.4f) - 0.2f);
        } else
            return this.LightLevel;
    }
}
