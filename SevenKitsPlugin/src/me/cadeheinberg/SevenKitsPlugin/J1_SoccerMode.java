package me.cadeheinberg.SevenKitsPlugin;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class J1_SoccerMode implements Listener {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);
  private static int bluePoints;
  private static int redPoints;
  private static Weapon soccerSword;
  private static Material soccerSwordMaterial;
  private static Block redTopLeft;
  private static Block blueTopLeft;
  private static Block ballMid;
  private static Block[] redList;
  private static Block[] blueList;
  private static ArrayList<Player> redTeam;
  private static ArrayList<Player> blueTeam;
  private static Player lastToKick;
  private static ArrayList<Player> allPlayers;
  private static ArrayList<String> inMode;

  public static void setUpClass() {
    inMode = new ArrayList<String>();
    lastToKick = null;
    bluePoints = 0;
    redPoints = 0;
    redList = new Block[15];
    blueList = new Block[15];
    allPlayers = new ArrayList<Player>();
    redTeam = new ArrayList<Player>();
    blueTeam = new ArrayList<Player>();
    soccerSwordMaterial = Material.IRON_AXE;
    findCenters();
    createRedList();
    createBlueList();
    setRedStatue(redPoints);
    setBlueStatue(bluePoints);
    soccerSword = new Weapon(soccerSwordMaterial, ChatColor.YELLOW + "Soccer Axe",
      "double jump pressing space bar x2");
  }

  public static void checkSoccerGoals() {
    new BukkitRunnable() {
      @Override
      public void run() {
        if (A_Main.ballStand.getLocation().getY() < 194) {
          if (A_Main.ballStand.getLocation().getZ() < -114) {
            if (lastToKick == null) {
              tellSoccerMessage(ChatColor.YELLOW + "The" + ChatColor.RED + "" + ChatColor.BOLD
                + " Red " + ChatColor.YELLOW + "team has been scored on!");
            } else {
              if (blueTeam.contains(lastToKick)) {
                tellSoccerMessage(ChatColor.YELLOW + "Player " + ChatColor.BLUE + ""
                  + ChatColor.BOLD + lastToKick.getName() + ChatColor.YELLOW + " scored a goal on"
                  + ChatColor.RED + "" + ChatColor.BOLD + " Red" + ChatColor.YELLOW + "!");
              } else if (redTeam.contains(lastToKick)) {
                tellSoccerMessage(ChatColor.YELLOW + "Player " + ChatColor.RED + "" + ChatColor.BOLD
                  + lastToKick.getName() + ChatColor.YELLOW + " scored a goal on" + ChatColor.RED
                  + "" + ChatColor.BOLD + " Red" + ChatColor.YELLOW + "!");
              } else {
                tellSoccerMessage(ChatColor.YELLOW + "The" + ChatColor.RED + "" + ChatColor.BOLD
                  + " Red " + ChatColor.YELLOW + "team has been scored on!");
              }
            }
            incBluePoints();
            launchFireWork(new Location(A_Main.kitpvp, -1016.5, 200, -134.5));
            launchFireWork(new Location(A_Main.kitpvp, -1004.5, 200, -134.5));
          } else {
            if (lastToKick == null) {
              tellSoccerMessage(ChatColor.YELLOW + "The" + ChatColor.BLUE + "" + ChatColor.BOLD
                + " Blue " + ChatColor.YELLOW + "team has been scored on!");
            } else {
              if (redTeam.contains(lastToKick)) {
                tellSoccerMessage(ChatColor.YELLOW + "Player " + ChatColor.RED + ""
                  + ChatColor.BOLD + lastToKick.getName() + ChatColor.YELLOW + " scored a goal on"
                  + ChatColor.BLUE + "" + ChatColor.BOLD + " Blue" + ChatColor.YELLOW + "!");
              } else if (blueTeam.contains(lastToKick)) {
                tellSoccerMessage(ChatColor.YELLOW + "Player " + ChatColor.BLUE + "" + ChatColor.BOLD
                  + lastToKick.getName() + ChatColor.YELLOW + " scored a goal on" + ChatColor.BLUE
                  + "" + ChatColor.BOLD + " Blue" + ChatColor.YELLOW + "!");
              } else {
                tellSoccerMessage(ChatColor.YELLOW + "The " + ChatColor.BLUE + "" + ChatColor.BOLD
                  + " Blue " + ChatColor.YELLOW + "team has been scored on!");
              }
            }
            incRedPoints();
            launchFireWork(new Location(A_Main.kitpvp, -1016.5, 200, -90.5));
            launchFireWork(new Location(A_Main.kitpvp, -1004.5, 200, -90.5));
          }
          A_Main.ballStand.teleport(A_Main.ballSpawn);
          teleportAllBack();
        }
      }
    }.runTaskTimer(plugin, 60L, 10L).getTaskId();
  }

  private static void incRedPoints() {
    if ((redPoints + 1) > 7) {
      tellSoccerMessage(ChatColor.RED + "" + ChatColor.BOLD + "Red won the game!");
      redPoints = 0;
      bluePoints = 0;
      setBlueStatue(bluePoints);
    } else {
      redPoints++;
    }
    setRedStatue(redPoints);
    A_Main.ballStand
      .setCustomName(ChatColor.BLUE + "" + ChatColor.BOLD + bluePoints + ChatColor.WHITE + ""
        + ChatColor.BOLD + ":" + ChatColor.RED + "" + ChatColor.BOLD + redPoints);
  }

  private static void incBluePoints() {
    if ((bluePoints + 1) > 7) {
      tellSoccerMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Blue won the game!");
      bluePoints = 0;
      redPoints = 0;
      setRedStatue(redPoints);
    } else {
      bluePoints++;
    }
    setBlueStatue(bluePoints);
    A_Main.ballStand
      .setCustomName(ChatColor.BLUE + "" + ChatColor.BOLD + bluePoints + ChatColor.WHITE + ""
        + ChatColor.BOLD + ":" + ChatColor.RED + "" + ChatColor.BOLD + redPoints);
  }

  public static void teleportAllBack() {
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        teleportBlueBack();
        teleportRedBack();
      }
    }, 30);
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        removeBallFloor();
      }
    }, 60);
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        replaceBallFloor();
      }
    }, 80);
  }

  private static void removeBallFloor() {
    Material mat = Material.AIR;
    ballMid.getRelative(-1, 0, -1).setType(mat);
    ballMid.getRelative(-1, 0, 0).setType(mat);
    ballMid.getRelative(-1, 0, 1).setType(mat);

    ballMid.getRelative(0, 0, -1).setType(mat);
    ballMid.setType(mat);
    ballMid.getRelative(0, 0, 1).setType(mat);

    ballMid.getRelative(1, 0, -1).setType(mat);
    ballMid.getRelative(1, 0, 0).setType(mat);
    ballMid.getRelative(1, 0, 1).setType(mat);
  }

  private static void replaceBallFloor() {
    Material mat = Material.BARRIER;
    ballMid.getRelative(-1, 0, -1).setType(mat);
    ballMid.getRelative(-1, 0, 0).setType(mat);
    ballMid.getRelative(-1, 0, 1).setType(mat);

    ballMid.getRelative(0, 0, -1).setType(mat);
    ballMid.setType(mat);
    ballMid.getRelative(0, 0, 1).setType(mat);

    ballMid.getRelative(1, 0, -1).setType(mat);
    ballMid.getRelative(1, 0, 0).setType(mat);
    ballMid.getRelative(1, 0, 1).setType(mat);
  }

  private static void teleportBlueBack() {
    for (Player player : blueTeam) {
      player.teleport(new Location(A_Main.kitpvp, -1010.5, 194, -93.5, 180, 0));
    }
  }

  private static void teleportRedBack() {
    for (Player player : redTeam) {
      player.teleport(new Location(A_Main.kitpvp, -1010.5, 194, -131.5, 0, 0));
    }
  }

  private static void setRedStatue(int num) {
    clearRedStatue();
    Material mat = Material.RED_WOOL;
    if (num == 0) {
      redList[0].setType(mat);
      redList[1].setType(mat);
      redList[2].setType(mat);
      redList[5].setType(mat);
      redList[8].setType(mat);
      redList[11].setType(mat);
      redList[14].setType(mat);
      redList[13].setType(mat);
      redList[12].setType(mat);
      redList[9].setType(mat);
      redList[6].setType(mat);
      redList[3].setType(mat);
    } else if (num == 1) {
      redList[3].setType(mat);
      redList[1].setType(mat);
      redList[4].setType(mat);
      redList[7].setType(mat);
      redList[10].setType(mat);
      redList[13].setType(mat);
      redList[12].setType(mat);
      redList[14].setType(mat);
    } else if (num == 2) {
      redList[0].setType(mat);
      redList[1].setType(mat);
      redList[2].setType(mat);
      redList[5].setType(mat);
      redList[8].setType(mat);
      redList[7].setType(mat);
      redList[6].setType(mat);
      redList[9].setType(mat);
      redList[12].setType(mat);
      redList[13].setType(mat);
      redList[14].setType(mat);
    } else if (num == 3) {
      redList[0].setType(mat);
      redList[1].setType(mat);
      redList[2].setType(mat);
      redList[5].setType(mat);
      redList[8].setType(mat);
      redList[7].setType(mat);
      redList[11].setType(mat);
      redList[12].setType(mat);
      redList[13].setType(mat);
      redList[14].setType(mat);
    } else if (num == 4) {
      redList[0].setType(mat);
      redList[3].setType(mat);
      redList[6].setType(mat);
      redList[7].setType(mat);
      redList[8].setType(mat);
      redList[5].setType(mat);
      redList[2].setType(mat);
      redList[11].setType(mat);
      redList[14].setType(mat);
    } else if (num == 5) {
      redList[2].setType(mat);
      redList[1].setType(mat);
      redList[0].setType(mat);
      redList[3].setType(mat);
      redList[6].setType(mat);
      redList[7].setType(mat);
      redList[8].setType(mat);
      redList[11].setType(mat);
      redList[14].setType(mat);
      redList[13].setType(mat);
      redList[12].setType(mat);
    } else if (num == 6) {
      redList[2].setType(mat);
      redList[1].setType(mat);
      redList[0].setType(mat);
      redList[3].setType(mat);
      redList[6].setType(mat);
      redList[9].setType(mat);
      redList[12].setType(mat);
      redList[13].setType(mat);
      redList[14].setType(mat);
      redList[11].setType(mat);
      redList[8].setType(mat);
      redList[7].setType(mat);
    } else if (num == 7) {
      redList[0].setType(mat);
      redList[1].setType(mat);
      redList[2].setType(mat);
      redList[5].setType(mat);
      redList[8].setType(mat);
      redList[11].setType(mat);
      redList[14].setType(mat);
    }

  }

  private static void setBlueStatue(int num) {
    clearBlueStatue();
    Material mat = Material.BLUE_WOOL;
    if (num == 0) {
      blueList[0].setType(mat);
      blueList[1].setType(mat);
      blueList[2].setType(mat);
      blueList[5].setType(mat);
      blueList[8].setType(mat);
      blueList[11].setType(mat);
      blueList[14].setType(mat);
      blueList[13].setType(mat);
      blueList[12].setType(mat);
      blueList[9].setType(mat);
      blueList[6].setType(mat);
      blueList[3].setType(mat);
    } else if (num == 1) {
      blueList[3].setType(mat);
      blueList[1].setType(mat);
      blueList[4].setType(mat);
      blueList[7].setType(mat);
      blueList[10].setType(mat);
      blueList[13].setType(mat);
      blueList[12].setType(mat);
      blueList[14].setType(mat);
    } else if (num == 2) {
      blueList[0].setType(mat);
      blueList[1].setType(mat);
      blueList[2].setType(mat);
      blueList[5].setType(mat);
      blueList[8].setType(mat);
      blueList[7].setType(mat);
      blueList[6].setType(mat);
      blueList[9].setType(mat);
      blueList[12].setType(mat);
      blueList[13].setType(mat);
      blueList[14].setType(mat);
    } else if (num == 3) {
      blueList[0].setType(mat);
      blueList[1].setType(mat);
      blueList[2].setType(mat);
      blueList[5].setType(mat);
      blueList[8].setType(mat);
      blueList[7].setType(mat);
      blueList[11].setType(mat);
      blueList[12].setType(mat);
      blueList[13].setType(mat);
      blueList[14].setType(mat);
    } else if (num == 4) {
      blueList[0].setType(mat);
      blueList[3].setType(mat);
      blueList[6].setType(mat);
      blueList[7].setType(mat);
      blueList[8].setType(mat);
      blueList[5].setType(mat);
      blueList[2].setType(mat);
      blueList[11].setType(mat);
      blueList[14].setType(mat);
    } else if (num == 5) {
      blueList[2].setType(mat);
      blueList[1].setType(mat);
      blueList[0].setType(mat);
      blueList[3].setType(mat);
      blueList[6].setType(mat);
      blueList[7].setType(mat);
      blueList[8].setType(mat);
      blueList[11].setType(mat);
      blueList[14].setType(mat);
      blueList[13].setType(mat);
      blueList[12].setType(mat);
    } else if (num == 6) {
      blueList[2].setType(mat);
      blueList[1].setType(mat);
      blueList[0].setType(mat);
      blueList[3].setType(mat);
      blueList[6].setType(mat);
      blueList[9].setType(mat);
      blueList[12].setType(mat);
      blueList[13].setType(mat);
      blueList[14].setType(mat);
      blueList[11].setType(mat);
      blueList[8].setType(mat);
      blueList[7].setType(mat);
    } else if (num == 7) {
      blueList[0].setType(mat);
      blueList[1].setType(mat);
      blueList[2].setType(mat);
      blueList[5].setType(mat);
      blueList[8].setType(mat);
      blueList[11].setType(mat);
      blueList[14].setType(mat);
    }

  }

  private static void clearRedStatue() {
    for (Block block : redList) {
      block.setType(Material.AIR);
    }
  }

  private static void clearBlueStatue() {
    for (Block block : blueList) {
      block.setType(Material.AIR);
    }
  }

  private static void findCenters() {
    redTopLeft = A_Main.kitpvp.getBlockAt(-1012, 205, -136);
    blueTopLeft = A_Main.kitpvp.getBlockAt(-1010, 205, -90);
    ballMid = A_Main.kitpvp.getBlockAt(-1011, 208, -113);
  }

  private static void createRedList() {
    int j = 0;
    for (int i = 0; i > -5; i--) {
      redList[j] = redTopLeft.getRelative(0, i, 0);
      j++;
      redList[j] = redTopLeft.getRelative(1, i, 0);
      j++;
      redList[j] = redTopLeft.getRelative(2, i, 0);
      j++;
    }
  }

  private static void createBlueList() {
    int j = 0;
    for (int i = 0; i > -5; i--) {
      blueList[j] = blueTopLeft.getRelative(0, i, 0);
      j++;
      blueList[j] = blueTopLeft.getRelative(-1, i, 0);
      j++;
      blueList[j] = blueTopLeft.getRelative(-2, i, 0);
      j++;
    }
  }

  private static void launchFireWork(Location location) {
    Firework firework = A_Main.kitpvp.spawn(location, Firework.class);
    FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
    data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.YELLOW)
      .with(Type.BALL_LARGE).withFlicker().build());
    data.setPower(1);
    firework.setFireworkMeta(data);
  }

  public static void joinSoccerMode(Player player) {
    Vars.getFighter(player).changeToSoccerMode();
    player.teleport(A_Main.soccerSpawn);
    player.getInventory().clear();
    player.getInventory().addItem(soccerSword.getWeaponItem());
    player.setAllowFlight(true);
    player.setFlying(false);
    addPlayerToGame(player);
  }

  public static void tellSoccerMessage(String note) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (Zgen.safeZone(player.getLocation())) {
        if (Zgen.soccerZone(player.getLocation())) {
          player.sendMessage(note);
        }
      }
    }
  }

  @EventHandler
  public void onDoubleJump(PlayerToggleFlightEvent e) {
    if(Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
      return;
    }
    e.getPlayer().setFlying(false);
    if (Zgen.safeZone(e.getPlayer().getLocation())) {
      if (Zgen.soccerZone(e.getPlayer().getLocation())) {
        if (e.getPlayer().getCooldown(soccerSwordMaterial) > 0) {
          return;
        } else {
          Player player = e.getPlayer();
          player.setCooldown(soccerSwordMaterial, 100);
          Zgen.launchPlayer(player, 1.6);
          player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
          player.setAllowFlight(false);
          Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
              if(player != null) {
                if(!player.isOnline()) {
                  return;
                }
                if(!Vars.getFighter(player).isSoccerMode()) {
                  return;
                }
                player.setAllowFlight(true);
                player.setFlying(false);
              }
            }
          }, 99);
        }
      }
    }
  }
  
  public static void launchVehicle(Entity vehicle, Player player) {
    Location loc = player.getEyeLocation();
    loc.setPitch(-15);
    Vector vector = loc.getDirection();
    vehicle.setVelocity(vector.multiply(2.3));
  }

  public static int getRedPoints() {
    return redPoints;
  }

  public static int getBluePoints() {
    return bluePoints;
  }

  public static Player getLastToKick() {
    return lastToKick;
  }

  public static void setLastToKick(Player lastToKick) {
    J1_SoccerMode.lastToKick = lastToKick;
  }
  
  public static void doSmallDribble(LivingEntity sheep, Player player) {
    lastToKick = player;
    Location loc = player.getEyeLocation();
    loc.setPitch(-10);
    Vector vector = loc.getDirection();
    if(player.isSprinting()) {
      sheep.setVelocity(vector.multiply(0.75));
    }else {
      sheep.setVelocity(vector.multiply(0.5));
    }

  }
  
  private static void addPlayerToGame(Player player) {
    if (redTeam.size() < blueTeam.size()) {
      redTeam.add(player);
      allPlayers.add(player);
      F_KitManager.giveArmor(player, F5_Igor.getArmorColor(), true);
      J1_SoccerMode.tellSoccerMessage(ChatColor.YELLOW + "" + player.getName() + " has joined the"
        + ChatColor.RED + " Red " + ChatColor.YELLOW + "team");
    } else {
      blueTeam.add(player);
      allPlayers.add(player);
      F_KitManager.giveArmor(player, F6_Heavy.getArmorColor(), true);
      J1_SoccerMode.tellSoccerMessage(ChatColor.YELLOW + "" + player.getName() + " has joined the"
        + ChatColor.BLUE + " Blue " + ChatColor.YELLOW + "team");
    }
  }

  public static void removePlayer(Player player) { 
    if (redTeam.contains(player)) {
      redTeam.remove(player);
      J1_SoccerMode.tellSoccerMessage(ChatColor.YELLOW + "" + player.getName() + " has left the"
        + ChatColor.RED + " Red " + ChatColor.YELLOW + "team");
    } else {
      blueTeam.remove(player);
      J1_SoccerMode.tellSoccerMessage(ChatColor.YELLOW + "" + player.getName() + " has left the"
        + ChatColor.BLUE + " Blue " + ChatColor.YELLOW + "team");
    }
  }

  public static ArrayList<String> getInMode() {
    return inMode;
  }

  public static void setInMode(ArrayList<String> inMode) {
    J1_SoccerMode.inMode = inMode;
  }
  
}
