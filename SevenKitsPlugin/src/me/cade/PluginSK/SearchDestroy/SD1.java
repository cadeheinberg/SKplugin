package me.cade.PluginSK.SearchDestroy;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.entity.Player;

public class SD1 {
  
  private static int gameCapacity = 6;
  
  private static ArrayList<UUID> queue = new ArrayList<UUID>(gameCapacity);
  
  private static boolean gameInProgress = false;
  
  public static boolean addPlayer(Player player) {
    if(gameInProgress) {
      return false;
    }
    if(isFull()) {
      return false;
    }
    queue.add(player.getUniqueId());
    return true;
  }
  
  private static boolean isFull() {
    if(queue.size() >= gameCapacity) {
      return false;
    }
    return true;
  }
  
  static boolean containsPlayer(Player player) {
    if(queue.contains(player.getUniqueId())) {
      return true;
    }
    return false;
  }
  
  static void removePlayer(Player player) {
    queue.remove(player.getUniqueId());
  }
  
  

}
