package com.axedgaming.leadage.common.utils;

import com.axedgaming.leadage.common.items.IRadioFrequencyItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class RadioInventoryHelper {
    private RadioInventoryHelper() {}

    public static ItemStack findPriorityTransmitter(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() instanceof IRadioFrequencyItem frequencyItem && frequencyItem.canTransmit(mainHand)) {
            return mainHand;
        }

        ItemStack offHand = player.getOffhandItem();
        if (offHand.getItem() instanceof IRadioFrequencyItem frequencyItem && frequencyItem.canTransmit(offHand)) {
            return offHand;
        }

        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof IRadioFrequencyItem frequencyItem && frequencyItem.canTransmit(stack)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    public static List<ItemStack> getHotbarRadios(Player player) {
        List<ItemStack> radios = new ArrayList<>();
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() instanceof IRadioFrequencyItem) {
                radios.add(stack);
            }
        }
        return radios;
    }

    public static boolean hasReceivingRadioOnFrequency(Player player, int frequency) {
        for (ItemStack stack : getHotbarRadios(player)) {
            if (stack.getItem() instanceof IRadioFrequencyItem frequencyItem && frequencyItem.getFrequency(stack) == frequency) {
                return true;
            }
        }
        return false;
    }
}
