package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class E_PlayerJoin implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    ItemStack[] toStore = vipJoinCheck(player);
    int sLevel = player.getLevel();
    float sExp = player.getExp();
    player.getInventory().clear();
    E1_Fighter pFight = new E1_Fighter(player);
    Vars.addFigher(player.getUniqueId(), pFight);
    pFight.playerJoin();
    storeCheckSandbox(player, pFight, toStore, sLevel, sExp);
    player.teleport(A_Main.spawn);
  }
  
  public static ItemStack[] vipJoinCheck(Player player) {
    if(player.hasPermission("seven.vip")) {
      return player.getInventory().getContents();
    }
    return null;
  }
  
  public static void storeCheckSandbox(Player player, E1_Fighter pFight, ItemStack[] sandInv, int sLevel, float sExp) {
    if(sandInv == null) {
      return;
    }
    pFight.setVipInventory(sandInv);
    pFight.setSandboxLevel(sLevel);
    pFight.setSandboxExp(sExp);
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    e.setQuitMessage("");
    Player player = e.getPlayer();
    player.teleport(A_Main.spawn);
    E1_Fighter pFight = Vars.getFighter(player);
    if(player.getCooldown(H1_CombatTracker.getTrackerMaterial()) > 0) {
      pFight.decCakes((pFight.getCakes() + 1) / 2);
    }
    pFight.checkIfLeftMiniGame();
    pFight.fighterQuitServer();
    placeInventoryBack(player, pFight);
    Vars.removeFighter(player.getUniqueId());
  }
  
  public static void placeInventoryBack(Player player, E1_Fighter pFight) {
    if(!player.hasPermission("seven.vip")) {
      player.getInventory().clear();
      return;
    }
    if(pFight.isBuildMode()) {
      return;
    }
    if(pFight.getVipInventory() == null) {
      player.getInventory().clear();
      return;
    }
    player.getInventory().clear();
    player.getInventory().setContents(pFight.getVipInventory());
    player.setLevel(Vars.getFighter(player).getSandboxLevel());
    player.setExp(Vars.getFighter(player).getSandboxExp());
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    if(!e.isBedSpawn()) {
      e.getPlayer().setBedSpawnLocation(A_Main.sandBoxSpawn, true);
    }
    if(Zgen.worldSand(e.getPlayer().getWorld())) {
      if(e.getPlayer().getBedSpawnLocation() != null) {
        e.setRespawnLocation(e.getPlayer().getBedSpawnLocation());
      }else {
        e.setRespawnLocation(A_Main.sandBoxSpawn);
      }
      return;
    }
    e.setRespawnLocation(A_Main.spawn);
    Player player = e.getPlayer();
    E1_Fighter pFight = Vars.getFighter(player);
    pFight.fighterRespawn();
  }

}
