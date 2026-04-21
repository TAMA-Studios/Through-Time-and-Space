/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.codec;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.code.tama.tts.TTSMod;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import io.netty.buffer.ByteBufUtil;
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

	/**
	 * CRITICAL: Returning false disables DFU's "compressed" codec path, which
	 * encodes record fields by positional index (using getIntStream) rather than
	 * by name (using getMap). The compressed path assumes a registry-backed
	 * key-compression scheme that doesn't exist here, and breaks whenever fields
	 * are added/reordered. With compressMaps=false, all records encode as named
	 * maps, which our getMap/createMap handle correctly.
	 */
	@Override
	public boolean compressMaps() {
		return false;
	}

	// ---------------------------------------------------------
	// Empty / convert
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf empty() {
		return new FriendlyByteBuf(Unpooled.buffer());
	}

	@Override
	public <U> U convertTo(DynamicOps<U> outOps, FriendlyByteBuf input) {
		return outOps.createString(input.toString());
	}

	// ---------------------------------------------------------
	// Boolean
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createBoolean(boolean value) {
		FriendlyByteBuf buf = empty();
		buf.writeBoolean(value);
		return buf;
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
	// Numeric --------------- tagged so round-trips preserve exact type
	// ---------------------------------------------------------

	private static final byte TAG_BYTE   = 0;
	private static final byte TAG_SHORT  = 1;
	private static final byte TAG_INT    = 2;
	private static final byte TAG_LONG   = 3;
	private static final byte TAG_FLOAT  = 4;
	private static final byte TAG_DOUBLE = 5;

	@Override
	public FriendlyByteBuf createNumeric(Number i) {
		FriendlyByteBuf buf = empty();
		if      (i instanceof Byte b)    { buf.writeByte(TAG_BYTE);   buf.writeByte(b); }
		else if (i instanceof Short s)   { buf.writeByte(TAG_SHORT);  buf.writeShort(s); }
		else if (i instanceof Integer n) { buf.writeByte(TAG_INT);    buf.writeInt(n); }
		else if (i instanceof Long l)    { buf.writeByte(TAG_LONG);   buf.writeLong(l); }
		else if (i instanceof Float f)   { buf.writeByte(TAG_FLOAT);  buf.writeFloat(f); }
		else if (i instanceof Double d)  { buf.writeByte(TAG_DOUBLE); buf.writeDouble(d); }
		else                             { buf.writeByte(TAG_LONG);   buf.writeLong(i.longValue()); }
		return buf;
	}

	@Override
	public DataResult<Number> getNumberValue(FriendlyByteBuf input) {
		try {
			byte tag = input.readByte();
			return DataResult.success(switch (tag) {
				case TAG_BYTE   -> input.readByte();
				case TAG_SHORT  -> input.readShort();
				case TAG_INT    -> input.readInt();
				case TAG_LONG   -> input.readLong();
				case TAG_FLOAT  -> input.readFloat();
				case TAG_DOUBLE -> input.readDouble();
				default -> throw new IllegalStateException("Unknown numeric tag: " + tag);
			});
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read number: " + e);
		}
	}

	// ---------------------------------------------------------
	// String
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createString(String value) {
		FriendlyByteBuf buf = empty();
		buf.writeUtf(value);
		return buf;
	}

	@Override
	public DataResult<String> getStringValue(FriendlyByteBuf input) {
		try {
			return DataResult.success(input.readUtf());
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read string: " + e);
		}
	}

	// ---------------------------------------------------------
	// IntStream --------------- separate fast path, NO type tag, used by BlockPos etc.
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createIntList(IntStream input) {
		FriendlyByteBuf buf = empty();
		int[] ints = input.toArray();
		buf.writeVarInt(ints.length);
		for (int i : ints) {
			buf.writeInt(i);
		}
		return buf;
	}

	@Override
	public DataResult<IntStream> getIntStream(FriendlyByteBuf input) {
		try {
			int size = input.readVarInt();
			if (size < 0 || size > 4096) {
				return DataResult.error(() -> "Suspicious int stream size: " + size);
			}
			int[] ints = new int[size];
			for (int i = 0; i < size; i++) {
				ints[i] = input.readInt();
			}
			return DataResult.success(Arrays.stream(ints));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read int stream: " + e);
		}
	}

	// ---------------------------------------------------------
	// LongStream --------------- separate fast path, NO type tag
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createLongList(LongStream input) {
		FriendlyByteBuf buf = empty();
		long[] longs = input.toArray();
		buf.writeVarInt(longs.length);
		for (long l : longs) {
			buf.writeLong(l);
		}
		return buf;
	}

	@Override
	public DataResult<LongStream> getLongStream(FriendlyByteBuf input) {
		try {
			int size = input.readVarInt();
			if (size < 0 || size > 4096) {
				return DataResult.error(() -> "Suspicious long stream size: " + size);
			}
			long[] longs = new long[size];
			for (int i = 0; i < size; i++) {
				longs[i] = input.readLong();
			}
			return DataResult.success(Arrays.stream(longs));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed to read long stream: " + e);
		}
	}

	// ---------------------------------------------------------
	// Generic list --------------- length-prefixed blobs
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createList(Stream<FriendlyByteBuf> input) {
		FriendlyByteBuf buf = empty();
		List<FriendlyByteBuf> list = input.toList();
		buf.writeVarInt(list.size());
		for (FriendlyByteBuf element : list) {
			int len = element.readableBytes();
			buf.writeVarInt(len);
			buf.writeBytes(element, element.readerIndex(), len);
		}
		return buf;
	}

	@Override
	public DataResult<Stream<FriendlyByteBuf>> getStream(FriendlyByteBuf input) {
		try {
			if (input.readableBytes() == 0) {
				return DataResult.success(Stream.empty());
			}
			int size = input.readVarInt();
			if (size < 0 || size > 65536) {
				return DataResult.error(() -> "Suspicious list size: " + size);
			}
			List<FriendlyByteBuf> list = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				int length = input.readVarInt();
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
	// Map --------------- entry count + (utf key + length-prefixed value blob) per entry
	// ---------------------------------------------------------

	@Override
	public FriendlyByteBuf createMap(Stream<Pair<FriendlyByteBuf, FriendlyByteBuf>> input) {
		FriendlyByteBuf buf = empty();
		List<Pair<FriendlyByteBuf, FriendlyByteBuf>> entries = input.toList();
		buf.writeVarInt(entries.size());
		for (var entry : entries) {
			// Key is always a UTF string buffer
			FriendlyByteBuf keyBuf = new FriendlyByteBuf(entry.getFirst().copy());
			buf.writeUtf(keyBuf.readUtf());
			// Value is a length-prefixed blob
			FriendlyByteBuf val = entry.getSecond();
			int len = val.readableBytes();
			buf.writeVarInt(len);
			buf.writeBytes(val, val.readerIndex(), len);
		}
		return buf;
	}

	@Override
	public DataResult<MapLike<FriendlyByteBuf>> getMap(FriendlyByteBuf input) {
		try {
			if (input.readableBytes() == 0) {
				return DataResult.success(MapLike.forMap(Map.of(), FriendlyByteBufOps.INSTANCE));
			}
			int size = input.readVarInt();
			if (size < 0 || size > 65536) {
				return DataResult.error(() -> "Suspicious map size: " + size);
			}
			Map<String, FriendlyByteBuf> map = new HashMap<>();
			for (int i = 0; i < size; i++) {
				String key = input.readUtf();
				int vLen = input.readVarInt();
				byte[] vData = new byte[vLen];
				input.readBytes(vData);
				map.put(key, new FriendlyByteBuf(Unpooled.wrappedBuffer(vData)));
			}
			return DataResult.success(new MapLike<>() {
				@Override
				public Stream<Pair<FriendlyByteBuf, FriendlyByteBuf>> entries() {
					return map.entrySet().stream()
							.map(e -> Pair.of(createString(e.getKey()), e.getValue()));
				}

				@Override
				public FriendlyByteBuf get(FriendlyByteBuf key) {
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
		return getMap(new FriendlyByteBuf(input.copy())).map(MapLike::entries);
	}

	// ---------------------------------------------------------
	// Merge helpers
	// ---------------------------------------------------------

	@Override
	public RecordBuilder<FriendlyByteBuf> mapBuilder() {
		return new RecordBuilder.MapBuilder<>(this);
	}

	@Override
	public DataResult<FriendlyByteBuf> mergeToList(FriendlyByteBuf list, FriendlyByteBuf value) {
		try {
			Stream<FriendlyByteBuf> stream = getStream(new FriendlyByteBuf(list.copy()))
					.result().orElse(Stream.empty());
			return DataResult.success(createList(Stream.concat(stream, Stream.of(value))));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed mergeToList: " + e);
		}
	}

	@Override
	public DataResult<FriendlyByteBuf> mergeToMap(FriendlyByteBuf map, FriendlyByteBuf key, FriendlyByteBuf value) {
		try {
			MapLike<FriendlyByteBuf> ml = getMap(new FriendlyByteBuf(map.copy())).result().orElse(null);
			Map<String, FriendlyByteBuf> m = new HashMap<>();
			if (ml != null) {
				ml.entries().forEach(p -> m.put(new FriendlyByteBuf(p.getFirst().copy()).readUtf(), p.getSecond()));
			}
			m.put(new FriendlyByteBuf(key.copy()).readUtf(), value);
			return DataResult.success(
					createMap(m.entrySet().stream().map(e -> Pair.of(createString(e.getKey()), e.getValue()))));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed mergeToMap: " + e);
		}
	}

	@Override
	public DataResult<FriendlyByteBuf> mergeToMap(FriendlyByteBuf map, MapLike<FriendlyByteBuf> values) {
		try {
			MapLike<FriendlyByteBuf> ml = getMap(new FriendlyByteBuf(map.copy())).result().orElse(null);
			Map<String, FriendlyByteBuf> m = new HashMap<>();
			if (ml != null) {
				ml.entries().forEach(p -> m.put(new FriendlyByteBuf(p.getFirst().copy()).readUtf(), p.getSecond()));
			}
			values.entries().forEach(p -> m.put(new FriendlyByteBuf(p.getFirst().copy()).readUtf(), p.getSecond()));
			return DataResult.success(
					createMap(m.entrySet().stream().map(e -> Pair.of(createString(e.getKey()), e.getValue()))));
		} catch (Exception e) {
			return DataResult.error(() -> "Failed mergeToMap(values): " + e);
		}
	}

	@Override
	public FriendlyByteBuf remove(FriendlyByteBuf input, String key) {
		MapLike<FriendlyByteBuf> ml = getMap(new FriendlyByteBuf(input.copy())).result().orElse(null);
		if (ml == null) return input;
		Map<String, FriendlyByteBuf> m = ml.entries()
				.collect(Collectors.toMap(p -> new FriendlyByteBuf(p.getFirst().copy()).readUtf(), Pair::getSecond));
		m.remove(key);
		return createMap(m.entrySet().stream().map(e -> Pair.of(createString(e.getKey()), e.getValue())));
	}

	// ---------------------------------------------------------
	// Helper --------------- used by packet encode/decode
	// ---------------------------------------------------------

	public static class Helper {

		public static <T> T readWithCodec(FriendlyByteBuf buf, Codec<T> codec) {
			return readWithCodec(buf, codec, null);
		}

		public static <T> T readWithCodec(FriendlyByteBuf buf, Codec<T> codec, T fallback) {
			int len = buf.readVarInt();
			TTSMod.LOGGER.info("[Helper] readWithCodec len={} remaining={}", len, buf.readableBytes());
			byte[] data = new byte[len];
			buf.readBytes(data);
			FriendlyByteBuf temp = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));
			TTSMod.LOGGER.info("[Helper] temp hex: {}", ByteBufUtil.hexDump(temp));
			try {
				return codec.parse(FriendlyByteBufOps.INSTANCE, temp)
						.resultOrPartial(err -> TTSMod.LOGGER.error("Codec parse error: {}", err))
						.orElse(fallback);
			} catch (Exception e) {
				TTSMod.LOGGER.error("Unchecked exception. Remaining: {}. hex: {}",
						temp.readableBytes(), ByteBufUtil.hexDump(temp));
				TTSMod.LOGGER.error("Stack trace:", e);
				return fallback;
			}
		}

		public static <T> void writeWithCodec(FriendlyByteBuf buf, Codec<T> codec, T value) {
			FriendlyByteBuf temp = codec.encodeStart(FriendlyByteBufOps.INSTANCE, value)
					.resultOrPartial(TTSMod.LOGGER::error).orElseThrow();
			int len = temp.readableBytes();
			buf.writeVarInt(len); // write our own length prefix
			buf.writeBytes(temp, 0, len);
		}
	}
}