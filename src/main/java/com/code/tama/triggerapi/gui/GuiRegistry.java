package com.code.tama.triggerapi.gui;

import com.code.tama.triggerapi.networking.gui.OpenGuiPacket;
import com.code.tama.tts.server.networking.Networking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Central registry for opening GUIs
 */
public class GuiRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiRegistry.class);
    
    public static void openGui(ServerPlayer player, ResourceLocation guiId) {
        GuiDefinition def = GuiLoader.getGuiDefinition(guiId);
        if (def == null) {
            LOGGER.error("GUI definition not found: {}", guiId);
            return;
        }
        
        String type = def.getType();
        
        if ("container".equals(type)) {
            // Open container-style GUI
            player.openMenu(new ContainerGuiProvider(def, guiId));
        } else {
            // Open custom GUI (send packet to client)
            Networking.sendToClient(player, new OpenGuiPacket(guiId));
        }
    }
}