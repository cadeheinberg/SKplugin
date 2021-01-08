package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class E0_FighterStorage {
  
  // downloads players profile from mysql
  public static void downloadFighter(E1_Fighter fighter, Player player) {
    fighter.setKitID(B_Database.getStat(player, B_Database.column[2]));
    fighter.setKitIndex(B_Database.getStat(player, B_Database.column[3]));
    fighter.setPlayerLevel(B_Database.getStat(player, B_Database.column[4]));
    fighter.setPrestige(B_Database.getStat(player, B_Database.column[5]));
    fighter.setKills(B_Database.getStat(player, B_Database.column[6]));
    fighter.setDeaths(B_Database.getStat(player, B_Database.column[7]));
    fighter.setCakes(B_Database.getStat(player, B_Database.column[8]));
    fighter.setKillStreak(B_Database.getStat(player, B_Database.column[9]));
    fighter.setKillStreakAttribute(B_Database.getStat(player, B_Database.column[10]));
    fighter.setSpeedAttribute(B_Database.getStat(player, B_Database.column[11]));
    fighter.setSpecialItemAttribute(B_Database.getStat(player, B_Database.column[12]));
    fighter.setProtectionArmorAttribute(B_Database.getStat(player, B_Database.column[13]));
    fighter.setUnlocked(0, B_Database.getStat(player, B_Database.column[14]));
    fighter.setUnlocked(1, B_Database.getStat(player, B_Database.column[15]));
    fighter.setUnlocked(2, B_Database.getStat(player, B_Database.column[16]));
    fighter.setUnlocked(3, B_Database.getStat(player, B_Database.column[17]));
    fighter.setUnlocked(4, B_Database.getStat(player, B_Database.column[18]));
    fighter.setUnlocked(5, B_Database.getStat(player, B_Database.column[19]));
    fighter.setUnlocked(6, B_Database.getStat(player, B_Database.column[20]));
    fighter.setExp(B_Database.getStat(player, B_Database.column[21]));
    fighter.setCustomColorIndex(B_Database.getStat(player, B_Database.column[22]));
    fighter.setTokens(B_Database.getStat(player, B_Database.column[23]));
  }

  // uploads players profile to save on mysql
  public static void uploadFighter(E1_Fighter fighter, Player player) {
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[2], fighter.getKitID());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[3], fighter.getKitIndex());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[4], fighter.getPlayerLevel());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[5], fighter.getPrestige());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[6], fighter.getKills());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[7], fighter.getDeaths());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[8], fighter.getCakes());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[9], fighter.getKillStreak());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[10], fighter.getKillStreakAttribute());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[11], fighter.getSpeedAttribute());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[12], fighter.getSpecialItemAttribute());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[13], fighter.getProtectionArmorAttribute());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[14], fighter.getUnlocked()[0]);
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[15], fighter.getUnlocked()[1]);
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[16], fighter.getUnlocked()[2]);
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[17], fighter.getUnlocked()[3]);
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[18], fighter.getUnlocked()[4]);
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[19], fighter.getUnlocked()[5]);
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[20], fighter.getUnlocked()[6]);
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[21], fighter.getExp());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[22], fighter.getCustomColorIndex());
    B_Database.setStat(player.getUniqueId().toString(), B_Database.column[23], fighter.getTokens());
  }
  
  public static void applyKillStreakAttribute(E1_Fighter fighter, Player player, int killStreak, int killStreakAttribute) {
    if (killStreak >= 2) {
      if (killStreakAttribute == 1) {
        removeKillStreakAttributes(fighter, player);
        player.sendMessage(
          ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Super Jump Killstreak applied");
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 1));
        if(!Zgen.safeZone(player.getLocation())) {
          player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 8, 2);
        }
        return;
      }
    }
    if (killStreak >= 4) {
      if (killStreakAttribute == 2) {
        removeKillStreakAttributes(fighter, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 0));
        player.sendMessage(
          ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Harder punches Killstreak applied");
        if(!Zgen.safeZone(player.getLocation())) {
          player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 8, 2);
        }
        return;
      }
    }
    if (killStreak >= 8) {
      if (killStreakAttribute == 3) {
        removeKillStreakAttributes(fighter, player);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
        player.setHealth(30);
        fighter.setJuggerSet(true);
        player.sendMessage(
          ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Juggernaut health Killstreak applied");
        if(!Zgen.safeZone(player.getLocation())) {
          player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 8, 2);
        }
        return;
      }
    }
  }
  
  public static void removeKillStreakAttributes(E1_Fighter fighter, Player player) {
    player.removePotionEffect(PotionEffectType.JUMP);
    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    fighter.setJuggerSet(false);
  }
  
  public static void applyAttributes(E1_Fighter fighter, Player player, Plugin plugin) {
    applySpeedAttribute(fighter, player, plugin);
    applyKillStreakAttribute(fighter, player, fighter.getKillStreak(), fighter.getKillStreakAttribute());
    if (fighter.getKillStreakAttribute() == 3 && fighter.getKillStreak() >= 8) {
      if (Zgen.safeZone(player.getLocation())) {
        player.setExp(0);
        player.setFoodLevel(20);
        player.setHealth(30);
      }
    } else {
      if (Zgen.safeZone(player.getLocation())) {
        player.setExp(0);
        player.setFoodLevel(20);
        player.setHealth(20);
      }
    }
    E0_FighterStorage.applySpecialItemAttribute(fighter, player);
    player.getInventory().addItem(I1_Parachute.getParachute().getWeaponItem());
  }
  
  public static void applySpecialItemAttribute(E1_Fighter fighter, Player player) {
    if (fighter.getSpecialItemAttribute() == 0) {
      return;
    } else if (fighter.getSpecialItemAttribute() == 1) {
      player.getInventory().addItem(I2_TNT.getTNT().getWeaponItem());
      return;
    } else if (fighter.getSpecialItemAttribute() == 2) {
      player.getInventory().addItem(I3_PearlGun.getGun().getWeaponItem());
      return;
    } else if (fighter.getSpecialItemAttribute() == 3) {
      player.getInventory().addItem(I4_TruthSword.getSword().getWeaponItem());
      return;
    }
  }

  public static void applySpeedAttribute(E1_Fighter fighter, Player player, Plugin plugin) {
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        player
          .addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0, true, false));
        if (fighter.getSpeedAttribute() == 0) {
          return;
        } else if (fighter.getSpeedAttribute() == 1) {
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
          return;
        } else if (fighter.getSpeedAttribute() == 2) {
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
          return;
        } else if (fighter.getSpeedAttribute() == 3) {
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
          return;
        }
      }
    }, 2);
  }

}
