package me.cade.PluginSK.KitListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import me.cade.PluginSK.Glowing;
import me.cade.PluginSK.PlayerGlow;

public class G3_Goblin {
  
  // dropping the bow activates special ability
  // which is whenever a player is hit with sword
  // or arrow it slows and poisons them and barrage arrow
  
  public static void doDrop(Player killer) {
    if(killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    G8_Cooldown.startAbilityDuration(killer, 200, 300);
    locateNearestEnemy(killer);
    killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
  }
  
  private static void locateNearestEnemy(Player killer) {
    for( Entity victim : killer.getNearbyEntities(100, 50, 100)) {
      if(victim instanceof Player) {
        killer.sendMessage(ChatColor.AQUA + "Enemy Located");
        killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 8, 1);
        PlayerGlow.setPlayerGlowOn((Player) victim, new Player[] {killer});
        return;
      }
    }
    killer.sendMessage(ChatColor.RED + "No enemies in range");
  }
  
  public static void deActivateSpecial(Player player) {
    turnOffLocater(player);
  }
  
  private static void turnOffLocater(Player killer) {
    Player victim = Bukkit.getPlayer(Glowing.glowMap.get(killer.getUniqueId()));
    if(victim == null) {
      return;
    }
    if(!victim.isOnline()) {
      return;
    }
    Glowing.setGlowing(victim, killer, false);
  }
  
  public static void doArrorwHitEntity(Player killer, LivingEntity victim, Arrow arrow) {
//    if(arrow.getFireTicks() > 0) {
//      victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 2));
//      victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 2));
//    }
  }
  
  public static void doArrowShoot(Player shooter, Arrow arrow) {
//    if (Fighter.fighters.get((shooter).getUniqueId()).isFighterAbility()) {
//      arrow.setFireTicks(1000);
    
//      maybe do barrage
    
//    }

  }



}
