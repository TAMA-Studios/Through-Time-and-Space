/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TardisTeleportCommand {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("tts")
                        .then(Commands.literal("interior")
                                .then(Commands.argument("dimension", ResourceLocationArgument.id()) // FIXED
                                        // ARGUMENT
                                        // TYPE
                                        .suggests((context, builder) -> {
                                            List<String> tardisDims =
                                                    context.getSource().getServer().levelKeys().stream()
                                                            .map(ResourceKey::location)
                                                            .map(ResourceLocation::toString)
                                                            .filter(dim -> dim.startsWith("tts:tardis_"))
                                                            .collect(Collectors.toList());
                                            return SharedSuggestionProvider.suggest(tardisDims, builder);
                                        })
                                        .executes(ctx -> teleportPlayer(
                                                ctx.getSource(),
                                                ResourceLocationArgument.getId(
                                                        ctx, "dimension"))))) // FIXED ARGUMENT RETRIEVAL
                );
    }

    private static int teleportPlayer(CommandSourceStack source, ResourceLocation dimension) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("Command must be run by a player."));
            return 0;
        }

        ResourceKey<Level> targetDimension = ResourceKey.create(Registries.DIMENSION, dimension);
        ServerLevel targetLevel = source.getServer().getLevel(targetDimension);

        if (targetLevel == null) {
            source.sendFailure(Component.literal("Invalid or unloaded dimension: " + dimension));
            return 0;
        }

        player.teleportTo(targetLevel, 0, 128, 0, player.getYRot(), player.getXRot());
        source.sendSuccess(() -> Component.literal("Teleported to " + dimension + " at 0, 128, 0"), true);
        return Command.SINGLE_SUCCESS;
    }
}
