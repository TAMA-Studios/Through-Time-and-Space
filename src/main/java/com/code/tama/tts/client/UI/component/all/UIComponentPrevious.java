/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.component.all;

import com.code.tama.tts.client.UI.component.core.ComponentTypes;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.server.registries.misc.UICategoryRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;

import net.minecraft.world.entity.player.Player;

public class UIComponentPrevious extends UIComponent {
	public UIComponentPrevious(Float[] x, Float[] y, ComponentTypes type) {
		super(x, y, type, UICategoryRegistry.ALL.get());
	}

	@Override
	public void onInteract(Player player, AbstractMonitorTile monitor) {
		super.onInteract(player, monitor);
		if (monitor.categoryID <= 1)
			monitor.categoryID = UICategoryRegistry.getMaxID();
		else
			monitor.categoryID--;

		monitor.setChanged();
		monitor.getLevel().sendBlockUpdated(monitor.getBlockPos(), monitor.getBlockState(), monitor.getBlockState(), 3);
	}
}
