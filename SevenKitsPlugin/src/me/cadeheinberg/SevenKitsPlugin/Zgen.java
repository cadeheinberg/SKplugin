package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Zgen {

  public static boolean safeZone(Location location) {
    if(location.getY() > 186) {
      return true;
    }
    return false;
  }
  
  public static boolean worldSand(World w) {
    if(w == A_Main.sandbox || w == A_Main.sandboxNether) {
      return true;
    }
    return false;
  }
  
  public static boolean worldSandSafe(Location location) {
    if(location.getWorld() != A_Main.sandbox) {
      return false;
    }
    double x = location.getX();
    double z = location.getZ();

    //spawn
    if (x <= -1020 && x >= -1089) {
      if (z <= -99 && z >= -168) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean worldSandLavaSafe(Location location) {
    if(location.getWorld() != A_Main.sandbox) {
      return false;
    }
    double x = location.getX();
    double z = location.getZ();

    //spawn
    if (x <= -1005 && x >= -1104) {
      if (z <= -84 && z >= -183) {
        return true;
      }
    }
    return false;
  }


  public static boolean inPlayGround(Location location) {
    double x = location.getX();
    double z = location.getZ();
    if (location.getY() < 195) {
      return false;
    }

    //spawn
    if (x <= -1062 && x >= -1079) {
      if (z <= -130 && z >= -147) {
        return true;
      }
    }
    
    return false;
  }
  
  public static boolean inPlayGroundPVP(Location location) {
    double x = location.getX();
    double z = location.getZ();
    if (location.getY() < 195) {
      return false;
    }

    //spawn
    if (x <= -1061 && x >= -1079) {
      if (z <= -129 && z >= -147) {
        return true;
      }
    }
    
    return false;
  }

  public static void launchPlayer(Entity entity, Double power) {
    if (!(entity instanceof Player)) {
      if (entity.getPassengers().size() < 1) {
        return;
      }
      Player player = (Player) entity.getPassengers().get(0);
      Location playerLocation = player.getLocation();
      if (playerLocation.getPitch() < 25) {
        playerLocation.setPitch((float) 25);
      }
      Vector currentDirection = playerLocation.getDirection().normalize();
      currentDirection = currentDirection.multiply(new Vector(power, power, power));
      entity.setVelocity(currentDirection);
    } else {
      Vector currentDirection = entity.getLocation().getDirection().normalize();
      currentDirection = currentDirection.multiply(new Vector(power, power, power));
      entity.setVelocity(currentDirection);
    }
  }

  public static boolean soccerZone(Location location) {
    if(location.getX() > -1026) {
      return true;
    }
    return false;
  }
}
