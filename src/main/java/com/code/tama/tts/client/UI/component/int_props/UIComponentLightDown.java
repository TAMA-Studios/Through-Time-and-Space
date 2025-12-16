/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.component.int_props;

import com.code.tama.tts.client.UI.component.core.ComponentTypes;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.server.registries.misc.UICategoryRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import net.minecraft.world.entity.player.Player;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class UIComponentLightDown extends UIComponent {
	public UIComponentLightDown(Float[] x, Float[] y, ComponentTypes type) {
		super(x, y, type, UICategoryRegistry.INTERIOR_PROPS.get());
	}

	@Override
	public void onInteract(Player player, AbstractMonitorTile monitor) {
		super.onInteract(player, monitor);
		assert monitor.getLevel() != null;
		GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {
			if (cap.GetLightLevel() > 0f)
				cap.GetEnvironmentalData().SetLightLevel(cap.GetLightLevel() - 0.1f);
		});
	}
}
