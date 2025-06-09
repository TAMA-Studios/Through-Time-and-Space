/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class Exterior implements INBTSerializable<CompoundTag> {
    private String Name;
    private ResourceLocation ModelName;

    public Exterior(String name, ResourceLocation modelName) {
        this.Name = name;
        this.ModelName = modelName;
    }

    public Exterior(CompoundTag Tag) {
        this.deserializeNBT(Tag);
    }

    public String GetExteriorName() {
        return Name;
    }

    public ResourceLocation GetModelName() {
        return ModelName;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag Tag = new CompoundTag();
        Tag.putString("name", this.Name);
        Tag.putString("model", this.ModelName.toString());
        return Tag;
    }

    @Override
    public void deserializeNBT(CompoundTag Tag) {
        this.Name = Tag.getString("name");
        this.ModelName = ResourceLocation.parse(Tag.getString("model"));
    }
}
