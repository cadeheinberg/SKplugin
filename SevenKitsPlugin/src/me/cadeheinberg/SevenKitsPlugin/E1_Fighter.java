package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class E1_Fighter {

  private Plugin plugin;

  private Player player;
  private int[] unlocked;
  private int kitID;
  private int kitIndex;

  private Player lastToDamage;
  private Player lastDamagedBy;
  private long lastTimeDamaged;

  private int playerLevel;
  private int prestige;
  private int exp;

  private int kills;
  private int deaths;
  private int cakes;
  private int tokens;
  private int killStreak;

  private int killStreakAttribute;
  private boolean juggerSet;
  private int speedAttribute;
  private int specialItemAttribute;
  private int protectionArmorAttribute;

  private int parachuteTask;
  private Chicken chicken;
  private int noobTask;

  private F7_Wizard wizardKit;

  private boolean buildMode;
  private boolean fightMode;
  private boolean soccerMode;
  private boolean royaleMode;

  private int customColorIndex;
  private ScoreBoardObject scoreBoardObject;
  private SandScoreBoardObject sandBoardObject;

  private ItemStack[] vipInventory;
  private int sandboxLevel;
  private float sandboxExp;

  public E1_Fighter(Player player) {
    this.player = player;
    setDefaults();
    if (B_Database.playerExists(player)) {
      E0_FighterStorage.downloadFighter(this, player);
    } else {
      B_Database.addScore(player);
    }
    updateName();
  }

  public void playerJoin() {
    if (!(player.hasPermission("seven.vip"))) {
      setCustomColorIndex(-1);
    }
    giveKit(true);
    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    E0_FighterStorage.applyKillStreakAttribute(this, player, killStreak, killStreakAttribute);
    playerJoinOrRespawn();
    changeToFighterMode(false);
    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0, true, false));
    player.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Seven Kits PvP",
      ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Welcome", 20, 50, 10);
    sandBoardObject = new SandScoreBoardObject(player);
    scoreBoardObject = new ScoreBoardObject(player);
  }

  public void fighterRespawn() {
    E0_FighterStorage.applySpeedAttribute(this, player, plugin);
    setLastDamagedBy(null);
    setLastToDamageDamageBy(null);
    setLastToDamage(null);
    playerJoinOrRespawn();
    player.getInventory().remove(Material.COAL);
    player.getInventory().remove(Material.STICK);
    player.getInventory().remove(Material.SHEARS);
  }

  public void playerJoinOrRespawn() {
    player.setHealth(20);
    player.setLevel(1);
    player.setCooldown(H1_CombatTracker.getTrackerMaterial(), 0);
    player.setCooldown(H1_CombatTracker.getHurtTrackerMaterial(), 0);
    player.setCooldown(I1_Parachute.getParachute().getWeaponItem().getType(), 0);
    player.setCooldown(F0_Materials.getMaterialFromKitIDAndIndex(kitID - 1, kitIndex), 0);
  }

  public void fighterQuitServer() {
    if (parachuteTask != -1) {
      if (chicken != null) {
        I1_Parachute.getOff(chicken, player.getUniqueId());
      }
    }
    checkBountyOnLeaveOrSpawnOrDeath();
    M_Bounty.getBossBar().removePlayer(player);
    exitAllGameModes();
    E0_FighterStorage.uploadFighter(this, player);
  }

  public void checkIfLeftMiniGame() {
    if (isSoccerMode()) {
      J1_SoccerMode.removePlayer(player);
      changeToFighterMode(true);
    }
  }

  public void setMaxHealth() {
    if (juggerSet) {
      player.setHealth(30);
    } else {
      player.setHealth(20);
    }
  }

  public void wentToSpawn() {
    checkIfLeftMiniGame();
    checkBountyOnLeaveOrSpawnOrDeath();
    setLastDamagedBy(null);
    setLastToDamageDamageBy(null);
    setLastToDamage(null);
    setMaxHealth();
    checkListeningForNoob();
  }

  public void changeToFighterMode(boolean giveKit) {
    if (buildMode) {
      saveBuildMode();
    }
    setFightMode(true);
    buildMode = false;
    soccerMode = false;
    if(player.getGameMode() == GameMode.SURVIVAL) {
      player.setFlying(false);
      player.setAllowFlight(false);
    }
    player.setLevel(1);
    player.setExp(0);
    player.setFoodLevel(20);
    setMaxHealth();
    if (giveKit) {
      giveKit(true);
    }
  }

  private void setFightMode(boolean setter) {
    if (setter) {
      if (!J0_FIghterMode.listContainsPlayer(player)) {
        J0_FIghterMode.addFighterToList(player);
      }

    } else {
      if (J0_FIghterMode.listContainsPlayer(player)) {
        J0_FIghterMode.removeFighterFromList(player);
      }
    }
    fightMode = setter;
  }

  public void changeToBuilder() {
    buildMode = true;
    soccerMode = false;
    setFightMode(false);
    J_BuilderMode.giveBuilderItems(player);
  }

  public void changeToSoccerMode() {
    if (buildMode) {
      saveBuildMode();
    }
    soccerMode = true;
    buildMode = false;
    setFightMode(false);
  }

  public void exitAllGameModes() {
    if (J0_FIghterMode.listContainsPlayer(player)) {
      J0_FIghterMode.removeFighterFromList(player);
    }
  }

  public void checkBountyOnLeaveOrSpawnOrDeath() {
    if (M_Bounty.isBountyOn()) {
      if (player.equals(M_Bounty.getBountySetOn())) {
        M_Bounty.tellAllBountyOverNoWinner();
      }
    }
    return;
  }

  public void sellTokens(int amount) {
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if (amount < 1) {
      player.sendMessage(ChatColor.RED + "Market selling requires at least " + ChatColor.GREEN + ""
        + ChatColor.BOLD + "1 Cake " + ChatColor.RED + "for that!");
      return;
    }
    if (tokens < amount) {
      player.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.GREEN + ""
        + ChatColor.BOLD + " Cakes " + ChatColor.RED + "to sell");
      return;
    }
    decTokens(amount);
    incCakes(amount * C_CakeSpawner.getCakePrice());
    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Sold: " + amount + " "
      + "cakes at $" + C_CakeSpawner.getCakePrice() + " each");
    player.sendMessage(ChatColor.GREEN + "$" + (amount * C_CakeSpawner.getCakePrice()) + " "
      + C_CakeSpawner.getCakeName() + " has been added to your balance");
    return;
  }

  public void buyTokens(int amount) {
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if (cakes < (amount * C_CakeSpawner.getCakePrice())) {
      player.sendMessage(ChatColor.RED + "You do not have enoguh " + ChatColor.GREEN + ""
        + ChatColor.BOLD + "Cash " + ChatColor.RED + "for that!");
      return;
    }
    decCakes(amount * C_CakeSpawner.getCakePrice());
    incTokens(amount);
    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Bought: " + amount + " "
      + "cakes at $" + C_CakeSpawner.getCakePrice() + " each");
    player
      .sendMessage(ChatColor.GREEN + "" + amount  + " cakes have been added to your balance");
    return;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void saveBuildMode() {
    setVipInventory(player.getInventory().getContents());
    setSandboxLevel(player.getLevel());
    setSandboxExp(player.getExp());
    return;
  }

  public void checkListeningForNoob() {
    if (kitID == F1_Noob.getKitID()) {
      if (noobTask != -1) {
        G1_Noob.stopListening(player, this);
      }
    }
    return;
  }

  public void updateName() {
    B_Database.updateName(player, B_Database.column[1], player.getName());
  }

  public void sendActionBar(String message) {
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }

  public void giveKit(boolean inKitPVP) {
    if (inKitPVP) {
      if (!fightMode) {
        changeToFighterMode(false);
      }
      removePreviousKit();
    }
    if (kitID == F1_Noob.getKitID()) {
      new F1_Noob(player, kitIndex, true);
    } else if (kitID == F2_Booster.getKitID()) {
      new F2_Booster(player, kitIndex, true);
    } else if (kitID == F3_Shotty.getKitID()) {
      new F3_Shotty(player, kitIndex, true);
    } else if (kitID == F4_Goblin.getKitID()) {
      new F4_Goblin(player, kitIndex, true);
    } else if (kitID == F5_Igor.getKitID()) {
      new F5_Igor(player, kitIndex, true);
    } else if (kitID == F6_Heavy.getKitID()) {
      new F6_Heavy(player, kitIndex, true);
    } else if (kitID == F7_Wizard.getKitID()) {
      wizardKit = new F7_Wizard(player, true);
    }
    E0_FighterStorage.applyAttributes(this, player, plugin);
    if (inKitPVP) {
      player.getInventory().setItem(7, H1_CombatTracker.getHurtTracker().getWeaponItem());
      player.getInventory().setItem(8, H1_CombatTracker.getTracker().getWeaponItem());
    }
  }

  public void giveKit(int kitID, int kitIndex, boolean inKitPVP) {
    setKitID(kitID);
    setKitIndex(kitIndex);
    giveKit(inKitPVP);
  }

  private void removePreviousKit() {
    wizardKit = null;
    player.setLevel(1);
    if (player.getGameMode() != GameMode.CREATIVE) {
      player.getInventory().clear();
    }
  }

  public void incPlayerLevel() {
    if (this.playerLevel >= 28) {
      player.closeInventory();
      player.sendMessage(ChatColor.AQUA + "Max level reached!");
      player.sendMessage(ChatColor.AQUA + "Visit level manager in shop to prestige!");
      return;
    }
    this.playerLevel = this.playerLevel + 1;
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
    player.sendMessage(
      ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled up to level " + playerLevel + "!");
    scoreBoardObject.updateLevel();
  }

  public void incKills() {
    this.kills = this.kills + 1;
    this.killStreak = this.killStreak + 1;
    incExp(30);
    if (juggerSet) {
      player.setHealth(30);
    } else {
      player.setHealth(20);
    }
    scoreBoardObject.updateKills();
    scoreBoardObject.updateRatio();
    scoreBoardObject.updateKillstreak();
    E0_FighterStorage.applyKillStreakAttribute(this, player, killStreak, killStreakAttribute);
    Z_LeaderBoard.updateStreaksLeader(player.getName(), killStreak);
  }

  public void incDeaths() {
    this.deaths = this.deaths + 1;
    this.killStreak = 0;
    specialItemAttribute = 0;
    scoreBoardObject.updateDeaths();
    scoreBoardObject.updateRatio();
    scoreBoardObject.updateKillstreak();
    removeKillStreakAttributes();
    if (kitID == F1_Noob.getKitID()) {
      if (noobTask != -1) {
        G1_Noob.stopListening(player, this);
      }
    }
  }

  public void incExp(int exp) {
    this.exp = this.exp + exp;
    scoreBoardObject.updateExp();
    if (playerLevel >= 28) {
      return;
    }
    if (this.exp >= E2_Experience.getExpNeeded(playerLevel)) {
      this.exp = this.exp - E2_Experience.getExpNeeded(playerLevel);
      incPlayerLevel();
    }
  }

  public void incCakes(int inc) {
    this.cakes = this.cakes + inc;
    sendActionBar(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "+ $" + inc);
    scoreBoardObject.updateCookies();
  }

  public void incPrestige() {
    if (this.prestige >= 3) {
      player.closeInventory();
      player.sendMessage(ChatColor.AQUA + "Max prestige reached!");
      return;
    }
    this.prestige = this.prestige + 1;
    player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 8, 1);
    if (this.prestige == 3) {
      player.sendMessage(ChatColor.AQUA + "Max prestige reached!");
      player.sendMessage(ChatColor.AQUA + "Congrats!");
    }
    resetFighter();
    scoreBoardObject.updateAll();
  }

  public void decCakes(int inc) {
    this.cakes = this.cakes - inc;
    sendActionBar(ChatColor.DARK_RED + "" + ChatColor.BOLD + "- $" + inc);
    scoreBoardObject.updateCookies();
  }

  public Player getPlayer() {
    return player;
  }

  public int getPlayerLevel() {
    return playerLevel;
  }

  public void setPlayerLevel(int playerLevel) {
    this.playerLevel = playerLevel;
  }

  public int getPrestige() {
    return prestige;
  }

  public int getKills() {
    return kills;
  }

  public int getCakes() {
    return cakes;
  }

  public int getDeaths() {
    return deaths;
  }

  public boolean isBuildMode() {
    return buildMode;
  }

  public int getCustomColorIndex() {
    return customColorIndex;
  }

  public void setCustomColorIndex(int customColorIndex) {
    this.customColorIndex = customColorIndex;
  }

  public ScoreBoardObject getScoreBoardObject() {
    return scoreBoardObject;
  }

  public void setScoreBoardObject(ScoreBoardObject scoreBoardObject) {
    this.scoreBoardObject = scoreBoardObject;
  }

  public void setLastToDamageDamageBy(Player player) {
    if (lastToDamage == null) {
      return;
    }
    if (!lastToDamage.isOnline()) {
      return;
    }
    Vars.getFighter(lastToDamage).setLastDamagedBy(null);
  }

  public void incKillStreak() {
    this.killStreak = this.killStreak + 1;
    scoreBoardObject.updateKillstreak();
  }

  public long getLastTimeDamaged() {
    return lastTimeDamaged;
  }

  public void setLastTimeDamaged(long lastTimeDamaged) {
    this.lastTimeDamaged = lastTimeDamaged;
  }

  public boolean isFightMode() {
    return fightMode;
  }

  public boolean isSoccerMode() {
    return soccerMode;
  }

  public int getNoobTask() {
    return noobTask;
  }

  public void setNoobTask(int noobTask) {
    this.noobTask = noobTask;
  }

  public void setKils(int setter) {
    kills = setter;
  }

  public void setDeaths(int setter) {
    deaths = setter;
  }

  public Chicken getChicken() {
    return chicken;
  }

  public void setChicken(Chicken chick) {
    this.chicken = chick;
  }

  public void setChickenNull() {
    this.chicken = null;
  }

  public ItemStack[] getVipInventory() {
    return vipInventory;
  }

  public void setVipInventory(ItemStack[] vipInventory) {
    this.vipInventory = vipInventory;
  }

  public void updateWholeScoreboard() {
    if (Zgen.worldSand(player.getWorld())) {
      sandBoardObject.updateAll();
    } else {
      scoreBoardObject.updateAll();
    }
  }
  
  public void updateStockValue() {
    if (Zgen.worldSand(player.getWorld())) {
      sandBoardObject.updateStock();
    } else {
      scoreBoardObject.updateStock();
    }
  }

  public void updateStockTime() {
    if (Zgen.worldSand(player.getWorld())) {
      sandBoardObject.updateStockTime();
    } else {
      scoreBoardObject.updateStockTime();
    }
  }

  public void setCakes(int stat) {
    cakes = stat;
  }

  public void setKillStreak(int stat) {
    killStreak = stat;
  }

  public void setPrestige(int stat) {
    prestige = stat;
  }

  public void setKills(int stat) {
    kills = stat;
  }

  public void setUnlocked(int index, int stat) {
    unlocked[index] = stat;
  }

  public int getKillStreak() {
    return killStreak;
  }

  public int getKillStreakAttribute() {
    return killStreakAttribute;
  }

  public void setKillStreakAttribute(int killStreakAttribute) {
    this.killStreakAttribute = killStreakAttribute;
  }

  public int getSpeedAttribute() {
    return speedAttribute;
  }

  public void setSpeedAttribute(int speedAttribute) {
    this.speedAttribute = speedAttribute;
  }

  public int getSpecialItemAttribute() {
    return specialItemAttribute;
  }

  public void setSpecialItemAttribute(int specialItemAttribute) {
    this.specialItemAttribute = specialItemAttribute;
  }

  public int getProtectionArmorAttribute() {
    return protectionArmorAttribute;
  }

  public void setProtectionArmorAttribute(int protectionArmorAttribute) {
    this.protectionArmorAttribute = protectionArmorAttribute;
  }

  public Player getLastToDamage() {
    return lastToDamage;
  }

  public void setLastToDamage(Player lastToDamage) {
    this.lastToDamage = lastToDamage;
  }

  public Player getLastDamagedBy() {
    return lastDamagedBy;
  }

  public void setLastDamagedBy(Player lastDamagedBy) {
    this.lastDamagedBy = lastDamagedBy;
  }

  public int getKitIndex() {
    return kitIndex;
  }

  public void setKitIndex(int kitIndex) {
    this.kitIndex = kitIndex;
  }

  public int getKitID() {
    return kitID;
  }

  public void setKitID(int kitID) {
    this.kitID = kitID;
  }

  public int[] getUnlocked() {
    return unlocked;
  }

  public void incUnlocked(int index) {
    unlocked[index] = unlocked[index] + 1;
  }

  public F7_Wizard getWizardKit() {
    return wizardKit;
  }

  public void setWizardKit(F7_Wizard wizardKit) {
    this.wizardKit = wizardKit;
  }

  public int getParachuteTask() {
    return parachuteTask;
  }

  public void setParachuteTask(int parachuteTask) {
    this.parachuteTask = parachuteTask;
  }

  public int getExp() {
    return exp;
  }

  public void setExp(int exp) {
    this.exp = exp;
  }

  public Plugin getPlugin() {
    return plugin;
  }

  public void setPlugin(Plugin plugin) {
    this.plugin = plugin;
  }

  public void uploadFighter() {
    E0_FighterStorage.uploadFighter(this, player);
  }

  public void downloadFighter() {
    E0_FighterStorage.uploadFighter(this, player);
  }

  public boolean isJuggerSet() {
    return juggerSet;
  }

  public void setJuggerSet(boolean juggerSet) {
    this.juggerSet = juggerSet;
  }

  public void removeKillStreakAttributes() {
    E0_FighterStorage.removeKillStreakAttributes(this, player);
  }

  public void applyKillStreakAttribute() {
    E0_FighterStorage.applyKillStreakAttribute(this, player, killStreak, killStreakAttribute);
  }

  public boolean isRoyaleMode() {
    return royaleMode;
  }

  public void setRoyaleMode(boolean royaleMode) {
    this.royaleMode = royaleMode;
  }

  public int getSandboxLevel() {
    return sandboxLevel;
  }

  public void setSandboxLevel(int sandboxLevel) {
    this.sandboxLevel = sandboxLevel;
  }

  public float getSandboxExp() {
    return sandboxExp;
  }

  public void setSandboxExp(float sandboxExp) {
    this.sandboxExp = sandboxExp;
  }

  public void setDefaults() {
    plugin = A_Main.getPlugin(A_Main.class);
    unlocked = new int[] {0, 0, -1, -1, -1, -1, -1};
    kitID = 1;
    kitIndex = 0;
    setLastToDamage(null);
    setLastDamagedBy(null);
    playerLevel = 1;
    prestige = 1;
    kills = 0;
    deaths = 0;
    cakes = 500;
    killStreak = 0;
    killStreakAttribute = 0;
    speedAttribute = 0;
    specialItemAttribute = 0;
    protectionArmorAttribute = 0;
    exp = 0;
    buildMode = false;
    fightMode = true;
    soccerMode = false;
    royaleMode = false;
    M_Bounty.getBossBar().addPlayer(player);
    customColorIndex = -1;
    lastTimeDamaged = 1;
    parachuteTask = -1;
    noobTask = -1;
    chicken = null;
    setVipInventory(null);
    juggerSet = false;
    sandboxLevel = 0;
    sandboxExp = 0;
    tokens = 5;
  }

  private void resetFighter() {
    unlocked = new int[] {0, 0, -1, -1, -1, -1, -1};
    kitID = 1;
    kitIndex = 0;
    playerLevel = 1;
    killStreakAttribute = 0;
    speedAttribute = 0;
    specialItemAttribute = 0;
    protectionArmorAttribute = 0;
    exp = 0;
    giveKit(true);
    buildMode = false;
    fightMode = true;
    soccerMode = false;
    royaleMode = false;
    tokens = 5;
  }

  public void setDefaultsKeepKD() {
    plugin = A_Main.getPlugin(A_Main.class);
    unlocked = new int[] {0, 0, -1, -1, -1, -1, -1};
    kitID = 1;
    kitIndex = 0;
    setLastToDamage(null);
    setLastDamagedBy(null);
    playerLevel = 1;
    prestige = 1;
    cakes = 500;
    killStreak = 0;
    killStreakAttribute = 0;
    speedAttribute = 0;
    specialItemAttribute = 0;
    protectionArmorAttribute = 0;
    exp = 0;
    buildMode = false;
    fightMode = true;
    soccerMode = false;
    royaleMode = false;
    M_Bounty.getBossBar().addPlayer(player);
    customColorIndex = -1;
    lastTimeDamaged = 1;
    parachuteTask = -1;
    noobTask = -1;
    chicken = null;
    setVipInventory(null);
    juggerSet = false;
    sandboxLevel = 0;
    sandboxExp = 0;
    tokens = 5;
  }

  public SandScoreBoardObject getSandBoardObject() {
    return sandBoardObject;
  }

  public void setSandBoardObject(SandScoreBoardObject sandBoardObject) {
    this.sandBoardObject = sandBoardObject;
  }

  public int getTokens() {
    return tokens;
  }

  public void setTokens(int tokens) {
    this.tokens = tokens;
  }

  public void incTokens(int adder) {
    this.tokens = this.tokens + adder;
    if (Zgen.worldSand(player.getWorld())) {
      sandBoardObject.updateTokens();
    } else {
      scoreBoardObject.updateTokens();
    }
  }

  public void decTokens(int subber) {
    this.tokens = this.tokens - subber;
    if (Zgen.worldSand(player.getWorld())) {
      sandBoardObject.updateTokens();
    } else {
      scoreBoardObject.updateTokens();
    }
  }



}
