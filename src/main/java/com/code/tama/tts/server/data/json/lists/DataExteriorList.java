/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.lists;

import com.code.tama.tts.core.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.server.data.json.dataHolders.DataExterior;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DataExteriorList {
	@Getter
	private static List<DataExterior> exteriorList;

	public static void setExteriorList(List<DataExterior> list) {
		exteriorList = list;
		for (DataExterior ext : list) {
			list.removeIf(r -> r != ext && r.toString().equals(ext.toString()));
		}

		for (DataExterior exterior : exteriorList) {
			ExteriorModelContainer toAdd = new ExteriorModelContainer(exterior.ModelName(), exterior.texture(),
					exterior.light(), exterior.maxRot(), exterior.name(), exterior.parent(), exterior.collection());
			AtomicReference<Boolean> ExistsOrNot = new AtomicReference<>();
			ExistsOrNot.set(false);
			for (ExteriorModelContainer existing : ExteriorsRegistry.EXTERIORS) {
				if (existing.toString().equals(toAdd.toString()))
					ExistsOrNot.set(true);
			}

			if (!ExistsOrNot.get()) {
				ExteriorsRegistry.EXTERIORS.add(toAdd);

				if (ExteriorsRegistry.COLLECTIONS.containsKey(exterior.collection())) {
					List<String> groupList = ExteriorsRegistry.COLLECTIONS.get(exterior.collection());
					if (!groupList.contains(exterior.parent()))
						groupList.add(exterior.parent());
				} else {
					ExteriorsRegistry.COLLECTIONS.put(exterior.collection(), new ArrayList<>(List.of(exterior.parent())));
				}

				if (ExteriorsRegistry.GROUPS.containsKey(exterior.parent()))
					ExteriorsRegistry.GROUPS.get(exterior.parent()).add(toAdd);
				else {
					ExteriorsRegistry.GROUPS.put(exterior.parent(), new ArrayList<>());
					ExteriorsRegistry.GROUPS.get(exterior.parent()).add(toAdd);
				}
			}
		}
	}
}
