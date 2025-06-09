/* (C) TAMA Studios 2025 */
package com.code.tama.tts;

import static com.code.tama.tts.server.registries.TTSBlocks.BLOCKS;
import static com.code.tama.tts.server.registries.TTSCreativeTabs.CREATIVE_MODE_TABS;
import static com.code.tama.tts.server.registries.TTSItems.DIMENSIONAL_ITEMS;
import static com.code.tama.tts.server.registries.TTSItems.ITEMS;
import static com.code.tama.tts.server.registries.TTSTileEntities.TILE_ENTITIES;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.code.tama.triggerapi.AnnotationUtils;
import com.code.tama.triggerapi.FileHelper;
import com.code.tama.triggerapi.TriggerAPI;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer;
import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.core.annotations.DimensionalTab;
import com.code.tama.tts.core.annotations.MainTab;
import com.code.tama.tts.server.dimensions.Biomes;
import com.code.tama.tts.server.loots.ModLootModifiers;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.registries.TTSCreativeTabs;
import com.code.tama.tts.server.registries.TTSEntities;
import com.code.tama.tts.server.registries.UICategoryRegistry;
import com.code.tama.tts.server.registries.UIComponentRegistry;
import com.code.tama.tts.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.tts.server.threads.ExteriorTileTickThread;
import com.code.tama.tts.server.threads.SkyboxRenderThread;
import com.code.tama.tts.server.worlds.biomes.MTerrablender;
import com.code.tama.tts.server.worlds.biomes.surface.MSurfaceRules;
import com.code.tama.tts.server.worlds.tree.ModFoliagePlacers;
import com.code.tama.tts.server.worlds.tree.ModTrunkPlacerTypes;
import com.mojang.logging.LogUtils;
import org.apache.logging.log4j.Logger;
import terrablender.api.SurfaceRuleManager;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TTSMod.MODID)
@SuppressWarnings("removal")
public class TTSMod {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "tts";
    public static final Logger LOGGER = com.code.tama.triggerapi.Logger.LOGGER;
    public static final org.slf4j.Logger LOGGER_SLF4J = LogUtils.getLogger();
    public static ArrayList<AbstractSoundScheme> SoundSchemes = new ArrayList<>();
    public static SkyboxRenderThread skyboxRenderThread = new SkyboxRenderThread();
    public static ExteriorTileTickThread exteriorTileTickThread = new ExteriorTileTickThread();
    public static TriggerAPI triggerAPI;

    public TTSMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        CustomLevelRenderer.Register();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    MinecraftForge.EVENT_BUS.register(CustomLevelRenderer.class);
                }
        );

        triggerAPI = new TriggerAPI(modEventBus);

        FileHelper.createStoredFile("last_time_launched", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm")));

        // Register Blocks, Items, Dimensions etc...
        BLOCKS.register(modEventBus);

        ITEMS.register(modEventBus);

        DIMENSIONAL_ITEMS.register(modEventBus);

        TILE_ENTITIES.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);

        TTSEntities.ENTITY_TYPES.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        TTSSounds.register(modEventBus);

        UICategoryRegistry.register(modEventBus);

        UIComponentRegistry.register(modEventBus);

        exteriorTileTickThread.start();

        ModTrunkPlacerTypes.register(modEventBus);

        ModFoliagePlacers.register(modEventBus);

        MTerrablender.registerBiomes();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        Biomes.BIOME_MODIFIERS.register(modEventBus);

        Biomes.CHUNK_GENERATORS.register(modEventBus);

        skyboxRenderThread.start();

        // TODO: Finish the config and find a use for it
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Networking.registerPackets();
        event.enqueueWork(() -> {
            LOGGER.info("Surface Rules Added");
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, MSurfaceRules.makeRules());
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        for (RegistryObject<Item> item : ITEMS.getEntries()) {
            if (AnnotationUtils.hasAnnotation(DimensionalTab.class, item)) {
                if (event.getTabKey() == TTSCreativeTabs.DIMENSIONAL_TAB.getKey()) event.accept(item);
            }
            if (AnnotationUtils.hasAnnotation(MainTab.class, item)) {
                if (event.getTabKey() == TTSCreativeTabs.MAIN_TAB.getKey()) event.accept(item);
            }
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server Starting");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartedEvent event) {
        LOGGER.info("Server Started!");
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        LOGGER.info("Server Stopping");
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        LOGGER.info("Server Stopped!");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Inside OnClientSetup");
            MinecraftForge.EVENT_BUS.register(CameraShakeHandler.class);
            LOGGER.info("Camera Shake Handler Registered");
            event.enqueueWork(() -> {
            });
        }
    }
}
