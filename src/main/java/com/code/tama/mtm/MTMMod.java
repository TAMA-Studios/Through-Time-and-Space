package com.code.tama.mtm;

import com.code.tama.mtm.client.CameraShakeHandler;
import com.code.tama.mtm.client.CustomLevelRenderer;
import com.code.tama.mtm.client.ExteriorModelsHandler;
import com.code.tama.mtm.client.MTMSounds;
import com.code.tama.mtm.client.models.ModernBoxModel;
import com.code.tama.mtm.client.models.TTCapsuleModel;
import com.code.tama.mtm.client.models.WhittakerExteriorModel;
import com.code.tama.mtm.client.renderers.PortalTileEntityRenderer;
import com.code.tama.mtm.core.Constants;
import com.code.tama.mtm.core.abstractClasses.HierarchicalExteriorModel;
import com.code.tama.mtm.core.annotations.DimensionalTab;
import com.code.tama.mtm.core.annotations.MainTab;
import com.code.tama.mtm.server.dimensions.Biomes;
import com.code.tama.mtm.server.loots.ModLootModifiers;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.registries.MTMCreativeTabs;
import com.code.tama.mtm.server.registries.MTMEntities;
import com.code.tama.mtm.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.mtm.server.threads.ExteriorTileTickThread;
import com.code.tama.mtm.server.threads.SkyboxRenderThread;
import com.code.tama.mtm.server.worlds.biomes.MTerrablender;
import com.code.tama.mtm.server.worlds.biomes.surface.MSurfaceRules;
import com.code.tama.mtm.server.worlds.tree.ModFoliagePlacers;
import com.code.tama.mtm.server.worlds.tree.ModTrunkPlacerTypes;
import com.code.tama.triggerapi.AnnotationUtils;
import com.code.tama.triggerapi.FileHelper;
import com.code.tama.triggerapi.TriggerAPI;
import com.mojang.logging.LogUtils;
import lombok.Getter;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Logger;
import terrablender.api.SurfaceRuleManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.code.tama.mtm.server.registries.MTMBlocks.BLOCKS;
import static com.code.tama.mtm.server.registries.MTMCreativeTabs.CREATIVE_MODE_TABS;
import static com.code.tama.mtm.server.registries.MTMItems.DIMENSIONAL_ITEMS;
import static com.code.tama.mtm.server.registries.MTMItems.ITEMS;
import static com.code.tama.mtm.server.registries.MTMTileEntities.PORTAL_TILE_ENTITY;
import static com.code.tama.mtm.server.registries.MTMTileEntities.TILE_ENTITIES;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MTMMod.MODID)
@SuppressWarnings("removal")
public class MTMMod {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "mtm";
    public static final Logger LOGGER = com.code.tama.triggerapi.Logger.LOGGER;
    public static final org.slf4j.Logger LOGGER_SLF4J = LogUtils.getLogger();
    public static ArrayList<AbstractSoundScheme> SoundSchemes = new ArrayList<>();
    public static SkyboxRenderThread skyboxRenderThread = new SkyboxRenderThread();
    public static ExteriorTileTickThread exteriorTileTickThread = new ExteriorTileTickThread();
    @Getter
    private static final ExteriorModelsHandler<HierarchicalExteriorModel> exteriorModelsHandler = new ExteriorModelsHandler<>();
    public static TriggerAPI triggerAPI;

    public MTMMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        CustomLevelRenderer.Register();
        MinecraftForge.EVENT_BUS.addListener(CustomLevelRenderer::onRenderLevel);

        triggerAPI = new TriggerAPI();

        this.RegisterExteriorModels();

        FileHelper.createStoredFile("last_time_launched", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm")));

        // Register Blocks, Items, Dimensions etc...
        BLOCKS.register(modEventBus);

        ITEMS.register(modEventBus);

        DIMENSIONAL_ITEMS.register(modEventBus);

        TILE_ENTITIES.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);

        MTMEntities.ENTITY_TYPES.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        MTMSounds.register(modEventBus);

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

        // TODO: Finish the config and find a use for it
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static void RegisterExteriorModel(Class<? extends HierarchicalExteriorModel> modelClass, ModelLayerLocation layerLocation, ResourceLocation modelName) {
        ExteriorModelsHandler.GetInstance().AddModel(modelClass, layerLocation);
        com.code.tama.mtm.server.ExteriorModelsHandler.ModelList.add(modelName);
    }

    private void RegisterExteriorModels() {
//        ExteriorModelsHandler.GetInstance().AddModel(ModernBoxModel.class, ModernBoxModel.LAYER_LOCATION);
//        ExteriorModelsHandler.GetInstance().AddModel(WhittakerExteriorModel.class, WhittakerExteriorModel.LAYER_LOCATION);
//        ExteriorModelsHandler.GetInstance().AddModel(TTCapsuleModel.class, TTCapsuleModel.LAYER_LOCATION);
        RegisterExteriorModel(ModernBoxModel.class, ModernBoxModel.LAYER_LOCATION, Constants.ExteriorModelNames.ModernBox);
        RegisterExteriorModel(TTCapsuleModel.class, TTCapsuleModel.LAYER_LOCATION, Constants.ExteriorModelNames.TTCapsule);
        RegisterExteriorModel(WhittakerExteriorModel.class, WhittakerExteriorModel.LAYER_LOCATION, Constants.ExteriorModelNames.Whittaker);

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
                if (event.getTabKey() == MTMCreativeTabs.DIMENSIONAL_TAB.getKey()) event.accept(item);
            }
            if (AnnotationUtils.hasAnnotation(MainTab.class, item)) {
                if (event.getTabKey() == MTMCreativeTabs.MAIN_TAB.getKey()) event.accept(item);
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

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            LOGGER.info("Registering Renderers");
            event.registerBlockEntityRenderer(PORTAL_TILE_ENTITY.get(), PortalTileEntityRenderer::new);
        }
    }
}
