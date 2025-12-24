package com.axedgaming.leadage.common;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.effects.MaskingEffect;
import com.axedgaming.leadage.common.effects.SaturnismEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOD_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LeadAge.MOD_ID);

    public static final RegistryObject<MobEffect> MASKING = MOD_EFFECT.register("masking", MaskingEffect::new);

    public static final RegistryObject<MobEffect> SATURNISM = MOD_EFFECT.register("saturnism", SaturnismEffect::new);

    public static void register(EventBus eventBus) {
        MOD_EFFECT.register(eventBus);
    }
}
