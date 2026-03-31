package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.client.RadioClientHooks;
import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioItem extends Item implements IRadioFrequencyItem {
    public RadioItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canTransmit(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isShiftKeyDown()) {
            int next = getFrequency(stack) + 1;
            if (next > com.axedgaming.leadage.common.utils.RadioConstants.MAX_FREQUENCY) {
                next = com.axedgaming.leadage.common.utils.RadioConstants.MIN_FREQUENCY;
            }
            setFrequency(stack, next);
            player.displayClientMessage(Component.literal("Radio réglée sur " + RadioChannelHelper.formatFrequency(next)), true);
        }

        if (level.isClientSide) {
            RadioClientHooks.openFrequencyScreen(hand, RadioChannelHelper.getFrequency(stack));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Fréquence: " + RadioChannelHelper.formatFrequency(getFrequency(stack))).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Commence un message par ! pour émettre.").withStyle(ChatFormatting.GRAY));
    }
}
