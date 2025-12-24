package com.axedgaming.leadage.common.effects;

import com.axedgaming.leadage.Config;
import com.axedgaming.leadage.common.ModCapabilities;
import com.axedgaming.leadage.common.network.MaskingSyncPacket;
import com.axedgaming.leadage.common.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

import java.util.UUID;

public class MaskingEffect extends MobEffect {

    private static final String TEAM_NAME = "leadage_masked";

    private static final UUID MASKING_AGGRO_UUID =
            UUID.fromString("7c8a2c3d-0c3d-4f6a-9e92-6d64f4eae111");

    public MaskingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof ServerPlayer player)) return;

        player.getCapability(ModCapabilities.MASKING).ifPresent(data -> {
            if (!data.isMasked()) {
                data.setMasked(true);

                NetworkHandler.sendToTracking(
                        player,
                        new MaskingSyncPacket(player.getId(), true)
                );
            }
        });

        Scoreboard scoreboard = player.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(TEAM_NAME);

        if (team == null) {
            team = scoreboard.addPlayerTeam(TEAM_NAME);
            team.setNameTagVisibility(Team.Visibility.NEVER);
        }

        if (scoreboard.getPlayersTeam(player.getScoreboardName()) != team) {
            scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
        }
    }

    @Override
    public void addAttributeModifiers(
            LivingEntity entity,
            AttributeMap attributes,
            int amplifier
    ) {
        super.addAttributeModifiers(entity, attributes, amplifier);

        if (!(entity instanceof Player player)) return;

        var followRange = player.getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange == null) return;

        double targetRange = Config.CERUSE_AGGRO_RANGE.get();

        followRange.removeModifier(MASKING_AGGRO_UUID);

        double base = followRange.getBaseValue();
        double delta = targetRange - base;

        followRange.addTransientModifier(new AttributeModifier(
                MASKING_AGGRO_UUID,
                "Masking fixed aggro range",
                delta,
                AttributeModifier.Operation.ADDITION
        ));
    }

    @Override
    public void removeAttributeModifiers(
            LivingEntity entity,
            AttributeMap attributes,
            int amplifier
    ) {
        super.removeAttributeModifiers(entity, attributes, amplifier);

        if (!(entity instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        player.getCapability(ModCapabilities.MASKING).ifPresent(data -> {
            if (data.isMasked()) {
                data.setMasked(false);

                NetworkHandler.sendToTracking(
                        player,
                        new MaskingSyncPacket(player.getId(), false)
                );
            }
        });

        Scoreboard scoreboard = player.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(TEAM_NAME);

        if (team != null) {
            scoreboard.removePlayerFromTeam(player.getScoreboardName(), team);
        }

        // ===== Attribute cleanup =====
        var followRange = player.getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null) {
            followRange.removeModifier(MASKING_AGGRO_UUID);
        }
    }
}