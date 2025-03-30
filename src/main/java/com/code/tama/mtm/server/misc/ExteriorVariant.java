package com.code.tama.mtm.server.misc;

import com.code.tama.mtm.server.enums.tardis.ExteriorModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class ExteriorVariant implements INBTSerializable<CompoundTag> {
    private ExteriorModel ExteriorType;
    private ResourceLocation Texture;
    private String Name;

    public ExteriorVariant(ExteriorModel exteriorModel, ResourceLocation texture, String name) {
        this.ExteriorType = exteriorModel;
        this.Texture = texture;
        this.Name = name;
    }

    public ExteriorVariant(CompoundTag Tag) {
        this.deserializeNBT(Tag);
    }

    public ExteriorModel GetExteriorType() {
        return ExteriorType;
    }

    public String GetExteriorName() {
        return Name;
    }

    public ResourceLocation GetTexture() {
        return new ResourceLocation(this.Texture.getNamespace(), this.Texture.getPath().replace(".png", "") + ".png");
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag Tag = new CompoundTag();
        Tag.putString("namespace", this.Texture.getNamespace());
        Tag.putString("path", this.Texture.getPath());
        Tag.putInt("model", this.ExteriorType.ordinal());
        return Tag;
    }

    @Override
    public void deserializeNBT(CompoundTag Tag) {
        this.Texture = new ResourceLocation(Tag.getString("namespace"), Tag.getString("path"));
        this.ExteriorType = ExteriorModel.values()[Tag.getInt("model")];
    }
}
