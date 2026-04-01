package com.axedgaming.leadage.common.network;

import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import com.axedgaming.leadage.common.items.radio.IRadioFrequencyItem;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetRadioFrequencyPacket {
    private final boolean targetBlock;
    private final BlockPos pos;
    private final int frequency;

    public SetRadioFrequencyPacket(boolean targetBlock, BlockPos pos, int frequency) {
        this.targetBlock = targetBlock;
        this.pos = pos;
        this.frequency = RadioConstants.clampFrequency(frequency);
    }

    public static void encode(SetRadioFrequencyPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.targetBlock);
        buffer.writeBlockPos(packet.pos);
        buffer.writeInt(packet.frequency);
    }

    public static SetRadioFrequencyPacket decode(FriendlyByteBuf buffer) {
        return new SetRadioFrequencyPacket(buffer.readBoolean(), buffer.readBlockPos(), buffer.readInt());
    }

    public static void handle(SetRadioFrequencyPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }

            if (packet.targetBlock) {
                if (player.level().getBlockEntity(packet.pos) instanceof RadioBlockEntity radioBe) {
                    radioBe.setFrequency(packet.frequency);
                } else if (player.level().getBlockEntity(packet.pos) instanceof RadioAnalyserBlockEntity analyserBe) {
                    analyserBe.setFrequency(packet.frequency);
                }
                return;
            }

            ItemStack held = player.getMainHandItem();
            if (held.getItem() instanceof IRadioFrequencyItem frequencyItem) {
                frequencyItem.setFrequency(held, packet.frequency);
            }
        });
        context.setPacketHandled(true);
    }
}
