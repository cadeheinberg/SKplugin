package me.cade.PluginSK.kitlisteners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import me.cade.PluginSK.F0_DealDamage;
import me.cade.PluginSK.Vars;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F4_Goblin;

public class G4_Goblin {

  public static void doBowLaunch(Player player, double force) {
    if (force > 0.75) {
      int ind = Vars.getFighter(player).getKitIndex();
      if (ind == 0) {
        Arrow ball = player.launchProjectile(Arrow.class);
        Vector currentDirection = player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(2.8, 2.4, 2.4));
        ball.setVelocity(currentDirection);

        Arrow ball2 = player.launchProjectile(Arrow.class);
        Vector currentDirection2 = player.getLocation().getDirection().normalize();
        currentDirection2 = currentDirection2.multiply(new Vector(2.4, 2.4, 2.4));
        ball2.setVelocity(currentDirection2);
        return;
      } else if (ind == 1) {
        Arrow ball = player.launchProjectile(Arrow.class);
        Vector currentDirection = player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(2.8, 2.4, 2.4));
        ball.setVelocity(currentDirection);

        Arrow ball2 = player.launchProjectile(Arrow.class);
        Vector currentDirection2 = player.getLocation().getDirection().normalize();
        currentDirection2 = currentDirection2.multiply(new Vector(2.4, 2.4, 2.4));
        ball2.setVelocity(currentDirection2);
        
        Arrow ball3 = player.launchProjectile(Arrow.class);
        Vector currentDirection3 = player.getLocation().getDirection().normalize();
        currentDirection3 = currentDirection3.multiply(new Vector(2, 2.4, 2.4));
        ball3.setVelocity(currentDirection3);
        return;
      } else if (ind == 2) {
        Arrow ball = player.launchProjectile(Arrow.class);
        Vector currentDirection = player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(2.8, 2.4, 2.4));
        ball.setVelocity(currentDirection);

        Arrow ball2 = player.launchProjectile(Arrow.class);
        Vector currentDirection2 = player.getLocation().getDirection().normalize();
        currentDirection2 = currentDirection2.multiply(new Vector(2.4, 2.4, 2.4));
        ball2.setVelocity(currentDirection2);

        Arrow ball3 = player.launchProjectile(Arrow.class);
        Vector currentDirection3 = player.getLocation().getDirection().normalize();
        currentDirection3 = currentDirection3.multiply(new Vector(2, 2.4, 2.4));
        ball3.setVelocity(currentDirection3);
        
        Arrow ball4 = player.launchProjectile(Arrow.class);
        Vector currentDirection4 = player.getLocation().getDirection().normalize();
        currentDirection4 = currentDirection4.multiply(new Vector(2.4, 2.9, 2.4));
        ball4.setVelocity(currentDirection4);
        return;
      } else if (ind == 3) {
        Arrow ball = player.launchProjectile(Arrow.class);
        Vector currentDirection = player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(2.8, 2.4, 2.4));
        ball.setVelocity(currentDirection);

        Arrow ball2 = player.launchProjectile(Arrow.class);
        Vector currentDirection2 = player.getLocation().getDirection().normalize();
        currentDirection2 = currentDirection2.multiply(new Vector(2.4, 2.4, 2.4));
        ball2.setVelocity(currentDirection2);

        Arrow ball3 = player.launchProjectile(Arrow.class);
        Vector currentDirection3 = player.getLocation().getDirection().normalize();
        currentDirection3 = currentDirection3.multiply(new Vector(2, 2.4, 2.4));
        ball3.setVelocity(currentDirection3);

        Arrow ball4 = player.launchProjectile(Arrow.class);
        Vector currentDirection4 = player.getLocation().getDirection().normalize();
        currentDirection4 = currentDirection4.multiply(new Vector(2.4, 2.9, 2.4));
        ball4.setVelocity(currentDirection4);

        Arrow ball5 = player.launchProjectile(Arrow.class);
        Vector currentDirection5 = player.getLocation().getDirection().normalize();
        currentDirection5 = currentDirection5.multiply(new Vector(2.4, 2, 2.4));
        ball5.setVelocity(currentDirection5);
        return;
      }
    }
  }
  
  public static void doGoblinArrowHit(Player shooter, LivingEntity victim, Projectile throwEntity) {
    if (victim instanceof LivingEntity) {
      F0_DealDamage.dealAmount(shooter, (LivingEntity) victim, (F0_Stats
        .getSpecialDamageList(F4_Goblin.getKitID() - 1)[Vars.getFighter(shooter).getKitIndex()]));
      Vector currentDirection = throwEntity.getVelocity().normalize();
      currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
      ((LivingEntity) victim).setVelocity(currentDirection);
    }
  }

}
