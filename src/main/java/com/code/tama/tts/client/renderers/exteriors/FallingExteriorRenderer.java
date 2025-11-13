/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.exteriors;

import com.code.tama.tts.server.entities.FallingExteriorEntity;
import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class FallingExteriorRenderer extends EntityRenderer<FallingExteriorEntity> {
	private final BlockEntityRenderDispatcher dispatcher;

	private BlockEntity entity;
	public FallingExteriorRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
		this.dispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
	}

	@Override
	public void render(FallingExteriorEntity entity, float yaw, float partialTicks, @NotNull PoseStack stack,
					   @NotNull MultiBufferSource buffer, int light) {
		if (entity.getTileData() != null) {
			if (this.entity == null) {
				this.entity = new ExteriorTile(TTSTileEntities.EXTERIOR_TILE.get(), BlockPos.ZERO,
						entity.getBlockState());
				this.entity.load(entity.getTileData());
			}
			dispatcher.render(this.entity, partialTicks, stack, buffer);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(FallingExteriorEntity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
