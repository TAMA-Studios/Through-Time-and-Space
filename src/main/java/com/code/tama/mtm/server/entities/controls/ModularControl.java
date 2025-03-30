package com.code.tama.mtm.server.entities.controls;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.server.MTMEntities;
import com.code.tama.mtm.server.enums.Controls;
import com.code.tama.mtm.server.MTMItems;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.networking.packets.entities.SyncButtonAnimationSetPacket;
import com.code.tama.mtm.server.tileentities.AbstractConsoleTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
    public ModularControl(Level p_19871_, AbstractConsoleTile consoleTile, Vec3 pos, float SizeX, float SizeY, float SizeZ) {
        super(MTMEntities.MODULAR_CONTROL.get(), p_19871_);
        this.Position = pos;
        this.size = new AABB(0, 0, 0, SizeX, SizeY, SizeZ);
        this.SetDimensions(EntityDimensions.scalable(SizeX, SizeY));
        this.consoleTile = consoleTile;
    }

    public AbstractConsoleTile consoleTile;
    public AABB size;
    public Vec3 Position;

    Controls control = Controls.EMPTY;

    public ModularControl(EntityType<ModularControl> modularControlEntityType, Level level) {
        super(modularControlEntityType, level);
    }

    @Override
    public Component TranslationKey() {
        return Component.translatable("mtm.tardis.control." + this.control.name().toLowerCase());
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag Tag) {
        this.control = Controls.values()[Tag.getInt("control")];
        this.size = new AABB(0, 0, 0, Tag.getDouble("maxX"), Tag.getDouble("maxY"), Tag.getDouble("maxZ"));
        if (this.level().getServer().getLevel(this.level().dimension()).getBlockEntity(
                new BlockPos(Tag.getInt("console_x"), Tag.getInt("console_y"), Tag.getInt("console_z"))) != null)
            this.consoleTile = (AbstractConsoleTile) this.level().getServer().getLevel(this.level().dimension()).getBlockEntity(
                    new BlockPos(Tag.getInt("console_x"), Tag.getInt("console_y"), Tag.getInt("console_z")));
        this.Position = new Vec3(Tag.getDouble("vecX"), Tag.getDouble("vecY"), Tag.getDouble("vecZ"));
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag Tag) {
        Tag.putInt("control", this.control.ordinal());
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
    public void OnControlClicked(ITARDISLevel capability, Player player) {
        if (player.getUsedItemHand() == InteractionHand.OFF_HAND) return;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MTMItems.SONIC_SCREWDRIVER.get())) {
            if (player.isCrouching())
                this.CycleControlBackward();
            else this.CycleControlForward();
            return;
        }
        InteractionResult interactionResult = this.control.GetControl().OnRightClick(capability, player);
        this.level().playSound(null, this.blockPosition(),
                interactionResult == InteractionResult.SUCCESS ?
                        this.control.GetControl().GetSuccessSound() :
                        this.control.GetControl().GetFailSound(), SoundSource.BLOCKS);

        if (this.control.GetControl().NeedsUpdate() && !this.level().isClientSide) {
            Networking.sendPacketToDimension(this.level().dimension(), new SyncButtonAnimationSetPacket(this.consoleTile.ControlAnimationMap, this.consoleTile.getBlockPos()));
        }
    }

    @Override
    public void OnControlHit(ITARDISLevel capability, Entity entity) {
        if (entity instanceof Player player)
            if (player.getUsedItemHand() == InteractionHand.OFF_HAND) return;
        this.control.GetControl().OnLeftClick(capability, entity);

        if (this.control.GetControl().NeedsUpdate() && !this.level().isClientSide) {
        }
    }

    @Override
    public AABB getAABB() {
        return this.size;
    }

    void CycleControlForward() {
        this.control = this.control.Cycle();
        if (this.level().isClientSide()) {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Control set to: " + this.control.name().toLowerCase()));
        }
    }

    void CycleControlBackward() {
        this.control = this.control.Cycle();
        if (this.level().isClientSide()) {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Control set to: " + this.control.name().toLowerCase()));
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buf) {
        if (this.size != null) {
            buf.writeDouble(this.size.getXsize());
            buf.writeDouble(this.size.getYsize());
            buf.writeDouble(this.size.getZsize());
        }
        if (this.control != null)
            buf.writeInt(this.control.ordinal());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buf) {
        this.size = new AABB(0, 0, 0, buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.control = Controls.values()[buf.readInt()];
    }

    @Override
    public void tick() {
        this.UpdateConsoleAnimationMap();
        super.tick();
    }

    public void UpdateConsoleAnimationMap() {
        if (this.consoleTile == null) return;
        if (!this.control.GetControl().NeedsUpdate()) return;
        if (!this.consoleTile.GetControlAnimationMap().containsKey(this.Position)) return;
        if (this.consoleTile.GetControlAnimationMap().get(this.Position) == this.control.GetControl().GetAnimationState())
            return;
        this.consoleTile.GetControlAnimationMap().remove(this.Position);
        this.consoleTile.GetControlAnimationMap().put(this.Position, this.control.GetControl().GetAnimationState());
    }
}