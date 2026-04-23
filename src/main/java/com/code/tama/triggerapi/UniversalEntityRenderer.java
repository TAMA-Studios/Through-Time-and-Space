/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UniversalEntityRenderer<T extends Entity> {
	public T cached = null;

	public static Map<EntityType<?>, Entity> types = new HashMap<>();

	public boolean isCached() {
		return cached != null;
	}

	public void cache(T ent) {
		this.cached = ent;
	}

	public static Entity getCachedEntity(EntityType<?> type, Level level) {
		if (!types.containsKey(type))
			types.put(type, type.create(level));

		return types.get(type);
	}

	@SuppressWarnings("unchecked")
	public void RenderEntity(T entity, PoseStack pose, MultiBufferSource source) {
		((EntityRenderer<T>) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(entity.getType()))
				.render(entity, 0, Minecraft.getInstance().getPartialTick(), pose, source, 0xf000f0);
	}

	@SuppressWarnings("unchecked")
	public static void RenderEntityStatic(Entity entity, PoseStack pose, MultiBufferSource source) {
		((EntityRenderer<Entity>) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(entity.getType()))
				.render(entity, 0, Minecraft.getInstance().getPartialTick(), pose, source, 0xf000f0);
	}

	public void RenderCachedEntity(PoseStack pose, MultiBufferSource source) {
		RenderEntity(cached, pose, source);
	}
}
