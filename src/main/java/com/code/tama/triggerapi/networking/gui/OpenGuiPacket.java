package com.code.tama.triggerapi.networking.gui;

import com.code.tama.triggerapi.gui.CustomGuiScreen;
import com.code.tama.triggerapi.gui.GuiDefinition;
import com.code.tama.triggerapi.gui.GuiLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenGuiPacket {
    private final ResourceLocation guiId;
    
    public OpenGuiPacket(ResourceLocation guiId) {
        this.guiId = guiId;
    }
    
    public static void encode(OpenGuiPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.guiId);
    }
    
    public static OpenGuiPacket decode(FriendlyByteBuf buffer) {
        return new OpenGuiPacket(buffer.readResourceLocation());
    }
    
    public static void handle(OpenGuiPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            GuiDefinition definition = GuiLoader.getGuiDefinition(packet.guiId);
            if (definition != null) {
                Minecraft.getInstance().setScreen(new CustomGuiScreen(definition, packet.guiId));
            }
        });
        context.setPacketHandled(true);
    }
}