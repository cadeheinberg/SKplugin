package me.cade.PluginSK.kitlisteners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import me.cade.PluginSK.A_Main;
import me.cade.PluginSK.E1_Fighter;
import me.cade.PluginSK.F0_DealDamage;
import me.cade.PluginSK.G8_Cooldown;
import me.cade.PluginSK.Vars;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F1_Noob;

public class G1_Noob {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);

  public static boolean doNoobDrop(Player player) {
    E1_Fighter pFight = Vars.getFighter(player);
    int weaponId = pFight.getKitIndex();
    if (player.getCooldown(F1_Noob.getItemList()[weaponId].getWeaponItem().getType()) > 0) {
      return false;
    }
    if(pFight.getNoobTask() != -1) {
      return false;
    }
    doJump(player, pFight);
    G8_Cooldown.setCooldown(player, F1_Noob.getItemList()[weaponId].getWeaponItem().getType(), 300);
    return true;
  }

  public static void doJump(Player player, E1_Fighter pFight) {
    player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
    launchPlayer(player, F0_Stats.getPowerList(0)[pFight.getKitIndex()], pFight);
  }

  public static void launchPlayer(Player player, Double power, E1_Fighter pFight) {
    Location local = player.getLocation();
    local.setPitch(-60);
    Vector currentDirection = local.getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(power, power, power));
    player.setVelocity(currentDirection);
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        listenForFall(player, pFight);
      }
    }, 5);
  }

  @SuppressWarnings("deprecation")
  public static void listenForFall(Player player, E1_Fighter pFight) {
    pFight.setNoobTask(new BukkitRunnable() {
      @Override
      public void run() {
        if (player == null) {
          stopListening(player, pFight);
          return;
        }
        if (!player.isOnline()) {
          stopListening(player, pFight);
          return;
        }
        if (player.isDead()) {
          stopListening(player, pFight);
          return;
        }
        if (player.isOnGround()) {
          stopListening(player, pFight);
          doGroundHit(player, player.getLocation(), 0.3,
            F0_Stats.getSpecialDamageList(0)[pFight.getKitIndex()]);
          return;
        }
        if (player.isSneaking()) {
          launchPlayerDown(player, 1.5, pFight);
        }
      }
    }.runTaskTimer(plugin, 0L, 1L).getTaskId());
  }

  public static void launchPlayerDown(Player player, Double power, E1_Fighter pFight) {
    Location local = player.getLocation();
    local.setPitch(80);
    Vector currentDirection = local.getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(power, power, power));
    player.setVelocity(currentDirection);
  }

  public static void stopListening(Player player, E1_Fighter pFight) {
    Bukkit.getScheduler().cancelTask(pFight.getNoobTask());
    pFight.setNoobTask(-1);
  }

  public static void doGroundHit(Player shooter, Location location, double power, Double double1) {
    A_Main.kitpvp.spawnParticle(Particle.EXPLOSION_LARGE, location.getX(), location.getY() + 1,
      location.getZ(), 3);
    A_Main.kitpvp.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
    for (Entity ent : A_Main.kitpvp.getNearbyEntities(location, 4, 4, 4)) {
      if (!(ent instanceof LivingEntity)) {
        return;
      }
      F0_DealDamage.dealAmount(shooter, (LivingEntity) ent, (F0_Stats
        .getSpecialDamageList(F1_Noob.getKitID() - 1)[Vars.getFighter(shooter).getKitIndex()]));
      Location upShoot = ent.getLocation();
      if (ent.isOnGround()) {
        upShoot.setY(upShoot.getY() + 1);
      }
      Vector currentDirection = upShoot.toVector().subtract(location.toVector());
      currentDirection = currentDirection.multiply(new Vector(power, power, power));
      ((LivingEntity) ent).setVelocity(currentDirection);
    }
  }

}
