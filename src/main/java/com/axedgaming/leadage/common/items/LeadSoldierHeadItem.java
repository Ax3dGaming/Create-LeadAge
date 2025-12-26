package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.common.blocks.LeadSoldierHeadBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class LeadSoldierHeadItem extends BlockItem {
    public LeadSoldierHeadItem(Block block,  Properties properties) {
        super(block, properties);
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
                        "tooltip.leadage.soldier_head.summary"
                )
        );

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.leadage.soldier_head.place"));
    }
}
