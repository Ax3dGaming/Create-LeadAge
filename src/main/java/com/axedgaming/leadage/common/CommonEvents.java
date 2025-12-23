package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.capability.MaskingProvider;
import com.axedgaming.leadage.common.network.NetworkHandler;
import com.axedgaming.leadage.common.network.MaskingSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "leadage")
public class CommonEvents {

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "masking"),
                    new MaskingProvider()
            );
        }
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player target) {
            target.getCapability(ModCapabilities.MASKING).ifPresent(data -> {
                NetworkHandler.sendToPlayer(
                        event.getEntity(),
                        new MaskingSyncPacket(target.getId(), data.isMasked())
                );
            });
        }
    }
}
