package me.cade.PluginSK.KitListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Main;

public class G8_Cooldown {

	private static Plugin plugin = Main.getPlugin(Main.class);
	
	public static void launchFirework(Player killer) {
	      Firework firework = killer.getWorld().spawn(killer.getLocation(), Firework.class);
	      FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
	      data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.YELLOW)
	        .with(Type.BALL_LARGE).withFlicker().build());
	      data.setPower(1);
	      firework.setFireworkMeta(data);
	}

	public static void startAbilityDuration(Player player, int durationTicks, int rechargeTicks) {
		launchFirework(player);
		Fighter.fighters.get(player.getUniqueId()).setAbilityActive(true);
		Fighter.fighters.get(player.getUniqueId()).setAbilityRecharged(false);
		player.setCooldown(Material.BIRCH_FENCE, durationTicks);
		int cooldownTask = new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Cooldown Task Running");
				if (player != null) {
					if (!player.isOnline()) {
						return;
					}
					if (player.getCooldown(Material.BIRCH_FENCE) < 1) {
						cancel();
						player.sendMessage("called 3");
						Fighter.get(player).setAbilityActive(false);
						player.setExp(0);
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 8, 1);
						startAbilityRecharge(player, rechargeTicks);
						return;
					}
					player.setExp(((float) player.getCooldown(Material.BIRCH_FENCE)) / durationTicks);
				}
			}
		}.runTaskTimer(plugin, 0L, 1L).getTaskId();
		Fighter.get(player).setCooldownTask(cooldownTask);
	}

	public static void startAbilityRecharge(Player player, int rechargeTicks) {
		player.setCooldown(Material.JUNGLE_FENCE, rechargeTicks);
		int rechargeTask = new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Recharge Task Running");
				if (player != null) {
					if (!player.isOnline()) {
						return;
					}
					if (player.getCooldown(Material.JUNGLE_FENCE) < 1) {
						cancel();
						player.sendMessage("called 4");
						Fighter.get(player).setAbilityRecharged(true);
						player.setExp(1);
				        player.sendMessage(ChatColor.GREEN + "Special Ability Recharged");
				        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
						return;
					}
					player.setExp(
							((float) (rechargeTicks - player.getCooldown(Material.JUNGLE_FENCE))) / rechargeTicks);
				}
			}
		}.runTaskTimer(plugin, 0L, 1L).getTaskId();
		Fighter.get(player).setRechargeTask(rechargeTask);
	}

}
