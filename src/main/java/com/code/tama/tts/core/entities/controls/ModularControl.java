/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.entities.controls;

import com.code.tama.tts.core.items.gadgets.SonicItem;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.S2C.entities.SyncButtonAnimationSetPacketS2C;
import com.code.tama.tts.core.registries.forge.TTSEntities;
import com.code.tama.tts.core.registries.forge.TTSItems;
import com.code.tama.tts.core.registries.tardis.ControlsRegistry;
import com.code.tama.tts.core.tileentities.consoles.AbstractConsoleTile;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.tardis.control_lists.ControlEntityRecord;
import com.code.tama.tts.server.tardis.control_lists.RotatedHitboxUtil;
import com.code.tama.tts.server.tardis.controls.AbstractControl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.RegistryObject;

public class ModularControl extends AbstractControlEntity implements IEntityAdditionalSpawnData {
	private static final EntityDataAccessor<Integer> CONTROL = SynchedEntityData.defineId(ModularControl.class,
			EntityDataSerializers.INT);

	private static final EntityDataAccessor<Integer> IDENTIFIER = SynchedEntityData.defineId(ModularControl.class,
			EntityDataSerializers.INT);

	public Vec3 Position;
	public AbstractConsoleTile consoleTile;
	private BlockPos consolePos;

	/**
	 * Local-space AABB centered on origin (no rotation applied). Rotation is
	 * handled by AbstractControlEntity.makeBoundingBox() via getYRot().
	 */
	public AABB size;

	/**
	 * DO NOT CALL THIS! Use
	 * {@code ModularControl(Level, AbstractConsoleTile, ControlEntityRecord)}
	 */
	@ApiStatus.Internal
	public ModularControl(EntityType<ModularControl> modularControlEntityType, Level level) {
		super(modularControlEntityType, level);
	}

	public ModularControl(Level level, AbstractConsoleTile consoleTile, ControlEntityRecord record) {

		super(TTSEntities.MODULAR_CONTROL.get(), level);

		System.out.println("Record " + record.ID() + " cx=" + record.cx() + " cy=" + record.cy() + " cz=" + record.cz()
				+ " hw=" + record.hw() + " hh=" + record.hh() + " hd=" + record.hd() + " yaw=" + record.yawDeg());
		assert consoleTile.getLevel() != null;
		this.consolePos = consoleTile.getBlockPos();

		// Position is purely for NBT storage — actual spawn position is set by
		// summonButtons
		// via entity.setPos(), which already applies the offs correction. Don't touch
		// offs here.
		this.Position = new Vec3(record.cx(), record.cy(), record.cz());

		// X/Z are centered on origin (entity position is the horizontal center of the
		// control).
		// Y starts at 0 and goes up — entity position is the bottom of the control,
		// matching
		// the old min/max corner behavior so position + size stay in sync.
		float fullH = record.hh() * 2f;
		this.size = RotatedHitboxUtil.rotatedTightAABB(record.hw(), record.hh(), record.hd(), record.yawDeg());
		this.SetDimensions(EntityDimensions.scalable((float) (this.size.getXsize()), (float) (this.size.getYsize())));

		this.SetDimensions(EntityDimensions.scalable(record.hw() * 2f, fullH));
		this.consoleTile = consoleTile;
		this.SetIdentifier(record.ID());

		this.setYRot(record.yawDeg());
		this.yRotO = record.yawDeg();
	}

	// ------------------------------------------------------------------
	// Hitbox
	// ------------------------------------------------------------------

	@Override
	public AABB getLocalAABB() {
		return this.size;
	}

	/** @deprecated Kept for any external callers — delegates to getLocalAABB(). */
	@Deprecated
	@Override
	public AABB getAABB() {
		return getLocalAABB();
	}

	// ------------------------------------------------------------------
	// Save / Load
	// ------------------------------------------------------------------

	@Override
	protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
		// Store as half-extents so we can reconstruct the centered AABB on load
		tag.putDouble("aabb_hw", this.size.getXsize() / 2.0);
		tag.putDouble("aabb_hh", this.size.getYsize() / 2.0);
		tag.putDouble("aabb_hd", this.size.getZsize() / 2.0);

		tag.putDouble("pos_x", this.Position.x);
		tag.putDouble("pos_y", this.Position.y);
		tag.putDouble("pos_z", this.Position.z);

		tag.putFloat("yaw", this.getYRot());

		tag.putInt("control", ControlsRegistry.getOrdinal(this.GetControl()));

		if (this.consoleTile != null) {
			tag.putInt("console_x", this.consoleTile.getBlockPos().getX());
			tag.putInt("console_y", this.consoleTile.getBlockPos().getY());
			tag.putInt("console_z", this.consoleTile.getBlockPos().getZ());
		}

