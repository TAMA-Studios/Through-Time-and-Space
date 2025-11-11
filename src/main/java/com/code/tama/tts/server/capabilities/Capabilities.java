/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.renderers.tiles.tardis.FragmentLinksTile;
import com.code.tama.tts.server.capabilities.caps.LevelCapability;
import com.code.tama.tts.server.capabilities.caps.PlayerCapability;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ILevelCap;
import com.code.tama.tts.server.capabilities.interfaces.IPlayerCap;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.capabilities.providers.FragmentLinksCapabilityProvider;
import com.code.tama.tts.server.capabilities.providers.SerializableCapabilityProvider;
import com.code.tama.tts.server.worlds.dimension.TDimensions;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Capabilities {

	public static final ResourceLocation FRAGMENT_LINKS = new ResourceLocation(MODID, "fragment_links");
	public static final ResourceLocation PLAYER = new ResourceLocation(MODID, "player");
	public static final Capability<IPlayerCap> PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	/**
	 * The capability used for TARDIS levels, basically, any level that holds TARDIS
	 * data, a console unit, clone of an EOH, etc
	 */
	public static final Capability<ITARDISLevel> TARDIS_LEVEL_CAPABILITY = CapabilityManager
			.get(new CapabilityToken<>() {
			});

	/**
	 * The capability used for the TIR, (TODO:) Cracks (11th hour), Rifts (Cardiff,
	 * Boom Town), etc
	 */
	public static final Capability<ILevelCap> LEVEL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final ResourceLocation TARDIS_CAPABILITY_KEY = new ResourceLocation(MODID, "tardis");
	public static final ResourceLocation LEVEL_CAP_KEY = new ResourceLocation(MODID, "level_cap");

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

			event.addCapability(Capabilities.LEVEL_CAP_KEY,
					new SerializableCapabilityProvider<>(LEVEL_CAPABILITY, new LevelCapability(event.getObject())));

			if (!event.getObject().dimensionTypeId().location().equals(TDimensions.TARDIS_DIM_TYPE.location()))
				return;

			event.addCapability(Capabilities.TARDIS_CAPABILITY_KEY, new SerializableCapabilityProvider<>(
					TARDIS_LEVEL_CAPABILITY, new TARDISLevelCapability(event.getObject())));
		}

		@SubscribeEvent
		public static void attachEnergyCapability(AttachCapabilitiesEvent<BlockEntity> event) {
			if (event.getObject() instanceof FragmentLinksTile) {
				FragmentLinksCapabilityProvider cap = new FragmentLinksCapabilityProvider();
				event.addCapability(Capabilities.FRAGMENT_LINKS, cap);

				// make sure it cleans up when the tile is removed
				event.addListener(cap.energyCap::invalidate);
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
