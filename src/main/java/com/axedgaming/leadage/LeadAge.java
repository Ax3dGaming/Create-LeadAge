package com.axedgaming.leadage;

import com.axedgaming.leadage.common.*;
import com.axedgaming.leadage.common.network.NetworkHandler;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
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

import static com.axedgaming.leadage.common.ModBlocks.LEAD_CASING;

@Mod(LeadAge.MOD_ID)
public class LeadAge {
    public static final String MOD_ID = "leadage";

    public static final CreateRegistrate REGISTRATE =
            CreateRegistrate.create(MOD_ID);

    public static final Logger LOGGER = LogManager.getLogger();

    public LeadAge(FMLJavaModLoadingContext context) {

        IEventBus modBus = context.getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        REGISTRATE.registerEventListeners(modBus);

        ModEffects.MOD_EFFECT.register(modBus);
        ModRecipes.SERIALIZERS.register(modBus);
        ModRecipes.TYPES.register(modBus);
        ModEntities.ENTITIES.register(modBus);

        ModGameRules.register();
        ModBlocks.register();
        ModItems.register();

        NetworkHandler.register();

        ModCreativeTab.register(modBus);

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

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
