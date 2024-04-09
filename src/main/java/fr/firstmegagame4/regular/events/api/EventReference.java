package fr.firstmegagame4.regular.events.api;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface EventReference {

	Identifier getIdentifier();

	RegularEvent getEvent();
}
