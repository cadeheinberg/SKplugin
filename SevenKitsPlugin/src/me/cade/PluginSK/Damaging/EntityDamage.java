package me.cade.PluginSK.Damaging;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.SpecialItems.H1_CombatTracker;
import me.cade.PluginSK.PlayerChat;
import me.cade.PluginSK.SafeZone;
import me.cade.PluginSK.BuildKits.F0_Noob;
import me.cade.PluginSK.BuildKits.F1_Beserker;
import me.cade.PluginSK.BuildKits.F2_Scorch;
import me.cade.PluginSK.BuildKits.F3_Goblin;
import me.cade.PluginSK.BuildKits.F4_Igor;
import me.cade.PluginSK.BuildKits.F5_Sumo;
import me.cade.PluginSK.BuildKits.F6_Grief;
import me.cade.PluginSK.KitListeners.G6_Grief;

public class EntityDamage implements Listener {
	
	//do grief give back health special
	  @EventHandler
	  public void onDamage(EntityDamageByEntityEvent e) {
	    if(!(e.getEntity() instanceof Player)) {
	      return;
	    }
	    if (e.getCause() != EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) {
	      e.setDamage(0);
	      return;
	    }
	    if (SafeZone.safeZone(e.getEntity().getLocation())) {
	      e.setCancelled(true);
	      return;
	    }
	    
	    Player victim;
	    Player killer;

	    victim = (Player) e.getEntity();
	    killer = (Player) e.getDamager();

	    if (victim.equals(killer)) {
	      return;
	    }

	    Fighter fKiller = Fighter.get(killer);
	    Fighter fVictim = Fighter.get(victim);
	    
	    if(fKiller.getKitID() == F6_Grief.getKitID()) {
	      G6_Grief.doStealHealth(killer, fKiller, victim);
	    }

	    fVictim.setLastDamagedBy(killer);
	    fKiller.setLastToDamage(victim);

	    killer.setCooldown(H1_CombatTracker.getTrackerMaterial(), 200);
	    victim.setCooldown(H1_CombatTracker.getTrackerMaterial(), 200);

	  }
  
  //do grief give back health special
  @EventHandler
  public void onDamageByEntity(EntityDamageByEntityEvent e) {
    if(!(e.getEntity() instanceof Player)) {
      return;
    }
    if (e.getCause() != EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) {
      e.setDamage(0);
      return;
    }
    if (SafeZone.safeZone(e.getEntity().getLocation())) {
      e.setCancelled(true);
      return;
    }
    
    Player victim;
    Player killer;

    victim = (Player) e.getEntity();
    killer = (Player) e.getDamager();

    if (victim.equals(killer)) {
      return;
    }

    Fighter fKiller = Fighter.get(killer);
    Fighter fVictim = Fighter.get(victim);
    
    if(fKiller.getKitID() == F6_Grief.getKitID()) {
      G6_Grief.doStealHealth(killer, fKiller, victim);
    }

    fVictim.setLastDamagedBy(killer);
    fKiller.setLastToDamage(victim);

    killer.setCooldown(H1_CombatTracker.getTrackerMaterial(), 200);
    victim.setCooldown(H1_CombatTracker.getTrackerMaterial(), 200);

  }
  
  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    e.setDeathMessage("");
    Player victim = e.getEntity();
    Player killer = null;

//    Glowing.setGlowingOffForAll(victim);
    
    Fighter fVictim = Fighter.get(victim);
    fVictim.incDeaths();
    
    fVictim.fighterDeath();

    killer = Bukkit.getPlayer(fVictim.getLastDamagedBy());
    if(!checkKillerStatus(killer, victim, fVictim)) {
      return;
    }
    Fighter fKiller = Fighter.get(killer);
    fKiller.incKills();
    
    if (fKiller.getLastDamagedBy() != null) {
      if (fKiller.getLastDamagedBy().equals(victim.getUniqueId())) {
        fKiller.setLastDamagedBy(null);
      }
    }
    fVictim.setLastDamagedBy(null);
    fVictim.setLastToDamage(null);

    tellDeathMessage(killer.getName(), victim.getName(), fKiller.getKitID());

  }
  
  public static boolean checkKillerStatus(Player killer, Player victim, Fighter fVictim) {
    if (killer == null) {
      return false;
    }
    if (killer.equals(victim)) {
      return false;
    }
    if (!killer.isOnline()) {
      return false;
    }
    return true;
  }
  
  public void tellDeathMessage(String killerName, String victimName, int kitID) {
    String weaponName = "";
    if (kitID == F0_Noob.getKitID()) {
      weaponName = F0_Noob.getKitChatColor() + F0_Noob.getKitName();
    } else if (kitID == F1_Beserker.getKitID()) {
      weaponName = F1_Beserker.getKitChatColor() + F1_Beserker.getKitName();
    } else if (kitID == F2_Scorch.getKitID()) {
      weaponName = F2_Scorch.getKitChatColor() + F2_Scorch.getKitName();
    } else if (kitID == F3_Goblin.getKitID()) {
      weaponName = F3_Goblin.getKitChatColor() + F3_Goblin.getKitName();
    } else if (kitID == F4_Igor.getKitID()) {
      weaponName = F4_Igor.getKitChatColor() + F4_Igor.getKitName();
    } else if (kitID == F5_Sumo.getKitID()) {
      weaponName = F5_Sumo.getKitChatColor() + F5_Sumo.getKitName();
    } else if (kitID == F6_Grief.getKitID()) {
      weaponName = F6_Grief.getKitChatColor() + F6_Grief.getKitName();
    } else {
      weaponName = "Fists";
    }

   PlayerChat.tellPlayerMessageToAll(ChatColor.YELLOW + "" + ChatColor.ITALIC + killerName + ChatColor.RESET + " killed "
      + ChatColor.YELLOW + "" + ChatColor.ITALIC + victimName + ChatColor.RESET
      + " using " + "" + "[" + weaponName + ChatColor.RESET + "" + ChatColor.WHITE + "]");
  }
  

}
