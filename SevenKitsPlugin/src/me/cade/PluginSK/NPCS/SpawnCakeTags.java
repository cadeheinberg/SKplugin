package me.cade.PluginSK.NPCS;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import me.cade.PluginSK.Main;

public class SpawnCakeTags {

  private static UUID iceCake;
  private static UUID sandCake;
  private static UUID grassCake;

  public static void makeIceCake(String time) {
    String name =
      ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + time + " min";
    Location local = new Location(Main.hub, -1014.5, 67, -143.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, true);
    iceCake = stand.getStand().getUniqueId();
  }

  public static void makeSandCake(String time) {
    String name =
      ChatColor.GREEN + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + time + " min";
    Location local = new Location(Main.hub, -1057.5, 67, -101.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, true);
    sandCake = stand.getStand().getUniqueId();
  }

  public static void makeGrassCake(String time) {
    String name =
      ChatColor.YELLOW + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + time + " min";
    Location local = new Location(Main.hub, -1092.5, 67, -93.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, true);
    grassCake = stand.getStand().getUniqueId();
  }

  public static void updateCakeTimes(double time) {
    String toString = Double.toString(time).substring(0, 3);
    if (Bukkit.getEntity(iceCake) == null) {
      makeIceCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + ""
        + toString + " min");
    }
    ((ArmorStand) Bukkit.getEntity(iceCake)).setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD
      + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
    if (Bukkit.getEntity(sandCake) == null) {
      makeSandCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + ""
        + toString + " min");
    }
    ((ArmorStand) Bukkit.getEntity(sandCake)).setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD
      + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
    if (Bukkit.getEntity(grassCake) == null) {
      makeGrassCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + ""
        + toString + " min");
    }
    ((ArmorStand) Bukkit.getEntity(grassCake)).setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD
      + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
  }

}
