package com.code.tama.mtm.server.threads;

import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.networking.packets.C2S.exterior.TriggerSyncExteriorVariantPacketC2S;
import com.code.tama.mtm.server.tileentities.ExteriorTile;

public class GetExteriorVariantThread extends Thread {
    ExteriorTile tile;

    public GetExteriorVariantThread(ExteriorTile tile) {
        this.tile = tile;
    }

    @Override
    public void run() {
        super.run();
        this.tile.ThreadWorking = true;
        if(tile.getLevel() == null) return;
        if (tile.getLevel() == null) tile.Variant = ExteriorVariants.Get(0);
        if (tile.getLevel().isClientSide && tile.Variant == null)
            Networking.sendToServer(new TriggerSyncExteriorVariantPacketC2S(
                    tile.getLevel().dimension(),
                    tile.getBlockPos().getX(),
                    tile.getBlockPos().getY(),
                    tile.getBlockPos().getZ()
            ));
        if (tile.Variant == null) tile.Variant = ExteriorVariants.Get(0);

        //TODO: Uncomment this when the variant cycling logic is implemented
//        if(tile.Variant.GetModelName() != tile.getModelIndex()) {
//            while (tile.Variant.GetModelName() != tile.getModelIndex()) {
//                tile.CycleVariant();
//            }
//            tile.setChanged();
//            tile.NeedsClientUpdate();
//        }
        this.tile.ThreadWorking = false;
    }
}
