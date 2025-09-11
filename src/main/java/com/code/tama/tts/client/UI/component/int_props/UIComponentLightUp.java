/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.component.int_props;

import com.code.tama.tts.client.UI.component.core.ComponentTypes;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.registries.UICategoryRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import net.minecraft.world.entity.player.Player;

public class UIComponentLightUp extends UIComponent {
    public UIComponentLightUp(Float[] x, Float[] y, ComponentTypes type) {
        super(x, y, type, UICategoryRegistry.INTERIOR_PROPS.get());
    }

    @Override
    public void onInteract(Player player, AbstractMonitorTile monitor) {
        super.onInteract(player, monitor);
        monitor.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            if (cap.GetLightLevel() < 1.5f) cap.SetLightLevel(cap.GetLightLevel() + 0.1f);
        });
    }
}
