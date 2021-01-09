package me.cade.PluginSK.NPCS;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.BuildKits.*;

public class D_SpawnKitSelectors {
  
  private static D1_ArmorStand[] kits;
  static String[] kitNames;

  private static ChatColor y;
  private static ChatColor b;
  
  public static void removeAllNpcs() {
    for( Entity e : Main.hub.getEntities()) {
      if(e instanceof Player) {
        continue;
      }else {
        e.remove();
      }
    }
  }
  
  @SuppressWarnings("deprecation")
  public static void spawnKitSelectors() {
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
    itemsHeld[0] = F0_Noob.getItemList()[2].getWeaponItem();
    itemsHeld[1] = F1_Booster.getItemList()[2].getWeaponItem();
    itemsHeld[2] = F2_Shotty.getItemList()[2].getWeaponItem();
    itemsHeld[3] = F3_Goblin.getItemList()[2].getWeaponItem();
    itemsHeld[4] = F4_Igor.getItemList()[2].getWeaponItem();
    itemsHeld[5] = F5_Heavy.getItemList()[2].getWeaponItem();
    itemsHeld[6] = F6_Zero.getItemList()[0].getWeaponItem();
    
    Color[] colors = new Color[7];
    colors[0] = F0_Noob.getArmorColor();
    colors[1] = F1_Booster.getArmorColor();
    colors[2] = F2_Shotty.getArmorColor();
    colors[3] = F3_Goblin.getArmorColor();
    colors[4] = F4_Igor.getArmorColor();
    colors[5] = F5_Heavy.getArmorColor();
    colors[6] = F6_Zero.getArmorColor();
    
    double x = -1046.5;
    for(int i = 0; i < 7; i++) {
      Location locale = new Location(Main.hub, x, 196, -106.5);
      kits[i] = new D1_ArmorStand(kitNames[i] , locale, 180, true, false);
      kits[i].equipColoredArmor(colors[i]);
      kits[i].getStand().setItemInHand(itemsHeld[i]);
      x = x - 2.0;
    }
  }

}
