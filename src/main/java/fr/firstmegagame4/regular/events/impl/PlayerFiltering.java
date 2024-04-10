package fr.firstmegagame4.regular.events.impl;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.function.Predicate;

public class PlayerFiltering {

	public static final AttachmentType<FilterType> FILTER_ATTACHMENT = AttachmentRegistry.<FilterType>builder()
		.persistent(Codec.STRING.xmap(s -> FilterType.valueOf(s.toUpperCase()), t -> t.toString().toLowerCase()))
		.copyOnDeath()
		.buildAndRegister(RegularEventsInitializer.createId("filter"));

	public static List<ServerPlayerEntity> filter(List<ServerPlayerEntity> players) {
		return players.stream().filter(player -> {
			if (player.hasAttached(PlayerFiltering.FILTER_ATTACHMENT)) {
				FilterType filterType = player.getAttached(PlayerFiltering.FILTER_ATTACHMENT);
				assert filterType != null;
				return filterType.passes(player);
			}
			else {
				return true;
			}
		}).toList();
	}

	public enum FilterType {
		IF_SPECTATING(ServerPlayerEntity::isSpectator),
		IF_INVULNERABLE(p -> p.getAbilities().creativeMode || p.isSpectator()),
		ALWAYS(p -> true);

		private final Predicate<ServerPlayerEntity> filter;

		FilterType(Predicate<ServerPlayerEntity> filter) {
			this.filter = filter;
		}

		private boolean passes(ServerPlayerEntity player) {
			return !this.filter.test(player);
		}
	}
}
