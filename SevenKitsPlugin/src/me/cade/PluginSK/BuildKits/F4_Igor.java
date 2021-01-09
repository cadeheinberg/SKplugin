package me.cade.PluginSK.BuildKits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.Weapon;

public class F4_Igor {

  private static final int kitID = 4;
  private static final String kitName = ChatColor.RED + "Igor";
  private static final Color armorColor = Color.fromRGB(255, 15, 99);
  private static Weapon[] items;
  private static ChatColor r;

  private Player player;
  
  public F4_Igor(Player player, int kitIndex) {
    this.player = player;
    giveKit(player, kitIndex);
  }

  public void giveKit(Player player, int index) {
    PlayerInventory playInv = player.getInventory();
    playInv.addItem(items[index].getWeaponItem());
    F_KitArmor.giveArmor(player, armorColor);
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
  }

  public static void makeKit() {
    r = ChatColor.RED;

    String name = r + "Igors Trident";
    String dependent = ChatColor.YELLOW + " type";

    String w = ChatColor.WHITE + "";
    String attack = ChatColor.YELLOW + " attack damage";
    String special = ChatColor.YELLOW + " special damage";
    String cool = ChatColor.YELLOW + " cooldown";

    items = new Weapon[4];
    
    for (int i = 0; i < 4; i++) {
      items[i] = new Weapon(F_Materials.getMaterialList(kitID)[i], name,
        w + F_Stats.getDamageList(kitID)[i] + attack,
        w + F_Stats.getSpecialDamageList(kitID)[i] + special,
        w + F_Stats.getStringList(kitID)[i] + dependent,
        w + F_Stats.getCooldownList(kitID)[i] + cool);
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
