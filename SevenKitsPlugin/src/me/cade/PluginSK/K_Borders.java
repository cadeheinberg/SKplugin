package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class K_Borders {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);
  private static int bordersTask;
  private static int bountyBorderTask;

  public static void startCheckingBorders() {
    setBordersTask(0);
    setBordersTask(new BukkitRunnable() {
      @Override
      public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
          if(Zgen.worldSand(player.getWorld())) {
            return;
          }
          if (player.getLocation().getY() < 45) {
            if (player.getGameMode() == GameMode.CREATIVE) {
              return;
            }
            player.setHealth(0);
          } else if (Vars.getFighter(player).isBuildMode()) {
            if (player.getLocation().getY() < 186) {
              if (player.getGameMode() == GameMode.CREATIVE) {
                return;
              }
              if(player.getLocation().getZ() < -300) {
                player.teleport(A_Main.mineSpawn);
                return;
              }
              (Vars.getFighter(player)).changeToFighterMode(true);
            }
          }
        }

      }
    }.runTaskTimer(plugin, 60L, 20L).getTaskId());
  }

  public static void checkBountyBorders(Player player) {
    setBountyBorderTask(0);
    setBountyBorderTask(new BukkitRunnable() {
      @Override
      public void run() {
        if (player.getLocation().getY() > 186) {
          M_Bounty.tellAllBountyOverNoWinner();
        }
      }
    }.runTaskTimer(plugin, 60L, 20L).getTaskId());
  }

  public static int getBordersTask() {
    return bordersTask;
  }

  public static void setBordersTask(int bordersTask) {
    K_Borders.bordersTask = bordersTask;
  }

  public static int getBountyBorderTask() {
    return bountyBorderTask;
  }

  public static void setBountyBorderTask(int bountyBorderTask) {
    K_Borders.bountyBorderTask = bountyBorderTask;
  }

  public static void cancelBountyBorderTask() {
    Bukkit.getScheduler().cancelTask(bountyBorderTask);
  }



}
