package me.cade.PluginSK.BuildKits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.Weapon;

public class F0_Noob {

  private static final int kitID = 0;
  private static final String kitName = ChatColor.WHITE + "Noob";
  private static final Color armorColor = Color.fromRGB(255, 255, 255);
  private static Weapon[] items;
  private static ChatColor w= ChatColor.WHITE;
  
  private Player player;
  
  public F0_Noob(Player player, int kitIndex) {
    this.player = player;
    giveKit(player, kitIndex);
  }

  public static void giveKit(Player player, int kitIndex) {
    PlayerInventory playInv = player.getInventory();
    playInv.addItem(items[kitIndex].getWeaponItem());
    F_KitArmor.giveArmor(player, armorColor);
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
  }
  
  public static void makeKit() {
    String name = w + "Noob Sword";
    String dependent = ChatColor.YELLOW + " power";

    String whiteString = w + "";
    String attack = ChatColor.YELLOW + " attack damage";
    String special = ChatColor.YELLOW + " ground pound damage";
    String cool = ChatColor.YELLOW + " cooldown";

    items = new Weapon[4];

    for (int i = 0; i < 4; i++) {
      items[i] = new Weapon(F_Materials.getMaterialList(kitID)[i], name,
        whiteString + F_Stats.getDamageList(kitID)[i] + attack,
        whiteString + F_Stats.getSpecialDamageList(kitID)[i] + special,
        whiteString + F_Stats.getPowerList(kitID)[i] + dependent,
        whiteString + F_Stats.getCooldownList(kitID)[i] + cool);
      items[i].addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
        new AttributeModifier("GENERIC_ATTACK_DAMAGE", F_Stats.getDamageList(kitID)[i],
          AttributeModifier.Operation.ADD_NUMBER));
    }
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

  public static Weapon[] getItemList() {
    return items;
  }

}
