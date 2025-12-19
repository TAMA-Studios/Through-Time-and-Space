/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.code.tama.tts.server.misc.containers.ARSStructureContainer;
import com.code.tama.tts.server.registries.tardis.ARSRegistry;
import lombok.Getter;

public class DataARSList {
	@Getter
	private static List<ARSStructureContainer> StructureList;

	public static void setList(List<ARSStructureContainer> list) {
		StructureList = removeDuplicates(list);
		ARSRegistry.STRUCTURES.clear();
		ARSRegistry.STRUCTURES.addAll(StructureList);
	}

	public static List<ARSStructureContainer> removeDuplicates(List<ARSStructureContainer> list) {
		Set<String> seen = new HashSet<>();
		return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
	}
}
