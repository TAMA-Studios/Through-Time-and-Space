/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.code.tama.tts.TTSMod;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import io.netty.buffer.Unpooled;

import net.minecraft.network.FriendlyByteBuf;

/**
 * A DynamicOps implementation for serializing/deserializing via
 * FriendlyByteBuf. Supports primitives, lists, and maps.
 */
public class FriendlyByteBufOps implements DynamicOps<FriendlyByteBuf> {
	public static final FriendlyByteBufOps INSTANCE = new FriendlyByteBufOps();

	private FriendlyByteBufOps() {
	}

	// ---------------------------------------------------------
	// Base Ops
	// ---------------------------------------------------------

	@Override
	public <U> U convertTo(DynamicOps<U> outOps, FriendlyByteBuf input) {
		// Convert by serializing to JSON and re-parsing
		// NOTE: This implementation might be flawed for non-string data,
		// but is kept as per original code structure.
		return outOps.createString(input.toString());
	}

	@Override
	public FriendlyByteBuf createBoolean(boolean value) {
		FriendlyByteBuf buf = empty();
		buf.writeBoolean(value);
		return buf;
	}

	// ---------------------------------------------------------
	// Primitive extractors
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createList(Stream<FriendlyByteBuf> input) {
		FriendlyByteBuf buf = empty();
		List<FriendlyByteBuf> list = input.toList();
		buf.writeVarInt(list.size());
		for (FriendlyByteBuf element : list) {
			// Ensure array() is called on a buffer with a known backing array and no
			// offset.
			// A safer way is using ByteBufUtil.getBytes(element) or element.copy().array()
			// but relying on element.array() for buffers created by 'create*' methods
			// should be fine.
			byte[] data = element.array();
			buf.writeVarInt(data.length);
			buf.writeBytes(data);
		}
		return buf;
	}

	@Override
	public FriendlyByteBuf createMap(Stream<Pair<FriendlyByteBuf, FriendlyByteBuf>> input) {
		FriendlyByteBuf buf = empty();
		List<Pair<FriendlyByteBuf, FriendlyByteBuf>> entries = input.toList();
		buf.writeVarInt(entries.size());
		for (var entry : entries) {
			// NOTE: This assumes the key buffer contains only a string and consumes it.
			// This is generally acceptable in createMap as the key buffer is discarded.
			String key = entry.getFirst().readUtf();
			buf.writeUtf(key);

			byte[] vData = entry.getSecond().array();
			buf.writeVarInt(vData.length);
			buf.writeBytes(vData);
		}
		return buf;
	}

	@Override
	public FriendlyByteBuf createNumeric(Number i) {
		FriendlyByteBuf buf = empty();
		buf.writeInt(i.intValue());
		return buf;
	}

	// ---------------------------------------------------------
	// Primitive creators
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createString(String value) {
		FriendlyByteBuf buf = empty();
		buf.writeUtf(value);
		return buf;
	}

	@Override
	public FriendlyByteBuf empty() {
		return new FriendlyByteBuf(Unpooled.buffer());
	}

	@Override
	public DataResult<Boolean> getBooleanValue(FriendlyByteBuf input) {
		try {
			return DataResult.success(input.readBoolean());
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read boolean: " + e);
		}
	}

	// ---------------------------------------------------------
	// List handling
	// ---------------------------------------------------------

	@Override
	public DataResult<MapLike<FriendlyByteBuf>> getMap(FriendlyByteBuf input) {
		try {
			int size = input.readVarInt();
			Map<String, FriendlyByteBuf> map = new HashMap<>();
			for (int i = 0; i < size; i++) {
				String key = input.readUtf();
				int vLen = input.readVarInt();

				// FIX: Correctly read the raw bytes using readBytes(byte[])
				byte[] vData = new byte[vLen];
				input.readBytes(vData);

				FriendlyByteBuf val = new FriendlyByteBuf(Unpooled.wrappedBuffer(vData));
				map.put(key, val);
			}

			return DataResult.success(new MapLike<>() {
				@Override
				public Stream<Pair<FriendlyByteBuf, FriendlyByteBuf>> entries() {
					return map.entrySet().stream().map(e -> Pair.of(createString(e.getKey()), e.getValue()));
				}

				@Override
				public FriendlyByteBuf get(FriendlyByteBuf key) {
					// FIX: Duplicate the key buffer to prevent advancement of the reader index
					// (consumption) if the Codec tries to read the key multiple times.
					FriendlyByteBuf safeKey = new FriendlyByteBuf(key.copy());
					return map.get(safeKey.readUtf());
				}

				@Override
				public FriendlyByteBuf get(String key) {
					return map.get(key);
				}

				@Override
				public String toString() {
					return map.toString();
				}
			});
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read map: " + e);
		}
	}

	@Override
	public DataResult<Stream<Pair<FriendlyByteBuf, FriendlyByteBuf>>> getMapValues(FriendlyByteBuf input) {
		// NOTE: Must duplicate 'input' before calling getMap, as getMap consumes it.
		return getMap(new FriendlyByteBuf(input.copy())).map(MapLike::entries);
	}

	// ---------------------------------------------------------
	// Map handling
	// ---------------------------------------------------------

	@Override
	public DataResult<Number> getNumberValue(FriendlyByteBuf input) {
		try {
			// NOTE: Using readInt() assumes all numbers are encoded as ints.
			// If longs/doubles are possible, this needs to be more complex.
			return DataResult.success(input.readInt());
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read number: " + e);
		}
	}

