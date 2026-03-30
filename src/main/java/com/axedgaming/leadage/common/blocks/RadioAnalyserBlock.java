package com.axedgaming.leadage.common.blocks;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RadioAnalyserBlock extends BaseEntityBlock {
    public static final IntegerProperty FREQUENCY = IntegerProperty.create("frequency", 76, 108);
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public RadioAnalyserBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FREQUENCY, 92).setValue(POWERED, Boolean.FALSE));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FREQUENCY, POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RadioAnalyserBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.RADIO_ANALYSER_BE.get(), RadioAnalyserBlockEntity::serverTick);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, net.minecraft.core.Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof RadioAnalyserBlockEntity be && player.isShiftKeyDown()) {
            int next = be.getFrequency() + 1;
            if (next > 108) next = 76;
            be.setFrequency(next);
            level.setBlock(pos, state.setValue(FREQUENCY, next), 3);
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("Analyser réglé sur " + next + " MHz"), true);
        }

        // TODO: ouvrir un menu pour modifier targetText.
        // TODO: brancher la sélection de fréquence façon Create.
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
