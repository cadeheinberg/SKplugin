package me.cade.PluginSK.kitlisteners;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;
import me.cade.PluginSK.F0_DealDamage;
import me.cade.PluginSK.G8_Cooldown;
import me.cade.PluginSK.Vars;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F3_Shotty;

public class G3_Shotty {

  public static boolean doShotty(Player player, int weaponId) {
    if (player.getCooldown(F3_Shotty.getItemList()[weaponId].getWeaponItem().getType()) > 0) {
      return false;
    }
    shootSnowballs(player);
    launchPlayer(player, F0_Stats.getPowerList(F3_Shotty.getKitID() - 1)[weaponId]);
    if(weaponId == 3) {
      if(player.getLevel() >= 2) {
        doShottyReload(player);
        return false;
      }else {
        player.setLevel(player.getLevel() + 1);
        return true;
      }
    }
    G8_Cooldown.setCooldown(player, F3_Shotty.getItemList()[weaponId].getWeaponItem().getType(),
      F0_Stats.getTicksList(F3_Shotty.getKitID() - 1)[weaponId]);
    return true;
  }
  
  public static void doShottyReload(Player player) {
    if(player.getCooldown(F3_Shotty.getItemList()[3].getWeaponItem().getType()) > 0) {
      return;
    }
    G8_Cooldown.setCooldown(player, F3_Shotty.getItemList()[3].getWeaponItem().getType(),
      F0_Stats.getTicksList(F3_Shotty.getKitID() - 1)[3]);
    player.setLevel(1);
  }

  public static void launchPlayer(Player player, Double power) {
    Vector currentDirection = player.getLocation().getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(power, power, power));
    player.setVelocity(currentDirection);
  }

  public static void shootSnowballs(Player player) {
    player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 8, 1);

    Snowball ball = player.launchProjectile(Snowball.class);
    Vector currentDirection = player.getLocation().getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(2.3, 2, 2));
    ball.setVelocity(currentDirection);

    Snowball ball2 = player.launchProjectile(Snowball.class);
    Vector currentDirection2 = player.getLocation().getDirection().normalize();
    currentDirection2 = currentDirection2.multiply(new Vector(2, 2, 2));
    ball2.setVelocity(currentDirection2);

    Snowball ball3 = player.launchProjectile(Snowball.class);
    Vector currentDirection3 = player.getLocation().getDirection().normalize();
    currentDirection3 = currentDirection3.multiply(new Vector(1.7, 2, 2));
    ball3.setVelocity(currentDirection3);

    Snowball ball4 = player.launchProjectile(Snowball.class);
    Vector currentDirection4 = player.getLocation().getDirection().normalize();
    currentDirection4 = currentDirection4.multiply(new Vector(2, 2.5, 2));
    ball4.setVelocity(currentDirection4);

    Snowball ball5 = player.launchProjectile(Snowball.class);
    Vector currentDirection5 = player.getLocation().getDirection().normalize();
    currentDirection5 = currentDirection5.multiply(new Vector(2, 1.5, 2));
    ball5.setVelocity(currentDirection5);
  }


  public static void doHitter(Player shooter, Entity hitEntity, Projectile throwEntity) {
    if (!(hitEntity instanceof LivingEntity)) {
      return;
    }
    if (hitEntity instanceof LivingEntity) {
      F0_DealDamage.dealAmount(shooter, (LivingEntity) hitEntity, (F0_Stats
        .getSpecialDamageList(F3_Shotty.getKitID() - 1)[Vars.getFighter(shooter).getKitIndex()]));
      Vector currentDirection = throwEntity.getVelocity().normalize();
      currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
      ((LivingEntity) hitEntity).setVelocity(currentDirection);
    }
  }
}
