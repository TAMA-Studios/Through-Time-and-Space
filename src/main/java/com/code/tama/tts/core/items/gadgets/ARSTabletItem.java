/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.items.gadgets;

import com.code.tama.triggerapi.animation.GeoHelper;
import com.code.tama.triggerapi.animation.GeoItemRenderer;
import com.code.tama.tts.client.gui.ARSMapScreen;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.DistExecutor;

import java.util.function.Consumer;

public class ARSTabletItem extends Item {

	public ARSTabletItem(Properties p_41383_) {
		super(p_41383_);
	}

	public GeoItemRenderer getRenderer() {
		return GeoHelper.getRenderer("itemgeo/ars_tablet", "item/gadgets/ars");
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

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			if (level.isClientSide) {
				TARDISLevelCapability.GetClientTARDISCapSupplier().ifPresent(t -> {
					t.UpdateClient(DataUpdateValues.ARS);
					Minecraft.getInstance().setScreen(new ARSMapScreen(level, t.getARSGrids()));
				});
			}
		});
		return super.use(level, player, interactionHand);
	}

	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {

		// DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
		// if (Minecraft.getInstance().level != null) {
		// GeoHelper.playAnimation(p_41404_, "animation.fire",
		// Minecraft.getInstance().level.getGameTime());
		// }
		// });
		super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
	}
}
