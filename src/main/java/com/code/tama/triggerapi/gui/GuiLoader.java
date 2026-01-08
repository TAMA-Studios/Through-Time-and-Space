package com.code.tama.triggerapi.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads GUI definitions from datapacks
 * Looks in data/modid/triggerapi/gui/*.json
 */
public class GuiLoader extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiLoader.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private static final Map<ResourceLocation, GuiDefinition> GUI_DEFINITIONS = new HashMap<>();
    
    public GuiLoader() {
        super(GSON, "triggerapi/gui");
    }
    
    @Override
    protected void apply(Map<ResourceLocation, com.google.gson.JsonElement> map, 
                        ResourceManager resourceManager, ProfilerFiller profiler) {
        GUI_DEFINITIONS.clear();
        
        map.forEach((location, json) -> {
            try {
                GuiDefinition definition = GSON.fromJson(json, GuiDefinition.class);
                GUI_DEFINITIONS.put(location, definition);
                LOGGER.info("Loaded GUI definition: {}", location);
            } catch (Exception e) {
                LOGGER.error("Failed to load GUI definition: {}", location, e);
            }
        });
        
        LOGGER.info("Loaded {} GUI definitions", GUI_DEFINITIONS.size());
    }
    
    public static GuiDefinition getGuiDefinition(ResourceLocation location) {
        return GUI_DEFINITIONS.get(location);
    }
    
    public static Map<ResourceLocation, GuiDefinition> getAllDefinitions() {
        return GUI_DEFINITIONS;
    }
}