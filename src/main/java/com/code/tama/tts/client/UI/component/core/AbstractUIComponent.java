/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.component.core;

import java.util.Map;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.UI.category.UICategory;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.math.Axis;
import lombok.Getter;
import lombok.Setter;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.player.Player;

@Setter
@Getter
public abstract class AbstractUIComponent {
	boolean State;

	/** The category this UIComponent shows up in */
	UICategory Category;

	public ResourceLocation GetIcon() {
		return new ResourceLocation(TTSMod.MODID, "textures/gui/button.png");
	}

	public abstract ComponentTypes Type();

	public void onInteract(Player player, AbstractMonitorTile monitor) {
		switch (this.Type()) {
			case TOGGLE, RADIO, CHECK -> this.State = !this.State;
			case BUTTON -> {
				this.State = true;
				if (!player.level().isClientSide)
					player.level().getServer().execute(new TickTask(5, () -> this.setState(false)));
			}
		}
	}

	/**
	 * @return a map with an axis (XP or YP) and a float array (0 being minimum, 1
	 *         being maximum)
	 */
	public abstract Map<Axis, Float[]> XYBounds();
}
