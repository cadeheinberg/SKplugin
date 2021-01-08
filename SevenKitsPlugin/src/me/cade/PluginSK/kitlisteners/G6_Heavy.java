package me.cade.PluginSK.kitlisteners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import me.cade.PluginSK.A_Main;
import me.cade.PluginSK.E1_Fighter;
import me.cade.PluginSK.F0_DealDamage;
import me.cade.PluginSK.G8_Cooldown;
import me.cade.PluginSK.Vars;
import me.cade.PluginSK.Zgen;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F6_Heavy;

public class G6_Heavy {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);

  public static boolean doHeavy(Player player, int weaponId) {
    if (player.getCooldown(F6_Heavy.getItemList()[weaponId].getWeaponItem().getType()) > 0) {
      return false;
    }
    if (player.getLevel() >= F0_Stats.getIntList(F6_Heavy.getKitID() - 1)[weaponId]) {
      G8_Cooldown.setCooldown(player, F6_Heavy.getItemList()[weaponId].getWeaponItem().getType(),
        F0_Stats.getTicksList(F6_Heavy.getKitID() - 1)[weaponId]);
      player.setLevel(1);
      return false;
    }
    player.setLevel(player.getLevel() + 1);
    shootArrow(player);
    return true;
  }

  public static void shootArrow(Player player) {
    player.launchProjectile(Arrow.class);
    player.playSound(player.getLocation(), Sound.BLOCK_WOOL_PLACE, 8, 1);
  }

  public static void doPickup(Player player, Entity rightClicked) {
    if (Zgen.safeZone(player.getLocation())) {
      return;
    }
    if (rightClicked.getType() == EntityType.VILLAGER) {
      return;
    }
    if (player.getPassengers() == null) {
      return;
    }
    if (player.getPassengers().size() >= 1) {
      return;
    }
    player.addPassenger(rightClicked);
  }

  public static void doHeavyThrow(Player killer, LivingEntity victim) {
    killer.eject();

    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        Location playerLocation = killer.getLocation();
        if (playerLocation.getPitch() < -30) {
          playerLocation.setPitch((float) -30.0);
        }
        Vector currentDirection = playerLocation.getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
        victim.setVelocity(currentDirection);
      }
    }, 1);
  }

  public static void doHeavyDrop(E1_Fighter pFight) {
    if (pFight.getPlayer().getCooldown(F6_Heavy.getItemList()[pFight.getKitIndex()].getWeaponItem().getType()) > 0) {
      return;
    }
    G8_Cooldown.setCooldown(pFight.getPlayer(), F6_Heavy.getItemList()[pFight.getKitIndex()].getWeaponItem().getType(),
      F0_Stats.getTicksList(F6_Heavy.getKitID() - 1)[pFight.getKitIndex()]);
    pFight.getPlayer().setLevel(1);
  }

  public static void doHeavyArrowHit(Player shooter, LivingEntity victim, Projectile throwEntity) {
    if (victim instanceof LivingEntity) {
      F0_DealDamage.dealAmount(shooter, (LivingEntity) victim, (F0_Stats
        .getSpecialDamageList(F6_Heavy.getKitID() - 1)[Vars.getFighter(shooter).getKitIndex()]));
      Vector currentDirection = throwEntity.getVelocity().normalize();
      currentDirection = currentDirection.multiply(new Vector(0.8, 0.8, 0.8));
      ((LivingEntity) victim).setVelocity(currentDirection);
    }
  }

}
