package me.cade.PluginSK.KitListeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.BuildKits.F1_Beserker;
import me.cade.PluginSK.BuildKits.F_Stats;

public class G1_Beserker {

  public static void doRightClick(Player player) {
    if (player.getCooldown(F1_Beserker.getWeapon().getWeaponItem().getType()) > 0) {
      return;
    }
    player.setCooldown(F1_Beserker.getWeapon().getWeaponItem().getType(),
      ((F_Stats.getTicksList(F1_Beserker.getKitID())[0])));

    doBoosterJump(player);
  }

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
	killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationTicks, 0));
    killer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, durationTicks, 0));
    killer.setWalkSpeed((float) 0.3);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
  }

  public static void deActivateSpecial(Player killer) {
    killer.setWalkSpeed((float) 0.2);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }

  private static void doBoosterJump(Player player) {
	    player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
	    Vector currentDirection = player.getLocation().getDirection().normalize();
	    currentDirection = currentDirection.multiply(new Vector(1.3, 1.3, 1.3));
	    player.setVelocity(currentDirection);
  }

}
