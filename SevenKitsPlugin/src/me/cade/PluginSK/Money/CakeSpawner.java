package me.cade.PluginSK.Money;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.NPCS.SpawnCakeTags;

public class CakeSpawner {

  private static Plugin plugin = Main.getPlugin(Main.class);
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
  
  public static void startCakes() {
    SpawnCakeTags.makeGrassCake("start");
    SpawnCakeTags.makeSandCake("start");
    SpawnCakeTags.makeIceCake("start");
    setUpCakeAreas();
    spawnCakes();
  }
  
  public static void stopCakes() {
    clearCakeAreas();
    Bukkit.getScheduler().cancelTask(cakeTask);
  }
      
  private static void setUpCakeAreas() {
    cakeSpawnTask = -1;
    cakePrice = 5;
    cakeName = "dollars";
    cakeMaterial = Material.CAKE;
    cakeSpawn1 = new Location(Main.hub, -1014.5,65,-143.5);
    cakeSpawn2 = new Location(Main.hub, -1057.5,65,-101.5);
    cakeSpawn3 = new Location(Main.hub, -1092.5,65,-93.5);
  }

  private static void spawnCakes() {
    chunkEntities1 = new ArrayList<Entity>();
    chunkEntities2 = new ArrayList<Entity>();
    chunkEntities3 = new ArrayList<Entity>();
    cakeTask = new BukkitRunnable() {
      @Override
      public void run() {
        chunkEntities1 = (ArrayList<Entity>) Main.hub.getNearbyEntities(cakeSpawn1, 5, 5, 5);
        chunkEntities2 = (ArrayList<Entity>) Main.hub.getNearbyEntities(cakeSpawn2, 5, 5, 5);
        chunkEntities3 = (ArrayList<Entity>) Main.hub.getNearbyEntities(cakeSpawn3, 5, 5, 5);
        checkCakeArea(chunkEntities1, cakeSpawn1);
        checkCakeArea(chunkEntities2, cakeSpawn2);
        checkCakeArea(chunkEntities3, cakeSpawn3);
        Bukkit.getScheduler().cancelTask(cakeSpawnTask);
        doCakeTime();
      }
    }.runTaskTimer(plugin, 0L, 1200L).getTaskId();
  }
  
  public static void doCakeTime() {
    minLeft = 1;
    cakeSpawnTask = new BukkitRunnable() {
      @Override
      public void run() {
        SpawnCakeTags.updateCakeTimes(minLeft);
        minLeft = minLeft - 0.1;
        if (minLeft < 0.1) {
          minLeft = 0;
        }
      }
    }.runTaskTimer(plugin, 0L, 120L).getTaskId();
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
      Main.hub.dropItem(cakeSpawn, cakeDrop);
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

  public static double getNextLeft() {
    return nextLeft;
  }
  
}

