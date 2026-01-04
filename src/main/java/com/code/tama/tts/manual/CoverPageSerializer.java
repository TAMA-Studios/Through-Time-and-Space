/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;

public class CoverPageSerializer extends PageSerializer {

	public CoverPageSerializer() {
		super(str -> str.contentEquals("cover"));
	}

	@Override
	public List<Page> read(JsonObject root) {
		CoverPage page = new CoverPage();

		page.setTitle(root.get("title").getAsString());

		if (root.has("icon"))
			page.setIcon(new ResourceLocation(root.get("icon").getAsString()));

		return Lists.newArrayList(page);
	}
}
