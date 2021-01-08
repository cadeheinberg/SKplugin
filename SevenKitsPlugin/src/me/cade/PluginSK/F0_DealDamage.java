package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class F0_DealDamage {

  public static void dealAmount(Player killer, LivingEntity victim, double amount) {
    if(killer.getName().equals(victim.getName())) {
      return;
    }
    if (victim.getHealth() <= 0) {
      return;
    }
    if(victim instanceof Player) {
      if(M_Bounty.isBountyOn()) {
        if (!victim.equals(M_Bounty.getBountySetOn())) {
          if(!killer.equals(M_Bounty.getBountySetOn())) {
            killer.sendMessage(ChatColor.RED + "Attack the player with a" + ChatColor.AQUA + ""
              + ChatColor.BOLD + " Bounty " + ChatColor.RED + "set on them!");
            return;
          }
        }
      }
      if(((Player) victim).getGameMode() == GameMode.CREATIVE) {
        return;
      }
      EntityDamageByEntityEvent damage =
        new EntityDamageByEntityEvent(killer, (Player) victim, DamageCause.ENTITY_ATTACK, amount);
      Bukkit.getServer().getPluginManager().callEvent(damage);
    }
    victim.damage(0.01);
    if((victim.getHealth() - amount) <= 0) {
      //kill living entity
      victim.setHealth(0);
    }else {
      victim.setHealth((victim.getHealth() - amount));
    }
  }
}
