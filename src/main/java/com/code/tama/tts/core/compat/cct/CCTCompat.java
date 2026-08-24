/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.compat.cct;

import com.code.tama.tts.core.compat.CompatClass;
import com.code.tama.tts.core.compat.cct.registry.CCTRegistry;

public class CCTCompat extends CompatClass {
	@Override
	public void runCompat() {
		CCTRegistry.init();
	}

	@Override
	public void runCommonSetup() {

	}
}
