/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.items.gadgets;

import com.code.tama.tts.client.ClientSetup;
import com.code.tama.tts.server.tardis.subsystems.*;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import com.code.tama.triggerapi.helpers.HologramRenderer;

public class HoloGlasses extends ArmorItem {
	public static int MAX = 4;
	int selected = 0;
	boolean dirty = false;
	public HoloGlasses(Properties p_40388_) {
		super(ArmorMaterials.CHAIN, Type.HELMET, p_40388_);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if (level.isClientSide) {
			while (ClientSetup.SONIC_GLASSES.consumeClick()) {
				// TODO: GUI
				selected = selected >= MAX - 1 ? 0 : selected + 1;
				dirty = true;
			}

			if (dirty) {
				HologramRenderer.getBlocks().clear();
				switch (selected) {
					case 0 :
						renderDematCircuit();
						break;
					case 1 :
						renderReactorCore();
						break;
					case 2 :
						renderDynamoController();
						break;
					case 3 :
						renderDynamoGenerators();
						break;
					default :
						break;
				}
				dirty = false;

			}
		}
		super.onArmorTick(stack, level, player);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level p_40395_, Player p_40396_, InteractionHand p_40397_) {
		return super.use(p_40395_, p_40396_, p_40397_);
	}

	public void renderDematCircuit() {
		renderSystem(new DematerializationCircuit());
	}

	public void renderReactorCore() {
		renderSystem(new NetherReactorCoreSubsystem());
	}

	public void renderDynamoGenerators() {
		renderSystem(new DynamorphicGeneratorStack());
	}

	public void renderDynamoController() {
		renderSystem(new DynamorphicController());
	}

	private void renderSystem(AbstractSubsystem subsystem) {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			Player player = Minecraft.getInstance().player;
			subsystem.BlockMap().forEach((pos, blockState) -> {
				assert player != null;
				HologramRenderer.addBlockToRender(pos.offset(player.blockPosition().getX(),
						player.blockPosition().above().getY(), player.blockPosition().getZ()), blockState);

			});
		});
	}
}
