package com.code.tama.mtm.data;

import com.code.tama.mtm.server.misc.SpaceTimeCoordinate;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

@Setter
@Getter
public class DoorData implements INBTSerializable<CompoundTag> {
    SpaceTimeCoordinate location = new SpaceTimeCoordinate();

    float YRot;

    public DoorData(float YRot, SpaceTimeCoordinate Location) {
        this.location = Location;
        this.YRot = YRot;
    }

    public DoorData() {}

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("location", this.location.serializeNBT());
        tag.putFloat("yRot", this.YRot);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.location = SpaceTimeCoordinate.of(tag);
        this.YRot = tag.getFloat("yRot");
    }

}