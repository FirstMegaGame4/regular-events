package fr.firstmegagame4.regular.events.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EventUtil {

	public static void shout(MinecraftServer server, Text message) {
		server.getPlayerManager().getPlayerList().forEach(player -> player.sendMessage(message));
	}

	public static void spawnItemAtPos(Item item, World world, Vec3d pos) {
		EventUtil.spawnItemStackAtPos(item.getDefaultStack(), world, pos);
	}

	public static void spawnItemStackAtPos(ItemStack stack, World world, Vec3d pos) {
		ItemEntity itemEntity = new ItemEntity(world, pos.x, pos.y, pos.z, stack);
		world.spawnEntity(itemEntity);
	}

	public static void spawnItemAtPosWithVelocity(Item item, World world, Vec3d pos, Vec3d velocity) {
		EventUtil.spawnItemStackAtPosWithVelocity(item.getDefaultStack(), world, pos, velocity);
	}

	public static void spawnItemStackAtPosWithVelocity(ItemStack stack, World world, Vec3d pos, Vec3d velocity) {
		ItemEntity itemEntity = new ItemEntity(world, pos.x, pos.y, pos.z, stack, velocity.x, velocity.y, velocity.z);
		world.spawnEntity(itemEntity);
	}

	public static void playerRandomAction(List<ServerPlayerEntity> players, MinecraftServer server, BiConsumer<ServerWorld, ServerPlayerEntity> action) {
		EventUtil.playerRandomAction(players, server.getOverworld().getRandom(), action);
	}

	public static void playerRandomAction(List<ServerPlayerEntity> players, Random random, BiConsumer<ServerWorld, ServerPlayerEntity> action) {
		if (!players.isEmpty()) {
			ServerPlayerEntity player = players.get(random.nextInt(players.size()));
			action.accept(player.getServerWorld(), player);
		}
	}

	public static <A> Map<ServerPlayerEntity, A> getInfoForPlayers(List<ServerPlayerEntity> players, AttachmentType<A> type) {
		Map<ServerPlayerEntity, A> map = new Object2ObjectOpenHashMap<>();
		players.forEach(player -> map.put(player, player.getAttached(type)));
		return map;
	}

	public static void sendTitleToPlayers(List<ServerPlayerEntity> players, Text text) {
		players.forEach(player -> player.networkHandler.sendPacket(new TitleS2CPacket(text)));
	}

	public static void sendSubtitleToPlayers(List<ServerPlayerEntity> players, Text text) {
		players.forEach(player -> player.networkHandler.sendPacket(new SubtitleS2CPacket(text)));
	}

	public static Text applyFormattings(Text text, Formatting... formattings) {
		return EventUtil.applyFormattings(text, Arrays.stream(formattings).collect(Collectors.toSet()));
	}

	public static Text applyFormattings(Text text, Set<Formatting> formattings) {
		MutableText mutableText = text.copy();
		formattings.forEach(mutableText::formatted);
		return mutableText;
	}
}
