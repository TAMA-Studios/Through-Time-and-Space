/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.achievements;

import com.code.tama.tts.TTSMod;
import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class TTSAchievementTrigger extends SimpleCriterionTrigger<TTSAchievementTrigger.Instance> {

	private final ResourceLocation id;

	public TTSAchievementTrigger(String name) {
		this.id = new ResourceLocation(TTSMod.MODID, name);
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	protected Instance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
		String event = json.has("event") ? json.get("event").getAsString() : "";

		return new Instance(id, player, event);
	}

	public void trigger(ServerPlayer player, String event) {
		trigger(player, instance -> instance.matches(event));
	}

	public static class Instance extends AbstractCriterionTriggerInstance {

		private final String event;

		public Instance(ResourceLocation id, ContextAwarePredicate player, String event) {
			super(id, player);
			this.event = event;
		}

		public boolean matches(String event) {
			return this.event.equals(event);
		}
	}
}