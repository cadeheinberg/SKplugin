package me.cade.PluginSK.BuildKits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import com.mojang.datafixers.util.Pair;
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.PluginSK.Main;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class F6 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 6;
	static final String kitName = "Grief";
	static final String kitDrop = "Invisibility & Health Steal";
	static final String kitRightClick = "Use Shield";
	static final ChatColor kitChatColor = ChatColor.AQUA;
	static final Color armorColor = Color.fromRGB(0, 0, 0);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = kitChatColor + "Grief Sword";
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
		this.cooldownTicks = 0;
		this.material = Material.NETHERITE_SWORD;
		this.primaryEnchantment = null;
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}
	
	public F6() {
		super();
	}
	
	public F6(Player player) {
		super(player);
	}
	
	@Override
	public void loadSecondaryWeapon() {
		//pass
	}

	@Override
	public boolean doRightClick(Material material) {
		return super.doRightClick(material);
	}

	@Override
	public void doDrop(Material material) {
		// do special conditions before (right here)
		super.doDrop(material);
	}

	@Override
	void activateSpecial() {
		CraftPlayer craftPlayer = (CraftPlayer) super.player;
		super.player.setInvisible(true);
		makeInvisible(craftPlayer.getHandle());
		super.player.playSound(super.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
		Main.getPpAPI().addActivePlayerParticle(player, ParticleEffect.HEART, ParticleStyle.fromName("swords"));
		super.activateSpecial();
	}

	@Override
	public void deActivateSpecial() {
		CraftPlayer craftPlayer = (CraftPlayer) super.player;
		super.player.setInvisible(false);
		makeVisible(craftPlayer.getHandle());
		Main.getPpAPI().removeActivePlayerParticles(player, ParticleEffect.HEART);
		super.deActivateSpecial();
	}
	
	private static void makeInvisible(Entity entity) {

		// Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
		List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList = new ArrayList<>();

		// Adding an ice block to the head
		equipmentList.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(null)));
		equipmentList.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(null)));
		equipmentList.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(null)));
		equipmentList.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(null)));

		// Creating the packet
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
			conn.sendPacket(packet);
		}
	}

	private static void makeVisible(Entity entity) {

		// Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
		List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList = new ArrayList<>();

		Player p = Bukkit.getServer().getPlayer(entity.getUniqueID());
		// Adding an ice block to the head
		equipmentList.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(p.getEquipment().getHelmet())));
		equipmentList.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(p.getEquipment().getChestplate())));
		equipmentList.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(p.getEquipment().getLeggings())));
		equipmentList.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(p.getEquipment().getBoots())));

		// Creating the packet
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
			conn.sendPacket(packet);
		}
	}

	public void doStealHealth(Player victim) {
		if (super.pFight.isAbilityActive()) {
			double combined = super.player.getHealth() + 1.5;
			if (combined > 20) {
				super.player.setHealth(20);
			} else {
				super.player.setHealth(combined);
			}
			super.player.playSound(super.player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
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
