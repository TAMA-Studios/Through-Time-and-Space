package com.code.tama.mtm.server.misc.interfaces;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IConsoleModel<T extends BlockEntity> {
    void SetupAnimations(T tile, float ageInTicks);
}

