/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.caps;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.events.RiftEvent;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.S2C.dimensions.SyncLevelCapPacketS2C;
import com.code.tama.tts.server.capabilities.interfaces.ILevelCap;
import com.code.tama.tts.server.data.RiftData;
import com.code.tama.tts.server.misc.containers.TIRBlockContainer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.RequiredArgsConstructor;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;

import com.code.tama.triggerapi.codec.Codecs;

@RequiredArgsConstructor
public class LevelCapability implements ILevelCap {
	private static final Codec<Map<BlockPos, RiftData>> RIFT_MAP_CODEC = RiftData.CODEC.listOf().xmap(
			list -> list.stream().collect(Collectors.toMap(RiftData::getPos, r -> r)),
			map -> new ArrayList<>(map.values()));

	public static final Codec<ILevelCap> CODEC = RecordCodecBuilder
			.create(instance -> instance.group(RIFT_MAP_CODEC.fieldOf("rifts").forGetter(ILevelCap::GetRiftData),
					Codec.unboundedMap(Codecs.UUID_CODEC, TIRBlockContainer.CODEC).fieldOf("tirBlocks")
							.forGetter(ILevelCap::GetTIRBlocks))
					.apply(instance, LevelCapability::new));

	Map<UUID, TIRBlockContainer> TIRBlocks = new HashMap<>();
	Map<AABB, RiftData> riftAABBs = new HashMap<>(); // This doesn't get saved nor synced to client, this is due to it being directly tied to activeRifts
	Map<BlockPos, RiftData> activeRifts = new HashMap<>();
	public final Level level;

	private LevelCapability(Map<BlockPos, RiftData> rifts, Map<UUID, TIRBlockContainer> tirBlocks) {
		this.activeRifts = rifts;
		this.TIRBlocks = tirBlocks;
		this.level = null;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();

		nbt.putInt("TIRSize", TIRBlocks.size());
		nbt.putInt("rifts", activeRifts.size());

		// TIR blocks
		AtomicInteger finalI = new AtomicInteger();
		TIRBlocks.forEach((uuid, tirBlockContainer) -> {
			CompoundTag tag = new CompoundTag();
			tag.putUUID("uuid", uuid);
			tag.put("container",
					TIRBlockContainer.CODEC.encodeStart(NbtOps.INSTANCE, tirBlockContainer).get().orThrow());
			nbt.put(String.valueOf(finalI.getAndIncrement()), tag);
		});

		// Rifts
		AtomicInteger finalI1 = new AtomicInteger();
		activeRifts.forEach((pos, riftData) -> {
			CompoundTag tag = new CompoundTag();
			tag.put("rift", RiftData.CODEC.encodeStart(NbtOps.INSTANCE, riftData).get().orThrow());
			nbt.put(String.valueOf(finalI1.getAndIncrement()), tag);
		});

		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.TIRBlocks.clear();
		this.activeRifts.clear();

		int size = nbt.getInt("TIRSize");
		for (int i = 0; i < size; i++) {
			CompoundTag tag = nbt.getCompound(String.valueOf(i));
			if (tag.contains("uuid")) {
				UUID uuid = tag.getUUID("uuid");

				DataResult<TIRBlockContainer> result = TIRBlockContainer.CODEC.parse(NbtOps.INSTANCE,
						tag.get("container"));
				result.resultOrPartial(err -> TTSMod.LOGGER.error("[TIR] Failed to decode TIRBlockContainer: {}", err))
						.ifPresent(container -> {
							this.TIRBlocks.put(uuid, container);
						});
			}
		}

		size = nbt.getInt("rifts");
		for (int i = 0; i < size; i++) {
			CompoundTag tag = nbt.getCompound(String.valueOf(i));

			DataResult<RiftData> result = RiftData.CODEC.parse(NbtOps.INSTANCE, tag.get("rift"));

			result.resultOrPartial(err -> TTSMod.LOGGER.error("[RIFT] Failed to decode RiftData: {}", err))
					.ifPresent(container -> this.activeRifts.put(container.getPos(), container));
		}
	}

