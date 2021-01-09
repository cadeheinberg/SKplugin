package me.cade.PluginSK.PlayerJoin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import me.cade.PluginSK.*;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    player.teleport(Main.hubSpawn);
    Fighter fighter = new Fighter(player);
    fighter.addToFightersHashMap();
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {

  }
  
  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    e.setRespawnLocation(Main.hubSpawn);
  }
  
}
