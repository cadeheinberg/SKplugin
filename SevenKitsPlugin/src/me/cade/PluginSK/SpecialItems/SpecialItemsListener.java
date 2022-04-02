package me.cade.PluginSK.SpecialItems;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.MetadataValue;
import org.spigotmc.event.entity.EntityDismountEvent;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.SafeZone;
import me.cade.PluginSK.Damaging.CreateExplosion;

public class SpecialItemsListener implements Listener {
	
	@EventHandler
	  public void onDamage(EntityDismountEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getDismounted().getType() == EntityType.CHICKEN) {
				Fighter.get((Player) e.getEntity()).fighterDismountParachute();
			}
		}
	}

	@EventHandler
	  public void onExplode(EntityExplodeEvent e) {
		for(Block b : e.blockList()) {
			if(b.getType() == Material.PACKED_ICE) {
				b.breakNaturally();
			}
		}
	    e.blockList().clear();
	    e.setCancelled(true);
	    if(SafeZone.safeZone(e.getLocation())) {
	      return;
	    }
	    if (e.getEntityType() != EntityType.PRIMED_TNT) {
	      if (e.getEntityType() == EntityType.FIREBALL) {
	       //do fireball hit for an item probably
	      }
	      return;
	    }
	    List<MetadataValue> values = e.getEntity().getMetadata("thrower");
	    Player killer = (Player) Bukkit.getPlayer(values.get(0).asString());
	    if(killer == null) {
	    	e.setCancelled(true);
	    	return;
	    }
	    Location location = e.getLocation();
	    CreateExplosion.doAnExplosion(killer, location, 1.6, 6.5);
	  }

	}

