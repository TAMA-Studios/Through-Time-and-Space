/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.createmod.catnip.platform.CatnipServices;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TTSBlockEntityBuilder<T extends BlockEntity, P> extends BlockEntityBuilder<T, P> {

	private NonNullSupplier<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<? super T>>> renderer;

	@Nullable private NonNullSupplier<SimpleBlockEntityVisualizer.Factory<T>> visualFactory;
	private Predicate<@NotNull T> renderNormally;

	private final Collection<NonNullSupplier<? extends Collection<NonNullSupplier<? extends Block>>>> deferredValidBlocks = new ArrayList<>();

	public static <T extends BlockEntity, P> BlockEntityBuilder<T, P> create(@NotNull AbstractRegistrate<?> owner,
			@NotNull P parent, @NotNull String name, @NotNull BuilderCallback callback,
			@NotNull BlockEntityFactory<T> factory) {
		return new TTSBlockEntityBuilder<>(owner, parent, name, callback, factory);
	}

	protected TTSBlockEntityBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback,
			BlockEntityFactory<T> factory) {
		super(owner, parent, name, callback, factory);
	}

	public TTSBlockEntityBuilder<T, P> validBlocksDeferred(
			NonNullSupplier<? extends Collection<NonNullSupplier<? extends Block>>> blocks) {
		deferredValidBlocks.add(blocks);
		return this;
	}

	@Override
	protected @NotNull BlockEntityType<T> createEntry() {
		deferredValidBlocks.stream().map(Supplier::get).flatMap(Collection::stream).forEach(this::validBlock);
		return super.createEntry();
	}

	public TTSBlockEntityBuilder<T, P> visual(NonNullSupplier<SimpleBlockEntityVisualizer.Factory<T>> visualFactory) {
		return visual(visualFactory, true);
	}

	public TTSBlockEntityBuilder<T, P> visual(NonNullSupplier<SimpleBlockEntityVisualizer.Factory<T>> visualFactory,
			boolean renderNormally) {
		return visual(visualFactory, be -> renderNormally);
	}

	public TTSBlockEntityBuilder<T, P> visual(NonNullSupplier<SimpleBlockEntityVisualizer.Factory<T>> visualFactory,
			Predicate<@NotNull T> renderNormally) {
		if (this.visualFactory == null) {
			CatnipServices.PLATFORM.executeOnClientOnly(() -> this::registerVisualizer);
		}

		this.visualFactory = visualFactory;
		this.renderNormally = renderNormally;

		return this;
	}

	protected void registerVisualizer() {
		OneTimeEventReceiver.addModListener(getOwner(), FMLClientSetupEvent.class, $ -> {
			var visualFactory = this.visualFactory;
			if (visualFactory != null) {
				Predicate<@NotNull T> renderNormally = this.renderNormally;
				SimpleBlockEntityVisualizer.builder(getEntry()).factory(visualFactory.get())
						.skipVanillaRender(be -> !renderNormally.test(be)).apply();
			}
		});
	}

	public BlockEntityBuilder<T, P> renderer(
			NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<? super T>> renderer) {
		if (this.renderer == null) {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::registerRenderer);
		}

		this.renderer = () -> renderer;
		return this;
	}

	protected void registerRenderer() {
		OneTimeEventReceiver.addModListener(this.getOwner(), FMLClientSetupEvent.class, (event) -> {
			NonNullSupplier<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<? super T>>> renderer = this.renderer;
			if (renderer != null) {
				BlockEntityType<T> type = this.getEntry();
				NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<? super T>> rend = renderer
						.get();
				Objects.requireNonNull(rend);
				BlockEntityRenderers.register(type, rend::apply);
			}
		});
	}
}