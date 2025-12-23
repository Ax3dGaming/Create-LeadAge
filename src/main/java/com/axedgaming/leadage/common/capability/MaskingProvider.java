package com.axedgaming.leadage.common.capability;

import com.axedgaming.leadage.common.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class MaskingProvider implements ICapabilityProvider {

    private final MaskingData data = new MaskingData();
    private final LazyOptional<IMaskingData> optional = LazyOptional.of(() -> data);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == ModCapabilities.MASKING ? optional.cast() : LazyOptional.empty();
    }
}
