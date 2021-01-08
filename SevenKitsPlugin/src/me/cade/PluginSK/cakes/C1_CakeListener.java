package me.cade.PluginSK.cakes;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.cade.PluginSK.E1_Fighter;
import me.cade.PluginSK.Vars;

public class C1_CakeListener {

  public static void pickUpCake(Player player) {
    E1_Fighter pFight = Vars.getFighter(player);
    pFight.incTokens(1);
    pFight.incExp(3);
    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 8, 1);
    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 140, 1));
  }
}


