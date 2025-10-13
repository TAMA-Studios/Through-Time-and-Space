/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.component.all;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.UI.component.core.ComponentTypes;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.server.registries.UICategoryRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class UIComponentPower extends UIComponent {
    public UIComponentPower(Float[] x, Float[] y, ComponentTypes type) {
        super(x, y, type, UICategoryRegistry.ALL.get(), new ResourceLocation(MODID, "textures/gui/power.png"));
    }

    @Override
    public void onInteract(Player player, AbstractMonitorTile monitor) {
        super.onInteract(player, monitor);
        monitor.powered = !monitor.powered;

        monitor.setChanged();
        monitor.getLevel().sendBlockUpdated(monitor.getBlockPos(), monitor.getBlockState(), monitor.getBlockState(), 3);
    }
}
