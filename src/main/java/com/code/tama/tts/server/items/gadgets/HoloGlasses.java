/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.items.gadgets;

import com.code.tama.tts.client.ClientSetup;
import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import com.code.tama.triggerapi.helpers.RenderHelper;

public class HoloGlasses extends ArmorItem {
	DematerializationCircuit circuit = new DematerializationCircuit();
	public HoloGlasses(Properties p_40388_) {
		super(ArmorMaterials.CHAIN, Type.HELMET, p_40388_);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if (level.isClientSide) {
			while (ClientSetup.HOLO_GLASSES_GUI.get().consumeClick()) {
				circuit.BlockMap().forEach((pos, blockState) -> {
					RenderHelper.addBlockToRender(pos.offset(player.blockPosition().getX(),
							player.blockPosition().getY(), player.blockPosition().getZ()), blockState);
				});
				// TODO: GUI
			}
		}
		super.onArmorTick(stack, level, player);
	}

	public void renderDematCircuit() {
		System.out.println("renderFunc");
	}
}
