package com.code.tama.mtm.client;

import com.code.tama.mtm.client.models.*;
import com.code.tama.mtm.client.renderers.*;
import com.code.tama.mtm.client.renderers.monitors.MonitorPanelRenderer;
import com.code.tama.mtm.client.renderers.monitors.MonitorRenderer;
import com.code.tama.mtm.server.registries.MTMBlocks;
import com.code.tama.mtm.server.registries.MTMEntities;
import com.code.tama.mtm.server.registries.MTMTileEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

import static com.code.tama.mtm.MTMMod.MODID;
import static com.code.tama.mtm.server.registries.MTMTileEntities.PORTAL_TILE_ENTITY;

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
        event.registerEntityRenderer(MTMEntities.MODULAR_CONTROL.get(), ControlRenderer::new);
                event.registerBlockEntityRenderer(MTMTileEntities.EXTERIOR_TILE.get(), TardisExteriorRenderer::new);
        event.registerBlockEntityRenderer(MTMTileEntities.DOOR_TILE.get(), ModernPoliceBoxInteriorDoorsRenderer::new);
        event.registerBlockEntityRenderer(MTMTileEntities.HUDOLIN_CONSOLE_TILE.get(), context -> new ConsoleRenderer<>(context, new HudolinConsole<>(context.bakeLayer(HudolinConsole.LAYER_LOCATION))));
        event.registerBlockEntityRenderer(PORTAL_TILE_ENTITY.get(), PortalTileEntityRenderer::new);
        event.registerBlockEntityRenderer(MTMTileEntities.CHAMELEON_CIRCUIT_PANEL.get(), ChameleonCircuitRenderer::new);
        event.registerBlockEntityRenderer(MTMTileEntities.MONITOR_TILE.get(), MonitorRenderer::new);
        event.registerBlockEntityRenderer(MTMTileEntities.MONITOR_PANEL_TILE.get(), MonitorPanelRenderer::new);
        // Register your renderer here, first value here \/ is the Tile RegistryObject \/ is the renderer
        event.registerBlockEntityRenderer(MTMTileEntities.EXAMPLE_TILE.get(), MTMModern::new);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.EXTERIOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.DOOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.HUDOLIN_CONSOLE_BLOCK.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.MONITOR_PANEL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.MONITOR_BLOCK.get(), RenderType.cutout());

        // Rotors
        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.COPPER_ROTOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.AMETHYST_ROTOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MTMBlocks.BLUE_ROTOR.get(), RenderType.translucent());
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

