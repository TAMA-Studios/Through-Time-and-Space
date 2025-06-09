/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.dimensions;

import com.code.tama.tts.Exteriors;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Used to sync the TARDIS Cap between the server and the client
 */
public class SyncTARDISCapPacketS2C {
    private final float LightLevel;
    private final boolean IsPoweredOn, IsInFlight, ShouldPlayRotorAnimation;
    private final BlockPos Destination, Location;
    private final ResourceKey<Level> ExteriorLevel;
    private final ResourceLocation ExteriorModelIndex;

    public SyncTARDISCapPacketS2C(float LightLevel, boolean IsPoweredOn, boolean IsInFlight, boolean ShouldPlayRotorAnimation, BlockPos Destination, BlockPos Location, ResourceKey<Level> exteriorLevel, ResourceLocation ExteriorModelIndex) {
        this.LightLevel = LightLevel;
        this.IsInFlight = IsInFlight;
        this.IsPoweredOn = IsPoweredOn;
        this.ShouldPlayRotorAnimation = ShouldPlayRotorAnimation;
        this.Destination = Destination;
        this.Location = Location;
        this.ExteriorLevel = exteriorLevel;
        this.ExteriorModelIndex = ExteriorModelIndex;
    }

    public static void encode(SyncTARDISCapPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.LightLevel);
        buffer.writeBoolean(packet.IsPoweredOn);
        buffer.writeBoolean(packet.IsInFlight);
        buffer.writeBoolean(packet.ShouldPlayRotorAnimation);
        buffer.writeBlockPos(packet.Destination);
        buffer.writeBlockPos(packet.Location);
        buffer.writeResourceKey(packet.ExteriorLevel);
        buffer.writeResourceLocation(packet.ExteriorModelIndex);
    }

    public static SyncTARDISCapPacketS2C decode(FriendlyByteBuf buffer) {
        return new SyncTARDISCapPacketS2C(
                buffer.readFloat(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBlockPos(),
                buffer.readBlockPos(),
                buffer.readResourceKey(Registries.DIMENSION),
                buffer.readResourceLocation()
        );
    }

    public static void handle(SyncTARDISCapPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(
                        cap -> {
                            cap.SetLightLevel(packet.LightLevel);
                            cap.SetExteriorLocation(new SpaceTimeCoordinate(packet.Location));
                            cap.SetDestination(new SpaceTimeCoordinate(packet.Destination));
                            cap.SetPowered(packet.IsPoweredOn);
                            cap.SetInFlight(packet.IsInFlight);
                            cap.SetPlayRotorAnimation(packet.ShouldPlayRotorAnimation);
                            cap.SetCurrentLevel(packet.ExteriorLevel);
                            cap.SetExteriorModel(Exteriors.GetByName(packet.ExteriorModelIndex));
                        });
            }
        });
        context.setPacketHandled(true);
    }
}