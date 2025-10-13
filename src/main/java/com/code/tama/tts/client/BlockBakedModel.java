/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockBakedModel implements BakedModel {
    public final List<BakedQuad> quads;

    public BlockBakedModel(List<BakedQuad> quads) {
        this.quads = quads;
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance()
                .getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(new ResourceLocation("minecraft", "stone"));
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(
            @Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand) {
        return this.quads;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }
}
