package com.axedgaming.leadage.common.network;

import com.axedgaming.leadage.common.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MaskingSyncPacket {


    private final int entityId;
    private final boolean masked;

    public MaskingSyncPacket(int entityId, boolean masked) {
        this.entityId = entityId;
        this.masked = masked;
    }

    public static void encode(MaskingSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.masked);
    }

    public static MaskingSyncPacket decode(FriendlyByteBuf buf) {
        return new MaskingSyncPacket(buf.readInt(), buf.readBoolean());
    }

    public static void handle(MaskingSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = Minecraft.getInstance().level.getEntity(msg.entityId) instanceof Player p ? p : null;
            if (player == null) return;

            player.getCapability(ModCapabilities.MASKING).ifPresent(data -> {
                data.setMasked(msg.masked);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}