package com.axedgaming.leadage.common.blocks.radio;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.client.RadioClientHooks;
import com.axedgaming.leadage.common.blocks.entity.RadioBlockEntity;
import com.axedgaming.leadage.common.utils.RadioConstants;
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
    public static final IntegerProperty FREQUENCY = IntegerProperty.create("frequency", RadioConstants.MIN_FREQUENCY, RadioConstants.MAX_FREQUENCY);

    public RadioBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FREQUENCY, RadioConstants.DEFAULT_FREQUENCY));
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
        if (level.isClientSide && level.getBlockEntity(pos) instanceof RadioBlockEntity be) {
            RadioClientHooks.openBlockFrequencyScreen(pos, be.getFrequency());
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
