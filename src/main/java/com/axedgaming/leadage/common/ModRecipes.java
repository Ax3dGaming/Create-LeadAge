package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.recipe.SaltInfusionRecipe;
import com.axedgaming.leadage.common.recipe.SaltInfusionRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LeadAge.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, LeadAge.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SaltInfusionRecipe>> SALT_INFUSION_SERIALIZER =
            SERIALIZERS.register("salt_infusion", SaltInfusionRecipeSerializer::new);

    public static final RegistryObject<RecipeType<SaltInfusionRecipe>> SALT_INFUSION_TYPE =
            TYPES.register("salt_infusion", () -> new RecipeType<>() {});

    public static final ResourceLocation SALT_INFUSION_ID =
            ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "salt_infusion");
}
