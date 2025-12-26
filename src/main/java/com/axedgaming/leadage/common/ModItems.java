package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.items.CeruseItem;
import com.axedgaming.leadage.common.items.DivingWeightItem;
import com.axedgaming.leadage.common.items.LeadSoldierHeadItem;
import com.axedgaming.leadage.common.items.SaltItem;
import com.axedgaming.leadage.common.materials.LeadTier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LeadAge.MOD_ID);

    public static final RegistryObject<Item> LEAD_GLANCE = ITEMS.register("lead_glance",
            () -> new BlockItem((Block) ModBlocks.LEAD_GLANCE.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAD_BLOCK = ITEMS.register("lead_block",
            () -> new BlockItem((Block) ModBlocks.LEAD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAD_BRICKS = ITEMS.register("lead_bricks",
            () -> new BlockItem((Block) ModBlocks.LEAD_BRICKS.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAD_BRICK_STAIRS = ITEMS.register("lead_brick_stairs",
            () -> new BlockItem(ModBlocks.LEAD_BRICK_STAIRS.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAD_BRICK_SLAB = ITEMS.register("lead_brick_slab",
            () ->  new BlockItem(ModBlocks.LEAD_BRICK_SLAB.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAD_TILES = ITEMS.register("lead_tiles",
            ()  -> new BlockItem(ModBlocks.LEAD_TILES.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAD_PLATES = ITEMS.register("lead_plates",
            () ->  new BlockItem(ModBlocks.LEAD_PLATES.get(), new Item.Properties()));

    public static final RegistryObject<Item> LEAD_GLASS = ITEMS.register("lead_glass",
            () -> new BlockItem(ModBlocks.LEAD_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> POLISHED_LEAD_GLASS = ITEMS.register("polished_lead_glass",
            () -> new BlockItem(ModBlocks.POLISHED_LEAD_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> TILED_LEAD_GLASS = ITEMS.register("tiled_lead_glass",
            () -> new BlockItem(ModBlocks.TILED_LEAD_GLASS.get(), new Item.Properties()));

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
            () -> new SaltItem(new Item.Properties()));

    public static final RegistryObject<Item> CERUSE = ITEMS.register("ceruse",
            () -> new CeruseItem(new Item.Properties()));

    public static final RegistryObject<Item> DIVING_WEIGHT = ITEMS.register("diving_weight",
            () -> new DivingWeightItem(new Item.Properties()));

    public static final RegistryObject<Item> LEAD_PICKAXE = ITEMS.register("lead_pickaxe",
            () -> new PickaxeItem(
                    LeadTier.LEAD,
                    1,
                    -2.8f,
                    new Item.Properties().stacksTo(1)
            ));

    public static final RegistryObject<Item> LEAD_AXE = ITEMS.register("lead_axe",
            () -> new AxeItem(
                    LeadTier.LEAD,
                    5.0f,
                    -3.1f,
                    new Item.Properties().stacksTo(1)
            ));

    public static final RegistryObject<Item> LEAD_HOE = ITEMS.register("lead_hoe",
            () -> new HoeItem(
                    LeadTier.LEAD,
                    -2,
                    -1.0f,
                    new Item.Properties().stacksTo(1)
            ));

    public static final RegistryObject<Item> LEAD_SHOVEL = ITEMS.register("lead_shovel",
            () -> new ShovelItem(
                    LeadTier.LEAD,
                    1.5f,
                    -3.0f,
                    new Item.Properties().stacksTo(1)
            ));

    public static final RegistryObject<Item> LEAD_SWORD = ITEMS.register("lead_sword",
            () -> new SwordItem(
                    LeadTier.LEAD,
                    3,
                    -2.4f,
                    new Item.Properties().stacksTo(1)
            ));

    public static final RegistryObject<Item> LEAD_SOLDIER_SPAWN_EGG =
            ITEMS.register("lead_soldier_spawn_egg",
                    () -> new ForgeSpawnEggItem(
                            ModEntities.LEAD_SOLDIER,
                            0xB7B7B7,
                            0x2B2B2B,
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> LEAD_SOLDER_HEAD = ITEMS.register("lead_soldier_head",
            () -> new LeadSoldierHeadItem(ModBlocks.LEAD_SOLDIER_HEAD.get(), new Item.Properties()));

    public static void registerItems(EventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
