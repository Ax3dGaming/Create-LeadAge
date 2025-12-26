package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.blocks.LeadSoldierHeadBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LeadAge.MOD_ID);

    public static final RegistryObject<Block> LEAD_GLANCE = BLOCKS.register("lead_glance",
            () -> new Block(BlockBehaviour.Properties
                    .copy(Blocks.IRON_ORE)
                    .strength(3.0F, 3.0F)
            ));

    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block",
            () -> new Block(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK)
                    .strength(5.0F, 6.0F)
            ));

    public static final RegistryObject<Block> LEAD_BRICKS = BLOCKS.register("lead_bricks",
            () -> new Block(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK)
                    .strength(5.0F, 6.0F)
            ));

    public static final RegistryObject<Block> LEAD_BRICK_STAIRS = BLOCKS.register("lead_brick_stairs",
            () -> new StairBlock(
                    () -> LEAD_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties
                            .copy(Blocks.IRON_BLOCK)
                            .strength(5.0F, 6.0F)
            ));

    public static final RegistryObject<Block> LEAD_BRICK_SLAB = BLOCKS.register("lead_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK)
                    .strength(5.0F, 6.0F)
            ));

    public static final RegistryObject<Block> LEAD_TILES = BLOCKS.register("lead_tiles",
            () -> new Block(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK)
                    .strength(5.0F, 6.0F)
            ));

    public static final RegistryObject<Block> LEAD_PLATES = BLOCKS.register("lead_plates",
            () -> new Block(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK)
                    .strength(5.0F, 6.0F)
            ));

    public static final RegistryObject<Block> LEAD_GLASS = BLOCKS.register("lead_glass",
            () -> new GlassBlock(BlockBehaviour.Properties
                    .copy(Blocks.GLASS)
                    .strength(0.3F, 0.3F)
                    .noOcclusion()
                    .lightLevel(state -> 3)
            ));

    public static final RegistryObject<Block> POLISHED_LEAD_GLASS = BLOCKS.register("polished_lead_glass",
            () -> new GlassBlock(BlockBehaviour.Properties
                    .copy(Blocks.GLASS)
                    .strength(0.3F, 0.3F)
                    .noOcclusion()
                    .lightLevel(state -> 3)
            ));

    public static final RegistryObject<Block> TILED_LEAD_GLASS = BLOCKS.register("tiled_lead_glass",
            () -> new GlassBlock(BlockBehaviour.Properties
                    .copy(Blocks.GLASS)
                    .strength(0.3F, 0.3F)
                    .noOcclusion()
                    .lightLevel(state -> 3)
            ));

    public static final RegistryObject<Block> LEAD_SOLDIER_HEAD = BLOCKS.register("lead_soldier_head",
            () -> new LeadSoldierHeadBlock(BlockBehaviour.Properties.copy(ModBlocks.LEAD_BLOCK.get())));

    public static void register(EventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
