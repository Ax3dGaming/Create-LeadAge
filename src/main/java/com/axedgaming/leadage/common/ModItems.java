package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.items.CeruseItem;
import com.axedgaming.leadage.common.items.DivingWeightItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LeadAge.MOD_ID);

    public static final RegistryObject<Item> LEAD_GLANCE = ITEMS.register("lead_glance",
            () -> new BlockItem((Block) ModBlocks.LEAD_GLANCE.get(), new Item.Properties()));

    public static final RegistryObject<Item> RAW_GALENA = ITEMS.register("raw_galena",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CRUSHED_GALENA = ITEMS.register("crushed_galena",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEAD_SHEET =  ITEMS.register("lead_sheet",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEAD_SALT = ITEMS.register("lead_salt",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CERUSE = ITEMS.register("ceruse",
            () -> new CeruseItem(new Item.Properties()));

    public static final RegistryObject<Item> DIVING_WEIGHT = ITEMS.register("diving_weight",
            () -> new DivingWeightItem(new Item.Properties()));

    public static void registerItems(EventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
