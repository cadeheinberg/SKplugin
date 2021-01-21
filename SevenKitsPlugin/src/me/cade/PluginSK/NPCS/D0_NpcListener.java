package me.cade.PluginSK.NPCS;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.cade.PluginSK.Fighter;

public class D0_NpcListener implements Listener {

  @EventHandler
  public void onClick(PlayerInteractAtEntityEvent e) {
    if (e.getHand() == EquipmentSlot.OFF_HAND) {
      return; // off hand packet, ignore.
    }
    Player player = e.getPlayer();
    String name = e.getRightClicked().getCustomName();
    if (name == null) {
      return;
    }
    if(e.getRightClicked().getType() != EntityType.ARMOR_STAND) {
      return;
    }
    // Kit Names
    if (name.equals(D_SpawnKitSelectors.kitNames[0])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      if(fighter.getUnlockedBoolean(0)) {
        fighter.giveKit(0, 0);
      }else {
        player.sendMessage(ChatColor.RED + "You do not own this kit");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      }
    } else if (name.equals(D_SpawnKitSelectors.kitNames[1])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      if(fighter.getUnlockedBoolean(1)) {
        fighter.giveKit(1, 0);
      }else {
        player.sendMessage(ChatColor.RED + "You do not own this kit");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      }
    } else if (name.equals(D_SpawnKitSelectors.kitNames[2])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      if(fighter.getUnlockedBoolean(2)) {
        fighter.giveKit(2, 0);
      }else {
        player.sendMessage(ChatColor.RED + "You do not own this kit");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      }
    } else if (name.equals(D_SpawnKitSelectors.kitNames[3])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      if(fighter.getUnlockedBoolean(3)) {
        fighter.giveKit(3, 0);
      }else {
        player.sendMessage(ChatColor.RED + "You do not own this kit");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      }
    } else if (name.equals(D_SpawnKitSelectors.kitNames[4])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      if(fighter.getUnlockedBoolean(4)) {
        fighter.giveKit(4, 0);
      }else {
        player.sendMessage(ChatColor.RED + "You do not own this kit");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      }
    } else if (name.equals(D_SpawnKitSelectors.kitNames[5])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      if(fighter.getUnlockedBoolean(5)) {
        fighter.giveKit(5, 0);
      }else {
        player.sendMessage(ChatColor.RED + "You do not own this kit");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      }
    } else if (name.equals(D_SpawnKitSelectors.kitNames[6])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      if(fighter.getUnlockedBoolean(6)) {
        fighter.giveKit(6, 0);
      }else {
        player.sendMessage(ChatColor.RED + "You do not own this kit");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
      }
    }
  }
    
    @EventHandler
    public void onGrabArmorStand(PlayerArmorStandManipulateEvent e) {
      if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
        return;
      }
      e.setCancelled(true);
    }

}
