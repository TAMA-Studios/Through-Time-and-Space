/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.tts.server.misc.containers.PlayerPosition;

public interface IHelpWithPlayerMixin {
	PlayerPosition GetLastPosition();

	String GetViewedTARDIS();

	void SetLastPlayerPosition(PlayerPosition playerPosition);

	void SetViewedTARDIS(String tardis);
}
