/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

/**
 * Tiny, allocation-light playback clock for one animated instance. Drive it
 * from game time (server tick + partialTick), not System.currentTimeMillis(),
 * so it pauses with the game and is frame-rate independent.
 */
public class AnimationPlayer {
	private GeoAnimation current;
	private float startTimeTicks = 0;

	public void play(GeoAnimation animation, float nowTicks) {
		if (this.current != animation) {
			this.current = animation;
			this.startTimeTicks = GeoAnimTicker.getTicks();
		}
	}

	public void stop() {
		this.current = null;
	}

	public boolean isPlaying() {
		return current != null;
	}

	/**
	 * Applies the current animation to the model at the given game time (ticks,
	 * 20/sec) + partial tick.
	 */
	public void apply(GeoModel model, float nowTicks, float partialTick) {
		model.resetPose();
		if (current == null)
			return;
		float elapsedSeconds = ((GeoAnimTicker.getTicks() + partialTick) - startTimeTicks) / 20.0f;
		current.apply(model, elapsedSeconds);
	}
}