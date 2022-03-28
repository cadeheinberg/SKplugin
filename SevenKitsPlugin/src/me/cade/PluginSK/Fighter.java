package me.cade.PluginSK;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.cade.PluginSK.BuildKits.F0_Noob;
import me.cade.PluginSK.BuildKits.F_KitFilter;
import me.cade.PluginSK.KitListeners.G0_Noob;
import me.cade.PluginSK.KitListeners.G_KitFilter;
import me.cade.PluginSK.ScoreBoard.ScoreBoardObject;
import me.cade.PluginSK.SpecialItems.H1_CombatTracker;

public class Fighter {

	// your error for the unlocked might be in the way you are uploading it as a
	// boolean array
	private Plugin plugin;
	public static HashMap<UUID, Fighter> fighters = new HashMap<UUID, Fighter>();
	private Player player;
	private UUID uuid;

	private boolean abilityActive;
	private boolean abilityRecharged;

	//changes alot during the game
	private UUID lastToDamage;
	private UUID lastDamagedBy;
	private int cooldownTask;
	private int rechargeTask;

	private int kitID;
	private int kitIndex;
	private int playerLevel;
	private int kills;
	private int killStreak;
	private int deaths;
	private int cakes;
	private int exp;
	private int[] unlockedKits = new int[7];
	private ScoreBoardObject scoreBoardObject;

	private int noobTask;

	public Fighter(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.addToFightersHashMap();
		fighterJoin();
		if(isAbilityActive()) {
			player.sendMessage("called 5");
			setAbilityActive(false);
		}
		if(!isAbilityRecharged()) {
			player.sendMessage("called 6");
			setAbilityRecharged(true);
		};
	}

	private void fighterJoin() {
		this.setDefaults();
		this.downloadDatabase();
		this.grantUnlocked();
		this.scoreBoardObject = new ScoreBoardObject(player);
		this.giveKit();
	    this.adjustJoinModifiers();
	}
	
	public void fighterRespawn() {
		this.setLastDamagedBy(null);
		this.setLastToDamage(null);
		this.adjustJoinModifiers();
	}
	
	public void fighterLeftServer() {
		uploadFighter();
	}
	
	public void fighterDeath() {
		if(isAbilityActive()) {
			player.sendMessage("called 1");
			setAbilityActive(false);
		}
		if(!isAbilityRecharged()) {
			player.sendMessage("called 2");
			setAbilityRecharged(true);
		};
		player.setExp(1);
		player.setLevel(0);
	    player.setWalkSpeed((float) 0.2);
	}

