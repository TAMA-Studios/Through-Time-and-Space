/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis;

import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DoorData {
    public static Codec<DoorData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.FLOAT.fieldOf("yrot").forGetter(DoorData::getYRot),
                    SpaceTimeCoordinate.CODEC.fieldOf("location").forGetter(DoorData::getLocation))
            .apply(instance, DoorData::new));

    float YRot;
    SpaceTimeCoordinate location;
}
