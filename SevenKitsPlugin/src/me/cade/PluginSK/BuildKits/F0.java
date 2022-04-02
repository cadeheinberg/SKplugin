package me.cade.PluginSK.BuildKits;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Damaging.DealDamage;

public class F0 extends FighterKit {

	static final int kitID = 0;
	static final String kitName = "Airbender";
	static final String kitDrop = "Gust of Wind";
	static final String kitRightClick = "Use Shield";
	static final ChatColor kitChatColor = ChatColor.WHITE;
	static final Color armorColor = Color.fromRGB(255, 255, 255);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = kitChatColor + "Airbender Sword";
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
		this.material = Material.IRON_SWORD;
		this.primaryEnchantment = null;
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}
	
	public F0() {
		super();
	}
	
	public F0(Player player) {
		super(player);
	}

	@Override
	public void loadSecondaryWeapon() {
		// pass
	}

	@Override
	public boolean doRightClick() {
	      return true;
	}

	@Override
	public void doDrop() {
		if (Fighter.get(this.getPlayer()).getGroundPoundTask() != -1) {
			return;
		}
		super.doDrop();
	}

	@Override
	void activateSpecial() {
		super.activateSpecial();
		this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, durationTicks, 1));
		this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTicks, 1));
		gustLaunch(this.getPlayer());
		this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.ENTITY_HORSE_ANGRY, 8, 1);
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	public static void gustLaunch(Player killer) {
		Location playerLocation = killer.getLocation();
		killer.playSound(killer.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
		if (playerLocation.getPitch() > 49) {
			launchPlayer(killer, -1.5);
		}
		Location origin = killer.getEyeLocation();
		Vector direction = killer.getLocation().getDirection();
		double dX = direction.getX();
		double dY = direction.getY();
		double dZ = direction.getZ();
		playerLocation.setPitch((float) -30.0);
		int range = 13;
		double power = 2.8;
		ArrayList<Integer> hitList = new ArrayList<Integer>();
		for (int j = 2; j < range; j++) {
			origin = origin.add(dX * j, dY * j, dZ * j);
			killer.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, origin.getX(), origin.getY(), origin.getZ(), 5);
			ArrayList<Entity> entityList = (ArrayList<Entity>) killer.getWorld().getNearbyEntities(origin, 2.5, 2.5,
					2.5);
			for (Entity entity : entityList) {
				if (entity instanceof LivingEntity)
					if (hitList.contains(((LivingEntity) entity).getEntityId())) {
						continue;
					}
				hitList.add(((LivingEntity) entity).getEntityId());
				{
					DealDamage.dealAmount(killer, (LivingEntity) entity, 1);
					if (killer.getName().equals(((LivingEntity) entity).getName())) {
						return;
					}
					Vector currentDirection = playerLocation.getDirection().normalize();
					currentDirection = currentDirection.multiply(new Vector(power, power, power));
					entity.setVelocity(currentDirection);
				}
			}
			origin = origin.subtract(dX * j, dY * j, dZ * j);
		}
	}

	public static void launchPlayer(Entity entity, Double power) {
		Vector currentDirection = entity.getLocation().getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(power, power, power));
		entity.setVelocity(currentDirection);
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
