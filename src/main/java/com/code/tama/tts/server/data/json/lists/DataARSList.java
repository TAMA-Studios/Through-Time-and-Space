/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.code.tama.tts.client.gui.ARSRoomRegistry;
import com.code.tama.tts.core.registries.tardis.ARSRegistry;
import com.code.tama.tts.server.misc.containers.ARSStructureContainer;
import lombok.Getter;

public class DataARSList {
	@Getter
	private static List<ARSStructureContainer> StructureList;

	public static void setList(List<ARSStructureContainer> list) {
		StructureList = removeDuplicates(list);
		List<ARSRoomRegistry.ARSRoomInfo> tempInfo = new ArrayList<>();
		list.forEach(l -> {
			tempInfo.add(new ARSRoomRegistry.ARSRoomInfo(l.path().toString(), l.Name().getString(), l.color()));
		});
		ARSRegistry.STRUCTURES_INFO.clear();
		ARSRegistry.STRUCTURES_INFO.addAll(removeInfoDuplicates(tempInfo));
		ARSRegistry.STRUCTURES.clear();
		ARSRegistry.STRUCTURES.addAll(StructureList);
	}

	public static List<ARSStructureContainer> removeDuplicates(List<ARSStructureContainer> list) {
		Set<String> seen = new HashSet<>();
		return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
	}

	public static List<ARSRoomRegistry.ARSRoomInfo> removeInfoDuplicates(List<ARSRoomRegistry.ARSRoomInfo> list) {
		Set<String> seen = new HashSet<>();
		return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
	}
}
