package me.cade.PluginSK.KitListeners;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.BuildKits.F3_Goblin;
import me.cade.PluginSK.BuildKits.F_Stats;
import me.cade.PluginSK.Damaging.DealDamage;

public class G3_Goblin {

  // dropping the bow activates special ability
  // which is whenever a player is hit with sword
  // or arrow it slows and poisons them and barrage arrow

  public static void doDrop(Player killer) {
	if (killer.getCooldown(Material.BIRCH_FENCE) > 0 || killer.getCooldown(Material.JUNGLE_FENCE) > 0) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    G8_Cooldown.startAbilityDuration(killer, 200, 50);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    gustLaunch(killer);
    killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
  }
  
  public static void gustLaunch(Player killer) {
	    Location playerLocation = killer.getLocation();
	    killer.playSound(killer.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
	    if (playerLocation.getPitch() > 49) {
	      launchPlayer(killer, -1.5);
	    }
	    Location origin = killer.getEyeLocation();
	    Vector direction = killer.getLocation().getDirection();
	    double dX = direction.getX();
	    double dY = direction.getY();
	    double dZ = direction.getZ();
	    playerLocation.setPitch((float) -30.0);
	    int range = 13;
	    double power = 2.8;
	    ArrayList<Integer> hitList = new ArrayList<Integer>();
	    for (int j = 2; j < range; j++) {
	      origin = origin.add(dX * j, dY * j, dZ * j);
	      killer.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, origin.getX(), origin.getY(), origin.getZ(),
	        5);
	      ArrayList<Entity> entityList =
	        (ArrayList<Entity>) killer.getWorld().getNearbyEntities(origin, 2.5, 2.5, 2.5);
	      for (Entity entity : entityList) {
	        if (entity instanceof LivingEntity) 
	          if(hitList.contains(((LivingEntity) entity).getEntityId())) {
	            continue;
	          }
	          hitList.add(((LivingEntity) entity).getEntityId());{
	          DealDamage.dealAmount(killer, (LivingEntity) entity, 1);
	          if(killer.getName().equals(((LivingEntity) entity).getName())) {
	            return;
	          }
	          Vector currentDirection = playerLocation.getDirection().normalize();
	          currentDirection = currentDirection.multiply(new Vector(power, power, power));
	          entity.setVelocity(currentDirection);
	        }
	      }
	      origin = origin.subtract(dX * j, dY * j, dZ * j);
	    }
	  }
  
  public static void launchPlayer(Entity entity, Double power) {
	      Vector currentDirection = entity.getLocation().getDirection().normalize();
	      currentDirection = currentDirection.multiply(new Vector(power, power, power));
	      entity.setVelocity(currentDirection);
	  }

  public static void deActivateSpecial(Player player) {
    CraftPlayer craftPlayer = (CraftPlayer) player;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }

  public static void doArrorwHitEntity(Player killer, LivingEntity victim, Arrow arrow) {
    //create your own form of knockback
    DealDamage.dealAmount(killer, victim, F_Stats.getDamageList(F3_Goblin.getKitID())[0]);
    if (arrow.getFireTicks() > 0) {
      victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 2));
      victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 2));
    }
  }

  public static boolean doArrowShoot(Player shooter, Arrow arrow) {
    if (shooter.getCooldown(F3_Goblin.getWeapon().getWeaponItem().getType()) > 0) {
      return false;
    }
    shooter.setCooldown(F3_Goblin.getWeapon().getWeaponItem().getType(),
      F_Stats.getTicksList(F3_Goblin.getKitID())[0]);
    if (Fighter.get(shooter).isAbilityActive()) {
      arrow.setFireTicks(1000);
    }
    return true;
  }



}
