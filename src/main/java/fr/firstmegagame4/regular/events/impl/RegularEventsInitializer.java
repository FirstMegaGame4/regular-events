package fr.firstmegagame4.regular.events.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegularEventsInitializer implements ModInitializer {

	public static final GameRules.Key<GameRules.IntRule> SPACE_BETWEEN_EVENTS = GameRuleRegistry.register("spaceBetweenEvents", GameRules.Category.MISC, GameRuleFactory.createIntRule(10800, 1));

    public static final Logger LOGGER = LoggerFactory.getLogger(RegularEventsInitializer.id());

	@Override
	public void onInitialize() {
		RegularEventsInitializer.LOGGER.info("Starting Regular Events Initialization...");

		DefaultRegularEvents.register();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> RegularEventsCommands.registerCommands(dispatcher));

		RegularEventsInitializer.LOGGER.info("Ending Regular Events Initialization...");
	}

	public static String id() {
		return "regular_events";
	}

	public static Identifier createId(String path) {
		return new Identifier(RegularEventsInitializer.id(), path);
	}
}
