/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.entities;

import javax.annotation.Nullable;

import com.code.tama.tts.core.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSEntities;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tardis.exteriorViewing.EnvironmentViewerUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * A real, physical, rideable form of a TARDIS' {@link ExteriorTile}, used for
 * "Spatial Flight" / "Real World Flight". A landed exterior is snapshotted,
 * turned into one of these, ridden and flown freely by a player, then
 * converted back into a block + {@link ExteriorTile} (with the original data
 * restored) when the pilot lands it.
 *
 * <p>This intentionally does <b>not</b> extend {@link FallingExteriorEntity}
 * - that class does its own hand-rolled sand-physics style gravity/collision
 * and was never fully wired up to restore block entity data on landing. This
 * entity is built on {@link Mob} instead so we get the vanilla
 * rider-control plumbing (the controlling player's WASD + look rotation
 * sync to the server automatically via the normal player input packet -
 * no custom networking needed for basic flight).</p>
 */
public class TardisFlightEntity extends Mob {

	// ---- Synced, render-relevant snapshot of the ExteriorTile we came from ----
	private static final EntityDataAccessor<String> MODEL_NAMESPACE = SynchedEntityData
			.defineId(TardisFlightEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<String> MODEL_PATH = SynchedEntityData
			.defineId(TardisFlightEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Integer> FACING = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DOORS_OPEN = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> TRANSPARENCY = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.FLOAT);

	/** Blocks/tick at full forward input. Tune to taste. */
	private static final double FLIGHT_SPEED = 0.6D;
	/** Extra downward bias applied while the rider holds shift, on top of pitch-based descent. */
	private static final double DESCEND_BIAS = 0.15D;

	/** Full NBT snapshot of the {@link ExteriorTile} this was converted from, restored verbatim on landing. */
	@Nullable public CompoundTag exteriorData;

	/**
	 * Set to true for the brief window around our own {@link #Land(ServerPlayer)}
	 * call to {@code player.stopRiding()}, so the {@code EntityMountEvent}
	 * handler can tell "we intentionally landed" apart from "the player got
	 * bumped off by an incidental sneak press" and only cancel the latter.
	 */
	public volatile boolean allowDismount = false;

	/** The exact block state (including FACING) to place back down when this lands. */
	private BlockState blockState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();

	public TardisFlightEntity(EntityType<? extends TardisFlightEntity> type, Level level) {
		super(type, level);
		this.setNoGravity(true);
		this.setPersistenceRequired();
		this.noCulling = true;
	}

	/** Registration helper - hand this to your EntityType.Builder / attribute registry event. */
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.MOVEMENT_SPEED, 0.0D)
				.add(Attributes.FLYING_SPEED, (float) FLIGHT_SPEED);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MODEL_NAMESPACE, "tts");
		this.entityData.define(MODEL_PATH, "police_box");
		this.entityData.define(FACING, Direction.NORTH.get3DDataValue());
		this.entityData.define(DOORS_OPEN, 0);
		this.entityData.define(TRANSPARENCY, 1.0F);
	}

	@Override
	protected void registerGoals() {
		// Intentionally empty. This entity is never AI-driven, only ever ridden.
	}

	// =====================================================================
	// Conversion: ExteriorTile -> TardisFlightEntity
	// =====================================================================

	/**
	 * Snapshots the given {@link ExteriorTile}, removes its block + block entity
	 * from the world, and spawns a rideable {@link TardisFlightEntity} in its
	 * place. Does NOT mount anyone on it or touch player state - that's the
	 * calling control's job (see {@code SpatialFlightControl}).
	 */
	public static TardisFlightEntity fromTile(ExteriorTile tile) {
		return fromTile(tile, tile.getLevel());
	}

	/**
	 * Same as {@link #fromTile(ExteriorTile)}, but lets the caller supply the
	 * exterior's {@link Level} explicitly rather than trusting {@code tile.getLevel()}.
	 * Prefer this overload from control code - fetch the level straight from the
	 * TARDIS' {@code SpaceTimeCoordinate} (same source
	 * {@code EnvironmentViewerUtils.startSpectateExt} uses for its own
	 * cross-dimension teleport) so we're never relying on a possibly-stale
	 * {@code ExteriorTile} reference for something as important as "which
	 * dimension is this."
	 */
	public static TardisFlightEntity fromTile(ExteriorTile tile, Level level) {
		assert level != null;

		CompoundTag data = tile.saveWithoutMetadata();
		BlockPos pos = tile.getBlockPos();
		BlockState state = tile.getBlockState();

		level.removeBlockEntity(pos);
		level.removeBlock(pos, false);

		TardisFlightEntity entity = new TardisFlightEntity(TTSEntities.TARDIS_FLIGHT.get(), level);
		entity.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		entity.exteriorData = data;
		entity.blockState = state;

		String modelNamespace = data.contains("modelNamespace") ? data.getString("modelNamespace") : "tts";
		String modelPath = data.contains("modelPath") ? data.getString("modelPath") : "police_box";
		Direction facing = data.contains("facing") ? Direction.byName(data.getString("facing")) : null;

		entity.entityData.set(MODEL_NAMESPACE, modelNamespace);
		entity.entityData.set(MODEL_PATH, modelPath);
		entity.entityData.set(FACING, (facing == null ? Direction.NORTH : facing).get3DDataValue());
		entity.entityData.set(DOORS_OPEN, data.getInt("doorsOpen"));
		entity.entityData.set(TRANSPARENCY, data.contains("Transparency") ? data.getFloat("Transparency") : 1.0F);

		level.addFreshEntity(entity);
		return entity;
	}

	// =====================================================================
	// Riding + flight control
	// =====================================================================

	public boolean canBeControlledByRider() {
		return this.getControllingPassenger() instanceof Player;
	}

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		return this.getFirstPassenger() instanceof Player player ? player : null;
	}

	@Override
	protected void positionRider(Entity passenger, MoveFunction moveFunction) {
		if (this.hasPassenger(passenger)) {
			moveFunction.accept(passenger, this.getX(), this.getY() + this.getPassengersRidingOffset(), this.getZ());
		}
	}

	// NOTE: depending on your exact 1.20.1 mappings this may need to be `float`
	// instead of `double`, and/or `protected` instead of `public` to correctly
	// @Override - adjust to whatever your IDE says Entity actually declares.
	@Override
	public double getPassengersRidingOffset() {
		return this.getBbHeight() * 0.9D;
	}

	@Override
	public void travel(Vec3 travelVector) {
		if (this.canBeControlledByRider() && this.getControllingPassenger() instanceof Player player) {
			// Face wherever the pilot is looking
			this.setYRot(player.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(player.getXRot() * 0.5F);
			this.yBodyRot = this.getYRot();
			this.yHeadRot = this.getYRot();

			float forward = player.zza;
			float strafe = player.xxa * 0.5F;

			// Matches vanilla Entity#getInputVector's relative-movement formula:
			// resultX = strafe*cos(yaw) - forward*sin(yaw)
			// resultZ = forward*cos(yaw) + strafe*sin(yaw)
			// (previous version had the strafe term's sign flipped on both axes,
			// which is what made A/D feel inverted)
			float yawRad = this.getYRot() * Mth.DEG_TO_RAD;
			double moveX = (-Mth.sin(yawRad) * forward + Mth.cos(yawRad) * strafe) * FLIGHT_SPEED;
			double moveZ = (Mth.cos(yawRad) * forward + Mth.sin(yawRad) * strafe) * FLIGHT_SPEED;

			// Pitch controls climb/descend, like flying a plane - scaled so you still
			// get some vertical movement even when hovering (not pushing forward).
			double moveY = -Mth.sin(player.getXRot() * Mth.DEG_TO_RAD) * FLIGHT_SPEED
					* Math.max(Math.abs(forward), 0.3F);

			boolean shifting = player.isShiftKeyDown();
			if (shifting)
				moveY -= DESCEND_BIAS;

			this.setDeltaMovement(moveX, moveY, moveZ);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D)); // gentle air drag so it doesn't drift forever

			// Landing: only while the pilot is actively holding shift AND we're
			// actually touching the ground - bumping terrain mid-flight without
			// shift held does nothing.
			//
			// Deliberately NOT using this.onGround() here - that flag is set by
			// vanilla's own collision resolution based on *falling* into
			// something, which this entity never really does (permanently
			// no-gravity, hand-rolled movement). In practice it reads stale/true
			// far more than it should for a custom-physics vehicle like this, so
			// instead we look directly at whether there's actually a solid block
			// immediately under the hitbox right now.
			if (!this.level().isClientSide && shifting && this.isTouchingGround()
					&& player instanceof ServerPlayer serverPlayer) {
				this.Land(serverPlayer);
			}
		} else {
			// No rider (e.g. abandoned mid-air somehow) - just hold position rather
			// than falling, since we're permanently no-gravity. TODO: consider an
			// auto-landing routine here instead if you want abandoned flights to
			// resolve themselves.
			this.setDeltaMovement(Vec3.ZERO);
		}
	}

	/**
	 * Checks for a solid block collision in a thin slab immediately beneath the
	 * current hitbox, independent of {@link #onGround()}. Used for the landing
	 * trigger instead of the vanilla flag - see the comment at the call site for
	 * why.
	 */
    public boolean isTouchingGround() {
		AABB box = this.getBoundingBox();
		AABB probe = new AABB(box.minX, box.minY - 0.1D, box.minZ, box.maxX, box.minY + 0.05D, box.maxZ);
		return this.level().getBlockCollisions(this, probe).iterator().hasNext();
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false; // Landing is the only intended way this entity goes away
	}

	// =====================================================================
	// Conversion: TardisFlightEntity -> ExteriorTile
	// =====================================================================

	/**
	 * Dismounts the rider, restores their normal visibility/abilities, places the
	 * exterior block back down, restores the saved {@link ExteriorTile} data onto
	 * the freshly-placed block entity, patches up the TARDIS' navigational data,
	 * and removes this entity. Server-side only.
	 */
	public void Land(ServerPlayer player) {
		this.allowDismount = true;

		Level level = this.level();
		if (level.isClientSide)
			return;

		BlockPos landingPos = this.blockPosition();

		player.stopRiding();
		EnvironmentViewerUtils.updatePlayerAbilities(player, player.getAbilities(), false);
		player.setInvisible(false);
		player.onUpdateAbilities();

		level.setBlockAndUpdate(landingPos, this.blockState);

		if (level.getBlockEntity(landingPos) instanceof ExteriorTile tile) {
			if (this.exteriorData != null)
				tile.load(this.exteriorData);

			tile.state = ExteriorState.LANDED;
			tile.setChanged();
			level.sendBlockUpdated(landingPos, level.getBlockState(landingPos), level.getBlockState(landingPos), 3);

			if (tile.GetInterior() != null && level.getServer() != null) {
				ServerLevel interior = level.getServer().getLevel(tile.GetInterior());
				if (interior != null) {
					TARDISLevelCapability.GetTARDISCapSupplier(interior).ifPresent(cap -> {
						cap.SetExteriorTile(tile);
						SpaceTimeCoordinate here = new SpaceTimeCoordinate(landingPos, level.dimension());
						cap.GetNavigationalData().SetExteriorLocation(here);
						cap.GetNavigationalData().setDestination(here);
						cap.GetFlightData().setInFlight(false);
						cap.GetFlightData().setTicksInFlight(0);
					});
				}
			}
		}
		player.stopRiding();

		this.discard();
	}

	// =====================================================================
	// Client-side rendering accessors
	// =====================================================================

	public ResourceLocation getSyncedModel() {
		return new ResourceLocation(this.entityData.get(MODEL_NAMESPACE), this.entityData.get(MODEL_PATH));
	}

	public Direction getSyncedFacing() {
		return Direction.from3DDataValue(this.entityData.get(FACING));
	}

	public int getSyncedDoorsOpen() {
		return this.entityData.get(DOORS_OPEN);
	}

	public float getSyncedTransparency() {
		return this.entityData.get(TRANSPARENCY);
	}
}