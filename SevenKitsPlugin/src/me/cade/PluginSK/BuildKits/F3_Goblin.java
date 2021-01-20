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
  private static final String kitName = "Goblin";
  private static final Color armorColor = Color.fromRGB(77, 255, 0);
  private static Weapon item;
  private static Weapon goblinSword;
  private static ChatColor kitChatColor = ChatColor.GREEN;
  
  private Player player;
  
  public F3_Goblin(Player player, int kitIndex) {
    this.player = player;
    giveKit(player, kitIndex);
  }

  public void giveKit(Player player, int index) {
    PlayerInventory playInv = player.getInventory();
    playInv.addItem(item.getWeaponItem());
    playInv.addItem(goblinSword.getWeaponItem());
    playInv.addItem(new ItemStack(Material.ARROW, 1));
    F_KitArmor.giveArmor(player, armorColor);
    player.closeInventory();
    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
  }
  
  public static void makeKit() {

    Double projectileDamage = F_Stats.getProjectileDamageList(kitID)[0];
    
    String name = ChatColor.GREEN + "Goblin Bow";
    String lore1 = ChatColor.WHITE + "infinite" + ChatColor.YELLOW + " arrows";  
    String lore2 = ChatColor.WHITE + "" + projectileDamage + ChatColor.YELLOW + " arrow damage";  

    item = new Weapon(F_Materials.getMaterial(kitID), name, lore1, lore2);
    
    item.getWeaponItem().addEnchantment(Enchantment.ARROW_INFINITE, 1);
    
    goblinSword = new Weapon(Material.WOODEN_SWORD, ChatColor.GREEN + "Goblin Sword",
      ChatColor.WHITE + "" + F_Stats.getDamageList(kitID)[0] + ChatColor.YELLOW + " damage", "Fire Aspect");
    
    goblinSword.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", F_Stats.getDamageList(kitID)[0],
        AttributeModifier.Operation.ADD_NUMBER));
    
    goblinSword.applyWeaponUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
    
  }
  
  public static ChatColor getKitChatColor() {
    return kitChatColor;
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
