/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.codec.lua;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class CodecToLua {
	public <T> LuaValue convertCodecToLua(Codec<T> codec, T instance) {
		// 1. Encode Java Instance to JsonElement using JsonOps
		JsonElement json = codec.encodeStart(JsonOps.INSTANCE, instance).getOrThrow(false, (error) -> {
		});

		// 2. Convert GSON to LuaJ Table
		return CodecToLua.mapJsonToLua(json);
	}
	public static LuaValue mapJsonToLua(JsonElement element) {
		if (element.isJsonPrimitive()) {
			JsonPrimitive p = element.getAsJsonPrimitive();
			if (p.isBoolean())
				return LuaValue.valueOf(p.getAsBoolean());
			if (p.isNumber())
				return LuaValue.valueOf(p.getAsDouble());
			return LuaValue.valueOf(p.getAsString());
		} else if (element.isJsonArray()) {
			LuaTable table = new LuaTable();
			JsonArray array = element.getAsJsonArray();
			for (int i = 0; i < array.size(); i++) {
				// Lua is 1-indexed
				table.set(i + 1, mapJsonToLua(array.get(i)));
			}
			return table;
		} else if (element.isJsonObject()) {
			LuaTable table = new LuaTable();
			for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
				table.set(entry.getKey(), mapJsonToLua(entry.getValue()));
			}
			return table;
		}
		return LuaValue.NIL;
	}
}