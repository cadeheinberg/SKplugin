package me.cade.PluginSK.Damaging;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CreateExplosion {
  
  public static void doAnExplosion(Player shooter, Location location, double power, Double damage) {
    location.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, location.getX(), location.getY() + 2,
      location.getZ(), 2);
    location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
    for (Entity ent : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
      if (!(ent instanceof LivingEntity)) {
        continue;
      }
      Location upShoot = ent.getLocation();
      if (ent.isOnGround()) {
        upShoot.setY(upShoot.getY() + 1);
      }
      DealDamage.dealAmount(shooter, (LivingEntity) ent, damage);
      Vector currentDirection = upShoot.toVector().subtract(location.toVector());
      currentDirection = currentDirection.multiply(new Vector(power, power, power));
      ent.setVelocity(currentDirection);
    }
  }

}
