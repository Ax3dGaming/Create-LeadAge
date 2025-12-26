package com.axedgaming.leadage.common.materials;

import com.axedgaming.leadage.common.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum LeadTier implements Tier {
    LEAD;

    @Override
    public int getUses() {
        return 200;
    }

    @Override
    public float getSpeed() {
        return 8.0f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 2.0f;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.LEAD_INGOT.get());
    }
}