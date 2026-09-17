/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.Panels;

import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.S2C.dimensions.SyncCapVariantPacketS2C;
import com.code.tama.tts.core.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;

import net.minecraft.world.level.Level;

/**
 * Server-side exterior mutations, shared between the physical panel and the
 * Chameleon Circuit GUI. Callers must already be on the logical server.
 */
public class ChameleonCircuitActions {

	public static void nextVariant(Level world, ITARDISLevel cap) {
		cap.GetData().CycleVariant();
		sync(world, cap);
	}

	public static void prevVariant(Level world, ITARDISLevel cap) {
		cap.GetData().CycleVariantDown();
		sync(world, cap);
	}

	public static void nextGroup(Level world, ITARDISLevel cap) {
		ExteriorModelContainer exterior = cap.GetData().getExteriorModel();
		String newGroup = ExteriorsRegistry.CycleGroup(exterior.getParent(), exterior.getCollection());
		cap.GetData().SetExteriorVariant(ExteriorsRegistry.GROUPS.get(newGroup).get(0));
		sync(world, cap);
	}

	public static void prevGroup(Level world, ITARDISLevel cap) {
		ExteriorModelContainer exterior = cap.GetData().getExteriorModel();
		String newGroup = ExteriorsRegistry.CyclePrevGroup(exterior.getParent(), exterior.getCollection());
		cap.GetData().SetExteriorVariant(ExteriorsRegistry.GROUPS.get(newGroup).get(0));
		sync(world, cap);
	}

	public static void nextCollection(Level world, ITARDISLevel cap) {
		ExteriorModelContainer exterior = cap.GetData().getExteriorModel();
		String newCollection = ExteriorsRegistry.CycleCollection(exterior.getCollection());
		String firstGroup = ExteriorsRegistry.COLLECTIONS.get(newCollection).get(0);
		cap.GetData().SetExteriorVariant(ExteriorsRegistry.GROUPS.get(firstGroup).get(0));
		sync(world, cap);
	}

	private static void sync(Level world, ITARDISLevel cap) {
		Networking.sendPacketToDimension(world.dimension(),
				new SyncCapVariantPacketS2C(ExteriorsRegistry.GetOrdinal(cap.GetData().getExteriorModel())));
		cap.GetExteriorTile().UpdateAll();
		cap.UpdateClient(DataUpdateValues.DATA);
	}
}