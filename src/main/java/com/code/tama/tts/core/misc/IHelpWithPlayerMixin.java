/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.misc;

import com.code.tama.tts.core.misc.containers.PlayerPosition;

public interface IHelpWithPlayerMixin {
	PlayerPosition GetLastPosition();

	String GetViewedTARDIS();

	void SetLastPlayerPosition(PlayerPosition playerPosition);

	void SetViewedTARDIS(String tardis);
}
