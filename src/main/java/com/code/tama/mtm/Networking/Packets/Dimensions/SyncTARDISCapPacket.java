package com.code.tama.mtm.Networking.Packets.Dimensions;

import com.code.tama.mtm.Capabilities.CapabilityConstants;
import com.code.tama.mtm.misc.SpaceTimeCoordinate;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Used to sync the TARDIS Cap between the server and the client
 */
public class SyncTARDISCapPacket {
    private final float LightLevel;
    private final boolean IsPoweredOn, IsInFlight, ShouldPlayRotorAnimation;
    private final BlockPos Destination, Location;

    public SyncTARDISCapPacket(float LightLevel, boolean IsPoweredOn, boolean IsInFlight, boolean ShouldPlayRotorAnimation, BlockPos Destination, BlockPos Location) {
        this.LightLevel = LightLevel;
        this.IsInFlight = IsInFlight;
        this.IsPoweredOn = IsPoweredOn;
        this.ShouldPlayRotorAnimation = ShouldPlayRotorAnimation;
        this.Destination = Destination;
        this.Location = Location;
    }

    public static void encode(SyncTARDISCapPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.LightLevel);
        buffer.writeBoolean(packet.IsPoweredOn);
        buffer.writeBoolean(packet.IsInFlight);
        buffer.writeBoolean(packet.ShouldPlayRotorAnimation);
        buffer.writeBlockPos(packet.Destination);
        buffer.writeBlockPos(packet.Location);

    }

    public static SyncTARDISCapPacket decode(FriendlyByteBuf buffer) {
        return new SyncTARDISCapPacket(
                buffer.readFloat(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBlockPos(),
                buffer.readBlockPos()
        );
    }

    public static void handle(SyncTARDISCapPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
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
                        });
            }
        });
        context.setPacketHandled(true);
    }
}