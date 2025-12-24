package com.axedgaming.leadage.common.commands;

import com.axedgaming.leadage.LeadAge;
import com.axedgaming.leadage.common.ModCapabilities;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID)
public class SaturnismCommand {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("saturnism")
                        .requires(cs -> cs.hasPermission(2))
                        .then(
                                Commands.literal("get")
                                        .executes(ctx -> get(ctx.getSource()))
                        )
                        .then(
                                Commands.literal("set")
                                        .then(
                                                Commands.argument("value", IntegerArgumentType.integer(0))
                                                        .executes(ctx -> set(
                                                                ctx.getSource(),
                                                                IntegerArgumentType.getInteger(ctx, "value")
                                                        ))
                                        )
                        )
        );
    }

    private static int get(CommandSourceStack source) {
        if (!(source.getEntity() instanceof net.minecraft.server.level.ServerPlayer player)) {
            source.sendFailure(Component.literal("Player only"));
            return 0;
        }

        player.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            source.sendSuccess(
                    () -> Component.literal("Saturnism: " + data.get()),
                    false
            );
        });

        return 1;
    }

    private static int set(CommandSourceStack source, int value) {
        if (!(source.getEntity() instanceof net.minecraft.server.level.ServerPlayer player)) {
            source.sendFailure(Component.literal("Player only"));
            return 0;
        }

        player.getCapability(ModCapabilities.SATURNISM).ifPresent(data -> {
            data.set(value);
            source.sendSuccess(
                    () -> Component.literal("Saturnism set to " + value),
                    false
            );
        });

        return 1;
    }
}
