package com.axedgaming.leadage.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SaltInfusionRecipeSerializer
        implements RecipeSerializer<SaltInfusionRecipe> {

    @Override
    public SaltInfusionRecipe fromJson(
            ResourceLocation id,
            JsonObject json
    ) {
        return new SaltInfusionRecipe(id);
    }

    @Override
    public SaltInfusionRecipe fromNetwork(
            ResourceLocation id,
            FriendlyByteBuf buf
    ) {
        return new SaltInfusionRecipe(id);
    }

    @Override
    public void toNetwork(
            FriendlyByteBuf buf,
            SaltInfusionRecipe recipe
    ) {}
}