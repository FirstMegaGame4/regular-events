package fr.firstmegagame4.regular.events.api;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public interface RegularEvent {

	EventDifficulty getEventDifficulty();

	Text getEventDisplayName();

	Text getEventDescription();

	void execute(MinecraftServer server, List<ServerPlayerEntity> players);
}
