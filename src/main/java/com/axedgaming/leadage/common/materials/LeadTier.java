package com.axedgaming.leadage.common.materials;

import com.axedgaming.leadage.common.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum LeadTier implements Tier {
    LEAD;

    @Override
    public int getUses() {
        return 180;
    }

    @Override
    public float getSpeed() {
        return 5.0f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 1.5f;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.LEAD_INGOT.get());
    }
}