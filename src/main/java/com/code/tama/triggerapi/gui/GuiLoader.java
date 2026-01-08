package com.code.tama.triggerapi.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads GUI definitions and Lua scripts from datapacks
 * GUIs: data/modid/triggerapi/gui/*.json
 * Scripts: data/modid/triggerapi/gui/scripts/*.lua
 */
public class GuiLoader extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiLoader.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private static final Map<ResourceLocation, GuiDefinition> GUI_DEFINITIONS = new HashMap<>();
    private static final Map<String, String> LUA_SCRIPTS = new HashMap<>();
    
    private ResourceManager currentResourceManager;
    
    public GuiLoader() {
        super(GSON, "triggerapi/gui");
    }
    
    @Override
    protected void apply(Map<ResourceLocation, com.google.gson.JsonElement> map, 
                        ResourceManager resourceManager, ProfilerFiller profiler) {
        this.currentResourceManager = resourceManager;
        
        GUI_DEFINITIONS.clear();
        LUA_SCRIPTS.clear();
        
        // Load GUI definitions
        map.forEach((location, json) -> {
            try {
                GuiDefinition definition = GSON.fromJson(json, GuiDefinition.class);
                GUI_DEFINITIONS.put(location, definition);
                LOGGER.info("Loaded GUI definition: {}", location);
            } catch (Exception e) {
                LOGGER.error("Failed to load GUI definition: {}", location, e);
            }
        });
        
        // Load Lua scripts
        loadLuaScripts(resourceManager);
        
        LOGGER.info("Loaded {} GUI definitions and {} Lua scripts", 
            GUI_DEFINITIONS.size(), LUA_SCRIPTS.size());
    }
    
    private void loadLuaScripts(ResourceManager resourceManager) {
        // Scan all namespaces for lua scripts
        for (String namespace : resourceManager.getNamespaces()) {
            try {
                ResourceLocation scriptDir = new ResourceLocation(namespace, "triggerapi/gui/scripts");
                
                // Try to find all .lua files
                resourceManager.listResources("triggerapi/gui/scripts", 
                    path -> path.getPath().endsWith(".lua")).forEach((location, resource) -> {
                    try {
                        InputStream stream = resource.open();
                        BufferedReader reader = new BufferedReader(
                            new InputStreamReader(stream, StandardCharsets.UTF_8));
                        
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        
                        String scriptPath = location.getPath()
                            .replace("triggerapi/gui/scripts/", "")
                            .replace(".lua", "");
                        String scriptName = namespace + ":" + scriptPath;
                        
                        LUA_SCRIPTS.put(scriptName, content.toString());
                        LOGGER.info("Loaded Lua script: {}", scriptName);
                        
                        reader.close();
                    } catch (Exception e) {
                        LOGGER.error("Failed to load Lua script: {}", location, e);
                    }
                });
            } catch (Exception e) {
                // Namespace doesn't have scripts folder, skip
            }
        }
    }
    
    public static GuiDefinition getGuiDefinition(ResourceLocation location) {
        return GUI_DEFINITIONS.get(location);
    }
    
    public static Map<ResourceLocation, GuiDefinition> getAllDefinitions() {
        return GUI_DEFINITIONS;
    }
    
    public static String getLuaScript(String scriptName) {
        return LUA_SCRIPTS.get(scriptName);
    }
    
    public static Map<String, String> getAllScripts() {
        return LUA_SCRIPTS;
    }
}