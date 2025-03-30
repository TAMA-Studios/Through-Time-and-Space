package com.code.tama.mtm.server.capabilities;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityConstants {
    public static final Capability<ITARDISLevel> TARDIS_LEVEL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
}
