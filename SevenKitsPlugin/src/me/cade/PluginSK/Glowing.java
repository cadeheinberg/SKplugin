package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;
import net.md_5.bungee.api.ChatColor;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class Glowing {

  public static HashMap<UUID, UUID> glowMap = new HashMap<UUID, UUID>();

  /**
   * Return true if glow can be set on targeted player
   * Return false if glow is already set on player or they have some sort of blocker
   */
  public static void setGlowingOn(Player glowingPlayer, Player activatingGlow,
    Collection<Player> allViewers) {

    glowMap.put(activatingGlow.getUniqueId(), glowingPlayer.getUniqueId());

    SendActionBar.sendActionBar(glowingPlayer, ChatColor.YELLOW + "REVEALED");
    glowingPlayer.playSound(glowingPlayer.getLocation(), Sound.ENTITY_PILLAGER_HURT, 8, 1);

    GlowAPI.setGlowing(glowingPlayer, GlowAPI.Color.DARK_RED, allViewers);

    return;

  }

  public static void setGlowingOff(Player glowingPlayer, Player activatingGlow,
    Collection<Player> allViewers) {

    GlowAPI.setGlowing(glowingPlayer, false, allViewers);
    SendActionBar.sendActionBar(glowingPlayer, ChatColor.YELLOW + "REVEAL OFF");
    glowingPlayer.playSound(glowingPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 8,
      1);
    
    glowMap.remove(activatingGlow.getUniqueId());
    
    return;
  }
  
  public static void setGlowingOffForAll(Player glowingPlayer) {

    GlowAPI.setGlowing(glowingPlayer, false, Bukkit.getServer().getOnlinePlayers());
    
    return;
  }



}
