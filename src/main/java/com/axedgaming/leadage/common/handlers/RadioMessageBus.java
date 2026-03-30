package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import com.axedgaming.leadage.common.utils.RadioConstants;
import com.axedgaming.leadage.common.utils.RadioInventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.Set;

public final class RadioMessageBus {
    private RadioMessageBus() {}

    public static void publish(ServerPlayer sender, RadioMessage message) {
        ServerLevel level = sender.serverLevel();
        Set<ServerPlayer> delivered = new HashSet<>();

        for (ServerPlayer player : level.players()) {
            if (RadioInventoryHelper.hasReceivingRadioOnFrequency(player, message.frequency())) {
                sendToPlayer(player, message);
                delivered.add(player);
            }
        }

        for (RadioBlockEntity radioBe : RadioBlockRegistry.getRadios()) {
            if (radioBe.isRemoved() || radioBe.getLevel() != level || radioBe.getFrequency() != message.frequency()) {
                continue;
            }

            AABB area = new AABB(radioBe.getBlockPos()).inflate(RadioConstants.BROADCAST_RADIUS);
            for (ServerPlayer nearby : level.getEntitiesOfClass(ServerPlayer.class, area)) {
                if (delivered.add(nearby)) {
                    sendToPlayer(nearby, message);
                }
            }
        }

        for (RadioAnalyserBlockEntity analyserBe : RadioBlockRegistry.getAnalysers()) {
            if (analyserBe.isRemoved() || analyserBe.getLevel() != level || analyserBe.getFrequency() != message.frequency()) {
                continue;
            }

            if (analyserBe.matches(message.content())) {
                analyserBe.triggerPulse();
            }
        }
    }

    private static void sendToPlayer(ServerPlayer player, RadioMessage message) {
        player.sendSystemMessage(Component.literal("[" + message.frequency() + " MHz] " + message.senderName() + ": " + message.content()));
    }
}
