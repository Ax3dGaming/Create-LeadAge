package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.client.TicketClientHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TicketItem extends Item {
    public static final String TAG_MESSAGE = "Message";

    public TicketItem(Properties properties) {
        super(properties);
    }

    public static String getMessage(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return "";
        }
        return tag.getString(TAG_MESSAGE);
    }

    public static void setMessage(ItemStack stack, String message) {
        stack.getOrCreateTag().putString(TAG_MESSAGE, message == null ? "" : message);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            TicketClientHooks.openTicketScreen(hand, getMessage(stack));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String message = getMessage(stack);

        if (message.isEmpty()) {
            tooltip.add(Component.translatable("ticket.no_message").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("ticket.message", message));
        }
    }
}