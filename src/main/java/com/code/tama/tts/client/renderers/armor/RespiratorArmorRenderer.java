/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.armor;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.code.tama.tts.client.models.armor.RespiratorModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EquipmentSlot;

/** the armor renderer for the {@link RespiratorModel} */
public class RespiratorArmorRenderer<T extends RespiratorModel> {
	private final T model;

	public RespiratorArmorRenderer(Supplier<LayerDefinition> supplier, Function<ModelPart, T> modelConstructor) {
		this.model = modelConstructor.apply(supplier.get().bakeRoot());
	}

	private static final ModelPart EMPTY_PART = new ModelPart(Collections.emptyList(), Collections.emptyMap());

	public ModelPart makeArmorParts(EquipmentSlot slot) {
		return new ModelPart(Collections.emptyList(),
				Map.of("head", slot == EquipmentSlot.HEAD ? checkNonNull(model.Head) : EMPTY_PART, "hat", EMPTY_PART,
						"body", EMPTY_PART, "right_arm", EMPTY_PART, "left_arm", EMPTY_PART, "right_leg", EMPTY_PART,
						"left_leg", EMPTY_PART));
	}

	private static @NotNull ModelPart checkNonNull(@Nullable ModelPart part) {
		return part == null ? EMPTY_PART : part;
	}
}
