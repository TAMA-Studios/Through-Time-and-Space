/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.decoration;

import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.boti.client.BotiPortalModel;
import com.code.tama.tts.config.TTSConfig;
import com.code.tama.tts.mixin.client.IMinecraftAccessor;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public class PortalTileEntityRenderer implements BlockEntityRenderer<PortalTileEntity> {
	private final Minecraft mc = Minecraft.getInstance();

	public PortalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(@NotNull PortalTileEntity portal, float partialTick, @NotNull PoseStack stack,
			@NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
		assert mc.level != null;
		if (portal.getTargetLevel() == null || portal.getTargetPos() == null) {
			return;
		}

		if (!TTSConfig.ClientConfig.BOTI_ENABLED.get()) // || ModList.get().isLoaded("immersive_portals") // TODO: Test
														// IP
			return;
		stack.pushPose();

		portal.getFBOContainer().Render(stack, (pose, botiSource) -> {
			pose.pushPose();
			BotiPortalModel.createBodyLayer().bakeRoot().render(pose, botiSource.getBuffer(RenderType.solid()),
					0xf000f0, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0);
			pose.popPose();
		}, (pose, botiSource) -> {
		}, (pose, botiSource) -> {
			// TODO: SKY RENDERER!!!
			pose.pushPose();
			pose.scale(2, 4, 2);
			if (portal.SkyColor == null
					|| (Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 1)
							% 1200 == 0) {
				if (portal.type != null) {
					Minecraft mc = Minecraft.getInstance();
					ClientLevel oldLevel = mc.level;
					assert mc.level != null;
					Holder<DimensionType> dimType = mc.level.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
							.getHolderOrThrow(portal.dimensionTypeId);

					LevelRenderer renderer = new LevelRenderer(mc, mc.getEntityRenderDispatcher(),
							mc.getBlockEntityRenderDispatcher(), mc.renderBuffers());
					assert mc.player != null;
					ClientLevel level = new ClientLevel(mc.player.connection, mc.level.getLevelData(),
							portal.targetLevel, dimType, mc.options.getEffectiveRenderDistance(),
							mc.options.getEffectiveRenderDistance(), mc.level.getProfilerSupplier(), renderer, false,
							0);
					renderer.setLevel(level);

					mc.level = level;
					assert Minecraft.getInstance().level != null;
					portal.SkyColor = Minecraft.getInstance().level.getSkyColor(portal.targetPos.getCenter(),
							((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
					mc.level = oldLevel;
				} else {
					assert Minecraft.getInstance().player != null;
					assert Minecraft.getInstance().level != null;
					portal.SkyColor = Minecraft.getInstance().level.getSkyColor(
							Minecraft.getInstance().player.position(),
							((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
				}
			}
			// StencilUtils.drawColoredCube(stack, 1, portal.SkyColor);
			BotiPortalModel.createBodyLayer().bakeRoot().render(pose, botiSource.getBuffer(RenderType.debugFilledBox()),
					0xf000f0, OverlayTexture.NO_OVERLAY, (float) portal.SkyColor.x, (float) portal.SkyColor.y,
					(float) portal.SkyColor.z, 1f);
			botiSource.endBatch();
			pose.popPose();
			pose.pushPose();
			pose.translate(-0.5, -0.5, -0.5);
			pose.mulPose(Axis.XP.rotationDegrees(180));
			BOTIUtils.RenderScene(pose, portal);
			pose.popPose();
		});
		stack.popPose();
	}
}
