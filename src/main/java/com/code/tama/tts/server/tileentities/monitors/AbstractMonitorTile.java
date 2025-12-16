/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.monitors;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.tts.client.util.Fonts;
import com.code.tama.tts.server.blocks.monitor.AbstractMonitorBlock;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

@Slf4j
@Getter
@Setter
public abstract class AbstractMonitorTile extends AbstractPortalTile {
	public int categoryID = 1;
	public boolean powered = false;
	public DyeColor color;
	public ResourceLocation FONT = Fonts.DEFAULT;

	public AbstractMonitorTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
		this.color = DyeColor.WHITE;
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.putBoolean("powered", isPowered());
		tag.putInt("categoryID", getCategoryID());
		tag.putInt("dye", this.color.getId());
		super.saveAdditional(tag);
	}

	public AbstractMonitorBlock GetBlock() {
		return ((AbstractMonitorBlock) this.getBlockState().getBlock());
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		saveAdditional(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		load(tag);
		super.handleUpdateTag(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		setPowered(tag.getBoolean("powered"));
		setCategoryID(tag.getInt("categoryID"));
		this.color = DyeColor.byId(tag.getInt("dye"));
		super.load(tag);
	}

	@Override
	public void onLoad() {
		assert this.getLevel() != null;
		GetTARDISCapSupplier(this.getLevel())
				.ifPresent(cap -> cap.UpdateClient(DataUpdateValues.ALL));
		super.onLoad();
	}
}
