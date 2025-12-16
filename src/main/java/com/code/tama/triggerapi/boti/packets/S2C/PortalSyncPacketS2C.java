/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti.packets.S2C;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PortalSyncPacketS2C {
	private final ResourceKey<DimensionType> dimensionTypeId;

	private final BlockPos pos;

	private final ResourceKey<Level> targetLevel;

	private final BlockPos targetPos;

	private final DimensionType type;

	private final float y;

	public PortalSyncPacketS2C(BlockPos pos, ResourceKey<Level> targetLevel, DimensionType type, BlockPos targetPos,
			ResourceKey<DimensionType> dimensionTypeId, float y) {
		this.pos = pos;
		this.targetLevel = targetLevel;
		this.targetPos = targetPos;
		this.type = type;
		this.dimensionTypeId = dimensionTypeId;
		this.y = y;
	}

	@OnlyIn(Dist.CLIENT)
	public static void SyncPortal(PortalSyncPacketS2C msg) {
		Level level = Minecraft.getInstance().level;
		if (level != null) {
			BlockEntity be = level.getBlockEntity(msg.pos);
			if (be instanceof AbstractPortalTile portal) {
				portal.type = msg.type;
				portal.dimensionTypeId = msg.dimensionTypeId;

				portal.setTargetLevel(msg.targetLevel, msg.targetPos, msg.y, false);
			}
		}
	}

	public static PortalSyncPacketS2C decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		ResourceKey<Level> level = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
		BlockPos targetPos = buf.readBlockPos();
		DimensionType type = buf.readJsonWithCodec(DimensionType.CODEC).get();
		ResourceKey<DimensionType> dimensionTypeId = buf.readResourceKey(Registries.DIMENSION_TYPE);
		float y = buf.readFloat();
		return new PortalSyncPacketS2C(pos, level, type, targetPos, dimensionTypeId, y);
	}

	public static void encode(PortalSyncPacketS2C msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
		buf.writeResourceLocation(msg.targetLevel.location());
		buf.writeBlockPos(msg.targetPos);
		buf.writeJsonWithCodec(DimensionType.CODEC, Holder.direct(msg.type));
		buf.writeResourceKey(msg.dimensionTypeId);
		buf.writeFloat(msg.y);
	}

	public static void handle(PortalSyncPacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SyncPortal(msg)));
		ctx.get().setPacketHandled(true);
	}
}
