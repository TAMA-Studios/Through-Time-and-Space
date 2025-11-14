/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.RegistryObject;

public class TTSRegistrate extends AbstractRegistrate<TTSRegistrate> {
	private static final Map<RegistryEntry<?>, RegistryObject<CreativeModeTab>> TAB_LOOKUP = Collections
			.synchronizedMap(new IdentityHashMap<>());

	@Nullable protected Function<Item, TooltipFlag> currentTooltipModifierFactory;

	@Nullable protected RegistryObject<CreativeModeTab> currentTab;

	protected TTSRegistrate(String modid) {
		super(modid);
	}

	public static TTSRegistrate create(String modid) {
		TTSRegistrate registrate = new TTSRegistrate(modid);
		// The registrate is registered here instead of in the constructor so that if a
		// subclass
		// overrides the addRegisterCallback to be dependent on some sort of state
		// initialized in the constructor,
		// it won't explode. The consequence is that subclasses must manually provide
		// their registrate to the callback API
		TTSRegistrateRegistrationCallback.provideRegistrate(registrate);
		return registrate;
	}

	@Override
	public <T extends BlockEntity> TTSBlockEntityBuilder<T, TTSRegistrate> blockEntity(String name,
			BlockEntityBuilder.BlockEntityFactory<T> factory) {
		return blockEntity(self(), name, factory);
	}

	@Override
	public <T extends BlockEntity, P> TTSBlockEntityBuilder<T, P> blockEntity(P parent, String name,
			BlockEntityBuilder.BlockEntityFactory<T> factory) {
		return (TTSBlockEntityBuilder<T, P>) entry(name,
				(callback) -> TTSBlockEntityBuilder.create(this, parent, name, callback, factory));
	}
}