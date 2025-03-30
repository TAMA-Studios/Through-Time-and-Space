package com.code.tama.mtm.server.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.code.tama.mtm.MTMMod.MODID;

/**
 * This is for functions related to string manipulation
 * @version 2.4
 */
public class GrammarNazi {
    public GrammarNazi() {}

    /**
     * @return The ID of a Block
     */
    public static String IDFromBlock(Block block) {
        return block.toString().replaceAll("aseoha:", "").replaceAll("minecraft:", "").replace("{", "").replace("}", "").replace(":", "").toLowerCase().substring(5);
    }

    /**
     * @return The ID of a Block (With Namespace)
     */
    public static String FullIDFromBlock(Block block) {
        return block.toString().replace("{", "").replace("}", "").toLowerCase().substring(5);
    }

    /**
     * @return The ID of an Item
     */
    public static String IDFromItem(Item item) {
        return item.toString().replaceAll(MODID, "").replaceAll("[{}:]", "").toLowerCase();
    }

    /**
     * @return The ID of an Item
     */
    public static String BlockPosToString(BlockPos blockPos) {
        return blockPos.toString().toLowerCase().replace("blockpos", "").replaceAll("[{}:xyz=]", "");
    }

    /**
     * Made to accept Item#toString(), remove the minecraft:item@modid, and capitalize every first letter of every word while also replacing underscores with spaces
     * @param text Item#toString()
     * @return Item#toString() without minecraft:item@modid and every first letter of every word capitalized
     */
    public static String CleanItemString(String text) {
        /**
         * remove minecraft:item@modid
         */
        text = text.replace("minecraft:item@" + MODID + ":", "");
        return CapitalizeFirstLetters(ScoreToSpace(text));
    }

    /** Replaces Underscore characters ('_') with space characters (' ') and capitalizes the first letter of every word**/
    public static String CapitalizeFirstLettersAndRemoveScores(String text) {
        return CapitalizeFirstLetters(ScoreToSpace(text));
    }

    /**
     * @return The Original text with all first letters capitalized
     */
    public static String CapitalizeFirstLetters(String text) {
        String firstLetter = text.substring(0, 1).toUpperCase();
        /**
         * Find any characters coming after a space char and replace it with the uppercase variant
         */
        for(int i = 0; i < text.length(); i++) {
            if (text.substring(i, i + 1).contains(" "))
                text = text.replace(text.substring(i, i + 2), text.substring(i, i + 2).toUpperCase());
        }
        return firstLetter + text.substring(1);
    }

    /**
     * Replace _ chars with space chars
     */
    public static String ScoreToSpace(String text){
        return text.replace("_", " ");
    }
}
