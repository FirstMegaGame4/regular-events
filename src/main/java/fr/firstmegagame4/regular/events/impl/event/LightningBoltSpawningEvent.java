package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class LightningBoltSpawningEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.MALUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Lightning Bolt Spawning");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Spawns lighting bolts upon 3 random players");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		for (int i = 0; i < 3; i++) {
			EventUtil.playerRandomAction(players, server, (world, player) -> {
				LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, player.getServerWorld());
				lightningEntity.setPosition(player.getPos());
				world.spawnEntity(lightningEntity);
				EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" received a lightning bolt!"));
			});
		}
	}
}
