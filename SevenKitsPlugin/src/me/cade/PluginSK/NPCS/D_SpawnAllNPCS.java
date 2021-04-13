package me.cade.PluginSK.NPCS;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import me.cade.PluginSK.Main;

public class D_SpawnAllNPCS {

  public static void spawnNPCS() {
    D_SpawnKitSelectors.spawnKitSelectors();
    D_SpawnGameSelectors.spawnGameSelectors();
  }
  
  public static void removeAllNpcs() {
    for( Entity e : Main.hub.getEntities()) {
      if(e instanceof Player) {
        continue;
      }else {
        e.remove();
      }
    }
  }

}
