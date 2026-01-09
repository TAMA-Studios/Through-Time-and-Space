/* (C) TAMA Studios 2025 */
package com.code.tama.tts;

import com.code.tama.triggerapi.TriggerAPI;
import com.code.tama.triggerapi.helpers.FileHelper;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer;
import com.code.tama.tts.compat.ModCompat;
import com.code.tama.tts.config.TTSConfig;
import com.code.tama.tts.server.dimensions.Biomes;
import com.code.tama.tts.server.items.tabs.Decorational;
import com.code.tama.tts.server.items.tabs.DimensionalTab;
import com.code.tama.tts.server.items.tabs.MainTab;
import com.code.tama.tts.server.items.tabs.SOV;
import com.code.tama.tts.server.loots.TTSLootModifiers;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.registries.TTSRegistrate;
import com.code.tama.tts.server.registries.forge.*;
import com.code.tama.tts.server.registries.misc.SonicModeRegistry;
import com.code.tama.tts.server.registries.misc.UICategoryRegistry;
import com.code.tama.tts.server.registries.misc.UIComponentRegistry;
import com.code.tama.tts.server.registries.tardis.ControlsRegistry;
import com.code.tama.tts.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.tts.server.worlds.TTSFeatures;
import com.code.tama.tts.server.worlds.tree.ModFoliagePlacers;
import com.code.tama.tts.server.worlds.tree.TTSTrunkPlacerTypes;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.code.tama.triggerapi.Logger.DATE_FORMAT_FILE;
import static com.code.tama.triggerapi.Logger.DATE_FORMAT_FOLDER;
import static com.code.tama.tts.server.registries.forge.TTSCreativeTabs.CREATIVE_MODE_TABS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TTSMod.MODID)
public class TTSMod {
	public static final String MODID = "tts";
	private static final TTSRegistrate REGISTRATE = TTSRegistrate.create(MODID);
	private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

	public static final Logger LOGGER = com.code.tama.triggerapi.Logger.LOGGER;
	public static final org.slf4j.Logger LOGGER_SLF4J = LogUtils.getLogger();
	// Define mod id in a common place for everything to reference
	public static ArrayList<AbstractSoundScheme> SoundSchemes = new ArrayList<>();
	public static TriggerAPI triggerAPI;

	public TTSMod() {
		REGISTRATE.skipErrors(true);
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);
		CustomLevelRenderer.Register();

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
				() -> () -> MinecraftForge.EVENT_BUS.register(CustomLevelRenderer.class));

		// This comment suppresses the "InstantiationOfUtilityClass" warning
		// noinspection InstantiationOfUtilityClass
		triggerAPI = new TriggerAPI(modEventBus, MODID);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TTSConfig.ClientConfig.SPEC,
				"through_time_and_space-client-config.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, TTSConfig.ServerConfig.SPEC,
				"through_time_and_space-server-config.toml");

		FileHelper.createStoredFile("last_time_launched",
				LocalDateTime.now().format(DATE_FORMAT_FILE) + " - " + LocalDateTime.now().format(DATE_FORMAT_FOLDER));

		// Registration!

		registrates(modEventBus);

		SonicModeRegistry.register(modEventBus);
		ControlsRegistry.register(modEventBus);
		TTSEntities.ENTITY_TYPES.register(modEventBus);
		TTSParticles.PARTICLES.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
		TTSLootModifiers.register(modEventBus);
		TTSSounds.register(modEventBus);
		UICategoryRegistry.register(modEventBus);
		UIComponentRegistry.register(modEventBus);
		TTSTrunkPlacerTypes.register(modEventBus);
		ModFoliagePlacers.register(modEventBus);
		TTSFeatures.FEATURES.register(modEventBus);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		// Register the item to a creative tab
		modEventBus.addListener(this::addCreative);
		Biomes.BIOME_MODIFIERS.register(modEventBus);
		Biomes.CHUNK_GENERATORS.register(modEventBus);
		ModCompat.Run();
	}

	private void registrates(IEventBus modEventBus) {
		REGISTRATE.registerEventListeners(modEventBus);

		TTSBlocks.register();
		TTSTileEntities.register();
		TTSItems.register();
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {

		if (event.getTabKey() == TTSCreativeTabs.DIMENSIONAL_TAB.getKey()
				|| event.getTabKey() == TTSCreativeTabs.MAIN_TAB.getKey()
				|| event.getTabKey() == TTSCreativeTabs.DECORATIONAL_TAB.getKey()) {

			// Check items
			for (Field f : TTSItems.class.getFields()) {

				// Check if the field type is ItemEntry<?>
				if (ItemEntry.class.isAssignableFrom(f.getType())) {

					try {
						// Get the value of the field
						Object value = f.get(null);

						if (value instanceof ItemEntry<?> entry) {
							if (event.getTabKey() == TTSCreativeTabs.DIMENSIONAL_TAB.getKey())
								if (f.isAnnotationPresent(DimensionalTab.class)) {
									ItemStack stack = entry.get().getDefaultInstance();
									stack.setCount(1);
									if (stack.getCount() == 1 && !entry.get().asItem().equals(Items.AIR))
										event.accept(stack);
								}

							if (event.getTabKey() == TTSCreativeTabs.MAIN_TAB.getKey())
								if (f.isAnnotationPresent(MainTab.class)) {
									ItemStack stack = entry.get().getDefaultInstance();
									stack.setCount(1);
									if (stack.getCount() == 1 && !entry.get().asItem().equals(Items.AIR))
										event.accept(stack);
								}
						}

					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			}

			// Now do blocks
			for (Field f : TTSBlocks.class.getFields()) {

				// Check if the field type is ItemEntry<?>
				if (BlockEntry.class.isAssignableFrom(f.getType())) {

					try {
						// Get the value of the field
						Object value = f.get(null);

						if (value instanceof BlockEntry<?> entry) {
							if (event.getTabKey() == TTSCreativeTabs.DIMENSIONAL_TAB.getKey())
								if (f.isAnnotationPresent(DimensionalTab.class)) {
									ItemStack stack = entry.get().asItem().getDefaultInstance();
									stack.setCount(1);
									if (stack.getCount() == 1 && !entry.get().asItem().equals(Items.AIR))
										event.accept(stack);
								}

							if (event.getTabKey() == TTSCreativeTabs.DECORATIONAL_TAB.getKey())
								if (f.isAnnotationPresent(Decorational.class) || f.isAnnotationPresent(SOV.class)) {
									ItemStack stack = entry.get().asItem().getDefaultInstance();
									stack.setCount(1);
									if (stack.getCount() == 1 && !entry.get().asItem().equals(Items.AIR))
										event.accept(stack);
								}

							if (event.getTabKey() == TTSCreativeTabs.MAIN_TAB.getKey())
								if (f.isAnnotationPresent(MainTab.class)) {
									ItemStack stack = entry.get().asItem().getDefaultInstance();
									stack.setCount(1);
									if (stack.getCount() == 1 && !entry.get().asItem().equals(Items.AIR))
										event.accept(stack);
								}
						}

					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		Networking.registerPackets();
		event.enqueueWork(() -> {
		});
	}

	public static TTSRegistrate registrate() {
		if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("com.code.tama"))
			throw new UnsupportedOperationException("GET OFF MY LAWN! (Don't use TTS's Registrate instance, make your own.");
		return REGISTRATE;
	}
}
