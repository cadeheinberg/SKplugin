package me.cade.PluginSK.NPCS;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import me.cade.PluginSK.Main;

public class D_SpawnGameSelectors {
  
  private static ChatColor a = ChatColor.AQUA;;
  private static ChatColor b = ChatColor.BOLD;
  
  public static String[] names;
  
  public static void spawnGameSelectors() {
    String p = a + "" + b + "";
    names = new String[4];
    Location[] locations = new Location[4];
    
    names[0] = p + "Search & Destroy";
    locations[0] = new Location(Main.hub, -1039.5, 195, -112.5);

    names[1] = p + "Team Deathmatch";
    locations[1] = new Location(Main.hub, -1034.5, 195, -113.5);
    
    names[2] = p + "Capture The Flag";
    locations[2] = new Location(Main.hub, -1034.5, 195, -119.5);
    
    names[3] = p + "Ladder Match";
    locations[3] = new Location(Main.hub, -1038.5, 195, -122.5);

    //diamond search and destroy
    //-1039.5, 195, -112.5 facing 180
    D1_ArmorStand killStreak = new D1_ArmorStand(names[0], locations[0], 180, false, false);
    killStreak.equipDiamondArmor();
    
    //iron team death match
    //-1034.5, 195, -113.5 facing 135
    D1_ArmorStand armorUpgrade = new D1_ArmorStand(names[1], locations[1], 135, false, false);
    armorUpgrade.equipIronArmor();;
    
    //gold capture the flag
    //-1034.5, 195, -119.5 facing 90
    D1_ArmorStand levelManager = new D1_ArmorStand(names[2], locations[2], 90, false, false);
    levelManager.equipGoldArmor();
    
    //chainmail ladder match
    //-1038.5, 195, -122.5 facing 0
    D1_ArmorStand agilityUpgrade = new D1_ArmorStand(names[3], locations[3], 0, false, false);
    agilityUpgrade.equipChainArmor();
  }

}
