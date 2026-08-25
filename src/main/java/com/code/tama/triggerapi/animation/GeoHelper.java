/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import com.code.tama.triggerapi.TriggerAPI;

public class GeoHelper {

	// One GeoItemRenderer per (model, texture) pair, built once and reused forever.
	// This is the fix for the "new GeoItemRenderer every call" bug,
	// setAnimation-style
	// calls and actual rendering now always hit the same object.
	private static final Map<String, GeoItemRenderer> RENDERER_CACHE = new ConcurrentHashMap<>();

	/**
	 * Texture and Model are passed in as lowercase a-z modelname/texturenames, such
	 * as "colt" or "tardis". Safe to call every frame, returns the same cached
	 * instance after the first call for a given (model, texture) pair.
	 */
	public static GeoItemRenderer getRenderer(String model, String texture) {
		String key = model + "|" + texture;
		return RENDERER_CACHE.computeIfAbsent(key, k -> new GeoItemRenderer(getModel(model),
				ResourceLocation.tryBuild(TriggerAPI.MOD_ID, "textures/" + texture + ".png"), getAnimations(model)));
	}

	/**
	 * GeoModelLoader/GeoAnimationLoader cache internally, so this is also safe to
	 * call repeatedly.
	 */
	public static GeoModel getModel(String model) {
		return GeoModelLoader.load(Minecraft.getInstance().getResourceManager(),
				ResourceLocation.tryBuild(TriggerAPI.MOD_ID, "models/" + model + ".geo.json"));
	}

	/**
	 * Animations are stored in "animations/modelname.animation.json"
	 *
	 * @param model
	 *            The modelname to get the animation of
	 * @return A Model's animations, keyed by animation name
	 */
	public static Map<String, GeoAnimation> getAnimations(String model) {
		return GeoAnimationLoader.load(Minecraft.getInstance().getResourceManager(),
				ResourceLocation.tryBuild(TriggerAPI.MOD_ID, "animations/" + model + ".animation.json"));
	}

	/**
	 * Starts (or restarts) an animation on THIS specific stack. Stored on the
	 * stack's own NBT, so two stacks of the same item animate independently and
	 * GeoItemRenderer (a shared singleton) always reads the right one because it's
	 * told which stack it's currently rendering.
	 */
	public static void playAnimation(ItemStack stack, String animationName, long nowTicks) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putString(GeoItemRenderer.NBT_ANIM_NAME, animationName);
		tag.putLong(GeoItemRenderer.NBT_ANIM_START, nowTicks);
	}

	public static void stopAnimation(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag != null) {
			tag.remove(GeoItemRenderer.NBT_ANIM_NAME);
			tag.remove(GeoItemRenderer.NBT_ANIM_START);
		}
	}
}