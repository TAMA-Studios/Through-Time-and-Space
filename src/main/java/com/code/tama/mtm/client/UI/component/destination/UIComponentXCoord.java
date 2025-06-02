package com.code.tama.mtm.client.UI.component.destination;

import com.code.tama.mtm.client.UI.component.core.ComponentTypes;
import com.code.tama.mtm.client.UI.component.core.UIComponent;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.registries.UICategoryRegistry;
import com.code.tama.mtm.server.tileentities.AbstractMonitorTile;
import net.minecraft.world.entity.player.Player;

public class UIComponentXCoord extends UIComponent {
    public UIComponentXCoord(Float[] x, Float[] y, ComponentTypes type) {
        super(x, y, type, UICategoryRegistry.DESTINATION_LOC.get());
    }

    @Override
    public void onInteract(Player player, AbstractMonitorTile monitor) {
        super.onInteract(player, monitor);
        monitor.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            if (player.isCrouching())
                cap.SetDestination(cap.GetDestination().AddX(-cap.GetIncrement()));
            else
                cap.SetDestination(cap.GetDestination().AddX(cap.GetIncrement()));
        });
    }
}