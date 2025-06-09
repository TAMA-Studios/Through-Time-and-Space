/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models.core;

import java.util.function.Function;

import com.code.tama.tts.server.tileentities.ConsoleTile;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class ConsoleModel<T extends BlockEntity> extends HierarchicalModel<Entity> {

    private final ModelPart Root;

    public ConsoleModel(ModelPart root){
        this(root, RenderType::entityCutoutNoCull);
    }

    public ConsoleModel(ModelPart root, Function<ResourceLocation, RenderType> renderTypeFunction){
        super(renderTypeFunction);
        this.Root = root;
    }

    public abstract void SetupAnimations(@NotNull ConsoleTile tile, float ageInTicks);

    @Override
    public @NotNull ModelPart root() {
        return Root;
    }

    @Override
    public void setupAnim(@NotNull Entity E, float LimbSwing, float LimbSwingAmount, float AgeInTicks, float NetHeadYaw, float HeadPitch) {}
}
