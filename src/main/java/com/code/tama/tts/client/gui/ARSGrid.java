package com.code.tama.tts.client.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

/**
 * A single ARS room entry: where it sits on the grid, its display name,
 * and the color used to represent it on the map.
 */
@Getter
public class ARSGrid {

    private final ARSPos pos;
    @Setter
    private String name;
    @Setter
    private int color; // packed 0xRRGGBB

    public ARSGrid(ARSPos pos, String name, int color) {
        this.pos = pos;
        this.name = name;
        this.color = color;
    }

    public ARSGrid(ARSPos pos, String name, String hexColor) {
        this(pos, name, parseHexColor(hexColor));
    }

    public static int parseHexColor(String hex) {
        String cleaned = hex.startsWith("#") ? hex.substring(1) : hex;
        return Integer.parseInt(cleaned, 16) & 0xFFFFFF;
    }

    public String getHexColor() {
        return String.format("#%06X", color);
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.put("Pos", pos.serialize());
        tag.putString("Name", name);
        tag.putInt("Color", color);
        return tag;
    }

    public static ARSGrid deserialize(CompoundTag tag) {
        return new ARSGrid(
                ARSPos.deserialize(tag.getCompound("Pos")),
                tag.getString("Name"),
                tag.getInt("Color")
        );
    }
}