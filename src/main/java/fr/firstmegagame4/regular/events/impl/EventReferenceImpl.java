package fr.firstmegagame4.regular.events.impl;

import fr.firstmegagame4.regular.events.api.EventReference;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.util.Identifier;

public class EventReferenceImpl implements EventReference {

	private final Identifier identifier;

	public EventReferenceImpl(Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public RegularEvent getEvent() {
		return RegularEventsImpl.REGISTRY.get(this.identifier).get();
	}
}
