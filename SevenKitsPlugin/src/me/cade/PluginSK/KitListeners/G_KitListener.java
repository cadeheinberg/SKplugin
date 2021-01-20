package me.cade.PluginSK.KitListeners;

import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftTrident;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.cade.PluginSK.*;
import me.cade.PluginSK.BuildKits.*;

public class G_KitListener implements Listener {

  @EventHandler
  public void onRightClick(PlayerInteractEvent e) {
    if (SafeZone.safeZone(e.getPlayer().getLocation())) {
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
    Player player = e.getPlayer();
    int kitID = Fighter.fighters.get(player.getUniqueId()).getKitID();
    if (kitID == F2_Scorch.getKitID()) {
      if (e.getItem().getType() == F2_Scorch.getWeapon().getWeaponItem().getType()) {
        G2_Scorch.doRightClick(player);
      }
    } else if (kitID == F5_Wizard.getKitID()) {
      if (e.getItem().getType() == F5_Wizard.getWeapon().getWeaponItem().getType()) {
        G5_Wizard.doRightClick(player);
      }  
    }
  }

  @EventHandler
  public void onDrop(PlayerDropItemEvent e) {
    if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
      e.setCancelled(true);
    }
    if (SafeZone.safeZone(e.getPlayer().getLocation())) {
      return;
    }
    Player player = e.getPlayer();
    int kitID = Fighter.fighters.get(player.getUniqueId()).getKitID();
    if (kitID == F0_Noob.getKitID()) {
      G0_Noob.doDrop(player);
    } else if (kitID == F1_Beserker.getKitID()) {
      G1_Beserker.doDrop(player);
    } else if (kitID == F2_Scorch.getKitID()) {
      G2_Scorch.doDrop(player);
    } else if (kitID == F3_Goblin.getKitID()) {
      G3_Goblin.doDrop(player);
    } else if (kitID == F4_Igor.getKitID()) {
      G4_Igor.doDrop(player);
    } else if (kitID == F5_Wizard.getKitID()) {
      G5_Wizard.doDrop(player);
    } else if (kitID == F6_Grief.getKitID()) {
      G6_Grief.doDrop(player);
    } 
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent e) {
    if (!(e.getEntity().getShooter() instanceof Player)) {
      return;
    }
    Player shooter = (Player) e.getEntity().getShooter();
    // Hit a block
    if (e.getHitBlock() != null) {
      if (SafeZone.safeZone(e.getHitBlock().getLocation())) {
        return;
      }
      if (e.getEntityType() == EntityType.SNOWBALL) {
        G2_Scorch.doSnowballHitGround(shooter, e.getHitBlock().getLocation(), (Snowball) e.getEntity());
      }
      if (e.getEntityType() == EntityType.ARROW) {
        // check if shooter is heavy or goblin
        e.getEntity().remove();
      }
      if (e.getEntityType() == EntityType.TRIDENT) {
        G4_Igor.doTridentHitGround(shooter, e.getHitBlock().getLocation(),
          (CraftTrident) e.getEntity());
        e.getEntity().remove();
      }
    }

    // Hit an entity
    if (e.getHitEntity() != null) {
      if (SafeZone.safeZone(e.getHitEntity().getLocation())) {
        return;
      }
      if (!(e.getHitEntity() instanceof LivingEntity)) {
        return;
      }
      if (e.getEntityType() == EntityType.SNOWBALL) {
        G2_Scorch.doSnowballHitEntity(shooter, (LivingEntity) e.getHitEntity(), (Snowball) e.getEntity());
      }
      if (e.getEntityType() == EntityType.ARROW) {
        if (Fighter.fighters.get(shooter.getUniqueId()).getKitID() == 5) {
          // heavy

        } else {
          // goblin probably
          G3_Goblin.doArrorwHitEntity(shooter, (LivingEntity) e.getHitEntity(), (Arrow) e.getEntity());
        }
      }
      if (e.getEntityType() == EntityType.TRIDENT) {
        G4_Igor.doTridentHitEntity(shooter, (LivingEntity) e.getHitEntity(),
          (CraftTrident) e.getEntity());
      }
    }
  }

  @EventHandler
  public void onProjectileLaunch(ProjectileLaunchEvent e) {
    if (SafeZone.safeZone(e.getLocation())) {
      e.setCancelled(true);
      return;
    }
    if (e.getEntityType() == EntityType.TRIDENT) {
      if (e.getEntity().getShooter() instanceof Player) {
        if(!(G4_Igor.doThrowTrident((Player) e.getEntity().getShooter(), (CraftTrident) e.getEntity()))) {
          e.setCancelled(true);
        }
      }
    }
  }

  @EventHandler
  public void onBowShot(EntityShootBowEvent e) {
    if (SafeZone.safeZone(e.getEntity().getLocation())) {
      return;
    }
    if (e.getEntity() instanceof Player) {
      if (e.getProjectile().getType() == EntityType.ARROW) {
        if(!(G3_Goblin.doArrowShoot(((Player) e.getEntity()), (Arrow) e.getProjectile()))) {
          e.setCancelled(true);
        }
      }
    }
  }


}
