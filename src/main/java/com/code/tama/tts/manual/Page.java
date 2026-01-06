/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import com.code.tama.tts.TTSMod;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.apache.logging.log4j.Level;

import java.io.InputStreamReader;
import java.util.List;

@Getter
public class Page {

	private static final List<PageSerializer> SERIALIZERS = Lists.newArrayList();

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

    // Returns true if this page was clicked
	// public boolean onClick(int x, int y){
	// if(x < this.x)
	// }

	// Returns left over string
	public String parseString(String page) {

		Font font = Minecraft.getInstance().font;

		// First split by newlines to handle explicit line breaks
		String[] paragraphs = page.split("\\n");

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
				int width = font.width(word.replace("\\b", ""));
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
						if (!build.isEmpty() || j > p + 1) {
							build.append("\n");
						}
						build.append(paragraphs[j]);
					}

					return build.toString();
				}
			}

			// Add the last line of this paragraph
			if (!line.isEmpty()) {
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
		boolean isBold = false;
		for (String lines : this.getLines()) {
			y += (font.lineHeight + 2) * index;

			for (int i = 0; i < lines.length(); i++) {
				char c = lines.charAt(i);
				String pair;
				if (isBold && lines.length() > i + 1) {
					pair = "" + c + (lines.charAt(i + 1));
					if (pair.equals("\\b")) {
						isBold = false;
						continue;
					}
				} else if (lines.length() > i + 1) {
					pair = "" + c + (lines.charAt(i + 1));
					if (pair.equals("\\b")) {
						isBold = true;
						continue;
					}
				}

				guiGraphics.drawString(font, "" + c, x + (i * 5), y, 0x000000, isBold);
				++index;
			}
		}

		// draw page number
		String pageNum = globalPage + "";
		guiGraphics.drawString(font, pageNum, x + (WIDTH) / 2 + font.width(pageNum) / 2, y + 120, 0x000000, false);
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