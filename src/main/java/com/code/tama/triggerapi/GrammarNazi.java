/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import static com.code.tama.tts.TTSMod.LOGGER;

import java.util.ArrayList;
import java.util.List;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSEntities;
import com.code.tama.tts.core.registries.forge.TTSItems;
import com.code.tama.tts.core.registries.misc.SonicModeRegistry;
import com.code.tama.tts.core.registries.tardis.ControlsRegistry;
import com.code.tama.tts.core.registries.tardis.ExteriorsRegistry;

import net.minecraft.client.resources.language.I18n;

import com.code.tama.triggerapi.exceptions.GrammarException;

/**
 * Native (Rust) bridge for string/grammar utilities. Drop-in faster replacement
 * for GrammarNazi methods.
 *
 * @version 1.0
 */
public class GrammarNazi {

	static {
		NativeLoader.load("tts_native");
	}

	// -- Core string ops ------------------------------------------------------

	/** Capitalizes the first letter of every word. */
	public static native String capitalizeFirstLetters(String text);

	/** Replaces underscores with spaces and capitalizes first letters. */
	public static native String cleanString(String text);

	/** Replaces '_' with ' '. */
	public static native String scoreToSpace(String text);

	// -- Block / Item ID extraction -------------------------------------------

	/**
	 * Pass {@code blockPos.toString()} — returns "x y z" with all formatting
	 * stripped.
	 */
	public static native String blockPosToString(String blockPosToString);

	/**
	 * Strips namespace, braces, colons; lowercases; drops the leading "block"
	 * prefix. Pass {@code block.toString()}.
	 */
	public static native String idFromBlock(String blockToString);

	/**
	 * Like idFromBlock but keeps the namespace prefix. Pass
	 * {@code block.toString()}.
	 */
	public static native String fullIdFromBlock(String blockToString);

	/**
	 * Strips modid, braces, and colons from an item's toString(). Pass
	 * {@code item.toString()} and your MODID.
	 */
	public static native String idFromItem(String itemToString, String modid);

	/**
	 * Strips the "minecraft:item@modid:" prefix, replaces underscores with spaces,
	 * and capitalizes first letters.
	 */
	public static native String cleanItemString(String itemToString, String modid);

	// -- String building ------------------------------------------------------

	/** Concatenates all strings with no separator. */
	public static native String stitch(String[] strings);

	/** Concatenates all strings with the given delimiter between each. */
	public static native String stitchWithDelimiter(String delimiter, String[] strings);

	// -- Extras ---------------------------------------------------------------

	/** Case-insensitive contains check. */
	public static native boolean containsIgnoreCase(String haystack, String needle);

	/**
	 * Truncates text to maxLen characters, appending "..." if cut. The returned
	 * string will be at most maxLen characters long.
	 */
	public static native String truncate(String text, int maxLen);

	/**
	 * Strips the "namespace:" prefix from a resource location string. e.g.
	 * "minecraft:stone" → "stone"
	 */
	public static native String stripNamespace(String resourceLocation);

	public static void checkTranslation(String key) {
		String translation = I18n.get(key); // I18n.get(key);
		if (translation == null || translation.equals(key) || translation.isEmpty()) {
			MissingTranslations.add(key);
		}
	}

	private static final List<String> MissingTranslations = new ArrayList<>();

	public static void checkAllTranslations() throws GrammarException {
		TTSItems.AllValues().forEach(item -> {
			String key = item.get().getDescriptionId();
			checkTranslation(key);
		});

		TTSBlocks.AllValues().forEach(block -> {
			String key = block.get().getDescriptionId();
			checkTranslation(key);
		});

		TTSEntities.ENTITY_TYPES.getEntries().forEach(entity -> {
			String key = entity.get().getDescriptionId();
			checkTranslation(key);
		});

		SonicModeRegistry.SONIC_MODE.getEntries().forEach(mode -> {
			String key = mode.get().getTranslationKey();
			checkTranslation(key);
		});

		ControlsRegistry.CONTROLS.getEntries().forEach(mode -> {
			String key = mode.get().getTranslationKey();
			checkTranslation(key);
		});

		ExteriorsRegistry.EXTERIORS.forEach(exterior -> {
			String key = exterior.getTranslationKey();
			checkTranslation(key);
		});

		if (!MissingTranslations.isEmpty()) {
			LOGGER.error("Missing translations!");
			MissingTranslations.forEach(LOGGER::error);

			throw new GrammarException(MissingTranslations);
		}
	}
}