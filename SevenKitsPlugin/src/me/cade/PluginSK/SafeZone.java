package me.cade.PluginSK;

import org.bukkit.Location;

public class SafeZone {

  public static boolean safeZone(Location location) {
    if(location.getY() > 186) {
      return true;
    }
    return false;
  }
  
}
