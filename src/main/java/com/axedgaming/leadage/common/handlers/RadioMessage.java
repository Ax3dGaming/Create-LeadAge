package com.axedgaming.leadage.common.handlers;

import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public record RadioMessage(UUID senderId, String senderName, int frequency, String content) {
    public static RadioMessage fromPlayer(ServerPlayer player, int frequency, String content) {
        return new RadioMessage(player.getUUID(), player.getScoreboardName(), frequency, content);
    }
}
