package me.cade.PluginSK.SpecialItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class H1_CombatTracker {

  private static Weapon tracker;
  private static Material mat;

  public static void makeTracker() {
    mat = Material.GOLD_INGOT;
    tracker = new Weapon(mat, ChatColor.LIGHT_PURPLE + "Spawn Teleporter",
      ChatColor.WHITE + "Right Click to go to /spawn",
      ChatColor.WHITE + "Don't leave game while PvP cooldown is on!");

  }

  public static Weapon getTracker() {
    return tracker;
  }

  public static Material getTrackerMaterial() {
    return mat;
  }
  
  public static void doRightClick(Player player) {
		if (player.getWorld() == Main.hub) {
	        if (player.getCooldown(H1_CombatTracker.getTrackerMaterial()) > 0) {
	          player.sendMessage(ChatColor.RED + "Can't" + ChatColor.AQUA + "" + ChatColor.BOLD
	            + " /spawn" + ChatColor.RED + ". You have a" + ChatColor.AQUA + "" + ChatColor.BOLD
	            + " PVP Cooldown " + ChatColor.RED + "on");
	          return;
	        }
	        player.teleport(Main.hubSpawn);
		}
  }

}
