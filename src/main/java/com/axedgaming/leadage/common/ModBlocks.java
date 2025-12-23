package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LeadAge.MOD_ID);

    public static final RegistryObject<Block> LEAD_GLANCE = BLOCKS.register("lead_glance",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static void register(EventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
