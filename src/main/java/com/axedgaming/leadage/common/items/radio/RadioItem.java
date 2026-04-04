package com.axedgaming.leadage.common.items.radio;

import com.axedgaming.leadage.client.RadioClientHooks;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioItem extends BlockItem implements IRadioFrequencyItem {
    public RadioItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean canTransmit(ItemStack stack) {
        return true;
    }

    public static boolean isLookingAtBlock(Player player, double range){
        HitResult hit = player.pick(range, 0.0F, false);
        return hit.getType() == HitResult.Type.BLOCK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isShiftKeyDown() && !isLookingAtBlock(player, 10)) {
            int next = getFrequency(stack) + 1;
            if (next > com.axedgaming.leadage.common.utils.RadioConstants.MAX_FREQUENCY) {
                next = com.axedgaming.leadage.common.utils.RadioConstants.MIN_FREQUENCY;
            }
            setFrequency(stack, next);
            player.displayClientMessage(Component.translatable("utils.leadage.radio.set", RadioChannelHelper.formatFrequency(next)), true);
        }

        if (level.isClientSide && !isLookingAtBlock(player, 10)) {
            RadioClientHooks.openFrequencyScreen(hand, RadioChannelHelper.getFrequency(stack));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("utils.leadage.frequency", RadioChannelHelper.formatFrequency(getFrequency(stack))).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("utils.leadage.send").withStyle(ChatFormatting.GRAY));
    }
}
