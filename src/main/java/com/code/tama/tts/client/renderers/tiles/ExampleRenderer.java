/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.tileentities.ExampleTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

//                                                             \/ Replace with Tile Entity class
public class ExampleRenderer implements BlockEntityRenderer<ExampleTileEntity> {
    public static final int fullBright = LightTexture.pack(15, 15);
    public final BlockEntityRendererProvider.Context context;

    public ExampleRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(
            @NotNull ExampleTileEntity example, // Make sure this is the Tile Entity class
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource,
            int combinedLight,
            int combinedOverlay) {
        if (example.getLevel() == null) return;

        // If the TARDIS level cap is present
        example.getLevel()
                .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                .ifPresent(cap -> {
                    // Do all of this ("cap" is the TARDIS level capability)
                    poseStack.pushPose();

                    poseStack.popPose();
                });
    }
}
