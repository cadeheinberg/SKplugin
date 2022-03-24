package me.cade.PluginSK.KitListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.Damaging.DealDamage;

public class G0_Noob {

  //experience bar starts out as full
  //when special ability is used, it drains the experience bar slowly
  //when the experience bar reaches 0, the special ability ends, play a cancel noise
  //the experience bar then slowly climbs back up
  //when it reaches full, a ding goes off that it is ready to use again
  
  // when sword is dropped give strength/regen boost

	  private static Plugin plugin = Main.getPlugin(Main.class);
  
  public static void doDrop(Player killer) {
    if(killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    if(Fighter.get(killer).getNoobTask() != -1) {
	      return;
	}
    activateSpecial(killer, 200, 300);
  }
  
  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, durationTicks, 0));
    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTicks, 1));
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);

	doJump(killer, Fighter.get(killer));
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_HORSE_ANGRY, 8, 1);
  }
  
  public static void deActivateSpecial(Player killer) {
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }
  
  public static void doJump(Player player, Fighter pFight) {
	    player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
	    launchPlayer(player, 1.4, pFight);
	  }

	  public static void launchPlayer(Player player, Double power, Fighter pFight) {
	    Location local = player.getLocation();
	    local.setPitch(-60);
	    Vector currentDirection = local.getDirection().normalize();
	    currentDirection = currentDirection.multiply(new Vector(power, power, power));
	    player.setVelocity(currentDirection);
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	      @Override
	      public void run() {
	        listenForFall(player, pFight);
	      }
	    }, 5);
	  }
	  @SuppressWarnings("deprecation")
	  public static void listenForFall(Player player, Fighter pFight) {
	    pFight.setNoobTask(new BukkitRunnable() {
	      @Override
	      public void run() {
	        if (player == null) {
	          stopListening(player, pFight);
	          return;
	        }
	        if (!player.isOnline()) {
	          stopListening(player, pFight);
	          return;
	        }
	        if (player.isDead()) {
	          stopListening(player, pFight);
	          return;
	        }
	        if (player.isOnGround()) {
	          stopListening(player, pFight);
	          doGroundHit(player, player.getLocation(), 0.3);
	          return;
	        }
	        if (player.isSneaking()) {
	          launchPlayerDown(player, 1.5, pFight);
	        }
	      }
	    }.runTaskTimer(plugin, 0L, 1L).getTaskId());
	  }

	  public static void launchPlayerDown(Player player, Double power, Fighter pFight) {
	    Location local = player.getLocation();
	    local.setPitch(80);
	    Vector currentDirection = local.getDirection().normalize();
	    currentDirection = currentDirection.multiply(new Vector(power, power, power));
	    player.setVelocity(currentDirection);
	  }

	  public static void stopListening(Player player, Fighter pFight) {
	    Bukkit.getScheduler().cancelTask(pFight.getNoobTask());
	    pFight.setNoobTask(-1);
	  }

	  //make this freeze players also
	  public static void doGroundHit(Player shooter, Location location, double power) {
	    shooter.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, location.getX(), location.getY() + 1,
	      location.getZ(), 3);
	    shooter.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
	    for (Entity ent : shooter.getWorld().getNearbyEntities(location, 4, 4, 4)) {
	      if (!(ent instanceof LivingEntity)) {
	        return;
	      }
	     DealDamage.dealAmount(shooter, (LivingEntity) ent, 5.0);
	      Location upShoot = ent.getLocation();
	      if (ent.isOnGround()) {
	        upShoot.setY(upShoot.getY() + 1);
	      }
	      Vector currentDirection = upShoot.toVector().subtract(location.toVector());
	      currentDirection = currentDirection.multiply(new Vector(power, power, power));
	      ((LivingEntity) ent).setVelocity(currentDirection);
	    }
	  }

	}
