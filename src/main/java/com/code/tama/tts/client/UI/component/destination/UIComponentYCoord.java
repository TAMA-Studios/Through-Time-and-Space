/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.component.destination;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import com.code.tama.tts.client.UI.component.core.ComponentTypes;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.server.registries.misc.UICategoryRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;

import net.minecraft.world.entity.player.Player;

public class UIComponentYCoord extends UIComponent {
	public UIComponentYCoord(Float[] x, Float[] y, ComponentTypes type) {
		super(x, y, type, UICategoryRegistry.DESTINATION_LOC.get());
	}

	@Override
	public void onInteract(Player player, AbstractMonitorTile monitor) {
		super.onInteract(player, monitor);
		assert monitor.getLevel() != null;
		GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {
			if (player.isCrouching())
				cap.GetNavigationalData().setDestination(
						cap.GetNavigationalData().getDestination().AddY(-cap.GetNavigationalData().getIncrement()));
			else
				cap.GetNavigationalData().setDestination(
						cap.GetNavigationalData().getDestination().AddY(cap.GetNavigationalData().getIncrement()));
		});
	}
}
