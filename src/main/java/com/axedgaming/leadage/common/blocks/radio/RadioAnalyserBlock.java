package com.axedgaming.leadage.common.blocks.radio;

import com.axedgaming.leadage.client.RadioClientHooks;
import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.items.TicketItem;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class RadioAnalyserBlock extends BaseEntityBlock {
    public static final IntegerProperty FREQUENCY =
            IntegerProperty.create("frequency", RadioConstants.MIN_FREQUENCY, RadioConstants.MAX_FREQUENCY);
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public RadioAnalyserBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(FREQUENCY, RadioConstants.DEFAULT_FREQUENCY)
                .setValue(POWERED, Boolean.FALSE)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FREQUENCY, POWERED, FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
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
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof RadioAnalyserBlockEntity analyserBe) {
                ItemStack ticket = analyserBe.removeTicket();

                if (!ticket.isEmpty()) {
                    net.minecraft.world.Containers.dropItemStack(
                            level,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            ticket
                    );
                }
            }

            super.onRemove(oldState, level, pos, newState, isMoving);
            return;
        }

        super.onRemove(oldState, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof RadioAnalyserBlockEntity analyserBe)) {
            return InteractionResult.PASS;
        }

        ItemStack heldItem = player.getItemInHand(hand);
        boolean clickedSlotZone = isClickOnTicketZone(pos, hit);

        // ---------- IF CLIENT ----------
        if (level.isClientSide) {
            // If clicked in ticket zone, slot interaction going
            if (clickedSlotZone) {
                if (heldItem.isEmpty() || heldItem.getItem() instanceof TicketItem) {
                    return InteractionResult.SUCCESS;
                }
            } else {
                // Else => open frequency screen
                if (heldItem.isEmpty()) {
                    RadioClientHooks.openBlockFrequencyScreen(pos, analyserBe.getFrequency());
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        }

        // ---------- IF SERVER ----------
        if (clickedSlotZone) {
            // Ticket insert
            if (heldItem.getItem() instanceof TicketItem) {
                ItemStack remainder = analyserBe.tryInsertTicket(heldItem, player);
                if (remainder.getCount() != heldItem.getCount()) {
                    player.setItemInHand(hand, remainder);
                    return InteractionResult.CONSUME;
                }
                return InteractionResult.PASS;
            }

            // Ticket remove if empty hand
            if (heldItem.isEmpty()) {
                ItemStack extracted = analyserBe.removeTicket();
                if (!extracted.isEmpty()) {
                    player.setItemInHand(hand, extracted);
                    return InteractionResult.CONSUME;
                }
            }

            return InteractionResult.PASS;
        }

        // Not in ticket zone => nothing going server side
        return InteractionResult.CONSUME;
    }

    /**
     * Slot zone on the block
     * Coords between 0 and 1 inside the block
     */
    private static boolean isClickOnTicketZone(BlockPos pos, BlockHitResult hit) {
        double localX = hit.getLocation().x - pos.getX();
        double localY = hit.getLocation().y - pos.getY();
        double localZ = hit.getLocation().z - pos.getZ();

        return localX >= 0.25D && localX <= 0.75D
                && localY >= 0.70D && localY <= 1.00D
                && localZ >= 0.25D && localZ <= 0.75D;
    }
}