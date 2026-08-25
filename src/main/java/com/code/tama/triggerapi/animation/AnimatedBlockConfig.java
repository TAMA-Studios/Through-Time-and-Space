/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

/**
 * Three independent block-rendering backends, sharing the same GeoModel /
 * GeoRenderer / AnimatedBlockRegistry underneath, only "who fires the draw call
 * each frame" changes: <br />
 * LEVEL_EVENT - RenderLevelStageEvent (Forge's own stable public hook).
 * Default. No BlockEntity, no mixin. MIXIN - Tail-injects vanilla
 * LevelRenderer#renderLevel directly, bypassing Forge's event bus entirely.
 * Same registry, same draw code, reached a different way. Use this if something
 * in your load order (another mod's rendering fork, a shader pack, etc.) is
 * interfering with RenderLevelStageEvent specifically. BLOCK_ENTITY - Falls
 * back to a minimal non-ticking BlockEntity + BlockEntityRenderer. Real (tiny)
 * per-block cost, but the most "boring"/well-trodden path if the other two ever
 * fight with a specific mod in a way that's not worth debugging. <br />
 * Wire USE_MODE to a real Forge ModConfigSpec enum value in production; left as
 * a plain static field here so the rest of the system doesn't need to know
 * about your config plumbing.
 */
public class AnimatedBlockConfig {

	public enum Mode {
		LEVEL_EVENT, MIXIN, BLOCK_ENTITY
	}

	public static volatile Mode MODE = Mode.LEVEL_EVENT;
}
