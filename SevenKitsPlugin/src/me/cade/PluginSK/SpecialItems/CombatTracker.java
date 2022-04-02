package me.cade.PluginSK.SpecialItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class CombatTracker {

  private static Material mat = Material.GOLD_INGOT;
  private static Weapon tracker = new Weapon(mat, ChatColor.LIGHT_PURPLE + "Spawn Teleporter",
	      ChatColor.WHITE + "Right Click to go to /spawn",
	      ChatColor.WHITE + "Don't leave game while PvP cooldown is on!");
  
  private Player player;
  
  public CombatTracker(Player player) {
	  this.player = player;
	  player.getInventory().setItem(8, getTracker().getWeaponItem());
  }

  public static Weapon getTracker() {
    return tracker;
  }

  public static Material getTrackerMaterial() {
    return mat;
  }
  
  public void doRightClick() {
		if (player.getWorld() == Main.hub) {
	        if (player.getCooldown(CombatTracker.getTrackerMaterial()) > 0) {
	          player.sendMessage(ChatColor.RED + "Can't" + ChatColor.AQUA + "" + ChatColor.BOLD
	            + " /spawn" + ChatColor.RED + ". You have a" + ChatColor.AQUA + "" + ChatColor.BOLD
	            + " PVP Cooldown " + ChatColor.RED + "on");
	          return;
	        }
	        player.teleport(Main.hubSpawn);
		}
  }

}
