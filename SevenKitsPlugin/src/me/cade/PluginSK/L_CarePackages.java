package me.cade.PluginSK;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import me.cade.PluginSK.cakes.C_CakeSpawner;

public class L_CarePackages implements Listener {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);
  private static Location[] spawns;
  private static Random rand;

  private static Block[] beaconBlocks;
  private static double minLeft;
  private static boolean spawnedIn;
  private static Location currentChestSpot;

  private static int chestTask;

  public static void setUpCarePackageClass() {
    spawns = new Location[3];
    rand = new Random();
    beaconBlocks = new Block[19];
    spawnedIn = false;

    spawns[0] = new Location(A_Main.kitpvp, -1043, 65, -151);
    spawns[1] = new Location(A_Main.kitpvp, -1020, 65, -116);
    spawns[2] = new Location(A_Main.kitpvp, -1081, 65, -115);

    doChestRunnable();
  }

  public static void closeDownCarePackageClass() {
    for (int i = 0; i < 19; i++) {
      if (beaconBlocks[i] != null) {
        beaconBlocks[i].setType(Material.AIR);
      }
    }
  }

  public static void doChestRunnable() {
    Location spawnSpot = spawns[rand.nextInt(3)];
    minLeft = 12;
    chestTask = new BukkitRunnable() {
      @Override
      public void run() {
        if (spawnedIn) {
          return;
        }
        minLeft = minLeft - 0.1;
        if (minLeft < 0.3) {
          spawnRandomChest(spawnSpot);
          spawnedIn = true;
          minLeft = 0;
          Bukkit.getScheduler().cancelTask(chestTask);
        } else if (minLeft <= 2.01) {
          spawnBeacon(spawnSpot);
        }
      }
    }.runTaskTimer(plugin, 0L, 120L).getTaskId();
  }


  public static void spawnRandomChest(Location chestSpot) {
    currentChestSpot = chestSpot;
    A_Main.kitpvp.getBlockAt(chestSpot).setType(Material.CHEST);
    beaconBlocks[18] = A_Main.kitpvp.getBlockAt(chestSpot);
  }

  public static void spawnBeacon(Location chestSpot) {
    Block beacon = chestSpot.getBlock().getRelative(0, -1, 0);
    Block gold = beacon.getRelative(0, -1, 0);

    beaconBlocks[0] = beacon.getRelative(0, 0, -1);
    beaconBlocks[1] = beacon.getRelative(1, 0, -1);
    beaconBlocks[2] = beacon.getRelative(-1, 0, -1);

    beaconBlocks[3] = beacon.getRelative(-1, 0, 0);
    beaconBlocks[4] = beacon.getRelative(1, 0, 0);

    beaconBlocks[5] = beacon.getRelative(0, 0, 1);
    beaconBlocks[6] = beacon.getRelative(-1, 0, 1);
    beaconBlocks[7] = beacon.getRelative(1, 0, 1);

    beaconBlocks[8] = gold.getRelative(0, 0, -1);
    beaconBlocks[9] = gold.getRelative(1, 0, -1);
    beaconBlocks[10] = gold.getRelative(-1, 0, -1);

    beaconBlocks[11] = gold.getRelative(-1, 0, 0);
    beaconBlocks[12] = gold.getRelative(1, 0, 0);

    beaconBlocks[14] = gold.getRelative(0, 0, 1);
    beaconBlocks[13] = gold.getRelative(-1, 0, 1);
    beaconBlocks[15] = gold.getRelative(1, 0, 1);

    for (int i = 0; i < 16; i++) {
      beaconBlocks[i].setType(Material.IRON_BLOCK);
    }

    gold.setType(Material.GOLD_BLOCK);
    beacon.setType(Material.BEACON);
    beaconBlocks[16] = beacon;
    beaconBlocks[17] = gold;

  }

  @EventHandler
  public void onChestOpen(PlayerInteractEvent e) {
    if (Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    if (e.getClickedBlock() == null) {
      return;
    }
    if (e.getClickedBlock().getType() == Material.CHEST) {
      if (Zgen.safeZone(e.getPlayer().getLocation())) {
        if (!Vars.getFighter(e.getPlayer()).isBuildMode()) {
          e.setCancelled(true);
          e.getPlayer().sendMessage(ChatColor.RED + "You must be in" + ChatColor.AQUA + ""
            + ChatColor.BOLD + " Sandbox Mode " + ChatColor.RED + "to use this");
          return;
        }
      } else {
        Block clicked = e.getClickedBlock();
        if (!clicked.getLocation().equals(currentChestSpot)) {
          return;
        }
        e.setCancelled(true);
        e.getPlayer().closeInventory();
        giveAReward(e.getPlayer());
        e.getClickedBlock().setType(Material.AIR);
        A_Main.kitpvp.spawnParticle(Particle.CLOUD, e.getClickedBlock().getX(),
          e.getClickedBlock().getY(), e.getClickedBlock().getZ(), 5);
        for (Entity ent : A_Main.kitpvp.getNearbyEntities(e.getClickedBlock().getLocation(), 5, 5,
          5)) {
          if (!(ent instanceof LivingEntity)) {
            continue;
          }
          Location upShoot = ent.getLocation();
          upShoot.setY(upShoot.getY() + 1);
          Vector currentDirection =
            upShoot.toVector().subtract(e.getClickedBlock().getLocation().toVector());
          currentDirection = currentDirection.multiply(new Vector(0.8, 0.8, 0.8));
          ent.setVelocity(currentDirection);
        }
        spawnedIn = false;
        return;
      }
    } else if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
      if (!Vars.getFighter(e.getPlayer()).isBuildMode()) {
        e.setCancelled(true);
        e.getPlayer().sendMessage(ChatColor.RED + "You must be in" + ChatColor.AQUA + ""
          + ChatColor.BOLD + " Sandbox Mode " + ChatColor.RED + "to use this");
        return;
      }
    }
  }

  public static void giveAReward(Player player) {
    Vars.getFighter(player).incCakes(50);
    Vars.getFighter(player).incExp(100);
    player.sendMessage(ChatColor.GREEN + "Care Package Reward: " + ChatColor.WHITE + "50 "
      + C_CakeSpawner.getCakeName() + " " + ChatColor.GREEN + "&" + ChatColor.WHITE + " 100 Exp!");
    doChestRunnable();
    for (int i = 0; i < 19; i++) {
      beaconBlocks[i].setType(Material.AIR);
    }
  }

  public static String getMinLeft() {
    if (spawnedIn) {
      return "SPAWNED";
    }
    String time = "" + minLeft;
    if (minLeft > 10) {
      if (time.length() > 4) {
        time = time.substring(0, 4);
      }
    } else {
      if (time.length() > 3) {
        time = time.substring(0, 3);
      }
    }
    return time + " min";
  }

}
