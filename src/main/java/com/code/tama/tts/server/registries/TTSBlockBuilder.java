package com.code.tama.tts.server.registries;

import com.code.tama.tts.datagen.assets.DataBlockStateProvider;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TTSBlockBuilder<T extends Block, P> extends BlockBuilder<T, P> {
    protected TTSBlockBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, NonNullFunction<BlockBehaviour.Properties, T> factory, NonNullSupplier<BlockBehaviour.Properties> initialProperties) {
        super(owner, parent, name, callback, factory, initialProperties);
    }

    public static <T extends Block, P> @NotNull TTSBlockBuilder<T, P> create(@NotNull AbstractRegistrate<?> owner, @NotNull P parent, @NotNull String name, @NotNull BuilderCallback callback, NonNullFunction<BlockBehaviour.Properties, T> factory) {
        return (new TTSBlockBuilder<>(owner, parent, name, callback, factory, BlockBehaviour.Properties::of)).defaultBlockstate().defaultLoot().defaultLang();
    }

    public TTSBlockBuilder<T, P> stateWithExistingModel() {
        return this.blockstate(DataBlockStateProvider::existingModel);
    }

    public TTSBlockBuilder<T, P> stateWithExistingModel(String path) {
        return this.blockstate((ctx, provider) -> DataBlockStateProvider.existingModel(path, ctx, provider));
    }

    public TTSBlockBuilder<T, P> controlPanelState() {
        return this.blockstate(DataBlockStateProvider::controlPanel);
    }

    public TTSBlockBuilder<T, P> airState() {
        return this.blockstate(DataBlockStateProvider::air);
    }


    public TTSBlockBuilder<T, P> slabStateAndModel() {
        try {
            return this.blockstate(DataBlockStateProvider::slab);
        }
        catch (Exception e) {e.printStackTrace();}
        return this;
    }

    public TTSBlockBuilder<T, P> simpleSlabStateAndModel() {
        try {
            return this.blockstate(DataBlockStateProvider::simpleSlab);
        }
        catch (Exception e) {e.printStackTrace();}
        return this;
    }

    public TTSBlockBuilder<T, P> simpleTrapdoor() {
        try {
            return this.blockstate(DataBlockStateProvider::simpleTrapdoor);
        }
        catch (Exception e) {e.printStackTrace();}
        return this;
    }

    @Override
    public TTSBlockBuilder<T, P> properties(NonNullUnaryOperator<BlockBehaviour.Properties> func) {
        return (TTSBlockBuilder<T, P>) super.properties(func);
    }

    @Override
    public TTSBlockBuilder<T, P> initialProperties(NonNullSupplier<? extends Block> block) {
        return (TTSBlockBuilder<T, P>) super.initialProperties(block);
    }

    @Override
    public TTSBlockBuilder<T, P> simpleItem() {
        return (TTSBlockBuilder<T, P>) super.simpleItem();
    }

    @Override
    public <BE extends BlockEntity> TTSBlockBuilder<T, P> simpleBlockEntity(BlockEntityBuilder.BlockEntityFactory<BE> factory) {
        return (TTSBlockBuilder<T, P>) super.simpleBlockEntity(factory);
    }

    @Override
    public TTSBlockBuilder<T, P> color(NonNullSupplier<Supplier<BlockColor>> colorHandler) {
        return (TTSBlockBuilder<T, P>) super.color(colorHandler);
    }

    @Override
    public TTSBlockBuilder<T, P> defaultBlockstate() {
        try {
            return (TTSBlockBuilder<T, P>) super.defaultBlockstate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public TTSBlockBuilder<T, P> blockstate(NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> cons) {
        return (TTSBlockBuilder<T, P>) super.blockstate(cons);
    }

    @Override
    public TTSBlockBuilder<T, P> defaultLang() {
        return (TTSBlockBuilder<T, P>) super.defaultLang();
    }

    @Override
    public TTSBlockBuilder<T, P> lang(String name) {
        return (TTSBlockBuilder<T, P>) super.lang(name);
    }

    @Override
    public TTSBlockBuilder<T, P> defaultLoot() {
        return (TTSBlockBuilder<T, P>) super.defaultLoot();
    }

    @Override
    public TTSBlockBuilder<T, P> loot(NonNullBiConsumer<RegistrateBlockLootTables, T> cons) {
        return (TTSBlockBuilder<T, P>) super.loot(cons);
    }

    @Override
    public TTSBlockBuilder<T, P> recipe(NonNullBiConsumer<DataGenContext<Block, T>, RegistrateRecipeProvider> cons) {
        return (TTSBlockBuilder<T, P>) super.recipe(cons);
    }
}
