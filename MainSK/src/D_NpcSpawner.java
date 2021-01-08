package me.cadeheinberg.SevenKitsPlugin;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class D_NpcSpawner {
  
  private static D1_ArmorStand[] kits;
  static String[] kitNames;

  static String[] shopNames;
  
  static String builderName;
  static String bountyName;
  static String dailyName;
  static String cosmeticName;
  static String webStoreName;
  static String goToMineName;
  static String joinSoccer;
  static String patchyParrot;
  static String sellCakes;
  
  private static UUID iceCake;
  private static UUID sandCake;
  private static UUID grassCake;
  
  private static ChatColor y;
  private static ChatColor a;
  private static ChatColor b;
  
  public static void spawnAllNpcs() {
    createKitSelectors();
    makeShopSelectors();
    makeInvisibleStands();
    makeBuilder();
    makeBounty();
    makeDailyReward();
    makeCosmetics();
    makeWebStore();
    makeGoToMine();
    makeGoToMine2();
    makeJoinSoccer();
    makeSandboxBook();
    makeCookieBook();
    makeShopBook();
    makeMiniBook();
    makeStartedBook();
    makeHighestKill();
    makeHighestKillstreak();
    makeBestRatio();
    makeVIPBook();
    makeCosmeticsBook();
    makeParrot();
    makeIceCake("1.0");
    makeSandCake("1.0");
    makeGrassCake("1.0");
    makeCakeSeller();
    makeWhatAreCommands();
    makeWhatAreCommands2();
  }
  
  public static void removeAllNpcs() {
    for( Entity e : A_Main.kitpvp.getEntities()) {
      if(e instanceof Player) {
        continue;
      }else {
        e.remove();
      }
    }
  }
  
  @SuppressWarnings("deprecation")
  public static void createKitSelectors() {
    y = ChatColor.YELLOW;
    b = ChatColor.BOLD;
    String p = y + "" + b + "";
    kits = new D1_ArmorStand[7];
    kitNames = new String[7];
    kitNames[0] = p + "  Noob  ";
    kitNames[1] = p + " Booster ";
    kitNames[2] = p + " Shotty ";
    kitNames[3] = p + " Goblin ";
    kitNames[4] = p + "  Igor  ";
    kitNames[5] = p + "  Heavy  ";
    kitNames[6] = p + " Wizard ";
    
    ItemStack[] itemsHeld = new ItemStack[7];
    itemsHeld[0] = F1_Noob.getItemList()[2].getWeaponItem();
    itemsHeld[1] = F2_Booster.getItemList()[2].getWeaponItem();
    itemsHeld[2] = F3_Shotty.getItemList()[2].getWeaponItem();
    itemsHeld[3] = F4_Goblin.getItemList()[2].getWeaponItem();
    itemsHeld[4] = F5_Igor.getItemList()[2].getWeaponItem();
    itemsHeld[5] = F6_Heavy.getItemList()[2].getWeaponItem();
    itemsHeld[6] = F7_Wizard.getItemList()[0].getWeaponItem();
    
    Color[] colors = new Color[7];
    colors[0] = F1_Noob.getArmorColor();
    colors[1] = F2_Booster.getArmorColor();
    colors[2] = F3_Shotty.getArmorColor();
    colors[3] = F4_Goblin.getArmorColor();
    colors[4] = F5_Igor.getArmorColor();
    colors[5] = F6_Heavy.getArmorColor();
    colors[6] = F7_Wizard.getArmorColor();
    
    double x = -1046.5;
    for(int i = 0; i < 7; i++) {
      Location locale = new Location(A_Main.kitpvp, x, 196, -106.5);
      kits[i] = new D1_ArmorStand(kitNames[i] , locale, 180, true, false);
      kits[i].equipColoredArmor(colors[i]);
      kits[i].getStand().setItemInHand(itemsHeld[i]);
      x = x - 2.0;
    }
  }
  
  public static void makeShopSelectors() {
    a = ChatColor.AQUA;
    String p = a + "" + b + "";
    shopNames = new String[5];
    shopNames[0] = p + "Killstreak Selector";
    shopNames[1] = p + "Armor Upgrade";
    shopNames[2] = p + "Level Manager";
    shopNames[3] = p + "Agility Upgrade";
    shopNames[4] = p + "Item Shop";
    //Killstreak
    Location kill = new Location(A_Main.kitpvp, -1066.5, 194.5, -110.5);
    D1_ArmorStand killStreak = new D1_ArmorStand(shopNames[0], kill, 180, false, false);
    killStreak.equipChainArmor();
    //Armor
    Location armor = new Location(A_Main.kitpvp, -1069.5, 194.5, -112.5);
    D1_ArmorStand armorUpgrade = new D1_ArmorStand(shopNames[1], armor, -115, false, false);
    armorUpgrade.equipDiamondArmor();
    //Level
    Location level = new Location(A_Main.kitpvp, -1071.5, 194.5, -115.5);
    D1_ArmorStand levelManager = new D1_ArmorStand(shopNames[2], level, -90, false, false);
    levelManager.equipIronArmor();
    //Agility
    Location agility = new Location(A_Main.kitpvp, -1069.5, 194.5, -118.5);
    D1_ArmorStand agilityUpgrade = new D1_ArmorStand(shopNames[3], agility, -60, false, false);
    agilityUpgrade.equipGoldArmor();
    //Item Shop
    Location item = new Location(A_Main.kitpvp, -1065.5, 194.5, -121.5);
    D2_LivingEntity itemShop = new D2_LivingEntity(EntityType.VILLAGER, shopNames[4], item, 0);
    itemShop.getName();
  }
  
  public static void makeInvisibleStands() {
    Location freeNoob = new Location(A_Main.kitpvp, -1046.5, 197.30, -106.4);
    D1_ArmorStand freeNoobStand = new D1_ArmorStand(ChatColor.GREEN + "FREE", freeNoob, 180, false, true);
    freeNoobStand.getName();
    
    Location freeBooster = new Location(A_Main.kitpvp, -1048.5, 197.30, -106.4);
    D1_ArmorStand freeBoosterStand = new D1_ArmorStand(ChatColor.GREEN + "FREE", freeBooster, 180, false, true);
    freeBoosterStand.getName();
    
    Location vipBuilder = new Location(A_Main.kitpvp, -1060.5, 198.1, -126.5);
    D1_ArmorStand vipBuilderStand = new D1_ArmorStand(ChatColor.AQUA + "VIP", vipBuilder, -108, false, true);
    vipBuilderStand.getName();
    
    Location vipBounty = new Location(A_Main.kitpvp, -1039.5, 196.8, -110.5);
    D1_ArmorStand vipBountyStand = new D1_ArmorStand(ChatColor.AQUA + "VIP", vipBounty, 180, false, true);
    vipBountyStand.getName();
    
    Location vipCosmetics = new Location(A_Main.kitpvp, -1059.6, 197.80, -121.4);
    D1_ArmorStand vipCosmeticsStand = new D1_ArmorStand(ChatColor.AQUA + "VIP", vipCosmetics, -130, false, true);
    vipCosmeticsStand.getName();
  }
  
  public static void makeBuilder() {
    //Item Shop
    builderName = ChatColor.RED + "" + ChatColor.BOLD + "Sandbox Mode";
    Location builderMode = new Location(A_Main.kitpvp, -1060.5, 196.5, -126.5);
    D2_LivingEntity builderModeSheep = new D2_LivingEntity(EntityType.SHEEP, builderName, builderMode, -108);
    ((Sheep) builderModeSheep.getEntity()).setColor(DyeColor.RED);
  }
  
  public static void makeBounty() {
    //Item Shop
    bountyName = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Set a Bounty";
    Location bountySet = new Location(A_Main.kitpvp, -1039.5, 194.5, -110.5);
    D2_LivingEntity bountySetter = new D2_LivingEntity(EntityType.SKELETON, bountyName, bountySet, 180);
    bountySetter.getName();
  }
  
  public static void makeDailyReward() {
    //Item Shop
    dailyName = ChatColor.AQUA + "" + ChatColor.BOLD + "Voting";
    Location dailySet = new Location(A_Main.kitpvp, -1046.5, 196.5, -126.5);
    D2_LivingEntity dailyReward = new D2_LivingEntity(EntityType.CREEPER, dailyName, dailySet, 122);
    dailyReward.getName();
  }
  
  public static void makeCosmetics() {
    //Item Shop
    cosmeticName = ChatColor.AQUA + "" + ChatColor.BOLD + "Cosmetics";
    Location cosmeticSet = new Location(A_Main.kitpvp, -1059.5, 195.5, -121.5);
    D1_ArmorStand cosmeticChange = new D1_ArmorStand(ChatColor.GREEN + cosmeticName, cosmeticSet, -135, false, false);
    cosmeticChange.equipColoredArmor(Color.fromRGB(255, 170, 23));
  }
  
  public static void makeWebStore() {
    //Purchase ranks or make donations
    webStoreName = ChatColor.AQUA + "" + ChatColor.BOLD + "Web Store";
    Location webStoreSet = new Location(A_Main.kitpvp, -1046.5, 195.5, -120.5);
    D2_LivingEntity webStore = new D2_LivingEntity(EntityType.WITHER_SKELETON, webStoreName, webStoreSet, 135);
    webStore.getName();
  }
  
  @SuppressWarnings("deprecation")
  public static void makeGoToMine() {
    goToMineName = ChatColor.RED + "" + ChatColor.BOLD + "Go To Survival";
    Location loc = new Location(A_Main.kitpvp, -1057.5, 196.5, -129.5);
    D2_LivingEntity ent = new D2_LivingEntity(EntityType.ZOMBIE, goToMineName, loc, -7);
    Zombie v = (Zombie) ent.getEntity();
    v.setBaby(false);
    v.getEquipment().setItemInHand(new ItemStack(Material.STONE_PICKAXE, 1));
    ent.getName();
  }
  
  @SuppressWarnings("deprecation")
  public static void makeGoToMine2() {
    Location loc = new Location(A_Main.kitpvp, -1034.5, 194.5, -117.5);
    D2_LivingEntity ent = new D2_LivingEntity(EntityType.ZOMBIE, goToMineName, loc, 90);
    Zombie v = (Zombie) ent.getEntity();
    v.setBaby(false);
    v.getEquipment().setItemInHand(new ItemStack(Material.STONE_PICKAXE, 1));
    ent.getName();
  }
  
  public static void makeJoinSoccer() {
    joinSoccer = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Join Soccer";
    Location loc = new Location(A_Main.kitpvp, -1034.5, 194.5, -111.5);
    D2_LivingEntity ent = new D2_LivingEntity(EntityType.PANDA, joinSoccer, loc, 110);
    ((Panda) ent.getEntity()).setAdult();
  }
  
  public static void makeParrot() {
    patchyParrot = ChatColor.YELLOW + "" + ChatColor.BOLD + "Bork";
    Location loc = new Location(A_Main.kitpvp, -1052.5, 207.0, -131.5);
    D2_LivingEntity ent = new D2_LivingEntity(EntityType.WOLF, patchyParrot, loc, 0);
    ((Wolf) ent.getEntity()).setBaby();
    ((Wolf) ent.getEntity()).setSitting(true);
  }
  
  public static void makeSandboxBook() {
    String name = ChatColor.WHITE + "Info";
    Location local = new Location(A_Main.kitpvp, -1058.5, 196.1, -129.625);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeVIPBook() {
    String name = ChatColor.WHITE + "VIP Perks";
    Location local = new Location(A_Main.kitpvp, -1045.375, 195.1, -122.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 90, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeCosmeticsBook() {
    String name = ChatColor.WHITE + "Info";
    Location local = new Location(A_Main.kitpvp, -1060.625, 195.1, -123.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, -90, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeCookieBook() {
    String name = ChatColor.WHITE + "Get Money";
    Location local = new Location(A_Main.kitpvp, -1055.625, 197.1, -130.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, -90, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeStartedBook() {
    String name = ChatColor.WHITE + "Get Started";
    Location local = new Location(A_Main.kitpvp, -1049.375, 197.1, -130.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 90, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeMiniBook() {
    String name = ChatColor.WHITE + "Info";
    Location local = new Location(A_Main.kitpvp, -1034.375, 194.1, -114.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 90, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeShopBook() {
    String name = ChatColor.WHITE + "Info";
    Location local = new Location(A_Main.kitpvp, -1063.5, 194.1, -117.625);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeHighestKill() {
    String name = ChatColor.GREEN + "" + ChatColor.BOLD + "Most Kills";
    Location local = new Location(A_Main.kitpvp, -1052.5, 198, -142.9);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeBestRatio() {
    String name = ChatColor.GREEN + "" + ChatColor.BOLD + "Best KD";
    Location local = new Location(A_Main.kitpvp, -1057.9, 198, -138.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, -90, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeHighestKillstreak() {
    String name = ChatColor.GREEN + "" + ChatColor.BOLD + "Best Killstreak";
    Location local = new Location(A_Main.kitpvp, -1047.1, 198, -138.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    stand.getStand().setSmall(true);
    stand.getName();
  }
  
  public static void makeIceCake(String time) {
    String name = ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + time + " min";
    Location local = new Location(A_Main.kitpvp, -1014.5, 65, -143.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    iceCake = stand.getStand().getUniqueId();
  }
  
  public static void makeSandCake(String time) {
    String name = ChatColor.GREEN + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + time + " min";
    Location local = new Location(A_Main.kitpvp, -1057.5, 65, -101.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    sandCake = stand.getStand().getUniqueId();
  }
  
  public static void makeGrassCake(String time) {
    String name = ChatColor.YELLOW + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + time +  " min";
    Location local = new Location(A_Main.kitpvp, -1092.5, 65, -93.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    grassCake = stand.getStand().getUniqueId();
  }
  
  public static void updateCakeTimes(double time){
    String toString = Double.toString(time).substring(0, 3);
    if(Bukkit.getEntity(iceCake) == null){
      makeIceCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
    }
    ((ArmorStand) Bukkit.getEntity(iceCake)).setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
    if(Bukkit.getEntity(sandCake) == null){
      makeSandCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
    }
    ((ArmorStand) Bukkit.getEntity(sandCake)).setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
    if(Bukkit.getEntity(grassCake) == null){
      makeGrassCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
    }
    ((ArmorStand) Bukkit.getEntity(grassCake)).setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + toString + " min");
  }
  
  public static void makeCakeSeller() {
    sellCakes = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Sell Cakes";
    Location loc = new Location(A_Main.kitpvp, -1058.5, 194.5, -118.5);
    D2_LivingEntity ent = new D2_LivingEntity(EntityType.VILLAGER, sellCakes, loc, -20);
    ent.getName();
  }
  
  public static void makeWhatAreCommands() {
    String name = ChatColor.GOLD + "" + ChatColor.BOLD + "Questions? Type Command: ";
    Location local = new Location(A_Main.kitpvp, -1052.5, 197.5, -138.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    stand.getStand().setGravity(false);
    stand.getName();
  }
  
  public static void makeWhatAreCommands2() {
    String name = ChatColor.YELLOW + "/whataremycommands";
    Location local = new Location(A_Main.kitpvp, -1052.5, 197.25, -138.5);
    D1_ArmorStand stand = new D1_ArmorStand(name, local, 0, false, false);
    stand.getStand().setGravity(false);
    stand.getName();
  }
  


}
