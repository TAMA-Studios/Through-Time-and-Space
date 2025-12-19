/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.code.tama.tts.server.data.json.dataHolders.flightEvents.DataFlightEvent;
import lombok.Getter;

public class DataFlightEventList {
	@Getter
	private static List<DataFlightEvent> List = new ArrayList<>();

	public static void setList(List<DataFlightEvent> list) {
		List = removeDuplicates(list);
	}

	public static List<DataFlightEvent> removeDuplicates(List<DataFlightEvent> list) {
		Set<String> seen = new HashSet<>();
		return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
	}
}