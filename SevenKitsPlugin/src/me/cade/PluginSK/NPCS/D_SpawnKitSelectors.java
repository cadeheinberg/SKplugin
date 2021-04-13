package me.cade.PluginSK.NPCS;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.BuildKits.*;

public class D_SpawnKitSelectors {
  
  private static D1_ArmorStand[] kits;
  static String[] kitNames;

  private static ChatColor y;
  private static ChatColor b;
  
  @SuppressWarnings("deprecation")
  public static void spawnKitSelectors() {
    y = ChatColor.YELLOW;
    b = ChatColor.BOLD;
    String p = y + "" + b + "";
    kits = new D1_ArmorStand[7];
    kitNames = new String[7];
    kitNames[0] = p + "  " + F0_Noob.getKitName() + "  ";
    kitNames[1] = p + "  " + F1_Beserker.getKitName() + "  ";
    kitNames[2] = p + "  " + F2_Scorch.getKitName() + "  ";
    kitNames[3] = p + "  " + F3_Goblin.getKitName() + "  ";
    kitNames[4] = p + "  " + F4_Igor.getKitName() + "  ";
    kitNames[5] = p + "  " + F5_Wizard.getKitName() + "  ";
    kitNames[6] = p + "  " + F6_Grief.getKitName() + "  ";
    
    ItemStack[] itemsHeld = new ItemStack[7];
    itemsHeld[0] = F0_Noob.getWeapon().getWeaponItem();
    itemsHeld[1] = F1_Beserker.getWeapon().getWeaponItem();
    itemsHeld[2] = F2_Scorch.getWeapon().getWeaponItem();
    itemsHeld[3] = F3_Goblin.getWeapon().getWeaponItem();
    itemsHeld[4] = F4_Igor.getWeapon().getWeaponItem();
    itemsHeld[5] = F5_Wizard.getWeapon().getWeaponItem();
    itemsHeld[6] = F6_Grief.getWeapon().getWeaponItem();
    
    Color[] colors = new Color[7];
    colors[0] = F0_Noob.getArmorColor();
    colors[1] = F1_Beserker.getArmorColor();
    colors[2] = F2_Scorch.getArmorColor();
    colors[3] = F3_Goblin.getArmorColor();
    colors[4] = F4_Igor.getArmorColor();
    colors[5] = F5_Wizard.getArmorColor();
    colors[6] = F6_Grief.getArmorColor();
    
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
