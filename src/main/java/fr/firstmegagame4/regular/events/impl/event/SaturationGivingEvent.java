package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class SaturationGivingEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.BONUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Saturation Giving Event");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Give the saturation effect randomly on one player");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1200, 0, false, false));
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" just received saturation!"));
		});
	}
}
