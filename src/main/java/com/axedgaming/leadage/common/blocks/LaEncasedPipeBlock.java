package com.axedgaming.leadage.common.blocks;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class LaEncasedPipeBlock extends EncasedPipeBlock {

    public LaEncasedPipeBlock(Properties properties, Supplier<Block> casing) {
        super(properties, casing);
    }

    @Override
    public BlockEntityType<? extends FluidPipeBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ENCASED_FLUID_PIPE.get();
    }
}
