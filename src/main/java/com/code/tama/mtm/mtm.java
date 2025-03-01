package com.code.tama.mtm;

import com.code.tama.TriggerAPI.FileHelper;
import com.code.tama.TriggerAPI.TriggerAPI;
import com.code.tama.mtm.Client.CameraShakeHandler;
import com.code.tama.mtm.Client.CustomLevelRenderer;
import com.code.tama.mtm.Client.Renderer.PortalTileEntityRenderer;
import com.code.tama.mtm.Client.Sounds;
import com.code.tama.mtm.Dimension.Biomes;
import com.code.tama.mtm.Entities.MEntities;
import com.code.tama.mtm.Networking.Networking;
import com.code.tama.mtm.TARDIS.FlightSoundSchemes.AbstractSoundScheme;
import com.code.tama.mtm.Threads.ExteriorTileTickThread;
import com.code.tama.mtm.Threads.SkyboxRenderThread;
import com.code.tama.mtm.World.biomes.MTerrablender;
import com.code.tama.mtm.World.biomes.surface.MSurfaceRules;
import com.code.tama.mtm.World.tree.ModFoliagePlacers;
import com.code.tama.mtm.World.tree.ModTrunkPlacerTypes;
import com.code.tama.mtm.loot.ModLootModifiers;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;
import terrablender.api.SurfaceRuleManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.code.tama.mtm.Blocks.MBlocks.BLOCKS;
import static com.code.tama.mtm.Items.MItems.DIMENSIONAL_ITEMS;
import static com.code.tama.mtm.Items.MItems.ITEMS;
import static com.code.tama.mtm.Items.MTabs.CREATIVE_MODE_TABS;
import static com.code.tama.mtm.TileEntities.MTileEntities.PORTAL_TILE_ENTITY;
import static com.code.tama.mtm.TileEntities.MTileEntities.TILE_ENTITIES;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(mtm.MODID)
@SuppressWarnings("removal")
public class mtm {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "mtm";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = com.code.tama.TriggerAPI.Logger.LOGGER;
    public static final org.slf4j.Logger LOGGER_SLF4J = LogUtils.getLogger();
    public static ArrayList<AbstractSoundScheme> SoundSchemes = new ArrayList<>();
    public static SkyboxRenderThread skyboxRenderThread = new SkyboxRenderThread();
    public static ExteriorTileTickThread exteriorTileTickThread = new ExteriorTileTickThread();
    public static TriggerAPI triggerAPI;

    public mtm() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        CustomLevelRenderer.Register();
        MinecraftForge.EVENT_BUS.addListener(CustomLevelRenderer::onRenderLevel);

        triggerAPI = new TriggerAPI();//MODID);

        FileHelper.createStoredFile("last_time_launched", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm")));

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);

        ITEMS.register(modEventBus);

        DIMENSIONAL_ITEMS.register(modEventBus);

        TILE_ENTITIES.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);

        MEntities.ENTITY_TYPES.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        Sounds.register(modEventBus);

        exteriorTileTickThread.start();

        ExteriorVariants.InitVariants();

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

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Networking.registerPackets();
        event.enqueueWork(() -> {
            LOGGER.info("Surface Rules Added");
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, MSurfaceRules.makeRules());
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
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
//            Sheets.addWoodType(ModWoodTypes.PINE);
            LOGGER.info("Inside OnClientSetup");
            MinecraftForge.EVENT_BUS.register(CameraShakeHandler.class);
            LOGGER.info("Camera Shake Handler Registered");
            event.enqueueWork(() -> {
//                DimensionSpecialEffects.EFFECTS.put(MDimensions.GALLIFREY_EFFECTS, new GallifreyEffects());
            });
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            LOGGER.info("Registering Renderers");
            event.registerBlockEntityRenderer(PORTAL_TILE_ENTITY.get(), PortalTileEntityRenderer::new);
        }
    }
}
