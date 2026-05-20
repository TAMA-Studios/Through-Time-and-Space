/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.entities.controls;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.List;

import com.code.tama.tts.core.registries.forge.TTSItems;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.tardis.control_lists.RotatedHitboxUtil;
import org.jetbrains.annotations.NotNull;

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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import com.code.tama.triggerapi.ReflectionBuddy;

public abstract class AbstractControlEntity extends Entity {

	/**
	 * How many AABB slices to use per control. 3 is fine for small controls, bump
	 * to 4 for larger ones. TODO: Figure out how to integrate this into the BB
	 * plugin
	 **/
	protected int hitboxSlices() {
		return this.getYRot() == 0 ? 1 : 5;
	}

	public AbstractControlEntity(EntityType<?> entity, Level level) {
		super(entity, level);
		this.setNoGravity(true);
	}

	public void onTwineInteract(Player player) {
		TARDISLevelCapability.GetTARDISCapSupplier(this.level()).ifPresent(cap -> {
			boolean flag = player.getMainHandItem().getItem().equals(TTSItems.TWINE_SPOOL.get())
					&& player.getOffhandItem().getItem().equals(TTSItems.TWINE_SPOOL.get());
			if (flag)
				this.OnControlClicked(cap, player);
			else
				this.OnControlHit(cap, player);
		});
	}

	// ------------------------------------------------------------------
	// Hitbox
	// ------------------------------------------------------------------

	/**
	 * Local-space AABB slices for this control (centered on origin, before entity
	 * position is applied). Automatically uses the entity's yRot and the base AABB
	 * from getLocalAABB(). Override hitboxSlices() in subclasses if a specific
	 * control needs more precision.
	 */
	public List<AABB> getLocalHitboxSlices() {
		AABB base = getLocalAABB();
		if (base == null)
			return List.of();
		return RotatedHitboxUtil.makeSlices(base.getXsize() / 2.0, base.minY, base.maxY, // pass Y bounds directly so
																							// slices honour 0-based Y
				base.getZsize() / 2.0, this.getYRot(), hitboxSlices());
	}

	/**
	 * The "master" bounding box is the union of all slices. Minecraft uses this for
	 * broad-phase collision and rendering culling.
	 */
	@Override
	protected @NotNull AABB makeBoundingBox() {
		List<AABB> slices = getLocalHitboxSlices();
		// System.out
		// .println("makeBoundingBox id=" + this.getId() + " yrot=" + this.getYRot() + "
		// slices=" + slices.size());
		// for (AABB s : slices) {
		// System.out.println(" slice: " + s);
		// }

		if (slices.isEmpty())
			return super.makeBoundingBox();

		Vec3 pos = this.position();
		AABB union = slices.get(0).move(pos);
		for (int i = 1; i < slices.size(); i++) {
			union = union.minmax(slices.get(i).move(pos));
		}
		return union;
	}

	/** Called when this control is clicked (Right Click). */
	public abstract void OnControlClicked(ITARDISLevel capability, Player player);

	/** Called when this control is hit (Left Click). */
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

	/**
	 * The un-rotated local AABB for this control, centered on origin. Subclasses
	 * return their shape here; rotation is handled automatically via getYRot().
	 */
	public abstract AABB getLocalAABB();

	/**
	 * @deprecated Use getLocalAABB() — kept so any subclasses that override
	 *             getAABB() still compile.
	 */
	@Deprecated
	public AABB getAABB() {
		return getLocalAABB();
	}

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
		GetTARDISCapSupplier(source.getEntity().level()).ifPresent(c -> this.OnControlHit(c, source.getEntity()));
		return false;
	}

	@Override
	public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
		GetTARDISCapSupplier(player.level()).ifPresent(cap -> this.OnControlClicked(cap, player));
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean isPickable() {
		return true;
	}
}