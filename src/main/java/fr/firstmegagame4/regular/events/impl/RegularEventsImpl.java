package fr.firstmegagame4.regular.events.impl;

import fr.firstmegagame4.regular.events.api.DelayedEvent;
import fr.firstmegagame4.regular.events.api.EventReference;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RegularEventsImpl {

	public static final Map<Identifier, Supplier<RegularEvent>> REGISTRY = new Object2ObjectOpenHashMap<>();

	public static final List<EventReference> REFERENCES = new ObjectArrayList<>();

	public static EventReference register(Identifier identifier, Supplier<RegularEvent> event) {
		if (event instanceof DelayedEvent delayed) {
			delayed.registerData(AttachmentRegistry::createPersistent);
		}
		RegularEventsImpl.REGISTRY.put(identifier, event);
		EventReference reference = new EventReferenceImpl(identifier);
		RegularEventsImpl.REFERENCES.add(reference);
		return reference;
	}

	public static RegularEvent pickEventRandomly(Random random) {
		return RegularEventsImpl.REFERENCES.get(random.nextInt(RegularEventsImpl.REFERENCES.size())).getEvent();
	}
}
