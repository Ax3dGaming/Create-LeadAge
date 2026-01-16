package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {
    public static final BlockEntityEntry<FluidPipeBlockEntity> ENCASED_FLUID_PIPE = LeadAge.REGISTRATE
            .blockEntity("encased_fluid_pipe", FluidPipeBlockEntity::new)
            .register();

    public static void register() {}
}
