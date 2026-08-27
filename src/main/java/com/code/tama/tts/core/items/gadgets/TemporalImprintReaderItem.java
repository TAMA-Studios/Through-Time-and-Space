/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.items.gadgets;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import com.code.tama.tts.core.items.core.PowerableItem;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.S2C.entities.UpdateTIRPacketS2C;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.misc.containers.TIRBlockContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.DistExecutor;

import com.code.tama.triggerapi.animation.GeoHelper;
import com.code.tama.triggerapi.animation.GeoItemRenderer;

public class TemporalImprintReaderItem extends PowerableItem {
	private boolean firstTick = true;
	BlockPos pos;
	public TemporalImprintReaderItem(Item.Properties properties) {
		super(properties, 256);
	}

	@Override
	public @NotNull ItemStack getDefaultInstance() {
		return super.getDefaultInstance();
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		float hue = Math.max(0.0F,
				((float) this.getEnergyCapacity() - (float) this.GetPower(stack)) / (float) this.getEnergyCapacity());
		return Mth.hsvToRgb(hue, 1.0F, 1.0F);
	}

	@Override
	public int getEnergyCapacity() {
		return 256;
	}

	public void Update(Player p) {
		DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER,
				() -> () -> p.level().getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(
						cap -> Networking.sendToPlayer((ServerPlayer) p, new UpdateTIRPacketS2C(cap.GetTIRBlocks()))));

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::UpdateFromClient);
	}

	public void UpdateFromClient() {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
				() -> () -> Minecraft.getInstance().level.getCapability(Capabilities.LEVEL_CAPABILITY)
						.ifPresent(cap -> Networking.sendToServer(new UpdateTIRPacketS2C(cap.GetTIRBlocks()))));
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int i,
			boolean b) {
		if (firstTick) {
			firstTick = false;
			GeoHelper.playAnimation(stack, "animation.idle", level.getGameTime());

			if (entity instanceof ServerPlayer player)
				Update(player);
		}
		super.inventoryTick(stack, level, entity, i, b);
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
		if (!IsLinked(useOnContext.getItemInHand()))
			useOnContext.getLevel().getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(cap -> {
				UUID uuid = UUID.randomUUID();
				cap.GetTIRBlocks().put(uuid, new TIRBlockContainer(useOnContext.getClickedPos(), uuid));
				useOnContext.getItemInHand().getOrCreateTag().putUUID("uuid", uuid);
			});
		return super.useOn(useOnContext);
	}

	public static boolean IsLinked(ItemStack stack) {
		return stack.getOrCreateTag().contains("uuid");
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip,
			@NotNull TooltipFlag flagIn) {

		tooltip.add(Component.translatable("gadgets.tir.blockPos", GetLinkedPos(stack, worldIn).toString()));

		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public BlockPos GetLinkedPos(ItemStack stack, Level level) {
		if (pos == null) {
			if (level != null)
				if (stack.getOrCreateTag().contains("uuid")) {
					level.getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(cap -> {
						if (cap.GetTIRBlocks().containsKey(stack.getOrCreateTag().getUUID("uuid")))
							this.pos = cap.GetTIRBlocks().get(stack.getOrCreateTag().getUUID("uuid")).getPos();
						else
							UpdateFromClient();
					});
				}
		}

		return this.pos != null ? this.pos : BlockPos.ZERO;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return getRenderer();
			}
		});
	}

	public GeoItemRenderer getRenderer() {
		return GeoHelper.getRenderer("tir", "item/gadgets/tir.png");
	}
}
