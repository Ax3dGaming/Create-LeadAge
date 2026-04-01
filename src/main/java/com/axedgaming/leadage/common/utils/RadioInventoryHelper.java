package com.axedgaming.leadage.common.utils;

import com.axedgaming.leadage.common.items.radio.PortableRadioItem;
import com.axedgaming.leadage.common.items.radio.RadioItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RadioInventoryHelper {

    public static ItemStack findPriorityTransmitRadio(Player player) {
        ItemStack main = player.getMainHandItem();
        if (isTransmitRadio(main)) {
            return main;
        }

        ItemStack off = player.getOffhandItem();
        if (isTransmitRadio(off)) {
            return off;
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (isTransmitRadio(stack)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    public static boolean hasReceivingRadioOnFrequency(Player player, int frequency) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (isReceivingRadio(stack) && getFrequency(stack) == frequency) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTransmitRadio(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof RadioItem;
    }

    public static boolean isReceivingRadio(ItemStack stack) {
        return !stack.isEmpty() && (
                stack.getItem() instanceof RadioItem ||
                        stack.getItem() instanceof PortableRadioItem
        );
    }

    public static int getFrequency(ItemStack stack) {
        return RadioChannelHelper.getFrequency(stack);
    }
}