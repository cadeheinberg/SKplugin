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
import me.cade.PluginSK.E1_Fighter;
import me.cade.PluginSK.F_KitManager;
import me.cade.PluginSK.Vars;
import me.cade.PluginSK.Weapon;
import me.cade.PluginSK.cakes.C_CakeSpawner;

public class F7_Wizard {

  private static final int kitID = 7;
  private static final String kitName = ChatColor.AQUA + "Wizard";
  private static final Color armorColor = Color.fromRGB(0, 0, 0);
  private static Weapon[] wandAndPotion;
  private static Weapon[] spells;
  private static Weapon[] buySpells;
  private static Weapon[] buyWand;
  private static Weapon[] displayItems;
  private static Integer[] prices;
  private static ChatColor a;
  private static Inventory kitMenu;
  private static Inventory spellMenu;
  private static Inventory spellBuyMenu;
  private static Inventory buyWandMenu;

  private Player player;
  private int spell;
  private int numSpells;
  private Inventory changeSpellMenu;

  public F7_Wizard(Player player, boolean giveKit) {
    this.player = player;
    spell = 0;

    changeSpellMenu = Bukkit.createInventory(null, 45,
      ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Buy Wizard Wand");

    for (int i = 0; i < 45; i++) {
      changeSpellMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }

    Integer numOfSpells = Vars.getFighter(player).getUnlocked()[6];

    numSpells = numOfSpells;

    if (giveKit) {
      addPlayer(player);
    }
  }

  public static Color getArmorColor() {
    return armorColor;
  }

  public static String getKitName() {
    return kitName;
  }

  public int getSpell() {
    return spell;
  }

  public int getNumSpells() {
    return numSpells;
  }

  public void openChangeSpell() {
    if (numSpells == 3) {
      changeSpellMenu.setItem(25, spells[3].getWeaponItem());
    }
    if (numSpells >= 2) {
      changeSpellMenu.setItem(23, spells[2].getWeaponItem());
    }
    if (numSpells >= 1) {
      changeSpellMenu.setItem(21, spells[1].getWeaponItem());
    }
    if (numSpells >= 0) {
      changeSpellMenu.setItem(19, spells[0].getWeaponItem());
    }
    player.openInventory(changeSpellMenu);
  }

  public Inventory getChangeSpellMenu() {
    return changeSpellMenu;
  }

  private void addPlayer(Player player) {
    PlayerInventory playInv = player.getInventory();
    playInv.addItem(wandAndPotion[0].getWeaponItem());
    F_KitManager.giveArmor(player, armorColor, false);
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
  }

  public static void openKitMenu(Player player) {
    player.openInventory(kitMenu);
  }

  public static boolean giveThisKit(Player player) {
    Integer numKit = Vars.getFighter(player).getUnlocked()[6];
    if (numKit == -1) {
      openBuyWandMenu(player);
      return false;
    }
    return true;
  }

  public static void openSpellMenu(Player player) {
    Integer numKit = Vars.getFighter(player).getUnlocked()[6];
    if (numKit == -1) {
      openBuyWandMenu(player);
      return;
    } else {
      player.openInventory(spellMenu);
      return;
    }
  }

  public static void openBuyWandMenu(Player player) {
    player.openInventory(buyWandMenu);
  }

  public void setNumberOfSpells(int setter) {
    numSpells = setter;
  }

  public static boolean tryToBuyWand(Player player) {
    E1_Fighter pFight = Vars.getFighter(player);
    int money = pFight.getCakes();
    if (money < prices[0]) {
      int owed = prices[0] - money;
      player.closeInventory();
      player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      player.sendMessage(ChatColor.RED + "You need " + ChatColor.WHITE + owed + "" + ChatColor.RED
        + " more " + C_CakeSpawner.getCakeName());
      return false;
    } else {
      pFight.decCakes(prices[0]);
      pFight.incUnlocked(kitID - 1);
      player.closeInventory();
      player.closeInventory();
      player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
      player.sendMessage(ChatColor.AQUA + "Buy some spells now!");
      pFight.incExp(1250);
      return true;
    }
  }

  public static boolean tryToBuySpell(Player player, int index) {
    if (F_KitManager.tryToBuyKit(player, index, prices, kitID)) {
      return true;
    }
    return false;
  }

  public static boolean tryToUseThisSpell(Player player, int index) {
    if (F_KitManager.hasKitManager(player, index, kitID, buySpells, spells, spellBuyMenu)) {
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

  public static Inventory getSpellBuyMenu() {
    return spellBuyMenu;
  }

  public static Inventory getBuyWandMenu() {
    return buyWandMenu;
  }

  public static Inventory getSpellMenu() {
    return spellMenu;
  }

  public void removeWizard() {
    wandAndPotion[0].removeWeapon(player);
  }

  public static void makeWizard() {
    a = ChatColor.DARK_AQUA;
    
    String w = ChatColor.WHITE + "";
    String gr = ChatColor.GREEN + "";
    String cost = ChatColor.YELLOW + " " + C_CakeSpawner.getCakeName() + " (one time purchase)";
    String name = a + "Wizard Wand";
    String attack = ChatColor.YELLOW + " attack damage";
    String special = ChatColor.YELLOW + " spell damage";
    String cool = ChatColor.YELLOW + " cooldown";

    prices = F0_Prices.getPriceList(kitID - 1);
    
    displayItems = new Weapon[4];
    Weapon displayWand = new Weapon(Material.BLAZE_ROD, name, gr + prices[0] + cost, w + F0_Stats.getDamageList(kitID - 1)[0] + attack);

    wandAndPotion = new Weapon[2];
    wandAndPotion[0] =
      new Weapon(Material.BLAZE_ROD, name, w + F0_Stats.getDamageList(kitID - 1)[0] + attack);
    displayItems[0] = new Weapon(Material.BLAZE_ROD, name, w + F0_Stats.getDamageList(kitID - 1)[0] + attack);
    wandAndPotion[1] = new Weapon(Material.SPLASH_POTION, a + "Purchase Spells",
      ChatColor.RED + "Must have wand first");
    wandAndPotion[0].applyWeaponUnsafeEnchantment(Enchantment.KNOCKBACK, 1);

    wandAndPotion[0].addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", 5, AttributeModifier.Operation.ADD_NUMBER));

    buyWand = new Weapon[1];
    buyWand[0] = new Weapon(Material.BLAZE_ROD,
      ChatColor.GREEN + "" + ChatColor.BOLD + "$" + prices[0], "Click to Buy");

    spells = new Weapon[4];
    
    spells[0] = new Weapon(Material.NETHER_WART, a + "Mana Bolt",
      w + F0_Stats.getSpecialDamageList(kitID - 1)[0] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[0] + cool);
    displayItems[0] = new Weapon(Material.NETHER_WART, a + "Mana Bolt", gr + "0" + cost,
      w + F0_Stats.getSpecialDamageList(kitID - 1)[0] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[0] + cool);
    
    spells[1] = new Weapon(Material.FIRE_CHARGE, a + "Fireball Spell",
      w + F0_Stats.getSpecialDamageList(kitID - 1)[1] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[1] + cool);
    displayItems[1] = new Weapon(Material.FIRE_CHARGE, a + "Fireball Spell", gr + prices[1] + cost,
      w + F0_Stats.getSpecialDamageList(kitID - 1)[1] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[1] + cool);
    
    spells[2] = new Weapon(Material.ICE, a + "Ice Cage Spell",
      w + F0_Stats.getSpecialDamageList(kitID - 1)[2] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[2] + cool);
    displayItems[2] = new Weapon(Material.ICE, a + "Ice Cage Spell", gr + prices[2] + cost,
      w + F0_Stats.getSpecialDamageList(kitID - 1)[2] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[2] + cool);
    
    spells[3] = new Weapon(Material.PHANTOM_MEMBRANE, a + "Gust Spell",
      w + F0_Stats.getSpecialDamageList(kitID - 1)[3] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[3] + cool);
    displayItems[3] = new Weapon(Material.PHANTOM_MEMBRANE, a + "Gust Spell", gr + prices[3] + cost,
      w + F0_Stats.getSpecialDamageList(kitID - 1)[3] + special,
      w + F0_Stats.getCooldownList(kitID - 1)[3] + cool);

    buySpells = new Weapon[4];
    buySpells[0] = new Weapon(Material.NETHER_WART,
      ChatColor.GREEN + "" + ChatColor.BOLD + "$" + prices[0], "Click to Buy");
    buySpells[1] = new Weapon(Material.FIRE_CHARGE,
      ChatColor.GREEN + "" + ChatColor.BOLD + "$" + prices[1], "Click to Buy");
    buySpells[2] = new Weapon(Material.ICE, ChatColor.GREEN + "" + ChatColor.BOLD + "$" + prices[2],
      "Click to Buy");
    buySpells[3] = new Weapon(Material.PHANTOM_MEMBRANE,
      ChatColor.GREEN + "" + ChatColor.BOLD + "$" + prices[3], "Click to Buy");

    kitMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Wizard Legend");
    spellBuyMenu = Bukkit.createInventory(null, 45,
      ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Buy This Spell");
    spellMenu =
      Bukkit.createInventory(null, 45, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Spell Shop");
    buyWandMenu = Bukkit.createInventory(null, 45,
      ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Buy Wizard Wand");

    for (int i = 0; i < 45; i++) {
      kitMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    kitMenu.setItem(21, displayWand.getWeaponItem());
    kitMenu.setItem(23, wandAndPotion[1].getWeaponItem());

    for (int i = 0; i < 45; i++) {
      spellMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    spellMenu.setItem(19, displayItems[0].getWeaponItem());
    spellMenu.setItem(21, displayItems[1].getWeaponItem());
    spellMenu.setItem(23, displayItems[2].getWeaponItem());
    spellMenu.setItem(25, displayItems[3].getWeaponItem());

    for (int i = 0; i < 45; i++) {
      spellBuyMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }

    for (int i = 0; i < 45; i++) {
      buyWandMenu.setItem(i, new ItemStack(Material.AIR, 1));
    }
    buyWandMenu.setItem(22, buyWand[0].getWeaponItem());
  }

  public static Weapon[] getItemList() {
    return wandAndPotion;
  }
}
