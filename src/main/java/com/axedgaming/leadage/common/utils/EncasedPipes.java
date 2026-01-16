package com.axedgaming.leadage.common.utils;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.ModBlockStateGens;
import com.axedgaming.leadage.common.blocks.LaEncasedPipeBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.encasing.*;
import com.simibubi.create.content.fluids.PipeAttachmentModel;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class EncasedPipes {
    public static BlockEntry<LaEncasedPipeBlock> createEncasedFluidPipe(
            String name,
            Supplier<Block> casing,
            CTSpriteShiftEntry sprite
    ) {

        return LeadAge.REGISTRATE
                .block(name + "_encased_fluid_pipe",
                        p -> new LaEncasedPipeBlock(p, casing))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.noOcclusion()
                        .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
                .transform(axeOrPickaxe())
                .blockstate(ModBlockStateGens.encasedPipe(name))
                .onRegister(CreateRegistrate.blockModel(() -> PipeAttachmentModel::withAO))
                .loot((p, b) -> p.dropOther(b, AllBlocks.FLUID_PIPE.get()))
                .onRegisterAfter(Registries.BLOCK, block ->
                        EncasingRegistry.addVariant(
                                AllBlocks.FLUID_PIPE.get(),
                                block
                        )
                )
                .onRegister(CreateRegistrate.connectedTextures(
                        () -> new EncasedCTBehaviour(sprite)
                ))
                .onRegister(CreateRegistrate.casingConnectivity(
                        (block, cc) -> cc.make(
                                block,
                                sprite,
                                (s, f) -> !s.getValue(
                                        EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(f)
                                )
                        )
                ))

                .register();
    }
}
