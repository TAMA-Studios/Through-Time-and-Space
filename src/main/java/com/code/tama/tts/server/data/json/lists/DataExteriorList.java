/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.code.tama.tts.server.data.json.dataHolders.DataExterior;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import com.code.tama.tts.server.registries.tardis.ExteriorsRegistry;
import lombok.Getter;

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
					exterior.light(), exterior.name());
			AtomicReference<Boolean> ExistsOrNot = new AtomicReference<>();
			ExistsOrNot.set(false);
			for (ExteriorModelContainer existing : ExteriorsRegistry.EXTERIORS) {
				if (existing.getModel().equals(toAdd.getModel()))
					ExistsOrNot.set(true);
			}

			if (!ExistsOrNot.get())
				ExteriorsRegistry.EXTERIORS.add(toAdd);
		}
	}
}
