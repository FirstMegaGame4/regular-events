package fr.firstmegagame4.regular.events.mixin;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public abstract class WorldMixin {

	@Shadow
	public
	abstract Random getRandom();

	@Shadow
	public abstract GameRules getGameRules();
}
