package me.cade.PluginSK.cakes;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.cade.PluginSK.A_Main;
import me.cade.PluginSK.D_NpcSpawner;
import me.cade.PluginSK.Vars;

public class C_CakeSpawner {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);
  private static final int cakeLimit = 1;
  private static final Random rand = new Random(9999);
  private static Location cakeSpawn1;
  private static Location cakeSpawn2;
  private static Location cakeSpawn3;
  private static ArrayList<Entity> chunkEntities1;
  private static ArrayList<Entity> chunkEntities2;
  private static ArrayList<Entity> chunkEntities3;
  private static int cakeTask;
  private static String cakeName;
  private static Material cakeMaterial;
  
  private static int cakePrice;
  private static double minLeft;
  private static double nextLeft;
  private static int cakeSpawnTask;
  private static int nextStockTask;
      
  public static void setUpCakeAreas() {
    cakeSpawnTask = -1;
    cakePrice = 5;
    cakeName = "dollars";
    cakeMaterial = Material.CAKE;
    cakeSpawn1 = new Location(A_Main.kitpvp, -1014.5,65,-143.5);
    cakeSpawn2 = new Location(A_Main.kitpvp, -1057.5,65,-101.5);
    cakeSpawn3 = new Location(A_Main.kitpvp, -1092.5,65,-93.5);
    doStockExchangeLoop();
    doNextStockTime();
  }

  public static void spawnCakes() {
    chunkEntities1 = new ArrayList<Entity>();
    chunkEntities2 = new ArrayList<Entity>();
    chunkEntities3 = new ArrayList<Entity>();
    cakeTask = new BukkitRunnable() {
      @Override
      public void run() {
        chunkEntities1 = (ArrayList<Entity>) A_Main.kitpvp.getNearbyEntities(cakeSpawn1, 5, 5, 5);
        chunkEntities2 = (ArrayList<Entity>) A_Main.kitpvp.getNearbyEntities(cakeSpawn2, 5, 5, 5);
        chunkEntities3 = (ArrayList<Entity>) A_Main.kitpvp.getNearbyEntities(cakeSpawn3, 5, 5, 5);
        checkCakeArea(chunkEntities1, cakeSpawn1);
        checkCakeArea(chunkEntities2, cakeSpawn2);
        checkCakeArea(chunkEntities3, cakeSpawn3);
        Bukkit.getScheduler().cancelTask(cakeSpawnTask);
        doCakeTime();
      }
    }.runTaskTimer(plugin, 0L, 1200L).getTaskId();
  }
  
  public static void doStockExchangeLoop() {
    Random rand = new Random();
    new BukkitRunnable() {
      @Override
      public void run() {
        //2 to 5 dollars
        int newPrice = 0;
        if(rand.nextInt(3) == 2) {
          newPrice = cakePrice + 1;
        }else {
          newPrice = cakePrice - 1;
        }
        if(newPrice < 4) {
          if(rand.nextInt(3) == 2) {
            newPrice = cakePrice + 2;
          }else {
            newPrice = cakePrice + 1;
          }
        }
        cakePrice = newPrice;
        for(Player player :  Bukkit.getOnlinePlayers()) {
          Vars.getFighter(player).updateStockValue();
        }
        Bukkit.getScheduler().cancelTask(nextStockTask);
        doNextStockTime();
      }
    }.runTaskTimer(plugin, 0L, 36000L);
  }
  
  public static void doNextStockTime() {
    nextLeft = 30;
    nextStockTask = new BukkitRunnable() {
      @Override
      public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
          Vars.getFighter(player).updateStockTime();
        }
        nextLeft = nextLeft - 0.1;
        if (nextLeft < 0.1) {
          nextLeft = 0;
        }
      }
    }.runTaskTimer(plugin, 0L, 120L).getTaskId();
  }
  
  public static void doCakeTime() {
    minLeft = 1;
    cakeSpawnTask = new BukkitRunnable() {
      @Override
      public void run() {
        D_NpcSpawner.updateCakeTimes(minLeft);
        minLeft = minLeft - 0.1;
        if (minLeft < 0.1) {
          minLeft = 0;
        }
      }
    }.runTaskTimer(plugin, 0L, 120L).getTaskId();
  }
  
  public static void shutDownCakes() {
    clearCakeAreas();
    Bukkit.getScheduler().cancelTask(cakeTask);
  }

  private static void clearCakeAreas() {
    clearEachArea(chunkEntities1, cakeSpawn1);
    clearEachArea(chunkEntities2, cakeSpawn2);
    clearEachArea(chunkEntities3, cakeSpawn3);
  }
  
  private static void clearEachArea(ArrayList<Entity> chunkEntities, Location cakeSpawn) {
    for (int i = 0; i < chunkEntities.size(); i++) {
      if (chunkEntities.get(i) instanceof LivingEntity) {
        continue;
      }
      if (chunkEntities.get(i).getType() == EntityType.DROPPED_ITEM) {
        if (chunkEntities.get(i).getName().equals("money")) {
          chunkEntities.get(i).remove();
        }
      }
    }
  }

  private static void checkCakeArea(ArrayList<Entity> chunkEntities, Location cakeSpawn) {
    int count = 0;
    if(chunkEntities != null) {
      if(chunkEntities.size() > 0) {
        for (int i = 0; i < chunkEntities.size(); i++) {
          if (chunkEntities.get(i) instanceof LivingEntity) {
            continue;
          }
          if (chunkEntities.get(i).getType() == EntityType.DROPPED_ITEM) {
            if (chunkEntities.get(i).getName().equals("Cake")) {
              count++;
            }
          }
        }
      }
    }
    if (count < cakeLimit) {
      ItemStack cakeDrop = new ItemStack(cakeMaterial, 1);
      ArrayList<String> cakeLore = new ArrayList<String>();
      cakeLore.add("" + rand.nextInt(99999));
      ItemMeta meta = cakeDrop.getItemMeta();
      meta.setDisplayName("Cake");
      meta.setLore(cakeLore);
      cakeDrop.setItemMeta(meta);
      A_Main.kitpvp.dropItem(cakeSpawn, cakeDrop);
    }
  }
  
  public static String getCakeName() {
    return cakeName;
  }
  
  public static Material gatCakeMaterial() {
    return cakeMaterial;
  }

  public static int getCakePrice() {
    return cakePrice;
  }

  public static void setCakePrice(int cakePrice) {
    C_CakeSpawner.cakePrice = cakePrice;
  }

  public static double getNextLeft() {
    return nextLeft;
  }

  public static void setNextLeft(double nextLeft) {
    C_CakeSpawner.nextLeft = nextLeft;
  }
  
  public static String getNextStockString() {
    String nextTime = "";
    if(nextLeft >= 10) {
      nextTime = Double.toString(nextLeft).substring(0, 4);
    }else {
      nextTime = Double.toString(nextLeft).substring(0, 3);
    }
    return nextTime;
  }

}
