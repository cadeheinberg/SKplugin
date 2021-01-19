package me.cade.PluginSK.KitListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.cade.PluginSK.Glowing;

public class G0_Noob {

  //experience bar starts out as full
  //when special ability is used, it drains the experience bar slowly
  //when the experience bar reaches 0, the special ability ends, play a cancel noise
  //the experience bar then slowly climbs back up
  //when it reaches full, a ding goes off that it is ready to use again
  
  // when sword is dropped give strength/regen boost
  
  public static void doDrop(Player killer) {
    if(killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 200, 300);
  }
  
  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
    for(Player player : Bukkit.getServer().getOnlinePlayers()) {
      Glowing.setGlowing(killer, player, false);
    }
    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, durationTicks, 1));
    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTicks, 2));
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_HORSE_ANGRY, 8, 1);
  }

}
