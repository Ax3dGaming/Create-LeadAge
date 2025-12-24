package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.common.ModEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CeruseItem extends Item {

    public CeruseItem(Properties props) {
        super(props);
    }

    private int getDurationTicks() {
        return Config.CERUSE_DURATION.get() * 20;
    }

    private int getCooldownTicks() {
        return Config.CERUSE_COOLDOWN.get() * 20;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            applyEffect(player);
            applyCooldown(player);
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public InteractionResult interactLivingEntity(
            ItemStack stack,
            Player player,
            LivingEntity target,
            InteractionHand hand
    ) {
        if (!(target instanceof Player targetPlayer)) {
            return InteractionResult.PASS;
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResult.FAIL;
        }

        if (!player.level().isClientSide) {
            applyEffect(targetPlayer);
            applyCooldown(player);
            stack.shrink(1);
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    private void applyEffect(Player player) {
        player.addEffect(new MobEffectInstance(
                ModEffects.MASKING.get(),
                getDurationTicks(),
                0,
                false,
                true,
                true
        ));
    }

    private void applyCooldown(Player player) {
        player.getCooldowns().addCooldown(this, getCooldownTicks());
    }
}
