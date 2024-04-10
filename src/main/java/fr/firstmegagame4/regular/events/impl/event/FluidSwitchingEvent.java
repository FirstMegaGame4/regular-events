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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FluidSwitchingEvent implements RegularEvent {

	@Override
	public EventDifficulty getEventDifficulty() {
		return DefaultEventDifficulties.HARD_CHALLENGE;
	}

	@Override
	public Text getEventDisplayName() {
		return Text.of("Fluid Switching");
	}

	@Override
	public Text getEventDescription() {
		return Text.of("Switches fluids nearest from one random player");
	}

	@Override
	public void execute(MinecraftServer server, List<ServerPlayerEntity> players) {
		EventUtil.playerRandomAction(players, server, (world, player) -> {
			Set<BlockPos> waterPositions = new HashSet<>();
			Set<BlockPos> lavaPositions = new HashSet<>();
			for (int i = 0; i <= 9; i++) {
				for (int j = 0; j <= 9; j++) {
					for (int k = 0; k <= 9; k++) {
						BlockPos pos = player.getBlockPos().add(-4 + i, -4 + j, -4 + k);
						if (world.testBlockState(pos, state -> state.isOf(Blocks.WATER))) {
							waterPositions.add(pos);
						}
						else if (world.testBlockState(pos, state -> state.isOf(Blocks.LAVA))) {
							lavaPositions.add(pos);
						}
					}
				}
			}
			waterPositions.forEach(pos -> world.setBlockState(pos, Blocks.AIR.getDefaultState()));
			lavaPositions.forEach(pos -> world.setBlockState(pos, Blocks.WATER.getDefaultState()));
			waterPositions.forEach(pos -> world.setBlockState(pos, Blocks.LAVA.getDefaultState()));
			EventUtil.shout(server, Objects.requireNonNull(player.getDisplayName()).copy().append(" got their nearest fluids replaced!"));
		});
	}
}
