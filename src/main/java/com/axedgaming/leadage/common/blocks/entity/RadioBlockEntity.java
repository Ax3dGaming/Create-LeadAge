package com.axedgaming.leadage.common.blocks.entity;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.handlers.radio.RadioWorldRegistry;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RadioBlockEntity extends BlockEntity {
    private int frequency = RadioConstants.DEFAULT_FREQUENCY;

    public RadioBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIO_BE.get(), pos, state);
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = RadioConstants.clampFrequency(frequency);
        setChanged();
    }


    @Override
    public void onLoad() {
        super.onLoad();
        assert this.level != null;
        if (!this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
            RadioWorldRegistry.addRadio(serverLevel, this);
        }
    }

    @Override
    public void setRemoved() {
        assert this.level != null;
        if (!this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
            RadioWorldRegistry.removeRadio(serverLevel, this);
        }

        super.setRemoved();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.frequency = RadioConstants.clampFrequency(tag.getInt(RadioConstants.NBT_FREQUENCY));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
    }
}
