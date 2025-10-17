/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.component.destination;

import com.code.tama.tts.client.UI.component.core.ComponentTypes;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.registries.misc.UICategoryRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;

import net.minecraft.world.entity.player.Player;

public class UIComponentZCoord extends UIComponent {
	public UIComponentZCoord(Float[] x, Float[] y, ComponentTypes type) {
		super(x, y, type, UICategoryRegistry.DESTINATION_LOC.get());
	}

	@Override
	public void onInteract(Player player, AbstractMonitorTile monitor) {
		super.onInteract(player, monitor);
		monitor.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
			if (player.isCrouching())
				cap.GetNavigationalData().setDestination(
						cap.GetNavigationalData().getDestination().AddZ(-cap.GetNavigationalData().getIncrement()));
			else
				cap.GetNavigationalData().setDestination(
						cap.GetNavigationalData().getDestination().AddZ(cap.GetNavigationalData().getIncrement()));
		});
	}
}
