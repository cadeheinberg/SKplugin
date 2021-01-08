package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class G8_Cooldown {
  
  private static Plugin plugin = A_Main.getPlugin(A_Main.class);
  
  public static void setCooldown(Player player, Material material, int ticks) {
    player.setCooldown(material, ticks);
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        if(player != null) {
        if(!player.isOnline()) {
          return;
        }
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
        }
      }
    }, ticks);
  }

}
