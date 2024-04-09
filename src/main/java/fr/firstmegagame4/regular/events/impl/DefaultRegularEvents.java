package fr.firstmegagame4.regular.events.impl;

import fr.firstmegagame4.regular.events.api.RegularEvents;
import fr.firstmegagame4.regular.events.impl.event.*;

public class DefaultRegularEvents {

	public static void register() {
		RegularEvents.register(RegularEventsInitializer.createId("anvil_falling"), AnvilFallingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("fire_blowing"), FireBlowingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("potato_dropping"), PotatoDroppingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("fluid_switching"), FluidSwitchingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("diamond_dropping"), DiamondDroppingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("water_blowing"), WaterBlowingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("wither_spawning"), WitherSpawningEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("lightning_bolt_spawning"), LightningBoltSpawningEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("freeze_blowing"), FreezeBlowingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("cobweb_blowing"), CobwebBlowingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("darkness_giving"), DarknessGivingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("evoker_fang_spawning"), EvokerFangSpawningEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("enormous_slime_spawning"), EnormousSlimeSpawningEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("experience_dropping"), ExperienceDroppingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("elder_guardian_spawning"), ElderGuardianSpawningEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("warden_spawning"), WardenSpawningEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("hunger_giving"), HungerGivingEvent::new);
		RegularEvents.register(RegularEventsInitializer.createId("saturation_giving"), SaturationGivingEvent::new);
	}
}
