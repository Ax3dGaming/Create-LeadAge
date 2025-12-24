package com.axedgaming.leadage.common.commands;

import com.mojang.brigadier.Command;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CheckPoisonTagCommand {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("checkpoison")
                        .requires(source -> source.hasPermission(0))
                        .executes(ctx -> run(ctx.getSource()))
        );
    }

    private static int run(CommandSourceStack source) {
        if (!(source.getEntity() instanceof Player player)) {
            source.sendFailure(Component.literal("Player only"));
            return Command.SINGLE_SUCCESS;
        }

        ItemStack stack = player.getMainHandItem();

        boolean poisoned =
                stack.hasTag()
                        && stack.getTag().getBoolean("lead_poisoned");

        source.sendSuccess(
                () -> Component.literal(
                        "lead_poisoned = " + poisoned
                ),
                false
        );

        return Command.SINGLE_SUCCESS;
    }
}