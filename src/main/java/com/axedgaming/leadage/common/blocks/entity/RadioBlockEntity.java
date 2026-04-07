package com.axedgaming.leadage.common.blocks.entity;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.blocks.radio.RadioBlock;
import com.axedgaming.leadage.common.handlers.radio.RadioWorldRegistry;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class RadioBlockEntity extends BlockEntity {
    private int frequency = RadioConstants.DEFAULT_FREQUENCY;

    public RadioBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIO_BE.get(), pos, state);
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

            if (state.hasProperty(RadioBlock.FREQUENCY)
                    && state.getValue(RadioBlock.FREQUENCY) != clamped) {
                level.setBlock(worldPosition, state.setValue(RadioBlock.FREQUENCY, clamped), 3);
            } else {
                level.sendBlockUpdated(worldPosition, state, state, 3);
            }
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.level != null && !this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
            RadioWorldRegistry.addRadio(serverLevel, this);
        }
    }

    @Override
    public void setRemoved() {
        if (this.level != null && !this.level.isClientSide && this.level instanceof ServerLevel serverLevel) {
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

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}