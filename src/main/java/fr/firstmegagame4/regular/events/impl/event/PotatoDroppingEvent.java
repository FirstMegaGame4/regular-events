package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;

public class PotatoDroppingEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.BONUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Falling Potatoes");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Falls potatoes upon random player");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			Vec3d pos = player.getPos().add(0, 10, 0);
			Vec3d corner = pos.add(-5, 0, -5);
			for (int i = 1; i <= 30; i++) {
				for (int j = 1; j < 30; j++) {
					if (world.getRandom().nextFloat() <= 0.15f) {
						EventUtil.spawnItemAtPos(Items.POISONOUS_POTATO, world, corner.add(i / 3.0, 0, j / 3.0));
					}
					else {
						EventUtil.spawnItemAtPos(Items.POTATO, world, corner.add(i / 3.0, 0, j / 3.0));
					}
				}
			}
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" received potatoes!"));
		});
	}
}
