package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IRadioFrequencyItem {
    default int getFrequency(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(RadioConstants.NBT_FREQUENCY)) {
            tag.putInt(RadioConstants.NBT_FREQUENCY, RadioConstants.DEFAULT_FREQUENCY);
        }
        return RadioChannelHelper.clampFrequency(tag.getInt(RadioConstants.NBT_FREQUENCY));
    }

    default void setFrequency(ItemStack stack, int frequency) {
        stack.getOrCreateTag().putInt(RadioConstants.NBT_FREQUENCY, RadioChannelHelper.clampFrequency(frequency));
    }

    default boolean canTransmit(ItemStack stack) {
        return false;
    }
}
