package me.cade.PluginSK.BuildKits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class F_KitArmor {


  public static void giveArmor(Player player, Color armorColor) {

    ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
    LeatherArmorMeta lhe = (LeatherArmorMeta) lhelmet.getItemMeta();
    lhe.setUnbreakable(true);

    ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
    LeatherArmorMeta lch = (LeatherArmorMeta) lchest.getItemMeta();
    lch.setUnbreakable(true);

    ItemStack lleggs = new ItemStack(Material.LEATHER_LEGGINGS, 1);
    LeatherArmorMeta lle = (LeatherArmorMeta) lleggs.getItemMeta();
    lle.setUnbreakable(true);

    ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
    LeatherArmorMeta lbo = (LeatherArmorMeta) lboots.getItemMeta();
    lbo.setUnbreakable(true);
    
    lhe.setColor(armorColor);
    lch.setColor(armorColor);
    lle.setColor(armorColor);
    lbo.setColor(armorColor);

    lhe.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    lch.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    lle.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    lbo.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    lhe.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    lch.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    lle.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    lbo.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    lhe.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    lch.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    lle.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    lbo.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    lhelmet.setItemMeta(lhe);
    lchest.setItemMeta(lch);
    lleggs.setItemMeta(lle);
    lboots.setItemMeta(lbo);

    player.getEquipment().setHelmet(lhelmet);
    player.getEquipment().setChestplate(lchest);
    player.getEquipment().setLeggings(lleggs);
    player.getEquipment().setBoots(lboots);

  }

}
