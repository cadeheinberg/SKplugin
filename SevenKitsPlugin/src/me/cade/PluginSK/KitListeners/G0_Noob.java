package me.cade.PluginSK.KitListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Main;

public class G0_Noob {

  //experience bar starts out as full
  //when special ability is used, it drains the experience bar slowly
  //when the experience bar reaches 0, the special ability ends, play a cancel noise
  //the experience bar then slowly climbs back up
  //when it reaches full, a ding goes off that it is ready to use again
  
  // when sword is dropped give strength/regen boost
	
	private static Plugin plugin = Main.getPlugin(Main.class);
  
  public static void doDrop(Player killer) {
	if (killer.getCooldown(Material.BIRCH_FENCE) > 0 || killer.getCooldown(Material.JUNGLE_FENCE) > 0) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    if(Fighter.get(killer).getGroundPoundTask() != -1) {
	      return;
	}
    activateSpecial(killer, 200, 50);
  }
  
  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
	G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, durationTicks, 1));
    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTicks, 1));
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    doIceCageSpell(killer);
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_HORSE_ANGRY, 8, 1);
  }
  
  public static void deActivateSpecial(Player killer) {
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }
  
  private static void doIceCageSpell(Player player) {
	    Item iceCube = player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ICE, 1));
	    Vector currentDirection = player.getLocation().getDirection().normalize();
	    currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
	    iceCube.setVelocity(currentDirection);
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	      @Override
	      public void run() {
	        iceCube.remove();
	        Location iceLocation = iceCube.getLocation();
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

	        player.getWorld().playSound(iceBlock.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, 1);
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

  
}