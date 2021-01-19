package me.cade.PluginSK.BuildKits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.Weapon;

public class F1_Beserker {

  private static final int kitID = 1;
  private static final String kitName = ChatColor.LIGHT_PURPLE + "Beserker";
  private static final Color armorColor = Color.fromRGB(150, 0, 255);
  private static Weapon item;  
  private Player player;

  public F1_Beserker(Player player, int kitIndex) {
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
    
    String name = ChatColor.LIGHT_PURPLE + "Beserker Axe";
    String lore1 = ChatColor.WHITE + "" + baseDamage + ChatColor.YELLOW + " attack damage";   

    item = new Weapon(F_Materials.getMaterial(kitID), name, lore1);
    
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
