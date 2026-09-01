/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.compat.cct.registry;

import static com.code.tama.tts.TTSMod.registrate;

import java.util.function.Function;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.compat.cct.CCTARDISInterface;
import com.code.tama.tts.core.compat.cct.blocks.TardisCCInterfaceBlock;
import com.code.tama.tts.core.compat.cct.tiles.TardisCCInterfaceTile;
import com.code.tama.tts.core.registries.TTSRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class CCTRegistry {

	public static final BlockEntry<TardisCCInterfaceBlock> TARDIS_INTERFACE_BLOCK = registrate()
			.block("tardis_interface_block", TardisCCInterfaceBlock::new).simpleItem().defaultBlockstate().register();

	public static final BlockEntityEntry<TardisCCInterfaceTile> TARDIS_INTERFACE_TILE = registrate()
			.blockEntity("tardis_interface_tile", TardisCCInterfaceTile::new)
			.validBlocks(CCTRegistry.TARDIS_INTERFACE_BLOCK).register();

	public static void attachPeripherals(AttachCapabilitiesEvent<BlockEntity> event) {
		if (event.getObject() instanceof TardisCCInterfaceTile tardisInterface) {
			PeripheralProvider.attach(event, tardisInterface, CCTARDISInterface::new);
		}
	}

	public static void init() {
		MinecraftForge.EVENT_BUS.addListener(CCTRegistry::attachPeripherals);
	}

	public static final Capability<IPeripheral> CAPABILITY_PERIPHERAL = CapabilityManager.get(new CapabilityToken<>() {
	});
	private static final ResourceLocation PERIPHERAL = UniversalCommon.modRL("peripheral");

	// A {@link ICapabilityProvider} that lazily creates an {@link IPeripheral} when
	// required.
	private static final class PeripheralProvider<O extends BlockEntity> implements ICapabilityProvider {
		private final O blockEntity;
		private final Function<O, IPeripheral> factory;
		private @Nullable LazyOptional<IPeripheral> peripheral;

		private PeripheralProvider(O blockEntity, Function<O, IPeripheral> factory) {
			this.blockEntity = blockEntity;
			this.factory = factory;
		}

		private static <O extends BlockEntity> void attach(AttachCapabilitiesEvent<BlockEntity> event, O blockEntity,
				Function<O, IPeripheral> factory) {
			var provider = new PeripheralProvider<>(blockEntity, factory);
			event.addCapability(PERIPHERAL, provider);
			event.addListener(provider::invalidate);
		}

		private void invalidate() {
			if (peripheral != null)
				peripheral.invalidate();
			peripheral = null;
		}

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction direction) {
			if (capability != CAPABILITY_PERIPHERAL)
				return LazyOptional.empty();
			if (blockEntity.isRemoved())
				return LazyOptional.empty();

			var peripheral = this.peripheral;
			return (peripheral == null
					? (this.peripheral = LazyOptional.of(() -> factory.apply(blockEntity)))
					: peripheral).cast();
		}
	}
}
