package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Z_PlayerChat implements Listener {
  
  @EventHandler
  public void chatEvent(AsyncPlayerChatEvent e) {
    e.setCancelled(true);
    tellPlayerMessageToAll(calculatePlayerMessage(e.getPlayer(), e.getMessage()));
  }
  
  
  public static String calculatePlayerMessage(Player player, String note) {
    E1_Fighter pFight = Vars.getFighter(player);
    int numLevel = pFight.getPlayerLevel();
    int numPrestige = pFight.getPrestige();
    String message = "";
    String levelPre = "";
    if (numPrestige == 1) {
      levelPre = ChatColor.LIGHT_PURPLE + "" + numLevel;
    } else if (numPrestige == 2) {
      levelPre = ChatColor.AQUA + "" + numLevel;
    } else if (numPrestige == 3) {
      levelPre = ChatColor.RED + "" + numLevel;
    }
    if (player.hasPermission("seven.owner")) {
      message = levelPre + ChatColor.RED + "" + ChatColor.BOLD + "" + " OWNER" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    } else if (player.hasPermission("seven.admin")) {
      message = levelPre + ChatColor.GOLD + "" + ChatColor.BOLD + "" + " ADMIN" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    } else if (player.hasPermission("seven.builder")) {
      message = levelPre + ChatColor.GREEN + "" + ChatColor.BOLD + "" + " BUILDER" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    }  else if (player.hasPermission("seven.vip")) {
      message = levelPre + ChatColor.AQUA + "" + ChatColor.BOLD + "" + " VIP" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    } else {
      message =
        levelPre + ChatColor.GRAY + " " + player.getName() + ": " + ChatColor.GRAY + note;
    }
    return message;
  }
  
  public static void tellAllInFighterMode(String note) {
    for(String name : J0_FIghterMode.getInMode()) {
      if(Bukkit.getPlayer(name) != null) {
        if(Bukkit.getPlayer(name).isOnline()) {
          Bukkit.getPlayer(name).sendMessage(note);
        }
      }
    }
    return;
  }
  
  public static void tellPlayerMessageToAll(String note) {
    for (Player inWorld : Bukkit.getOnlinePlayers()) {
      inWorld.sendMessage(note);
    }
  }
  
  public static void tellPlayerMessageToAllInWorld(World w, String note) {
    for (Player inWorld : w.getPlayers()) {
      inWorld.sendMessage(note);
    }
  }


}