package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.entities.controls.ModularControl;
import com.code.tama.mtm.server.tardis.control_lists.AbstractControlList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractConsoleTile extends BlockEntity {

    private final AnimationState RotorAnimationState = new AnimationState();
    public HashMap<Vec3, UUID> ControlMap = new HashMap<>();
    public HashMap<Vec3, Float> ControlAnimationMap = new HashMap<>();
    int ControlSize = 0;
    int AnimationMapSize = 0;
    private boolean IsDestroyed = false;

    public AbstractConsoleTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        this.ControlSize = 0;
        this.ControlMap.forEach((pos, uuid) -> {
            this.ControlSize++;
            tag.putDouble("x_" + this.ControlSize, pos.x);
            tag.putDouble("y_" + this.ControlSize, pos.y);
            tag.putDouble("z_" + this.ControlSize, pos.z);
            tag.putUUID("control_" + this.ControlSize, uuid);
        });
//        for (UUID uuid1 : this.ControlUUIDList) {
//            this.ControlSize++;
//            tag.putUUID("control_" + this.ControlSize, uuid1);
//        }
        tag.putInt("ControlSize", this.ControlSize);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.ControlSize = tag.getInt("ControlSize");
        for (int i = 1; i <= this.ControlSize; i++) {
//            this.ControlUUIDList.add(tag.getUUID("control_" + i));
            this.ControlMap.put(
                    new Vec3(tag.getDouble("x_" + i),
                            tag.getDouble("y_" + i),
                            tag.getDouble("z_" + i)),
                    tag.getUUID("control_" + i));
        }
        super.load(tag);
    }

    @Override
    public void setRemoved() {
        if (!this.IsDestroyed) super.setRemoved();
        this.ControlMap.forEach(((vec3, uuid) -> {
            assert this.level != null;
            if (!this.level.isClientSide)
                if (this.level.getServer().getLevel(this.level.dimension()).getEntity(uuid) != null)
                    this.level.getServer().getLevel(this.level.dimension()).getEntity(uuid).remove(Entity.RemovalReason.DISCARDED);
        }));
        this.ControlMap = new HashMap<>();
        this.ControlSize = 0;
    }

    public void Destroy() {
        this.IsDestroyed = true;
        this.setRemoved();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        // Only summon the entity server-side to avoid duplication
        if (!level.isClientSide && this.ControlMap.isEmpty()) {
            this.summonButtons(level);
        }
    }

    private void summonButtons(Level level) {
        BlockPos blockPos = this.getBlockPos();
        Vec3 centerPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
        this.GetControlList().GetPositionSizeMap().forEach((summonPos, Sizes) -> {
            summonPos = centerPos.add(summonPos);
            ModularControl entity = new ModularControl(level, this, summonPos, (float) Sizes.x, (float) Sizes.y, (float) Sizes.z);
            entity.setPos(summonPos);
            level.addFreshEntity(entity);
            this.ControlSize++;
            this.ControlAnimationMap.put(summonPos, 0.0f);
            this.ControlMap.put(summonPos, entity.getUUID());
            level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 2);
        });
    }

    public AnimationState GetRotorAnimation() {
        return this.RotorAnimationState;
    }

    public void SetControlAnimationMapValue(Vec3 pos, float state) {
        this.ControlAnimationMap.remove(pos);
        this.ControlAnimationMap.put(pos, state);
    }

    public HashMap<Vec3, Float> GetControlAnimationMap() {
        return this.ControlAnimationMap;
    }

    abstract AbstractControlList GetControlList();

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if(blockEntity instanceof ConsoleTile consoleTile) {
            level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
                if(level.isClientSide)
                    consoleTile.GetRotorAnimation().animateWhen(cap.ShouldPlayRotorAnimation(), (int) level.getGameTime());
                if(cap.IsInFlight()) {
                        cap.GetFlightScheme().GetFlightLoop().PlayIfUnfinished(level, pos);
                }
                else cap.GetFlightScheme().GetFlightLoop().SetFinished(true);
            });
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.AnimationMapSize = 0;
        this.ControlAnimationMap.forEach((pos, state) -> {
            this.AnimationMapSize++;
            tag.putDouble("x_" + this.AnimationMapSize, pos.x);
            tag.putDouble("y_" + this.AnimationMapSize, pos.y);
            tag.putDouble("z_" + this.AnimationMapSize, pos.z);
            tag.putFloat("state_" + this.AnimationMapSize, state);
        });
        tag.putInt("animation_map_size", this.AnimationMapSize);
        return super.getUpdateTag();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        // Empty Map
        this.ControlAnimationMap = new HashMap<>();
        // Start syncing
        this.AnimationMapSize = tag.getInt("animation_map_size");
        for (int i = 1; i <= this.ControlSize; i++) {
            this.ControlAnimationMap.put(
                    new Vec3(tag.getDouble("x_" + i),
                            tag.getDouble("y_" + i),
                            tag.getDouble("z_" + i)),
                    tag.getFloat("state_" + i));
        }
        super.handleUpdateTag(tag);
    }
}