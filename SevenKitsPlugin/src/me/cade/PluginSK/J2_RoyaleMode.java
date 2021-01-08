package me.cade.PluginSK;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import me.cade.PluginSK.kitbuilders.F5_Igor;

public class J2_RoyaleMode {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);

  private static ArrayList<String> queue;
  private static ArrayList<String> inGame;
  private static ArrayList<String> spectators;
  private static ArrayList<String> inMode;

  private static int capacity;
  private static int minimum;
  private static boolean gameOn;

  private static ArrayList<Block> floor;

  public static void setUpClass() {
    inMode = new ArrayList<String>();
    queue = new ArrayList<String>();
    inGame = new ArrayList<String>();
    spectators = new ArrayList<String>();
    floor = new ArrayList<Block>();
    capacity = 5;
    minimum = 2;
    gameOn = false;
    findFloor();
  }

  public static void addPlayer(Player player) {
    if (gameOn) {
      player.sendMessage(ChatColor.RED + "Queue for" + ChatColor.AQUA + "" + ChatColor.BOLD
        + " Chicken Wars " + ChatColor.RED + "is full!");
      return;
    }
    if (!availableSpot()) {
      player.sendMessage(ChatColor.RED + "Queue for" + ChatColor.AQUA + "" + ChatColor.BOLD
        + " Chicken Wars " + ChatColor.RED + "is full!");
    }
    queue.add(player.getName());
    Vars.getFighter(player).giveKit(F5_Igor.getKitID(), 3, true);
    tellAll(ChatColor.YELLOW + player.getName() + " has joined");
    if (minimumReached()) {
      startGameCountDown();
    }
  }

  public static void removePlayer(Player player) {
    if (queue.contains(player.getName())) {
      queue.remove(player.getName());
    }
    if (inGame.contains(player.getName())) {
      inGame.remove(player.getName());
    }
    if (spectators.contains(player.getName())) {
      spectators.remove(player.getName());
    }
    player.getInventory().clear();
  }

  private static void startGame() {
    for (String name : queue) {
      inGame.add(name);
    }
    queue.clear();
    removeFloor();
    gameOn = true;
    doDelayedFloorReplace();
  }

  private static void findFloor() {
    Block b = A_Main.kitpvp.getBlockAt(-747, 186, -108);
    floor.add(b.getRelative(0, 0, 0));
  }

  private static void removeFloor() {
    for (Block b : floor) {
      b.setType(Material.AIR);
    }
  }

  private static void replaceFloor() {
    for (Block b : floor) {
      b.setType(Material.GLASS);
    }
  }

  private static void gameOver() {
    Player winner = Bukkit.getPlayer(inGame.get(0));
    if (winner != null) {
      winner.sendMessage("you won!");
    }
    inGame.clear();
    gameOn = false;
  }

  public static void playerDied(Player player) {
    inGame.remove(player.getName());
    spectators.add(player.getName());
    if (onePlayerLeft()) {
      gameOver();
    }
  }

  private static boolean onePlayerLeft() {
    if (inGame.size() == 1) {
      return true;
    }
    return false;
  }

  private static boolean availableSpot() {
    if (queue.size() >= capacity) {
      return false;
    }
    return true;
  }

  private static boolean minimumReached() {
    if (queue.size() >= minimum) {
      return true;
    }
    return false;
  }

  private static void startGameCountDown() {
    tellAll(ChatColor.YELLOW + "Game starting in 5 seconds");
    doDelayedGameStart();
  }

  private static void doDelayedFloorReplace() {
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        replaceFloor();
      }
    }, 100);
  }

  private static void doDelayedGameStart() {
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        startGame();
      }
    }, 100);
  }

  public static void tellAll(String note) {
    for (String name : inMode) {
      if(Bukkit.getPlayer(name) != null) {
        Bukkit.getPlayer(name).sendMessage(note);
      }
    }
  }

  public static ArrayList<String> getInMode() {
    return inMode;
  }

  public static void setInMode(ArrayList<String> inMode) {
    J2_RoyaleMode.inMode = inMode;
  }

}
