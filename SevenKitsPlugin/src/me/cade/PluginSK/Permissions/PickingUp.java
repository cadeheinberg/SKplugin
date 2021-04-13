package me.cade.PluginSK.Permissions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import me.cade.PluginSK.Money.A_CakeManager;
import me.cade.PluginSK.Money.CakePickUp;

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
    
    //debug line
    //e.getEntity().sendMessage(ChatColor.GREEN + "Pikced Up Item: " 
    //+ ChatColor.WHITE + e.getItem().getItemStack().getItemMeta().getDisplayName());
    
    if (e.getItem().getItemStack().getItemMeta().getDisplayName().equals(A_CakeManager.currencyNameSingular)) {
      e.getItem().remove();
      e.setCancelled(true);
      
      //this method just increases the players money, xp and stuff
      CakePickUp.pickUpCake((Player) e.getEntity());
      return;
    }
    e.setCancelled(true);
  }

}
