package me.cade.PluginSK.KitListeners;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.BuildKits.F3_Goblin;
import me.cade.PluginSK.BuildKits.F_Stats;
import me.cade.PluginSK.Damaging.DealDamage;

public class G3_Goblin {

  // dropping the bow activates special ability
  // which is whenever a player is hit with sword
  // or arrow it slows and poisons them and barrage arrow

  public static void doDrop(Player killer) {
	if (killer.getCooldown(Material.BIRCH_FENCE) > 0 || killer.getCooldown(Material.JUNGLE_FENCE) > 0) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    G8_Cooldown.startAbilityDuration(killer, 200, 300);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    locateNearestEnemy(killer, 200);
    killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
  }

  private static void locateNearestEnemy(Player killer, int duration) {
    Player closestPlayer = null;
    double lowestDistance = 0;
    double currentDistance = 0;
    int itterator = 0;

    // search other team eventually instead
    for (Entity victim : killer.getNearbyEntities(100, 50, 100)) {
      if (victim instanceof Player) {
        currentDistance = killer.getLocation().distance(victim.getLocation());
        if (itterator == 0) {
          lowestDistance = currentDistance;
          closestPlayer = (Player) victim;
          itterator++;
          continue;
        }
        if (currentDistance < lowestDistance) {
          lowestDistance = currentDistance;
          closestPlayer = (Player) victim;
        }
        itterator++;
      }
    }

    if (closestPlayer == null) {
      killer.sendMessage(ChatColor.RED + "No enemies in range");
      return;
    }

    ArrayList<Player> viewers = new ArrayList<Player>();
    viewers.add(killer);
    // eventually should just use team
    
    //Glowing.setGlowingOn((Player) closestPlayer, killer, viewers);

    killer.sendMessage(ChatColor.AQUA + "Enemy Located");
    killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 8, 1);

  }

  public static void deActivateSpecial(Player player) {
    turnOffLocater(player);
    CraftPlayer craftPlayer = (CraftPlayer) player;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }

  private static void turnOffLocater(Player killer) {
    //Player victim = Bukkit.getPlayer(Glowing.glowMap.get(killer.getUniqueId()));
//    if (victim == null) {
//      return;
//    }
//    if (!victim.isOnline()) {
//      return;
//    }
//    ArrayList<Player> viewers = new ArrayList<Player>();
//    viewers.add(killer);
    // eventually should just use team
    //Glowing.setGlowingOff(victim, killer, viewers);
  }

  public static void doArrorwHitEntity(Player killer, LivingEntity victim, Arrow arrow) {
    //create your own form of knockback
    DealDamage.dealAmount(killer, victim, F_Stats.getDamageList(F3_Goblin.getKitID())[0]);
    if (arrow.getFireTicks() > 0) {
      victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 2));
      victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 2));
    }
  }

  public static boolean doArrowShoot(Player shooter, Arrow arrow) {
    if (shooter.getCooldown(F3_Goblin.getWeapon().getWeaponItem().getType()) > 0) {
      return false;
    }
    shooter.setCooldown(F3_Goblin.getWeapon().getWeaponItem().getType(),
      F_Stats.getTicksList(F3_Goblin.getKitID())[0]);
    if (Fighter.get(shooter).isAbilityActive()) {
      arrow.setFireTicks(1000);
    }
    return true;
  }



}
