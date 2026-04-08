package com.axedgaming.leadage.common.menu;

import com.axedgaming.leadage.common.ModBlocks;
import com.axedgaming.leadage.common.ModMenus;
import com.axedgaming.leadage.common.blocks.entity.RadioAnalyserBlockEntity;
import com.axedgaming.leadage.common.items.TicketItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class RadioAnalyserMenu extends AbstractContainerMenu {
    private static final int TILE_SLOT_COUNT = 1;
    private static final int TILE_SLOT_START = 0;
    private static final int PLAYER_INV_START = TILE_SLOT_START + TILE_SLOT_COUNT;
    private static final int PLAYER_INV_COUNT = 27;
    private static final int HOTBAR_COUNT = 9;
    private static final int PLAYER_TOTAL_COUNT = PLAYER_INV_COUNT + HOTBAR_COUNT;
    private static final int PLAYER_END = PLAYER_INV_START + PLAYER_TOTAL_COUNT;

    private final RadioAnalyserBlockEntity blockEntity;
    private final ContainerLevelAccess access;

    public RadioAnalyserMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory,
                (RadioAnalyserBlockEntity) playerInventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public RadioAnalyserMenu(int containerId, Inventory playerInventory, RadioAnalyserBlockEntity blockEntity) {
        super(ModMenus.RADIO_ANALYSER_MENU.get(), containerId);
        this.blockEntity = blockEntity;
        this.access = ContainerLevelAccess.create(playerInventory.player.level(), blockEntity.getBlockPos());

        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 80, 18) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof TicketItem;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public RadioAnalyserBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.RADIO_ANALYSER.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot sourceSlot = this.slots.get(index);

        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        original = sourceStack.copy();

        if (index < TILE_SLOT_START + TILE_SLOT_COUNT) {
            if (!this.moveItemStackTo(sourceStack, PLAYER_INV_START, PLAYER_END, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (sourceStack.getItem() instanceof TicketItem) {
                if (!this.moveItemStackTo(sourceStack, TILE_SLOT_START, TILE_SLOT_START + TILE_SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return original;
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 51 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 109));
        }
    }
}