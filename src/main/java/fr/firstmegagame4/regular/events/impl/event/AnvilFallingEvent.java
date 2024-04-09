package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class AnvilFallingEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.MALUS;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Anvil Falling Event");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Falls 10 anvils randomly on players");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		for (int i = 0; i < 10; i++) {
			EventUtil.playerRandomAction(players, server, (world, player) -> {
				world.setBlockState(player.getBlockPos().up(25), Blocks.ANVIL.getDefaultState());
				EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" has been targeted by an anvil!"));
			});
		}
	}
}
