package me.cade.PluginSK;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;
import me.cade.PluginSK.kitlisteners.G7_Wizard;

public class I_ItemListener implements Listener {

  @EventHandler
  public void onItem(PlayerInteractEvent e) {
    if(Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (e.getHand() == EquipmentSlot.OFF_HAND) {
      return; // off hand packet, ignore.
    }
    if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR)
      || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
      return;
    }
    if (e.getItem() == null) {
      return;
    }
    if (Zgen.safeZone(e.getPlayer().getLocation())) {
      if (e.getItem().equals(I1_Parachute.getParachute().getWeaponItem())) {
        I1_Parachute.doParachute(e.getPlayer(), true);
      }
      return;
    }
    if (e.getItem().equals(I1_Parachute.getParachute().getWeaponItem())) {
      I1_Parachute.doParachute(e.getPlayer(), false);
      return;
    }
    if (e.getItem().equals(I2_TNT.getTNT().getWeaponItem())) {
      I2_TNT.doTNT(e.getPlayer());
      return;
    } else if (e.getItem().equals(I3_PearlGun.getGun().getWeaponItem())) {
      I3_PearlGun.doPearl(e.getPlayer());
      return;
    }
  }

  @EventHandler
  public void onParachuteGetOff(EntityDismountEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())) {
      return;
    }
    if (!(e.getEntity() instanceof Player)) {
      return;
    }
    if (e.getDismounted().getType() == EntityType.CHICKEN) {
      I1_Parachute.getOff((Chicken) e.getDismounted(), ((Player) e.getEntity()).getUniqueId());
    } 
    else if (e.getDismounted().getType() == EntityType.SNOWBALL) {
      e.getDismounted().remove();
    }
  }

  @EventHandler
  public void onExplode(EntityExplodeEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())) {
      if(Zgen.worldSandLavaSafe(e.getEntity().getLocation())) {
        e.blockList().clear();
        e.setCancelled(true);
        return;
      }
      if(e.blockList() == null) {
        return;
      }
      for(Block b : e.blockList()) {
        if(b == null) {
          continue;
        }
        if(Zgen.worldSandLavaSafe(b.getLocation())) {
          e.blockList().clear();
          e.setCancelled(true);
        }
      }
      return;
    }
    e.blockList().clear();
    e.setCancelled(true);
    if(Zgen.safeZone(e.getLocation())) {
      return;
    }
    if (e.getEntityType() != EntityType.PRIMED_TNT) {
      if (e.getEntityType() == EntityType.FIREBALL) {
        G7_Wizard.doFireBallHit(e.getEntity(), e.getLocation());
      }
      return;
    }
    Location location = e.getLocation();
    A_Main.kitpvp.spawnParticle(Particle.EXPLOSION_LARGE, location.getX(), location.getY() + 2,
      location.getZ(), 2);
    A_Main.kitpvp.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
    for (Entity ent : A_Main.kitpvp.getNearbyEntities(location, 4, 4, 4)) {
      if (!(ent instanceof LivingEntity)) {
        continue;
      }
      Location upShoot = ent.getLocation();
      if (ent.isOnGround()) {
        upShoot.setY(upShoot.getY() + 1);
      }
      F0_DealDamage.dealAmount(I2_TNT.getThrower(e.getEntity().getEntityId()), (LivingEntity) ent,
        6);
      Vector currentDirection = upShoot.toVector().subtract(location.toVector());
      currentDirection = currentDirection.multiply(new Vector(1.2, 1.2, 1.2));
      ent.setVelocity(currentDirection);
    }
  }

}
