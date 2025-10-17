/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models.core;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IAnimateableModel<T extends BlockEntity> {
	void SetupAnimations(T tile, float ageInTicks);
}
