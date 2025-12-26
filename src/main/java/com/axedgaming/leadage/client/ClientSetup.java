package com.axedgaming.leadage.client;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.client.render.LeadSoldierRenderer;
import com.axedgaming.leadage.client.render.layer.MaskingLayer;
import com.axedgaming.leadage.common.ModBlocks;
import com.axedgaming.leadage.common.ModEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = LeadAge.MOD_ID,
        value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ClientSetup {

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (String skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new MaskingLayer(renderer));
            }
        }

        System.out.println("MaskingLayer REGISTERED");
    }

    @SubscribeEvent
    @SuppressWarnings("removal")
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.LEAD_GLASS.get(),
                    RenderType.translucent()
            );
        });

        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.POLISHED_LEAD_GLASS.get(),
                    RenderType.translucent()
            );
        });

        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.TILED_LEAD_GLASS.get(),
                    RenderType.translucent()
            );
        });

        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.LEAD_SOLDIER_HEAD.get(),
                    RenderType.cutout()
            );
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.LEAD_SOLDIER.get(), LeadSoldierRenderer::new);
    }
}