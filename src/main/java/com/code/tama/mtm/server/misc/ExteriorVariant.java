package com.code.tama.mtm.server.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class ExteriorVariant implements INBTSerializable<CompoundTag> {
    private ResourceLocation Texture;
    private String Name;
    private ResourceLocation ModelName;

    public ExteriorVariant(ResourceLocation texture, String name, ResourceLocation modelName) {
        this.Texture = texture;
        this.Name = name;
        this.ModelName = modelName;
    }

    public ExteriorVariant(CompoundTag Tag) {
        this.deserializeNBT(Tag);
    }

    public String GetExteriorName() {
        return Name;
    }

    public ResourceLocation GetModelName() {
        return ModelName;
    }

    public ResourceLocation GetTexture() {
        return new ResourceLocation(this.Texture.getNamespace(), this.Texture.getPath().replace(".png", "") + ".png");
    }

    public ResourceLocation GetEmmisiveTexture() {
        return new ResourceLocation(this.Texture.getNamespace(), this.Texture.getPath().replace(".png", "").replace("/box/", "/emmisive/") + ".png");
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag Tag = new CompoundTag();
        Tag.putString("namespace", this.Texture.getNamespace());
        Tag.putString("path", this.Texture.getPath());
        Tag.putString("name", this.Name);
        Tag.putString("modelName", this.ModelName.getPath());
        Tag.putString("modelNameNamespace", this.ModelName.getNamespace());
        return Tag;
    }

    @Override
    public void deserializeNBT(CompoundTag Tag) {
        this.Texture = new ResourceLocation(Tag.getString("namespace"), Tag.getString("path"));
        this.Name = Tag.getString("name");
        this.ModelName = new ResourceLocation(Tag.getString("modelNameNamespace"), Tag.getString("modelName"));
    }
}
