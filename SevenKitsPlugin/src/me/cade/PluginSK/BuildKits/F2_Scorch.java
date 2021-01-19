package me.cade.PluginSK.BuildKits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.Weapon;

public class F2_Scorch {

  private static final int kitID = 2;
  private static final String kitName = ChatColor.GOLD + "Scorch";
  private static final Color armorColor = Color.fromRGB(255, 255, 0);
  private static Weapon item;

  private Player player;
  
  public F2_Scorch(Player player, int kitIndex) {
    this.player = player;
    giveKit(player, kitIndex);
  }

  public void giveKit(Player player, int index) {
    PlayerInventory playInv = player.getInventory();
    playInv.addItem(item.getWeaponItem());
    F_KitArmor.giveArmor(player, armorColor);
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
  }

  public static void makeKit() {

    int baseDamage = F_Stats.getDamageList(kitID)[0];
    Double projectileDamage = F_Stats.getProjectileDamageList(kitID)[0];
    
    String name = ChatColor.YELLOW + "Scorch Shotgun";
    String lore1 = ChatColor.WHITE + "" + baseDamage + ChatColor.YELLOW + " attack damage";  
    String lore2 = ChatColor.WHITE + "" + projectileDamage + ChatColor.YELLOW + " snowball damage";  

    item = new Weapon(F_Materials.getMaterial(kitID), name, lore1, lore2);
    
    item.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", baseDamage,
        AttributeModifier.Operation.ADD_NUMBER));
    
  }
  
  public static Weapon getWeapon() {
    return item;
  }

  public Player getPlayer() {
    return this.player;
  }
  
  public static Color getArmorColor() {
    return armorColor;
  }

  public static String getKitName() {
    return kitName;
  }
  
  public static int getKitID() {
    return kitID;
  }

}
