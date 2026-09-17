/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.items.armor;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.armor.RespiratorModel;
import com.code.tama.tts.client.renderers.armor.RespiratorArmorRenderer;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import com.code.tama.triggerapi.items.armor.ModdedArmorItem;

public class RespiratorArmorItem extends ModdedArmorItem {
	public RespiratorArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
		super(pMaterial, pType, pProperties);
	}

	@Override
	protected ModelPart getRenderer(LivingEntity living, ItemStack stack, EquipmentSlot slot) {
		ModelPart p = new RespiratorArmorRenderer<>(RespiratorModel::createBodyLayer, RespiratorModel::new)
				.makeArmorParts(slot);
		return p;
	}

	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return makeCustomTextureLocation(MODID, "respirator");
	}

}
