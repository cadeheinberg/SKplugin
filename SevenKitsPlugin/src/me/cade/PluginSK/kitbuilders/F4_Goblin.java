package me.cade.PluginSK.kitbuilders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.F_KitManager;
import me.cade.PluginSK.Weapon;
import me.cade.PluginSK.cakes.C_CakeSpawner;

public class F4_Goblin {

  private static final int kitID = 4;
  private static final String kitName = ChatColor.GREEN + "Goblin";
  private static final Color armorColor = Color.fromRGB(77, 255, 0);
  private static Weapon[] items;
  private static Weapon[] buyItems;
  private static Weapon[] displayItems;
  private static Material[] displays;
  private static Integer[] prices;
  private static Weapon goblinSword;
  private static ChatColor n;
  private static Inventory kitMenu;
  private static Inventory buyMenu;

  private Player player;
  private int index;

  public F4_Goblin(Player player, int index, boolean giveKit) {
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
    playInv.addItem(goblinSword.getWeaponItem());
    playInv.addItem(new ItemStack(Material.ARROW, 1));
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

  public static Material[] getDisplays() {
    return displays;
  }

  public static Inventory getKitMenu() {
    return kitMenu;
  }

  public static Inventory getBuyMenu() {
    return buyMenu;
  }

  public void removeGoblin() {
    items[index].removeWeapon(player);
    goblinSword.removeWeapon(player);
    player.getInventory().remove(Material.ARROW);
  }

  public static void makeGoblin() {
    n = ChatColor.GREEN;

    String name = n + "Goblin Bow";
    String dependent = ChatColor.YELLOW + " arrows";

    String w = ChatColor.WHITE + "";
    String gr = ChatColor.GREEN + "";
    String cost = ChatColor.YELLOW + " " + C_CakeSpawner.getCakeName() + " cookies (one time purchase)";
    String attack = ChatColor.YELLOW + " attack damage";
    String special = ChatColor.YELLOW + " arrow damage";

    prices = F0_Prices.getPriceList(kitID - 1);
    buyItems = new Weapon[4];
    items = new Weapon[4];

    displayItems = new Weapon[4];
    displays = new Material[] {Material.BOW, Material.BEETROOT_SEEDS, Material.MELON_SEEDS,
      Material.WHEAT_SEEDS};

    int itemLevel = 1;
    for (int i = 0; i < 4; i++) {
      displayItems[i] = new Weapon(displays[i], name + " (Level: " + itemLevel + ")", gr + prices[i] + cost,
        w + F0_Stats.getDamageList(kitID - 1)[i] + attack,
        w + F0_Stats.getSpecialDamageList(kitID - 1)[i] + special,
        w + F0_Stats.getIntList(kitID - 1)[i] + dependent);
      items[i] = new Weapon(F0_Materials.getMaterialList(kitID - 1)[i], name,
        w + F0_Stats.getDamageList(kitID - 1)[i] + attack,
        w + F0_Stats.getSpecialDamageList(kitID - 1)[i] + special,
        w + F0_Stats.getIntList(kitID - 1)[i] + dependent);
      items[i].getWeaponItem().addEnchantment(Enchantment.ARROW_INFINITE, 1);
      buyItems[i] = new Weapon(F0_Materials.getMaterialList(kitID - 1)[i],
        ChatColor.GREEN + "" + ChatColor.BOLD + "$" + prices[i], "Click to Buy");
      itemLevel++;
    }

    goblinSword = new Weapon(Material.WOODEN_SWORD, n + "Goblin Sword",
      F0_Stats.getDamageList(kitID - 1)[0] + " damage", "Fire Aspect");
    goblinSword.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", F0_Stats.getDamageList(kitID - 1)[0],
        AttributeModifier.Operation.ADD_NUMBER));
    goblinSword.applyWeaponUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);

    kitMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Goblin Legend");
    buyMenu = Bukkit.createInventory(null, 45,
      ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Buy This Level Dart Bow");

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

  public static Weapon[] getDisplayItems() {
    return displayItems;
  }


}
