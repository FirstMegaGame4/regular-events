package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class WardenSpawningEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.OMINOUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Warden Spawning");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Spawn a Warden 50 blocks above a random person");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			WardenEntity wardenEntity = new WardenEntity(EntityType.WARDEN, world);
			wardenEntity.setPosition(player.getPos().add(0.0, 50.0, 0.0));
			wardenEntity.setTarget(player);
			world.spawnEntity(wardenEntity);
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" should worry about the Warden near to them..."));
		});
	}
}
