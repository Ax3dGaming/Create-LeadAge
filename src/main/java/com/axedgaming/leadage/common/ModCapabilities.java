package com.axedgaming.leadage.common;

import com.axedgaming.leadage.common.capability.IMaskingData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<IMaskingData> MASKING =
            CapabilityManager.get(new CapabilityToken<>() {});
}
