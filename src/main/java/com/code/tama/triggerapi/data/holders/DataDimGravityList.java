/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.data.holders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

import com.code.tama.triggerapi.helpers.GravityHelper;

public class DataDimGravityList {
	@Getter
	private static List<DataDimGravity> StructureList;

	public static void setList(List<DataDimGravity> list) {
		StructureList = removeDuplicates(list);
		GravityHelper.setMap(StructureList);
	}

	public static List<DataDimGravity> removeDuplicates(List<DataDimGravity> list) {
		Set<String> seen = new HashSet<>();
		return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
	}
}