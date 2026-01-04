/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import java.util.List;
import java.util.function.Predicate;

import com.google.gson.JsonObject;

public abstract class PageSerializer {

	Predicate<String> type;

	public PageSerializer(Predicate<String> type) {
		this.type = type;
	}

	public boolean match(String type) {
		return this.type.test(type);
	}

	public abstract List<Page> read(JsonObject root);

}