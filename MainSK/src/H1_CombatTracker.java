package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class H1_CombatTracker {

  private static Weapon tracker;
  private static Weapon hurt;
  private static Material mat;
  private static Material hurtMat;

  public static void makeTracker() {
    mat = Material.GOLD_INGOT;
    tracker = new Weapon(mat, ChatColor.LIGHT_PURPLE + "PvP Timer",
      ChatColor.WHITE + "Timer for when you can exit PVP",
      ChatColor.WHITE + "Don't leave game while on");

    hurtMat = Material.BRICK;
    hurt = new Weapon(hurtMat, ChatColor.LIGHT_PURPLE + "Damage Timer",
      ChatColor.WHITE + "Timer for counting death as opponent's kill" ,
      ChatColor.WHITE + "Death is counted as suicide when off");
  }

  public static Weapon getTracker() {
    return tracker;
  }

  public static Material getTrackerMaterial() {
    return mat;
  }

  public static Weapon getHurtTracker() {
    return hurt;
  }

  public static Material getHurtTrackerMaterial() {
    return hurtMat;
  }

}
