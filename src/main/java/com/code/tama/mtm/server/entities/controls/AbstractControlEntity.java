package com.code.tama.mtm.server.entities.controls;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.networking.packets.entities.ControlClickedPacket;
import com.code.tama.mtm.server.networking.packets.entities.ControlHitPacket;


import com.code.tama.triggerapi.ReflectionBuddy;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractControlEntity extends Entity {
    public AbstractControlEntity(EntityType<?> entity, Level level) {
        super(entity, level);
        this.setNoGravity(true);   // Prevent it from falling
    }

    public abstract Component TranslationKey();

    /** Called when this control is clicked (Right Click) **/
    public abstract void OnControlClicked(ITARDISLevel capability, Player player);

    /** Called when this control is hit (Left Click) **/
    public abstract void OnControlHit(ITARDISLevel capability, Entity entity);

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        if(player.level().isClientSide) {
            Networking.sendToServer(new ControlClickedPacket(this.uuid));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(source.getEntity() == null) {
            return false;
        } else {
            source.getEntity().level();
        }

        Networking.sendToServer(new ControlHitPacket(this.uuid));
        source.getEntity().level().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(c -> this.OnControlHit(c, source.getEntity()));
        return false;
    }

    public abstract AABB getAABB();

    @Override
    protected @NotNull AABB makeBoundingBox() {
        return this.getAABB() != null ? this.getAABB().move(this.position()) : super.makeBoundingBox();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void SetDimensions(EntityDimensions t) {
        ReflectionBuddy.Entity.dimensions.apply(this).scale(t.height, t.width);
        this.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return ReflectionBuddy.Entity.dimensions.apply(this);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
}