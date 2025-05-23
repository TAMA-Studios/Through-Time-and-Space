package com.code.tama.triggerapi;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigHelper {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static ForgeConfigSpec CONFIG_SPEC;

    public static ForgeConfigSpec.IntValue maxTeleportDistance;
    public static ForgeConfigSpec.BooleanValue enableLogging;

    static {
        BUILDER.push("General Settings");
        maxTeleportDistance = BUILDER
                .comment("Maximum distance for entity teleportation")
                .defineInRange("maxTeleportDistance", 100, 1, 1000);
        enableLogging = BUILDER
                .comment("Enable detailed logging")
                .define("enableLogging", true);
        BUILDER.pop();
        CONFIG_SPEC = BUILDER.build();
    }

    @SuppressWarnings("removal")
    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG_SPEC);
    }

    public static <T> T getValue(ForgeConfigSpec.ConfigValue<T> configValue) {
        return configValue.get();
    }
}