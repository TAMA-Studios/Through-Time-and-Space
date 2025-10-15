/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis;

import com.code.tama.triggerapi.codec.Codecs;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.PlayerPosition;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapVariantPacketC2S;
import com.code.tama.tts.server.registries.tardis.ExteriorsRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TARDISData {
    public static final Codec<TARDISData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.FLOAT.fieldOf("lightLevel").forGetter(TARDISData::getLightLevel),
                    Codec.unboundedMap(Codecs.UUID_CODEC, PlayerPosition.CODEC)
                            .fieldOf("viewingPlayerPositions")
                            .forGetter(TARDISData::getViewingPlayerMap),
                    Codecs.UUID_CODEC
                            .optionalFieldOf("ownerUUID")
                            .xmap(opt -> opt.orElse(null), Optional::ofNullable)
                            .forGetter(TARDISData::getOwnerUUID),
                    ExteriorModelContainer.CODEC
                            .optionalFieldOf("exteriorModelID", ExteriorsRegistry.Get(0))
                            .forGetter(TARDISData::getExteriorModel),
                    Codec.BOOL.fieldOf("isPowered").forGetter(TARDISData::isPowered),
                    Codec.BOOL.fieldOf("isDiscoMode").forGetter(TARDISData::isIsDiscoMode),
                    DoorData.CODEC.fieldOf("interiorDoorData").forGetter(TARDISData::getInteriorDoorData),
                    SubsystemsData.CODEC.fieldOf("subsystemsData").forGetter(TARDISData::getSubSystemsData),
                    ControlParameters.CODEC.fieldOf("controlData").forGetter(TARDISData::getControlData),
                    ProtocolData.CODEC.fieldOf("protocolData").forGetter(TARDISData::getProtocolsData),
                    Codec.LONG.fieldOf("ticks").forGetter(TARDISData::getTicks),
                    SpaceTimeCoordinate.CODEC.fieldOf("doorBlock").forGetter(TARDISData::getDoorBlock),
                    ResourceLocation.CODEC.fieldOf("vortex").forGetter(TARDISData::getVortex))
            .apply(instance, TARDISData::new));

    ITARDISLevel TARDIS;
    float LightLevel;
    Map<UUID, PlayerPosition> ViewingPlayerMap = new HashMap<>();
    UUID OwnerUUID;
    ExteriorModelContainer ExteriorModel = ExteriorsRegistry.EXTERIORS.get(0);
    boolean Powered, IsDiscoMode;
    DoorData InteriorDoorData = new DoorData(0, new SpaceTimeCoordinate(BlockPos.ZERO), 0);
    SubsystemsData SubSystemsData = new SubsystemsData();
    ControlParameters ControlData = new ControlParameters();
    ProtocolData ProtocolsData = new ProtocolData();
    private long ticks = 0;
    SpaceTimeCoordinate doorBlock = new SpaceTimeCoordinate();
    ResourceLocation Vortex = new ResourceLocation(TTSMod.MODID, "textures/rift/infiniteabyssofnothingness");

    public TARDISData(TARDISLevelCapability TARDIS) {
        this.TARDIS = TARDIS;
    }

    public TARDISData(
            float lightLevel,
            Map<UUID, PlayerPosition> viewingPlayerMap,
            UUID ownerUUID,
            ExteriorModelContainer exteriorModelID,
            boolean powered,
            boolean isDiscoMode,
            DoorData interiorDoorData,
            SubsystemsData subSystemsData,
            ControlParameters controlData,
            ProtocolData protocolsData,
            long ticks,
            SpaceTimeCoordinate doorBlock,
            ResourceLocation vortex) {
        LightLevel = lightLevel;
        ViewingPlayerMap = viewingPlayerMap;
        OwnerUUID = ownerUUID;
        ExteriorModel = exteriorModelID;
        Powered = powered;
        this.doorBlock = doorBlock;
        IsDiscoMode = isDiscoMode;
        InteriorDoorData = interiorDoorData;
        SubSystemsData = subSystemsData;
        ControlData = controlData;
        ProtocolsData = protocolsData;
        this.ticks = ticks;
        if (vortex.equals(new ResourceLocation("", "")))
            this.Vortex = new ResourceLocation(TTSMod.MODID, "textures/rift/infiniteabyssofnothingness.png");
        else this.Vortex = vortex;
    }

    public boolean IsViewingTARDIS(UUID player) {
        return this.ViewingPlayerMap.containsKey(player);
    }

    public void SetViewing(UUID player, PlayerPosition position) {
        this.ViewingPlayerMap.put(player, position);
    }

    public DoorData getDoorData() {
        if (this.InteriorDoorData == null) {
            this.InteriorDoorData = new DoorData(0, new SpaceTimeCoordinate(new BlockPos(0, 128, 0)), 0);
        }
        return this.InteriorDoorData;
    }

    public void SetPowered(boolean IsPoweredOn) {
        this.Powered = IsPoweredOn;
        if (this.TARDIS.GetFlightData().isInFlight() && !IsPoweredOn) {
            this.TARDIS.Crash();
        }
    }

    public ExteriorModelContainer getExteriorModel() {
        if (this.ExteriorModel == null) {
            Networking.sendToServer(
                    new TriggerSyncCapVariantPacketC2S(this.TARDIS.GetLevel().dimension()));
            return ExteriorsRegistry.EXTERIORS.get(0);
        }
        return this.ExteriorModel;
    }

    public void SetExteriorVariant(ExteriorModelContainer model) {
        this.ExteriorModel = model;
        if (this.TARDIS.GetExteriorTile() != null) {
            this.TARDIS.GetExteriorTile().Model = model;
            this.TARDIS.GetExteriorTile().setModelIndex(model.getModel());
            this.TARDIS.GetExteriorTile().NeedsClientUpdate();
            this.TARDIS.GetExteriorTile().setChanged();
        }
    }

    public void CycleVariant() {
        this.ExteriorModel = ExteriorsRegistry.Cycle(this.ExteriorModel);
    }

    public void SetLightLevel(float LightLevel) {
        LightLevel = Math.max(Math.min(LightLevel, 1.5F), 0.1F);
        this.LightLevel = LightLevel;
    }

    @Nullable Player GetOwner() {
        if (this.TARDIS.GetLevel().isClientSide) return null;
        //        return this.level.getServer().overworld().getPlayerByUUID(this.GetOwnerID());
        return this.TARDIS.GetLevel().getServer().getPlayerList().getPlayer(this.getOwnerUUID());
    }
}
