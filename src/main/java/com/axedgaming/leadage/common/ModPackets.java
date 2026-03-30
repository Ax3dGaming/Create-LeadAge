package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.network.SetRadioFrequencyPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class ModPackets {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private ModPackets() {}

    public static void register() {
        INSTANCE.registerMessage(packetId++, SetRadioFrequencyPacket.class,
                SetRadioFrequencyPacket::encode,
                SetRadioFrequencyPacket::decode,
                SetRadioFrequencyPacket::handle);
    }
}
