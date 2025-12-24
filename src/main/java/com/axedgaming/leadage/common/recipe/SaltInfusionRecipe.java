package com.axedgaming.leadage.common.recipe;

import com.axedgaming.leadage.common.ModRecipes;
import com.axedgaming.leadage.common.items.SaltItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class SaltInfusionRecipe extends CustomRecipe {

    public SaltInfusionRecipe(ResourceLocation id) {
        super(id, CraftingBookCategory.MISC);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        boolean hasSalt = false;
        boolean hasFood = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.getItem() instanceof SaltItem) {
                hasSalt = true;
            } else if (stack.getItem().isEdible()) {
                hasFood = true;
            } else {
                return false;
            }
        }

        return hasSalt && hasFood;
    }

    @Override
    public ItemStack assemble(
            CraftingContainer inv,
            RegistryAccess access
    ) {
        ItemStack food = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem().isEdible()) {
                food = stack;
                break;
            }
        }

        if (food.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = food.copy();
        result.setCount(1);
        result.getOrCreateTag().putBoolean("lead_poisoned", true);

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w * h >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SALT_INFUSION_SERIALIZER.get();
    }
}