package com.axedgaming.leadage.common.blocks.entity;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import com.axedgaming.leadage.common.handlers.RadioBlockRegistry;
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
        this.frequency = RadioChannelHelper.clampFrequency(frequency);
        setChanged();
    }


    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            RadioBlockRegistry.registerRadio(this);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null && !level.isClientSide) {
            RadioBlockRegistry.unregisterRadio(this);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.frequency = RadioChannelHelper.clampFrequency(tag.getInt(RadioConstants.NBT_FREQUENCY));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
    }
}
