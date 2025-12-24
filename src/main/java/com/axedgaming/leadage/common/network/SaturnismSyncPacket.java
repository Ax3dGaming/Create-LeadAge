package com.axedgaming.leadage.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SaturnismSyncPacket {
    private final int entityId;
    private final int value;

    public SaturnismSyncPacket(int entityId, int value) {
        this.entityId = entityId;
        this.value = value;
    }

    public static void encode(SaturnismSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.value);
    }

    public static SaturnismSyncPacket decode(FriendlyByteBuf buf) {
        return new SaturnismSyncPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(SaturnismSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var level = Minecraft.getInstance().level;
            if (level == null) return;
            var e = level.getEntity(msg.entityId);
            if (e == null) return;

            e.getCapability(com.axedgaming.leadage.common.ModCapabilities.SATURNISM).ifPresent(data -> {
                data.set(msg.value);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}