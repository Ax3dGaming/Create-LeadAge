package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.common.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MaskingAggroHandler {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;

        LivingEntity target = mob.getTarget();
        if (!(target instanceof Player player)) return;

        if (!player.hasEffect(ModEffects.MASKING.get())) return;

        double maxDistance = Config.CERUSE_AGGRO_RANGE.get();
        double maxDistanceSq = maxDistance * maxDistance;

        if (mob.distanceToSqr(player) > maxDistanceSq) {
            mob.setTarget(null);
        }
    }
}