package me.cade.PluginSK.KitListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.cade.PluginSK.Glowing;

public class G1_Beserker {

  // right click to activate temporay speed boost
  
  public static void doDrop(Player killer) {
    if(killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 300, 500);
  }
  
  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
    for(Player player : Bukkit.getServer().getOnlinePlayers()) {
      Glowing.setGlowing(killer, player, true);
    }
    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationTicks, 3));
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
  }
  
}
