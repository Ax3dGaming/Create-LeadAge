package com.axedgaming.leadage.common.blocks.entity;

import com.axedgaming.leadage.common.ModBlockEntities;
import com.axedgaming.leadage.common.ModMenus;
import com.axedgaming.leadage.common.blocks.radio.RadioAnalyserBlock;
import com.axedgaming.leadage.common.handlers.radio.RadioWorldRegistry;
import com.axedgaming.leadage.common.items.TicketItem;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import com.axedgaming.leadage.common.items.TicketItem;
import com.axedgaming.leadage.common.utils.RadioTextHelper;
import net.minecraft.world.item.ItemStack;


public class RadioAnalyserBlockEntity extends BlockEntity implements net.minecraft.world.MenuProvider {
    private int frequency = RadioConstants.DEFAULT_FREQUENCY;
    private int pulseTicks = 0;

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();

            if (level != null && !level.isClientSide) {
                BlockState state = getBlockState();
                level.sendBlockUpdated(worldPosition, state, state, 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, net.minecraft.world.item.ItemStack stack) {
            return stack.getItem() instanceof TicketItem;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private LazyOptional<ItemStackHandler> itemHandler = LazyOptional.of(() -> inventory);

    public RadioAnalyserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIO_ANALYSER_BE.get(), pos, state);
    }

    public ItemStackHandler getInventory() {
        return inventory;
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
        if (level == null || level.isClientSide) {
            return;
        }

        if (matchesTicketMessage(incomingMessage)) {
            triggerPulse();
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RadioAnalyserBlockEntity be) {
        if (level.isClientSide || be.pulseTicks <= 0) {
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
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.frequency = RadioConstants.clampFrequency(tag.getInt(RadioConstants.NBT_FREQUENCY));
        this.pulseTicks = tag.getInt(RadioConstants.NBT_PULSE_TICKS);

        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
        tag.putInt(RadioConstants.NBT_PULSE_TICKS, pulseTicks);
        tag.put("Inventory", inventory.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt(RadioConstants.NBT_FREQUENCY, frequency);
        tag.putInt(RadioConstants.NBT_PULSE_TICKS, pulseTicks);
        tag.put("Inventory", inventory.serializeNBT());
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.leadage.radio_analyser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new com.axedgaming.leadage.common.menu.RadioAnalyserMenu(containerId, playerInventory, this);
    }

    public String getTicketMessage() {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.isEmpty() || !(stack.getItem() instanceof TicketItem)) {
            return "";
        }

        return TicketItem.getMessage(stack);
    }

    public boolean matchesTicketMessage(String incomingMessage) {
        String expected = RadioTextHelper.normalizeForComparison(getTicketMessage());
        String received = RadioTextHelper.normalizeForComparison(incomingMessage);

        if (expected.isEmpty()) {
            return false;
        }

        return expected.equals(received);
    }
}