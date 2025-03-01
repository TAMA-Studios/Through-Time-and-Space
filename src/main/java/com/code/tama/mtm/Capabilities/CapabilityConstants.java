package com.code.tama.mtm.Capabilities;

import com.code.tama.mtm.Capabilities.Interfaces.ITARDISLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityConstants {
    public static final Capability<ITARDISLevel> TARDIS_LEVEL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
}
