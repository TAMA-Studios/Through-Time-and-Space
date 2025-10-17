/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.renderers.tiles.tardis.FragmentLinksTile;
import com.code.tama.tts.server.capabilities.caps.PlayerCapability;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.IPlayerCap;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.capabilities.providers.SerializableCapabilityProvider;
import com.code.tama.tts.server.worlds.dimension.MDimensions;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Capabilities {

	public static final ResourceLocation FRAGMENT_LINKS = new ResourceLocation(MODID, "fragment_links");
	public static final ResourceLocation PLAYER = new ResourceLocation(MODID, "player");
	public static final Capability<IPlayerCap> PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final Capability<ITARDISLevel> TARDIS_LEVEL_CAPABILITY = CapabilityManager
			.get(new CapabilityToken<>() {
			});
	public static final ResourceLocation TARDIS_LEVEL_KEY = new ResourceLocation(MODID, "tardis");

	public static <T, O extends ICapabilityProvider> LazyOptional<T> getCap(Capability<T> cap, O object) {
		return object == null ? LazyOptional.empty() : object.getCapability(cap);
	}

	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(ITARDISLevel.class);
		event.register(IPlayerCap.class);
	}

	@Mod.EventBusSubscriber(modid = MODID)
	public static class AttachCapabilities {
		@SubscribeEvent
		public static void AttachWorldCapabilities(AttachCapabilitiesEvent<Level> event) {

			// For unregistered worlds, because some mods either aren't that bright or just
			// as hacky and
			// gimmicky as a
			// nintendo console
			if (event.getObject().registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
					.getKey(event.getObject().dimensionType()) == null)
				return;

			if (!event.getObject().dimensionTypeId().location().equals(MDimensions.TARDIS_DIM_TYPE.location()))
				return;

			event.addCapability(Capabilities.TARDIS_LEVEL_KEY, new SerializableCapabilityProvider<>(
					TARDIS_LEVEL_CAPABILITY, new TARDISLevelCapability(event.getObject())));
		}

		@SubscribeEvent
		public static void attachEnergyCapability(AttachCapabilitiesEvent<BlockEntity> event) {
			if (event.getObject() instanceof FragmentLinksTile) {
				EnergyStorage energyStorage = new EnergyStorage(10000, 1000);
				LazyOptional<EnergyStorage> energyCap = LazyOptional.of(() -> energyStorage);

				event.addCapability(Capabilities.FRAGMENT_LINKS, new ICapabilityProvider() {
					@Override
					public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
						if (cap == ForgeCapabilities.ENERGY) {
							return energyCap.cast();
						}
						return LazyOptional.empty();
					}
				});

				// make sure it cleans up when the tile is removed
				event.addListener(energyCap::invalidate);
			}
		}

		@SubscribeEvent
		public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player) {
				event.addCapability(Capabilities.PLAYER, new SerializableCapabilityProvider<>(PLAYER_CAPABILITY,
						new PlayerCapability(event.getObject())));
			}
		}
	}
}
