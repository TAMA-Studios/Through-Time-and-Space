/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

public class ControlLists {
	public static AbstractControlList GetHudolin() {
		return new HudolinControlList();
	}

	public static AbstractControlList GetNESS() {
		return new NESSControlList();
	}

	public static AbstractControlList GetTokamak() {
		return new TokamakControlList();
	}
}
