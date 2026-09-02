/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.entities;

import javax.annotation.Nullable;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSEntities;
import com.code.tama.tts.core.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tardis.exteriorViewing.EnvironmentViewerUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
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

import com.code.tama.triggerapi.universal.UniversalCommon;

/**
 * A real, physical, rideable form of a TARDIS' {@link ExteriorTile}, used for
 * "Spatial Flight" / "Real World Flight". A landed exterior is snapshotted,
 * turned into one of these, ridden and flown freely by a player, then converted
 * back into a block + {@link ExteriorTile} (with the original data restored)
 * when the pilot lands it.
 *
 * <p>
 * This intentionally does <b>not</b> extend {@link FallingExteriorEntity} -
 * that class does its own hand-rolled sand-physics style gravity/collision and
 * was never fully wired up to restore block entity data on landing. This entity
 * is built on {@link Mob} instead so we get the vanilla rider-control plumbing
 * (the controlling player's WASD + look rotation sync to the server
 * automatically via the normal player input packet - no custom networking
 * needed for basic flight).
 * </p>
 */
public class TardisFlightEntity extends Mob {

	// ---- Synced, render-relevant snapshot of the ExteriorTile we came from ----
	private static final EntityDataAccessor<String> MODEL_NAMESPACE = SynchedEntityData
			.defineId(TardisFlightEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<String> MODEL_PATH = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.STRING);

	private static final EntityDataAccessor<Integer> MODEL_ID = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.INT);

	private static final EntityDataAccessor<Integer> FACING = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DOORS_OPEN = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> TRANSPARENCY = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(TardisFlightEntity.class,
			EntityDataSerializers.FLOAT);

	/** Blocks/tick at full forward input. Tune to taste. */
	private static final double FLIGHT_SPEED = 0.6D;
	/**
	 * Extra downward bias applied while the rider holds shift, on top of
	 * pitch-based descent.
	 */
	private static final double DESCEND_BIAS = 0.15D;

	/**
	 * Full NBT snapshot of the {@link ExteriorTile} this was converted from,
	 * restored verbatim on landing.
	 */
	@Nullable public CompoundTag exteriorData;

	/**
	 * Set to true for the brief window around our own {@link #Land(ServerPlayer)}
	 * call to {@code player.stopRiding()}, so the {@code EntityMountEvent} handler
	 * can tell "we intentionally landed" apart from "the player got bumped off by
	 * an incidental sneak press" and only cancel the latter.
	 */
	public volatile boolean allowDismount = false;

	/**
	 * The exact block state (including FACING) to place back down when this lands.
	 */
	private BlockState blockState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();

	public TardisFlightEntity(EntityType<? extends TardisFlightEntity> type, Level level) {
		super(type, level);
		this.setNoGravity(true);
		this.setPersistenceRequired();
		this.noCulling = true;
	}

	/**
	 * Registration helper - hand this to your EntityType.Builder / attribute
	 * registry event.
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.MOVEMENT_SPEED, 0.0D)
				.add(Attributes.FLYING_SPEED, (float) FLIGHT_SPEED);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MODEL_NAMESPACE, "tts");
		this.entityData.define(MODEL_PATH, "police_box");
		this.entityData.define(MODEL_ID, 1);
		this.entityData.define(FACING, Direction.NORTH.get3DDataValue());
		this.entityData.define(DOORS_OPEN, 0);
		this.entityData.define(TRANSPARENCY, 1.0F);
		this.entityData.define(SPEED, 0.0F);
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
	 * place. Does NOT mount anyone on it or touch player state - that's the calling
	 * control's job (see {@code SpatialFlightControl}).
	 */
	public static TardisFlightEntity fromTile(ExteriorTile tile) {
		return fromTile(tile, tile.getLevel());
	}

	/**
	 * Same as {@link #fromTile(ExteriorTile)}, but lets the caller supply the
	 * exterior's {@link Level} explicitly rather than trusting
	 * {@code tile.getLevel()}. Prefer this overload from control code - fetch the
	 * level straight from the TARDIS' {@code SpaceTimeCoordinate} (same source
	 * {@code EnvironmentViewerUtils.startSpectateExt} uses for its own
	 * cross-dimension teleport) so we're never relying on a possibly-stale
	 * {@code ExteriorTile} reference for something as important as "which dimension
	 * is this."
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

		if (data.contains("model")) {
			ExteriorModelContainer Model = ExteriorModelContainer.CODEC.parse(NbtOps.INSTANCE, data.get("model")).get()
					.orThrow();
			entity.entityData.set(MODEL_ID, ExteriorsRegistry.GetOrdinal(Model));
		}

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

	@Nullable @Override
	public LivingEntity getControllingPassenger() {
		return this.getFirstPassenger() instanceof Player player ? player : null;
	}

	@Override
	protected void positionRider(Entity passenger, MoveFunction moveFunction) {
		if (this.hasPassenger(passenger)) {
			moveFunction.accept(passenger, this.getX(), this.getY(), this.getZ());
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

			this.setYRot(player.getYRot());
			this.setXRot(player.getXRot());
			this.yBodyRot = this.getYRot();
			this.yHeadRot = this.getYRot();

			float forward = player.zza; // Forward / Backward (W/S)
			float strafe = player.xxa; // Left / Right (A/D)

			Vec3 lookVec = this.getLookAngle();
			Vec3 currentVelocity = this.getDeltaMovement();

			double maxSpeed = 7.0D;
			Vec3 targetVelocity = Vec3.ZERO;

			if (forward != 0) {
				targetVelocity = lookVec.scale(forward > 0 ? maxSpeed : -maxSpeed * 0.5D);
			}

			if (strafe != 0) {
				Vec3 sideVec = new Vec3(Math.cos(this.getYRot() * Mth.DEG_TO_RAD), 0,
						Mth.sin(this.getYRot() * Mth.DEG_TO_RAD));
				targetVelocity = targetVelocity.add(sideVec.scale(strafe * 0.5D * (maxSpeed * 0.5D)));
			}

			// Lower decimal = heavier/driftier ship; higher decimal = more responsive
			double accelerationRate = 0.005D;
			Vec3 newVelocity = currentVelocity.lerp(targetVelocity, accelerationRate);

			// 6. Handle shifting / descending / landing
			boolean shifting = player.isShiftKeyDown(); // TODO: Keybind, shifting doesn't work in this use case cause
														// ofc it doesn't.
			if (shifting) {
				newVelocity = newVelocity.add(0, -0.05D, 0); // Descend bias

				if (!this.level().isClientSide && this.isTouchingGround()
						&& player instanceof ServerPlayer serverPlayer) {
					this.Land(serverPlayer);
				}
			}

			// 7. Apply movement
			this.setDeltaMovement(newVelocity);
			this.move(MoverType.SELF, this.getDeltaMovement());

		} else {
			// Hold position if abandoned mid-air
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
			if (this.exteriorData != null) {
				tile.load(this.exteriorData);
			}

			tile.state = ExteriorState.LANDED;
			tile.setChanged();
			tile.UpdateAll();
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
		EnvironmentViewerUtils.stopRWF(player);

		this.discard();
	}

	// =====================================================================
	// Client-side rendering accessors
	// =====================================================================

	public ResourceLocation getSyncedModel() {
		return UniversalCommon.newRL(this.entityData.get(MODEL_NAMESPACE), this.entityData.get(MODEL_PATH));
	}

	public int getSyncedModelID() {
		return this.entityData.get(MODEL_ID);
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