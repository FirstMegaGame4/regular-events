package fr.firstmegagame4.regular.events.api;

import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.BiConsumer;

public interface DelayedEvent extends RegularEvent {

	void registerData(BiConsumer<Identifier, Codec<?>> registrar);

	int getDelay();

	void finish(MinecraftServer server, List<ServerPlayerEntity> players);
}
