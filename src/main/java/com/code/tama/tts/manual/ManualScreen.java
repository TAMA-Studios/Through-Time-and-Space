/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.code.tama.tts.TTSMod;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.ItemStack;

public class ManualScreen extends Screen {
	public static final ResourceLocation TEXTURE = new ResourceLocation(TTSMod.MODID, "textures/gui/manual.png");
	private final List<ManualChapter> manualChapters;
	private int pageIndex;
	private int chapterIndex;
	private final String modid;
	private static final MutableComponent NEXT_PAGE = Component.translatable("tooltip.tts.manual.next_page");
	private static final MutableComponent PREV_PAGE = Component.translatable("tooltip.tts.manual.previous_page");
	private static final MutableComponent NEXT_CHAPTER = Component.translatable("tooltip.tts.manual.next_chapter");
	private static final MutableComponent PREV_CHAPTER = Component.translatable("tooltip.tts.manual.previous_chapter");
	private static final MutableComponent LAST_PAGE = Component.translatable("tooltip.tts.manual.last_page");
	private static final MutableComponent FIRST_PAGE = Component.translatable("tooltip.tts.manual.first_page");
	private ChangePageButton pageForward;
	private ChangePageButton pageBack;
	private ChangeChapterButton chapterForward;
	private ChangeChapterButton chapterBack;
	private ReturnToIndexButton endOfManual;
	private ReturnToIndexButton startOfManual;
	public int pageX;
	public int pageY;
	public int page2X;

	protected ManualScreen(Component titleIn, String modid) {
		super(titleIn);
		this.manualChapters = Lists.newArrayList();
		this.pageIndex = 0;
		this.chapterIndex = 0;
		this.pageX = this.width / 2 - 110;
		this.pageY = this.height / 2 - 70;
		this.page2X = this.pageX + 120;
		this.modid = modid;
		this.read();
		this.insertTOC();
	}

	public ManualScreen(ItemStack stack) {
		this(Component.literal("Manual"), TTSMod.MODID);
		if (stack.hasTag()) {
			assert stack.getTag() != null;
			if (stack.getTag().contains("page")) {
				this.pageIndex = stack.getTag().getInt("page");
			}

			if (stack.getTag().contains("chapter")) {
				this.chapterIndex = stack.getTag().getInt("chapter");
			}
		}
	}

