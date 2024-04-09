package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class DiamondDroppingEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.BONUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Diamond Dropping");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Drops 6 diamonds to one random player");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			EventUtil.spawnItemStackAtPos(Items.DIAMOND.getDefaultStack().copyWithCount(6), world, player.getPos().add(0, 10, 0));
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" received diamonds!"));
		});
	}
}
