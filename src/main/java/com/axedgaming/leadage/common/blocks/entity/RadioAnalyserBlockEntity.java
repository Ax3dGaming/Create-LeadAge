package com.axedgaming.leadage.common.blocks.entity;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.blocks.radio.RadioAnalyserBlock;
import com.axedgaming.leadage.common.handlers.radio.RadioWorldRegistry;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RadioAnalyserBlockEntity extends BlockEntity {
    private int frequency = RadioConstants.DEFAULT_FREQUENCY;
    private int pulseTicks = 0;

    public RadioAnalyserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIO_ANALYSER_BE.get(), pos, state);
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        int clamped = RadioConstants.clampFrequency(frequency);
        this.frequency = clamped;
        setChanged();

        if (level != null && !level.isClientSide) {
            BlockState state = getBlockState();

            if (state.hasProperty(RadioAnalyserBlock.FREQUENCY)
                    && state.getValue(RadioAnalyserBlock.FREQUENCY) != clamped) {
                level.setBlock(worldPosition, state.setValue(RadioAnalyserBlock.FREQUENCY, clamped), 3);
            } else {
                level.sendBlockUpdated(worldPosition, state, state, 3);
            }
        }
    }

    public int getPulseTicks() {
        return pulseTicks;
    }

    public boolean isPowered() {
        BlockState state = getBlockState();
        return state.hasProperty(RadioAnalyserBlock.POWERED) && state.getValue(RadioAnalyserBlock.POWERED);
    }

    public void triggerPulse() {
        if (level == null || level.isClientSide) {
            return;
        }

        pulseTicks = RadioConstants.ANALYSER_PULSE_TICKS;

        BlockState state = getBlockState();
        if (state.hasProperty(RadioAnalyserBlock.POWERED) && !state.getValue(RadioAnalyserBlock.POWERED)) {
            level.setBlock(worldPosition, state.setValue(RadioAnalyserBlock.POWERED, true), 3);
        } else {
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }

        level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
        setChanged();
    }

    public void onRadioMessageReceived(String incomingMessage) {
        triggerPulse();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RadioAnalyserBlockEntity be) {
        if (level.isClientSide) {
            return;
        }

        if (be.pulseTicks <= 0) {
            return;
        }

        be.pulseTicks--;

        if (be.pulseTicks == 0) {
            BlockState currentState = be.getBlockState();

            if (currentState.hasProperty(RadioAnalyserBlock.POWERED)
                    && currentState.getValue(RadioAnalyserBlock.POWERED)) {
                level.setBlock(pos, currentState.setValue(RadioAnalyserBlock.POWERED, false), 3);
                level.updateNeighborsAt(pos, currentState.getBlock());
            } else {
                level.sendBlockUpdated(pos, currentState, currentState, 3);
            }

            be.setChanged();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (this.level != null && !this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
            BlockState state = getBlockState();

            if (state.hasProperty(RadioAnalyserBlock.FREQUENCY)
                    && state.getValue(RadioAnalyserBlock.FREQUENCY) != frequency) {
                level.setBlock(worldPosition, state.setValue(RadioAnalyserBlock.FREQUENCY, frequency), 3);
            }

            RadioWorldRegistry.addAnalyser(serverLevel, this);
        }
    }

    @Override
    public void setRemoved() {
        if (this.level != null && !this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
            RadioWorldRegistry.removeAnalyser(serverLevel, this);
        }

        super.setRemoved();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.frequency = RadioConstants.clampFrequency(tag.getInt(RadioConstants.NBT_FREQUENCY));
        this.pulseTicks = tag.getInt(RadioConstants.NBT_PULSE_TICKS);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
        tag.putInt(RadioConstants.NBT_PULSE_TICKS, pulseTicks);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
        tag.putInt(RadioConstants.NBT_PULSE_TICKS, pulseTicks);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}