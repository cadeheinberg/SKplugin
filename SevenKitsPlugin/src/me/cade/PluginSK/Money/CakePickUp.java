package me.cade.PluginSK.Money;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.cade.PluginSK.Fighter;

public class CakePickUp {

  public static void pickUpCake(Player player) {
    Fighter fighter = Fighter.get(player);
    fighter.incCakesByAmount(10);
    fighter.incExpByAmount(3);
    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 8, 1);
    player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 140, 1));
  }
  
}
