package me.cade.PluginSK.NPCS;

import org.bukkit.GameMode;
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
      fighter.setKitID(0);
      fighter.setKitIndex(2);
      fighter.giveKit();
    } else if (name.equals(D_SpawnKitSelectors.kitNames[1])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      fighter.setKitID(1);
      fighter.setKitIndex(2);
      fighter.giveKit();
    } else if (name.equals(D_SpawnKitSelectors.kitNames[2])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      fighter.setKitID(2);
      fighter.setKitIndex(2);
      fighter.giveKit();
    } else if (name.equals(D_SpawnKitSelectors.kitNames[3])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      fighter.setKitID(3);
      fighter.setKitIndex(2);
      fighter.giveKit();
    } else if (name.equals(D_SpawnKitSelectors.kitNames[4])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      fighter.setKitID(4);
      fighter.setKitIndex(2);
      fighter.giveKit();
    } else if (name.equals(D_SpawnKitSelectors.kitNames[5])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      fighter.setKitID(5);
      fighter.setKitIndex(2);
      fighter.giveKit();
    } else if (name.equals(D_SpawnKitSelectors.kitNames[6])) {
      Fighter fighter = Fighter.fighters.get(player.getUniqueId());
      fighter.setKitID(6);
      fighter.setKitIndex(2);
      fighter.giveKit();
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
