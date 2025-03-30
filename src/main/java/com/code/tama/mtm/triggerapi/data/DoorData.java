package com.code.tama.mtm.triggerapi.data;

import com.code.tama.mtm.server.misc.SpaceTimeCoordinate;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

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

    public float getYRot() {
        return this.YRot;
    }

    public void setYRot(float YRot) {
        this.YRot = YRot;
    }

    public SpaceTimeCoordinate getLocation() {
        return this.location;
    }

    public void setLocation(SpaceTimeCoordinate location) {
        this.location = location;
    }
}
