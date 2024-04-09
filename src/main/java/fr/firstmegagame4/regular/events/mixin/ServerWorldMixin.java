package fr.firstmegagame4.regular.events.mixin;

import fr.firstmegagame4.regular.events.api.DelayedEvent;
import fr.firstmegagame4.regular.events.api.EventDifficulty;
import fr.firstmegagame4.regular.events.api.EventUtil;
import fr.firstmegagame4.regular.events.api.RegularEvent;
import fr.firstmegagame4.regular.events.impl.PlayerFiltering;
import fr.firstmegagame4.regular.events.impl.RegularEventsImpl;
import fr.firstmegagame4.regular.events.impl.RegularEventsInitializer;
import fr.firstmegagame4.regular.events.impl.TimerAccess;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.level.ServerWorldProperties;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends WorldMixin implements TimerAccess {

	@Unique
	private final Map<DelayedEvent, MutableInt> eventTasks = new Object2ObjectOpenHashMap<>();

	@Unique
	@Nullable
	private RegularEvent beingExecuted;

	@Shadow
	@Final
	private ServerWorldProperties worldProperties;

	@Shadow
	@NotNull
	public abstract MinecraftServer getServer();

	@Inject(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerWorldProperties;setTime(J)V", shift = At.Shift.AFTER))
	private void triggerEvent(CallbackInfo ci) {
		int space = 1200 + this.getGameRules().getInt(RegularEventsInitializer.SPACE_BETWEEN_EVENTS);
		if (this.worldProperties.getTime() % space == 0) {
			this.beingExecuted = RegularEventsImpl.pickEventRandomly(this.getRandom());
			assert this.beingExecuted != null;
			EventDifficulty difficulty = this.beingExecuted.getEventDifficulty();
			EventUtil.sendTitleToPlayers(this.getServer().getPlayerManager().getPlayerList(), EventUtil.applyFormattings(difficulty.getDisplay(), difficulty.getFormattings()));
		}
		else if (this.worldProperties.getTime() % space == 120) {
			if (this.beingExecuted != null) {
				EventUtil.sendSubtitleToPlayers(this.getServer().getPlayerManager().getPlayerList(), this.beingExecuted.getEventDescription());
				EventUtil.sendTitleToPlayers(this.getServer().getPlayerManager().getPlayerList(), EventUtil.applyFormattings(this.beingExecuted.getEventDisplayName(), this.beingExecuted.getEventDifficulty().getFormattings()));
			}
		}
		else if (this.worldProperties.getTime() % space == 840) {
			if (this.beingExecuted != null) {
				EventUtil.sendTitleToPlayers(this.getServer().getPlayerManager().getPlayerList(), EventUtil.applyFormattings(Text.of("3"), this.beingExecuted.getEventDifficulty().getFormattings()));
			}
		}
		else if (this.worldProperties.getTime() % space == 960) {
			if (this.beingExecuted != null) {
				EventUtil.sendTitleToPlayers(this.getServer().getPlayerManager().getPlayerList(), EventUtil.applyFormattings(Text.of("2"), this.beingExecuted.getEventDifficulty().getFormattings()));
			}
		}
		else if (this.worldProperties.getTime() % space == 1080) {
			if (this.beingExecuted != null) {
				EventUtil.sendTitleToPlayers(this.getServer().getPlayerManager().getPlayerList(), EventUtil.applyFormattings(Text.of("1"), this.beingExecuted.getEventDifficulty().getFormattings()));
			}
		}
		else if (this.worldProperties.getTime() % space == 1200) {
			if (this.beingExecuted != null) {
				EventUtil.sendTitleToPlayers(this.getServer().getPlayerManager().getPlayerList(), EventUtil.applyFormattings(Text.of("Event Begins!"), this.beingExecuted.getEventDifficulty().getFormattings()));
				this.beingExecuted.execute(this.getServer(), PlayerFiltering.filter(this.getServer().getPlayerManager().getPlayerList()));
				if (this.beingExecuted instanceof DelayedEvent delayed) {
					this.regular_events$addEventTimer(delayed);
				}
			}
		}
		Set<Map.Entry<DelayedEvent, MutableInt>> entrySet = this.eventTasks.entrySet();
		for (Map.Entry<DelayedEvent, MutableInt> entry : entrySet) {
			MutableInt time = entry.getValue();
			time.add(1);
			if (time.getValue() >= entry.getKey().getDelay()) {
				this.eventTasks.remove(entry.getKey());
				entry.getKey().finish(this.getServer(), PlayerFiltering.filter(this.getServer().getPlayerManager().getPlayerList()));
			}
			else {
				this.eventTasks.put(entry.getKey(), time);
			}
		}
	}

	@Override
	public void regular_events$addEventTimer(DelayedEvent delayedEvent) {
		this.eventTasks.put(delayedEvent, new MutableInt());
	}
}
