package me.cade.PluginSK;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cade.PluginSK.BuildKits.FighterKit.EnchantmentPair;

public class Weapon {
  
  private ItemStack weaponItem;
  private Material weaponMaterial;
  private String weaponName;

  public Weapon(Material material, String name, String lore) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
        weaponItem.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    itemLore.add(lore2);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2, String lore3) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    itemLore.add(lore2);
    itemLore.add(lore3);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2, String lore3, String lore4) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    itemLore.add(lore2);
    itemLore.add(lore3);
    itemLore.add(lore4);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2, String lore3, String lore4, String lore5) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    itemLore.add(lore2);
    itemLore.add(lore3);
    itemLore.add(lore4);
    itemLore.add(lore5);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
    }
  }
  
  public Weapon(Material material, String name, double meleeDamage, 
		  double projectileDamage, double specialDamage, 
		  int weaponCooldown, int abilityCooldown, 
		  int abilityRecharge, EnchantmentPair enchantment,
		  EnchantmentPair perkEnchantment1) {
	    ArrayList<String> itemLore = new ArrayList<String>();
	    itemLore.add(ChatColor.WHITE + "" + meleeDamage + ChatColor.YELLOW + " attack damage");
	    itemLore.add(ChatColor.WHITE + "" + projectileDamage + ChatColor.YELLOW + " projectile damage");
	    itemLore.add(ChatColor.WHITE + "" + specialDamage + ChatColor.YELLOW + " ability damage");
	    itemLore.add(ChatColor.WHITE + "" + Math.round((weaponCooldown/20) / 10)*10 + ChatColor.YELLOW + " weapon cooldown");
	    itemLore.add(ChatColor.WHITE + "" + Math.round((abilityCooldown/20) / 10)*10 + ChatColor.YELLOW + " ability cooldown");
	    itemLore.add(ChatColor.WHITE + "" + Math.round((abilityRecharge/20) / 10)*10 + ChatColor.YELLOW + " ability recharge");
	    
	    if(enchantment != null) {
	    	itemLore.add(ChatColor.DARK_PURPLE + "Primary: " +  enchantment.getString());
	    }else {
	    	itemLore.add(ChatColor.DARK_PURPLE + "Primary: no enchantment");
	    }
	    
	    if(perkEnchantment1 != null) {
	    	itemLore.add(ChatColor.DARK_PURPLE + "Perk: " + perkEnchantment1.getString());
	    }else {
	    	itemLore.add(ChatColor.DARK_PURPLE + "Perk: no enchantment");
	    }
	    
	    weaponMaterial = material;
	    weaponName = name;
	    weaponItem = new ItemStack(weaponMaterial, 1);
	    ItemMeta meta = weaponItem.getItemMeta();
	    meta.setDisplayName(weaponName);
	    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
	    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
	    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	    meta.setLore(itemLore);
	    meta.setUnbreakable(true);
	    weaponItem.setItemMeta(meta);
	    if(enchantment != null) {
	    	this.applyWeaponUnsafeEnchantment(enchantment.getEnchantment(),
					enchantment.getLevel());
	    }
	    if(perkEnchantment1 != null) {
	    	this.applyWeaponUnsafeEnchantment(perkEnchantment1.getEnchantment(),
	    			perkEnchantment1.getLevel());
	    }
	    this.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("GENERIC_ATTACK_DAMAGE",
				meleeDamage, AttributeModifier.Operation.ADD_NUMBER));
	  }
  
  public void addLore(String lore) {
    ItemMeta meta = weaponItem.getItemMeta();
    ArrayList<String> itemLore = (ArrayList<String>) meta.getLore();
    itemLore.add(lore);
    meta.setLore(itemLore);
    weaponItem.setItemMeta(meta);
  }

  public void applyWeaponEnchantment(Enchantment enchantment, int power) {
    weaponItem.addEnchantment(enchantment, power);
  }
  
  public void applyWeaponUnsafeEnchantment(Enchantment enchantment, int power) {
    weaponItem.addUnsafeEnchantment(enchantment, power);
  }
  
  public void addNewAttribute(Attribute attribute, AttributeModifier modifier) {
    ItemMeta meta = weaponItem.getItemMeta();
    meta.addAttributeModifier(attribute, modifier);
    weaponItem.setItemMeta(meta);
  }
  
  public void giveWeapon(Player player) {
    player.getInventory().addItem(weaponItem);
  }
  
  public void removeWeapon(Player player) {
    player.getInventory().remove(weaponItem);
  }

  public String getWeaponName() {
    return weaponName;
  }

  public ItemStack getWeaponItem() {
    return weaponItem;
  }
}
