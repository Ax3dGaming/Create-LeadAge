package com.axedgaming.leadage.common.network;

import com.axedgaming.leadage.common.items.TicketItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetTicketMessagePacket {
    private final InteractionHand hand;
    private final String message;

    public SetTicketMessagePacket(InteractionHand hand, String message) {
        this.hand = hand;
        this.message = message == null ? "" : message;
    }

    public static void encode(SetTicketMessagePacket packet, FriendlyByteBuf buffer) {
        buffer.writeEnum(packet.hand);
        buffer.writeUtf(packet.message, 120);
    }

    public static SetTicketMessagePacket decode(FriendlyByteBuf buffer) {
        return new SetTicketMessagePacket(buffer.readEnum(InteractionHand.class), buffer.readUtf(120));
    }

    public static void handle(SetTicketMessagePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }

            ItemStack stack = player.getItemInHand(packet.hand);
            if (!(stack.getItem() instanceof TicketItem)) {
                return;
            }

            TicketItem.setMessage(stack, packet.message);
        });
        context.setPacketHandled(true);
    }
}