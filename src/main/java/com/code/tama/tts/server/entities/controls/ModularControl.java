/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.entities.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncButtonAnimationSetPacketS2C;
import com.code.tama.tts.server.registries.forge.TTSEntities;
import com.code.tama.tts.server.registries.tardis.ControlsRegistry;
import com.code.tama.tts.server.tardis.control_lists.ControlEntityRecord;
import com.code.tama.tts.server.tardis.controls.AbstractControl;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.RegistryObject;

import com.code.tama.triggerapi.helpers.world.BlockUtils;

public class ModularControl extends AbstractControlEntity implements IEntityAdditionalSpawnData {
	private static final EntityDataAccessor<Integer> CONTROL = SynchedEntityData.defineId(ModularControl.class,
			EntityDataSerializers.INT);

	private static final EntityDataAccessor<Integer> IDENTIFIER = SynchedEntityData.defineId(ModularControl.class,
			EntityDataSerializers.INT);

	public Vec3 Position;
	public AbstractConsoleTile consoleTile;
	private BlockPos consolePos;
	public AABB size;

	/**
	 * DO NOT CALL THIS! Use
	 * {@code ModularControl(Level, AbstractConsoleTile, ControlEntityRecord)}*
	 */
	@ApiStatus.Internal
	public ModularControl(EntityType<ModularControl> modularControlEntityType, Level level) {
		super(modularControlEntityType, level);
	}

	public ModularControl(Level level, AbstractConsoleTile consoleTile, ControlEntityRecord record) {
		super(TTSEntities.MODULAR_CONTROL.get(), level);
		assert consoleTile.getLevel() != null;
		this.consolePos = consoleTile.getBlockPos();
		float offs;
		if (level.getBlockState(consoleTile.getBlockPos().below()).getBlock() instanceof SnowLayerBlock)
			offs = -1;
		else
			offs = BlockUtils
					.getReverseHeightModifier(consoleTile.getLevel().getBlockState(consoleTile.getBlockPos().below()));
		this.Position = new Vec3(record.minX(), record.minY() - offs, record.minZ());
		double Y = record.maxY() - offs;
		this.size = new AABB(0, 0 - offs, 0, record.maxX(), Y, record.maxZ());
		this.SetDimensions(EntityDimensions.scalable(record.maxX(), (float) Y));
		this.consoleTile = consoleTile;
		this.SetIdentifier(record.ID());
	}

	@Override
	protected void addAdditionalSaveData(@NotNull CompoundTag Tag) {
		Tag.putDouble("aabb_min_x", this.size.minX);
		Tag.putDouble("aabb_min_y", this.size.minY);
		Tag.putDouble("aabb_min_z", this.size.minZ);
		Tag.putDouble("aabb_max_x", this.size.maxX);
		Tag.putDouble("aabb_max_y", this.size.maxY);
		Tag.putDouble("aabb_max_z", this.size.maxZ);

		Tag.putDouble("pos_x", this.Position.x);
		Tag.putDouble("pos_y", this.Position.y);
		Tag.putDouble("pos_z", this.Position.z);

		Tag.putInt("control", ControlsRegistry.getOrdinal(this.GetControl()));

		if (this.consoleTile != null) {
			Tag.putInt("console_x", this.consoleTile.getBlockPos().getX());
			Tag.putInt("console_y", this.consoleTile.getBlockPos().getY());
			Tag.putInt("console_z", this.consoleTile.getBlockPos().getZ());
		}
		Tag.putDouble("vec_x", this.Position.x);
		Tag.putDouble("vec_y", this.Position.y);
		Tag.putDouble("vec_z", this.Position.z);
		Tag.putInt("identifier", this.Identifier());
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(CONTROL, 0);
		this.entityData.define(IDENTIFIER, 0);
	}

	@Override
	protected void readAdditionalSaveData(@NotNull CompoundTag Tag) {
		this.SetControl(ControlsRegistry.getFromOrdinal(Tag.getInt("control")).get());

		double minX = Tag.getDouble("aabb_min_x");
		double minY = Tag.getDouble("aabb_min_y");
		double minZ = Tag.getDouble("aabb_min_z");
		double maxX = Tag.getDouble("aabb_max_x");
		double maxY = Tag.getDouble("aabb_max_y");
		double maxZ = Tag.getDouble("aabb_max_z");

		this.size = new AABB(minX, minY, minZ, maxX, maxY, maxZ);

		this.Position = new Vec3(Tag.getDouble("pos_x"), Tag.getDouble("pos_y"), Tag.getDouble("pos_z"));

		if (this.level().getServer() != null) {
			if (this.level().getServer().getLevel(this.level().dimension()).getBlockEntity(
					new BlockPos(Tag.getInt("console_x"), Tag.getInt("console_y"), Tag.getInt("console_z"))) != null)
				this.consoleTile = (AbstractConsoleTile) this.level().getServer().getLevel(this.level().dimension())
						.getBlockEntity(new BlockPos(Tag.getInt("console_x"), Tag.getInt("console_y"),
								Tag.getInt("console_z")));
		}
		this.Position = new Vec3(Tag.getDouble("vecX"), Tag.getDouble("vecY"), Tag.getDouble("vecZ"));

		this.SetIdentifier(Tag.getInt("identifier"));

		this.GetControl().SetNeedsUpdate(true);
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

	@Override
	public void OnControlClicked(ITARDISLevel capability, Player player) {
		if (player.getMainHandItem() != Items.AIR.getDefaultInstance()) {
			ItemStack stack = player.getMainHandItem();
			CompoundTag tag = stack.getOrCreateTag();
			tag.putInt("BoundEntityId", this.getId());
			tag.putUUID("BoundEntityUUID", this.getUUID());
			player.displayClientMessage(net.minecraft.network.chat.Component.literal("Twine tied to control!"), true);
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
	public AABB getAABB() {
		return this.size;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void readSpawnData(FriendlyByteBuf buf) {
		this.Position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
		this.size = new AABB(0, 0, 0, buf.readDouble(), buf.readDouble(), buf.readDouble());
		this.SetControl(
				((RegistryObject<AbstractControl>) ControlsRegistry.CONTROLS.getEntries().toArray()[buf.readInt()])
						.get());
		this.SetIdentifier(buf.readInt());
	}

	@Override
	public void tick() {
		if (this.consoleTile != null && this.GetControl().NeedsUpdate())
			this.UpdateConsoleAnimationMap();
		super.tick();
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buf) {
		buf.writeDouble(this.Position.x);
		buf.writeDouble(this.Position.y);
		buf.writeDouble(this.Position.z);

		buf.writeDouble(this.size.getXsize());
		buf.writeDouble(this.size.getYsize());
		buf.writeDouble(this.size.getZsize());

		if (this.GetControl() != null)
			buf.writeInt(ControlsRegistry.getOrdinal(this.GetControl()));
		buf.writeInt(this.Identifier());
	}

	public AbstractConsoleTile getConsoleTile() {
		return (AbstractConsoleTile) this.level().getBlockEntity(this.consolePos);
	}
}
