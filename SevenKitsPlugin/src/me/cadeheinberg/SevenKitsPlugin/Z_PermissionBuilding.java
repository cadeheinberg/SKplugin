package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.potion.PotionEffectType;

public class Z_PermissionBuilding implements Listener {

  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      if (Zgen.worldSandLavaSafe(e.getBlock().getLocation())) {
        if (e.getPlayer().hasPermission("seven.builder")) {
          if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
          }
        }
        e.setCancelled(true);
      }
      return;
    }
    if (e.getPlayer().hasPermission("seven.builder")) {
      if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
        return;
      }
    }
    if (Zgen.inPlayGround(e.getBlock().getLocation())) {
      if (Vars.getFighter(e.getPlayer()).isBuildMode()) {
        if (e.getBlock().getType() == Material.TNT || e.getBlock().getType() == Material.PISTON
          || e.getBlock().getType() == Material.STICKY_PISTON) {
          e.setCancelled(true);
          return;
        }
        return;
      } else {
        e.getPlayer().sendMessage(ChatColor.RED + "You must be in" + ChatColor.AQUA + ""
          + ChatColor.BOLD + " Sandbox Mode " + ChatColor.RED + "to do this");
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onDrop(PlayerDropItemEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (e.getPlayer().hasPermission("seven.staff")) {
      if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
        return;
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      if (Zgen.worldSandLavaSafe(e.getBlock().getLocation())) {
        if (e.getPlayer().hasPermission("seven.builder")) {
          if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
          }
        }
        e.setCancelled(true);
      }
      return;
    }
    if (e.getPlayer().hasPermission("seven.builder")) {
      if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
        return;
      }
    }
    if (Zgen.inPlayGround(e.getBlock().getLocation())) {
      if (Vars.getFighter(e.getPlayer()).isBuildMode()) {
        return;
      } else {
        e.getPlayer().sendMessage(ChatColor.RED + "You must be in" + ChatColor.AQUA + ""
          + ChatColor.BOLD + " Sandbox Mode " + ChatColor.RED + "to do this");
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onGrow(BlockGrowEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onBurn(BlockBurnEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      if (Zgen.worldSandSafe(e.getBlock().getLocation())) {
        e.setCancelled(true);
      }
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onIgnite(BlockIgniteEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      if (Zgen.worldSandSafe(e.getBlock().getLocation())) {
        e.setCancelled(true);
      }
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onBook(PlayerTakeLecternBookEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (e.getPlayer().isOp()) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onBed(PlayerBedEnterEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onExplode(BlockExplodeEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      if (Zgen.worldSandSafe(e.getBlock().getLocation())) {
        e.setCancelled(true);
      }
      return;
    }
    e.setCancelled(true);
  }

//  @EventHandler
//  public void onMelt(BlockFadeEvent e) {
//    if (Zgen.worldSand(e.getBlock().getWorld())) {
//      return;
//    }
//    e.setCancelled(true);
//  }

  @EventHandler
  public void onFoodChange(FoodLevelChangeEvent e) {
    if (Zgen.worldSand(e.getEntity().getWorld())) {
      return;
    }
    e.setCancelled(true);
    e.setFoodLevel(20);
  }

  @EventHandler
  public void onPickUp(EntityPickupItemEvent e) {
    if (Zgen.worldSand(e.getEntity().getWorld())) {
      return;
    }
    if (!(e.getEntity() instanceof Player)) {
      e.setCancelled(true);
      return;
    }
    if (((Player) e.getEntity()).getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if (e.getItem().getName().equals("Cake")) {
      e.getItem().remove();
      e.setCancelled(true);
      C1_CakeListener.pickUpCake((Player) e.getEntity());
      return;
    }
    if (Vars.getFighter((Player) e.getEntity()).isBuildMode()) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onCombust(EntityCombustEvent e) {
    if (Zgen.worldSand(e.getEntity().getWorld())) {
      return;
    }
    if (e.getEntity() instanceof LivingEntity) {
      if (Zgen.safeZone(e.getEntity().getLocation())) {
        e.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onSwapHand(PlayerSwapHandItemsEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onLevelChange(PlayerExpChangeEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    e.setAmount(0);
  }


  @EventHandler
  public void onPortal(PortalCreateEvent e) {
    if (e.getWorld() != A_Main.kitpvp) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onPortal(PlayerChangedWorldEvent e) {
    if (e.getPlayer().getWorld() == A_Main.kitpvp) {
      Vars.getFighter(e.getPlayer()).getScoreBoardObject().reApplyBoard();
      Vars.getFighter(e.getPlayer()).getScoreBoardObject().updateAll();
      if (e.getPlayer().hasPermission("seven.vip")) {
        return;
      }
      if (Vars.getFighter(e.getPlayer()) == null) {
        return;
      }
      Vars.getFighter(e.getPlayer()).changeToFighterMode(true);
    }else if(Zgen.worldSand(e.getPlayer().getWorld())){
      if(!Zgen.worldSand(e.getFrom())) {
        Player player = e.getPlayer();
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.JUMP);
        if (!Vars.getFighter(player).isBuildMode()) {
          Vars.getFighter(e.getPlayer()).changeToBuilder();
        }
        Vars.getFighter(player).getSandBoardObject().reApplyBoard();
        Vars.getFighter(player).getSandBoardObject().updateAll();
        Vars.getFighter(player).getSandBoardObject().updateStockTime();
      }
    }
  }

  @EventHandler
  public void onBucketFill(PlayerBucketFillEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      if (!Zgen.worldSandLavaSafe(e.getPlayer().getLocation())) {
        if (!Zgen.worldSandLavaSafe(e.getBlock().getLocation())) {
          if (!Zgen.worldSandLavaSafe(e.getBlockClicked().getLocation())) {
            return;
          }
        }
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onBucketEmpty(PlayerBucketEmptyEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      if (!Zgen.worldSandLavaSafe(e.getPlayer().getLocation())) {
        if (!Zgen.worldSandLavaSafe(e.getBlock().getLocation())) {
          if (!Zgen.worldSandLavaSafe(e.getBlockClicked().getLocation())) {
            return;
          }
        }
      }
    }
    if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
      return;
    }
    e.setCancelled(true);
  }


  @EventHandler
  public void onEatCake(PlayerStatisticIncrementEvent e) {
    if (!Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if (e.getStatistic() == Statistic.CAKE_SLICES_EATEN) {
      Vars.getFighter(e.getPlayer()).incCakes(1);
    }
  }


  @EventHandler
  public void onPistonOut(BlockPistonExtendEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      if (e.getBlocks() == null) {
        return;
      }
      for (Block b : e.getBlocks()) {
        if (b == null) {
          continue;
        }
        if (Zgen.worldSandLavaSafe(b.getLocation())) {
          e.setCancelled(true);
          return;
        }
      }
      return;
    }
  }

  @EventHandler
  public void onPistonIn(BlockPistonRetractEvent e) {
    if (Zgen.worldSand(e.getBlock().getWorld())) {
      if (e.getBlocks() == null) {
        return;
      }
      for (Block b : e.getBlocks()) {
        if (b == null) {
          continue;
        }
        if (Zgen.worldSandLavaSafe(b.getLocation())) {
          e.setCancelled(true);
          return;
        }
      }
      return;
    }
  }

  @EventHandler
  public void onCreatureIn(CreatureSpawnEvent e) {
    if (Zgen.worldSandLavaSafe(e.getLocation())) {
      e.setCancelled(true);
    }
    return;
  }

  @EventHandler
  public void onGrabArmorStand(PlayerArmorStandManipulateEvent e) {
    if (!(A_Main.kitpvp == e.getPlayer().getWorld())) {
      return;
    }
    if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
      return;
    }
    e.setCancelled(true);
  }

}


