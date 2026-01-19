package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.blocks.entity.LaEncasedPipeBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {

    public static final BlockEntityEntry<LaEncasedPipeBlockEntity> ENCASED_FLUID_PIPE =
            LeadAge.REGISTRATE
                    .blockEntity("encased_fluid_pipe", LaEncasedPipeBlockEntity::new)
                    .validBlocks(ModBlocks.LEAD_ENCASED_FLUID_PIPE)
                    //.renderer(() -> FluidPipeRenderer::new)
                    .register();

    public static void register() {}
}
