package com.code.tama.tts.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;

@Mixin(BlockBehaviour.Properties.class)
public interface BlockBehaviourPropertiesAccessor {

    // ----- GETTERS -----
    @Accessor Function<BlockState, MapColor> getMapColor();
    @Accessor boolean getHasCollision();
    @Accessor SoundType getSoundType();
    @Accessor ToIntFunction<BlockState> getLightEmission();
    @Accessor float getExplosionResistance();
    @Accessor float getDestroyTime();
    @Accessor boolean getRequiresCorrectToolForDrops();
    @Accessor boolean getIsRandomlyTicking();
    @Accessor float getFriction();
    @Accessor float getSpeedFactor();
    @Accessor float getJumpFactor();
    @Accessor ResourceLocation getDrops();
    @Accessor boolean getCanOcclude();
    @Accessor boolean getIsAir();
    @Accessor boolean getIgnitedByLava();
    @Accessor @Deprecated boolean getLiquid();
    @Accessor @Deprecated boolean getForceSolidOff();
    @Accessor boolean getForceSolidOn();
    @Accessor PushReaction getPushReaction();
    @Accessor boolean getSpawnParticlesOnBreak();
    @Accessor NoteBlockInstrument getInstrument();
    @Accessor boolean getReplaceable();
    @Accessor java.util.function.Supplier<ResourceLocation> getLootTableSupplier();
    @Accessor BlockBehaviour.StateArgumentPredicate<EntityType<?>> getIsValidSpawn();
    @Accessor BlockBehaviour.StatePredicate getIsRedstoneConductor();
    @Accessor BlockBehaviour.StatePredicate getIsSuffocating();
    @Accessor BlockBehaviour.StatePredicate getIsViewBlocking();
    @Accessor BlockBehaviour.StatePredicate getHasPostProcess();
    @Accessor BlockBehaviour.StatePredicate getEmissiveRendering();
    @Accessor boolean getDynamicShape();
    @Accessor
    FeatureFlagSet getRequiredFeatures();
    @Accessor Optional<BlockBehaviour.OffsetFunction> getOffsetFunction();

    // ----- SETTERS -----
    @Accessor @Mutable void setMapColor(Function<BlockState, MapColor> mapColor);
    @Accessor @Mutable void setHasCollision(boolean hasCollision);
    @Accessor @Mutable void setSoundType(SoundType soundType);
    @Accessor @Mutable void setLightEmission(ToIntFunction<BlockState> lightEmission);
    @Accessor @Mutable void setExplosionResistance(float explosionResistance);
    @Accessor @Mutable void setDestroyTime(float destroyTime);
    @Accessor @Mutable void setRequiresCorrectToolForDrops(boolean requiresCorrectToolForDrops);
    @Accessor @Mutable void setIsRandomlyTicking(boolean isRandomlyTicking);
    @Accessor @Mutable void setFriction(float friction);
    @Accessor @Mutable void setSpeedFactor(float speedFactor);
    @Accessor @Mutable void setJumpFactor(float jumpFactor);
    @Accessor @Mutable void setDrops(ResourceLocation drops);
    @Accessor @Mutable void setCanOcclude(boolean canOcclude);
    @Accessor @Mutable void setIsAir(boolean isAir);
    @Accessor @Mutable void setIgnitedByLava(boolean ignitedByLava);
    @Accessor @Mutable @Deprecated void setLiquid(boolean liquid);
    @Accessor @Mutable @Deprecated void setForceSolidOff(boolean forceSolidOff);
    @Accessor @Mutable
    void setForceSolidOn(boolean forceSolidOn);
    @Accessor @Mutable void setPushReaction(PushReaction pushReaction);
    @Accessor @Mutable void setSpawnParticlesOnBreak(boolean spawnParticlesOnBreak);
    @Accessor @Mutable void setInstrument(NoteBlockInstrument instrument);
    @Accessor @Mutable void setReplaceable(boolean replaceable);
    @Accessor @Mutable void setLootTableSupplier(java.util.function.Supplier<ResourceLocation> lootTableSupplier);
    @Accessor @Mutable void setIsValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn);
    @Accessor @Mutable void setIsRedstoneConductor(BlockBehaviour.StatePredicate isRedstoneConductor);
    @Accessor @Mutable void setIsSuffocating(BlockBehaviour.StatePredicate isSuffocating);
    @Accessor @Mutable void setIsViewBlocking(BlockBehaviour.StatePredicate isViewBlocking);
    @Accessor @Mutable void setHasPostProcess(BlockBehaviour.StatePredicate hasPostProcess);
    @Accessor @Mutable void setEmissiveRendering(BlockBehaviour.StatePredicate emissiveRendering);
    @Accessor @Mutable void setDynamicShape(boolean dynamicShape);
    @Accessor @Mutable void setRequiredFeatures(FeatureFlagSet requiredFeatures);
    @Accessor @Mutable void setOffsetFunction(Optional<BlockBehaviour.OffsetFunction> offsetFunction);
}
