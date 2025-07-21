/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.workbench;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.registries.TTSItems;
import net.minecraft.world.item.Items;

public class WorkBenchRecipeReg {
    public static void Register() {
        TTSMod.WorkBenchRecipeHandler.AddRecipe(
                TTSItems.ZEITON.get(),
                Items.IRON_INGOT,
                Items.REDSTONE,
                Items.COPPER_INGOT,
                TTSItems.SONIC_SCREWDRIVER.get());
    }
}
