/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.util;

import com.code.tama.tts.server.items.SonicItem;
import com.mojang.blaze3d.platform.InputConstants;
import cpw.mods.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;

public class KeyboardHandler {
    public static final Lazy<KeyMapping> SONIC_MODE = Lazy.of(() -> new KeyMapping(
            "tts.keybinds.sonic_mode", InputConstants.Type.KEYSYM, InputConstants.KEY_G, "tts.keybinds.catagory"));

    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (SONIC_MODE.get().consumeClick()) {
                if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof SonicItem sonic) {
                    // TODO: what the hell is this fix it lmfao
                    //                    sonic.InteractionType = SonicInteractionType.values()[
                    //                            sonic.InteractionType.ordinal() < SonicInteractionType.values().length
                    // - 1
                    //                                    ? sonic.InteractionType.ordinal() + 1
                    //                                    : 0];
                    //
                    //
                    // Minecraft.getInstance().player.sendSystemMessage(sonic.InteractionType.Name());
                }
            }
        }
    }

    private void keybindSetup(RegisterKeyMappingsEvent event) {
        event.register(SONIC_MODE.get());
    }
}
