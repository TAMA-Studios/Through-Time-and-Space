/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.gui.terminal;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.TerminalCommandPacketC2S;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A Linux-terminal-style console for the TARDIS: a scrollback log and a
 * single command-line prompt at the bottom. Typed commands are sent to the
 * server as {@link TerminalCommandPacketC2S}; the server parses and executes
 * them and replies with a {@code TerminalResponsePacketS2C}, which
 * {@link #appendOutput(String)} prints back into the log.
 *
 * <p>
 * Log lines are word-wrapped to the console's content width at the moment
 * they're added (not re-wrapped every frame), and the log supports
 * scrollback via mouse wheel or Page Up/Down. Scrolling stays "stuck" to the
 * bottom while new output arrives, unless you've scrolled up to read
 * something, in which case new output arrives below without yanking your
 * view back down.
 * </p>
 */
public class TARDISConsoleScreen extends Screen {

	private static final int COL_BG = 0xF0020A02;
	private static final int COL_FRAME = 0xFF0F3A16;
	private static final int COL_FRAME_LIGHT = 0xFF1F6B2A;
	private static final int COL_TEXT = 0xFF3CFF5E;
	private static final int COL_TEXT_DIM = 0xFF1E8A34;
	private static final int COL_TEXT_ECHO = 0xFF8CFFA0;
	private static final int COL_TEXT_ERR = 0xFFFF5A4A;
	private static final int COL_SCANLINE = 0x22001A05;

	private static final int GUI_W = 420;
	private static final int GUI_H = 260;
	private static final int LINE_H = 10;
	private static final int MAX_LOG_LINES = 1000;
	private static final int CONTENT_MARGIN = 10;
	private static final int SCROLL_STEP_LINES = 3;

	private record LogLine(int color, String text) {
	}

	private final ITARDISLevel tardis;
	private final List<LogLine> log = new ArrayList<>();
	private final Deque<String> history = new ArrayDeque<>();
	private int historyIndex = -1;
	private String draftBeforeHistory = "";

	/** Lines scrolled up from the bottom. 0 = pinned to the latest output. */
	private int scrollOffset = 0;

	private int leftPos, topPos;
	private EditBox input;

	public TARDISConsoleScreen(ITARDISLevel tardis) {
		super(Component.literal("TARDIS Console"));
		this.tardis = tardis;
	}

	@Override
	protected void init() {
		this.leftPos = (this.width - GUI_W) / 2;
		this.topPos = (this.height - GUI_H) / 2;

		if (log.isEmpty()) {
			printLocal("TARDIS Interface Console v1.0");
			printLocal("Type 'help' for a list of commands, or 'man <command>' for details.");
			printLocal("");
		}

		int inputY = topPos + GUI_H - 22;
		input = new EditBox(this.font, leftPos + 18, inputY, GUI_W - 36, 14, Component.empty());
		input.setMaxLength(256);
		input.setBordered(false);
		input.setTextColor(COL_TEXT);
		addRenderableWidget(input);
		setInitialFocus(input);
	}

	// ------------------------------------------------------------------
	// Command handling
	// ------------------------------------------------------------------

	private void submitCommand() {
		String command = input.getValue().trim();
		input.setValue("");
		historyIndex = -1;
		draftBeforeHistory = "";

		if (command.isEmpty())
			return;

		printEcho("> " + command);
		history.addFirst(command);
		while (history.size() > 50)
			history.removeLast();

		if (command.equalsIgnoreCase("clear")) {
			log.clear();
			scrollOffset = 0;
			return;
		}

		Networking.sendToServer(new TerminalCommandPacketC2S(command));
	}

	/** Called by TerminalResponsePacketS2C when a reply arrives from the server. */
	public void appendOutput(String message) {
		if (message == null || message.isEmpty())
			return;
		boolean isError = message.startsWith("ERR:");
		int color = isError ? COL_TEXT_ERR : COL_TEXT_DIM;
		for (String part : message.split("\n", -1))
			addWrapped(color, part);
	}

	private void printLocal(String message) {
		addWrapped(COL_TEXT_DIM, message);
	}

	private void printEcho(String message) {
		addWrapped(COL_TEXT_ECHO, message);
	}

	/** Word-wraps text to the content width and appends it, preserving scroll position. */
	private void addWrapped(int color, String text) {
		boolean stickToBottom = scrollOffset == 0;

		for (String line : wordWrap(text, contentWidth()))
			log.add(new LogLine(color, line));

		while (log.size() > MAX_LOG_LINES) {
			log.remove(0);
			if (scrollOffset > 0)
				scrollOffset--;
		}

		if (!stickToBottom)
			scrollOffset = Math.min(maxScrollOffset(), scrollOffset + 1);
		clampScroll();
	}

	private int contentWidth() {
		return GUI_W - CONTENT_MARGIN * 2;
	}

	/** Greedy word-wrap using the screen's actual font metrics. */
	private List<String> wordWrap(String text, int maxWidth) {
		List<String> lines = new ArrayList<>();
		if (text.isEmpty()) {
			lines.add("");
			return lines;
		}

		StringBuilder current = new StringBuilder();
		for (String word : text.split(" ", -1)) {
			String candidate = current.isEmpty() ? word : current + " " + word;

			if (this.font.width(candidate) <= maxWidth) {
				current = new StringBuilder(candidate);
				continue;
			}

			if (!current.isEmpty()) {
				lines.add(current.toString());
				current = new StringBuilder();
			}

			// A single word longer than the whole line - hard break it.
			String remaining = word;
			while (this.font.width(remaining) > maxWidth && remaining.length() > 1) {
				int cut = remaining.length();
				while (cut > 1 && this.font.width(remaining.substring(0, cut)) > maxWidth)
					cut--;
				lines.add(remaining.substring(0, cut));
				remaining = remaining.substring(cut);
			}
			current = new StringBuilder(remaining);
		}
		lines.add(current.toString());
		return lines;
	}

	// Scrolling

	private int visibleLogLines() {
		int logTop = topPos + 22;
		int logBottom = topPos + GUI_H - 30;
		return Math.max(1, (logBottom - logTop) / LINE_H);
	}

	private int maxScrollOffset() {
		return Math.max(0, log.size() - visibleLogLines());
	}

	private void clampScroll() {
		scrollOffset = Math.max(0, Math.min(maxScrollOffset(), scrollOffset));
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		scrollOffset += (int) Math.signum(delta) * SCROLL_STEP_LINES;
		clampScroll();
		return true;
	}

	// Input handling (Enter to submit, Up/Down for history, PgUp/PgDn to scroll)

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == InputConstants.KEY_SPACE)
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(TTSSounds.KEYBOARD_SPACE.get(), 1.0F));
		else
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(switch (ThreadLocalRandom.current().nextInt(3)) {
                case 0 -> TTSSounds.KEYBOARD_01.get();
                case 1 -> TTSSounds.KEYBOARD_02.get();
                default -> TTSSounds.KEYBOARD_PRESS_01.get();
            }, 1.0F));

		if (keyCode == InputConstants.KEY_PAGEUP) { // Page Up
			scrollOffset += visibleLogLines() - 1;
			clampScroll();
			return true;
		}
		if (keyCode == InputConstants.KEY_PAGEDOWN) { // Page Down
			scrollOffset -= visibleLogLines() - 1;
			clampScroll();
			return true;
		}

		if (this.getFocused() == input) {
			if (keyCode == InputConstants.KEY_RETURN || keyCode == InputConstants.KEY_NUMPADENTER) {
				submitCommand();
				return true;
			}
			if (keyCode == InputConstants.KEY_UP) {
				navigateHistory(1);
				return true;
			}
			if (keyCode == InputConstants.KEY_DOWN) {
				navigateHistory(-1);
				return true;
			}
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	private void navigateHistory(int direction) {
		if (history.isEmpty())
			return;

		List<String> asList = new ArrayList<>(history);

		if (historyIndex == -1 && direction > 0) {
			draftBeforeHistory = input.getValue();
		}

		historyIndex += direction;
		historyIndex = Math.max(-1, Math.min(asList.size() - 1, historyIndex));

		if (historyIndex == -1) {
			input.setValue(draftBeforeHistory);
		} else {
			input.setValue(asList.get(historyIndex));
		}
		input.moveCursorToEnd();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	// Rendering

	@Override
	public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
		renderBackground(gfx);
		drawFrame(gfx);
		drawTitle(gfx);
		drawLog(gfx);
		drawScrollHint(gfx);
		drawPrompt(gfx);
		super.render(gfx, mouseX, mouseY, partialTick);
		drawScanlines(gfx);
	}

	private void drawFrame(GuiGraphics gfx) {
		gfx.fill(leftPos - 2, topPos - 2, leftPos + GUI_W + 2, topPos + GUI_H + 2, COL_FRAME_LIGHT);
		gfx.fill(leftPos, topPos, leftPos + GUI_W, topPos + GUI_H, COL_BG);
		gfx.fill(leftPos, topPos, leftPos + GUI_W, topPos + 16, COL_FRAME);
	}

	private void drawTitle(GuiGraphics gfx) {
		gfx.drawCenteredString(this.font, "T.A.R.D.I.S. CONSOLE", leftPos + GUI_W / 2, topPos + 4, COL_TEXT);
	}

	private void drawLog(GuiGraphics gfx) {
		int logTop = topPos + 22;
		int visibleLines = visibleLogLines();

		int end = log.size() - scrollOffset;
		int start = Math.max(0, end - visibleLines);

		int y = logTop;
		for (int i = start; i < end; i++) {
			LogLine entry = log.get(i);
			gfx.drawString(this.font, entry.text(), leftPos + CONTENT_MARGIN, y, entry.color(), false);
			y += LINE_H;
		}
	}

	/** Small "more above/below" indicator so scrollback isn't a total mystery. */
	private void drawScrollHint(GuiGraphics gfx) {
		if (scrollOffset < maxScrollOffset()) {
			gfx.drawString(this.font, "^ more ^", leftPos + GUI_W - 54, topPos + 22, COL_TEXT_DIM, false);
		}
		if (scrollOffset > 0) {
			int logBottom = topPos + GUI_H - 30;
			gfx.drawString(this.font, "v more v", leftPos + GUI_W - 54, logBottom - 9, COL_TEXT_DIM, false);
		}
	}

	private void drawPrompt(GuiGraphics gfx) {
		int promptY = topPos + GUI_H - 22;
		gfx.drawString(this.font, ">", leftPos + 10, promptY + 3, COL_TEXT, false);
		gfx.fill(leftPos + 8, topPos + GUI_H - 26, leftPos + GUI_W - 8, topPos + GUI_H - 26 + 1, COL_FRAME_LIGHT);
	}

	private void drawScanlines(GuiGraphics gfx) {
		for (int sy = topPos; sy < topPos + GUI_H; sy += 2) {
			gfx.fill(leftPos, sy, leftPos + GUI_W, sy + 1, COL_SCANLINE);
		}
	}
}