package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.items.DivingWeightItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID)
public class HeavyPlayerHandler {

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        boolean doesIgnoreCreative = Config.DIVING_WEIGHT_IGNORE_CREATIVE.get();
        if (!(event.getEntity() instanceof Player player)) return;
        if ((doesIgnoreCreative && player.isCreative()) || player.isSpectator()) return;

        int maxWeight = Config.MAX_DIVING_WEIGHT.get();
        double minJump = Config.MIN_JUMP_VELOCITY.get();

        int weight = countWeights(player, maxWeight);
        if (weight <= 0) return;

        float factor = Mth.clamp(
                1.0f - (weight / (float) maxWeight),
                0.05f,
                1.0f
        );

        Vec3 motion = player.getDeltaMovement();
        double newY = Math.max(motion.y * factor, minJump);

        player.setDeltaMovement(motion.x, newY, motion.z);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        boolean doesIgnoreCreative = Config.DIVING_WEIGHT_IGNORE_CREATIVE.get();
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.isInWater()) return;
        if ((doesIgnoreCreative && player.isCreative())) return;

        int maxWeight = Config.MAX_DIVING_WEIGHT.get();
        int weight = countWeights(player, maxWeight);
        if (weight <= 0) return;

        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(
                motion.x,
                motion.y - (0.06 * weight / maxWeight),
                motion.z
        );
    }

    private static int countWeights(Player player, int maxWeight) {
        int count = 0;

        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() instanceof DivingWeightItem) {
                count += stack.getCount();
            }
        }

        return Math.min(count, maxWeight);
    }
}