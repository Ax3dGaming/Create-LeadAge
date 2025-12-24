package com.axedgaming.leadage;

import com.axedgaming.leadage.client.render.layer.MaskingLayer;
import com.axedgaming.leadage.common.*;
import com.axedgaming.leadage.common.network.NetworkHandler;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LeadAge.MOD_ID)
public class LeadAge {
    public static final String MOD_ID = "leadage";

    public LeadAge(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        ModItems.ITEMS.register(modBus);
        ModCreativeTab.CREATIVE_MODE_TAB.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModEffects.MOD_EFFECT.register(modBus);
        ModRecipes.SERIALIZERS.register(modBus);
        ModRecipes.TYPES.register(modBus);
        ModGameRules.register();

        NetworkHandler.register();

        context.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
    }

    private void CommonSetup(FMLCommonSetupEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {}
    }
}
