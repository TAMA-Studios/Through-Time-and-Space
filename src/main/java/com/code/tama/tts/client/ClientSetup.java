/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.registries.TTSTileEntities.HARTNELL_ROTOR;
import static com.code.tama.tts.server.registries.TTSTileEntities.PORTAL_TILE_ENTITY;

import com.code.tama.tts.client.models.ColinRichmondInteriorDoors;
import com.code.tama.tts.client.models.HartnellRotorModel;
import com.code.tama.tts.client.models.HudolinConsoleModel;
import com.code.tama.tts.client.models.NESSConsoleModel;
import com.code.tama.tts.client.renderers.ControlRenderer;
import com.code.tama.tts.client.renderers.monitors.CRTMonitorRenderer;
import com.code.tama.tts.client.renderers.monitors.MonitorPanelRenderer;
import com.code.tama.tts.client.renderers.monitors.MonitorRenderer;
import com.code.tama.tts.client.renderers.tiles.*;
import com.code.tama.tts.client.renderers.worlds.SkyBlock;
import com.code.tama.tts.client.renderers.worlds.effects.GallifreyEffects;
import com.code.tama.tts.client.renderers.worlds.effects.TardisSkyEffects;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.registries.TTSEntities;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.worlds.dimension.MDimensions;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import java.io.IOException;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    public static final Lazy<KeyMapping> EXTERIOR_VIEW = Lazy.of(() -> new KeyMapping(
            "tts.keybinds.exterior_view_cancel", // Will be localized using this translation
            // key
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_C, // Default key is C
            "key.categories.tts.main" // Mapping will be in the misc category
            ));

    // Event is on the mod event bus only on the physical client
    @SubscribeEvent
    public void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(EXTERIOR_VIEW.get());
    }

    @SubscribeEvent
    public static void registerSkyRenderers(RegisterDimensionSpecialEffectsEvent event) {
        // Register your custom sky renderer for the TARDIS dimension
        event.register(
                MDimensions.DimensionEffects.TARDIS_DIM_TYPE.location(),
                new TardisSkyEffects(MDimensions.TARDIS_DIM_TYPE));
        event.register(
                MDimensions.DimensionEffects.GALLIFREY_EFFECTS.location(),
                new GallifreyEffects(MDimensions.GALLIFREY_DIM_TYPE));
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(
                () -> ItemBlockRenderTypes.setRenderLayer(TTSBlocks.CHROMIUM_BLOCK.get(), RenderType.translucent()));
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.EXTERIOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.DOOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.NESS_CONSOLE_BLOCK.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.MONITOR_PANEL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.MONITOR_BLOCK.get(), RenderType.cutout());

        // Rotors
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.COPPER_ROTOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.AMETHYST_ROTOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.BLUE_ROTOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.COMPRESSED_MULTIBLOCK.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void onRegisterNamedRenderTypes(RegisterNamedRenderTypesEvent event) {
        event.register("emmisive", RenderType.cutout(), Sheets.cutoutBlockSheet());
        event.register("reflective", RenderType.cutout(), Sheets.cutoutBlockSheet());
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HartnellRotorModel.LAYER_LOCATION, HartnellRotorModel::createBodyLayer);
        event.registerLayerDefinition(
                ColinRichmondInteriorDoors.LAYER_LOCATION, ColinRichmondInteriorDoors::createBodyLayer);
        event.registerLayerDefinition(HudolinConsoleModel.LAYER_LOCATION, HudolinConsoleModel::createBodyLayer);
        event.registerLayerDefinition(NESSConsoleModel.LAYER_LOCATION, NESSConsoleModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
        BlockEntityRenderers.register(TTSTileEntities.SKY_TILE.get(), SkyTileRenderer::new);
        event.registerEntityRenderer(TTSEntities.MODULAR_CONTROL.get(), ControlRenderer::new);
        event.registerBlockEntityRenderer(
                TTSTileEntities.CHROMIUM_BLOCK_ENTITY.get(), ChromiumBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(
                TTSTileEntities.COMPRESSED_MULTIBLOCK_TILE.get(), CompressedMultiblockRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.EXTERIOR_TILE.get(), TardisExteriorRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.HARTNELL_DOOR.get(), HartnellDoorRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.DOOR_TILE.get(), InteriorDoorRenderer::new);
        event.registerBlockEntityRenderer(
                TTSTileEntities.HUDOLIN_CONSOLE_TILE.get(),
                context -> new HudolinConsoleRenderer<>(
                        context, new HudolinConsoleModel<>(context.bakeLayer(HudolinConsoleModel.LAYER_LOCATION))));

        event.registerBlockEntityRenderer(
                TTSTileEntities.NESS_CONSOLE_TILE.get(),
                context -> new NESSConsoleRenderer<>(
                        context, new NESSConsoleModel<>(context.bakeLayer(NESSConsoleModel.LAYER_LOCATION))));

        event.registerBlockEntityRenderer(PORTAL_TILE_ENTITY.get(), PortalTileEntityRenderer::new);
        event.registerBlockEntityRenderer(
                HARTNELL_ROTOR.get(),
                context -> new HartnellRotorRenderer<>(
                        context, new HartnellRotorModel<>(context.bakeLayer(HartnellRotorModel.LAYER_LOCATION))));
        event.registerBlockEntityRenderer(TTSTileEntities.CHAMELEON_CIRCUIT_PANEL.get(), ChameleonCircuitRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.MONITOR_TILE.get(), MonitorRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.CRT_MONITOR_TILE.get(), CRTMonitorRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.MONITOR_PANEL_TILE.get(), MonitorPanelRenderer::new);
        // Register your renderer here, first value here \/ is the Tile RegistryObject
        // \/ is the renderer
        event.registerBlockEntityRenderer(TTSTileEntities.EXAMPLE_TILE.get(), ExampleRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.SONIC_CONFIGURATOR.get(), SonicConfiguratorRenderer::new);
    }

    // @SubscribeEvent
    // public static void registerShaders(RegisterShadersEvent event) {
    // try {
    // event.registerShader(new ShaderInstance(
    // event.getResourceProvider(),
    // new ResourceLocation(MODID, "shaders/core/transparent_block"),
    // DefaultVertexFormat.POSITION_TEX
    // ), TransperentModdedEntityRenderer::setShader);
    // } catch (IOException e) {
    // throw new RuntimeException("Failed to load custom shader: transparent_block",
    // e);
    // }
    // }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) {
        try {
            // Load and register the shader
            ShaderInstance shader = new ShaderInstance(
                    event.getResourceProvider(), new ResourceLocation("sky"), DefaultVertexFormat.POSITION);

            // Register the shader instance with your handler
            event.registerShader(shader, SkyBlock::setSkyShader);
        } catch (IOException ex) {
            System.err.println("Failed to load shader");
            ex.printStackTrace();
        }
    }
}
