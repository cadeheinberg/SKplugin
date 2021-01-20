package me.cade.PluginSK;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import me.cade.PluginSK.BuildKits.F_KitFilter;
import me.cade.PluginSK.KitListeners.G_KitFilter;

public class Fighter {
  
  public static HashMap<UUID, Fighter> fighters = new HashMap<UUID,Fighter>();
  private Player player;
  private UUID uuid;
  private int kitID;
  private int kitIndex;
  
  private boolean fighterAbility;
  
  private UUID lastToDamage;
  private UUID lastDamagedBy;
  
  public Fighter(Player player) {
    this.player = player;
    this.uuid = player.getUniqueId();
    this.fighterAbility = false;
    this.player.setExp(1);
    this.player.setLevel(0);
    this.player.setInvisible(false);
    this.player.setWalkSpeed((float) 0.2);
  }
  
  public static Fighter get(Player player) {
    return fighters.get(player.getUniqueId());
  }
  
  public void addToFightersHashMap() {
    fighters.put(this.uuid, this);
  }
  
  public void giveKit() {
    player.getInventory().clear();
    F_KitFilter.giveKitFromKitID(player, this.kitID, this.kitIndex);
  }
  
  public void giveKit(int kitID, int kidIndex) {
    this.setKitID(kitID);
    this.setKitIndex(kidIndex);
    this.giveKit();
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

  public boolean isFighterAbility() {
    return fighterAbility;
  }

  public void setFighterAbility(boolean fighterAbility) {
    if(!fighterAbility) {
      G_KitFilter.deactivateSpecialFromKitID(player, this.kitID, this.kitIndex);
    }
    this.fighterAbility = fighterAbility;
  }

  public UUID getLastToDamage() {
    return lastToDamage;
  }

  public void setLastToDamage(Player lastToDamage) {
    this.lastToDamage = lastToDamage.getUniqueId();
  }

  public UUID getLastDamagedBy() {
    return lastDamagedBy;
  }

  public void setLastDamagedBy(Player lastDamagedBy) {
    this.lastDamagedBy = lastDamagedBy.getUniqueId();
  }
  
}
