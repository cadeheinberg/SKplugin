package me.cade.PluginSK.Permissions;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

public class BasicPermissions implements Listener {

  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    if (e.getPlayer().hasPermission("seven.builder")) {
      if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
        // allow to build
        return;
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    if (e.getPlayer().hasPermission("seven.builder")) {
      if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
        // allow to bureak
        return;
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onGrow(BlockGrowEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onBurn(BlockBurnEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onIgnite(BlockIgniteEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onBook(PlayerTakeLecternBookEvent e) {
    if (e.getPlayer().isOp()) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onBed(PlayerBedEnterEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onExplode(BlockExplodeEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onFoodChange(FoodLevelChangeEvent e) {
    e.setCancelled(true);
    e.setFoodLevel(20);
  }

  @EventHandler
  public void onCombust(EntityCombustEvent e) {
    if (!(e.getEntity() instanceof LivingEntity)) {
      e.setCancelled(true);
    }
  }

  public void onSwapHand(PlayerSwapHandItemsEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onExpChange(PlayerExpChangeEvent e) {
    e.setAmount(0);
  }

  @EventHandler
  public void onGrabArmorStand(PlayerArmorStandManipulateEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onLevelChangeEvent(PlayerLevelChangeEvent e) {
    e.getPlayer().setLevel(0);
  }

}
