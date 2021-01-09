package me.cade.PluginSK.BuildKits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import me.cade.PluginSK.Weapon;

public class F5_Heavy {

	private static final int kitID = 5;
	private static final String kitName = ChatColor.BLUE + "Heavy";
	private static final Color armorColor = Color.fromRGB(8, 111, 255);
	private static Weapon[] items;
	private static ChatColor b;
	
	private Player player;
	
	//special will be a wall/shield idk
	
  public F5_Heavy(Player player, int kitIndex) {
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
		b = ChatColor.BLUE;
		
    String name = b + "Death Machine";
    String dependent = ChatColor.YELLOW + " capacity";

    String w = ChatColor.WHITE + "";
    String attack = ChatColor.YELLOW + " attack damage";
    String special = ChatColor.YELLOW + " arrow damage";
    String cool = ChatColor.YELLOW + " reload";

    items = new Weapon[4];

    for (int i = 0; i < 4; i++) {
      items[i] = new Weapon(F_Materials.getMaterialList(kitID)[i], name,
        w + F_Stats.getDamageList(kitID)[i] + attack,
        w + F_Stats.getSpecialDamageList(kitID)[i] + special,
        w + F_Stats.getIntList(kitID)[i] + dependent,
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
