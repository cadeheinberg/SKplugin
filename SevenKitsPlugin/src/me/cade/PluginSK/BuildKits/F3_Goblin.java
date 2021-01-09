package me.cade.PluginSK.BuildKits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.Weapon;

public class F3_Goblin {

  private static final int kitID = 3;
  private static final String kitName = ChatColor.GREEN + "Goblin";
  private static final Color armorColor = Color.fromRGB(77, 255, 0);
  private static Weapon[] items;
  private static Weapon goblinSword;
  private static ChatColor n;

  private Player player;
  
  public F3_Goblin(Player player, int kitIndex) {
    this.player = player;
    giveKit(player, kitIndex);
  }

  public void giveKit(Player player, int index) {
    PlayerInventory playInv = player.getInventory();
    playInv.addItem(items[index].getWeaponItem());
    playInv.addItem(goblinSword.getWeaponItem());
    playInv.addItem(new ItemStack(Material.ARROW, 1));
    F_KitArmor.giveArmor(player, armorColor);
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
  }

  public static void makeKit() {
    n = ChatColor.GREEN;

    String name = n + "Goblin Bow";
    String dependent = ChatColor.YELLOW + " arrows";

    String w = ChatColor.WHITE + "";
    String attack = ChatColor.YELLOW + " attack damage";
    String special = ChatColor.YELLOW + " arrow damage";

    items = new Weapon[4];
    
    for (int i = 0; i < 4; i++) {
      items[i] = new Weapon(F_Materials.getMaterialList(kitID)[i], name,
        w + F_Stats.getDamageList(kitID)[i] + attack,
        w + F_Stats.getSpecialDamageList(kitID)[i] + special,
        w + F_Stats.getIntList(kitID)[i] + dependent);
      items[i].getWeaponItem().addEnchantment(Enchantment.ARROW_INFINITE, 1);
    }

    goblinSword = new Weapon(Material.WOODEN_SWORD, n + "Goblin Sword",
      F_Stats.getDamageList(kitID)[0] + " damage", "Fire Aspect");
    goblinSword.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", F_Stats.getDamageList(kitID)[0],
        AttributeModifier.Operation.ADD_NUMBER));
    goblinSword.applyWeaponUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
    
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
