/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.entities.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.enums.Controls;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncButtonAnimationSetPacketS2C;
import com.code.tama.tts.server.registries.TTSEntities;
import com.code.tama.tts.server.registries.TTSItems;
import com.code.tama.tts.server.tardis.control_lists.ControlEntityRecord;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import org.jetbrains.annotations.NotNull;

public class ModularControl extends AbstractControlEntity implements IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<String> CONTROL =
            SynchedEntityData.defineId(ModularControl.class, EntityDataSerializers.STRING);
    public Vec3 Position;
    public final int ID;
    public AbstractConsoleTile consoleTile;
    public AABB size;

    /** DO NOT CALL THIS! Use {@code ModularControl(Level, AbstractConsoleTile, ControlEntityRecord)}**/
    public ModularControl(EntityType<ModularControl> modularControlEntityType, Level level) {
        super(modularControlEntityType, level);
        this.ID = 0;
    }

    public ModularControl(Level level, AbstractConsoleTile consoleTile, ControlEntityRecord record) {
        super(TTSEntities.MODULAR_CONTROL.get(), level);
        this.Position = new Vec3(record.minX(), record.minY(), record.minZ());
        this.size = new AABB(0, 0, 0, record.maxX(), record.maxY(), record.maxZ());
        this.SetDimensions(EntityDimensions.scalable(record.maxX(), record.maxY()));
        this.consoleTile = consoleTile;
        this.ID = record.ID();
        this.setPos(this.Position);
    }

    public Controls GetControl() {
        return Controls.valueOf(this.entityData.get(CONTROL));
    }

    @Override
    public void OnControlClicked(ITARDISLevel capability, Player player) {
        if (player.getUsedItemHand() == InteractionHand.OFF_HAND) return;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(TTSItems.SONIC_SCREWDRIVER.get())) {
            if (player.isCrouching()) {
                this.CycleControlBackward();
                player.sendSystemMessage(Component.literal(
                        "Control set to: " + this.GetControl().name().toLowerCase()));
            } else {
                this.CycleControlForward();
                player.sendSystemMessage(Component.literal(
                        "Control set to: " + this.GetControl().name().toLowerCase()));
            }
            return;
        }
        InteractionResult interactionResult = this.GetControl().GetControl().OnRightClick(capability, player);

        this.level()
                .playSound(
                        null,
                        this.blockPosition(),
                        interactionResult == InteractionResult.SUCCESS
                                ? this.GetControl().GetControl().GetSuccessSound()
                                : this.GetControl().GetControl().GetFailSound(),
                        SoundSource.BLOCKS);

        if (this.GetControl().GetControl().NeedsUpdate() && !this.level().isClientSide) {
            Networking.sendPacketToDimension(
                    this.level().dimension(),
                    new SyncButtonAnimationSetPacketS2C(
                            this.consoleTile.ControlAnimationMap, this.consoleTile.getBlockPos()));
        }
    }

    @Override
    public void OnControlHit(ITARDISLevel capability, Entity entity) {
        if (entity instanceof Player player) if (player.getUsedItemHand() == InteractionHand.OFF_HAND) return;
        InteractionResult interactionResult = this.GetControl().GetControl().OnLeftClick(capability, entity);

        this.level()
                .playSound(
                        null,
                        this.blockPosition(),
                        interactionResult == InteractionResult.SUCCESS
                                ? this.GetControl().GetControl().GetSuccessSound()
                                : this.GetControl().GetControl().GetFailSound(),
                        SoundSource.BLOCKS);

        if (this.GetControl().GetControl().NeedsUpdate() && !this.level().isClientSide) {
            System.out.println("Needs Update!");
        }
    }

    public void SetControl(Controls control) {
        this.entityData.set(CONTROL, control.name());
    }

    @Override
    public Component TranslationKey() {
        return Component.translatable(
                "tts.tardis.control." + this.GetControl().name().toLowerCase());
    }

    public void UpdateConsoleAnimationMap() {
        if (this.consoleTile == null) return;
        if (!this.GetControl().GetControl().NeedsUpdate()) return;
        if (!this.consoleTile.GetControlAnimationMap().containsKey(this.ID)) return;
        if (this.consoleTile.GetControlAnimationMap().get(this.ID)
                == this.GetControl().GetControl().GetAnimationState()) return;
        this.consoleTile.GetControlAnimationMap().remove(this.ID);
        this.consoleTile
                .GetControlAnimationMap()
                .put(this.ID, this.GetControl().GetControl().GetAnimationState());
    }

    @Override
    public AABB getAABB() {
        return this.size;
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buf) {
        this.size = new AABB(0, 0, 0, buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.SetControl(Controls.values()[buf.readInt()]);
    }

    @Override
    public void tick() {
        this.UpdateConsoleAnimationMap();
        super.tick();
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buf) {
        if (this.size != null) {
            buf.writeDouble(this.size.getXsize());
            buf.writeDouble(this.size.getYsize());
            buf.writeDouble(this.size.getZsize());
        }
        if (this.GetControl() != null) buf.writeInt(this.GetControl().ordinal());
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag Tag) {
        Tag.putString("control", this.GetControl().name());
        Tag.putDouble("maxX", this.size.maxX);
        Tag.putDouble("maxY", this.size.maxY);
        Tag.putDouble("maxZ", this.size.maxZ);
        if (this.consoleTile != null) {
            Tag.putInt("console_x", this.consoleTile.getBlockPos().getX());
            Tag.putInt("console_y", this.consoleTile.getBlockPos().getY());
            Tag.putInt("console_z", this.consoleTile.getBlockPos().getZ());
        }
        Tag.putDouble("vec_x", this.Position.x);
        Tag.putDouble("vec_y", this.Position.y);
        Tag.putDouble("vec_z", this.Position.z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(CONTROL, Controls.EMPTY.name());
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag Tag) {
        this.SetControl(Controls.valueOf(Tag.getString("control")));
        this.size = new AABB(0, 0, 0, Tag.getDouble("maxX"), Tag.getDouble("maxY"), Tag.getDouble("maxZ"));
        if (this.level()
                        .getServer()
                        .getLevel(this.level().dimension())
                        .getBlockEntity(
                                new BlockPos(Tag.getInt("console_x"), Tag.getInt("console_y"), Tag.getInt("console_z")))
                != null)
            this.consoleTile = (AbstractConsoleTile) this.level()
                    .getServer()
                    .getLevel(this.level().dimension())
                    .getBlockEntity(
                            new BlockPos(Tag.getInt("console_x"), Tag.getInt("console_y"), Tag.getInt("console_z")));
        this.Position = new Vec3(Tag.getDouble("vecX"), Tag.getDouble("vecY"), Tag.getDouble("vecZ"));
    }

    void CycleControlBackward() {
        this.SetControl(this.GetControl().Cycle());
    }

    void CycleControlForward() {
        this.SetControl(this.GetControl().Cycle());
    }
}
