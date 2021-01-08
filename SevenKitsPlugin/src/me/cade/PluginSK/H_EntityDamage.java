package me.cade.PluginSK;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;
import me.cade.PluginSK.kitbuilders.F1_Noob;
import me.cade.PluginSK.kitbuilders.F2_Booster;
import me.cade.PluginSK.kitbuilders.F3_Shotty;
import me.cade.PluginSK.kitbuilders.F4_Goblin;
import me.cade.PluginSK.kitbuilders.F5_Igor;
import me.cade.PluginSK.kitbuilders.F6_Heavy;
import me.cade.PluginSK.kitbuilders.F7_Wizard;
import me.cade.PluginSK.kitlisteners.G6_Heavy;

public class H_EntityDamage implements Listener {

  @EventHandler
  public void onDamage(EntityDamageEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())) {
      if(Zgen.worldSandSafe(e.getEntity().getLocation())) {
        if(e.getEntity() instanceof LivingEntity) {
          if(e.getEntity() instanceof Monster) {
            e.getEntity().remove();
            return;
          }
        }
        e.setCancelled(true);
      }
      return;
    }
    if (Zgen.safeZone(e.getEntity().getLocation())) {
      if (Zgen.soccerZone(e.getEntity().getLocation())) {
        if (e.getCause() == DamageCause.SUFFOCATION) {
          if (e.getEntityType() == EntityType.PANDA) {
            // if penalty
            e.getEntity().remove();
            A_Main.spawnSoccerBall(A_Main.ballSpawn);
            if (J1_SoccerMode.getLastToKick() == null) {
              J1_SoccerMode
                .tellSoccerMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Penalty! Respawning..");
            } else {
              J1_SoccerMode.tellSoccerMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Penalty on "
                + J1_SoccerMode.getLastToKick().getName() + "!" + " Respawning..");
            }
            J1_SoccerMode.teleportAllBack();
          }
        }
        e.setCancelled(true);
        return;
      } else if (Zgen.inPlayGroundPVP(e.getEntity().getLocation())) {
        e.setDamage(0);
        return;
      }
      e.setCancelled(true);
      return;
    }
    if (!(e.getEntity() instanceof Player)) {
      if (e.getEntityType() == EntityType.CHICKEN) {
        e.setCancelled(true);
        return;
      }
      return;
    }
    if (e.getCause() == DamageCause.FALL) {
      e.setCancelled(true);
      if (Vars.getFighter(((Player) e.getEntity())).getNoobTask() != -1) {
        return;
      }
      if ((e.getDamage() / 2) < 1) {
        return;
      }
      if ((((((Player) e.getEntity()).getHealth()) - (e.getDamage()) / 2)) < 0) {
        ((Player) e.getEntity()).setHealth(0);
      } else {
        ((Player) e.getEntity())
          .setHealth((((((Player) e.getEntity()).getHealth()) - (e.getDamage()) / 2)));
        ((Player) e.getEntity()).playSound(((Player) e.getEntity()).getLocation(),
          Sound.ENTITY_PLAYER_HURT, 8, 1);
      }
    } else if (e.getCause() == DamageCause.LIGHTNING) {
      e.setDamage(0);
    }
  }

  @EventHandler
  public void onDamageByEntity(EntityDamageByEntityEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())) {
      return;
    }
    if (e.getCause() != EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) {
      e.setDamage(0);
      return;
    }
    if (Zgen.safeZone(e.getEntity().getLocation())) {
      if (Zgen.soccerZone(e.getEntity().getLocation())) {
        if (e.getEntity() instanceof Player) {
          e.setDamage(0);
        } else {
          e.setDamage(0);
          J1_SoccerMode.setLastToKick((Player) e.getDamager());
          launchSheep(A_Main.ballStand, (Player) e.getDamager());
        }
        return;
      } else if (Zgen.inPlayGroundPVP(e.getEntity().getLocation())) {
        if (Zgen.inPlayGroundPVP(e.getDamager().getLocation())) {
          if (e.getDamager() instanceof Player) {
            if (Vars.getFighter((Player) e.getDamager()).isBuildMode()) {
              e.setDamage(0);
            } else {
              e.getDamager().sendMessage(ChatColor.RED + "You must be in" + ChatColor.AQUA + ""
                + ChatColor.BOLD + " Sandbox Mode " + ChatColor.RED + "to do this");
              e.setCancelled(true);
            }
          }
          return;
        }
      }
      e.setCancelled(true);
      return;
    }
    Player victim;
    Player killer;

    if (!(e.getEntity() instanceof Player)) {
      e.setDamage(0);
      return;
    }

    if (e.getCause() == DamageCause.PROJECTILE) {
      e.setDamage(0);
    }

    victim = (Player) e.getEntity();

    if (!(e.getDamager() instanceof Player)) {
      if (e.getDamager().getType() == EntityType.PRIMED_TNT) {
        e.setDamage(0);
      }
      return;
    }

    killer = (Player) e.getDamager();

    if (victim.equals(killer)) {
      return;
    }
    
    if(M_Bounty.isBountyOn()) {
      if (!victim.equals(M_Bounty.getBountySetOn())) {
        if(!killer.equals(M_Bounty.getBountySetOn())) {
          killer.sendMessage(ChatColor.RED + "Attack the player with a" + ChatColor.AQUA + ""
            + ChatColor.BOLD + " Bounty " + ChatColor.RED + "set on them!");
          e.setCancelled(true);
          return;
        }
      }
    }

    E1_Fighter pKiller = Vars.getFighter(killer);
    E1_Fighter fVictim = Vars.getFighter(victim);

    fVictim.setLastDamagedBy(killer);
    fVictim.setLastTimeDamaged(System.currentTimeMillis());
    pKiller.setLastToDamage(victim);

    killer.setCooldown(H1_CombatTracker.getTrackerMaterial(), 200);
    victim.setCooldown(H1_CombatTracker.getTrackerMaterial(), 200);
    victim.setCooldown(H1_CombatTracker.getHurtTrackerMaterial(), 200);

    if (pKiller.getKitID() == 6) {
      if (killer.getPassengers() == null) {
        return;
      }
      if (killer.getPassengers().size() < 1) {
        return;
      }
      if (!(killer.getPassengers().get(0).equals(victim))) {
        return;
      }
      G6_Heavy.doHeavyThrow(killer, victim);
    }
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())) {
      Z_PlayerChat.tellPlayerMessageToAllInWorld(e.getEntity().getWorld(), e.getDeathMessage());
      return;
    }
    e.setDeathMessage("");
    Player victim = e.getEntity();
    Player killer = null;

    victim.eject();
    E1_Fighter fVictim = Vars.getFighter(victim);
    if(fVictim.isRoyaleMode()) {
      doRoyaleMiniGameDeath();
      return;
    }
    fVictim.incDeaths();

    int reward = 0;
    reward = M_Bounty.doBountyCheckOnDeath(victim, fVictim);

    killer = fVictim.getLastDamagedBy();
    if(!checkKillerStatus(killer, victim, fVictim)) {
      return;
    }
    E1_Fighter fKiller = Vars.getFighter(killer);
    fKiller.incKills();
    if (fKiller.getLastDamagedBy() != null) {
      if (fKiller.getLastDamagedBy().equals(victim)) {
        fKiller.setLastDamagedBy(null);
      }
    }
    fVictim.setLastDamagedBy(null);
    fVictim.setLastToDamage(null);

    tellDeathMessage(killer.getName(), victim.getName(), fKiller.getKitID());

    giveKillReward(fKiller, reward);
  }
  
  public static void doRoyaleMiniGameDeath() {
    
  }
  
  public static boolean checkKillerStatus(Player killer, Player victim, E1_Fighter fVictim) {
    if (killer == null) {
      return false;
    }
    if (killer.equals(victim)) {
      return false;
    }
    if (!killer.isOnline()) {
      return false;
    }
    if (System.currentTimeMillis() - fVictim.getLastTimeDamaged() >= 10000) {
      fVictim.setLastTimeDamaged(1);
      return false;
    }
    fVictim.setLastTimeDamaged(1);
    return true;
  }
  
  public void tellDeathMessage(String killerName, String victimName, int weaponID) {
    String weaponName = "";
    if (weaponID == 7) {
      weaponName = F7_Wizard.getKitName();
    } else if (weaponID == 6) {
      weaponName = F6_Heavy.getKitName();
    } else if (weaponID == 5) {
      weaponName = F5_Igor.getKitName();
    } else if (weaponID == 4) {
      weaponName = F4_Goblin.getKitName();
    } else if (weaponID == 3) {
      weaponName = F3_Shotty.getKitName();
    } else if (weaponID == 2) {
      weaponName = F2_Booster.getKitName();
    } else if (weaponID == 1) {
      weaponName = F1_Noob.getKitName();
    } else {
      weaponName = "Fists";
    }
    Z_PlayerChat.tellAllInFighterMode(ChatColor.YELLOW + "" + ChatColor.ITALIC + killerName + ChatColor.RESET + " killed "
      + ChatColor.YELLOW + "" + ChatColor.ITALIC + victimName + ChatColor.RESET
      + " using " + "" + "[" + weaponName + ChatColor.RESET + "" + ChatColor.WHITE + "]");
  }
  
  public static void giveKillReward(E1_Fighter fKiller, int reward) {
    int streak = fKiller.getKillStreak();
    if (streak >= 5) {
      fKiller.incCakes(15 + reward);
    } else if (streak >= 2) {
      fKiller.incCakes(streak + 10 + reward);
    } else {
      fKiller.incCakes(10 + reward);
    }
  }

  public static void launchSheep(LivingEntity sheep, Player player) {
    Location loc = player.getEyeLocation();
    loc.setPitch(-15);
    Vector vector = loc.getDirection();
    sheep.setVelocity(vector.multiply(2.3));
  }

}
