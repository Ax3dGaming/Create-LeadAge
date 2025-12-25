package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class DivingWeightItem extends Item {

    public DivingWeightItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {

        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.leadage.hint"));
            return;
        }

        int maxWeight = Config.MAX_DIVING_WEIGHT.get();

        tooltip.add(Component.translatable("tooltip.leadage.hint_pressed"));

        tooltip.add(Component.empty());

        tooltip.add(
                Component.translatable(
                        "tooltip.leadage.diving_weight.summary",
                        Component.literal(String.valueOf(maxWeight)).withStyle(ChatFormatting.GREEN)
                )
        );

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.leadage.diving_weight.when_held"));
        tooltip.add(Component.translatable("tooltip.leadage.diving_weight.effect"));
    }
}
