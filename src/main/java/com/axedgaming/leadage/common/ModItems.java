package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.items.CeruseItem;
import com.axedgaming.leadage.common.items.DivingWeightItem;
import com.axedgaming.leadage.common.items.LeadSoldierHeadItem;
import com.axedgaming.leadage.common.items.SaltItem;
import com.axedgaming.leadage.common.materials.LeadTier;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;

public class ModItems {

    public static final ItemEntry<Item> RAW_GALENA =
            LeadAge.REGISTRATE.item("raw_galena", Item::new).register();

    public static final ItemEntry<Item> CRUSHED_GALENA =
            LeadAge.REGISTRATE.item("crushed_galena", Item::new).register();

    public static final ItemEntry<Item> LEAD_INGOT =
            LeadAge.REGISTRATE.item("lead_ingot", Item::new).register();

    public static final ItemEntry<Item> LEAD_NUGGET =
            LeadAge.REGISTRATE.item("lead_nugget", Item::new).register();

    public static final ItemEntry<Item> LEAD_SHEET =
            LeadAge.REGISTRATE.item("lead_sheet", Item::new).register();

    public static final ItemEntry<SaltItem> LEAD_SALT =
            LeadAge.REGISTRATE.item("lead_salt", SaltItem::new).register();

    public static final ItemEntry<CeruseItem> CERUSE =
            LeadAge.REGISTRATE.item("ceruse", CeruseItem::new).register();

    public static final ItemEntry<DivingWeightItem> DIVING_WEIGHT =
            LeadAge.REGISTRATE.item("diving_weight", DivingWeightItem::new).register();

    public static final ItemEntry<PickaxeItem> LEAD_PICKAXE =
            LeadAge.REGISTRATE.item("lead_pickaxe",
                            p -> new PickaxeItem(LeadTier.LEAD, 1, -2.8f, p.stacksTo(1)))
                    .register();

    public static final ItemEntry<AxeItem> LEAD_AXE =
            LeadAge.REGISTRATE.item("lead_axe",
                            p -> new AxeItem(LeadTier.LEAD, 5.0f, -3.1f, p.stacksTo(1)))
                    .register();

    public static final ItemEntry<HoeItem> LEAD_HOE =
            LeadAge.REGISTRATE.item("lead_hoe",
                            p -> new HoeItem(LeadTier.LEAD, -2, -1.0f, p.stacksTo(1)))
                    .register();

    public static final ItemEntry<ShovelItem> LEAD_SHOVEL =
            LeadAge.REGISTRATE.item("lead_shovel",
                            p -> new ShovelItem(LeadTier.LEAD, 1.5f, -3.0f, p.stacksTo(1)))
                    .register();

    public static final ItemEntry<SwordItem> LEAD_SWORD =
            LeadAge.REGISTRATE.item("lead_sword",
                            p -> new SwordItem(LeadTier.LEAD, 3, -2.4f, p.stacksTo(1)))
                    .register();

    public static final ItemEntry<ForgeSpawnEggItem> LEAD_SOLDIER_SPAWN_EGG =
            LeadAge.REGISTRATE.item("lead_soldier_spawn_egg",
                            p -> new ForgeSpawnEggItem(
                                    ModEntities.LEAD_SOLDIER,
                                    0xB7B7B7,
                                    0x2B2B2B,
                                    p))
                    .register();

    public static final ItemEntry<Item> INCOMPLETE_LEAD_SOLDIER_HEAD =
            LeadAge.REGISTRATE.item("incomplete_lead_soldier_head", Item::new).register();

    public static final ItemEntry<LeadSoldierHeadItem> LEAD_SOLDER_HEAD =
            LeadAge.REGISTRATE.item("lead_soldier_head",
                            p -> new LeadSoldierHeadItem(ModBlocks.LEAD_SOLDIER_HEAD.get(), p))
                    .register();


    public static final ItemEntry<BlockItem> LEAD_GLANCE =
            LeadAge.REGISTRATE.item("lead_glance",
                            p -> new BlockItem(ModBlocks.LEAD_GLANCE.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_BLOCK =
            LeadAge.REGISTRATE.item("lead_block",
                            p -> new BlockItem(ModBlocks.LEAD_BLOCK.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_BRICKS =
            LeadAge.REGISTRATE.item("lead_bricks",
                            p -> new BlockItem(ModBlocks.LEAD_BRICKS.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_BRICK_STAIRS =
            LeadAge.REGISTRATE.item("lead_brick_stairs",
                            p -> new BlockItem(ModBlocks.LEAD_BRICK_STAIRS.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_BRICK_SLAB =
            LeadAge.REGISTRATE.item("lead_brick_slab",
                            p -> new BlockItem(ModBlocks.LEAD_BRICK_SLAB.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_TILES =
            LeadAge.REGISTRATE.item("lead_tiles",
                            p -> new BlockItem(ModBlocks.LEAD_TILES.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_PLATES =
            LeadAge.REGISTRATE.item("lead_plates",
                            p -> new BlockItem(ModBlocks.LEAD_PLATES.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_GLASS =
            LeadAge.REGISTRATE.item("lead_glass",
                            p -> new BlockItem(ModBlocks.LEAD_GLASS.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> POLISHED_LEAD_GLASS =
            LeadAge.REGISTRATE.item("polished_lead_glass",
                            p -> new BlockItem(ModBlocks.POLISHED_LEAD_GLASS.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> TILED_LEAD_GLASS =
            LeadAge.REGISTRATE.item("tiled_lead_glass",
                            p -> new BlockItem(ModBlocks.TILED_LEAD_GLASS.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_BARS =
            LeadAge.REGISTRATE.item("lead_bars",
                            p -> new BlockItem(ModBlocks.LEAD_BARS.get(), p))
                    .register();

    public static final ItemEntry<BlockItem> LEAD_CASING =
            LeadAge.REGISTRATE.item("lead_casing",
                            p -> new BlockItem(ModBlocks.LEAD_CASING.get(), p))
                    .register();

    public static void register() {}
}