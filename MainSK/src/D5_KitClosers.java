package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class D5_KitClosers implements Listener {

  @EventHandler
  public void onCloseMenu(InventoryCloseEvent e) {
    if(Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    Player player = (Player) e.getPlayer();
    Inventory clicked = e.getInventory();
    if (clicked.equals(F1_Noob.getKitMenu()) || clicked.equals(F1_Noob.getBuyMenu())) {
      for (int i = 0; i < 4; i++) {
        player.setCooldown(F1_Noob.getItemList()[i].getWeaponItem().getType(), 0);
      }
    } else if (clicked.equals(F2_Booster.getKitMenu()) || clicked.equals(F2_Booster.getBuyMenu())) {
      for (int i = 0; i < 4; i++) {
        player.setCooldown(F2_Booster.getItemList()[i].getWeaponItem().getType(), 0);
      }
    } else if (clicked.equals(F3_Shotty.getKitMenu()) || clicked.equals(F3_Shotty.getBuyMenu())) {
      for (int i = 0; i < 4; i++) {
        player.setCooldown(F3_Shotty.getItemList()[i].getWeaponItem().getType(), 0);
      }
    } else if (clicked.equals(F4_Goblin.getKitMenu()) || clicked.equals(F4_Goblin.getBuyMenu())) {
      for (int i = 0; i < 4; i++) {
        player.setCooldown(F4_Goblin.getDisplays()[i], 0);
      }
    } else if (clicked.equals(F5_Igor.getKitMenu()) || clicked.equals(F5_Igor.getBuyMenu())) {
      for (int i = 0; i < 4; i++) {
        player.setCooldown(F5_Igor.getDisplays()[i], 0);
      }
    } else if (clicked.equals(F6_Heavy.getKitMenu()) || clicked.equals(F6_Heavy.getBuyMenu())) {
      for (int i = 0; i < 4; i++) {
        player.setCooldown(F6_Heavy.getItemList()[i].getWeaponItem().getType(), 0);
      }
    } else if (clicked.equals(F7_Wizard.getKitMenu())
      || clicked.equals(F7_Wizard.getBuyWandMenu())) {
      player.setCooldown(Material.BLAZE_ROD, 0);
      player.setCooldown(Material.SPLASH_POTION, 0);
    }


  }


}
