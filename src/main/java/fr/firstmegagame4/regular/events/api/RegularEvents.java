package fr.firstmegagame4.regular.events.api;

import fr.firstmegagame4.regular.events.impl.RegularEventsImpl;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class RegularEvents {

	public static EventReference register(Identifier identifier, Supplier<RegularEvent> event) {
		return RegularEventsImpl.register(identifier, event);
	}
}
