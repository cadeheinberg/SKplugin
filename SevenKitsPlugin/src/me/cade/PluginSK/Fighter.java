package me.cade.PluginSK;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import me.cade.PluginSK.BuildKits.F_KitFilter;
import me.cade.PluginSK.KitListeners.G_KitFilter;
import me.cade.PluginSK.ScoreBoard.ScoreBoardObject;

public class Fighter {
  
  public static HashMap<UUID, Fighter> fighters = new HashMap<UUID,Fighter>();
  private Player player;
  private UUID uuid;
  
  private boolean fighterAbility;
  
  private UUID lastToDamage;
  private UUID lastDamagedBy;
  
  private int kitID;
  private int kitIndex;
  private int playerLevel;
  private int kills;
  private int killStreak;
  private int deaths;
  private int cakes;
  private int exp;
  private int unlocked;
  private boolean[] booleanUnlocked = new boolean[7];
  private ScoreBoardObject scoreBoardObject;
  
  public Fighter(Player player) {
    this.player = player;
    this.uuid = player.getUniqueId();
    this.addToFightersHashMap();
    fighterJoin();
  }
  
  private void fighterJoin() {
    setDefaults();
    downloadDatabase();
    convertUnlocked();
    this.scoreBoardObject = new ScoreBoardObject(player);
    this.giveKit();
  }
  
  private void downloadDatabase() {
    if (Database.playerExists(player)) {
      downloadFighter();
    } else {
      Database.addScore(player);
    }
    updateName();
  }
  
  private void convertUnlocked(){
    String str = "" + unlocked;
    this.booleanUnlocked[0] = getBooleanFromInt(Integer.parseInt(str.substring(0, 1)));
    this.booleanUnlocked[1] = getBooleanFromInt(Integer.parseInt(str.substring(1, 2)));
    this.booleanUnlocked[2] = getBooleanFromInt(Integer.parseInt(str.substring(2, 3)));
    
    this.booleanUnlocked[3] = getBooleanFromInt(Integer.parseInt(str.substring(3, 4)));
    this.booleanUnlocked[4] = getBooleanFromInt(Integer.parseInt(str.substring(4, 5)));
    this.booleanUnlocked[5] = getBooleanFromInt(Integer.parseInt(str.substring(5, 6)));
    
    this.booleanUnlocked[6] = getBooleanFromInt(Integer.parseInt(str.substring(6)));
  }
  
  private boolean getBooleanFromInt(int i) {
      if(i == 0) {
        return false;
      }
      return true;
  } 
  
  public void fighterLeftServer() {
    uploadFighter();
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
  
  public static Fighter get(Player player) {
    return fighters.get(player.getUniqueId());
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
    if(this.lastToDamage == null) {
      this.lastToDamage = null;
      return;
    }
    this.lastToDamage = lastToDamage.getUniqueId();
  }

  public UUID getLastDamagedBy() {
    return lastDamagedBy;
  }

  public void setLastDamagedBy(Player lastDamagedBy) {
    if(lastDamagedBy == null) {
      this.lastDamagedBy = null;
      return;
    }
    this.lastDamagedBy = lastDamagedBy.getUniqueId();
  }
  
  public void downloadFighter() {
    this.setKitID(Database.getStat(player, Database.column[2]));
    this.setKitIndex(Database.getStat(player, Database.column[3]));
    this.setPlayerLevel(Database.getStat(player, Database.column[4]));
    this.setKills(Database.getStat(player, Database.column[5]));
    this.setKillStreak(Database.getStat(player, Database.column[6]));
    this.setDeaths(Database.getStat(player, Database.column[7]));
    this.setCakes(Database.getStat(player, Database.column[8]));
    this.setExp(Database.getStat(player, Database.column[9]));
    this.setUnlocked(Database.getStat(player, Database.column[10]));
  }
  
  public void uploadFighter() {
    Database.setStat(player.getUniqueId().toString(), Database.column[2], this.getKitID());
    Database.setStat(player.getUniqueId().toString(), Database.column[3], this.getKitIndex());
    Database.setStat(player.getUniqueId().toString(), Database.column[4], this.getPlayerLevel());
    Database.setStat(player.getUniqueId().toString(), Database.column[5], this.getKills());
    Database.setStat(player.getUniqueId().toString(), Database.column[6], this.getKillStreak());
    Database.setStat(player.getUniqueId().toString(), Database.column[7], this.getDeaths());
    Database.setStat(player.getUniqueId().toString(), Database.column[8], this.getCakes());
    Database.setStat(player.getUniqueId().toString(), Database.column[9], this.getExp());
    Database.setStat(player.getUniqueId().toString(), Database.column[10], this.getUnlocked());
  }

  public int getPlayerLevel() {
    return playerLevel;
  }

  public void setPlayerLevel(int playerLevel) {
    this.playerLevel = playerLevel;
  }
  
  public void incPlayerLevel() {
    this.playerLevel++;
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
    player.sendMessage(
      ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled up to level " + playerLevel + "!");
    scoreBoardObject.updateLevel();
  }

  public int getKills() {
    return kills;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }
  
  public void incKills() {
    this.kills++;
    this.killStreak++;
    incExpByAmount(30);
    scoreBoardObject.updateKills();
    scoreBoardObject.updateRatio();
    scoreBoardObject.updateKillstreak();
  }

  public int getKillStreak() {
    return killStreak;
  }

  public void setKillStreak(int killStreak) {
    this.killStreak = killStreak;
  }
  
  public void incKillStreak() {
    this.killStreak++;
  }

  public int getDeaths() {
    return deaths;
  }

  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }
  
  public void incDeaths() {
    this.deaths++;
    setKillStreak(0);
    scoreBoardObject.updateDeaths();
    scoreBoardObject.updateRatio();
    scoreBoardObject.updateKillstreak();
  }

  public int getCakes() {
    return cakes;
  }

  public void setCakes(int cakes) {
    this.cakes = cakes;
  }
  
  public void incCakesByAmount(int inc) {
    this.cakes = this.cakes + inc;
    scoreBoardObject.updateCookies();
  }
  
  public void decCakes(int inc) {
    this.cakes = this.cakes - inc;
    scoreBoardObject.updateCookies();
  }

  public int getExp() {
    return exp;
  }

  public void setExp(int exp) {
    this.exp = exp;
  }
  
  public void incExpByAmount(int exp) {
    this.exp = this.exp + exp;
    scoreBoardObject.updateExp();
  }

  public int getUnlocked() {
    return unlocked;
  }

  public void setUnlocked(int unlocked) {
    this.unlocked = unlocked;
    this.convertUnlocked();
  }
  
  public boolean getUnlockedBoolean(int kitID) {
    return booleanUnlocked[kitID];
  }
  
  private void updateName() {
    Database.updateName(player, Database.column[1], player.getName());
  }
  
  public void setDefaults() {
    this.fighterAbility = false;
    this.player.setExp(1);
    this.player.setLevel(0);
    this.player.setInvisible(false);
    this.player.setWalkSpeed((float) 0.2);
    Glowing.setGlowingOffForAll(this.player);
    this.lastToDamage = null;
    this.lastDamagedBy = null;
    this.kitID = 1;
    this.kitIndex = 0;
    this.playerLevel = 1;
    this.kills = 0;
    this.deaths = 0;
    this.cakes = 500;
    this.killStreak = 0;
    this.exp = 0;
  }
  
}
