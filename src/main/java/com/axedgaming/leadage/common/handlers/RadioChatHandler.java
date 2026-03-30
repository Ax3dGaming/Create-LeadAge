package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.common.items.IRadioFrequencyItem;
import com.axedgaming.leadage.common.utils.RadioInventoryHelper;
import com.axedgaming.leadage.common.utils.RadioTextHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "leadage")
public final class RadioChatHandler {
    private RadioChatHandler() {}

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        String rawMessage = event.getMessage();
        if (!RadioTextHelper.isRadioMessage(rawMessage)) {
            return;
        }

        ServerPlayer player = event.getPlayer();
        ItemStack transmitter = RadioInventoryHelper.findPriorityTransmitter(player);

        if (transmitter.isEmpty() || !(transmitter.getItem() instanceof IRadioFrequencyItem frequencyItem)) {
            player.sendSystemMessage(Component.literal("Aucune radio utilisable trouvée pour émettre."));
            event.setCanceled(true);
            return;
        }

        String content = RadioTextHelper.stripRadioPrefix(rawMessage);
        if (content.isBlank()) {
            event.setCanceled(true);
            return;
        }

        int frequency = frequencyItem.getFrequency(transmitter);
        RadioMessageBus.publish(player, RadioMessage.fromPlayer(player, frequency, content));
        event.setCanceled(true);
    }
}
