package me.cade.PluginSK.KitListeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.BuildKits.F5_Heavy;
import me.cade.PluginSK.BuildKits.F_Stats;

public class G5_Heavy {
  
  // inifinite ammo
  // normally, gun shoots slow
  // once dropped, the gun will shoot faster for temporay amount of time
  
  public static void doRightClick(Player player) {
    if (player.getCooldown(F5_Heavy.getWeapon().getWeaponItem().getType()) > 0) {
      return;
    }
    shootArrow(player);
    if (Fighter.fighters.get(player.getUniqueId()).isFighterAbility()) {
      //nothing yet
    } else {
      player.setCooldown(F5_Heavy.getWeapon().getWeaponItem().getType(),
        F_Stats.getTicksList(F5_Heavy.getKitID())[0]);
    }

  }
  
  public static void doDrop(Player killer) {
    if(killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 300, 500);
  }
  
  private static void shootArrow(Player player) {
    player.launchProjectile(Arrow.class);
    player.playSound(player.getLocation(), Sound.BLOCK_WOOL_PLACE, 8, 1);
  }
  
  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_CAT_BEG_FOR_FOOD, 8, 1);
  }

}
