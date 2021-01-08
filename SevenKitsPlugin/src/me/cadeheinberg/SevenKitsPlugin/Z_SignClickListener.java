package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Z_SignClickListener implements Listener {

  @EventHandler
  public void onSignClick(PlayerInteractEvent e) {
    if (e.getClickedBlock() == null) {
      return;
    }
    if (!(e.getClickedBlock().getState() instanceof Sign)) {
      return;
    }
    Sign sign = (Sign) e.getClickedBlock().getState();
    if (sign.getLine(1).equals("Sell All Cakes")) {
      if (!Zgen.worldSandSafe(sign.getLocation())) {
        return;

      }
      Vars.getFighter(e.getPlayer()).sellTokens(Vars.getFighter(e.getPlayer()).getTokens());
    }    else if (sign.getLine(1).equals("Transfer Cakes")) {
      if (!Zgen.worldSandSafe(sign.getLocation())) {
        return;
      }
      J_BuilderMode.investAllTokens(e.getPlayer());
    }
    else if (sign.getLine(1).equals("Buy Booster Axe")) {
      if (!Zgen.worldSandSafe(sign.getLocation())) {
        return;
      }
      Player player = e.getPlayer();
      if (player.getGameMode() == GameMode.CREATIVE) {
        return;
      }
      if (Vars.getFighter(player).getCakes() < 500) {
        player.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.AQUA + ""
          + ChatColor.BOLD + " Cakes " + ChatColor.RED + "to buy this");
        return;
      }
      E1_Fighter pFight = Vars.getFighter(player);
      pFight.decCakes(500);
      player.getInventory().addItem(F2_Booster.getItemList()[pFight.getUnlocked()[1]].getWeaponItem());
    }
    
    
    
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (sign.getLine(2).equals("kills and deaths")) {
      if (Zgen.inPlayGround(sign.getLocation())) {
        return;
      }
      Vars.getFighter(e.getPlayer()).setKils(0);
      Vars.getFighter(e.getPlayer()).setDeaths(0);
      Vars.getFighter(e.getPlayer()).getScoreBoardObject().updateKills();
      Vars.getFighter(e.getPlayer()).getScoreBoardObject().updateDeaths();
      return;
    } else if (sign.getLine(2).equals("Among Us")) {
      if (Zgen.inPlayGround(sign.getLocation())) {
        return;
      }
      e.getPlayer().teleport(A_Main.amongSpawn);
      return;
    }
  }
}
