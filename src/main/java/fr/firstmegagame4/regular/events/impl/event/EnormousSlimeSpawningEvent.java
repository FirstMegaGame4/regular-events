package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class EnormousSlimeSpawningEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.OMINOUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Enormous Slime Spawning");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Spawns an enormous slime on one random player");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			SlimeEntity slimeEntity = new SlimeEntity(EntityType.SLIME, world);
			slimeEntity.setSize(127, true);
			slimeEntity.setPosition(player.getPos().add(0.0, 50.0, 0.0));
			world.spawnEntity(slimeEntity);
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" got an enormous slime!"));
		});
	}
}
