/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.data;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.server.ServerLifecycleHooks;

@NoArgsConstructor
public class ProtocolData implements INBTSerializable<CompoundTag> {

    public ProtocolData(CompoundTag compoundTag) {
        this.deserializeNBT(compoundTag);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {}

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        return compoundTag;
    }

    public void EP1(Player player, ITARDISLevel tardis) {
        if (tardis.GetOwnerID().equals(player.getUUID())) {
            ServerLevel tardisLevel = ServerLifecycleHooks.getCurrentServer()
                    .getLevel(tardis.GetLevel().dimension());
            if (tardisLevel == null) return;
            tardisLevel.getEntities().getAll().forEach(ent -> {
                if (ent instanceof ServerPlayer serverPlayer) {
                    serverPlayer.sendSystemMessage(Component.translatable(
                            "tts.protocol.emergency_protocol_one.activated", player.getName(), player.getName()));
                }
            });

            if (tardis.GetExteriorTile() != null) {
                tardis.GetControlData().setHelmicRegulator(0.6f);
                tardis.GetControlData().setSimpleMode(true);
                tardis.SetDestination(new SpaceTimeCoordinate(
                        tardis.GetExteriorTile().getLevel().getSharedSpawnPos()));
            }

            tardis.Dematerialize();
        }
    }
}
