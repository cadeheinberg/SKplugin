package me.cade.PluginSK.kitlisteners;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import me.cade.PluginSK.A_Main;
import me.cade.PluginSK.E1_Fighter;
import me.cade.PluginSK.F0_DealDamage;
import me.cade.PluginSK.G8_Cooldown;
import me.cade.PluginSK.Vars;
import me.cade.PluginSK.Zgen;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F7_Wizard;

public class G7_Wizard {

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);

  public static boolean doWizard(Player player, int weaponId) {
    if (player.getCooldown(Material.BLAZE_ROD) > 0) {
      return false;
    }
    if (weaponId == 0) {
      doManaSpell(player);
    } else if (weaponId == 1) {
      doFireBall(player);
    } else if (weaponId == 2) {
      doIceCageSpell(player);
    } else if (weaponId == 3) {
      gustLaunch(player);
    } else {
      return false;
    }
    G8_Cooldown.setCooldown(player, Material.BLAZE_ROD,
      F0_Stats.getTicksList(F7_Wizard.getKitID() - 1)[weaponId]);
    return true;
  }

  private static void doFireBall(Player player) {
    Fireball ball = player.launchProjectile(LargeFireball.class);
    ball.setCustomName(player.getName());
    Vector currentDirection = ball.getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(3, 3, 3));
    ball.setVelocity(currentDirection);
  }

  public static void doFireBallHit(Entity fireBall, Location location) {
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
      Vector currentDirection = upShoot.toVector().subtract(location.toVector());
      currentDirection = currentDirection.multiply(new Vector(0.3, 0.3, 0.3));
      ent.setVelocity(currentDirection);
      if (Bukkit.getPlayer(fireBall.getCustomName()) == null) {
        return;
      }
      if (!Bukkit.getPlayer(fireBall.getCustomName()).isOnline()) {
        return;
      }
      F0_DealDamage.dealAmount(Bukkit.getPlayer(fireBall.getCustomName()), (LivingEntity) ent,
        (F0_Stats.getSpecialDamageList(F7_Wizard.getKitID() - 1)[Vars
          .getFighter(Bukkit.getPlayer(fireBall.getCustomName())).getKitIndex()]));
    }
  }

  private static void doIceCageSpell(Player player) {
    Item iceCube = A_Main.kitpvp.dropItem(player.getLocation(), new ItemStack(Material.ICE, 1));
    Vector currentDirection = player.getLocation().getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
    iceCube.setVelocity(currentDirection);
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
      @Override
      public void run() {
        iceCube.remove();
        Location iceLocation = iceCube.getLocation();
        if(Zgen.safeZone(iceLocation)) {
          return;
        }
        Block iceBlock = iceLocation.getBlock();
        Block[] b = new Block[57];
        int j = 0;
        for (int i = -1; i < 3; i++) {
          b[j++] = iceBlock.getRelative(2, i, -1);
          b[j++] = iceBlock.getRelative(2, i, 0);
          b[j++] = iceBlock.getRelative(2, i, 1);

          b[j++] = iceBlock.getRelative(-2, i, -1);
          b[j++] = iceBlock.getRelative(-2, i, 0);
          b[j++] = iceBlock.getRelative(-2, i, 1);

          b[j++] = iceBlock.getRelative(-1, i, 2);
          b[j++] = iceBlock.getRelative(0, i, 2);
          b[j++] = iceBlock.getRelative(1, i, 2);

          b[j++] = iceBlock.getRelative(-1, i, -2);
          b[j++] = iceBlock.getRelative(0, i, -2);
          b[j++] = iceBlock.getRelative(1, i, -2);
        }
        // top
        b[j++] = iceBlock.getRelative(-1, 3, -1);
        b[j++] = iceBlock.getRelative(-1, 3, 0);
        b[j++] = iceBlock.getRelative(-1, 3, 1);
        b[j++] = iceBlock.getRelative(1, 3, -1);
        b[j++] = iceBlock.getRelative(1, 3, 0);
        b[j++] = iceBlock.getRelative(1, 3, 1);
        b[j++] = iceBlock.getRelative(0, 3, -1);
        b[j++] = iceBlock.getRelative(0, 3, 0);
        b[j++] = iceBlock.getRelative(0, 3, 1);

        A_Main.kitpvp.playSound(iceBlock.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, 1);
        for (int i = 0; i < 57; i++) {
          Block block = b[i];
          if (block.getType() != Material.AIR) {
            continue;
          }
          if (i > 11) {
            block.setType(Material.ICE);
          }
          Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
              block.setType(Material.AIR);
            }
          }, 120);
        }
      }
    }, 10);
  }

  private static void doManaSpell(Player killer) {
    Location origin = killer.getEyeLocation();
    Vector direction = killer.getLocation().getDirection();
    double dX = direction.getX();
    double dY = direction.getY();
    double dZ = direction.getZ();
    // range
    ArrayList<Integer> hitList = new ArrayList<Integer>();
    for (int j = 1; j < 15; j++) {
      origin = origin.add(dX * j, dY * j, dZ * j);
      killer.getWorld().spawnParticle(Particle.FALLING_LAVA, origin.getX(), origin.getY(),
        origin.getZ(), 5);
      ArrayList<Entity> entityList =
        (ArrayList<Entity>) killer.getWorld().getNearbyEntities(origin, 0.75, 0.75, 0.75);
      for (Entity entity : entityList) {
        if (entity instanceof LivingEntity) {
          if(Zgen.safeZone(entity.getLocation())) {
            return;
          }
          if(hitList.contains(((LivingEntity) entity).getEntityId())) {
            continue;
          }
          hitList.add(((LivingEntity) entity).getEntityId());
          F0_DealDamage.dealAmount(killer, (LivingEntity) entity,
            (F0_Stats.getSpecialDamageList(F7_Wizard.getKitID() - 1)[Vars.getFighter(killer)
              .getKitIndex()]));
        }
      }
      origin = origin.subtract(dX * j, dY * j, dZ * j);
    }
  }

  public static void doWizardDrop(E1_Fighter pFight) {
    pFight.getWizardKit().openChangeSpell();
  }


  public static void gustLaunch(Player killer) {
    Location playerLocation = killer.getLocation();
    killer.playSound(killer.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
    if (playerLocation.getPitch() > 60) {
      Zgen.launchPlayer(killer, -1.5);
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
      killer.getWorld().spawnParticle(Particle.CLOUD, origin.getX(), origin.getY(), origin.getZ(),
        5);
      ArrayList<Entity> entityList =
        (ArrayList<Entity>) killer.getWorld().getNearbyEntities(origin, 2.5, 2.5, 2.5);
      for (Entity entity : entityList) {
        if (entity instanceof LivingEntity) 
          if(hitList.contains(((LivingEntity) entity).getEntityId())) {
            continue;
          }
          hitList.add(((LivingEntity) entity).getEntityId());{
          F0_DealDamage.dealAmount(killer, (LivingEntity) entity,
            (F0_Stats.getSpecialDamageList(F7_Wizard.getKitID() - 1)[Vars.getFighter(killer)
              .getKitIndex()]));
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

}
