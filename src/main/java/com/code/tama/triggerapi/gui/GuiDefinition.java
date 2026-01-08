package com.code.tama.triggerapi.gui;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

/**
 * Represents a GUI definition loaded from JSON
 * Location: data/modid/triggerapi/gui/example.json
 */
@Getter
public class GuiDefinition {
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("size")
    private int size = 27; // Default chest size
    
    @SerializedName("buttons")
    private List<ButtonDefinition> buttons;
    
    @SerializedName("background")
    private BackgroundDefinition background;

    public static class ButtonDefinition {
        @SerializedName("slot")
        private int slot;
        
        @SerializedName("item")
        private String item; // Item ID like "minecraft:diamond"
        
        @SerializedName("name")
        private String name;
        
        @SerializedName("lore")
        private List<String> lore;
        
        @SerializedName("glow")
        private boolean glow = false;
        
        @SerializedName("script")
        private String script; // Lua script to execute
        
        @SerializedName("close_on_click")
        private boolean closeOnClick = false;
        
        public int getSlot() {
            return slot;
        }
        
        public String getItem() {
            return item;
        }
        
        public String getName() {
            return name;
        }
        
        public List<String> getLore() {
            return lore;
        }
        
        public boolean isGlow() {
            return glow;
        }
        
        public String getScript() {
            return script;
        }
        
        public boolean shouldCloseOnClick() {
            return closeOnClick;
        }
    }
    
    public static class BackgroundDefinition {
        @SerializedName("item")
        private String item;
        
        @SerializedName("fill_empty")
        private boolean fillEmpty = false;
        
        public String getItem() {
            return item;
        }
        
        public boolean shouldFillEmpty() {
            return fillEmpty;
        }
    }
}