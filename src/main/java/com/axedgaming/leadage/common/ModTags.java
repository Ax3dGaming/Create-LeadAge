package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> LEAD =
                TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "lead"));

        public static final TagKey<Item> LEAD_POISONED =
                TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(LeadAge.MOD_ID, "lead_poisoned"));
    }
}