	public void UpdateClient() {
		assert this.level != null;
		if (this.level.isClientSide)
			return;

		Networking.sendPacketToDimension(new SyncLevelCapPacketS2C(this), this.level);
	}

	@Override
	public Map<UUID, TIRBlockContainer> GetTIRBlocks() {
		return this.TIRBlocks;
	}

	@Override
	public void SetTIRBlocks(Map<UUID, TIRBlockContainer> containerMap) {
		this.TIRBlocks.clear();
		this.TIRBlocks = containerMap;
	}

	@Override
	public Map<BlockPos, RiftData> GetRiftData() {
		return this.activeRifts;
	}

	@Override
	public Map<AABB, RiftData> GetRiftDataAABBs() {
		return this.riftAABBs;
	}

	@Override
	public void SetRiftData(Map<BlockPos, RiftData> activeRifts) {
		this.activeRifts.clear();
		this.riftAABBs.clear();
		this.activeRifts = new HashMap<>(activeRifts);

		this.activeRifts.forEach((bp, r) -> {
			this.riftAABBs.put(new AABB(bp.above().south().west(), bp.below().north().east()), r);
		});
	}

	@Override
	public void OnLoad(ServerPlayer player) {
		// Networking.sendToPlayer(player, new UpdateTIRPacketS2C(this.GetTIRBlocks()));
		UpdateClient();
	}

	public void addRift(BlockPos pos, RiftData rift) {
		if (!this.activeRifts.containsKey(pos)) {
			this.activeRifts.put(pos, rift);
			MinecraftForge.EVENT_BUS.post(new RiftEvent.NewRift(Minecraft.getInstance().level, rift));
			UpdateClient();

			this.riftAABBs.put(new AABB(pos.above().south().west(), pos.below().north().east()), rift);
			System.out.println("Adding rift at: " + pos.toShortString() + " " + rift.getYRot());
		}
	}

	@Override
	public void Tick() {
		if (level.isClientSide)
			return;
		if (level.getGameTime() % 20 != 0)
			return; // run every second

		if (level.random.nextFloat() > 0.05f)
			return; // 5% chance

		ThreadLocalRandom random = ThreadLocalRandom.current();

		int range = 100000;

		// Pick random X/Z
		int x = random.nextInt(range * 2) - range;
		int z = random.nextInt(range * 2) - range;

		// Get height (so you're not spawning in the void or underground)
		int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

		BlockPos spawn = new BlockPos(x, y, z);
		ChunkAccess chunk = this.level.getChunk(spawn);
		ChunkPos pos = chunk.getPos();
		x = pos.getMinBlockX() + this.level.random.nextInt(16);
		z = pos.getMinBlockZ() + this.level.random.nextInt(16);
		BlockPos.MutableBlockPos bpos = new BlockPos.MutableBlockPos(x, this.level.getMaxBuildHeight(), z);

		while (bpos.getY() > this.level.getMinBuildHeight()) {
			bpos.move(Direction.DOWN);

			if (this.level.getBlockState(bpos).isSolid() && this.level.isEmptyBlock(bpos.above())) {
				break;
			}
		}
		if (!level.getBlockState(bpos).isSolid())
			return;

		BlockPos riftPos = BlockPos.ZERO;
		Direction facing = Direction.NORTH;

		BlockPos base = bpos.above(); // where rift center would be

		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos anchor = base.relative(dir.getOpposite()); // solid block behind rift

			if (!this.level.getBlockState(anchor).isSolid())
				continue;

			BlockPos front = base.relative(dir);

			if (!this.level.isEmptyBlock(base) || !this.level.isEmptyBlock(front))
				continue;

			Direction left = dir.getCounterClockWise();
			Direction right = dir.getClockWise();

			BlockPos leftPos = base.relative(left);
			BlockPos rightPos = base.relative(right);

			if (!this.level.isEmptyBlock(leftPos) || !this.level.isEmptyBlock(rightPos))
				continue;

			riftPos = base;
			facing = dir;
			break;
		}

		if (riftPos != BlockPos.ZERO)
			addRift(riftPos, new RiftData(riftPos, facing.toYRot(), UUID.randomUUID()));
	}

}