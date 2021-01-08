package me.cade.PluginSK;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Z_LeaderBoard {

  private static Sign[] streaks;
  private static Sign[] kills;
  private static Sign[] ratios;

  private static int[] maxStreaks;

  private static int updateTask;
  
  private static Plugin plugin = A_Main.getPlugin(A_Main.class);

  public static void getSigns() {
    streaks = new Sign[3];
    kills = new Sign[3];
    ratios = new Sign[3];
    maxStreaks = new int[3];
    {
      int x = -1054;
      for (int i = 0; i < 3; i++) {
        Block b = A_Main.kitpvp.getBlockAt(x, 198, -143);
        if (b.getType() == Material.BIRCH_WALL_SIGN) {
          kills[i] = (Sign) b.getState();
        }
        x++;
      }
    }
    {
      int z = -140;
      for (int i = 0; i < 3; i++) {
        Block b = A_Main.kitpvp.getBlockAt(-1048, 198, z);
        if (b.getType() == Material.BIRCH_WALL_SIGN) {
          streaks[i] = (Sign) b.getState();
        }
        z++;
      }
    }
    {
      int z = -138;
      for (int i = 0; i < 3; i++) {
        Block b = A_Main.kitpvp.getBlockAt(-1058, 198, z);
        if (b.getType() == Material.BIRCH_WALL_SIGN) {
          ratios[i] = (Sign) b.getState();
        }
        z--;
      }
    }
    for (int i = 0; i < 3; i++) {
      maxStreaks[i] = Integer.parseInt(streaks[i].getLine(2));
    }
  }

  public static void updateStreaksLeader(String name, int setter) {
    if (setter >= maxStreaks[0]) {
      maxStreaks[0] = setter;
      String name1 = streaks[0].getLine(1);
      String setter1 = streaks[0].getLine(2);
      String name2 = streaks[1].getLine(1);
      String setter2 = streaks[1].getLine(2);
      streaks[0].setLine(0, "(1)");
      streaks[0].setLine(1, "" + name);
      streaks[0].setLine(2, "" + setter);
      streaks[0].update();
      if (!name.equals(name1)) {
        streaks[1].setLine(0, "(2)");
        streaks[1].setLine(1, "" + name1);
        streaks[1].setLine(2, "" + setter1);
        streaks[1].update();
        if (!name.equals(name2)) {
          streaks[2].setLine(0, "(3)");
          streaks[2].setLine(1, "" + name2);
          streaks[2].setLine(2, "" + setter2);
          streaks[2].update();
        }
      }
      return;
    } else if (setter >= maxStreaks[1]) {
      maxStreaks[1] = setter;
      String name1 = streaks[0].getLine(1);
      String name2 = streaks[1].getLine(1);
      String setter2 = streaks[1].getLine(2);
      if (name.equals(name1)) {
        return;
      }
      streaks[1].setLine(0, "(2)");
      streaks[1].setLine(1, "" + name);
      streaks[1].setLine(2, "" + setter);
      streaks[1].update();
      if (!name.equals(name2)) {
        streaks[2].setLine(0, "(3)");
        streaks[2].setLine(1, "" + name2);
        streaks[2].setLine(2, "" + setter2);
        streaks[2].update();
      }
      return;
    } else if (setter >= maxStreaks[2]) {
      maxStreaks[2] = setter;
      String name1 = streaks[0].getLine(1);
      String name2 = streaks[1].getLine(1);
      if (name.equals(name1) || name.equals(name2)) {
        return;
      }
      maxStreaks[2] = setter;
      streaks[2].setLine(0, "(3)");
      streaks[2].setLine(1, "" + name);
      streaks[2].setLine(2, "" + setter);
      streaks[2].update();
      return;
    }
  }
  
  public static void updateKillsLeaders() {
    ArrayList<String> leaders = B_Database.getKillsLeaders();
    if (leaders == null) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "RATIO LEADER ERROR");
      return;
    }
    String ratio1 = leaders.get(0);
    kills[0].setLine(0, "(1)");
    kills[0].setLine(1, "" + leaders.get(1));
    kills[0].setLine(2, "" + ratio1);
    kills[0].update();

    String ratio2 = leaders.get(2);
    kills[1].setLine(0, "(2)");
    kills[1].setLine(1, "" + leaders.get(3));
    kills[1].setLine(2, "" + ratio2);
    kills[1].update();
    
    String ratio3 = leaders.get(4);
    kills[2].setLine(0, "(3)");
    kills[2].setLine(1, "" + leaders.get(5));
    kills[2].setLine(2, "" + ratio3);
    kills[2].update();
  }

  public static void updateKDLeaders() {
    ArrayList<String> leaders = B_Database.getKDLeaders();
    if (leaders == null) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "RATIO LEADER ERROR");
      return;
    }
    String ratio1 = leaders.get(0);
    if(ratio1.length() > 4) {
      ratio1 = ratio1.substring(0, 4);
    }
    ratios[0].setLine(0, "(1)");
    ratios[0].setLine(1, "" + leaders.get(1));
    ratios[0].setLine(2, "" + ratio1);
    ratios[0].update();

    String ratio2 = leaders.get(2);
    if(ratio2.length() > 4) {
      ratio2 = ratio2.substring(0, 4);
    }
    ratios[1].setLine(0, "(2)");
    ratios[1].setLine(1, "" + leaders.get(3));
    ratios[1].setLine(2, "" + ratio2);
    ratios[1].update();
    
    String ratio3 = leaders.get(4);
    if(ratio3.length() > 4) {
      ratio3 = ratio3.substring(0, 4);
    }
    ratios[2].setLine(0, "(3)");
    ratios[2].setLine(1, "" + leaders.get(5));
    ratios[2].setLine(2, "" + ratio3);
    ratios[2].update();
  }

  public static void updateSignsRunnable() {
    updateTask = new BukkitRunnable() {
      @Override
      public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
          Vars.getFighter(player).uploadFighter();
        }
        updateKDLeaders();
        updateKillsLeaders();
      }
    }.runTaskTimer(plugin, 60L, 600L).getTaskId();
  }
  
  public static void endKDRunnable() {
    Bukkit.getScheduler().cancelTask(updateTask);
  }

}
