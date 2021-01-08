package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class D3_Menus {

  private static Inventory streakMenu;
  private static Inventory armorMenu;
  private static Inventory agilityMenu;
  private static Inventory levelMenu;
  private static Inventory specialItemMenu;
  
  private static Inventory dailyRewardMenu;
  
  private static Inventory cosmeticMenu;
  private static Inventory changeArmorColorMenu;
  private static Color[] armorChangeColors;

  public static void makeShopMenus() {
    // Killstreak
    streakMenu = Bukkit.createInventory(null, 45,
      ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Select a Killstreak");
    Weapon k = new Weapon(Material.RED_STAINED_GLASS_PANE, ChatColor.YELLOW + "Turn Off",
      "Turns off killstreaks");
    Weapon k1 =
      new Weapon(Material.RABBIT_HIDE, ChatColor.YELLOW + "(2) Super Jump", "Level 5 required");
    Weapon k2 = new Weapon(Material.NETHER_QUARTZ_ORE, ChatColor.YELLOW + "(4) Harder Punches",
      "Level 8 required");
    Weapon k3 = new Weapon(Material.TNT, ChatColor.YELLOW + "(8) Juggernaut", "Level 12 required");
    for (int i = 0; i < 45; i++) {
      streakMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    streakMenu.setItem(19, k.getWeaponItem());
    streakMenu.setItem(21, k1.getWeaponItem());
    streakMenu.setItem(23, k2.getWeaponItem());
    streakMenu.setItem(25, k3.getWeaponItem());
    // Armor
    armorMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Armor Legend");
    Weapon armor1 = new Weapon(Material.RED_STAINED_GLASS_PANE, ChatColor.YELLOW + "Turn Off",
      "Turns off armor buff");
    Weapon armor2 =
      new Weapon(Material.GOLDEN_CHESTPLATE, ChatColor.YELLOW + "Protection I", "Level 8 required");
    Weapon armor3 =
      new Weapon(Material.IRON_CHESTPLATE, ChatColor.YELLOW + "Protection II", "Level 15 required");
    Weapon armor4 = new Weapon(Material.DIAMOND_CHESTPLATE, ChatColor.YELLOW + "Protection III",
      "Level 22 required");
    for (int i = 0; i < 45; i++) {
      armorMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    armorMenu.setItem(19, armor1.getWeaponItem());
    armorMenu.setItem(21, armor2.getWeaponItem());
    armorMenu.setItem(23, armor3.getWeaponItem());
    armorMenu.setItem(25, armor4.getWeaponItem());
    // Level
    levelMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Leveling Legend");
    Weapon l = new Weapon(Material.RED_STAINED_GLASS_PANE, ChatColor.YELLOW + "Get XP info",
      "Click for info");
    Weapon l1 =
      new Weapon(Material.GOLDEN_CHESTPLATE, ChatColor.YELLOW + "Prestige Up", "Level 28 required");
    for (int i = 0; i < 45; i++) {
      levelMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    levelMenu.setItem(21, l.getWeaponItem());
    levelMenu.setItem(23, l1.getWeaponItem());
    // Agility
    agilityMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Skill Legend");
    Weapon skill1 = new Weapon(Material.RED_STAINED_GLASS_PANE, ChatColor.YELLOW + "Turn Off",
      "Turns off skill buff");
    Weapon skill2 =
      new Weapon(Material.FEATHER, ChatColor.YELLOW + "Speed Boost I", "Level 6 required");
    Weapon skill3 =
      new Weapon(Material.FEATHER, ChatColor.YELLOW + "Speed Boost II", "Level 11 required");
    Weapon skill4 =
      new Weapon(Material.FEATHER, ChatColor.YELLOW + "Speed Boost III", "Level 17 required");
    for (int i = 0; i < 45; i++) {
      agilityMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    agilityMenu.setItem(19, skill1.getWeaponItem());
    agilityMenu.setItem(21, skill2.getWeaponItem());
    agilityMenu.setItem(23, skill3.getWeaponItem());
    agilityMenu.setItem(25, skill4.getWeaponItem());
    // Item Shop
    specialItemMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Item Legend");
    Weapon item1 = new Weapon(Material.RED_STAINED_GLASS_PANE, ChatColor.YELLOW + "Turn off",
      "Turns off item add-on");
    Weapon item2 = new Weapon(Material.COAL, ChatColor.YELLOW + "Grenade", ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "200",
      "Prestige 1, Level 10 required");
    Weapon item3 = new Weapon(Material.SHEARS, ChatColor.YELLOW + "Pearl Gun", ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "500",
      "Prestige 2, Level 1 required");
    Weapon item4 = new Weapon(Material.STICK, ChatColor.YELLOW + "Stick of Truth", ChatColor.GREEN + "Cost: " + ChatColor.WHITE + "2000",
      "Prestige 3, Level 1 required");
    for (int i = 0; i < 45; i++) {
      specialItemMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    specialItemMenu.setItem(19, item1.getWeaponItem());
    specialItemMenu.setItem(21, item2.getWeaponItem());
    specialItemMenu.setItem(23, item3.getWeaponItem());
    specialItemMenu.setItem(25, item4.getWeaponItem());
  }

//  public static void makeDailyRewardMenu() {
//    dailyRewardMenu =
//      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Daily Rewards");
//    Weapon k = new Weapon(Material.COOKIE, ChatColor.YELLOW + "Daily Cookies",
//      ChatColor.GREEN + "Reward: " + ChatColor.WHITE + "100 Cookies");
//    Weapon k1 = new Weapon(Material.MOJANG_BANNER_PATTERN, ChatColor.YELLOW + "Vote For Server",
//      ChatColor.GREEN + "Reward: " + ChatColor.WHITE + "100 Cookies");
//    Weapon k2 = new Weapon(Material.CHEST, ChatColor.YELLOW + "Basic Chest", "Common reward");
//    Weapon k3 =
//      new Weapon(Material.ENDER_CHEST, ChatColor.YELLOW + "Legendary Chest", "Rare reward");
//    for (int i = 0; i < 45; i++) {
//      dailyRewardMenu.setItem(i, new ItemStack(Material.AIR, 1));
//    }
//    dailyRewardMenu.setItem(19, k.getWeaponItem());
//    dailyRewardMenu.setItem(21, k1.getWeaponItem());
//    dailyRewardMenu.setItem(23, k2.getWeaponItem());
//    dailyRewardMenu.setItem(25, k3.getWeaponItem());
//  }
  
  public static void makeCosmeticMenu() {
    cosmeticMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Cosmetics Menu");
    Weapon k = new Weapon(Material.LEATHER_CHESTPLATE, ChatColor.YELLOW + "Custom Armor",
      "Click to change");
    for (int i = 0; i < 45; i++) {
      cosmeticMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    cosmeticMenu.setItem(19,k.getWeaponItem());
    
    armorChangeColors = new Color[45];
    changeArmorColorMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Armor Change");
    int r = 255;
    int g = 0;
    int b = 0;
    for(int i = 0; i < 45; i++) {
      armorChangeColors[i] = Color.fromRGB(r, g, b);
      if(i < 7) {
        b = b + 36;
      }else if(i < 14) {
        r = r - 36;
      }else if(i < 21) {
        g = g + 36;
      }else if(i < 28) {
        b = b - 36;
      }else if(i < 35) {
        r = r + 36;
      }else if(i < 41) {
        g = g - 36;
      }else if(i == 41){
        r = 255;
        g = 255;
        b = 255;
      }else if(i == 42){
        r = 0;
        g = 0;
        b = 0;
      }
      ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
      LeatherArmorMeta lch = (LeatherArmorMeta) lchest.getItemMeta();
      lch.setColor(armorChangeColors[i]);
      lchest.setItemMeta(lch);
      changeArmorColorMenu.setItem(i, lchest);
    }
    Weapon off = new Weapon(Material.RED_STAINED_GLASS_PANE, ChatColor.YELLOW + "Turn Off",
      "Click to change");
    changeArmorColorMenu.setItem(44,off.getWeaponItem());
  }
  
  public static Color getArmorColor(int index) {
    return armorChangeColors[index];
  }
  
  public static Inventory getCosmeticMenu() {
    return cosmeticMenu;
  }
  
  public static Inventory getChangeArmorColorMenu() {
    return changeArmorColorMenu;
  }

  public static Inventory getDailyRewardMenu() {
    return dailyRewardMenu;
  }

  public static Inventory getStreakMenu() {
    return streakMenu;
  }

  public static Inventory getArmorMenu() {
    return armorMenu;
  }


  public static Inventory getLevelMenu() {
    return levelMenu;
  }

  public static Inventory getAgilityMenu() {
    return agilityMenu;
  }


  public static Inventory getSpecialItemMenu() {
    return specialItemMenu;
  }

}
