package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.Config;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SaltItem extends Item {
    public SaltItem(Properties properties) {super(properties);}

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag){

        if(!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.leadage.hint"));
            return;
        }

        int radius = Config.SALT_RADIUS.get();

        tooltip.add(Component.empty());

        tooltip.add(
                Component.translatable(
                        "tooltip.leadage.salt.summary",
                        radius
                )
        );

    }
}
