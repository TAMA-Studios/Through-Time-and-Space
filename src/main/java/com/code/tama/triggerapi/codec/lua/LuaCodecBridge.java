/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.codec.lua;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class LuaCodecBridge {
	private <T> LuaValue convertCodecToLua(Codec<T> codec, T instance) {
		JsonElement json = codec.encodeStart(JsonOps.INSTANCE, instance).getOrThrow(false, (error) -> {
			// Handle error
		});

		// 2. Convert GSON to LuaJ Table
		return CodecToLua.mapJsonToLua(json);
	}

	/**
	 * Converts a Java Object into a LuaTable using a Mojang Codec.
	 */
	public static <T> LuaTable encodeToLua(Codec<T> codec, T value) {
		// 1. Run the encoder using your custom LuaValueOps
		DataResult<LuaValue> result = codec.encodeStart(LuaValueOps.INSTANCE, value);

		// 2. Extract the result or throw an error if the codec fails
		LuaValue luaResult = result.getOrThrow(false, (error) -> {
			throw new RuntimeException("Codec encoding failed: " + error);
		});

		// 3. Ensure it's a table (since fieldOf results in a Map/Table)
		if (luaResult.istable()) {
			return (LuaTable) luaResult;
		} else {
			throw new IllegalStateException("Codec did not produce a table. Result was: " + luaResult.typename());
		}
	}
}