/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import com.code.tama.tts.TTSMod;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.apache.logging.log4j.Level;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Page {

	private static final List<PageSerializer> SERIALIZERS = Lists.newArrayList();
	// &b = bold, &f = italic, &t = underline, &n = newline, &0-&7 = colors
	private static final String SPECIAL_CHARS[] = {"&b", "&f", "&t", "&n", "&1", "&2", "&3", "&4", "&5", "&6", "&7"};

	public static final int WIDTH = 65, LINES = 10, MAX_LINE_WIDTH = 115;
	/**
	 * -- GETTER --
	 * Gets the number of new lines which all the text will be rendered as
	 */
	protected List<String> lines = Lists.newArrayList();

	public Page() {
	}

	static {
		SERIALIZERS.add(new NormalPageSerializer());
		SERIALIZERS.add(new CoverPageSerializer());
	}

	/**
	 * Removes all special formatting characters from a string
	 */
	public String remFormatting(String removeFrom) {
		String result = removeFrom;
		for (String special : SPECIAL_CHARS) {
			result = result.replace(special, "");
		}
		return result;
	}

	// Returns left over string
	// Returns left over string
	public String parseString(String page) {

		Font font = Minecraft.getInstance().font;

		// First split by newlines to handle explicit line breaks
		String[] paragraphs = page.split("&n");

		this.lines.clear();
		int totalLinesAdded = 0;

		for (int p = 0; p < paragraphs.length; ++p) {
			String paragraph = paragraphs[p];

			// Handle empty lines (from consecutive \n)
			if (paragraph.trim().isEmpty()) {
				this.lines.add("");
				totalLinesAdded++;

				// Check if we've exceeded line limit
				if (totalLinesAdded >= LINES) {
					// Collect remaining content
					StringBuilder remaining = new StringBuilder();
					for (int j = p + 1; j < paragraphs.length; ++j) {
						remaining.append(paragraphs[j]);
						if (j < paragraphs.length - 1) {
							remaining.append("\n");
						}
					}
					return remaining.toString();
				}
				continue;
			}

			List<String> words = Lists.newArrayList(paragraph.split(" "));
			int currentWidth = 0;

			StringBuilder line = new StringBuilder();
			String prevLineWord = "";

			for (int i = 0; i < words.size(); ++i) {
				String word = words.get(i);
				int width = font.width(word);
				currentWidth += width;

				int prevLineWidth = font.width(line.toString());

				// If this new word can fit on this line and the line with the previous words can fit
				if (currentWidth < WIDTH && prevLineWidth < WIDTH) {
					prevLineWord = line.toString();
					line.append(word).append(" ");
				}
				// If we should start a new line
				else {
					// If the length of the line we have just constructed is also over the limit,
					// split the words onto new lines
					int appendedWordWidth = font.width(line.toString());
					if (appendedWordWidth > WIDTH) {
						if (appendedWordWidth >= MAX_LINE_WIDTH) {
							String split = line.substring(prevLineWord.length());
							this.lines.add(prevLineWord);
							totalLinesAdded++;
							this.lines.add(split);
							totalLinesAdded++;
							line = new StringBuilder(word + " ");
							currentWidth = 0;
						}
						// If the line is smaller than the max allowed line width but still larger than
						// the page character limit
						else {
							this.lines.add(line.toString());
							totalLinesAdded++;
							line = new StringBuilder(word + " ");
							currentWidth = 0;
						}
					}
					// If previous word and current word are within limit
					else {
						this.lines.add(line.toString());
						totalLinesAdded++;
						line = new StringBuilder(word + " ");
						currentWidth = 0;
					}
				}

				// If there are too many lines, return the remaining words from this paragraph
				// plus all remaining paragraphs
				if (totalLinesAdded >= LINES) {
					StringBuilder build = new StringBuilder();

					// Add remaining words from current paragraph
					for (String s : words.subList(i, words.size())) {
						build.append(s).append(" ");
					}

					// Add remaining paragraphs
					for (int j = p + 1; j < paragraphs.length; ++j) {
						if (build.length() > 0 || j > p + 1) {
							build.append("\n");
						}
						build.append(paragraphs[j]);
					}

					return build.toString();
				}
			}

			// Add the last line of this paragraph
			if (line.length() > 0) {
				this.lines.add(line.toString());
				totalLinesAdded++;
			}

			// Check again after adding the last line
			if (totalLinesAdded >= LINES) {
				// Collect remaining paragraphs
				StringBuilder remaining = new StringBuilder();
				for (int j = p + 1; j < paragraphs.length; ++j) {
					if (j > p + 1) {
						remaining.append("\n");
					}
					remaining.append(paragraphs[j]);
				}
				return remaining.toString();
			}
		}

		return ""; // DO NOT CHANGE THIS or you will soft lock the game
	}

	public int getNumberOfLines() {
		return this.lines.size();
	}

	public void render(GuiGraphics guiGraphics, Font font, int globalPage, int x, int y, int width, int height) {
		int index = 0;
		// Track active styles: [bold, italic, underline, color0-7]
		boolean styles[] = new boolean[11];

		for (String line : this.getLines()) {
			int ny = y + (font.lineHeight + 2) * index;
			++index;

			// Build and render the line character by character
			StringBuilder visibleText = new StringBuilder();

			boolean wasStyleCode = false;
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);

				if(wasStyleCode) {
					wasStyleCode = false;
					continue;
				}
				// Handle style toggles
				boolean isStyleCode = false;
				if(("" + c).equals("&")) {
					if (line.length() > i + 1) {
						String spec = "" + c + line.charAt(i + 1);
						switch (spec) {
							case "&b" -> {
								styles[0] = !styles[0];
								isStyleCode = true;
							}
							case "&f" -> {
								styles[1] = !styles[1];
								isStyleCode = true;
							}
							case "&t" -> {
								styles[2] = !styles[2];
								isStyleCode = true;
							}
							case "&0" -> {
								styles[3] = !styles[3];
								isStyleCode = true;
							}
							case "&1" -> {
								styles[4] = !styles[4];
								isStyleCode = true;
							}
							case "&2" -> {
								styles[5] = !styles[5];
								isStyleCode = true;
							}
							case "&3" -> {
								styles[6] = !styles[6];
								isStyleCode = true;
							}
							case "&4" -> {
								styles[7] = !styles[7];
								isStyleCode = true;
							}
							case "&5" -> {
								styles[8] = !styles[8];
								isStyleCode = true;
							}
							case "&6" -> {
								styles[9] = !styles[9];
								isStyleCode = true;
							}
							case "&7" -> {
								styles[10] = !styles[10];
								isStyleCode = true;
							}
						}
						wasStyleCode = isStyleCode;
					}
				}

				// If this is a visible character, render it with current styles
				if (!isStyleCode) {
					List<ChatFormatting> activeFormats = new ArrayList<>();

					if (styles[0]) activeFormats.add(ChatFormatting.BOLD);
					if (styles[1]) activeFormats.add(ChatFormatting.ITALIC);
					if (styles[2]) activeFormats.add(ChatFormatting.UNDERLINE);
					if (styles[3]) activeFormats.add(ChatFormatting.BLACK);
					if (styles[4]) activeFormats.add(ChatFormatting.WHITE);
					if (styles[5]) activeFormats.add(ChatFormatting.RED);
					if (styles[6]) activeFormats.add(ChatFormatting.GREEN);
					if (styles[7]) activeFormats.add(ChatFormatting.BLUE);
					if (styles[8]) activeFormats.add(ChatFormatting.YELLOW);
					if (styles[9]) activeFormats.add(ChatFormatting.LIGHT_PURPLE);

					// Calculate x position based on visible text rendered so far
					int nx = x + font.width(remFormatting(visibleText.toString()));

					MutableComponent charComponent = Component.literal(Character.toString(c));
					for (ChatFormatting format : activeFormats) {
						charComponent = charComponent.withStyle(format);
					}

					guiGraphics.drawString(font, charComponent, nx, ny, 0x000000, false);
					visibleText.append(c);
				}
			}
		}

		// Draw page number
		String pageNum = globalPage + "";
		guiGraphics.drawString(font, pageNum, x + (WIDTH) / 2 - font.width(pageNum) / 2, y + 120, 0x000000, false);
	}

	public static List<Page> read(ResourceLocation id) {
		try {
			Resource resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(id);
			JsonObject root = JsonParser.parseReader(new InputStreamReader(resource.open())).getAsJsonObject();

			String type = root.get("type").getAsString();

			for (PageSerializer serializer : SERIALIZERS) {
				if (serializer.match(type)) {
					return serializer.read(root);
				}
			}

		} catch (Exception e) {
			TTSMod.LOGGER.log(Level.INFO, String.format("Exception in manual page %s!", id.toString()));
			TTSMod.LOGGER.catching(Level.INFO, e);
		}
		return null;
	}

	public static ResourceLocation getPageResourceLocation(ResourceLocation loc, String localeCode) {
		return new ResourceLocation(loc.getNamespace(), "manual/" + localeCode + "/page/" + loc.getPath() + ".json");
	}

	public void onClick(double x, double y) {
	}
}