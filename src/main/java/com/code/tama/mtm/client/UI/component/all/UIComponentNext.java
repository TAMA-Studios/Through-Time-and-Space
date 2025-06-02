package com.code.tama.mtm.client.UI.component.all;

import com.code.tama.mtm.client.UI.component.core.ComponentTypes;
import com.code.tama.mtm.client.UI.component.core.UIComponent;
import com.code.tama.mtm.server.registries.UICategoryRegistry;
import com.code.tama.mtm.server.tileentities.AbstractMonitorTile;
import net.minecraft.world.entity.player.Player;

public class UIComponentNext extends UIComponent {
    public UIComponentNext(Float[] x, Float[] y, ComponentTypes type) {
        super(x, y, type, UICategoryRegistry.ALL.get());
    }

    @Override
    public void onInteract(Player player, AbstractMonitorTile monitor) {
        super.onInteract(player, monitor);
        if (monitor.categoryID >= UICategoryRegistry.getMaxID())
            monitor.categoryID = 1;
        else
            monitor.categoryID++;

        monitor.setChanged();
        monitor.getLevel().sendBlockUpdated(monitor.getBlockPos(), monitor.getBlockState(), monitor.getBlockState(), 3);
    }
}