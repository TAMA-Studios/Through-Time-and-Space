/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.interfaces;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IAnimateableModel<T extends BlockEntity> {
    void SetupAnimations(T tile, float ageInTicks);
}
