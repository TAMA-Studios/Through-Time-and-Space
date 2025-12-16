/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.lists;

import com.code.tama.triggerapi.helpers.OxygenHelper;
import com.code.tama.tts.server.data.json.dataHolders.DataDimOxygen;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataDimOxygenList {
	@Getter
	private static List<DataDimOxygen> StructureList;

	public static void setList(List<DataDimOxygen> list) {
		StructureList = removeDuplicates(list);
		OxygenHelper.setMap(StructureList);
	}

	public static List<DataDimOxygen> removeDuplicates(List<DataDimOxygen> list) {
		Set<String> seen = new HashSet<>();
		return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
	}
}