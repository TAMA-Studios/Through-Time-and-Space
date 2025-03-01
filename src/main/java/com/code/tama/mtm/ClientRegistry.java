package com.code.tama.mtm;

import com.code.tama.mtm.Blocks.MBlocks;
import com.code.tama.mtm.Client.Models.ColinRichmondInteriorDoors;
import com.code.tama.mtm.Client.Models.HudolinConsole;
import com.code.tama.mtm.Client.Models.ModernBoxModel;
import com.code.tama.mtm.Client.Renderer.*;
import com.code.tama.mtm.Entities.MEntities;
import com.code.tama.mtm.TileEntities.ConsoleTile;
import com.code.tama.mtm.TileEntities.MTileEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

import static com.code.tama.mtm.TileEntities.MTileEntities.PORTAL_TILE_ENTITY;
import static com.code.tama.mtm.mtm.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistry {

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModernBoxModel.LAYER_LOCATION, ModernBoxModel::createBodyLayer);
        event.registerLayerDefinition(ColinRichmondInteriorDoors.LAYER_LOCATION, ColinRichmondInteriorDoors::createBodyLayer);
        event.registerLayerDefinition(HudolinConsole.LAYER_LOCATION, HudolinConsole::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
        event.registerEntityRenderer(MEntities.MODULAR_CONTROL.get(), ControlRenderer::new);
        event.registerBlockEntityRenderer(MTileEntities.EXTERIOR_TILE.get(), ModernPoliceBoxRenderer::new);
        event.registerBlockEntityRenderer(MTileEntities.DOOR_TILE.get(), ModernPoliceBoxInteriorDoorsRenderer::new);
        event.registerBlockEntityRenderer(MTileEntities.HUDOLIN_CONSOLE_TILE.get(), context ->  new ConsoleRenderer<>(context, new HudolinConsole<ConsoleTile>(context.bakeLayer(HudolinConsole.LAYER_LOCATION))));
        event.registerBlockEntityRenderer(PORTAL_TILE_ENTITY.get(), PortalTileEntityRenderer::new);
        event.registerBlockEntityRenderer(MTileEntities.CHAMELEON_CIRCUIT_PANEL.get(), ExteriorSelector_Renderer::new);
    }

    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(MBlocks.EXTERIOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MBlocks.DOOR_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MBlocks.HUDOLIN_CONSOLE_BLOCK.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void onRegisterNamedRenderTypes(RegisterNamedRenderTypesEvent event)
    {
        event.register("emmisive", RenderType.cutout(), Sheets.cutoutBlockSheet());
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
//        try {
//            event.registerShader(new ShaderInstance(
//                    event.getResourceProvider(),
//                    new ResourceLocation(MODID, "shaders/core/transparent_block"),
//                    DefaultVertexFormat.POSITION_TEX
//            ), TransperentModdedEntityRenderer::setShader);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load custom shader: transparent_block", e);
//        }
    }
}

