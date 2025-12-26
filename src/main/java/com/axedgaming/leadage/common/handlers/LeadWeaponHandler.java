package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.common.ModTags;
import com.axedgaming.leadage.common.ModCapabilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LeadWeaponHandler {

    @SubscribeEvent
    public static void onHit(LivingHurtEvent event) {
        Entity src = event.getSource().getEntity();
        if (!(src instanceof Player attacker)) return;

        LivingEntity target = event.getEntity();
        if (target.level().isClientSide()) return;

        if (!attacker.getMainHandItem().is(ModTags.Items.LEAD_TOOLS)) return;

        RandomSource rand = attacker.getRandom();

        if (target instanceof ServerPlayer sp) {
            sp.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
                data.add(Config.SATURNISM_HIT.get());
            });
        }

        float nauseaChance = Config.LEAD_NAUSEA_CHANCE.get() / 100f;

        if (rand.nextFloat() < nauseaChance) {
            target.addEffect(new MobEffectInstance(
                    MobEffects.CONFUSION,
                    20 * Config.LEAD_NAUSEA_DURATION.get(),
                    0
            ));
        }

        float poisonChance = Config.LEAD_POISON_CHANCE.get() / 100f;

        if (rand.nextFloat() < poisonChance) {
            target.addEffect(new MobEffectInstance(
                    MobEffects.POISON,
                    20 * Config.LEAD_POISON_DURATION.get(),
                    0
            ));
        }
    }
}