/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.compat.cct;

import com.code.tama.tts.core.compat.cct.tiles.TardisCCInterfaceTile;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class CCTARDISInterface implements IPeripheral {
	public final TardisCCInterfaceTile tile;

	public CCTARDISInterface(TardisCCInterfaceTile t) {
		this.tile = t;
	}

	@LuaFunction(mainThread = true)
	public void takeOff() {
		tile.getCap().ifPresent(ITARDISLevel::Dematerialize);
	}

	@LuaFunction(mainThread = true)
	public void land() {
		tile.getCap().ifPresent(ITARDISLevel::Rematerialize);
	}

	@LuaFunction(mainThread = true)
	public boolean canFly() {
		if (tile.getCap().orElseGet(null) == null)
			return false;

		return tile.getCap().orElseGet(null).CanFly();
	}

	@LuaFunction(mainThread = true)
	public void setDestination(int x, int y, int z) {
		tile.getCap().ifPresent(t -> t.GetNavigationalData().setDestination(new SpaceTimeCoordinate(x, y, z)));
	}

	@LuaFunction(mainThread = true)
	public void setDestinationLevel(String levelID) {
		tile.getCap().ifPresent(t -> {
			SpaceTimeCoordinate c = t.GetNavigationalData().getDestination();
			c.setLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(levelID)));
			t.GetNavigationalData().setDestination(c);
		});
	}

	@Override
	public @NotNull String getType() {
		return "tts:tardis";
	}

	@Override
	public boolean equals(IPeripheral other) {
		return other instanceof CCTARDISInterface o && tile == o.tile;
	}
}
