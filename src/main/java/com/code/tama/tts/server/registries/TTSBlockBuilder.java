/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.datagen.assets.DataBlockStateProvider;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.*;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.nullness.*;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class TTSBlockBuilder<T extends Block, P> extends BlockBuilder<T, P> {
	protected TTSBlockBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback,
			NonNullFunction<BlockBehaviour.Properties, T> factory,
			NonNullSupplier<BlockBehaviour.Properties> initialProperties) {
		super(owner, parent, name, callback, factory, initialProperties);
	}

	public static <T extends Block, P> @NotNull TTSBlockBuilder<T, P> create(@NotNull AbstractRegistrate<?> owner,
			@NotNull P parent, @NotNull String name, @NotNull BuilderCallback callback,
			@NotNull NonNullFunction<BlockBehaviour.Properties, T> factory) {
		return (new TTSBlockBuilder<>(owner, parent, name, callback, factory, BlockBehaviour.Properties::of))
				.defaultBlockstate().defaultLoot().defaultLang();
	}

	public TTSBlockBuilder<T, P> stateWithExistingModel() {
		return this.blockstate(DataBlockStateProvider::existingModel);
	}

	public TTSBlockBuilder<T, P> verySimpleBlock() {
		return this
				.properties(p -> p.mapColor(MapColor.COLOR_BROWN).strength(1.25F).noOcclusion().lightLevel(state -> 10))
				.stateWithExistingModel().simpleItem().defaultLang().defaultLoot();
	}

	public TTSBlockBuilder<T, P> stateWithExistingModel(String path) {
		return this.blockstate((ctx, provider) -> DataBlockStateProvider.existingModel(path, ctx, provider));
	}

	public TTSBlockBuilder<T, P> controlPanelState() {
		return this.itemWithPath(BlockItem::new, "control/").build().blockstate(DataBlockStateProvider::controlPanel);
	}

	public TTSBlockBuilder<T, P> airState() {
		return this.blockstate(DataBlockStateProvider::air);
	}

	public TTSBlockBuilder<T, P> slabStateAndModel() {
		try {
			return this.blockstate(DataBlockStateProvider::slab);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public TTSBlockBuilder<T, P> simpleSlabStateAndModel() {
		try {
			return this.blockstate(DataBlockStateProvider::simpleSlab);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public TTSBlockBuilder<T, P> simpleTrapdoor() {
		try {
			return this.blockstate(DataBlockStateProvider::simpleTrapdoor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public @NotNull TTSBlockBuilder<T, P> properties(@NotNull NonNullUnaryOperator<BlockBehaviour.Properties> func) {
		return (TTSBlockBuilder<T, P>) super.properties(func);
	}

	@Override
	public @NotNull TTSBlockBuilder<T, P> initialProperties(@NotNull NonNullSupplier<? extends Block> block) {
		return (TTSBlockBuilder<T, P>) super.initialProperties(block);
	}

	@Override
	public @NotNull TTSBlockBuilder<T, P> simpleItem() {
		return (TTSBlockBuilder<T, P>) super.simpleItem();
	}

	public <I extends Item> ItemBuilder<I, TTSBlockBuilder<T, P>> itemWithPath(
			NonNullBiFunction<? super T, Item.Properties, ? extends I> factory, String path) {
		return ((ItemBuilder) this.getOwner()
				.item(this, this.getName(), (p) -> (Item) factory.apply(this.getEntry(), p))
				.setData(ProviderType.LANG, (ctx, provider) -> {
					provider.add(ctx.get().getDescriptionId(), Arrays.stream(ctx.getName().split("/")).toList()
							.get(Arrays.stream(ctx.getName().split("/")).toArray().length));
				})).model((ctx, prov) -> {
					String modelPath = "tts:block/" + this.getName() + path;
					System.out.println(modelPath);
					((RegistrateItemModelProvider) prov)
							.withExistingParent("item/" + ((DataGenContext<Item, I>) ctx).getName(), modelPath);

					// ((RegistrateItemModelProvider) prov).blockItem(this.asSupplier());

				});
	}

	public TTSBlockBuilder<T, P> simpleItemNoData() {
		return this.itemNoData(BlockItem::new).build();
	}
	public <I extends Item> ItemBuilder<I, TTSBlockBuilder<T, P>> itemNoData(
			NonNullBiFunction<? super T, Item.Properties, ? extends I> factory) {
		return ((ItemBuilder<I, TTSBlockBuilder<T, P>>) this.getOwner().item(this, this.getName(),
				(p) -> (Item) factory.apply(this.getEntry(), p)));
	}

	@Override
	public <BE extends BlockEntity> TTSBlockBuilder<T, P> simpleBlockEntity(
			BlockEntityBuilder.BlockEntityFactory<BE> factory) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	public TTSBlockBuilder<T, P> blankBlockstate() {
		return this.airState();
	}

	@Override
	public TTSBlockBuilder<T, P> blockstate(
			NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> cons) {
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
