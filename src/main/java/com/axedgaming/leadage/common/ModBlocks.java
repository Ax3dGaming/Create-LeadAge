package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.blocks.LeadSoldierHeadBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.fluids.PipeAttachmentModel;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import static com.axedgaming.leadage.LeadAge.REGISTRATE;
import static com.simibubi.create.AllBlocks.FLUID_PIPE;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class ModBlocks {

    public static final BlockEntry<Block> LEAD_GLANCE =
            REGISTRATE.block("lead_glance", Block::new)
                    .initialProperties(() -> Blocks.IRON_ORE)
                    .properties(p -> p.strength(3.0F))
                    .register();

    public static final BlockEntry<Block> LEAD_BLOCK =
            REGISTRATE.block("lead_block", Block::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .properties(p -> p.strength(5.0F, 6.0F))
                    .register();

    public static final BlockEntry<Block> LEAD_BRICKS =
            REGISTRATE.block("lead_bricks", Block::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .register();

    public static final BlockEntry<StairBlock> LEAD_BRICK_STAIRS =
            REGISTRATE.block("lead_brick_stairs",
                            p -> new StairBlock(LEAD_BRICKS.get()::defaultBlockState, p))
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .register();

    public static final BlockEntry<SlabBlock> LEAD_BRICK_SLAB =
            REGISTRATE.block("lead_brick_slab", SlabBlock::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .register();

    public static final BlockEntry<Block> LEAD_TILES =
            REGISTRATE.block("lead_tiles", Block::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .register();

    public static final BlockEntry<Block> LEAD_PLATES =
            REGISTRATE.block("lead_plates", Block::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .register();

    public static final BlockEntry<GlassBlock> LEAD_GLASS =
            REGISTRATE.block("lead_glass", GlassBlock::new)
                    .initialProperties(() -> Blocks.GLASS)
                    .properties(p -> p.noOcclusion().lightLevel(s -> 3))
                    .register();

    public static final BlockEntry<GlassBlock> POLISHED_LEAD_GLASS =
            REGISTRATE.block("polished_lead_glass", GlassBlock::new)
                    .initialProperties(() -> Blocks.GLASS)
                    .properties(p -> p.noOcclusion().lightLevel(s -> 3))
                    .register();

    public static final BlockEntry<GlassBlock> TILED_LEAD_GLASS =
            REGISTRATE.block("tiled_lead_glass", GlassBlock::new)
                    .initialProperties(() -> Blocks.GLASS)
                    .properties(p -> p.noOcclusion().lightLevel(s -> 3))
                    .register();

    public static final BlockEntry<LeadSoldierHeadBlock> LEAD_SOLDIER_HEAD =
            REGISTRATE.block("lead_soldier_head", LeadSoldierHeadBlock::new)
                    .initialProperties(() -> LEAD_BLOCK.get())
                    .register();

    public static final BlockEntry<IronBarsBlock> LEAD_BARS =
            REGISTRATE.block("lead_bars", IronBarsBlock::new)
                    .initialProperties(() -> LEAD_BLOCK.get())
                    .properties(p -> p.noOcclusion())
                    .register();

    public static final BlockEntry<CasingBlock> LEAD_CASING =
            REGISTRATE
                    .block("lead_casing", CasingBlock::new)
                    .initialProperties(() -> ModBlocks.LEAD_BLOCK.get())
                    .transform(BuilderTransformers.casing(() -> ModSpriteShifts.LEAD_CASING))
                    .register();

    //TODO: Encased fluid pipe

    public static void register() {}
}