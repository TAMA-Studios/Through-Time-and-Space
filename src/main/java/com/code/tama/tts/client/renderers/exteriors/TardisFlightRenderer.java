/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.exteriors;

import com.code.tama.tts.client.renderers.tiles.tardis.TardisExteriorRenderer;
import com.code.tama.tts.core.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.core.entities.TardisFlightEntity;
import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Renders a {@link TardisFlightEntity} by delegating to the same
 * {@link com.code.tama.tts.client.renderers.tiles.tardis.TardisExteriorRenderer}
 * a landed exterior uses, exactly like {@code FallingExteriorRenderer} does
 * for the block-teleport flight mode - except we build the scratch tile from
 * synced entity data instead of an (unsynced) blockData tag, so it actually
 * shows the right model/facing/doors on the client.
 *
 * <p>Known limitation: the scratch tile here is never given a real level/
 * position, so {@code AbstractPortalTile}'s BOTI "window into the interior"
 * won't have anywhere valid to point while flying - you'll get the model,
 * doors and lighting, but not the portal-scene-through-the-windows effect.
 * That's the same limitation the (currently disabled) FallingExteriorEntity
 * render path has.</p>
 */
public class TardisFlightRenderer extends EntityRenderer<TardisFlightEntity> {
	private final BlockEntityRenderDispatcher dispatcher;

	private ExteriorTile scratchTile;
	private Direction scratchTileFacing;

	public TardisFlightRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
		this.dispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
	}

	@Override
	public void render(TardisFlightEntity entity, float yaw, float partialTicks, @NotNull PoseStack stack,
			@NotNull MultiBufferSource buffer, int light) {
		Direction facing = entity.getSyncedFacing();

		// Rebuild the scratch tile if it doesn't exist yet, or if the exterior's
		// facing changed (rare mid-flight, but cheap enough to just check).
		if (this.scratchTile == null || this.scratchTileFacing != facing) {
			BlockState state = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState().setValue(ExteriorBlock.FACING,
					facing);
			this.scratchTile = new ExteriorTile(TTSTileEntities.EXTERIOR_TILE.get(), BlockPos.ZERO, state);
			this.scratchTileFacing = facing;
		}

		this.scratchTile.setModelIndex(entity.getSyncedModel());
		this.scratchTile.setClientTransparency(entity.getSyncedTransparency());
		this.scratchTile.SetDoorsOpen(entity.getSyncedDoorsOpen());

		((TardisExteriorRenderer<ExteriorTile>) Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(this.scratchTile)).render(this.scratchTile, partialTicks, stack, buffer, light, OverlayTexture.NO_OVERLAY);
//		dispatcher.render(this.scratchTile, partialTicks, stack, buffer);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull TardisFlightEntity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}