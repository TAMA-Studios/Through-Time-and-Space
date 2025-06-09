/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.registries.TTSTileEntities.PORTAL_TILE_ENTITY;

import com.code.tama.tts.client.models.*;
import com.code.tama.tts.client.renderers.ControlRenderer;
import com.code.tama.tts.client.renderers.monitors.MonitorPanelRenderer;
import com.code.tama.tts.client.renderers.monitors.MonitorRenderer;
import com.code.tama.tts.client.renderers.tiles.*;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.registries.TTSEntities;
import com.code.tama.tts.server.registries.TTSTileEntities;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistry {

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModernBoxModel.LAYER_LOCATION, ModernBoxModel::createBodyLayer);
        event.registerLayerDefinition(WhittakerExteriorModel.LAYER_LOCATION, WhittakerExteriorModel::createBodyLayer);
        event.registerLayerDefinition(TTCapsuleModel.LAYER_LOCATION, TTCapsuleModel::createBodyLayer);
        event.registerLayerDefinition(ColinRichmondInteriorDoors.LAYER_LOCATION, ColinRichmondInteriorDoors::createBodyLayer);
        event.registerLayerDefinition(HudolinConsole.LAYER_LOCATION, HudolinConsole::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
        event.registerEntityRenderer(TTSEntities.MODULAR_CONTROL.get(), ControlRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.EXTERIOR_TILE.get(), TardisExteriorRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.HARTNELL_DOOR.get(), HartnellDoorRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.DOOR_TILE.get(), ModernPoliceBoxInteriorDoorsRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.HUDOLIN_CONSOLE_TILE.get(), context -> new ConsoleRenderer<>(context, new HudolinConsole<>(context.bakeLayer(HudolinConsole.LAYER_LOCATION))));
        event.registerBlockEntityRenderer(PORTAL_TILE_ENTITY.get(), PortalTileEntityRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.CHAMELEON_CIRCUIT_PANEL.get(), ChameleonCircuitRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.MONITOR_TILE.get(), MonitorRenderer::new);
        event.registerBlockEntityRenderer(TTSTileEntities.MONITOR_PANEL_TILE.get(), MonitorPanelRenderer::new);
        // Register your renderer here, first value here \/ is the Tile RegistryObject \/ is the renderer
        event.registerBlockEntityRenderer(TTSTileEntities.EXAMPLE_TILE.get(), TTSModern::new);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.EXTERIOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.DOOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.MONITOR_PANEL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.MONITOR_BLOCK.get(), RenderType.cutout());

        // Rotors
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.COPPER_ROTOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.AMETHYST_ROTOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(TTSBlocks.BLUE_ROTOR.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void onRegisterNamedRenderTypes(RegisterNamedRenderTypesEvent event) {
        event.register("emmisive", RenderType.cutout(), Sheets.cutoutBlockSheet());
    }

//    @SubscribeEvent
//    public static void registerShaders(RegisterShadersEvent event) {
//        try {
//            event.registerShader(new ShaderInstance(
//                    event.getResourceProvider(),
//                    new ResourceLocation(MODID, "shaders/core/transparent_block"),
//                    DefaultVertexFormat.POSITION_TEX
//            ), TransperentModdedEntityRenderer::setShader);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load custom shader: transparent_block", e);
//        }
//    }
}
