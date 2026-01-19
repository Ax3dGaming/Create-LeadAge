package com.axedgaming.leadage.common.blocks;

import com.simibubi.create.AllBlockEntityTypes;
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
        return AllBlockEntityTypes.FLUID_PIPE.get();
    }
}