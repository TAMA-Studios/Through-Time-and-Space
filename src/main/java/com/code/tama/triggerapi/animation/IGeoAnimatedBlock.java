/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import net.minecraft.resources.ResourceLocation;

/**
 * Implement this on any Block that should be picked up by the no-block-entity
 * animation system. Only used client-side.
 */
public interface IGeoAnimatedBlock {
	GeoModel getGeoModel();
	ResourceLocation getGeoTexture();
}
