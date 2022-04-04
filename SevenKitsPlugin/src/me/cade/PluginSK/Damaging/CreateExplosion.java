package me.cade.PluginSK.Damaging;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CreateExplosion {

	public static void doAnExplosion(Player shooter, Location location, double power, Double damage,
			boolean confusion) {
		location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location.getX(), location.getY() + 2,
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
			Vector currentDirection = upShoot.toVector().subtract(location.toVector());
//			if (Math.abs(currentDirection.getX()) < 0.50 && Math.abs(currentDirection.getZ()) < 0.50
//					&& currentDirection.getX() != 0 && currentDirection.getZ() != 0) {
//				double factor = Math.abs(0.50 / Math.min(currentDirection.getX(), currentDirection.getZ()));
//				currentDirection.setX(currentDirection.getX() * factor);
//				currentDirection.setZ(currentDirection.getZ() * factor);
//			}
//			shooter.sendMessage(currentDirection.toString());
			currentDirection = currentDirection.multiply(new Vector(power, power, power));
			ent.setVelocity(currentDirection);
			if (((LivingEntity) ent) != shooter) {
				DealDamage.dealAmount(shooter, (LivingEntity) ent, damage);
				if (confusion) {
					((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 2));
					((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 2));
				}
			}
		}
	}

}
