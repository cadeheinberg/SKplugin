package me.cade.PluginSK.KitListeners;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.Fighter;

public class G6_Grief {
  
  // Every damage you do gives you health. Like a vampire
  
  public static void doDrop(Player killer) {
	 if (killer.getCooldown(Material.BIRCH_FENCE) > 0 || killer.getCooldown(Material.JUNGLE_FENCE) > 0) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 300, 500);
  }
  
  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_ENDERMAN_DEATH, 16, 1);
  }
  
  public static void deactivateSpecialAbility(Player killer) {
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }

  public static void doStealHealth(Player killer, Fighter fKiller, Player victim) {
    if(fKiller.isAbilityActive()) {
      double combined = killer.getHealth() + 1.5;
      if(combined > 20) {
        killer.setHealth(20);
      }else {
        killer.setHealth(combined);
      }
      killer.playSound(killer.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
      Firework firework = killer.getWorld().spawn(killer.getLocation(), Firework.class);
      FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
      data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.YELLOW)
        .with(Type.BALL_LARGE).withFlicker().build());
      data.setPower(1);
      firework.setFireworkMeta(data);
    }
  }

}