	public void adjustJoinModifiers() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0, true, false));
			}
		}, 1);
	}
	
	public void setAbilityActive(boolean fighterAbility) {
		player.sendMessage("Setting abilityActive: " + fighterAbility);
		this.abilityActive = fighterAbility;
		if (!fighterAbility) {
			//turning ability active off
			player.setCooldown(Material.BIRCH_FENCE, 0);
			player.sendMessage("abilityActive turning off");
			cancelCooldownTask();
			G_KitFilter.deactivateSpecialFromKitID(player, this.kitID, this.kitIndex);
		}
	}

	public void setAbilityRecharged(boolean fighterRecharged) {
		player.sendMessage("Setting abilityRecharged: " + fighterRecharged);
		this.abilityRecharged = fighterRecharged;
		if(fighterRecharged) {
			//turning ability charged fully on
			player.setCooldown(Material.JUNGLE_FENCE, 0);
			player.sendMessage("abilityRecharged turning on");
			cancelRechargeTask();
		}
	}
	
	public void cancelCooldownTask() {
		Bukkit.getScheduler().cancelTask(this.cooldownTask);
		this.cooldownTask = -1;
	}
	
	public void cancelRechargeTask() {
		Bukkit.getScheduler().cancelTask(this.rechargeTask);
		this.rechargeTask = -1; 
	}

	public void addToFightersHashMap() {
		fighters.put(this.uuid, this);
	}
	
	private void downloadDatabase() {
		if (MySQL.playerExists(player)) {
			downloadFighter();
		} else {
			MySQL.addScore(player);
		}
		updateName();
	}

	public void giveKit() {
		player.getInventory().clear();
		F_KitFilter.giveKitFromKitID(player, this.kitID, this.kitIndex);
		;
		player.getInventory().setItem(8, H1_CombatTracker.getTracker().getWeaponItem());
		player.closeInventory();
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

	public boolean isAbilityActive() {
		return abilityActive;
	}

	public UUID getLastToDamage() {
		return lastToDamage;
	}

	public void setLastToDamage(Player lastToDamage) {
		if (this.lastToDamage == null) {
			this.lastToDamage = null;
			return;
		}
		this.lastToDamage = lastToDamage.getUniqueId();
	}

	public UUID getLastDamagedBy() {
		return lastDamagedBy;
	}

	public void setLastDamagedBy(Player lastDamagedBy) {
		if (lastDamagedBy == null) {
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
		this.setUnlockedKit(0, MySQL.getStat(player, MySQL.column[10]));
		this.setUnlockedKit(1, MySQL.getStat(player, MySQL.column[11]));
		this.setUnlockedKit(2, MySQL.getStat(player, MySQL.column[12]));
		this.setUnlockedKit(3, MySQL.getStat(player, MySQL.column[13]));
		this.setUnlockedKit(4, MySQL.getStat(player, MySQL.column[14]));
		this.setUnlockedKit(5, MySQL.getStat(player, MySQL.column[15]));
		this.setUnlockedKit(6, MySQL.getStat(player, MySQL.column[16]));
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
		MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlockedKit(0));
		MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlockedKit(1));
		MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlockedKit(2));
		MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlockedKit(3));
		MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlockedKit(4));
		MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlockedKit(5));
		MySQL.setStat(player.getUniqueId().toString(), MySQL.column[10], this.getUnlockedKit(6));
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
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled up to level " + playerLevel + "!");
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

	public void setUnlockedKit(int kitID, int index) {
		this.unlockedKits[kitID] = index;
	}

	public int getUnlockedKit(int kitID) {
		return unlockedKits[kitID];
	}

	private void updateName() {
		MySQL.updateName(player, MySQL.column[1], player.getName());
	}

	public void setDefaults() {
		this.plugin = Main.getPlugin(Main.class);
		this.abilityActive = false;
		this.abilityRecharged = true;
		this.player.setExp(1);
		this.player.setLevel(0);
		this.player.setInvisible(false);
		this.player.setWalkSpeed((float) 0.2);
		// Glowing.setGlowingOffForAll(this.player);
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
		this.unlockedKits[0] = 1;
		this.unlockedKits[1] = 1;
		this.unlockedKits[2] = 0;
		this.unlockedKits[3] = 0;
		this.unlockedKits[4] = 0;
		this.unlockedKits[5] = 0;
		this.unlockedKits[6] = 0;
		this.setNoobTask(-1);
		this.setCooldownTask(-1);
	}

	public void grantUnlocked() {
		this.unlockedKits[0] = 1;
		this.unlockedKits[1] = 1;
		this.unlockedKits[2] = 1;
		this.unlockedKits[3] = 1;
		this.unlockedKits[4] = 1;
		this.unlockedKits[5] = 1;
		this.unlockedKits[6] = 1;
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

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	public int getCooldownTask() {
		return cooldownTask;
	}

	public void setCooldownTask(int cooldownTask) {
		this.cooldownTask = cooldownTask;
	}
	
	public void setRechargeTask(int rechargeTask) {
		this.rechargeTask = rechargeTask;
	}

	public int getRechargeTask() {
		return rechargeTask;
	}

	public boolean isAbilityRecharged() {
		return abilityRecharged;
	}

}
