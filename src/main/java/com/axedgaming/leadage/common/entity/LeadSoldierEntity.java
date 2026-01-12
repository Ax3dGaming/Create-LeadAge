package com.axedgaming.leadage.common.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;

public class LeadSoldierEntity extends PathfinderMob {

    public LeadSoldierEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {


        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1D, true));

        this.targetSelector.addGoal(
                1,
                new NearestAttackableTargetGoal<>(
                        this,
                        Mob.class,
                        10,
                        true,
                        false,
                        this::isValidTarget
                )
        );
    }

    public boolean hasSword() {
        return this.getMainHandItem().getItem() instanceof SwordItem;
    }

    public boolean hasTunic() {
        ItemStack chest = getTunic();
        return chest.getItem() instanceof ArmorItem armor
                && armor.getType() == ArmorItem.Type.CHESTPLATE;
    }

    public boolean hasColoredTunic() {
        ItemStack chest = getTunic();
        return chest.getItem() instanceof DyeableLeatherItem dyeable
                && dyeable.hasCustomColor(chest);
    }

    public int getTunicColorRGBOrMinus1() {
        ItemStack chest = getTunic();
        if (chest.getItem() instanceof DyeableLeatherItem dyeable
                && dyeable.hasCustomColor(chest)) {
            return dyeable.getColor(chest);
        }
        return -1;
    }

    public ItemStack getTunic() {
        return this.getItemBySlot(EquipmentSlot.CHEST);
    }

    private void onEquipmentChanged() {
        LivingEntity target = this.getTarget();
        if (target != null && !isValidTarget(target)) {
            this.setTarget(null);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.isEmpty() && player.isShiftKeyDown()) {
            ItemStack chest = getTunic();
            if (!chest.isEmpty()) {
                if (!level().isClientSide) {
                    this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                    this.spawnAtLocation(chest);
                    onEquipmentChanged();
                }
                return InteractionResult.sidedSuccess(level().isClientSide);
            }

            ItemStack main = this.getMainHandItem();
            if (!main.isEmpty()) {
                if (!level().isClientSide) {
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    this.spawnAtLocation(main);
                    onEquipmentChanged();
                }
                return InteractionResult.sidedSuccess(level().isClientSide);
            }
        }

        if (stack.getItem() instanceof SwordItem && this.getMainHandItem().isEmpty()) {
            if (!level().isClientSide) {
                this.setItemSlot(EquipmentSlot.MAINHAND, stack.copyWithCount(1));
                stack.shrink(1);
                onEquipmentChanged();
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (stack.getItem() instanceof DyeableLeatherItem && getTunic().isEmpty()) {
            if (!level().isClientSide) {
                this.setItemSlot(EquipmentSlot.CHEST, stack.copyWithCount(1));
                stack.shrink(1);
                onEquipmentChanged();
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        LivingEntity target = this.getTarget();
        if (target != null && !isValidTarget(target)) {
            this.setTarget(null);
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        ItemStack sword = this.getMainHandItem();
        if (!sword.isEmpty()) {
            this.spawnAtLocation(sword);
        }

        ItemStack tunic = getTunic();
        if (!tunic.isEmpty()) {
            this.spawnAtLocation(tunic);
        }
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return true;
    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    private boolean isValidTarget(LivingEntity target) {
        if (target == this) return false;
        if (!target.isAlive()) return false;

        boolean hasSword = hasSword();
        boolean hasTunic = hasTunic();

        if (hasSword && target instanceof Animal) {
            return true;
        }

        if (hasTunic && target instanceof Monster) {
            return true;
        }

        if (target instanceof LeadSoldierEntity other) {

            boolean thisArmed = hasSword;
            boolean otherArmed = other.hasSword();

            boolean thisColored = hasColoredTunic();
            boolean otherColored = other.hasColoredTunic();

            int thisColor = getTunicColorRGBOrMinus1();
            int otherColor = other.getTunicColorRGBOrMinus1();

            if (thisColored) {
                if (!otherColored || thisColor != otherColor) {
                    return true;
                }
            }

            if (!hasTunic && thisArmed && !other.hasTunic() && otherArmed) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        this.swing(InteractionHand.MAIN_HAND);
        return super.doHurtTarget(target);
    }

    @Override
    public boolean isAggressive() {
        return this.getTarget() != null;
    }

}