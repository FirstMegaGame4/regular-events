package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class ElderGuardianSpawningEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.OMINOUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Elder Guardian Spawning");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Spawn an Elder Guardian on a random person");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			ElderGuardianEntity elderGuardianEntity = new ElderGuardianEntity(EntityType.ELDER_GUARDIAN, world);
			elderGuardianEntity.setPosition(player.getPos());
			elderGuardianEntity.setTarget(player);
			world.spawnEntity(elderGuardianEntity);
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" should worry about the Elder Guardian near to them..."));
		});
	}
}
