/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.registries.tardis.SubsystemsRegistry;
import com.code.tama.tts.server.tardis.subsystems.AbstractSubsystem;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TTSCommands {

	public static LiteralArgumentBuilder<CommandSourceStack> interior = Commands.literal("interior")
			.then(Commands.argument("dimension", ResourceLocationArgument.id())
					.suggests((context, builder) -> SharedSuggestionProvider
							.suggest(tardisDimList(context.getSource().getServer()), builder))
					.executes(
							ctx -> teleportPlayer(ctx.getSource(), ResourceLocationArgument.getId(ctx, "dimension"))));
	public static LiteralArgumentBuilder<CommandSourceStack> subsystem = Commands.literal("place_subsystem")
			.then(Commands.argument("subsystem", StringArgumentType.string()).suggests((context, builder) -> {
				List<String> systems = SubsystemsRegistry.subsystems.stream().map(AbstractSubsystem::name).toList();
				return SharedSuggestionProvider.suggest(systems, builder);
			}).executes(ctx -> placeSystem(ctx.getSource(), StringArgumentType.getString(ctx, "subsystem"))));

	LiteralArgumentBuilder<CommandSourceStack> BASE = Commands.literal("tardis-tts");
	public static LiteralArgumentBuilder<CommandSourceStack> debug = Commands.literal("debug")
			.then(subsystem);
	private static int placeSystem(CommandSourceStack source, String system) {
		ServerPlayer player = source.getPlayer();
		AbstractSubsystem subsystem = SubsystemsRegistry.subsystems.stream().filter(sub -> sub.name().equals(system))
				.toList().get(0);

		subsystem.BlockMap().forEach((pos, state) -> {
			assert player != null;
			player.level().setBlockAndUpdate(
					pos.offset((int) player.position().x, (int) player.position().y + 1, (int) player.position().z),
					state);
		});

		source.sendSuccess(() -> {
			assert player != null;
			return Component.literal("Placed subsystem " + system + " at " + player.position());
		}, true);
		return Command.SINGLE_SUCCESS;
	}

	private static List<String> tardisDimList(MinecraftServer server) {
		return server.levelKeys().stream().map(ResourceKey::location).map(ResourceLocation::toString)
				.filter(dim -> dim.startsWith(TTSMod.MODID + "-tardis:")).collect(Collectors.toList());
	}

	private static int teleportPlayer(CommandSourceStack source, ResourceLocation dimension) {
		ServerPlayer player = source.getPlayer();
		if (player == null) {
			source.sendFailure(Component.literal("Command must be run by an alive, connected, player."));
			return 0;
		}

		ResourceKey<Level> targetDimension = ResourceKey.create(Registries.DIMENSION, dimension);
		ServerLevel targetLevel = source.getServer().getLevel(targetDimension);

		if (targetLevel == null) {
			source.sendFailure(Component.literal("Invalid or unloaded dimension: " + dimension));
			return 0;
		}

		AtomicInteger success = new AtomicInteger();
		success.set(-1);
		targetLevel.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent((cap) -> {
			SpaceTimeCoordinate pos = cap.GetData().getInteriorDoorData().getLocation();
			double x = pos.GetX();
			double y = pos.GetY();
			double z = pos.GetZ();
			player.teleportTo(targetLevel, x, y, z, 90, 0);
			source.sendSuccess(() -> Component.literal("Teleported to " + dimension), true);
			success.set(Command.SINGLE_SUCCESS);
		});
		if (success.get() != -1)
			return success.get();

		player.teleportTo(targetLevel, 0, 128, 0, player.getYRot(), player.getXRot());
		source.sendSuccess(() -> Component.literal("Teleported to " + dimension), true);
		return Command.SINGLE_SUCCESS;
	}

	@SubscribeEvent
	public static void onRegisterCommands(RegisterCommandsEvent event) {
		register(event.getDispatcher());
	}

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tardis-tts").then(interior).then(debug));
	}
}
