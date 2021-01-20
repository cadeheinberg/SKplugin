package me.cade.PluginSK.KitListeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.cade.PluginSK.AbilityEnchantment;

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
    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationTicks, 0));
    killer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, durationTicks, 0));
    killer.setWalkSpeed((float) 0.3);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
  }
  
  public static void deActivateSpecial(Player killer) {
    killer.setWalkSpeed((float) 0.2);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }
  
}