/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import static com.code.tama.triggerapi.lua.LuaScriptEngine.toLuaValue;

import java.util.Map;
import java.util.UUID;

import com.code.tama.tts.server.data.tardis.DoorData;
import com.code.tama.tts.server.data.tardis.data.TARDISData;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import com.code.tama.tts.server.misc.containers.PlayerPosition;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class TARDISLuaHelper {

	/**
	 * Converts a TARDISData instance into a LuaTable for Lua scripts.
	 */
	public static LuaTable tardisDataToLua(TARDISData tardis) {
		LuaTable table = new LuaTable();

		// Basic info
		table.set("ticks", tardis.getTicks());
		table.set("fuel", tardis.getFuel());
		table.set("powered", toLuaValue(tardis.isPowered()));
		table.set("discoMode", toLuaValue(tardis.isIsDiscoMode()));
		table.set("sparking", toLuaValue(tardis.isSparking()));
		table.set("alarms", toLuaValue(tardis.isAlarmsState()));
		table.set("refueling", toLuaValue(tardis.isRefueling()));

		// Owner info
		UUID ownerUUID = tardis.getOwnerUUID();
		table.set("ownerUUID", ownerUUID != null ? toLuaValue(ownerUUID.toString()) : LuaValue.NIL);
		Player owner = tardis.GetOwner();
		table.set("owner", owner != null ? LuaExecutable.playerTable(owner) : LuaValue.NIL);

		// Exterior
		ExteriorModelContainer exterior = tardis.getExteriorModel();
		LuaTable exteriorTable = new LuaTable();
		if (exterior != null) {
			exteriorTable.set("model", resourceLocationTable(exterior.getModel()));
			exteriorTable.set("resource",
					exterior.getResource() != null ? exterior.getResource().toString() : LuaValue.NIL);
		}
		table.set("exteriorModel", exteriorTable);

		// Door data
		DoorData door = tardis.getDoorData();
		LuaTable doorTable = new LuaTable();
		doorTable.set("yrot", door.getYRot());
		doorTable.set("doorBlock", spaceTimeCoordinateTable(door.getLocation()));
		table.set("doorData", doorTable);

		// Subsystems and control data
		table.set("subsystems", tardis.getSubSystemsData().toLuaTable());
		table.set("controlData", tardis.getControlData().toLuaTable());
		table.set("protocolsData", tardis.getProtocolsData().toLuaTable());

		// Viewing players
		LuaTable viewingTable = new LuaTable();
		for (Map.Entry<UUID, PlayerPosition> entry : tardis.getViewingPlayerMap().entrySet()) {
			LuaTable posTable = new LuaTable();
			PlayerPosition pos = entry.getValue();
			posTable.set("x", pos.getX());
			posTable.set("y", pos.getY());
			posTable.set("z", pos.getZ());
			viewingTable.set(entry.getKey().toString(), posTable);
		}
		table.set("viewingPlayers", viewingTable);

		// Vortex
		table.set("vortex", tardis.getVortex() != null ? resourceLocationTable(tardis.getVortex()) : LuaValue.NIL);

		// Door block
		LuaTable coordTable = spaceTimeCoordinateTable(tardis.getDoorBlock());
		table.set("doorBlock", coordTable);

		// Functions
		table.set("cycleVariant", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				tardis.CycleVariant();
				return LuaValue.NIL;
			}
		});

		table.set("setPowered", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				tardis.SetPowered(args.checkboolean(1));
				return LuaValue.NIL;
			}
		});

		return table;
	}

	public static LuaTable resourceLocationTable(ResourceLocation location) {
		LuaTable table = new LuaTable();
		table.set("namespace", location.getNamespace());
		table.set("path", location.getPath());
		return table;
	}
}
