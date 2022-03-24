package me.cade.PluginSK;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import me.cade.PluginSK.BuildKits.F0_Noob;
import me.cade.PluginSK.BuildKits.F_KitFilter;
import me.cade.PluginSK.KitListeners.G0_Noob;
import me.cade.PluginSK.KitListeners.G_KitFilter;
import me.cade.PluginSK.ScoreBoard.ScoreBoardObject;


public class Fighter {
	
	//your error for the unlocked might be in the way you are uploading it as a boolean array
  
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
  
  private int noobTask;
  
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
    this.player.setExp(1);
    this.player.setLevel(0);
  }
  
  private void downloadDatabase() {
    if (MySQL.playerExists(player)) {
      downloadFighter();
    } else {
      MySQL.addScore(player);
    }
    updateName();
  }
  
  private void convertUnlocked(){
    this.booleanUnlocked[0] = true;
    this.booleanUnlocked[1] = true;
    this.booleanUnlocked[2] = true;
    
    this.booleanUnlocked[3] = true;
    this.booleanUnlocked[4] = true;
    this.booleanUnlocked[5] = true;
    
    this.booleanUnlocked[6] = true;
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
    this.setKitID(MySQL.getStat(player, MySQL.column[2]));
    this.setKitIndex(MySQL.getStat(player, MySQL.column[3]));
    this.setPlayerLevel(MySQL.getStat(player, MySQL.column[4]));
    this.setKills(MySQL.getStat(player, MySQL.column[5]));
    this.setKillStreak(MySQL.getStat(player, MySQL.column[6]));
    this.setDeaths(MySQL.getStat(player, MySQL.column[7]));
    this.setCakes(MySQL.getStat(player, MySQL.column[8]));
    this.setExp(MySQL.getStat(player, MySQL.column[9]));
    this.setUnlocked(MySQL.getStat(player, MySQL.column[10]));
  }
  
  public void uploadFighter() {
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[2], this.getKitID());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[3], this.getKitIndex());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[4], this.getPlayerLevel());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[5], this.getKills());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[6], this.getKillStreak());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[7], this.getDeaths());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[8], this.getCakes());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[9], this.getExp());
    MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlocked());
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
  
  public void doDeathChecks() {
	    if (kitID == F0_Noob.getKitID()) {
	        if (noobTask != -1) {
	          G0_Noob.stopListening(player, this);
	        }
	      }
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
    MySQL.updateName(player, MySQL.column[1], player.getName());
  }
  
  public void setDefaults() {
    this.fighterAbility = false;
    this.player.setExp(1);
    this.player.setLevel(0);
    this.player.setInvisible(false);
    this.player.setWalkSpeed((float) 0.2);
    //Glowing.setGlowingOffForAll(this.player);
    this.lastToDamage = null;
    this.lastDamagedBy = null;
    this.kitID = 0;
    this.kitIndex = 0;
    this.playerLevel = 1;
    this.kills = 0;
    this.deaths = 0;
    this.cakes = 500;
    this.killStreak = 0;
    this.exp = 0;
    this.setNoobTask(-1);
  }

  public int getNoobTask() {
	    return noobTask;
	  }

	  public void setNoobTask(int noobTask) {
	    this.noobTask = noobTask;
	  }

public void checkListeningForNoob() {
    if (kitID == F0_Noob.getKitID()) {
      if (noobTask != -1) {
        G0_Noob.stopListening(player, this);
      }
    }
    return;
  }
  
}
