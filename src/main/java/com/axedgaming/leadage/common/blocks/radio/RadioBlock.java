package com.axedgaming.leadage.common.blocks.radio;

import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RadioBlock extends BaseEntityBlock {
    public static final IntegerProperty FREQUENCY = IntegerProperty.create("frequency", 76, 108);

    public RadioBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FREQUENCY, 92));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FREQUENCY);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RadioBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof RadioBlockEntity be && player.isShiftKeyDown()) {
            int next = be.getFrequency() + 1;
            if (next > 108) next = 76;
            be.setFrequency(next);
            level.setBlock(pos, state.setValue(FREQUENCY, next), 3);
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("Radio réglée sur " + next + " MHz"), true);
        }

        // TODO: brancher ici la sélection de fréquence façon Create.
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
