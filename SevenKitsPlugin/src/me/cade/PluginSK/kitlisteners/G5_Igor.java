package me.cade.PluginSK.kitlisteners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import me.cade.PluginSK.A_Main;
import me.cade.PluginSK.F0_DealDamage;
import me.cade.PluginSK.G8_Cooldown;
import me.cade.PluginSK.Vars;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F5_Igor;

public class G5_Igor {

  public static boolean doIgor(Player player, int weaponId) {
    if (player.getCooldown(Material.TRIDENT) > 0) {
      return false;
    }
    throwTrident(player);
    G8_Cooldown.setCooldown(player, Material.TRIDENT,
      F0_Stats.getTicksList(F5_Igor.getKitID() - 1)[weaponId]);
    return true;
  }

  public static void throwTrident(Player player) {
    player.getInventory().remove(Material.TRIDENT);
    player.getInventory().addItem(F5_Igor.getTridentWeapon(Vars.getFighter(player).getKitIndex()));
  }

  public static void doTridentHitLivingEntity(Player shooter, LivingEntity hitEntity,
    int weaponId) {
    if (weaponId == 3) {
      Location local = hitEntity.getLocation();
      local.setY(hitEntity.getLocation().getY() - 0.5);
      doAnExplosion(shooter, local, 0.3,
        F0_Stats.getSpecialDamageList(F5_Igor.getKitID() - 1)[weaponId]);
    } else if (weaponId == 2) {
      hitEntity.getWorld().strikeLightning(hitEntity.getLocation());
      F0_DealDamage.dealAmount(shooter, (LivingEntity) hitEntity,
        (F0_Stats.getSpecialDamageList(F5_Igor.getKitID() - 1)[weaponId]));
    } else if (weaponId == 1) {
      hitEntity.setFireTicks(100);
      F0_DealDamage.dealAmount(shooter, (LivingEntity) hitEntity,
        (F0_Stats.getSpecialDamageList(F5_Igor.getKitID() - 1)[weaponId]));
    } else {
      F0_DealDamage.dealAmount(shooter, (LivingEntity) hitEntity,
        (F0_Stats.getSpecialDamageList(F5_Igor.getKitID() - 1)[weaponId]));
    }

  }

  public static void doTridentHitBlock(Player shooter, Block hitBlock, int weaponId) {
    if (weaponId == 3) {
      doAnExplosion(shooter, hitBlock.getLocation(), 0.3,
        F0_Stats.getSpecialDamageList(F5_Igor.getKitID() - 1)[weaponId]);
    } 
  }
  
  public static void doAnExplosion(Player shooter, Location location, double power, Double double1) {
    A_Main.kitpvp.spawnParticle(Particle.EXPLOSION_LARGE, location.getX(), location.getY() + 2,
      location.getZ(), 2);
    A_Main.kitpvp.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
    for (Entity ent : A_Main.kitpvp.getNearbyEntities(location, 4, 4, 4)) {
      if (!(ent instanceof LivingEntity)) {
        continue;
      }
      Location upShoot = ent.getLocation();
      if (ent.isOnGround()) {
        upShoot.setY(upShoot.getY() + 1);
      }
      F0_DealDamage.dealAmount(shooter, (LivingEntity) ent, double1);
      Vector currentDirection = upShoot.toVector().subtract(location.toVector());
      currentDirection = currentDirection.multiply(new Vector(power, power, power));
      ent.setVelocity(currentDirection);
    }
  }
}
