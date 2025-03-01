package com.code.tama.mtm.Networking.Packets.Exterior;


import com.code.tama.mtm.TileEntities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Sets the alpha value of the exterior **/
public class SetExteriorAlpha {

    public float alpha;
    public BlockPos pos;

    public SetExteriorAlpha(float alpha, BlockPos pos) {
        this.alpha = alpha; this.pos = pos;
    }

    public static void encode(SetExteriorAlpha mes, FriendlyByteBuf buf) {
        buf.writeFloat(mes.alpha);
        buf.writeBlockPos(mes.pos);
    }

    public static SetExteriorAlpha decode(FriendlyByteBuf buf) {
        return new SetExteriorAlpha(buf.readFloat(), buf.readBlockPos());
    }

    public static void handle(SetExteriorAlpha mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if(context.get().getSender().level().getBlockEntity(mes.pos) instanceof ExteriorTile exteriorTile) exteriorTile.setTransparency(mes.alpha);
        });
        context.get().setPacketHandled(true);
    }
}