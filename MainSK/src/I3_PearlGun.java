package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class I3_PearlGun {

  private static Weapon gun;

  public static void makeGun() {
    gun = new Weapon(Material.SHEARS, ChatColor.YELLOW + "Pearl Gun", "Right click to shoot");
  }

  public static Weapon getGun() {
    return gun;
  }
  
  public static void doPearl(Player player) {
    if(player.getCooldown(Material.SHEARS) > 0) {
      return;
    }
    Snowball pearl = player.launchProjectile(Snowball.class);
    pearl.addPassenger(player);
    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 8, 1);
    player.setCooldown(Material.SHEARS, 350);
  }
  
}
