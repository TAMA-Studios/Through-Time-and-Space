/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.codec.lua;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class LuaValueOps implements DynamicOps<LuaValue> {

	public static final LuaValueOps INSTANCE = new LuaValueOps();

	private LuaValueOps() {
	}

	@Override
	public LuaValue empty() {
		return LuaValue.NIL;
	}

	@Override
	public LuaValue createNumeric(Number value) {
		return LuaValue.valueOf(value.doubleValue());
	}

	@Override
	public LuaValue createBoolean(boolean value) {
		return LuaValue.valueOf(value);
	}

	@Override
	public LuaValue createString(String value) {
		return LuaValue.valueOf(value);
	}

	@Override
	public DataResult<Number> getNumberValue(LuaValue input) {
		if (input.isnumber()) {
			return DataResult.success(input.todouble());
		}
		if (input.isboolean()) {
			return DataResult.success(input.toboolean() ? 1 : 0);
		}
		return DataResult.error(() -> "Not a number: " + input);
	}

	@Override
	public DataResult<Boolean> getBooleanValue(LuaValue input) {
		if (input.isboolean()) {
			return DataResult.success(input.toboolean());
		}
		if (input.isnumber()) {
			return DataResult.success(input.todouble() != 0);
		}
		return DataResult.error(() -> "Not a boolean: " + input);
	}

	@Override
	public DataResult<String> getStringValue(LuaValue input) {
		if (input.isstring()) {
			return DataResult.success(input.tojstring());
		}
		return DataResult.error(() -> "Not a string: " + input);
	}

	@Override
	public DataResult<Stream<LuaValue>> getStream(LuaValue input) {
		if (!input.istable()) {
			return DataResult.error(() -> "Not a table: " + input);
		}

		LuaTable table = input.checktable();
		List<LuaValue> values = new ArrayList<>();

		int i = 1;
		while (true) {
			LuaValue v = table.get(i++);
			if (v.isnil())
				break;
			values.add(v);
		}

		return DataResult.success(values.stream());
	}

	@Override
	public DataResult<Consumer<Consumer<LuaValue>>> getList(LuaValue input) {
		return getStream(input).map(stream -> stream::forEach);
	}

	@Override
	public LuaValue createList(Stream<LuaValue> input) {
		LuaTable table = new LuaTable();
		int[] index = {1};
		input.forEach(v -> table.set(index[0]++, v));
		return table;
	}

	@Override
	public DataResult<LuaValue> mergeToList(LuaValue list, LuaValue value) {
		if (!list.isnil() && !list.istable()) {
			return DataResult.error(() -> "mergeToList called with not a list: " + list, list);
		}

		LuaTable result = new LuaTable();
		int index = 1;

		if (list.istable()) {
			LuaTable src = list.checktable();
			while (true) {
				LuaValue v = src.get(index);
				if (v.isnil())
					break;
				result.set(index, v);
				index++;
			}
		}

		result.set(index, value);
		return DataResult.success(result);
	}

	@Override
	public DataResult<LuaValue> mergeToMap(LuaValue map, LuaValue key, LuaValue value) {
		if (!map.isnil() && !map.istable()) {
			return DataResult.error(() -> "mergeToMap called with not a map: " + map, map);
		}
		if (!key.isstring()) {
			return DataResult.error(() -> "Key is not a string: " + key, map);
		}

		LuaTable result = new LuaTable();

		if (map.istable()) {
			LuaTable src = map.checktable();
			LuaValue k = LuaValue.NIL;
			while (true) {
				Varargs n = src.next(k);
				if ((k = n.arg1()).isnil())
					break;
				result.set(k, n.arg(2));
			}
		}

		result.set(key, value);
		return DataResult.success(result);
	}

	@Override
	public DataResult<MapLike<LuaValue>> getMap(LuaValue input) {
		if (!input.istable()) {
			return DataResult.error(() -> "Not a table: " + input);
		}

		LuaTable table = input.checktable();

		return DataResult.success(new MapLike<>() {
			@Nullable @Override
			public LuaValue get(LuaValue key) {
				return table.get(key);
			}

			@Nullable @Override
			public LuaValue get(String key) {
				return table.get(key);
			}

			@Override
			public Stream<Pair<LuaValue, LuaValue>> entries() {
				List<Pair<LuaValue, LuaValue>> out = new ArrayList<>();
				LuaValue k = LuaValue.NIL;

				while (true) {
					Varargs n = table.next(k);
					if ((k = n.arg1()).isnil())
						break;
					out.add(Pair.of(k, n.arg(2)));
				}

				return out.stream();
			}

			@Override
			public String toString() {
				return "LuaMap[" + table + "]";
			}
		});
	}

	@Override
	public LuaValue createMap(Stream<Pair<LuaValue, LuaValue>> map) {
		LuaTable table = new LuaTable();
		map.forEach(p -> table.set(p.getFirst(), p.getSecond()));
		return table;
	}

	@Override
	public LuaValue remove(LuaValue input, String key) {
		if (!input.istable())
			return input;

		LuaTable src = input.checktable();
		LuaTable result = new LuaTable();

		LuaValue k = LuaValue.NIL;
		while (true) {
			Varargs n = src.next(k);
			if ((k = n.arg1()).isnil())
				break;

			if (!k.tojstring().equals(key)) {
				result.set(k, n.arg(2));
			}
		}

		return result;
	}

	@Override
	public ListBuilder<LuaValue> listBuilder() {
		return new LuaListBuilder();
	}

	@Override
	public DataResult<Stream<Pair<LuaValue, LuaValue>>> getMapValues(LuaValue input) {
		if (!input.istable()) {
			return DataResult.error(() -> "Not a table: " + input);
		}

		LuaTable table = input.checktable();
		List<Pair<LuaValue, LuaValue>> values = new ArrayList<>();

		LuaValue k = LuaValue.NIL;
		while (true) {
			Varargs n = table.next(k);
			if ((k = n.arg1()).isnil())
				break;
			values.add(Pair.of(k, n.arg(2)));
		}

		return DataResult.success(values.stream());
	}

	@Override
	public <U> U convertTo(DynamicOps<U> outOps, LuaValue input) {

		if (input.isnil()) {
			return outOps.empty();
		}

		if (input.isboolean()) {
			return outOps.createBoolean(input.toboolean());
		}

		if (input.isnumber()) {
			return outOps.createNumeric(input.todouble());
		}

		if (input.isstring()) {
			return outOps.createString(input.tojstring());
		}

		if (input.istable()) {
			LuaTable table = input.checktable();

			// Detect array-style table
			boolean isArray = true;
			int index = 1;

			while (true) {
				LuaValue v = table.get(index);
				if (v.isnil())
					break;
				index++;
			}

			// If there are any non-numeric keys, it's a map
			LuaValue k = LuaValue.NIL;
			while (true) {
				Varargs n = table.next(k);
				if ((k = n.arg1()).isnil())
					break;

				if (!k.isint() || k.toint() <= 0) {
					isArray = false;
					break;
				}
			}

			if (isArray) {
				Stream<U> stream = Stream.iterate(1, i -> i + 1).map(i -> table.get(i)).takeWhile(v -> !v.isnil())
						.map(v -> convertTo(outOps, v));

				return outOps.createList(stream);
			} else {
				Stream<Pair<U, U>> stream = StreamSupport
						.stream(Spliterators.spliteratorUnknownSize(new Iterator<Pair<U, U>>() {
							LuaValue key = LuaValue.NIL;

							@Override
							public boolean hasNext() {
								Varargs n = table.next(key);
								return !n.arg1().isnil();
							}

							@Override
							public Pair<U, U> next() {
								Varargs n = table.next(key);
								key = n.arg1();
								return Pair.of(convertTo(outOps, key), convertTo(outOps, n.arg(2)));
							}
						}, Spliterator.ORDERED), false);

				return outOps.createMap(stream);
			}
		}

		// Fallback
		return outOps.empty();
	}

	@Override
	public RecordBuilder<LuaValue> mapBuilder() {
		return new LuaRecordBuilder(this);
	}

	@Override
	public String toString() {
		return "LUA";
	}

	public class LuaListBuilder implements ListBuilder<LuaValue> {
		private DataResult<LuaTable> result = DataResult.success(new LuaTable());

		@Override
		public DynamicOps<LuaValue> ops() {
			return INSTANCE;
		}

		@Override
		public ListBuilder<LuaValue> add(LuaValue value) {
			result = result.map(table -> {
				table.set(table.length() + 1, value);
				return table;
			});
			return this;
		}

		@Override
		public ListBuilder<LuaValue> add(DataResult<LuaValue> value) {
			result = result.apply2stable((table, val) -> {
				table.set(table.length() + 1, val);
				return table;
			}, value);
			return this;
		}

		@Override
		public ListBuilder<LuaValue> withErrorsFrom(DataResult<?> errorSource) {
			result = result.flatMap(v -> errorSource.map(e -> v));
			return this;
		}

		@Override
		public ListBuilder<LuaValue> mapError(java.util.function.UnaryOperator<String> onError) {
			result = result.mapError(onError);
			return this;
		}

		@Override
		public DataResult<LuaValue> build(LuaValue prefix) {
			if (prefix == null || prefix.isnil()) {
				return result.map(v -> v);
			}
			// If there is a prefix list, prepend it
			return result.flatMap(table -> {
				if (!prefix.istable())
					return DataResult.error(() -> "Prefix is not a table");
				LuaTable combined = new LuaTable();
				LuaTable pTable = prefix.checktable();
				for (int i = 1; i <= pTable.length(); i++) {
					combined.set(i, pTable.get(i));
				}
				int offset = pTable.length();
				for (int i = 1; i <= table.length(); i++) {
					combined.set(offset + i, table.get(i));
				}
				return DataResult.success(combined);
			});
		}
	}
}
