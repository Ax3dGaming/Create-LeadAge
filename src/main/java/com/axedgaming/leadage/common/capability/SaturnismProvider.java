package com.axedgaming.leadage.common.capability;

import com.axedgaming.leadage.LeadAge;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class SaturnismProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "saturnism");

    public static final Capability<ISaturnismData> SATURNISM =
            CapabilityManager.get(new CapabilityToken<>(){});

    private final SaturnismData data = new SaturnismData();
    private final LazyOptional<ISaturnismData> optional = LazyOptional.of(() -> data);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return cap == SATURNISM ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("value", data.get());
        tag.putInt("minuteTicks", data.getMinuteTicks());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.set(nbt.getInt("value"));
        data.setMinuteTicks(nbt.getInt("minuteTicks"));
    }
}