	public ManualScreen() {
		this(Component.literal("Manual"), TTSMod.MODID);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	protected void init() {
		super.init();
		this.read();
		this.clearWidgets();
		this.addRenderableWidget(this.pageForward = new ChangePageButton(this.width / 2 + 95, this.height / 2 + 70,
				true, (button) -> this.turnPage(true), true));
		this.addRenderableWidget(this.pageBack = new ChangePageButton(this.width / 2 - 120, this.height / 2 + 70, false,
				(button) -> this.turnPage(false), true));
		this.addRenderableWidget(this.chapterForward = new ChangeChapterButton(this.width / 2 + 25,
				this.height / 2 + 70, true, (button) -> this.turnChapter(true), true));
		this.addRenderableWidget(this.chapterBack = new ChangeChapterButton(this.width / 2 - 45, this.height / 2 + 70,
				false, (button) -> this.turnChapter(false), true));
		this.updatePageWidths();
		this.insertTOC();
		this.addRenderableWidget(this.endOfManual = new ReturnToIndexButton(this.width / 2 + 125, this.height / 2 - 85,
				true, (button) -> this.turnToTableOfContents(true), true));
		this.addRenderableWidget(this.startOfManual = new ReturnToIndexButton(this.width / 2 - 140,
				this.height / 2 - 85, false, (button) -> this.turnToTableOfContents(false), true));
	}

	public void openPage(int chapterIndex, int pageIndex) {
		this.chapterIndex = chapterIndex;
		this.pageIndex = pageIndex;
	}

	public void updatePageWidths() {
		this.pageX = this.width / 2 - 110;
		this.pageY = this.height / 2 - 70;
		this.page2X = this.pageX + 120;
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		guiGraphics.blit(this.getTexture(), (this.width - 256) / 2, (this.height - 187) / 2, 0, 0, 256, 187);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		Pair<Page, Page> pages = this.getPages();
		if (pages.getFirst() != null) {
			pages.getFirst().render(guiGraphics, this.font, this.getGlobalPageNumber(), this.pageX, this.pageY,
					this.width, this.height);
		}

		if (pages.getSecond() != null) {
			pages.getSecond().render(guiGraphics, this.font, this.getGlobalPageNumber() + 1, this.page2X, this.pageY,
					this.width, this.height);
		}

		this.renderTooltips(guiGraphics, mouseX, mouseY, partialTicks);
	}

	private void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		if (this.pageForward.isHovered()) {
			guiGraphics.renderTooltip(this.font, NEXT_PAGE, mouseX, mouseY);
		}

		if (this.pageBack.isHovered()) {
			guiGraphics.renderTooltip(this.font, PREV_PAGE, mouseX, mouseY);
		}

		if (this.chapterForward.isHovered()) {
			guiGraphics.renderTooltip(this.font, NEXT_CHAPTER, mouseX, mouseY);
		}

		if (this.chapterBack.isHovered()) {
			guiGraphics.renderTooltip(this.font, PREV_CHAPTER, mouseX, mouseY);
		}

		if (this.endOfManual.isHovered()) {
			guiGraphics.renderTooltip(this.font, LAST_PAGE, mouseX, mouseY);
		}

		if (this.startOfManual.isHovered()) {
			guiGraphics.renderTooltip(this.font, FIRST_PAGE, mouseX, mouseY);
		}

	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		Pair<Page, Page> pages = this.getPages();
		if (mouseY > (double) this.pageY && mouseY < (double) (this.pageY + 144)) {
			Page page;
			if (mouseX > (double) this.pageX && mouseX < (double) (this.pageX + 115)) {
				page = pages.getFirst();
				if (page != null) {
					page.onClick(mouseX - (double) this.pageX, mouseY - (double) this.pageY);
				}
			} else if (mouseX > (double) this.page2X && mouseX < (double) (this.page2X + 115)) {
				page = pages.getSecond();
				if (page != null) {
					page.onClick(mouseX - (double) this.pageX, mouseY - (double) this.pageY);
				}
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	public void turnPage(boolean forward) {
		ManualChapter currentManualChapter = this.getChapter();
		if (currentManualChapter == null) {
			this.chapterIndex = 0;
		} else {
			if (forward) {
				if (this.pageIndex + 2 < currentManualChapter.getPages().size()) {
					this.pageIndex += 2;
				} else if (this.chapterIndex + 1 < this.manualChapters.size()) {
					++this.chapterIndex;
					this.pageIndex = 0;
				}
			} else if (this.pageIndex - 2 >= 0) {
				this.pageIndex -= 2;
			} else if (this.chapterIndex - 1 >= 0) {
				--this.chapterIndex;
				int chapterSize = this.getChapter().getPages().size();
				this.pageIndex = chapterSize % 2 == 0 ? chapterSize - 2 : chapterSize - 1;
			}

		}
	}

	public void insertTOC() {
		if (this.manualChapters.isEmpty()) {
			return;
		}

		ManualChapter manualChapter = this.manualChapters.get(0);
		if (manualChapter == null || manualChapter.getPages() == null) {
			return;
		}

		int maxPerPage = 13;
		int maxChapters = this.manualChapters.size();
		int initialPages = (int) ((double) maxChapters / (double) maxPerPage);
		int numPages = (int) ((double) maxChapters % (double) maxPerPage) == 0 ? initialPages : initialPages + 1;
		if (numPages > 0) {
			int prevEndIndex = 0;

			for (int i = 0; i < numPages; ++i) {
				int startIndex = i * maxPerPage;
				int endIndex = startIndex + maxPerPage > maxChapters ? maxChapters - 1 : startIndex + maxPerPage;
				if (startIndex == prevEndIndex && prevEndIndex > 0) {
					startIndex = endIndex;
				}

				prevEndIndex = endIndex;
				if (i < manualChapter.getPages().size() && !(manualChapter.getPages().get(i) instanceof TOCPage)) {
					manualChapter.insertPage(i, new TOCPage(this, startIndex, endIndex));
				} else if (i >= manualChapter.getPages().size()) {
					manualChapter.insertPage(i, new TOCPage(this, startIndex, endIndex));
				}
			}
		}

	}

	public void turnChapter(boolean forward) {
		ManualChapter currentManualChapter = this.getChapter();
		if (currentManualChapter == null) {
			this.chapterIndex = 0;
		} else {
			if (forward) {
				if (this.chapterIndex + 1 < this.manualChapters.size()) {
					++this.chapterIndex;
					this.pageIndex = 0;
				}
			} else if (this.chapterIndex - 1 >= 0) {
				--this.chapterIndex;
				this.pageIndex = 0;
			}

		}
	}

	public void turnToChapter(int chapterIndex) {
		if (chapterIndex < this.manualChapters.size()) {
			this.chapterIndex = chapterIndex;
			this.pageIndex = 0;
		}

	}

	public void turnToTableOfContents(boolean forward) {
		if (this.manualChapters.isEmpty()) {
			this.chapterIndex = 0;
			this.pageIndex = 0;
			return;
		}

		if (forward) {
			int maxChapter = this.manualChapters.size() - 1;
			ManualChapter lastManualChapter = this.manualChapters.get(maxChapter);
			if (lastManualChapter != null && lastManualChapter.getPages() != null
					&& !lastManualChapter.getPages().isEmpty()) {
				this.chapterIndex = maxChapter;
				this.pageIndex = lastManualChapter.getPages().size() - 1;
			} else {
				this.chapterIndex = 0;
				this.pageIndex = 0;
			}
		} else {
			this.chapterIndex = 0;
			this.pageIndex = 0;
		}

	}

	public int getGlobalPageNumber() {
		int pages = 0;

		for (int i = 0; i < this.chapterIndex; ++i) {
			pages += this.manualChapters.get(i).getPages().size();
		}

		return pages + this.pageIndex;
	}

	public ManualChapter getChapter() {
		return this.chapterIndex < this.manualChapters.size() ? this.manualChapters.get(this.chapterIndex) : null;
	}

	public List<ManualChapter> getChapters() {
		return this.manualChapters;
	}

	public Pair<Page, Page> getPages() {
		ManualChapter manualChapter = this.getChapter();
		if (manualChapter != null && this.pageIndex < manualChapter.getPages().size()) {
			Page page2 = null;
			if (this.pageIndex + 1 < manualChapter.getPages().size()) {
				page2 = manualChapter.getPages().get(this.pageIndex + 1);
			}

			return new Pair<>(this.getChapter().getPages().get(this.pageIndex), page2);
		} else {
			return new Pair<>(null, null);
		}
	}

	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	@NotNull public <T extends AbstractWidget> T addRenderableWidget(@NotNull T widget) {
		return super.addRenderableWidget(widget);
	}

	private void read() {
		String localeCode = Minecraft.getInstance().getLanguageManager().getSelected();
		ResourceLocation indexLocation = this.getManualIndexResourceLocation(localeCode);
		Resource resource = null;

		try {
			resource = getManualResourceNullable(indexLocation);
		} catch (IOException exception) {
			exception.printStackTrace();
			TTSMod.LOGGER.error("Could not find Manual resources for locale: {}, reverting to contents for locale {}",
					Minecraft.getInstance().getLanguageManager().getSelected(), "en_us");
			localeCode = "en_us";
			indexLocation = this.getManualIndexResourceLocation(localeCode);

			try {
				resource = getManualResourceNullable(indexLocation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (resource != null) {
			try {
				JsonObject root = JsonParser.parseReader(new InputStreamReader(resource.open())).getAsJsonObject();
				Index index = Index.read(indexLocation, root, localeCode);
				this.manualChapters.clear();
				assert index != null;
				this.manualChapters.addAll(index.getChapters());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public ResourceLocation getManualIndexResourceLocation(String localeCode) {
		return Index.getIndexResourceLocation(new ResourceLocation(this.modid, "main"), localeCode);
	}

	private static Resource getManualResourceNullable(ResourceLocation rl) throws IOException {
		return Minecraft.getInstance().getResourceManager().getResourceOrThrow(rl);
	}
}