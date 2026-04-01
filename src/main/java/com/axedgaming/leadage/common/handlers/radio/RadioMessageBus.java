package com.axedgaming.leadage.common.handlers.radio;

import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import com.axedgaming.leadage.common.utils.RadioInventoryHelper;
import com.axedgaming.leadage.common.utils.RadioTextHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RadioMessageBus {

    public static final int RADIO_BLOCK_RANGE = 16;

    public static void broadcastPlayerRadioMessage(MinecraftServer server, ServerPlayer sender, int frequency, String message) {
        Component formatted = RadioTextHelper.formatRadioMessage(frequency, sender.getName().getString(), message);

        Set<UUID> delivered = new HashSet<>();

        for (ServerPlayer target : server.getPlayerList().getPlayers()) {
            if (RadioInventoryHelper.hasReceivingRadioOnFrequency(target, frequency)) {
                target.sendSystemMessage(formatted);
                delivered.add(target.getUUID());
            }
        }

        for (ServerLevel level : server.getAllLevels()) {
            broadcastViaPlacedRadios(level, frequency, formatted, delivered);
            notifyAnalysers(level, frequency, message);
        }
    }

    private static void broadcastViaPlacedRadios(ServerLevel level, int frequency, Component formatted, Set<UUID> delivered) {

        for (RadioBlockEntity radioBe : RadioWorldRegistry.getRadios(level)) {

            if (radioBe.isRemoved()) continue;
            if (radioBe.getFrequency() != frequency) continue;

            BlockPos pos = radioBe.getBlockPos();

            AABB area = new AABB(pos).inflate(RADIO_BLOCK_RANGE);

            for (ServerPlayer nearby : level.getEntitiesOfClass(ServerPlayer.class, area)) {
                if (delivered.add(nearby.getUUID())) {
                    nearby.sendSystemMessage(formatted);
                }
            }
        }
    }

    private static void notifyAnalysers(ServerLevel level, int frequency, String message) {

        for (RadioAnalyserBlockEntity analyser : RadioWorldRegistry.getAnalysers(level)) {

            if (analyser.isRemoved()) continue;
            if (analyser.getFrequency() != frequency) continue;

            analyser.onRadioMessageReceived(message);
        }
    }
}