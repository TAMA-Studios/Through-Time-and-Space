/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.entities.controls;

import com.code.tama.triggerapi.ReflectionBuddy;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
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

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public abstract class AbstractControlEntity extends Entity {
	public AbstractControlEntity(EntityType<?> entity, Level level) {
		super(entity, level);
		this.setNoGravity(true); // Prevent it from falling
	}

	@Override
	protected @NotNull AABB makeBoundingBox() {
		return this.getAABB() != null ? this.getAABB().move(this.position()) : super.makeBoundingBox();
	}

	/** Called when this control is clicked (Right Click) * */
	public abstract void OnControlClicked(ITARDISLevel capability, Player player);

	/** Called when this control is hit (Left Click) * */
	public abstract void OnControlHit(ITARDISLevel capability, Entity entity);

	public void SetDimensions(EntityDimensions t) {
		ReflectionBuddy.Entity.dimensions.apply(this).scale(t.height, t.width);
		this.refreshDimensions();
	}

	public abstract Component TranslationKey();

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public abstract AABB getAABB();

	@Override
	public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
		return ReflectionBuddy.Entity.dimensions.apply(this);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getEntity() == null) {
			return false;
		} else {
			source.getEntity().level();
		}


//		Networking.sendToServer(new ControlHitPacketC2S(this.uuid));
		GetTARDISCapSupplier(source.getEntity().level())
				.ifPresent(c -> this.OnControlHit(c, source.getEntity()));
		return false;
	}

	@Override
	public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
//		if (player.level().isClientSide) {
//			Networking.sendToServer(new ControlClickedPacketC2S(this.uuid));
//		}

		GetTARDISCapSupplier(player.level())
				.ifPresent(cap -> this.OnControlClicked(cap, player));
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean isPickable() {
		return true;
	}
}
