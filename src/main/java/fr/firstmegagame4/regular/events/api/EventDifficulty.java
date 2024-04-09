package fr.firstmegagame4.regular.events.api;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Set;

public interface EventDifficulty {

	Text getDisplay();

	Set<Formatting> getFormattings();
}
