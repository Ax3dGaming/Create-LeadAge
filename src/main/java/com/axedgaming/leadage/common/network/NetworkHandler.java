package com.axedgaming.leadage.common.network;

import com.axedgaming.leadage.LeadAge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    private static int index = 0;

    public static void register() {
        CHANNEL.registerMessage(
                index++,
                MaskingSyncPacket.class,
                MaskingSyncPacket::encode,
                MaskingSyncPacket::decode,
                MaskingSyncPacket::handle
        );
    }

    public static void sendToTracking(Player player, Object msg) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> player), msg);
    }

    public static void sendToPlayer(Player player, Object msg) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), msg);
    }
}
