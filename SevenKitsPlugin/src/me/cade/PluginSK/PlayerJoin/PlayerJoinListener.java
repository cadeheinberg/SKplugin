package me.cade.PluginSK.PlayerJoin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
    new Fighter(player);
    for(Player online : Bukkit.getOnlinePlayers()) {
      online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 8, 1);
    }
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    Fighter.get(e.getPlayer()).fighterLeftServer();
  }
  
  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    e.setRespawnLocation(Main.hubSpawn);
  }
  
}
