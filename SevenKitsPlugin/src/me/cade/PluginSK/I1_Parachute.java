package me.cade.PluginSK;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class I1_Parachute {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);

  private static Weapon parachute;

  public static void makeParachute() {
    parachute = new Weapon(Material.MAGMA_CREAM, ChatColor.YELLOW + "Parachute",
      ChatColor.WHITE + "Right click to open");
  }

  public static Weapon getParachute() {
    return parachute;
  }

  @SuppressWarnings("deprecation")
  public static void doParachute(Player player, boolean atSpawn) {
    if (player.isOnGround()) {
      return;
    }
    if (player.getCooldown(parachute.getWeaponItem().getType()) > 0) {
      return;
    }
    if(Vars.getFighter(player).getParachuteTask() != -1) {
      return;
    }
    if(atSpawn) {
      player.setCooldown(parachute.getWeaponItem().getType(), 100);
    }else {
      player.setCooldown(parachute.getWeaponItem().getType(), 400);
    }
    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 8, 1);

    summonEntity(player);
    return;
  }

  public static void summonEntity(Player player) {
    Chicken chicken =
      (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
    chicken.addPassenger(player);
    Vars.getFighter(player).setChicken(chicken);
    doGliding(chicken, player);
  }

  public static void doGliding(Chicken chicken, Player player) {
    Vars.getFighter(player).setParachuteTask(new BukkitRunnable() {
      @Override
      public void run() {
        if (chicken.isOnGround()) {
          getOff(chicken, player.getUniqueId());
          return;
        }
        launchChicken(chicken, player);
      }
    }.runTaskTimer(plugin, 0L, 1L).getTaskId());
  }

  public static void launchChicken(Chicken chicken, Player player) {
    Location loc = player.getEyeLocation();
    if (loc.getPitch() < 30) {
      loc.setPitch(30);
    } else if (loc.getPitch() >= 75) {
      loc.setPitch(75);
    }
    Vector vector = loc.getDirection();
    chicken.setVelocity(vector.multiply(0.6));
  }

  public static void resetCooldown(Player player) {
    player.setCooldown(parachute.getWeaponItem().getType(), 0);
  }

  public static void getOff(Chicken chicken, UUID uuid) {
    chicken.eject();
    chicken.remove();
    E1_Fighter pFight = Vars.getFighterWithName(uuid);
    Bukkit.getScheduler().cancelTask(pFight.getParachuteTask());
    pFight.setParachuteTask(-1);
    pFight.setChickenNull();
  }

}
