// src/main/java/com/code/tama/tts/core/ModShaders.java
// IMPORTANT: Adjust this package to your *actual* file path
package com.code.tama.tts.core;

import com.code.tama.tts.TTSMod;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.IOException;
import java.util.function.Consumer;

public class ShadersUtil {

    public static ShaderInstance CHROMIUM_SHADER;

    // We will register an instance of this listener in ClientSetup
    public static final MinecraftShaderLoader CHROMIUM_SHADER_LOADER =
            new MinecraftShaderLoader(new ResourceLocation(TTSMod.MODID, "chromium_shader"), DefaultVertexFormat.BLOCK, // Corrected VertexFormat
                    (shader) -> CHROMIUM_SHADER = shader);

    // No init method here anymore. The listener instance will be registered directly.

    // A helper class to load shaders on resource reload
    private static class MinecraftShaderLoader implements ResourceManagerReloadListener {
        private final ResourceLocation shaderProgramName;
        private final VertexFormat format;
        private final Consumer<ShaderInstance> consumer;

        public MinecraftShaderLoader(ResourceLocation shaderProgramName, VertexFormat format, Consumer<ShaderInstance> consumer) {
            this.shaderProgramName = shaderProgramName;
            this.format = format;
            this.consumer = consumer;
        }

        // THIS IS THE CORRECT METHOD TO IMPLEMENT FOR ResourceManagerReloadListener IN FORGE 1.20.X
        @Override
        public void onResourceManagerReload(ResourceManager pResourceManager) {
            try {
                // Use getPath() for the name parameter, as the ResourceLocation provides the full path.
                ShaderInstance shader = new ShaderInstance(pResourceManager, shaderProgramName.getPath(), format);
                consumer.accept(shader);
            } catch (IOException e) {
                TTSMod.LOGGER.error("Failed to load custom shader: " + shaderProgramName, e);
                // In a synchronous reload, you might rethrow as a RuntimeException if critical
                throw new RuntimeException("Failed to load shader: " + shaderProgramName, e);
            }
        }

        // IMPORTANT: The getId() method is NOT part of ResourceManagerReloadListener directly.
        // It's part of the `net.minecraft.server.packs.resources.ResourceProvider` interface,
        // which some listeners might implement. If you had it, remove it.
        // Also, `getFabricId()` is not standard Forge.
    }
}