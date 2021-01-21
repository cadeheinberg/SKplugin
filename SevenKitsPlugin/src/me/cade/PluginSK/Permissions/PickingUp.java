package me.cade.PluginSK.Permissions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickingUp implements Listener{
  
  @EventHandler
  public void onPickUp(EntityPickupItemEvent e) {
    if (!(e.getEntity() instanceof Player)) {
      e.setCancelled(true);
      return;
    }
    if (((Player) e.getEntity()).getGameMode() == GameMode.CREATIVE) {
      return;
    }
//    if (e.getItem().getName().equals("Cake")) {
//      e.getItem().remove();
//      e.setCancelled(true);
//      C1_CakeListener.pickUpCake((Player) e.getEntity());
//      return;
//    }
    e.setCancelled(true);
  }

}
