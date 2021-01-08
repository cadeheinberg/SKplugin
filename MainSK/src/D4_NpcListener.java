package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class D4_NpcListener implements Listener {

  @EventHandler
  public void onClick(PlayerInteractAtEntityEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      if (e.getRightClicked() instanceof ItemFrame) {
        if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
          ((ItemFrame) e.getRightClicked()).setFixed(false);
        }else {
          ((ItemFrame) e.getRightClicked()).setFixed(true);
        }
        e.setCancelled(true);
      }
      return;
    }
    if (e.getHand() == EquipmentSlot.OFF_HAND) {
      return; // off hand packet, ignore.
    }
    if (!(Zgen.safeZone(e.getRightClicked().getLocation()))) {
      // do heavy stuff
      if (e.getRightClicked() instanceof Player) {
        if (G_KitListener.checkIfRightKit(e.getPlayer(), 6)) {
          G6_Heavy.doPickup(e.getPlayer(), e.getRightClicked());
        }
      }
      return;
    }
    if (e.getRightClicked().getType() == EntityType.PANDA) {
      if (Zgen.soccerZone(e.getRightClicked().getLocation())) {
        J1_SoccerMode.doSmallDribble((LivingEntity) e.getRightClicked(), e.getPlayer());
      }
    }
    e.setCancelled(true);
    Player player = e.getPlayer();
    String name = e.getRightClicked().getCustomName();
    if (name == null) {
      return;
    }
    // Kit Names
    if (name.equals(D_NpcSpawner.kitNames[0])) {
      int unlocked = Vars.getFighter(player).getUnlocked()[0];
      if (unlocked < 3) {
        for (int i = 3; i > unlocked; i--) {
          player.setCooldown(F1_Noob.getItemList()[i].getWeaponItem().getType(), 9999999);
        }
      }
      F1_Noob.openKitMenu(player);
      return;
    } else if (name.equals(D_NpcSpawner.kitNames[1])) {
      int unlocked = Vars.getFighter(player).getUnlocked()[1];
      if (unlocked < 3) {
        for (int i = 3; i > unlocked; i--) {
          player.setCooldown(F2_Booster.getItemList()[i].getWeaponItem().getType(), 9999999);
        }
      }
      F2_Booster.openKitMenu(player);
      return;
    } else if (name.equals(D_NpcSpawner.kitNames[2])) {
      int unlocked = Vars.getFighter(player).getUnlocked()[2];
      if (unlocked < 3) {
        for (int i = 3; i > unlocked; i--) {
          player.setCooldown(F3_Shotty.getItemList()[i].getWeaponItem().getType(), 9999999);
        }
      }
      F3_Shotty.openKitMenu(player);
      return;
    } else if (name.equals(D_NpcSpawner.kitNames[3])) {
      int unlocked = Vars.getFighter(player).getUnlocked()[3];
      if (unlocked < 3) {
        for (int i = 3; i > unlocked; i--) {
          player.setCooldown(F4_Goblin.getDisplays()[i], 9999999);
        }
      }
      F4_Goblin.openKitMenu(player);
      return;
    } else if (name.equals(D_NpcSpawner.kitNames[4])) {
      int unlocked = Vars.getFighter(player).getUnlocked()[4];
      if (unlocked < 3) {
        for (int i = 3; i > unlocked; i--) {
          player.setCooldown(F5_Igor.getDisplays()[i], 9999999);
        }
      }
      F5_Igor.openKitMenu(player);
      return;
    } else if (name.equals(D_NpcSpawner.kitNames[5])) {
      int unlocked = Vars.getFighter(player).getUnlocked()[5];
      if (unlocked < 3) {
        for (int i = 3; i > unlocked; i--) {
          player.setCooldown(F6_Heavy.getItemList()[i].getWeaponItem().getType(), 9999999);
        }
      }
      F6_Heavy.openKitMenu(player);
      return;
    } else if (name.equals(D_NpcSpawner.kitNames[6])) {
      int unlocked = Vars.getFighter(player).getUnlocked()[6];
      if (unlocked == -1) {
        player.setCooldown(Material.BLAZE_ROD, 9999999);
        player.setCooldown(Material.SPLASH_POTION, 9999999);
      }
      F7_Wizard.openKitMenu(player);
      return;
    }
    // Shop Names
    else if (name.equals(D_NpcSpawner.shopNames[0])) {
      player.openInventory(D3_Menus.getStreakMenu());
      return;
    } else if (name.equals(D_NpcSpawner.shopNames[1])) {
      player.openInventory(D3_Menus.getArmorMenu());
      return;
    } else if (name.equals(D_NpcSpawner.shopNames[2])) {
      player.openInventory(D3_Menus.getLevelMenu());
      return;
    } else if (name.equals(D_NpcSpawner.shopNames[3])) {
      player.openInventory(D3_Menus.getAgilityMenu());
      return;
    } else if (name.equals(D_NpcSpawner.shopNames[4])) {
      player.openInventory(D3_Menus.getSpecialItemMenu());
      return;
    } else if (name.equals(D_NpcSpawner.builderName)) {
      if (Vars.getFighter(player).isBuildMode()) {
        Vars.getFighter(player).changeToFighterMode(true);
        return;
      } else if (player.hasPermission("seven.vip")) {
        Vars.getFighter(player).changeToBuilder();
        return;
      } else {
        player.sendMessage(ChatColor.RED + "You are not a" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " VIP " + ChatColor.RED + "member");
      }
      return;
    } else if (name.equals(D_NpcSpawner.bountyName)) {
      if (player.hasPermission("seven.vip")) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Set a bounty with: ");
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "--> " + ChatColor.YELLOW
          + "/bountyset " + ChatColor.WHITE + "PlayerName" + ChatColor.GREEN + " Reward");
        return;
      } else {
        player.sendMessage(ChatColor.RED + "You are not a" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " VIP " + ChatColor.RED + "member");
      }
      return;
    } else if (name.equals(D_NpcSpawner.dailyName)) {
      player.performCommand("vote gui");
      return;
    } else if (name.equals(D_NpcSpawner.cosmeticName)) {
      if (player.hasPermission("seven.vip")) {
        player.openInventory(D3_Menus.getCosmeticMenu());
        return;
      } else {
        player.sendMessage(ChatColor.RED + "You are not a" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " VIP " + ChatColor.RED + "member");
      }
      return;
    } else if (name.equals(D_NpcSpawner.webStoreName)) {
      player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Purchase a Rank At: ");
      player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "--> " + ChatColor.YELLOW
        + "https://sevenkitspvp.tebex.io/");
    } else if (name.equals(D_NpcSpawner.goToMineName)) {
      if (player.getBedSpawnLocation() == null) {
        player.setBedSpawnLocation(A_Main.sandBoxSpawn, true);
      }
      if (e.getPlayer().getBedSpawnLocation() != null) {
        e.getPlayer().teleport(e.getPlayer().getBedSpawnLocation());
      } else {
        e.getPlayer().teleport(A_Main.sandBoxSpawn);
      }
    } else if (name.equals(D_NpcSpawner.joinSoccer)) {
      if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
        e.getPlayer().teleport(A_Main.soccerSpawn);
        return;
      }
      J1_SoccerMode.joinSoccerMode(e.getPlayer());
    } else if (name.equals(D_NpcSpawner.patchyParrot)) {
      e.setCancelled(true);
      e.getPlayer().sendMessage(ChatColor.AQUA + "I am Bork!");
    }else if (name.equals(D_NpcSpawner.sellCakes)) {
      Vars.getFighter(e.getPlayer()).sellTokens(Vars.getFighter(e.getPlayer()).getTokens());
    }
  }

}
