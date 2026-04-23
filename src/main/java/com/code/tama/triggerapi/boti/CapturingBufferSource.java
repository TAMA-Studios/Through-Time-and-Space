/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class CapturingBufferSource implements MultiBufferSource {
	private final Map<RenderType, BufferBuilder> builders = new HashMap<>();

	@Override
	public VertexConsumer getBuffer(RenderType renderType) {
		return builders.computeIfAbsent(renderType, rt -> {
			BufferBuilder bb = new BufferBuilder(rt.bufferSize());
			bb.begin(rt.mode(), rt.format());
			return bb;
		});
	}

	public Map<RenderType, BufferBuilder.RenderedBuffer> finish() {
		Map<RenderType, BufferBuilder.RenderedBuffer> result = new HashMap<>();
		builders.forEach((rt, bb) -> result.put(rt, bb.end()));
		builders.clear();
		return result;
	}
}