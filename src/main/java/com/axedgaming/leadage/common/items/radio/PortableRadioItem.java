package com.axedgaming.leadage.common.items.radio;

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

public class PortableRadioItem extends Item implements IRadioFrequencyItem {
    public PortableRadioItem(Properties properties) {
        super(properties);
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
            player.displayClientMessage(Component.translatable("utils.leadage.portable_radio.set", RadioChannelHelper.formatFrequency(next)), true);
        }

        if (level.isClientSide) {
            RadioClientHooks.openItemFrequencyScreen(RadioChannelHelper.getFrequency(stack));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("utils.leadage.frequency", RadioChannelHelper.formatFrequency(getFrequency(stack))).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("utils.leadage.only_receive").withStyle(ChatFormatting.GRAY));
    }
}
