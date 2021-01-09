package me.cade.PluginSK;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import me.cade.PluginSK.BuildKits.F_KitFilter;

public class Fighter {
  
  public static HashMap<UUID, Fighter> fighters = new HashMap<UUID,Fighter>();
  private Player player;
  private UUID uuid;
  private int kitID;
  private int kitIndex;
  
  public Fighter(Player player) {
    this.player = player;
    this.uuid = player.getUniqueId();
  }
  
  public void addToFightersHashMap() {
    fighters.put(this.uuid, this);
  }
  
  public void giveKit() {
    player.getInventory().clear();
    F_KitFilter.giveKitFromKitID(player, this.kitID, this.kitIndex);
  }

  public Player getPlayer() {
    return player;
  }

  public int getKitID() {
    return kitID;
  }

  public void setKitID(int kitID) {
    this.kitID = kitID;
  }

  public int getKitIndex() {
    return kitIndex;
  }

  public void setKitIndex(int kitIndex) {
    this.kitIndex = kitIndex;
  }

  public UUID getUuid() {
    return uuid;
  }
  
}
