package fr.firstmegagame4.regular.events.impl.event;

import fr.firstmegagame4.regular.events.api.DefaultEventDifficulties;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Objects;

public class WaterBlowingEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.BALANCED;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Water Blowing");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("They are fine");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			for (int i = 0; i <= 9; i++) {
				for (int j = 0; j <= 9; j++) {
					for (int k = 0; k <= 9; k++) {
						BlockPos pos = player.getBlockPos().add(-4 + i, -4 + j, -4 + k);
						if (world.isAir(pos)) {
							world.setBlockState(pos, Blocks.WATER.getDefaultState());
						}
					}
				}
			}
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" has been water bloomed!"));
		});
	}
}
