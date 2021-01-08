package me.cade.PluginSK;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class Vars {

  //change player to uuid
  private static HashMap<UUID, E1_Fighter> fighters;
  
  public static void createVars() {
    fighters = new HashMap<UUID, E1_Fighter>(25);
  }
  
  public static void addFigher(UUID uuid, E1_Fighter pFight) {
    fighters.put(uuid, pFight);
  }
  
  public static E1_Fighter getFighter(Player player) {
    return fighters.get(player.getUniqueId());
  }
  
  public static E1_Fighter getFighterWithName(UUID uuid) {
    return fighters.get(uuid);
  }
  
  public static void removeFighter(UUID uuid) {
    fighters.remove(uuid);
  }
  
  public static void clearFighters() {
    fighters.clear();
  }
  
}