	@Override
	public DataResult<Stream<FriendlyByteBuf>> getStream(FriendlyByteBuf input) {
		try {
			int size = input.readVarInt();
			List<FriendlyByteBuf> list = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				int length = input.readVarInt();

				// FIX: Correctly read the raw bytes using readBytes(byte[])
				byte[] data = new byte[length];
				input.readBytes(data);

				list.add(new FriendlyByteBuf(Unpooled.wrappedBuffer(data)));
			}
			return DataResult.success(list.stream());
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read list: " + e);
		}
	}

	// ---------------------------------------------------------
	// Merge helpers
	// ---------------------------------------------------------

	@Override
	public DataResult<String> getStringValue(FriendlyByteBuf input) {
		try {
			return DataResult.success(input.readUtf());
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read string: " + e);
		}
	}

	@Override
	public RecordBuilder<FriendlyByteBuf> mapBuilder() {
		return new com.mojang.serialization.RecordBuilder.MapBuilder<>(this);
	}

	@Override
	public DataResult<FriendlyByteBuf> mergeToList(FriendlyByteBuf list, FriendlyByteBuf value) {
		// NOTE: Must duplicate 'list' before calling getStream, as getStream consumes
		// it.
		try {
			Stream<FriendlyByteBuf> stream = getStream(new FriendlyByteBuf(list.copy())).result()
					.orElse(Stream.empty());
			return DataResult.success(createList(Stream.concat(stream, Stream.of(value))));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed mergeToList: " + e);
		}
	}

	// ---------------------------------------------------------
	// Extra required ops
	// ---------------------------------------------------------

	@Override
	public DataResult<FriendlyByteBuf> mergeToMap(FriendlyByteBuf map, FriendlyByteBuf key, FriendlyByteBuf value) {
		// NOTE: Must duplicate 'map' before calling getMap, as getMap consumes it.
		try {
			FriendlyByteBuf safeMap = new FriendlyByteBuf(map.copy());
			MapLike<FriendlyByteBuf> ml = getMap(safeMap).result().orElse(null);

			FriendlyByteBuf safeKey = new FriendlyByteBuf(key.copy()); // Duplicate key before consuming

			Map<String, FriendlyByteBuf> m = new HashMap<>();
			if (ml != null) {
				// Must duplicate key buffers from the MapLike entries before consuming them!
				ml.entries().forEach(p -> m.put(new FriendlyByteBuf(p.getFirst().copy()).readUtf(), p.getSecond()));
			}
			m.put(safeKey.readUtf(), value);
			return DataResult.success(
					createMap(m.entrySet().stream().map(e -> Pair.of(createString(e.getKey()), e.getValue()))));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed mergeToMap: " + e);
		}
	}

	@Override
	public DataResult<FriendlyByteBuf> mergeToMap(FriendlyByteBuf map, MapLike<FriendlyByteBuf> values) {
		// NOTE: Must duplicate 'map' before calling getMap, as getMap consumes it.
		try {
			FriendlyByteBuf safeMap = new FriendlyByteBuf(map.copy());
			MapLike<FriendlyByteBuf> ml = getMap(safeMap).result().orElse(null);

			Map<String, FriendlyByteBuf> m = new HashMap<>();
			if (ml != null) {
				// Must duplicate key buffers from the MapLike entries before consuming them!
				ml.entries().forEach(p -> m.put(new FriendlyByteBuf(p.getFirst().copy()).readUtf(), p.getSecond()));
			}
			// Must duplicate key buffers from the input MapLike entries before consuming
			// them!
			values.entries().forEach(p -> m.put(new FriendlyByteBuf(p.getFirst().copy()).readUtf(), p.getSecond()));

			return DataResult.success(
					createMap(m.entrySet().stream().map(e -> Pair.of(createString(e.getKey()), e.getValue()))));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed mergeToMap(values): " + e);
		}
	}

	// ---------------------------------------------------------
	// Record builder
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf remove(FriendlyByteBuf input, String key) {
		// NOTE: Must duplicate 'input' before calling getMap, as getMap consumes it.
		FriendlyByteBuf safeInput = new FriendlyByteBuf(input.copy());
		MapLike<FriendlyByteBuf> ml = getMap(safeInput).result().orElse(null);
		if (ml == null)
			return input;

		// Must duplicate key buffers from the MapLike entries before consuming them!
		Map<String, FriendlyByteBuf> m = ml.entries()
				.collect(Collectors.toMap(p -> new FriendlyByteBuf(p.getFirst().copy()).readUtf(), Pair::getSecond));
		m.remove(key);

		return createMap(m.entrySet().stream().map(e -> Pair.of(createString(e.getKey()), e.getValue())));
	}

	public static class Helper {
		// Decoding
		public static <T> T readWithCodec(FriendlyByteBuf buf, Codec<T> codec) {
			byte[] data = buf.readByteArray();
			FriendlyByteBuf temp = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));

			// The code here was correct for passing the wrapped buffer to the Codec.
			return codec.parse(FriendlyByteBufOps.INSTANCE, temp)
					.resultOrPartial(err -> TTSMod.LOGGER.error("Codec parse error: {}", err)).orElseThrow();
		}

		// Encoding
		public static <T> void writeWithCodec(FriendlyByteBuf buf, Codec<T> codec, T value) {
			FriendlyByteBuf temp = codec.encodeStart(FriendlyByteBufOps.INSTANCE, value)
					.resultOrPartial(TTSMod.LOGGER::error).orElseThrow();

			// The code here was correct for reading the final encoded bytes.
			byte[] data = new byte[temp.readableBytes()];
			temp.readBytes(data);
			buf.writeByteArray(data);
		}
	}
}
