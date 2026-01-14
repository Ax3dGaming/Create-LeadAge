package com.axedgaming.leadage.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.ServerLevelAccessor;

public class LeadSoldierEntity extends Zombie {

    private static final EntityDimensions PLAYER_DIMENSIONS =
            EntityDimensions.fixed(0.6F, 1.8F);

    public LeadSoldierEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.setBaby(false);
        this.refreshDimensions();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.removeAllGoals(goal -> true);
        this.targetSelector.addGoal(
                1,
                new NearestAttackableTargetGoal<>(
                        this,
                        LivingEntity.class,
                        10,
                        true,
                        false,
                        this::isValidTarget
                )
        );
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return PLAYER_DIMENSIONS;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.62F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor level,
            DifficultyInstance difficulty,
            MobSpawnType reason,
            SpawnGroupData spawnData,
            CompoundTag tag
    ) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
        this.setBaby(false);
        this.setCanBreakDoors(false);
        this.setCanPickUpLoot(false);
        this.refreshDimensions();
        return data;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.isEmpty() && player.isShiftKeyDown()) {
            ItemStack chest = getItemBySlot(EquipmentSlot.CHEST);
            if (!chest.isEmpty()) {
                if (!level().isClientSide) {
                    setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                    spawnAtLocation(chest);
                }
                return InteractionResult.sidedSuccess(level().isClientSide);
            }
        }

        if (stack.getItem() instanceof SwordItem && getMainHandItem().isEmpty()) {
            if (!level().isClientSide) {
                setItemSlot(EquipmentSlot.MAINHAND, stack.copyWithCount(1));
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (stack.getItem() instanceof DyeableLeatherItem && getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            if (!level().isClientSide) {
                setItemSlot(EquipmentSlot.CHEST, stack.copyWithCount(1));
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        if (!getMainHandItem().isEmpty()) {
            spawnAtLocation(getMainHandItem());
        }

        ItemStack chest = getItemBySlot(EquipmentSlot.CHEST);
        if (!chest.isEmpty()) {
            spawnAtLocation(chest);
        }
    }

    private boolean isValidTarget(LivingEntity target) {
        if (target == this) return false;
        if (!target.isAlive()) return false;
        if (target instanceof Player) return false;

        boolean hasSword = getMainHandItem().getItem() instanceof SwordItem;
        boolean hasTunic = getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem;

        if (hasSword && target instanceof Animal) return true;
        if (hasTunic && target instanceof Monster) return true;

        if (target instanceof LeadSoldierEntity other) {
            ItemStack a = getItemBySlot(EquipmentSlot.CHEST);
            ItemStack b = other.getItemBySlot(EquipmentSlot.CHEST);

            if (a.getItem() instanceof DyeableLeatherItem da &&
                    b.getItem() instanceof DyeableLeatherItem db) {
                return da.getColor(a) != db.getColor(b);
            }
        }

        return false;
    }
}