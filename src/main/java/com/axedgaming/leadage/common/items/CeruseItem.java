package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.common.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import java.util.List;

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
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.leadage.hint"));
            return;
        }
        int duration = Config.CERUSE_DURATION.get();
        int range = Config.CERUSE_AGGRO_RANGE.get();

        tooltip.add(Component.translatable("tooltip.leadage.hint_pressed"));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.leadage.ceruse.summary"));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.leadage.when_used"));
        tooltip.add(
                Component.translatable(
                        "tooltip.leadage.ceruse.used",
                        Component.literal(String.valueOf(range)).withStyle(ChatFormatting.GREEN),
                        Component.literal(String.valueOf(duration)).withStyle(ChatFormatting.GREEN)
                )
        );
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
            playEffects((ServerLevel) level, player);
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
            playEffects((ServerLevel) player.level(), targetPlayer);
            stack.shrink(1);
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    private void applyEffect(Player player) {
        Boolean visible = Config.CERUSE_PARTICLES.get();
        player.addEffect(new MobEffectInstance(
                ModEffects.MASKING.get(),
                getDurationTicks(),
                0,
                false,
                visible,
                true
        ));
    }

    private void applyCooldown(Player player) {
        player.getCooldowns().addCooldown(this, getCooldownTicks());
    }

    private void playEffects(ServerLevel level, Player player) {
        RandomSource random = level.getRandom();
        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.UI_TOAST_IN,
                SoundSource.PLAYERS,
                0.6f,
                1.2f + random.nextFloat() * 0.2f
        );

        DustParticleOptions dust =
                new DustParticleOptions(new Vector3f(1.0f, 1.0f, 1.0f), 1.0f);

        level.sendParticles(
                dust,
                player.getX(),
                player.getY() + 1.0,
                player.getZ(),
                12,
                0.3,
                0.5,
                0.3,
                0.0
        );
    }
}