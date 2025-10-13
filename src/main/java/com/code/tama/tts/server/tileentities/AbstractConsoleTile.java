/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.entities.controls.ModularControl;
import com.code.tama.tts.server.tardis.control_lists.AbstractControlList;
import com.code.tama.tts.server.tardis.control_lists.ControlEntityRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractConsoleTile extends BlockEntity {

  public static <T extends BlockEntity> void tick(
      Level level, BlockPos pos, BlockState state, T blockEntity) {
    if (blockEntity instanceof AbstractConsoleTile tile) {
      level
          .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
          .ifPresent(
              cap -> {
                if (level.isClientSide)
                  tile.GetRotorAnimation()
                      .animateWhen(
                          cap.GetFlightData().isPlayRotorAnimation(), (int) level.getGameTime());
                //                if(cap.IsTakingOff()) {
                //                    if(!cap.GetFlightScheme().GetTakeoff().IsFinished())
                //                        cap.GetFlightScheme().GetTakeoff().PlayIfFinished(level,
                // pos);
                //                }
                else if (cap.GetFlightData().isInFlight()) {
                  cap.GetFlightData()
                      .getFlightSoundScheme()
                      .GetFlightLoop()
                      .PlayIfFinished(level, pos);
                } else cap.GetFlightData().getFlightSoundScheme().GetFlightLoop().SetFinished(true);
              });
    }
  }

  public HashMap<Integer, Float> ControlAnimationMap = new HashMap<>();
  public HashMap<Vec3, UUID> ControlMap = new HashMap<>();
  int AnimationMapSize = 0;
  int ControlSize = 0;
  private boolean IsDestroyed = false;

  private final AnimationState RotorAnimationState = new AnimationState();

  public AbstractConsoleTile(
      BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
    super(p_155228_, p_155229_, p_155230_);
  }

  public void Destroy() {
    this.IsDestroyed = true;
    this.setRemoved();
  }

  public HashMap<Integer, Float> GetControlAnimationMap() {
    return this.ControlAnimationMap;
  }

  public AnimationState GetRotorAnimation() {
    return this.RotorAnimationState;
  }

  public void SetControlAnimationMapValue(Integer pos, float state) {
    this.ControlAnimationMap.remove(pos);
    this.ControlAnimationMap.put(pos, state);
  }

  @Nullable @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public @NotNull CompoundTag getUpdateTag() {
    CompoundTag tag = super.getUpdateTag();
    this.AnimationMapSize = 0;
    this.ControlAnimationMap.forEach(
        (id, state) -> {
          this.AnimationMapSize++;
          tag.putInt("id", id);
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
      this.ControlAnimationMap.put(tag.getInt("id"), tag.getFloat("state_" + i));
    }
    super.handleUpdateTag(tag);
  }

  @Override
  public void load(CompoundTag tag) {
    this.ControlSize = tag.getInt("ControlSize");
    for (int i = 1; i <= this.ControlSize; i++) {
      // this.ControlUUIDList.add(tag.getUUID("control_" + i));
      this.ControlMap.put(
          new Vec3(tag.getDouble("x_" + i), tag.getDouble("y_" + i), tag.getDouble("z_" + i)),
          tag.getUUID("control_" + i));
    }
    super.load(tag);
  }

  @Override
  public void onLoad() {
    super.onLoad();
    // Only summon the entity server-side to avoid duplication
    if (!level.isClientSide && this.ControlMap.isEmpty()) {
      this.summonButtons(level);
    }
  }

  @Override
  public void setRemoved() {
    if (!this.IsDestroyed) super.setRemoved();
    this.ControlMap.forEach(
        ((vec3, uuid) -> {
          assert this.level != null;
          if (!this.level.isClientSide)
            if (this.level.getServer().getLevel(this.level.dimension()).getEntity(uuid) != null)
              this.level
                  .getServer()
                  .getLevel(this.level.dimension())
                  .getEntity(uuid)
                  .remove(Entity.RemovalReason.DISCARDED);
        }));
    this.ControlMap = new HashMap<>();
    this.ControlSize = 0;
  }

  @Override
  protected void saveAdditional(@NotNull CompoundTag tag) {
    this.ControlSize = 0;
    this.ControlMap.forEach(
        (pos, uuid) -> {
          this.ControlSize++;
          tag.putDouble("x_" + this.ControlSize, pos.x);
          tag.putDouble("y_" + this.ControlSize, pos.y);
          tag.putDouble("z_" + this.ControlSize, pos.z);
          tag.putUUID("control_" + this.ControlSize, uuid);
        });
    // for (UUID uuid1 : this.ControlUUIDList) {
    // this.ControlSize++;
    // tag.putUUID("control_" + this.ControlSize, uuid1);
    // }
    tag.putInt("ControlSize", this.ControlSize);
    super.saveAdditional(tag);
  }

  public AbstractControlList GetControlList() {
    return new AbstractControlList() {
      @Override
      public ArrayList<ControlEntityRecord> getPositionSizeMap() {
        return super.getPositionSizeMap();
      }
    };
  }

  private void summonButtons(Level level) {
    BlockPos blockPos = this.getBlockPos();
    Vec3 centerPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
    this.GetControlList()
        .getPositionSizeMap()
        .forEach(
            (record) -> {
              assert this.getLevel() != null;
              float offs;
              if (this.getLevel().getBlockState(this.getBlockPos().below()).getBlock()
                  instanceof SnowLayerBlock) offs = 1;
              else
                offs =
                    BlockUtils.getReverseHeightModifier(
                        this.getLevel().getBlockState(this.getBlockPos().below()));
              Vec3 summonPos =
                  centerPos.add(new Vec3(record.minX(), record.minY() - offs, record.minZ()));
              ModularControl entity = new ModularControl(level, this, record);
              entity.setPos(summonPos);
              level.addFreshEntity(entity);
              this.ControlSize++;
              this.ControlAnimationMap.put(record.ID(), 0.0f);
              this.ControlMap.put(summonPos, entity.getUUID());
              level.sendBlockUpdated(
                  this.worldPosition, this.getBlockState(), this.getBlockState(), 2);
            });
  }
}
