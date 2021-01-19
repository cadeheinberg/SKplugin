package me.cade.PluginSK.KitListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Main;

public class G8_Cooldown {

  private static Plugin plugin = Main.getPlugin(Main.class);

  public static void startAbilityDuration(Player player, int durationTicks, int rechargeTicks) {
    Fighter.fighters.get(player.getUniqueId()).setFighterAbility(true);
    player.setCooldown(Material.BIRCH_FENCE, durationTicks);
    int cooldownTask = new BukkitRunnable() {
      @Override
      public void run() {
        if (player != null) {
          if (!player.isOnline()) {
            return;
          }
          player.setExp(((float) player.getCooldown(Material.BIRCH_FENCE)) / durationTicks);
        }
      }
    }.runTaskTimer(plugin, 0L, 1L).getTaskId();
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        Bukkit.getScheduler().cancelTask(cooldownTask);
        if (player != null) {
          if (!player.isOnline()) {
            return;
          }
          Fighter.fighters.get(player.getUniqueId()).setFighterAbility(false);
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 8, 1);
          startAbilityRecharge(player, rechargeTicks);
        }
      }
    }, durationTicks);
  }

  public static void startAbilityRecharge(Player player, int rechargeTicks) {
    player.setCooldown(Material.JUNGLE_FENCE, rechargeTicks);
    int rechargeTask = new BukkitRunnable() {
      @Override
      public void run() {
        if (player != null) {
          if (!player.isOnline()) {
            return;
          }
          if (player.getCooldown(Material.JUNGLE_FENCE) < 1) {
            player.setExp(1);
            return;
          }
          player.setExp(((float) (rechargeTicks - player.getCooldown(Material.JUNGLE_FENCE))) / rechargeTicks);
        }
      }
    }.runTaskTimer(plugin, 0L, 1L).getTaskId();
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        Bukkit.getScheduler().cancelTask(rechargeTask);
        if (player != null) {
          if (!player.isOnline()) {
            return;
          }
          player.sendMessage(ChatColor.GREEN + "Special Ability Recharged");
          player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
        }
      }
    }, rechargeTicks);
  }

}
