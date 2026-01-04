/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import net.minecraft.client.gui.components.Button;

public class ReturnToIndexButton extends ChangeChapterButton {
	public ReturnToIndexButton(int x, int y, boolean isForward, Button.OnPress onPress, boolean playTurnSound) {
		super(x, y, 12, 16, isForward, onPress, playTurnSound);
	}
}
