package me.cade.PluginSK.SearchDestroy;

import org.bukkit.entity.Player;

public class SearchDestroyManager {
  
  public static boolean removePlayerFromQueueIfInAQueue(Player player) {
    if(SD1.containsPlayer(player)) {
      SD1.removePlayer(player);
      return true;
    }
    return false;
  }

}
