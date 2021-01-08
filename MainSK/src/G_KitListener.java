package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class G_KitListener implements Listener {

  @EventHandler
  public void onRightClick(PlayerInteractEvent e) {
    if (Zgen.safeZone(e.getPlayer().getLocation())) {
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
    E1_Fighter pFight = Vars.getFighter(player);
    int kitId = pFight.getKitID();
    int weaponId = pFight.getKitIndex();

    if(Zgen.worldSand(e.getPlayer().getWorld())){
      if(G_SandHearing.doSandKitListener(e.getPlayer(), e.getItem())){
//        e.setCancelled(true);
      }
      return;
    }
    if (pFight.isBuildMode()) {
      return;
    }

    if (kitId == F2_Booster.getKitID()) {
      if (!checkIfRightItem(F2_Booster.getItemList(), e.getItem())) {
        return;
      }
      if (!G2_Booster.doBooster(player, weaponId)) {
        e.setCancelled(true);
      }
    } else if (kitId == F3_Shotty.getKitID()) {
      if (!checkIfRightItem(F3_Shotty.getItemList(), e.getItem())) {
        return;
      }
      if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        e.setCancelled(true);
      }
      if (!G3_Shotty.doShotty(player, weaponId)) {
        e.setCancelled(true);
      }
    } else if (kitId == F6_Heavy.getKitID()) {
      if (!checkIfRightItem(F6_Heavy.getItemList(), e.getItem())) {
        return;
      }
      if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        e.setCancelled(true);
      }
      if (!G6_Heavy.doHeavy(player, weaponId)) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
        e.setCancelled(true);
      }
    } else if (kitId == F7_Wizard.getKitID()) {
      if (!checkIfWandItem(F7_Wizard.getItemList(), e.getItem())) {
        return;
      }
      if (!G7_Wizard.doWizard(player, weaponId)) {
        e.setCancelled(true);
      }
    }

  }

  @EventHandler
  public void onDrop(PlayerDropItemEvent e) {
    if(Zgen.worldSand(e.getPlayer().getWorld())){
      return;
    }
    if (!(e.isCancelled())) {
      e.setCancelled(true);
    }
    if (e.getItemDrop() == null) {
      return;
    }

    Player player = e.getPlayer();
    E1_Fighter pFight = Vars.getFighter(player);
    int kitId = pFight.getKitID();

    if (kitId == F7_Wizard.getKitID()) {
      if (!(e.getItemDrop().getItemStack().getItemMeta().getDisplayName()
        .equals(ChatColor.DARK_AQUA + "Wizard Wand"))) {
        return;
      }
      G7_Wizard.doWizardDrop(pFight);
    } else if (kitId == F6_Heavy.getKitID()) {
      if (!(e.getItemDrop().getItemStack().getItemMeta().getDisplayName()
        .equals(ChatColor.BLUE + "Death Machine"))) {
        return;
      }
      G6_Heavy.doHeavyDrop(pFight);
    }else if (kitId == F3_Shotty.getKitID()) {
      if(pFight.getKitIndex() != 3) {
        return;
      }
      if (!(e.getItemDrop().getItemStack().getItemMeta().getDisplayName()
        .equals(ChatColor.GOLD + "Shotty Shotgun"))) {
        return;
      }
      G3_Shotty.doShottyReload(player);
    }
    if (Zgen.safeZone(e.getPlayer().getLocation())) {
      return;
    }
    
    if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName()
      .equals(ChatColor.WHITE + "Noob Sword")) {
      G1_Noob.doNoobDrop(player);
      return;
    }



  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())){
      return;
    }
    e.getEntity().remove();
    if(Zgen.safeZone(e.getEntity().getLocation())) {
      return;
    }
    if (!(e.getEntity().getShooter() instanceof Player)) {
      return;
    }
    Player shooter = (Player) e.getEntity().getShooter();
    if (e.getEntity().getType() == EntityType.SNOWBALL) {
      if (e.getHitEntity() == null) {
        return;
      }
      if (checkIfRightKit(shooter, 3)) {
        G3_Shotty.doHitter(shooter, e.getHitEntity(), e.getEntity());
      }
    } else if (e.getEntity().getType() == EntityType.ARROW) {
      if (e.getHitEntity() == null) {
        return;
      }
      if (e.getHitEntity() instanceof LivingEntity) {
        if (checkIfRightKit(shooter, 4)) {
          G4_Goblin.doGoblinArrowHit(shooter, (LivingEntity) e.getHitEntity(),
            (Projectile) e.getEntity());
        } else if (checkIfRightKit(shooter, 6)) {
          G6_Heavy.doHeavyArrowHit(shooter, (LivingEntity) e.getHitEntity(),
            (Projectile) e.getEntity());
        }
      }
    } else if (e.getEntity().getType() == EntityType.TRIDENT) {
      if (e.getHitEntity() == null) {
        if (e.getHitBlock() != null) {
          G5_Igor.doTridentHitBlock(shooter, e.getHitBlock(),
            Vars.getFighter(shooter).getKitIndex());
        }
      } else {
        if (e.getHitEntity() instanceof LivingEntity) {
          G5_Igor.doTridentHitLivingEntity(shooter, (LivingEntity) e.getHitEntity(),
            Vars.getFighter(shooter).getKitIndex());
        }
      }
    }

  }

  @EventHandler
  public void onProjectileLaunch(ProjectileLaunchEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())){
      return;
    }
    if (e.getEntity().getShooter() == null) {
      return;
    }
    if (Zgen.safeZone(((Entity) e.getEntity().getShooter()).getLocation())) {
      e.setCancelled(true);
      return;
    }
    if (!(e.getEntity().getShooter() instanceof Player)) {
      return;
    }
    Player shooter = (Player) e.getEntity().getShooter();
    if (e.getEntity().getType() == EntityType.TRIDENT) {
      if (!checkIfRightKit(shooter, 5)) {
        return;
      }
      if (!G5_Igor.doIgor(shooter, Vars.getFighter(shooter).getKitIndex())) {
        e.setCancelled(true);
      }
    } else if (e.getEntity().getType() == EntityType.ARROW) {
      if (!checkIfRightKit(shooter, 4)) {
        return;
      }
    }
  }

  @EventHandler
  public void onProjectileLaunch(EntityShootBowEvent e) {
    if(Zgen.worldSand(e.getEntity().getWorld())){
      return;
    }
    if (e.getEntity() instanceof Player) {
      G4_Goblin.doBowLaunch(((Player) e.getEntity()), e.getForce());
    }
  }

  public static boolean checkIfRightItem(Weapon[] list, ItemStack item) {
    for (int i = 0; i < 4; i++) {
      if (item.getType().equals(list[i].getWeaponItem().getType())) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkIfWandItem(Weapon[] list, ItemStack item) {
    if (item.getType().equals(list[0].getWeaponItem().getType())) {
      return true;
    }
    return false;
  }

  public static boolean checkIfRightKit(Player player, int checker) {
    return (Vars.getFighter(player).getKitID() == checker);
  }

}
