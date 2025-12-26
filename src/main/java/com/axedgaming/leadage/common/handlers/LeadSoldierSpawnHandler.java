package com.axedgaming.leadage.common.handlers;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.ModBlocks;
import com.axedgaming.leadage.common.ModEntities;
import com.axedgaming.leadage.common.entity.LeadSoldierEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID)
public class LeadSoldierSpawnHandler {

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BlockState placed = event.getPlacedBlock();
        BlockPos pos = event.getPos();

        Block headBlock = ModBlocks.LEAD_SOLDIER_HEAD.get();
        Block baseBlock = ModBlocks.LEAD_BLOCK.get();

        if (placed.getBlock() != headBlock) return;

        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);

        if (belowState.getBlock() != baseBlock) return;

        level.removeBlock(pos, false);
        level.removeBlock(below, false);

        LeadSoldierEntity soldier = ModEntities.LEAD_SOLDIER.get().create(level);
        if (soldier == null) return;

        soldier.moveTo(
                below.getX() + 0.5,
                below.getY(),
                below.getZ() + 0.5,
                level.random.nextFloat() * 360F,
                0
        );

        level.addFreshEntity(soldier);
    }
}