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
                    SpaceTimeCoordinate.CODEC.fieldOf("location").forGetter(DoorData::getLocation),
                    Codec.INT.fieldOf("openDoors").forGetter(DoorData::getDoorsOpen))
            .apply(instance, DoorData::new));

    float YRot;
    SpaceTimeCoordinate location;
    int DoorsOpen = 0;

    public int CycleDoor() {
        return this.DoorsOpen = switch (this.DoorsOpen) {
            case 0 -> 1;
            case 1 -> 2;
            default -> 0;
        };
    }
}
