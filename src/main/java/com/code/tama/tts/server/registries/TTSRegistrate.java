/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;

public class TTSRegistrate extends AbstractRegistrate<TTSRegistrate> {
	protected TTSRegistrate(String modid) {
		super(modid);
	}

	public static TTSRegistrate create(String modid) {
		TTSRegistrate registrate = new TTSRegistrate(modid);
		TTSRegistrateRegistrationCallback.provideRegistrate(registrate);
		return registrate;
	}

	@Override
	public @NotNull TTSRegistrate registerEventListeners(@NotNull IEventBus bus) {
		return super.registerEventListeners(bus);
	}

	// @Override
	// public <T extends BlockEntity> TTSBlockEntityBuilder<T, TTSRegistrate>
	// blockEntity(String name,
	// BlockEntityBuilder.BlockEntityFactory<T> factory) {
	// return blockEntity(self(), name, factory);
	// }
	//
	// @Override
	// public <T extends BlockEntity, P> TTSBlockEntityBuilder<T, P> blockEntity(P
	// parent, String name,
	// BlockEntityBuilder.BlockEntityFactory<T> factory) {
	// return (TTSBlockEntityBuilder<T, P>) entry(name,
	// (callback) -> TTSBlockEntityBuilder.create(this, parent, name, callback,
	// factory));
	// }

	@Override
	public <T extends Block> TTSBlockBuilder<T, TTSRegistrate> block(
			NonNullFunction<BlockBehaviour.Properties, T> factory) {
		return this.block(this.self(), factory);
	}

	@Override
	public <T extends Block> TTSBlockBuilder<T, TTSRegistrate> block(String name,
			NonNullFunction<BlockBehaviour.Properties, T> factory) {
		return this.block(this.self(), name, factory);
	}

	@Override
	public <T extends Block, P> TTSBlockBuilder<T, P> block(P parent,
			NonNullFunction<BlockBehaviour.Properties, T> factory) {
		return this.block(parent, this.currentName(), factory);
	}

	@Override
	public <T extends Block, P> @NotNull TTSBlockBuilder<T, P> block(@NotNull P parent, String name,
			NonNullFunction<BlockBehaviour.Properties, T> factory) {
		return (TTSBlockBuilder<T, P>) this.entry(name,
				(callback) -> TTSBlockBuilder.create(this, parent, name, callback, factory));
	}
}