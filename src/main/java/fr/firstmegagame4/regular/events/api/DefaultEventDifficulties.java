package fr.firstmegagame4.regular.events.api;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DefaultEventDifficulties implements EventDifficulty {

	GREAT_BONUS(Text.translatableWithFallback(
		"event_difficulty.regular_events.great_bonus",
		"Great Bonus"
	), Formatting.BLUE),

	BONUS(Text.translatableWithFallback(
		"event_difficulty.regular_events.bonus",
		"Bonus"
	), Formatting.AQUA),

	GOOD_CHALLENGE(Text.translatableWithFallback(
		"event_difficulty.regular_events.good_challenge",
		"Bad Challenge"
	), Formatting.GREEN),

	BALANCED(Text.translatableWithFallback(
		"event_difficulty.regular_events.balanced",
		"Balanced"
	), Formatting.YELLOW),

	BAD_CHALLENGE(Text.translatableWithFallback(
		"event_difficulty.regular_events.bad_challenge",
		"Good Challenge"
	), Formatting.GOLD),

	MALUS(Text.translatableWithFallback(
		"event_difficulty.regular_events.malus",
		"Malus"
	), Formatting.RED),

	HORRIBLE_MALUS(Text.translatableWithFallback(
		"event_difficulty.regular_events.horrible_malus",
		"Horrible Malus"
	), Formatting.DARK_RED),

	OMINOUS(Text.translatableWithFallback(
		"event_difficulty.regular_events.horrible_malus",
		"Ominous"
	), Formatting.DARK_PURPLE);

	private final Text display;
	private final Set<Formatting> formattings;

	DefaultEventDifficulties(Text display, Formatting... formattings) {
		this.display = display;
		this.formattings = Arrays.stream(formattings).collect(Collectors.toSet());
	}

	@Override
	public Text getDisplay() {
		return this.display;
	}

	@Override
	public Set<Formatting> getFormattings() {
		return this.formattings;
	}
}
