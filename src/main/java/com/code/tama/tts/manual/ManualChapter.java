/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

import net.minecraft.resources.ResourceLocation;

@Getter
public class ManualChapter {

	private final List<Page> pages = Lists.newArrayList();
	private final String displayName;

	public ManualChapter(String display, List<Page> pages) {
		this.displayName = display;
		this.pages.addAll(pages);
	}

	public void insertPage(int index, Page page) {
		this.pages.add(index, page);
	}

	public static ManualChapter read(ResourceLocation id, JsonObject object, String localeCode) {
		try {
			String display = object.get("display_name").getAsString();

			List<Page> pages = Lists.newArrayList();

			for (JsonElement e : object.get("pages").getAsJsonArray()) {
				ResourceLocation pageID = Page.getPageResourceLocation(new ResourceLocation(e.getAsString()),
						localeCode);
				pages.addAll(Objects.requireNonNull(Page.read(pageID)));
			}

			return new ManualChapter(display, pages);

		} catch (Exception e) {
			return null;
		}
	}

	public static ResourceLocation getChapterResourceLocation(ResourceLocation loc, String localeCode) {
		return new ResourceLocation(loc.getNamespace(), "manual/" + localeCode + "/chapter/" + loc.getPath() + ".json");
	}

}
