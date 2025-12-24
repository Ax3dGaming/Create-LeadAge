package com.axedgaming.leadage;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec COMMON_SPEC;

    public static ForgeConfigSpec.IntValue MAX_DIVING_WEIGHT;

    public static ForgeConfigSpec.DoubleValue MIN_JUMP_VELOCITY;

    public static ForgeConfigSpec.BooleanValue DIVING_WEIGHT_IGNORE_CREATIVE;

    public static ForgeConfigSpec.IntValue CERUSE_DURATION;

    public static ForgeConfigSpec.IntValue CERUSE_COOLDOWN;

    public static ForgeConfigSpec.IntValue CERUSE_AGGRO_RANGE;

    public static ForgeConfigSpec.BooleanValue CERUSE_PARTICLES;

    public static ForgeConfigSpec.IntValue SALT_RADIUS;

    public static ForgeConfigSpec.IntValue SALT_PERCENTAGE;

    public static ForgeConfigSpec.IntValue SATURNISM_PASSIVE;

    public static ForgeConfigSpec.IntValue SATURNISM_CRAFT;

    public static ForgeConfigSpec.IntValue SATURNISM_SLEEP;

    public static ForgeConfigSpec.IntValue SATURNISM_SLEEP_WITH_LEAD;

    public static ForgeConfigSpec.IntValue SATURNISM_FOOD;

    public static ForgeConfigSpec.IntValue SATURNISM_EFFECT_THRESHOLD;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("diving_weight");

        MAX_DIVING_WEIGHT = builder
                .comment("Maximum number of diving weights taken into account")
                .defineInRange("maxWeight", 128, 1, 1024);

        MIN_JUMP_VELOCITY = builder
                .comment("Minimum jump Y velocity when fully weighted (0.42 = normal jump)")
                .defineInRange("minJumpVelocity", 0.18D, 0.0D, 1.0D);

        DIVING_WEIGHT_IGNORE_CREATIVE = builder
                .comment("Does the diving weight ignore creative mode")
                .define("divingWeightIgnoreCreative", true);

        builder.pop();

        builder.push("ceruse");

        CERUSE_DURATION = builder
                .comment("Duration of the effect given by the ceruse item (in s)")
                .defineInRange("ceruseDuration", 60, 1, 240);

        CERUSE_COOLDOWN = builder
                .comment("Cooldown of the ceruse item duration (in s)")
                .defineInRange("ceruseCooldown", 5, 1, 30);

        CERUSE_AGGRO_RANGE = builder
                .comment("The range where the mobs aggro you when ceruse is applied (in blocks)")
                .defineInRange("ceruseAggroRange", 8, 1, 20);

        CERUSE_PARTICLES = builder
                .comment("Enables or not the particles effect for the player")
                .define("doesEmitParticles", true);

        builder.pop();

        builder.push("salt");

        SALT_RADIUS = builder
                .comment("The radius where the salt transforms blocks")
                .defineInRange("saltRadius", 3, 1, 6);

        SALT_PERCENTAGE = builder
                .comment("The percentage of blocks transformed by salt")
                .defineInRange("saltPercentage", 50, 10, 100);

        builder.pop();

        builder.push("saturnism");

        SATURNISM_CRAFT = builder
                .comment("The gain in lead value when you craft or cook something relative to lead")
                .defineInRange("saturnismCraft", 2, 1, 10);

        SATURNISM_PASSIVE = builder
                .comment("The gain in passive lead value when you have something relative to lead on you")
                .defineInRange("saturnismPassive", 5, 1, 20);

        SATURNISM_FOOD = builder
                .comment("The gain of lead value when you eat lead poisoned food")
                .defineInRange("saturnismFood", 250, 100, 1000);

        SATURNISM_SLEEP = builder
                .comment("The loss of lead value when you sleep")
                .defineInRange("saturnismSleep", 50, 10, 100);

        SATURNISM_SLEEP_WITH_LEAD = builder
                .comment("The loss of lead value when you sleep with lead items on you")
                .defineInRange("saturnismSleepWithLead", 20, 5, 60);

        SATURNISM_EFFECT_THRESHOLD = builder
                .comment("The lead value you need to have to get the Saturnism effect")
                .defineInRange("saturnismEffectThreshold", 450, 200, 2000);

        COMMON_SPEC = builder.build();
    }
}