package me.cade.PluginSK;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class I4_TruthSword {

  private static Weapon sword;

  public static void makeSword() {
    sword = new Weapon(Material.STICK, ChatColor.YELLOW + "Stick Of Truth", "ouchie");
    sword.applyWeaponUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);
    sword.applyWeaponUnsafeEnchantment(Enchantment.KNOCKBACK, 20);
  }

  public static Weapon getSword() {
    return sword;
  }
  
}
