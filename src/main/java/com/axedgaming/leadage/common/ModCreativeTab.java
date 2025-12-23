package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LeadAge.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LEAD_AGE_TAB = CREATIVE_MODE_TAB.register("leadagetab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.leadagetab"))
                    .icon(ModItems.RAW_GALENA.get()::getDefaultInstance)
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.LEAD_GLANCE.get());
                        output.accept(ModItems.RAW_GALENA.get());
                        output.accept(ModItems.CRUSHED_GALENA.get());
                        output.accept(ModItems.LEAD_INGOT.get());
                        output.accept(ModItems.LEAD_NUGGET.get());
                        output.accept(ModItems.LEAD_SALT.get());
                        output.accept(ModItems.LEAD_SHEET.get());
                        output.accept(ModItems.CERUSE.get());
                        output.accept(ModItems.DIVING_WEIGHT.get());
                        output.accept(ModItems.LEAD_BLOCK.get());
                        output.accept(ModItems.LEAD_BRICKS.get());
                        output.accept(ModItems.LEAD_BRICK_STAIRS.get());
                        output.accept(ModItems.LEAD_BRICK_SLAB.get());
                        output.accept(ModItems.LEAD_TILES.get());
                        output.accept(ModItems.LEAD_PLATES.get());
                        output.accept(ModItems.LEAD_GLASS.get());
                        output.accept(ModItems.POLISHED_LEAD_GLASS.get());
                        output.accept(ModItems.TILED_LEAD_GLASS.get());
                    }).build());

    public static void register(EventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
