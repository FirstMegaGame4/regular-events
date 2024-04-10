package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class EvokerFangSpawningEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.HARD_CHALLENGE;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Evoker Fang Spawning");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Spawn evoker fangs on three random players");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		for (int i = 0; i < 3; i++) {
			EventUtil.playerRandomAction(players, server, (world, player) -> {
				EvokerFangsEntity evokerFangsEntity = new EvokerFangsEntity(EntityType.EVOKER_FANGS, world);
				evokerFangsEntity.setPosition(player.getPos());
				world.spawnEntity(evokerFangsEntity);
				EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" has been targeted by an evoker fang!"));
			});
		}
	}
}
