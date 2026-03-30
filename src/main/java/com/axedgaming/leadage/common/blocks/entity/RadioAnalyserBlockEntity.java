package com.axedgaming.leadage.common.blocks.entity;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.blocks.RadioAnalyserBlock;
import com.axedgaming.leadage.common.handlers.RadioBlockRegistry;
import com.axedgaming.leadage.common.handlers.RadioWorldRegistry;
import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import com.axedgaming.leadage.common.utils.RadioConstants;
import com.axedgaming.leadage.common.utils.RadioTextHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RadioAnalyserBlockEntity extends BlockEntity {
    private int frequency = RadioConstants.DEFAULT_FREQUENCY;
    private String targetMessage = "";
    private int pulseTicks = 0;

    public RadioAnalyserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIO_ANALYSER_BE.get(), pos, state);
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = RadioConstants.clampFrequency(frequency);
        setChanged();
    }

    public String getTargetText() {
        return targetMessage;
    }

    public void setTargetText(String targetText) {
        this.targetMessage = targetText == null ? "" : targetText;
        setChanged();
    }

    public boolean matches(String incomingMessage) {
        return RadioTextHelper.normalizeForComparison(targetMessage)
                .equals(RadioTextHelper.normalizeForComparison(incomingMessage));
    }

    public void triggerPulse() {
        if (level == null || level.isClientSide) {
            return;
        }

        pulseTicks = RadioConstants.ANALYSER_PULSE_TICKS;
        level.setBlock(worldPosition, getBlockState().setValue(RadioAnalyserBlock.POWERED, Boolean.TRUE), 3);
        level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
        setChanged();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RadioAnalyserBlockEntity be) {
        if (level.isClientSide || be.pulseTicks <= 0) {
            return;
        }

        be.pulseTicks--;
        if (be.pulseTicks == 0 && state.getValue(RadioAnalyserBlock.POWERED)) {
            level.setBlock(pos, state.setValue(RadioAnalyserBlock.POWERED, Boolean.FALSE), 3);
            level.updateNeighborsAt(pos, state.getBlock());
            be.setChanged();
        }
    }

    public void onRadioMessageReceived(String incomingMessage) {
        String expected = RadioTextHelper.normalizeForComparison(targetMessage);
        String received = RadioTextHelper.normalizeForComparison(incomingMessage);

        if (expected.isEmpty()) {
            return;
        }

        if (expected.equals(received)) {
            pulseTicks = 20;
            setChanged();

            if (level != null) {
                level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }


    @Override
    public void onLoad() {
        super.onLoad();

        assert this.level != null;
        if (!this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
            RadioWorldRegistry.addAnalyser(serverLevel, this);
        }
    }

    @Override
    public void setRemoved() {
        assert this.level != null;
        if (!this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
            RadioWorldRegistry.removeAnalyser(serverLevel, this);
        }

        super.setRemoved();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.frequency = RadioConstants.clampFrequency(tag.getInt(RadioConstants.NBT_FREQUENCY));
        this.targetMessage = tag.getString(RadioConstants.NBT_TARGET_TEXT);
        this.pulseTicks = tag.getInt(RadioConstants.NBT_PULSE_TICKS);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
        tag.putString(RadioConstants.NBT_TARGET_TEXT, targetMessage);
        tag.putInt(RadioConstants.NBT_PULSE_TICKS, pulseTicks);
    }
}
