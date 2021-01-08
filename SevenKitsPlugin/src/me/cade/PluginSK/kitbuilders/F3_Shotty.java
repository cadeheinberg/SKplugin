package me.cade.PluginSK.kitbuilders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.F_KitManager;
import me.cade.PluginSK.Weapon;
import me.cade.PluginSK.cakes.C_CakeSpawner;

public class F3_Shotty {

  private static final int kitID = 3;
  private static final String kitName = ChatColor.GOLD + "Shotty";
  private static final Color armorColor = Color.fromRGB(255, 255, 0);
  private static Weapon[] items;
  private static Weapon[] buyItems;
  private static Weapon[] displayItems;
  private static Material[] displays;
  private static Integer[] prices;
  private static ChatColor g;
  private static Inventory kitMenu;
  private static Inventory buyMenu;

  private Player player;
  private int index;

  public F3_Shotty(Player player, int index, boolean giveKit) {
    this.player = player;
    this.index = index;
    if (giveKit) {
      addPlayer(player, index);
    }
  }

  public static Color getArmorColor() {
    return armorColor;
  }

  public static String getKitName() {
    return kitName;
  }

  private void addPlayer(Player player, int index) {
    PlayerInventory playInv = player.getInventory();
    playInv.addItem(items[index].getWeaponItem());
    F_KitManager.giveArmor(player, armorColor, false);
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
  }

  public static void openKitMenu(Player player) {
    player.openInventory(kitMenu);
  }

  public static boolean giveThisKit(Player player, int index) {
    if (F_KitManager.hasKitManager(player, index, kitID, buyItems, items, buyMenu)) {
      return true;
    }
    return false;
  }

  public static boolean buyThisKit(Player player, int index) {
    if (F_KitManager.tryToBuyKit(player, index, prices, kitID)) {
      return true;
    }
    return false;
  }

  public static int getKitID() {
    return kitID;
  }

  public static Inventory getKitMenu() {
    return kitMenu;
  }

  public static Inventory getBuyMenu() {
    return buyMenu;
  }

  public void removeShotty() {
    items[index].removeWeapon(player);
  }

  public static void makeShotty() {
    g = ChatColor.GOLD;

    String name = g + "Shotty Shotgun";
    String dependent = ChatColor.YELLOW + " power";

    String w = ChatColor.WHITE + "";
    String gr = ChatColor.GREEN + "";
    String cost = ChatColor.YELLOW + " " + C_CakeSpawner.getCakeName() + " (one time purchase)";
    String attack = ChatColor.YELLOW + " attack damage";
    String special = ChatColor.YELLOW + " snowball damage";
    String cool = ChatColor.YELLOW + " cooldown";

    prices = F0_Prices.getPriceList(kitID - 1);
    buyItems = new Weapon[4];
    items = new Weapon[4];

    displayItems = new Weapon[4];
    displays = F0_Materials.getMaterialList(kitID - 1);

    int itemLevel = 1;
    for (int i = 0; i < 4; i++) {
      displayItems[i] = new Weapon(displays[i], name + " (Level: " + itemLevel + ")", gr + prices[i] + cost,
        w + F0_Stats.getDamageList(kitID - 1)[i] + attack,
        w + F0_Stats.getSpecialDamageList(kitID - 1)[i] + special,
        w + F0_Stats.getPowerList(kitID - 1)[i] + dependent,
        w + F0_Stats.getCooldownList(kitID - 1)[i] + cool);
      items[i] = new Weapon(F0_Materials.getMaterialList(kitID - 1)[i], name,
        w + F0_Stats.getDamageList(kitID - 1)[i] + attack,
        w + F0_Stats.getSpecialDamageList(kitID - 1)[i] + special,
        w + F0_Stats.getPowerList(kitID - 1)[i] + dependent,
        w + F0_Stats.getCooldownList(kitID - 1)[i] + cool);
      items[i].addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
        new AttributeModifier("GENERIC_ATTACK_DAMAGE", F0_Stats.getDamageList(kitID - 1)[i],
          AttributeModifier.Operation.ADD_NUMBER));
      buyItems[i] = new Weapon(F0_Materials.getMaterialList(kitID - 1)[i],
        ChatColor.GREEN + "" + ChatColor.BOLD + "$" + prices[i], "Click to Buy");
      itemLevel++;
    }
    
    displayItems[3].addLore(w + "+1" + ChatColor.YELLOW + " double Shot");
    items[3].addLore(w + "+1" + ChatColor.YELLOW + " double Shot");
    
    kitMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Shotty Legend");
    buyMenu = Bukkit.createInventory(null, 45,
      ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Buy This Level Shotty");

    for (int i = 0; i < 45; i++) {
      kitMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    kitMenu.setItem(19, displayItems[0].getWeaponItem());
    kitMenu.setItem(21, displayItems[1].getWeaponItem());
    kitMenu.setItem(23, displayItems[2].getWeaponItem());
    kitMenu.setItem(25, displayItems[3].getWeaponItem());

    for (int i = 0; i < 45; i++) {
      buyMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
  }

  public static Weapon[] getItemList() {
    return items;
  }

}