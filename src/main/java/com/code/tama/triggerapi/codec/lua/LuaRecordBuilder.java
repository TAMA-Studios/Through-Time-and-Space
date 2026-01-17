/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.codec.lua;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.RecordBuilder;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/**
 * Specifically designed for Forge 1.20.1 to bridge Mojang Codecs to LuaJ.
 * AbstractStringBuilder handles the conversion of String field names into
 * LuaValue keys automatically.
 */
class LuaRecordBuilder extends RecordBuilder.AbstractStringBuilder<LuaValue, LuaTable> {

	protected LuaRecordBuilder(DynamicOps<LuaValue> ops) {
		super(ops);
	}

	@Override
	protected LuaTable initBuilder() {
		return new LuaTable();
	}

	@Override
	protected LuaTable append(String key, LuaValue value, LuaTable builder) {
		// This is exactly where fieldOf("fieldName") maps to the Lua Table key
		builder.set(key, value);
		return builder;
	}

	@Override
	protected DataResult<LuaValue> build(LuaTable builder, LuaValue prefix) {
		// If there is no prefix, return the builder directly as a LuaValue
		if (prefix == null || prefix.isnil()) {
			return DataResult.success(builder);
		}

		// Error handling if the prefix provided by the Codec isn't a table
		if (!prefix.istable()) {
			return DataResult.error(() -> "mergeToMap called with not a map: " + prefix, prefix);
		}

		// Logic for merging: Prefix keys are added first, then the builder (new data)
		// overwrites them
		LuaTable merged = new LuaTable();
		LuaTable src = prefix.checktable();

		// 1. Copy everything from the prefix (existing data)
		copyTable(src, merged);

		// 2. Copy/Overwrite with everything from the current builder (newly encoded
		// data)
		copyTable(builder, merged);

		return DataResult.success(merged);
	}

	/**
	 * Helper to iterate through a LuaTable and copy entries into another.
	 */
	private void copyTable(LuaTable source, LuaTable destination) {
		LuaValue k = LuaValue.NIL;
		while (true) {
			Varargs n = source.next(k);
			k = n.arg1();
			if (k.isnil())
				break;
			destination.set(k, n.arg(2));
		}
	}
}