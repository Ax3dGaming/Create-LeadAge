package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LeadAge.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LEAD_AGE_TAB =
            TABS.register("leadage",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.leadage"))
                            .icon(() -> ModItems.RAW_GALENA.get().getDefaultInstance())
                            .displayItems((params, output) -> {

                                /* Items */
                                output.accept(ModItems.RAW_GALENA);
                                output.accept(ModItems.CRUSHED_GALENA);
                                output.accept(ModItems.LEAD_INGOT);
                                output.accept(ModItems.LEAD_NUGGET);
                                output.accept(ModItems.LEAD_SHEET);
                                output.accept(ModItems.LEAD_SALT);
                                output.accept(ModItems.CERUSE);
                                output.accept(ModItems.DIVING_WEIGHT);
                                output.accept(ModItems.INCOMPLETE_LEAD_SOLDIER_HEAD);
                                output.accept(ModItems.LEAD_SOLDER_HEAD);
                                output.accept(ModItems.LEAD_SOLDIER_SPAWN_EGG);

                                /* Tools & Armor */
                                output.accept(ModItems.LEAD_PICKAXE);
                                output.accept(ModItems.LEAD_AXE);
                                output.accept(ModItems.LEAD_SHOVEL);
                                output.accept(ModItems.LEAD_SWORD);
                                output.accept(ModItems.LEAD_HOE);

                                /* Blocks */
                                output.accept(ModItems.LEAD_GLANCE.get());
                                output.accept(ModItems.LEAD_BLOCK.get());
                                output.accept(ModItems.LEAD_BRICKS.get());
                                output.accept(ModItems.LEAD_BRICK_STAIRS.get());
                                output.accept(ModItems.LEAD_BRICK_SLAB.get());
                                output.accept(ModItems.LEAD_TILES.get());
                                output.accept(ModItems.LEAD_PLATES.get());
                                output.accept(ModItems.LEAD_GLASS.get());
                                output.accept(ModItems.POLISHED_LEAD_GLASS.get());
                                output.accept(ModItems.TILED_LEAD_GLASS.get());
                                output.accept(ModItems.LEAD_BARS.get());
                                output.accept(ModItems.LEAD_CASING.get());
                            })
                            .build()
            );

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}