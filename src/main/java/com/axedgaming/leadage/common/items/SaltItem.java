package com.axedgaming.leadage.common.items;

import com.axedgaming.leadage.Config;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.joml.Vector3f;

import java.util.List;

public class SaltItem extends Item {

    public SaltItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.leadage.hint"));
            return;
        }

        int radius = Config.SALT_RADIUS.get();

        tooltip.add(Component.translatable("tooltip.leadage.hint_pressed"));
        tooltip.add(Component.empty());
        tooltip.add(
                Component.translatable(
                        "tooltip.leadage.salt.summary",
                        radius
                )
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockPos center = context.getClickedPos();
        BlockState clickedState = level.getBlockState(center);

        if (
                clickedState.getBlock() != Blocks.GRASS_BLOCK &&
                        clickedState.getBlock() != Blocks.FARMLAND
        ) {
            return InteractionResult.PASS;
        }

        int radius = Config.SALT_RADIUS.get();
        int percentage = Config.SALT_PERCENTAGE.get();
        RandomSource random = level.getRandom();
        ServerLevel serverLevel = (ServerLevel) level;

        int changed = 0;

        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-radius, -radius, -radius),
                center.offset(radius, radius, radius)
        )) {
            if (random.nextInt(100) >= percentage) continue;

            BlockState state = serverLevel.getBlockState(pos);

            if (state.getBlock() == Blocks.GRASS_BLOCK) {
                serverLevel.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                spawnParticles(serverLevel, pos, random);
                changed++;
            } else if (state.getBlock() == Blocks.FARMLAND) {
                serverLevel.setBlock(pos, Blocks.COARSE_DIRT.defaultBlockState(), 3);
                spawnParticles(serverLevel, pos, random);
                changed++;
            }
        }

        if (changed > 0) {
            level.playSound(
                    null,
                    center,
                    SoundEvents.SAND_PLACE,
                    SoundSource.BLOCKS,
                    1.0f,
                    0.8f + random.nextFloat() * 0.2f
            );

            level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, center);
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.CONSUME;
    }

    private void spawnParticles(ServerLevel level, BlockPos pos, RandomSource random) {
        DustParticleOptions dust =
                new DustParticleOptions(new Vector3f(0.6f, 0.6f, 0.6f), 1.0f);

        level.sendParticles(
                dust,
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5,
                4 + random.nextInt(4),
                0.3,
                0.2,
                0.3,
                0.0
        );
    }
}