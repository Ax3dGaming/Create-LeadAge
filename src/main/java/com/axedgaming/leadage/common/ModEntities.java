package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.entity.LeadSoldierEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LeadAge.MOD_ID);

    public static final RegistryObject<EntityType<LeadSoldierEntity>> LEAD_SOLDIER =
            ENTITIES.register("lead_soldier",
                    () -> EntityType.Builder.<LeadSoldierEntity>of(LeadSoldierEntity::new, MobCategory.MISC)
                            .sized(0.9F, 2.4F)
                            .clientTrackingRange(8)
                            .build(LeadAge.MOD_ID + ":lead_soldier")
            );

    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent event) {
        event.put(LEAD_SOLDIER.get(), LeadSoldierEntity.createAttributes().build());
    }
}