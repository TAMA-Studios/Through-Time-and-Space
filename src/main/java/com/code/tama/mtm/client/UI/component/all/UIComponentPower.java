package com.code.tama.mtm.client.UI.component.all;

import com.code.tama.mtm.client.UI.component.core.ComponentTypes;
import com.code.tama.mtm.client.UI.component.core.UIComponent;
import com.code.tama.mtm.server.registries.UICategoryRegistry;
import com.code.tama.mtm.server.tileentities.AbstractMonitorTile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import static com.code.tama.mtm.MTMMod.MODID;

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