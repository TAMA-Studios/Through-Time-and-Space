/* (C) TAMA Studios 2025 */
package com.code.tama.tts;

import static com.code.tama.tts.server.registries.TTSBlocks.BLOCKS;
import static com.code.tama.tts.server.registries.TTSCreativeTabs.CREATIVE_MODE_TABS;
import static com.code.tama.tts.server.registries.TTSItems.DIMENSIONAL_ITEMS;
import static com.code.tama.tts.server.registries.TTSItems.ITEMS;
import static com.code.tama.tts.server.registries.TTSTileEntities.TILE_ENTITIES;

import com.code.tama.triggerapi.AnnotationUtils;
import com.code.tama.triggerapi.FileHelper;
import com.code.tama.triggerapi.TriggerAPI;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer;
import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.compat.ModCompat;
import com.code.tama.tts.server.dimensions.Biomes;
import com.code.tama.tts.server.items.tabs.DimensionalTab;
import com.code.tama.tts.server.items.tabs.MainTab;
import com.code.tama.tts.server.loots.ModLootModifiers;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.registries.*;
import com.code.tama.tts.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.tts.server.worlds.biomes.surface.MSurfaceRules;
import com.code.tama.tts.server.worlds.tree.ModFoliagePlacers;
import com.code.tama.tts.server.worlds.tree.ModTrunkPlacerTypes;
import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Logger;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TTSMod.MODID)
@SuppressWarnings("removal")
public class TTSMod {
    public static final Logger LOGGER = com.code.tama.triggerapi.Logger.LOGGER;
    public static final org.slf4j.Logger LOGGER_SLF4J = LogUtils.getLogger();
    // Define mod id in a common place for everything to reference
    public static final String MODID = "tts";
    public static ArrayList<AbstractSoundScheme> SoundSchemes = new ArrayList<>();
    public static TriggerAPI triggerAPI;

    public TTSMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        CustomLevelRenderer.Register();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.register(CustomLevelRenderer.class);
        });

        triggerAPI = new TriggerAPI(modEventBus);

        FileHelper.createStoredFile(
                "last_time_launched", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm")));

        // Register Blocks, Items, Dimensions etc...

        SonicModeRegistry.register(modEventBus);

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

        ModTrunkPlacerTypes.register(modEventBus);

        ModFoliagePlacers.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        Biomes.BIOME_MODIFIERS.register(modEventBus);

        Biomes.CHUNK_GENERATORS.register(modEventBus);

        // TODO: Finish the config and find a use for it
        // ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModCompat.Run();
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

    private void commonSetup(final FMLCommonSetupEvent event) {
        Networking.registerPackets();
        event.enqueueWork(() -> {
            LOGGER.info("Surface Rules Added");
            if (ModList.get().isLoaded("terrablender"))
                SurfaceRuleManager.addSurfaceRules(
                        SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, MSurfaceRules.makeRules());
        });
    }

    // You can use EventBusSubscriber to automatically register all static methods
    // in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MinecraftForge.EVENT_BUS.register(CameraShakeHandler.class);
            event.enqueueWork(() -> {
                Window w = Minecraft.getInstance().getWindow();
                //                                TardisBotiRenderer.init();
            });
        }
    }
}
