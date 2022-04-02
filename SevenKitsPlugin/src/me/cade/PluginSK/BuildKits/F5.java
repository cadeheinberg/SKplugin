package me.cade.PluginSK.BuildKits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.Damaging.DealDamage;

public class F5 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 5;
	static final String kitName = "Sumo";
	static final String kitDrop = "Ground Pound";
	static final String kitRightClick = "Toss Enemy";
	static final ChatColor kitChatColor = ChatColor.BLUE;
	static final Color armorColor = Color.fromRGB(8, 111, 255);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = ChatColor.BLUE + "Sumo Stick";
	private double meleeDamage;
	private double projectileDamage;
	private int cooldownTicks;
	private Material material;
	private EnchantmentPair primaryEnchantment;

	// Secondary
	static final String secondaryWeaponName = kitChatColor + "none";
	private double sceondaryMeleeDamage;
	private double secondaryProjectileDamage;
	private int secondaryCooldownTicks;
	private Material secondaryMaterial;
	private EnchantmentPair secondaryEnchantment;

	@Override
	public void setUpPrivateKitVariables() {
		this.durationTicks = 200;
		this.rechargeTicks = 50;
		this.meleeDamage = 5;
		this.projectileDamage = 4;
		this.specialDamage = 4;
		this.cooldownTicks = 5;
		this.material = Material.BAMBOO;
		this.primaryEnchantment = new EnchantmentPair(Enchantment.KNOCKBACK, 3);
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}
	
	public F5() {
		super();
	}
	
	public F5(Player player) {
		super(player);
	}
	
	@Override
	public void loadSecondaryWeapon() {
		//pass
	}

	@Override
	public boolean doRightClick() {
		//pick up player and throw them
		return true;
	}

	@Override
	public
	void doDrop() {
		super.doDrop();
	}

	@Override
	void activateSpecial() {
		super.activateSpecial();
		super.player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, durationTicks, 0));
		super.player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTicks, 1));
		doJump(super.player, Fighter.get(super.player));
		super.player.playSound(super.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}
	
	public static void doJump(Player player, Fighter pFight) {
		player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
		launchPlayer(player, 1.4, pFight);
	}

	public static void launchPlayer(Player player, Double power, Fighter pFight) {
		Location local = player.getLocation();
		local.setPitch(-60);
		Vector currentDirection = local.getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(power, power, power));
		player.setVelocity(currentDirection);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				listenForFall(player, pFight);
			}
		}, 5);
	}
	
	@SuppressWarnings("deprecation")
	public static void listenForFall(Player player, Fighter pFight) {
		pFight.setGroundPoundTask(new BukkitRunnable() {
			@Override
			public void run() {
				if (player == null) {
					stopListening(pFight);
					return;
				}
				if (!player.isOnline()) {
					stopListening(pFight);
					return;
				}
				if (player.isDead()) {
					stopListening(pFight);
					return;
				}
				if (player.isOnGround()) {
					stopListening(pFight);
					doGroundHit(player, player.getLocation(), 0.3);
					return;
				}
				if (player.isSneaking()) {
					launchPlayerDown(player, 1.5, pFight);
				}
			}
		}.runTaskTimer(Main.getInstance(), 0L, 1L).getTaskId());
	}

	public static void launchPlayerDown(Player player, Double power, Fighter pFight) {
		Location local = player.getLocation();
		local.setPitch(80);
		Vector currentDirection = local.getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(power, power, power));
		player.setVelocity(currentDirection);
	}

	public static void stopListening(Fighter pFight) {
		Bukkit.getScheduler().cancelTask(pFight.getGroundPoundTask());
		pFight.setGroundPoundTask(-1);
	}

	// make this freeze players also
	public static void doGroundHit(Player shooter, Location location, double power) {
		shooter.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, location.getX(), location.getY() + 1,
				location.getZ(), 3);
		shooter.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
		for (Entity ent : shooter.getWorld().getNearbyEntities(location, 4, 4, 4)) {
			if (!(ent instanceof LivingEntity)) {
				return;
			}
			DealDamage.dealAmount(shooter, (LivingEntity) ent, 5.0);
			Location upShoot = ent.getLocation();
			if (ent.isOnGround()) {
				upShoot.setY(upShoot.getY() + 1);
			}
			Vector currentDirection = upShoot.toVector().subtract(location.toVector());
			currentDirection = currentDirection.multiply(new Vector(power, power, power));
			((LivingEntity) ent).setVelocity(currentDirection);
		}
	}

	/*
	 * Get Methods Get Methods Get Methods Get Methods Get Methods Get Methods Get
	 * Methods Get Methods Get Methods Get Methods Get Methods Get Methods Get
	 * Methods Get Methods Get Methods
	 */

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public int getKitID() {
		return kitID;
	}

	@Override
	public ChatColor getKitChatColor() {
		return kitChatColor;
	}

	@Override
	public Color getArmorColor() {
		return armorColor;
	}

	@Override
	public String getKitName() {
		return kitName;
	}

	@Override
	public String getKitDrop() {
		return kitDrop;
	}

	@Override
	public String getKitRightClick() {
		return kitRightClick;
	}

	@Override
	public String getWeaponName() {
		return weaponName;
	}

	@Override
	public int getDurationTicks() {
		return durationTicks;
	}

	@Override
	public int getRechargeTicks() {
		return rechargeTicks;
	}

	@Override
	public double getProjectileDamage() {
		return projectileDamage;
	}

	@Override
	public double getMeleeDamage() {
		return meleeDamage;
	}

	@Override
	public double getSpecialDamage() {
		return specialDamage;
	}

	@Override
	public EnchantmentPair getPrimaryEnchantment() {
		return primaryEnchantment;
	}

	@Override
	public int getCooldownTicks() {
		return cooldownTicks;
	}
	
	public String getSecondaryWeaponName() {
		return secondaryWeaponName;
	}

	public double getSceondaryMeleeDamage() {
		return sceondaryMeleeDamage;
	}

	public double getSecondaryProjectileDamage() {
		return secondaryProjectileDamage;
	}

	public int getSecondaryCooldownTicks() {
		return secondaryCooldownTicks;
	}

	public EnchantmentPair getSecondaryEnchantment() {
		return secondaryEnchantment;
	}

	public Material getSecondaryMaterial() {
		return secondaryMaterial;
	}
}