package me.cade.PluginSK.kitlisteners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import me.cade.PluginSK.G8_Cooldown;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F2_Booster;

public class G2_Booster {

  public static boolean doBooster(Player player, int weaponId) {
    if (player.getCooldown(F2_Booster.getItemList()[weaponId].getWeaponItem().getType()) > 0) {
      return false;
    }
    player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
    launchPlayer(player, F0_Stats.getPowerList(F2_Booster.getKitID() - 1)[weaponId]);
    G8_Cooldown.setCooldown(player, F2_Booster.getItemList()[weaponId].getWeaponItem().getType(),
      F0_Stats.getTicksList(F2_Booster.getKitID() - 1)[weaponId]);
    return true;
  }

  public static void launchPlayer(Player player, Double power) {
    Vector currentDirection = player.getLocation().getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(power, power, power));
    player.setVelocity(currentDirection);
}

}
