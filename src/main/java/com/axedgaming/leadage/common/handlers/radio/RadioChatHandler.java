package com.axedgaming.leadage.common.handlers.radio;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.utils.RadioInventoryHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.network.chat.Component;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID)
public class RadioChatHandler {

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();

        String rawMessage = event.getMessage().getString();
        if (rawMessage == null || rawMessage.isBlank()) {
            return;
        }

        if (!rawMessage.startsWith("!")) {
            return;
        }

        String radioMessage = rawMessage.substring(1).trim();

        event.setCanceled(true);

        if (radioMessage.isEmpty()) {
            player.sendSystemMessage(Component.translatable("chat.leadage.radio.empty"));
            return;
        }

        ItemStack transmitter = RadioInventoryHelper.findPriorityTransmitRadio(player);
        if (transmitter.isEmpty()) {
            player.sendSystemMessage(Component.translatable("chat.leadage.radio.not_found"));
            return;
        }

        int frequency = RadioInventoryHelper.getFrequency(transmitter);

        RadioMessageBus.broadcastPlayerRadioMessage(
                player.server,
                player,
                frequency,
                radioMessage
        );
    }
}