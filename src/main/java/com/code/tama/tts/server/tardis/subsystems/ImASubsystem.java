/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.tardis.subsystems;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ImASubsystem {
	void OnActivate(Level level, BlockPos pos);
	void OnDeActivate(Level level, BlockPos pos);
	boolean isActivated();
	String name();
}
