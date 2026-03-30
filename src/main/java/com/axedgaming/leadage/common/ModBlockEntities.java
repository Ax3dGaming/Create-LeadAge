package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.blocks.entity.LaEncasedPipeBlockEntity;
import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "leadage");

    public static final RegistryObject<BlockEntityType<RadioBlockEntity>> RADIO_BE = BLOCK_ENTITIES.register("radio",
            () -> BlockEntityType.Builder.of(RadioBlockEntity::new, ModBlocks.RADIO.get()).build(null));

    public static final RegistryObject<BlockEntityType<RadioAnalyserBlockEntity>> RADIO_ANALYSER_BE = BLOCK_ENTITIES.register("radio_analyser",
            () -> BlockEntityType.Builder.of(RadioAnalyserBlockEntity::new, ModBlocks.RADIO_ANALYSER.get()).build(null));

    public static final BlockEntityEntry<LaEncasedPipeBlockEntity> ENCASED_FLUID_PIPE =
            LeadAge.REGISTRATE
                    .blockEntity("encased_fluid_pipe", LaEncasedPipeBlockEntity::new)
                    .validBlocks(ModBlocks.LEAD_ENCASED_FLUID_PIPE)
                    //.renderer(() -> FluidPipeRenderer::new)
                    .register();

    public static void register() {}
}
