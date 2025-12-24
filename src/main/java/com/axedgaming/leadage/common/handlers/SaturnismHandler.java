package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.ModCapabilities;
import com.axedgaming.leadage.common.ModGameRules;
import com.axedgaming.leadage.common.ModTags;
import com.axedgaming.leadage.common.ModEffects;
import com.axedgaming.leadage.common.network.NetworkHandler;
import com.axedgaming.leadage.common.network.SaturnismSyncPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID)
public class SaturnismHandler {

    private static boolean hasLead(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.is(ModTags.Items.LEAD)) return true;
        }
        for (ItemStack stack : player.getInventory().armor) {
            if (!stack.isEmpty() && stack.is(ModTags.Items.LEAD)) return true;
        }
        if (!player.getOffhandItem().isEmpty() && player.getOffhandItem().is(ModTags.Items.LEAD)) return true;
        return false;
    }

    private static boolean isLeadPoisonedFood(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (stack.is(ModTags.Items.LEAD_POISONED)) return true;
        return stack.hasTag() && stack.getTag().getBoolean("lead_poisoned");
    }

    private static void sync(ServerPlayer player, int value) {
        NetworkHandler.sendTo(player, new SaturnismSyncPacket(player.getId(), value));
    }

    private static void refreshEffect(ServerPlayer player, int value) {
        int threshold = Config.SATURNISM_EFFECT_THRESHOLD.get();
        if (value >= threshold) {
            player.addEffect(new MobEffectInstance(
                    ModEffects.SATURNISM.get(),
                    60,
                    0,
                    true,
                    false,
                    true
            ));
        } else {
            player.removeEffect(ModEffects.SATURNISM.get());
        }
    }

    private static void addSaturnism(ServerPlayer player, int amount) {
        player.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            int before = data.get();
            data.add(amount);
            int after = data.get();
            if (after != before) {
                sync(player, after);
                refreshEffect(player, after);
            }
        });
    }

    private static void setSaturnism(ServerPlayer player, int value) {
        player.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            int before = data.get();
            data.set(value);
            int after = data.get();
            if (after != before) {
                sync(player, after);
                refreshEffect(player, after);
            }
        });
    }

    private static void actionBarWake(ServerPlayer player, int value) {
        if (!player.level().getGameRules().getBoolean(ModGameRules.SATURNISM_DATA_WHEN_WAKING_UP)) return;

        Component msg;
        if (value <= 0) msg = Component.translatable("message.leadage.saturnism.none");
        else if (value < 25) msg = Component.translatable("message.leadage.saturnism.low", value);
        else if (value < 60) msg = Component.translatable("message.leadage.saturnism.mid", value);
        else msg = Component.translatable("message.leadage.saturnism.high", value);

        player.displayClientMessage(msg, true);
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(com.axedgaming.leadage.common.capability.SaturnismProvider.ID,
                    new com.axedgaming.leadage.common.capability.SaturnismProvider());
        }
    }

    @SubscribeEvent
    public static void clone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer newP)) return;
        if (!(event.getOriginal() instanceof ServerPlayer oldP)) return;

        oldP.getCapability(ModCapabilities.SATURNISM).ifPresent(oldData -> {
            newP.getCapability(ModCapabilities.SATURNISM).ifPresent(newData -> {
                newData.set(oldData.get());
                newData.setMinuteTicks(oldData.getMinuteTicks());
            });
        });

        newP.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            sync(newP, data.get());
            refreshEffect(newP, data.get());
        });
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        player.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            sync(player, data.get());
            refreshEffect(player, data.get());
        });
    }

    @SubscribeEvent
    public static void passiveTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        player.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            int t = data.getMinuteTicks() + 1;
            if (t >= 1200) {
                t = 0;
                if (hasLead(player)) {
                    addSaturnism(player, Config.SATURNISM_PASSIVE.get());
                }
            }
            data.setMinuteTicks(t);
        });
    }

    @SubscribeEvent
    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        var inv = event.getInventory();
        boolean usedLead = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (!s.isEmpty() && s.is(ModTags.Items.LEAD)) {
                usedLead = true;
                break;
            }
        }

        if (usedLead) {
            addSaturnism(player, Config.SATURNISM_CRAFT.get());
        }
    }

    @SubscribeEvent
    public static void onSmelt(PlayerEvent.ItemSmeltedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStack input = null;
        try {
            input = (ItemStack) event.getClass().getMethod("getSmelting").invoke(event);
        } catch (Throwable ignored) {}

        if (input != null && !input.isEmpty() && input.is(ModTags.Items.LEAD)) {
            addSaturnism(player, Config.SATURNISM_CRAFT.get());
        }
    }

    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        ItemStack eaten = event.getItem();
        boolean offhandLead = !player.getOffhandItem().isEmpty() && player.getOffhandItem().is(ModTags.Items.LEAD);

        if (isLeadPoisonedFood(eaten) || offhandLead) {
            addSaturnism(player, Config.SATURNISM_FOOD.get());
        }
    }

    @SubscribeEvent
    public static void onWake(PlayerWakeUpEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        player.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            int current = data.get();
            if (current <= 0) {
                actionBarWake(player, 0);
                return;
            }

            int reduction = hasLead(player)
                    ? Config.SATURNISM_SLEEP_WITH_LEAD.get()
                    : Config.SATURNISM_SLEEP.get();

            int next = Math.max(0, current - reduction);
            if (next != current) {
                setSaturnism(player, next);
            }

            actionBarWake(player, next);
        });
    }
}