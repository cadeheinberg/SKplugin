package me.cadeheinberg.SevenKitsPlugin;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class I2_TNT {

  private static Weapon throwTNT;
  private static HashMap<Integer, Player> throwers;

  public static void makeTNT() {
    throwTNT = new Weapon(Material.COAL, ChatColor.YELLOW + "Grenade", ChatColor.WHITE + "Right click to throw");
    throwers = new HashMap<Integer, Player>();
  }

  public static Weapon getTNT() {
    return throwTNT;
  }

  public static void doTNT(Player player) {
    if (player.getCooldown(throwTNT.getWeaponItem().getType()) > 0) {
      return;
    }
    player.setCooldown(throwTNT.getWeaponItem().getType(), 200);
    Entity tnt = A_Main.kitpvp.spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
    TNTPrimed fuse = (TNTPrimed) tnt;
    fuse.setFuseTicks(15);
    Vector currentDirection4 = player.getLocation().getDirection().normalize();
    currentDirection4 = currentDirection4.multiply(new Vector(1, 1, 1));
    tnt.setVelocity(currentDirection4);
    addThrower(tnt.getEntityId(), player);
  }

  public static void addThrower(Integer setter, Player player) {
    throwers.put(setter, player);
  }

  public static Player getThrower(Integer getter) {
    return throwers.get(getter);
  }

  public static void removeThrower(Integer getter) {
    throwers.remove(getter);
  }

  public static void resetCooldown(Player player) {
    player.setCooldown(throwTNT.getWeaponItem().getType(), 0);
  }


}
