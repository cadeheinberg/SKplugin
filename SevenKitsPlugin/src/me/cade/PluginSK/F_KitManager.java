package me.cade.PluginSK;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import me.cade.PluginSK.cakes.C_CakeSpawner;

public class F_KitManager {

  public static void openBuyMenu(Player player, int index, Weapon[] buyItems, Inventory buyMenu) {
    buyMenu.setItem(22, buyItems[index].getWeaponItem());
    player.openInventory(buyMenu);
  }

  public static boolean tryToBuyKit(Player player, int index, Integer[] prices, int kitID) {
    E1_Fighter pFight = Vars.getFighter(player);
    int money = pFight.getCakes();
    if (money < prices[index]) {
      int owed = prices[index] - money;
      player.closeInventory();
      player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      player.sendMessage(
        ChatColor.RED + "You need " + ChatColor.WHITE + owed + "" + ChatColor.RED + " more " + C_CakeSpawner.getCakeName());
      return false;
    } else {
      pFight.decCakes(prices[index]);
      pFight.incUnlocked(kitID - 1);
      player.closeInventory();
      player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
      if(index == 0) {
        pFight.incExp(2000);
      } else if(index == 1) {
        pFight.incExp(1500);
      } else if(index == 2) {
        pFight.incExp(1750);
      } if(index == 3) {
        pFight.incExp(2000);
      }
      return true;
    }
  }

  public static boolean hasKitManager(Player player, int index, int kitID, Weapon[] buyItems,
    Weapon[] items, Inventory buyMenu) {
    Integer numKit = Vars.getFighter(player).getUnlocked()[kitID - 1];
    if (numKit == -1) {
      permIsNull(player, index, buyItems, items, buyMenu);
      return false;
    }
    if (numKit < index) {
      if (hasPrePerm(player, index, numKit)) {
        openBuyMenu(player, index, buyItems, buyMenu);
        return false;
      } else {
        doesNotOwn(player, index, items);
        return false;
      }
    }
    return true;
  }

  public static void permIsNull(Player player, int index, Weapon[] buyItems, Weapon[] items,
    Inventory buyMenu) {
    if (index == 0) {
      openBuyMenu(player, index, buyItems, buyMenu);
    } else {
      doesNotOwn(player, index, items);
    }
    return;
  }

  public static boolean hasPrePerm(Player player, int index, int numKit) {
    if (numKit == index - 1) {
      return true;
    }
    return false;
  }

  public static void doesNotOwn(Player player, int index, Weapon[] items) {
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
    player.sendMessage(ChatColor.RED + "You must purchase " + items[index].getWeaponName() + "s"
      + ChatColor.RED + " in order!");
  }

  public static void giveArmor(Player player, Color armorColor, Boolean ignoreVip) {

    E1_Fighter pFight = Vars.getFighter(player);

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
    
    if(ignoreVip) {
      lhe.setColor(armorColor);
      lch.setColor(armorColor);
      lle.setColor(armorColor);
      lbo.setColor(armorColor);
    }else {
      if(pFight.getCustomColorIndex() > -1) {
        Color colorToSet = D3_Menus.getArmorColor(pFight.getCustomColorIndex());
        lhe.setColor(colorToSet);
        lch.setColor(colorToSet);
        lle.setColor(colorToSet);
        lbo.setColor(colorToSet);
      }else {
        lhe.setColor(armorColor);
        lch.setColor(armorColor);
        lle.setColor(armorColor);
        lbo.setColor(armorColor);
      }
    }

    ArrayList<String> itemLore = new ArrayList<String>();
    
    int level = pFight.getProtectionArmorAttribute();
    int setter = 0;
    if (level == 0) {
      // nothing
      lhe.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      lch.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      lle.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      lbo.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      itemLore.add(3 + " armor protection");
      lhe.setLore(itemLore);
      lch.setLore(itemLore);
      lle.setLore(itemLore);
      lbo.setLore(itemLore);
    }else if(level == 1) {
      // boots
      // BASE
      lhe.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      lch.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      lle.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      itemLore.add(3 + " armor protection");
      lhe.setLore(itemLore);
      lch.setLore(itemLore);
      lle.setLore(itemLore);
      // UPGRADE
      setter = 4;
      lbo.addEnchant(Enchantment.DURABILITY, 999, true);
      lbo.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", setter, AttributeModifier.Operation.ADD_NUMBER));
      itemLore.clear();
      itemLore.add(setter + " armor protection");
      lbo.setLore(itemLore);
    }else if(level == 2) {
      // boots and leggings
      // BASE
      lhe.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      lch.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER));
      itemLore.add(3 + " armor protection");
      lhe.setLore(itemLore);
      lch.setLore(itemLore);
      // UPGRADE
      setter = 4;
      lle.addEnchant(Enchantment.DURABILITY, 999, true);
      lbo.addEnchant(Enchantment.DURABILITY, 999, true);
      lle.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", setter, AttributeModifier.Operation.ADD_NUMBER));
      lbo.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", setter, AttributeModifier.Operation.ADD_NUMBER));
      itemLore.clear();
      itemLore.add(setter + " armor protection");
      lle.setLore(itemLore);
      lbo.setLore(itemLore);
    }else if(level == 3) {
      // full armor suit
      setter = 4;
      lhe.addEnchant(Enchantment.DURABILITY, 999, true);
      lch.addEnchant(Enchantment.DURABILITY, 999, true);
      lle.addEnchant(Enchantment.DURABILITY, 999, true);
      lbo.addEnchant(Enchantment.DURABILITY, 999, true);
      lhe.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", setter, AttributeModifier.Operation.ADD_NUMBER));
      lch.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", setter, AttributeModifier.Operation.ADD_NUMBER));
      lle.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", setter, AttributeModifier.Operation.ADD_NUMBER));
      lbo.addAttributeModifier(Attribute.GENERIC_ARMOR,
        new AttributeModifier("GENERIC_ARMOR", setter, AttributeModifier.Operation.ADD_NUMBER));
      itemLore.clear();
      itemLore.add(setter + " armor protection");
      lhe.setLore(itemLore);
      lch.setLore(itemLore);
      lle.setLore(itemLore);
      lbo.setLore(itemLore);
    }

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
