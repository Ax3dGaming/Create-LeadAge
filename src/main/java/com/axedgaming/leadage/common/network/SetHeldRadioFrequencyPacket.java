package com.axedgaming.leadage.common.network;

import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetHeldRadioFrequencyPacket {

    private final InteractionHand hand;
    private final int frequency;

    public SetHeldRadioFrequencyPacket(InteractionHand hand, int frequency) {
        this.hand = hand;
        this.frequency = RadioConstants.clampFrequency(frequency);
    }

    public static void encode(SetHeldRadioFrequencyPacket packet, FriendlyByteBuf buf) {
        buf.writeEnum(packet.hand);
        buf.writeInt(packet.frequency);
    }

    public static SetHeldRadioFrequencyPacket decode(FriendlyByteBuf buf) {
        return new SetHeldRadioFrequencyPacket(
                buf.readEnum(InteractionHand.class),
                buf.readInt()
        );
    }

    public static void handle(SetHeldRadioFrequencyPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }

            ItemStack stack = player.getItemInHand(packet.hand);
            if (stack.isEmpty()) {
                return;
            }

            RadioChannelHelper.setFrequency(stack, packet.frequency);
        });

        context.setPacketHandled(true);
    }
}