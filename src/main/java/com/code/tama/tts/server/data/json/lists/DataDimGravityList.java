/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.code.tama.tts.server.data.json.dataHolders.DataDimGravity;
import lombok.Getter;

import com.code.tama.triggerapi.helpers.GravityHelper;

public class DataDimGravityList {
	@Getter
	private static List<DataDimGravity> GravList;

	public static void setList(List<DataDimGravity> list) {
		GravList = removeDuplicates(list);
		GravityHelper.setMap(GravList);
	}

	public static List<DataDimGravity> removeDuplicates(List<DataDimGravity> list) {
		Set<String> seen = new HashSet<>();
		return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
	}
}