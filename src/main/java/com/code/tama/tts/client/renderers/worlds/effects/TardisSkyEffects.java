/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

public class TardisSkyEffects extends DimensionSpecialEffects {

    private final ResourceKey<DimensionType> targetType;

    // Private field to store the light value
    private float lightValue = 1.0f;  // Default light value is 1.0 (full brightness)

    public TardisSkyEffects(ResourceKey<DimensionType> targetType) {
        super(Float.NaN, false, SkyType.NONE, false, false);
        this.targetType = targetType;
    }

    // Getter for the light value
    public float getLightValue() {
        return this.lightValue;
    }

    // Setter for the light value
    public void setLightValue(float lightValue) {
        this.lightValue = lightValue;
    }

//    @Override
//    public Vec3 getBrightnessDependentFogColor(Vec3 skyColor, float brightness) {
//        // Modify the sky color based on the current light value
//        return skyColor.scale(brightness);  // Adjust brightness according to lightValue
//    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 skyColor, float brightness) {
        // Get the current level
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            // Retrieve the ambient light value from the level's capability
            float ambientLightValue = 0.0f;//level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
//                    .map(ITARDISLevel::GetLightLevel)
//                    .orElse(0.0f);  // Default to 0.0 if no capability is found

            // Modify the sky color based on the ambient light value
            return skyColor.add(ambientLightValue, ambientLightValue, ambientLightValue);
        }
        return skyColor;
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }
}
