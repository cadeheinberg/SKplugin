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
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.PluginSK.BuildKits.F0;
import me.cade.PluginSK.BuildKits.F1;
import me.cade.PluginSK.BuildKits.F2;
import me.cade.PluginSK.BuildKits.F3;
import me.cade.PluginSK.BuildKits.F4;
import me.cade.PluginSK.BuildKits.F5;
import me.cade.PluginSK.BuildKits.F6;
import me.cade.PluginSK.BuildKits.FighterKit;
import me.cade.PluginSK.NPCS.D_ProtocolStand;
import me.cade.PluginSK.ScoreBoard.ScoreBoardObject;
import me.cade.PluginSK.SpecialItems.CombatTracker;
import me.cade.PluginSK.SpecialItems.IceCageItem;
import me.cade.PluginSK.SpecialItems.ParachuteItem;
import me.cade.PluginSK.SpecialItems.ThrowingTNTItem;

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
	
	  private static float walkSpeed = (float) 0.235;
	  private static float walkSpeedBoosted = (float) 0.3;

	private int groundPoundTask;
	
	private IceCageItem iceCageItem = null;
	private ParachuteItem parachuteItem = null;
	private CombatTracker combatTracker = null;
	private ThrowingTNTItem throwingTNTItem = null;
	
	private D_ProtocolStand[] personalStands = new D_ProtocolStand[7];
	
	private D_ProtocolStand chargedStand = null;
	
	private FighterKit fKit = null;
	
	private static final int numberOfKits = 7;
	private static FighterKit[] fKits = {new F0(),new F1(),new F2(),new F3(),
			new F4(),new F5(),new F6()};

	public Fighter(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.addToFightersHashMap();
		this.fighterJoin();
	}
	
	//enchant armor when have better armor perk or health perk idk
	
	//CraftPlayer craftPlayer = (CraftPlayer) player;
    //AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    //CraftPlayer craftPlayer = (CraftPlayer) player;
    //AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());

	private void fighterJoin() {
		this.setDefaults();
		this.downloadDatabase();
		chargedStand = new D_ProtocolStand(ChatColor.GREEN + "" + 
	    		ChatColor.BOLD + player.getDisplayName() + "'s Spawn", Main.hubSpawn, player);
		this.grantUnlocked();
		this.scoreBoardObject = new ScoreBoardObject(player);
		this.giveKit();
		Main.getPpAPI().resetActivePlayerParticles(player);
		this.resetSpecialAbility();
	    this.adjustJoinModifiers();  
	}
	
	public void fighterRespawn() {
		this.setLastDamagedBy(null);
		this.setLastToDamage(null);
		this.adjustJoinModifiers();
		this.resetSpecialAbility();
		this.resetSpecialItemCooldowns();
	}
	
	private void resetSpecialItemCooldowns() {
		player.setCooldown(IceCageItem.getMaterial(), 0);
		player.setCooldown(ParachuteItem.getMaterial(), 0);
		player.setCooldown(CombatTracker.getTrackerMaterial(), 0);
		player.setCooldown(ThrowingTNTItem.getMaterial(), 0);
	}

	public void fighterLeftServer() {
		this.uploadFighter();
		this.fighterDismountParachute();
		this.resetSpecialAbility();
	}
	
	public void fighterDeath() {
		this.resetSpecialAbility();
		this.fighterDismountParachute();
		this.incDeaths();
	}
	
	public void fighterDismountParachute() {
		if(this.parachuteItem != null) {
			this.parachuteItem.getOff();
		}
	}

	public void resetSpecialAbility() {
		setAbilityActive(false);
		setAbilityRecharged(true);
		player.setExp(1);
		player.setLevel(0);
	    player.setWalkSpeed(getWalkSpeed());
	}

	public void adjustJoinModifiers() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, true, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 0, true, false));
			}
		}, 1);
	}
	
	public void setAbilityActive(boolean fighterAbility) {
		if (!fighterAbility) {
			//turning ability active off
			
			//only if the ability was already active
			if(this.abilityActive) {
				player.setCooldown(Material.BIRCH_FENCE, 0);
				cancelCooldownTask();
				fKit.deActivateSpecial();
			}
		}
		this.abilityActive = fighterAbility;
		changeAbilityActiveParticleEffect();
	}

	public void setAbilityRecharged(boolean fighterRecharged) {
		if(fighterRecharged) {
			//turning ability charged fully on
			
			//only if the ability was already not recharged
			if(!this.abilityRecharged) {
				player.setCooldown(Material.JUNGLE_FENCE, 0);
				cancelRechargeTask();
			}	
		}
		this.abilityRecharged = fighterRecharged;
		this.changeAbilityRechargedParticleEffect();
	}
	
	private void changeAbilityActiveParticleEffect() {
			if(this.kitID == 6) {
				//greif goes invisible
				return;
			}
		if(this.abilityActive) {
	        if (Main.getPpAPI() != null) {
	        	Main.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, 
	            		ParticleStyle.fromName("normal"), new OrdinaryColor(this.getFKit().getArmorColor().getRed(), 
	            				this.getFKit().getArmorColor().getGreen(), this.getFKit().getArmorColor().getBlue()));
	        	Main.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, 
	            		ParticleStyle.fromName("normal"), new OrdinaryColor(this.getFKit().getArmorColor().getRed(), 
	            				this.getFKit().getArmorColor().getGreen(), this.getFKit().getArmorColor().getBlue()));
	        }
		}else {
			if (Main.getPpAPI() != null) {
	        	Main.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
	        }
		}
	}
	
	private void changeAbilityRechargedParticleEffect() {
		if(this.abilityRecharged) {
	        if (Main.getPpAPI() != null) {
	        	Main.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, 
	            		ParticleStyle.fromName("point"), new OrdinaryColor(this.getFKit().getArmorColor().getRed(), 
	            				this.getFKit().getArmorColor().getGreen(), this.getFKit().getArmorColor().getBlue()));
	        	Main.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, 
	            		ParticleStyle.fromName("point"), new OrdinaryColor(this.getFKit().getArmorColor().getRed(), 
	            				this.getFKit().getArmorColor().getGreen(), this.getFKit().getArmorColor().getBlue()));
	        }
		}else {
			if (Main.getPpAPI() != null) {
	        	Main.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
	        }
		}
	}
	
	public void cancelCooldownTask() {
		if(this.cooldownTask != -1) {
			Bukkit.getScheduler().cancelTask(this.cooldownTask);
			this.cooldownTask = -1;
		}
	}
	
	public void cancelRechargeTask() {
		if(this.rechargeTask != -1) {
			Bukkit.getScheduler().cancelTask(this.rechargeTask);
			this.rechargeTask = -1; 
		}
	}

	public void addToFightersHashMap() {
		fighters.put(this.uuid, this);
	}
	
	private void downloadDatabase() {
		if (Main.mysql.playerExists(player)) {
			downloadFighter();
		} else {
			Main.mysql.addScore(player);
		}
		updateName();
	}

	public void giveKit() {
	    if (kitID == fKits[0].getKitID()) {
	        fKit = new F0(player);
	      } else if (kitID == fKits[1].getKitID()) {
		        fKit = new F1(player);
	      }
	      else if (kitID == fKits[2].getKitID()) {
		        fKit = new F2(player);
	      }
	      else if (kitID == fKits[3].getKitID()) {
		        fKit = new F3(player);
	      }
	      else if (kitID == fKits[4].getKitID()) {
		        fKit = new F4(player);
	      }
	      else if (kitID == fKits[5].getKitID()) {
		        fKit = new F5(player);
	      }
	      else if (kitID == fKits[6].getKitID()) {
		        fKit = new F6(player);
	      }
	}

	public void giveKitWithID(int kitID) {
		this.setKitID(kitID);
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
		this.setKitID(Main.mysql.getStat(player, Main.mysql.column[2]));
		this.setKitIndex(Main.mysql.getStat(player, Main.mysql.column[3]));
		this.setPlayerLevel(Main.mysql.getStat(player, Main.mysql.column[4]));
		this.setKills(Main.mysql.getStat(player, Main.mysql.column[5]));
		this.setKillStreak(Main.mysql.getStat(player, Main.mysql.column[6]));
		this.setDeaths(Main.mysql.getStat(player, Main.mysql.column[7]));
		this.setCakes(Main.mysql.getStat(player, Main.mysql.column[8]));
		this.setExp(Main.mysql.getStat(player, Main.mysql.column[9]));
		this.setUnlockedKit(0, Main.mysql.getStat(player, Main.mysql.column[10]));
		this.setUnlockedKit(1, Main.mysql.getStat(player, Main.mysql.column[11]));
		this.setUnlockedKit(2, Main.mysql.getStat(player, Main.mysql.column[12]));
		this.setUnlockedKit(3, Main.mysql.getStat(player, Main.mysql.column[13]));
		this.setUnlockedKit(4, Main.mysql.getStat(player, Main.mysql.column[14]));
		this.setUnlockedKit(5, Main.mysql.getStat(player, Main.mysql.column[15]));
		this.setUnlockedKit(6, Main.mysql.getStat(player, Main.mysql.column[16]));
	}

	public void uploadFighter() {
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[2], this.getKitID());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[3], this.getKitIndex());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[4], this.getPlayerLevel());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[5], this.getKills());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[6], this.getKillStreak());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[7], this.getDeaths());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[8], this.getCakes());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[9], this.getExp());
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[10], this.getUnlockedKit(0));
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[10], this.getUnlockedKit(1));
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[10], this.getUnlockedKit(2));
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[10], this.getUnlockedKit(3));
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[10], this.getUnlockedKit(4));
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[10], this.getUnlockedKit(5));
		Main.mysql.setStat(player.getUniqueId().toString(), Main.mysql.column[10], this.getUnlockedKit(6));
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
		if (kitID == fKits[5].getKitID()) {
			if (groundPoundTask != -1) {
				F5.stopListening(this);
			}
		}
		return;
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
		Main.mysql.updateName(player, Main.mysql.column[1], player.getName());
	}

	public void setDefaults() {
		this.plugin = Main.getPlugin(Main.class);
		this.abilityActive = false;
		this.abilityRecharged = true;
		this.player.setExp(1);
		this.player.setLevel(0);
		this.player.setInvisible(false);
		this.player.setWalkSpeed(getWalkSpeed());
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
		this.setGroundPoundTask(-1);
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

	public int getGroundPoundTask() {
		return groundPoundTask;
	}

	public void setGroundPoundTask(int groundPoundTask) {
		this.groundPoundTask = groundPoundTask;
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

	public IceCageItem getIceCageItem() {
		return iceCageItem;
	}
	
	public void setIceCubeItem(IceCageItem iceCageItem) {
		this.iceCageItem =  iceCageItem;
	}

	public ParachuteItem getParachuteItem() {
		return parachuteItem;
	}

	public void setParachuteItem(ParachuteItem parachuteItem) {
		this.parachuteItem = parachuteItem;
	}

	public CombatTracker getCombatTracker() {
		return combatTracker;
	}

	public void setCombatTracker(CombatTracker combatTracker) {
		this.combatTracker = combatTracker;
	}

	public ThrowingTNTItem getThrowingTNTItem() {
		return throwingTNTItem;
	}

	public void setThrowingTNTItem(ThrowingTNTItem throwingTNTItem) {
		this.throwingTNTItem = throwingTNTItem;
	}
	
	public ScoreBoardObject getScoreBoardObjext() {
		return this.scoreBoardObject;
	}
	
	public void setScoreBoardObjext(ScoreBoardObject scoreBoardObject) {
		this.scoreBoardObject = scoreBoardObject;
	}

	public D_ProtocolStand[] getPersonalStands() {
		return personalStands;
	}

	public D_ProtocolStand getChargedStand() {
		return chargedStand;
	}

	public FighterKit getFKit() {
		return fKit;
	}

	public void setFKit(FighterKit fkit) {
		this.fKit = fkit;
	}
	
	public static FighterKit[] getFKits() {
		return fKits;
	}
	
	  public static float getWalkSpeed() {
		return walkSpeed;
	  }

	  public static float getWalkSpeedBoosted() {
		return walkSpeedBoosted;
	  }

	public static int getNumberOfKits() {
		return numberOfKits;
	}

}
