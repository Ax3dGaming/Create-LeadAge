package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.common.utils.RadioInventoryHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.network.chat.Component;

@Mod.EventBusSubscriber(modid = "leadage")
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

        // on consomme quand même le message public
        event.setCanceled(true);

        if (radioMessage.isEmpty()) {
            player.sendSystemMessage(Component.literal("§7[Radio] Message vide."));
            return;
        }

        ItemStack transmitter = RadioInventoryHelper.findPriorityTransmitRadio(player);
        if (transmitter.isEmpty()) {
            player.sendSystemMessage(Component.literal("§7[Radio] Aucune radio émettrice trouvée."));
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