		tag.putInt("identifier", this.Identifier());
	}

	@Override
	protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
		this.SetControl(ControlsRegistry.getFromOrdinal(tag.getInt("control")).get());

		double hw = tag.getDouble("aabb_hw");
		double hh = tag.getDouble("aabb_hh");
		double hd = tag.getDouble("aabb_hd");
		this.size = new AABB(-hw, -hh, -hd, hw, hh, hd);

		this.Position = new Vec3(tag.getDouble("pos_x"), tag.getDouble("pos_y"), tag.getDouble("pos_z"));

		float yaw = tag.getFloat("yaw");
		this.setYRot(yaw);
		this.yRotO = yaw;

		if (this.level().getServer() != null) {
			BlockPos consoleBlockPos = new BlockPos(tag.getInt("console_x"), tag.getInt("console_y"),
					tag.getInt("console_z"));
			var be = this.level().getServer().getLevel(this.level().dimension()).getBlockEntity(consoleBlockPos);
			if (be instanceof AbstractConsoleTile tile)
				this.consoleTile = tile;
			this.consolePos = consoleBlockPos;
		}

		this.SetIdentifier(tag.getInt("identifier"));
		this.GetControl().SetNeedsUpdate(true);
	}

	// ------------------------------------------------------------------
	// Spawn data (client sync)
	// ------------------------------------------------------------------

	@Override
	public void writeSpawnData(FriendlyByteBuf buf) {
		buf.writeDouble(this.Position.x);
		buf.writeDouble(this.Position.y);
		buf.writeDouble(this.Position.z);

		// Half-extents
		buf.writeDouble(this.size.getXsize() / 2.0);
		buf.writeDouble(this.size.getYsize() / 2.0);
		buf.writeDouble(this.size.getZsize() / 2.0);

		buf.writeFloat(this.getYRot());

		buf.writeInt(this.GetControl() != null ? ControlsRegistry.getOrdinal(this.GetControl()) : 0);
		buf.writeInt(this.Identifier());
	}

	@Override
	public void readSpawnData(FriendlyByteBuf buf) {
		this.Position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());

		double hw = buf.readDouble();
		double hh = buf.readDouble();
		double hd = buf.readDouble();
		this.size = new AABB(-hw, 0, -hd, hw, hh * 2, hd);

		float yaw = buf.readFloat();
		this.setYRot(yaw);
		this.yRotO = yaw;

		this.SetControl(
				((RegistryObject<AbstractControl>) ControlsRegistry.CONTROLS.getEntries().toArray()[buf.readInt()])
						.get());
		this.SetIdentifier(buf.readInt());

		// Force bounding box rebuild now that yaw and size are both set
		this.setBoundingBox(this.makeBoundingBox());
	}

	// ------------------------------------------------------------------
	// Interactions
	// ------------------------------------------------------------------

	@Override
	public void OnControlClicked(ITARDISLevel capability, Player player) {
		if (player.getMainHandItem().getItem().equals(TTSItems.TWINE_SPOOL.get())) {
			ItemStack stack = player.getMainHandItem();
			CompoundTag tag = stack.getOrCreateTag();
			tag.putInt("BoundEntityId", this.getId());
			tag.putUUID("BoundEntityUUID", this.getUUID());
			player.displayClientMessage(Component.literal("Twine tied to control!"), true);
			return;
		}

		if (player.getUsedItemHand() == InteractionHand.OFF_HAND)
			return;

		if (capability.getCurrentFlightEvent().RequiredControls.contains(this.GetControl().id())) {
			capability.getCurrentFlightEvent().RequiredControls.remove(this.GetControl().id());
			capability.GetLevel().playLocalSound(this.blockPosition(), SoundEvents.NOTE_BLOCK_BIT.get(),
					SoundSource.BLOCKS, 1f, 1f, true);
			return;
		}

		if (player.getMainHandItem().getItem() instanceof SonicItem) {
			if (player.isCrouching()) {
				this.CycleControlBackward();
				player.sendSystemMessage(
						Component.literal("Control set to: " + this.GetControl().name().toLowerCase()));
			} else {
				this.CycleControlForward();
				player.sendSystemMessage(
						Component.literal("Control set to: " + this.GetControl().name().toLowerCase()));
			}
			return;
		}

		InteractionResult interactionResult = this.GetControl().OnRightClick(capability, player);

		this.level().playSound(player, this.blockPosition(),
				interactionResult == InteractionResult.SUCCESS
						? this.GetControl().GetSuccessSound()
						: this.GetControl().GetFailSound(),
				SoundSource.BLOCKS);

		if (this.GetControl().NeedsUpdate() && !this.level().isClientSide) {
			Networking.sendPacketToDimension(this.level().dimension(), new SyncButtonAnimationSetPacketS2C(
					this.consoleTile.ControlAnimationMap, this.consoleTile.getBlockPos()));
		}
	}

	@Override
	public void OnControlHit(ITARDISLevel capability, Entity entity) {
		if (entity instanceof Player player && player.getUsedItemHand() == InteractionHand.OFF_HAND)
			return;

		if (capability.getCurrentFlightEvent().RequiredControls.contains(this.GetControl().id())) {
			capability.getCurrentFlightEvent().RequiredControls.remove(this.GetControl().id());
			capability.GetLevel().playLocalSound(this.blockPosition(), SoundEvents.NOTE_BLOCK_BIT.get(),
					SoundSource.BLOCKS, 1f, 1f, true);
			return;
		}

		InteractionResult interactionResult = this.GetControl().OnLeftClick(capability, entity);

		if (entity instanceof Player player)
			this.level().playSound(player, this.blockPosition(),
					interactionResult == InteractionResult.SUCCESS
							? this.GetControl().GetSuccessSound()
							: this.GetControl().GetFailSound(),
					SoundSource.BLOCKS);
		else
			this.level().playSound(null, this.blockPosition(),
					interactionResult == InteractionResult.SUCCESS
							? this.GetControl().GetSuccessSound()
							: this.GetControl().GetFailSound(),
					SoundSource.BLOCKS);

		if (this.GetControl().NeedsUpdate() && !this.level().isClientSide) {
			System.out.println("Needs Update!");
			this.UpdateConsoleAnimationMap();
		}
	}

	// ------------------------------------------------------------------
	// Misc
	// ------------------------------------------------------------------

	@Override
	protected void defineSynchedData() {
		this.entityData.define(CONTROL, 0);
		this.entityData.define(IDENTIFIER, 0);
	}

	void CycleControlBackward() {
		this.SetControl(ControlsRegistry.CycleBackwards(this.GetControl()));
	}

	void CycleControlForward() {
		this.SetControl(ControlsRegistry.Cycle(this.GetControl()));
	}

	@SuppressWarnings("unchecked")
	public AbstractControl GetControl() {
		return ((RegistryObject<AbstractControl>) ControlsRegistry.CONTROLS.getEntries().toArray()[this.entityData
				.get(CONTROL)]).get();
	}

	public int Identifier() {
		return this.entityData.get(IDENTIFIER);
	}

	public void SetControl(AbstractControl control) {
		this.entityData.set(CONTROL, ControlsRegistry.getOrdinal(control));
	}

	public void SetIdentifier(int id) {
		this.entityData.set(IDENTIFIER, id);
	}

	@Override
	public Component TranslationKey() {
		MutableComponent component = Component.translatable(this.GetControl().getTranslationKey());
		if (Minecraft.getInstance().options.advancedItemTooltips)
			component.append(String.format("ID: %s", this.Identifier()));
		return component;
	}

	public void UpdateConsoleAnimationMap() {
		if (this.consoleTile.GetControlAnimationMap().containsKey(this.Identifier()))
			this.consoleTile.GetControlAnimationMap().remove(this.Identifier());
		this.consoleTile.GetControlAnimationMap().put(this.Identifier(), this.GetControl().GetAnimationState());

		if (!this.level().isClientSide)
			Networking.sendPacketToDimension(this.level().dimension(), new SyncButtonAnimationSetPacketS2C(
					this.consoleTile.ControlAnimationMap, this.consoleTile.getBlockPos()));
		this.GetControl().SetNeedsUpdate(false);
	}

	@Override
	public void tick() {
		if (this.consoleTile != null && this.GetControl().NeedsUpdate())
			this.UpdateConsoleAnimationMap();
		super.tick();
		// if (!this.level().isClientSide) {
		// AABB bb = this.getBoundingBox();
		// System.out.println(
		// "Control " + this.Identifier() + " pos=" + this.position() + " bb=" + bb.minX
		// + "," + bb.minY + ","
		// + bb.minZ + " -> " + bb.maxX + "," + bb.maxY + "," + bb.maxZ + " size=" +
		// this.size);
		// }
		if (this.consoleTile != null && this.GetControl().NeedsUpdate())
			this.UpdateConsoleAnimationMap();
		super.tick();
	}

	public AbstractConsoleTile getConsoleTile() {
		return (AbstractConsoleTile) this.level().getBlockEntity(this.consolePos);
	}
}