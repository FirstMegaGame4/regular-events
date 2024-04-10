package fr.firstmegagame4.regular.events.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import fr.firstmegagame4.regular.events.api.DelayedEvent;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class RegularEventsCommands {

	public static final SuggestionProvider<ServerCommandSource> NAMESPACES = (context, builder) -> {
		RegularEventsImpl.REFERENCES.forEach(eventReference -> builder.suggest(eventReference.getIdentifier().getNamespace()));
		return builder.buildFuture();
	};

	public static final SuggestionProvider<ServerCommandSource> PATHS = (context, builder) -> {
		RegularEventsImpl.REFERENCES.stream()
			.filter(reference -> reference.getIdentifier().getNamespace().equals(context.getArgument("namespace", String.class)))
			.forEach(reference -> builder.suggest(reference.getIdentifier().getPath()));
		return builder.buildFuture();
	};

	public static final SuggestionProvider<ServerCommandSource> FILTER_TYPES = (context, builder) -> {
		Arrays.stream(PlayerFiltering.FilterType.values()).map(t -> t.toString().toLowerCase()).forEach(builder::suggest);
		return builder.buildFuture();
	};

	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("regular-events").executes(RegularEventsCommands::regularEvents));
		dispatcher.register(CommandManager.literal("next-event").executes(RegularEventsCommands::nextEvent));
		dispatcher.register(
			CommandManager.literal("trigger-event")
				.requires(source -> source.hasPermissionLevel(2))
				.then(
					CommandManager.argument("namespace", StringArgumentType.word())
						.suggests(RegularEventsCommands.NAMESPACES)
						.then(
							CommandManager.argument("path", StringArgumentType.word())
								.suggests(RegularEventsCommands.PATHS)
								.executes(RegularEventsCommands::triggerEvent)
						)
				)
		);
		dispatcher.register(
			CommandManager.literal("blacklist-from-events")
				.requires(source -> source.hasPermissionLevel(2))
				.then(
					CommandManager.literal("append")
						.then(
							CommandManager.argument("player", EntityArgumentType.players())
								.then(
									CommandManager.argument("type", StringArgumentType.word())
										.suggests(RegularEventsCommands.FILTER_TYPES)
										.executes(RegularEventsCommands::appendInBlackList)
								)
						)
				)
				.then(
					CommandManager.literal("remove")
						.then(
							CommandManager.argument("player", EntityArgumentType.players())
								.executes(RegularEventsCommands::removeFromBlackList)
						)
				)
		);
	}

	private static int regularEvents(CommandContext<ServerCommandSource> context) {
		RegularEventsImpl.REFERENCES.forEach(reference -> {
			context.getSource().sendMessage(
				Text.of("- ").copy()
					.append(EventUtil.applyFormattings(Text.of(reference.getIdentifier()), Formatting.GREEN)).copy()
			);
		});
		return 1;
	}

	private static int nextEvent(CommandContext<ServerCommandSource> context) {
		int space = 1200 + context.getSource().getWorld().getGameRules().getInt(RegularEventsInitializer.SPACE_BETWEEN_EVENTS);
		int ticks = (int) (space - context.getSource().getWorld().getLevelProperties().getTime() % space);
		int seconds = ticks / 20;
		context.getSource().sendMessage(Text.of("Next Event in ").copy()
			.append(EventUtil.applyFormattings(Text.of(seconds + " "), Formatting.BLUE))
			.append(Text.of("seconds!")));
		return 1;
	}

	private static int triggerEvent(CommandContext<ServerCommandSource> context) {
		String namespace = context.getArgument("namespace", String.class);
		String path = context.getArgument("path", String.class);
		Identifier identifier = new Identifier(namespace, path);
		if (RegularEventsImpl.REGISTRY.containsKey(identifier)) {
			RegularEvent event = RegularEventsImpl.REGISTRY.get(identifier).get();
			EventDifficulty difficulty = event.getEventDifficulty();
			context.getSource().getPlayer();
			EventUtil.shout(
				context.getSource().getServer(),
				EventUtil.applyFormattings(Text.of(context.getSource().getDisplayName()), Formatting.BLUE).copy().append(" triggered Early Event!")
			);
			EventUtil.shout(context.getSource().getServer(), Text.of("Difficulty: ").copy().append(EventUtil.applyFormattings(difficulty.getDisplay(), difficulty.getFormattings())));
			EventUtil.sendSubtitleToPlayers(context.getSource().getServer().getPlayerManager().getPlayerList(), event.getEventDescription());
			EventUtil.sendTitleToPlayers(context.getSource().getServer().getPlayerManager().getPlayerList(), EventUtil.applyFormattings(event.getEventDisplayName(), difficulty.getFormattings()));
			event.execute(context.getSource().getServer(), PlayerFiltering.filter(context.getSource().getServer().getPlayerManager().getPlayerList()));
			if (event instanceof DelayedEvent delayed) {
				((TimerAccess) context.getSource().getServer().getOverworld()).regular_events$addEventTimer(delayed);
			}
			return 1;
		}
		else {
			context.getSource().sendError(Text.of(identifier).copy().append(" is not an event!"));
			return 0;
		}
	}

	private static int appendInBlackList(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		List<ServerPlayerEntity> selector = context.getArgument("player", EntitySelector.class).getPlayers(context.getSource());
		PlayerFiltering.FilterType filterType = PlayerFiltering.FilterType.valueOf(context.getArgument("type", String.class).toUpperCase());
		selector.forEach(player -> player.setAttached(PlayerFiltering.FILTER_ATTACHMENT, filterType));
		return 1;
	}

	private static int removeFromBlackList(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		List<ServerPlayerEntity> selector = context.getArgument("player", EntitySelector.class).getPlayers(context.getSource());
		selector.forEach(player -> player.removeAttached(PlayerFiltering.FILTER_ATTACHMENT));
		return 1;
	}
}
